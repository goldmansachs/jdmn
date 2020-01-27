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

import com.gs.dmn.feel.analysis.semantics.type.ContextType;
import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.expression.*;
import com.gs.dmn.signavio.transformation.BasicSignavioDMN2JavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.omg.spec.dmn._20180521.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.*;
import java.util.stream.Collectors;

public class TestLabUtil {
    protected static final Logger LOGGER = LoggerFactory.getLogger(TestLabUtil.class);

    private final BasicDMN2JavaTransformer dmnTransformer;
    private final Map<String, TDRGElement> cache = new LinkedHashMap<>();

    public TestLabUtil(BasicDMN2JavaTransformer dmnTransformer) {
        this.dmnTransformer = dmnTransformer;
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
        return dmnTransformer.drgElementVariableName(decision);
    }

    public String drgElementOutputType(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return dmnTransformer.drgElementOutputType(decision);
    }

    public String drgElementOutputVariableName(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return dmnTransformer.drgElementVariableName(decision);
    }

    public String drgElementArgumentList(OutputParameterDefinition outputParameterDefinition) {
        TDecision decision = (TDecision) findDRGElement(outputParameterDefinition);
        return dmnTransformer.drgElementArgumentList(decision);
    }

    public String drgElementOutputFieldName(TestLab testLab, int outputIndex) {
        TDecision decision = (TDecision) findDRGElement(testLab.getRootOutputParameter());
        return ((BasicSignavioDMN2JavaTransformer)dmnTransformer).drgElementOutputFieldName(decision, outputIndex);
    }

    public String inputDataVariableName(InputParameterDefinition inputParameterDefinition) {
        TDRGElement element = findDRGElement(inputParameterDefinition);
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

    public boolean hasListType(ParameterDefinition parameterDefinition) {
        TDRGElement element = findDRGElement(parameterDefinition);
        return this.dmnTransformer.isList(element);
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

    public String upperCaseFirst(String name) {
        return dmnTransformer.upperCaseFirst(name);
    }

    public String lowerCaseFirst(String name) {
        return dmnTransformer.lowerCaseFirst(name);
    }

    public String qualifiedName(String pkg, String cls) {
        return dmnTransformer.qualifiedName(pkg, cls);
    }

    public TDRGElement findDRGElement(ParameterDefinition parameterDefinition) {
        String id = parameterDefinition.getId();
        TDRGElement element = findDRGElementById(id);
        if (element == null) {
            element = findDRGElementByDiagramAndShapeIds(parameterDefinition.getDiagramId(), parameterDefinition.getShapeId());
        }
        if (element == null) {
            element = findDRGElementByLabel(parameterDefinition.getRequirementName(), parameterDefinition.getDiagramId(), parameterDefinition.getShapeId());
        }
        return element;
    }

    // For input parameters
    public String toJavaType(InputParameterDefinition inputParameterDefinition) {
        Type feelType = toFEELType(inputParameterDefinition);
        return dmnTransformer.toJavaType(feelType);
    }

    // For input parameters
    public String toJavaExpression(TestLab testLab, TestCase testCase, int inputIndex) {
        Type inputType = toFEELType(testLab.getInputParameterDefinitions().get(inputIndex));
        Expression inputExpression = testCase.getInputValues().get(inputIndex);
        TDecision decision = (TDecision) findDRGElement(testLab.getRootOutputParameter());

        return toJavaExpression(inputType, inputExpression, decision);
    }

    // For expected values
    public String toJavaExpression(TestLab testLab, Expression expression) {
        Type outputType = toFEELType(testLab.getRootOutputParameter());
        TDecision decision = (TDecision) findDRGElement(testLab.getRootOutputParameter());

        return toJavaExpression(outputType, expression, decision);
    }

    private String toJavaExpression(Type inputType, Expression inputExpression, TDecision decision) {
        if (inputExpression == null) {
            return "null";
        } else if (isSimple(inputExpression)) {
            String feelExpression = inputExpression.toFEELExpression();
            return dmnTransformer.literalExpressionToJava(feelExpression, decision);
        } else if (isList(inputExpression)) {
            List<Expression> expressionList = ((ListExpression) inputExpression).getElements();
            if (expressionList != null) {
                List<String> elements = expressionList
                        .stream().map(e -> toJavaExpression(elementType(inputType), e, decision)).collect(Collectors.toList());
                String exp = elements.stream().collect(Collectors.joining(", "));
                return this.dmnTransformer.asList(exp);
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
                // Top-upo the missing ones
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
                    String arg = toJavaExpression(memberType, p.getRight(), decision);
                    args.add(arg);
                }
                String arguments = args.stream().collect(Collectors.joining(", "));
                return dmnTransformer.constructor(dmnTransformer.itemDefinitionJavaClassName(dmnTransformer.toJavaType(inputType)), arguments);
            } else {
                return "null";
            }
        } else {
            throw new UnsupportedOperationException(String.format("%s is not supported", inputExpression.getClass().getSimpleName()));
        }
    }

    TItemDefinition elementType(TItemDefinition type) {
        if (type.isIsCollection()) {
            String typeRef = type.getTypeRef();
            if (typeRef != null) {
                return dmnTransformer.getDMNModelRepository().lookupItemDefinition(QualifiedName.toQualifiedName(typeRef));
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
                    if (sameName(child, name)) {
                        return child;
                    }
                }
            }
            if (!StringUtils.isBlank(label)) {
                for(TItemDefinition child: itemComponent) {
                    if (sameLabel(child, label)) {
                        return child;
                    }
                }
            }
            if (!StringUtils.isBlank(id)) {
                for (TItemDefinition child : itemComponent) {
                    if (sameId(child, id)) {
                        return child;
                    }
                }
                for(TItemDefinition child: itemComponent) {
                    if (idEndsWith(child, id)) {
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
            throw new DMNRuntimeException(String.format("Cannot find member '(name='%s' label='%s' id='%s') in ItemDefinition '%s'", name, label, id, itemDefinition.getName()), e);
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

    private TDRGElement findDRGElementById(String id) {
        String key = makeKey(id);
        if (!cache.containsKey(key)) {
            TDRGElement result = null;
            List<TDRGElement> drgElements = dmnTransformer.getDMNModelRepository().drgElements();
            for (TDRGElement element : drgElements) {
                if (sameId(element, id)) {
                    result = element;
                    break;
                }
            }
            cache.put(key, result);
        }
        return cache.get(key);
    }

    private TDRGElement findDRGElementByDiagramAndShapeIds(String diagramId, String shapeId) {
        String key = makeKey(diagramId, shapeId);
        if (!cache.containsKey(key)) {
            TDRGElement result = null;
            List<TDRGElement> drgElements = dmnTransformer.getDMNModelRepository().drgElements();
            for (TDRGElement element : drgElements) {
                if (idEndsWith(element, shapeId) || (sameDiagramId(element, diagramId) && sameShapeId(element, shapeId))) {
                    result = element;
                    break;
                }
            }
            cache.put(key, result);
        }
        return cache.get(key);
    }

    private TDRGElement findDRGElementByLabel(String label, String diagramId, String shapeId) {
        String key = makeKey(label, diagramId, shapeId);
        if (!cache.containsKey(key)) {
            TDRGElement result;
            List<TDRGElement> drgElements = dmnTransformer.getDMNModelRepository().drgElements();
            List<TDRGElement> elements = drgElements.stream().filter(element -> sameLabel(element, label)).collect(Collectors.toList());
            if (elements.size() == 0) {
                result = null;
            } else if (elements.size() == 1) {
                result = elements.get(0);
            } else {
                List<TDRGElement> sameShapeIdElements = elements.stream().filter(e -> sameShapeId(e, shapeId)).collect(Collectors.toList());
                QName diagramQName = ((SignavioDMNModelRepository)dmnTransformer.getDMNModelRepository()).getDiagramIdQName();
                String newDiagramID = elements.stream().filter(e -> sameShapeId(e, shapeId)).map(e -> e.getOtherAttributes().get(diagramQName)).collect(Collectors.joining(", "));
                if (sameShapeIdElements.size() == 1) {
                    LOGGER.warn(String.format("Incorrect diagramId for test input with label '%s' diagramId='%s' shapeId='%s'. DiagramId should be '%s'", label, diagramId, shapeId, newDiagramID));
                    result = sameShapeIdElements.get(0);
                } else {
                    throw new DMNRuntimeException(String.format("Multiple DRGElements for label '%s' with diagramId='%s' shapeId='%s'. Diagram ID should be one of '%s'", label, diagramId, shapeId, newDiagramID));
                }
            }
            cache.put(key, result);
        }
        return cache.get(key);
    }

    private boolean idEndsWith(TNamedElement element, String id) {
        return id != null && element.getId().endsWith(id);
    }

    private boolean sameId(TNamedElement element, String id) {
        return id != null && id.equals(element.getId());
    }

    private boolean sameDiagramId(TDRGElement element, String id) {
        if (id == null) {
            return false;
        }
        Map<QName, String> otherAttributes = element.getOtherAttributes();
        QName diagramIdQName = ((SignavioDMNModelRepository) this.dmnTransformer.getDMNModelRepository()).getDiagramIdQName();
        String shapeId = otherAttributes.get(diagramIdQName);
        return id.equals(shapeId);
    }

    private boolean sameShapeId(TDRGElement element, String id) {
        if (id == null) {
            return false;
        }
        Map<QName, String> otherAttributes = element.getOtherAttributes();
        QName shapeIdQName = ((SignavioDMNModelRepository) this.dmnTransformer.getDMNModelRepository()).getShapeIdQName();
        String shapeId = otherAttributes.get(shapeIdQName);
        return id.equals(shapeId);
    }

    private boolean sameName(TNamedElement element, String name) {
        return name != null && name.equals(element.getName());
    }

    private boolean sameLabel(TNamedElement element, String label) {
        return label != null && label.equals(element.getLabel());
    }

    private Type toFEELType(ParameterDefinition parameterDefinition) {
        try {
            String typeRef = getTypeRef(parameterDefinition);
            return dmnTransformer.toFEELType(QualifiedName.toQualifiedName(typeRef));
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot resolve FEEL type for requirementId requirement '%s' in DM '%s'", parameterDefinition.getId(), parameterDefinition.getModelName()));
        }
    }

    TItemDefinition lookupItemDefinition(ParameterDefinition parameterDefinition) {
        String typeRef = getTypeRef(parameterDefinition);
        return dmnTransformer.getDMNModelRepository().lookupItemDefinition(QualifiedName.toQualifiedName(typeRef));
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

    private String makeKey(String id) {
        return String.format("%s::", id);
    }

    private String makeKey(String diagramId, String shapeId) {
        return String.format("%s:%s:", diagramId, shapeId);
    }

    private String makeKey(String label, String diagramId, String shapeId) {
        return String.format("%s:%s:%s", label, diagramId, shapeId);
    }
}
