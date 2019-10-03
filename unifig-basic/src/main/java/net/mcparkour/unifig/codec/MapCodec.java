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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.mcparkour.common.reflection.Reflections;
import net.mcparkour.unifig.converter.Converter;
import net.mcparkour.unifig.model.object.ModelObject;
import net.mcparkour.unifig.model.object.ModelObjectFactory;
import net.mcparkour.unifig.model.value.ModelValue;
import net.mcparkour.unifig.model.value.ModelValueFactory;
import org.jetbrains.annotations.Nullable;

public class MapCodec<O, A, V> implements Codec<O, A, V, Map<?, ?>> {

	@Override
	public ModelValue<O, A, V> encode(Map<?, ?> object, Field field, Converter<O, A, V> converter) {
		ModelObjectFactory<O, A, V> objectFactory = converter.getModelObjectFactory();
		ModelObject<O, A, V> modelObject = objectFactory.createEmptyModelObject();
		for (Map.Entry<?, ?> entry : object.entrySet()) {
			Object key = entry.getKey();
			String keyString = key.toString();
			Object value = entry.getValue();
			Class<?> valueType = value.getClass();
			ModelValue<O, A, V> valueValue = converter.toModelValue(value, valueType, field);
			modelObject.setValue(keyString, valueValue);
		}
		ModelValueFactory<O, A, V> valueFactory = converter.getModelValueFactory();
		return valueFactory.createObjectModelValue(modelObject);
	}

	@Nullable
	@Override
	public Map<?, ?> decode(ModelValue<O, A, V> value, Field field, Converter<O, A, V> converter) {
		ModelObjectFactory<O, A, V> objectFactory = converter.getModelObjectFactory();
		O rawObject = value.asObject();
		ModelObject<O, A, V> object = objectFactory.createModelObject(rawObject);
		int size = object.getSize();
		Map<Object, Object> map = new LinkedHashMap<>(size);
		List<Class<?>> genericTypes = Reflections.getGenericTypes(field);
		Class<?> genericType = genericTypes.get(1);
		for (Map.Entry<String, ModelValue<O, A, V>> entry : object.getEntries()) {
			String key = entry.getKey();
			ModelValue<O, A, V> entryValue = entry.getValue();
			Object mapValue = converter.toObject(entryValue, genericType, field);
			map.put(key, mapValue);
		}
		return map;
	}
}
