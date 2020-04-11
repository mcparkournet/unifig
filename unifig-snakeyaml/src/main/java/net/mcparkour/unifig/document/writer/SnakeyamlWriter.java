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

package net.mcparkour.unifig.document.writer;

import java.util.List;
import java.util.Map;
import net.mcparkour.octenace.document.object.DocumentObject;
import net.mcparkour.unifig.options.Options;
import org.snakeyaml.engine.v2.api.Dump;
import org.snakeyaml.engine.v2.api.DumpSettings;
import org.snakeyaml.engine.v2.common.FlowStyle;

public class SnakeyamlWriter implements DocumentWriter<Map<String, Object>, List<Object>, Object> {

	private Dump dump;

	public SnakeyamlWriter(Options options) {
		DumpSettings settings = createDumpSettings(options);
		this.dump = new Dump(settings);
	}

	private DumpSettings createDumpSettings(Options options) {
		int indentSize = options.getIndentSize();
		return DumpSettings.builder()
			.setDefaultFlowStyle(FlowStyle.BLOCK)
			.setIndent(indentSize)
			.build();
	}

	@Override
	public String write(DocumentObject<Map<String, Object>, List<Object>, Object> document) {
		Map<String, Object> rawObject = document.getObject();
		return this.dump.dumpToString(rawObject);
	}
}
