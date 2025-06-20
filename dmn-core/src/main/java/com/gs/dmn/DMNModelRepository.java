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
import java.util.function.BiFunction;
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
    // Cache for DRGElements and top ItemDefinitions
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
                // Update caches
                if (!this.namespaceToDefinitions.containsKey(definitions.getNamespace())) {
                    this.namespaceToDefinitions.put(definitions.getNamespace(), definitions);
                } else {
                    throw new DMNRuntimeException(String.format("Duplicated model namespace '%s'", definitions.getNamespace()));
                }
            }
        }

        // Process all definitions
        for (TDefinitions definitions: this.getAllDefinitions()) {
            // Normalize
            findItemDefinitionAndAllowedValuesFor(definitions);

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

    protected void findItemDefinitionAndAllowedValuesFor(TDefinitions definitions) {
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
                String href = normalizeHref(definitions.getNamespace(), requiredDecision);
                map.compute(href, (k, v) -> v == null ? 1 : v + 1);
                parentMap.put(href, decision);
            }
        }
    }

    private void addCachedChildren(TDefinitions definitions, TDecisionService decisionService, Map<String, TDecision> parentMap, Map<String, Integer> map) {
        for (TDMNElementReference inputDecisionRef: decisionService.getInputDecision()) {
            if (inputDecisionRef != null) {
                String href = normalizeHref(definitions.getNamespace(), inputDecisionRef);
                map.compute(href, (k, v) -> v == null ? 1 : v + 1);
                TDecision inputDecision = findDecisionByRef(decisionService, href);
                parentMap.put(href, inputDecision);
            }
        }
    }

    public boolean isDMNImport(TImport import_) {
        // Always the latest version - a transformation to latest is performed when reading
        return DMNVersion.LATEST.getNamespace().equals(import_.getImportType());
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

    // Model name might not be unique
    public TDefinitions findModelByName(String modelName) {
        List<TDefinitions> result = new ArrayList<>();
        for (TDefinitions definitions : this.allDefinitions) {
            if (modelName.equals(definitions.getName())) {
                result.add(definitions);
            }
        }
        if (result.isEmpty()) {
            throw new DMNRuntimeException(String.format("Cannot find model '%s'", modelName));
        } else if (result.size() == 1) {
            return result.get(0);
        } else {
            throw new DMNRuntimeException(String.format("Model name '%s' is not unique", modelName));
        }
    }

    public void addElementMap(TDRGElement element, TDefinitions definitions) {
        this.elementToDefinitions.put(element, definitions);
    }

    public List<String> getImportedNames(TDefinitions definitions) {
        List<String> names = new ArrayList<>();
        for (TImport imp: definitions.getImport()) {
            if (isDMNImport(imp)) {
                String name = imp.getName();
                if (!StringUtils.isBlank(name) && !names.contains(name)) {
                    names.add(name);
                }
            }
        }
        return names;
    }

    public TImport findImport(TDefinitions definitions, String name) {
        for (TImport imp: definitions.getImport()) {
            if (imp.getName().equals(name)) {
                return imp;
            }
        }
        return null;
    }

    public TDefinitions getModel(TNamedElement element) {
        return this.elementToDefinitions.get(element);
    }

    public TDefinitions findModelByNamespace(String namespace) {
        TDefinitions definitions = this.namespaceToDefinitions.get(namespace);
        if (definitions == null) {
            throw new DMNRuntimeException(String.format("Cannot find DM for namespace '%s'", namespace));
        } else {
            return definitions;
        }
    }

    // Error location
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
        return element instanceof TBusinessKnowledgeModel && isRecursive(((TBusinessKnowledgeModel) element).getEncapsulatedLogic().getExpression(), element.getName());
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
            if (element instanceof TDecision) {
                result.add((TDecision) element);
            }
        }
    }

    protected void collectInputDatas(TDefinitions definitions, List<TInputData> result) {
        for (TDRGElement element: definitions.getDrgElement()) {
            if (element instanceof TInputData) {
                result.add((TInputData) element);
            }
        }
    }

    protected void collectBKMs(TDefinitions definitions, List<TBusinessKnowledgeModel> result) {
        for (TDRGElement element: definitions.getDrgElement()) {
            if (element instanceof TBusinessKnowledgeModel) {
                result.add((TBusinessKnowledgeModel) element);
            }
        }
    }

    protected void collectDSs(TDefinitions definitions, List<TDecisionService> result) {
        for (TDRGElement element: definitions.getDrgElement()) {
            if (element instanceof TDecisionService) {
                result.add((TDecisionService) element);
            }
        }
    }

    protected void collectInvocables(TDefinitions definitions, List<TInvocable> result) {
        for (TDRGElement element: definitions.getDrgElement()) {
            if (element instanceof TInvocable) {
                result.add((TInvocable) element);
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

    public Pair<TItemDefinition, TUnaryTests> findItemDefinitionAndAllowedValuesFor(TItemDefinition itemDefinition) {
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
                || itemDefinition.getTypeRef() == null
                || itemDefinition.isIsCollection()
                || !isEmpty(itemDefinition.getItemComponent())
                || itemDefinition.getFunctionItem() != null
                ) {
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
        return String.format("%s-%s", d.getName(), d.getId());
    }

    private String diagramElementKey(DiagramElement e) {
        if (e == null) {
            return "";
        }
        return String.format("%s-%s", e.getClass().getSimpleName(), e.getId());
    }

    public void sortNamedElementReferences(List<? extends DRGElementReference<? extends TNamedElement>> references) {
        references.sort(Comparator.comparing((DRGElementReference<? extends TNamedElement> o) -> NameUtils.removeSingleQuotes(o.getElementName())));
    }

    public TDRGElement findDRGElementByRef(TDRGElement parent, String href) {
        try {
            TDRGElement result = null;
            if (isAbsoluteURI(href)) {
                result = this.drgElementByRef.get(href);
                if (result == null) {
                    TDefinitions childDefinitions = this.findModelByNamespace(extractNamespaceURI(href));
                    result = findDRGElementByRef(childDefinitions, href);
                    // Update local cache
                    this.drgElementByRef.put(href, result);
                }
                return result;
            } else {
                TDefinitions parentDefinitions = getModel(parent);
                result = findDRGElementById(parentDefinitions, href);
                if (result != null) {
                    TDefinitions childDefinitions = getModel(result);
                    // Update local cache
                    String key = makeRef(childDefinitions.getNamespace(), href);
                    this.drgElementByRef.put(key, result);
                }
            }
            if (result == null) {
                throw new DMNRuntimeException(String.format("Cannot find DRG element for href='%s' in element '%s'", href, parent.getName()));
            } else {
                return result;
            }
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot find DRG element for href='%s' in element '%s'", href, parent.getName()), e);
        }
    }

    private TDRGElement findDRGElementByRef(TDefinitions definitions, String href) {
        for (TDRGElement element: findDRGElements(definitions)) {
            if (sameId(element, href)) {
                return element;
            }
        }
        return null;
    }

    private TDRGElement findDRGElementById(TDefinitions definitions, String id) {
        TDRGElement element = findDRGElementByFilter(definitions, id, this::sameId);
        if (element == null) {
            throw new DMNRuntimeException(String.format("Cannot find DRG element for id='%s' in model '%s#%s'", id, definitions.getNamespace(), definitions.getName()));
        } else {
            return element;
        }
    }

    public TDecision findDecisionByRef(TDRGElement parent, String href) {
        TDRGElement drgElement = findDRGElementByRef(parent, href);
        if (drgElement instanceof TDecision) {
            return (TDecision) drgElement;
        } else {
            throw new DMNRuntimeException(String.format("Cannot find Decision element for href='%s'", href));
        }
    }

    public TInputData findInputDataByRef(TDRGElement parent, String href) {
        TDRGElement drgElement = findDRGElementByRef(parent, href);
        if (drgElement instanceof TInputData) {
            return (TInputData) drgElement;
        } else {
            throw new DMNRuntimeException(String.format("Cannot find InputData element for href='%s'", href));
        }
    }

    public TInvocable findInvocableByRef(TDRGElement parent, String href) {
        TDRGElement drgElement = findDRGElementByRef(parent, href);
        if (drgElement instanceof TInvocable) {
            return (TInvocable) drgElement;
        } else {
            throw new DMNRuntimeException(String.format("Cannot find TInvocable element for href='%s'", href));
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
                throw new DMNRuntimeException(String.format("Found %s business knowledge models for name='%s'", value.size(), name));
            }
        }
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
        return findDRGElementByName(definitions, name);
    }

    public TDRGElement findDRGElementByName(TDefinitions definitions, String name) {
        TDRGElement element = findDRGElementByFilter(definitions, name, this::sameName);
        if (element == null) {
            throw new DMNRuntimeException(String.format("Cannot find DRG element for name='%s'", name));
        } else {
            return element;
        }
    }

    private TDRGElement findDRGElementByFilter(TDefinitions definitions, String property, BiFunction<TNamedElement, String, Boolean> filter) {
        if (definitions == null) {
            throw new DMNRuntimeException(String.format("Cannot find element for '%s'. Missing DM", property));
        }

        // Lookup in current model
        TDRGElement result = null;
        for (TDRGElement element: findDRGElements(definitions)) {
            if (filter.apply(element, property)) {
                result = element;
                break;
            }
        }
        return result;
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
                throw new DMNRuntimeException(String.format("Found %s DRG elements for name='%s'", value.size(), name));
            }
        }
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
                ImportPath importPath = findRelativeImportPath(parent, reference);
                result.add(makeDRGElementReference(importPath, child));
            } else {
                throw new DMNRuntimeException(String.format("Cannot find InputData for '%s' in parent '%s'", reference.getHref(), parent.getName()));
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
                ImportPath importPath = findAbsoluteImportPath(parent, reference, parentImportPath);
                result.add(makeDRGElementReference(importPath, child));
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
                ImportPath importPath = findAbsoluteImportPath(parent, reference, parentImportPath);
                List<DRGElementReference<TInputData>> inputReferences = collectTransitiveInputDatas(makeDRGElementReference(importPath, child));
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
            for (TInformationRequirement ir: ((TDecision) parent).getInformationRequirement()) {
                TDMNElementReference reference = ir.getRequiredDecision();
                if (reference != null) {
                    TDecision child = findDecisionByRef(parent, reference.getHref());
                    ImportPath importPath = findRelativeImportPath(parent, reference);
                    result.add(makeDRGElementReference(importPath, child));
                }
            }
        } else if (parent instanceof TDecisionService) {
            // Add reference for direct children
            for (TDMNElementReference outputDecisionRef: ((TDecisionService) parent).getOutputDecision()) {
                TDecision child = findDecisionByRef(parent, outputDecisionRef.getHref());
                ImportPath importPath = findRelativeImportPath(parent, outputDecisionRef);
                result.add(makeDRGElementReference(importPath, child));
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
            ImportPath importPath = findRelativeImportPath(parent, inputDecisionRef);
            result.add(makeDRGElementReference(importPath, child));
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
                    ImportPath importPath = findRelativeImportPath(element, reference);
                    result.add(makeDRGElementReference(importPath, invocable));
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
            List<TInformationRequirement> informationRequirements = ((TDecision) parent).getInformationRequirement();
            references.addAll(informationRequirements.stream().map(TInformationRequirement::getRequiredInput).filter(Objects::nonNull).collect(Collectors.toList()));
        } else if (parent instanceof TDecisionService) {
            List<TDMNElementReference> inputData = ((TDecisionService) parent).getInputData();
            references.addAll(inputData.stream().filter(Objects::nonNull).collect(Collectors.toList()));
        }
        return references;
    }

    protected List<TDMNElementReference> requiredDecisionReferences(TDRGElement parent) {
        List<TDMNElementReference> references = new ArrayList<>();
        if (parent instanceof TDecision) {
            List<TInformationRequirement> informationRequirements = ((TDecision) parent).getInformationRequirement();
            references.addAll(informationRequirements.stream().map(TInformationRequirement::getRequiredDecision).filter(Objects::nonNull).collect(Collectors.toList()));
        } else if (parent instanceof TDecisionService) {
            List<TDMNElementReference> inputDecisions = ((TDecisionService) parent).getInputDecision();
            references.addAll(inputDecisions.stream().filter(Objects::nonNull).collect(Collectors.toList()));
            List<TDMNElementReference> outputDecisions = ((TDecisionService) parent).getOutputDecision();
            references.addAll(outputDecisions.stream().filter(Objects::nonNull).collect(Collectors.toList()));
            List<TDMNElementReference> encapsulatedDecision = ((TDecisionService) parent).getEncapsulatedDecision();
            references.addAll(encapsulatedDecision.stream().filter(Objects::nonNull).collect(Collectors.toList()));
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

    public boolean isLibraryImport(TImport import_) {
        return DMNVersion.FEEL_LIBRARY_IMPORT.equals(import_.getImportType());
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
        if (importName == null) {
            importName = "";
        }
        if (DMNVersion.LATEST.getFeelPrefix().equals(importName)) {
            return null;
        }

        if (model == null) {
            return lookupItemDefinition(findAllItemDefinitions(), typeRef);
        } else if (importName.isEmpty()) {
            // Lookup in current model
            TItemDefinition result = lookupItemDefinition(findItemDefinitions(model), typeRef);
            if (result == null) {
                // Lookup in models imported with empty prefix
                for (TImport import_: model.getImport()) {
                    if (isDMNImport(import_)) {
                        if (StringUtils.isBlank(import_.getName())) {
                            String childNamespace = import_.getNamespace();
                            TDefinitions childModel = this.findModelByNamespace(childNamespace);
                            if (childModel == null) {
                                throw new DMNRuntimeException(String.format("Cannot find DM for '%s'", childNamespace));
                            }
                            result = lookupItemDefinition(childModel, typeRef);
                            if (result != null) {
                                return result;
                            }
                        }
                    }
                }
                return null;
            } else {
                return result;
            }
        } else {
            // Find model for importName
            String childNamespace = null;
            for (TImport import_: model.getImport()) {
                if (isDMNImport(import_)) {
                    if (import_.getName().equals(importName)) {
                        childNamespace = import_.getNamespace();
                        break;
                    }
                }
            }
            TDefinitions childModel = this.findModelByNamespace(childNamespace);
            if (childModel == null) {
                throw new DMNRuntimeException(String.format("Cannot find DM for '%s'", childNamespace));
            }
            // Lookup typeRef in model
            return lookupItemDefinition(findItemDefinitions(childModel), typeRef);
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
        if (element instanceof TDecision) {
            return ((TDecision) element).getExpression();
        } else if (element instanceof TBusinessKnowledgeModel) {
            TFunctionDefinition encapsulatedLogic = ((TBusinessKnowledgeModel) element).getEncapsulatedLogic();
            if (encapsulatedLogic != null) {
                return encapsulatedLogic.getExpression();
            }
        } else if (element instanceof TDecisionService) {
            return null;
        } else {
            throw new UnsupportedOperationException(String.format("'%s' is not supported yet", element.getClass().getSimpleName()));
        }
        return null;
    }

    public boolean isLiteralExpression(TDRGElement element) {
        return expression(element) instanceof TLiteralExpression;
    }

    public boolean isFreeTextLiteralExpression(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TLiteralExpression
                && FREE_TEXT_LANGUAGE.equals(((TLiteralExpression) expression).getExpressionLanguage());
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

    public boolean isListExpression(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TList;
    }

    public boolean isRelationExpression(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TRelation;
    }

    public boolean isFunctionDefinitionExpression(TDRGElement element) {
        TExpression expression = expression(element);
        return expression instanceof TFunctionDefinition;
    }

    public boolean isFEELFunction(TFunctionKind kind) {
        return kind == null || kind == TFunctionKind.FEEL;
    }

    public boolean isJavaFunction(TFunctionKind kind) {
        return kind == TFunctionKind.JAVA;
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
                if (expression instanceof TContext) {
                    // Derive from return entry
                    List<TContextEntry> contextEntryList = ((TContext) expression).getContextEntry();
                    for (TContextEntry ce: contextEntryList) {
                        if (ce.getVariable() == null) {
                            TExpression returnExp = ce.getExpression();
                            if (returnExp != null) {
                                typeRef = QualifiedName.toQualifiedName(model, returnExp.getTypeRef());
                            }
                        }
                    }
                } else if (expression instanceof TDecisionTable) {
                    // Derive from output clauses and rules
                    TDecisionTable dt = (TDecisionTable) expression;
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
                            typeRef = QualifiedName.toQualifiedName((TDefinitions) null, "number");
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
        } else if (element instanceof TInvocable) {
            return ((TInvocable) element).getVariable();
        } else if (element instanceof TInformationItem) {
            return (TInformationItem) element;
        }
        return null;
    }

    public String name(TNamedElement element) {
        String name = null;
        if (element != null) {
            name = element.getName();
        }
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException(String.format("Display name cannot be null for element '%s'", element == null ? null : element.getId()));
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
            throw new DMNRuntimeException(String.format("Display name cannot be null for element '%s'", element.getId()));
        }
        return name.trim();
    }

    public ImportPath findRelativeImportPath(TDRGElement parent, TDMNElementReference reference) {
        return findAbsoluteImportPath(parent, reference, new ImportPath());
    }

    public ImportPath findRelativeImportPath(TDRGElement parent, TDRGElement child) {
        TDefinitions parentModel = getModel(parent);
        String childNamespace = getNamespace(child);
        return findRelativeImportPath(parentModel, childNamespace);
    }

    private ImportPath findRelativeImportPath(TDefinitions parentDefinitions, String namespace) {
        return findAbsoluteImportPath(parentDefinitions, namespace, new ImportPath());
    }

    public ImportPath findAbsoluteImportPath(TDRGElement parent, TDMNElementReference reference, ImportPath parentPath) {
        TDefinitions parentDefinitions = this.elementToDefinitions.get(parent);
        String namespaceURI = extractNamespaceURI(reference.getHref());
        return findAbsoluteImportPath(parentDefinitions, namespaceURI, parentPath);
    }

    private ImportPath findAbsoluteImportPath(TDefinitions parentDefinitions, String namespace, ImportPath parentPath) {
        if (namespace == null || parentDefinitions.getNamespace().equals(namespace)) {
            // Same model
            return parentPath;
        } else {
            // Check imported declarations
            for (TImport import_: parentDefinitions.getImport()) {
                if (isDMNImport(import_)) {
                    if (Objects.equals(import_.getNamespace(), namespace)) {
                        return new ImportPath(parentPath, import_.getName());
                    }
                }
            }
        }
        throw new DMNRuntimeException(String.format("Cannot not find import for '%s' in '%s:%s'", namespace, parentDefinitions.getNamespace(), parentDefinitions.getName()));
    }

    protected static String makeRef(String absoluteURI, String href) {
        if (StringUtils.isBlank(absoluteURI)) {
            return href;
        } else {
            return absoluteURI + href;
        }
    }

    private static String normalizeHref(String absoluteURI, TDMNElementReference elementReference) {
        String href = elementReference.getHref();
        if (!isAbsoluteURI(href)) {
            href = makeRef(absoluteURI, href);
        }
        return href;
    }

    // absolute URI is a namespace
    protected String extractNamespaceURI(String href) {
        String namespace = null;
        if (isAbsoluteURI(href)) {
            namespace = href.substring(0, href.indexOf('#'));
        }
        return namespace;
    }

    public static String extractId(String href) {
        if (isAbsoluteURI(href)) {
            href = href.substring(href.indexOf('#') + 1);
        } else if (href != null && href.startsWith("#")) {
            href = href.substring(1);
        }
        return href;
    }

    protected static boolean isAbsoluteURI(String href) {
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
        if (exp instanceof TLiteralExpression) {
            String text = ((TLiteralExpression) exp).getText();
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
