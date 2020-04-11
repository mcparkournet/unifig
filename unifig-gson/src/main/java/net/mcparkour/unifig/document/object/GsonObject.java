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

package net.mcparkour.unifig.document.object;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.mcparkour.octenace.document.object.DocumentObject;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.unifig.document.value.GsonValue;

public class GsonObject implements DocumentObject<JsonObject, JsonArray, JsonElement> {

	private JsonObject object;

	public GsonObject(JsonObject object) {
		this.object = object;
	}

	@Override
	public DocumentValue<JsonObject, JsonArray, JsonElement> get(DocumentValue<JsonObject, JsonArray, JsonElement> key) {
		String rawKey = key.asString();
		JsonElement value = this.object.get(rawKey);
		return new GsonValue(value);
	}

	@Override
	public void set(DocumentValue<JsonObject, JsonArray, JsonElement> key, DocumentValue<JsonObject, JsonArray, JsonElement> value) {
		String rawKey = key.asString();
		JsonElement rawValue = value.getValue();
		this.object.add(rawKey, rawValue);
	}

	@Override
	public int getSize() {
		return this.object.size();
	}

	@Override
	public JsonObject getObject() {
		return this.object;
	}

	@Override
	public Iterator<Entry<DocumentValue<JsonObject, JsonArray, JsonElement>, DocumentValue<JsonObject, JsonArray, JsonElement>>> iterator() {
		Set<Entry<String, JsonElement>> entries = this.object.entrySet();
		Iterator<Entry<String, JsonElement>> iterator = entries.iterator();
		return new GsonModelObjectIterator(iterator);
	}

	public static final class GsonModelObjectIterator implements Iterator<Entry<DocumentValue<JsonObject, JsonArray, JsonElement>, DocumentValue<JsonObject, JsonArray, JsonElement>>> {

		private Iterator<Entry<String, JsonElement>> iterator;

		public GsonModelObjectIterator(Iterator<Entry<String, JsonElement>> iterator) {
			this.iterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return this.iterator.hasNext();
		}

		@Override
		public Entry<DocumentValue<JsonObject, JsonArray, JsonElement>, DocumentValue<JsonObject, JsonArray, JsonElement>> next() {
			Entry<String, JsonElement> next = this.iterator.next();
			String key = next.getKey();
			JsonElement value = next.getValue();
			JsonPrimitive keyPrimitive = new JsonPrimitive(key);
			GsonValue gsonKey = new GsonValue(keyPrimitive);
			GsonValue gsonValue = new GsonValue(value);
			return Map.entry(gsonKey, gsonValue);
		}
	}
}
