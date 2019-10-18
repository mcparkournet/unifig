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

package net.mcparkour.unifig.codec;

import java.util.List;
import java.util.Map;
import java.util.Set;
import net.mcparkour.unifig.codec.primitive.BooleanCodec;
import net.mcparkour.unifig.codec.primitive.number.ByteCodec;
import net.mcparkour.unifig.codec.primitive.CharacterCodec;
import net.mcparkour.unifig.codec.primitive.number.DoubleCodec;
import net.mcparkour.unifig.codec.primitive.number.FloatCodec;
import net.mcparkour.unifig.codec.primitive.number.IntegerCodec;
import net.mcparkour.unifig.codec.primitive.number.LongCodec;
import net.mcparkour.unifig.codec.primitive.number.ShortCodec;
import net.mcparkour.unifig.codec.registry.CodecRegistryBuilder;
import net.mcparkour.unifig.codec.registry.TypedCodecRegistryBuilder;

public final class Codecs {

	private Codecs() {
		throw new UnsupportedOperationException("Cannot create an instance of this class");
	}

	public static <O, A, V> CodecRegistryBuilder<O, A, V> createBasicCodecRegistryBuilder() {
		return new TypedCodecRegistryBuilder<O, A, V>()
			.codec(new BooleanCodec<>(), boolean.class, Boolean.class)
			.codec(new CharacterCodec<>(), char.class, Character.class)
			.codec(new ByteCodec<>(), byte.class, Byte.class)
			.codec(new ShortCodec<>(), short.class, Short.class)
			.codec(new IntegerCodec<>(), int.class, Integer.class)
			.codec(new LongCodec<>(), long.class, Long.class)
			.codec(new FloatCodec<>(), float.class, Float.class)
			.codec(new DoubleCodec<>(), double.class, Double.class)
			.codec(new StringCodec<>(), String.class)
			.codec(new EnumCodec<>(), Enum.class)
			.codec(new SetCodec<>(), Set.class)
			.codec(new ListCodec<>(), List.class)
			.codec(new MapCodec<>(), Map.class);
	}
}
