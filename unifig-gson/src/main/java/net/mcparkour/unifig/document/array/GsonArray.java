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

package net.mcparkour.unifig.document.array;

import java.util.Iterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.mcparkour.octenace.document.array.DocumentArray;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.unifig.document.value.GsonValue;

public class GsonArray implements DocumentArray<JsonObject, JsonArray, JsonElement> {

	private JsonArray array;

	public GsonArray(JsonArray array) {
		this.array = array;
	}

	@Override
	public DocumentValue<JsonObject, JsonArray, JsonElement> get(int index) {
		JsonElement rawValue = this.array.get(index);
		return new GsonValue(rawValue);
	}

	@Override
	public void add(DocumentValue<JsonObject, JsonArray, JsonElement> value) {
		JsonElement rawValue = value.getValue();
		this.array.add(rawValue);
	}

	@Override
	public int getSize() {
		return this.array.size();
	}

	@Override
	public JsonArray getArray() {
		return this.array;
	}

	@Override
	public Iterator<DocumentValue<JsonObject, JsonArray, JsonElement>> iterator() {
		Iterator<JsonElement> iterator = this.array.iterator();
		return new GsonModelArrayIterator(iterator);
	}

	private static final class GsonModelArrayIterator implements Iterator<DocumentValue<JsonObject, JsonArray, JsonElement>> {

		private Iterator<JsonElement> iterator;

		private GsonModelArrayIterator(Iterator<JsonElement> iterator) {
			this.iterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return this.iterator.hasNext();
		}

		@Override
		public DocumentValue<JsonObject, JsonArray, JsonElement> next() {
			JsonElement next = this.iterator.next();
			return new GsonValue(next);
		}
	}
}
