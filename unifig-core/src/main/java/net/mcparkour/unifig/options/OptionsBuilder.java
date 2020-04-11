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
import net.mcparkour.octenace.codec.common.Codecs;
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidator;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidators;
import net.mcparkour.octenace.mapper.property.name.NameConverter;

public class OptionsBuilder {

	private int indentSize;
	private IndentCharacter indentCharacter;
	private Path directoryPath;
	private NameConverter nameConverter;
	private List<PropertyInvalidator> propertyInvalidators;
	private CodecRegistry codecRegistry;

	public OptionsBuilder() {
		this.indentSize = 4;
		this.indentCharacter = IndentCharacter.SPACE;
		this.directoryPath = Path.of("");
		this.nameConverter = NameConverter.identity();
		this.propertyInvalidators = PropertyInvalidators.COMMON_PROPERTY_INVALIDATORS;
		this.codecRegistry = Codecs.COMMON_CODEC_REGISTRY;
	}

	public OptionsBuilder options(Options options) {
		return indentSize(options.getIndentSize())
			.indentCharacter(options.getIndentCharacter())
			.directoryPath(options.getDirectoryPath())
			.nameConverter(options.getNameConverter())
			.propertyInvalidators(options.getPropertyInvalidators())
			.codecRegistry(options.getCodecRegistry());
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

	public OptionsBuilder nameConverter(NameConverter nameConverter) {
		this.nameConverter = nameConverter;
		return this;
	}

	public OptionsBuilder propertyInvalidators(List<PropertyInvalidator> propertyInvalidators) {
		this.propertyInvalidators = propertyInvalidators;
		return this;
	}

	public OptionsBuilder codecRegistry(CodecRegistry codecRegistry) {
		this.codecRegistry = codecRegistry;
		return this;
	}

	public Options build() {
		return new Options(this.indentSize, this.indentCharacter, this.directoryPath, this.nameConverter, this.propertyInvalidators, this.codecRegistry);
	}
}
