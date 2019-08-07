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

package net.mcparkour.unifig.model;

import java.util.List;
import net.mcparkour.unifig.codec.registry.CodecRegistry;
import net.mcparkour.unifig.condition.FieldCondition;
import net.mcparkour.unifig.model.section.ConfigurationModelSectionFactory;
import net.mcparkour.unifig.model.value.ConfigurationModelValueFactory;

public interface ConfigurationModelBuilder<S, A, V> {

	ConfigurationModelBuilder<S, A, V> with(ConfigurationModel<S, A, V> configurationModel);

	ConfigurationModelBuilder<S, A, V> with(ConfigurationModelBuilder<S, A, V> configurationModelBuilder);

	ConfigurationModelBuilder<S, A, V> codecRegistry(CodecRegistry<S, A, V> codecRegistry);

	ConfigurationModelBuilder<S, A, V> configurationModelSectionFactory(ConfigurationModelSectionFactory<S, A, V> configurationModelSectionFactory);

	ConfigurationModelBuilder<S, A, V> configurationModelValueFactory(ConfigurationModelValueFactory<S, A, V> configurationModelValueFactory);

	ConfigurationModelBuilder<S, A, V> fieldConditions(FieldCondition... fieldConditions);

	ConfigurationModelBuilder<S, A, V> fieldConditions(List<FieldCondition> fieldConditions);

	ConfigurationModel<S, A, V> build();

	CodecRegistry<S, A, V> getCodecRegistry();

	ConfigurationModelSectionFactory<S, A, V> getConfigurationModelSectionFactory();

	ConfigurationModelValueFactory<S, A, V> getConfigurationModelValueFactory();

	List<FieldCondition> getFieldConditions();
}
