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

package net.mcparkour.unifig.codec.registry;

import java.util.List;
import java.util.Map;
import net.mcparkour.unifig.codec.Codec;

public interface CodecRegistryBuilder<S, A, V> extends CodecRegistryDataHolder<S, A, V> {

	default CodecRegistryBuilder<S, A, V> with(CodecRegistryDataHolder<S, A, V> registry) {
		Map<Class<?>, Codec<S, A, V, ?>> codecs = registry.getCodecs();
		return codecs(codecs);
	}

	CodecRegistryBuilder<S, A, V> codec(Codec<S, A, V, ?> codec, Class<?> type);

	default CodecRegistryBuilder<S, A, V> codec(Codec<S, A, V, ?> codec, Class<?>... types) {
		List<Class<?>> typesList = List.of(types);
		return codec(codec, typesList);
	}

	CodecRegistryBuilder<S, A, V> codec(Codec<S, A, V, ?> codec, List<Class<?>> types);

	CodecRegistryBuilder<S, A, V> codecs(Map<Class<?>, ? extends Codec<S, A, V, ?>> codecs);

	CodecRegistry<S, A, V> build();
}
