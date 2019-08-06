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

package net.mcparkour.unifig.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import net.mcparkour.unifig.annotation.Ignored;
import net.mcparkour.unifig.annotation.Property;
import net.mcparkour.unifig.codec.Codec;
import net.mcparkour.unifig.codec.CodecNotFoundException;
import net.mcparkour.unifig.codec.basic.BooleanCodec;
import net.mcparkour.unifig.codec.basic.ByteCodec;
import net.mcparkour.unifig.codec.basic.CharacterCodec;
import net.mcparkour.unifig.codec.basic.DoubleCodec;
import net.mcparkour.unifig.codec.basic.FloatCodec;
import net.mcparkour.unifig.codec.basic.IntegerCodec;
import net.mcparkour.unifig.codec.basic.LongCodec;
import net.mcparkour.unifig.codec.basic.ShortCodec;
import net.mcparkour.unifig.codec.basic.StringCodec;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.codec.registry.CodecRegistryBuilder;
import net.mcparkour.unifig.codec.registry.CodecRegistryBuilderFactory;
import net.mcparkour.unifig.model.section.ConfigurationModelSection;
import net.mcparkour.unifig.model.section.ConfigurationModelSectionFactory;
import net.mcparkour.unifig.model.value.ConfigurationModelValue;
import net.mcparkour.unifig.model.value.ConfigurationModelValueFactory;
import net.mcparkour.unifig.util.reflection.Reflections;
import org.jetbrains.annotations.Nullable;

public class BasicConfigurationModel<S, A, V> implements ConfigurationModel<S, A, V> {

	private CodecRegistry<S, A, V> codecRegistry;
	private ConfigurationModelSectionFactory<S, A, V> modelSectionFactory;
	private ConfigurationModelValueFactory<S, A, V> modelValueFactory;

	public BasicConfigurationModel(CodecRegistryBuilderFactory<S, A, V> codecRegistryBuilderFactory, ConfigurationModelSectionFactory<S, A, V> modelSectionFactory, ConfigurationModelValueFactory<S, A, V> modelValueFactory) {
		this.codecRegistry = createDefaultCodecRegistryBuilder(codecRegistryBuilderFactory, modelValueFactory).build();
		this.modelSectionFactory = modelSectionFactory;
		this.modelValueFactory = modelValueFactory;
	}

	public BasicConfigurationModel(CodecRegistryBuilderFactory<S, A, V> codecRegistryBuilderFactory, CodecRegistry<S, A, V> codecRegistry, ConfigurationModelSectionFactory<S, A, V> modelSectionFactory, ConfigurationModelValueFactory<S, A, V> modelValueFactory) {
		this.codecRegistry = createCodecRegistry(codecRegistryBuilderFactory, codecRegistry, modelValueFactory);
		this.modelSectionFactory = modelSectionFactory;
		this.modelValueFactory = modelValueFactory;
	}

	private CodecRegistry<S, A, V> createCodecRegistry(CodecRegistryBuilderFactory<S, A, V> codecRegistryBuilderFactory, CodecRegistry<S, A, V> codecRegistry, ConfigurationModelValueFactory<S, A, V> modelValueFactory) {
		return createDefaultCodecRegistryBuilder(codecRegistryBuilderFactory, modelValueFactory)
			.with(codecRegistry)
			.build();
	}

	private CodecRegistryBuilder<S, A, V> createDefaultCodecRegistryBuilder(CodecRegistryBuilderFactory<S, A, V> codecRegistryBuilderFactory, ConfigurationModelValueFactory<S, A, V> modelValueFactory) {
		return codecRegistryBuilderFactory.createCodecRegistryBuilder()
			.register(new BooleanCodec<>(modelValueFactory), boolean.class, Boolean.class)
			.register(new CharacterCodec<>(modelValueFactory), char.class, Character.class)
			.register(new ByteCodec<>(modelValueFactory), byte.class, Byte.class)
			.register(new ShortCodec<>(modelValueFactory), short.class, Short.class)
			.register(new IntegerCodec<>(modelValueFactory), int.class, Integer.class)
			.register(new LongCodec<>(modelValueFactory), long.class, Long.class)
			.register(new FloatCodec<>(modelValueFactory), float.class, Float.class)
			.register(new DoubleCodec<>(modelValueFactory), double.class, Double.class)
			.register(new StringCodec<>(modelValueFactory), String.class);
	}

	@Override
	public ConfigurationModelSection<S, A, V> fromConfiguration(Object configuration) {
		ConfigurationModelSection<S, A, V> modelSection = this.modelSectionFactory.createModelSection();
		Class<?> objectClass = configuration.getClass();
		Field[] fields = objectClass.getDeclaredFields();
		for (Field field : fields) {
			if (!Reflections.isStatic(field) && !field.isAnnotationPresent(Ignored.class)) {
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
			return this.modelValueFactory.createNullModelValue();
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
			if (!Reflections.isStatic(field) && !field.isAnnotationPresent(Ignored.class)) {
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

	public CodecRegistry<S, A, V> getCodecRegistry() {
		return this.codecRegistry;
	}

	public ConfigurationModelSectionFactory<S, A, V> getModelSectionFactory() {
		return this.modelSectionFactory;
	}

	public ConfigurationModelValueFactory<S, A, V> getModelValueFactory() {
		return this.modelValueFactory;
	}
}
