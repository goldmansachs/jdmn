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
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.AnyType;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.synthesis.expression.NativeExpressionFactory;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

public abstract class AbstractAnalysisVisitor extends AbstractVisitor {
    protected final DMNModelRepository dmnModelRepository;
    protected final BasicDMNToNativeTransformer dmnTransformer;
    protected final EnvironmentFactory environmentFactory;
    protected final NativeTypeFactory feelTypeTranslator;
    protected final NativeExpressionFactory expressionFactory;

    protected AbstractAnalysisVisitor(BasicDMNToNativeTransformer dmnTransformer) {
        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.dmnTransformer = dmnTransformer;
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();
        this.feelTypeTranslator = dmnTransformer.getFEELTypeTranslator();
        this.expressionFactory = dmnTransformer.getExpressionFactory();
    }

    protected FEELContext makeFilterContext(FEELContext parentContext, Expression source, String filterVariableName) {
        Environment environment = this.environmentFactory.makeEnvironment(parentContext.getEnvironment());
        Type itemType = AnyType.ANY;
        if (source.getType() instanceof ListType) {
            itemType = ((ListType) source.getType()).getElementType();
        }
        environment.addDeclaration(this.environmentFactory.makeVariableDeclaration(filterVariableName, itemType));
        return FEELContext.makeContext(parentContext.getElement(), environment);
    }
}
