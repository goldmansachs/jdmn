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
package com.gs.dmn.runtime.interpreter;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.ConstraintType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.el.interpreter.ELInterpreter;
import com.gs.dmn.feel.analysis.semantics.type.CompositeDataType;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.interpreter.Result;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

import java.util.List;
import java.util.Set;

import static com.gs.dmn.context.DMNContext.INPUT_ENTRY_PLACE_HOLDER;

public class AllowedValuesConverter {
    static Object validateConstraint(Object value, Type expectedType, ELInterpreter<Type, DMNContext> elInterpreter, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        if (value == null) {
            return null;
        }
        if (expectedType instanceof ConstraintType) {
            return checkConstraint(value, expectedType, elInterpreter, dmnTransformer) ? value : null;
        } else if (expectedType instanceof ListType type) {
            Type expectedElementType = type.getElementType();
            if (value instanceof List list) {
                for (Object element: list) {
                    boolean validElement = checkConstraint(element, expectedElementType, elInterpreter, dmnTransformer);
                    if (!validElement) {
                        return null;
                    }
                }
            }
            return value;
        } else if (expectedType instanceof CompositeDataType type) {
            Set<String> members = type.getMembers();
            for (String member: members) {
                Type expectedMemeberType = type.getMemberType(member);
                if (value instanceof Context context) {
                    Object memberValue = context.get(member);
                    boolean validMember =  checkConstraint(memberValue, expectedMemeberType, elInterpreter, dmnTransformer);
                    if (!validMember) {
                        return null;
                    }
                }
            }
            return value;
        } else {
            return value;
        }
    }

    static boolean checkConstraint(Object value, Type expectedType, ELInterpreter<Type, DMNContext> elInterpreter, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        if (value == null) {
            return true;
        }
        if (expectedType instanceof ConstraintType) {
            return evaluateConstraint(value, expectedType, elInterpreter, dmnTransformer);
        } else if (expectedType instanceof ListType type) {
            Type expectedElementType = type.getElementType();
            if (value instanceof List list) {
                for (Object element: list) {
                    boolean res = checkConstraint(element, expectedElementType, elInterpreter, dmnTransformer);
                    if (!res) {
                        return false;
                    }
                }
            }
            return true;
        } else if (expectedType instanceof CompositeDataType type) {
            Set<String> members = type.getMembers();
            for (String member: members) {
                Type expectedMemeberType = type.getMemberType(member);
                if (value instanceof Context context) {
                    Object memberValue = context.get(member);
                    boolean res = checkConstraint(memberValue, expectedMemeberType, elInterpreter, dmnTransformer);
                    if (!res) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return true;
        }
    }

    private static boolean evaluateConstraint(Object value, Type expectedType, ELInterpreter<Type, DMNContext> elInterpreter, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        String name = "_input";
        DMNContext inputContext = dmnTransformer.makeLocalContext(dmnTransformer.makeBuiltInContext());
        inputContext.addDeclaration(dmnTransformer.getEnvironmentFactory().makeVariableDeclaration(name, expectedType));
        Expression<Type> inputExpression = elInterpreter.analyzeExpression(name, inputContext);

        DMNContext constraintContext = dmnTransformer.makeUnaryTestContext(inputExpression, inputContext);
        constraintContext.bind(INPUT_ENTRY_PLACE_HOLDER, value);
        String unaryTests = ((ConstraintType) expectedType).getUnaryTests();
        Result result = elInterpreter.evaluateUnaryTests(unaryTests, constraintContext);
        Object resultValue = Result.value(result);
        return resultValue instanceof Boolean b ? b : true;
    }
}
