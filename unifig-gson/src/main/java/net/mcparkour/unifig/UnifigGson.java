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

package net.mcparkour.unifig;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import net.mcparkour.unifig.annotation.Ignored;
import net.mcparkour.unifig.annotation.Property;
import net.mcparkour.unifig.codec.BooleanCodec;
import net.mcparkour.unifig.codec.ByteCodec;
import net.mcparkour.unifig.codec.CharacterCodec;
import net.mcparkour.unifig.codec.Codec;
import net.mcparkour.unifig.codec.CodecNotFoundException;
import net.mcparkour.unifig.codec.DoubleCodec;
import net.mcparkour.unifig.codec.FloatCodec;
import net.mcparkour.unifig.codec.IntegerCodec;
import net.mcparkour.unifig.codec.LongCodec;
import net.mcparkour.unifig.codec.ShortCodec;
import net.mcparkour.unifig.codec.StringCodec;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.util.reflection.Reflections;
import org.jetbrains.annotations.Nullable;

public class UnifigGson {

	private static final CodecRegistry<JsonElement> CODEC_REGISTRY = CodecRegistry.<JsonElement>builder()
		.register(new BooleanCodec(), boolean.class, Boolean.class)
		.register(new CharacterCodec(), char.class, Character.class)
		.register(new ByteCodec(), byte.class, Byte.class)
		.register(new ShortCodec(), short.class, Short.class)
		.register(new IntegerCodec(), int.class, Integer.class)
		.register(new LongCodec(), long.class, Long.class)
		.register(new FloatCodec(), float.class, Float.class)
		.register(new DoubleCodec(), double.class, Double.class)
		.register(new StringCodec(), String.class)
		.build();

	public JsonObject fromConfigurationObject(Object object) {
		JsonObject jsonObject = new JsonObject();
		Class<?> objectClass = object.getClass();
		Field[] fields = objectClass.getDeclaredFields();
		for (Field field : fields) {
			if (!Reflections.isStatic(field) && !field.isAnnotationPresent(Ignored.class)) {
				field.trySetAccessible();
				String fieldName = getFieldName(field);
				Object fieldValue = Reflections.getFieldValue(field, object);
				Class<?> fieldType = field.getType();
				JsonElement jsonElement = toJsonElement(fieldValue, fieldType);
				jsonObject.add(fieldName, jsonElement);
			}
		}
		return jsonObject;
	}

	private JsonElement toJsonElement(@Nullable Object object, Class<?> type) {
		if (object == null) {
			return JsonNull.INSTANCE;
		}
		Codec<JsonElement, Object> codec = getObjectCodec(type);
		return codec.encode(object);
	}

	public <T> T toConfigurationObject(JsonObject jsonObject, Class<T> objectType) {
		Constructor<T> constructor = Reflections.getSerializationConstructor(objectType);
		T instance = Reflections.newInstance(constructor);
		Field[] fields = objectType.getDeclaredFields();
		for (Field field : fields) {
			if (!Reflections.isStatic(field) && !field.isAnnotationPresent(Ignored.class)) {
				field.trySetAccessible();
				String fieldName = getFieldName(field);
				Class<?> fieldType = field.getType();
				JsonElement jsonElement = jsonObject.get(fieldName);
				Object object = toObject(jsonElement, fieldType);
				Reflections.setFieldValue(field, instance, object);
			}
		}
		return instance;
	}

	private String getFieldName(Field field) {
		Property property = field.getAnnotation(Property.class);
		if (property != null) {
			return property.value();
		}
		return field.getName();
	}

	@Nullable
	private Object toObject(JsonElement jsonElement, Class<?> type) {
		if (jsonElement.isJsonNull()) {
			return null;
		}
		Codec<JsonElement, Object> codec = getObjectCodec(type);
		return codec.decode(jsonElement);
	}

	@SuppressWarnings("unchecked")
	private Codec<JsonElement, Object> getObjectCodec(Class<?> type) {
		Codec<JsonElement, ?> codec = CODEC_REGISTRY.get(type);
		if (codec == null) {
			throw new CodecNotFoundException(type);
		}
		return (Codec<JsonElement, Object>) codec;
	}
}
