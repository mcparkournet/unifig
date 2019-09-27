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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.mcparkour.unifig.TestConfiguration;
import net.mcparkour.unifig.model.object.ModelObject;
import net.mcparkour.unifig.model.reader.GsonModelReader;
import net.mcparkour.unifig.model.reader.ModelReader;
import net.mcparkour.unifig.model.writer.GsonModelWriter;
import net.mcparkour.unifig.model.writer.ModelWriter;
import net.mcparkour.unifig.util.Resources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GsonConverterTest {

	private Converter<JsonObject, JsonArray, JsonElement> converter;
	private ModelWriter<JsonObject, JsonArray, JsonElement> writer;
	private ModelReader<JsonObject, JsonArray, JsonElement> reader;
	private String configuration;

	@BeforeEach
	public void setUp() throws IOException {
		GsonConverterFactory factory = new GsonConverterFactory();
		this.converter = factory.createConverter();
		this.writer = new GsonModelWriter();
		this.reader = new GsonModelReader();
		Path configurationPath = Resources.getResourcePath("configuration.json");
		this.configuration = Files.readString(configurationPath);
	}

	@Test
	public void testFromConfigurationObject() {
		ModelObject<JsonObject, JsonArray, JsonElement> object = this.converter.fromConfiguration(new TestConfiguration());
		String written = this.writer.write(object);
		Assertions.assertEquals(this.configuration, written);
	}

	@Test
	public void testToConfigurationObject() {
		ModelObject<JsonObject, JsonArray, JsonElement> object = this.reader.read(this.configuration);
		TestConfiguration testConfiguration = this.converter.toConfiguration(object, TestConfiguration.class);
		Assertions.assertEquals(new TestConfiguration(), testConfiguration);
	}
}
