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
package com.gs.dmn.feel.analysis.syntax.ast;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.DMNEnvironmentFactory;
import com.gs.dmn.transformation.basic.DMNExpressionToNativeTransformer;
import com.gs.dmn.transformation.native_.NativeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAnalysisVisitor<C> extends AbstractVisitor<C> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractAnalysisVisitor.class);

    protected final BasicDMNToNativeTransformer dmnTransformer;

    protected final DMNModelRepository dmnModelRepository;
    protected final EnvironmentFactory environmentFactory;

    protected final NativeTypeFactory nativeTypeFactory;
    protected final NativeFactory nativeFactory;

    protected final DMNEnvironmentFactory dmnEnvironmentFactory;
    protected final DMNExpressionToNativeTransformer expressionToNativeTransformer;

    protected AbstractAnalysisVisitor(BasicDMNToNativeTransformer dmnTransformer, ErrorHandler errorHandler) {
        super(errorHandler);
        this.dmnTransformer = dmnTransformer;

        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();

        this.nativeTypeFactory = dmnTransformer.getNativeTypeFactory();
        this.dmnEnvironmentFactory = dmnTransformer.getDMNEnvironmentFactory();
        this.nativeFactory = dmnTransformer.getNativeFactory();

        this.expressionToNativeTransformer = dmnTransformer.getExpressionToNativeTransformer();
    }
}
