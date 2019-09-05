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

package net.mcparkour.unifig.codec.registry.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.mcparkour.unifig.codec.Codec;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.codec.registry.CodecRegistryBuilder;

public class BasicCodecRegistryBuilder<O, A, V> implements CodecRegistryBuilder<O, A, V> {

	private List<TypedCodec<O, A, V>> codecs = new ArrayList<>();

	@Override
	public CodecRegistryBuilder<O, A, V> codec(Codec<O, A, V, ?> codec, Class<?> type) {
		TypedCodec<O, A, V> typedCodec = new TypedCodec<>(type, codec);
		this.codecs.add(typedCodec);
		return this;
	}

	@Override
	public CodecRegistryBuilder<O, A, V> codec(Codec<O, A, V, ?> codec, List<Class<?>> types) {
		types.forEach(type -> codec(codec, type));
		return this;
	}

	@Override
	public CodecRegistryBuilder<O, A, V> codecs(Set<? extends Map.Entry<Class<?>, Codec<O, A, V, ?>>> codecs) {
		codecs.stream()
			.map(TypedCodec::new)
			.forEach(this.codecs::add);
		return this;
	}

	@Override
	public CodecRegistry<O, A, V> build() {
		return new BasicCodecRegistry<>(this.codecs);
	}

	@Override
	public Set<Map.Entry<Class<?>, Codec<O, A, V, ?>>> getCodecs() {
		return this.codecs.stream()
			.map(TypedCodec::toEntry)
			.collect(Collectors.toUnmodifiableSet());
	}
}
