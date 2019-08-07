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

package net.mcparkour.unifig.model.converter.basic;

import java.util.ArrayList;
import java.util.List;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.condition.FieldCondition;
import net.mcparkour.unifig.model.converter.ModelConverter;
import net.mcparkour.unifig.model.converter.ModelConverterBuilder;
import net.mcparkour.unifig.model.section.ModelSectionFactory;
import net.mcparkour.unifig.model.value.ModelValueFactory;

public class BasicModelConverterBuilder<S, A, V> implements ModelConverterBuilder<S, A, V> {

	private ModelSectionFactory<S, A, V> modelSectionFactory;
	private ModelValueFactory<S, A, V> modelValueFactory;
	private CodecRegistry<S, A, V> codecRegistry;
	private List<FieldCondition> fieldConditions = new ArrayList<>();

	@Override
	public ModelConverterBuilder<S, A, V> modelSectionFactory(ModelSectionFactory<S, A, V> factory) {
		this.modelSectionFactory = factory;
		return this;
	}

	@Override
	public ModelConverterBuilder<S, A, V> modelValueFactory(ModelValueFactory<S, A, V> factory) {
		this.modelValueFactory = factory;
		return this;
	}

	@Override
	public ModelConverterBuilder<S, A, V> codecRegistry(CodecRegistry<S, A, V> registry) {
		this.codecRegistry = registry;
		return this;
	}

	@Override
	public ModelConverterBuilder<S, A, V> fieldCondition(FieldCondition fieldCondition) {
		this.fieldConditions.add(fieldCondition);
		return this;
	}

	@Override
	public ModelConverterBuilder<S, A, V> fieldConditions(List<? extends FieldCondition> fieldConditions) {
		this.fieldConditions.addAll(fieldConditions);
		return this;
	}

	@Override
	public ModelConverter<S, A, V> build() {
		return new BasicModelConverter<>(this.modelSectionFactory, this.modelValueFactory, this.codecRegistry, this.fieldConditions);
	}

	@Override
	public ModelSectionFactory<S, A, V> getModelSectionFactory() {
		return this.modelSectionFactory;
	}

	@Override
	public ModelValueFactory<S, A, V> getModelValueFactory() {
		return this.modelValueFactory;
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
