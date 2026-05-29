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
import com.gs.dmn.error.SemanticErrorException;
import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.runtime.interpreter.EvaluationContext;
import com.gs.dmn.runtime.interpreter.Result;
import com.gs.dmn.tck.ast.InputNode;
import com.gs.dmn.tck.ast.ResultNode;
import com.gs.dmn.tck.ast.TestCase;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.error.TestLocation;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.FEELParameter;
import com.gs.dmn.transformation.basic.NativeParameter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class TCKUtil<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    public static String getModelName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            throw new SemanticErrorException("Missing model name");
        }

        // Remove extension
        int index = fileName.lastIndexOf(".");
        return index == -1 ? fileName : fileName.substring(0, index);
    }

    public static String getTestCasesName(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            throw new SemanticErrorException("Missing test cases name");
        }

        // Remove extension
        int index = fileName.lastIndexOf(".");
        return index == -1 ? fileName : fileName.substring(0, index);
    }

    public static String getNamespace(TestCases testCases, TestCase testCase, InputNode node) {
        String namespace = getNamespace(node);
        if (StringUtils.isBlank(namespace)) {
            namespace = getNamespace(testCase);
        }
        if (StringUtils.isBlank(namespace)) {
            namespace = getNamespace(testCases);
        }
        return namespace;
    }

    public static String getNamespace(TestCases testCases, TestCase testCase, ResultNode node) {
        String namespace = getNamespace(node);
        if (StringUtils.isBlank(namespace)) {
            namespace = getNamespace(testCase);
        }
        if (StringUtils.isBlank(namespace)) {
            namespace = getNamespace(testCases);
        }
        return namespace;
    }

    private static String getNamespace(TestCases testCases) {
        return testCases == null ? null : testCases.getNamespace();
    }

    private static String getNamespace(TestCase testCase) {
        return testCase == null ? null : testCase.getNamespace();
    }

    private static String getNamespace(InputNode node) {
        return node == null ? null : node.getNamespace();
    }

    private static String getNamespace(ResultNode node) {
        return node == null ? null : node.getNamespace();
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
    public List<InputNodeInfo> extractInputNodeInfoList(TestCases testCases, TestCase testCase) {
        List<InputNodeInfo> inputNodeInfos = new ArrayList<>();
        for (InputNode inputNode : testCase.getInputNode()) {
            InputNodeInfo inputNodeInfo = extractInputNodeInfo(testCases, testCase, inputNode);
            inputNodeInfos.add(inputNodeInfo);
        }
        return inputNodeInfos;
    }

    public InputNodeInfo extractInputNodeInfo(TestCases testCases, TestCase testCase, InputNode inputNode) {
        TDefinitions definitions = getRootModel(testCases);
        String namespace = getNamespace(testCases, testCase, inputNode);
        DRGElementReference<? extends TDRGElement> reference = findDRGElement(definitions, namespace, inputNode.getName());
        if (reference == null) {
            TestLocation location = new TestLocation(testCases.getTestCasesName(), testCase.getId(), testCases.getModelName());
            throw new SemanticErrorException(String.format("%s: Cannot find DRG element for InputNode '%s' in  namespace '%s'", location, inputNode.getName(), namespace));
        }
        return new InputNodeInfo(inputNode.getName(), reference, inputNode);
    }

    public boolean hasInputNodeInfo(DRGElementReference<TDecision> reference, List<InputNodeInfo> inputNodeInfoList) {
        return findInputNodeInfo(reference, inputNodeInfoList) != null;
    }

    public InputNodeInfo findInputNodeInfo(DRGElementReference<TDecision> reference, List<InputNodeInfo> inputNodeInfoList) {
        for (InputNodeInfo inputNodeInfo : inputNodeInfoList) {
            if (inputNodeInfo.isDecision()) {
                if (sameReference(reference, inputNodeInfo.getReference())) {
                    return inputNodeInfo;
                }
            }
        }
        return null;
    }

    private boolean sameReference(DRGElementReference<TDecision> ref1, DRGElementReference<? extends TDRGElement> ref2) {
        if (ref1 == null || ref2 == null) {
            return false;
        }
        return ref1.getElementName().equals(ref2.getElementName())
                && ref1.getNamespace().equals(ref2.getNamespace());
    }

    public List<List<String>> missingArguments(List<InputNodeInfo> inputNodeInfos, ResultNodeInfo resultNodeInfo) {
        // Calculate provided inputs
        List<String> inputNames = inputNodeInfos.stream()
                .map(this::inputDataVariableName)
                .toList();
        // Calculate missing arguments
        List<FEELParameter> parameters = this.transformer.drgElementTypeSignature(resultNodeInfo.getReference());
        List<FEELParameter> missingParameters = parameters.stream().filter(pair -> !inputNames.contains(pair.getNativeName())).toList();
        List<NativeParameter> missingArgs = missingParameters.stream()
                .map(p -> new NativeParameter(transformer.getNativeFactory().nullableParameterType(transformer.toNativeType(p.getType())), p.getNativeName()))
                .toList();

        List<List<String>> result = new ArrayList<>();
        for (int i=0; i<missingParameters.size(); i++) {
            Type type = parameters.get(i).getType();
            String defaultValue = isMockTesting() ? this.transformer.getDefaultValue(type, null) : this.transformer.getNativeFactory().nullLiteral();
            List<String> triplet = new ArrayList<>();
            triplet.add(missingArgs.get(i).getType());
            triplet.add(missingArgs.get(i).getName());
            triplet.add(defaultValue);
            result.add(triplet);
        }
        return result;
    }

    public boolean hasMockedDirectSubDecisions(ResultNodeInfo resultInfo, List<InputNodeInfo> inputNodeInfoList) {
        List<DRGElementReference<TDecision>> references = directSubDecisions(resultInfo);
        if (references.isEmpty()) {
            return false;
        } else {
            return references.stream().anyMatch(r -> hasInputNodeInfo(r, inputNodeInfoList));
        }
    }

    public List<DRGElementReference<TDecision>> directSubDecisions(ResultNodeInfo resultInfo) {
        return this.transformer.getDMNModelRepository().directSubDecisions(resultInfo.getReference().getElement());
    }

    public String nativeVariableName(DRGElementReference<TDecision> reference) {
        return this.transformer.nativeVariableName(reference);
    }

    public String toNativeType(InputNodeInfo info) {
        Type feelType = toFEELType(info);
        return this.typeFactory.nullableType(this.transformer.toNativeType(feelType));
    }

    public String inputDataVariableName(InputNodeInfo info) {
        TDRGElement element = info.getReference().getElement();
        if (element == null) {
            throw new SemanticErrorException(String.format("Cannot find element '%s'", info.getNodeName()));
        } else {
            return this.transformer.nativeVariableName(info.getReference());
        }
    }

    public String displayName(InputNodeInfo info) {
        TDRGElement element = info.getReference().getElement();
        if (element == null) {
            throw new SemanticErrorException(String.format("Cannot find element '%s'", info.getNodeName()));
        } else {
            return this.transformer.displayName(info.getReference());
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
            throw new SemanticErrorException(String.format("Cannot resolve FEEL type for node '%s'", info.getNodeName()), e);
        }
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
                ResultNodeInfo resultNodeInfo = extractResultNodeInfo(testCases, testCase, resultNode);
                TDRGElement element = resultNodeInfo.getReference().getElement();
                if (element != null) {
                    elements.add(element);
                }
            }
        }
        return new ArrayList<>(elements);
    }

    public ResultNodeInfo extractResultNodeInfo(TestCases testCases, TestCase testCase, ResultNode node) {
        TDefinitions definitions = getRootModel(testCases);
        String namespace = getNamespace(testCases, testCase, node);
        if (StringUtils.isBlank(namespace)) {
            // Default namespace is the namespace of the root model
            namespace = definitions.getNamespace();
        }
        DRGElementReference<? extends TDRGElement> reference = findDRGElement(definitions, namespace, node.getName());
        if (reference == null) {
            TestLocation location = new TestLocation(testCases.getTestCasesName(), testCase.getId(), testCases.getModelName());
            throw new SemanticErrorException(String.format("%s: Cannot find DRG element for InputNode '%s' in  namespace '%s'", location, node.getName(), namespace));
        }
        return new ResultNodeInfo(node.getName(), reference, node.getExpected());
    }

    public String toNativeExpression(ResultNodeInfo info) {
        Type outputType = toFEELType(info);
        if (outputType instanceof ItemDefinitionType) {
            String nativeExpression = this.tckValueTranslator.toNativeExpression(info.getExpectedValue(), ((ItemDefinitionType) outputType).toContextType(), info.getReference().getElement());
            return this.transformer.convertToItemDefinitionType(nativeExpression, (ItemDefinitionType) outputType);
        } else {
            return this.tckValueTranslator.toNativeExpression(info.getExpectedValue(), outputType, info.getReference().getElement());
        }
    }

    public String qualifiedNativeName(NodeInfo info) {
        TDRGElement element = info.getReference().getElement();
        if (element == null) {
            throw new SemanticErrorException(String.format("Cannot find DRG Element for node '%s'", info.getNodeName()));
        }
        if (info.isDecision()) {
            String pkg = this.transformer.nativeModelPackageName(info.getModelName());
            String cls = this.transformer.drgElementClassName(element);
            return this.transformer.qualifiedNativeName(pkg, cls);
        } else if (info.isBKM() || info.isDS()) {
            return this.transformer.singletonInvocableInstance((TInvocable) element);
        } else {
            throw new SemanticErrorException(String.format("Not supported '%s' in '%s'", info.getNodeType(), info.getNodeName()));
        }
    }

    public String qualifiedNativeName(DRGElementReference<? extends TDRGElement> reference) {
        return this.transformer.qualifiedNativeName(reference);
    }

    // For apply() for child elements in java
    public String drgElementArgumentMockList(NodeInfo info) {
        if (info.isDecision() || info.isBKM() || info.isDS()) {
            int argSize = transformer.drgElementTypeSignature(info.getReference()).size() + 1;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < argSize; i++) {
                if (i > 0) {
                    builder.append(", ");
                }
                builder.append("org.mockito.ArgumentMatchers.any()");
            }
            return builder.toString();
        } else {
            throw new SemanticErrorException(String.format("Not supported node type '%s'", info.getNodeType()));
        }
    }

    // For apply() in kotlin and python
    public String drgElementArgumentList(NodeInfo info) {
        if (info.isDecision() || info.isBKM() || info.isDS()) {
            return this.transformer.drgElementArgumentList(info.getReference());
        } else {
            throw new SemanticErrorException(String.format("Not supported node type '%s'", info.getNodeType()));
        }
    }

    public String drgElementArgumentListApplyContext(NodeInfo info) {
        if (info.isDecision() || info.isBKM() || info.isDS()) {
            return String.format("%s, %s", this.inputVariableName(), this.executionContextVariableName());
        } else {
            throw new SemanticErrorException(String.format("Not supported node type '%s'", info.getNodeType()));
        }
    }

    private Type toFEELType(ResultNodeInfo resultNode) {
        try {
            TDRGElement element = resultNode.getReference().getElement();
            return this.transformer.drgElementOutputFEELType(element);
        } catch (Exception e) {
            throw new SemanticErrorException(String.format("Cannot resolve FEEL type for node '%s'", resultNode.getNodeName()), e);
        }
    }

    //
    // Repository helper methods
    //
    private DRGElementReference<? extends TDRGElement> findDRGElement(TDefinitions rootDefinitions, String elementNamespace, String elementName) {
        if (elementNamespace == null) {
            return findDRGElement(rootDefinitions, elementName, new ImportPath());
        } else {
            return findDRGElement(rootDefinitions, elementNamespace, elementName, new ImportPath());
        }
    }

    private DRGElementReference<? extends TDRGElement> findDRGElement(TDefinitions definitions, String elementNamespace, String elementName, ImportPath importPath) {
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
                    DRGElementReference<? extends TDRGElement> result = findDRGElement(child, elementNamespace, elementName, new ImportPath(importPath, imp.getName()));
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }

    private DRGElementReference<? extends TDRGElement> findDRGElement(TDefinitions definitions, String elementName, ImportPath importPath) {
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
                DRGElementReference<? extends TDRGElement> result = findDRGElement(child, elementName, new ImportPath(importPath, imp.getName()));
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
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

    public String contextClassName() {
        return this.transformer.contextClassName();
    }

    public String inputVariableName() {
        return this.transformer.inputVariableName();
    }

    public String executionContextClassName() {
        return this.transformer.executionContextClassName();
    }

    public String executionContextVariableName() {
        return this.transformer.executionContextVariableName();
    }

    public String executionContextBuilderClassName() {
        return this.transformer.executionContextBuilderClassName();
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

    public String cacheVariableName() {
        return this.transformer.cacheVariableName();
    }

    public String defaultConstructor(String className) {
        return this.transformer.defaultConstructor(className);
    }

    public String constructor(ResultNodeInfo resultInfo) {
        String nativeQName = qualifiedNativeName(resultInfo);
        String args = this.transformer.drgElementConstructorArguments(resultInfo.getReference().getElement());
        return this.transformer.constructor(nativeQName, args);
    }

    public String contextSetter(String name, String args) {
        return String.format("%s%s)", this.transformer.contextSetter(name), args);
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
            throw new SemanticErrorException(String.format("Cannot find DRG elements for node '%s'", info.getNodeName()));
        } else if (element instanceof TDecision) {
            Map<QualifiedName, Object> informationRequirements = makeInputs(testCases, testCase);
            return interpreter.evaluateDecision(reference.getNamespace(), reference.getElementName(), EvaluationContext.makeDecisionEvaluationContext(element, informationRequirements));
        } else if (element instanceof TInvocable) {
            Map<QualifiedName, Object> informationRequirements = makeInputs(testCases, testCase);
            List<Object> arguments = makeArgs(element, informationRequirements);
            EvaluationContext functionContext = EvaluationContext.makeFunctionInvocationContext(element, arguments, this.transformer.makeGlobalContext(element));
            return interpreter.evaluateInvocable(reference.getNamespace(), reference.getElementName(), functionContext);
        } else {
            throw new SemanticErrorException(String.format("'%s' is not supported yet", element.getClass().getSimpleName()));
        }
    }

    public Object expectedValue(TestCases testCases, TestCase testCase, ResultNode resultNode) {
        ResultNodeInfo info = extractResultNodeInfo(testCases, testCase, resultNode);
        return this.tckValueInterpreter.makeValue(info.getExpectedValue());
    }

    private Map<QualifiedName, Object> makeInputs(TestCases testCases, TestCase testCase) {
        Map<QualifiedName, Object> inputs = new LinkedHashMap<>();
        List<InputNode> inputNode = testCase.getInputNode();
        for (InputNode input: inputNode) {
            String simpleName = input.getName();
            try {
                InputNodeInfo info = extractInputNodeInfo(testCases, testCase, input);
                Object value = this.tckValueInterpreter.makeValue(info.getValue());
                DRGElementReference<? extends TDRGElement> reference = info.getReference();
                inputs.put(QualifiedName.toQualifiedName(reference.getNamespace(), reference.getElementName()), value);
            } catch (Exception e) {
                String errorMessage = String.format("Cannot extract inputs for inputNode '%s'", simpleName);
                TestLocation testLocation = new TestLocation(testCases.getTestCasesName(), testCase.getId(), testCases.getModelName());
                throw new SemanticErrorException(String.format("%s: %s", testLocation, errorMessage), e);
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
        TDefinitions definitions;
        if (StringUtils.isBlank(namespace)) {
            // Lookup by name
            String modelName = getModelName(testCases);
            definitions = this.dmnModelRepository.findModelByName(modelName);
        } else {
            // Lookup by namespace
            definitions = this.dmnModelRepository.findModelByNamespace(namespace);
        }
        if (definitions == null) {
            TestLocation location = new TestLocation(testCases.getTestCasesName(), null, testCases.getModelName());
            throw new SemanticErrorException(String.format("%s: Cannot find DM %s", location, testCases.getModelName()));
        } else {
            return definitions;
        }
    }

    public String getRootModelNamespace(TestCases testCases) {
        TDefinitions rootModel = getRootModel(testCases);
        return rootModel.getNamespace();
    }

    public String getModelName(TestCases testCases) {
        String fileName = testCases.getModelName();
        return getModelName(fileName);
    }

    public int getElementsCount(TestCases testCases) {
        TDefinitions rootModel = getRootModel(testCases);
        List<TDRGElement> drgElements = this.dmnModelRepository.findDRGElements(rootModel);
        return drgElements.stream().filter(e -> e instanceof TDecision || e instanceof TInvocable).toList().size();
    }

    public String getTraceFileName(TestCases testCases) {
        String testCasesName = getTestCasesName(testCases.getTestCasesName());
        return testCasesName + ".json";
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
    // Test Mocking section
    //
    public boolean isMockTesting() {
        return this.transformer.isMockTesting();
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

    // Annotations
    public String testAnnotationTestCasesName() {
        return com.gs.dmn.runtime.annotation.TestCases.class.getName();
    }

    public String testAnnotationTestCaseName() {
        return com.gs.dmn.runtime.annotation.TestCase.class.getName();
    }

    public String escapeInString(String text) {
        return this.transformer.escapeInString(text);
    }
}
