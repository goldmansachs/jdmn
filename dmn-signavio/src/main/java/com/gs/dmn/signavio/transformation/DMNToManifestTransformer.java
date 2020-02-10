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
import com.gs.dmn.runtime.metadata.*;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import org.omg.spec.dmn._20180521.model.*;

import java.util.ArrayList;
import java.util.List;

import static com.gs.dmn.serialization.DMNVersion.DMN_11;

public class DMNToManifestTransformer {
    private final BasicDMN2JavaTransformer dmnTransformer;
    private final DMNModelRepository dmnModelRepository;

    public DMNToManifestTransformer(BasicDMN2JavaTransformer dmnTransformer) {
        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.dmnTransformer = dmnTransformer;
    }

    public DMNMetadata toManifest(String dmnVersion, String modelVersion, String platformVersion) {
        DMNMetadata manifest = new DMNMetadata(dmnVersion, modelVersion, platformVersion);
        for (TDefinitions definitions: this.dmnModelRepository.getAllDefinitions()) {
            // Add types
            for (TItemDefinition itemDefinition : this.dmnModelRepository.itemDefinitions(definitions)) {
                com.gs.dmn.runtime.metadata.Type type = makeMetadataType(itemDefinition);
                manifest.addType(type);
            }
            // Add elements
            for (TInputData inputData : this.dmnModelRepository.inputDatas(definitions)) {
                String id = inputData.getId();
                String name = inputData.getName();
                String label = inputData.getLabel();
                String javaParameterName = dmnTransformer.inputDataVariableName(inputData);
                String javaTypeName = dmnTransformer.drgElementOutputType(inputData);
                com.gs.dmn.runtime.metadata.QName typeRef = makeMetadataTypeRef(QualifiedName.toQualifiedName(inputData.getVariable().getTypeRef()));
                manifest.addElement(new InputData(id, name, label, javaParameterName, javaTypeName, typeRef));
            }
            for (TBusinessKnowledgeModel bkm : this.dmnModelRepository.businessKnowledgeModels(definitions)) {
                String id = bkm.getId();
                String name = bkm.getName();
                String label = bkm.getLabel();
                String javaFunctionName = dmnTransformer.bkmFunctionName(bkm);
                String javaTypeName = dmnTransformer.qualifiedName(dmnTransformer.javaRootPackageName(), dmnTransformer.drgElementClassName(bkm));
                String javaOutputTypeName = dmnTransformer.drgElementOutputType(bkm);
                com.gs.dmn.runtime.metadata.QName typeRef = makeMetadataTypeRef(dmnTransformer.drgElementOutputTypeRef(bkm));
                List<DRGElementReference> knowledgeReferences = makeMetadataKnowledgeReferences(bkm.getKnowledgeRequirement());
                com.gs.dmn.runtime.metadata.BKM element = new com.gs.dmn.runtime.metadata.BKM(id, name, label, javaFunctionName, javaTypeName, javaOutputTypeName, typeRef, knowledgeReferences);
                manifest.addElement(element);
            }
            for (TDecision decision : this.dmnModelRepository.decisions(definitions)) {
                String id = decision.getId();
                String name = decision.getName();
                String label = decision.getLabel();
                String javaParameterName = dmnTransformer.drgElementVariableName(decision);
                String javaTypeName = dmnTransformer.qualifiedName(dmnTransformer.javaRootPackageName(), dmnTransformer.drgElementClassName(decision));
                String javaOutputTypeName = dmnTransformer.drgElementOutputType(decision);
                com.gs.dmn.runtime.metadata.QName typeRef = makeMetadataTypeRef(QualifiedName.toQualifiedName(decision.getVariable().getTypeRef()));
                List<DRGElementReference> references = makeMetadataInformationReferences(decision);
                List<DRGElementReference> knowledgeReferences = makeMetadataKnowledgeReferences(decision.getKnowledgeRequirement());
                List<ExtensionElement> extensions = ((BasicSignavioDMN2JavaTransformer)dmnTransformer).makeMetadataExtensions(decision);
                com.gs.dmn.runtime.metadata.Decision element = new com.gs.dmn.runtime.metadata.Decision(id, name, label, javaParameterName, javaTypeName, javaOutputTypeName, typeRef, references, knowledgeReferences, extensions);
                manifest.addElement(element);
            }
        }
        return manifest;
    }

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

    private com.gs.dmn.runtime.metadata.Type makeMetadataType(TItemDefinition itemDefinition) {
        String id = itemDefinition.getId();
        String name = itemDefinition.getName();
        String label = itemDefinition.getLabel();
        boolean isCollection = itemDefinition.isIsCollection();
        com.gs.dmn.runtime.metadata.QName typeRef = makeMetadataTypeRef(QualifiedName.toQualifiedName(itemDefinition.getTypeRef()));
        String allowedValues = makeMetadataAllowedValues(itemDefinition.getAllowedValues());

        List<TItemDefinition> children = itemDefinition.getItemComponent();
        com.gs.dmn.runtime.metadata.Type type;
        if (children == null || children.isEmpty()) {
            type = new TypeReference(id, name, label, isCollection, typeRef, allowedValues);
        } else {
            List<com.gs.dmn.runtime.metadata.Type> subTypes = new ArrayList<>();
            for (TItemDefinition child : children) {
                subTypes.add(makeMetadataType(child));
            }
            type = new CompositeType(id, name, label, isCollection, subTypes);
        }
        return type;
    }

    private com.gs.dmn.runtime.metadata.QName makeMetadataTypeRef(QualifiedName typeRef) {
        if (typeRef == null) {
            return null;
        }
        String prefix = typeRef.getNamespace();
        if (prefix == null) {
            prefix = "";
        }
        String namespace = findNamespace(prefix);
        return new com.gs.dmn.runtime.metadata.QName(namespace, typeRef.getLocalPart());
    }

    private String makeMetadataAllowedValues(TUnaryTests allowedValues) {
        return allowedValues == null ? null : allowedValues.getText();
    }

    private String findNamespace(String prefix) {
        PrefixNamespaceMappings prefixNamespaceMappings = dmnModelRepository.getPrefixNamespaceMappings();
        if (DMN_11.getFeelPrefix().equals(prefix)) {
            return DMN_11.getFeelNamespace();
        } else {
            return prefixNamespaceMappings.get(prefix);
        }
    }
}
