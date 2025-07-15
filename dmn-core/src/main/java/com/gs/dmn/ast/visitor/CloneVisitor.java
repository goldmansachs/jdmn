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
package com.gs.dmn.ast.visitor;

import com.gs.dmn.ast.*;
import com.gs.dmn.ast.dmndi.*;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.xstream.dom.ElementInfo;

import javax.xml.namespace.QName;

public class CloneVisitor<C> extends AbstractVisitor<C, Object> {
    private final ObjectFactory objectFactory = new ObjectFactory();

    public CloneVisitor(BuildLogger logger, ErrorHandler errorHandler) {
        super(logger, errorHandler);
    }

    //
    // Definitions
    //
    @Override
    public TDefinitions visit(TDefinitions element, C context) {
        if (element == null) {
            return null;
        }

        TDefinitions newElement = objectFactory.createTDefinitions();
        visitTNamedElementProperties(newElement, element, context);
        for (TImport import_ : element.getImport()) {
            newElement.getImport().add((TImport) import_.accept(this, context));
        }
        for (TItemDefinition itemDefinition : element.getItemDefinition()) {
            newElement.getItemDefinition().add((TItemDefinition) itemDefinition.accept(this, context));
        }
        for (TDRGElement drgElement : element.getDrgElement()) {
            newElement.getDrgElement().add(visitTDRGElement(drgElement, context));
        }
        for (TArtifact artifact : element.getArtifact()) {
            newElement.getArtifact().add(visitTArtifact(artifact, context));
        }
        for (TElementCollection elementCollection : element.getElementCollection()) {
            newElement.getElementCollection().add((TElementCollection) elementCollection.accept(this, context));
        }
        for (TBusinessContextElement businessContextElement : element.getBusinessContextElement()) {
            newElement.getBusinessContextElement().add((TBusinessContextElement) businessContextElement.accept(this, context));
        }
        DMNDI dmndi = element.getDMNDI();
        if (dmndi != null) {
            newElement.setDMNDI((DMNDI) dmndi.accept(this, context));
        }
        newElement.setExpressionLanguage(element.getExpressionLanguage());
        newElement.setTypeLanguage(element.getTypeLanguage());
        newElement.setNamespace(element.getNamespace());
        newElement.setExporter(element.getExporter());
        newElement.setExporterVersion(element.getExporterVersion());
        return newElement;
    }

    //
    // Import
    //
    @Override
    public TImport visit(TImport element, C context) {
        if (element == null) {
            return null;
        }

        TImport newElement = objectFactory.createTImport();
        visitTImportProperties(newElement, element, context);
        return newElement;
    }

    @Override
    public TImportedValues visit(TImportedValues element, C context) {
        if (element == null) {
            return null;
        }

        TImportedValues newElement = objectFactory.createTImportedValues();
        visitTImportProperties(newElement, element, context);
        newElement.setImportedElement(element.getImportedElement());
        newElement.setExpressionLanguage(element.getExpressionLanguage());
        return newElement;
    }

    //
    // Data types
    //
    @Override
    public TItemDefinition visit(TItemDefinition element, C context) {
        if (element == null) {
            return null;
        }

        TItemDefinition newElement = objectFactory.createTItemDefinition();
        visitTNamedElementProperties(newElement, element, context);
        newElement.setTypeRef(visitTypeRef(element.getTypeRef(), context));
        TUnaryTests allowedValues = element.getAllowedValues();
        if (allowedValues != null) {
            newElement.setAllowedValues((TUnaryTests) allowedValues.accept(this, context));
        }
        for (TItemDefinition itemDefinition : element.getItemComponent()) {
            newElement.getItemComponent().add((TItemDefinition) itemDefinition.accept(this, context));
        }
        TFunctionItem functionItem = element.getFunctionItem();
        if (functionItem != null) {
            newElement.setFunctionItem((TFunctionItem) functionItem.accept(this, context));
        }
        newElement.setTypeLanguage(element.getTypeLanguage());
        newElement.setIsCollection(element.isIsCollection());
        return newElement;
    }

    @Override
    public TFunctionItem visit(TFunctionItem element, C context) {
        if (element == null) {
            return null;
        }

        TFunctionItem newElement = objectFactory.createTFunctionItem();
        visitTDMNElementProperties(newElement, element, context);
        for (TInformationItem parameter : element.getParameters()) {
            newElement.getParameters().add((TInformationItem) parameter.accept(this, context));
        }
        newElement.setOutputTypeRef(visitTypeRef(element.getOutputTypeRef(), context));
        return newElement;
    }

    //
    // DRG Elements
    //
    protected TDRGElement visitTDRGElement(TDRGElement element, C context) {
        if (element == null) {
            return null;
        }

        if (element instanceof TInputData) {
            return (TDRGElement) ((TInputData) element).accept(this, context);
        } else if (element instanceof TDecision) {
            return (TDRGElement) ((TDecision) element).accept(this, context);
        } else if (element instanceof TInvocable) {
            return visitTInvocable(element, context);
        } else if (element instanceof TKnowledgeSource) {
            return (TDRGElement) ((TKnowledgeSource) element).accept(this, context);
        } else {
            throw new DMNRuntimeException("Not supported");
        }
    }

    @Override
    public TInputData visit(TInputData element, C context) {
        if (element == null) {
            return null;
        }

        TInputData newElement = objectFactory.createTInputData();
        visitTNamedElementProperties(newElement, element, context);
        TInformationItem variable = element.getVariable();
        if (variable != null) {
            newElement.setVariable((TInformationItem) variable.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TDecision visit(TDecision element, C context) {
        if (element == null) {
            return null;
        }

        TDecision newElement = objectFactory.createTDecision();
        visitTNamedElementProperties(newElement, element, context);
        newElement.setQuestion(element.getQuestion());
        newElement.setAllowedAnswers(element.getAllowedAnswers());
        TInformationItem variable = element.getVariable();
        if (variable != null) {
            newElement.setVariable((TInformationItem) variable.accept(this, context));
        }
        for (TInformationRequirement informationRequirement : element.getInformationRequirement()) {
            newElement.getInformationRequirement().add((TInformationRequirement) informationRequirement.accept(this, context));
        }
        for (TKnowledgeRequirement knowledgeRequirement : element.getKnowledgeRequirement()) {
            newElement.getKnowledgeRequirement().add((TKnowledgeRequirement) knowledgeRequirement.accept(this, context));
        }
        for (TAuthorityRequirement authorityRequirement : element.getAuthorityRequirement()) {
            newElement.getAuthorityRequirement().add((TAuthorityRequirement) authorityRequirement.accept(this, context));
        }
        for (TDMNElementReference elementReference : element.getSupportedObjective()) {
            newElement.getSupportedObjective().add((TDMNElementReference) elementReference.accept(this, context));
        }
        for (TDMNElementReference elementReference : element.getImpactedPerformanceIndicator()) {
            newElement.getImpactedPerformanceIndicator().add((TDMNElementReference) elementReference.accept(this, context));
        }
        for (TDMNElementReference elementReference : element.getDecisionMaker()) {
            newElement.getDecisionMaker().add((TDMNElementReference) elementReference.accept(this, context));
        }
        for (TDMNElementReference elementReference : element.getDecisionOwner()) {
            newElement.getDecisionOwner().add((TDMNElementReference) elementReference.accept(this, context));
        }
        for (TDMNElementReference elementReference : element.getUsingProcess()) {
            newElement.getUsingProcess().add((TDMNElementReference) elementReference.accept(this, context));
        }
        for (TDMNElementReference elementReference : element.getUsingTask()) {
            newElement.getUsingTask().add((TDMNElementReference) elementReference.accept(this, context));
        }
        newElement.setExpression(visitTExpression(element.getExpression(), context));
        return newElement;
    }

    protected TDRGElement visitTInvocable(TDRGElement element, C context) {
        if (element == null) {
            return null;
        }

        if (element instanceof TBusinessKnowledgeModel) {
            return (TDRGElement) ((TBusinessKnowledgeModel) element).accept(this, context);
        } else if (element instanceof TDecisionService) {
            return (TDRGElement) ((TDecisionService) element).accept(this, context);
        } else {
            throw new DMNRuntimeException("Not supported");
        }
    }

    @Override
    public TBusinessKnowledgeModel visit(TBusinessKnowledgeModel element, C context) {
        if (element == null) {
            return null;
        }

        TBusinessKnowledgeModel newElement = objectFactory.createTBusinessKnowledgeModel();
        visitTInvocableProperties(newElement, element, context);
        TFunctionDefinition encapsulatedLogic = element.getEncapsulatedLogic();
        if (encapsulatedLogic != null) {
            newElement.setEncapsulatedLogic((TFunctionDefinition) encapsulatedLogic.accept(this, context));
        }
        for (TKnowledgeRequirement knowledgeRequirement : element.getKnowledgeRequirement()) {
            newElement.getKnowledgeRequirement().add((TKnowledgeRequirement) knowledgeRequirement.accept(this, context));
        }
        for (TAuthorityRequirement authorityRequirement : element.getAuthorityRequirement()) {
            newElement.getAuthorityRequirement().add((TAuthorityRequirement) authorityRequirement.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TDecisionService visit(TDecisionService element, C context) {
        if (element == null) {
            return null;
        }

        TDecisionService newElement = objectFactory.createTDecisionService();
        visitTInvocableProperties(newElement, element, context);
        for (TDMNElementReference elementReference : element.getOutputDecision()) {
            newElement.getOutputDecision().add((TDMNElementReference) elementReference.accept(this, context));
        }
        for (TDMNElementReference elementReference : element.getEncapsulatedDecision()) {
            newElement.getEncapsulatedDecision().add(((TDMNElementReference) elementReference.accept(this, context)));
        }
        for (TDMNElementReference elementReference : element.getInputDecision()) {
            newElement.getInputDecision().add((TDMNElementReference) elementReference.accept(this, context));
        }
        for (TDMNElementReference elementReference : element.getInputData()) {
            newElement.getInputData().add((TDMNElementReference) elementReference.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TKnowledgeSource visit(TKnowledgeSource element, C context) {
        if (element == null) {
            return null;
        }

        TKnowledgeSource newElement = objectFactory.createTKnowledgeSource();
        visitTNamedElementProperties(newElement, element, context);
        for (TAuthorityRequirement authorityRequirement : element.getAuthorityRequirement()) {
            newElement.getAuthorityRequirement().add((TAuthorityRequirement) authorityRequirement.accept(this, context));
        }
        newElement.setType(element.getType());
        TDMNElementReference owner = element.getOwner();
        if (owner != null) {
            newElement.setOwner((TDMNElementReference) owner.accept(this, context));
        }
        newElement.setLocationURI(element.getLocationURI());
        return newElement;
    }

    //
    // Expressions
    //
    protected TExpression visitTExpression(TExpression element, C context) {
        if (element == null) {
            return null;
        }

        TExpression newElement = null;
        if (element instanceof TContext) {
            newElement = (TExpression) element.accept(this, context);
        } else if (element instanceof TDecisionTable) {
            newElement = (TExpression) element.accept(this, context);
        } else if (element instanceof TFunctionDefinition) {
            newElement = (TExpression) element.accept(this, context);
        } else if (element instanceof TInvocation) {
            newElement = (TExpression) element.accept(this, context);
        } else if (element instanceof TList) {
            newElement = (TExpression) element.accept(this, context);
        } else if (element instanceof TLiteralExpression) {
            newElement = (TExpression) element.accept(this, context);
        } else if (element instanceof TRelation) {
            newElement = (TExpression) element.accept(this, context);
        } else if (element instanceof TUnaryTests) {
            newElement = (TExpression) element.accept(this, context);
        } else if (element instanceof TConditional) {
            newElement = (TExpression) element.accept(this, context);
        } else if (element instanceof TFor) {
            newElement = (TExpression) element.accept(this, context);
        } else if (element instanceof TFilter) {
            newElement = (TExpression) element.accept(this, context);
        } else if (element instanceof TEvery) {
            newElement = (TExpression) element.accept(this, context);
        } else if (element instanceof TSome) {
            newElement = (TExpression) element.accept(this, context);
        }
        return newElement;
    }

    @Override
    public TContext visit(TContext element, C context) {
        if (element == null) {
            return null;
        }

        TContext newElement = objectFactory.createTContext();
        visitTExpressionProperties(newElement, element, context);
        for (TContextEntry contextEntry : element.getContextEntry()) {
            newElement.getContextEntry().add((TContextEntry) contextEntry.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TContextEntry visit(TContextEntry element, C context) {
        if (element == null) {
            return null;
        }

        TContextEntry newElement = objectFactory.createTContextEntry();
        visitTDMNElementProperties(newElement, element, context);
        TInformationItem variable = element.getVariable();
        if (variable != null) {
            newElement.setVariable((TInformationItem) variable.accept(this, context));
        }
        newElement.setExpression(visitTExpression(element.getExpression(), context));
        return newElement;
    }

    @Override
    public TDecisionTable visit(TDecisionTable element, C context) {
        if (element == null) {
            return null;
        }

        TDecisionTable newElement = objectFactory.createTDecisionTable();
        visitTExpressionProperties(newElement, element, context);
        for (TInputClause inputClause : element.getInput()) {
            newElement.getInput().add((TInputClause) inputClause.accept(this, context));
        }
        for (TOutputClause outputClause : element.getOutput()) {
            newElement.getOutput().add((TOutputClause) outputClause.accept(this, context));
        }
        for (TRuleAnnotationClause clause : element.getAnnotation()) {
            newElement.getAnnotation().add((TRuleAnnotationClause) clause.accept(this, context));
        }
        for (TDecisionRule rule : element.getRule()) {
            newElement.getRule().add((TDecisionRule) rule.accept(this, context));
        }
        newElement.setHitPolicy(element.getHitPolicy());
        newElement.setAggregation(element.getAggregation());
        newElement.setPreferredOrientation(element.getPreferredOrientation());
        newElement.setOutputLabel(element.getOutputLabel());
        return newElement;
    }

    @Override
    public TInputClause visit(TInputClause element, C context) {
        if (element == null) {
            return null;
        }

        TInputClause newElement = objectFactory.createTInputClause();
        visitTDMNElementProperties(newElement, element, context);
        TLiteralExpression inputExpression = element.getInputExpression();
        if (inputExpression != null) {
            newElement.setInputExpression((TLiteralExpression) inputExpression.accept(this, context));
        }
        TUnaryTests inputValues = element.getInputValues();
        if (inputValues != null) {
            newElement.setInputValues((TUnaryTests) inputValues.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TOutputClause visit(TOutputClause element, C context) {
        if (element == null) {
            return null;
        }

        TOutputClause newElement = objectFactory.createTOutputClause();
        visitTDMNElementProperties(newElement, element, context);
        TUnaryTests outputValues = element.getOutputValues();
        if (outputValues != null) {
            newElement.setOutputValues((TUnaryTests) outputValues.accept(this, context));
        }
        TLiteralExpression defaultOutputEntry = element.getDefaultOutputEntry();
        if (defaultOutputEntry != null) {
            newElement.setDefaultOutputEntry((TLiteralExpression) defaultOutputEntry.accept(this, context));
        }
        newElement.setName(element.getName());
        newElement.setTypeRef(visitTypeRef(element.getTypeRef(), context));
        return newElement;
    }

    @Override
    public TRuleAnnotationClause visit(TRuleAnnotationClause element, C context) {
        if (element == null) {
            return null;
        }

        TRuleAnnotationClause newElement = objectFactory.createTRuleAnnotationClause();
        visitDMNBaseElementProperties(newElement, element);
        newElement.setName(element.getName());
        return newElement;
    }

    @Override
    public TDecisionRule visit(TDecisionRule element, C context) {
        if (element == null) {
            return null;
        }

        TDecisionRule newElement = objectFactory.createTDecisionRule();
        visitTDMNElementProperties(newElement, element, context);
        for (TUnaryTests unaryTests : element.getInputEntry()) {
            newElement.getInputEntry().add((TUnaryTests) unaryTests.accept(this, context));
        }
        for (TLiteralExpression expression : element.getOutputEntry()) {
            newElement.getOutputEntry().add((TLiteralExpression) expression.accept(this, context));
        }
        for (TRuleAnnotation ruleAnnotation : element.getAnnotationEntry()) {
            newElement.getAnnotationEntry().add((TRuleAnnotation) ruleAnnotation.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TRuleAnnotation visit(TRuleAnnotation element, C context) {
        if (element == null) {
            return null;
        }

        TRuleAnnotation newElement = objectFactory.createTRuleAnnotation();
        visitDMNBaseElementProperties(newElement, element);
        newElement.setText(element.getText());
        return newElement;
    }

    @Override
    public TFunctionDefinition visit(TFunctionDefinition element, C context) {
        if (element == null) {
            return null;
        }

        TFunctionDefinition newElement = objectFactory.createTFunctionDefinition();
        visitTExpressionProperties(newElement, element, context);
        for (TInformationItem informationItem : element.getFormalParameter()) {
            newElement.getFormalParameter().add((TInformationItem) informationItem.accept(this, context));
        }
        newElement.setExpression(visitTExpression(element.getExpression(), context));
        newElement.setKind(element.getKind());
        return newElement;
    }

    @Override
    public DMNBaseElement visit(TInvocation element, C context) {
        if (element == null) {
            return null;
        }

        TInvocation newElement = objectFactory.createTInvocation();
        visitTExpressionProperties(newElement, element, context);
        newElement.setExpression(visitTExpression(element.getExpression(), context));
        for (TBinding binding : element.getBinding()) {
            newElement.getBinding().add((TBinding) binding.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TBinding visit(TBinding element, C context) {
        if (element == null) {
            return null;
        }

        TBinding newElement = objectFactory.createTBinding();
        visitDMNBaseElementProperties(newElement, element);
        TInformationItem parameter = element.getParameter();
        if (parameter != null) {
            newElement.setParameter((TInformationItem) parameter.accept(this, context));
        }
        newElement.setExpression(visitTExpression(element.getExpression(), context));
        return newElement;
    }

    @Override
    public TList visit(TList element, C context) {
        if (element == null) {
            return null;
        }

        TList newElement = objectFactory.createTList();
        visitTExpressionProperties(newElement, element, context);
        for (TExpression expression : element.getExpression()) {
            newElement.getExpression().add(visitTExpression(expression, context));
        }
        return newElement;
    }

    @Override
    public TLiteralExpression visit(TLiteralExpression element, C context) {
        if (element == null) {
            return null;
        }

        TLiteralExpression newElement = objectFactory.createTLiteralExpression();
        visitTExpressionProperties(newElement, element, context);
        newElement.setText(element.getText());
        TImportedValues importedValues = element.getImportedValues();
        if (importedValues != null) {
            newElement.setImportedValues((TImportedValues) importedValues.accept(this, context));
        }
        newElement.setExpressionLanguage(element.getExpressionLanguage());
        return newElement;
    }

    @Override
    public TRelation visit(TRelation element, C context) {
        if (element == null) {
            return null;
        }

        TRelation newElement = objectFactory.createTRelation();
        visitTExpressionProperties(newElement, element, context);
        for (TInformationItem informationItem : element.getColumn()) {
            newElement.getColumn().add((TInformationItem) informationItem.accept(this, context));
        }
        for (TList list : element.getRow()) {
            newElement.getRow().add((TList) list.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TUnaryTests visit(TUnaryTests element, C context) {
        if (element == null) {
            return null;
        }

        TUnaryTests newElement = objectFactory.createTUnaryTests();
        visitTExpressionProperties(newElement, element, context);
        newElement.setText(element.getText());
        newElement.setExpressionLanguage(element.getExpressionLanguage());
        return newElement;
    }

    @Override
    public TConditional visit(TConditional element, C context) {
        if (element == null) {
            return null;
        }

        TConditional newElement = objectFactory.createTConditional();
        visitTExpressionProperties(newElement, element, context);
        TChildExpression ifExp = element.getIf();
        if (ifExp != null) {
            newElement.setIf((TChildExpression) ifExp.accept(this, context));
        }
        TChildExpression thenExp = element.getThen();
        if (thenExp != null) {
            newElement.setThen((TChildExpression) thenExp.accept(this, context));
        }
        TChildExpression elseExp = element.getElse();
        if (elseExp != null) {
            newElement.setElse((TChildExpression) elseExp.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TFor visit(TFor element, C context) {
        if (element == null) {
            return null;
        }

        TFor newElement = objectFactory.createTFor();
        visitTIteratorProperties(newElement, element, context);
        TChildExpression returnExp = element.getReturn();
        if (returnExp != null) {
            newElement.setReturn((TChildExpression) returnExp.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TFilter visit(TFilter element, C context) {
        if (element == null) {
            return null;
        }

        TFilter newElement = objectFactory.createTFilter();
        visitTExpressionProperties(newElement, element, context);
        TChildExpression inExp = element.getIn();
        if (inExp != null) {
            newElement.setIn((TChildExpression) inExp.accept(this, context));
        }
        TChildExpression matchExp = element.getMatch();
        if (matchExp != null) {
            newElement.setMatch((TChildExpression) matchExp.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TEvery visit(TEvery element, C context) {
        if (element == null) {
            return null;
        }

        TEvery newElement = objectFactory.createTEvery();
        visitTQuantifiedProperties(newElement, element, context);
        return newElement;
    }

    @Override
    public TSome visit(TSome element, C context) {
        if (element == null) {
            return null;
        }

        TSome newElement = objectFactory.createTSome();
        visitTQuantifiedProperties(newElement, element, context);
        return newElement;
    }

    @Override
    public TChildExpression visit(TChildExpression element, C context) {
        if (element == null) {
            return null;
        }

        TChildExpression newElement = objectFactory.createTChildExpression();
        visitDMNBaseElementProperties(newElement, element);
        visitTChildExpressionProperties(newElement, element, context);
        return newElement;
    }

    @Override
    public TTypedChildExpression visit(TTypedChildExpression element, C context) {
        if (element == null) {
            return null;
        }

        TTypedChildExpression newElement = objectFactory.createTTypedChildExpression();
        visitTChildExpressionProperties(newElement, element, context);
        newElement.setTypeRef(element.getTypeRef());
        return newElement;
    }

    //
    // Requirements
    //
    @Override
    public TAuthorityRequirement visit(TAuthorityRequirement element, C context) {
        if (element == null) {
            return null;
        }

        TAuthorityRequirement newElement = objectFactory.createTAuthorityRequirement();
        visitTDMNElementProperties(newElement, element, context);
        TDMNElementReference requiredDecision = element.getRequiredDecision();
        if (requiredDecision != null) {
            newElement.setRequiredDecision((TDMNElementReference) requiredDecision.accept(this, context));
        }
        TDMNElementReference requiredInput = element.getRequiredInput();
        if (requiredInput != null) {
            newElement.setRequiredInput((TDMNElementReference) requiredInput.accept(this, context));
        }
        TDMNElementReference requiredAuthority = element.getRequiredAuthority();
        if (requiredAuthority != null) {
            newElement.setRequiredAuthority((TDMNElementReference) requiredAuthority.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TInformationRequirement visit(TInformationRequirement element, C context) {
        if (element == null) {
            return null;
        }

        TInformationRequirement newElement = objectFactory.createTInformationRequirement();
        visitTDMNElementProperties(newElement, element, context);
        TDMNElementReference requiredDecision = element.getRequiredDecision();
        if (requiredDecision != null) {
            newElement.setRequiredDecision((TDMNElementReference) requiredDecision.accept(this, context));
        }
        TDMNElementReference requiredInput = element.getRequiredInput();
        if (requiredInput != null) {
            newElement.setRequiredInput((TDMNElementReference) requiredInput.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TKnowledgeRequirement visit(TKnowledgeRequirement element, C context) {
        if (element == null) {
            return null;
        }

        TKnowledgeRequirement newElement = objectFactory.createTKnowledgeRequirement();
        visitTDMNElementProperties(newElement, element, context);
        TDMNElementReference requiredKnowledge = element.getRequiredKnowledge();
        if (requiredKnowledge != null) {
            newElement.setRequiredKnowledge((TDMNElementReference) requiredKnowledge.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TInformationItem visit(TInformationItem element, C context) {
        if (element == null) {
            return null;
        }

        TInformationItem newElement = objectFactory.createTInformationItem();
        visitTNamedElementProperties(newElement, element, context);
        newElement.setTypeRef(visitTypeRef(element.getTypeRef(), context));
        return newElement;
    }

    @Override
    public TDMNElementReference visit(TDMNElementReference element, C context) {
        if (element == null) {
            return null;
        }

        TDMNElementReference newElement = objectFactory.createTDMNElementReference();
        visitDMNBaseElementProperties(newElement, element);
        newElement.setHref(element.getHref());
        return newElement;
    }

    //
    // Artifacts
    //
    protected TArtifact visitTArtifact(TArtifact element, C context) {
        if (element == null) {
            return null;
        }

        TArtifact newElement;
        if (element instanceof TAssociation) {
            newElement = (TArtifact) ((TAssociation) element).accept(this, context);
        } else if (element instanceof TGroup) {
            newElement = (TArtifact) ((TGroup) element).accept(this, context);
        } else if (element instanceof TTextAnnotation) {
            newElement = (TArtifact) ((TTextAnnotation) element).accept(this, context);
        } else {
            throw new DMNRuntimeException("Not supported");
        }
        visitTDMNElementProperties(newElement, element, context);
        return newElement;
    }

    @Override
    public TAssociation visit(TAssociation element, C context) {
        if (element == null) {
            return null;
        }

        TAssociation newElement = objectFactory.createTAssociation();
        visitTDMNElementProperties(newElement, element, context);
        TDMNElementReference sourceRef = element.getSourceRef();
        if (sourceRef != null) {
            newElement.setSourceRef((TDMNElementReference) sourceRef.accept(this, context));
        }
        TDMNElementReference targetRef = element.getTargetRef();
        if (targetRef != null) {
            newElement.setTargetRef((TDMNElementReference) targetRef.accept(this, context));
        }
        newElement.setAssociationDirection(element.getAssociationDirection());
        return newElement;
    }

    @Override
    public TGroup visit(TGroup element, C context) {
        if (element == null) {
            return null;
        }

        TGroup newElement = objectFactory.createTGroup();
        visitTDMNElementProperties(newElement, element, context);
        newElement.setName(element.getName());
        return newElement;
    }

    @Override
    public TTextAnnotation visit(TTextAnnotation element, C context) {
        if (element == null) {
            return null;
        }

        TTextAnnotation newElement = objectFactory.createTTextAnnotation();
        visitTDMNElementProperties(newElement, element, context);
        newElement.setText(element.getText());
        newElement.setTextFormat(element.getTextFormat());
        return newElement;
    }

    //
    // Other DMN elements
    //
    @Override
    public TBusinessContextElement visit(TBusinessContextElement element, C context) {
        if (element == null) {
            return null;
        }

        TBusinessContextElement newElement = objectFactory.createTBusinessContextElement();
        visitTNamedElementProperties(newElement, element, context);
        newElement.setURI(element.getURI());
        return newElement;
    }

    @Override
    public DMNBaseElement visit(TPerformanceIndicator element, C context) {
        if (element == null) {
            return null;
        }

        TPerformanceIndicator newElement = objectFactory.createTPerformanceIndicator();
        visitBusinessContextElementProperties(newElement, element, context);
        for (TDMNElementReference elementReference : element.getImpactingDecision()) {
            newElement.getImpactingDecision().add((TDMNElementReference) elementReference.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TOrganizationUnit visit(TOrganizationUnit element, C context) {
        if (element == null) {
            return null;
        }

        TOrganizationUnit newElement = objectFactory.createTOrganizationUnit();
        visitBusinessContextElementProperties(newElement, element, context);
        newElement.setURI(element.getURI());
        for (TDMNElementReference elementReference : element.getDecisionMade()) {
            newElement.getDecisionMade().add((TDMNElementReference) elementReference.accept(this, context));
        }
        for (TDMNElementReference elementReference : element.getDecisionOwned()) {
            newElement.getDecisionOwned().add((TDMNElementReference) elementReference.accept(this, context));
        }
        return newElement;
    }

    @Override
    public TElementCollection visit(TElementCollection element, C context) {
        if (element == null) {
            return null;
        }

        TElementCollection newElement = objectFactory.createTElementCollection();
        visitTNamedElementProperties(newElement, element, context);
        for (TDMNElementReference elementReference : element.getDrgElement()) {
            newElement.getDrgElement().add((TDMNElementReference) elementReference.accept(this, context));
        }
        return newElement;
    }

    //
    // Extensions
    //
    @Override
    public TDMNElement.ExtensionElements visit(TDMNElement.ExtensionElements element, C context) {
        if (element == null) {
            return null;
        }

        return visitExtensions(element, context);
    }

    //
    // DMN properties
    //
    private void visitDMNBaseElementProperties(DMNBaseElement newElement, DMNBaseElement element) {
        if (newElement == null || element == null) {
            return;
        }

        ElementInfo elementInfo = element.getElementInfo();
        ElementInfo newElementInfo = newElement.getElementInfo();
        newElementInfo.setLocation(elementInfo.getLocation());
        newElementInfo.setPrefix(elementInfo.getPrefix());
        newElementInfo.setNamespaceURI(elementInfo.getNamespaceURI());
        newElementInfo.getNsContext().putAll(elementInfo.getNsContext());
    }

    protected void visitTDMNElementProperties(TDMNElement newElement, TDMNElement element, C context) {
        if (newElement == null || element == null) {
            return;
        }

        visitDMNBaseElementProperties(newElement, element);
        newElement.getOtherAttributes().putAll(element.getOtherAttributes());
        newElement.setDescription(element.getDescription());
        newElement.setExtensionElements(visitExtensions(element.getExtensionElements(), context));
        newElement.setId(element.getId());
        newElement.setLabel(element.getLabel());
    }

    protected void visitTNamedElementProperties(TNamedElement newElement, TNamedElement element, C context) {
        if (newElement == null || element == null) {
            return;
        }

        visitTDMNElementProperties(newElement, element, context);
        newElement.setName(element.getName());
    }

    protected void visitTImportProperties(TImport newElement, TImport element, C context) {
        if (newElement == null || element == null) {
            return;
        }

        visitTNamedElementProperties(newElement, element, context);
        newElement.setNamespace(element.getNamespace());
        newElement.setLocationURI(element.getLocationURI());
        newElement.setImportType(element.getImportType());
    }

    protected void visitTInvocableProperties(TInvocable newElement, TInvocable element, C context) {
        if (newElement == null || element == null) {
            return;
        }

        visitTNamedElementProperties(newElement, element, context);
        TInformationItem variable = element.getVariable();
        if (variable != null) {
            newElement.setVariable((TInformationItem) variable.accept(this, context));
        }
    }

    protected void visitTExpressionProperties(TExpression newElement, TExpression element, C context) {
        if (newElement == null || element == null) {
            return;
        }

        visitTDMNElementProperties(newElement, element, context);
        newElement.setTypeRef(visitTypeRef(element.getTypeRef(), context));
    }

    protected void visitTIteratorProperties(TIterator newElement, TIterator element, C context) {
        if (newElement == null || element == null) {
            return;
        }

        visitTExpressionProperties(newElement, element, context);
        TTypedChildExpression inExp = element.getIn();
        if (inExp != null) {
            newElement.setIn((TTypedChildExpression) inExp.accept(this, context));
        }
        newElement.setIteratorVariable(element.getIteratorVariable());
    }

    protected void visitTQuantifiedProperties(TQuantified newElement, TQuantified element, C context) {
        if (newElement == null || element == null) {
            return;
        }

        visitTIteratorProperties(newElement, element, context);
        TChildExpression satisfies = element.getSatisfies();
        if (satisfies != null) {
            newElement.setSatisfies((TChildExpression) satisfies.accept(this, context));
        }
    }

    protected void visitTChildExpressionProperties(TChildExpression newElement, TChildExpression element, C context) {
        if (newElement == null || element == null) {
            return;
        }

        newElement.setId(element.getId());
        TExpression exp = element.getExpression();
        if (exp != null) {
            newElement.setExpression(visitTExpression(exp, context));
        }
    }

    private void visitBusinessContextElementProperties(TBusinessContextElement newElement, TBusinessContextElement element, C context) {
        if (newElement == null || element == null) {
            return;
        }

        visitTNamedElementProperties(newElement, element, context);
        newElement.setURI(element.getURI());
    }

    protected QName visitTypeRef(QName typeRef, C context) {
        if (typeRef == null) {
            return null;
        }

        return new QName(typeRef.getNamespaceURI(), typeRef.getLocalPart(), typeRef.getPrefix());
    }

    protected QName visitQName(QName qName, C context) {
        if (qName == null) {
            return null;
        }

        return new QName(qName.getNamespaceURI(), qName.getLocalPart(), qName.getPrefix());
    }

    protected TDMNElement.ExtensionElements visitExtensions(TDMNElement.ExtensionElements element, C context) {
        if (element == null) {
            return null;
        }

        TDMNElement.ExtensionElements newElement = objectFactory.createTDMNElementExtensionElements();
        visitDMNBaseElementProperties(newElement, element);
        newElement.getAny().addAll(element.getAny());
        return newElement;
    }

    //
    // DMN DI elements
    //
    @Override
    public DMNDI visit(DMNDI element, C context) {
        if (element == null) {
            return null;
        }

        DMNDI newElement = objectFactory.createDMNDI();
        visitDMNBaseElementProperties(newElement, element);
        for (DMNDiagram diagram : element.getDMNDiagram()) {
            newElement.getDMNDiagram().add((DMNDiagram) diagram.accept(this, context));
        }
        for (DMNStyle style : element.getDMNStyle()) {
            newElement.getDMNStyle().add((DMNStyle) style.accept(this, context));
        }
        return newElement;
    }

    @Override
    public DMNDiagram visit(DMNDiagram element, C context) {
        if (element == null) {
            return null;
        }

        DMNDiagram newElement = objectFactory.createDMNDiagram();
        visitDiagramProperties(newElement, element, context);
        Dimension size = element.getSize();
        if (size != null) {
            newElement.setSize((Dimension) size.accept(this, context));
        }
        for (DiagramElement diagramElement : element.getDMNDiagramElement()) {
            newElement.getDMNDiagramElement().add(visitDiagramElement(diagramElement, context));
        }
        return newElement;
    }

    protected DiagramElement visitDiagramElement(DiagramElement element, C context) {
        if (element == null) {
            return null;
        }

        DiagramElement newElement;
        if (element instanceof DMNDiagram) {
            newElement = (DiagramElement) ((DMNDiagram) element).accept(this, context);
        } else if (element instanceof DMNDecisionServiceDividerLine) {
            newElement = (DiagramElement) ((DMNDecisionServiceDividerLine) element).accept(this, context);
        } else if (element instanceof DMNEdge) {
            newElement = (DiagramElement) ((DMNEdge) element).accept(this, context);
        } else if (element instanceof DMNLabel) {
            newElement = (DiagramElement) ((DMNLabel) element).accept(this, context);
        } else if (element instanceof DMNShape) {
            newElement = (DiagramElement) ((DMNShape) element).accept(this, context);
        } else {
            throw new DMNRuntimeException("Not supported");
        }
        visitDiagramElementProperties(newElement, element, context);
        return newElement;
    }

    @Override
    public DMNShape visit(DMNShape element, C context) {
        if (element == null) {
            return null;
        }

        DMNShape newElement = objectFactory.createDMNShape();
        visitShapeProperties(newElement, element, context);
        DMNLabel dmnLabel = element.getDMNLabel();
        if (dmnLabel != null) {
            newElement.setDMNLabel((DMNLabel) dmnLabel.accept(this, context));
        }
        DMNDecisionServiceDividerLine dmnDecisionServiceDividerLine = element.getDMNDecisionServiceDividerLine();
        if (dmnDecisionServiceDividerLine != null) {
            newElement.setDMNDecisionServiceDividerLine((DMNDecisionServiceDividerLine) dmnDecisionServiceDividerLine.accept(this, context));
        }
        newElement.setDmnElementRef(visitQName(element.getDmnElementRef(), context));
        newElement.setIsListedInputData(element.isIsListedInputData());
        newElement.setIsCollapsed(element.isIsCollapsed());
        return newElement;
    }

    @Override
    public DMNBaseElement visit(DMNEdge element, C context) {
        if (element == null) {
            return null;
        }

        DMNEdge newElement = objectFactory.createDMNEdge();
        visitEdgeProperties(newElement, element, context);
        DMNLabel dmnLabel = element.getDMNLabel();
        if (dmnLabel != null) {
            newElement.setDMNLabel((DMNLabel) dmnLabel.accept(this, context));
        }
        newElement.setDmnElementRef(visitQName(element.getDmnElementRef(), context));
        newElement.setSourceElement(visitQName(element.getSourceElement(), context));
        newElement.setTargetElement(visitQName(element.getTargetElement(), context));
        return newElement;
    }

    protected Style visitStyle(Style element, C context) {
        if (element == null) {
            return null;
        }

        Style newElement;
        if (element instanceof DMNStyle) {
            newElement = (Style) ((DMNStyle) element).accept(this, context);
        } else if (element instanceof Style.IDREFStubStyle) {
            newElement = (Style) ((Style.IDREFStubStyle) element).accept(this, context);
        } else {
            throw new DMNRuntimeException("Not supported");
        }
        return newElement;
    }

    @Override
    public DMNStyle visit(DMNStyle element, C context) {
        if (element == null) {
            return null;
        }

        DMNStyle newElement = objectFactory.createDMNStyle();
        visitStyleProperties(newElement, element, context);
        Color fillColor = element.getFillColor();
        if (fillColor != null) {
            newElement.setFillColor((Color) fillColor.accept(this, context));
        }
        Color strokeColor = element.getStrokeColor();
        if (strokeColor != null) {
            newElement.setStrokeColor((Color) strokeColor.accept(this, context));
        }
        Color fontColor = element.getFontColor();
        if (fontColor != null) {
            newElement.setFontColor((Color) fontColor.accept(this, context));
        }
        newElement.setFontFamily(element.getFontFamily());
        newElement.setFontSize(element.getFontSize());
        newElement.setFontItalic(element.isFontItalic());
        newElement.setFontBold(element.isFontBold());
        newElement.setFontUnderline(element.isFontUnderline());
        newElement.setFontStrikeThrough(element.isFontStrikeThrough());
        newElement.setLabelHorizontalAlignment(element.getLabelHorizontalAlignment());
        newElement.setLabelVerticalAlignment(element.getLabelVerticalAlignment());
        return newElement;
    }

    @Override
    public Object visit(Style.IDREFStubStyle element, C context) {
        if (element == null) {
            return null;
        }

        Style.IDREFStubStyle newElement = objectFactory.createStyleIDREFStubStyle();
        visitStyleProperties(newElement, element, context);
        return newElement;
    }

    @Override
    public DMNLabel visit(DMNLabel element, C context) {
        if (element == null) {
            return null;
        }

        DMNLabel newElement = objectFactory.createDMNLabel();
        visitShapeProperties(newElement, element, context);
        newElement.setText(element.getText());
        return newElement;
    }

    @Override
    public DMNDecisionServiceDividerLine visit(DMNDecisionServiceDividerLine element, C context) {
        if (element == null) {
            return null;
        }

        DMNDecisionServiceDividerLine newElement = objectFactory.createDMNDecisionServiceDividerLine();
        visitEdgeProperties(newElement, element, context);
        return newElement;
    }

    @Override
    public Color visit(Color element, C context) {
        if (element == null) {
            return null;
        }

        Color newElement = objectFactory.createColor();
        visitDMNBaseElementProperties(newElement, element);
        newElement.setRed(element.getRed());
        newElement.setGreen(element.getGreen());
        newElement.setBlue(element.getBlue());
        return newElement;
    }

    @Override
    public DMNBaseElement visit(Point element, C context) {
        if (element == null) {
            return null;
        }

        Point newElement = objectFactory.createPoint();
        visitDMNBaseElementProperties(newElement, element);
        newElement.setX(element.getX());
        newElement.setY(element.getY());
        return newElement;
    }

    @Override
    public DMNBaseElement visit(Bounds element, C context) {
        if (element == null) {
            return null;
        }

        Bounds newElement = objectFactory.createBounds();
        visitDMNBaseElementProperties(newElement, element);
        newElement.setX(element.getX());
        newElement.setY(element.getY());
        newElement.setWidth(element.getWidth());
        newElement.setHeight(element.getHeight());
        return newElement;
    }

    @Override
    public Dimension visit(Dimension element, C context) {
        if (element == null) {
            return null;
        }

        Dimension newElement = objectFactory.createDimension();
        visitDMNBaseElementProperties(newElement, element);
        newElement.setWidth(element.getWidth());
        newElement.setHeight(element.getHeight());
        return newElement;
    }

    @Override
    public DiagramElement.Extension visit(DiagramElement.Extension element, C context) {
        if (element == null) {
            return null;
        }

        return visitExtension(element, context);
    }

    @Override
    public Object visit(Style.Extension element, C context) {
        if (element == null) {
            return null;
        }

        return visitExtension(element, context);
    }

    protected DiagramElement.Extension visitExtension(DiagramElement.Extension element, C context) {
        if (element == null) {
            return null;
        }

        DiagramElement.Extension newElement = objectFactory.createDiagramElementExtension();
        visitDMNBaseElementProperties(newElement, element);
        newElement.getAny().addAll(element.getAny());
        return newElement;
    }

    protected Style.Extension visitExtension(Style.Extension element, C context) {
        if (element == null) {
            return null;
        }

        Style.Extension newElement = objectFactory.createStyleExtension();
        visitDMNBaseElementProperties(newElement, element);
        newElement.getAny().addAll(element.getAny());
        return newElement;
    }

    //
    // DMN DI properties
    //
    protected void visitDiagramProperties(Diagram newElement, Diagram element, C context) {
        if (newElement == null || element == null) {
            return;
        }

        visitDiagramElementProperties(newElement, element, context);
        newElement.setName(element.getName());
        newElement.setDocumentation(element.getDocumentation());
        newElement.setResolution(element.getResolution());
    }

    protected void visitDiagramElementProperties(DiagramElement newElement, DiagramElement element, C context) {
        if (newElement == null || element == null) {
            return;
        }

        visitDMNBaseElementProperties(newElement, element);
        newElement.setExtension(visitExtension(element.getExtension(), context));
        newElement.setStyle(visitStyle(element.getStyle(), context));
        newElement.setSharedStyle(visitStyle(element.getSharedStyle(), context));
        newElement.setId(element.getId());
        newElement.getOtherAttributes().putAll(element.getOtherAttributes());
    }

    protected void visitShapeProperties(Shape newElement, Shape element, C context) {
        if (newElement == null || element == null) {
            return;
        }

        visitDiagramElementProperties(newElement, element, context);
        Bounds bounds = element.getBounds();
        if (bounds != null) {
            newElement.setBounds((Bounds) bounds.accept(this, context));
        }
    }

    protected void visitEdgeProperties(Edge newElement, Edge element, C context) {
        if (newElement == null || element == null) {
            return;
        }

        visitDiagramElementProperties(newElement, element, context);
        for (Point point : element.getWaypoint()) {
            newElement.getWaypoint().add((Point) point.accept(this, context));
        }
    }

    protected void visitStyleProperties(Style newElement, Style element, C context) {
        if (newElement == null || element == null) {
            return;
        }

        visitDMNBaseElementProperties(newElement, element);
        newElement.setExtension(visitExtension(element.getExtension(), context));
        newElement.setId(element.getId());
        newElement.getOtherAttributes().putAll(element.getOtherAttributes());
    }
}
