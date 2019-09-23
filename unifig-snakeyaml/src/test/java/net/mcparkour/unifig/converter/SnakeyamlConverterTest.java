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
import java.util.List;
import java.util.Map;
import net.mcparkour.unifig.TestConfiguration;
import net.mcparkour.unifig.model.object.ModelObject;
import net.mcparkour.unifig.model.reader.ModelReader;
import net.mcparkour.unifig.model.reader.SnakeyamlModelReader;
import net.mcparkour.unifig.model.writer.ModelWriter;
import net.mcparkour.unifig.model.writer.SnakeyamlModelWriter;
import net.mcparkour.unifig.util.Resources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SnakeyamlConverterTest {

	private Converter<Map<String, Object>, List<Object>, Object> converter;
	private ModelWriter<Map<String, Object>, List<Object>, Object> writer;
	private ModelReader<Map<String, Object>, List<Object>, Object> reader;
	private String configuration;

	@BeforeEach
	public void setUp() throws IOException {
		SnakeyamlConverterFactory factory = new SnakeyamlConverterFactory();
		this.converter = factory.createConverter();
		this.writer = new SnakeyamlModelWriter();
		this.reader = new SnakeyamlModelReader();
		Path configurationPath = Resources.getResourcePath("configuration.yml");
		this.configuration = Files.readString(configurationPath);
	}

	@Test
	public void testFromConfiguration() {
		ModelObject<Map<String, Object>, List<Object>, Object> object = this.converter.fromConfiguration(new TestConfiguration());
		String written = this.writer.write(object);
		Assertions.assertEquals(this.configuration, written);
	}

	@Test
	public void testToConfiguration() {
		ModelObject<Map<String, Object>, List<Object>, Object> object = this.reader.read(this.configuration);
		TestConfiguration testConfiguration = this.converter.toConfiguration(object, TestConfiguration.class);
		Assertions.assertEquals(new TestConfiguration(), testConfiguration);
	}
}
