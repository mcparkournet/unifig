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

package net.mcparkour.unifig;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnifigGsonTest {

	private UnifigGson unifigGson;
	private JsonObject expectedJsonObject;
	private TestConfiguration expectedTestConfiguration;

	@BeforeEach
	public void setUp() {
		this.unifigGson = new UnifigGson();
		this.expectedTestConfiguration = new TestConfiguration();
		this.expectedJsonObject = (JsonObject) new JsonParser().parse(TestConfiguration.JSON);
	}

	@Test
	public void testFromConfigurationObject() {
		JsonObject jsonObject = this.unifigGson.fromConfigurationObject(this.expectedTestConfiguration);
		Assertions.assertEquals(this.expectedJsonObject.toString(), jsonObject.toString());
	}

	@Test
	public void testToConfigurationObject() {
		TestConfiguration testConfiguration = this.unifigGson.toConfigurationObject(this.expectedJsonObject, TestConfiguration.class);
		Assertions.assertEquals(this.expectedTestConfiguration, testConfiguration);
	}
}
