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
package com.gs.dmn.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.metadata.*;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.serialization.DMNVersion.DMN_11;

public class DMNToManifestTransformer {
    // Unique HREFs across multiple XML files
    public static String uniqueHref(String href, TDefinitions importingModel, boolean multiModels) {
        if (multiModels) {
            // Add missing namespace for references in the same model
            if (href != null && href.startsWith("#")) {
                href = importingModel.getNamespace() + href;
            }
        } else {
            // Remove existing namespace: references in the same model
            int i = href.indexOf("#");
            href = i != -1 ? href.substring(i + 1) : href;
        }
        return href;
    }

    // Unique IDss across multiple XML files
    public static String uniqueId(String id, TDefinitions containingModel, boolean multiModels) {
        if (multiModels) {
            return String.format("%s#%s", containingModel.getNamespace(), id);
        } else {
            return id;
        }
    }

    protected final BasicDMNToNativeTransformer<com.gs.dmn.el.analysis.semantics.type.Type, DMNContext> dmnTransformer;
    protected final DMNModelRepository dmnModelRepository;
    private final BuildLogger logger;

    public DMNToManifestTransformer(BasicDMNToNativeTransformer<com.gs.dmn.el.analysis.semantics.type.Type, DMNContext> dmnTransformer, BuildLogger logger) {
        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.dmnTransformer = dmnTransformer;
        this.logger = logger;
    }

    public DMNMetadata toManifest(List<String> dmnNamespaces, String nativeNamespace, String dmnVersion, String modelVersion, String platformVersion) {
        DMNMetadata manifest = new DMNMetadata(dmnNamespaces, nativeNamespace, dmnVersion, modelVersion, platformVersion);
        boolean  multiModels = this.dmnModelRepository.getAllDefinitions().size() > 1;
        for (TDefinitions definitions: this.dmnModelRepository.getAllDefinitions()) {
            // Add types
            for (TItemDefinition itemDefinition : this.dmnModelRepository.findItemDefinitions(definitions)) {
                Type type = makeMetadataType(itemDefinition, definitions, multiModels);
                manifest.addType(type);
            }
            // Add elements
            for (TInputData inputData : this.dmnModelRepository.findInputDatas(definitions)) {
                InputData element = makeMetadataInputData(inputData, definitions, multiModels);
                manifest.addElement(element);
            }
            for (TBusinessKnowledgeModel bkm : this.dmnModelRepository.findBKMs(definitions)) {
                BKM element = makeMetadataBKM(bkm, definitions, multiModels);
                manifest.addElement(element);
            }
            for (TDecision decision : this.dmnModelRepository.findDecisions(definitions)) {
                Decision element = makeMetadataDecision(decision, definitions, multiModels);
                manifest.addElement(element);
            }
        }
        return manifest;
    }

    //
    // Types
    //
    private Type makeMetadataType(TItemDefinition itemDefinition, TDefinitions containingModel, boolean multiModels) {
        String id = uniqueId(itemDefinition.getId(), containingModel, multiModels);
        String name = itemDefinition.getName();
        String label = itemDefinition.getLabel();
        boolean isCollection = itemDefinition.isIsCollection();
        QName typeRef = makeMetadataTypeRef(containingModel, QualifiedName.toQualifiedName(containingModel, itemDefinition.getTypeRef()));
        String allowedValues = makeMetadataAllowedValues(itemDefinition.getAllowedValues());

        List<TItemDefinition> children = itemDefinition.getItemComponent();
        String namespace = containingModel.getNamespace();
        Type type;
        if (children == null || children.isEmpty()) {
            type = new TypeReference(namespace, id, name, label, isCollection, typeRef, allowedValues);
        } else {
            List<Type> subTypes = new ArrayList<>();
            for (TItemDefinition child : children) {
                subTypes.add(makeMetadataType(child, containingModel, multiModels));
            }
            type = new CompositeType(namespace, id, name, label, isCollection, subTypes);
        }
        return type;
    }

    protected QName makeMetadataTypeRef(TDefinitions model, QualifiedName typeRef) {
        if (this.dmnModelRepository.isNull(typeRef)) {
            return null;
        }
        String importName = typeRef.getNamespace();
        String namespace = findNamespace(model, importName);
        return new QName(namespace, typeRef.getLocalPart());
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
    private InputData makeMetadataInputData(com.gs.dmn.DRGElementReference<TInputData> reference, boolean multiModels) {
        TInputData inputData = reference.getElement();
        TDefinitions definitions = this.dmnModelRepository.getModel(inputData);
        return makeMetadataInputData(inputData, definitions, multiModels);
    }

    protected InputData makeMetadataInputData(TInputData inputData, TDefinitions containingModel, boolean multiModels) {
        String namespace = containingModel.getNamespace();
        String id = uniqueId(inputData.getId(), containingModel, multiModels);
        String name = inputData.getName();
        String label = inputData.getLabel();
        String diagramId = getDiagramId(inputData);
        String shapeId = getShapeId(inputData);
        String javaParameterName = this.dmnTransformer.namedElementVariableName(inputData);
        String javaTypeName = getJavaTypeName(inputData);
        QName typeRef = makeMetadataTypeRef(containingModel, QualifiedName.toQualifiedName(containingModel, inputData.getVariable().getTypeRef()));
        return new InputData(namespace, id, name, label, diagramId, shapeId, javaParameterName, javaTypeName, typeRef);
    }

    protected BKM makeMetadataBKM(TBusinessKnowledgeModel bkm, TDefinitions containingModel, boolean multiModels) {
        String namespace = containingModel.getNamespace();
        String id = uniqueId(bkm.getId(), containingModel, multiModels);
        String name = bkm.getName();
        String label = bkm.getLabel();
        String diagramId = getDiagramId(bkm);
        String shapeId = getShapeId(bkm);
        String javaParameterName = this.dmnTransformer.namedElementVariableName(bkm);
        String javaTypeName = this.dmnTransformer.qualifiedName(this.dmnTransformer.nativeModelPackageName(containingModel.getName()), this.dmnTransformer.drgElementClassName(bkm));
        String javaOutputTypeName = getJavaTypeName(bkm);
        QName typeRef = makeMetadataTypeRef(containingModel, QualifiedName.toQualifiedName(containingModel, bkm.getVariable().getTypeRef()));
        List<DRGElementReference> knowledgeReferences = makeMetadataKnowledgeReferences(bkm.getKnowledgeRequirement(), containingModel, multiModels);
        return new BKM(namespace, id, name, label, diagramId, shapeId, javaParameterName, javaTypeName, javaOutputTypeName, typeRef, knowledgeReferences);
    }

    protected Decision makeMetadataDecision(TDecision decision, TDefinitions containingModels, boolean multiModels) {
        String namespace = containingModels.getNamespace();
        String id = uniqueId(decision.getId(), containingModels, multiModels);
        String diagramId = getDiagramId(decision);
        String shapeId = getShapeId(decision);
        String name = decision.getName();
        String label = decision.getLabel();
        String nativeParameterName = this.dmnTransformer.namedElementVariableName(decision);
        String nativeTypeName = this.dmnTransformer.qualifiedName(this.dmnTransformer.nativeModelPackageName(containingModels.getName()), this.dmnTransformer.drgElementClassName(decision));
        String nativeOutputTypeName = getJavaTypeName(decision);
        QName typeRef = makeMetadataTypeRef(containingModels, QualifiedName.toQualifiedName(containingModels, decision.getVariable().getTypeRef()));
        List<DRGElementReference> references = makeMetadataInformationReferences(decision.getInformationRequirement(), containingModels, multiModels);
        List<DRGElementReference> knowledgeReferences = makeMetadataKnowledgeReferences(decision.getKnowledgeRequirement(), containingModels, multiModels);
        List<ExtensionElement> extensions = getExtensions(decision, containingModels, multiModels);
        List<InputData> transitiveRequiredInputs = makeTransitiveRequiredInputs(decision, multiModels);
        String protoRequestName = this.dmnTransformer.qualifiedRequestMessageName(decision);
        String protoResponseName = this.dmnTransformer.qualifiedResponseMessageName(decision);
        return new Decision(namespace, id, name, label, diagramId, shapeId, nativeParameterName, nativeTypeName, nativeOutputTypeName, typeRef, references, knowledgeReferences, extensions, transitiveRequiredInputs, protoRequestName, protoResponseName);
    }

    protected List<ExtensionElement> getExtensions(TDecision decision, TDefinitions importingModel, boolean multiModels) {
        return null;
    }

    protected String getDiagramId(TDRGElement element) {
        return null;
    }

    protected String getShapeId(TDRGElement element) {
        return null;
    }

    private String getJavaTypeName(TDRGElement element) {
        try {
            return this.dmnTransformer.drgElementOutputType(element);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return Object.class.getName();
        }
    }

    //
    // References
    //
    protected List<DRGElementReference> makeMetadataKnowledgeReferences(List<TKnowledgeRequirement> knowledgeRequirementList, TDefinitions importingModel, boolean multiModels) {
        List<DRGElementReference> references = new ArrayList<>();
        for (TKnowledgeRequirement knowledgeRequirement : knowledgeRequirementList) {
            addMetadataReference(references, knowledgeRequirement.getRequiredKnowledge(), importingModel, multiModels);
            addMetadataReference(references, knowledgeRequirement.getRequiredKnowledge(), importingModel, multiModels);
        }
        return references;
    }

    protected List<DRGElementReference> makeMetadataInformationReferences(List<TInformationRequirement> informationRequirementList, TDefinitions importingModel, boolean multiModels) {
        List<DRGElementReference> references = new ArrayList<>();
        for (TInformationRequirement informationRequirement : informationRequirementList) {
            addMetadataReference(references, informationRequirement.getRequiredInput(), importingModel, multiModels);
            addMetadataReference(references, informationRequirement.getRequiredDecision(), importingModel, multiModels);
        }
        return references;
    }

    protected List<InputData> makeTransitiveRequiredInputs(TDecision element, boolean multiModels) {
        com.gs.dmn.DRGElementReference<TDecision> reference = this.dmnModelRepository.makeDRGElementReference(element);
        List<com.gs.dmn.DRGElementReference<TInputData>> drgElementReferences = this.dmnTransformer.inputDataClosure(reference);
        return drgElementReferences.stream().map(e -> makeMetadataInputData(e, multiModels)).collect(Collectors.toList());
    }

    private void addMetadataReference(List<DRGElementReference> references, TDMNElementReference elementReference, TDefinitions importingModel, boolean multiModels) {
        if (elementReference != null) {
            String id = uniqueHref(elementReference.getHref(), importingModel, multiModels);
            if (id != null) {
                references.add(new DRGElementReference(id));
            }
        }
    }
}
