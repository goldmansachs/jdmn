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
package com.gs.dmn.signavio;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.interpreter.ImportPath;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.signavio.extension.MultiInstanceDecisionLogic;
import com.gs.dmn.signavio.extension.SignavioExtension;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.serialization.DMNVersion.DMN_12;

public class SignavioDMNModelRepository extends DMNModelRepository {
    private String schemaNamespace = "http://www.signavio.com/schema/dmn/1.1/";
    private final String[] schemaPrefixes = new String[] {
            "signavio", "sigExt"
    };

    private QName diagramIdQName = new QName(this.schemaNamespace, "diagramId");
    private QName shapeIdQName = new QName(this.schemaNamespace, "shapeId");

    public final SignavioExtension extension = new SignavioExtension(this);

    private final Map<String, TDRGElement> cache = new LinkedHashMap<>();

    public SignavioDMNModelRepository() {
        this(OBJECT_FACTORY.createTDefinitions(), new PrefixNamespaceMappings());
    }

    public SignavioDMNModelRepository(TDefinitions definitions, PrefixNamespaceMappings prefixNamespaceMappings) {
        this(new Pair<>(definitions, prefixNamespaceMappings));
    }

    public SignavioDMNModelRepository(Pair<TDefinitions, PrefixNamespaceMappings> pair) {
        this(Collections.singletonList(pair));
    }

    public SignavioDMNModelRepository(Pair<TDefinitions, PrefixNamespaceMappings> pair, String schemaNamespace) {
        this(pair);
        this.schemaNamespace = schemaNamespace;
        this.diagramIdQName = new QName(schemaNamespace, "diagramId");
        this.shapeIdQName = new QName(schemaNamespace, "shapeId");
    }

    public SignavioDMNModelRepository(List<Pair<TDefinitions, PrefixNamespaceMappings>> pairs) {
        super(pairs);
        for (Pair<TDefinitions, PrefixNamespaceMappings> pair: pairs) {
            TDefinitions definitions = pair.getLeft();
            List<Object> elementList = this.extension.findExtensions(definitions.getExtensionElements(), DMN_12.getNamespace(), "decisionService");
            for(Object element: elementList) {
                Object value = ((JAXBElement<?>) element).getValue();
                if (value instanceof TDecisionService) {
                    this.addElementMap((TDecisionService)value, definitions);
                }
            }
        }
    }

    @Override
    protected void addModelCoordinates(TDefinitions definitions, TDMNElement element, List<String> locationParts) {
        String diagramId = getDiagramId(element);
        if (!StringUtils.isBlank(diagramId)) {
            locationParts.add(String.format("model='%s'", diagramId));
        } else {
            String modelName = definitions.getName();
            if (!StringUtils.isBlank(modelName)) {
                locationParts.add(String.format("model='%s'", modelName));
            }
        }
    }

    @Override
    public SignavioDMNModelRepository copy() {
        return new SignavioDMNModelRepository(this.pairList);
    }

    public String getSchemaNamespace() {
        return this.schemaNamespace;
    }

    public String[] getSchemaPrefixes() {
        return this.schemaPrefixes;
    }

    private QName getDiagramIdQName() {
        return this.diagramIdQName;
    }

    private QName getShapeIdQName() {
        return this.shapeIdQName;
    }

    public String getDiagramId(TDMNElement element) {
        if (element == null) {
            return null;
        }
        Map<QName, String> otherAttributes = element.getOtherAttributes();
        javax.xml.namespace.QName diagramIdQName = getDiagramIdQName();
        return otherAttributes == null ? null : otherAttributes.get(diagramIdQName);
    }

    public String getShapeId(TDRGElement element) {
        if (element == null) {
            return null;
        }
        Map<QName, String> otherAttributes = element.getOtherAttributes();
        javax.xml.namespace.QName shapeIdQName = getShapeIdQName();
        return otherAttributes == null ? null : otherAttributes.get(shapeIdQName);
    }

    public SignavioExtension getExtension() {
        return this.extension;
    }

    public boolean isBKMLinkedToDecision(TNamedElement element) {
        return element instanceof TBusinessKnowledgeModel
                && getOutputDecision((TBusinessKnowledgeModel) element) != null;
    }

    public TDecision getOutputDecision(TBusinessKnowledgeModel element) {
        TDecisionService decisionService = getExtension().referencedService(element);
        return decisionService == null ? null : this.getOutputDecision(decisionService);
    }

    public boolean isMultiInstanceDecision(TDRGElement decision) {
        return this.extension.isMultiInstanceDecision(decision);
    }

    @Override
    protected List<DRGElementReference<TInputData>> collectTransitiveInputDatas(DRGElementReference<? extends TDRGElement> parentReference) {
        TDRGElement parent = parentReference.getElement();
        ImportPath parentImportPath = parentReference.getImportPath();
        List<DRGElementReference<TInputData>> result = new ArrayList<>();
        result.addAll(directInputDatas(parent));
        // Add inputs used in iteration body / topLevelDecision
        if (isMultiInstanceDecision(parent)) {
            MultiInstanceDecisionLogic multiInstanceDecisionLogic = this.extension.multiInstanceDecisionLogic(parent);
            TDecision topLevelDecision = multiInstanceDecisionLogic.getTopLevelDecision();
            DRGElementReference<? extends TDRGElement> topLevelReference = makeDRGElementReference(topLevelDecision);

            List<DRGElementReference<TInputData>> inputDataList = collectTransitiveInputDatas(topLevelReference);
            inputDataList.removeIf(tInputDataDMNReference -> tInputDataDMNReference.getElement() == multiInstanceDecisionLogic.getIterator());
            result.addAll(inputDataList);
        }

        // Process direct children
        List<TDMNElementReference> childReferences = requiredDecisionReferences(parent);
        for (TDMNElementReference reference: childReferences) {
            TDecision child = findDecisionByRef(parent, reference.getHref());
            if (child != null) {
                String importName = findImportName(parent, reference);
                DRGElementReference<TDecision> childReference = makeDRGElementReference(new ImportPath(parentImportPath, importName), child);
                List<DRGElementReference<TInputData>> inputReferences = collectTransitiveInputDatas(childReference);
                result.addAll(inputReferences);
            } else {
                throw new DMNRuntimeException(String.format("Cannot find Decision for '%s' in parent '%s'", reference.getHref(), parent.getName()));
            }
        }
        return result;
    }

    public void addItemDefinition(TDefinitions definitions, TItemDefinition itemDefinition) {
        definitions.getItemDefinition().add(itemDefinition);
        this.itemDefinitions.add(itemDefinition);
    }

    public TDRGElement findDRGElementById(String id) {
        String key = makeKey(id);
        TDRGElement result = cache.get(key);
        if (result == null) {
            TDefinitions definitions = getRootDefinitions();
            List<TDRGElement> drgElements = findDRGElements(definitions);
            for (TDRGElement element : drgElements) {
                if (sameId(element, id)) {
                    result = element;
                    cache.put(key, result);
                    break;
                }
            }
        }
        return result;
    }

    public TDRGElement findDRGElementByDiagramAndShapeIds(String diagramId, String shapeId) {
        String key = makeKey(diagramId, shapeId);
        TDRGElement result = cache.get(key);
        if (result == null) {
            TDefinitions definitions = getRootDefinitions();
            List<TDRGElement> drgElements = findDRGElements(definitions);
            for (TDRGElement element : drgElements) {
                if (idEndsWith(element, shapeId) || (sameDiagramId(element, diagramId) && sameShapeId(element, shapeId))) {
                    result = element;
                    cache.put(key, result);
                    break;
                }
            }
        }
        return result;
    }

    public TDRGElement findDRGElementByLabel(String label, String diagramId, String shapeId) {
        String key = makeKey(label, diagramId, shapeId);
        TDRGElement result = cache.get(key);
        if (result == null) {
            TDefinitions definitions = getRootDefinitions();
            List<TDRGElement> drgElements = findDRGElements(definitions);
            List<TDRGElement> elements = drgElements.stream().filter(element -> sameLabel(element, label)).collect(Collectors.toList());
            if (elements.size() == 0) {
                result = null;
            } else if (elements.size() == 1) {
                result = elements.get(0);
            } else {
                List<TDRGElement> sameShapeIdElements = elements.stream().filter(e -> sameShapeId(e, shapeId)).collect(Collectors.toList());
                QName diagramQName = getDiagramIdQName();
                String newDiagramID = elements.stream().filter(e -> sameShapeId(e, shapeId)).map(e -> e.getOtherAttributes().get(diagramQName)).collect(Collectors.joining(", "));
                if (sameShapeIdElements.size() == 1) {
                    LOGGER.warn(String.format("Incorrect diagramId for test input with label '%s' diagramId='%s' shapeId='%s'. DiagramId should be '%s'", label, diagramId, shapeId, newDiagramID));
                    result = sameShapeIdElements.get(0);
                } else {
                    throw new DMNRuntimeException(String.format("Multiple DRGElements for label '%s' with diagramId='%s' shapeId='%s'. Diagram ID should be one of '%s'", label, diagramId, shapeId, newDiagramID));
                }
            }
            if (result != null) {
                cache.put(key, result);
            }
        }
        return result;
    }

    public boolean idEndsWith(TNamedElement element, String id) {
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
        QName diagramIdQName = getDiagramIdQName();
        String diagramId = otherAttributes.get(diagramIdQName);
        return id.equals(diagramId);
    }

    private boolean sameShapeId(TDRGElement element, String id) {
        if (id == null) {
            return false;
        }
        Map<QName, String> otherAttributes = element.getOtherAttributes();
        QName shapeIdQName = this.getShapeIdQName();
        String shapeId = otherAttributes.get(shapeIdQName);
        return id.equals(shapeId);
    }

    public boolean sameLabel(TNamedElement element, String label) {
        return label != null && label.equals(element.getLabel());
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
