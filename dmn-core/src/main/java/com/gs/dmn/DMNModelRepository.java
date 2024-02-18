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

import com.gs.dmn.ast.*;
import com.gs.dmn.ast.dmndi.DMNDI;
import com.gs.dmn.ast.dmndi.DMNDiagram;
import com.gs.dmn.ast.dmndi.DMNStyle;
import com.gs.dmn.ast.dmndi.DiagramElement;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNVersion;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.gs.dmn.ast.TBuiltinAggregator.COUNT;
import static com.gs.dmn.ast.TBuiltinAggregator.SUM;

public class DMNModelRepository {
    public static final String FREE_TEXT_LANGUAGE = "free_text";
    protected static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();
    private static final Pattern WORD = Pattern.compile("\\w+");

    protected static final Logger LOGGER = LoggerFactory.getLogger(DMNModelRepository.class);

    protected final List<TDefinitions> definitionsList;

    // Derived properties to optimise search
    protected final List<TDefinitions> allDefinitions = new ArrayList<>();
    protected final Map<String, TDefinitions> namespaceToDefinitions = new LinkedHashMap<>();
    protected final Map<TNamedElement, TDefinitions> elementToDefinitions = new LinkedHashMap<>();
    protected List<TInvocable> invocables;
    protected List<TItemDefinition> itemDefinitions;
    protected Map<String, TDRGElement> drgElementByName = new LinkedHashMap<>();
    protected Map<String, TInvocable> invocablesByName = new LinkedHashMap<>();
    protected Map<String, TDRGElement> drgElementByRef = new LinkedHashMap<>();
    protected Map<TDefinitions, List<TDRGElement>> drgElementsByModel = new LinkedHashMap<>();
    protected Map<TDefinitions, List<TDecision>> decisionsByModel = new LinkedHashMap<>();
    protected Map<TDefinitions, List<TInputData>> inputDatasByModel = new LinkedHashMap<>();
    protected Map<TDefinitions, List<TBusinessKnowledgeModel>> bkmsByModel = new LinkedHashMap<>();
    protected Map<TDefinitions, List<TDecisionService>> dssByModel = new LinkedHashMap<>();

    public DMNModelRepository() {
        this(OBJECT_FACTORY.createTDefinitions());
    }

    public DMNModelRepository(TDefinitions definitions) {
        this(Collections.singletonList(definitions));
    }

    public DMNModelRepository(List<TDefinitions> definitionsList) {
        this.definitionsList = definitionsList;
        // Normalize definitions
        this.definitionsList.sort(Comparator.comparing((TDefinitions o) -> NameUtils.removeSingleQuotes(o.getName())));
        if (definitionsList != null) {
            for (TDefinitions definitions: definitionsList) {
                this.allDefinitions.add(definitions);
                if (!this.namespaceToDefinitions.containsKey(definitions.getNamespace())) {
                    this.namespaceToDefinitions.put(definitions.getNamespace(), definitions);
                } else {
                    throw new DMNRuntimeException("Duplicated model namespace '%s'".formatted(definitions.getNamespace()));
                }
            }
        }

        // Process all definitions
        for (TDefinitions definitions: this.getAllDefinitions()) {
            // Normalize
            normalize(definitions);

            // Set derived properties
            for (TNamedElement element: findItemDefinitions(definitions)) {
                this.elementToDefinitions.put(element, definitions);
            }
            for (TDRGElement element: findDRGElements(definitions)) {
                this.elementToDefinitions.put(element, definitions);
            }
        }
    }

    public DMNModelRepository copy() {
        return new DMNModelRepository(this.definitionsList);
    }

    protected void normalize(TDefinitions definitions) {
        if (definitions != null) {
            sortDRGElements(definitions.getDrgElement());
            sortNamedElements(definitions.getItemDefinition());
            sortDMNDI(definitions.getDMNDI());
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
            for (TDecision decision: findDecisions(definitions)) {
                addCachedChildren(definitions, decision, parentMap, map);
            }
            for (TDecisionService service: findDSs(definitions)) {
                addCachedChildren(definitions, service, parentMap, map);
            }
        }

        Set<String> result = new LinkedHashSet<>();
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
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
        for (TInformationRequirement ir: decision.getInformationRequirement()) {
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

    private void addCachedChildren(TDefinitions definitions, TDecisionService decisionService, Map<String, TDecision> parentMap, Map<String, Integer> map) {
        for (TDMNElementReference inputDecisionRef: decisionService.getInputDecision()) {
            if (inputDecisionRef != null) {
                String href = inputDecisionRef.getHref();
                if (!hasNamespace(href)) {
                    href = makeRef(definitions.getNamespace(), href);
                }
                map.compute(href, (k, v) -> v == null ? 1 : v + 1);
                TDecision inputDecision = findDecisionByRef(decisionService, href);
                parentMap.put(href, inputDecision);
            }
        }
    }

    public TDefinitions getRootDefinitions() {
        int size = this.getAllDefinitions().size();
        if (size == 1) {
            return this.allDefinitions.get(0);
        } else {
            throw new DMNRuntimeException("Cannot resolve root DM, there are '%d' DMs".formatted(size));
        }
    }

    public List<TDefinitions> getAllDefinitions() {
        return this.allDefinitions;
    }

    // Model name might not be unique
    public List<TDefinitions> findDefinitionByName(String modelName) {
        List<TDefinitions> result = new ArrayList<>();
        for (TDefinitions definitions : this.allDefinitions) {
            if (modelName.equals(definitions.getName())) {
                result.add(definitions);
            }
        }
        return result;
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

    public String makeLocation(TDefinitions definitions, TDMNElement element) {
        if (definitions == null && element == null) {
            return null;
        }

        List<String> locationParts = new ArrayList<>();
        addModelCoordinates(definitions, element, locationParts);
        addElementCoordinates(element, locationParts);
        return locationParts.isEmpty() ? null : "(%s)".formatted(String.join(", ", locationParts));
    }

    protected void addModelCoordinates(TDefinitions definitions, TDMNElement element, List<String> locationParts) {
        if (definitions != null) {
            String modelName = definitions.getName();
            if (!StringUtils.isBlank(modelName)) {
                locationParts.add("model='%s'".formatted(modelName));
            }
        }
    }

    protected void addElementCoordinates(TDMNElement element, List<String> locationParts) {
        if (element != null) {
            String id = element.getId();
            String label = element.getLabel();
            String name = element instanceof TNamedElement tne ? tne.getName() : null;
            if (!StringUtils.isBlank(label)) {
                locationParts.add("label='%s'".formatted(label));
            }
            if (!StringUtils.isBlank(name)) {
                locationParts.add("name='%s'".formatted(name));
            }
            if (!StringUtils.isBlank(id)) {
                locationParts.add("id='%s'".formatted(id));
            }
        }
    }

    public String getNamespace(TNamedElement element) {
        TDefinitions definitions = this.elementToDefinitions.get(element);
        return definitions == null ? null : definitions.getNamespace();
    }

    public String getModelName(TNamedElement element) {
        TDefinitions definitions = this.elementToDefinitions.get(element);
        return definitions == null ? null : definitions.getName();
    }

    private List<TInvocable> findAllInvocables() {
        if (this.invocables == null) {
            this.invocables = new ArrayList<>();
            for (TDefinitions definitions: this.allDefinitions) {
                collectInvocables(definitions, this.invocables);
            }
        }
        return this.invocables;
    }

    private List<TItemDefinition> findAllItemDefinitions() {
        if (this.itemDefinitions == null) {
            this.itemDefinitions = new ArrayList<>();
            for (TDefinitions definitions: this.allDefinitions) {
                this.itemDefinitions.addAll(definitions.getItemDefinition());
            }
        }
        return this.itemDefinitions;
    }

    public boolean isRecursiveBKM(TDRGElement element) {
        return element instanceof TBusinessKnowledgeModel tbkm && isRecursive(tbkm.getEncapsulatedLogic().getExpression(), element.getName());
    }

    public List<TDRGElement> findDRGElements(TDefinitions definitions) {
        List<TDRGElement> result = this.drgElementsByModel.get(definitions);
        if (result == null) {
            result = new ArrayList<>();
            collectDRGElements(definitions, result);
            this.drgElementsByModel.put(definitions, result);
        }
        return result;
    }

    public List<TDecision> findDecisions(TDefinitions definitions) {
        List<TDecision> result = this.decisionsByModel.get(definitions);
        if (result == null) {
            result = new ArrayList<>();
            collectDecisions(definitions, result);
            this.decisionsByModel.put(definitions, result);
        }
        return result;
    }

    public List<TInputData> findInputDatas(TDefinitions definitions) {
        List<TInputData> result = this.inputDatasByModel.get(definitions);
        if (result == null) {
            result = new ArrayList<>();
            collectInputDatas(definitions, result);
            this.inputDatasByModel.put(definitions, result);
        }
        return result;
    }

    public List<TBusinessKnowledgeModel> findBKMs(TDefinitions definitions) {
        List<TBusinessKnowledgeModel> result = this.bkmsByModel.get(definitions);
        if (result == null) {
            result = new ArrayList<>();
            collectBKMs(definitions, result);
            this.bkmsByModel.put(definitions, result);
        }
        return result;
    }

    public List<TDecisionService> findDSs(TDefinitions definitions) {
        List<TDecisionService> result = this.dssByModel.get(definitions);
        if (result == null) {
            result = new ArrayList<>();
            collectDSs(definitions, result);
            this.dssByModel.put(definitions, result);
        }
        return result;
    }

    public List<TItemDefinition> findItemDefinitions(TDefinitions definitions) {
        return definitions.getItemDefinition();
    }

    protected void collectDRGElements(TDefinitions definitions, List<TDRGElement> result) {
        for (TDRGElement element: definitions.getDrgElement()) {
            result.add(element);
        }
    }

    protected void collectDecisions(TDefinitions definitions, List<TDecision> result) {
        for (TDRGElement element: definitions.getDrgElement()) {
            if (element instanceof TDecision decision) {
                result.add(decision);
            }
        }
    }

    protected void collectInputDatas(TDefinitions definitions, List<TInputData> result) {
        for (TDRGElement element: definitions.getDrgElement()) {
            if (element instanceof TInputData data) {
                result.add(data);
            }
        }
    }

    protected void collectBKMs(TDefinitions definitions, List<TBusinessKnowledgeModel> result) {
        for (TDRGElement element: definitions.getDrgElement()) {
            if (element instanceof TBusinessKnowledgeModel model) {
                result.add(model);
            }
        }
    }

    protected void collectDSs(TDefinitions definitions, List<TDecisionService> result) {
        for (TDRGElement element: definitions.getDrgElement()) {
            if (element instanceof TDecisionService service) {
                result.add(service);
            }
        }
    }

    protected void collectInvocables(TDefinitions definitions, List<TInvocable> result) {
        for (TDRGElement element: definitions.getDrgElement()) {
            if (element instanceof TInvocable invocable) {
                result.add(invocable);
            }
        }
    }

    public boolean hasComponents(TItemDefinition itemDefinition) {
        return !this.isEmpty(itemDefinition.getItemComponent());
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

    public Pair<TItemDefinition, TUnaryTests> normalize(TItemDefinition itemDefinition) {
        // 7.3.3. ItemDefinition metamodel If an ItemDefinition element contains one or
        // more allowedValues, the allowedValues specifies the complete range of values that this
        // ItemDefinition represents. If an ItemDefinition element does not contain allowedValues, its range
        // of allowed values is the full range of the referenced typeRef.
        // Scan the chain of ItemDefinitions and find the first allowedValues
        TUnaryTests restrictions = null;
        while (true) {
            TUnaryTests allowedValues = itemDefinition.getAllowedValues();
            if (restrictions == null) {
                restrictions =  allowedValues;
            }
            TItemDefinition next = next(itemDefinition);
            if (next != null) {
                itemDefinition = next;
            } else {
                break;
            }
        }
        return new Pair<>(itemDefinition, restrictions);
    }

    protected TItemDefinition next(TItemDefinition itemDefinition) {
        if (itemDefinition == null
                || itemDefinition.isIsCollection()
                || !isEmpty(itemDefinition.getItemComponent())
                || itemDefinition.getFunctionItem() != null
                || itemDefinition.getTypeRef() == null) {
            return null;
        }
        TDefinitions model = getModel(itemDefinition);
        return lookupItemDefinition(model, QualifiedName.toQualifiedName(model, itemDefinition.getTypeRef()));
    }

    protected void sortDRGElements(List<? extends TDRGElement> elements) {
        elements.sort(Comparator.comparing((TDRGElement o) -> NameUtils.removeSingleQuotes(o.getName())));
    }

    public void sortNamedElements(List<? extends TNamedElement> elements) {
        elements.sort(Comparator.comparing((TNamedElement o) -> NameUtils.removeSingleQuotes(o.getName())));
    }

    private void sortDMNDI(DMNDI dmndi) {
        if (dmndi != null) {
            sortDMNDiagrams(dmndi);
            dmndi.getDMNStyle().sort(Comparator.comparing(this::styleKey));
        }
    }

    private String styleKey(DMNStyle s) {
        if (s == null) {
            return "";
        }
        return s.getId();
    }

    private void sortDMNDiagrams(DMNDI dmndi) {
        List<DMNDiagram> diagrams = dmndi.getDMNDiagram();
        diagrams.sort(Comparator.comparing(this::diagramKey));
        for (DMNDiagram d: diagrams) {
            d.getDMNDiagramElement().sort(Comparator.comparing(this::diagramElementKey));
        }
    }

    private String diagramKey(DMNDiagram d) {
        if (d == null) {
            return "";
        }
        return "%s-%s".formatted(d.getName(), d.getId());
    }

    private String diagramElementKey(DiagramElement e) {
        if (e == null) {
            return "";
        }
        return "%s-%s".formatted(e.getClass().getSimpleName(), e.getId());
    }

    public void sortNamedElementReferences(List<? extends DRGElementReference<? extends TNamedElement>> references) {
        references.sort(Comparator.comparing((DRGElementReference<? extends TNamedElement> o) -> NameUtils.removeSingleQuotes(o.getElementName())));
    }

    public TDRGElement findDRGElementByRef(TDRGElement parent, String href) {
        try {
            TDefinitions definitions = findChildDefinitions(parent, href);
            if (definitions == null) {
                throw new DMNRuntimeException("Cannot find model for href='%s'".formatted(href));
            }
            String key = makeRef(definitions.getNamespace(), href);
            TDRGElement result = this.drgElementByRef.get(key);
            if (result == null) {
                for (TDRGElement element: findDRGElements(definitions)) {
                    if (sameId(element, href)) {
                        result = element;
                        this.drgElementByRef.put(key, result);
                        break;
                    }
                }
            }
            if (result == null) {
                throw new DMNRuntimeException("Cannot find DRG element for href='%s'".formatted(href));
            } else {
                return result;
            }
        } catch (Exception e) {
            throw new DMNRuntimeException("Cannot find DRG element for href='%s'".formatted(href), e);
        }
    }

    public TDecision findDecisionByRef(TDRGElement parent, String href) {
        TDRGElement drgElement = findDRGElementByRef(parent, href);
        if (drgElement instanceof TDecision decision) {
            return decision;
        } else {
            throw new DMNRuntimeException("Cannot find Decision element for href='%s'".formatted(href));
        }
    }

    public TInputData findInputDataByRef(TDRGElement parent, String href) {
        TDRGElement drgElement = findDRGElementByRef(parent, href);
        if (drgElement instanceof TInputData data) {
            return data;
        } else {
            throw new DMNRuntimeException("Cannot find InputData element for href='%s'".formatted(href));
        }
    }

    public TInvocable findInvocableByRef(TDRGElement parent, String href) {
        TDRGElement drgElement = findDRGElementByRef(parent, href);
        if (drgElement instanceof TInvocable invocable) {
            return invocable;
        } else {
            throw new DMNRuntimeException("Cannot find TInvocable element for href='%s'".formatted(href));
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

    public TInvocable findInvocableByName(String name) {
        TInvocable result = this.invocablesByName.get(name);
        if (result == null) {
            List<TInvocable> value = new ArrayList<>();
            for (TInvocable invocable: findAllInvocables()) {
                if (sameName(invocable, name)) {
                    value.add(invocable);
                }
            }
            if (value.size() == 1) {
                result = value.get(0);
                this.invocablesByName.put(name, result);
            } else if (value.size() > 1) {
                throw new DMNRuntimeException("Found %s business knowledge models for name='%s'".formatted(value.size(), name));
            }
        }
        if (result == null) {
            throw new DMNRuntimeException("Cannot find business knowledge model for name='%s'".formatted(name));
        } else {
            return result;
        }
    }

    public TDRGElement findDRGElementByName(String namespace, String name) {
        TDefinitions definitions = this.namespaceToDefinitions.get(namespace);
        if (definitions == null) {
            throw new DMNRuntimeException("Cannot find model for namespace '%s'".formatted(namespace));
        }
        return findDRGElementByName(definitions, name);
    }

    public TDRGElement findDRGElementByName(TDefinitions definitions, String name) {
        if (definitions == null) {
            throw new DMNRuntimeException("Cannot find element for name='%s'. Missing DM".formatted(name));
        }
        for (TDRGElement element: findDRGElements(definitions)) {
            if (element.getName().equals(name)) {
                return element;
            }
        }
        throw new DMNRuntimeException("Cannot find element for name='%s'".formatted(name));
    }

    public TDRGElement findDRGElementByName(String name) {
        TDRGElement result = this.drgElementByName.get(name);
        if (result == null) {
            List<TDRGElement> value = new ArrayList<>();
            for (TDefinitions definitions: getAllDefinitions()) {
                for (TDRGElement element: findDRGElements(definitions)) {
                    if (sameName(element, name)) {
                        value.add(element);
                    }
                }
            }
            if (value.size() == 1) {
                result = value.get(0);
                this.drgElementByName.put(name, result);
            } else if (value.size() > 1) {
                throw new DMNRuntimeException("Found %s business knowledge models for name='%s'".formatted(value.size(), name));
            }
        }
        if (result == null) {
            throw new DMNRuntimeException("Cannot find element for name='%s'".formatted(name));
        } else {
            return result;
        }
    }

    public boolean sameId(TDMNElement element, String href) {
        String id = extractId(href);
        return element.getId().equals(id);
    }

    public boolean sameName(TNamedElement element, String name) {
        return element.getName().equals(name);
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
        for (TDMNElementReference reference: references) {
            TInputData child = findInputDataByRef(parent, reference.getHref());
            if (child != null) {
                String importName = findImportName(parent, reference);
                result.add(makeDRGElementReference(importName, child));
            } else {
                throw new DMNRuntimeException("Cannot find InputData for '%s' in parent '%s'".formatted(reference.getHref(), parent.getName()));
            }
        }
        return result;
    }

    public List<DRGElementReference<TInputData>> inputDataClosure(DRGElementReference<? extends TDRGElement> parentReference, DRGElementFilter drgElementFilter) {
        return drgElementFilter.filterInputs(collectTransitiveInputDatas(parentReference));
    }

    protected List<DRGElementReference<TInputData>> collectTransitiveInputDatas(DRGElementReference<? extends TDRGElement> parentReference) {
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
                throw new DMNRuntimeException("Cannot find InputData for '%s' in parent '%s'".formatted(reference.getHref(), parent.getName()));
            }
        }

        // Process direct children and update reference
        List<TDMNElementReference> childReferences = requiredDecisionReferences(parent);
        for (TDMNElementReference reference: childReferences) {
            TDecision child = findDecisionByRef(parent, reference.getHref());
            if (child != null) {
                // Update reference for descendants
                String importName = findImportName(parent, reference);
                List<DRGElementReference<TInputData>> inputReferences = collectTransitiveInputDatas(makeDRGElementReference(new ImportPath(parentImportPath, importName), child));
                result.addAll(inputReferences);
            } else {
                throw new DMNRuntimeException("Cannot find Decision for '%s' in parent '%s'".formatted(reference.getHref(), parent.getName()));
            }
        }
        return result;
    }

    public List<DRGElementReference<TDecision>> directSubDecisions(TDRGElement parent) {
        List<DRGElementReference<TDecision>> result = new ArrayList<>();
        if (parent instanceof TDecision decision) {
            // Add reference for direct children
            for (TInformationRequirement ir: decision.getInformationRequirement()) {
                TDMNElementReference reference = ir.getRequiredDecision();
                if (reference != null) {
                    TDecision child = findDecisionByRef(parent, reference.getHref());
                    String importName = findImportName(parent, reference);
                    result.add(makeDRGElementReference(importName, child));
                }
            }
        } else if (parent instanceof TDecisionService service) {
            // Add reference for direct children
            for (TDMNElementReference outputDecisionRef: service.getOutputDecision()) {
                TDecision child = findDecisionByRef(parent, outputDecisionRef.getHref());
                String importName = findImportName(parent, outputDecisionRef);
                result.add(makeDRGElementReference(importName, child));
            }
        }
        sortNamedElementReferences(result);
        return result;
    }

    public List<DRGElementReference<TDecision>> directInputDecisions(TDecisionService parent) {
        List<DRGElementReference<TDecision>> result = new ArrayList<>();
        // Add reference for direct children
        for (TDMNElementReference inputDecisionRef: parent.getInputDecision()) {
            TDecision child = findDecisionByRef(parent, inputDecisionRef.getHref());
            String importName = findImportName(parent, inputDecisionRef);
            result.add(makeDRGElementReference(importName, child));
        }
        sortNamedElementReferences(result);
        return result;
    }

    public List<DRGElementReference<TInvocable>> directSubInvocables(TDRGElement element) {
        List<DRGElementReference<TInvocable>> result = new ArrayList<>();
        // Add reference for direct children
        List<TKnowledgeRequirement> knowledgeRequirements = knowledgeRequirements(element);
        for (TKnowledgeRequirement kr: knowledgeRequirements) {
            TDMNElementReference reference = kr.getRequiredKnowledge();
            if (reference != null) {
                TInvocable invocable = findInvocableByRef(element, reference.getHref());
                if (invocable != null) {
                    String importName = findImportName(element, reference);
                    result.add(makeDRGElementReference(importName, invocable));
                } else {
                    throw new DMNRuntimeException("Cannot find Invocable for '%s'".formatted(reference.getHref()));
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
        if (parent instanceof TDecision decision) {
            List<TInformationRequirement> informationRequirements = decision.getInformationRequirement();
            references.addAll(informationRequirements.stream().map(TInformationRequirement::getRequiredInput).filter(Objects::nonNull).collect(Collectors.toList()));
        } else if (parent instanceof TDecisionService service) {
            List<TDMNElementReference> inputData = service.getInputData();
            references.addAll(inputData.stream().filter(Objects::nonNull).collect(Collectors.toList()));
        }
        return references;
    }

    protected List<TDMNElementReference> requiredDecisionReferences(TDRGElement parent) {
        List<TDMNElementReference> references = new ArrayList<>();
        if (parent instanceof TDecision decision) {
            List<TInformationRequirement> informationRequirements = decision.getInformationRequirement();
            references.addAll(informationRequirements.stream().map(TInformationRequirement::getRequiredDecision).filter(Objects::nonNull).collect(Collectors.toList()));
        } else if (parent instanceof TDecisionService service) {
            List<TDMNElementReference> inputDecisions = service.getInputDecision();
            references.addAll(inputDecisions.stream().filter(Objects::nonNull).collect(Collectors.toList()));
            List<TDMNElementReference> outputDecisions = service.getOutputDecision();
            references.addAll(outputDecisions.stream().filter(Objects::nonNull).collect(Collectors.toList()));
            List<TDMNElementReference> encapsulatedDecision = service.getEncapsulatedDecision();
            references.addAll(encapsulatedDecision.stream().filter(Objects::nonNull).collect(Collectors.toList()));
        }
        return references;
    }

    protected List<TKnowledgeRequirement> knowledgeRequirements(TDRGElement element) {
        List<TKnowledgeRequirement> knowledgeRequirements;
        if (element instanceof TDecision decision) {
            knowledgeRequirements = decision.getKnowledgeRequirement();
        } else if (element instanceof TBusinessKnowledgeModel model) {
            knowledgeRequirements = model.getKnowledgeRequirement();
        } else {
            knowledgeRequirements = new ArrayList<>();
        }
        return knowledgeRequirements;
    }

    public TDecisionTable decisionTable(TDRGElement element) {
        TExpression expression = expression(element);
        if (expression instanceof TDecisionTable table) {
            return table;
        } else {
            throw new DMNRuntimeException("Cannot find decision table in element '%s'".formatted(element.getName()));
        }
    }

    public boolean isNull(String typeRef) {
        return typeRef == null;
    }

    public boolean isNull(QualifiedName typeRef) {
        return typeRef == null;
    }

    public boolean isAny(QualifiedName typeRef) {
        return typeRef != null && "Any".equals(typeRef.getLocalPart());
    }

    public boolean isNullOrAny(QualifiedName typeRef) {
        return isNull(typeRef) || isAny(typeRef);
    }

    public TItemDefinition lookupItemDefinition(TDefinitions model, QualifiedName typeRef) {
        if (isNull(typeRef)) {
            return null;
        }
        String importName = typeRef.getNamespace();
        if (DMNVersion.LATEST.getFeelPrefix().equals(importName)) {
            return null;
        }

        if (model == null) {
            return lookupItemDefinition(findAllItemDefinitions(), typeRef);
        } else {
            for (TImport import_: model.getImport()) {
                if (import_.getName().equals(importName)) {
                    String modelNamespace = import_.getNamespace();
                    model = this.getModel(modelNamespace);
                    if (model == null) {
                        throw new DMNRuntimeException("Cannot find DM for '%s'".formatted(modelNamespace));
                    }
                }
            }
            return lookupItemDefinition(findItemDefinitions(model), typeRef);
        }
    }

    protected TItemDefinition lookupItemDefinition(List<TItemDefinition> itemDefinitionList, QualifiedName typeRef) {
        for (TItemDefinition itemDefinition: itemDefinitionList) {
            if (typeRef.getLocalPart().equals(itemDefinition.getName())) {
                return itemDefinition;
            }
        }
        return null;
    }

    public TItemDefinition lookupItemDefinition(String name) {
        return lookupItemDefinition(findAllItemDefinitions(), name);
    }

    protected TItemDefinition lookupItemDefinition(List<TItemDefinition> itemDefinitionList, String name) {
        if (name == null) {
            return null;
        }
        for (TItemDefinition itemDefinition: itemDefinitionList) {
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
        for (TOutputClause output: outputClauses) {
            TLiteralExpression defaultOutputEntry = output.getDefaultOutputEntry();
            if (defaultOutputEntry != null) {
                return true;
            }
        }
        return false;
    }

    public TExpression expression(TDRGElement element) {
        if (element instanceof TDecision decision) {
            return decision.getExpression();
        } else if (element instanceof TBusinessKnowledgeModel model) {
            TFunctionDefinition encapsulatedLogic = model.getEncapsulatedLogic();
            if (encapsulatedLogic != null) {
                return encapsulatedLogic.getExpression();
            }
        } else if (element instanceof TDecisionService) {
            return null;
        } else {
            throw new UnsupportedOperationException("'%s' is not supported yet".formatted(element.getClass().getSimpleName()));
        }
        return null;
    }

    public boolean isLiteralExpression(TDRGElement element) {
        return expression(element) instanceof TLiteralExpression;
    }

    public boolean isFreeTextLiteralExpression(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TLiteralExpression tle
                && FREE_TEXT_LANGUAGE.equals(tle.getExpressionLanguage());
    }

    public boolean isDecisionTableExpression(TDRGElement element) {
        return expression(element) instanceof TDecisionTable;
    }

    public boolean isCompoundDecisionTable(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TDecisionTable tdt
                && tdt.getOutput() != null
                && tdt.getOutput().size() > 1;
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

    public boolean isFunctionDefinitionExpression(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TFunctionDefinition;
    }

    public boolean isConditionalExpression(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TConditional;
    }

    public boolean isFilterExpression(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TFilter;
    }

    public boolean isForExpression(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TFor;
    }

    public boolean isSomeExpression(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TSome;
    }

    public boolean isEveryExpression(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TEvery;
    }


    //
    // Item definition related functions
    //
    public boolean isEmpty(List<TItemDefinition> list) {
        return list == null || list.isEmpty();
    }

    public QualifiedName variableTypeRef(TDefinitions model, TInformationItem element) {
        TInformationItem variable = variable(element);
        return variable == null ? null : QualifiedName.toQualifiedName(model, variable.getTypeRef());
    }

    public QualifiedName variableTypeRef(TDefinitions model, TDRGElement element) {
        TInformationItem variable = variable(element);
        QualifiedName typeRef = variable == null ? null : QualifiedName.toQualifiedName(model, variable.getTypeRef());
        // Derive from expression
        if (isNull(typeRef) && element instanceof TDecision) {
            typeRef = inferExpressionTypeRef(model, element);
        }
        return typeRef;
    }

    public QualifiedName outputTypeRef(TDefinitions model, TDRGElement element) {
        // Derive from variable
        TInformationItem variable = variable(element);
        QualifiedName typeRef = variable == null ? null : QualifiedName.toQualifiedName(model, variable.getTypeRef());
        // Derive from expression
        if (isNull(typeRef)) {
            typeRef = inferExpressionTypeRef(model, element);
        }
        return typeRef;
    }

    public QualifiedName inferExpressionTypeRef(TDefinitions model, TDRGElement element) {
        QualifiedName typeRef = null;
        // Derive from expression
        TExpression expression = expression(element);
        if (expression != null) {
            typeRef = QualifiedName.toQualifiedName(model, expression.getTypeRef());
            if (isNull(typeRef)) {
                if (expression instanceof TContext context) {
                    // Derive from return entry
                    List<TContextEntry> contextEntryList = context.getContextEntry();
                    for (TContextEntry ce: contextEntryList) {
                        if (ce.getVariable() == null) {
                            TExpression returnExp = ce.getExpression();
                            if (returnExp != null) {
                                typeRef = QualifiedName.toQualifiedName(model, returnExp.getTypeRef());
                            }
                        }
                    }
                } else if (expression instanceof TDecisionTable dt) {
                    List<TOutputClause> outputList = dt.getOutput();
                    if (outputList.size() == 1) {
                        typeRef = QualifiedName.toQualifiedName(model, outputList.get(0).getTypeRef());
                        if (isNull(typeRef)) {
                            // Derive from rules
                            List<TDecisionRule> ruleList = dt.getRule();
                            List<TLiteralExpression> outputEntry = ruleList.get(0).getOutputEntry();
                            typeRef = QualifiedName.toQualifiedName(model, outputEntry.get(0).getTypeRef());
                        }
                        // Apply aggregation and hit policy
                        if (dt.getHitPolicy() == THitPolicy.COLLECT) {
                            // Type is list
                            typeRef = null;
                        }
                        TBuiltinAggregator aggregation = dt.getAggregation();
                        if (aggregation == SUM || aggregation == COUNT) {
                            typeRef = QualifiedName.toQualifiedName(null, "number");
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
            throw new DMNRuntimeException("Missing or more than one decision services in BKM '%s'".formatted(decisionService.getName()));
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
        if (expression instanceof TDecisionTable table) {
            List<TDecisionRule> rules = table.getRule();
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
                throw new DMNRuntimeException("Cannot resolve name for outputClause '%s' in element '%s'".formatted(output.getId(), element.getName()));
            }
        }
        return outputClauseName;
    }

    public TInformationItem variable(TNamedElement element) {
        if (element instanceof TInputData data) {
            return data.getVariable();
        } else if (element instanceof TDecision decision) {
            return decision.getVariable();
        } else if (element instanceof TInvocable invocable) {
            return invocable.getVariable();
        } else if (element instanceof TInformationItem item) {
            return item;
        }
        return null;
    }

    public String name(TNamedElement element) {
        String name = null;
        if (element != null) {
            name = element.getName();
        }
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException("Display name cannot be null for element '%s'".formatted(element == null ? null : element.getId()));
        }
        return name.trim();
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
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException("Display name cannot be null for element '%s'".formatted(element.getId()));
        }
        return name.trim();
    }

    public String findChildImportName(TDRGElement parent, TDRGElement child) {
        // Collect references
        List<TDMNElementReference> references = new ArrayList<>();
        if (parent instanceof TDecision decision) {
            for (TInformationRequirement ir: decision.getInformationRequirement()) {
                TDMNElementReference reference = ir.getRequiredDecision();
                if (reference != null) {
                    references.add(reference);
                }
                reference = ir.getRequiredInput();
                if (reference != null) {
                    references.add(reference);
                }
            }
            for (TKnowledgeRequirement bkr: decision.getKnowledgeRequirement()) {
                TDMNElementReference reference = bkr.getRequiredKnowledge();
                if (reference != null) {
                    references.add(reference);
                }
            }
        } else if (parent instanceof TBusinessKnowledgeModel model) {
            for (TKnowledgeRequirement bkr: model.getKnowledgeRequirement()) {
                TDMNElementReference reference = bkr.getRequiredKnowledge();
                if (reference != null) {
                    references.add(reference);
                }
            }
        } else if (parent instanceof TDecisionService service) {
            references.addAll(service.getInputData());
            references.addAll(service.getInputDecision());
            references.addAll(service.getOutputDecision());
            references.addAll(service.getEncapsulatedDecision());
        }

        // Find reference for child
        String childNamespace = getNamespace(child);
        String childRefSuffix = childNamespace + "#" + child.getId();
        for (TDMNElementReference reference: references) {
            if (reference.getHref().endsWith(childRefSuffix)) {
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
            for (TImport import_: parentDefinitions.getImport()) {
                if (import_.getNamespace().equals(referenceNamespace)) {
                    return import_.getName();
                }
            }
            throw new DMNRuntimeException("Cannot find import prefix for '%s' in model '%s'".formatted(reference.getHref(), parentDefinitions.getName()));
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
        } else if (href != null && href.startsWith("#")) {
            href = href.substring(1);
        }
        return href;
    }

    protected static boolean hasNamespace(String href) {
        return href != null && href.indexOf('#') > 0;
    }

    public List<TItemDefinition> compositeItemDefinitions(TDefinitions definitions) {
        List<TItemDefinition> accumulator = new ArrayList<>();
        collectCompositeItemDefinitions(definitions.getItemDefinition(), accumulator);
        return accumulator;
    }

    private void collectCompositeItemDefinitions(List<TItemDefinition> itemDefinitions, List<TItemDefinition> accumulator) {
        if (itemDefinitions != null) {
            for (TItemDefinition itemDefinition: itemDefinitions) {
                if (hasComponents(itemDefinition)) {
                    accumulator.add(itemDefinition);
                    collectCompositeItemDefinitions(itemDefinition.getItemComponent(), accumulator);
                }
            }
        }
    }

    private boolean isRecursive(TExpression exp, String bkm) {
        if (exp instanceof TLiteralExpression expression) {
            String text = expression.getText();
            Matcher matcher = WORD.matcher(text);
            while (matcher.find()) {
                if (bkm.equals(matcher.group())) {
                    return true;
                }
            }
        }
        return false;
    }
}
