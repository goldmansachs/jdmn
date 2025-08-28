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

import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.serialization.TCKVersion;
import com.gs.dmn.signavio.testlab.*;
import com.gs.dmn.signavio.testlab.TestCase;
import com.gs.dmn.signavio.testlab.Visitor;
import com.gs.dmn.signavio.testlab.expression.*;
import com.gs.dmn.tck.ast.*;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ToTCKVisitor implements Visitor<TCKBaseElement, TestLabContext> {
    private final DatatypeFactory datatypeFactory;

    private final TestLabUtil testLabUtil;

    public ToTCKVisitor(BasicDMNToNativeTransformer<Type, DMNContext> transformer) {
        try {
            this.datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        this.testLabUtil = new TestLabUtil(transformer);
    }

    @Override
    public TCKBaseElement visit(TestLab element, TestLabContext context) {
        TestCases tckTestCases = new TestCases();
        Map<String, String> nsContext = tckTestCases.getElementInfo().getNsContext();
        addNamespaces(nsContext, TCKVersion.LATEST);

        // Set model name
        String modelName = element.getModelName();
        tckTestCases.setModelName(modelName);

        // Add test cases
        context.setInputParameterDefinitions(element.getInputParameterDefinitions());
        context.setOutputParameterDefinitions(element.getOutputParameterDefinitions());
        List<TestCase> testLabTestCases = element.getTestCases();
        for (int i=0; i<testLabTestCases.size(); i++) {
            // Update context
            context.setTestIndex(i);

            // Transform and add test case
            TestCase testLabTestCase = testLabTestCases.get(i);
            com.gs.dmn.tck.ast.TestCase tckTestCase = (com.gs.dmn.tck.ast.TestCase) testLabTestCase.accept(this, context);
            tckTestCases.getTestCase().add(tckTestCase);
        }

        return tckTestCases;
    }


    @Override
    public TCKBaseElement visit(InputParameterDefinition element, TestLabContext context) {
        throw new IllegalArgumentException("Not supported");
    }

    @Override
    public TCKBaseElement visit(OutputParameterDefinition element, TestLabContext context) {
        throw new IllegalArgumentException("Not supported");
    }

    @Override
    public TCKBaseElement visit(TestCase element, TestLabContext context) {
        com.gs.dmn.tck.ast.TestCase tckTestCase = new com.gs.dmn.tck.ast.TestCase();

        // Start ID from 1
        tckTestCase.setId(String.format("%03d", context.getTestIndex() + 1));

        // Add input values
        for (int i=0; i<element.getInputValues().size(); i++) {
            // Update context
            context.setInputIndex(i);

            // Transform and add input node
            Expression expression = element.getInputValues().get(i);
            TCKBaseElement tckElement = expression == null ? null : expression.accept(this, context);
            InputNode inputNode = makeInputNode(tckElement, context);
            tckTestCase.getInputNode().add(inputNode);
        }

        // Add result nodes
        for (int i=0; i<element.getExpectedValues().size(); i++) {
            // Update context
            context.setOutputIndex(i);

            // Transform and add result node
            Expression expression = element.getExpectedValues().get(i);
            TCKBaseElement tckElement = expression == null ? null : expression.accept(this, context);
            ResultNode resultNode = makeResultNode(tckElement, context);
            tckTestCase.getResultNode().add(resultNode);
        }

        return tckTestCase;
    }

    @Override
    public TCKBaseElement visit(NumberLiteral element, TestLabContext context) {
        Object value = makeXMLDecimal(element.getValue());
        return makeLiteralValue(value);
    }

    @Override
    public TCKBaseElement visit(StringLiteral element, TestLabContext context) {
        String value = element.getValue();
        return makeLiteralValue(value);
    }

    @Override
    public TCKBaseElement visit(BooleanLiteral element, TestLabContext context) {
        Boolean value = element.getValue();
        return makeLiteralValue(value);
    }

    @Override
    public TCKBaseElement visit(DateLiteral element, TestLabContext context) {
        Object value = makeXMLTemporal(element.getValue());
        return makeLiteralValue(value);
    }

    @Override
    public TCKBaseElement visit(TimeLiteral element, TestLabContext context) {
        Object value = makeXMLTemporal(element.getValue());
        return makeLiteralValue(value);
    }

    @Override
    public TCKBaseElement visit(DatetimeLiteral element, TestLabContext context) {
        Object value = makeXMLTemporal(element.getValue());
        return makeLiteralValue(value);
    }

    @Override
    public TCKBaseElement visit(EnumerationLiteral element, TestLabContext context) {
        String value = element.getName();
        return makeLiteralValue(value);
    }

    @Override
    public TCKBaseElement visit(ListExpression element, TestLabContext context) {
        List<Expression> elements = element.getElements();
        if (elements != null) {
            com.gs.dmn.tck.ast.List tckList = new com.gs.dmn.tck.ast.List();
            elements.forEach(exp -> {
                TCKBaseElement tckElement = exp.accept(this, context);
                ValueType valueType = makeValueType(tckElement);
                tckList.getItem().add(valueType);
            });
            return tckList;
        } else {
            AnySimpleType anySimpleType = AnySimpleType.of(null);
            ValueType valueType = new ValueType();
            valueType.setValue(anySimpleType);
            return valueType;
        }
    }

    @Override
    public TCKBaseElement visit(ComplexExpression element, TestLabContext context) {
        // Make components
        List<Component> components = new ArrayList<>();
        element.getSlots().forEach(s -> {
            Component component = (Component) s.accept(this, context);
            components.add(component);
        });

        // Create value of components
        ValueType valueType = new ValueType();
        valueType.getComponent().addAll(components);

        return valueType;
    }

    @Override
    public TCKBaseElement visit(Slot element, TestLabContext context) {
        Component component = new Component();
        component.setName(element.getItemComponentName());
        TCKBaseElement slotValue = element.getValue().accept(this, context);
        copyValue(component, slotValue);
        return component;
    }

    //
    // Helper methods
    //
    private void addNamespaces(Map<String, String> nsContext, TCKVersion version) {
        nsContext.put("", version.getNamespace());
        nsContext.putAll(version.getPrefixToNamespaceMap());
    }

    private InputNode makeInputNode(TCKBaseElement tckElement, TestLabContext context) {
        InputNode inputNode = new InputNode();
        setInputNodeName(inputNode, context);
        copyValue(inputNode, tckElement);
        return inputNode;
    }

    private void setInputNodeName(InputNode inputNode, TestLabContext context) {
        inputNode.setName(drgName(context.getInputParameterDefinitions().get(context.getInputIndex())));
    }

    private ResultNode makeResultNode(TCKBaseElement tckElement, TestLabContext context) {
        ResultNode resultNode = new ResultNode();
        setResultNodeName(resultNode, context);
        ValueType valueType = makeValueType(tckElement);
        resultNode.setExpected(valueType);
        return resultNode;
    }

    private void setResultNodeName(ResultNode resultNode, TestLabContext context) {
        resultNode.setName(drgName(context.getOutputParameterDefinitions().get(context.getOutputIndex())));
    }

    private TCKBaseElement makeLiteralValue(Object value) {
        AnySimpleType anySimpleType = AnySimpleType.of(value);
        return makeValueType(anySimpleType);
    }

    private Object makeXMLDecimal(String value) {
        return value != null ? new BigDecimal(value) : null;
    }

    private Object makeXMLTemporal(String value)  {
        try {
            return value != null ? datatypeFactory.newXMLGregorianCalendar(value) : null;
        } catch (Exception e) {
            throw new SemanticError(String.format("Cannot create temporal literal from '%s'", value), e);
        }
    }

    private ValueType makeValueType(TCKBaseElement element) {
        ValueType valueType = new ValueType();
        if (element == null) {
            valueType.setValue(AnySimpleType.of(null));
        } else if (element instanceof AnySimpleType) {
            valueType.setValue((AnySimpleType) element);
        } else if (element instanceof com.gs.dmn.tck.ast.List) {
            valueType.setList((com.gs.dmn.tck.ast.List) element);
        } else if (element instanceof ValueType) {
            valueType = (ValueType) element;
        } else {
            throw new IllegalArgumentException("Not supported");
        }
        return valueType;
    }

    private void copyValue(ValueType targetValueType, TCKBaseElement tckElement) {
        if (tckElement == null) {
            targetValueType.setValue(AnySimpleType.of(null));
        } else if (tckElement instanceof ValueType) {
            ValueType valueType = (ValueType) tckElement;
            targetValueType.setValue(valueType.getValue());
            targetValueType.setList(valueType.getList());
            targetValueType.getComponent().clear();
            targetValueType.getComponent().addAll(valueType.getComponent());
        } else if (tckElement instanceof com.gs.dmn.tck.ast.List) {
            targetValueType.setList((com.gs.dmn.tck.ast.List) tckElement);
        } else {
            throw new IllegalArgumentException("Not supported");
        }
    }

    private String drgName(ParameterDefinition parameterDefinition) {
        TDRGElement drgElement = this.testLabUtil.findDRGElement(parameterDefinition);
        return drgElement.getName();
    }
}
