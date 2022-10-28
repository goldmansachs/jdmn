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
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.native_.KotlinFactory;
import com.gs.dmn.transformation.proto.ProtoBufferKotlinFactory;

public class BasicDMNToKotlinTransformer extends BasicDMNToJavaTransformer {
    public BasicDMNToKotlinTransformer(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dialect, DMNModelRepository dmnModelRepository, EnvironmentFactory environmentFactory, NativeTypeFactory feelTypeTranslator, LazyEvaluationDetector lazyEvaluationDetector, InputParameters inputParameters) {
        super(dialect, dmnModelRepository, environmentFactory, feelTypeTranslator, lazyEvaluationDetector, inputParameters);
    }

    @Override
    protected void setProtoBufferFactory(BasicDMNToJavaTransformer transformer) {
        this.protoFactory = new ProtoBufferKotlinFactory(this);
    }

    @Override
    protected void setNativeFactory(BasicDMNToNativeTransformer<Type, DMNContext> transformer) {
        this.nativeFactory = new KotlinFactory(this);
    }

    // Types
    @Override
    public String itemDefinitionNativeQualifiedInterfaceName(TItemDefinition itemDefinition) {
        return this.nativeTypeFactory.nullableType(super.itemDefinitionNativeQualifiedInterfaceName(itemDefinition));
    }

    @Override
    public String itemDefinitionNativeClassName(String interfaceName) {
        if (interfaceName.endsWith("?")) {
            return interfaceName.replace("?", "Impl?");
        } else {
            return interfaceName + "Impl";
        }
    }

    @Override
    public String makeListType(String listType, String elementType) {
        return this.nativeTypeFactory.nullableType(String.format("%s<%s>", listType, this.nativeTypeFactory.nullableType(elementType)));
    }

    @Override
    public String makeListType(String listType) {
        return makeListType(listType, "Any?");
    }

    @Override
    protected String makeFunctionType(String name, String returnType) {
        return this.nativeTypeFactory.nullableType(String.format("%s<%s>", name, this.nativeTypeFactory.nullableType(returnType)));
    }

    @Override
    public String drgElementOutputType(DRGElementReference<? extends TDRGElement> reference) {
        return this.nativeTypeFactory.nullableType(super.drgElementOutputType(reference.getElement()));
    }

    @Override
    public String drgElementOutputType(TDRGElement element) {
        return this.nativeTypeFactory.nullableType(super.drgElementOutputType(element));
    }

    @Override
    public String drgElementSignatureWithMap(TDRGElement element) {
        return String.format("%s: %s, %s: %s", inputVariableName(), inputClassName(), executionContextVariableName(), executionContextClassName());
    }

    @Override
    protected String extractParameter(Pair<Pair<String, String>, String> param) {
        String type = param.getLeft().getLeft();
        String varName = param.getLeft().getRight();
        String propertyName = param.getRight();
        return String.format("var %s: %s = %s.%s",
                varName, type, executionContextVariableName(), getter(propertyName));
    }

    @Override
    public String lazyEvaluation(String elementName, String nativeName) {
        return isLazyEvaluated(elementName) ? String.format("%s?.getOrCompute()", nativeName) : nativeName;
    }

    @Override
    protected String inputClassName() {
        return "MutableMap<String, String>";
    }
}
