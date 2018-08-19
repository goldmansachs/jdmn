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
package com.gs.dmn.tck;

import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironmentFactory;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import org.apache.commons.lang3.StringUtils;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.dmn.tck.marshaller._20160719.TestCases.TestCase;
import org.omg.dmn.tck.marshaller._20160719.TestCases.TestCase.InputNode;
import org.omg.dmn.tck.marshaller._20160719.TestCases.TestCase.ResultNode;
import org.omg.dmn.tck.marshaller._20160719.ValueType;
import org.omg.dmn.tck.marshaller._20160719.ValueType.Component;
import org.omg.spec.dmn._20180521.model.TDRGElement;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.omg.spec.dmn._20180521.model.TInputData;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class TCKUtil {
    private final BasicDMN2JavaTransformer dmnTransformer;
    private final StandardFEELLib feelLib;

    public TCKUtil(BasicDMN2JavaTransformer dmnTransformer, StandardFEELLib feelLib) {
        this.dmnTransformer = dmnTransformer;
        this.feelLib = feelLib;
    }

    //
    // Delegate methods
    //
    public String drgElementClassName(ResultNode resultNode) {
        TDecision decision = (TDecision) findDRGElementByName(resultNode.getName());
        return dmnTransformer.drgElementClassName(decision);
    }

    public String drgElementVariableName(ResultNode resultNode) {
        TDecision decision = (TDecision) findDRGElementByName(resultNode.getName());
        return dmnTransformer.drgElementVariableName(decision);
    }

    public String drgElementOutputType(ResultNode resultNode) {
        TDecision decision = (TDecision) findDRGElementByName(resultNode.getName());
        return dmnTransformer.drgElementOutputType(decision);
    }

    public String drgElementArgumentsExtra(String arguments) {
        return dmnTransformer.drgElementArgumentsExtra(arguments);
    }

    public String drgElementArgumentList(ResultNode resultNode) {
        TDecision decision = (TDecision) findDRGElementByName(resultNode.getName());
        return dmnTransformer.drgElementArgumentList(decision);
    }

    public String inputDataVariableName(InputNode inputNode) {
        TDRGElement element = findDRGElementByName(inputNode.getName());
        if (element instanceof TInputData) {
            return dmnTransformer.inputDataVariableName((TInputData) element);
        } else {
            throw new UnsupportedOperationException(String.format("'%s' not supported", element.getClass().getSimpleName()));
        }
    }

    public String assertClassName() {
        return dmnTransformer.assertClassName();
    }

    public String annotationSetClassName() {
        return dmnTransformer.annotationSetClassName();
    }

    public String annotationSetVariableName() {
        return dmnTransformer.annotationSetVariableName();
    }

    public String eventListenerClassName() {
        return dmnTransformer.eventListenerClassName();
    }

    public String defaultEventListenerClassName() {
        return dmnTransformer.defaultEventListenerClassName();
    }

    public String eventListenerVariableName() {
        return dmnTransformer.eventListenerVariableName();
    }

    public String externalExecutorClassName() {
        return dmnTransformer.externalExecutorClassName();
    }

    public String externalExecutorVariableName() {
        return dmnTransformer.externalExecutorVariableName();
    }

    public String defaultExternalExecutorClassName() {
        return dmnTransformer.defaultExternalExecutorClassName();
    }

    public String cacheInterfaceName() {
        return dmnTransformer.cacheInterfaceName();
    }

    public String cacheVariableName() {
        return dmnTransformer.cacheVariableName();
    }

    public String defaultCacheClassName() {
        return dmnTransformer.defaultCacheClassName();
    }

    public boolean isCaching() {
        return dmnTransformer.isCaching();
    }

    public boolean isCaching(String element) {
        return dmnTransformer.isCaching(element);
    }

    public String drgElementSignatureExtraCache(String signature) {
        return dmnTransformer.drgElementSignatureExtraCache(signature);
    }

    public String drgElementArgumentsExtraCache(String arguments) {
        return dmnTransformer.drgElementArgumentsExtraCache(arguments);
    }

    public String drgElementDefaultArgumentsExtraCache(String arguments) {
        return dmnTransformer.drgElementDefaultArgumentsExtraCache(arguments);
    }

    public String getter(String outputName) {
        return dmnTransformer.getter(outputName);
    }

    public String upperCaseFirst(String name) {
        return dmnTransformer.upperCaseFirst(name);
    }

    public String lowerCaseFirst(String name) {
        return dmnTransformer.lowerCaseFirst(name);
    }

    public String qualifiedName(String pkg, String cls) {
        return dmnTransformer.qualifiedName(pkg, cls);
    }

    // For input parameters
    public String toJavaType(InputNode inputNode) {
        Type feelType = toFEELType(inputNode);
        return dmnTransformer.toJavaType(feelType);
    }

    // For input parameters
    public String toJavaExpression(TestCases testCases, TestCase testCase, InputNode inputNode) {
        Type inputType = toFEELType(inputNode);
        return toJavaExpression(inputNode, inputType);
    }

    // For expected values
    public String toJavaExpression(TestCases testCases, ResultNode resultNode) {
        Type outputType = toFEELType(resultNode);
        return toJavaExpression(resultNode.getExpected(), outputType);
    }

    private java.util.List<Pair<String, String>> sortParameters(java.util.List<Pair<String, String>> parameters) {
        parameters.sort(Comparator.comparing(Pair::getLeft));
        return parameters;
    }

    private TDRGElement findDRGElementByName(String name) {
        return dmnTransformer.getDMNModelRepository().findDRGElementByName(name);
    }

    private Type toFEELType(InputNode inputNode) {
        try {
            QualifiedName typeRef = getTypeRef(inputNode);
            return dmnTransformer.toFEELType(typeRef);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot resolve FEEL type for node '%s'", inputNode.getName()));
        }
    }

    private Type toFEELType(ResultNode resultNode) {
        try {
            QualifiedName typeRef = getTypeRef(resultNode);
            return dmnTransformer.toFEELType(typeRef);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot resolve FEEL type for node '%s'", resultNode.getName()));
        }
    }

    private QualifiedName getTypeRef(InputNode node) {
        TDRGElement element = findDRGElementByName(node.getName());
        QualifiedName typeRef = null;
        if (element instanceof TInputData) {
            typeRef = QualifiedName.toQualifiedName(((TInputData) element).getVariable().getTypeRef());
        } else {
            throw new UnsupportedOperationException(String.format("Cannot resolve FEEL type for node '%s'. '%s' not supported", node.getName(), element.getClass().getSimpleName()));
        }
        return typeRef;
    }

    private QualifiedName getTypeRef(ResultNode node) {
        TDRGElement element = findDRGElementByName(node.getName());
        QualifiedName typeRef = null;
        if (element instanceof TDecision) {
            typeRef = QualifiedName.toQualifiedName(((TDecision) element).getVariable().getTypeRef());
        } else {
            throw new UnsupportedOperationException(String.format("Cannot resolve FEEL type for node '%s'. '%s' not supported", node.getName(), element.getClass().getSimpleName()));
        }
        return typeRef;
    }

    //
    // Make java expressions for TestCases
    //
    private String toJavaExpression(ValueType valueType, Type type) {
        if (valueType.getValue() != null) {
            Object value = valueType.getValue();
            String text = getTextContent(value);
            if (text == null || "null".equals(text)) {
                return "null";
            } else if (isNumber(type)) {
                return String.format("number(\"%s\")", text);
            } else if (isString(type)) {
                return String.format("\"%s\"", text);
            } else if (isBoolean(type)) {
                return text;
            } else if (isDate(type)) {
                return String.format("date(\"%s\")", text);
            } else if (isTime(type)) {
                return String.format("time(\"%s\")", text);
            } else if (isDateTime(type)) {
                return String.format("dateAndTime(\"%s\")", text);
            } else if (isDurationTime(type)) {
                return String.format("duration(\"%s\")", text);
            } else {
                throw new DMNRuntimeException(String.format("Cannot make value for input '%s' with type '%s'", valueType, type));
            }
        } else if (valueType.getList() != null) {
            return toJavaExpression(valueType.getList().getValue(), (ListType) type);
        } else if (valueType.getComponent() != null) {
            return toJavaExpression(valueType.getComponent(), (ItemDefinitionType) type);
        }
        throw new DMNRuntimeException(String.format("Cannot make value for input '%s' with type '%s'", valueType, type));
    }

    private String toJavaExpression(ValueType.List list, ListType listType) {
        java.util.List<String> javaList = new ArrayList<>();
        for (ValueType listValueType : list.getItem()) {
            Type elementType = listType.getElementType();
            String value = toJavaExpression(listValueType, elementType);
            javaList.add(value);
        }
        return String.format("asList(%s)", javaList.stream().collect(Collectors.joining(", ")));
    }

    private String toJavaExpression(java.util.List<Component> components, ItemDefinitionType type) {
        java.util.List<Pair<String, String>> argumentList = new ArrayList<>();
        for (Component c : components) {
            String name = c.getName();
            Type memberType = type.getMemberType(name);
            String value = toJavaExpression(c, memberType);
            argumentList.add(new Pair(name, value));
        }
        sortParameters(argumentList);
        String interfaceName = dmnTransformer.toJavaType(type);
        String arguments = argumentList.stream().map(Pair::getRight).collect(Collectors.joining(", "));
        return dmnTransformer.constructor(dmnTransformer.itemDefinitionJavaClassName(interfaceName), arguments);
    }

    public RuntimeEnvironment makeEnvironment(TestCase testCase) {
        RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentFactory.instance().makeEnvironment();
        java.util.List<InputNode> inputNode = testCase.getInputNode();
        for (int i = 0; i < inputNode.size(); i++) {
            InputNode input = inputNode.get(i);
            try {
                Object value = makeValue(input);
                String name = input.getName();
                runtimeEnvironment.bind(name, value);
            } catch (Exception e) {
                e.printStackTrace();
                throw new DMNRuntimeException(String.format("Cannot process input node '%s' at position %d", input.getName(), i), e);
            }
        }
        return runtimeEnvironment;
    }

    private Object makeValue(InputNode inputNode) {
        TDRGElement drgElement = dmnTransformer.getDMNModelRepository().findDRGElementByName(inputNode.getName());
        if (drgElement instanceof TInputData) {
            Type type = dmnTransformer.toFEELType(QualifiedName.toQualifiedName(((TInputData) drgElement).getVariable().getTypeRef()));
            return makeValue(inputNode, type);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported DRGElement '%s'", drgElement.getClass().getSimpleName()));
        }
    }

    public Object makeValue(ValueType valueType, Type type) {
        if (valueType.getValue() != null) {
            Object value = valueType.getValue();
            String text = getTextContent(value);
            if (text == null) {
                return null;
            } else if (isNumber(type)) {
                return feelLib.number(text);
            } else if (isString(type)) {
                return text;
            } else if (isBoolean(type)) {
                if (StringUtils.isBlank(text)) {
                    return null;
                } else {
                    return Boolean.parseBoolean(text);
                }
            } else if (isDate(type)) {
                return feelLib.date(text);
            } else if (isTime(type)) {
                return feelLib.time(text);
            } else if (isDateTime(type)) {
                return feelLib.dateAndTime(text);
            } else if (isDurationTime(type)) {
                return feelLib.duration(text);
            } else {
                throw new DMNRuntimeException(String.format("Cannot make value for input '%s' with type '%s'", valueType, type));
            }
        } else if (valueType.getList() != null) {
            return makeList(valueType, (ListType) type);
        } else if (valueType.getComponent() != null) {
            return makeContext(valueType, (ItemDefinitionType) type);
        }
        throw new DMNRuntimeException(String.format("Cannot make value for input '%s' with type '%s'", valueType, type));
    }

    private java.util.List makeList(ValueType valueType, ListType listType) {
        java.util.List<Object> javaList = new ArrayList<>();
        ValueType.List list = valueType.getList().getValue();
        for (ValueType listValueType : list.getItem()) {
            Type elementType = listType.getElementType();
            Object value = makeValue(listValueType, elementType);
            javaList.add(value);
        }
        return javaList;
    }

    private Context makeContext(ValueType valueType, ItemDefinitionType type) {
        Context context = new Context();
        java.util.List<Component> components = valueType.getComponent();
        for (Component c : components) {
            String name = c.getName();
            Type memberType = type.getMemberType(name);
            Object value = makeValue(c, memberType);
            context.add(name, value);
        }
        return context;
    }

    private boolean isNumber(Type type) {
        return type == NumberType.NUMBER || type.equivalentTo(ListType.NUMBER_LIST);
    }

    private boolean isString(Type type) {
        return type == StringType.STRING || type.equivalentTo(ListType.STRING_LIST);
    }

    private boolean isBoolean(Type type) {
        return type == BooleanType.BOOLEAN || type.equivalentTo(ListType.BOOLEAN_LIST);
    }

    private boolean isDate(Type type) {
        return type == DateType.DATE || type.equivalentTo(ListType.DATE_LIST);
    }

    private boolean isTime(Type type) {
        return type == TimeType.TIME || type.equivalentTo(ListType.TIME_LIST);
    }

    private boolean isDateTime(Type type) {
        return type == DateTimeType.DATE_AND_TIME || type.equivalentTo(ListType.DATE_AND_TIME_LIST);
    }

    private boolean isDurationTime(Type type) {
        return type instanceof DurationType;
    }

    private String getTextContent(Object value) {
        if (value instanceof JAXBElement) {
            if (((JAXBElement) value).isNil()) {
                return null;
            } else {
                value = ((JAXBElement) value).getValue();
            }
        }
        if (value instanceof String) {
            return (String) value;
        } else if (value instanceof Float) {
            return feelLib.string(value);
        } else if (value instanceof Double) {
            return feelLib.string(value);
        } else if (value instanceof BigDecimal) {
            return feelLib.string(value);
        } else if (value instanceof Boolean) {
            return value.toString();
        } else if (value instanceof XMLGregorianCalendar) {
            return feelLib.string(value);
        } else if (value instanceof Duration) {
            return feelLib.string(value);
        } else if (value instanceof org.w3c.dom.Element) {
            return ((org.w3c.dom.Element) value).getTextContent();
        } else {
            return null;
        }
    }
}
