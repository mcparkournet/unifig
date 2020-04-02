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
import java.util.List;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.condition.FieldCondition;
import net.mcparkour.octenace.converter.LetterCase;

public class Options {

	private int indentSize;
	private IndentCharacter indentCharacter;
	private LetterCase defaultKeysLetterCase;
	private Path directoryPath;
	private CodecRegistry codecRegistry;
	private List<FieldCondition> fieldConditions;

	public Options(int indentSize, IndentCharacter indentCharacter, LetterCase defaultKeysLetterCase, Path directoryPath, CodecRegistry codecRegistry, List<FieldCondition> fieldConditions) {
		this.indentSize = indentSize;
		this.indentCharacter = indentCharacter;
		this.defaultKeysLetterCase = defaultKeysLetterCase;
		this.directoryPath = directoryPath;
		this.codecRegistry = codecRegistry;
		this.fieldConditions = fieldConditions;
	}

	public int getIndentSize() {
		return this.indentSize;
	}

	public IndentCharacter getIndentCharacter() {
		return this.indentCharacter;
	}

	public LetterCase getDefaultKeysLetterCase() {
		return this.defaultKeysLetterCase;
	}

	public Path getDirectoryPath() {
		return this.directoryPath;
	}

	public CodecRegistry getCodecRegistry() {
		return this.codecRegistry;
	}

	public List<FieldCondition> getFieldConditions() {
		return List.copyOf(this.fieldConditions);
	}
}
