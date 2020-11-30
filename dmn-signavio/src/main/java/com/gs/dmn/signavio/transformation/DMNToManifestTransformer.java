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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.metadata.*;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.transformation.basic.BasicSignavioDMNToJavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import org.omg.spec.dmn._20191111.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.serialization.DMNVersion.DMN_11;

public class DMNToManifestTransformer {
    private final BasicDMNToNativeTransformer dmnTransformer;
    private final DMNModelRepository dmnModelRepository;

    public DMNToManifestTransformer(BasicDMNToNativeTransformer dmnTransformer) {
        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.dmnTransformer = dmnTransformer;
    }

    public DMNMetadata toManifest(String dmnNamespace, String nativeNamespace, String dmnVersion, String modelVersion, String platformVersion) {
        DMNMetadata manifest = new DMNMetadata(dmnNamespace, nativeNamespace, dmnVersion, modelVersion, platformVersion);
        for (TDefinitions definitions: this.dmnModelRepository.getAllDefinitions()) {
            // Add types
            for (TItemDefinition itemDefinition : this.dmnModelRepository.findItemDefinitions(definitions)) {
                com.gs.dmn.runtime.metadata.Type type = makeMetadataType(definitions, itemDefinition);
                manifest.addType(type);
            }
            // Add elements
            for (TInputData inputData : this.dmnModelRepository.findInputDatas(definitions)) {
                InputData element = makeMetadataInputData(definitions, inputData);
                manifest.addElement(element);
            }
            for (TBusinessKnowledgeModel bkm : this.dmnModelRepository.findBKMs(definitions)) {
                com.gs.dmn.runtime.metadata.BKM element = makeMetadataBKM(definitions, bkm);
                manifest.addElement(element);
            }
            for (TDecision decision : this.dmnModelRepository.findDecisions(definitions)) {
                com.gs.dmn.runtime.metadata.Decision element = makeMetadataDecision(definitions, decision);
                manifest.addElement(element);
            }
        }
        return manifest;
    }

    //
    // Types
    //
    private com.gs.dmn.runtime.metadata.Type makeMetadataType(TDefinitions model, TItemDefinition itemDefinition) {
        String id = itemDefinition.getId();
        String name = itemDefinition.getName();
        String label = itemDefinition.getLabel();
        boolean isCollection = itemDefinition.isIsCollection();
        com.gs.dmn.runtime.metadata.QName typeRef = makeMetadataTypeRef(model, QualifiedName.toQualifiedName(model, itemDefinition.getTypeRef()));
        String allowedValues = makeMetadataAllowedValues(itemDefinition.getAllowedValues());

        List<TItemDefinition> children = itemDefinition.getItemComponent();
        com.gs.dmn.runtime.metadata.Type type;
        if (children == null || children.isEmpty()) {
            type = new TypeReference(id, name, label, isCollection, typeRef, allowedValues);
        } else {
            List<com.gs.dmn.runtime.metadata.Type> subTypes = new ArrayList<>();
            for (TItemDefinition child : children) {
                subTypes.add(makeMetadataType(model, child));
            }
            type = new CompositeType(id, name, label, isCollection, subTypes);
        }
        return type;
    }

    private com.gs.dmn.runtime.metadata.QName makeMetadataTypeRef(TDefinitions model, QualifiedName typeRef) {
        if (typeRef == null) {
            return null;
        }
        String importName = typeRef.getNamespace();
        String namespace = findNamespace(model, importName);
        return new com.gs.dmn.runtime.metadata.QName(namespace, typeRef.getLocalPart());
    }

    private String makeMetadataAllowedValues(TUnaryTests allowedValues) {
        return allowedValues == null ? null : allowedValues.getText();
    }

    private String findNamespace(TDefinitions model, String importName) {
        if (DMN_11.getFeelPrefix().equals(importName)) {
            return DMN_11.getFeelNamespace();
        }
        for (TImport import_: model.getImport()) {
            if (import_.getName().equals(importName)) {
                model = this.dmnModelRepository.getModel(import_.getNamespace());
                if (model == null) {
                    throw new DMNRuntimeException(String.format("Cannot find model for import name '%s'", importName));
                }
            }
        }
        return model.getNamespace();
    }

    //
    // Elements
    //
    private InputData makeMetadataInputData(com.gs.dmn.DRGElementReference<TInputData> reference) {
        TInputData inputData = reference.getElement();
        TDefinitions definitions = this.dmnModelRepository.getModel(inputData);
        return makeMetadataInputData(definitions, inputData);
    }

    private InputData makeMetadataInputData(TDefinitions definitions, TInputData inputData) {
        String id = inputData.getId();
        String name = inputData.getName();
        String label = inputData.getLabel();
        String diagramId = ((SignavioDMNModelRepository) this.dmnModelRepository).getDiagramId(inputData);
        String shapeId = ((SignavioDMNModelRepository) this.dmnModelRepository).getShapeId(inputData);
        String javaParameterName = this.dmnTransformer.namedElementVariableName(inputData);
        String javaTypeName = this.dmnTransformer.drgElementOutputType(inputData);
        com.gs.dmn.runtime.metadata.QName typeRef = makeMetadataTypeRef(definitions, QualifiedName.toQualifiedName(definitions, inputData.getVariable().getTypeRef()));
        return new InputData(id, name, label, diagramId, shapeId, javaParameterName, javaTypeName, typeRef);
    }

    private BKM makeMetadataBKM(TDefinitions definitions, TBusinessKnowledgeModel bkm) {
        String id = bkm.getId();
        String name = bkm.getName();
        String label = bkm.getLabel();
        String diagramId = ((SignavioDMNModelRepository) this.dmnModelRepository).getDiagramId(bkm);
        String shapeId = ((SignavioDMNModelRepository) this.dmnModelRepository).getShapeId(bkm);
        String javaFunctionName = this.dmnTransformer.bkmFunctionName(bkm);
        String javaTypeName = this.dmnTransformer.qualifiedName(this.dmnTransformer.nativeModelPackageName(definitions.getName()), this.dmnTransformer.drgElementClassName(bkm));
        String javaOutputTypeName = this.dmnTransformer.drgElementOutputType(bkm);
        com.gs.dmn.runtime.metadata.QName typeRef = makeMetadataTypeRef(definitions, this.dmnTransformer.drgElementOutputTypeRef(bkm));
        List<DRGElementReference> knowledgeReferences = makeMetadataKnowledgeReferences(bkm.getKnowledgeRequirement());
        return new BKM(id, name, label, diagramId, shapeId, javaFunctionName, javaTypeName, javaOutputTypeName, typeRef, knowledgeReferences);
    }

    private Decision makeMetadataDecision(TDefinitions definitions, TDecision decision) {
        String id = decision.getId();
        String diagramId = ((SignavioDMNModelRepository) this.dmnModelRepository).getDiagramId(decision);
        String shapeId = ((SignavioDMNModelRepository) this.dmnModelRepository).getShapeId(decision);
        String name = decision.getName();
        String label = decision.getLabel();
        String nativeParameterName = this.dmnTransformer.namedElementVariableName(decision);
        String nativeTypeName = this.dmnTransformer.qualifiedName(this.dmnTransformer.nativeModelPackageName(definitions.getName()), this.dmnTransformer.drgElementClassName(decision));
        String nativeOutputTypeName = this.dmnTransformer.drgElementOutputType(decision);
        com.gs.dmn.runtime.metadata.QName typeRef = makeMetadataTypeRef(definitions, QualifiedName.toQualifiedName(definitions, decision.getVariable().getTypeRef()));
        List<DRGElementReference> references = makeMetadataInformationReferences(decision);
        List<DRGElementReference> knowledgeReferences = makeMetadataKnowledgeReferences(decision.getKnowledgeRequirement());
        List<ExtensionElement> extensions = ((BasicSignavioDMNToJavaTransformer) this.dmnTransformer).makeMetadataExtensions(decision);
        List<InputData> transitiveRequiredInputs = makeTransitiveRequiredInputs(decision);
        String protoRequestName = this.dmnTransformer.qualifiedRequestMessageName(decision);
        String protoResponseName = this.dmnTransformer.qualifiedResponseMessageName(decision);
        return new Decision(id, name, label, diagramId, shapeId, nativeParameterName, nativeTypeName, nativeOutputTypeName, typeRef, references, knowledgeReferences, extensions, transitiveRequiredInputs, protoRequestName, protoResponseName);
    }


    //
    // References
    //
    private List<DRGElementReference> makeMetadataKnowledgeReferences(List<TKnowledgeRequirement> knowledgeRequirementList) {
        List<DRGElementReference> references = new ArrayList<>();
        for (TKnowledgeRequirement knowledgeRequirement : knowledgeRequirementList) {
            addMetadataReference(references, knowledgeRequirement.getRequiredKnowledge());
            addMetadataReference(references, knowledgeRequirement.getRequiredKnowledge());
        }
        return references;
    }

    private List<DRGElementReference> makeMetadataInformationReferences(TDecision decision) {
        List<DRGElementReference> references = new ArrayList<>();
        List<TInformationRequirement> informationRequirementList = decision.getInformationRequirement();
        for (TInformationRequirement informationRequirement : informationRequirementList) {
            addMetadataReference(references, informationRequirement.getRequiredInput());
            addMetadataReference(references, informationRequirement.getRequiredDecision());
        }
        return references;
    }

    private List<InputData> makeTransitiveRequiredInputs(TDecision element) {
        com.gs.dmn.DRGElementReference<TDecision> reference = this.dmnModelRepository.makeDRGElementReference(element);
        List<com.gs.dmn.DRGElementReference<TInputData>> drgElementReferences = this.dmnTransformer.inputDataClosure(reference);
        return drgElementReferences.stream().map(this::makeMetadataInputData).collect(Collectors.toList());
    }

    private void addMetadataReference(List<DRGElementReference> references, TDMNElementReference elementReference) {
        if (elementReference != null) {
            String href = cleanMetadataReference(elementReference.getHref());
            if (href != null) {
                references.add(new DRGElementReference(href));
            }
        }
    }

    private String cleanMetadataReference(String href) {
        return href.startsWith("#") ? href.substring(1) : href;
    }
}
