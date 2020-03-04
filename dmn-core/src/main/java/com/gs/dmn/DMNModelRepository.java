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

public class DMNModelRepository {
    private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private static final Logger LOGGER = LoggerFactory.getLogger(DMNModelRepository.class);

    protected final TDefinitions rootDefinitions;

    protected final List<TDefinitions> importedDefinitions = new ArrayList<>();

    protected final List<TDefinitions> allDefinitions = new ArrayList<>();

    protected final Map<String, TDefinitions> definitionsMap = new LinkedHashMap<>();

    protected final Map<TDRGElement, TDefinitions> elementMap = new LinkedHashMap<>();

    protected final PrefixNamespaceMappings prefixNamespaceMappings;

    // Derived properties to optimise search
    protected List<TDRGElement> drgElements;
    protected List<TDecision> decisions;
    protected List<TInputData> inputDatas;
    protected List<TBusinessKnowledgeModel> businessKnowledgeModels;
    protected List<TDecisionService> decisionServices;
    protected List<TItemDefinition> itemDefinitions;
    protected Map<String, TDRGElement> drgElementByName = new LinkedHashMap<>();
    protected Map<String, TBusinessKnowledgeModel> knowledgeModelByName = new LinkedHashMap<>();
    protected Map<String, TDecisionService> decisionServiceByName = new LinkedHashMap<>();
    protected Map<String, TDRGElement> drgElementByRef = new LinkedHashMap<>();
    protected Map<String, TInputData> inputDataByRef = new LinkedHashMap<>();
    protected Map<String, TDecision> decisionByRef = new LinkedHashMap<>();
    protected Map<String, TBusinessKnowledgeModel> businessKnowledgeModelByRef = new LinkedHashMap<>();
    protected Map<String, TDecisionService> decisionServiceByRef = new LinkedHashMap<>();

    public DMNModelRepository() {
        this(OBJECT_FACTORY.createTDefinitions(), new PrefixNamespaceMappings());
    }

    public DMNModelRepository(TDefinitions definitions) {
        this(definitions, new PrefixNamespaceMappings());
    }

    public DMNModelRepository(TDefinitions rootDefinitions, PrefixNamespaceMappings prefixNamespaceMappings) {
        this(rootDefinitions, Collections.emptyList(), prefixNamespaceMappings);
    }

    public DMNModelRepository(TDefinitions rootDefinitions, List<TDefinitions> importedDefinitions, PrefixNamespaceMappings prefixNamespaceMappings) {
        this.rootDefinitions = rootDefinitions;
        if (rootDefinitions != null) {
            this.definitionsMap.put(this.rootDefinitions.getNamespace(), this.rootDefinitions);
        }
        if (importedDefinitions != null) {
            this.importedDefinitions.addAll(importedDefinitions);
            for (TDefinitions definitions: this.importedDefinitions) {
                this.definitionsMap.put(definitions.getNamespace(), definitions);
            }
        }
        this.prefixNamespaceMappings = prefixNamespaceMappings;
        this.allDefinitions.addAll(this.definitionsMap.values());

        // Process all definitions
        for(TDefinitions definitions: this.getAllDefinitions()) {
            // Normalize
            normalize(definitions);

            // Set derived properties
            for (TDRGElement element: drgElements(definitions)) {
                this.elementMap.put(element, definitions);
            }
            List<TImport> imports = definitions.getImport();
            for(TImport imp: imports) {
                this.prefixNamespaceMappings.put(imp.getName(), imp.getNamespace());
            }
        }
    }

    public DMNModelRepository(Pair<TDefinitions, PrefixNamespaceMappings> pair) {
        this(pair.getLeft(), pair.getRight());
    }

    protected void normalize(TDefinitions definitions) {
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
        Map<String, TDecision> parentMap = new LinkedHashMap<>();
        for (TDefinitions definitions: this.getAllDefinitions()) {
            for (TDecision decision : decisions(definitions)) {
                addCachedChildren(definitions, decision, parentMap, map);
            }
        }

        Set<String> result = new LinkedHashSet<>();
        for(Map.Entry<String, Integer> entry: map.entrySet()) {
            if (entry.getValue() > 1) {
                String key = entry.getKey();
                TDecision drgElement = this.findDecisionByRef(parentMap.get(key), key);
                if (drgElement != null) {
                    result.add(name(drgElement));
                }
            }
        }

        LOGGER.info("Decisions to be cached: {}", String.join(", ", result));

        return result;
    }

    private void addCachedChildren(TDefinitions definitions, TDecision decision, Map<String, TDecision> parentMap, Map<String, Integer> map) {
        for (TInformationRequirement ir : decision.getInformationRequirement()) {
            TDMNElementReference requiredDecision = ir.getRequiredDecision();
            if (requiredDecision != null) {
                String href = requiredDecision.getHref();
                if (!hasNamespace(href)) {
                    href = makeRef(definitions.getNamespace(), href);
                }
                map.compute(href, (k, v) -> v == null ? 1 : v + 1);
                parentMap.put(href, decision);
            }
        }
    }

    public String removeSingleQuotes(String name) {
        if (isQuotedName(name)) {
            name = name.substring(1, name.length() - 1);
        }
        return name;
    }

    protected boolean isQuotedName(String name) {
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

    public void addElementMap(TDRGElement element, TDefinitions definitions) {
        this.elementMap.put(element, definitions);
    }

    public List<String> getImportedNames() {
        List<String> names = new ArrayList<>();
        for (TDefinitions definitions: this.allDefinitions) {
            for (TImport imp: definitions.getImport()) {
                names.add(imp.getName());
            }
        }
        return names;
    }

    public List<TDRGElement> drgElements() {
        if (this.drgElements == null) {
            this.drgElements = new ArrayList<>();
            for (TDefinitions definitions: this.allDefinitions) {
                drgElements(definitions, this.drgElements);
            }
        }
        return this.drgElements;
    }

    public List<TDecision> decisions() {
        if (this.decisions == null) {
            this.decisions = new ArrayList<>();
            for (TDefinitions definitions: this.allDefinitions) {
                decisions(definitions, this.decisions);
            }
        }
        return this.decisions;
    }

    public List<TInputData> inputDatas() {
        if (this.inputDatas == null) {
            this.inputDatas = new ArrayList<>();
            for (TDefinitions definitions: this.allDefinitions) {
                inputDatas(definitions, this.inputDatas);
            }
        }
        return this.inputDatas;
    }

    public List<TBusinessKnowledgeModel> businessKnowledgeModels() {
        if (this.businessKnowledgeModels == null) {
            this.businessKnowledgeModels = new ArrayList<>();
            for (TDefinitions definitions: this.allDefinitions) {
                businessKnowledgeModels(definitions, this.businessKnowledgeModels);
            }
        }
        return this.businessKnowledgeModels;
    }

    public List<TDecisionService> decisionServices() {
        if (this.decisionServices == null) {
            this.decisionServices = new ArrayList<>();
            for (TDefinitions definitions: this.allDefinitions) {
                decisionServices(definitions, this.decisionServices);
            }
        }
        return this.decisionServices;
    }

    public List<TItemDefinition> itemDefinitions() {
        if (this.itemDefinitions == null) {
            this.itemDefinitions = new ArrayList<>();
            for (TDefinitions definitions: this.allDefinitions) {
                this.itemDefinitions.addAll(definitions.getItemDefinition());
            }
        }
        return this.itemDefinitions;
    }

    public List<TDRGElement> drgElements(TDefinitions definitions) {
        List<TDRGElement> result = new ArrayList<>();
        drgElements(definitions, result);
        return result;
    }

    public List<TDecision> decisions(TDefinitions definitions) {
        List<TDecision> result = new ArrayList<>();
        decisions(definitions, result);
        return result;
    }

    public List<TInputData> inputDatas(TDefinitions definitions) {
        List<TInputData> result = new ArrayList<>();
        inputDatas(definitions, result);
        return result;
    }

    public List<TBusinessKnowledgeModel> businessKnowledgeModels(TDefinitions definitions) {
        List<TBusinessKnowledgeModel> result = new ArrayList<>();
        businessKnowledgeModels(definitions, result);
        return result;
    }

    public List<TDecisionService> decisionServices(TDefinitions definitions) {
        List<TDecisionService> result = new ArrayList<>();
        decisionServices(definitions, result);
        return result;
    }

    public List<TItemDefinition> itemDefinitions(TDefinitions definitions) {
        return definitions.getItemDefinition();
    }

    protected void drgElements(TDefinitions definitions, List<TDRGElement> result) {
        for (JAXBElement<? extends TDRGElement> jaxbElement : definitions.getDrgElement()) {
            TDRGElement element = jaxbElement.getValue();
            result.add(element);
        }
    }

    protected void decisions(TDefinitions definitions, List<TDecision> result) {
        for (JAXBElement<? extends TDRGElement> jaxbElement : definitions.getDrgElement()) {
            TDRGElement element = jaxbElement.getValue();
            if (element instanceof TDecision) {
                result.add((TDecision) element);
            }
        }
    }

    protected void inputDatas(TDefinitions definitions, List<TInputData> result) {
        for (JAXBElement<? extends TDRGElement> jaxbElement : definitions.getDrgElement()) {
            TDRGElement element = jaxbElement.getValue();
            if (element instanceof TInputData) {
                result.add((TInputData) element);
            }
        }
    }

    protected void businessKnowledgeModels(TDefinitions definitions, List<TBusinessKnowledgeModel> result) {
        for (JAXBElement<? extends TDRGElement> jaxbElement : definitions.getDrgElement()) {
            TDRGElement element = jaxbElement.getValue();
            if (element instanceof TBusinessKnowledgeModel) {
                result.add((TBusinessKnowledgeModel) element);
            }
        }
    }

    protected void decisionServices(TDefinitions definitions, List<TDecisionService> result) {
        for (JAXBElement<? extends TDRGElement> jaxbElement : definitions.getDrgElement()) {
            TDRGElement element = jaxbElement.getValue();
            if (element instanceof TDecisionService) {
                result.add((TDecisionService) element);
            }
        }
    }

    public List<TItemDefinition> sortItemComponent(TItemDefinition itemDefinition) {
        if (itemDefinition == null || itemDefinition.getItemComponent() == null) {
            return new ArrayList<>();
        }
        List<TItemDefinition> children = new ArrayList<>(itemDefinition.getItemComponent());
        children.sort((o1, o2) -> {
            if (o1 == null && o2 == null) {
                return 0;
            } else if (o1 == null) {
                return 1;
            } else if (o2 == null) {
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

    protected TItemDefinition next(TItemDefinition itemDefinition) {
        if (itemDefinition == null
                || itemDefinition.isIsCollection()
                || !isEmpty(itemDefinition.getItemComponent())
                || itemDefinition.getTypeRef() == null) {
            return null;
        }
        return lookupItemDefinition(QualifiedName.toQualifiedName(itemDefinition.getTypeRef()));
    }

    protected void sortDRGElements(List<JAXBElement<? extends TDRGElement>> result) {
        result.sort(Comparator.comparing((JAXBElement<? extends TDRGElement> o) -> removeSingleQuotes(o.getValue().getName())));
    }

    public void sortNamedElements(List<? extends TNamedElement> result) {
        result.sort(Comparator.comparing((TNamedElement o) -> removeSingleQuotes(o.getName())));
    }

    public TDRGElement findDRGElementByRef(TDRGElement parent, String href) {
        TDefinitions definitions = findChildDefinitions(parent, href);
        String key = makeRef(definitions.getNamespace(), href);
        if (!this.drgElementByRef.containsKey(key)) {
            TDRGElement value = null;
            for (TDRGElement element : drgElements(definitions)) {
                if (sameId(element, href)) {
                    value = element;
                    break;
                }
            }
            this.drgElementByRef.put(key, value);
        }
        TDRGElement result = this.drgElementByRef.get(key);
        if (result == null) {
            throw new DMNRuntimeException(String.format("Cannot find DRG element for href='%s'", href));
        } else {
            return result;
        }
    }

    public TDecision findDecisionByRef(TDRGElement parent, String href) {
        TDefinitions definitions = findChildDefinitions(parent, href);
        String key = makeRef(definitions.getNamespace(), href);
        if (!this.decisionByRef.containsKey(key)) {
            TDecision value = null;
            for (TDecision decision : decisions(definitions)) {
                if (sameId(decision, href)) {
                    value = decision;
                    break;
                }
            }
            this.decisionByRef.put(key, value);
        }
        TDecision result = this.decisionByRef.get(key);
        if (result == null) {
            throw new DMNRuntimeException(String.format("Cannot find decision for href='%s'", href));
        } else {
            return result;
        }
    }

    public TInputData findInputDataByRef(TDRGElement parent, String href) {
        TDefinitions definitions = findChildDefinitions(parent, href);
        String key = makeRef(definitions.getNamespace(), href);
        if (!this.inputDataByRef.containsKey(key)) {
            TInputData value = null;
            for (TInputData inputData : inputDatas(definitions)) {
                if (sameId(inputData, href)) {
                    value = inputData;
                    break;
                }
            }
            this.inputDataByRef.put(key, value);
        }
        TInputData result = this.inputDataByRef.get(key);
        if (result == null) {
            throw new DMNRuntimeException(String.format("Cannot find input data for href='%s'", href));
        } else {
            return result;
        }
    }

    public TInvocable findInvocableByRef(TDRGElement parent, String href) {
        TDefinitions definitions = findChildDefinitions(parent, href);
        String key = makeRef(definitions.getNamespace(), href);
        if (!this.businessKnowledgeModelByRef.containsKey(key)) {
            TBusinessKnowledgeModel value = null;
            for (TBusinessKnowledgeModel knowledgeModel : businessKnowledgeModels(definitions)) {
                if (sameId(knowledgeModel, href)) {
                    value = knowledgeModel;
                    break;
                }
            }
            this.businessKnowledgeModelByRef.put(key, value);
        }
        TInvocable result = this.businessKnowledgeModelByRef.get(key);
        if (result != null) {
            return result;
        }
        if (!this.decisionServiceByRef.containsKey(key)) {
            TDecisionService value = null;
            for (TDecisionService service : decisionServices(definitions)) {
                if (sameId(service, href)) {
                    value = service;
                    break;
                }
            }
            this.decisionServiceByRef.put(key, value);
        }
        result = this.decisionServiceByRef.get(key);
        if (result == null) {
            throw new DMNRuntimeException(String.format("Cannot find invocable (knowledge model or decision service) for href='%s'", href));
        } else {
            return result;
        }
    }

    protected TDefinitions findChildDefinitions(TDRGElement parent, String href) {
        String namespace = extractNamespace(href);
        if (StringUtils.isEmpty(namespace)) {
            return this.elementMap.get(parent);
        } else {
            return this.definitionsMap.get(namespace);
        }
    }

    public TBusinessKnowledgeModel findKnowledgeModelByName(String name) {
        if (!this.knowledgeModelByName.containsKey(name)) {
            TBusinessKnowledgeModel value = null;
            for (TBusinessKnowledgeModel knowledgeModel : businessKnowledgeModels()) {
                if (sameName(knowledgeModel, name)) {
                    value = knowledgeModel;
                    break;
                }
            }
            this.knowledgeModelByName.put(name, value);
        }
        TBusinessKnowledgeModel result = this.knowledgeModelByName.get(name);
        if (result == null) {
            throw new DMNRuntimeException(String.format("Cannot find business knowledge model for name='%s'", name));
        } else {
            return result;
        }
    }

    public TDecisionService findDecisionServiceByName(String name) {
        if (!this.decisionServiceByName.containsKey(name)) {
            TDecisionService value = null;
            for (TDecisionService service : decisionServices()) {
                if (sameName(service, name)) {
                    value = service;
                    break;
                }
            }
            this.decisionServiceByName.put(name, value);
        }
        TDecisionService result = this.decisionServiceByName.get(name);
        if (result == null) {
            throw new DMNRuntimeException(String.format("Cannot find decision service for name='%s'", name));
        } else {
            return result;
        }
    }

    public TDRGElement findDRGElementByName(String name) {
        if (!this.drgElementByName.containsKey(name)) {
            TDRGElement value = null;
            for (TDRGElement element : drgElements()) {
                if (sameName(element, name)) {
                    value = element;
                    break;
                }
            }
            this.drgElementByName.put(name, value);
        }
        TDRGElement result = this.drgElementByName.get(name);
        if (result == null) {
            throw new DMNRuntimeException(String.format("Cannot find element for name='%s'", name));
        } else {
            return result;
        }
    }

    public boolean sameId(TDMNElement element, String href) {
        String id = extractId(href);
        return element.getId().equals(id);
    }

    protected boolean sameName(TNamedElement element, String href) {
        return element.getName().equals(href);
    }

    public List<TDecision> directSubDecisions(TDRGElement element) {
        List<TDecision> result = new ArrayList<>();
        if (element instanceof TDecision) {
            for (TInformationRequirement ir : ((TDecision) element).getInformationRequirement()) {
                TDMNElementReference requiredDecision = ir.getRequiredDecision();
                if (requiredDecision != null) {
                    TDecision childDecision = findDecisionByRef(element, requiredDecision.getHref());
                    result.add(childDecision);
                }
            }
        } else if (element instanceof TDecisionService) {
            for (TDMNElementReference outputDecisionRef : ((TDecisionService) element).getOutputDecision()) {
                TDecision childDecision = findDecisionByRef(element, outputDecisionRef.getHref());
                result.add(childDecision);
            }
        }
        sortNamedElements(result);
        return result;
    }

    public Collection<TDecision> allSubDecisions(TDRGElement element) {
        Set<TDecision> result = new LinkedHashSet<>();
        collectSubDecisions(element, result);
        return result;
    }

    protected void collectSubDecisions(TDRGElement element, Collection<TDecision> decisions) {
        decisions.addAll(directSubDecisions(element));
        for (TDecision child : directSubDecisions(element)) {
            collectSubDecisions(child, decisions);
        }
    }

    public List<TDecision> topologicalSort(TDRGElement decision) {
        List<TDecision> result = new ArrayList<>();
        topologicalSort((TDecision)decision, result);
        result.remove(decision);
        return result;
    }

    protected void topologicalSort(TDecision parent, List<TDecision> decisions) {
        if (!decisions.contains(parent)) {
            for(TInformationRequirement ir: parent.getInformationRequirement()) {
                TDMNElementReference requiredDecision = ir.getRequiredDecision();
                if (requiredDecision != null) {
                    TDecision child = findDecisionByRef(parent, requiredDecision.getHref());
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

    protected void topologicalSortWithMarkers(TDecision parent, List<Object> objects) {
        if (!objects.contains(parent)) {
            objects.add(new StartMarker(parent));
            for (TInformationRequirement ir: parent.getInformationRequirement()) {
                TDMNElementReference requiredDecision = ir.getRequiredDecision();
                if (requiredDecision != null) {
                    TDecision child = findDecisionByRef(parent, requiredDecision.getHref());
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
            return directInputDatas(element, informationRequirement);
        } else {
            return new ArrayList<>();
        }
    }

    protected List<TInputData> directInputDatas(TDRGElement parent, List<TInformationRequirement> informationRequirement) {
        List<TInputData> result = new ArrayList<>();
        for (TInformationRequirement ir : informationRequirement) {
            TDMNElementReference requiredInput = ir.getRequiredInput();
            if (requiredInput != null) {
                TInputData inputData = findInputDataByRef(parent, requiredInput.getHref());
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
                TInvocable invocable = findInvocableByRef(element, reference.getHref());
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

    protected void collectInvocables(TDRGElement element, Set<TInvocable> accumulator) {
        accumulator.addAll(directSubInvocables(element));
        for (TDRGElement child : directSubInvocables(element)) {
            collectInvocables(child, accumulator);
        }
    }

    public List<TDRGElement> directDRGElements(TDRGElement element) {
        List<TDRGElement> result = new ArrayList<>();
        result.addAll(directInputDatas(element));
        result.addAll(directSubDecisions(element));
        result.addAll(directSubInvocables(element));
        return result;
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
        return this.findDecisionByRef(decisionService, outputDecisionList.get(0).getHref());
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
        } else if (element instanceof TDecision) {
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

    public String importName(TDMNElementReference reference) {
        if (reference != null) {
            String href = reference.getHref();
            String namespace = extractNamespace(href);
            if (namespace != null) {
                String prefix = this.prefixNamespaceMappings.getPrefix(namespace);
                return prefix;
            }
        }
        return  null;
    }

    public String importNameForId(TDMNElementReference reference, String id) {
        if (reference != null) {
            String href = reference.getHref();
            if (href.contains(id)) {
                String namespace = extractNamespace(href);
                if (namespace != null) {
                    String prefix = this.prefixNamespaceMappings.getPrefix(namespace);
                    return prefix;
                }
            }
        }
        return  null;
    }

    protected static String makeRef(String namespace, String href) {
        if (StringUtils.isEmpty(namespace)) {
            return href;
        } else {
            return namespace + href;
        }
    }

    protected String extractNamespace(String href) {
        String namespace = null;
        if (hasNamespace(href)) {
            namespace = href.substring(0, href.indexOf('#'));
        }
        return namespace;
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

    protected static boolean hasNamespace(String href) {
        return href.startsWith("http://");
    }
}
