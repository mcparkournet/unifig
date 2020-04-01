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
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.codec.registry.TypedCodecRegistryBuilder;
import net.mcparkour.unifig.condition.FieldCondition;

public class BasicOptionsBuilder implements OptionsBuilder {

	private int indentSize;
	private IndentCharacter indentCharacter;
	private LetterCase defaultKeysLetterCase;
	private Path directoryPath;
	private CodecRegistry<?, ?, ?> codecRegistry;
	private List<FieldCondition> fieldConditions;

	public BasicOptionsBuilder() {
		this(4, IndentCharacter.SPACE, LetterCase.INHERITED, Path.of(""), new TypedCodecRegistryBuilder<>().build(), new ArrayList<>(0));
	}

	public BasicOptionsBuilder(Options options) {
		this(options.getIndentSize(), options.getIndentCharacter(), options.getDefaultKeysLetterCase(), options.getDirectoryPath(), options.getCodecRegistry(), new ArrayList<>(options.getFieldConditions()));
	}

	private BasicOptionsBuilder(int indentSize, IndentCharacter indentCharacter, LetterCase defaultKeysLetterCase, Path directoryPath, CodecRegistry<?, ?, ?> codecRegistry, List<FieldCondition> fieldConditions) {
		this.indentSize = indentSize;
		this.indentCharacter = indentCharacter;
		this.defaultKeysLetterCase = defaultKeysLetterCase;
		this.directoryPath = directoryPath;
		this.codecRegistry = codecRegistry;
		this.fieldConditions = fieldConditions;
	}

	@Override
	public OptionsBuilder indentSize(int indentSize) {
		this.indentSize = indentSize;
		return this;
	}

	@Override
	public OptionsBuilder indentCharacter(IndentCharacter indentCharacter) {
		this.indentCharacter = indentCharacter;
		return this;
	}

	@Override
	public OptionsBuilder defaultKeysLetterCase(LetterCase defaultKeysLetterCase) {
		this.defaultKeysLetterCase = defaultKeysLetterCase;
		return this;
	}

	@Override
	public OptionsBuilder directoryPath(Path directoryPath) {
		this.directoryPath = directoryPath;
		return this;
	}

	@Override
	public <O, A, V> OptionsBuilder codecRegistry(CodecRegistry<O, A, V> codecRegistry) {
		this.codecRegistry = codecRegistry;
		return this;
	}

	@Override
	public OptionsBuilder fieldConditions(List<FieldCondition> fieldConditions) {
		this.fieldConditions = fieldConditions;
		return this;
	}

	@Override
	public Options build() {
		return new BasicOptions(this.indentSize, this.indentCharacter, this.defaultKeysLetterCase, this.directoryPath, this.codecRegistry, this.fieldConditions);
	}
}
