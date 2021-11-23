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
package com.gs.dmn.runtime.compiler;

import com.gs.dmn.feel.analysis.semantics.environment.Declaration;
import com.gs.dmn.feel.analysis.semantics.type.FunctionType;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.runtime.DMNContext;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Function;
import com.gs.dmn.runtime.function.DMNInvocable;
import com.gs.dmn.runtime.function.FEELFunction;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.native_.FreeVariable;
import com.gs.dmn.transformation.native_.NativeFactory;
import org.apache.commons.text.RandomStringGenerator;
import org.omg.spec.dmn._20191111.model.*;

import java.util.ArrayList;
import java.util.List;

public class ClassParts {
    public static ClassParts makeClassParts(Function function, FEELTranslator feelTranslator, BasicDMNToNativeTransformer dmnTransformer, DMNContext context, String feelLibClassName) {
        // Apply method parts
        String signature = "Object[] args";
        boolean convertToContext = true;
        List<FreeVariable> freeVariables = new ArrayList<>();


        if (function instanceof FEELFunction) {
            FunctionDefinition functionDefinition = (FunctionDefinition) ((FEELFunction) function).getFunctionDefinition();
            FunctionType functionType = (FunctionType) functionDefinition.getType();

            // Apply method parts
            String body = feelTranslator.expressionToNative(functionDefinition.getBody(), context);
            NativeFactory nativeFactory = dmnTransformer.getNativeFactory();
            String applyMethod = nativeFactory.applyMethod(functionType, signature, convertToContext, body, freeVariables);
            String returnType = dmnTransformer.toNativeType(dmnTransformer.convertType(functionType.getReturnType(), convertToContext));

            return new ClassParts(feelLibClassName, returnType, applyMethod);
        } else if (function instanceof DMNInvocable) {
            TInvocable invocable = (TInvocable) ((DMNInvocable) function).getInvocable();
            Declaration declaration = (Declaration) ((DMNInvocable) function).getDeclaration();
            FunctionType functionType = (FunctionType) declaration.getType();
            if (invocable instanceof TBusinessKnowledgeModel) {
                TFunctionDefinition encapsulatedLogic = ((TBusinessKnowledgeModel) invocable).getEncapsulatedLogic();
                TExpression value = encapsulatedLogic.getExpression().getValue();
                if (value instanceof TLiteralExpression) {
                    String expText = ((TLiteralExpression) value).getText();
                    String body = feelTranslator.expressionToNative(expText, context);
                    NativeFactory nativeFactory = dmnTransformer.getNativeFactory();

                    String applyMethod = nativeFactory.applyMethod(functionType, signature, convertToContext, body, freeVariables);
                    String returnType = dmnTransformer.toNativeType(dmnTransformer.convertType(functionType.getReturnType(), convertToContext));

                    return new ClassParts(feelLibClassName, returnType, applyMethod);
                }
            }
        }
        throw new DMNRuntimeException(String.format("Compilation for lambda '%s' is not supported yet", function));
    }

    private static final RandomStringGenerator RANDOM_STRING_GENERATOR = new RandomStringGenerator.Builder()
            .withinRange('a', 'z').withinRange('0', '9').build();

    private static String uniqueName() {
        return RANDOM_STRING_GENERATOR.generate(10);
    }

    private final String packageName;
    private final String className;
    private final String feelLibClassName;
    private final String returnType;
    private final String applyMethod;

    private ClassParts(String feelLibClassName, String returnType, String applyMethod) {
        this.packageName = "com.gs.dmn.runtime";
        this.className = String.format("LambdaExpression%sImpl", uniqueName());
        this.feelLibClassName = feelLibClassName;
        this.returnType = returnType;
        this.applyMethod = applyMethod;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getFeelLibClassName() {
        return feelLibClassName;
    }

    public String getReturnType() {
        return returnType;
    }

    public String getApplyMethod() {
        return applyMethod;
    }
}

