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

import net.mcparkour.unifig.codec.Codec;
import net.mcparkour.unifig.codec.CodecDecodeException;
import net.mcparkour.unifig.model.value.ModelValue;
import net.mcparkour.unifig.model.value.ModelValueFactory;
import org.jetbrains.annotations.Nullable;

public class StringCodec<O, A, V> implements Codec<O, A, V, String> {

	private ModelValueFactory<O, A, V> modelValueFactory;

	public StringCodec(ModelValueFactory<O, A, V> modelValueFactory) {
		this.modelValueFactory = modelValueFactory;
	}

	@Override
	public ModelValue<O, A, V> encode(String object) {
		return this.modelValueFactory.createStringModelValue(object);
	}

	@Nullable
	@Override
	public String decode(ModelValue<O, A, V> value, Class<? extends String> type) {
		if (!value.isString()) {
			throw new CodecDecodeException("value is not a String");
		}
		return value.asString();
	}
}