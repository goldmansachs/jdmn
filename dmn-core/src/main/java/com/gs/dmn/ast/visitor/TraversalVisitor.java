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

import javax.xml.namespace.QName;

public class TraversalVisitor<C> extends AbstractVisitor<C, Object> {
    public TraversalVisitor(BuildLogger logger, ErrorHandler errorHandler) {
        super(logger, errorHandler);
    }

    //
    // Definitions
    //
    @Override
    public DMNBaseElement visit(TDefinitions element, C context) {
        if (element == null) {
            return null;
        }

        visitTNamedElementProperties(element, context);
        for (TImport import_ : element.getImport()) {
            import_.accept(this, context);
        }
        for (TItemDefinition itemDefinition : element.getItemDefinition()) {
            itemDefinition.accept(this, context);
        }
        for (TDRGElement drgElement : element.getDrgElement()) {
            visitTDRGElement(drgElement, context);
        }
        for (TArtifact artifact : element.getArtifact()) {
            visitTArtifact(artifact, context);
        }
        for (TElementCollection elementCollection : element.getElementCollection()) {
            elementCollection.accept(this, context);
        }
        for (TBusinessContextElement businessContextElement : element.getBusinessContextElement()) {
            businessContextElement.accept(this, context);
        }
        DMNDI dmndi = element.getDMNDI();
        if (dmndi != null) {
            dmndi.accept(this, context);
        }
        return element;
    }

    //
    // Import
    //
    @Override
    public DMNBaseElement visit(TImport element, C context) {
        if (element == null) {
            return null;
        }

        visitTImportProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TImportedValues element, C context) {
        if (element == null) {
            return null;
        }

        visitTImportProperties(element, context);
        return element;
    }

    //
    // Data types
    //
    @Override
    public DMNBaseElement visit(TItemDefinition element, C context) {
        if (element == null) {
            return null;
        }

        visitTNamedElementProperties(element, context);
        element.setTypeRef(visitTypeRef(element.getTypeRef(), context));
        TUnaryTests allowedValues = element.getAllowedValues();
        if (allowedValues != null) {
            allowedValues.accept(this, context);
        }
        for (TItemDefinition itemDefinition : element.getItemComponent()) {
            itemDefinition.accept(this, context);
        }
        TFunctionItem functionItem = element.getFunctionItem();
        if (functionItem != null) {
            functionItem.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TFunctionItem element, C context) {
        if (element == null) {
            return null;
        }

        visitTDMNElementProperties(element, context);
        for (TInformationItem parameter : element.getParameters()) {
            parameter.accept(this, context);
        }
        element.setOutputTypeRef(visitTypeRef(element.getOutputTypeRef(), context));
        return element;
    }

    //
    // DRG Elements
    //
    protected void visitTDRGElement(TDRGElement element, C context) {
        if (element == null) {
            return;
        }

        if (element instanceof TInputData) {
            ((TInputData) element).accept(this, context);
        } else if (element instanceof TDecision) {
            ((TDecision) element).accept(this, context);
        } else if (element instanceof TInvocable) {
            visitTInvocable(element, context);
        } else if (element instanceof TKnowledgeSource) {
            ((TKnowledgeSource) element).accept(this, context);
        } else {
            throw new DMNRuntimeException("Not supported");
        }
    }

    @Override
    public DMNBaseElement visit(TInputData element, C context) {
        if (element == null) {
            return null;
        }

        visitTNamedElementProperties(element, context);
        TInformationItem variable = element.getVariable();
        if (variable != null) {
            variable.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TDecision element, C context) {
        if (element == null) {
            return null;
        }

        visitTNamedElementProperties(element, context);
        TInformationItem variable = element.getVariable();
        if (variable != null) {
            variable.accept(this, context);
        }
        for (TInformationRequirement informationRequirement : element.getInformationRequirement()) {
            informationRequirement.accept(this, context);
        }
        for (TKnowledgeRequirement knowledgeRequirement : element.getKnowledgeRequirement()) {
            knowledgeRequirement.accept(this, context);
        }
        for (TAuthorityRequirement authorityRequirement : element.getAuthorityRequirement()) {
            authorityRequirement.accept(this, context);
        }
        for (TDMNElementReference elementReference : element.getSupportedObjective()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference elementReference : element.getImpactedPerformanceIndicator()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference elementReference : element.getDecisionMaker()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference elementReference : element.getDecisionOwner()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference elementReference : element.getUsingProcess()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference elementReference : element.getUsingTask()) {
            elementReference.accept(this, context);
        }
        visitTExpression(element.getExpression(), context);
        return element;
    }

    protected void visitTInvocable(TDRGElement element, C context) {
        if (element == null) {
            return;
        }

        if (element instanceof TBusinessKnowledgeModel) {
            ((TBusinessKnowledgeModel) element).accept(this, context);
        } else if (element instanceof TDecisionService) {
            ((TDecisionService) element).accept(this, context);
        } else {
            throw new DMNRuntimeException("Not supported");
        }
    }

    @Override
    public DMNBaseElement visit(TBusinessKnowledgeModel element, C context) {
        if (element == null) {
            return null;
        }

        visitTInvocableProperties(element, context);
        TFunctionDefinition encapsulatedLogic = element.getEncapsulatedLogic();
        if (encapsulatedLogic != null) {
            encapsulatedLogic.accept(this, context);
        }
        for (TKnowledgeRequirement knowledgeRequirement : element.getKnowledgeRequirement()) {
            knowledgeRequirement.accept(this, context);
        }
        for (TAuthorityRequirement authorityRequirement : element.getAuthorityRequirement()) {
            authorityRequirement.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TDecisionService element, C context) {
        if (element == null) {
            return null;
        }

        visitTInvocableProperties(element, context);
        for (TDMNElementReference elementReference : element.getOutputDecision()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference elementReference : element.getEncapsulatedDecision()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference elementReference : element.getInputDecision()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference elementReference : element.getInputData()) {
            elementReference.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TKnowledgeSource element, C context) {
        if (element == null) {
            return null;
        }

        visitTNamedElementProperties(element, context);
        for (TAuthorityRequirement authorityRequirement : element.getAuthorityRequirement()) {
            authorityRequirement.accept(this, context);
        }
        TDMNElementReference owner = element.getOwner();
        if (owner != null) {
            owner.accept(this, context);
        }
        return element;
    }

    //
    // Expressions
    //
    protected void visitTExpression(TExpression element, C context) {
        if (element == null) {
            return;
        }

        if (element instanceof TContext) {
            element.accept(this, context);
        } else if (element instanceof TDecisionTable) {
            element.accept(this, context);
        } else if (element instanceof TFunctionDefinition) {
            element.accept(this, context);
        } else if (element instanceof TInvocation) {
            element.accept(this, context);
        } else if (element instanceof TList) {
            element.accept(this, context);
        } else if (element instanceof TLiteralExpression) {
            element.accept(this, context);
        } else if (element instanceof TRelation) {
            element.accept(this, context);
        } else if (element instanceof TUnaryTests) {
            element.accept(this, context);
        } else if (element instanceof TConditional) {
            element.accept(this, context);
        } else if (element instanceof TFor) {
            element.accept(this, context);
        } else if (element instanceof TFilter) {
            element.accept(this, context);
        } else if (element instanceof TEvery) {
            element.accept(this, context);
        } else if (element instanceof TSome) {
            element.accept(this, context);
        }
    }

    @Override
    public DMNBaseElement visit(TContext element, C context) {
        if (element == null) {
            return null;
        }

        visitTExpressionProperties(element, context);
        for (TContextEntry contextEntry : element.getContextEntry()) {
            contextEntry.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TContextEntry element, C context) {
        if (element == null) {
            return null;
        }

        visitTDMNElementProperties(element, context);
        TInformationItem variable = element.getVariable();
        if (variable != null) {
            variable.accept(this, context);
        }
        visitTExpression(element.getExpression(), context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TDecisionTable element, C context) {
        if (element == null) {
            return null;
        }

        visitTExpressionProperties(element, context);
        for (TInputClause inputClause : element.getInput()) {
            inputClause.accept(this, context);
        }
        for (TOutputClause outputClause : element.getOutput()) {
            outputClause.accept(this, context);
        }
        for (TRuleAnnotationClause clause : element.getAnnotation()) {
            clause.accept(this, context);
        }
        for (TDecisionRule rule : element.getRule()) {
            rule.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TInputClause element, C context) {
        if (element == null) {
            return null;
        }

        visitTDMNElementProperties(element, context);
        TLiteralExpression inputExpression = element.getInputExpression();
        if (inputExpression != null) {
            inputExpression.accept(this, context);
        }
        TUnaryTests inputValues = element.getInputValues();
        if (inputValues != null) {
            inputValues.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TOutputClause element, C context) {
        if (element == null) {
            return null;
        }

        visitTDMNElementProperties(element, context);
        TUnaryTests outputValues = element.getOutputValues();
        if (outputValues != null) {
            outputValues.accept(this, context);
        }
        TLiteralExpression defaultOutputEntry = element.getDefaultOutputEntry();
        if (defaultOutputEntry != null) {
            defaultOutputEntry.accept(this, context);
        }
        element.setTypeRef(visitTypeRef(element.getTypeRef(), context));
        return element;
    }

    @Override
    public DMNBaseElement visit(TRuleAnnotationClause element, C context) {
        return element;
    }

    @Override
    public DMNBaseElement visit(TDecisionRule element, C context) {
        if (element == null) {
            return null;
        }

        visitTDMNElementProperties(element, context);
        for (TUnaryTests unaryTests : element.getInputEntry()) {
            unaryTests.accept(this, context);
        }
        for (TLiteralExpression expression : element.getOutputEntry()) {
            expression.accept(this, context);
        }
        for (TRuleAnnotation ruleAnnotation : element.getAnnotationEntry()) {
            ruleAnnotation.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TRuleAnnotation element, C context) {
        return element;
    }

    @Override
    public DMNBaseElement visit(TFunctionDefinition element, C context) {
        if (element == null) {
            return null;
        }

        visitTExpressionProperties(element, context);
        for (TInformationItem informationItem : element.getFormalParameter()) {
            informationItem.accept(this, context);
        }
        visitTExpression(element.getExpression(), context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TInvocation element, C context) {
        if (element == null) {
            return null;
        }


        visitTExpressionProperties(element, context);
        visitTExpression(element.getExpression(), context);
        for (TBinding binding : element.getBinding()) {
            binding.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TBinding element, C context) {
        if (element == null) {
            return null;
        }

        TInformationItem parameter = element.getParameter();
        if (parameter != null) {
            parameter.accept(this, context);
        }
        visitTExpression(element.getExpression(), context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TList element, C context) {
        if (element == null) {
            return null;
        }

        visitTExpressionProperties(element, context);
        for (TExpression expression : element.getExpression()) {
            visitTExpression(expression, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TLiteralExpression element, C context) {
        if (element == null) {
            return null;
        }

        visitTExpressionProperties(element, context);
        TImportedValues importedValues = element.getImportedValues();
        if (importedValues != null) {
            importedValues.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TRelation element, C context) {
        if (element == null) {
            return null;
        }

        visitTExpressionProperties(element, context);
        for (TInformationItem informationItem : element.getColumn()) {
            informationItem.accept(this, context);
        }
        for (TList list : element.getRow()) {
            list.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TUnaryTests element, C context) {
        if (element == null) {
            return null;
        }

        visitTExpressionProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TConditional element, C context) {
        if (element == null) {
            return null;
        }

        visitTExpressionProperties(element, context);
        TChildExpression ifExp = element.getIf();
        if (ifExp != null) {
            ifExp.accept(this, context);
        }
        TChildExpression thenExp = element.getThen();
        if (thenExp != null) {
            thenExp.accept(this, context);
        }
        TChildExpression elseExp = element.getElse();
        if (elseExp != null) {
            elseExp.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TFor element, C context) {
        if (element == null) {
            return null;
        }

        visitTIteratorProperties(element, context);
        TChildExpression returnExp = element.getReturn();
        if (returnExp != null) {
            returnExp.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TFilter element, C context) {
        if (element == null) {
            return null;
        }

        visitTExpressionProperties(element, context);
        TChildExpression inExp = element.getIn();
        if (inExp != null) {
            inExp.accept(this, context);
        }
        TChildExpression matchExp = element.getMatch();
        if (matchExp != null) {
            matchExp.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TEvery element, C context) {
        if (element == null) {
            return null;
        }

        visitTQuantifiedProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TSome element, C context) {
        if (element == null) {
            return null;
        }

        visitTQuantifiedProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TChildExpression element, C context) {
        if (element == null) {
            return null;
        }

        visitTChildExpressionProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TTypedChildExpression element, C context) {
        if (element == null) {
            return null;
        }

        visitTChildExpressionProperties(element, context);
        element.setTypeRef(element.getTypeRef());
        return element;
    }

    //
    // Requirements
    //
    @Override
    public DMNBaseElement visit(TAuthorityRequirement element, C context) {
        if (element == null) {
            return null;
        }

        visitTDMNElementProperties(element, context);
        TDMNElementReference requiredDecision = element.getRequiredDecision();
        if (requiredDecision != null) {
            requiredDecision.accept(this, context);
        }
        TDMNElementReference requiredInput = element.getRequiredInput();
        if (requiredInput != null) {
            requiredInput.accept(this, context);
        }
        TDMNElementReference requiredAuthority = element.getRequiredAuthority();
        if (requiredAuthority != null) {
            requiredAuthority.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TInformationRequirement element, C context) {
        if (element == null) {
            return null;
        }

        visitTDMNElementProperties(element, context);
        TDMNElementReference requiredDecision = element.getRequiredDecision();
        if (requiredDecision != null) {
            requiredDecision.accept(this, context);
        }
        TDMNElementReference requiredInput = element.getRequiredInput();
        if (requiredInput != null) {
            requiredInput.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TKnowledgeRequirement element, C context) {
        if (element == null) {
            return null;
        }

        visitTDMNElementProperties(element, context);
        TDMNElementReference requiredKnowledge = element.getRequiredKnowledge();
        if (requiredKnowledge != null) {
            requiredKnowledge.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TInformationItem element, C context) {
        if (element == null) {
            return null;
        }

        visitTNamedElementProperties(element, context);
        element.setTypeRef(visitTypeRef(element.getTypeRef(), context));
        return element;
    }

    @Override
    public DMNBaseElement visit(TDMNElementReference element, C context) {
        if (element == null) {
            return null;
        }

        element.setHref(element.getHref());
        return element;
    }

    //
    // Artifacts
    //
    protected void visitTArtifact(TArtifact element, C context) {
        if (element == null) {
            return;
        }

        visitTDMNElementProperties(element, context);
        if (element instanceof TAssociation) {
            ((TAssociation) element).accept(this, context);
        } else if (element instanceof TGroup) {
            ((TGroup) element).accept(this, context);
        } else if (element instanceof TTextAnnotation) {
            ((TTextAnnotation) element).accept(this, context);
        } else {
            throw new DMNRuntimeException("Not supported");
        }
    }

    @Override
    public DMNBaseElement visit(TAssociation element, C context) {
        if (element == null) {
            return null;
        }

        visitTDMNElementProperties(element, context);
        TDMNElementReference sourceRef = element.getSourceRef();
        if (sourceRef != null) {
            sourceRef.accept(this, context);
        }
        TDMNElementReference targetRef = element.getTargetRef();
        if (targetRef != null) {
            targetRef.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TGroup element, C context) {
        if (element == null) {
            return null;
        }

        visitTDMNElementProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TTextAnnotation element, C context) {
        if (element == null) {
            return null;
        }

        visitTDMNElementProperties(element, context);
        return element;
    }

    //
    // Other DMN elements
    //
    @Override
    public DMNBaseElement visit(TBusinessContextElement element, C context) {
        if (element == null) {
            return null;
        }

        visitTNamedElementProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TPerformanceIndicator element, C context) {
        if (element == null) {
            return null;
        }

        visitTNamedElementProperties(element, context);
        for (TDMNElementReference elementReference : element.getImpactingDecision()) {
            elementReference.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TOrganizationUnit element, C context) {
        if (element == null) {
            return null;
        }

        visitTNamedElementProperties(element, context);
        for (TDMNElementReference elementReference : element.getDecisionMade()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference elementReference : element.getDecisionOwned()) {
            elementReference.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TElementCollection element, C context) {
        if (element == null) {
            return null;
        }

        visitTNamedElementProperties(element, context);
        for (TDMNElementReference elementReference : element.getDrgElement()) {
            elementReference.accept(this, context);
        }
        return element;
    }

    //
    // Extensions
    //
    @Override
    public DMNBaseElement visit(TDMNElement.ExtensionElements element, C context) {
        if (element == null) {
            return null;
        }

        return visitExtensions(element, context);
    }

    //
    // DMN properties
    //
    protected void visitTDMNElementProperties(TDMNElement element, C context) {
        if (element == null) {
            return;
        }

        visitExtensions(element.getExtensionElements(), context);
    }

    protected void visitTNamedElementProperties(TNamedElement element, C context) {
        if (element == null) {
            return;
        }

        visitTDMNElementProperties(element, context);
    }

    protected void visitTImportProperties(TImport element, C context) {
        if (element == null) {
            return;
        }

        visitTNamedElementProperties(element, context);
    }

    protected void visitTInvocableProperties(TInvocable element, C context) {
        if (element == null) {
            return;
        }

        visitTNamedElementProperties(element, context);
        TInformationItem variable = element.getVariable();
        if (variable != null) {
            variable.accept(this, context);
        }
    }

    protected void visitTExpressionProperties(TExpression element, C context) {
        if (element == null) {
            return;
        }

        visitTDMNElementProperties(element, context);
        element.setTypeRef(visitTypeRef(element.getTypeRef(), context));
    }

    protected void visitTIteratorProperties(TIterator element, C context) {
        if (element == null) {
            return;
        }

        visitTExpressionProperties(element, context);
        TChildExpression inExp = element.getIn();
        if (inExp != null) {
            inExp.accept(this, context);
        }
    }

    protected void visitTQuantifiedProperties(TQuantified element, C context) {
        if (element == null) {
            return;
        }

        visitTIteratorProperties(element, context);
        TChildExpression satisfies = element.getSatisfies();
        if (satisfies != null) {
            satisfies.accept(this, context);
        }
    }

    protected void visitTChildExpressionProperties(TChildExpression element, C context) {
        TExpression exp = element.getExpression();
        if (exp != null) {
            visitTExpression(exp, context);
        }
    }

    protected QName visitTypeRef(QName typeRef, C context) {
        return typeRef;
    }

    protected QName visitQName(QName qName, C context) {
        return qName;
    }

    protected DMNBaseElement visitExtensions(TDMNElement.ExtensionElements element, C context) {
        return element;
    }

    //
    // DMN DI elements
    //
    @Override
    public DMNBaseElement visit(DMNDI element, C context) {
        if (element == null) {
            return null;
        }

        for (DMNDiagram diagram : element.getDMNDiagram()) {
            diagram.accept(this, context);
        }
        for (DMNStyle style : element.getDMNStyle()) {
            style.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(DMNDiagram element, C context) {
        if (element == null) {
            return null;
        }

        visitDiagramProperties(element, context);
        Dimension size = element.getSize();
        if (size != null) {
            size.accept(this, context);
        }
        for (DiagramElement diagramElement : element.getDMNDiagramElement()) {
            visitDiagramElement(diagramElement, context);
        }
        return element;
    }

    protected void visitDiagramElement(DiagramElement element, C context) {
        if (element == null) {
            return;
        }

        visitDiagramElementProperties(element, context);
        if (element instanceof DMNDiagram) {
            ((DMNDiagram) element).accept(this, context);
        } else if (element instanceof DMNDecisionServiceDividerLine) {
            ((DMNDecisionServiceDividerLine) element).accept(this, context);
        } else if (element instanceof DMNEdge) {
            ((DMNEdge) element).accept(this, context);
        } else if (element instanceof DMNLabel) {
            ((DMNLabel) element).accept(this, context);
        } else if (element instanceof DMNShape) {
            ((DMNShape) element).accept(this, context);
        } else {
            throw new DMNRuntimeException("Not supported");
        }
    }

    @Override
    public DMNBaseElement visit(DMNShape element, C context) {
        if (element == null) {
            return null;
        }

        visitShapeProperties(element, context);
        DMNLabel dmnLabel = element.getDMNLabel();
        if (dmnLabel != null) {
            dmnLabel.accept(this, context);
        }
        DMNDecisionServiceDividerLine dmnDecisionServiceDividerLine = element.getDMNDecisionServiceDividerLine();
        if (dmnDecisionServiceDividerLine != null) {
            dmnDecisionServiceDividerLine.accept(this, context);
        }
        element.setDmnElementRef(visitQName(element.getDmnElementRef(), context));
        return element;
    }

    @Override
    public DMNBaseElement visit(DMNEdge element, C context) {
        if (element == null) {
            return null;
        }

        visitEdgeProperties(element, context);
        DMNLabel dmnLabel = element.getDMNLabel();
        if (dmnLabel != null) {
            dmnLabel.accept(this, context);
        }
        return element;
    }

    protected void visitStyle(Style element, C context) {
        if (element == null) {
            return;
        }

        if (element instanceof DMNStyle) {
            ((DMNStyle) element).accept(this, context);
        } else if (element instanceof Style.IDREFStubStyle) {
            ((Style.IDREFStubStyle) element).accept(this, context);
        } else {
            throw new DMNRuntimeException("Not supported");
        }
    }

    @Override
    public DMNBaseElement visit(DMNStyle element, C context) {
        if (element == null) {
            return null;
        }

        visitStyleProperties(element, context);
        Color fillColor = element.getFillColor();
        if (fillColor != null) {
            fillColor.accept(this, context);
        }
        Color strokeColor = element.getStrokeColor();
        if (strokeColor != null) {
            strokeColor.accept(this, context);
        }
        Color fontColor = element.getFontColor();
        if (fontColor != null) {
            fontColor.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(Style.IDREFStubStyle element, C context) {
        if (element == null) {
            return null;
        }

        visitStyleProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(DMNLabel element, C context) {
        if (element == null) {
            return null;
        }

        visitShapeProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(DMNDecisionServiceDividerLine element, C context) {
        if (element == null) {
            return null;
        }

        visitEdgeProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(Color element, C context) {
        return element;
    }

    @Override
    public DMNBaseElement visit(Point element, C context) {
        return element;
    }

    @Override
    public DMNBaseElement visit(Bounds element, C context) {
        return element;
    }

    @Override
    public DMNBaseElement visit(Dimension element, C context) {
        return element;
    }

    @Override
    public DMNBaseElement visit(DiagramElement.Extension element, C context) {
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

    protected DMNBaseElement visitExtension(DiagramElement.Extension element, C context) {
        return element;
    }

    protected Style.Extension visitExtension(Style.Extension element, C context) {
        return element;
    }

    //
    // DMN DI properties
    //
    protected void visitDiagramProperties(Diagram element, C context) {
        if (element == null) {
            return;
        }

        visitDiagramElementProperties(element, context);
    }

    protected void visitDiagramElementProperties(DiagramElement element, C context) {
        if (element == null) {
            return;
        }

        visitExtension(element.getExtension(), context);
        visitStyle(element.getStyle(), context);
        visitStyle(element.getSharedStyle(), context);
    }

    protected void visitShapeProperties(Shape element, C context) {
        if (element == null) {
            return;
        }

        visitDiagramElementProperties(element, context);
        Bounds bounds = element.getBounds();
        if (bounds != null) {
            bounds.accept(this, context);
        }
    }

    protected void visitEdgeProperties(Edge element, C context) {
        if (element == null) {
            return;
        }

        visitDiagramElementProperties(element, context);
        for (Point point : element.getWaypoint()) {
            point.accept(this, context);
        }
    }

    protected void visitStyleProperties(Style element, C context) {
        if (element == null) {
            return;
        }

        visitExtension(element.getExtension(), context);
    }
}
