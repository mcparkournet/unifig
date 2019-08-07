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

package net.mcparkour.unifig.model;

import net.mcparkour.unifig.TestConfiguration;
import net.mcparkour.unifig.model.converter.ModelConverter;
import net.mcparkour.unifig.model.section.ModelSection;
import net.mcparkour.unifig.model.section.PaperModelSection;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaperModelConverterTest {

	private ModelConverter<ConfigurationSection, Object, Object> modelConverter;
	private FileConfiguration expectedConfiguration;
	private TestConfiguration expectedTestConfiguration;

	@BeforeEach
	public void setUp() throws InvalidConfigurationException {
		PaperModelConverterFactory factory = new PaperModelConverterFactory();
		this.modelConverter = factory.createModelConverter();
		this.expectedTestConfiguration = new TestConfiguration();
		YamlConfiguration yamlConfiguration = new YamlConfiguration();
		yamlConfiguration.loadFromString(TestConfiguration.YAML);
		this.expectedConfiguration = yamlConfiguration;
	}

	@Test
	public void testFromConfiguration() {
		ModelSection<ConfigurationSection, Object, Object> section = this.modelConverter.fromConfiguration(this.expectedTestConfiguration);
		Assertions.assertEquals(this.expectedConfiguration.saveToString(), ((FileConfiguration) section.getSection()).saveToString());
	}

	@Test
	public void testToConfiguration() {
		PaperModelSection section = new PaperModelSection(this.expectedConfiguration);
		TestConfiguration testConfiguration = this.modelConverter.toConfiguration(section, TestConfiguration.class);
		Assertions.assertEquals(this.expectedTestConfiguration, testConfiguration);
	}
}
