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

import java.util.Collection;
import java.util.Map;
import net.mcparkour.unifig.model.object.ModelObject;
import net.mcparkour.unifig.options.Options;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class SnakeyamlModelWriter implements ModelWriter<Map<String, Object>, Collection<Object>, Object> {

	private Yaml yaml;

	public SnakeyamlModelWriter(Options options) {
		DumperOptions dumperOptions = createDumperOptions(options);
		this.yaml = new Yaml(dumperOptions);
	}

	private DumperOptions createDumperOptions(Options options) {
		DumperOptions dumperOptions = new DumperOptions();
		int indentSize = options.getIndentSize();
		dumperOptions.setIndent(indentSize);
		dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		return dumperOptions;
	}

	@Override
	public String write(ModelObject<Map<String, Object>, Collection<Object>, Object> object) {
		Map<String, Object> rawObject = object.getObject();
		return this.yaml.dump(rawObject);
	}
}
