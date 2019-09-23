/*
 * MIT License
 *
 * Copyright (c) 2019 MCParkour
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.mcparkour.unifig.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.mcparkour.common.reflection.Reflections;
import net.mcparkour.unifig.annotation.Property;
import net.mcparkour.unifig.codec.Codec;
import net.mcparkour.unifig.codec.CodecNotFoundException;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.condition.FieldCondition;
import net.mcparkour.unifig.model.array.ModelArray;
import net.mcparkour.unifig.model.array.ModelArrayFactory;
import net.mcparkour.unifig.model.object.ModelObject;
import net.mcparkour.unifig.model.object.ModelObjectFactory;
import net.mcparkour.unifig.model.value.ModelValue;
import net.mcparkour.unifig.model.value.ModelValueFactory;
import org.jetbrains.annotations.Nullable;

public class BasicModelConverter<O, A, V> implements ModelConverter<O, A, V> {

	private ModelObjectFactory<O, A, V> modelObjectFactory;
	private ModelArrayFactory<O, A, V> modelArrayFactory;
	private ModelValueFactory<O, A, V> modelValueFactory;
	private CodecRegistry<O, A, V> codecRegistry;
	private List<? extends FieldCondition> fieldConditions;

	public static <O, A, V> ModelConverterBuilder<O, A, V> builder() {
		return new BasicModelConverterBuilder<>();
	}

	public BasicModelConverter(ModelObjectFactory<O, A, V> modelObjectFactory, ModelArrayFactory<O, A, V> modelArrayFactory, ModelValueFactory<O, A, V> modelValueFactory, CodecRegistry<O, A, V> codecRegistry, List<? extends FieldCondition> fieldConditions) {
		this.modelObjectFactory = modelObjectFactory;
		this.modelArrayFactory = modelArrayFactory;
		this.modelValueFactory = modelValueFactory;
		this.codecRegistry = codecRegistry;
		this.fieldConditions = fieldConditions;
	}

	@Override
	public ModelObject<O, A, V> fromConfiguration(Object configuration) {
		ModelObject<O, A, V> object = this.modelObjectFactory.createEmptyModelObject();
		Class<?> configurationType = configuration.getClass();
		Field[] fields = configurationType.getDeclaredFields();
		for (Field field : fields) {
			if (isFieldValid(field)) {
				field.trySetAccessible();
				String fieldName = getFieldName(field);
				Class<?> fieldType = field.getType();
				Object fieldValue = Reflections.getFieldValue(field, configuration);
				ModelValue<O, A, V> value = toModelValue(fieldValue, fieldType);
				object.setValue(fieldName, value);
			}
		}
		return object;
	}

	private ModelValue<O, A, V> toModelValue(@Nullable Object object, Class<?> type) {
		if (object == null) {
			return this.modelValueFactory.createNullModelValue();
		}
		if (List.class.isAssignableFrom(type)) {
			List<?> collection = (List<?>) object;
			ModelArray<O, A, V> array = this.modelArrayFactory.createEmptyModelArray();
			for (Object element : collection) {
				Class<?> elementType = element.getClass();
				ModelValue<O, A, V> elementValue = toModelValue(element, elementType);
				array.addValue(elementValue);
			}
			return this.modelValueFactory.createArrayModelValue(array);
		}
		Codec<O, A, V, Object> codec = getObjectCodec(type);
		if (codec == null) {
			ModelObject<O, A, V> modelObject = fromConfiguration(object);
			return this.modelValueFactory.createObjectModelValue(modelObject);
		}
		return codec.encode(object);
	}

	@Override
	public <T> T toConfiguration(ModelObject<O, A, V> object, Class<T> configurationType) {
		Constructor<T> constructor = Reflections.getSerializationConstructor(configurationType);
		T instance = Reflections.newInstance(constructor);
		Field[] fields = configurationType.getDeclaredFields();
		for (Field field : fields) {
			if (isFieldValid(field)) {
				field.trySetAccessible();
				String fieldName = getFieldName(field);
				Class<?> fieldType = field.getType();
				ModelValue<O, A, V> value = object.getValue(fieldName);
				Object rawObject = toObject(value, fieldType, field);
				Reflections.setFieldValue(field, instance, rawObject);
			}
		}
		return instance;
	}

	private boolean isFieldValid(Field field) {
		return this.fieldConditions.stream()
			.allMatch(condition -> condition.check(field));
	}

	private String getFieldName(Field field) {
		Property property = field.getAnnotation(Property.class);
		if (property != null) {
			return property.value();
		}
		return field.getName();
	}

	@Nullable
	private Object toObject(ModelValue<O, A, V> value, Class<?> type, Field field) {
		if (value.isNull()) {
			return null;
		}
		if (value.isArray()) {
			A rawArray = value.asArray();
			ModelArray<O, A, V> array = this.modelArrayFactory.createModelArray(rawArray);
			int size = array.getSize();
			List<Object> list = new ArrayList<>(size);
			List<Class<?>> genericTypes = Reflections.getGenericTypes(field);
			Class<?> genericType = genericTypes.get(0);
			for (ModelValue<O, A, V> elementValue : array) {
				Object object = toObject(elementValue, genericType, field);
				list.add(object);
			}
			return list;
		}
		Codec<O, A, V, Object> codec = getObjectCodec(type);
		if (codec == null) {
			if (!value.isObject()) {
				throw new CodecNotFoundException(type);
			}
			O rawObject = value.asObject();
			ModelObject<O, A, V> object = this.modelObjectFactory.createModelObject(rawObject);
			return toConfiguration(object, type);
		}
		return codec.decode(value, type);
	}

	@SuppressWarnings("unchecked")
	@Nullable
	private Codec<O, A, V, Object> getObjectCodec(Class<?> type) {
		Codec<O, A, V, ?> codec = this.codecRegistry.get(type);
		if (codec == null) {
			return null;
		}
		return (Codec<O, A, V, Object>) codec;
	}

	@Override
	public ModelObjectFactory<O, A, V> getModelObjectFactory() {
		return this.modelObjectFactory;
	}

	@Override
	public ModelArrayFactory<O, A, V> getModelArrayFactory() {
		return this.modelArrayFactory;
	}

	@Override
	public ModelValueFactory<O, A, V> getModelValueFactory() {
		return this.modelValueFactory;
	}

	@Override
	public CodecRegistry<O, A, V> getCodecRegistry() {
		return this.codecRegistry;
	}

	@Override
	public List<FieldCondition> getFieldConditions() {
		return List.copyOf(this.fieldConditions);
	}
}
