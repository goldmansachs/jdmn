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
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.expression.*;
import com.gs.dmn.signavio.transformation.basic.BasicSignavioDMNToJavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import com.gs.dmn.transformation.native_.expression.NativeExpressionFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.omg.spec.dmn._20191111.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class TestLabUtil {
    protected static final Logger LOGGER = LoggerFactory.getLogger(TestLabUtil.class);

    private final BasicDMNToNativeTransformer dmnTransformer;
    private final SignavioDMNModelRepository dmnModelRepository;
    private final NativeExpressionFactory nativeFactory;
    private final NativeTypeFactory typeFactory;

    public TestLabUtil(BasicDMNToNativeTransformer dmnTransformer) {
        DMNModelRepository dmnModelRepository = dmnTransformer.getDMNModelRepository();
        if (dmnModelRepository instanceof SignavioDMNModelRepository) {
            this.dmnModelRepository = (SignavioDMNModelRepository) dmnModelRepository;
        } else {
            this.dmnModelRepository = new SignavioDMNModelRepository(dmnModelRepository.getRootDefinitions(), dmnModelRepository.getPrefixNamespaceMappings());
        }
        this.dmnTransformer = dmnTransformer;
        this.nativeFactory = dmnTransformer.getNativeFactory();
        this.typeFactory = dmnTransformer.getNativeTypeFactory();
    }

    //
    // Delegate methods
    //
    public String drgElementClassName(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return dmnTransformer.drgElementClassName(decision);
    }

    public String drgElementVariableName(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return dmnTransformer.namedElementVariableName(decision);
    }

    public String drgElementOutputType(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return dmnTransformer.drgElementOutputType(decision);
    }

    public String drgElementArgumentList(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return dmnTransformer.drgElementArgumentList(decision);
    }

    public String drgElementOutputFieldName(TestLab testLab, int outputIndex) {
        TDecision decision = (TDecision) findDRGElement(testLab.getRootOutputParameter());
        return ((BasicSignavioDMNToJavaTransformer)dmnTransformer).drgElementOutputFieldName(decision, outputIndex);
    }

    public String inputDataVariableName(InputParameterDefinition inputParameterDefinition) {
        TDRGElement element = findDRGElement(inputParameterDefinition);
        return dmnTransformer.namedElementVariableName(element);
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

    public boolean hasListType(ParameterDefinition parameterDefinition) {
        TDRGElement element = findDRGElement(parameterDefinition);
        return this.dmnTransformer.hasListType(element);
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
        return dmnTransformer.getter(outputName);
    }

    public String qualifiedName(TestLab testLab, OutputParameterDefinition rootOutputParameter) {
        String pkg = dmnTransformer.nativeModelPackageName(rootOutputParameter.getModelName());
        String cls = drgElementClassName(rootOutputParameter);
        return dmnTransformer.qualifiedName(pkg, cls);
    }

    public TDRGElement findDRGElement(ParameterDefinition parameterDefinition) {
        String id = parameterDefinition.getId();
        TDRGElement element = this.dmnModelRepository.findDRGElementById(id);
        if (element == null) {
            element = this.dmnModelRepository.findDRGElementByDiagramAndShapeIds(parameterDefinition.getDiagramId(), parameterDefinition.getShapeId());
        }
        if (element == null) {
            element = this.dmnModelRepository.findDRGElementByLabel(parameterDefinition.getRequirementName(), parameterDefinition.getDiagramId(), parameterDefinition.getShapeId());
        }
        return element;
    }

    // For input parameters
    public String toNativeType(InputParameterDefinition inputParameterDefinition) {
        Type feelType = toFEELType(inputParameterDefinition);
        String type = dmnTransformer.toNativeType(feelType);
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
            return dmnTransformer.literalExpressionToNative(decision, feelExpression);
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
                for(Pair<String, Expression> p: pairs) {
                    Type memberType = memberType(inputType, p.getLeft());
                    String arg = toNativeExpression(memberType, p.getRight(), decision);
                    args.add(arg);
                }
                String arguments = String.join(", ", args);
                return dmnTransformer.constructor(dmnTransformer.itemDefinitionNativeClassName(dmnTransformer.toNativeType(inputType)), arguments);
            } else {
                return "null";
            }
        } else {
            throw new UnsupportedOperationException(String.format("%s is not supported", inputExpression.getClass().getSimpleName()));
        }
    }

    TItemDefinition elementType(TItemDefinition type) {
        TDefinitions model = this.dmnModelRepository.getModel(type);
        if (type.isIsCollection()) {
            String typeRef = type.getTypeRef();
            if (typeRef != null) {
                return this.dmnModelRepository.lookupItemDefinition(model, QualifiedName.toQualifiedName(model, typeRef));
            }
            List<TItemDefinition> itemComponent = type.getItemComponent();
            if (itemComponent.size() == 1) {
                return itemComponent.get(0);
            } else {
                return type;
            }
        }
        throw new DMNRuntimeException(String.format("Cannot find element type of type '%s'", type.getName()));
    }

    TItemDefinition memberType(TItemDefinition itemDefinition, Slot slot) {
        String id = slot.getId();
        String name = slot.getItemComponentName();
        String label = slot.getName();
        try {
            // Locate element by name, label or id
            List<TItemDefinition> itemComponent = itemDefinition.getItemComponent();
            if (itemComponent == null || itemComponent.isEmpty()) {
                itemDefinition = this.dmnTransformer.getDMNModelRepository().normalize(itemDefinition);
                itemComponent = itemDefinition.getItemComponent();
            }
            if (!StringUtils.isBlank(name)) {
                for(TItemDefinition child: itemComponent) {
                    if (this.dmnModelRepository.sameName(child, name)) {
                        return child;
                    }
                }
            }
            if (!StringUtils.isBlank(label)) {
                for(TItemDefinition child: itemComponent) {
                    if (this.dmnModelRepository.sameLabel(child, label)) {
                        return child;
                    }
                }
            }
            if (!StringUtils.isBlank(id)) {
                for (TItemDefinition child : itemComponent) {
                    if (this.dmnModelRepository.sameId(child, id)) {
                        return child;
                    }
                }
                for(TItemDefinition child: itemComponent) {
                    if (this.dmnModelRepository.idEndsWith(child, id)) {
                        return child;
                    }
                }
                for(TItemDefinition child: itemComponent) {
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
            throw new DMNRuntimeException(String.format("Cannot find member '(name='%s' label='%s' id='%s') in ItemDefinition '%s'", name, label, id, itemDefinition == null ? null : itemDefinition.getName()), e);
        }
        throw new DMNRuntimeException(String.format("Cannot find member '(name='%s' label='%s' id='%s') in ItemDefinition '%s'", name, label, id, itemDefinition.getName()));
    }

    private boolean sameSlotId(TItemDefinition child, String id) {
        child.getExtensionElements();
        return false;
    }

    private Type elementType(Type type) {
        Type elementType = null;
        if (type instanceof ListType) {
            elementType = ((ListType) type).getElementType();
        }
        if (elementType == null) {
            throw new DMNRuntimeException(String.format("Cannot find element type of type '%s'", type));
        } else {
            return elementType;
        }
    }

    private Set<String> members(Type type) {
        if (type instanceof ItemDefinitionType) {
            return ((ItemDefinitionType) type).getMembers();
        } else if (type instanceof ContextType) {
            return ((ContextType) type).getMembers();
        } else {
            return new LinkedHashSet<>();
        }
    }

    private Type memberType(Type type, String member) {
        Type memberType = null;
        if (type instanceof ItemDefinitionType) {
            memberType = ((ItemDefinitionType) type).getMemberType(member);
        } else if (type instanceof ContextType) {
            memberType = ((ContextType) type).getMemberType(member);
        }
        if (memberType == null) {
            throw new DMNRuntimeException(String.format("Cannot find member '%s' in type '%s'", member, type));
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
            TDefinitions model = this.dmnModelRepository.getModel(element);
            String typeRef = getTypeRef(parameterDefinition);
            return dmnTransformer.toFEELType(model, QualifiedName.toQualifiedName(model, typeRef));
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot resolve FEEL type for requirementId requirement '%s' in DM '%s'", parameterDefinition.getId(), parameterDefinition.getModelName()));
        }
    }

    TItemDefinition lookupItemDefinition(ParameterDefinition parameterDefinition) {
        String typeRef = getTypeRef(parameterDefinition);
        TDRGElement element = findDRGElement(parameterDefinition);
        TDefinitions model = this.dmnModelRepository.getModel(element);
        return dmnTransformer.getDMNModelRepository().lookupItemDefinition(model, QualifiedName.toQualifiedName(model, typeRef));
    }

    private String getTypeRef(ParameterDefinition parameterDefinition) {
        TDRGElement element = findDRGElement(parameterDefinition);
        String typeRef;
        if (element instanceof TInputData) {
            typeRef = ((TInputData) element).getVariable().getTypeRef();
        } else if (element instanceof TDecision) {
            typeRef = ((TDecision) element).getVariable().getTypeRef();
        } else {
            throw new UnsupportedOperationException(String.format("Cannot resolve FEEL type for requirementId requirement '%s'. '%s' not supported", parameterDefinition.getId(), element.getClass().getSimpleName()));
        }
        return typeRef;
    }

    //
    // Proto section
    //
    public boolean isGenerateProto() {
        return this.dmnTransformer.isGenerateProto();
    }

    public String toNativeExpressionProto(InputParameterDefinition inputParameterDefinition) {
        String inputName = inputDataVariableName(inputParameterDefinition);
        Type type = toFEELType(inputParameterDefinition);
        return this.dmnTransformer.getNativeFactory().convertValueToProtoNativeType(inputName, type, false);
    }

    public String toNativeExpressionProto(TestLab testLab, Expression expression) {
        Type outputType = toFEELType(testLab.getRootOutputParameter());
        TDecision decision = (TDecision) findDRGElement(testLab.getRootOutputParameter());
        String value = toNativeExpression(outputType, expression, decision);
        if (this.dmnTransformer.isDateTimeType(outputType) || this.dmnTransformer.isComplexType(outputType)) {
            return this.dmnTransformer.getNativeFactory().convertValueToProtoNativeType(value, outputType, false);
        } else {
            return value;
        }
    }

    public String toNativeTypeProto(InputParameterDefinition inputParameterDefinition) {
        Type type = toFEELType(inputParameterDefinition);
        return this.dmnTransformer.getProtoFactory().toNativeProtoType(type);
    }

    public boolean isProtoReference(InputParameterDefinition inputParameterDefinition) {
        Type type = toFEELType(inputParameterDefinition);
        return this.dmnTransformer.isProtoReference(type);
    }

    public String drgElementVariableNameProto(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return dmnTransformer.getProtoFactory().namedElementVariableNameProto(decision);
    }

    public String drgElementArgumentListProto(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return dmnTransformer.drgElementArgumentListProto(decision);
    }

    public String qualifiedRequestMessageName(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return dmnTransformer.getProtoFactory().qualifiedRequestMessageName(decision);
    }

    public String qualifiedResponseMessageName(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return dmnTransformer.getProtoFactory().qualifiedResponseMessageName(decision);
    }

    public String drgElementOutputTypeProto(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return dmnTransformer.drgElementOutputTypeProto(decision);
    }

    public String requestVariableName(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return this.dmnTransformer.getProtoFactory().requestVariableName(decision);
    }

    public String responseVariableName(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return this.dmnTransformer.getProtoFactory().responseVariableName(decision);
    }

    public String protoGetter(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        Type type = this.dmnTransformer.drgElementOutputFEELType(decision);
        String name = drgElementVariableName(outputParameterDefinition);
        return this.dmnTransformer.getProtoFactory().protoGetter(name, type);
    }

    public String protoGetter(OutputParameterDefinition outputParameterDefinition, String memberName) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        Type type = this.dmnTransformer.drgElementOutputFEELType(decision);
        if (type instanceof ListType) {
            type = ((ListType) type).getElementType();
        }
        if (type instanceof CompositeDataType) {
            Type memberType = ((CompositeDataType) type).getMemberType(memberName);
            return this.dmnTransformer.getProtoFactory().protoGetter(memberName, memberType);
        } else {
            String protoName = this.dmnTransformer.getProtoFactory().protoName(memberName);
            return this.dmnTransformer.getter(protoName);
        }
    }

    public String protoSetter(InputParameterDefinition inputParameterDefinition) {
        String inputName = inputDataVariableName(inputParameterDefinition);
        Type type = toFEELType(inputParameterDefinition);
        return this.dmnTransformer.getProtoFactory().protoSetter(inputName, type);
    }
}
