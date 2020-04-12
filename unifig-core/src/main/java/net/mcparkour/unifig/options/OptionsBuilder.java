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

package net.mcparkour.unifig.options;

import java.nio.file.Path;

public class OptionsBuilder {

	private int indentSize;
	private IndentCharacter indentCharacter;
	private Path directoryPath;

	public OptionsBuilder() {
		this.indentSize = 4;
		this.indentCharacter = IndentCharacter.SPACE;
		this.directoryPath = Path.of("");
	}

	public OptionsBuilder options(Options options) {
		return indentSize(options.getIndentSize())
			.indentCharacter(options.getIndentCharacter())
			.directoryPath(options.getDirectoryPath());
	}

	public OptionsBuilder indentSize(int indentSize) {
		this.indentSize = indentSize;
		return this;
	}

	public OptionsBuilder indentCharacter(IndentCharacter indentCharacter) {
		this.indentCharacter = indentCharacter;
		return this;
	}

	public OptionsBuilder directoryPath(Path directoryPath) {
		this.directoryPath = directoryPath;
		return this;
	}

	public Options build() {
		return new Options(this.indentSize, this.indentCharacter, this.directoryPath);
	}
}
