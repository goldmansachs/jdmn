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
import com.gs.dmn.runtime.interpreter.ImportPath;
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
    protected static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();

    private static final Logger LOGGER = LoggerFactory.getLogger(DMNModelRepository.class);

    protected final List<TDefinitions> allDefinitions = new ArrayList<>();

    protected final Map<String, TDefinitions> namespaceToDefinitions = new LinkedHashMap<>();

    protected final Map<TNamedElement, TDefinitions> elementToDefinitions = new LinkedHashMap<>();

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
    protected Map<String, TDRGElement> drgElementByRef = new LinkedHashMap<>();
    protected Map<String, TInputData> inputDataByRef = new LinkedHashMap<>();
    protected Map<String, TDecision> decisionByRef = new LinkedHashMap<>();
    protected Map<String, TBusinessKnowledgeModel> businessKnowledgeModelByRef = new LinkedHashMap<>();
    protected Map<String, TDecisionService> decisionServiceByRef = new LinkedHashMap<>();

    public DMNModelRepository() {
        this(OBJECT_FACTORY.createTDefinitions(), new PrefixNamespaceMappings());
    }

    public DMNModelRepository(TDefinitions definitions, PrefixNamespaceMappings prefixNamespaceMappings) {
        this(new Pair<>(definitions, prefixNamespaceMappings));
    }

    public DMNModelRepository(Pair<TDefinitions, PrefixNamespaceMappings> pair) {
        this(Arrays.asList(pair));
    }

    public DMNModelRepository(List<Pair<TDefinitions, PrefixNamespaceMappings>> pairList) {
        this.prefixNamespaceMappings = new PrefixNamespaceMappings();
        if (pairList != null) {
            for (Pair<TDefinitions, PrefixNamespaceMappings> pair: pairList) {
                TDefinitions definitions = pair.getLeft();
                this.allDefinitions.add(definitions);
                if (!this.namespaceToDefinitions.containsKey(definitions.getNamespace())) {
                    this.namespaceToDefinitions.put(definitions.getNamespace(), definitions);
                } else {
                    throw new DMNRuntimeException(String.format("Duplicated model namespace '%s'", definitions.getNamespace()));
                }
                this.prefixNamespaceMappings.merge(pair.getRight());
            }
        }

        // Process all definitions
        for(TDefinitions definitions: this.getAllDefinitions()) {
            // Normalize
            normalize(definitions);

            // Set derived properties
            for (TNamedElement element: itemDefinitions(definitions)) {
                this.elementToDefinitions.put(element, definitions);
            }
            for (TDRGElement element: drgElements(definitions)) {
                this.elementToDefinitions.put(element, definitions);
            }
        }
    }

    protected void normalize(TDefinitions definitions) {
        if (definitions != null) {
            sortDRGElements(definitions.getDrgElement());
            sortNamedElements(definitions.getItemDefinition());
        }
    }

    public Set<String> computeCachedElements(boolean cachingFlag, int cachingThreshold) {
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
            if (entry.getValue() > cachingThreshold) {
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
        int size = this.getAllDefinitions().size();
        if (size == 1) {
            return this.allDefinitions.get(0);
        } else {
            throw new DMNRuntimeException(String.format("Cannot resolve root DM, there are '%d' DMs", size));
        }
    }

    public List<TDefinitions> getAllDefinitions() {
        return this.allDefinitions;
    }

    public PrefixNamespaceMappings getPrefixNamespaceMappings() {
        return this.prefixNamespaceMappings;
    }

    public void addElementMap(TDRGElement element, TDefinitions definitions) {
        this.elementToDefinitions.put(element, definitions);
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

    public TDefinitions getModel(String namespace) {
        return this.namespaceToDefinitions.get(namespace);
    }

    public TDefinitions getModel(TNamedElement element) {
        return this.elementToDefinitions.get(element);
    }

    public String getNamespace(TNamedElement element) {
        TDefinitions definitions = this.elementToDefinitions.get(element);
        return definitions == null ? null : definitions.getNamespace();
    }

    public String getModelName(TNamedElement element) {
        TDefinitions definitions = this.elementToDefinitions.get(element);
        return definitions == null ? null : definitions.getName();
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
        TDefinitions model = getModel(itemDefinition);
        return lookupItemDefinition(model, QualifiedName.toQualifiedName(model, itemDefinition.getTypeRef()));
    }

    protected void sortDRGElements(List<JAXBElement<? extends TDRGElement>> elements) {
        elements.sort(Comparator.comparing((JAXBElement<? extends TDRGElement> o) -> removeSingleQuotes(o.getValue().getName())));
    }

    public void sortNamedElements(List<? extends TNamedElement> elements) {
        elements.sort(Comparator.comparing((TNamedElement o) -> removeSingleQuotes(o.getName())));
    }

    public void sortNamedElementReferences(List<? extends DRGElementReference<? extends TNamedElement>> references) {
        references.sort(Comparator.comparing((DRGElementReference<? extends TNamedElement> o) -> removeSingleQuotes(o.getElementName())));
    }

    public <T extends TDRGElement> List<T> selectElement(List<DRGElementReference<T>> references) {
        return references.stream().map(DRGElementReference::getElement).collect(Collectors.toList());
    }

    public List<? extends TDRGElement> selectDRGElement(List<DRGElementReference<? extends TDRGElement>> references) {
        return references.stream().map(DRGElementReference::getElement).collect(Collectors.toList());
    }

    public List<TDecision> topologicalSort(TDRGElement decision) {
        List<TDecision> result = new ArrayList<>();
        topologicalSort((TDecision) decision, result);
        result.remove(decision);
        return result;
    }

    protected void topologicalSort(TDecision parent, List<TDecision> decisions) {
        if (!decisions.contains(parent)) {
            for (TInformationRequirement ir: parent.getInformationRequirement()) {
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
        topologicalSortWithMarkers((TDecision) decision, objects);
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

    public TDRGElement findDRGElementByRef(String href) {
        try {
            if (!this.drgElementByRef.containsKey(href)) {
                String id = extractId(href);
                TDRGElement value = null;
                for (TDRGElement element: drgElements()) {
                    if (sameId(element, id)) {
                        value = element;
                        break;
                    }
                }
                this.drgElementByRef.put(href, value);
            }
            TDRGElement result = this.drgElementByRef.get(href);
            if (result == null) {
                throw new DMNRuntimeException(String.format("Cannot find DRG element for href='%s'", href));
            } else {
                return result;
            }
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot find DRG element for href='%s'", href), e);
        }
    }

    public TDRGElement findDRGElementByRef(TDRGElement parent, String href) {
        try {
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
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot find DRG element for href='%s'", href), e);
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
        if (definitions == null) {
            throw new DMNRuntimeException(String.format("Cannot find model for href='%s'", href));
        }
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
            return this.elementToDefinitions.get(parent);
        } else {
            return this.namespaceToDefinitions.get(namespace);
        }
    }

    public TBusinessKnowledgeModel findKnowledgeModelByName(String name) {
        if (!this.knowledgeModelByName.containsKey(name)) {
            List<TBusinessKnowledgeModel> value = new ArrayList<>();
            for (TBusinessKnowledgeModel knowledgeModel : businessKnowledgeModels()) {
                if (sameName(knowledgeModel, name)) {
                    value.add(knowledgeModel);
                }
            }
            if (value.size() == 1) {
                this.knowledgeModelByName.put(name, value.get(0));
            } else if (value.size() > 1) {
                throw new DMNRuntimeException(String.format("Found %s business knowledge models for name='%s'", value.size(), name));
            }
        }
        TBusinessKnowledgeModel result = this.knowledgeModelByName.get(name);
        if (result == null) {
            throw new DMNRuntimeException(String.format("Cannot find business knowledge model for name='%s'", name));
        } else {
            return result;
        }
    }

    public TDRGElement findDRGElementByName(String namespace, String name) {
        TDefinitions definitions = this.namespaceToDefinitions.get(namespace);
        if (definitions == null) {
            throw new DMNRuntimeException(String.format("Cannot find model for namespace '%s'", namespace));
        }
        for (TDRGElement element: drgElements(definitions)) {
            if (element.getName().equals(name)) {
                return element;
            }
        }
        throw new DMNRuntimeException(String.format("Cannot find element for name='%s'", name));
    }

    public TDRGElement findDRGElementByName(String name) {
        if (!this.drgElementByName.containsKey(name)) {
            List<TDRGElement> value = new ArrayList<>();
            for (TDRGElement element : drgElements()) {
                if (sameName(element, name)) {
                    value.add(element);
                }
            }
            if (value.size() == 1) {
                this.drgElementByName.put(name, value.get(0));
            } else if (value.size() > 1) {
                throw new DMNRuntimeException(String.format("Found %s business knowledge models for name='%s'", value.size(), name));
            }
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

    public <T extends TDRGElement> DRGElementReference<T> makeDRGElementReference(T element) {
        return makeDRGElementReference(new ImportPath(), element);
    }

    public <T extends TDRGElement> DRGElementReference<T> makeDRGElementReference(String importName, T element) {
        return makeDRGElementReference(new ImportPath(importName), element);
    }

    public <T extends TDRGElement> DRGElementReference<T> makeDRGElementReference(ImportPath importPath, T element) {
        return new DRGElementReference<>(importPath, getNamespace(element), getModelName(element), element);
    }

    public List<DRGElementReference<TInputData>> directInputDatas(TDRGElement parent) {
        List<DRGElementReference<TInputData>> result = new ArrayList<>();
        // Add reference for direct children
        List<TDMNElementReference> references = requiredInputDataReferences(parent);
        for (TDMNElementReference reference : references) {
            TInputData child = findInputDataByRef(parent, reference.getHref());
            if (child != null) {
                String importName = findImportName(parent, reference);
                result.add(makeDRGElementReference(importName, child));
            } else {
                throw new DMNRuntimeException(String.format("Cannot find InputData for '%s' in parent '%s'", reference.getHref(), parent.getName()));
            }
        }
        return result;
    }

    public List<DRGElementReference<TInputData>> allInputDatas(DRGElementReference<? extends TDRGElement> parentReference, DRGElementFilter drgElementFilter) {
        return drgElementFilter.filterInputs(collectAllInputDatas(parentReference));
    }

    protected List<DRGElementReference<TInputData>> collectAllInputDatas(DRGElementReference<? extends TDRGElement> parentReference) {
        TDRGElement parent = parentReference.getElement();
        ImportPath parentImportPath = parentReference.getImportPath();
        List<DRGElementReference<TInputData>> result = new ArrayList<>();
        // Add reference for direct children
        List<TDMNElementReference> references = requiredInputDataReferences(parent);
        for (TDMNElementReference reference: references) {
            TInputData child = findInputDataByRef(parent, reference.getHref());
            if (child != null) {
                String importName = findImportName(parent, reference);
                result.add(makeDRGElementReference(new ImportPath(parentImportPath, importName), child));
            } else {
                throw new DMNRuntimeException(String.format("Cannot find InputData for '%s' in parent '%s'", reference.getHref(), parent.getName()));
            }
        }

        // Process direct children and update reference
        List<TDMNElementReference> childReferences = requiredDecisionReferences(parent);
        for (TDMNElementReference reference: childReferences) {
            TDecision child = findDecisionByRef(parent, reference.getHref());
            if (child != null) {
                // Update reference for descendants
                String importName = findImportName(parent, reference);
                List<DRGElementReference<TInputData>> inputReferences = collectAllInputDatas(makeDRGElementReference(new ImportPath(parentImportPath, importName), child));
                result.addAll(inputReferences);
            } else {
                throw new DMNRuntimeException(String.format("Cannot find Decision for '%s' in parent '%s'", reference.getHref(), parent.getName()));
            }
        }
        return result;
    }

    public List<DRGElementReference<TDecision>> directSubDecisions(TDRGElement parent) {
        List<DRGElementReference<TDecision>> result = new ArrayList<>();
        if (parent instanceof TDecision) {
            // Add reference for direct children
            for (TInformationRequirement ir : ((TDecision) parent).getInformationRequirement()) {
                TDMNElementReference reference = ir.getRequiredDecision();
                if (reference != null) {
                    TDecision child = findDecisionByRef(parent, reference.getHref());
                    String importName = findImportName(parent, reference);
                    result.add(makeDRGElementReference(importName, child));
                }
            }
        } else if (parent instanceof TDecisionService) {
            // Add reference for direct children
            for (TDMNElementReference outputDecisionRef : ((TDecisionService) parent).getOutputDecision()) {
                TDecision child = findDecisionByRef(parent, outputDecisionRef.getHref());
                String importName = findImportName(parent, outputDecisionRef);
                result.add(makeDRGElementReference(importName, child));
            }
        }
        sortNamedElementReferences(result);
        return result;
    }

    public List<DRGElementReference<TInvocable>> directSubInvocables(TDRGElement element) {
        List<DRGElementReference<TInvocable>> result = new ArrayList<>();
        // Add reference for direct children
        List<TKnowledgeRequirement> knowledgeRequirements = knowledgeRequirements(element);
        for (TKnowledgeRequirement kr : knowledgeRequirements) {
            TDMNElementReference reference = kr.getRequiredKnowledge();
            if (reference != null) {
                TInvocable invocable = findInvocableByRef(element, reference.getHref());
                if (invocable != null) {
                    String importName = findImportName(element, reference);
                    result.add(makeDRGElementReference(importName, invocable));
                } else {
                    throw new DMNRuntimeException(String.format("Cannot find Invocable for '%s'", reference.getHref()));
                }
            }
        }
        return result;
    }

    public List<DRGElementReference<? extends TDRGElement>> directDRGElements(TDRGElement element) {
        List<DRGElementReference<? extends TDRGElement>> result = new ArrayList<>();
        result.addAll(directInputDatas(element));
        result.addAll(directSubDecisions(element));
        result.addAll(directSubInvocables(element));
        return result;
    }

    protected List<TDMNElementReference> requiredInputDataReferences(TDRGElement parent) {
        List<TDMNElementReference> references = new ArrayList<>();
        if (parent instanceof TDecision) {
            for (TInformationRequirement ir : ((TDecision) parent).getInformationRequirement()) {
                TDMNElementReference requiredInput = ir.getRequiredInput();
                if (requiredInput != null) {
                    references.add(requiredInput);
                }
            }
        }

        return references;
    }

    protected List<TDMNElementReference> requiredDecisionReferences(TDRGElement parent) {
        List<TDMNElementReference> references = new ArrayList<>();
        if (parent instanceof TDecision) {
            for (TInformationRequirement ir : ((TDecision) parent).getInformationRequirement()) {
                TDMNElementReference requiredDecision = ir.getRequiredDecision();
                if (requiredDecision != null) {
                    references.add(requiredDecision);
                }
            }
        } else if (parent instanceof TDecisionService) {
            references.addAll(((TDecisionService) parent).getOutputDecision());
        }
        return references;
    }

    protected List<TKnowledgeRequirement> knowledgeRequirements(TDRGElement element) {
        List<TKnowledgeRequirement> knowledgeRequirements;
        if (element instanceof TDecision) {
            knowledgeRequirements = ((TDecision) element).getKnowledgeRequirement();
        } else if (element instanceof TBusinessKnowledgeModel) {
            knowledgeRequirements = ((TBusinessKnowledgeModel) element).getKnowledgeRequirement();
        } else {
            knowledgeRequirements = new ArrayList<>();
        }
        return knowledgeRequirements;
    }

    public TDecisionTable decisionTable(TDRGElement element) {
        TExpression expression = expression(element);
        if (expression instanceof TDecisionTable) {
            return (TDecisionTable) expression;
        } else {
            throw new DMNRuntimeException(String.format("Cannot find decision table in element '%s'", element.getName()));
        }
    }

    public TItemDefinition lookupItemDefinition(TDefinitions model, QualifiedName typeRef) {
        if (typeRef == null) {
            return null;
        }
        String importName = typeRef.getNamespace();
        if (DMNVersion.LATEST.getFeelPrefix().equals(importName)) {
            return null;
        }

        if (model == null) {
            return lookupItemDefinition(itemDefinitions(), typeRef);
        } else {
            for (TImport import_: model.getImport()) {
                if (import_.getName().equals(importName)) {
                    String modelNamespace = import_.getNamespace();
                    model = this.getModel(modelNamespace);
                    if (model == null) {
                        throw new DMNRuntimeException(String.format("Cannot find DM for '%s'", modelNamespace));
                    }
                }
            }
            return lookupItemDefinition(itemDefinitions(model), typeRef);
        }
    }

    protected TItemDefinition lookupItemDefinition(List<TItemDefinition> itemDefinitionList, QualifiedName typeRef) {
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

    public List<DRGElementReference<? extends TDRGElement>> sortedUniqueInputs(TDecision decision, DRGElementFilter drgElementFilter) {
        List<DRGElementReference<? extends TDRGElement>> inputs = new ArrayList<>();
        inputs.addAll(directInputDatas(decision));
        inputs.addAll(directSubDecisions(decision));
        List<DRGElementReference<? extends TDRGElement>> result = drgElementFilter.filterDRGElements(inputs);
        sortNamedElementReferences(result);
        return result;
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
        TDefinitions model = this.getModel(element);
        QualifiedName typeRef = null;
        if (element instanceof TInformationItem) {
            typeRef = QualifiedName.toQualifiedName(model, ((TInformationItem) element).getTypeRef());
        }
        if (typeRef == null) {
            // Derive from variable
            TInformationItem variable = variable(element);
            if (variable != null) {
                typeRef = QualifiedName.toQualifiedName(model, variable.getTypeRef());
            }
        }
        if (typeRef == null) {
            // Derive from expression
            TExpression expression = expression(element);
            if (expression != null) {
                typeRef = QualifiedName.toQualifiedName(model, expression.getTypeRef());
                if (typeRef == null) {
                    if (expression instanceof TContext) {
                        // Derive from return entry
                        List<TContextEntry> contextEntryList = ((TContext) expression).getContextEntry();
                        for(TContextEntry ce: contextEntryList) {
                            if (ce.getVariable() == null) {
                                JAXBElement<? extends TExpression> returnElement = ce.getExpression();
                                if (returnElement != null) {
                                    typeRef = QualifiedName.toQualifiedName(model, returnElement.getValue().getTypeRef());
                                }
                            }
                        }
                    } else if (expression instanceof TDecisionTable) {
                        // Derive from output clause
                        List<TOutputClause> outputList = ((TDecisionTable) expression).getOutput();
                        if (outputList.size() == 1) {
                            typeRef = QualifiedName.toQualifiedName(model, outputList.get(0).getTypeRef());
                            if (typeRef == null) {
                                // Derive from rules
                                List<TDecisionRule> ruleList = ((TDecisionTable) expression).getRule();
                                List<TLiteralExpression> outputEntry = ruleList.get(0).getOutputEntry();
                                typeRef = QualifiedName.toQualifiedName(model, outputEntry.get(0).getTypeRef());
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

    public String findChildImportName(TDRGElement parent, TDRGElement child) {
        // Collect references
        List<TDMNElementReference> references = new ArrayList<>();
        if (parent instanceof TDecision) {
            for (TInformationRequirement ir: ((TDecision) parent).getInformationRequirement()) {
                TDMNElementReference reference = ir.getRequiredDecision();
                if (reference != null) {
                    references.add(reference);
                }
                reference = ir.getRequiredInput();
                if (reference != null) {
                    references.add(reference);
                }
            }
            for (TKnowledgeRequirement bkr: ((TDecision) parent).getKnowledgeRequirement()) {
                TDMNElementReference reference = bkr.getRequiredKnowledge();
                if (reference != null) {
                    references.add(reference);
                }
            }
        } else if (parent instanceof TBusinessKnowledgeModel) {
            for (TKnowledgeRequirement bkr: ((TBusinessKnowledgeModel) parent).getKnowledgeRequirement()) {
                TDMNElementReference reference = bkr.getRequiredKnowledge();
                if (reference != null) {
                    references.add(reference);
                }
            }
        } else if (parent instanceof TDecisionService) {
            references.addAll(((TDecisionService) parent).getInputData());
            references.addAll(((TDecisionService) parent).getInputDecision());
            references.addAll(((TDecisionService) parent).getOutputDecision());
            references.addAll(((TDecisionService) parent).getEncapsulatedDecision());
        }

        // Find reference for child
        String childNamespace = getNamespace(child);
        String childRefSuffix = childNamespace +  "#" + child.getId();
        for (TDMNElementReference reference: references) {
            if (reference.getHref().endsWith(childRefSuffix))  {
                return findImportName(parent, reference);
            }
        }
        return null;
    }

    public String findImportName(TDRGElement parent, TDMNElementReference reference) {
        TDefinitions parentDefinitions = this.elementToDefinitions.get(parent);
        String referenceNamespace = extractNamespace(reference.getHref());
        if (referenceNamespace == null) {
            return null;
        } else if (parentDefinitions.getNamespace().equals(referenceNamespace)) {
            return null;
        } else {
            for (TImport import_ : parentDefinitions.getImport()) {
                if (import_.getNamespace().equals(referenceNamespace)) {
                    return import_.getName();
                }
            }
            throw new DMNRuntimeException(String.format("Cannot find import prefix for '%s' in model '%s'", reference.getHref(), parentDefinitions.getName()));
        }
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
        return href != null && href.indexOf('#') > 0;
    }
}
