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
import com.gs.dmn.ImportPath;
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.EvaluationContext;
import com.gs.dmn.runtime.interpreter.Result;
import com.gs.dmn.tck.ast.*;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TCKUtil.class);

    public static String getModelName(String fileName) {
        // Remove extension
        int index = fileName.lastIndexOf(".");
        return index == -1 ? fileName : fileName.substring(0, index);
    }

    private final DMNModelRepository dmnModelRepository;

    private final BasicDMNToNativeTransformer<Type, DMNContext> transformer;
    private final NativeTypeFactory typeFactory;
    private final TCKValueInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> tckValueInterpreter;
    private final TCKValueTranslator<NUMBER, DATE, TIME, DATE_TIME, DURATION> tckValueTranslator;

    public TCKUtil(BasicDMNToNativeTransformer<Type, DMNContext> transformer, FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib) {
        this.transformer = transformer;
        this.dmnModelRepository = transformer.getDMNModelRepository();
        this.typeFactory = transformer.getNativeTypeFactory();
        this.tckValueInterpreter = new TCKValueInterpreter<>(transformer, feelLib);
        this.tckValueTranslator = transformer.isMockTesting() ? new MockTCKValueTranslator<>(transformer, feelLib) : new TCKValueTranslator<>(transformer, feelLib);
    }

    public BasicDMNToNativeTransformer<Type, DMNContext> getTransformer() {
        return this.transformer;
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
            return new InputNodeInfo(testCases.getModelName(), inputNode.getName(), NodeInfo.nodeTypeFrom(reference), reference, inputNode);
        } else {
            Pair<DRGElementReference<? extends TDRGElement>, ValueType> pair = extractInfoFromValue(definitions, inputNode);
            if (pair == null || pair.getLeft() == null) {
                throw new DMNRuntimeException(String.format("Cannot find DRG element for InputNode '%s' for TestCase '%s' in TestCases '%s'", inputNode.getName(), testCase.getId(), testCases.getModelName()));
            }
            return new InputNodeInfo(testCases.getModelName(), inputNode.getName(), NodeInfo.nodeTypeFrom(pair.getLeft()), pair.getLeft(), pair.getRight());
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
                if (this.dmnModelRepository.isDMNImport(imp)) {
                    String namespace = imp.getNamespace();
                    TDefinitions child = this.dmnModelRepository.findModelByNamespace(namespace);
                    DRGElementReference<? extends TDRGElement> result = extractInfoFromModel(child, elementNamespace, elementName, new ImportPath(importPath, imp.getName()));
                    if (result != null) {
                        return result;
                    }
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
            if (this.dmnModelRepository.isDMNImport(imp)) {
                String namespace = imp.getNamespace();
                TDefinitions child = this.dmnModelRepository.findModelByNamespace(namespace);
                DRGElementReference<? extends TDRGElement> result = extractInfoFromModel(child, elementName, new ImportPath(importPath, imp.getName()));
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public Pair<DRGElementReference<? extends TDRGElement>, ValueType> extractInfoFromValue(TDefinitions definitions, InputNode node) {
        // Navigate the import paths if needed
        String name = node.getName();
        ImportPath path = new ImportPath();
        TImport import_ = this.dmnModelRepository.findImport(definitions, name);
        ValueType value = node;
        while (import_ != null) {
            path.addPathElement(name);
            definitions = this.dmnModelRepository.findModelByNamespace(import_.getNamespace());
            name = null;
            if (value.getComponent() != null && value.getComponent().size() == 1) {
                Component component = value.getComponent().get(0);
                name = component.getName();
                value = component;
            }
            import_ = this.dmnModelRepository.findImport(definitions, name);
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

    public List<List<String>> missingArguments(TestCases testCases, TestCase testCase, ResultNode resultNode) {
        // Calculate provided inputs
        List<String> inputNames = testCase.getInputNode().stream()
                .map(in -> this.extractInputNodeInfo(testCases, testCase, in))
                .map(this::inputDataVariableName)
                .collect(Collectors.toList());
        // Calculate missing inputs
        ResultNodeInfo resultNodeInfo = extractResultNodeInfo(testCases, testCase, resultNode);
        List<Pair<String, Type>> parameters = this.transformer.drgElementTypeSignature(resultNodeInfo.getReference(), transformer::nativeName);
        List<Pair<String, Type>> missingParameters = parameters.stream().filter(pair -> !inputNames.contains(pair.getLeft())).collect(Collectors.toList());
        List<Pair<String, String>> missingArgs = missingParameters.stream()
                .map(p -> new Pair<>(transformer.getNativeFactory().nullableParameterType(transformer.toNativeType(p.getRight())), p.getLeft()))
                .collect(Collectors.toList());

        List<List<String>> result = new ArrayList<>();
        for (int i=0; i<missingParameters.size(); i++) {
            Type type = parameters.get(i).getRight();
            String defaultValue = isMockTesting() ? this.transformer.getDefaultValue(type, null) : this.transformer.getNativeFactory().nullLiteral();
            List<String> triplet = new ArrayList<>();
            triplet.add(missingArgs.get(i).getLeft());
            triplet.add(missingArgs.get(i).getRight());
            triplet.add(defaultValue);
            result.add(triplet);
        }
        return result;
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
        return this.tckValueTranslator.toNativeExpression(info.getValue(), inputType, info.getReference().getElement());
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
            String varTypeRef = QualifiedName.toName(((TInputData) element).getVariable().getTypeRef());
            typeRef = QualifiedName.toQualifiedName(model, varTypeRef);
        } else if (element instanceof TDecision) {
            String varTypeRef = QualifiedName.toName(((TDecision) element).getVariable().getTypeRef());
            typeRef = QualifiedName.toQualifiedName(model, varTypeRef);
        } else {
            throw new UnsupportedOperationException(String.format("Cannot resolve FEEL type for node '%s'. '%s' not supported", node.getNodeName(), element.getClass().getSimpleName()));
        }
        return typeRef;
    }

    //
    // Translator - Result nodes
    //
    public List<String> findComplexInputDatas(TestCases testCases) {
        List<TDRGElement> drgElementList = findDRGElementsUnderTest(testCases);
        Set<String> set = new LinkedHashSet<>();
        for (TDRGElement drgElement: drgElementList) {
            List<String> moduleNames = this.transformer.drgElementComplexInputClassNames(drgElement);
            set.addAll(moduleNames);
        }
        ArrayList<String> list = new ArrayList<>(set);
        Collections.sort(list);
        return list;
    }

    public List<TDRGElement> findDRGElementsUnderTest(TestCases testCases) {
        Set<TDRGElement> elements = new LinkedHashSet<>();
        for (TestCase testCase : testCases.getTestCase()) {
            for (ResultNode resultNode : testCase.getResultNode()) {
                TDRGElement element = findDRGElement(testCases, testCase, resultNode);
                if (element != null) {
                    elements.add(element);
                }
            }
        }
        return new ArrayList<>(elements);
    }

    public ResultNodeInfo extractResultNodeInfo(TestCases testCases, TestCase testCase, ResultNode resultNode) {
        TDRGElement element = findDRGElement(testCases, testCase, resultNode);
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        return new ResultNodeInfo(testCases.getModelName(), testCase.getType().value(), resultNode.getName(), reference, resultNode.getExpected());
    }

    public String toNativeExpression(ResultNodeInfo info) {
        Type outputType = toFEELType(info);
        if (outputType instanceof ItemDefinitionType) {
            String javaExpression = this.tckValueTranslator.toNativeExpression(info.getExpectedValue(), ((ItemDefinitionType) outputType).toContextType(), info.getReference().getElement());
            return this.transformer.getNativeFactory().convertToItemDefinitionType(javaExpression, (ItemDefinitionType) outputType);
        } else {
            return this.tckValueTranslator.toNativeExpression(info.getExpectedValue(), outputType, info.getReference().getElement());
        }
    }

    public String toNativeExpressionProto(ResultNodeInfo info) {
        Type resultType = toFEELType(info);
        ValueType expectedValue = info.getExpectedValue();
        String value = this.tckValueTranslator.toNativeExpression(expectedValue, resultType, info.getReference().getElement());
        if (this.transformer.isDateTimeType(resultType) || this.transformer.isComplexType(resultType)) {
            return transformer.getNativeFactory().convertValueToProtoNativeType(value, resultType, false);
        } else {
            return value;
        }
    }

    public String qualifiedName(ResultNodeInfo info) {
        TDRGElement element = info.getReference().getElement();
        if (element == null) {
            throw new DMNRuntimeException(String.format("Cannot find DRG Element for node '%s'", info.getNodeName()));
        }
        if (info.isDecision()) {
            String pkg = this.transformer.nativeModelPackageName(info.getRootModelName());
            String cls = this.transformer.drgElementClassName(element);
            return this.transformer.qualifiedName(pkg, cls);
        } else if (info.isBKM() || info.isDS()) {
            return this.transformer.singletonInvocableInstance((TInvocable) element);
        } else {
            throw new DMNRuntimeException(String.format("Not supported '%s' in '%s'", info.getNodeType(), info.getNodeName()));
        }
    }

    public String drgElementArgumentList(ResultNodeInfo info) {
        if (info.isDecision()) {
            TDecision decision = (TDecision) info.getReference().getElement();
            return this.transformer.drgElementArgumentList(this.dmnModelRepository.makeDRGElementReference(decision));
        } else if (info.isBKM()) {
            TBusinessKnowledgeModel bkm = (TBusinessKnowledgeModel) info.getReference().getElement();
            return this.transformer.drgElementArgumentList(this.dmnModelRepository.makeDRGElementReference(bkm));
        } else if (info.isDS()) {
            TDecisionService ds = (TDecisionService) info.getReference().getElement();
            return this.transformer.drgElementArgumentList(this.dmnModelRepository.makeDRGElementReference(ds));
        } else {
            throw new DMNRuntimeException(String.format("Not supported node type '%s'", info.getNodeType()));
        }
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
    public String testCaseId(TestCase testCase) {
        String id = testCase.getId();
        return this.transformer.nativeFriendlyName(id);
    }

    public String assertClassName() {
        return this.transformer.assertClassName();
    }

    public String executionContextClassName() {
        return this.transformer.executionContextClassName();
    }

    public String executionContextVariableName() {
        return this.transformer.executionContextVariableName();
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
        DRGElementReference<? extends TDRGElement> reference = info.getReference();
        TDRGElement element = reference.getElement();
        if (element == null) {
            throw new DMNRuntimeException(String.format("Cannot find DRG elements for node '%s'", info.getNodeName()));
        } else if (element instanceof TDecision) {
            Map<QualifiedName, Object> informationRequirements = makeInputs(testCases, testCase);
            return interpreter.evaluateDecision(reference.getNamespace(), reference.getElementName(), EvaluationContext.makeDecisionEvaluationContext(element, informationRequirements));
        } else if (element instanceof TInvocable) {
            Map<QualifiedName, Object> informationRequirements = makeInputs(testCases, testCase);
            List<Object> arguments = makeArgs(element, informationRequirements);
            EvaluationContext functionContext = EvaluationContext.makeFunctionInvocationContext(element, arguments, this.transformer.makeGlobalContext(element));
            return interpreter.evaluateInvocable(reference.getNamespace(), reference.getElementName(), functionContext);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", element.getClass().getSimpleName()));
        }
    }

    public Object expectedValue(TestCases testCases, TestCase testCase, ResultNode resultNode) {
        ResultNodeInfo info = extractResultNodeInfo(testCases, testCase, resultNode);
        return this.tckValueInterpreter.makeValue(info.getExpectedValue());
    }

    private Map<QualifiedName, Object> makeInputs(TestCases testCases, TestCase testCase) {
        Map<QualifiedName, Object> inputs = new LinkedHashMap<>();
        List<InputNode> inputNode = testCase.getInputNode();
        for (int i = 0; i < inputNode.size(); i++) {
            InputNode input = inputNode.get(i);
            String simpleName = input.getName();
            try {
                if (this.transformer.isSingletonInputData()) {
                    InputNodeInfo info = extractInputNodeInfo(testCases, testCase, input);
                    Object value = this.tckValueInterpreter.makeValue(info.getValue());
                    DRGElementReference<? extends TDRGElement> reference = info.getReference();
                    inputs.put(QualifiedName.toQualifiedName(reference.getNamespace(), reference.getElementName()), value);
                } else {
                    Object value = this.tckValueInterpreter.makeValue(input);
                    inputs.put(QualifiedName.toQualifiedName((String) null, simpleName), value);
                }
            } catch (Exception e) {
                LOGGER.error("Cannot extract inputs ", e);
                throw new DMNRuntimeException(String.format("Cannot process input node '%s' for TestCase %d for DM '%s'", simpleName, i, testCase.getName()), e);
            }
        }
        return inputs;
    }

    private List<Object> makeArgs(TDRGElement drgElement, Map<QualifiedName, Object> inputs) {
        List<Object> args = new ArrayList<>();
        if (drgElement instanceof TInvocable) {
            // Preserve the order in the call
            List<FormalParameter<Type>> formalParameters = this.transformer.invocableFEELParameters(drgElement);
            for (FormalParameter<Type> parameter: formalParameters) {
                for (Map.Entry<QualifiedName, Object> entry : inputs.entrySet()) {
                    if (entry.getKey().getLocalPart().equals(parameter.getName())) {
                        args.add(entry.getValue());
                    }
                }
            }
        }
        return args;
    }

    //
    // Model - lookup methods
    //
    private TDefinitions getRootModel(TestCases testCases) {
        // Find DM by namespace or name
        String namespace = getNamespace(testCases);
        TDefinitions definitions = null;
        if (StringUtils.isBlank(namespace)) {
            // Lookup by name
            String modelName = getModelName(testCases);
            definitions = this.dmnModelRepository.findModelByName(modelName);
        } else {
            // Lookup by namespace
            definitions = this.dmnModelRepository.findModelByNamespace(namespace);
        }
        if (definitions == null) {
            throw new DMNRuntimeException(String.format("Cannot find DM '%s' for TestCases", testCases.getModelName()));
        } else {
            return definitions;
        }
    }

    private TDRGElement findDRGElement(TestCases testCases, TestCase testCase, ResultNode node) {
        String namespace = getNamespace(testCases, testCase, node);
        String name = drgElementName(testCases, testCase, node);
        if (!StringUtils.isBlank(namespace)) {
            return this.dmnModelRepository.findDRGElementByName(namespace, name);
        } else {
            TDefinitions rootModel = getRootModel(testCases);
            return this.dmnModelRepository.findDRGElementByName(rootModel, name);
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

    private String getModelName(TestCases testCases) {
        String fileName = testCases.getModelName();
        return getModelName(fileName);
    }

    private String getNamespace(TestCases testCases) {
        return testCases.getNamespace();
    }

    private String getNamespace(TestCase testCase) {
        return testCase.getNamespace();
    }

    private String getNamespace(TestCases testCases, TestCase testCase, InputNode node) {
        String namespace = getNamespace(node);
        if (StringUtils.isBlank(namespace)) {
            namespace = getNamespace(testCase);
        }
        if (StringUtils.isBlank(namespace)) {
            namespace = getNamespace(testCases);
        }
        return namespace;
    }

    private String getNamespace(TestCases testCases, TestCase testCase, ResultNode node) {
        String namespace = getNamespace(node);
        if (StringUtils.isBlank(namespace)) {
            namespace = getNamespace(testCase);
        }
        if (StringUtils.isBlank(namespace)) {
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
    // Singleton section
    //
    public boolean isSingletonDecision() {
        return this.transformer.isSingletonDecision();
    }

    public String singletonDecisionInstance(String decisionQName) {
        return this.transformer.singletonDecisionInstance(decisionQName);
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

    public String protoSetter(Pair<String, Type> parameter, String args) {
        return this.transformer.getProtoFactory().protoSetter(parameter.getLeft(), parameter.getRight(), args);
    }

    public String drgElementArgumentListProto(ResultNodeInfo info) {
        TDRGElement element = info.getReference().getElement();
        return this.transformer.drgElementArgumentListProto(element);
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

    //
    // Test Mocking section
    //
    public boolean isMockTesting() {
        return this.transformer.isMockTesting();
    }

    public static String mockContextVariable() {
        return "mockContext_";
    }

    public String getNativeNumberType() {
        return this.transformer.getNativeNumberType();
    }

    public String getNativeDateType() {
        return this.transformer.getNativeDateType();
    }

    public String getNativeTimeType() {
        return this.transformer.getNativeTimeType();
    }

    public String getNativeDateAndTimeType() {
        return this.transformer.getNativeDateAndTimeType();
    }

    public String getNativeDurationType() {
        return this.transformer.getNativeDurationType();
    }

    public String getDefaultIntegerValue() {
        return this.transformer.getDefaultIntegerValue();
    }

    public String getDefaultDecimalValue() {
        return this.transformer.getDefaultDecimalValue();
    }

    public String getDefaultStringValue() {
        return this.transformer.getDefaultStringValue();
    }

    public String getDefaultBooleanValue() {
        return this.transformer.getDefaultBooleanValue();
    }

    public String getDefaultDateValue() {
        return this.transformer.getDefaultDateValue();
    }

    public String getDefaultTimeValue() {
        return this.transformer.getDefaultTimeValue();
    }

    public String getDefaultDateAndTimeValue() {
        return this.transformer.getDefaultDateAndTimeValue();
    }

    public String getDefaultDurationValue() {
        return this.transformer.getDefaultDurationValue();
    }
}
