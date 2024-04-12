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

import com.gs.dmn.NameUtils;
import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.DMNExpressionToNativeTransformer;
import com.gs.dmn.transformation.native_.statement.Statement;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SignavioDMNExpressionToNativeTransformer extends DMNExpressionToNativeTransformer {
    protected SignavioDMNExpressionToNativeTransformer(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        super(dmnTransformer);
    }

    //
    // TInvocation
    //
    @Override
    protected Statement invocationExpressionToNative(TDRGElement element, TInvocation invocation, DMNContext parentContext) {
        // Compute name-java binding for arguments
        Map<String, Statement> argBinding = new LinkedHashMap<>();
        for (TBinding binding : invocation.getBinding()) {
            String argName = binding.getParameter().getName();
            TExpression argExpression = binding.getExpression();
            Statement argJava = this.dmnTransformer.expressionToNative(element, argExpression, parentContext);
            argBinding.put(argName, argJava);
        }
        // Build call
        TExpression body = invocation.getExpression();
        if (body instanceof TLiteralExpression) {
            String invocableName = NameUtils.invocableName(((TLiteralExpression) body).getText());
            TInvocable invocable = this.dmnModelRepository.findInvocableByName(invocableName);
            if (invocable == null) {
                throw new DMNRuntimeException(String.format("Cannot find BKM for '%s'", invocableName));
            }
            List<Statement> argList = new ArrayList<>();
            List<String> formalParameterList = this.dmnTransformer.invocableFEELParameterNames(invocable);
            for (String paramName : formalParameterList) {
                if (argBinding.containsKey(paramName)) {
                    Statement argValue = argBinding.get(paramName);
                    argList.add(argValue);
                } else {
                    throw new UnsupportedOperationException(String.format("Cannot find binding for parameter '%s'", paramName));
                }
            }
            String invocableInstance = this.dmnTransformer.singletonInvocableInstance(invocable);
            String argListString = argList.stream().map(Statement::getText).collect(Collectors.joining(", "));
            String expressionText = String.format("%s.apply(%s)", invocableInstance, this.dmnTransformer.augmentArgumentList(argListString));
            Type expressionType = this.dmnTransformer.drgElementOutputFEELType(invocable);
            return this.nativeFactory.makeExpressionStatement(expressionText, expressionType);
        } else {
            throw new DMNRuntimeException(String.format("Not supported '%s'", body.getClass().getSimpleName()));
        }
    }

    //
    // Annotations
    //
    @Override
    protected List<String> collectAnnotationTexts(TDecisionRule rule) {
        List<String> annotations = new ArrayList<>();
        String description = rule.getDescription();
        if (!StringUtils.isBlank(description)) {
            annotations.add(description);
        }
        return annotations;
    }
}
