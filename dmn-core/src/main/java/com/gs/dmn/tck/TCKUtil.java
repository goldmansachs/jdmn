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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.ImportPath;
import com.gs.dmn.runtime.interpreter.Result;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironmentFactory;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import org.apache.commons.lang3.StringUtils;
import org.omg.dmn.tck.marshaller._20160719.TestCaseType;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.dmn.tck.marshaller._20160719.TestCases.TestCase;
import org.omg.dmn.tck.marshaller._20160719.TestCases.TestCase.InputNode;
import org.omg.dmn.tck.marshaller._20160719.TestCases.TestCase.ResultNode;
import org.omg.dmn.tck.marshaller._20160719.ValueType;
import org.omg.dmn.tck.marshaller._20160719.ValueType.Component;
import org.omg.spec.dmn._20191111.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;
import java.util.stream.Collectors;

public class TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TCKUtil.class);

    private final DMNModelRepository dmnModelRepository;

    private final BasicDMNToNativeTransformer transformer;
    private final StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib;
    private final NativeTypeFactory typeFactory;

    public TCKUtil(BasicDMNToNativeTransformer transformer, StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib) {
        this.transformer = transformer;
        this.feelLib = feelLib;
        this.dmnModelRepository = transformer.getDMNModelRepository();
        this.typeFactory = transformer.getNativeTypeFactory();
    }

    //
    // Translator - Input nodes
    //
    public InputNodeInfo extractInputNodeInfo(TestCases testCases, TestCase testCase, InputNode inputNode) {
        TDefinitions definitions = getRootModel(testCases);
        if (this.transformer.isSingletonInputData()) {
            String namespace = getNamespace(testCases, testCase, inputNode);
            DRGElementReference<? extends TDRGElement> reference = extractInfoFromModel(definitions, namespace, inputNode.getName());
            if (reference == null) {
                throw new DMNRuntimeException(String.format("Cannot find DRG element for InputNode '%s' for TestCase '%s' in TestCases '%s'", inputNode.getName(), testCase.getId(), testCases.getModelName()));
            }
            return new InputNodeInfo(testCases.getModelName(), inputNode.getName(), reference, inputNode);
        } else {
            Pair<DRGElementReference<? extends TDRGElement>, ValueType> pair = extractInfoFromValue(definitions, inputNode);
            if (pair == null || pair.getLeft() == null) {
                throw new DMNRuntimeException(String.format("Cannot find DRG element for InputNode '%s' for TestCase '%s' in TestCases '%s'", inputNode.getName(), testCase.getId(), testCases.getModelName()));
            }
            return new InputNodeInfo(testCases.getModelName(), inputNode.getName(), pair.getLeft(), pair.getRight());
        }
    }

    private DRGElementReference<? extends TDRGElement> extractInfoFromModel(TDefinitions rootDefinitions, String elementNamespace, String elementName) {
        if (elementNamespace == null) {
            return extractInfoFromModel(rootDefinitions, elementName, new ImportPath());
        } else {
            return extractInfoFromModel(rootDefinitions, elementNamespace, elementName, new ImportPath());
        }
    }

    private DRGElementReference<? extends TDRGElement> extractInfoFromModel(TDefinitions definitions, String elementNamespace, String elementName, ImportPath importPath) {
        if (definitions.getNamespace().equals(elementNamespace)) {
            // Found model, lookup element by name
            for (TDRGElement drg: this.dmnModelRepository.findDRGElements(definitions)) {
                if (drg.getName().equals(elementName)) {
                    return this.dmnModelRepository.makeDRGElementReference(importPath, drg);
                }
            }
        } else {
            // Lookup in imports
            for (TImport imp: definitions.getImport()) {
                String namespace = imp.getNamespace();
                TDefinitions child = this.dmnModelRepository.getModel(namespace);
                DRGElementReference<? extends TDRGElement> result = extractInfoFromModel(child, elementNamespace, elementName, new ImportPath(importPath, imp.getName()));
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    private DRGElementReference<? extends TDRGElement> extractInfoFromModel(TDefinitions definitions, String elementName, ImportPath importPath) {
        // Lookup element by name
        for (TDRGElement drg: this.dmnModelRepository.findDRGElements(definitions)) {
            if (drg.getName().equals(elementName)) {
                return this.dmnModelRepository.makeDRGElementReference(importPath, drg);
            }
        }
        // Lookup in imports
        for (TImport imp: definitions.getImport()) {
            String namespace = imp.getNamespace();
            TDefinitions child = this.dmnModelRepository.getModel(namespace);
            DRGElementReference<? extends TDRGElement> result = extractInfoFromModel(child, elementName, new ImportPath(importPath, imp.getName()));
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public Pair<DRGElementReference<? extends TDRGElement>, ValueType> extractInfoFromValue(TDefinitions definitions, InputNode node) {
        // Navigate the import paths if needed
        String name = node.getName();
        ImportPath path = new ImportPath();
        TImport import_ = getImport(definitions, name);
        ValueType value = node;
        while (import_ != null) {
            path.addPathElement(name);
            definitions = this.dmnModelRepository.getModel(import_.getNamespace());
            name = null;
            if (value.getComponent() != null && value.getComponent().size() == 1) {
                Component component = value.getComponent().get(0);
                name = component.getName();
                value = component;
            }
            import_ = getImport(definitions, name);
        }

        // Find DRG element and value
        List<TDRGElement> drgElements = this.dmnModelRepository.findDRGElements(definitions);
        TDRGElement element = null;
        for (TDRGElement e: drgElements) {
            if (e.getName().equals(name)) {
                element = e;
                break;
            }
        }
        if (element == null) {
            throw new DMNRuntimeException(String.format("Cannot find DRG element for node '%s'", node.getName()));
        }

        // Make result
        return new Pair<>(this.dmnModelRepository.makeDRGElementReference(path, element), value);
    }

    private TImport getImport(TDefinitions definitions, String name) {
        for (TImport imp: definitions.getImport()) {
            if (imp.getName().equals(name)) {
                return imp;
            }
        }
        return null;
    }

    public String toNativeType(InputNodeInfo info) {
        Type feelType = toFEELType(info);
        return this.typeFactory.nullableType(this.transformer.toNativeType(feelType));
    }

    public String inputDataVariableName(InputNodeInfo info) {
        TDRGElement element = info.getReference().getElement();
        if (element == null) {
            throw new DMNRuntimeException(String.format("Cannot find element '%s'", info.getNodeName()));
        } else {
            return this.transformer.drgElementReferenceVariableName(info.getReference());
        }
    }

    public String toNativeExpression(InputNodeInfo info) {
        Type inputType = toFEELType(info);
        return toNativeExpression(info.getValue(), inputType);
    }

    private Type toFEELType(InputNodeInfo info) {
        try {
            TDRGElement element = info.getReference().getElement();
            return this.transformer.drgElementOutputFEELType(element);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot resolve FEEL type for node '%s'", info.getNodeName()), e);
        }
    }

    private QualifiedName getTypeRef(InputNodeInfo node) {
        TDRGElement element = node.getReference().getElement();
        TDefinitions model = this.dmnModelRepository.getModel(element);
        QualifiedName typeRef;
        if (element == null) {
            throw new DMNRuntimeException(String.format("Cannot find element '%s'.", node.getNodeName()));
        } else if (element instanceof TInputData) {
            String varTypeRef = ((TInputData) element).getVariable().getTypeRef();
            typeRef = QualifiedName.toQualifiedName(model, varTypeRef);
        } else if (element instanceof TDecision) {
            String varTypeRef = ((TDecision) element).getVariable().getTypeRef();
            typeRef = QualifiedName.toQualifiedName(model, varTypeRef);
        } else {
            throw new UnsupportedOperationException(String.format("Cannot resolve FEEL type for node '%s'. '%s' not supported", node.getNodeName(), element.getClass().getSimpleName()));
        }
        return typeRef;
    }

    //
    // Translator - Result nodes
    //
    public ResultNodeInfo extractResultNodeInfo(TestCases testCases, TestCase testCase, ResultNode resultNode) {
        TDRGElement element = findDRGElement(testCases, testCase, resultNode);
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return new ResultNodeInfo(testCases.getModelName(), resultNode.getName(), reference, resultNode.getExpected());
    }

    public String toNativeExpression(ResultNodeInfo info) {
        Type outputType = toFEELType(info);
        return toNativeExpression(info.getExpectedValue(), outputType);
    }

    public String toNativeExpressionProto(ResultNodeInfo info) {
        Type resultType = toFEELType(info);
        ValueType expectedValue = info.getExpectedValue();
        String value = toNativeExpression(expectedValue, resultType);
        if (this.transformer.isDateTimeType(resultType) || this.transformer.isComplexType(resultType)) {
            return transformer.getNativeFactory().convertValueToProtoNativeType(value, resultType, false);
        } else {
            return value;
        }
    }

    public String qualifiedName(ResultNodeInfo info) {
        String pkg = this.transformer.nativeModelPackageName(info.getRootModelName());
        String cls = this.transformer.drgElementClassName(info.getReference().getElement());
        return this.transformer.qualifiedName(pkg, cls);
    }

    public String drgElementArgumentListExtraCache(String arguments) {
        return this.transformer.drgElementArgumentListExtraCache(arguments);
    }

    public String drgElementArgumentListExtra(String arguments) {
        return this.transformer.drgElementArgumentListExtra(arguments);
    }

    public String drgElementArgumentList(ResultNodeInfo info) {
        TDecision decision = (TDecision) info.getReference().getElement();
        return this.transformer.drgElementArgumentList(this.dmnModelRepository.makeDRGElementReference(decision));
    }

    private Type toFEELType(ResultNodeInfo resultNode) {
        try {
            TDRGElement element = resultNode.getReference().getElement();
            return this.transformer.drgElementOutputFEELType(element);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot resolve FEEL type for node '%s'", resultNode.getNodeName()), e);
        }
    }

    private QualifiedName getTypeRef(ResultNodeInfo node) {
        TDRGElement element = node.getReference().getElement();
        TDefinitions model = this.dmnModelRepository.getModel(element);
        QualifiedName typeRef;
        if (element == null) {
            throw new DMNRuntimeException(String.format("Cannot find element '%s'.", node.getNodeName()));
        } else if (element instanceof TDecision) {
            typeRef = QualifiedName.toQualifiedName(model, ((TDecision) element).getVariable().getTypeRef());
        } else {
            throw new UnsupportedOperationException(String.format("Cannot resolve FEEL type for node '%s'. '%s' not supported", node.getNodeName(), element.getClass().getSimpleName()));
        }
        return typeRef;
    }

    //
    // Translator - helper delegated methods
    //
    public String assertClassName() {
        return this.transformer.assertClassName();
    }

    public String annotationSetClassName() {
        return this.transformer.annotationSetClassName();
    }

    public String annotationSetVariableName() {
        return this.transformer.annotationSetVariableName();
    }

    public String eventListenerClassName() {
        return this.transformer.eventListenerClassName();
    }

    public String defaultEventListenerClassName() {
        return this.transformer.defaultEventListenerClassName();
    }

    public String eventListenerVariableName() {
        return this.transformer.eventListenerVariableName();
    }

    public String externalExecutorClassName() {
        return this.transformer.externalExecutorClassName();
    }

    public String externalExecutorVariableName() {
        return this.transformer.externalExecutorVariableName();
    }

    public String defaultExternalExecutorClassName() {
        return this.transformer.defaultExternalExecutorClassName();
    }

    public String cacheInterfaceName() {
        return this.transformer.cacheInterfaceName();
    }

    public String cacheVariableName() {
        return this.transformer.cacheVariableName();
    }

    public String defaultCacheClassName() {
        return this.transformer.defaultCacheClassName();
    }

    public String defaultConstructor(String className) {
        return this.transformer.defaultConstructor(className);
    }

    public boolean isCached(InputNodeInfo info) {
        return this.transformer.isCached(info.getReference().getElementName());
    }

    //
    // Interpreter
    //
    public Result evaluate(DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> interpreter, TestCases testCases, TestCase testCase, ResultNode resultNode) {
        ResultNodeInfo info = extractResultNodeInfo(testCases, testCase, resultNode);
        RuntimeEnvironment runtimeEnvironment = makeEnvironment(testCases, testCase);
        List<Object> args = makeArgs(info.getReference().getElement(), testCase);
        return interpreter.evaluate(info.getReference(), args, runtimeEnvironment);
    }

    public Object expectedValue(TestCases testCases, TestCase testCase, ResultNode resultNode) {
        ResultNodeInfo info = extractResultNodeInfo(testCases, testCase, resultNode);
        return makeValue(info.getExpectedValue());
    }

    private RuntimeEnvironment makeEnvironment(TestCases testCases, TestCase testCase) {
        RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentFactory.instance().makeEnvironment();
        List<InputNode> inputNode = testCase.getInputNode();
        for (int i = 0; i < inputNode.size(); i++) {
            InputNode input = inputNode.get(i);
            try {
                if (this.transformer.isSingletonInputData()) {
                    InputNodeInfo info = extractInputNodeInfo(testCases, testCase, input);
                    String name = this.transformer.bindingName(info.getReference());
                    Object value = makeValue(info.getValue());
                    runtimeEnvironment.bind(name, value);
                } else {
                    String name = input.getName();
                    Object value = makeValue(input);
                    runtimeEnvironment.bind(name, value);
                }
            } catch (Exception e) {
                LOGGER.error("Cannot make environment ", e);
                throw new DMNRuntimeException(String.format("Cannot process input node '%s' for TestCase %d for DM '%s'", input.getName(), i, testCase.getName()), e);
            }
        }
        return runtimeEnvironment;
    }

    private List<Object> makeArgs(TDRGElement drgElement, TestCase testCase) {
        List<Object> args = new ArrayList<>();
        if (drgElement instanceof TInvocable) {
            // Preserve de order in the call
            List<FormalParameter> formalParameters = this.transformer.invocableFEELParameters(drgElement);
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

    //
    // Model - lookup methods
    //
    private TDefinitions getRootModel(TestCases testCases) {
        TDefinitions definitions;
        if (this.dmnModelRepository.getAllDefinitions().size() == 1) {
            // One single DM
            definitions = this.dmnModelRepository.getRootDefinitions();
        } else {
            // Find DM by namespace
            String namespace = getNamespace(testCases);
            if (!StringUtils.isEmpty(namespace)) {
                definitions = this.dmnModelRepository.getModel(namespace);
            } else {
                throw new DMNRuntimeException(String.format("Missing namespace for TestCases '%s'", testCases.getModelName()));
            }
        }
        if (definitions == null) {
            throw new DMNRuntimeException(String.format("Cannot find root DM for TestCases '%s'", testCases.getModelName()));
        } else {
            return definitions;
        }
    }

    private TDRGElement findDRGElement(TestCases testCases, TestCase testCase, ResultNode node) {
        try {
            String namespace = getNamespace(testCases, testCase, node);
            String name = drgElementName(testCases, testCase, node);
            if (namespace != null) {
                return this.dmnModelRepository.findDRGElementByName(namespace, name);
            } else {
                return this.dmnModelRepository.findDRGElementByName(name);
            }
        } catch (Exception e) {
            return null;
        }
    }

    private String drgElementName(TestCases testCases, TestCase testCase, ResultNode resultNode) {
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

    private String getNamespace(TestCases testCases) {
        return testCases.getNamespace();
    }

    private String getNamespace(TestCase testCase) {
        return testCase.getNamespace();
    }

    private String getNamespace(TestCases testCases, TestCase testCase, InputNode node) {
        String namespace = getNamespace(node);
        if (StringUtils.isEmpty(namespace)) {
            namespace = getNamespace(testCases);
        }
        return namespace;
    }

    private String getNamespace(TestCases testCases, TestCase testCase, ResultNode node) {
        String namespace = getNamespace(node);
        if (StringUtils.isEmpty(namespace)) {
            namespace = getNamespace(testCase);
        }
        if (StringUtils.isEmpty(namespace)) {
            namespace = getNamespace(testCases);
        }
        return namespace;
    }

    private String getNamespace(InputNode node) {
        return node.getNamespace();
    }

    private String getNamespace(ResultNode node) {
        return node.getNamespace();
    }

    //
    // Make java expressions from ValueType
    //
    private String toNativeExpression(ValueType valueType, Type type) {
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
            return toNativeExpression(valueType.getList().getValue(), (ListType) type);
        } else if (valueType.getComponent() != null) {
            return toNativeExpression(valueType.getComponent(), (ItemDefinitionType) type);
        }
        throw new DMNRuntimeException(String.format("Cannot make value for input '%s' with type '%s'", valueType, type));
    }

    private String toNativeExpression(ValueType.List list, ListType listType) {
        List<String> javaList = new ArrayList<>();
        for (ValueType listValueType : list.getItem()) {
            Type elementType = listType.getElementType();
            String value = toNativeExpression(listValueType, elementType);
            javaList.add(value);
        }
        return String.format("asList(%s)", String.join(", ", javaList));
    }

    private String toNativeExpression(List<Component> components, ItemDefinitionType type) {
        List<Pair<String, String>> argumentList = new ArrayList<>();
        Set<String> members = type.getMembers();
        Set<String> present = new LinkedHashSet<>();
        for (Component c : components) {
            String name = c.getName();
            Type memberType = type.getMemberType(name);
            String value = toNativeExpression(c, memberType);
            argumentList.add(new Pair<>(name, value));
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
        String interfaceName = this.transformer.toNativeType(type);
        String arguments = argumentList.stream().map(Pair::getRight).collect(Collectors.joining(", "));
        return this.transformer.constructor(this.transformer.itemDefinitionNativeClassName(interfaceName), arguments);
    }

    public Object makeValue(ValueType valueType) {
        if (valueType.getValue() != null) {
            Object value = jaxbElementValue(valueType.getValue());
            String text = getTextContent(value);
            if (text == null) {
                return null;
            } else if (isNumber(value)) {
                return this.feelLib.number(text);
            } else if (isString(value)) {
                return text;
            } else if (isBoolean(value)) {
                if (StringUtils.isBlank(text)) {
                    return null;
                } else {
                    return Boolean.parseBoolean(text);
                }
            } else if (isDate(value)) {
                return this.feelLib.date(text);
            } else if (isTime(value)) {
                return this.feelLib.time(text);
            } else if (isDateTime(value)) {
                return this.feelLib.dateAndTime(text);
            } else if (isDurationTime(value)) {
                return this.feelLib.duration(text);
            } else {
                Object obj = valueType.getValue().getValue();
                if (obj instanceof Number) {
                    obj = this.feelLib.number(obj.toString());
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

    private List<?> makeList(ValueType valueType) {
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

    private List<Pair<String, String>> sortParameters(List<Pair<String, String>> parameters) {
        parameters.sort(Comparator.comparing(Pair::getLeft));
        return parameters;
    }

    private Object makeValue(ValueType valueType, Type type) {
        if (valueType.getValue() != null) {
            Object value = jaxbElementValue(valueType.getValue());
            String text = getTextContent(value);
            if (text == null) {
                return null;
            } else if (isNumber(value, type)) {
                return this.feelLib.number(text);
            } else if (isString(value, type)) {
                return text;
            } else if (isBoolean(value, type)) {
                if (StringUtils.isBlank(text)) {
                    return null;
                } else {
                    return Boolean.parseBoolean(text);
                }
            } else if (isDate(value, type)) {
                return this.feelLib.date(text);
            } else if (isTime(value, type)) {
                return this.feelLib.time(text);
            } else if (isDateTime(value, type)) {
                return this.feelLib.dateAndTime(text);
            } else if (isDurationTime(value, type)) {
                return this.feelLib.duration(text);
            } else {
                Object obj = valueType.getValue().getValue();
                if (obj instanceof Number) {
                    obj = this.feelLib.number(obj.toString());
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

    private List<?> makeList(ValueType valueType, ListType listType) {
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
            return this.feelLib.string(value);
        } else if (value instanceof Boolean) {
            return value.toString();
        } else if (value instanceof XMLGregorianCalendar) {
            return this.feelLib.string(value);
        } else if (value instanceof Duration) {
            return this.feelLib.string(value);
        } else if (value instanceof org.w3c.dom.Element) {
            return ((org.w3c.dom.Element) value).getTextContent();
        } else {
            return null;
        }
    }

    private Object jaxbElementValue(Object value) {
        if (value instanceof JAXBElement) {
            if (((JAXBElement<?>) value).isNil()) {
                return null;
            } else {
                value = ((JAXBElement<?>) value).getValue();
            }
        }
        return value;
    }

    //
    // Proto section
    //
    public boolean isGenerateProto() {
        return this.transformer.isGenerateProto();
    }

    public String qualifiedRequestMessageName(ResultNodeInfo info) {
        TDecision decision = (TDecision) info.getReference().getElement();
        return transformer.getProtoFactory().qualifiedRequestMessageName(decision);
    }

    public String requestVariableName(ResultNodeInfo info) {
        TDRGElement element = info.getReference().getElement();
        return transformer.getProtoFactory().requestVariableName(element);
    }

    public String builderVariableName(ResultNodeInfo info) {
        TDRGElement element = info.getReference().getElement();
        return transformer.namedElementVariableName(element) + "Builder_";
    }

    public List<Pair<String, Type>> drgElementTypeSignature(ResultNodeInfo info) {
        TDRGElement element = info.getReference().getElement();
        return this.transformer.drgElementTypeSignature(element, this.transformer::nativeName);
    }

    public String protoSetter(Pair<String, Type> parameter) {
        return this.transformer.getProtoFactory().protoSetter(parameter.getLeft(), parameter.getRight());
    }

    public String drgElementArgumentListExtraCacheProto(ResultNodeInfo info) {
        TDRGElement element = info.getReference().getElement();
        return this.transformer.drgElementArgumentListExtraCacheProto(element);
    }

    public String toNativeExpressionProto(Pair<String, Type> pair) {
        String inputName = pair.getLeft();
        Type type = pair.getRight();
        return this.transformer.getNativeFactory().convertValueToProtoNativeType(inputName, type, false);
    }

    public String toNativeTypeProto(Type type) {
        return this.transformer.getProtoFactory().toNativeProtoType(type);
    }

    public boolean isProtoReference(Type type) {
        return this.transformer.isProtoReference(type);
    }

    public String protoGetter(ResultNodeInfo info) {
        TDRGElement element = info.getReference().getElement();
        Type type = this.transformer.drgElementOutputFEELType(element);
        String name = this.transformer.namedElementVariableName(element);
        return this.transformer.getProtoFactory().protoGetter(name, type);
    }
}
