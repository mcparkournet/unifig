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

package net.mcparkour.unifig;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.mcparkour.octenace.mapper.DocumentMapper;
import net.mcparkour.unifig.document.reader.GsonReader;
import net.mcparkour.unifig.document.writer.GsonWriter;
import net.mcparkour.unifig.options.Options;

public class GsonConfiguration<T> extends CommonConfiguration<JsonObject, JsonArray, JsonElement, T> {

	private static final GsonModel GSON_MODEL = new GsonModel();
	private static final GsonReader GSON_READER = new GsonReader();

	public GsonConfiguration(Class<T> configurationType, T defaultConfiguration, Options options, DocumentMapper<JsonObject, JsonArray, JsonElement, T> mapper) {
		super(GSON_MODEL, GSON_READER, new GsonWriter(options), configurationType, defaultConfiguration, options, mapper);
	}
}
