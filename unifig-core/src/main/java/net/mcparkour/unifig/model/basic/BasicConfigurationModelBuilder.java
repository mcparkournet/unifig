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

import java.util.ArrayList;
import java.util.List;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.codec.registry.basic.BasicCodecRegistry;
import net.mcparkour.unifig.condition.FieldCondition;
import net.mcparkour.unifig.model.ConfigurationModel;
import net.mcparkour.unifig.model.ConfigurationModelBuilder;
import net.mcparkour.unifig.model.section.ConfigurationModelSectionFactory;
import net.mcparkour.unifig.model.value.ConfigurationModelValueFactory;

public class BasicConfigurationModelBuilder<S, A, V> implements ConfigurationModelBuilder<S, A, V> {

	private ConfigurationModelSectionFactory<S, A, V> configurationModelSectionFactory;
	private ConfigurationModelValueFactory<S, A, V> configurationModelValueFactory;
	private CodecRegistry<S, A, V> codecRegistry;
	private List<FieldCondition> fieldConditions = new ArrayList<>();

	@Override
	public ConfigurationModelBuilder<S, A, V> with(ConfigurationModel<S, A, V> configurationModel) {
		this.configurationModelSectionFactory = configurationModel.getConfigurationModelSectionFactory();
		this.configurationModelValueFactory = configurationModel.getConfigurationModelValueFactory();
		CodecRegistry<S, A, V> codecRegistry = configurationModel.getCodecRegistry();
		this.codecRegistry = BasicCodecRegistry.<S, A, V>builder()
			.with(this.codecRegistry)
			.with(codecRegistry)
			.build();
		List<FieldCondition> fieldConditions = configurationModel.getFieldConditions();
		this.fieldConditions.addAll(fieldConditions);
		return this;
	}

	@Override
	public ConfigurationModelBuilder<S, A, V> with(ConfigurationModelBuilder<S, A, V> configurationModelBuilder) {
		this.configurationModelSectionFactory = configurationModelBuilder.getConfigurationModelSectionFactory();
		this.configurationModelValueFactory = configurationModelBuilder.getConfigurationModelValueFactory();
		CodecRegistry<S, A, V> codecRegistry = configurationModelBuilder.getCodecRegistry();
		this.codecRegistry = BasicCodecRegistry.<S, A, V>builder()
			.with(this.codecRegistry)
			.with(codecRegistry)
			.build();
		List<FieldCondition> fieldConditions = configurationModelBuilder.getFieldConditions();
		this.fieldConditions.addAll(fieldConditions);
		return this;
	}

	@Override
	public ConfigurationModelBuilder<S, A, V> configurationModelSectionFactory(ConfigurationModelSectionFactory<S, A, V> configurationModelSectionFactory) {
		this.configurationModelSectionFactory = configurationModelSectionFactory;
		return this;
	}

	@Override
	public ConfigurationModelBuilder<S, A, V> configurationModelValueFactory(ConfigurationModelValueFactory<S, A, V> configurationModelValueFactory) {
		this.configurationModelValueFactory = configurationModelValueFactory;
		return this;
	}

	@Override
	public ConfigurationModelBuilder<S, A, V> codecRegistry(CodecRegistry<S, A, V> codecRegistry) {
		this.codecRegistry = codecRegistry;
		return this;
	}

	@Override
	public ConfigurationModelBuilder<S, A, V> fieldConditions(FieldCondition... fieldConditions) {
		List<FieldCondition> fieldConditionList = List.of(fieldConditions);
		return fieldConditions(fieldConditionList);
	}

	@Override
	public ConfigurationModelBuilder<S, A, V> fieldConditions(List<FieldCondition> fieldConditions) {
		this.fieldConditions.addAll(fieldConditions);
		return this;
	}

	@Override
	public ConfigurationModel<S, A, V> build() {
		return new BasicConfigurationModel<>(this.configurationModelSectionFactory, this.configurationModelValueFactory, this.codecRegistry, this.fieldConditions);
	}

	@Override
	public ConfigurationModelSectionFactory<S, A, V> getConfigurationModelSectionFactory() {
		return this.configurationModelSectionFactory;
	}

	@Override
	public ConfigurationModelValueFactory<S, A, V> getConfigurationModelValueFactory() {
		return this.configurationModelValueFactory;
	}

	@Override
	public CodecRegistry<S, A, V> getCodecRegistry() {
		return this.codecRegistry;
	}

	@Override
	public List<FieldCondition> getFieldConditions() {
		return List.copyOf(this.fieldConditions);
	}
}
