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

package net.mcparkour.unifig;

import com.moandjiezana.toml.Toml;
import net.mcparkour.unifig.converter.Toml4jModelConverterFactory;
import net.mcparkour.unifig.model.reader.Toml4jModelReader;
import net.mcparkour.unifig.model.writer.Toml4jModelWriter;

public class Toml4jConfiguration<T> extends BasicConfiguration<Toml, Object, Object, T> {

	public Toml4jConfiguration(Class<T> configurationType) {
		super(configurationType, "toml", new Toml4jModelReader(), new Toml4jModelWriter(), new Toml4jModelConverterFactory());
	}
}
