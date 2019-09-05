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

package net.mcparkour.unifig.model.converter;

import java.util.List;
import java.util.Map;
import net.mcparkour.unifig.MapYamlConfiguration;
import net.mcparkour.unifig.TestConfiguration;
import net.mcparkour.unifig.model.object.ModelObject;
import net.mcparkour.unifig.model.object.PaperModelObject;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaperModelConverterTest {

	private static final String YAML = "primitiveBoolean: true\n" +
		"wrapperBoolean: true\n" +
		"primitiveCharacter: c\n" +
		"wrapperCharacter: c\n" +
		"primitiveByte: 1\n" +
		"wrapperByte: 1\n" +
		"primitiveShort: 1\n" +
		"wrapperShort: 1\n" +
		"primitiveInteger: 1\n" +
		"wrapperInteger: 1\n" +
		"primitiveLong: 1\n" +
		"wrapperLong: 1\n" +
		"primitiveFloat: 0.1\n" +
		"wrapperFloat: 0.1\n" +
		"primitiveDouble: 0.1\n" +
		"wrapperDouble: 0.1\n" +
		"string: string\n" +
		"nullString: null\n" +
		"bar: foobar\n" +
		"subConfiguration:\n" +
		"  primitiveBoolean: true\n" +
		"  wrapperBoolean: true\n" +
		"  primitiveCharacter: c\n" +
		"  wrapperCharacter: c\n" +
		"  primitiveByte: 1\n" +
		"  wrapperByte: 1\n" +
		"  primitiveShort: 1\n" +
		"  wrapperShort: 1\n" +
		"  primitiveInteger: 1\n" +
		"  wrapperInteger: 1\n" +
		"  primitiveLong: 1\n" +
		"  wrapperLong: 1\n" +
		"  primitiveFloat: 0.1\n" +
		"  wrapperFloat: 0.1\n" +
		"  primitiveDouble: 0.1\n" +
		"  wrapperDouble: 0.1\n" +
		"  string: string\n" +
		"  nullString: null\n" +
		"  bar: foobar\n" +
		"  testEnum: TWO\n" +
		"  testEnum2: not-four\n" +
		"  stringList:\n" +
		"  - '1'\n" +
		"  - '2'\n" +
		"  - '3'\n" +
		"testEnum: TWO\n" +
		"testEnum2: not-four\n" +
		"stringList:\n" +
		"- '1'\n" +
		"- '2'\n" +
		"- '3'\n" +
		"objectList:\n" +
		"- primitiveBoolean: true\n" +
		"  wrapperBoolean: true\n" +
		"  primitiveCharacter: c\n" +
		"  wrapperCharacter: c\n" +
		"  primitiveByte: 1\n" +
		"  wrapperByte: 1\n" +
		"  primitiveShort: 1\n" +
		"  wrapperShort: 1\n" +
		"  primitiveInteger: 1\n" +
		"  wrapperInteger: 1\n" +
		"  primitiveLong: 1\n" +
		"  wrapperLong: 1\n" +
		"  primitiveFloat: 0.1\n" +
		"  wrapperFloat: 0.1\n" +
		"  primitiveDouble: 0.1\n" +
		"  wrapperDouble: 0.1\n" +
		"  string: string\n" +
		"  nullString: null\n" +
		"  bar: foobar\n" +
		"  testEnum: TWO\n" +
		"  testEnum2: not-four\n" +
		"  stringList:\n" +
		"  - '1'\n" +
		"  - '2'\n" +
		"  - '3'\n" +
		"- primitiveBoolean: true\n" +
		"  wrapperBoolean: true\n" +
		"  primitiveCharacter: c\n" +
		"  wrapperCharacter: c\n" +
		"  primitiveByte: 1\n" +
		"  wrapperByte: 1\n" +
		"  primitiveShort: 1\n" +
		"  wrapperShort: 1\n" +
		"  primitiveInteger: 1\n" +
		"  wrapperInteger: 1\n" +
		"  primitiveLong: 1\n" +
		"  wrapperLong: 1\n" +
		"  primitiveFloat: 0.1\n" +
		"  wrapperFloat: 0.1\n" +
		"  primitiveDouble: 0.1\n" +
		"  wrapperDouble: 0.1\n" +
		"  string: string\n" +
		"  nullString: null\n" +
		"  bar: foobar\n" +
		"  testEnum: TWO\n" +
		"  testEnum2: not-four\n" +
		"  stringList:\n" +
		"  - '1'\n" +
		"  - '2'\n" +
		"  - '3'\n" +
		"- primitiveBoolean: true\n" +
		"  wrapperBoolean: true\n" +
		"  primitiveCharacter: c\n" +
		"  wrapperCharacter: c\n" +
		"  primitiveByte: 1\n" +
		"  wrapperByte: 1\n" +
		"  primitiveShort: 1\n" +
		"  wrapperShort: 1\n" +
		"  primitiveInteger: 1\n" +
		"  wrapperInteger: 1\n" +
		"  primitiveLong: 1\n" +
		"  wrapperLong: 1\n" +
		"  primitiveFloat: 0.1\n" +
		"  wrapperFloat: 0.1\n" +
		"  primitiveDouble: 0.1\n" +
		"  wrapperDouble: 0.1\n" +
		"  string: string\n" +
		"  nullString: null\n" +
		"  bar: foobar\n" +
		"  testEnum: TWO\n" +
		"  testEnum2: not-four\n" +
		"  stringList:\n" +
		"  - '1'\n" +
		"  - '2'\n" +
		"  - '3'\n";

	private ModelConverter<Map<String, Object>, List<Object>, Object> modelConverter;
	private FileConfiguration expectedConfiguration;
	private TestConfiguration expectedTestConfiguration;

	@BeforeEach
	public void setUp() throws InvalidConfigurationException {
		PaperModelConverterFactory factory = new PaperModelConverterFactory();
		this.modelConverter = factory.createModelConverter();
		this.expectedTestConfiguration = new TestConfiguration();
		YamlConfiguration configuration = new YamlConfiguration();
		configuration.loadFromString(YAML);
		this.expectedConfiguration = configuration;
	}

	@Test
	public void testFromConfiguration() {
		ModelObject<Map<String, Object>, List<Object>, Object> object = this.modelConverter.fromConfiguration(this.expectedTestConfiguration);
		Map<String, Object> rawObject = object.getObject();
		MapYamlConfiguration configuration = new MapYamlConfiguration(rawObject);
		Assertions.assertEquals(YAML, configuration.saveToString());
	}

	@Test
	public void testToConfiguration() {
		Map<String, Object> map = this.expectedConfiguration.getValues(false);
		PaperModelObject object = new PaperModelObject(map);
		TestConfiguration testConfiguration = this.modelConverter.toConfiguration(object, TestConfiguration.class);
		Assertions.assertEquals(this.expectedTestConfiguration, testConfiguration);
	}
}
