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
import java.util.Set;
import net.mcparkour.unifig.codec.Codec;

public interface CodecRegistryBuilder<O, A, V> extends CodecRegistryDataHolder<O, A, V> {

	default CodecRegistryBuilder<O, A, V> with(CodecRegistryDataHolder<O, A, V> registry) {
		Set<Map.Entry<Class<?>, Codec<O, A, V, ?>>> codecs = registry.getCodecs();
		return codecs(codecs);
	}

	CodecRegistryBuilder<O, A, V> codec(Codec<O, A, V, ?> codec, Class<?> type);

	default CodecRegistryBuilder<O, A, V> codec(Codec<O, A, V, ?> codec, Class<?>... types) {
		List<Class<?>> typesList = List.of(types);
		return codec(codec, typesList);
	}

	CodecRegistryBuilder<O, A, V> codec(Codec<O, A, V, ?> codec, List<Class<?>> types);

	CodecRegistryBuilder<O, A, V> codecs(Set<? extends Map.Entry<Class<?>, Codec<O, A, V, ?>>> codecs);

	CodecRegistry<O, A, V> build();
}
