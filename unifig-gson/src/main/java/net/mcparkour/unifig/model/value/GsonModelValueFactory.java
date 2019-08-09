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

package net.mcparkour.unifig.model.value;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GsonModelValueFactory implements ModelValueFactory<JsonObject, JsonArray, JsonElement> {

	@Override
	public ModelValue<JsonObject, JsonArray, JsonElement> createNullModelValue() {
		return new GsonModelValue(JsonNull.INSTANCE);
	}

	@Override
	public ModelValue<JsonObject, JsonArray, JsonElement> createModelValue(Object value) {
		if (value instanceof JsonElement) {
			JsonElement element = (JsonElement) value;
			return new GsonModelValue(element);
		}
		JsonPrimitive primitive = createJsonPrimitive(value);
		return new GsonModelValue(primitive);
	}

	private JsonPrimitive createJsonPrimitive(Object value) {
		if (value instanceof Boolean) {
			Boolean aBoolean = (Boolean) value;
			return new JsonPrimitive(aBoolean);
		}
		if (value instanceof Character) {
			Character character = (Character) value;
			return new JsonPrimitive(character);
		}
		if (value instanceof Number) {
			Number number = (Number) value;
			return new JsonPrimitive(number);
		}
		if (value instanceof String) {
			String string = (String) value;
			return new JsonPrimitive(string);
		}
		throw new RuntimeException("value is not a Boolean, Character, Number or String");
	}
}
