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

package net.mcparkour.unifig.model.section;

import java.util.List;
import net.mcparkour.unifig.model.value.ModelValue;
import net.mcparkour.unifig.model.value.PaperModelValue;
import org.bukkit.configuration.ConfigurationSection;

public class PaperModelSection implements ModelSection<ConfigurationSection, List<Object>, Object> {

	private ConfigurationSection section;

	public PaperModelSection(ConfigurationSection section) {
		this.section = section;
	}

	@Override
	public ModelValue<ConfigurationSection, List<Object>, Object> getValue(String key) {
		Object value = this.section.get(key);
		return new PaperModelValue(value);
	}

	@Override
	public void setValue(String key, ModelValue<ConfigurationSection, List<Object>, Object> value) {
		Object rawValue = value.getValue();
		this.section.set(key, rawValue);
	}

	@Override
	public ConfigurationSection getSection() {
		return this.section;
	}
}
