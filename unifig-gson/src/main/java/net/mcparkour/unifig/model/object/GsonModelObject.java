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

package net.mcparkour.unifig.model.object;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.mcparkour.unifig.model.value.GsonModelValue;
import net.mcparkour.unifig.model.value.ModelValue;

public class GsonModelObject implements ModelObject<JsonObject, JsonArray, JsonElement> {

	private JsonObject object;

	public GsonModelObject(JsonObject object) {
		this.object = object;
	}

	@Override
	public ModelValue<JsonObject, JsonArray, JsonElement> getValue(String key) {
		JsonElement value = this.object.get(key);
		return new GsonModelValue(value);
	}

	@Override
	public void setValue(String key, ModelValue<JsonObject, JsonArray, JsonElement> value) {
		JsonElement rawValue = value.getValue();
		this.object.add(key, rawValue);
	}

	@Override
	public Set<Map.Entry<String, ModelValue<JsonObject, JsonArray, JsonElement>>> getEntries() {
		return this.object.entrySet()
			.stream()
			.<Map.Entry<String, ModelValue<JsonObject, JsonArray, JsonElement>>>map(entry -> Map.entry(entry.getKey(), new GsonModelValue(entry.getValue())))
			.collect(Collectors.toUnmodifiableSet());
	}

	@Override
	public int getSize() {
		return this.object.size();
	}

	@Override
	public JsonObject getObject() {
		return this.object;
	}
}
