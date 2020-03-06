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
import org.omg.spec.dmn._20180521.model.*;
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

    private static final boolean IGNORE_ELEMENT_TYPE = false;
    private final DMNModelRepository dmnModelRepository;

    private final BasicDMN2JavaTransformer dmnTransformer;
    private final StandardFEELLib feelLib;

    public TCKUtil(BasicDMN2JavaTransformer dmnTransformer, StandardFEELLib feelLib) {
        this.dmnTransformer = dmnTransformer;
        this.feelLib = feelLib;
        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
    }

    //
    // Translator - Input nodes
    //
    public InputNodeInfo extractInputNodeInfo(TestCases testCases, TestCase testCase, InputNode inputNode) {
        TDefinitions definitions = getRootModel(testCases);
        if (this.dmnTransformer.isSingletonInputData()) {
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
            for (TDRGElement drg: this.dmnModelRepository.drgElements(definitions)) {
                if (drg.getName().equals(elementName)) {
                    String namespace = this.dmnModelRepository.getNamespace(drg);
                    return new DRGElementReference<>(namespace, drg, importPath);
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
        for (TDRGElement drg: this.dmnModelRepository.drgElements(definitions)) {
            if (drg.getName().equals(elementName)) {
                String namespace = this.dmnModelRepository.getNamespace(drg);
                return new DRGElementReference<>(namespace, drg, importPath);
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
            definitions = dmnModelRepository.getModel(import_.getNamespace());
            name = null;
            if (value.getComponent() != null && value.getComponent().size() == 1) {
                Component component = value.getComponent().get(0);
                name = component.getName();
                value = component;
            }
            import_ = getImport(definitions, name);
        }

        // Find DRG element and value
        List<TDRGElement> drgElements = dmnModelRepository.drgElements(definitions);
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
        String namespace = this.dmnModelRepository.getNamespace(element);
        return new Pair<>(new DRGElementReference<>(namespace, element, path), value);
    }

    private TImport getImport(TDefinitions definitions, String name) {
        for (TImport imp: definitions.getImport()) {
            if (imp.getName().equals(name)) {
                return imp;
            }
        }
        return null;
    }

    public String toJavaType(InputNodeInfo info) {
        Type feelType = toFEELType(info);
        return this.dmnTransformer.toJavaType(feelType);
    }

    public String inputDataVariableName(InputNodeInfo info) {
        TDRGElement element = info.getReference().getElement();
        if (element == null) {
            throw new DMNRuntimeException(String.format("Cannot find element '%s'", info.getNodeName()));
        } else if (element instanceof TInputData) {
            return this.dmnTransformer.inputDataVariableName(info.getReference());
        } else if (element instanceof TDecision) {
            return this.dmnTransformer.drgElementVariableName(info.getReference());
        } else {
            throw new UnsupportedOperationException(String.format("'%s' not supported", element.getClass().getSimpleName()));
        }
    }

    public String toJavaExpression(InputNodeInfo info) {
        Type inputType = toFEELType(info);
        return toJavaExpression(info.getValue(), inputType);
    }

    private Type toFEELType(InputNodeInfo info) {
        try {
            QualifiedName typeRef = getTypeRef(info);
            return this.dmnTransformer.toFEELType(typeRef);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot resolve FEEL type for node '%s'", info.getNodeName()));
        }
    }

    private QualifiedName getTypeRef(InputNodeInfo node) {
        TDRGElement element = node.getReference().getElement();
        QualifiedName typeRef;
        if (element == null) {
            throw new DMNRuntimeException(String.format("Cannot find element '%s'.", node.getNodeName()));
        } else if (element instanceof TInputData) {
            String varTypeRef = ((TInputData) element).getVariable().getTypeRef();
            typeRef = QualifiedName.toQualifiedName(varTypeRef);
        } else if (element instanceof TDecision) {
            String varTypeRef = ((TDecision) element).getVariable().getTypeRef();
            typeRef = QualifiedName.toQualifiedName(varTypeRef);
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
        String namespace = this.dmnModelRepository.getNamespace(element);
        DRGElementReference<TDRGElement> reference = new DRGElementReference<>(namespace, element);
        return new ResultNodeInfo(testCases.getModelName(), resultNode.getName(), reference, resultNode.getExpected());
    }

    public String toJavaExpression(ResultNodeInfo info) {
        Type outputType = toFEELType(info);
        return toJavaExpression(info.getExpectedValue(), outputType);
    }

    public String qualifiedName(ResultNodeInfo info) {
        String pkg = this.dmnTransformer.javaModelPackageName(info.getRootModelName());
        String cls = this.dmnTransformer.drgElementClassName(info.getReference().getElement());
        return this.dmnTransformer.qualifiedName(pkg, cls);
    }

    public String drgElementArgumentsExtraCache(String arguments) {
        return this.dmnTransformer.drgElementArgumentsExtraCache(arguments);
    }

    public String drgElementArgumentsExtra(String arguments) {
        return this.dmnTransformer.drgElementArgumentsExtra(arguments);
    }

    public String drgElementArgumentList(ResultNodeInfo info) {
        TDecision decision = (TDecision) info.getReference().getElement();
        String namespace = this.dmnModelRepository.getNamespace(decision);
        return this.dmnTransformer.drgElementArgumentList(new DRGElementReference<TDRGElement>(namespace, decision));
    }

    private Type toFEELType(ResultNodeInfo resultNode) {
        try {
            QualifiedName typeRef = getTypeRef(resultNode);
            return this.dmnTransformer.toFEELType(typeRef);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot resolve FEEL type for node '%s'", resultNode.getNodeName()));
        }
    }

    private QualifiedName getTypeRef(ResultNodeInfo node) {
        TDRGElement element = node.getReference().getElement();
        QualifiedName typeRef;
        if (element == null) {
            throw new DMNRuntimeException(String.format("Cannot find element '%s'.", node.getNodeName()));
        } else if (element instanceof TDecision) {
            typeRef = QualifiedName.toQualifiedName(((TDecision) element).getVariable().getTypeRef());
        } else {
            throw new UnsupportedOperationException(String.format("Cannot resolve FEEL type for node '%s'. '%s' not supported", node.getNodeName(), element.getClass().getSimpleName()));
        }
        return typeRef;
    }

    //
    // Translator - helper delegated methods
    //
    public String assertClassName() {
        return this.dmnTransformer.assertClassName();
    }

    public String annotationSetClassName() {
        return this.dmnTransformer.annotationSetClassName();
    }

    public String annotationSetVariableName() {
        return this.dmnTransformer.annotationSetVariableName();
    }

    public String eventListenerClassName() {
        return this.dmnTransformer.eventListenerClassName();
    }

    public String defaultEventListenerClassName() {
        return this.dmnTransformer.defaultEventListenerClassName();
    }

    public String eventListenerVariableName() {
        return this.dmnTransformer.eventListenerVariableName();
    }

    public String externalExecutorClassName() {
        return this.dmnTransformer.externalExecutorClassName();
    }

    public String externalExecutorVariableName() {
        return this.dmnTransformer.externalExecutorVariableName();
    }

    public String defaultExternalExecutorClassName() {
        return this.dmnTransformer.defaultExternalExecutorClassName();
    }

    public String cacheInterfaceName() {
        return this.dmnTransformer.cacheInterfaceName();
    }

    public String cacheVariableName() {
        return this.dmnTransformer.cacheVariableName();
    }

    public String defaultCacheClassName() {
        return this.dmnTransformer.defaultCacheClassName();
    }

    public boolean isCaching() {
        return this.dmnTransformer.isCaching();
    }

    public boolean isCached(InputNodeInfo info) {
        return this.dmnTransformer.isCached(info.getReference().getElementName());
    }

    //
    // Interpreter
    //
    public Result evaluate(DMNInterpreter interpreter, TestCases testCases, TestCase testCase, ResultNode resultNode) {
        TDRGElement drgElement = findDRGElement(testCases, testCase, resultNode);
        ImportPath importPath = null;
        RuntimeEnvironment runtimeEnvironment = makeEnvironment(testCases, testCase);
        List<Object> args = makeArgs(drgElement, testCase);
        return interpreter.evaluate(importPath, drgElement, args, runtimeEnvironment);
    }

    public Object expectedValue(TestCases testCases, TestCase testCase, ResultNode resultNode) {
        if (IGNORE_ELEMENT_TYPE) {
            return makeValue(resultNode.getExpected());
        } else {
            TDRGElement drgElement = findDRGElement(testCases, testCase, resultNode);
            if (drgElement == null) {
                throw new DMNRuntimeException(String.format("Cannot find DRG element '%s'", resultNode.getName()));
            }
            Environment environment = this.dmnTransformer.makeEnvironment(drgElement);
            Type elementType = this.dmnTransformer.drgElementOutputFEELType(drgElement, environment);
            return makeValue(resultNode.getExpected(), elementType);
        }
    }

    private RuntimeEnvironment makeEnvironment(TestCases testCases, TestCase testCase) {
        RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentFactory.instance().makeEnvironment();
        List<InputNode> inputNode = testCase.getInputNode();
        for (int i = 0; i < inputNode.size(); i++) {
            InputNode input = inputNode.get(i);
            try {
                Object value = makeInputValue(testCases, testCase, input);
                String name = input.getName();
                runtimeEnvironment.bind(name, value);
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
            List<FormalParameter> formalParameters = this.dmnTransformer.invFEELParameters(drgElement);
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

    private TDRGElement findDRGElement(TestCases testCases, TestCase testCase, InputNode node) {
        try {
            String namespace = getNamespace(testCases, testCase, node);
            String name = node.getName();
            if (namespace != null) {
                return this.dmnModelRepository.findDRGElementByName(namespace, name);
            } else {
                return this.dmnModelRepository.findDRGElementByName(name);
            }
        } catch (Exception e) {
            return null;
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
        return String.format("asList(%s)", String.join(", ", javaList));
    }

    private String toJavaExpression(List<Component> components, ItemDefinitionType type) {
        List<Pair<String, String>> argumentList = new ArrayList<>();
        Set<String> members = type.getMembers();
        Set<String> present = new LinkedHashSet<>();
        for (Component c : components) {
            String name = c.getName();
            Type memberType = type.getMemberType(name);
            String value = toJavaExpression(c, memberType);
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
        String interfaceName = this.dmnTransformer.toJavaType(type);
        String arguments = argumentList.stream().map(Pair::getRight).collect(Collectors.joining(", "));
        return this.dmnTransformer.constructor(this.dmnTransformer.itemDefinitionJavaClassName(interfaceName), arguments);
    }

    private Object makeInputValue(TestCases testCases, TestCase testCase, InputNode inputNode) {
        if (IGNORE_ELEMENT_TYPE) {
            return makeValue(inputNode);
        } else {
            TDRGElement drgElement = findDRGElement(testCases, testCase, inputNode);
            if (drgElement instanceof TInputData) {
                Type type = this.dmnTransformer.drgElementOutputFEELType(drgElement);
                return makeValue(inputNode, type);
            } else if (drgElement instanceof TDecision) {
                Type type = this.dmnTransformer.drgElementOutputFEELType(drgElement);
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
            if (((JAXBElement<?>) value).isNil()) {
                return null;
            } else {
                value = ((JAXBElement<?>) value).getValue();
            }
        }
        return value;
    }
}
