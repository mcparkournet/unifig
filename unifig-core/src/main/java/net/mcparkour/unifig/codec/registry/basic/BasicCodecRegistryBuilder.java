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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.mcparkour.unifig.codec.Codec;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.codec.registry.CodecRegistryBuilder;

public class BasicCodecRegistryBuilder<S, A, V> implements CodecRegistryBuilder<S, A, V> {

	private Map<Class<?>, Codec<S, A, V, ?>> codecs = new HashMap<>();

	@Override
	public CodecRegistryBuilder<S, A, V> codec(Codec<S, A, V, ?> codec, Class<?> type) {
		this.codecs.put(type, codec);
		return this;
	}

	@Override
	public CodecRegistryBuilder<S, A, V> codec(Codec<S, A, V, ?> codec, List<Class<?>> types) {
		for (Class<?> type : types) {
			codec(codec, type);
		}
		return this;
	}

	@Override
	public CodecRegistryBuilder<S, A, V> codecs(Map<Class<?>, ? extends Codec<S, A, V, ?>> codecs) {
		this.codecs.putAll(codecs);
		return this;
	}

	@Override
	public CodecRegistry<S, A, V> build() {
		return new BasicCodecRegistry<>(this.codecs);
	}

	@Override
	public Map<Class<?>, Codec<S, A, V, ?>> getCodecs() {
		return Map.copyOf(this.codecs);
	}
}
