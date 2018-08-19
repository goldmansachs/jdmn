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
package com.gs.dmn.serialization;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.*;

import static com.gs.dmn.serialization.DMNConstants.*;

public class DMNDialectTransformer {
    private static final ObjectFactory DMN_12_OBJECT_FACTORY = new ObjectFactory();
    private final BuildLogger logger;
    private final PrefixNamespaceMappings prefixNamespaceMappings = new PrefixNamespaceMappings();

    public DMNDialectTransformer(BuildLogger logger) {
        this.logger = logger;
    }

    public DMNModelRepository transformRepository(org.omg.spec.dmn._20151101.model.TDefinitions dmn11Definitions) {
        TDefinitions definitions = transform(dmn11Definitions);
        return new DMNModelRepository(definitions, prefixNamespaceMappings);
    }

    private TDefinitions transform(org.omg.spec.dmn._20151101.model.TDefinitions dmn11Definitions) {
        logger.info(String.format("Transforming '%s' to DMN 1.2 ...", dmn11Definitions.getName()));

        TDefinitions definitions = DMN_12_OBJECT_FACTORY.createTDefinitions();

        addNamedElementProperties(dmn11Definitions, definitions);

        definitions.getImport().addAll(transformList(dmn11Definitions.getImport()));
        definitions.getItemDefinition().addAll(transformList(dmn11Definitions.getItemDefinition()));
        definitions.getDrgElement().addAll(transformList(dmn11Definitions.getDrgElement()));
        definitions.getArtifact().addAll(transformList(dmn11Definitions.getArtifact()));
        definitions.getElementCollection().addAll(transformList(dmn11Definitions.getElementCollection()));
        definitions.getBusinessContextElement().addAll(transformList(dmn11Definitions.getBusinessContextElement()));
        definitions.setExpressionLanguage(transformLanguage(dmn11Definitions.getExpressionLanguage()));
        definitions.setTypeLanguage(transformLanguage(dmn11Definitions.getTypeLanguage()));
        definitions.setNamespace(transformNamespace(dmn11Definitions.getNamespace()));
        definitions.setExporter(dmn11Definitions.getExporter());
        definitions.setExporterVersion(dmn11Definitions.getExporterVersion());

        logger.info("Done");

        return definitions;
    }

    private TDMNElement transformElement(org.omg.spec.dmn._20151101.model.TDMNElement element) {
        if (element == null) {
            return null;
        }
        if (element instanceof org.omg.spec.dmn._20151101.model.TUnaryTests) {
            return transform((org.omg.spec.dmn._20151101.model.TUnaryTests) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TInputClause) {
            return transform((org.omg.spec.dmn._20151101.model.TInputClause) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TArtifact) {
            return transformArtifact((org.omg.spec.dmn._20151101.model.TArtifact) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TOutputClause) {
            return transform((org.omg.spec.dmn._20151101.model.TOutputClause) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TDecisionRule) {
            return transform((org.omg.spec.dmn._20151101.model.TDecisionRule) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TNamedElement) {
            return transformNamedElement((org.omg.spec.dmn._20151101.model.TNamedElement) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TExpression) {
            return transformExpression((org.omg.spec.dmn._20151101.model.TExpression) element);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported", element.getClass()));
        }
    }

    private TNamedElement transformNamedElement(org.omg.spec.dmn._20151101.model.TNamedElement element) {
        if (element == null) {
            return null;
        }
        if (element instanceof org.omg.spec.dmn._20151101.model.TDRGElement) {
            return transformDRGElement((org.omg.spec.dmn._20151101.model.TDRGElement) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TElementCollection) {
            return transform((org.omg.spec.dmn._20151101.model.TElementCollection) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TDecisionService) {
            return transform((org.omg.spec.dmn._20151101.model.TDecisionService) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TDefinitions) {
            return transform((org.omg.spec.dmn._20151101.model.TDefinitions) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TItemDefinition) {
            return transform((org.omg.spec.dmn._20151101.model.TItemDefinition) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TBusinessContextElement) {
            return transformBusinessContextElement((org.omg.spec.dmn._20151101.model.TBusinessContextElement) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TInformationItem) {
            return transform((org.omg.spec.dmn._20151101.model.TInformationItem) element);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported", element.getClass()));
        }
    }

    private void addElementProperties(org.omg.spec.dmn._20151101.model.TDMNElement source, TDMNElement target) {
        target.setDescription(source.getDescription());
        target.setExtensionElements(transform(source.getExtensionElements()));
        target.setId(source.getId());
        target.setLabel(source.getLabel());
        target.getOtherAttributes().putAll(transform(source.getOtherAttributes()));
    }

    private void addNamedElementProperties(org.omg.spec.dmn._20151101.model.TNamedElement source, TNamedElement target) {
        addElementProperties(source, target);
        target.setName(source.getName());
    }

    private void addExpressionProperties(org.omg.spec.dmn._20151101.model.TExpression source, TExpression target) {
        addElementProperties(source, target);
        target.setTypeRef(transform(source.getTypeRef()));
    }

    private void addImportProperties(org.omg.spec.dmn._20151101.model.TImport element, TImport result) {
        result.setNamespace(transformNamespace(element.getNamespace()));
        result.setLocationURI(element.getLocationURI());
        result.setImportType(element.getImportType());
    }

    private JAXBElement transformJAXBElement(JAXBElement element) {
        if (element == null) {
            return null;
        }

        Object value = element.getValue();
        if (value instanceof org.omg.spec.dmn._20151101.model.TDefinitions) {
            return DMN_12_OBJECT_FACTORY.createDefinitions(transform((org.omg.spec.dmn._20151101.model.TDefinitions) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TImport) {
            return DMN_12_OBJECT_FACTORY.createImport(transform((org.omg.spec.dmn._20151101.model.TImport) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TElementCollection) {
            return DMN_12_OBJECT_FACTORY.createElementCollection(transform((org.omg.spec.dmn._20151101.model.TElementCollection) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TDecision) {
            return DMN_12_OBJECT_FACTORY.createDecision(transform((org.omg.spec.dmn._20151101.model.TDecision) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TPerformanceIndicator) {
            return DMN_12_OBJECT_FACTORY.createPerformanceIndicator(transform((org.omg.spec.dmn._20151101.model.TPerformanceIndicator) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TOrganizationUnit) {
            return DMN_12_OBJECT_FACTORY.createOrganizationUnit(transform((org.omg.spec.dmn._20151101.model.TOrganizationUnit) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TBusinessContextElement) {
            return DMN_12_OBJECT_FACTORY.createBusinessContextElement(transformBusinessContextElement((org.omg.spec.dmn._20151101.model.TBusinessContextElement) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TBusinessKnowledgeModel) {
            return DMN_12_OBJECT_FACTORY.createBusinessKnowledgeModel(transform((org.omg.spec.dmn._20151101.model.TBusinessKnowledgeModel) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TInputData) {
            return DMN_12_OBJECT_FACTORY.createInputData(transform((org.omg.spec.dmn._20151101.model.TInputData) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TKnowledgeSource) {
            return DMN_12_OBJECT_FACTORY.createKnowledgeSource(transform((org.omg.spec.dmn._20151101.model.TKnowledgeSource) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TInformationRequirement) {
            return DMN_12_OBJECT_FACTORY.createInformationRequirement(transform((org.omg.spec.dmn._20151101.model.TInformationRequirement) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TKnowledgeRequirement) {
            return DMN_12_OBJECT_FACTORY.createKnowledgeRequirement(transform((org.omg.spec.dmn._20151101.model.TKnowledgeRequirement) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TAuthorityRequirement) {
            return DMN_12_OBJECT_FACTORY.createAuthorityRequirement(transform((org.omg.spec.dmn._20151101.model.TAuthorityRequirement) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TItemDefinition) {
            return DMN_12_OBJECT_FACTORY.createItemDefinition(transform((org.omg.spec.dmn._20151101.model.TItemDefinition) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TLiteralExpression) {
            return DMN_12_OBJECT_FACTORY.createLiteralExpression(transform((org.omg.spec.dmn._20151101.model.TLiteralExpression) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TInvocation) {
            return DMN_12_OBJECT_FACTORY.createInvocation(transform((org.omg.spec.dmn._20151101.model.TInvocation) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TInformationItem) {
            return DMN_12_OBJECT_FACTORY.createInformationItem(transform((org.omg.spec.dmn._20151101.model.TInformationItem) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TDecisionTable) {
            return DMN_12_OBJECT_FACTORY.createDecisionTable(transform((org.omg.spec.dmn._20151101.model.TDecisionTable) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TTextAnnotation) {
            return DMN_12_OBJECT_FACTORY.createTextAnnotation(transform((org.omg.spec.dmn._20151101.model.TTextAnnotation) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TAssociation) {
            return DMN_12_OBJECT_FACTORY.createAssociation(transform((org.omg.spec.dmn._20151101.model.TAssociation) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TArtifact) {
            return DMN_12_OBJECT_FACTORY.createArtifact(transformArtifact((org.omg.spec.dmn._20151101.model.TArtifact) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TContext) {
            return DMN_12_OBJECT_FACTORY.createContext(transform((org.omg.spec.dmn._20151101.model.TContext) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TContextEntry) {
            return DMN_12_OBJECT_FACTORY.createContextEntry(transform((org.omg.spec.dmn._20151101.model.TContextEntry) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TFunctionDefinition) {
            return DMN_12_OBJECT_FACTORY.createFunctionDefinition(transform((org.omg.spec.dmn._20151101.model.TFunctionDefinition) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TRelation) {
            return DMN_12_OBJECT_FACTORY.createRelation(transform((org.omg.spec.dmn._20151101.model.TRelation) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TList) {
            return DMN_12_OBJECT_FACTORY.createList(transform((org.omg.spec.dmn._20151101.model.TList) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.model.TDecisionService) {
            return DMN_12_OBJECT_FACTORY.createDecisionService(transform((org.omg.spec.dmn._20151101.model.TDecisionService) value));
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", value.getClass()));
        }
    }

    private Collection transformList(List elements) {
        List result = new ArrayList();
        for(Object element: elements) {
            if (element instanceof JAXBElement) {
                result.add(transformJAXBElement((JAXBElement) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.model.TDMNElement) {
                result.add(transformElement((org.omg.spec.dmn._20151101.model.TDMNElement) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.model.TImport) {
                result.add(transform((org.omg.spec.dmn._20151101.model.TImport) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.model.TKnowledgeRequirement) {
                result.add(transform((org.omg.spec.dmn._20151101.model.TKnowledgeRequirement) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.model.TAuthorityRequirement) {
                result.add(transform((org.omg.spec.dmn._20151101.model.TAuthorityRequirement) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.model.TInformationRequirement) {
                result.add(transform((org.omg.spec.dmn._20151101.model.TInformationRequirement) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.model.TDMNElementReference) {
                result.add(transform((org.omg.spec.dmn._20151101.model.TDMNElementReference) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.model.THitPolicy) {
                result.add(transform((org.omg.spec.dmn._20151101.model.THitPolicy) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.model.TBuiltinAggregator) {
                result.add(transform((org.omg.spec.dmn._20151101.model.TBuiltinAggregator) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.model.TDecisionTableOrientation) {
                result.add(transform((org.omg.spec.dmn._20151101.model.TDecisionTableOrientation) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.model.TContextEntry) {
                result.add(transform((org.omg.spec.dmn._20151101.model.TContextEntry) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.model.TBinding) {
                result.add(transform((org.omg.spec.dmn._20151101.model.TBinding) element));
            } else {
                throw new DMNRuntimeException(String.format("'%s' not supported yet", element.getClass()));
            }
        }
        return result;
    }

    private Map<QName, String> transform(Map<QName, String> otherAttributes) {
        Map<QName, String> result = new LinkedHashMap<>();
        for(QName key: otherAttributes.keySet()) {
            result.put(new QName(key.getNamespaceURI(), key.getLocalPart(), key.getPrefix()), otherAttributes.get(key));
        }
        return result;
    }

    private TDMNElement.ExtensionElements transform(org.omg.spec.dmn._20151101.model.TDMNElement.ExtensionElements element) {
        if (element == null) {
            return null;
        }

        TDMNElement.ExtensionElements result = DMN_12_OBJECT_FACTORY.createTDMNElementExtensionElements();
        List<Object> extensions = new ArrayList<>();
        for(Object extension: element.getAny()) {
            if (extension instanceof JAXBElement) {
                extensions.add(transformJAXBElement((JAXBElement) extension));
            } else if (DMNConstants.DMN_11_PACKAGE.equals(extension.getClass().getPackage().getName())) {
                extensions.add(transformElement((org.omg.spec.dmn._20151101.model.TDMNElement)extension));
            } else {
                extensions.add(extension);
            }
        }
        result.getAny().addAll(extensions);
        return result;
    }

    private TImport transform(org.omg.spec.dmn._20151101.model.TImport element) {
        if (element == null) {
            return null;
        }

        TImport result = DMN_12_OBJECT_FACTORY.createTImport();
        addImportProperties(element, result);
        return result;
    }

    private TItemDefinition transform(org.omg.spec.dmn._20151101.model.TItemDefinition element) {
        if (element == null) {
            return null;
        }

        TItemDefinition result = DMN_12_OBJECT_FACTORY.createTItemDefinition();
        addNamedElementProperties(element, result);
        result.setTypeRef(transform(element.getTypeRef()));
        result.setAllowedValues(transform(element.getAllowedValues()));
        result.getItemComponent().addAll(transformList(element.getItemComponent()));
        result.setTypeLanguage(element.getTypeLanguage());
        result.setIsCollection(element.isIsCollection());
        return result;
    }

    private TUnaryTests transform(org.omg.spec.dmn._20151101.model.TUnaryTests element) {
        if (element == null) {
            return null;
        }

        TUnaryTests result = DMN_12_OBJECT_FACTORY.createTUnaryTests();
        addElementProperties(element, result);
        result.setText(element.getText());
        result.setExpressionLanguage(element.getExpressionLanguage());
        return result;
    }

    private TDRGElement transformDRGElement(org.omg.spec.dmn._20151101.model.TDRGElement element) {
        if (element == null) {
            return null;
        }

        if (element instanceof org.omg.spec.dmn._20151101.model.TBusinessKnowledgeModel) {
            return transform((org.omg.spec.dmn._20151101.model.TBusinessKnowledgeModel) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TDecision) {
            return transform((org.omg.spec.dmn._20151101.model.TDecision) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TInputData) {
            return transform((org.omg.spec.dmn._20151101.model.TInputData) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TKnowledgeSource) {
            return transform((org.omg.spec.dmn._20151101.model.TKnowledgeSource) element);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", element.getClass()));
        }
    }

    private TBusinessKnowledgeModel transform(org.omg.spec.dmn._20151101.model.TBusinessKnowledgeModel element) {
        if (element == null) {
            return null;
        }

        TBusinessKnowledgeModel result = DMN_12_OBJECT_FACTORY.createTBusinessKnowledgeModel();
        addNamedElementProperties(element, result);
        result.setEncapsulatedLogic(transform(element.getEncapsulatedLogic()));
        result.setVariable(transform(element.getVariable()));
        result.getKnowledgeRequirement().addAll(transformList(element.getKnowledgeRequirement()));
        result.getAuthorityRequirement().addAll(transformList(element.getAuthorityRequirement()));
        return result;
    }

    private TDecision transform(org.omg.spec.dmn._20151101.model.TDecision element) {
        if (element == null) {
            return null;
        }

        TDecision result = DMN_12_OBJECT_FACTORY.createTDecision();
        addNamedElementProperties(element, result);
        result.setQuestion(element.getQuestion());
        result.setAllowedAnswers(element.getAllowedAnswers());
        result.setVariable(transform(element.getVariable()));
        result.getInformationRequirement().addAll(transformList(element.getInformationRequirement()));
        result.getKnowledgeRequirement().addAll(transformList(element.getKnowledgeRequirement()));
        result.getAuthorityRequirement().addAll(transformList(element.getAuthorityRequirement()));
        result.getSupportedObjective().addAll(transformList(element.getSupportedObjective()));
        result.getImpactedPerformanceIndicator().addAll(transformList(element.getImpactedPerformanceIndicator()));
        result.getDecisionMaker().addAll(transformList(element.getDecisionMaker()));
        result.getDecisionOwner().addAll(transformList(element.getDecisionOwner()));
        result.getUsingProcess().addAll(transformList(element.getUsingProcess()));
        result.getUsingTask().addAll(transformList(element.getUsingTask()));
        result.setExpression(transformJAXBElement(element.getExpression()));
        return result;
    }

    private TInputData transform(org.omg.spec.dmn._20151101.model.TInputData element) {
        if (element == null) {
            return null;
        }

        TInputData result = DMN_12_OBJECT_FACTORY.createTInputData();
        addNamedElementProperties(element, result);
        result.setVariable(transform(element.getVariable()));
        return result;
    }

    private TKnowledgeSource transform(org.omg.spec.dmn._20151101.model.TKnowledgeSource element) {
        if (element == null) {
            return null;
        }

        TKnowledgeSource result = DMN_12_OBJECT_FACTORY.createTKnowledgeSource();
        addNamedElementProperties(element, result);
        result.getAuthorityRequirement().addAll(transformList(element.getAuthorityRequirement()));
        result.setType(element.getType());
        result.setOwner(transform(element.getOwner()));
        result.setLocationURI(element.getLocationURI());
        return result;
    }

    private TArtifact transformArtifact(org.omg.spec.dmn._20151101.model.TArtifact element) {
        if (element == null) {
            return null;
        }

        if (element instanceof org.omg.spec.dmn._20151101.model.TTextAnnotation) {
            return transform((org.omg.spec.dmn._20151101.model.TTextAnnotation) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TAssociation) {
            return transform((org.omg.spec.dmn._20151101.model.TAssociation) element);
        } else {
            TArtifact result = DMN_12_OBJECT_FACTORY.createTArtifact();
            addElementProperties(element, result);
            return result;
        }
    }

    private TTextAnnotation transform(org.omg.spec.dmn._20151101.model.TTextAnnotation element) {
        if (element == null) {
            return null;
        }

        TTextAnnotation result = DMN_12_OBJECT_FACTORY.createTTextAnnotation();
        addElementProperties(element, result);
        result.setText(element.getText());
        result.setTextFormat(element.getTextFormat());

        return result;
    }

    private TAssociation transform(org.omg.spec.dmn._20151101.model.TAssociation element) {
        if (element == null) {
            return null;
        }

        TAssociation result = DMN_12_OBJECT_FACTORY.createTAssociation();
        addElementProperties(element, result);
        result.setSourceRef(transform(element.getSourceRef()));
        result.setTargetRef(transform(element.getTargetRef()));
        result.setAssociationDirection(transform(element.getAssociationDirection()));

        return result;
    }

    private TAssociationDirection transform(org.omg.spec.dmn._20151101.model.TAssociationDirection associationDirection) {
        if (associationDirection == null) {
            return null;
        } else {
            return TAssociationDirection.fromValue(associationDirection.value());
        }
    }

    private TElementCollection transform(org.omg.spec.dmn._20151101.model.TElementCollection element) {
        if (element == null) {
            return null;
        }

        TElementCollection result = DMN_12_OBJECT_FACTORY.createTElementCollection();
        addNamedElementProperties(element, result);
        result.getDrgElement().addAll(transformList(element.getDrgElement()));
        return result;
    }

    private TDecisionService transform(org.omg.spec.dmn._20151101.model.TDecisionService element) {
        if (element == null) {
            return null;
        }

        TDecisionService result = DMN_12_OBJECT_FACTORY.createTDecisionService();
        addNamedElementProperties(element, result);
        result.getOutputDecision().addAll(transformList(element.getOutputDecision()));
        result.getEncapsulatedDecision().addAll(transformList(element.getEncapsulatedDecision()));
        result.getInputDecision().addAll(transformList(element.getInputDecision()));
        result.getInputData().addAll(transformList(element.getInputData()));
        return result;
    }

    private TBusinessContextElement transformBusinessContextElement(org.omg.spec.dmn._20151101.model.TBusinessContextElement element) {
        if (element == null) {
            return null;
        }

        if (element instanceof org.omg.spec.dmn._20151101.model.TPerformanceIndicator) {
            return transform((org.omg.spec.dmn._20151101.model.TPerformanceIndicator) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TOrganizationUnit) {
            return transform((org.omg.spec.dmn._20151101.model.TOrganizationUnit) element);
        } else {
            TBusinessContextElement result = DMN_12_OBJECT_FACTORY.createTBusinessContextElement();
            addNamedElementProperties(element, result);
            result.setURI(element.getURI());
            return result;
        }
    }

    private TPerformanceIndicator transform(org.omg.spec.dmn._20151101.model.TPerformanceIndicator element) {
        if (element == null) {
            return null;
        }

        TPerformanceIndicator result = DMN_12_OBJECT_FACTORY.createTPerformanceIndicator();
        addNamedElementProperties(element, result);
        result.setURI(element.getURI());
        result.getImpactingDecision().addAll(transformList(element.getImpactingDecision()));
        return result;
    }

    private TOrganizationUnit transform(org.omg.spec.dmn._20151101.model.TOrganizationUnit element) {
        if (element == null) {
            return null;
        }

        TOrganizationUnit result = DMN_12_OBJECT_FACTORY.createTOrganizationUnit();
        addNamedElementProperties(element, result);
        result.setURI(element.getURI());
        result.getDecisionMade().addAll(transformList(element.getDecisionMade()));
        result.getDecisionOwned().addAll(transformList(element.getDecisionOwned()));
        return result;
    }

    private TInformationItem transform(org.omg.spec.dmn._20151101.model.TInformationItem element) {
        if (element == null) {
            return null;
        }

        TInformationItem result = DMN_12_OBJECT_FACTORY.createTInformationItem();
        addNamedElementProperties(element, result);
        result.setTypeRef(transform(element.getTypeRef()));
        return result;
    }

    private TKnowledgeRequirement transform(org.omg.spec.dmn._20151101.model.TKnowledgeRequirement element) {
        if (element == null) {
            return null;
        }

        TKnowledgeRequirement result = DMN_12_OBJECT_FACTORY.createTKnowledgeRequirement();
        result.setRequiredKnowledge(transform(element.getRequiredKnowledge()));
        return result;
    }

    private TAuthorityRequirement transform(org.omg.spec.dmn._20151101.model.TAuthorityRequirement element) {
        if (element == null) {
            return null;
        }

        TAuthorityRequirement result = DMN_12_OBJECT_FACTORY.createTAuthorityRequirement();
        result.setRequiredDecision(transform(element.getRequiredDecision()));
        result.setRequiredInput(transform(element.getRequiredInput()));
        result.setRequiredAuthority(transform(element.getRequiredAuthority()));
        return result;
    }

    private TInformationRequirement transform(org.omg.spec.dmn._20151101.model.TInformationRequirement element) {
        if (element == null) {
            return null;
        }

        TInformationRequirement result = DMN_12_OBJECT_FACTORY.createTInformationRequirement();
        result.setRequiredDecision(transform(element.getRequiredDecision()));
        result.setRequiredInput(transform(element.getRequiredInput()));
        return result;
    }

    private TDMNElementReference transform(org.omg.spec.dmn._20151101.model.TDMNElementReference element) {
        if (element == null) {
            return null;
        }

        TDMNElementReference result = DMN_12_OBJECT_FACTORY.createTDMNElementReference();
        result.setHref(element.getHref());
        return result;
    }

    private TExpression transformExpression(org.omg.spec.dmn._20151101.model.TExpression element) {
        if (element == null) {
            return null;
        }

        if (element instanceof org.omg.spec.dmn._20151101.model.TFunctionDefinition) {
            return transform((org.omg.spec.dmn._20151101.model.TFunctionDefinition) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TDecisionTable) {
            return transform((org.omg.spec.dmn._20151101.model.TDecisionTable) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TRelation) {
            return transform((org.omg.spec.dmn._20151101.model.TRelation) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TList) {
            return transform((org.omg.spec.dmn._20151101.model.TList) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TContext) {
            return transform((org.omg.spec.dmn._20151101.model.TContext) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TInvocation) {
            return transform((org.omg.spec.dmn._20151101.model.TInvocation) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.model.TLiteralExpression) {
            return transform((org.omg.spec.dmn._20151101.model.TLiteralExpression) element);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", element.getClass()));
        }
    }

    private TFunctionDefinition transform(org.omg.spec.dmn._20151101.model.TFunctionDefinition element) {
        if (element == null) {
            return null;
        }

        TFunctionDefinition result = DMN_12_OBJECT_FACTORY.createTFunctionDefinition();
        addExpressionProperties(element, result);
        result.getFormalParameter().addAll(transformList(element.getFormalParameter()));
        result.setExpression(transformJAXBElement(element.getExpression()));
        return result;
    }

    private TDecisionTable transform(org.omg.spec.dmn._20151101.model.TDecisionTable element) {
        if (element == null) {
            return null;
        }

        TDecisionTable result = DMN_12_OBJECT_FACTORY.createTDecisionTable();
        addExpressionProperties(element, result);
        result.getInput().addAll(transformList(element.getInput()));
        result.getOutput().addAll(transformList(element.getOutput()));
        result.getRule().addAll(transformList(element.getRule()));
        result.setHitPolicy(transform(element.getHitPolicy()));
        result.setAggregation(transform(element.getAggregation()));
        result.setPreferredOrientation(transform(element.getPreferredOrientation()));
        result.setOutputLabel(element.getOutputLabel());
        return result;
    }

    private TRelation transform(org.omg.spec.dmn._20151101.model.TRelation element) {
        if (element == null) {
            return null;
        }

        TRelation result = DMN_12_OBJECT_FACTORY.createTRelation();
        addExpressionProperties(element, result);
        result.getColumn().addAll(transformList(element.getColumn()));
        result.getRow().addAll(transformList(element.getRow()));
        return result;
    }

    private TList transform(org.omg.spec.dmn._20151101.model.TList element) {
        if (element == null) {
            return null;
        }

        TList result = DMN_12_OBJECT_FACTORY.createTList();
        addExpressionProperties(element, result);
        result.getExpression().addAll(transformList(element.getExpression()));
        return result;
    }

    private TContext transform(org.omg.spec.dmn._20151101.model.TContext element) {
        if (element == null) {
            return null;
        }

        TContext result = DMN_12_OBJECT_FACTORY.createTContext();
        addExpressionProperties(element, result);
        result.getContextEntry().addAll(transformList(element.getContextEntry()));
        return result;
    }

    private TContextEntry transform(org.omg.spec.dmn._20151101.model.TContextEntry element) {
        if (element == null) {
            return null;
        }

        TContextEntry result = DMN_12_OBJECT_FACTORY.createTContextEntry();
        result.setVariable(transform(element.getVariable()));
        result.setExpression(transformJAXBElement(element.getExpression()));
        return result;
    }

    private TInvocation transform(org.omg.spec.dmn._20151101.model.TInvocation element) {
        if (element == null) {
            return null;
        }

        TInvocation result = DMN_12_OBJECT_FACTORY.createTInvocation();
        addExpressionProperties(element, result);
        result.setExpression(transformJAXBElement(element.getExpression()));
        result.getBinding().addAll(transformList(element.getBinding()));
        return result;
    }

    private TBinding transform(org.omg.spec.dmn._20151101.model.TBinding element) {
        if (element == null) {
            return null;
        }

        TBinding result = DMN_12_OBJECT_FACTORY.createTBinding();
        result.setParameter(transform(element.getParameter()));
        result.setExpression(transformJAXBElement(element.getExpression()));
        return result;
    }

    private TLiteralExpression transform(org.omg.spec.dmn._20151101.model.TLiteralExpression element) {
        if (element == null) {
            return null;
        }

        TLiteralExpression result = DMN_12_OBJECT_FACTORY.createTLiteralExpression();
        addExpressionProperties(element, result);
        result.setText(element.getText());
        result.setImportedValues(transformImportedValues(element.getImportedValues()));
        result.setExpressionLanguage(element.getExpressionLanguage());
        return result;
    }

    private TImportedValues transformImportedValues(org.omg.spec.dmn._20151101.model.TImportedValues element) {
        if (element == null) {
            return null;
        }

        TImportedValues result = DMN_12_OBJECT_FACTORY.createTImportedValues();
        addImportProperties(element, result);
        result.setImportedElement(element.getImportedElement());
        result.setExpressionLanguage(element.getExpressionLanguage());
        return null;
    }

    private TDecisionTableOrientation transform(org.omg.spec.dmn._20151101.model.TDecisionTableOrientation element) {
        if (element == null) {
            return null;
        }

        return TDecisionTableOrientation.fromValue(element.value());
    }

    private TBuiltinAggregator transform(org.omg.spec.dmn._20151101.model.TBuiltinAggregator element) {
        if (element == null) {
            return null;
        }

        return TBuiltinAggregator.fromValue(element.value());
    }

    private THitPolicy transform(org.omg.spec.dmn._20151101.model.THitPolicy element) {
        if (element == null) {
            return null;
        }

        return THitPolicy.fromValue(element.value());
    }

    private TInputClause transform(org.omg.spec.dmn._20151101.model.TInputClause element) {
        if (element == null) {
            return null;
        }

        TInputClause result = DMN_12_OBJECT_FACTORY.createTInputClause();
        addElementProperties(element, result);
        result.setInputExpression(transform(element.getInputExpression()));
        result.setInputValues(transform(element.getInputValues()));
        return result;
    }

    private TOutputClause transform(org.omg.spec.dmn._20151101.model.TOutputClause element) {
        if (element == null) {
            return null;
        }

        TOutputClause result = DMN_12_OBJECT_FACTORY.createTOutputClause();
        addElementProperties(element, result);
        result.setOutputValues(transform(element.getOutputValues()));
        result.setDefaultOutputEntry(transform(element.getDefaultOutputEntry()));
        result.setName(element.getName());
        result.setTypeRef(transform(element.getTypeRef()));
        return result;
    }

    private TDecisionRule transform(org.omg.spec.dmn._20151101.model.TDecisionRule element) {
        if (element == null) {
            return null;
        }

        TDecisionRule result = DMN_12_OBJECT_FACTORY.createTDecisionRule();
        addElementProperties(element, result);
        result.getInputEntry().addAll(transformList(element.getInputEntry()));
        result.getOutputEntry().addAll(transformList(element.getOutputEntry()));
        return result;
    }

    private String transform(QName element) {
        if (element == null) {
            return null;
        }

        String namespaceURI = element.getNamespaceURI();
        String prefix = element.getPrefix();
        String localPart = element.getLocalPart();
        this.prefixNamespaceMappings.put(prefix, namespaceURI);
        if (FEEL_11_NS.equals(namespaceURI)) {
            return String.format("%s.%s", FEEL_11_PREFIX, localPart);
        } else if (FEEL_12_NS.equals(namespaceURI)) {
            return String.format("%s.%s", FEEL_12_PREFIX, localPart);
        } else {
            if (StringUtils.isBlank(prefix)) {
                if (localPart.contains(".")) {
                    return String.format(".%s", localPart);
                } else {
                    return localPart;
                }
            } else {
                return String.format("%s.%s", prefix, localPart);
            }
        }
    }

    private String transformLanguage(String expressionLanguage) {
        if (FEEL_11_NS.equals(expressionLanguage)) {
            return FEEL_12_NS;
        } else {
            return expressionLanguage;
        }
    }

    private String transformNamespace(String namespace) {
        if (DMN_11_NS.equals(namespace)) {
            return DMN_12_NS;
        } else {
            return namespace;
        }
    }
}
