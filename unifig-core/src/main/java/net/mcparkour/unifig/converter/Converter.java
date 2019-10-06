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

package net.mcparkour.unifig.converter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import net.mcparkour.unifig.model.array.ModelArrayFactory;
import net.mcparkour.unifig.model.object.ModelObject;
import net.mcparkour.unifig.model.object.ModelObjectFactory;
import net.mcparkour.unifig.model.value.ModelValue;
import net.mcparkour.unifig.model.value.ModelValueFactory;
import net.mcparkour.unifig.options.Options;
import org.jetbrains.annotations.Nullable;

public interface Converter<O, A, V> {

	ModelObject<O, A, V> fromConfiguration(Object configuration);

	ModelValue<O, A, V> toModelValue(@Nullable Object object, Type type);

	<T> T toConfiguration(ModelObject<O, A, V> object, Class<T> configurationType);

	Object toObject(ModelValue<O, A, V> value, Type type);

	boolean isFieldValid(Field field);

	String getFieldName(Field field);

	ModelObjectFactory<O, A, V> getModelObjectFactory();

	ModelArrayFactory<O, A, V> getModelArrayFactory();

	ModelValueFactory<O, A, V> getModelValueFactory();

	Options getOptions();
}
