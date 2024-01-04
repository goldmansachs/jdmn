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

import com.gs.dmn.ast.*;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.context.Context;
import com.gs.dmn.feel.analysis.syntax.ast.expression.context.ContextEntry;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.StringLiteral;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.external.JavaFunctionInfo;

import java.util.ArrayList;
import java.util.List;

public class ExternalFunctionExtractor {
    public JavaFunctionInfo extractJavaFunctionInfo(TDRGElement element, TFunctionDefinition functionDefinition) {
        // Extract class, method and param types names
        String className = null;
        String methodName = null;
        List<String> paramTypes = new ArrayList<>();
        TExpression body = functionDefinition.getExpression();
        if (body instanceof TContext) {
            for (TContextEntry entry: ((TContext) body).getContextEntry()) {
                String name = entry.getVariable().getName();
                if ("class".equals(name)) {
                    TExpression value = entry.getExpression();
                    if (value instanceof TLiteralExpression) {
                        className = ((TLiteralExpression) value).getText().replaceAll("\"", "");
                    }
                } else if (isMethodSignature(name)) {
                    TExpression value = entry.getExpression();
                    if (value instanceof TLiteralExpression) {
                        String signature = ((TLiteralExpression) value).getText().replaceAll("\"", "");
                        int lpIndex = signature.indexOf('(');
                        int rpIndex = signature.indexOf(')');
                        methodName = signature.substring(0, lpIndex);
                        String[] types = signature.substring(lpIndex + 1, rpIndex).split(",");
                        for (String t: types) {
                            paramTypes.add(t.trim());
                        }
                    }
                }
            }
        }
        if (className != null && methodName != null) {
            return new JavaFunctionInfo(className, methodName, paramTypes);
        } else {
            throw new DMNRuntimeException(String.format("Cannot extract Java function info for element '%s'", element.getName()));
        }
    }

    public JavaFunctionInfo extractJavaFunctionInfo(TDRGElement element, FunctionDefinition<Type> functionDefinition) {
        // Extract class, method and param types names
        String className = null;
        String methodName = null;
        List<String> paramTypes = new ArrayList<>();
        Expression<Type> body = functionDefinition.getBody();
        if (body instanceof Context) {
            body = ((Context<Type>) body).getEntries().get(0).getExpression();
        }
        if (body instanceof Context) {
            for (ContextEntry<Type> entry: ((Context<Type>) body).getEntries()) {
                String name = entry.getKey().getKey();
                if ("class".equals(name)) {
                    Expression<Type> value = entry.getExpression();
                    if (value instanceof StringLiteral) {
                        String lexeme = ((StringLiteral<Type>) value).getLexeme();
                        className = StringEscapeUtil.stripQuotes(lexeme);
                    }
                } else if (isMethodSignature(name)) {
                    Expression<Type> value = entry.getExpression();
                    if (value instanceof StringLiteral) {
                        String lexeme = ((StringLiteral<Type>) value).getLexeme();
                        String signature = StringEscapeUtil.stripQuotes(lexeme);
                        int lpIndex = signature.indexOf('(');
                        int rpIndex = signature.indexOf(')');
                        methodName = signature.substring(0, lpIndex);
                        String[] types = signature.substring(lpIndex + 1, rpIndex).split(",");
                        for (String t: types) {
                            paramTypes.add(t.trim());
                        }
                    }
                }
            }
        }
        if (className != null && methodName != null) {
            return new JavaFunctionInfo(className, methodName, paramTypes);
        } else {
            throw new DMNRuntimeException(String.format("Cannot extract Java function info for element '%s'", element.getName()));
        }
    }

    private boolean isMethodSignature(String name) {
        return "method signature".equals(name) || "methodSignature".equals(name) || "'method signature'".equals(name);
    }
}
