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

import java.util.List;
import java.util.Map;
import net.mcparkour.octenace.mapper.DocumentMapper;
import net.mcparkour.unifig.document.reader.PaperReader;
import net.mcparkour.unifig.document.writer.PaperWriter;
import net.mcparkour.unifig.options.Options;

public class PaperConfiguration<T> extends CommonConfiguration<Map<String, Object>, List<Object>, Object, T> {

	private static final PaperModel PAPER_MODEL = new PaperModel();
	private static final PaperReader PAPER_READER = new PaperReader();

	public PaperConfiguration(Class<T> configurationType, T defaultConfiguration, Options options, DocumentMapper<Map<String, Object>, List<Object>, Object, T> mapper) {
		super(PAPER_MODEL, PAPER_READER, new PaperWriter(options), configurationType, defaultConfiguration, options, mapper);
	}
}
