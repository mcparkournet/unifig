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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.mcparkour.common.reflection.Reflections;
import net.mcparkour.unifig.converter.Converter;
import net.mcparkour.unifig.model.array.ModelArray;
import net.mcparkour.unifig.model.array.ModelArrayFactory;
import net.mcparkour.unifig.model.value.ModelValue;
import net.mcparkour.unifig.model.value.ModelValueFactory;
import org.jetbrains.annotations.Nullable;

public class SetCodec<O, A, V> implements Codec<O, A, V, Set<?>> {

	@Override
	public ModelValue<O, A, V> encode(Set<?> object, Field field, Converter<O, A, V> converter) {
		ModelArrayFactory<O, A, V> arrayFactory = converter.getModelArrayFactory();
		ModelArray<O, A, V> array = arrayFactory.createEmptyModelArray();
		for (Object element : object) {
			Class<?> elementType = element.getClass();
			ModelValue<O, A, V> elementValue = converter.toModelValue(element, elementType, field);
			array.addValue(elementValue);
		}
		ModelValueFactory<O, A, V> valueFactory = converter.getModelValueFactory();
		return valueFactory.createArrayModelValue(array);
	}

	@Nullable
	@Override
	public Set<?> decode(ModelValue<O, A, V> value, Field field, Converter<O, A, V> converter) {
		ModelArrayFactory<O, A, V> arrayFactory = converter.getModelArrayFactory();
		A rawArray = value.asArray();
		ModelArray<O, A, V> array = arrayFactory.createModelArray(rawArray);
		int size = array.getSize();
		Set<Object> set = new LinkedHashSet<>(size);
		List<Class<?>> genericTypes = Reflections.getGenericTypes(field);
		Class<?> genericType = genericTypes.get(0);
		for (ModelValue<O, A, V> elementValue : array) {
			Object object = converter.toObject(elementValue, genericType, field);
			set.add(object);
		}
		return set;
	}
}
