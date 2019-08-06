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

package net.mcparkour.unifig.model.value;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

public class PaperConfigurationModelValue implements ConfigurationModelValue<ConfigurationSection, Object, Object> {

	@Nullable
	private Object value;

	public PaperConfigurationModelValue(@Nullable Object value) {
		this.value = value;
	}

	@Override
	public boolean isNull() {
		return this.value == null;
	}

	@Override
	public boolean asBoolean() {
		Object value = getNotNullValue();
		if (!isBoolean()) {
			throw new ValueConversionException(Boolean.class);
		}
		return (Boolean) value;
	}

	@Override
	public boolean isBoolean() {
		return this.value instanceof Boolean;
	}

	@Override
	public char asCharacter() {
		if (!isCharacter()) {
			throw new ValueConversionException(Character.class);
		}
		String string = asString();
		return string.charAt(0);
	}

	@Override
	public boolean isCharacter() {
		return isString() && asString().length() == 1;
	}

	@Override
	public Number asNumber() {
		Object value = getNotNullValue();
		if (!isNumber()) {
			throw new ValueConversionException(Number.class);
		}
		return (Number) value;
	}

	@Override
	public boolean isNumber() {
		return this.value instanceof Number;
	}

	@Override
	public String asString() {
		Object value = getNotNullValue();
		if (!isString()) {
			throw new ValueConversionException(String.class);
		}
		return (String) value;
	}

	@Override
	public boolean isString() {
		return this.value instanceof String;
	}

	@Override
	public Object asArray() {
		Object value = getNotNullValue();
		if (!isArray()) {
			throw new ValueConversionException(Object.class);
		}
		return value;
	}

	@Override
	public boolean isArray() {
		return true;
	}

	@Override
	public ConfigurationSection asSection() {
		Object value = getNotNullValue();
		if (!isSection()) {
			throw new ValueConversionException(ConfigurationSection.class);
		}
		return (ConfigurationSection) value;
	}

	@Override
	public boolean isSection() {
		return this.value instanceof ConfigurationSection;
	}

	private Object getNotNullValue() {
		if (this.value == null) {
			throw new ValueConversionException("value is null");
		}
		return this.value;
	}

	@Nullable
	@Override
	public Object getValue() {
		return this.value;
	}
}
