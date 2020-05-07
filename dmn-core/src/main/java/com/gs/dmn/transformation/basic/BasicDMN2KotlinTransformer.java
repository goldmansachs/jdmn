/*
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
package com.gs.dmn.transformation.basic;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.synthesis.expression.KotlinExpressionFactory;
import com.gs.dmn.feel.synthesis.type.FEELTypeTranslator;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import org.omg.spec.dmn._20180521.model.TDRGElement;
import org.omg.spec.dmn._20180521.model.TItemDefinition;

import java.util.Map;

public class BasicDMN2KotlinTransformer extends BasicDMN2JavaTransformer {
    public BasicDMN2KotlinTransformer(DMNModelRepository dmnModelRepository, EnvironmentFactory environmentFactory, FEELTypeTranslator feelTypeTranslator, LazyEvaluationDetector lazyEvaluationDetector, Map<String, String> inputParameters) {
        super(dmnModelRepository, environmentFactory, feelTypeTranslator, lazyEvaluationDetector, inputParameters);
    }

    @Override
    protected void setExpressionFactory() {
        this.expressionFactory = new KotlinExpressionFactory(this);
    }

    // Types
    @Override
    public String itemDefinitionJavaQualifiedInterfaceName(TItemDefinition itemDefinition) {
        return nullableType(super.itemDefinitionJavaQualifiedInterfaceName(itemDefinition));
    }

    @Override
    public String itemDefinitionJavaClassName(String interfaceName) {
        if (interfaceName.endsWith("?")) {
            return interfaceName.replace("?", "Impl?");
        } else {
            return interfaceName + "Impl";
        }
    }

    @Override
    protected String makeListType(String listType, String elementType) {
        return nullableType(String.format("%s<%s>", listType, nullableType(elementType)));
    }

    @Override
    protected String makeListType(String listType) {
        return nullableType(String.format("%s<Any?>", listType));
    }

    @Override
    protected String makeFunctionType(String name, String returnType) {
        return nullableType(String.format("%s<%s>", name, nullableType(returnType)));
    }

    @Override
    public String drgElementOutputType(DRGElementReference<? extends TDRGElement> reference) {
        return nullableType(super.drgElementOutputType(reference.getElement()));
    }

    @Override
    public String drgElementOutputType(TDRGElement element) {
        return nullableType(super.drgElementOutputType(element));
    }

    @Override
    public String lazyEvaluation(String elementName, String javaName) {
        return isLazyEvaluated(elementName) ? String.format("%s?.getOrCompute()", javaName) : javaName;
    }

    private String nullableType(String type) {
        return type.endsWith("?") ? type : type + "?";
    }
}
