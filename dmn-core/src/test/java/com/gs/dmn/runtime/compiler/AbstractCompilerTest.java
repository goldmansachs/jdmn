/**
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.runtime.compiler;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.feel.analysis.semantics.type.BuiltinFunctionType;
import com.gs.dmn.feel.analysis.semantics.type.NumberType;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.NumericLiteral;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertNotNull;

public abstract class AbstractCompilerTest {
    private final DMNDialectDefinition dialectDefinition = new StandardDMNDialectDefinition();

    protected abstract JavaCompiler getCompiler();

    protected ClassData makeClassData() {
        FunctionDefinition element = new FunctionDefinition(Arrays.asList(), new NumericLiteral("123"), false);
        element.setType(new BuiltinFunctionType(Arrays.asList(), NumberType.NUMBER));
        FEELContext context = null;
        DMNModelRepository repository = new DMNModelRepository();
        BasicDMN2JavaTransformer dmnTransformer = dialectDefinition.createBasicTransformer(repository, new NopLazyEvaluationDetector(), new LinkedHashMap<>());
        FEELTranslator feelTranslator = dialectDefinition.createFEELTranslator(repository, new LinkedHashMap<>());
        return getCompiler().makeClassData(element, context, dmnTransformer, feelTranslator, dialectDefinition.createFEELLib().getClass().getName());
    }

    @Test
    public void testCompile() throws Exception {
        Class<?> cls = getCompiler().compile(makeClassData());
        assertNotNull(cls);
    }
}
