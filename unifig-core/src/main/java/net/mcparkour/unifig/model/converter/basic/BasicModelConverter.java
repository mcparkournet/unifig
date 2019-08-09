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

package net.mcparkour.unifig.model.converter.basic;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import net.mcparkour.unifig.annotation.Property;
import net.mcparkour.unifig.codec.Codec;
import net.mcparkour.unifig.codec.CodecNotFoundException;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.condition.FieldCondition;
import net.mcparkour.unifig.model.converter.ModelConverter;
import net.mcparkour.unifig.model.converter.ModelConverterBuilder;
import net.mcparkour.unifig.model.section.ModelSection;
import net.mcparkour.unifig.model.section.ModelSectionFactory;
import net.mcparkour.unifig.model.value.ModelValue;
import net.mcparkour.unifig.model.value.ModelValueFactory;
import net.mcparkour.unifig.util.reflection.Reflections;
import org.jetbrains.annotations.Nullable;

public class BasicModelConverter<S, A, V> implements ModelConverter<S, A, V> {

	private ModelSectionFactory<S, A, V> modelSectionFactory;
	private ModelValueFactory<S, A, V> modelValueFactory;
	private CodecRegistry<S, A, V> codecRegistry;
	private List<? extends FieldCondition> fieldConditions;

	public static <S, A, V> ModelConverterBuilder<S, A, V> builder() {
		return new BasicModelConverterBuilder<>();
	}

	public BasicModelConverter(ModelSectionFactory<S, A, V> modelSectionFactory, ModelValueFactory<S, A, V> modelValueFactory, CodecRegistry<S, A, V> codecRegistry, List<? extends FieldCondition> fieldConditions) {
		this.modelSectionFactory = modelSectionFactory;
		this.modelValueFactory = modelValueFactory;
		this.codecRegistry = codecRegistry;
		this.fieldConditions = fieldConditions;
	}

	@Override
	public ModelSection<S, A, V> fromConfiguration(Object configuration) {
		ModelSection<S, A, V> section = this.modelSectionFactory.createEmptyModelSection();
		Class<?> configurationType = configuration.getClass();
		Field[] fields = configurationType.getDeclaredFields();
		for (Field field : fields) {
			if (isFieldValid(field)) {
				field.trySetAccessible();
				String fieldName = getFieldName(field);
				Class<?> fieldType = field.getType();
				Object fieldValue = Reflections.getFieldValue(field, configuration);
				ModelValue<S, A, V> value = toModelValue(fieldValue, fieldType);
				section.setValue(fieldName, value);
			}
		}
		return section;
	}

	private ModelValue<S, A, V> toModelValue(@Nullable Object object, Class<?> type) {
		if (object == null) {
			return this.modelValueFactory.createNullModelValue();
		}
		Codec<S, A, V, Object> codec = getObjectCodec(type);
		if (codec == null) {
			ModelSection<S, A, V> section = fromConfiguration(object);
			return this.modelValueFactory.createSectionModelValue(section);
		}
		return codec.encode(object);
	}

	@Override
	public <T> T toConfiguration(ModelSection<S, A, V> section, Class<T> configurationType) {
		Constructor<T> constructor = Reflections.getSerializationConstructor(configurationType);
		T instance = Reflections.newInstance(constructor);
		Field[] fields = configurationType.getDeclaredFields();
		for (Field field : fields) {
			if (isFieldValid(field)) {
				field.trySetAccessible();
				String fieldName = getFieldName(field);
				Class<?> fieldType = field.getType();
				ModelValue<S, A, V> value = section.getValue(fieldName);
				Object object = toObject(value, fieldType);
				Reflections.setFieldValue(field, instance, object);
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
	private Object toObject(ModelValue<S, A, V> value, Class<?> type) {
		if (value.isNull()) {
			return null;
		}
		Codec<S, A, V, Object> codec = getObjectCodec(type);
		if (codec == null) {
			if (!value.isSection()) {
				throw new CodecNotFoundException(type);
			}
			S rawSection = value.asSection();
			ModelSection<S, A, V> section = this.modelSectionFactory.createModelSection(rawSection);
			return toConfiguration(section, type);
		}
		return codec.decode(value);
	}

	@SuppressWarnings("unchecked")
	@Nullable
	private Codec<S, A, V, Object> getObjectCodec(Class<?> type) {
		Codec<S, A, V, ?> codec = this.codecRegistry.get(type);
		if (codec == null) {
			return null;
		}
		return (Codec<S, A, V, Object>) codec;
	}

	@Override
	public ModelSectionFactory<S, A, V> getModelSectionFactory() {
		return this.modelSectionFactory;
	}

	@Override
	public ModelValueFactory<S, A, V> getModelValueFactory() {
		return this.modelValueFactory;
	}

	@Override
	public CodecRegistry<S, A, V> getCodecRegistry() {
		return this.codecRegistry;
	}

	@Override
	public List<FieldCondition> getFieldConditions() {
		return List.copyOf(this.fieldConditions);
	}
}
