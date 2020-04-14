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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import net.mcparkour.unifig.document.array.GsonArrayFactory;
import net.mcparkour.unifig.document.object.GsonObjectFactory;
import net.mcparkour.unifig.document.reader.GsonReader;
import net.mcparkour.unifig.document.value.GsonValueFactory;
import net.mcparkour.unifig.document.writer.GsonWriter;
import net.mcparkour.unifig.options.Options;
import net.mcparkour.unifig.options.OptionsBuilder;
import org.jetbrains.annotations.Nullable;

public class GsonConfigurationFactory implements ConfigurationFactory {

	private DocumentMapperFactory<JsonObject, JsonArray, JsonElement> mapperFactory;

	public GsonConfigurationFactory() {
		var objectFactory = new GsonObjectFactory();
		var arrayFactory = new GsonArrayFactory();
		var valueFactory = new GsonValueFactory();
		NameConverter nameConverter = NameConverter.identity();
		List<PropertyInvalidator> propertyInvalidators = PropertyInvalidators.COMMON_PROPERTY_INVALIDATORS;
		var codecRegistry = createCodecRegistry();
		var mapper = new CommonMapper<>(objectFactory, arrayFactory, valueFactory, nameConverter, propertyInvalidators, codecRegistry);
		this.mapperFactory = new CommonDocumentMapperFactory<>(mapper);
	}

	private static CodecRegistry<JsonObject, JsonArray, JsonElement> createCodecRegistry() {
		return new CachedCodecRegistryBuilder<JsonObject, JsonArray, JsonElement>()
			.registry(Codecs.createCommonCodecRegistry())
			.registry(ExtraCodecs.createExtraCodecRegistry())
			.build();
	}

	@Override
	public <T> Configuration<T> createConfiguration(Class<T> configurationType, @Nullable T defaultConfiguration, Options options) {
		GsonModel model = new GsonModel();
		var mapper = this.mapperFactory.createMapper(configurationType);
		GsonReader reader = new GsonReader();
		GsonWriter writer = new GsonWriter(options);
		return new CommonConfiguration<>(configurationType, defaultConfiguration, options, model, mapper, reader, writer);
	}

	@Override
	public Options createOptions() {
		return new OptionsBuilder()
			.build();
	}
}
