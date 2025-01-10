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
package com.gs.dmn.feel.analysis.semantics;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.el.analysis.ELAnalyzer;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.FEELAnalyzerImpl;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;

public abstract class AbstractStandardBuiltinFunctionsResolutionTest extends AbstractBuiltinFunctionsResolutionTest {
    private final LazyEvaluationDetector lazyEvaluator = new NopLazyEvaluationDetector();
    private final BasicDMNToJavaTransformer basicTransformer = new JavaTimeDMNDialectDefinition().createBasicTransformer(new DMNModelRepository(), lazyEvaluator, new InputParameters());
    private final FEELAnalyzerImpl feelAnalyzer = new FEELAnalyzerImpl(basicTransformer);
    private final DMNContext dmnContext = basicTransformer.makeBuiltInContext();

    @Override
    protected ELAnalyzer<Type, DMNContext> getFEELAnalyzer() {
        return feelAnalyzer;
    }

    @Override
    protected DMNContext getDMNContext() {
        return dmnContext;
    }
}