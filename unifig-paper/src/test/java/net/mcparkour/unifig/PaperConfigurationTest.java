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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaperConfigurationTest {

	private Configuration<TestConfiguration> configuration;
	private String configurationString;
	private Configuration<TestPaperConfiguration> paperConfiguration;
	private String paperConfigurationString;

	@BeforeEach
	public void setUp() throws IOException {
		ConfigurationFactory configurationFactory = new PaperConfigurationFactory();
		this.configuration = configurationFactory.createConfiguration(TestConfiguration.class);
		Path configurationPath = Resources.getResourcePath("configuration.yml");
		this.configurationString = Files.readString(configurationPath);
		this.paperConfiguration = configurationFactory.createConfiguration(TestPaperConfiguration.class);
		Path paperConfigurationPath = Resources.getResourcePath("paper-configuration.yml");
		this.paperConfigurationString = Files.readString(paperConfigurationPath);
	}

	@Test
	public void testFromConfiguration() {
		String written = this.configuration.writeToString(new TestConfiguration());
		Assertions.assertEquals(this.configurationString, written);
	}

	@Test
	public void testToConfiguration() {
		TestConfiguration testConfiguration = this.configuration.readFromString(this.configurationString);
		Assertions.assertEquals(new TestConfiguration(), testConfiguration);
	}

	@Test
	public void testFromPaperConfiguration() {
		String written = this.paperConfiguration.writeToString(new TestPaperConfiguration());
		Assertions.assertEquals(this.paperConfigurationString, written);
	}

	@Test
	public void testToPaperConfiguration() {
		TestPaperConfiguration testConfiguration = this.paperConfiguration.readFromString(this.paperConfigurationString);
		Assertions.assertEquals(new TestPaperConfiguration(), testConfiguration);
	}
}
