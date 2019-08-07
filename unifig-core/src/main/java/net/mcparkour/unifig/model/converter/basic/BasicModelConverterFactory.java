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

package net.mcparkour.unifig.model.converter.basic;

import net.mcparkour.unifig.codec.basic.BooleanCodec;
import net.mcparkour.unifig.codec.basic.ByteCodec;
import net.mcparkour.unifig.codec.basic.CharacterCodec;
import net.mcparkour.unifig.codec.basic.DoubleCodec;
import net.mcparkour.unifig.codec.basic.FloatCodec;
import net.mcparkour.unifig.codec.basic.IntegerCodec;
import net.mcparkour.unifig.codec.basic.LongCodec;
import net.mcparkour.unifig.codec.basic.ShortCodec;
import net.mcparkour.unifig.codec.basic.StringCodec;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.codec.registry.basic.BasicCodecRegistry;
import net.mcparkour.unifig.condition.basic.IgnoredAnnotationNotPresentedFieldCondition;
import net.mcparkour.unifig.condition.basic.NonStaticFieldCondition;
import net.mcparkour.unifig.model.converter.ModelConverter;
import net.mcparkour.unifig.model.converter.ModelConverterBuilder;
import net.mcparkour.unifig.model.converter.ModelConverterFactory;
import net.mcparkour.unifig.model.section.ModelSectionFactory;
import net.mcparkour.unifig.model.value.ModelValueFactory;

public class BasicModelConverterFactory<S, A, V> implements ModelConverterFactory<S, A, V> {

	private ModelSectionFactory<S, A, V> modelSectionFactory;
	private ModelValueFactory<S, A, V> modelValueFactory;

	public BasicModelConverterFactory(ModelSectionFactory<S, A, V> modelSectionFactory, ModelValueFactory<S, A, V> modelValueFactory) {
		this.modelSectionFactory = modelSectionFactory;
		this.modelValueFactory = modelValueFactory;
	}

	@Override
	public ModelConverter<S, A, V> createModelConverter() {
		return createModelConverterBuilder()
			.build();
	}

	@Override
	public ModelConverter<S, A, V> createModelConverter(ModelConverterBuilder<S, A, V> builder) {
		ModelConverterBuilder<S, A, V> modelConverterBuilder = createModelConverterBuilder();
		CodecRegistry<S, A, V> codecRegistry = BasicCodecRegistry.<S, A, V>builder()
			.with(builder.getCodecRegistry())
			.with(modelConverterBuilder.getCodecRegistry())
			.build();
		return modelConverterBuilder
			.with(builder)
			.codecRegistry(codecRegistry)
			.build();
	}

	private ModelConverterBuilder<S, A, V> createModelConverterBuilder() {
		return BasicModelConverter.<S, A, V>builder()
			.modelSectionFactory(this.modelSectionFactory)
			.modelValueFactory(this.modelValueFactory)
			.codecRegistry(createCodecRegistry())
			.fieldConditions(new NonStaticFieldCondition(), new IgnoredAnnotationNotPresentedFieldCondition());
	}

	private CodecRegistry<S, A, V> createCodecRegistry() {
		return BasicCodecRegistry.<S, A, V>builder()
			.codec(new BooleanCodec<>(this.modelValueFactory), boolean.class, Boolean.class)
			.codec(new CharacterCodec<>(this.modelValueFactory), char.class, Character.class)
			.codec(new ByteCodec<>(this.modelValueFactory), byte.class, Byte.class)
			.codec(new ShortCodec<>(this.modelValueFactory), short.class, Short.class)
			.codec(new IntegerCodec<>(this.modelValueFactory), int.class, Integer.class)
			.codec(new LongCodec<>(this.modelValueFactory), long.class, Long.class)
			.codec(new FloatCodec<>(this.modelValueFactory), float.class, Float.class)
			.codec(new DoubleCodec<>(this.modelValueFactory), double.class, Double.class)
			.codec(new StringCodec<>(this.modelValueFactory), String.class)
			.build();
	}

	public ModelSectionFactory<S, A, V> getModelSectionFactory() {
		return this.modelSectionFactory;
	}

	public ModelValueFactory<S, A, V> getModelValueFactory() {
		return this.modelValueFactory;
	}
}
