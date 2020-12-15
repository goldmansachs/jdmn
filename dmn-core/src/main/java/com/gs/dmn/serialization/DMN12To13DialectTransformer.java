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
package com.gs.dmn.serialization;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import org.omg.spec.dmn._20191111.model.*;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.*;

import static com.gs.dmn.serialization.DMNVersion.DMN_12;
import static com.gs.dmn.serialization.DMNVersion.DMN_13;

public class DMN12To13DialectTransformer extends SimpleDMNDialectTransformer<org.omg.spec.dmn._20180521.model.TDefinitions, TDefinitions> {
    private static final ObjectFactory DMN_13_OBJECT_FACTORY = new ObjectFactory();

    public DMN12To13DialectTransformer(BuildLogger logger) {
        super(logger, DMN_12, DMN_13);
    }

    @Override
    public Pair<TDefinitions, PrefixNamespaceMappings> transformDefinitions(org.omg.spec.dmn._20180521.model.TDefinitions sourceDefinitions) {
        TDefinitions definitions = transform(sourceDefinitions);
        return new Pair<>(definitions, this.prefixNamespaceMappings);
    }

    private TDefinitions transform(org.omg.spec.dmn._20180521.model.TDefinitions sourceDefinitions) {
        logger.info(String.format("Transforming '%s' from DMN 1.2 to DMN 1.3 ...", sourceDefinitions.getName()));

        TDefinitions definitions = DMN_13_OBJECT_FACTORY.createTDefinitions();

        addNamedElementProperties(sourceDefinitions, definitions);

        definitions.getImport().addAll(transformList(sourceDefinitions.getImport()));
        definitions.getItemDefinition().addAll(transformList(sourceDefinitions.getItemDefinition()));
        definitions.getDrgElement().addAll(transformList(sourceDefinitions.getDrgElement()));
        definitions.getArtifact().addAll(transformList(sourceDefinitions.getArtifact()));
        definitions.getElementCollection().addAll(transformList(sourceDefinitions.getElementCollection()));
        definitions.getBusinessContextElement().addAll(transformList(sourceDefinitions.getBusinessContextElement()));
        definitions.setExpressionLanguage(transformLanguage(sourceDefinitions.getExpressionLanguage()));
        definitions.setTypeLanguage(transformLanguage(sourceDefinitions.getTypeLanguage()));
        definitions.setNamespace(transformNamespace(sourceDefinitions.getNamespace()));
        definitions.setExporter(sourceDefinitions.getExporter());
        definitions.setExporterVersion(sourceDefinitions.getExporterVersion());
        definitions.setDMNDI(transform(sourceDefinitions.getDMNDI()));
        logger.info("Done");

        return definitions;
    }

    private TDMNElement transformElement(org.omg.spec.dmn._20180521.model.TDMNElement element) {
        if (element == null) {
            return null;
        }

        if (element instanceof org.omg.spec.dmn._20180521.model.TUnaryTests) {
            return transform((org.omg.spec.dmn._20180521.model.TUnaryTests) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TInputClause) {
            return transform((org.omg.spec.dmn._20180521.model.TInputClause) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TArtifact) {
            return transformArtifact((org.omg.spec.dmn._20180521.model.TArtifact) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TOutputClause) {
            return transform((org.omg.spec.dmn._20180521.model.TOutputClause) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TDecisionRule) {
            return transform((org.omg.spec.dmn._20180521.model.TDecisionRule) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TNamedElement) {
            return transformNamedElement((org.omg.spec.dmn._20180521.model.TNamedElement) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TExpression) {
            return transformExpression((org.omg.spec.dmn._20180521.model.TExpression) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TKnowledgeRequirement) {
            return transform((org.omg.spec.dmn._20180521.model.TKnowledgeRequirement) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TAuthorityRequirement) {
            return transform((org.omg.spec.dmn._20180521.model.TAuthorityRequirement) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TInformationRequirement) {
            return transform((org.omg.spec.dmn._20180521.model.TInformationRequirement) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TContextEntry) {
            return transform((org.omg.spec.dmn._20180521.model.TContextEntry) element);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported", element.getClass()));
        }
    }

    private TNamedElement transformNamedElement(org.omg.spec.dmn._20180521.model.TNamedElement element) {
        if (element == null) {
            return null;
        }
        if (element instanceof org.omg.spec.dmn._20180521.model.TDRGElement) {
            return transformDRGElement((org.omg.spec.dmn._20180521.model.TDRGElement) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TElementCollection) {
            return transform((org.omg.spec.dmn._20180521.model.TElementCollection) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TDefinitions) {
            return transform((org.omg.spec.dmn._20180521.model.TDefinitions) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TItemDefinition) {
            return transform((org.omg.spec.dmn._20180521.model.TItemDefinition) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TBusinessContextElement) {
            return transformBusinessContextElement((org.omg.spec.dmn._20180521.model.TBusinessContextElement) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TInformationItem) {
            return transform((org.omg.spec.dmn._20180521.model.TInformationItem) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TImport) {
            return transform((org.omg.spec.dmn._20180521.model.TImport) element);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported", element.getClass()));
        }
    }

    private void addElementProperties(org.omg.spec.dmn._20180521.model.TDMNElement source, TDMNElement target) {
        target.setDescription(source.getDescription());
        target.setExtensionElements(transform(source.getExtensionElements()));
        target.setId(source.getId());
        target.setLabel(source.getLabel());
        target.getOtherAttributes().putAll(transform(source.getOtherAttributes()));
    }

    private void addArtifactProperties(org.omg.spec.dmn._20180521.model.TArtifact source, TArtifact target) {
        addElementProperties(source, target);
    }

    private void addNamedElementProperties(org.omg.spec.dmn._20180521.model.TNamedElement source, TNamedElement target) {
        addElementProperties(source, target);
        target.setName(source.getName());
    }

    private void addDRGElementProperties(org.omg.spec.dmn._20180521.model.TDRGElement source, TDRGElement target) {
        addNamedElementProperties(source, target);
    }

    private void addInvocableProperties(org.omg.spec.dmn._20180521.model.TInvocable source, TInvocable target) {
        addDRGElementProperties(source, target);
        target.setVariable(transform(source.getVariable()));
    }

    private void addExpressionProperties(org.omg.spec.dmn._20180521.model.TExpression source, TExpression target) {
        addElementProperties(source, target);
        target.setTypeRef(transformTypeRef(source.getTypeRef()));
    }

    private void addImportProperties(org.omg.spec.dmn._20180521.model.TImport element, TImport result) {
        addNamedElementProperties(element, result);
        result.setNamespace(transformNamespace(element.getNamespace()));
        result.setLocationURI(element.getLocationURI());
        result.setImportType(transformImportType(element.getImportType()));
    }

    private JAXBElement transformJAXBElement(JAXBElement element) {
        if (element == null) {
            return null;
        }

        Object value = element.getValue();
        if (value instanceof org.omg.spec.dmn._20180521.model.TDefinitions) {
            return DMN_13_OBJECT_FACTORY.createDefinitions(transform((org.omg.spec.dmn._20180521.model.TDefinitions) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TImport) {
            return DMN_13_OBJECT_FACTORY.createImport(transform((org.omg.spec.dmn._20180521.model.TImport) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TElementCollection) {
            return DMN_13_OBJECT_FACTORY.createElementCollection(transform((org.omg.spec.dmn._20180521.model.TElementCollection) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TDecision) {
            return DMN_13_OBJECT_FACTORY.createDecision(transform((org.omg.spec.dmn._20180521.model.TDecision) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TPerformanceIndicator) {
            return DMN_13_OBJECT_FACTORY.createPerformanceIndicator(transform((org.omg.spec.dmn._20180521.model.TPerformanceIndicator) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TOrganizationUnit) {
            return DMN_13_OBJECT_FACTORY.createOrganizationUnit(transform((org.omg.spec.dmn._20180521.model.TOrganizationUnit) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TBusinessContextElement) {
            return DMN_13_OBJECT_FACTORY.createBusinessContextElement(transformBusinessContextElement((org.omg.spec.dmn._20180521.model.TBusinessContextElement) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TBusinessKnowledgeModel) {
            return DMN_13_OBJECT_FACTORY.createBusinessKnowledgeModel(transform((org.omg.spec.dmn._20180521.model.TBusinessKnowledgeModel) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TInputData) {
            return DMN_13_OBJECT_FACTORY.createInputData(transform((org.omg.spec.dmn._20180521.model.TInputData) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TKnowledgeSource) {
            return DMN_13_OBJECT_FACTORY.createKnowledgeSource(transform((org.omg.spec.dmn._20180521.model.TKnowledgeSource) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TInformationRequirement) {
            return DMN_13_OBJECT_FACTORY.createInformationRequirement(transform((org.omg.spec.dmn._20180521.model.TInformationRequirement) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TKnowledgeRequirement) {
            return DMN_13_OBJECT_FACTORY.createKnowledgeRequirement(transform((org.omg.spec.dmn._20180521.model.TKnowledgeRequirement) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TAuthorityRequirement) {
            return DMN_13_OBJECT_FACTORY.createAuthorityRequirement(transform((org.omg.spec.dmn._20180521.model.TAuthorityRequirement) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TItemDefinition) {
            return DMN_13_OBJECT_FACTORY.createItemDefinition(transform((org.omg.spec.dmn._20180521.model.TItemDefinition) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TLiteralExpression) {
            return DMN_13_OBJECT_FACTORY.createLiteralExpression(transform((org.omg.spec.dmn._20180521.model.TLiteralExpression) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TInvocation) {
            return DMN_13_OBJECT_FACTORY.createInvocation(transform((org.omg.spec.dmn._20180521.model.TInvocation) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TInformationItem) {
            return DMN_13_OBJECT_FACTORY.createInformationItem(transform((org.omg.spec.dmn._20180521.model.TInformationItem) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TDecisionTable) {
            return DMN_13_OBJECT_FACTORY.createDecisionTable(transform((org.omg.spec.dmn._20180521.model.TDecisionTable) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TTextAnnotation) {
            return DMN_13_OBJECT_FACTORY.createTextAnnotation(transform((org.omg.spec.dmn._20180521.model.TTextAnnotation) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TAssociation) {
            return DMN_13_OBJECT_FACTORY.createAssociation(transform((org.omg.spec.dmn._20180521.model.TAssociation) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TArtifact) {
            return DMN_13_OBJECT_FACTORY.createArtifact(transformArtifact((org.omg.spec.dmn._20180521.model.TArtifact) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TContext) {
            return DMN_13_OBJECT_FACTORY.createContext(transform((org.omg.spec.dmn._20180521.model.TContext) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TContextEntry) {
            return DMN_13_OBJECT_FACTORY.createContextEntry(transform((org.omg.spec.dmn._20180521.model.TContextEntry) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TFunctionDefinition) {
            return DMN_13_OBJECT_FACTORY.createFunctionDefinition(transform((org.omg.spec.dmn._20180521.model.TFunctionDefinition) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TRelation) {
            return DMN_13_OBJECT_FACTORY.createRelation(transform((org.omg.spec.dmn._20180521.model.TRelation) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TList) {
            return DMN_13_OBJECT_FACTORY.createList(transform((org.omg.spec.dmn._20180521.model.TList) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.TDecisionService) {
            return DMN_13_OBJECT_FACTORY.createDecisionService(transform((org.omg.spec.dmn._20180521.model.TDecisionService) value));
        // DMDI elements
        } else if (value instanceof org.omg.spec.dmn._20180521.model.DMNStyle) {
            return DMN_13_OBJECT_FACTORY.createDMNStyle(transform((org.omg.spec.dmn._20180521.model.DMNStyle) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.DMNLabel) {
            return DMN_13_OBJECT_FACTORY.createDMNLabel(transform((org.omg.spec.dmn._20180521.model.DMNLabel) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.DMNShape) {
            return DMN_13_OBJECT_FACTORY.createDMNShape(transform((org.omg.spec.dmn._20180521.model.DMNShape) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.DMNDiagram) {
            return DMN_13_OBJECT_FACTORY.createDMNDiagram(transform((org.omg.spec.dmn._20180521.model.DMNDiagram) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.DMNEdge) {
            return DMN_13_OBJECT_FACTORY.createDMNEdge(transform((org.omg.spec.dmn._20180521.model.DMNEdge) value));
        } else if (value instanceof org.omg.spec.dmn._20180521.model.DMNDecisionServiceDividerLine) {
            return DMN_13_OBJECT_FACTORY.createDMNDecisionServiceDividerLine(transform((org.omg.spec.dmn._20180521.model.DMNDecisionServiceDividerLine) value));
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", value.getClass()));
        }
    }

    private Collection transformList(List elements) {
        List result = new ArrayList();
        for(Object element: elements) {
            if (element instanceof JAXBElement) {
                result.add(transformJAXBElement((JAXBElement) element));
            } else if (element instanceof org.omg.spec.dmn._20180521.model.TDMNElement) {
                result.add(transformElement((org.omg.spec.dmn._20180521.model.TDMNElement) element));
            } else if (element instanceof org.omg.spec.dmn._20180521.model.TDMNElementReference) {
                result.add(transform((org.omg.spec.dmn._20180521.model.TDMNElementReference) element));
            } else if (element instanceof org.omg.spec.dmn._20180521.model.THitPolicy) {
                result.add(transform((org.omg.spec.dmn._20180521.model.THitPolicy) element));
            } else if (element instanceof org.omg.spec.dmn._20180521.model.TBuiltinAggregator) {
                result.add(transform((org.omg.spec.dmn._20180521.model.TBuiltinAggregator) element));
            } else if (element instanceof org.omg.spec.dmn._20180521.model.TDecisionTableOrientation) {
                result.add(transform((org.omg.spec.dmn._20180521.model.TDecisionTableOrientation) element));
            } else if (element instanceof org.omg.spec.dmn._20180521.model.TBinding) {
                result.add(transform((org.omg.spec.dmn._20180521.model.TBinding) element));
            // DMDI elements
            } else if (element instanceof org.omg.spec.dmn._20180521.model.DMNDiagram) {
                result.add(transform((org.omg.spec.dmn._20180521.model.DMNDiagram) element));
            } else if (element instanceof org.omg.spec.dmn._20180521.model.DMNStyle) {
                result.add(transform((org.omg.spec.dmn._20180521.model.DMNStyle) element));
            } else if (element instanceof org.omg.spec.dmn._20180521.model.Point) {
                result.add(transform((org.omg.spec.dmn._20180521.model.Point) element));
            } else {
                throw new DMNRuntimeException(String.format("'%s' not supported yet", element.getClass()));
            }
        }
        return result;
    }

    private Map<QName, String> transform(Map<QName, String> otherAttributes) {
        Map<QName, String> result = new LinkedHashMap<>();
        for(Map.Entry<QName, String> entry: otherAttributes.entrySet()) {
            QName key = entry.getKey();
            result.put(transformQName(key), entry.getValue());
        }
        return result;
    }

    private TDMNElement.ExtensionElements transform(org.omg.spec.dmn._20180521.model.TDMNElement.ExtensionElements element) {
        if (element == null) {
            return null;
        }

        TDMNElement.ExtensionElements result = DMN_13_OBJECT_FACTORY.createTDMNElementExtensionElements();
        List<Object> any = element.getAny();
        result.getAny().addAll(transformAny(any));
        return result;
    }

    private List<Object> transformAny(List<Object> any) {
        List<Object> extensions = new ArrayList<>();
        for(Object extension: any) {
            if (extension instanceof JAXBElement) {
                extensions.add(transformJAXBElement((JAXBElement) extension));
            } else if (extension instanceof Element) {
                extensions.add(transform((Element) extension));
            } else if (this.sourceVersion.getJavaPackage().equals(extension.getClass().getPackage().getName())) {
                extensions.add(transformElement((org.omg.spec.dmn._20180521.model.TDMNElement) extension));
            } else {
                extensions.add(extension);
            }
        }
        return extensions;
    }

    private TImport transform(org.omg.spec.dmn._20180521.model.TImport element) {
        if (element == null) {
            return null;
        }

        TImport result = DMN_13_OBJECT_FACTORY.createTImport();
        addImportProperties(element, result);
        return result;
    }

    private TItemDefinition transform(org.omg.spec.dmn._20180521.model.TItemDefinition element) {
        if (element == null) {
            return null;
        }

        TItemDefinition result = DMN_13_OBJECT_FACTORY.createTItemDefinition();
        addNamedElementProperties(element, result);
        result.setTypeRef(transformTypeRef(element.getTypeRef()));
        result.setAllowedValues(transform(element.getAllowedValues()));
        result.getItemComponent().addAll(transformList(element.getItemComponent()));
        result.setTypeLanguage(element.getTypeLanguage());
        result.setIsCollection(element.isIsCollection());
        return result;
    }

    private TUnaryTests transform(org.omg.spec.dmn._20180521.model.TUnaryTests element) {
        if (element == null) {
            return null;
        }

        TUnaryTests result = DMN_13_OBJECT_FACTORY.createTUnaryTests();
        addElementProperties(element, result);
        result.setText(element.getText());
        result.setExpressionLanguage(element.getExpressionLanguage());
        return result;
    }

    private TDRGElement transformDRGElement(org.omg.spec.dmn._20180521.model.TDRGElement element) {
        if (element == null) {
            return null;
        }

        if (element instanceof org.omg.spec.dmn._20180521.model.TBusinessKnowledgeModel) {
            return transform((org.omg.spec.dmn._20180521.model.TBusinessKnowledgeModel) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TDecisionService) {
            return transform((org.omg.spec.dmn._20180521.model.TDecisionService) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TDecision) {
            return transform((org.omg.spec.dmn._20180521.model.TDecision) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TInputData) {
            return transform((org.omg.spec.dmn._20180521.model.TInputData) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TKnowledgeSource) {
            return transform((org.omg.spec.dmn._20180521.model.TKnowledgeSource) element);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", element.getClass()));
        }
    }

    private TBusinessKnowledgeModel transform(org.omg.spec.dmn._20180521.model.TBusinessKnowledgeModel element) {
        if (element == null) {
            return null;
        }

        TBusinessKnowledgeModel result = DMN_13_OBJECT_FACTORY.createTBusinessKnowledgeModel();
        addInvocableProperties(element, result);
        result.setEncapsulatedLogic(transform(element.getEncapsulatedLogic()));
        result.getKnowledgeRequirement().addAll(transformList(element.getKnowledgeRequirement()));
        result.getAuthorityRequirement().addAll(transformList(element.getAuthorityRequirement()));
        return result;
    }

    private TDecisionService transform(org.omg.spec.dmn._20180521.model.TDecisionService element) {
        if (element == null) {
            return null;
        }

        TDecisionService result = DMN_13_OBJECT_FACTORY.createTDecisionService();
        addInvocableProperties(element, result);
        result.getOutputDecision().addAll(transformList(element.getOutputDecision()));
        result.getEncapsulatedDecision().addAll(transformList(element.getEncapsulatedDecision()));
        result.getInputDecision().addAll(transformList(element.getInputDecision()));
        result.getInputData().addAll(transformList(element.getInputData()));
        return result;
    }

    private TDecision transform(org.omg.spec.dmn._20180521.model.TDecision element) {
        if (element == null) {
            return null;
        }

        TDecision result = DMN_13_OBJECT_FACTORY.createTDecision();
        addDRGElementProperties(element, result);
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

    private TInputData transform(org.omg.spec.dmn._20180521.model.TInputData element) {
        if (element == null) {
            return null;
        }

        TInputData result = DMN_13_OBJECT_FACTORY.createTInputData();
        addDRGElementProperties(element, result);
        result.setVariable(transform(element.getVariable()));
        return result;
    }

    private TKnowledgeSource transform(org.omg.spec.dmn._20180521.model.TKnowledgeSource element) {
        if (element == null) {
            return null;
        }

        TKnowledgeSource result = DMN_13_OBJECT_FACTORY.createTKnowledgeSource();
        addDRGElementProperties(element, result);
        result.getAuthorityRequirement().addAll(transformList(element.getAuthorityRequirement()));
        result.setType(element.getType());
        result.setOwner(transform(element.getOwner()));
        result.setLocationURI(element.getLocationURI());
        return result;
    }

    private TArtifact transformArtifact(org.omg.spec.dmn._20180521.model.TArtifact element) {
        if (element == null) {
            return null;
        }

        if (element instanceof org.omg.spec.dmn._20180521.model.TTextAnnotation) {
            return transform((org.omg.spec.dmn._20180521.model.TTextAnnotation) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TAssociation) {
            return transform((org.omg.spec.dmn._20180521.model.TAssociation) element);
        } else {
            TArtifact result = DMN_13_OBJECT_FACTORY.createTArtifact();
            addElementProperties(element, result);
            return result;
        }
    }

    private TTextAnnotation transform(org.omg.spec.dmn._20180521.model.TTextAnnotation element) {
        if (element == null) {
            return null;
        }

        TTextAnnotation result = DMN_13_OBJECT_FACTORY.createTTextAnnotation();
        addArtifactProperties(element, result);
        result.setText(element.getText());
        result.setTextFormat(element.getTextFormat());

        return result;
    }

    private TAssociation transform(org.omg.spec.dmn._20180521.model.TAssociation element) {
        if (element == null) {
            return null;
        }

        TAssociation result = DMN_13_OBJECT_FACTORY.createTAssociation();
        addArtifactProperties(element, result);
        result.setSourceRef(transform(element.getSourceRef()));
        result.setTargetRef(transform(element.getTargetRef()));
        result.setAssociationDirection(transform(element.getAssociationDirection()));

        return result;
    }

    private TAssociationDirection transform(org.omg.spec.dmn._20180521.model.TAssociationDirection associationDirection) {
        if (associationDirection == null) {
            return null;
        } else {
            return TAssociationDirection.fromValue(associationDirection.value());
        }
    }

    private TElementCollection transform(org.omg.spec.dmn._20180521.model.TElementCollection element) {
        if (element == null) {
            return null;
        }

        TElementCollection result = DMN_13_OBJECT_FACTORY.createTElementCollection();
        addNamedElementProperties(element, result);
        result.getDrgElement().addAll(transformList(element.getDrgElement()));
        return result;
    }

    private TBusinessContextElement transformBusinessContextElement(org.omg.spec.dmn._20180521.model.TBusinessContextElement element) {
        if (element == null) {
            return null;
        }

        if (element instanceof org.omg.spec.dmn._20180521.model.TPerformanceIndicator) {
            return transform((org.omg.spec.dmn._20180521.model.TPerformanceIndicator) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TOrganizationUnit) {
            return transform((org.omg.spec.dmn._20180521.model.TOrganizationUnit) element);
        } else {
            TBusinessContextElement result = DMN_13_OBJECT_FACTORY.createTBusinessContextElement();
            addNamedElementProperties(element, result);
            result.setURI(element.getURI());
            return result;
        }
    }

    private TPerformanceIndicator transform(org.omg.spec.dmn._20180521.model.TPerformanceIndicator element) {
        if (element == null) {
            return null;
        }

        TPerformanceIndicator result = DMN_13_OBJECT_FACTORY.createTPerformanceIndicator();
        addNamedElementProperties(element, result);
        result.setURI(element.getURI());
        result.getImpactingDecision().addAll(transformList(element.getImpactingDecision()));
        return result;
    }

    private TOrganizationUnit transform(org.omg.spec.dmn._20180521.model.TOrganizationUnit element) {
        if (element == null) {
            return null;
        }

        TOrganizationUnit result = DMN_13_OBJECT_FACTORY.createTOrganizationUnit();
        addNamedElementProperties(element, result);
        result.setURI(element.getURI());
        result.getDecisionMade().addAll(transformList(element.getDecisionMade()));
        result.getDecisionOwned().addAll(transformList(element.getDecisionOwned()));
        return result;
    }

    private TInformationItem transform(org.omg.spec.dmn._20180521.model.TInformationItem element) {
        if (element == null) {
            return null;
        }

        TInformationItem result = DMN_13_OBJECT_FACTORY.createTInformationItem();
        addNamedElementProperties(element, result);
        result.setTypeRef(transformTypeRef(element.getTypeRef()));
        return result;
    }

    private TKnowledgeRequirement transform(org.omg.spec.dmn._20180521.model.TKnowledgeRequirement element) {
        if (element == null) {
            return null;
        }

        TKnowledgeRequirement result = DMN_13_OBJECT_FACTORY.createTKnowledgeRequirement();
        addElementProperties(element, result);
        result.setRequiredKnowledge(transform(element.getRequiredKnowledge()));
        return result;
    }

    private TAuthorityRequirement transform(org.omg.spec.dmn._20180521.model.TAuthorityRequirement element) {
        if (element == null) {
            return null;
        }

        TAuthorityRequirement result = DMN_13_OBJECT_FACTORY.createTAuthorityRequirement();
        addElementProperties(element, result);
        result.setRequiredDecision(transform(element.getRequiredDecision()));
        result.setRequiredInput(transform(element.getRequiredInput()));
        result.setRequiredAuthority(transform(element.getRequiredAuthority()));
        return result;
    }

    private TInformationRequirement transform(org.omg.spec.dmn._20180521.model.TInformationRequirement element) {
        if (element == null) {
            return null;
        }

        TInformationRequirement result = DMN_13_OBJECT_FACTORY.createTInformationRequirement();
        addElementProperties(element, result);
        result.setRequiredDecision(transform(element.getRequiredDecision()));
        result.setRequiredInput(transform(element.getRequiredInput()));
        return result;
    }

    private TDMNElementReference transform(org.omg.spec.dmn._20180521.model.TDMNElementReference element) {
        if (element == null) {
            return null;
        }

        TDMNElementReference result = DMN_13_OBJECT_FACTORY.createTDMNElementReference();
        result.setHref(element.getHref());
        return result;
    }

    private TExpression transformExpression(org.omg.spec.dmn._20180521.model.TExpression element) {
        if (element == null) {
            return null;
        }

        if (element instanceof org.omg.spec.dmn._20180521.model.TFunctionDefinition) {
            return transform((org.omg.spec.dmn._20180521.model.TFunctionDefinition) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TDecisionTable) {
            return transform((org.omg.spec.dmn._20180521.model.TDecisionTable) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TRelation) {
            return transform((org.omg.spec.dmn._20180521.model.TRelation) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TList) {
            return transform((org.omg.spec.dmn._20180521.model.TList) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TContext) {
            return transform((org.omg.spec.dmn._20180521.model.TContext) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TInvocation) {
            return transform((org.omg.spec.dmn._20180521.model.TInvocation) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.TLiteralExpression) {
            return transform((org.omg.spec.dmn._20180521.model.TLiteralExpression) element);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", element.getClass()));
        }
    }

    private TFunctionDefinition transform(org.omg.spec.dmn._20180521.model.TFunctionDefinition element) {
        if (element == null) {
            return null;
        }

        TFunctionDefinition result = DMN_13_OBJECT_FACTORY.createTFunctionDefinition();
        addExpressionProperties(element, result);
        result.getFormalParameter().addAll(transformList(element.getFormalParameter()));
        result.setExpression(transformJAXBElement(element.getExpression()));
        result.setKind(transform(element.getKind()));
        return result;
    }

    private TFunctionKind transform(org.omg.spec.dmn._20180521.model.TFunctionKind element) {
        if (element == null) {
            return null;
        }

        return TFunctionKind.fromValue(element.value());
    }

    private TDecisionTable transform(org.omg.spec.dmn._20180521.model.TDecisionTable element) {
        if (element == null) {
            return null;
        }

        TDecisionTable result = DMN_13_OBJECT_FACTORY.createTDecisionTable();
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

    private TRelation transform(org.omg.spec.dmn._20180521.model.TRelation element) {
        if (element == null) {
            return null;
        }

        TRelation result = DMN_13_OBJECT_FACTORY.createTRelation();
        addExpressionProperties(element, result);
        result.getColumn().addAll(transformList(element.getColumn()));
        result.getRow().addAll(transformList(element.getRow()));
        return result;
    }

    private TList transform(org.omg.spec.dmn._20180521.model.TList element) {
        if (element == null) {
            return null;
        }

        TList result = DMN_13_OBJECT_FACTORY.createTList();
        addExpressionProperties(element, result);
        result.getExpression().addAll(transformList(element.getExpression()));
        return result;
    }

    private TContext transform(org.omg.spec.dmn._20180521.model.TContext element) {
        if (element == null) {
            return null;
        }

        TContext result = DMN_13_OBJECT_FACTORY.createTContext();
        addExpressionProperties(element, result);
        result.getContextEntry().addAll(transformList(element.getContextEntry()));
        return result;
    }

    private TContextEntry transform(org.omg.spec.dmn._20180521.model.TContextEntry element) {
        if (element == null) {
            return null;
        }

        TContextEntry result = DMN_13_OBJECT_FACTORY.createTContextEntry();
        addElementProperties(element, result);
        result.setVariable(transform(element.getVariable()));
        result.setExpression(transformJAXBElement(element.getExpression()));
        return result;
    }

    private TInvocation transform(org.omg.spec.dmn._20180521.model.TInvocation element) {
        if (element == null) {
            return null;
        }

        TInvocation result = DMN_13_OBJECT_FACTORY.createTInvocation();
        addExpressionProperties(element, result);
        result.setExpression(transformJAXBElement(element.getExpression()));
        result.getBinding().addAll(transformList(element.getBinding()));
        return result;
    }

    private TBinding transform(org.omg.spec.dmn._20180521.model.TBinding element) {
        if (element == null) {
            return null;
        }

        TBinding result = DMN_13_OBJECT_FACTORY.createTBinding();
        result.setParameter(transform(element.getParameter()));
        result.setExpression(transformJAXBElement(element.getExpression()));
        return result;
    }

    private TLiteralExpression transform(org.omg.spec.dmn._20180521.model.TLiteralExpression element) {
        if (element == null) {
            return null;
        }

        TLiteralExpression result = DMN_13_OBJECT_FACTORY.createTLiteralExpression();
        addExpressionProperties(element, result);
        result.setText(element.getText());
        result.setImportedValues(transformImportedValues(element.getImportedValues()));
        result.setExpressionLanguage(element.getExpressionLanguage());
        return result;
    }

    private TImportedValues transformImportedValues(org.omg.spec.dmn._20180521.model.TImportedValues element) {
        if (element == null) {
            return null;
        }

        TImportedValues result = DMN_13_OBJECT_FACTORY.createTImportedValues();
        addImportProperties(element, result);
        result.setImportedElement(element.getImportedElement());
        result.setExpressionLanguage(element.getExpressionLanguage());
        return result;
    }

    private TDecisionTableOrientation transform(org.omg.spec.dmn._20180521.model.TDecisionTableOrientation element) {
        if (element == null) {
            return null;
        }

        return TDecisionTableOrientation.fromValue(element.value());
    }

    private TBuiltinAggregator transform(org.omg.spec.dmn._20180521.model.TBuiltinAggregator element) {
        if (element == null) {
            return null;
        }

        return TBuiltinAggregator.fromValue(element.value());
    }

    private THitPolicy transform(org.omg.spec.dmn._20180521.model.THitPolicy element) {
        if (element == null) {
            return null;
        }

        return THitPolicy.fromValue(element.value());
    }

    private TInputClause transform(org.omg.spec.dmn._20180521.model.TInputClause element) {
        if (element == null) {
            return null;
        }

        TInputClause result = DMN_13_OBJECT_FACTORY.createTInputClause();
        addElementProperties(element, result);
        result.setInputExpression(transform(element.getInputExpression()));
        result.setInputValues(transform(element.getInputValues()));
        return result;
    }

    private TOutputClause transform(org.omg.spec.dmn._20180521.model.TOutputClause element) {
        if (element == null) {
            return null;
        }

        TOutputClause result = DMN_13_OBJECT_FACTORY.createTOutputClause();
        addElementProperties(element, result);
        result.setOutputValues(transform(element.getOutputValues()));
        result.setDefaultOutputEntry(transform(element.getDefaultOutputEntry()));
        result.setName(element.getName());
        result.setTypeRef(transformTypeRef(element.getTypeRef()));
        return result;
    }

    private TDecisionRule transform(org.omg.spec.dmn._20180521.model.TDecisionRule element) {
        if (element == null) {
            return null;
        }

        TDecisionRule result = DMN_13_OBJECT_FACTORY.createTDecisionRule();
        addElementProperties(element, result);
        result.getInputEntry().addAll(transformList(element.getInputEntry()));
        result.getOutputEntry().addAll(transformList(element.getOutputEntry()));
        return result;
    }

    private String transformTypeRef(String element) {
        return element;
    }

    private String transformLanguage(String expressionLanguage) {
        if (this.sourceVersion.getFeelNamespace().equals(expressionLanguage)) {
            return this.targetVersion.getFeelNamespace();
        }
        return expressionLanguage;
    }

    private String transformNamespace(String namespace) {
        return namespace;
    }

    //
    // DMNDI section
    //
    private DMNDI transform(org.omg.spec.dmn._20180521.model.DMNDI element) {
        if (element == null) {
            return null;
        }

        DMNDI result = DMN_13_OBJECT_FACTORY.createDMNDI();
//        protected List<org.omg.spec.dmn._20180521.model.DMNDiagram> dmnDiagram;
//        protected List<org.omg.spec.dmn._20180521.model.DMNStyle> dmnStyle;
        result.getDMNDiagram().addAll(transformList(element.getDMNDiagram()));
        result.getDMNStyle().addAll(transformList(element.getDMNStyle()));
        return result;
    }

    private DMNDiagram transform(org.omg.spec.dmn._20180521.model.DMNDiagram element) {
        if (element == null) {
            return null;
        }

        DMNDiagram result = DMN_13_OBJECT_FACTORY.createDMNDiagram();
        addDiagramProperties(element, result);
//        protected org.omg.spec.dmn._20180521.model.Dimension size;
//        protected List<JAXBElement<? extends org.omg.spec.dmn._20180521.model.DiagramElement>> dmnDiagramElement;
        result.setSize(transform(element.getSize()));
        result.getDMNDiagramElement().addAll(transformList(element.getDMNDiagramElement()));
        return result;
    }

    private DiagramElement transform(org.omg.spec.dmn._20180521.model.DiagramElement element) {
        if (element == null) {
            return null;
        }

        if (element instanceof org.omg.spec.dmn._20180521.model.DMNLabel) {
            return transform((org.omg.spec.dmn._20180521.model.DMNLabel) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.DMNShape) {
            return transform((org.omg.spec.dmn._20180521.model.DMNShape) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.DMNDiagram) {
            return transform((org.omg.spec.dmn._20180521.model.DMNDiagram) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.DMNEdge) {
            return transform((org.omg.spec.dmn._20180521.model.DMNEdge) element);
        } else if (element instanceof org.omg.spec.dmn._20180521.model.DMNDecisionServiceDividerLine) {
            return transform((org.omg.spec.dmn._20180521.model.DMNDecisionServiceDividerLine) element);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported", element.getClass()));
        }
    }

    private DMNLabel transform(org.omg.spec.dmn._20180521.model.DMNLabel element) {
        if (element == null) {
            return null;
        }

        DMNLabel result = DMN_13_OBJECT_FACTORY.createDMNLabel();
        addShapeProperties(element, result);
//        protected String text;
        result.setText(element.getText());
        return result;
    }

    private DMNShape transform(org.omg.spec.dmn._20180521.model.DMNShape element) {
        if (element == null) {
            return null;
        }

        DMNShape result = DMN_13_OBJECT_FACTORY.createDMNShape();
        addShapeProperties(element, result);
//        protected org.omg.spec.dmn._20180521.model.DMNLabel dmnLabel;
//        protected org.omg.spec.dmn._20180521.model.DMNDecisionServiceDividerLine dmnDecisionServiceDividerLine;
//        protected QName dmnElementRef;
//        protected Boolean isListedInputData;
//        protected Boolean isCollapsed;
        result.setDMNLabel(transform(element.getDMNLabel()));
        result.setDMNDecisionServiceDividerLine(transform(element.getDMNDecisionServiceDividerLine()));
        result.setDmnElementRef(transformQName(element.getDmnElementRef()));
        result.setIsListedInputData(element.isIsListedInputData());
        result.setIsCollapsed(element.isIsCollapsed());
        return result;
    }

    private DMNEdge transform(org.omg.spec.dmn._20180521.model.DMNEdge element) {
        if (element == null) {
            return null;
        }

        DMNEdge result = DMN_13_OBJECT_FACTORY.createDMNEdge();
        addEdgeProperties(element, result);
//        protected org.omg.spec.dmn._20180521.model.DMNLabel dmnLabel;
//        protected QName dmnElementRef;
        result.setDMNLabel(transform(element.getDMNLabel()));
        result.setDmnElementRef(transformQName(element.getDmnElementRef()));
        return result;
    }

    private DMNDecisionServiceDividerLine transform(org.omg.spec.dmn._20180521.model.DMNDecisionServiceDividerLine element) {
        if (element == null) {
            return null;
        }

        DMNDecisionServiceDividerLine result = DMN_13_OBJECT_FACTORY.createDMNDecisionServiceDividerLine();
        addEdgeProperties(element, result);
        return result;
    }

    private DMNStyle transform(org.omg.spec.dmn._20180521.model.DMNStyle element) {
        if (element == null) {
            return null;
        }

        DMNStyle result = DMN_13_OBJECT_FACTORY.createDMNStyle();
        addStyleProperties(element, result);
//        protected org.omg.spec.dmn._20180521.model.Color fillColor;
//        protected org.omg.spec.dmn._20180521.model.Color strokeColor;
//        protected org.omg.spec.dmn._20180521.model.Color fontColor;
//        protected String fontFamily;
//        protected Double fontSize;
//        protected Boolean fontItalic;
//        protected Boolean fontBold;
//        protected Boolean fontUnderline;
//        protected Boolean fontStrikeThrough;
//        protected org.omg.spec.dmn._20180521.model.AlignmentKind labelHorizontalAlignement;
//        protected org.omg.spec.dmn._20180521.model.AlignmentKind labelVerticalAlignment;
        result.setFillColor(transform(element.getFillColor()));
        result.setStrokeColor(transform(element.getStrokeColor()));
        result.setFontColor(transform(element.getFontColor()));
        result.setFontFamily(element.getFontFamily());
        result.setFontSize(element.getFontSize());
        result.setFontItalic(element.isFontItalic());
        result.setFontBold(element.isFontBold());
        result.setFontUnderline(element.isFontUnderline());
        result.setFontStrikeThrough(element.isFontStrikeThrough());
        result.setLabelHorizontalAlignement(transform(element.getLabelHorizontalAlignement()));
        result.setLabelVerticalAlignment(transform(element.getLabelVerticalAlignment()));
        return result;
    }

    private Color transform(org.omg.spec.dmn._20180521.model.Color element) {
        if (element == null) {
            return null;
        }

        Color result = DMN_13_OBJECT_FACTORY.createColor();
//        protected int red;
//        protected int green;
//        protected int blue;
        result.setRed(element.getRed());
        result.setGreen(element.getGreen());
        result.setBlue(element.getBlue());
        return result;
    }

    private AlignmentKind transform(org.omg.spec.dmn._20180521.model.AlignmentKind element) {
        if (element == null) {
            return null;
        }

        return AlignmentKind.fromValue(element.value());
    }

    private DiagramElement.Extension transform(org.omg.spec.dmn._20180521.model.DiagramElement.Extension element) {
        if (element == null) {
            return null;
        }

        DiagramElement.Extension result = DMN_13_OBJECT_FACTORY.createDiagramElementExtension();
//        protected List<Object> any;
        result.getAny().addAll(transformAny(element.getAny()));
        return result;
    }

    private Style.Extension transform(org.omg.spec.dmn._20180521.model.Style.Extension element) {
        if (element == null) {
            return null;
        }

        Style.Extension result = DMN_13_OBJECT_FACTORY.createStyleExtension();
//        protected List<Object> any;
        result.getAny().addAll(transformAny(element.getAny()));
        return result;
    }

    private QName transformQName(QName element) {
        if (element == null) {
            return null;
        }

        String namespaceURI = element.getNamespaceURI();
        String prefix = element.getPrefix();
        String localPart = element.getLocalPart();
        return new QName(namespaceURI, localPart, prefix);
    }

    private Dimension transform(org.omg.spec.dmn._20180521.model.Dimension element) {
        if (element == null) {
            return null;
        }

        Dimension result = DMN_13_OBJECT_FACTORY.createDimension();
//        protected double width;
//        protected double height;
        result.setWidth(element.getWidth());
        result.setHeight(element.getHeight());
        return result;
    }

    private Bounds transform(org.omg.spec.dmn._20180521.model.Bounds element) {
        if (element == null) {
            return null;
        }

        Bounds result = DMN_13_OBJECT_FACTORY.createBounds();
//        protected double x;
//        protected double y;
//        protected double width;
//        protected double height;
        result.setX(element.getX());
        result.setY(element.getY());
        result.setWidth(element.getWidth());
        result.setHeight(element.getHeight());
        return result;
    }

    private Point transform(org.omg.spec.dmn._20180521.model.Point element) {
        if (element == null) {
            return null;
        }

        Point result = DMN_13_OBJECT_FACTORY.createPoint();
//        protected double x;
//        protected double y;
        result.setX(element.getX());
        result.setY(element.getY());
        return result;
    }

    private void addDiagramElementProperties(org.omg.spec.dmn._20180521.model.DiagramElement element, DiagramElement result) {
//        protected DiagramElement.Extension extension;
//        protected JAXBElement<? extends org.omg.spec.dmn._20180521.model.Style> style;
//        protected Object sharedStyle;
//        protected String id;
//        private Map<QName, String> otherAttributes = new HashMap<QName, String>();
        result.setExtension(transform(element.getExtension()));
        result.setStyle(transformJAXBElement(element.getStyle()));
        result.setSharedStyle(transformSharedStyle(element.getSharedStyle()));
        result.setId(element.getId());
        result.getOtherAttributes().putAll(transform(element.getOtherAttributes()));
    }

    private Object transformSharedStyle(Object element) {
        if (element == null) {
            return null;
        }

        if (element instanceof org.omg.spec.dmn._20180521.model.DMNStyle) {
            return transform((org.omg.spec.dmn._20180521.model.DMNStyle) element);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported", element.getClass()));
        }
    }

    private void addDiagramProperties(org.omg.spec.dmn._20180521.model.Diagram element, Diagram result) {
        addDiagramElementProperties(element, result);
//        protected String name;
//        protected String documentation;
//        protected Double resolution;
        result.setName(element.getName());
        result.setDocumentation(element.getDocumentation());
        result.setResolution(element.getResolution());
    }

    private void addShapeProperties(org.omg.spec.dmn._20180521.model.Shape element, Shape result) {
        addDiagramElementProperties(element, result);
//        protected org.omg.spec.dmn._20180521.model.Bounds bounds;
        result.setBounds(transform(element.getBounds()));
    }

    private void addEdgeProperties(org.omg.spec.dmn._20180521.model.Edge element, Edge result) {
        addDiagramElementProperties(element, result);
//        protected List<org.omg.spec.dmn._20180521.model.Point> waypoint;
        result.getWaypoint().addAll(transformList(element.getWaypoint()));
    }

    private void addStyleProperties(org.omg.spec.dmn._20180521.model.Style element, Style result) {
//        protected org.omg.spec.dmn._20180521.model.Style.Extension extension;
//        protected String id;
//        private Map<QName, String> otherAttributes = new HashMap<QName, String>();
        result.setExtension(transform(element.getExtension()));
        result.setId(element.getId());
        result.getOtherAttributes().putAll(transform(element.getOtherAttributes()));
    }
}
