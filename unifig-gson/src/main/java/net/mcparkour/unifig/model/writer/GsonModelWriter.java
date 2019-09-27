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

package net.mcparkour.unifig.model.writer;

import java.io.StringWriter;
import java.io.Writer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import net.mcparkour.unifig.model.object.ModelObject;

public class GsonModelWriter implements ModelWriter<JsonObject, JsonArray, JsonElement> {

	private Gson gson = new GsonBuilder()
		.serializeNulls()
		.create();

	@Override
	public String write(ModelObject<JsonObject, JsonArray, JsonElement> object) {
		JsonObject rawObject = object.getObject();
		StringWriter writer = new StringWriter();
		JsonWriter jsonWriter = createJsonWriter(writer, 4);
		this.gson.toJson(rawObject, jsonWriter);
		return writer.append('\n').toString();
	}

	private JsonWriter createJsonWriter(Writer writer, int indent) {
		JsonWriter jsonWriter = new JsonWriter(writer);
		String indentString = " ".repeat(indent);
		jsonWriter.setIndent(indentString);
		return jsonWriter;
	}
}
