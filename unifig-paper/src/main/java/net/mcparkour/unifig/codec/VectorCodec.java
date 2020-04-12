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

package net.mcparkour.unifig.codec;

import java.util.List;
import java.util.Map;
import net.mcparkour.octenace.codec.Codec;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.mapper.Mapper;
import net.mcparkour.octenace.mapper.metadata.TypeMetadata;
import net.mcparkour.octenace.mapper.metadata.ValueMetadata;
import org.bukkit.util.Vector;

public class VectorCodec implements Codec<Map<String, Object>, List<Object>, Object, ValueMetadata, Vector> {

	@Override
	public DocumentValue<Map<String, Object>, List<Object>, Object> toDocument(Vector object, ValueMetadata metadata, Mapper<Map<String, Object>, List<Object>, Object, ?> mapper) {
		double x = object.getX();
		double y = object.getY();
		double z = object.getZ();
		var valueFactory = mapper.getValueFactory();
		String string = String.format("%s;%s;%s", x, y, z);
		return valueFactory.createValue(string);
	}

	@Override
	public Vector toObject(DocumentValue<Map<String, Object>, List<Object>, Object> document, ValueMetadata metadata, Mapper<Map<String, Object>, List<Object>, Object, ?> mapper) {
		String string = document.asString();
		String[] split = string.split(";");
		double x = Double.parseDouble(split[0]);
		double y = Double.parseDouble(split[1]);
		double z = Double.parseDouble(split[2]);
		return new Vector(x, y, z);
	}

	@Override
	public ValueMetadata getMetadata(TypeMetadata type, Mapper<Map<String, Object>, List<Object>, Object, ?> mapper) {
		return new ValueMetadata();
	}
}
