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
import java.util.ArrayList;
import java.util.List;
import net.mcparkour.octenace.codec.basic.Codecs;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.condition.FieldCondition;
import net.mcparkour.octenace.condition.FieldConditions;
import net.mcparkour.octenace.converter.LetterCase;
import net.mcparkour.unifig.condition.IgnoredAnnotationNotPresentedFieldCondition;

public class OptionsBuilder {

	private int indentSize;
	private IndentCharacter indentCharacter;
	private LetterCase defaultKeysLetterCase;
	private Path directoryPath;
	private CodecRegistry codecRegistry;
	private List<FieldCondition> fieldConditions;

	public OptionsBuilder() {
		this.indentSize = 4;
		this.indentCharacter = IndentCharacter.SPACE;
		this.defaultKeysLetterCase = LetterCase.INHERITED;
		this.directoryPath = Path.of("");
		this.codecRegistry = Codecs.BASIC_CODEC_REGISTRY;
		this.fieldConditions = createDefaultFieldConditions();
	}

	private static List<FieldCondition> createDefaultFieldConditions() {
		List<FieldCondition> list = new ArrayList<>(3);
		list.addAll(FieldConditions.BASIC_FIELD_CONDITIONS);
		list.add(new IgnoredAnnotationNotPresentedFieldCondition());
		return list;
	}

	public OptionsBuilder options(Options options) {
		return indentSize(options.getIndentSize())
			.indentCharacter(options.getIndentCharacter())
			.defaultKeysLetterCase(options.getDefaultKeysLetterCase())
			.directoryPath(options.getDirectoryPath())
			.codecRegistry(options.getCodecRegistry())
			.fieldConditions(options.getFieldConditions());
	}

	public OptionsBuilder indentSize(int indentSize) {
		this.indentSize = indentSize;
		return this;
	}

	public OptionsBuilder indentCharacter(IndentCharacter indentCharacter) {
		this.indentCharacter = indentCharacter;
		return this;
	}

	public OptionsBuilder defaultKeysLetterCase(LetterCase defaultKeysLetterCase) {
		this.defaultKeysLetterCase = defaultKeysLetterCase;
		return this;
	}

	public OptionsBuilder directoryPath(Path directoryPath) {
		this.directoryPath = directoryPath;
		return this;
	}

	public OptionsBuilder codecRegistry(CodecRegistry codecRegistry) {
		this.codecRegistry = codecRegistry;
		return this;
	}

	public OptionsBuilder fieldConditions(List<FieldCondition> fieldConditions) {
		this.fieldConditions = fieldConditions;
		return this;
	}

	public Options build() {
		return new Options(this.indentSize, this.indentCharacter, this.defaultKeysLetterCase, this.directoryPath, this.codecRegistry, this.fieldConditions);
	}
}
