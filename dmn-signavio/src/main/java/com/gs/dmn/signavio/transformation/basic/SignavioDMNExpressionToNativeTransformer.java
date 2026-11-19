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
package com.gs.dmn.signavio.transformation.basic;

import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.SemanticErrorException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.DMNExpressionToNativeTransformer;
import com.gs.dmn.transformation.native_.statement.Statement;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SignavioDMNExpressionToNativeTransformer extends DMNExpressionToNativeTransformer {
    protected SignavioDMNExpressionToNativeTransformer(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        super(dmnTransformer);
    }

    //
    // TInvocation
    //
    @Override
    protected Statement invocationExpressionToNative(TDRGElement element, TInvocation invocation, DMNContext parentContext) {
        // Make invocation
        TExpression invocationExpression = invocation.getExpression();
        if (invocationExpression instanceof TLiteralExpression literalExpression) {
            // Find invocable by name
            TInvocable invocable = findInvocable(literalExpression);
            // Compute name-java binding for arguments
            Map<String, Statement> argBinding = makeBindingMap(element, invocation, parentContext);
            // Make argument list
            List<Statement> argList = makeArguments(this.dmnTransformer.invocableFormalParameters(invocable), argBinding);
            // Make invocation statementTest
            return makeInvocationStatement(invocable, argList);
        } else {
            throw new SemanticErrorException(String.format("Not supported '%s'", invocationExpression.getClass().getSimpleName()));
        }
    }

    //
    // Annotations
    //
    @Override
    protected List<Pair<String, String>> collectAnnotationTexts(TDRGElement parent, TDecisionTable decisionTable, TDecisionRule rule) {
        List<Pair<String, String>> annotations = new ArrayList<>();
        String description = rule.getDescription();
        if (!StringUtils.isBlank(description)) {
            annotations.add(new Pair<>("", description));
        }
        return annotations;
    }
}
