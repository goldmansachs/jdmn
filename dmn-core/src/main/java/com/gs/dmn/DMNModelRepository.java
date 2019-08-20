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
package com.gs.dmn;

import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import java.util.*;
import java.util.stream.Collectors;

public class DMNModelRepository {
    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private static final Logger LOGGER = LoggerFactory.getLogger(DMNModelRepository.class);

    private final TDefinitions rootDefinitions;

    private final List<TDefinitions> importedDefinitions = new ArrayList<>();

    private final List<TDefinitions> allDefinitions = new ArrayList<>();

    private final PrefixNamespaceMappings prefixNamespaceMappings;

    public DMNModelRepository() {
        this(OBJECT_FACTORY.createTDefinitions(), new PrefixNamespaceMappings());
    }

    public DMNModelRepository(TDefinitions definitions) {
        this(definitions, new PrefixNamespaceMappings());
    }

    public DMNModelRepository(TDefinitions rootDefinitions, PrefixNamespaceMappings prefixNamespaceMappings) {
        this(rootDefinitions, Arrays.asList(), prefixNamespaceMappings);
    }

    public DMNModelRepository(TDefinitions rootDefinitions, List<TDefinitions> importedDefinitions, PrefixNamespaceMappings prefixNamespaceMappings) {
        this.rootDefinitions = rootDefinitions;
        if (rootDefinitions != null) {
            normalize(this.rootDefinitions);
            this.allDefinitions.add(rootDefinitions);
        }
        if (importedDefinitions != null) {
            this.importedDefinitions.addAll(importedDefinitions);
            this.allDefinitions.addAll(importedDefinitions);
        }
        // Normalize all definitions
        this.prefixNamespaceMappings = prefixNamespaceMappings;
        for(TDefinitions def: allDefinitions) {
            List<TImport> imports = def.getImport();
            for(TImport imp: imports) {
                this.prefixNamespaceMappings.put(imp.getName(), imp.getNamespace());
            }
            normalize(def);
        }
    }

    public DMNModelRepository(Pair<TDefinitions, PrefixNamespaceMappings> pair) {
        this(pair.getLeft(), pair.getRight());
    }

    private void normalize(TDefinitions definitions) {
        if (definitions != null) {
            sortDRGElements(definitions.getDrgElement());
            sortNamedElements(definitions.getItemDefinition());
        }
    }

    public Set<String> computeCachedElements(boolean cachingFlag) {
        if (!cachingFlag) {
            return new LinkedHashSet<>();
        }

        LOGGER.info("Scanning for decisions to cache ...");

        Map<String, Integer> map = new LinkedHashMap<>();
        for (TDecision decision : decisions()) {
            for (TInformationRequirement ir : decision.getInformationRequirement()) {
                TDMNElementReference requiredDecision = ir.getRequiredDecision();
                if (requiredDecision != null) {
                    String href = requiredDecision.getHref();
                    Integer counter = map.get(href);
                    if (counter == null) {
                        counter = Integer.valueOf(0);
                    }
                    counter++;
                    map.put(href, counter);
                }
            }
        }

        Set<String> result = new LinkedHashSet<>();
        for(Map.Entry<String, Integer> entry: map.entrySet()) {
            if (entry.getValue() > 1) {
                TDecision drgElement = this.findDecisionById(entry.getKey());
                if (drgElement != null) {
                    result.add(name(drgElement));
                }
            }
        }

        LOGGER.info(String.format("Decisions to be cached: %s", result.stream().collect(Collectors.joining(", "))));

        return result;
    }

    public String removeSingleQuotes(String name) {
        if (isQuotedName(name)) {
            name = name.substring(1, name.length() - 1);
        }
        return name;
    }

    private boolean isQuotedName(String name) {
        return name != null && name.startsWith("'") && name.endsWith("'");
    }

    public TDefinitions getRootDefinitions() {
        return rootDefinitions;
    }

    public List<TDefinitions> getAllDefinitions() {
        return this.allDefinitions;
    }

    public PrefixNamespaceMappings getPrefixNamespaceMappings() {
        return prefixNamespaceMappings;
    }

    public List<TDRGElement> drgElements() {
        List<TDRGElement> result = new ArrayList<>();
        for (TDefinitions definitions: this.allDefinitions) {
            for (JAXBElement<? extends TDRGElement> jaxbElement : definitions.getDrgElement()) {
                TDRGElement element = jaxbElement.getValue();
                result.add(element);
            }
        }
        return result;
    }

    public List<TDecision> decisions() {
        List<TDecision> result = new ArrayList<>();
        for (TDefinitions definitions: this.allDefinitions) {
            for (JAXBElement<? extends TDRGElement> jaxbElement : definitions.getDrgElement()) {
                TDRGElement element = jaxbElement.getValue();
                if (element instanceof TDecision) {
                    result.add((TDecision) element);
                }
            }
        }
        return result;
    }

    public List<TInputData> inputDatas() {
        List<TInputData> result = new ArrayList<>();
        for (TDefinitions definitions: this.allDefinitions) {
            for (JAXBElement<? extends TDRGElement> jaxbElement : definitions.getDrgElement()) {
                TDRGElement element = jaxbElement.getValue();
                if (element instanceof TInputData) {
                    result.add((TInputData) element);
                }
            }
        }
        return result;
    }

    public List<TBusinessKnowledgeModel> businessKnowledgeModels() {
        List<TBusinessKnowledgeModel> result = new ArrayList<>();
        for (TDefinitions definitions: this.allDefinitions) {
            for (JAXBElement<? extends TDRGElement> jaxbElement : definitions.getDrgElement()) {
                TDRGElement element = jaxbElement.getValue();
                if (element instanceof TBusinessKnowledgeModel) {
                    result.add((TBusinessKnowledgeModel) element);
                }
            }
        }
        return result;
    }

    public List<TDecisionService> decisionServices() {
        List<TDecisionService> result = new ArrayList<>();
        for (TDefinitions definitions: this.allDefinitions) {
            for (JAXBElement<? extends TDRGElement> jaxbElement : definitions.getDrgElement()) {
                TDRGElement element = jaxbElement.getValue();
                if (element instanceof TDecisionService) {
                    result.add((TDecisionService) element);
                }
            }
        }
        return result;
    }

    public List<TItemDefinition> itemDefinitions() {
        List<TItemDefinition> result = new ArrayList<>();
        for (TDefinitions definitions: this.allDefinitions) {
            result.addAll(definitions.getItemDefinition());
        }
        return result;
    }

    public List<TItemDefinition> sortItemComponent(TItemDefinition itemDefinition) {
        if (itemDefinition == null || itemDefinition.getItemComponent() == null) {
            return null;
        }
        List<TItemDefinition> children = new ArrayList<>(itemDefinition.getItemComponent());
        children.sort((o1, o2) -> {
            if (o1 == null && o2 == null) {
                return 0;
            } if (o1 == null) {
                return 1;
            } if (o2 == null) {
                return -1;
            } else {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return children;
    }

    public TItemDefinition normalize(TItemDefinition itemDefinition) {
        while (true) {
            TItemDefinition next = next(itemDefinition);
            if (next != null) {
                itemDefinition = next;
            } else {
                break;
            }
        }
        return itemDefinition;
    }

    private TItemDefinition next(TItemDefinition itemDefinition) {
        if (itemDefinition == null
                || itemDefinition.isIsCollection()
                || !isEmpty(itemDefinition.getItemComponent())
                || itemDefinition.getTypeRef() == null) {
            return null;
        }
        return lookupItemDefinition(QualifiedName.toQualifiedName(itemDefinition.getTypeRef()));
    }

    private void sortDRGElements(List<JAXBElement<? extends TDRGElement>> result) {
        result.sort(Comparator.comparing((JAXBElement<? extends TDRGElement> o) -> removeSingleQuotes(o.getValue().getName())));
    }

    public void sortNamedElements(List<? extends TNamedElement> result) {
        result.sort(Comparator.comparing((TNamedElement o) -> removeSingleQuotes(o.getName())));
    }

    public TDecision findDecisionById(String href) {
        for (TDecision decision : decisions()) {
            if (sameId(decision, href)) {
                return decision;
            }
        }
        throw new DMNRuntimeException(String.format("Cannot find decision for href='%s'", href));
    }

    public TInputData findInputDataById(String href) {
        for (TInputData inputData : inputDatas()) {
            if (sameId(inputData, href)) {
                return inputData;
            }
        }
        throw new DMNRuntimeException(String.format("Cannot find input data for href='%s'", href));
    }

    public TInvocable findInvocableById(String href) {
        for (TBusinessKnowledgeModel knowledgeModel : businessKnowledgeModels()) {
            if (sameId(knowledgeModel, href)) {
                return knowledgeModel;
            }
        }
        for (TDecisionService service : decisionServices()) {
            if (sameId(service, href)) {
                return service;
            }
        }
        throw new DMNRuntimeException(String.format("Cannot find invocable (knowledge model or decision service) for href='%s'", href));
    }

    public TBusinessKnowledgeModel findKnowledgeModelByName(String name) {
        for (TBusinessKnowledgeModel knowledgeModel : businessKnowledgeModels()) {
            if (sameName(knowledgeModel, name)) {
                return knowledgeModel;
            }
        }
        throw new DMNRuntimeException(String.format("Cannot find business knowledge model for name='%s'", name));
    }

    public TDecisionService findDecisionServiceByName(String name) {
        for (TDecisionService service : decisionServices()) {
            if (sameName(service, name)) {
                return service;
            }
        }
        throw new DMNRuntimeException(String.format("Cannot find decision service for name='%s'", name));
    }

    public TDRGElement findDRGElementByName(String name) {
        for (TDRGElement element : drgElements()) {
            if (sameName(element, name)) {
                return element;
            }
        }
        throw new DMNRuntimeException(String.format("Cannot find element for name='%s'", name));
    }

    public boolean sameId(TDMNElement element, String href) {
        String id = extractId(href);
        return element.getId().equals(id);
    }

    private boolean sameName(TNamedElement element, String href) {
        return element.getName().equals(href);
    }

    public List<TDecision> directSubDecisions(TDRGElement element) {
        List<TDecision> decisions = new ArrayList<>();
        if (element instanceof TDecision) {
            for (TInformationRequirement ir : ((TDecision) element).getInformationRequirement()) {
                TDMNElementReference requiredDecision = ir.getRequiredDecision();
                if (requiredDecision != null) {
                    decisions.add(findDecisionById(requiredDecision.getHref()));
                }
            }
            sortNamedElements(decisions);
        }
        return decisions;
    }

    public Collection<TDecision> allSubDecisions(TDRGElement element) {
        Set<TDecision> decisions = new LinkedHashSet<>();
        collectSubDecisions(element, decisions);
        return decisions;
    }

    private void collectSubDecisions(TDRGElement element, Collection<TDecision> decisions) {
        decisions.addAll(directSubDecisions(element));
        for (TDecision child : directSubDecisions(element)) {
            collectSubDecisions(child, decisions);
        }
    }

    public List<TDecision> topologicalSort(TDRGElement decision) {
        List<TDecision> decisions = new ArrayList<>();
        topologicalSort((TDecision)decision, decisions);
        decisions.remove(decision);
        return decisions;
    }

    private void topologicalSort(TDecision parent, List<TDecision> decisions) {
        if (!decisions.contains(parent)) {
            for(TInformationRequirement ir: parent.getInformationRequirement()) {
                TDMNElementReference requiredDecision = ir.getRequiredDecision();
                if (requiredDecision != null) {
                    TDecision child = findDecisionById(requiredDecision.getHref());
                    if (child != null) {
                        topologicalSort(child, decisions);
                    }
                }
            }
            decisions.add(parent);
        }
    }

    public List<Object> topologicalSortWithMarkers(TDRGElement decision) {
        List<Object> objects = new ArrayList<>();
        topologicalSortWithMarkers((TDecision)decision, objects);
        objects.remove(0);
        objects.remove(objects.size() - 1);
        return objects;
    }

    private void topologicalSortWithMarkers(TDecision parent, List<Object> objects) {
        if (!objects.contains(parent)) {
            objects.add(new StartMarker(parent));
            for(TInformationRequirement ir: parent.getInformationRequirement()) {
                TDMNElementReference requiredDecision = ir.getRequiredDecision();
                if (requiredDecision != null) {
                    TDecision child = findDecisionById(requiredDecision.getHref());
                    if (child != null) {
                        topologicalSortWithMarkers(child, objects);
                    }
                }
            }
            objects.add(parent);
        }
    }

    public List<TInputData> directInputDatas(TDRGElement element) {
        if (element instanceof TDecision) {
            List<TInformationRequirement> informationRequirement = ((TDecision) element).getInformationRequirement();
            return directInputDatas(informationRequirement);
        } else {
            return new ArrayList<>();
        }
    }

    private List<TInputData> directInputDatas(List<TInformationRequirement> informationRequirement) {
        List<TInputData> result = new ArrayList<>();
        for (TInformationRequirement ir : informationRequirement) {
            TDMNElementReference requiredInput = ir.getRequiredInput();
            if (requiredInput != null) {
                TInputData inputData = findInputDataById(requiredInput.getHref());
                if (inputData != null) {
                    result.add(inputData);
                } else {
                    throw new DMNRuntimeException(String.format("Cannot find InputData for '%s'", requiredInput.getHref()));
                }
            }
        }
        return result;
    }

    public List<TInputData> allInputDatas(TDRGElement element) {
        if (element != null) {
            Set<TInputData> result = new LinkedHashSet<>();
            collectInputDatas(element, result);
            return new ArrayList<>(result);
        } else {
            return new ArrayList<>();
        }
    }

    public void collectInputDatas(TDRGElement element, Set<TInputData> inputDatas) {
        inputDatas.addAll(directInputDatas(element));
        for (TDecision child : directSubDecisions(element)) {
            collectInputDatas(child, inputDatas);
        }
    }

    public List<TInvocable> directSubInvocables(TDRGElement element) {
        List<TInvocable> result = new ArrayList<>();
        List<TKnowledgeRequirement> knowledgeRequirements;
        if (element instanceof TDecision) {
            knowledgeRequirements = ((TDecision) element).getKnowledgeRequirement();
        } else if (element instanceof TBusinessKnowledgeModel) {
            knowledgeRequirements = ((TBusinessKnowledgeModel) element).getKnowledgeRequirement();
        } else {
            knowledgeRequirements = new ArrayList<>();
        }
        for (TKnowledgeRequirement kr : knowledgeRequirements) {
            TDMNElementReference reference = kr.getRequiredKnowledge();
            if (reference != null) {
                TInvocable invocable = findInvocableById(reference.getHref());
                if (invocable != null) {
                    result.add(invocable);
                } else {
                    throw new DMNRuntimeException(String.format("Cannot find BusinessKnowledgeModel for '%s'", reference.getHref()));
                }
            }
        }
        return result;
    }

    public List<TInvocable> allInvocables(TDRGElement element) {
        Set<TInvocable> result = new LinkedHashSet<>();
        collectInvocables(element, result);
        return new ArrayList<>(result);
    }

    private void collectInvocables(TDRGElement element, Set<TInvocable> accumulator) {
        accumulator.addAll(directSubInvocables(element));
        for (TDRGElement child : directSubInvocables(element)) {
            collectInvocables(child, accumulator);
        }
    }

    public List<TDRGElement> allDrgElements(TDRGElement element) {
        List<TDRGElement> result = new ArrayList<>();
        collectDrgElements(element, result);
        return result;
    }

    private void collectDrgElements(TDRGElement element, List<TDRGElement> accumulator) {
        if (element instanceof TInputData) {
            // Add input data
            if (!accumulator.contains(element)) {
                accumulator.add(element);
            }
        } else if (element instanceof TBusinessKnowledgeModel) {
            // Process knowledge requirements
            List<TKnowledgeRequirement> krList = ((TBusinessKnowledgeModel) element).getKnowledgeRequirement();
            for(TKnowledgeRequirement kr: krList) {
                TInvocable invocable = findInvocableById(kr.getRequiredKnowledge().getHref());
                collectDrgElements(invocable, accumulator);
            }
            // Add BKM
            if (!accumulator.contains(element)) {
                accumulator.add(element);
            }
        } else if (element instanceof TDecisionService) {
            // Process output decisions
            List<TDMNElementReference> decisionRefList = ((TDecisionService) element).getOutputDecision();
            for(TDMNElementReference ref: decisionRefList) {
                collectDrgElements(findDecisionById(ref.getHref()), accumulator);
            }
            // Add Decision Service
            if (!accumulator.contains(element)) {
                accumulator.add(element);
            }
        } else if (element instanceof TDecision) {
            // Process knowledge requirements
            List<TKnowledgeRequirement> krList = ((TDecision) element).getKnowledgeRequirement();
            for(TKnowledgeRequirement kr: krList) {
                TInvocable invocable = findInvocableById(kr.getRequiredKnowledge().getHref());
                collectDrgElements(invocable, accumulator);
            }
            // Process information requirements
            List<TInformationRequirement> irList = ((TDecision) element).getInformationRequirement();
            for(TInformationRequirement ir: irList) {
                TDMNElementReference requiredInput = ir.getRequiredInput();
                if (requiredInput != null) {
                    collectDrgElements(findInputDataById(requiredInput.getHref()), accumulator);
                }
                TDMNElementReference requiredDecision = ir.getRequiredDecision();
                if (requiredDecision != null) {
                    collectDrgElements(findDecisionById(requiredDecision.getHref()), accumulator);
                }
            }
            // Add decision
            if (!accumulator.contains(element)) {
                accumulator.add(element);
            }
        }
    }

    public TDecisionTable decisionTable(TDRGElement element) {
        TExpression expression = expression(element);
        if (expression instanceof TDecisionTable) {
            return (TDecisionTable) expression;
        } else {
            throw new DMNRuntimeException(String.format("Cannot find decision table in element '%s'", element.getName()));
        }
    }

    public TItemDefinition lookupItemDefinition(QualifiedName typeRef) {
        return lookupItemDefinition(itemDefinitions(), typeRef);
    }

    protected TItemDefinition lookupItemDefinition(List<TItemDefinition> itemDefinitionList, QualifiedName typeRef) {
        if (typeRef == null || DMNVersion.LATEST.getFeelPrefix().equals(typeRef.getNamespace())) {
            return null;
        }
        for (TItemDefinition itemDefinition : itemDefinitionList) {
            if (typeRef.getLocalPart().equals(itemDefinition.getName())) {
                return itemDefinition;
            }
        }
        return null;
    }

    public TItemDefinition lookupItemDefinition(String name) {
        return lookupItemDefinition(itemDefinitions(), name);
    }

    protected TItemDefinition lookupItemDefinition(List<TItemDefinition> itemDefinitionList, String name) {
        if (name == null) {
            return null;
        }
        for (TItemDefinition itemDefinition : itemDefinitionList) {
            if (name.equals(itemDefinition.getName())) {
                return itemDefinition;
            }
        }
        return null;
    }

    public void collectInputs(TDecision decision, Set<TDRGElement> tdrgElements) {
        tdrgElements.addAll(directInputDatas(decision));
        tdrgElements.addAll(directSubDecisions(decision));
    }

    public boolean hasDefaultValue(TDecisionTable decisionTable) {
        List<TOutputClause> outputClauses = decisionTable.getOutput();
        for(TOutputClause output: outputClauses) {
            TLiteralExpression defaultOutputEntry = output.getDefaultOutputEntry();
            if (defaultOutputEntry != null) {
                return true;
            }
        }
        return false;
    }

    public TExpression expression(TNamedElement element) {
        if (element instanceof TDecision) {
            JAXBElement<? extends TExpression> expression = ((TDecision) element).getExpression();
            if (expression != null) {
                return expression.getValue();
            }
        } else if (element instanceof TBusinessKnowledgeModel) {
            TFunctionDefinition encapsulatedLogic = ((TBusinessKnowledgeModel) element).getEncapsulatedLogic();
            if (encapsulatedLogic != null) {
                JAXBElement<? extends TExpression> expression = encapsulatedLogic.getExpression();
                if (expression != null) {
                    return expression.getValue();
                }
            }
        } else if (element instanceof TDecisionService) {
            return null;
        } else if (element instanceof TInformationItem) {
            return null;
        } else {
            throw new UnsupportedOperationException(String.format("'%s' is not supported yet", element.getClass().getSimpleName()));
        }
        return null;
    }

    public boolean isLiteralExpression(TDRGElement element) {
        return expression(element) instanceof TLiteralExpression;
    }

    public boolean isFreeTextLiteralExpression(TNamedElement element) {
        TExpression expression = expression(element);
        return expression instanceof TLiteralExpression
                && DMNToJavaTransformer.FREE_TEXT_LANGUAGE.equals(((TLiteralExpression)expression).getExpressionLanguage());
    }

    public boolean isDecisionTableExpression(TDRGElement element) {
        return expression(element) instanceof TDecisionTable;
    }

    public boolean isCompoundDecisionTable(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TDecisionTable
                && ((TDecisionTable) expression).getOutput() != null
                && ((TDecisionTable) expression).getOutput().size() > 1;
    }

    public boolean isInvocationExpression(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TInvocation;
    }

    public boolean isContextExpression(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TContext;
    }

    public boolean isRelationExpression(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TRelation;
    }

    //
    // Item definition related functions
    //
    public boolean isEmpty(List<TItemDefinition> list) {
        return list == null || list.isEmpty();
    }

    public QualifiedName typeRef(TNamedElement element) {
        QualifiedName typeRef = null;
        if (element instanceof TInformationItem) {
            typeRef = QualifiedName.toQualifiedName(((TInformationItem) element).getTypeRef());
        }
        if (typeRef == null) {
            // Derive from variable
            TInformationItem variable = variable(element);
            if (variable != null) {
                typeRef = QualifiedName.toQualifiedName(variable.getTypeRef());
            }
        }
        if (typeRef == null) {
            // Derive from expression
            TExpression expression = expression(element);
            if (expression != null) {
                typeRef = QualifiedName.toQualifiedName(expression.getTypeRef());
                if (typeRef == null) {
                    if (expression instanceof TContext) {
                        // Derive from return entry
                        List<TContextEntry> contextEntryList = ((TContext) expression).getContextEntry();
                        for(TContextEntry ce: contextEntryList) {
                            if (ce.getVariable() == null) {
                                JAXBElement<? extends TExpression> returnElement = ce.getExpression();
                                if (returnElement != null) {
                                    typeRef = QualifiedName.toQualifiedName(returnElement.getValue().getTypeRef());
                                }
                            }
                        }
                    } else if (expression instanceof TDecisionTable) {
                        // Derive from output clause
                        List<TOutputClause> outputList = ((TDecisionTable) expression).getOutput();
                        if (outputList.size() == 1) {
                            typeRef = QualifiedName.toQualifiedName(outputList.get(0).getTypeRef());
                            if (typeRef == null) {
                                // Derive from rules
                                List<TDecisionRule> ruleList = ((TDecisionTable) expression).getRule();
                                List<TLiteralExpression> outputEntry = ruleList.get(0).getOutputEntry();
                                typeRef = QualifiedName.toQualifiedName(outputEntry.get(0).getTypeRef());
                            }
                        }
                    }
                }
            }
        }
        return typeRef;
    }

    //
    // Decision Service related functions
    //
    public TDecision getOutputDecision(TDecisionService decisionService) {
        List<TDMNElementReference> outputDecisionList = decisionService.getOutputDecision();
        if (outputDecisionList.size() != 1) {
            throw new DMNRuntimeException(String.format("Missing or more than one decision services in BKM '%s'", decisionService.getName()));
        }
        return this.findDecisionById(outputDecisionList.get(0).getHref());
    }

    //
    // DecisionTable related functions
    //
    public boolean isSingleHit(THitPolicy policy) {
        return policy == THitPolicy.FIRST
                || policy == THitPolicy.UNIQUE
                || policy == THitPolicy.ANY
                || policy == THitPolicy.PRIORITY
                || policy == null;
    }

    public boolean isFirstSingleHit(THitPolicy policy) {
        return policy == THitPolicy.FIRST;
    }

    public boolean atLeastTwoRules(TDecisionTable expression) {
        return expression.getRule() != null && expression.getRule().size() >= 2;
    }

    public boolean isMultipleHit(THitPolicy hitPolicy) {
        return THitPolicy.COLLECT == hitPolicy
                || THitPolicy.RULE_ORDER == hitPolicy
                || THitPolicy.OUTPUT_ORDER == hitPolicy
                ;
    }

    public boolean isOutputOrderHit(THitPolicy hitPolicy) {
        return THitPolicy.PRIORITY == hitPolicy
                || THitPolicy.OUTPUT_ORDER == hitPolicy
                ;
    }

    public boolean hasAggregator(TDecisionTable decisionTable) {
        return decisionTable.getAggregation() != null;
    }

    public int rulesCount(TDRGElement element) {
        TExpression expression = expression(element);
        if (expression instanceof TDecisionTable) {
            List<TDecisionRule> rules = ((TDecisionTable) expression).getRule();
            return rules == null ? 0 : rules.size();
        } else {
            return -1;
        }
    }

    public String outputClauseName(TDRGElement element, TOutputClause output) {
        // Try TOutputClause.typeRef
        String outputClauseName = output.getName();
        if (StringUtils.isBlank(outputClauseName)) {
            // Try variable.typeRef from parent
            if (!isCompoundDecisionTable(element)) {
                TInformationItem variable = variable(element);
                if (variable != null) {
                    outputClauseName = variable.getName();
                }
            }
            if (StringUtils.isBlank(outputClauseName)) {
                throw new DMNRuntimeException(String.format("Cannot resolve name for outputClause '%s' in element '%s'", output.getId(), element.getName()));
            }
        }
        return outputClauseName;
    }

    public TInformationItem variable(TNamedElement element) {
        if (element instanceof TInputData) {
            return ((TInputData) element).getVariable();
        } if (element instanceof TDecision) {
            return ((TDecision) element).getVariable();
        } else if (element instanceof TBusinessKnowledgeModel) {
            return ((TBusinessKnowledgeModel) element).getVariable();
        }
        return null;
    }

    public String name(TNamedElement element) {
        return element.getName();
    }

    public String label(TDMNElement element) {
        String label = element.getLabel();
        return label == null ? "" : label.replace('\"', '\'');
    }

    public String displayName(TNamedElement element) {
        String name = label(element);
        if (StringUtils.isBlank(name)) {
            name = element.getName();
        }
        if (name == null) {
            throw new DMNRuntimeException(String.format("Display name cannot be null for element '%s'", element.getId()));
        }
        return name;
    }

    public String namespacePrefix(TDMNElementReference reference) {
        if (reference != null) {
            String href = reference.getHref();
            String namespace = extractNamespace(href);
            if (namespace != null) {
                String prefix = this.prefixNamespaceMappings.getPrefix(namespace);
                if (prefix != null) {
                    return prefix;
                }
            }
        }
        return  null;
    }

    public String namespacePrefixForId(TDMNElementReference reference, String id) {
        if (reference != null) {
            String href = reference.getHref();
            if (href.contains(id)) {
                String namespace = extractNamespace(href);
                if (namespace != null) {
                    String prefix = this.prefixNamespaceMappings.getPrefix(namespace);
                    if (prefix != null) {
                        return prefix;
                    }
                }
            }
        }
        return  null;
    }

    public static String extractId(String href) {
        if (hasNamespace(href)) {
            href = href.substring(href.indexOf('#') + 1);
        }
        if (href.startsWith("#")) {
            href = href.substring(1);
        }
        return href;
    }

    private String extractNamespace(String href) {
        String namespace = null;
        if (hasNamespace(href)) {
            namespace = href.substring(0, href.indexOf('#'));
        }
        return namespace;
    }

    private static boolean hasNamespace(String href) {
        return href.startsWith("http://");
    }
}
