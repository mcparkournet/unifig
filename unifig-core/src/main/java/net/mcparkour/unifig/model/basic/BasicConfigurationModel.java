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

package net.mcparkour.unifig.model.basic;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import net.mcparkour.unifig.annotation.Property;
import net.mcparkour.unifig.codec.Codec;
import net.mcparkour.unifig.codec.CodecNotFoundException;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.condition.FieldCondition;
import net.mcparkour.unifig.model.ConfigurationModel;
import net.mcparkour.unifig.model.ConfigurationModelBuilder;
import net.mcparkour.unifig.model.section.ConfigurationModelSection;
import net.mcparkour.unifig.model.section.ConfigurationModelSectionFactory;
import net.mcparkour.unifig.model.value.ConfigurationModelValue;
import net.mcparkour.unifig.model.value.ConfigurationModelValueFactory;
import net.mcparkour.unifig.util.reflection.Reflections;
import org.jetbrains.annotations.Nullable;

public class BasicConfigurationModel<S, A, V> implements ConfigurationModel<S, A, V> {

	private ConfigurationModelSectionFactory<S, A, V> configurationModelSectionFactory;
	private ConfigurationModelValueFactory<S, A, V> configurationModelValueFactory;
	private CodecRegistry<S, A, V> codecRegistry;
	private List<FieldCondition> fieldConditions;

	public static <S, A, V> ConfigurationModelBuilder<S, A, V> builder() {
		return new BasicConfigurationModelBuilder<>();
	}

	public BasicConfigurationModel(ConfigurationModelSectionFactory<S, A, V> configurationModelSectionFactory, ConfigurationModelValueFactory<S, A, V> configurationModelValueFactory, CodecRegistry<S, A, V> codecRegistry, List<FieldCondition> fieldConditions) {
		this.configurationModelSectionFactory = configurationModelSectionFactory;
		this.configurationModelValueFactory = configurationModelValueFactory;
		this.codecRegistry = codecRegistry;
		this.fieldConditions = fieldConditions;
	}

	@Override
	public ConfigurationModelSection<S, A, V> fromConfiguration(Object configuration) {
		ConfigurationModelSection<S, A, V> modelSection = this.configurationModelSectionFactory.createModelSection();
		Class<?> objectClass = configuration.getClass();
		Field[] fields = objectClass.getDeclaredFields();
		for (Field field : fields) {
			if (isFieldValid(field)) {
				field.trySetAccessible();
				String fieldName = getFieldName(field);
				Object fieldValue = Reflections.getFieldValue(field, configuration);
				Class<?> fieldType = field.getType();
				ConfigurationModelValue<S, A, V> modelValue = toModelValue(fieldValue, fieldType);
				modelSection.set(fieldName, modelValue);
			}
		}
		return modelSection;
	}

	private ConfigurationModelValue<S, A, V> toModelValue(@Nullable Object object, Class<?> type) {
		if (object == null) {
			return this.configurationModelValueFactory.createNullModelValue();
		}
		Codec<S, A, V, Object> codec = getObjectCodec(type);
		return codec.encode(object);
	}

	@Override
	public <T> T toConfiguration(ConfigurationModelSection<S, A, V> modelSection, Class<T> configurationType) {
		Constructor<T> constructor = Reflections.getSerializationConstructor(configurationType);
		T instance = Reflections.newInstance(constructor);
		Field[] fields = configurationType.getDeclaredFields();
		for (Field field : fields) {
			if (isFieldValid(field)) {
				field.trySetAccessible();
				String fieldName = getFieldName(field);
				Class<?> fieldType = field.getType();
				ConfigurationModelValue<S, A, V> modelValue = modelSection.get(fieldName);
				Object object = toObject(modelValue, fieldType);
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
	private Object toObject(ConfigurationModelValue<S, A, V> modelValue, Class<?> type) {
		if (modelValue.isNull()) {
			return null;
		}
		Codec<S, A, V, Object> codec = getObjectCodec(type);
		return codec.decode(modelValue);
	}

	@SuppressWarnings("unchecked")
	private Codec<S, A, V, Object> getObjectCodec(Class<?> type) {
		Codec<S, A, V, ?> codec = this.codecRegistry.get(type);
		if (codec == null) {
			throw new CodecNotFoundException(type);
		}
		return (Codec<S, A, V, Object>) codec;
	}

	@Override
	public ConfigurationModelSectionFactory<S, A, V> getConfigurationModelSectionFactory() {
		return this.configurationModelSectionFactory;
	}

	@Override
	public ConfigurationModelValueFactory<S, A, V> getConfigurationModelValueFactory() {
		return this.configurationModelValueFactory;
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
