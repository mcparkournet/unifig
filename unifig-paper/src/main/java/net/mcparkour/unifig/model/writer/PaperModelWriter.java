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

package net.mcparkour.unifig.model.writer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.mcparkour.common.reflection.Reflections;
import net.mcparkour.unifig.model.object.ModelObject;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

public class PaperModelWriter implements ModelWriter<Map<String, Object>, List<Object>, Object> {

	private static final Field MAP_FIELD = Reflections.getField(MemorySection.class, "map");

	@Override
	public String write(ModelObject<Map<String, Object>, List<Object>, Object> object) {
		Map<String, Object> rawObject = object.getObject();
		YamlConfiguration configuration = new YamlConfiguration();
		Map<String, Object> configurationMap = getMap(configuration);
		configurationMap.putAll(rawObject);
		return configuration.saveToString();
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getMap(YamlConfiguration configuration) {
		Object value = Reflections.getFieldValue(MAP_FIELD, configuration);
		Objects.requireNonNull(value);
		return (Map<String, Object>) value;
	}
}
