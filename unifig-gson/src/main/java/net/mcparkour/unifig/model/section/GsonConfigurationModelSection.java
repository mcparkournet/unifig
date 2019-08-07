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

package net.mcparkour.unifig.model.section;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.mcparkour.unifig.model.value.ConfigurationModelValue;
import net.mcparkour.unifig.model.value.GsonConfigurationModelValue;

public class GsonConfigurationModelSection implements ConfigurationModelSection<JsonObject, JsonArray, JsonElement> {

	private JsonObject section;

	public GsonConfigurationModelSection(JsonObject section) {
		this.section = section;
	}

	@Override
	public ConfigurationModelValue<JsonObject, JsonArray, JsonElement> get(String name) {
		JsonElement value = this.section.get(name);
		return new GsonConfigurationModelValue(value);
	}

	@Override
	public void set(String name, ConfigurationModelValue<JsonObject, JsonArray, JsonElement> value) {
		JsonElement rawValue = value.getValue();
		this.section.add(name, rawValue);
	}

	@Override
	public JsonObject getSection() {
		return this.section;
	}
}
