/*
 * MIT License
 *
 * Copyright (c) 2019-2020 MCParkour
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

package net.mcparkour.unifig.document.value;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.DocumentValueFactory;

public class GsonValueFactory implements DocumentValueFactory<JsonObject, JsonArray, JsonElement> {

	@Override
	public DocumentValue<JsonObject, JsonArray, JsonElement> createNullValue() {
		return createValue(JsonNull.INSTANCE);
	}

	@Override
	public DocumentValue<JsonObject, JsonArray, JsonElement> createValue(boolean value) {
		JsonPrimitive primitive = new JsonPrimitive(value);
		return createValue(primitive);
	}

	@Override
	public DocumentValue<JsonObject, JsonArray, JsonElement> createValue(int value) {
		JsonPrimitive primitive = new JsonPrimitive(value);
		return createValue(primitive);
	}

	@Override
	public DocumentValue<JsonObject, JsonArray, JsonElement> createValue(long value) {
		JsonPrimitive primitive = new JsonPrimitive(value);
		return createValue(primitive);
	}

	@Override
	public DocumentValue<JsonObject, JsonArray, JsonElement> createValue(float value) {
		JsonPrimitive primitive = new JsonPrimitive(value);
		return createValue(primitive);
	}

	@Override
	public DocumentValue<JsonObject, JsonArray, JsonElement> createValue(double value) {
		JsonPrimitive primitive = new JsonPrimitive(value);
		return createValue(primitive);
	}

	@Override
	public DocumentValue<JsonObject, JsonArray, JsonElement> createValue(String value) {
		JsonPrimitive primitive = new JsonPrimitive(value);
		return createValue(primitive);
	}

	@Override
	public DocumentValue<JsonObject, JsonArray, JsonElement> createObjectValue(JsonObject object) {
		return createValue(object);
	}

	@Override
	public DocumentValue<JsonObject, JsonArray, JsonElement> createArrayValue(JsonArray array) {
		return createValue(array);
	}

	@Override
	public DocumentValue<JsonObject, JsonArray, JsonElement> createValue(JsonElement value) {
		return new GsonValue(value);
	}
}
