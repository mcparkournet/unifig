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

package net.mcparkour.unifig.document.value;

import java.util.List;
import java.util.Map;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.document.value.ValueConversionException;
import org.jetbrains.annotations.Nullable;

public class SnakeyamlValue implements DocumentValue<Map<String, Object>, List<Object>, Object> {

	@Nullable
	private Object value;

	public SnakeyamlValue(@Nullable Object value) {
		this.value = value;
	}

	@Override
	public boolean isNull() {
		return this.value == null;
	}

	@Override
	public boolean asBoolean() {
		if (!isBoolean()) {
			throw new ValueConversionException(Boolean.class);
		}
		return (Boolean) getNotNullValue();
	}

	@Override
	public boolean isBoolean() {
		return this.value instanceof Boolean;
	}

	@Override
	public byte asByte() {
		Number number = asNumber();
		return number.byteValue();
	}

	@Override
	public short asShort() {
		Number number = asNumber();
		return number.shortValue();
	}

	@Override
	public int asInt() {
		Number number = asNumber();
		return number.intValue();
	}

	@Override
	public long asLong() {
		Number number = asNumber();
		return number.longValue();
	}

	@Override
	public float asFloat() {
		Number number = asNumber();
		return number.floatValue();
	}

	@Override
	public double asDouble() {
		Number number = asNumber();
		return number.doubleValue();
	}

	private Number asNumber() {
		if (!isNumber()) {
			throw new ValueConversionException(Number.class);
		}
		return (Number) getNotNullValue();
	}

	@Override
	public boolean isNumber() {
		return this.value instanceof Number;
	}

	@Override
	public char asChar() {
		if (!isChar()) {
			throw new ValueConversionException(Character.class);
		}
		String string = asString();
		return string.charAt(0);
	}

	@Override
	public boolean isChar() {
		return isString() && asString().length() == 1;
	}

	@Override
	public String asString() {
		Object value = getNotNullValue();
		return value.toString();
	}

	@Override
	public boolean isString() {
		return this.value instanceof String;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> asObject() {
		if (!isObject()) {
			throw new ValueConversionException(Map.class);
		}
		return (Map<String, Object>) getNotNullValue();
	}

	@Override
	public boolean isObject() {
		return this.value instanceof Map;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object> asArray() {
		if (!isArray()) {
			throw new ValueConversionException(List.class);
		}
		return (List<Object>) getNotNullValue();
	}

	@Override
	public boolean isArray() {
		return this.value instanceof List;
	}

	@Nullable
	@Override
	public Object getValue() {
		return this.value;
	}

	private Object getNotNullValue() {
		if (this.value == null) {
			throw new ValueConversionException("Value is null");
		}
		return this.value;
	}
}
