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

package net.mcparkour.unifig;

import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.mcparkour.unifig.codec.Codecs;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.codec.registry.CodecRegistryBuilder;
import net.mcparkour.unifig.condition.FieldCondition;
import net.mcparkour.unifig.condition.FieldConditions;
import net.mcparkour.unifig.converter.BasicConverter;
import net.mcparkour.unifig.model.GsonModel;
import net.mcparkour.unifig.model.array.GsonModelArrayFactory;
import net.mcparkour.unifig.model.object.GsonModelObjectFactory;
import net.mcparkour.unifig.model.reader.GsonModelReader;
import net.mcparkour.unifig.model.value.GsonModelValueFactory;
import net.mcparkour.unifig.model.writer.GsonModelWriter;
import net.mcparkour.unifig.options.BasicOptionsBuilder;
import net.mcparkour.unifig.options.Options;
import net.mcparkour.unifig.options.OptionsBuilder;
import org.jetbrains.annotations.Nullable;

public class GsonConfigurationFactory implements ConfigurationFactory {

	@Override
	public <T> Configuration<T> createConfiguration(Class<T> configurationType, @Nullable T defaultConfiguration, Options options) {
		GsonModel model = new GsonModel();
		GsonModelObjectFactory objectFactory = new GsonModelObjectFactory();
		GsonModelArrayFactory arrayFactory = new GsonModelArrayFactory();
		GsonModelValueFactory valueFactory = new GsonModelValueFactory();
		BasicConverter<JsonObject, JsonArray, JsonElement> converter = new BasicConverter<>(objectFactory, arrayFactory, valueFactory, options);
		GsonModelReader reader = new GsonModelReader();
		GsonModelWriter writer = new GsonModelWriter(options);
		return new BasicConfiguration<>(configurationType, defaultConfiguration, options, model, converter, reader, writer);
	}

	@Override
	public OptionsBuilder createOptionsBuilder() {
		CodecRegistryBuilder<JsonObject, JsonArray, JsonElement> codecRegistryBuilder = Codecs.createBasicCodecRegistryBuilder();
		CodecRegistry<JsonObject, JsonArray, JsonElement> codecRegistry = codecRegistryBuilder.build();
		List<FieldCondition> fieldConditions = FieldConditions.createBasicFieldConditionList();
		return new BasicOptionsBuilder()
			.codecRegistry(codecRegistry)
			.fieldConditions(fieldConditions);
	}
}
