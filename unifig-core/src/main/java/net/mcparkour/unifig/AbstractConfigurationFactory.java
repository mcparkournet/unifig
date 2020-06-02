/*
 * MIT License
 *
 * Copyright (c) 2019-2020 MCParkour
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

package net.mcparkour.unifig;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.mcparkour.common.reflection.Reflections;
import net.mcparkour.octenace.codec.common.Codecs;
import net.mcparkour.octenace.codec.common.collection.LinkedHashMapCodec;
import net.mcparkour.octenace.codec.common.collection.LinkedHashSetCodec;
import net.mcparkour.octenace.codec.common.extra.ExtraCodecs;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.codec.registry.cached.CachedCodecRegistryBuilder;
import net.mcparkour.octenace.document.array.DocumentArrayFactory;
import net.mcparkour.octenace.document.object.DocumentObjectFactory;
import net.mcparkour.octenace.document.value.DocumentValueFactory;
import net.mcparkour.octenace.mapper.CommonDocumentMapperFactory;
import net.mcparkour.octenace.mapper.CommonMapper;
import net.mcparkour.octenace.mapper.DocumentMapper;
import net.mcparkour.octenace.mapper.DocumentMapperFactory;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidator;
import net.mcparkour.octenace.mapper.property.name.NameConverter;
import net.mcparkour.unifig.options.Options;

public abstract class AbstractConfigurationFactory<O, A, V> implements ConfigurationFactory {

	private DocumentMapperFactory<O, A, V> mapperFactory;
	private Options defaultOptions;

	public AbstractConfigurationFactory(DocumentObjectFactory<O, A, V> objectFactory, DocumentArrayFactory<O, A, V> arrayFactory, DocumentValueFactory<O, A, V> valueFactory, Options defaultOptions, NameConverter nameConverter, List<PropertyInvalidator> propertyInvalidators, CodecRegistry<O, A, V> codecRegistry) {
		this.defaultOptions = defaultOptions;
		var mapper = new CommonMapper<>(objectFactory, arrayFactory, valueFactory, nameConverter, propertyInvalidators, codecRegistry);
		this.mapperFactory = new CommonDocumentMapperFactory<>(mapper);
	}

	protected static <O, A, V> CodecRegistry<O, A, V> createDefaultCodecRegistry() {
		return new CachedCodecRegistryBuilder<O, A, V>()
			.registry(Codecs.createCommonCodecRegistry())
			.registry(ExtraCodecs.createExtraCodecRegistry())
			.codec(Set.class, new LinkedHashSetCodec<>())
			.codec(Map.class, new LinkedHashMapCodec<>())
			.build();
	}

	@Override
	public <T> Configuration<T> createConfiguration(Class<T> configurationType) {
		T defaultConfiguration = createDefaultConfiguration(configurationType);
		return createConfiguration(configurationType, defaultConfiguration, this.defaultOptions);
	}

	@Override
	public <T> Configuration<T> createConfiguration(Class<T> configurationType, Options options) {
		T defaultConfiguration = createDefaultConfiguration(configurationType);
		return createConfiguration(configurationType, defaultConfiguration, options);
	}

	private <T> T createDefaultConfiguration(Class<T> configurationType) {
		Constructor<T> constructor = Reflections.getSerializationConstructor(configurationType);
		return Reflections.newInstance(constructor);
	}

	@Override
	public <T> Configuration<T> createConfiguration(Class<T> configurationType, T defaultConfiguration) {
		return createConfiguration(configurationType, defaultConfiguration, this.defaultOptions);
	}

	@Override
	public <T> Configuration<T> createConfiguration(Class<T> configurationType, T defaultConfiguration, Options options) {
		DocumentMapper<O, A, V, T> mapper = this.mapperFactory.createMapper(configurationType);
		return createConfiguration(configurationType, defaultConfiguration, options, mapper);
	}

	protected abstract <T> Configuration<T> createConfiguration(Class<T> configurationType, T defaultConfiguration, Options options, DocumentMapper<O, A, V, T> mapper);
}
