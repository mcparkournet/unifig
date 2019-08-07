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

package net.mcparkour.unifig.codec.basic;

import net.mcparkour.unifig.codec.Codec;
import net.mcparkour.unifig.model.value.ConfigurationModelValue;
import net.mcparkour.unifig.model.value.ConfigurationModelValueFactory;

public abstract class AbstractCodec<S, A, V, T> implements Codec<S, A, V, T> {

	private ConfigurationModelValueFactory<S, A, V> configurationModelValueFactory;

	AbstractCodec(ConfigurationModelValueFactory<S, A, V> configurationModelValueFactory) {
		this.configurationModelValueFactory = configurationModelValueFactory;
	}

	@Override
	public ConfigurationModelValue<S, A, V> encode(T object) {
		return this.configurationModelValueFactory.createModelValue(object);
	}

	public ConfigurationModelValueFactory<S, A, V> getConfigurationModelValueFactory() {
		return this.configurationModelValueFactory;
	}
}
