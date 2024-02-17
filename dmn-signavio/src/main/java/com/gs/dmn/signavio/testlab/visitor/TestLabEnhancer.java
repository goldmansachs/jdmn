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
package com.gs.dmn.signavio.testlab.visitor;

import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.signavio.testlab.*;
import com.gs.dmn.signavio.testlab.expression.ComplexExpression;
import com.gs.dmn.signavio.testlab.expression.Expression;
import com.gs.dmn.signavio.testlab.expression.ListExpression;
import com.gs.dmn.signavio.testlab.expression.Slot;

import java.util.List;

public class TestLabEnhancer extends NopTestLabVisitor<EnhancerContext> {
    private final TestLabUtil testLabUtil;

    public TestLabEnhancer(TestLabUtil testLabUtil) {
        this.testLabUtil = testLabUtil;
    }

    public void enhance(TestLab testLab) {
        testLab.accept(this, new EnhancerContext(testLab));
    }

    @Override
    public TestLabElement visit(TestLab testLab, EnhancerContext context) {
        List<TestCase> testCases = testLab.getTestCases();
        for (int i=0; i<testCases.size(); i++) {
            TestCase tc = testCases.get(i);
            tc.accept(this, context.withTestCaseIndex(i));
        }
        return testLab;
    }

    @Override
    public TestLabElement visit(TestCase testCase, EnhancerContext context) {
        TestLab testLab = context.getTestLab();
        int testCaseIndex = context.getTestCaseIndex();
        List<Expression> inputValues = testCase.getInputValues();
        if (inputValues != null) {
            for(int i = 0; i< inputValues.size(); i++) {
                Expression expression = inputValues.get(i);
                if (expression != null) {
                    InputParameterDefinition parameterDefinition = testLab.getInputParameterDefinitions().get(i);
                    try {
                        TItemDefinition itemDefinition = testLabUtil.lookupItemDefinition(parameterDefinition);
                        expression.accept(this, context.withType(itemDefinition));
                    } catch (Exception e) {
                        String requirementName = parameterDefinition.getRequirementName();
                        throw new DMNRuntimeException("Error in TestCase '%d' for '%s'".formatted(testCaseIndex + 1, requirementName), e);
                    }
                }
            }
        }
        List<Expression> expectedValues = testCase.getExpectedValues();
        if (expectedValues != null) {
            for(int i = 0; i< expectedValues.size(); i++) {
                Expression expression = expectedValues.get(i);
                if (expression != null) {
                    OutputParameterDefinition parameterDefinition = testLab.getOutputParameterDefinitions().get(i);
                    TItemDefinition itemDefinition = testLabUtil.lookupItemDefinition(parameterDefinition);
                    expression.accept(this, context.withType(itemDefinition));
                }
            }
        }
        return testCase;
    }

    @Override
    public TestLabElement visit(ListExpression element, EnhancerContext context) {
        TItemDefinition listType = context.getType();
        List<Expression> elements = element.getElements();
        if (elements != null) {
            TItemDefinition elementType = testLabUtil.elementType(listType);
            for (Expression e: elements) {
                if (e != null) {
                    e.accept(this, context.withType(elementType));
                }
            }
        }
        return element;
    }

    @Override
    public TestLabElement visit(ComplexExpression expression, EnhancerContext context) {
        TItemDefinition complexType = context.getType();
        List<Slot> slots = expression.getSlots();
        if (slots != null) {
            for (Slot slot : slots) {
                slot.accept(this, context.withType(testLabUtil.memberType(complexType, slot)));
            }
        }
        return expression;
    }

    @Override
    public TestLabElement visit(Slot slot, EnhancerContext context) {
        // Set name in parent
        TItemDefinition type = context.getType();
        String name = type.getName();
        slot.setItemComponentName(name);

        // Visit value
        slot.getValue().accept(this, context);

        return slot;
    }
}
