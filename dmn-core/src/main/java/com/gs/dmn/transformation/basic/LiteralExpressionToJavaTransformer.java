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
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.transformation.java.ExpressionStatement;
import com.gs.dmn.transformation.java.Statement;
import org.omg.spec.dmn._20180521.model.TDRGElement;

public class LiteralExpressionToJavaTransformer {
    private final BasicDMNToNativeTransformer dmnTransformer;
    private final FEELTranslator feelTranslator;
    private final DMNModelRepository dmnModelRepository;

    LiteralExpressionToJavaTransformer(BasicDMNToNativeTransformer dmnTransformer) {
        this.dmnTransformer = dmnTransformer;
        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.feelTranslator = dmnTransformer.getFEELTranslator();
    }

    public Statement expressionToNative(String expressionText, TDRGElement element) {
        Environment environment = this.dmnTransformer.makeEnvironment(element);
        return literalExpressionToNative(element, expressionText, environment);
    }

    Statement literalExpressionToNative(TDRGElement element, String expressionText, Environment environment) {
        FEELContext context = FEELContext.makeContext(element, environment);
        Expression expression = this.feelTranslator.analyzeExpression(expressionText, context);
        Type expressionType = expression.getType();

        String javaExpression = this.feelTranslator.expressionToNative(expression, context);
        return new ExpressionStatement(javaExpression, expressionType);
    }
}
