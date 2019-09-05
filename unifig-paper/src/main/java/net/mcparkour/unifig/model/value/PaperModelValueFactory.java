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

package net.mcparkour.unifig.model.value;

import java.util.List;
import org.bukkit.configuration.ConfigurationSection;

public class PaperModelValueFactory implements ModelValueFactory<ConfigurationSection, List<Object>, Object> {

	@Override
	public ModelValue<ConfigurationSection, List<Object>, Object> createNullModelValue() {
		return new PaperModelValue(null);
	}

	@Override
	public ModelValue<ConfigurationSection, List<Object>, Object> createBooleanModelValue(boolean value) {
		return createModelValue(value);
	}

	@Override
	public ModelValue<ConfigurationSection, List<Object>, Object> createCharacterModelValue(char value) {
		return createModelValue(value);
	}

	@Override
	public ModelValue<ConfigurationSection, List<Object>, Object> createNumberModelValue(Number value) {
		return createModelValue(value);
	}

	@Override
	public ModelValue<ConfigurationSection, List<Object>, Object> createStringModelValue(String value) {
		return createModelValue(value);
	}

	@Override
	public ModelValue<ConfigurationSection, List<Object>, Object> createSectionModelValue(ConfigurationSection section) {
		return createModelValue(section);
	}

	@Override
	public ModelValue<ConfigurationSection, List<Object>, Object> createArrayModelValue(List<Object> array) {
		return createModelValue(array);
	}

	@Override
	public ModelValue<ConfigurationSection, List<Object>, Object> createModelValue(Object value) {
		return new PaperModelValue(value);
	}
}
