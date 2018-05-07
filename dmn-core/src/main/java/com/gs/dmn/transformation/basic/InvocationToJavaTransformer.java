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
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.transformation.java.ExpressionStatement;
import com.gs.dmn.transformation.java.Statement;
import org.omg.spec.dmn._20180521.model.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InvocationToJavaTransformer {
    private final BasicDMN2JavaTransformer dmnTransformer;
    private final DMNModelRepository dmnModelRepository;

    InvocationToJavaTransformer(BasicDMN2JavaTransformer dmnTransformer) {
        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.dmnTransformer = dmnTransformer;
    }

    public Statement expressionToJava(TInvocation invocation, TDRGElement element) {
        Environment environment = dmnTransformer.makeEnvironment(element);
        return invocationExpressionToJava(invocation, environment, element);
    }

    Statement invocationExpressionToJava(TInvocation invocation, Environment environment, TDRGElement element) {
        // Compute name-java binding for arguments
        Map<String, Statement> argBinding = new LinkedHashMap<>();
        for(TBinding binding: invocation.getBinding()) {
            String argName= binding.getParameter().getName();
            TExpression argExpression = binding.getExpression().getValue();
            Statement argJava = dmnTransformer.expressionToJava(argExpression, environment, element);
            argBinding.put(argName, argJava);
        }
        // Build call
        TExpression body = invocation.getExpression().getValue();
        if (body instanceof TLiteralExpression) {
            String bkmName = ((TLiteralExpression) body).getText();
            TBusinessKnowledgeModel bkm = dmnModelRepository.findKnowledgeModelByName(bkmName);
            if (bkm == null) {
                throw new DMNRuntimeException(String.format("Cannot find BKM for '%s'", bkmName));
            }
            List<Statement> argList = new ArrayList<>();
            List<String> formalParameterList = dmnTransformer.bkmFEELParameterNames(bkm);
            for(String paramName: formalParameterList) {
                if (argBinding.containsKey(paramName)) {
                    Statement argValue = argBinding.get(paramName);
                    argList.add(argValue);
                } else {
                    throw new UnsupportedOperationException(String.format("Cannot find binding for parameter '%s'", paramName));
                }
            }
            String bkmFunctionName = dmnTransformer.bkmFunctionName(bkm);
            String argListString = argList.stream().map(s -> ((ExpressionStatement)s).getExpression()).collect(Collectors.joining(", "));
            String expressionText = String.format("%s(%s)", bkmFunctionName, dmnTransformer.drgElementArgumentsExtra(dmnTransformer.augmentArgumentList(argListString)));
            Type expressionType = dmnTransformer.toFEELType(dmnTransformer.drgElementOutputTypeRef(bkm));
            return new ExpressionStatement(expressionText, expressionType);
        } else {
            throw new DMNRuntimeException(String.format("Not supported '%s'", body.getClass().getSimpleName()));
        }
    }
}
