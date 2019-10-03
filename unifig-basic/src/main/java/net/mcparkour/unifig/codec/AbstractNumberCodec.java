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

import java.lang.reflect.Field;
import net.mcparkour.unifig.converter.Converter;
import net.mcparkour.unifig.model.value.ModelValue;
import net.mcparkour.unifig.model.value.ModelValueFactory;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractNumberCodec<O, A, V, T extends Number> implements Codec<O, A, V, T> {

	@Override
	public ModelValue<O, A, V> encode(T object, Field field, Converter<O, A, V> converter) {
		ModelValueFactory<O, A, V> valueFactory = converter.getModelValueFactory();
		return valueFactory.createNumberModelValue(object);
	}

	@Nullable
	@Override
	public T decode(ModelValue<O, A, V> value, Field field, Converter<O, A, V> converter) {
		if (!value.isNumber()) {
			throw new CodecDecodeException("value is not a number");
		}
		Number number = value.asNumber();
		return decode(number);
	}

	public abstract T decode(Number number);
}
