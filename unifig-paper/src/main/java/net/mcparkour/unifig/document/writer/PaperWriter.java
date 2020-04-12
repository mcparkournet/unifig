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
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;

public class PaperWriter implements DocumentWriter<Map<String, Object>, List<Object>, Object> {

	private Yaml yaml;

	public PaperWriter(Options options) {
		DumperOptions dumperOptions = createDumperOptions(options);
		this.yaml = new Yaml(dumperOptions);
	}

	private DumperOptions createDumperOptions(Options options) {
		int indentSize = options.getIndentSize();
		DumperOptions dumperOptions = new DumperOptions();
		dumperOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
		dumperOptions.setIndent(indentSize);
		return dumperOptions;
	}

	@Override
	public String write(DocumentObject<Map<String, Object>, List<Object>, Object> document) {
		Map<String, Object> rawObject = document.getObject();
		return this.yaml.dump(rawObject);
	}
}
