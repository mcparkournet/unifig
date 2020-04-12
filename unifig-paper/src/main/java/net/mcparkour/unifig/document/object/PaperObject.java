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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.mcparkour.octenace.document.object.DocumentObject;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.unifig.document.value.PaperValue;

public class PaperObject implements DocumentObject<Map<String, Object>, List<Object>, Object> {

	private Map<String, Object> object;

	public PaperObject(Map<String, Object> object) {
		this.object = object;
	}

	@Override
	public DocumentValue<Map<String, Object>, List<Object>, Object> get(DocumentValue<Map<String, Object>, List<Object>, Object> key) {
		String rawKey = key.asString();
		Object value = this.object.get(rawKey);
		return new PaperValue(value);
	}

	@Override
	public void set(DocumentValue<Map<String, Object>, List<Object>, Object> key, DocumentValue<Map<String, Object>, List<Object>, Object> value) {
		String rawKey = key.asString();
		Object rawValue = value.getValue();
		this.object.put(rawKey, rawValue);
	}

	@Override
	public int getSize() {
		return this.object.size();
	}

	@Override
	public Map<String, Object> getObject() {
		return this.object;
	}

	@Override
	public Iterator<Entry<DocumentValue<Map<String, Object>, List<Object>, Object>, DocumentValue<Map<String, Object>, List<Object>, Object>>> iterator() {
		Set<Entry<String, Object>> entries = this.object.entrySet();
		Iterator<Entry<String, Object>> iterator = entries.iterator();
		return new PaperModelObjectIterator(iterator);
	}

	public static final class PaperModelObjectIterator implements Iterator<Entry<DocumentValue<Map<String, Object>, List<Object>, Object>, DocumentValue<Map<String, Object>, List<Object>, Object>>> {

		private Iterator<Entry<String, Object>> iterator;

		public PaperModelObjectIterator(Iterator<Entry<String, Object>> iterator) {
			this.iterator = iterator;
		}

		@Override
		public boolean hasNext() {
			return this.iterator.hasNext();
		}

		@Override
		public Entry<DocumentValue<Map<String, Object>, List<Object>, Object>, DocumentValue<Map<String, Object>, List<Object>, Object>> next() {
			Entry<String, Object> next = this.iterator.next();
			String key = next.getKey();
			Object value = next.getValue();
			PaperValue paperKey = new PaperValue(key);
			PaperValue paperValue = new PaperValue(value);
			return Map.entry(paperKey, paperValue);
		}
	}
}
