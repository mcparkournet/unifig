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
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.mapper.DocumentMapper;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidator;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidators;
import net.mcparkour.octenace.mapper.property.name.NameConverter;
import net.mcparkour.unifig.document.array.GsonArrayFactory;
import net.mcparkour.unifig.document.object.GsonObjectFactory;
import net.mcparkour.unifig.document.value.GsonValueFactory;
import net.mcparkour.unifig.options.Options;
import net.mcparkour.unifig.options.OptionsBuilder;

public class GsonConfigurationFactory extends AbstractConfigurationFactory<JsonObject, JsonArray, JsonElement> {

	private static final GsonObjectFactory GSON_OBJECT_FACTORY = new GsonObjectFactory();
	private static final GsonArrayFactory GSON_ARRAY_FACTORY = new GsonArrayFactory();
	private static final GsonValueFactory GSON_VALUE_FACTORY = new GsonValueFactory();
	private static final Options GSON_DEFAULT_OPTIONS = new OptionsBuilder().build();

	public GsonConfigurationFactory() {
		this(NameConverter.identity(), PropertyInvalidators.COMMON_PROPERTY_INVALIDATORS, createDefaultCodecRegistry());
	}

	public GsonConfigurationFactory(NameConverter nameConverter, List<PropertyInvalidator> propertyInvalidators, CodecRegistry<JsonObject, JsonArray, JsonElement> codecRegistry) {
		super(GSON_OBJECT_FACTORY, GSON_ARRAY_FACTORY, GSON_VALUE_FACTORY, GSON_DEFAULT_OPTIONS, nameConverter, propertyInvalidators, codecRegistry);
	}

	@Override
	public <T> Configuration<T> createConfiguration(Class<T> configurationType, T defaultConfiguration, Options options, DocumentMapper<JsonObject, JsonArray, JsonElement, T> mapper) {
		return new GsonConfiguration<>(configurationType, defaultConfiguration, options, mapper);
	}
}
