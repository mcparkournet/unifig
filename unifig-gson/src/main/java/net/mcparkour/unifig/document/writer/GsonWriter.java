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

package net.mcparkour.unifig.document.writer;

import java.io.StringWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.unifig.options.IndentCharacter;
import net.mcparkour.unifig.options.Options;

public class GsonWriter implements DocumentWriter<JsonObject, JsonArray, JsonElement> {

	private String indent;
	private Gson gson;

	public GsonWriter(Options options) {
		this.indent = createIndent(options);
		this.gson = new GsonBuilder()
			.serializeNulls()
			.create();
	}

	private String createIndent(Options options) {
		IndentCharacter indentCharacter = options.getIndentCharacter();
		char character = indentCharacter.getCharacter();
		String characterString = String.valueOf(character);
		int indentSize = options.getIndentSize();
		return characterString.repeat(indentSize);
	}

	@Override
	public String write(DocumentValue<JsonObject, JsonArray, JsonElement> document) {
		JsonElement rawElement = document.getValue();
		StringWriter writer = new StringWriter();
		JsonWriter jsonWriter = new JsonWriter(writer);
		jsonWriter.setIndent(this.indent);
		this.gson.toJson(rawElement, jsonWriter);
		return writer.append('\n').toString();
	}
}
