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
import net.mcparkour.octenace.codec.registry.CodecRegistry;
import net.mcparkour.octenace.mapper.DocumentMapper;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidator;
import net.mcparkour.octenace.mapper.property.invalidator.PropertyInvalidators;
import net.mcparkour.octenace.mapper.property.name.NameConverter;
import net.mcparkour.octenace.mapper.property.name.NameConverters;
import net.mcparkour.unifig.document.array.PaperArrayFactory;
import net.mcparkour.unifig.document.object.PaperObjectFactory;
import net.mcparkour.unifig.document.value.PaperValueFactory;
import net.mcparkour.unifig.options.Options;
import net.mcparkour.unifig.options.OptionsBuilder;

public class PaperConfigurationFactory extends AbstractConfigurationFactory<Map<String, Object>, List<Object>, Object> {

	private static final PaperObjectFactory PAPER_OBJECT_FACTORY = new PaperObjectFactory();
	private static final PaperArrayFactory PAPER_ARRAY_FACTORY = new PaperArrayFactory();
	private static final PaperValueFactory PAPER_VALUE_FACTORY = new PaperValueFactory();
	private static final Options PAPER_DEFAULT_OPTIONS = new OptionsBuilder()
		.indentSize(2)
		.build();

	public PaperConfigurationFactory() {
		this(NameConverters.KEBAB_CASE_NAME_CONVERTER, PropertyInvalidators.COMMON_PROPERTY_INVALIDATORS, createDefaultCodecRegistry());
	}

	public PaperConfigurationFactory(NameConverter nameConverter, List<PropertyInvalidator> propertyInvalidators, CodecRegistry<Map<String, Object>, List<Object>, Object> codecRegistry) {
		super(PAPER_OBJECT_FACTORY, PAPER_ARRAY_FACTORY, PAPER_VALUE_FACTORY, PAPER_DEFAULT_OPTIONS, nameConverter, propertyInvalidators, codecRegistry);
	}

	@Override
	protected <T> Configuration<T> createConfiguration(Class<T> configurationType, T defaultConfiguration, Options options, DocumentMapper<Map<String, Object>, List<Object>, Object, T> mapper) {
		return new PaperConfiguration<>(configurationType, defaultConfiguration, options, mapper);
	}
}
