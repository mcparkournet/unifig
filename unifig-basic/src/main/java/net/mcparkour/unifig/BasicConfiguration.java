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

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.mcparkour.unifig.converter.Converter;
import net.mcparkour.unifig.model.Model;
import net.mcparkour.unifig.model.object.ModelObject;
import net.mcparkour.unifig.model.reader.ModelReader;
import net.mcparkour.unifig.model.writer.ModelWriter;
import net.mcparkour.unifig.options.Options;
import org.jetbrains.annotations.Nullable;

public class BasicConfiguration<O, A, V, T> implements Configuration<T> {

	private Class<T> configurationType;
	@Nullable
	private T defaultConfiguration;
	private Options options;
	private Model model;
	private Converter<O, A, V> converter;
	private ModelReader<O, A, V> reader;
	private ModelWriter<O, A, V> writer;
	private String configurationFileName;

	public BasicConfiguration(Class<T> configurationType, @Nullable T defaultConfiguration, Options options, Model model, Converter<O, A, V> converter, ModelReader<O, A, V> reader, ModelWriter<O, A, V> writer) {
		this.configurationType = configurationType;
		this.defaultConfiguration = defaultConfiguration;
		this.options = options;
		this.model = model;
		this.converter = converter;
		this.reader = reader;
		this.writer = writer;
		this.configurationFileName = getConfigurationFileName(configurationType, model);
	}

	private static String getConfigurationFileName(Class<?> configurationType, Model model) {
		net.mcparkour.unifig.annotation.Configuration annotation = configurationType.getAnnotation(net.mcparkour.unifig.annotation.Configuration.class);
		String configurationExtension = model.getFileExtension();
		if (annotation == null) {
			return "configuration." + configurationExtension;
		}
		return annotation.value() + "." + configurationExtension;
	}

	@Override
	public T read() {
		Path directoryPath = this.options.getDirectoryPath();
		return read(directoryPath);
	}

	@Override
	public T read(Path directoryPath) {
		try {
			Path path = directoryPath.resolve(this.configurationFileName);
			File file = path.toFile();
			if (this.defaultConfiguration != null && !file.exists()) {
				File directory = directoryPath.toFile();
				directory.mkdirs();
				file.createNewFile();
				write(this.defaultConfiguration, directoryPath);
				return this.defaultConfiguration;
			}
			String string = Files.readString(path);
			return readFromString(string);
		} catch (IOException exception) {
			throw new UncheckedIOException(exception);
		}
	}

	@Override
	public T readFromString(String string) {
		ModelObject<O, A, V> object = this.reader.read(string);
		return this.converter.toConfiguration(object, this.configurationType);
	}

	@Override
	public void write(T configuration) {
		Path directoryPath = this.options.getDirectoryPath();
		write(configuration, directoryPath);
	}

	@Override
	public void write(T configuration, Path directoryPath) {
		try {
			String string = writeToString(configuration);
			Path path = directoryPath.resolve(this.configurationFileName);
			Files.writeString(path, string);
		} catch (IOException exception) {
			throw new UncheckedIOException(exception);
		}
	}

	@Override
	public String writeToString(T configuration) {
		ModelObject<O, A, V> object = this.converter.fromConfiguration(configuration);
		return this.writer.write(object);
	}
}
