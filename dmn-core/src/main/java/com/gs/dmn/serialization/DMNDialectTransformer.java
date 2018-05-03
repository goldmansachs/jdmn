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

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.*;

import static com.gs.dmn.serialization.DMNConstants.*;

public class DMNDialectTransformer {
    private static final ObjectFactory dmnFactory = new ObjectFactory();
    private final BuildLogger logger;

    public DMNDialectTransformer(BuildLogger logger) {
        this.logger = logger;
    }

    public TDefinitions transform(org.omg.spec.dmn._20151101.dmn.TDefinitions dmn11Definitions) {
        logger.info(String.format("Transforming '%s' to DMN 1.2 ...", dmn11Definitions.getName()));

        TDefinitions definitions = dmnFactory.createTDefinitions();

        addNamedElementProperties(dmn11Definitions, definitions);

        definitions.getImport().addAll(transformList(dmn11Definitions.getImport()));
        definitions.getItemDefinition().addAll(transformList(dmn11Definitions.getItemDefinition()));
        definitions.getDrgElement().addAll(transformList(dmn11Definitions.getDrgElement()));
        definitions.getArtifact().addAll(transformList(dmn11Definitions.getArtifact()));
        definitions.getElementCollection().addAll(transformList(dmn11Definitions.getElementCollection()));
        definitions.getBusinessContextElement().addAll(transformList(dmn11Definitions.getBusinessContextElement()));
        definitions.setExpressionLanguage(dmn11Definitions.getExpressionLanguage());
        definitions.setTypeLanguage(dmn11Definitions.getTypeLanguage());
        definitions.setNamespace(dmn11Definitions.getNamespace());
        definitions.setExporter(dmn11Definitions.getExporter());
        definitions.setExporterVersion(dmn11Definitions.getExporterVersion());

        logger.info("Done");

        return definitions;
    }

    private void addElementProperties(org.omg.spec.dmn._20151101.dmn.TDMNElement source, TDMNElement target) {
        target.setDescription(source.getDescription());
        target.setExtensionElements(transform(source.getExtensionElements()));
        target.setId(source.getId());
        target.setLabel(source.getLabel());
        target.getOtherAttributes().putAll(transform(source.getOtherAttributes()));
    }

    private void addNamedElementProperties(org.omg.spec.dmn._20151101.dmn.TNamedElement source, TNamedElement target) {
        addElementProperties(source, target);
        target.setName(source.getName());
    }

    private void addExpressionProperties(org.omg.spec.dmn._20151101.dmn.TExpression source, TExpression target) {
        addElementProperties(source, target);
        target.setTypeRef(transform(source.getTypeRef()));
    }

    private void addImportProperties(org.omg.spec.dmn._20151101.dmn.TImport element, TImport result) {
        result.setNamespace(element.getNamespace());
        result.setLocationURI(element.getLocationURI());
        result.setImportType(element.getImportType());
    }

    private Collection transformList(List elements) {
        List result = new ArrayList();
        for(Object element: elements) {
            if (element instanceof JAXBElement) {
                result.add(transform((JAXBElement) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TImport) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TImport) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TItemDefinition) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TItemDefinition) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TInformationItem) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TInformationItem) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TDRGElement) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TDRGElement) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TExpression) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TExpression) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TArtifact) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TArtifact) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TElementCollection) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TElementCollection) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TBusinessContextElement) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TBusinessContextElement) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TKnowledgeRequirement) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TKnowledgeRequirement) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TAuthorityRequirement) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TAuthorityRequirement) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TInformationRequirement) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TInformationRequirement) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TDMNElementReference) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TDMNElementReference) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TInputClause) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TInputClause) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TOutputClause) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TOutputClause) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TDecisionRule) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TDecisionRule) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.THitPolicy) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.THitPolicy) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TBuiltinAggregator) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TBuiltinAggregator) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TDecisionTableOrientation) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TDecisionTableOrientation) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TUnaryTests) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TUnaryTests) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TList) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TList) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TContextEntry) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TContextEntry) element));
            } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TBinding) {
                result.add(transform((org.omg.spec.dmn._20151101.dmn.TBinding) element));
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

    private TDMNElement.ExtensionElements transform(org.omg.spec.dmn._20151101.dmn.TDMNElement.ExtensionElements element) {
        if (element == null) {
            return null;
        }

        TDMNElement.ExtensionElements result = dmnFactory.createTDMNElementExtensionElements();
        result.getAny().addAll(element.getAny());
        return result;
    }

    private TImport transform(org.omg.spec.dmn._20151101.dmn.TImport element) {
        if (element == null) {
            return null;
        }

        TImport result = dmnFactory.createTImport();
        addImportProperties(element, result);
        return result;
    }

    private TItemDefinition transform(org.omg.spec.dmn._20151101.dmn.TItemDefinition element) {
        if (element == null) {
            return null;
        }

        TItemDefinition result = dmnFactory.createTItemDefinition();
        addNamedElementProperties(element, result);
        result.setTypeRef(transform(element.getTypeRef()));
        result.setAllowedValues(transform(element.getAllowedValues()));
        result.getItemComponent().addAll(transformList(element.getItemComponent()));
        result.setTypeLanguage(element.getTypeLanguage());
        result.setIsCollection(element.isIsCollection());
        return result;
    }

    private TUnaryTests transform(org.omg.spec.dmn._20151101.dmn.TUnaryTests element) {
        if (element == null) {
            return null;
        }

        TUnaryTests result = dmnFactory.createTUnaryTests();
        addElementProperties(element, result);
        result.setText(element.getText());
        result.setExpressionLanguage(element.getExpressionLanguage());
        return result;
    }

    private TDRGElement transform(org.omg.spec.dmn._20151101.dmn.TDRGElement element) {
        if (element == null) {
            return null;
        }

        if (element instanceof org.omg.spec.dmn._20151101.dmn.TBusinessKnowledgeModel) {
            return transform((org.omg.spec.dmn._20151101.dmn.TBusinessKnowledgeModel) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TDecision) {
            return transform((org.omg.spec.dmn._20151101.dmn.TDecision) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TInputData) {
            return transform((org.omg.spec.dmn._20151101.dmn.TInputData) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TKnowledgeSource) {
            return transform((org.omg.spec.dmn._20151101.dmn.TKnowledgeSource) element);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", element.getClass()));
        }
    }

    private TBusinessKnowledgeModel transform(org.omg.spec.dmn._20151101.dmn.TBusinessKnowledgeModel element) {
        if (element == null) {
            return null;
        }

        TBusinessKnowledgeModel result = dmnFactory.createTBusinessKnowledgeModel();
        addNamedElementProperties(element, result);
        result.setEncapsulatedLogic(transform(element.getEncapsulatedLogic()));
        result.setVariable(transform(element.getVariable()));
        result.getKnowledgeRequirement().addAll(transformList(element.getKnowledgeRequirement()));
        result.getAuthorityRequirement().addAll(transformList(element.getAuthorityRequirement()));
        return result;
    }

    private TDecision transform(org.omg.spec.dmn._20151101.dmn.TDecision element) {
        if (element == null) {
            return null;
        }

        TDecision result = dmnFactory.createTDecision();
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
        result.setExpression(transform(element.getExpression()));
        return result;
    }

    private TInputData transform(org.omg.spec.dmn._20151101.dmn.TInputData element) {
        if (element == null) {
            return null;
        }

        TInputData result = dmnFactory.createTInputData();
        addNamedElementProperties(element, result);
        result.setVariable(transform(element.getVariable()));
        return result;
    }

    private TKnowledgeSource transform(org.omg.spec.dmn._20151101.dmn.TKnowledgeSource element) {
        if (element == null) {
            return null;
        }

        TKnowledgeSource result = dmnFactory.createTKnowledgeSource();
        addNamedElementProperties(element, result);
        result.getAuthorityRequirement().addAll(transformList(element.getAuthorityRequirement()));
        result.setType(element.getType());
        result.setOwner(transform(element.getOwner()));
        result.setLocationURI(element.getLocationURI());
        return result;
    }

    private TArtifact transform(org.omg.spec.dmn._20151101.dmn.TArtifact element) {
        if (element == null) {
            return null;
        }

        TArtifact result = dmnFactory.createTArtifact();
        addElementProperties(element, result);   
        return result;
    }

    private TElementCollection transform(org.omg.spec.dmn._20151101.dmn.TElementCollection element) {
        if (element == null) {
            return null;
        }

        TElementCollection result = dmnFactory.createTElementCollection();
        addNamedElementProperties(element, result);
        result.getDrgElement().addAll(transformList(element.getDrgElement()));
        return result;
    }

    private TBusinessContextElement transform(org.omg.spec.dmn._20151101.dmn.TBusinessContextElement element) {
        if (element == null) {
            return null;
        }

        TBusinessContextElement result = dmnFactory.createTBusinessContextElement();
        addNamedElementProperties(element, result);
        result.setURI(element.getURI());
        return result;
    }

    private TInformationItem transform(org.omg.spec.dmn._20151101.dmn.TInformationItem element) {
        if (element == null) {
            return null;
        }

        TInformationItem result = dmnFactory.createTInformationItem();
        addNamedElementProperties(element, result);
        result.setTypeRef(transform(element.getTypeRef()));
        return result;
    }

    private TKnowledgeRequirement transform(org.omg.spec.dmn._20151101.dmn.TKnowledgeRequirement element) {
        if (element == null) {
            return null;
        }

        TKnowledgeRequirement result = dmnFactory.createTKnowledgeRequirement();
        result.setRequiredKnowledge(transform(element.getRequiredKnowledge()));
        return result;
    }

    private TAuthorityRequirement transform(org.omg.spec.dmn._20151101.dmn.TAuthorityRequirement element) {
        if (element == null) {
            return null;
        }

        TAuthorityRequirement result = dmnFactory.createTAuthorityRequirement();
        result.setRequiredDecision(transform(element.getRequiredDecision()));
        result.setRequiredInput(transform(element.getRequiredInput()));
        result.setRequiredAuthority(transform(element.getRequiredAuthority()));
        return result;
    }

    private TInformationRequirement transform(org.omg.spec.dmn._20151101.dmn.TInformationRequirement element) {
        if (element == null) {
            return null;
        }

        TInformationRequirement result = dmnFactory.createTInformationRequirement();
        result.setRequiredDecision(transform(element.getRequiredDecision()));
        result.setRequiredInput(transform(element.getRequiredInput()));
        return result;
    }

    private TDMNElementReference transform(org.omg.spec.dmn._20151101.dmn.TDMNElementReference element) {
        if (element == null) {
            return null;
        }

        TDMNElementReference result = dmnFactory.createTDMNElementReference();
        result.setHref(element.getHref());
        return result;
    }

    private JAXBElement transform(JAXBElement element) {
        if (element == null) {
            return null;
        }

        Object value = element.getValue();
        if (value instanceof org.omg.spec.dmn._20151101.dmn.TDecision) {
            return dmnFactory.createDecision(transform((org.omg.spec.dmn._20151101.dmn.TDecision) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.dmn.TBusinessKnowledgeModel) {
            return dmnFactory.createBusinessKnowledgeModel(transform((org.omg.spec.dmn._20151101.dmn.TBusinessKnowledgeModel) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.dmn.TKnowledgeSource) {
            return dmnFactory.createKnowledgeSource(transform((org.omg.spec.dmn._20151101.dmn.TKnowledgeSource) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.dmn.TInputData) {
            return dmnFactory.createInputData(transform((org.omg.spec.dmn._20151101.dmn.TInputData) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.dmn.TFunctionDefinition) {
            return dmnFactory.createFunctionDefinition(transform((org.omg.spec.dmn._20151101.dmn.TFunctionDefinition) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.dmn.TDecisionTable) {
            return dmnFactory.createDecisionTable(transform((org.omg.spec.dmn._20151101.dmn.TDecisionTable) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.dmn.TRelation) {
            return dmnFactory.createRelation(transform((org.omg.spec.dmn._20151101.dmn.TRelation) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.dmn.TList) {
            return dmnFactory.createList(transform((org.omg.spec.dmn._20151101.dmn.TList) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.dmn.TContext) {
            return dmnFactory.createContext(transform((org.omg.spec.dmn._20151101.dmn.TContext) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.dmn.TInvocation) {
            return dmnFactory.createInvocation(transform((org.omg.spec.dmn._20151101.dmn.TInvocation) value));
        } else if (value instanceof org.omg.spec.dmn._20151101.dmn.TLiteralExpression) {
            return dmnFactory.createLiteralExpression(transform((org.omg.spec.dmn._20151101.dmn.TLiteralExpression) value));
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", value.getClass()));
        }
    }

    private TExpression transform(org.omg.spec.dmn._20151101.dmn.TExpression element) {
        if (element == null) {
            return null;
        }

        if (element instanceof org.omg.spec.dmn._20151101.dmn.TFunctionDefinition) {
            return transform((org.omg.spec.dmn._20151101.dmn.TFunctionDefinition) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TDecisionTable) {
            return transform((org.omg.spec.dmn._20151101.dmn.TDecisionTable) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TRelation) {
            return transform((org.omg.spec.dmn._20151101.dmn.TRelation) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TList) {
            return transform((org.omg.spec.dmn._20151101.dmn.TList) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TContext) {
            return transform((org.omg.spec.dmn._20151101.dmn.TContext) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TInvocation) {
            return transform((org.omg.spec.dmn._20151101.dmn.TInvocation) element);
        } else if (element instanceof org.omg.spec.dmn._20151101.dmn.TLiteralExpression) {
            return transform((org.omg.spec.dmn._20151101.dmn.TLiteralExpression) element);
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", element.getClass()));
        }
    }

    private TFunctionDefinition transform(org.omg.spec.dmn._20151101.dmn.TFunctionDefinition element) {
        if (element == null) {
            return null;
        }

        TFunctionDefinition result = dmnFactory.createTFunctionDefinition();
        addExpressionProperties(element, result);
        result.getFormalParameter().addAll(transformList(element.getFormalParameter()));
        result.setExpression(transform(element.getExpression()));
        return result;
    }

    private TDecisionTable transform(org.omg.spec.dmn._20151101.dmn.TDecisionTable element) {
        if (element == null) {
            return null;
        }

        TDecisionTable result = dmnFactory.createTDecisionTable();
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

    private TRelation transform(org.omg.spec.dmn._20151101.dmn.TRelation element) {
        if (element == null) {
            return null;
        }

        TRelation result = dmnFactory.createTRelation();
        addExpressionProperties(element, result);
        result.getColumn().addAll(transformList(element.getColumn()));
        result.getRow().addAll(transformList(element.getRow()));
        return result;
    }

    private TList transform(org.omg.spec.dmn._20151101.dmn.TList element) {
        if (element == null) {
            return null;
        }

        TList result = dmnFactory.createTList();
        addExpressionProperties(element, result);
        result.getExpression().addAll(transformList(element.getExpression()));
        return result;
    }

    private TContext transform(org.omg.spec.dmn._20151101.dmn.TContext element) {
        if (element == null) {
            return null;
        }

        TContext result = dmnFactory.createTContext();
        addExpressionProperties(element, result);
        result.getContextEntry().addAll(transformList(element.getContextEntry()));
        return result;
    }

    private TContextEntry transform(org.omg.spec.dmn._20151101.dmn.TContextEntry element) {
        if (element == null) {
            return null;
        }

        TContextEntry result = dmnFactory.createTContextEntry();
        result.setVariable(transform(element.getVariable()));
        result.setExpression(transform(element.getExpression()));
        return result;
    }

    private TInvocation transform(org.omg.spec.dmn._20151101.dmn.TInvocation element) {
        if (element == null) {
            return null;
        }

        TInvocation result = dmnFactory.createTInvocation();
        addExpressionProperties(element, result);
        result.setExpression(transform(element.getExpression()));
        result.getBinding().addAll(transformList(element.getBinding()));
        return result;
    }

    private TBinding transform(org.omg.spec.dmn._20151101.dmn.TBinding element) {
        if (element == null) {
            return null;
        }

        TBinding result = dmnFactory.createTBinding();
        result.setParameter(transform(element.getParameter()));
        result.setExpression(transform(element.getExpression()));
        return result;
    }

    private TLiteralExpression transform(org.omg.spec.dmn._20151101.dmn.TLiteralExpression element) {
        if (element == null) {
            return null;
        }

        TLiteralExpression result = dmnFactory.createTLiteralExpression();
        addExpressionProperties(element, result);
        result.setText(element.getText());
        result.setImportedValues(transformImportedValues(element.getImportedValues()));
        return result;
    }

    private TImportedValues transformImportedValues(org.omg.spec.dmn._20151101.dmn.TImportedValues element) {
        if (element == null) {
            return null;
        }

        TImportedValues result = dmnFactory.createTImportedValues();
        addImportProperties(element, result);
        result.setImportedElement(element.getImportedElement());
        result.setExpressionLanguage(element.getExpressionLanguage());
        return null;
    }

    private TDecisionTableOrientation transform(org.omg.spec.dmn._20151101.dmn.TDecisionTableOrientation element) {
        if (element == null) {
            return null;
        }

        return TDecisionTableOrientation.fromValue(element.value());
    }

    private TBuiltinAggregator transform(org.omg.spec.dmn._20151101.dmn.TBuiltinAggregator element) {
        if (element == null) {
            return null;
        }

        return TBuiltinAggregator.fromValue(element.value());
    }

    private THitPolicy transform(org.omg.spec.dmn._20151101.dmn.THitPolicy element) {
        if (element == null) {
            return null;
        }

        return THitPolicy.fromValue(element.value());
    }

    private TInputClause transform(org.omg.spec.dmn._20151101.dmn.TInputClause element) {
        if (element == null) {
            return null;
        }

        TInputClause result = dmnFactory.createTInputClause();
        addElementProperties(element, result);
        result.setInputExpression(transform(element.getInputExpression()));
        result.setInputValues(transform(element.getInputValues()));
        return result;
    }

    private TOutputClause transform(org.omg.spec.dmn._20151101.dmn.TOutputClause element) {
        if (element == null) {
            return null;
        }

        TOutputClause result = dmnFactory.createTOutputClause();
        addElementProperties(element, result);
        result.setOutputValues(transform(element.getOutputValues()));
        result.setDefaultOutputEntry(transform(element.getDefaultOutputEntry()));
        result.setName(element.getName());
        result.setTypeRef(transform(element.getTypeRef()));
        return result;
    }

    private TDecisionRule transform(org.omg.spec.dmn._20151101.dmn.TDecisionRule element) {
        if (element == null) {
            return null;
        }

        TDecisionRule result = dmnFactory.createTDecisionRule();
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
        if (!StringUtils.isBlank(prefix)) {
            return String.format("%s.%s", prefix, localPart);
        } else if (FEEL_11_NS.equals(namespaceURI)) {
            return String.format("%s.%s", FEEL_11_PREFIX, localPart);
        } else if (FEEL_12_NS.equals(namespaceURI)) {
             return String.format("%s.%s", FEEL_12_PREFIX, localPart);
        } else {
            return localPart;
        }
    }

}
