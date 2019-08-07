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
import net.mcparkour.unifig.model.section.ConfigurationModelSection;
import net.mcparkour.unifig.model.section.PaperConfigurationModelSection;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaperConfigurationModelTest {

	private ConfigurationModel<ConfigurationSection, Object, Object> configurationModel;
	private FileConfiguration expectedConfiguration;
	private TestConfiguration expectedTestConfiguration;

	@BeforeEach
	public void setUp() throws InvalidConfigurationException {
		PaperConfigurationModelFactory factory = new PaperConfigurationModelFactory();
		this.configurationModel = factory.createConfigurationModel();
		this.expectedTestConfiguration = new TestConfiguration();
		YamlConfiguration yamlConfiguration = new YamlConfiguration();
		yamlConfiguration.loadFromString(TestConfiguration.YAML);
		this.expectedConfiguration = yamlConfiguration;
	}

	@Test
	public void testFromConfiguration() {
		ConfigurationModelSection<ConfigurationSection, Object, Object> section = this.configurationModel.fromConfiguration(this.expectedTestConfiguration);
		Assertions.assertEquals(this.expectedConfiguration.saveToString(), ((FileConfiguration) section.getSection()).saveToString());
	}

	@Test
	public void testToConfiguration() {
		PaperConfigurationModelSection section = new PaperConfigurationModelSection(this.expectedConfiguration);
		TestConfiguration testConfiguration = this.configurationModel.toConfiguration(section, TestConfiguration.class);
		Assertions.assertEquals(this.expectedTestConfiguration, testConfiguration);
	}
}
