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
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.Nullable;

public class GsonModelValue implements ModelValue<JsonObject, JsonArray, JsonElement> {

	private JsonElement value;

	public GsonModelValue(JsonElement value) {
		this.value = value;
	}

	@Override
	public boolean isNull() {
		return this.value.isJsonNull();
	}

	@Override
	public boolean asBoolean() {
		return this.value.getAsBoolean();
	}

	@Override
	public boolean isBoolean() {
		return isPrimitive() && asPrimitive().isBoolean();
	}

	@Override
	public char asCharacter() {
		return this.value.getAsCharacter();
	}

	@Override
	public boolean isCharacter() {
		return isPrimitive() && asPrimitive().isString() && asString().length() == 1;
	}

	@Override
	public Number asNumber() {
		return this.value.getAsNumber();
	}

	@Override
	public boolean isNumber() {
		return isPrimitive() && asPrimitive().isNumber();
	}

	@Override
	public String asString() {
		return this.value.getAsString();
	}

	@Override
	public boolean isString() {
		return isPrimitive() && asPrimitive().isString();
	}

	private JsonPrimitive asPrimitive() {
		return this.value.getAsJsonPrimitive();
	}

	private boolean isPrimitive() {
		return this.value.isJsonPrimitive();
	}

	@Override
	public JsonObject asObject() {
		return this.value.getAsJsonObject();
	}

	@Override
	public boolean isObject() {
		return this.value.isJsonObject();
	}

	@Override
	public JsonArray asArray() {
		return this.value.getAsJsonArray();
	}

	@Override
	public boolean isArray() {
		return this.value.isJsonArray();
	}

	@Nullable
	@Override
	public JsonElement getValue() {
		return this.value;
	}
}
