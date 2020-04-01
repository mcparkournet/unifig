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

package net.mcparkour.unifig.converter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.mcparkour.unifig.Configuration;
import net.mcparkour.unifig.ConfigurationFactory;
import net.mcparkour.unifig.GsonConfigurationFactory;
import net.mcparkour.unifig.TestConfiguration;
import net.mcparkour.unifig.util.Resources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GsonConverterTest {

	private Configuration<TestConfiguration> configuration;
	private String configurationString;

	@BeforeEach
	public void setUp() throws IOException {
		ConfigurationFactory configurationFactory = new GsonConfigurationFactory();
		this.configuration = configurationFactory.createConfiguration(TestConfiguration.class);
		Path configurationPath = Resources.getResourcePath("configuration.json");
		this.configurationString = Files.readString(configurationPath);
	}

	@Test
	public void testFromConfigurationObject() {
		String written = this.configuration.writeToString(new TestConfiguration());
		Assertions.assertEquals(this.configurationString, written);
	}

	@Test
	public void testToConfigurationObject() {
		TestConfiguration testConfiguration = this.configuration.readFromString(this.configurationString);
		Assertions.assertEquals(new TestConfiguration(), testConfiguration);
	}
}
