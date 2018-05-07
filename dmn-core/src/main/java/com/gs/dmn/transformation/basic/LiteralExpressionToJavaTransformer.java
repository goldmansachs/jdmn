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
    private final BasicDMN2JavaTransformer dmnTransformer;
    private final FEELTranslator feelTranslator;
    private final DMNModelRepository dmnModelRepository;

    LiteralExpressionToJavaTransformer(BasicDMN2JavaTransformer dmnTransformer) {
        this.dmnTransformer = dmnTransformer;
        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.feelTranslator = dmnTransformer.getFEELTranslator();
    }

    public Statement expressionToJava(String expressionText, TDRGElement element) {
        Environment environment = dmnTransformer.makeEnvironment(element);
        return literalExpressionToJava(expressionText, environment, element);
    }

    Statement literalExpressionToJava(String expressionText, Environment environment, TDRGElement element) {
        FEELContext context = FEELContext.makeContext(environment);
        Expression expression = feelTranslator.analyzeExpression(expressionText, context);
        Type expressionType = expression.getType();

        String javaExpression = feelTranslator.expressionToJava(expression, context);
        return new ExpressionStatement(javaExpression, expressionType);
    }
}
