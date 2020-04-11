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
import net.mcparkour.octenace.codec.registry.CodecRegistryBuilder;
import net.mcparkour.octenace.mapper.CommonMapper;
import net.mcparkour.octenace.mapper.Mapper;
import net.mcparkour.octenace.mapper.property.name.NameConverters;
import net.mcparkour.unifig.document.array.SnakeyamlArrayFactory;
import net.mcparkour.unifig.document.object.SnakeyamlObjectFactory;
import net.mcparkour.unifig.document.reader.SnakeyamlReader;
import net.mcparkour.unifig.document.value.SnakeyamlValueFactory;
import net.mcparkour.unifig.document.writer.SnakeyamlWriter;
import net.mcparkour.unifig.options.Options;
import net.mcparkour.unifig.options.OptionsBuilder;
import org.jetbrains.annotations.Nullable;

public class SnakeyamlConfigurationFactory implements ConfigurationFactory {

	@Override
	public <T> Configuration<T> createConfiguration(Class<T> configurationType, @Nullable T defaultConfiguration, Options options) {
		SnakeyamlModel model = new SnakeyamlModel();
		SnakeyamlObjectFactory objectFactory = new SnakeyamlObjectFactory();
		SnakeyamlArrayFactory arrayFactory = new SnakeyamlArrayFactory();
		SnakeyamlValueFactory valueFactory = new SnakeyamlValueFactory();
		Mapper<Map<String, Object>, List<Object>, Object> converter = new CommonMapper<>(objectFactory, arrayFactory, valueFactory, options.getNameConverter(), options.getPropertyInvalidators(), options.getCodecRegistry());
		SnakeyamlReader reader = new SnakeyamlReader();
		SnakeyamlWriter writer = new SnakeyamlWriter(options);
		return new CommonConfiguration<>(configurationType, defaultConfiguration, options, model, converter, reader, writer);
	}

	@Override
	public Options createOptions() {
		return new OptionsBuilder()
			.indentSize(2)
			.nameConverter(NameConverters.KEBAB_CASE_NAME_CONVERTER)
			.codecRegistry(new CodecRegistryBuilder()
				.registry(Codecs.COMMON_CODEC_REGISTRY)
				.registry(ExtraCodecs.EXTRA_CODEC_REGISTRY)
				.build())
			.build();
	}
}
