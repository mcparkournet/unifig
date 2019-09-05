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

package net.mcparkour.unifig.codec.basic;

import java.lang.reflect.Field;
import java.util.Arrays;
import net.mcparkour.unifig.annotation.Property;
import net.mcparkour.unifig.codec.Codec;
import net.mcparkour.unifig.model.value.ModelValue;
import net.mcparkour.unifig.model.value.ModelValueFactory;
import net.mcparkour.unifig.util.reflection.Reflections;
import org.jetbrains.annotations.Nullable;

public class EnumCodec<O, A, V> implements Codec<O, A, V, Enum<?>> {

	private ModelValueFactory<O, A, V> modelValueFactory;

	public EnumCodec(ModelValueFactory<O, A, V> modelValueFactory) {
		this.modelValueFactory = modelValueFactory;
	}

	@Override
	public ModelValue<O, A, V> encode(Enum<?> object) {
		Class<? extends Enum<?>> type = object.getDeclaringClass();
		String name = getEnumName(object, type);
		return this.modelValueFactory.createStringModelValue(name);
	}

	@Override
	@Nullable
	public Enum<?> decode(ModelValue<O, A, V> value, Class<? extends Enum<?>> type) {
		Enum<?>[] enumConstants = type.getEnumConstants();
		String valueString = value.asString();
		return Arrays.stream(enumConstants)
			.filter(enumConstant -> valueString.equals(getEnumName(enumConstant, type)))
			.findFirst()
			.orElse(null);
	}

	private String getEnumName(Enum<?> object, Class<? extends Enum<?>> type) {
		String name = object.name();
		Field field = Reflections.getField(type, name);
		Property property = field.getAnnotation(Property.class);
		if (property != null) {
			return property.value();
		}
		return name;
	}
}
