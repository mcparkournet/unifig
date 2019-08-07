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

package net.mcparkour.unifig.model.basic;

import net.mcparkour.unifig.codec.basic.BooleanCodec;
import net.mcparkour.unifig.codec.basic.ByteCodec;
import net.mcparkour.unifig.codec.basic.CharacterCodec;
import net.mcparkour.unifig.codec.basic.DoubleCodec;
import net.mcparkour.unifig.codec.basic.FloatCodec;
import net.mcparkour.unifig.codec.basic.IntegerCodec;
import net.mcparkour.unifig.codec.basic.LongCodec;
import net.mcparkour.unifig.codec.basic.ShortCodec;
import net.mcparkour.unifig.codec.basic.StringCodec;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.codec.registry.basic.BasicCodecRegistry;
import net.mcparkour.unifig.condition.basic.IgnoredAnnotationNotPresentedFieldCondition;
import net.mcparkour.unifig.condition.basic.NonStaticFieldCondition;
import net.mcparkour.unifig.model.ConfigurationModel;
import net.mcparkour.unifig.model.ConfigurationModelBuilder;
import net.mcparkour.unifig.model.ConfigurationModelFactory;
import net.mcparkour.unifig.model.section.ConfigurationModelSectionFactory;
import net.mcparkour.unifig.model.value.ConfigurationModelValueFactory;

public class BasicConfigurationModelFactory<S, A, V> implements ConfigurationModelFactory<S, A, V> {

	private ConfigurationModelSectionFactory<S, A, V> configurationModelSectionFactory;
	private ConfigurationModelValueFactory<S, A, V> configurationModelValueFactory;

	public BasicConfigurationModelFactory(ConfigurationModelSectionFactory<S, A, V> configurationModelSectionFactory, ConfigurationModelValueFactory<S, A, V> configurationModelValueFactory) {
		this.configurationModelSectionFactory = configurationModelSectionFactory;
		this.configurationModelValueFactory = configurationModelValueFactory;
	}

	@Override
	public ConfigurationModel<S, A, V> createConfigurationModel() {
		return createConfigurationModelBuilder()
			.build();
	}

	@Override
	public ConfigurationModel<S, A, V> createConfigurationModel(ConfigurationModelBuilder<S, A, V> builder) {
		return createConfigurationModelBuilder()
			.with(builder)
			.build();
	}

	private ConfigurationModelBuilder<S, A, V> createConfigurationModelBuilder() {
		return BasicConfigurationModel.<S, A, V>builder()
			.configurationModelSectionFactory(this.configurationModelSectionFactory)
			.configurationModelValueFactory(this.configurationModelValueFactory)
			.codecRegistry(createCodecRegistry())
			.fieldConditions(new NonStaticFieldCondition(), new IgnoredAnnotationNotPresentedFieldCondition());
	}

	private CodecRegistry<S, A, V> createCodecRegistry() {
		return BasicCodecRegistry.<S, A, V>builder()
			.register(new BooleanCodec<>(this.configurationModelValueFactory), boolean.class, Boolean.class)
			.register(new CharacterCodec<>(this.configurationModelValueFactory), char.class, Character.class)
			.register(new ByteCodec<>(this.configurationModelValueFactory), byte.class, Byte.class)
			.register(new ShortCodec<>(this.configurationModelValueFactory), short.class, Short.class)
			.register(new IntegerCodec<>(this.configurationModelValueFactory), int.class, Integer.class)
			.register(new LongCodec<>(this.configurationModelValueFactory), long.class, Long.class)
			.register(new FloatCodec<>(this.configurationModelValueFactory), float.class, Float.class)
			.register(new DoubleCodec<>(this.configurationModelValueFactory), double.class, Double.class)
			.register(new StringCodec<>(this.configurationModelValueFactory), String.class)
			.build();
	}

	public ConfigurationModelSectionFactory<S, A, V> getConfigurationModelSectionFactory() {
		return this.configurationModelSectionFactory;
	}

	public ConfigurationModelValueFactory<S, A, V> getConfigurationModelValueFactory() {
		return this.configurationModelValueFactory;
	}
}
