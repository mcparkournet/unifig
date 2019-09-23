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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.mcparkour.unifig.converter.Converter;
import net.mcparkour.unifig.converter.ConverterFactory;
import net.mcparkour.unifig.model.object.ModelObject;
import net.mcparkour.unifig.model.reader.ModelReader;
import net.mcparkour.unifig.model.writer.ModelWriter;

public class BasicConfiguration<O, A, V, T> implements Configuration<T> {

	private Class<T> configurationType;
	private String configurationFileName;
	private ModelReader<O, A, V> reader;
	private ModelWriter<O, A, V> writer;
	private Converter<O, A, V> converter;

	public BasicConfiguration(Class<T> configurationType, String configurationExtension, ModelReader<O, A, V> reader, ModelWriter<O, A, V> writer, ConverterFactory<O, A, V> converterFactory) {
		this.configurationType = configurationType;
		this.configurationFileName = getConfigurationFileName(configurationType, configurationExtension);
		this.reader = reader;
		this.writer = writer;
		this.converter = converterFactory.createConverter();
	}

	private String getConfigurationFileName(Class<T> configurationType, String configurationExtension) {
		net.mcparkour.unifig.annotation.Configuration annotation = configurationType.getAnnotation(net.mcparkour.unifig.annotation.Configuration.class);
		if (annotation == null) {
			return "configuration." + configurationExtension;
		}
		return annotation.value() + "." + configurationExtension;
	}

	@Override
	public T read(Path directoryPath) {
		try {
			Path path = directoryPath.resolve(this.configurationFileName);
			String string = Files.readString(path);
			ModelObject<O, A, V> object = this.reader.read(string);
			return this.converter.toConfiguration(object, this.configurationType);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public void write(T configuration, Path directoryPath) {
		try {
			ModelObject<O, A, V> object = this.converter.fromConfiguration(configuration);
			String string = this.writer.write(object);
			Path path = directoryPath.resolve(this.configurationFileName);
			Files.writeString(path, string);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
