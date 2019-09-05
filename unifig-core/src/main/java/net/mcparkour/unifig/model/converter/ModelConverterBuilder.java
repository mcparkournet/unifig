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

package net.mcparkour.unifig.model.converter;

import java.util.List;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.condition.FieldCondition;
import net.mcparkour.unifig.model.array.ModelArrayFactory;
import net.mcparkour.unifig.model.section.ModelSectionFactory;
import net.mcparkour.unifig.model.value.ModelValueFactory;

public interface ModelConverterBuilder<S, A, V> extends ModelConverterDataHolder<S, A, V> {

	default ModelConverterBuilder<S, A, V> with(ModelConverterDataHolder<S, A, V> store) {
		ModelSectionFactory<S, A, V> sectionFactory = store.getModelSectionFactory();
		ModelArrayFactory<S, A, V> arrayFactory = store.getModelArrayFactory();
		ModelValueFactory<S, A, V> valueFactory = store.getModelValueFactory();
		CodecRegistry<S, A, V> codecRegistry = store.getCodecRegistry();
		List<FieldCondition> fieldConditions = store.getFieldConditions();
		return modelSectionFactory(sectionFactory)
			.modelArrayFactory(arrayFactory)
			.modelValueFactory(valueFactory)
			.codecRegistry(codecRegistry)
			.fieldConditions(fieldConditions);
	}

	ModelConverterBuilder<S, A, V> modelSectionFactory(ModelSectionFactory<S, A, V> factory);

	ModelConverterBuilder<S, A, V> modelArrayFactory(ModelArrayFactory<S, A, V> factory);

	ModelConverterBuilder<S, A, V> modelValueFactory(ModelValueFactory<S, A, V> factory);

	ModelConverterBuilder<S, A, V> codecRegistry(CodecRegistry<S, A, V> registry);

	ModelConverterBuilder<S, A, V> fieldCondition(FieldCondition fieldCondition);

	default ModelConverterBuilder<S, A, V> fieldConditions(FieldCondition... fieldConditions) {
		List<FieldCondition> fieldConditionList = List.of(fieldConditions);
		return fieldConditions(fieldConditionList);
	}

	ModelConverterBuilder<S, A, V> fieldConditions(List<? extends FieldCondition> fieldConditions);

	ModelConverter<S, A, V> build();
}
