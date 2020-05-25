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

package net.mcparkour.unifig;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.mcparkour.octenace.document.value.DocumentValue;
import net.mcparkour.octenace.mapper.DocumentMapper;
import net.mcparkour.unifig.document.DocumentModel;
import net.mcparkour.unifig.document.reader.DocumentReader;
import net.mcparkour.unifig.document.writer.DocumentWriter;
import net.mcparkour.unifig.options.Options;
import org.jetbrains.annotations.Nullable;

public class CommonConfiguration<O, A, V, T> implements Configuration<T> {

	private Class<T> configurationType;
	@Nullable
	private T defaultConfiguration;
	@Nullable
	private T cachedConfiguration;
	private Options options;
	private DocumentModel model;
	private DocumentMapper<O, A, V, T> mapper;
	private DocumentReader<O, A, V> reader;
	private DocumentWriter<O, A, V> writer;
	private String configurationFileName;

	public CommonConfiguration(Class<T> configurationType, @Nullable T defaultConfiguration, Options options, DocumentModel model, DocumentMapper<O, A, V, T> mapper, DocumentReader<O, A, V> reader, DocumentWriter<O, A, V> writer) {
		this.configurationType = configurationType;
		this.defaultConfiguration = defaultConfiguration;
		this.cachedConfiguration = null;
		this.options = options;
		this.model = model;
		this.mapper = mapper;
		this.reader = reader;
		this.writer = writer;
		this.configurationFileName = getConfigurationFileName(configurationType, model);
	}

	private static String getConfigurationFileName(Class<?> configurationType, DocumentModel model) {
		var annotation = configurationType.getAnnotation(net.mcparkour.unifig.annotation.Configuration.class);
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
		if (this.cachedConfiguration != null) {
			return this.cachedConfiguration;
		}
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
		DocumentValue<O, A, V> document = this.reader.read(string);
		T object = this.mapper.toObject(document);
		if (object == null) {
			throw new RuntimeException("Configuration document is null");
		}
		return this.cachedConfiguration = object;
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
		DocumentValue<O, A, V> document = this.mapper.toDocument(configuration);
		if (!document.isObject()) {
			throw new RuntimeException("Configuration is not an object");
		}
		this.cachedConfiguration = configuration;
		return this.writer.write(document);
	}
}
