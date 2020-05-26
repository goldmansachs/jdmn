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
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.FunctionType;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.synthesis.expression.NativeExpressionFactory;
import com.gs.dmn.runtime.LambdaExpression;
import com.gs.dmn.transformation.java.ExpressionStatement;
import org.omg.spec.dmn._20180521.model.TDRGElement;
import org.omg.spec.dmn._20180521.model.TExpression;
import org.omg.spec.dmn._20180521.model.TFunctionDefinition;

public class FunctionDefinitionToJavaTransformer {
    private final BasicDMNToNativeTransformer dmnTransformer;
    private final DMNModelRepository modelRepository;
    private final EnvironmentFactory environmentFactory;
    private final NativeExpressionFactory expressionFactory;

    FunctionDefinitionToJavaTransformer(BasicDMNToNativeTransformer dmnTransformer) {
        this.modelRepository = dmnTransformer.getDMNModelRepository();
        this.dmnTransformer = dmnTransformer;
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();
        this.expressionFactory = dmnTransformer.getExpressionFactory();
    }

    public ExpressionStatement functionDefinitionToNative(TDRGElement element, TFunctionDefinition expression, Environment environment) {
        FunctionType functionType = (FunctionType) this.dmnTransformer.expressionType(element, expression, environment);
        TExpression bodyExpression = expression.getExpression().getValue();
        Environment functionDefinitionEnvironment = this.dmnTransformer.makeFunctionDefinitionEnvironment(element, expression, environment);
        ExpressionStatement statement = (ExpressionStatement) this.dmnTransformer.expressionToNative(element, bodyExpression, functionDefinitionEnvironment);
        String body = statement.getExpression();

        String expressionText = functionDefinitionToNative(functionType, body, false);
        return new ExpressionStatement(expressionText, functionType);
    }

    public String functionDefinitionToNative(FunctionDefinition element, String body, boolean convertToContext) {
        FunctionType functionType = (FunctionType) element.getType();
        return functionDefinitionToNative(functionType, body, convertToContext);
    }

    private String functionDefinitionToNative(FunctionType functionType, String body, boolean convertToContext) {
        String returnType = this.dmnTransformer.toNativeType(this.dmnTransformer.convertType(functionType.getReturnType(), convertToContext));
        String signature = "Object... args";
        String applyMethod = this.expressionFactory.applyMethod(functionType, signature, convertToContext, body);
        return functionDefinitionToNative(returnType, applyMethod);
    }

    private String functionDefinitionToNative(String returnType, String applyMethod) {
        String functionalInterface = LambdaExpression.class.getName();
        return this.expressionFactory.functionalInterfaceConstructor(functionalInterface, returnType, applyMethod);
    }

}
