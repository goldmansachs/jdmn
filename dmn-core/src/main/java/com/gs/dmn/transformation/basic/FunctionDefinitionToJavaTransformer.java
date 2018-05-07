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
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.FunctionType;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.runtime.LambdaExpression;
import com.gs.dmn.transformation.java.ExpressionStatement;
import org.omg.spec.dmn._20180521.model.TDRGElement;
import org.omg.spec.dmn._20180521.model.TExpression;
import org.omg.spec.dmn._20180521.model.TFunctionDefinition;

public class FunctionDefinitionToJavaTransformer {
    private final BasicDMN2JavaTransformer dmnTransformer;
    private final DMNModelRepository modelRepository;
    private final EnvironmentFactory environmentFactory;

    FunctionDefinitionToJavaTransformer(BasicDMN2JavaTransformer dmnTransformer) {
        this.modelRepository = dmnTransformer.getDMNModelRepository();
        this.dmnTransformer = dmnTransformer;
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();
    }

    public ExpressionStatement functionDefinitionToJava(TFunctionDefinition expression, Environment environment, TDRGElement element) {
        FunctionType functionType = (FunctionType) dmnTransformer.expressionType(expression, environment);
        TExpression bodyExpression = expression.getExpression().getValue();
        Environment functionDefinitionEnvironment = dmnTransformer.makeFunctionDefinitionEnvironment(expression, environment);
        ExpressionStatement statement = (ExpressionStatement) dmnTransformer.expressionToJava(bodyExpression, functionDefinitionEnvironment, element);
        String body = statement.getExpression();

        String expressionText = functionDefinitionToJava(functionType, body, false);
        return new ExpressionStatement(expressionText, functionType);
    }

    public String functionDefinitionToJava(FunctionDefinition element, String body, boolean convertToContext) {
        FunctionType functionType = (FunctionType) element.getType();
        return functionDefinitionToJava(functionType, body, convertToContext);
    }

    private String functionDefinitionToJava(FunctionType functionType, String body, boolean convertToContext) {
        String returnType = dmnTransformer.toJavaType(dmnTransformer.convertType(functionType.getReturnType(), convertToContext));
        String signature = "Object... args";
        String applyMethod = dmnTransformer.applyMethod(functionType, signature, convertToContext, body);
        return functionDefinitionToJava(returnType, applyMethod);
    }

    private String functionDefinitionToJava(String returnType, String applyMethod) {
        String functionalInterface = LambdaExpression.class.getName();
        return dmnTransformer.functionalInterfaceConstructor(functionalInterface, returnType, applyMethod);
    }

}
