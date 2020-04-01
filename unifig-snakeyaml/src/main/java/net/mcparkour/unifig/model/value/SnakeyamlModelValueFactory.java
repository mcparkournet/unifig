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

package net.mcparkour.unifig.model.value;

import java.util.Collection;
import java.util.Map;

public class SnakeyamlModelValueFactory implements ModelValueFactory<Map<String, Object>, Collection<Object>, Object> {

	@Override
	public ModelValue<Map<String, Object>, Collection<Object>, Object> createNullModelValue() {
		return new SnakeyamlModelValue(null);
	}

	@Override
	public ModelValue<Map<String, Object>, Collection<Object>, Object> createBooleanModelValue(boolean value) {
		return createModelValue(value);
	}

	@Override
	public ModelValue<Map<String, Object>, Collection<Object>, Object> createNumberModelValue(Number value) {
		return createModelValue(value);
	}

	@Override
	public ModelValue<Map<String, Object>, Collection<Object>, Object> createStringModelValue(String value) {
		return createModelValue(value);
	}

	@Override
	public ModelValue<Map<String, Object>, Collection<Object>, Object> createObjectModelValue(Map<String, Object> object) {
		return createModelValue(object);
	}

	@Override
	public ModelValue<Map<String, Object>, Collection<Object>, Object> createArrayModelValue(Collection<Object> array) {
		return createModelValue(array);
	}

	@Override
	public ModelValue<Map<String, Object>, Collection<Object>, Object> createModelValue(Object value) {
		return new SnakeyamlModelValue(value);
	}
}
