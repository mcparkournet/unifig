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

import java.util.List;
import java.util.Map;
import net.mcparkour.octenace.codec.common.Codecs;
import net.mcparkour.octenace.codec.common.extra.ExtraCodecs;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.codec.registry.cached.CachedCodecRegistryBuilder;
import net.mcparkour.octenace.mapper.CommonDocumentMapperFactory;
import net.mcparkour.octenace.mapper.CommonMapper;
import net.mcparkour.octenace.mapper.DocumentMapperFactory;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidator;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidators;
import net.mcparkour.octenace.mapper.property.name.NameConverter;
import net.mcparkour.octenace.mapper.property.name.NameConverters;
import net.mcparkour.unifig.codec.VectorCodec;
import net.mcparkour.unifig.document.array.PaperArrayFactory;
import net.mcparkour.unifig.document.object.PaperObjectFactory;
import net.mcparkour.unifig.document.reader.PaperReader;
import net.mcparkour.unifig.document.value.PaperValueFactory;
import net.mcparkour.unifig.document.writer.PaperWriter;
import net.mcparkour.unifig.options.Options;
import net.mcparkour.unifig.options.OptionsBuilder;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

public class PaperConfigurationFactory implements ConfigurationFactory {

	private DocumentMapperFactory<Map<String, Object>, List<Object>, Object> mapperFactory;

	public PaperConfigurationFactory() {
		var objectFactory = new PaperObjectFactory();
		var arrayFactory = new PaperArrayFactory();
		var valueFactory = new PaperValueFactory();
		NameConverter nameConverter = NameConverters.KEBAB_CASE_NAME_CONVERTER;
		List<PropertyInvalidator> propertyInvalidators = PropertyInvalidators.COMMON_PROPERTY_INVALIDATORS;
		var codecRegistry = createCodecRegistry();
		var mapper = new CommonMapper<>(objectFactory, arrayFactory, valueFactory, nameConverter, propertyInvalidators, codecRegistry);
		this.mapperFactory = new CommonDocumentMapperFactory<>(mapper);
	}

	private static CodecRegistry<Map<String, Object>, List<Object>, Object> createCodecRegistry() {
		VectorCodec vectorCodec = new VectorCodec();
		return new CachedCodecRegistryBuilder<Map<String, Object>, List<Object>, Object>()
			.registry(Codecs.createCommonCodecRegistry())
			.registry(ExtraCodecs.createExtraCodecRegistry())
			.codec(Vector.class, vectorCodec)
			.build();
	}

	@Override
	public <T> Configuration<T> createConfiguration(Class<T> configurationType, @Nullable T defaultConfiguration, Options options) {
		PaperModel model = new PaperModel();
		var mapper = this.mapperFactory.createMapper(configurationType);
		PaperReader reader = new PaperReader();
		PaperWriter writer = new PaperWriter(options);
		return new CommonConfiguration<>(configurationType, defaultConfiguration, options, model, mapper, reader, writer);
	}

	@Override
	public Options createOptions() {
		return new OptionsBuilder()
			.indentSize(2)
			.build();
	}
}
