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
import net.mcparkour.unifig.model.object.ModelObjectFactory;
import net.mcparkour.unifig.model.value.ModelValueFactory;

public interface ModelConverterBuilder<O, A, V> extends ModelConverterDataHolder<O, A, V> {

	default ModelConverterBuilder<O, A, V> with(ModelConverterDataHolder<O, A, V> store) {
		ModelObjectFactory<O, A, V> objectFactory = store.getModelObjectFactory();
		ModelArrayFactory<O, A, V> arrayFactory = store.getModelArrayFactory();
		ModelValueFactory<O, A, V> valueFactory = store.getModelValueFactory();
		CodecRegistry<O, A, V> codecRegistry = store.getCodecRegistry();
		List<FieldCondition> fieldConditions = store.getFieldConditions();
		return modelObjectFactory(objectFactory)
			.modelArrayFactory(arrayFactory)
			.modelValueFactory(valueFactory)
			.codecRegistry(codecRegistry)
			.fieldConditions(fieldConditions);
	}

	ModelConverterBuilder<O, A, V> modelObjectFactory(ModelObjectFactory<O, A, V> factory);

	ModelConverterBuilder<O, A, V> modelArrayFactory(ModelArrayFactory<O, A, V> factory);

	ModelConverterBuilder<O, A, V> modelValueFactory(ModelValueFactory<O, A, V> factory);

	ModelConverterBuilder<O, A, V> codecRegistry(CodecRegistry<O, A, V> registry);

	ModelConverterBuilder<O, A, V> fieldCondition(FieldCondition fieldCondition);

	default ModelConverterBuilder<O, A, V> fieldConditions(FieldCondition... fieldConditions) {
		List<FieldCondition> fieldConditionList = List.of(fieldConditions);
		return fieldConditions(fieldConditionList);
	}

	ModelConverterBuilder<O, A, V> fieldConditions(List<? extends FieldCondition> fieldConditions);

	ModelConverter<O, A, V> build();
}
