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

package net.mcparkour.unifig.model.object;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.mcparkour.unifig.model.value.ModelValue;
import net.mcparkour.unifig.model.value.SnakeyamlModelValue;

public class SnakeyamlModelObject implements ModelObject<Map<String, Object>, Collection<Object>, Object> {

	private Map<String, Object> object;

	public SnakeyamlModelObject(Map<String, Object> object) {
		this.object = object;
	}

	@Override
	public ModelValue<Map<String, Object>, Collection<Object>, Object> getValue(String key) {
		Object value = this.object.get(key);
		return new SnakeyamlModelValue(value);
	}

	@Override
	public void setValue(String key, ModelValue<Map<String, Object>, Collection<Object>, Object> value) {
		Object rawValue = value.getValue();
		this.object.put(key, rawValue);
	}

	@Override
	public Set<Map.Entry<String, ModelValue<Map<String, Object>, Collection<Object>, Object>>> getEntries() {
		return this.object.entrySet()
			.stream()
			.<Map.Entry<String, ModelValue<Map<String, Object>, Collection<Object>, Object>>>map(entry -> {
				String key = entry.getKey();
				Object value = entry.getValue();
				SnakeyamlModelValue modelValue = new SnakeyamlModelValue(value);
				return Map.entry(key, modelValue);
			})
			.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	@Override
	public int getSize() {
		return this.object.size();
	}

	@Override
	public Map<String, Object> getObject() {
		return this.object;
	}
}
