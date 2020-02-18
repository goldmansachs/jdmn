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
package com.gs.dmn.tck;

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.ImportPath;
import com.gs.dmn.runtime.interpreter.Result;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironmentFactory;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import org.apache.commons.lang3.StringUtils;
import org.omg.dmn.tck.marshaller._20160719.TestCaseType;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.dmn.tck.marshaller._20160719.TestCases.TestCase;
import org.omg.dmn.tck.marshaller._20160719.TestCases.TestCase.InputNode;
import org.omg.dmn.tck.marshaller._20160719.TestCases.TestCase.ResultNode;
import org.omg.dmn.tck.marshaller._20160719.ValueType;
import org.omg.dmn.tck.marshaller._20160719.ValueType.Component;
import org.omg.spec.dmn._20180521.model.TDRGElement;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.omg.spec.dmn._20180521.model.TInputData;
import org.omg.spec.dmn._20180521.model.TInvocable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;
import java.util.stream.Collectors;

public class TCKUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TCKUtil.class);

    private final BasicDMN2JavaTransformer dmnTransformer;
    private final StandardFEELLib feelLib;
    private static final boolean IGNORE_ELEMENT_TYPE = false;

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
        if (element == null) {
            throw new DMNRuntimeException(String.format("Cannot find element '%s'", inputNode.getName()));
        } else if (element instanceof TInputData) {
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

    public String qualifiedName(TestCases testCases, ResultNode result) {
        String pkg = dmnTransformer.javaModelPackageName(testCases.getModelName());
        String cls = drgElementClassName(result);
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

    private List<Pair<String, String>> sortParameters(List<Pair<String, String>> parameters) {
        parameters.sort(Comparator.comparing(Pair::getLeft));
        return parameters;
    }

    private TDRGElement findDRGElementByName(String name) {
        try {
            return dmnTransformer.getDMNModelRepository().findDRGElementByName(name);
        } catch (Exception e) {
            return null;
        }
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
        QualifiedName typeRef;
        if (element == null) {
            throw new DMNRuntimeException(String.format("Cannot find element '%s'.", node.getName()));
        } else if (element instanceof TInputData) {
            String varTypeRef = ((TInputData) element).getVariable().getTypeRef();
            typeRef = QualifiedName.toQualifiedName(varTypeRef);
        } else {
            throw new UnsupportedOperationException(String.format("Cannot resolve FEEL type for node '%s'. '%s' not supported", node.getName(), element.getClass().getSimpleName()));
        }
        return typeRef;
    }

    private QualifiedName getTypeRef(ResultNode node) {
        TDRGElement element = findDRGElementByName(node.getName());
        QualifiedName typeRef;
        if (element == null) {
            throw new DMNRuntimeException(String.format("Cannot find element '%s'.", node.getName()));
        } else if (element instanceof TDecision) {
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
            Object value = jaxbElementValue(valueType.getValue());
            String text = getTextContent(value);
            if (text == null || "null".equals(text)) {
                return "null";
            } else if (isNumber(value, type)) {
                return String.format("number(\"%s\")", text);
            } else if (isString(value, type)) {
                return String.format("\"%s\"", text);
            } else if (isBoolean(value, type)) {
                return text;
            } else if (isDate(value, type)) {
                return String.format("date(\"%s\")", text);
            } else if (isTime(value, type)) {
                return String.format("time(\"%s\")", text);
            } else if (isDateTime(value, type)) {
                return String.format("dateAndTime(\"%s\")", text);
            } else if (isDurationTime(value, type)) {
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
        List<String> javaList = new ArrayList<>();
        for (ValueType listValueType : list.getItem()) {
            Type elementType = listType.getElementType();
            String value = toJavaExpression(listValueType, elementType);
            javaList.add(value);
        }
        return String.format("asList(%s)", javaList.stream().collect(Collectors.joining(", ")));
    }

    private String toJavaExpression(List<Component> components, ItemDefinitionType type) {
        List<Pair<String, String>> argumentList = new ArrayList<>();
        Set<String> members = type.getMembers();
        Set<String> present = new LinkedHashSet<>();
        for (Component c : components) {
            String name = c.getName();
            Type memberType = type.getMemberType(name);
            String value = toJavaExpression(c, memberType);
            argumentList.add(new Pair(name, value));
            present.add(name);
        }
        // Add the missing members
        for (String member: members) {
            if (!present.contains(member)) {
                Pair<String, String> pair = new Pair<>(member, "null");
                argumentList.add(pair);
            }
        }
        sortParameters(argumentList);
        String interfaceName = dmnTransformer.toJavaType(type);
        String arguments = argumentList.stream().map(Pair::getRight).collect(Collectors.joining(", "));
        return dmnTransformer.constructor(dmnTransformer.itemDefinitionJavaClassName(interfaceName), arguments);
    }

    public RuntimeEnvironment makeEnvironment(TestCase testCase) {
        RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentFactory.instance().makeEnvironment();
        List<InputNode> inputNode = testCase.getInputNode();
        for (int i = 0; i < inputNode.size(); i++) {
            InputNode input = inputNode.get(i);
            try {
                Object value = makeInputValue(input);
                String name = input.getName();
                runtimeEnvironment.bind(name, value);
            } catch (Exception e) {
                LOGGER.error("Cannot make environment ", e);
                throw new DMNRuntimeException(String.format("Cannot process input node '%s' for TestCase %d for DM '%s'", input.getName(), i, testCase.getName()), e);
            }
        }
        return runtimeEnvironment;
    }

    public List<Object> makeArgs(TDRGElement drgElement, TestCase testCase) {
        List<Object> args = new ArrayList<>();
        if (drgElement instanceof TInvocable) {
            // Preserve de order in the call
            List<FormalParameter> formalParameters = dmnTransformer.invFEELParameters(drgElement);
            Map<String, Object> map = new LinkedHashMap<>();
            List<InputNode> inputNode = testCase.getInputNode();
            for (int i = 0; i < inputNode.size(); i++) {
                TestCase.InputNode input = inputNode.get(i);
                try {
                    Object value = makeValue(input);
                    map.put(input.getName(), value);
                } catch (Exception e) {
                    LOGGER.error("Cannot make arguments", e);
                    throw new DMNRuntimeException(String.format("Cannot process input node '%s' for TestCase %d for DRGElement '%s'", input.getName(), i, drgElement.getName()), e);
                }
            }
            for (FormalParameter parameter: formalParameters) {
                args.add(map.get(parameter.getName()));
            }
        }
        return args;
    }

    private String drgElementName(TestCase testCase, ResultNode resultNode) {
        String elementToEvaluate;
        if (testCase.getType() == TestCaseType.DECISION) {
            elementToEvaluate = resultNode.getName();
        } else if (testCase.getType() == TestCaseType.DECISION_SERVICE) {
            elementToEvaluate = testCase.getInvocableName();
        } else if (testCase.getType() == TestCaseType.BKM) {
            elementToEvaluate = testCase.getInvocableName();
        } else {
            throw new IllegalArgumentException(String.format("Not supported type '%s'", testCase.getType()));
        }
        return elementToEvaluate;
    }

    public Object expectedValue(TestCase testCase, ResultNode resultNode) {
        if (IGNORE_ELEMENT_TYPE) {
            Object expectedValue = makeValue(resultNode.getExpected());
            return expectedValue;
        } else {
            String drgElementName = drgElementName(testCase, resultNode);
            TDRGElement drgElement = findDRGElementByName(drgElementName);
            if (drgElement == null) {
                throw new DMNRuntimeException(String.format("Cannot find DRG element '%s'", drgElementName));
            }
            Environment environment = dmnTransformer.makeEnvironment(drgElement);
            Type elementType = dmnTransformer.drgElementOutputFEELType(drgElement, environment);
            Object expectedValue = makeValue(resultNode.getExpected(), elementType);
            return expectedValue;
        }
    }

    public Result evaluate(DMNInterpreter interpreter, TestCase testCase, ResultNode resultNode) {
        String drgElementName = drgElementName(testCase, resultNode);
        TDRGElement drgElement = findDRGElementByName(drgElementName);
        ImportPath importPath = null;
        return interpreter.evaluateInvocation(importPath, drgElementName, makeArgs(drgElement, testCase), makeEnvironment(testCase));
    }

    private Object makeInputValue(InputNode inputNode) {
        if (IGNORE_ELEMENT_TYPE) {
            return makeValue(inputNode);
        } else {
            TDRGElement drgElement = findDRGElementByName(inputNode.getName());
            if (drgElement instanceof TInputData) {
                Type type = dmnTransformer.drgElementOutputFEELType(drgElement);
                return makeValue(inputNode, type);
            } else if (drgElement instanceof TDecision) {
                Type type = dmnTransformer.drgElementOutputFEELType(drgElement);
                return makeValue(inputNode, type);
            } else {
                return makeValue(inputNode, null);
            }
        }
    }

    public Object makeValue(ValueType valueType) {
        if (valueType.getValue() != null) {
            Object value = jaxbElementValue(valueType.getValue());
            String text = getTextContent(value);
            if (text == null) {
                return null;
            } else if (isNumber(value)) {
                return feelLib.number(text);
            } else if (isString(value)) {
                return text;
            } else if (isBoolean(value)) {
                if (StringUtils.isBlank(text)) {
                    return null;
                } else {
                    return Boolean.parseBoolean(text);
                }
            } else if (isDate(value)) {
                return feelLib.date(text);
            } else if (isTime(value)) {
                return feelLib.time(text);
            } else if (isDateTime(value)) {
                return feelLib.dateAndTime(text);
            } else if (isDurationTime(value)) {
                return feelLib.duration(text);
            } else {
                Object obj = valueType.getValue().getValue();
                if (obj instanceof Number) {
                    obj = feelLib.number(obj.toString());
                }
                return obj;
            }
        } else if (valueType.getList() != null) {
            return makeList(valueType);
        } else if (valueType.getComponent() != null) {
            return makeContext(valueType);
        }
        throw new DMNRuntimeException(String.format("Cannot make value for input '%s'", valueType));
    }

    private List makeList(ValueType valueType) {
        List<Object> javaList = new ArrayList<>();
        ValueType.List list = valueType.getList().getValue();
        for (ValueType listValueType : list.getItem()) {
            Object value = makeValue(listValueType);
            javaList.add(value);
        }
        return javaList;
    }

    private Context makeContext(ValueType valueType) {
        Context context = new Context();
        List<Component> components = valueType.getComponent();
        for (Component c : components) {
            String name = c.getName();
            Object value = makeValue(c);
            context.add(name, value);
        }
        return context;
    }

    private boolean isNumber(Object value) {
        return value instanceof Number;
    }

    private boolean isString(Object value) {
        return value instanceof String;
    }

    private boolean isBoolean(Object value) {
        return value instanceof Boolean;
    }

    private boolean isDate(Object value) {
        if (value instanceof XMLGregorianCalendar) {
            return ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATE;
        } else {
            return false;
        }
    }

    private boolean isTime(Object value) {
        if (value instanceof XMLGregorianCalendar) {
            return ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.TIME;
        } else {
            return false;
        }
    }

    private boolean isDateTime(Object value) {
        if (value instanceof XMLGregorianCalendar) {
            return ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATETIME;
        } else {
            return false;
        }
    }

    private boolean isDurationTime(Object value) {
        return value instanceof Duration;
    }

    public Object makeValue(ValueType valueType, Type type) {
        if (valueType.getValue() != null) {
            Object value = jaxbElementValue(valueType.getValue());
            String text = getTextContent(value);
            if (text == null) {
                return null;
            } else if (isNumber(value, type)) {
                return feelLib.number(text);
            } else if (isString(value, type)) {
                return text;
            } else if (isBoolean(value, type)) {
                if (StringUtils.isBlank(text)) {
                    return null;
                } else {
                    return Boolean.parseBoolean(text);
                }
            } else if (isDate(value, type)) {
                return feelLib.date(text);
            } else if (isTime(value, type)) {
                return feelLib.time(text);
            } else if (isDateTime(value, type)) {
                return feelLib.dateAndTime(text);
            } else if (isDurationTime(value, type)) {
                return feelLib.duration(text);
            } else {
                Object obj = valueType.getValue().getValue();
                if (obj instanceof Number) {
                    obj = feelLib.number(obj.toString());
                }
                return obj;
            }
        } else if (valueType.getList() != null) {
            if (type instanceof ListType) {
                return makeList(valueType, (ListType) type);
            } else {
                // Try singleton
                return makeList(valueType, new ListType(type));
            }
        } else if (valueType.getComponent() != null) {
            return makeContext(valueType, (CompositeDataType) type);
        }
        throw new DMNRuntimeException(String.format("Cannot make value for input '%s' with type '%s'", valueType, type));
    }

    private List makeList(ValueType valueType, ListType listType) {
        List<Object> javaList = new ArrayList<>();
        ValueType.List list = valueType.getList().getValue();
        for (ValueType listValueType : list.getItem()) {
            Type elementType = listType.getElementType();
            Object value = makeValue(listValueType, elementType);
            javaList.add(value);
        }
        return javaList;
    }

    private Context makeContext(ValueType valueType, CompositeDataType type) {
        Context context = new Context();
        List<Component> components = valueType.getComponent();
        for (Component c : components) {
            String name = c.getName();
            Type memberType = type == null ? null : type.getMemberType(name);
            Object value = makeValue(c, memberType);
            context.add(name, value);
        }
        return context;
    }

    private boolean isNumber(Object value, Type type) {
        if (value instanceof Number) {
            return true;
        }
        if (type == null) {
            return false;
        }
        return type == NumberType.NUMBER || type.equivalentTo(ListType.NUMBER_LIST);
    }

    private boolean isString(Object value, Type type) {
        if (value instanceof String) {
            return true;
        }
        if (type == null) {
            return false;
        }
        return type == StringType.STRING || type.equivalentTo(ListType.STRING_LIST);
    }

    private boolean isBoolean(Object value, Type type) {
        if (value instanceof Boolean) {
            return true;
        }
        if (type == null) {
            return false;
        }
        return type == BooleanType.BOOLEAN || type.equivalentTo(ListType.BOOLEAN_LIST);
    }

    private boolean isDate(Object value, Type type) {
        if (value instanceof XMLGregorianCalendar) {
            return ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATE;
        }
        if (type == null) {
            return false;
        }
        return type == DateType.DATE || type.equivalentTo(ListType.DATE_LIST);
    }

    private boolean isTime(Object value, Type type) {
        if (value instanceof XMLGregorianCalendar) {
            return ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.TIME;
        }
        if (type == null) {
            return false;
        }
        return type == TimeType.TIME || type.equivalentTo(ListType.TIME_LIST);
    }

    private boolean isDateTime(Object value, Type type) {
        if (value instanceof XMLGregorianCalendar) {
            return ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATETIME;
        }
        if (type == null) {
            return false;
        }
        return type == DateTimeType.DATE_AND_TIME || type.equivalentTo(ListType.DATE_AND_TIME_LIST);
    }

    private boolean isDurationTime(Object value, Type type) {
        if (value instanceof Duration) {
            return true;
        }
        if (type == null) {
            return false;
        }
        return type instanceof DurationType
                || type.equivalentTo(ListType.DAYS_AND_TIME_DURATION_LIST)
                || type.equivalentTo(ListType.YEARS_AND_MONTHS_DURATION_LIST);
    }

    private String getTextContent(Object value) {
        if (value instanceof String) {
            return (String) value;
        } else if (value instanceof Number) {
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

    private Object jaxbElementValue(Object value) {
        if (value instanceof JAXBElement) {
            if (((JAXBElement) value).isNil()) {
                return null;
            } else {
                value = ((JAXBElement) value).getValue();
            }
        }
        return value;
    }
}
