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
package com.gs.dmn.signavio.testlab;

import com.gs.dmn.signavio.testlab.expression.ComplexExpression;
import com.gs.dmn.signavio.testlab.expression.Expression;
import com.gs.dmn.signavio.testlab.expression.ListExpression;
import com.gs.dmn.signavio.testlab.expression.Slot;
import org.omg.spec.dmn._20191111.model.TItemDefinition;

import java.util.LinkedHashMap;
import java.util.List;

public class TestLabEnhancer extends NopTestLabVisitor implements TestLabVisitor {
    private final TestLabUtil testLabUtil;

    public TestLabEnhancer(TestLabUtil testLabUtil) {
        this.testLabUtil = testLabUtil;
    }

    public void enhance(TestLab testLab) {
        testLab.accept(this, new LinkedHashMap<>());
    }

    @Override
    public Object visit(TestLab element, Object... params) {
        element.getTestCases().forEach(
            tc -> tc.accept(this, element)
        );
        return element;
    }

    @Override
    public Object visit(TestCase testCase, Object... params) {
        TestLab testLab = getTestLab(params);
        List<Expression> inputValues = testCase.getInputValues();
        if (inputValues != null) {
            for(int i = 0; i< inputValues.size(); i++) {
                Expression expression = inputValues.get(i);
                if (expression != null) {
                    InputParameterDefinition parameterDefinition = testLab.getInputParameterDefinitions().get(i);
                    TItemDefinition itemDefinition = testLabUtil.lookupItemDefinition(parameterDefinition);
                    expression.accept(this, testLab, itemDefinition);
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
                    expression.accept(this, testLab, itemDefinition);
                }
            }
        }
        return testCase;
    }

    @Override
    public Object visit(ListExpression element, Object... params) {
        TestLab testLab = getTestLab(params);
        TItemDefinition listType = getType(params);
        List<Expression> elements = element.getElements();
        if (elements != null) {
            TItemDefinition elementType = testLabUtil.elementType(listType);
            for (Expression e: elements) {
                if (e != null) {
                    e.accept(this, testLab, elementType);
                }
            }
        }
        return element;
    }

    @Override
    public Object visit(ComplexExpression expression, Object... params) {
        TestLab testLab = getTestLab(params);
        TItemDefinition complexType = getType(params);
        List<Slot> slots = expression.getSlots();
        if (slots != null) {
            slots.forEach(
                    s -> s.accept(this, testLab, testLabUtil.memberType(complexType, s))
            );
        }
        return expression;
    }

    @Override
    public Object visit(Slot slot, Object... params) {
        // Set name in parent
        TItemDefinition type = getType(params);
        String name = type.getName();
        slot.setItemComponentName(name);

        // Visit value
        slot.getValue().accept(this, params);

        return slot;
    }

    private TestLab getTestLab(Object... params) {
        return (TestLab)params[0];
    }

    private TItemDefinition getType(Object... params) {
        return (TItemDefinition) params[1];
    }
}
