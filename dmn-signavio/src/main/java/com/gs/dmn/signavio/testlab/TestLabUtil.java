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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.type.CompositeDataType;
import com.gs.dmn.feel.analysis.semantics.type.ContextType;
import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.expression.*;
import com.gs.dmn.signavio.transformation.basic.BasicSignavioDMNToJavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.native_.expression.NativeExpressionFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class TestLabUtil {
    protected static final Logger LOGGER = LoggerFactory.getLogger(TestLabUtil.class);

    private final BasicDMNToNativeTransformer<Type, DMNContext> transformer;
    private final SignavioDMNModelRepository repository;
    private final NativeExpressionFactory nativeFactory;
    private final NativeTypeFactory typeFactory;

    public TestLabUtil(BasicDMNToNativeTransformer<Type, DMNContext> transformer) {
        DMNModelRepository dmnModelRepository = transformer.getDMNModelRepository();
        if (dmnModelRepository instanceof SignavioDMNModelRepository modelRepository) {
            this.repository = modelRepository;
        } else {
            this.repository = new SignavioDMNModelRepository(dmnModelRepository.getRootDefinitions());
        }
        this.transformer = transformer;
        this.nativeFactory = transformer.getNativeFactory();
        this.typeFactory = transformer.getNativeTypeFactory();
    }

    //
    // Delegate methods
    //
    public String drgElementClassName(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return transformer.drgElementClassName(decision);
    }

    public String drgElementVariableName(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return transformer.namedElementVariableName(decision);
    }

    public String drgElementOutputType(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return transformer.drgElementOutputType(decision);
    }

    public String drgElementArgumentList(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return transformer.drgElementArgumentList(decision);
    }

    public String drgElementOutputFieldName(TestLab testLab, int outputIndex) {
        TDecision decision = (TDecision) findDRGElement(testLab.getRootOutputParameter());
        return ((BasicSignavioDMNToJavaTransformer) transformer).drgElementOutputFieldName(decision, outputIndex);
    }

    public String inputDataVariableName(InputParameterDefinition inputParameterDefinition) {
        TDRGElement element = findDRGElement(inputParameterDefinition);
        return transformer.namedElementVariableName(element);
    }

    public String assertClassName() {
        return transformer.assertClassName();
    }

    public String executionContextClassName() {
        return this.transformer.executionContextClassName();
    }

    public String executionContextVariableName() {
        return this.transformer.executionContextVariableName();
    }

    public String annotationSetClassName() {
        return transformer.annotationSetClassName();
    }

    public String annotationSetVariableName() {
        return transformer.annotationSetVariableName();
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

    public boolean hasListType(ParameterDefinition parameterDefinition) {
        TDRGElement element = findDRGElement(parameterDefinition);
        return this.transformer.hasListType(element);
    }

    public boolean isSimple(Expression expression) {
        return expression instanceof SimpleExpression;
    }

    public boolean isComplex(Expression expression) {
        return expression instanceof ComplexExpression;
    }

    public boolean isList(Expression expression) {
        return expression instanceof ListExpression;
    }

    public String getter(String outputName) {
        return transformer.getter(outputName);
    }

    public String qualifiedName(TestLab testLab, OutputParameterDefinition rootOutputParameter) {
        String pkg = transformer.nativeModelPackageName(rootOutputParameter.getModelName());
        String cls = drgElementClassName(rootOutputParameter);
        return transformer.qualifiedName(pkg, cls);
    }

    public TDRGElement findDRGElement(ParameterDefinition parameterDefinition) {
        String id = parameterDefinition.getId();
        TDRGElement element = this.repository.findDRGElementById(id);
        if (element == null) {
            element = this.repository.findDRGElementByDiagramAndShapeIds(parameterDefinition.getDiagramId(), parameterDefinition.getShapeId());
        }
        if (element == null) {
            element = this.repository.findDRGElementByLabel(parameterDefinition.getRequirementName(), parameterDefinition.getDiagramId(), parameterDefinition.getShapeId());
        }
        return element;
    }

    // For input parameters
    public String toNativeType(InputParameterDefinition inputParameterDefinition) {
        Type feelType = toFEELType(inputParameterDefinition);
        String type = transformer.toNativeType(feelType);
        return this.typeFactory.nullableType(type);
    }

    // For input parameters
    public String toNativeExpression(TestLab testLab, TestCase testCase, int inputIndex) {
        Type inputType = toFEELType(testLab.getInputParameterDefinitions().get(inputIndex));
        Expression inputExpression = testCase.getInputValues().get(inputIndex);
        TDecision decision = (TDecision) findDRGElement(testLab.getRootOutputParameter());

        return toNativeExpression(inputType, inputExpression, decision);
    }

    // For expected values
    public String toNativeExpression(TestLab testLab, Expression expression) {
        Type outputType = toFEELType(testLab.getRootOutputParameter());
        TDecision decision = (TDecision) findDRGElement(testLab.getRootOutputParameter());
        return toNativeExpression(outputType, expression, decision);
    }

    private String toNativeExpression(Type inputType, Expression inputExpression, TDecision decision) {
        if (inputExpression == null) {
            return "null";
        } else if (isSimple(inputExpression)) {
            String feelExpression = inputExpression.toFEELExpression();
            return transformer.literalExpressionToNative(decision, feelExpression);
        } else if (isList(inputExpression)) {
            List<Expression> expressionList = ((ListExpression) inputExpression).getElements();
            if (expressionList != null) {
                Type elementType = elementType(inputType);
                List<String> elements = expressionList
                        .stream().map(e -> toNativeExpression(elementType, e, decision)).collect(Collectors.toList());
                String exp = String.join(", ", elements);
                return this.nativeFactory.asList(elementType, exp);
            } else {
                return "null";
            }
        } else if (isComplex(inputExpression)) {
            List<Slot> slots = ((ComplexExpression) inputExpression).getSlots();
            if (slots != null) {
                Set<String> members = members(inputType);
                List<Pair<String, Expression>> pairs = new ArrayList<>();
                Set<String> present = new LinkedHashSet<>();
                for (Slot s: slots) {
                    ImmutablePair<String, Expression> pair = new ImmutablePair<>(s.getItemComponentName(), s.getValue());
                    pairs.add(pair);
                    present.add(pair.getLeft());
                }
                // Add the missing members
                for (String member: members) {
                    if (!present.contains(member)) {
                        ImmutablePair<String, Expression> pair = new ImmutablePair<>(member, null);
                        pairs.add(pair);
                    }
                }
                sortParameters(pairs);
                List<String> args = new ArrayList<>();
                for (Pair<String, Expression> p: pairs) {
                    Type memberType = memberType(inputType, p.getLeft());
                    String arg = toNativeExpression(memberType, p.getRight(), decision);
                    args.add(arg);
                }
                String arguments = String.join(", ", args);
                return transformer.constructor(transformer.itemDefinitionNativeClassName(transformer.toNativeType(inputType)), arguments);
            } else {
                return "null";
            }
        } else {
            throw new UnsupportedOperationException("%s is not supported".formatted(inputExpression.getClass().getSimpleName()));
        }
    }

    public TItemDefinition elementType(TItemDefinition type) {
        TDefinitions model = this.repository.getModel(type);
        if (type.isIsCollection()) {
            String typeRef = QualifiedName.toName(type.getTypeRef());
            if (!this.repository.isNull(typeRef)) {
                return this.repository.lookupItemDefinition(model, QualifiedName.toQualifiedName(model, typeRef));
            }
            List<TItemDefinition> itemComponent = type.getItemComponent();
            if (itemComponent.size() == 1) {
                return itemComponent.get(0);
            } else {
                return type;
            }
        } else {
            throw new DMNRuntimeException("Expected list type, found '%s'".formatted(type.getName()));
        }
    }

    public TItemDefinition memberType(TItemDefinition itemDefinition, Slot slot) {
        String id = slot.getId();
        String name = slot.getItemComponentName();
        String label = slot.getName();
        try {
            // Locate element by name, label or id
            List<TItemDefinition> itemComponent = itemDefinition.getItemComponent();
            if (itemComponent == null || itemComponent.isEmpty()) {
                itemDefinition = this.transformer.getDMNModelRepository().normalize(itemDefinition).getLeft();
                itemComponent = itemDefinition.getItemComponent();
            }
            if (!StringUtils.isBlank(name)) {
                for (TItemDefinition child: itemComponent) {
                    if (this.repository.sameName(child, name)) {
                        return child;
                    }
                }
            }
            if (!StringUtils.isBlank(label)) {
                for (TItemDefinition child: itemComponent) {
                    if (this.repository.sameLabel(child, label)) {
                        return child;
                    }
                }
            }
            if (!StringUtils.isBlank(id)) {
                for (TItemDefinition child: itemComponent) {
                    if (this.repository.sameId(child, id)) {
                        return child;
                    }
                }
                for (TItemDefinition child: itemComponent) {
                    if (this.repository.idEndsWith(child, id)) {
                        return child;
                    }
                }
                for (TItemDefinition child: itemComponent) {
                    if (sameSlotId(child, id)) {
                        return child;
                    }
                }
            }
            int index = Integer.parseInt(id);
            TItemDefinition memberType = itemDefinition.getItemComponent().get(index);
            if (memberType != null) {
                return memberType;
            }
        } catch (Exception e) {
            throw new DMNRuntimeException("Cannot find member '(name='%s' label='%s' id='%s') in ItemDefinition '%s'".formatted(name, label, id, itemDefinition == null ? null : itemDefinition.getName()), e);
        }
        throw new DMNRuntimeException("Cannot find member '(name='%s' label='%s' id='%s') in ItemDefinition '%s'".formatted(name, label, id, itemDefinition.getName()));
    }

    private boolean sameSlotId(TItemDefinition child, String id) {
        child.getExtensionElements();
        return false;
    }

    private Type elementType(Type type) {
        if (type instanceof ListType listType) {
            return listType.getElementType();
        } else {
            throw new DMNRuntimeException("Expected list type, found '%s'".formatted(type));
        }
    }

    private Set<String> members(Type type) {
        if (type instanceof ItemDefinitionType definitionType) {
            return definitionType.getMembers();
        } else if (type instanceof ContextType contextType) {
            return contextType.getMembers();
        } else {
            return new LinkedHashSet<>();
        }
    }

    private Type memberType(Type type, String member) {
        Type memberType = null;
        if (type instanceof ItemDefinitionType definitionType) {
            memberType = definitionType.getMemberType(member);
        } else if (type instanceof ContextType contextType) {
            memberType = contextType.getMemberType(member);
        }
        if (memberType == null) {
            throw new DMNRuntimeException("Cannot find member '%s' in type '%s'".formatted(member, type));
        } else {
            return memberType;
        }
    }

    private List<Pair<String, Expression>> sortParameters(List<Pair<String, Expression>> parameters) {
        parameters.sort(Comparator.comparing(Pair::getLeft));
        return parameters;
    }

    private Type toFEELType(ParameterDefinition parameterDefinition) {
        try {
            TDRGElement element = findDRGElement(parameterDefinition);
            TDefinitions model = this.repository.getModel(element);
            String typeRef = getTypeRef(parameterDefinition);
            return transformer.toFEELType(model, QualifiedName.toQualifiedName(model, typeRef));
        } catch (Exception e) {
            throw new DMNRuntimeException("Cannot resolve FEEL type for requirementId requirement '%s' in DM '%s'".formatted(parameterDefinition.getId(), parameterDefinition.getModelName()));
        }
    }

    public TItemDefinition lookupItemDefinition(ParameterDefinition parameterDefinition) {
        String typeRef = getTypeRef(parameterDefinition);
        TDRGElement element = findDRGElement(parameterDefinition);
        TDefinitions model = this.repository.getModel(element);
        return transformer.getDMNModelRepository().lookupItemDefinition(model, QualifiedName.toQualifiedName(model, typeRef));
    }

    private String getTypeRef(ParameterDefinition parameterDefinition) {
        TDRGElement element = findDRGElement(parameterDefinition);
        String typeRef;
        if (element instanceof TInputData data) {
            typeRef = QualifiedName.toName(data.getVariable().getTypeRef());
        } else if (element instanceof TDecision decision) {
            typeRef = QualifiedName.toName(decision.getVariable().getTypeRef());
        } else {
            throw new UnsupportedOperationException("Cannot resolve FEEL type for requirementId requirement '%s'. '%s' not supported".formatted(parameterDefinition.getId(), element.getClass().getSimpleName()));
        }
        return typeRef;
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
    // Native factory methods
    //
    public String defaultConstructor(String className) {
        return this.transformer.defaultConstructor(className);
    }

    public String constructor(String className, String arguments) {
        return this.transformer.constructor(className, arguments);
    }

    //
    // Proto section
    //
    public boolean isGenerateProto() {
        return this.transformer.isGenerateProto();
    }

    public String toNativeExpressionProto(InputParameterDefinition inputParameterDefinition) {
        String inputName = inputDataVariableName(inputParameterDefinition);
        Type type = toFEELType(inputParameterDefinition);
        return this.transformer.getNativeFactory().convertValueToProtoNativeType(inputName, type, false);
    }

    public String toNativeExpressionProto(TestLab testLab, Expression expression) {
        Type outputType = toFEELType(testLab.getRootOutputParameter());
        TDecision decision = (TDecision) findDRGElement(testLab.getRootOutputParameter());
        String value = toNativeExpression(outputType, expression, decision);
        if (this.transformer.isDateTimeType(outputType) || this.transformer.isComplexType(outputType)) {
            return this.transformer.getNativeFactory().convertValueToProtoNativeType(value, outputType, false);
        } else {
            return value;
        }
    }

    public String toNativeTypeProto(InputParameterDefinition inputParameterDefinition) {
        Type type = toFEELType(inputParameterDefinition);
        return this.transformer.getProtoFactory().toNativeProtoType(type);
    }

    public boolean isProtoReference(InputParameterDefinition inputParameterDefinition) {
        Type type = toFEELType(inputParameterDefinition);
        return this.transformer.isProtoReference(type);
    }

    public String drgElementVariableNameProto(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return transformer.getProtoFactory().namedElementVariableNameProto(decision);
    }

    public String drgElementArgumentListProto(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return transformer.drgElementArgumentListProto(decision);
    }

    public String qualifiedRequestMessageName(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return transformer.getProtoFactory().qualifiedRequestMessageName(decision);
    }

    public String qualifiedResponseMessageName(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return transformer.getProtoFactory().qualifiedResponseMessageName(decision);
    }

    public String drgElementOutputTypeProto(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return transformer.drgElementOutputTypeProto(decision);
    }

    public String requestVariableName(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return this.transformer.getProtoFactory().requestVariableName(decision);
    }

    public String responseVariableName(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return this.transformer.getProtoFactory().responseVariableName(decision);
    }

    public String protoGetter(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        Type type = this.transformer.drgElementOutputFEELType(decision);
        String name = drgElementVariableName(outputParameterDefinition);
        return this.transformer.getProtoFactory().protoGetter(name, type);
    }

    public String protoGetter(OutputParameterDefinition outputParameterDefinition, String memberName) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        Type type = this.transformer.drgElementOutputFEELType(decision);
        if (type instanceof ListType listType) {
            type = listType.getElementType();
        }
        if (type instanceof CompositeDataType dataType) {
            Type memberType = dataType.getMemberType(memberName);
            return this.transformer.getProtoFactory().protoGetter(memberName, memberType);
        } else {
            String protoName = this.transformer.getProtoFactory().protoName(memberName);
            return this.transformer.getter(protoName);
        }
    }

    public String protoSetter(InputParameterDefinition inputParameterDefinition, String args) {
        String inputName = inputDataVariableName(inputParameterDefinition);
        Type type = toFEELType(inputParameterDefinition);
        return this.transformer.getProtoFactory().protoSetter(inputName, type, args);
    }
}
