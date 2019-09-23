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

package net.mcparkour.unifig.converter;

import net.mcparkour.unifig.codec.BooleanCodec;
import net.mcparkour.unifig.codec.ByteCodec;
import net.mcparkour.unifig.codec.CharacterCodec;
import net.mcparkour.unifig.codec.DoubleCodec;
import net.mcparkour.unifig.codec.EnumCodec;
import net.mcparkour.unifig.codec.FloatCodec;
import net.mcparkour.unifig.codec.IntegerCodec;
import net.mcparkour.unifig.codec.LongCodec;
import net.mcparkour.unifig.codec.ShortCodec;
import net.mcparkour.unifig.codec.StringCodec;
import net.mcparkour.unifig.codec.registry.BasicCodecRegistry;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.condition.IgnoredAnnotationNotPresentedFieldCondition;
import net.mcparkour.unifig.condition.NonStaticFieldCondition;
import net.mcparkour.unifig.model.array.ModelArrayFactory;
import net.mcparkour.unifig.model.object.ModelObjectFactory;
import net.mcparkour.unifig.model.value.ModelValueFactory;

public class BasicConverterFactory<O, A, V> implements ConverterFactory<O, A, V> {

	private ModelObjectFactory<O, A, V> modelObjectFactory;
	private ModelArrayFactory<O, A, V> modelArrayFactory;
	private ModelValueFactory<O, A, V> modelValueFactory;

	public BasicConverterFactory(ModelObjectFactory<O, A, V> modelObjectFactory, ModelArrayFactory<O, A, V> modelArrayFactory, ModelValueFactory<O, A, V> modelValueFactory) {
		this.modelObjectFactory = modelObjectFactory;
		this.modelArrayFactory = modelArrayFactory;
		this.modelValueFactory = modelValueFactory;
	}

	@Override
	public Converter<O, A, V> createConverter() {
		return createModelConverterBuilder()
			.build();
	}

	@Override
	public Converter<O, A, V> createConverter(ConverterBuilder<O, A, V> builder) {
		ConverterBuilder<O, A, V> converterBuilder = createModelConverterBuilder();
		CodecRegistry<O, A, V> codecRegistry = BasicCodecRegistry.<O, A, V>builder()
			.with(builder.getCodecRegistry())
			.with(converterBuilder.getCodecRegistry())
			.build();
		return converterBuilder
			.with(builder)
			.codecRegistry(codecRegistry)
			.build();
	}

	private ConverterBuilder<O, A, V> createModelConverterBuilder() {
		return BasicConverter.<O, A, V>builder()
			.modelObjectFactory(this.modelObjectFactory)
			.modelArrayFactory(this.modelArrayFactory)
			.modelValueFactory(this.modelValueFactory)
			.codecRegistry(createCodecRegistry())
			.fieldConditions(new NonStaticFieldCondition(), new IgnoredAnnotationNotPresentedFieldCondition());
	}

	private CodecRegistry<O, A, V> createCodecRegistry() {
		return BasicCodecRegistry.<O, A, V>builder()
			.codec(new BooleanCodec<>(this.modelValueFactory), boolean.class, Boolean.class)
			.codec(new CharacterCodec<>(this.modelValueFactory), char.class, Character.class)
			.codec(new ByteCodec<>(this.modelValueFactory), byte.class, Byte.class)
			.codec(new ShortCodec<>(this.modelValueFactory), short.class, Short.class)
			.codec(new IntegerCodec<>(this.modelValueFactory), int.class, Integer.class)
			.codec(new LongCodec<>(this.modelValueFactory), long.class, Long.class)
			.codec(new FloatCodec<>(this.modelValueFactory), float.class, Float.class)
			.codec(new DoubleCodec<>(this.modelValueFactory), double.class, Double.class)
			.codec(new StringCodec<>(this.modelValueFactory), String.class)
			.codec(new EnumCodec<>(this.modelValueFactory), Enum.class)
			.build();
	}

	public ModelObjectFactory<O, A, V> getModelObjectFactory() {
		return this.modelObjectFactory;
	}

	public ModelArrayFactory<O, A, V> getModelArrayFactory() {
		return this.modelArrayFactory;
	}

	public ModelValueFactory<O, A, V> getModelValueFactory() {
		return this.modelValueFactory;
	}
}
