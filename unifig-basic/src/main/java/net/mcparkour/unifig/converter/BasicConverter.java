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
import java.lang.reflect.Type;
import java.util.List;
import net.mcparkour.common.reflection.Reflections;
import net.mcparkour.common.reflection.type.Types;
import net.mcparkour.unifig.annotation.Property;
import net.mcparkour.unifig.codec.Codec;
import net.mcparkour.unifig.codec.CodecNotFoundException;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.condition.FieldCondition;
import net.mcparkour.unifig.model.array.ModelArrayFactory;
import net.mcparkour.unifig.model.object.ModelObject;
import net.mcparkour.unifig.model.object.ModelObjectFactory;
import net.mcparkour.unifig.model.value.ModelValue;
import net.mcparkour.unifig.model.value.ModelValueFactory;
import net.mcparkour.unifig.options.LetterCase;
import net.mcparkour.unifig.options.Options;
import net.mcparkour.unifig.util.LetterCaseTransformer;
import org.jetbrains.annotations.Nullable;

public class BasicConverter<O, A, V> implements Converter<O, A, V> {

	private ModelObjectFactory<O, A, V> modelObjectFactory;
	private ModelArrayFactory<O, A, V> modelArrayFactory;
	private ModelValueFactory<O, A, V> modelValueFactory;
	private Options options;

	public BasicConverter(ModelObjectFactory<O, A, V> modelObjectFactory, ModelArrayFactory<O, A, V> modelArrayFactory, ModelValueFactory<O, A, V> modelValueFactory, Options options) {
		this.modelObjectFactory = modelObjectFactory;
		this.modelArrayFactory = modelArrayFactory;
		this.modelValueFactory = modelValueFactory;
		this.options = options;
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
				Object fieldValue = Reflections.getFieldValue(field, configuration);
				Type fieldType = field.getGenericType();
				ModelValue<O, A, V> value = toModelValue(fieldValue, fieldType);
				object.setValue(fieldName, value);
			}
		}
		return object;
	}

	@Override
	public ModelValue<O, A, V> toModelValue(@Nullable Object object, Type type) {
		if (object == null) {
			return this.modelValueFactory.createNullModelValue();
		}
		Codec<O, A, V, Object> codec = getObjectCodec(type);
		if (codec == null) {
			ModelObject<O, A, V> modelObject = fromConfiguration(object);
			return this.modelValueFactory.createObjectModelValue(modelObject);
		}
		return codec.encode(object, type, this);
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
				ModelValue<O, A, V> value = object.getValue(fieldName);
				Type fieldType = field.getGenericType();
				Object rawObject = toObject(value, fieldType);
				Reflections.setFieldValue(field, instance, rawObject);
			}
		}
		return instance;
	}

	@Override
	@Nullable
	public Object toObject(ModelValue<O, A, V> value, Type type) {
		if (value.isNull()) {
			return null;
		}
		Codec<O, A, V, Object> codec = getObjectCodec(type);
		if (codec == null) {
			if (!value.isObject()) {
				throw new CodecNotFoundException(type);
			}
			O rawObject = value.asObject();
			ModelObject<O, A, V> object = this.modelObjectFactory.createModelObject(rawObject);
			Type rawType = Types.getRawType(type);
			Class<?> classType = Types.asClassType(rawType);
			return toConfiguration(object, classType);
		}
		return codec.decode(value, type, this);
	}

	@Override
	public boolean isFieldValid(Field field) {
		List<FieldCondition> fieldConditions = this.options.getFieldConditions();
		return fieldConditions.stream()
			.allMatch(condition -> condition.check(field));
	}

	@Override
	public String getFieldName(Field field) {
		Property property = field.getAnnotation(Property.class);
		if (property != null) {
			return property.value();
		}
		String name = field.getName();
		LetterCase defaultKeysLetterCase = this.options.getDefaultKeysLetterCase();
		if (defaultKeysLetterCase == LetterCase.KEBAB) {
			return LetterCaseTransformer.toKebabCase(name);
		}
		if (defaultKeysLetterCase == LetterCase.SNAKE) {
			return LetterCaseTransformer.toSnakeCase(name);
		}
		return name;
	}

	@SuppressWarnings("unchecked")
	@Nullable
	private Codec<O, A, V, Object> getObjectCodec(Type type) {
		CodecRegistry<?, ?, ?> codecRegistry = this.options.getCodecRegistry();
		Type rawType = Types.getRawType(type);
		Class<?> classType = Types.asClassType(rawType);
		Codec<?, ?, ?, ?> codec = codecRegistry.get(classType);
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
	public Options getOptions() {
		return this.options;
	}
}
