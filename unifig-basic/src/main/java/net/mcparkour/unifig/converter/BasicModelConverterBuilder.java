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

package net.mcparkour.unifig.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.condition.FieldCondition;
import net.mcparkour.unifig.model.array.ModelArrayFactory;
import net.mcparkour.unifig.model.object.ModelObjectFactory;
import net.mcparkour.unifig.model.value.ModelValueFactory;

public class BasicModelConverterBuilder<O, A, V> implements ModelConverterBuilder<O, A, V> {

	private ModelObjectFactory<O, A, V> modelObjectFactory;
	private ModelArrayFactory<O, A, V> modelArrayFactory;
	private ModelValueFactory<O, A, V> modelValueFactory;
	private CodecRegistry<O, A, V> codecRegistry;
	private List<FieldCondition> fieldConditions = new ArrayList<>();

	@Override
	public ModelConverterBuilder<O, A, V> modelObjectFactory(ModelObjectFactory<O, A, V> factory) {
		this.modelObjectFactory = factory;
		return this;
	}

	@Override
	public ModelConverterBuilder<O, A, V> modelArrayFactory(ModelArrayFactory<O, A, V> factory) {
		this.modelArrayFactory = factory;
		return this;
	}

	@Override
	public ModelConverterBuilder<O, A, V> modelValueFactory(ModelValueFactory<O, A, V> factory) {
		this.modelValueFactory = factory;
		return this;
	}

	@Override
	public ModelConverterBuilder<O, A, V> codecRegistry(CodecRegistry<O, A, V> registry) {
		this.codecRegistry = registry;
		return this;
	}

	@Override
	public ModelConverterBuilder<O, A, V> fieldCondition(FieldCondition fieldCondition) {
		this.fieldConditions.add(fieldCondition);
		return this;
	}

	@Override
	public ModelConverterBuilder<O, A, V> fieldConditions(List<? extends FieldCondition> fieldConditions) {
		this.fieldConditions.addAll(fieldConditions);
		return this;
	}

	@Override
	public ModelConverter<O, A, V> build() {
		Objects.requireNonNull(this.modelObjectFactory);
		Objects.requireNonNull(this.modelArrayFactory);
		Objects.requireNonNull(this.modelValueFactory);
		Objects.requireNonNull(this.codecRegistry);
		return new BasicModelConverter<>(this.modelObjectFactory, this.modelArrayFactory, this.modelValueFactory, this.codecRegistry, this.fieldConditions);
	}

	@Override
	public ModelObjectFactory<O, A, V> getModelObjectFactory() {
		return this.modelObjectFactory;
	}

	@Override
	public ModelArrayFactory<O, A, V> getModelArrayFactory() {
		return this.modelArrayFactory;
	}

	@Override
	public ModelValueFactory<O, A, V> getModelValueFactory() {
		return this.modelValueFactory;
	}

	@Override
	public CodecRegistry<O, A, V> getCodecRegistry() {
		return this.codecRegistry;
	}

	@Override
	public List<FieldCondition> getFieldConditions() {
		return List.copyOf(this.fieldConditions);
	}
}
