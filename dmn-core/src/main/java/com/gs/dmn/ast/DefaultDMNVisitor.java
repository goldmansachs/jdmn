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
package com.gs.dmn.ast;

import com.gs.dmn.ast.dmndi.*;

import javax.xml.namespace.QName;

public class DefaultDMNVisitor<C> implements Visitor<C> {
    //
    // DMN Elements
    //
    // Definitions
    @Override
    public Object visit(TDefinitions<C> element, C context) {
        visitTNamedElementProperties(element, context);
        for (TImport<C> import_ : element.getImport()){
            import_.accept(this, context);
        }
        for (TItemDefinition<C> itemDefinition : element.getItemDefinition()) {
            itemDefinition.accept(this, context);
        }
        for (TDRGElement<C> drgElement : element.getDrgElement()) {
            visitTDRGElement(drgElement, context);
        }
        for (TArtifact<C> artifact : element.getArtifact()) {
            visitTArtifact(artifact, context);
        }
        for (TElementCollection<C> elementCollection : element.getElementCollection()) {
            elementCollection.accept(this, context);
        }
        for (TBusinessContextElement<C> businessContextElement : element.getBusinessContextElement()) {
            businessContextElement.accept(this, context);
        }
        DMNDI<C> dmndi = element.getDMNDI();
        if (dmndi != null) {
            dmndi.accept(this, context);
        }
        return element;
    }

    // Import
    @Override
    public Object visit(TImport<C> element, C context) {
        visitTNamedElementProperties(element, context);
        return element;
    }

    @Override
    public Object visit(TImportedValues<C> element, C context) {
        visitTNamedElementProperties(element, context);
        return element;
    }

    // Data types
    @Override
    public Object visit(TItemDefinition<C> element, C context) {
        visitTNamedElementProperties(element, context);
        element.setTypeRef(visitTypeRef(element.getTypeRef(), context));
        TUnaryTests<C> allowedValues = element.getAllowedValues();
        if (allowedValues != null) {
            allowedValues.accept(this, context);
        }
        for (TItemDefinition<C> itemDefinition : element.getItemComponent()) {
            itemDefinition.accept(this, context);
        }
        TFunctionItem<C> functionItem = element.getFunctionItem();
        if (functionItem  != null) {
            functionItem.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TFunctionItem<C> element, C context) {
        visitTDMNElementProperties(element, context);
        for (TInformationItem<C> parameter : element.getParameters()) {
            parameter.accept(this, context);
        }
        element.setOutputTypeRef(visitTypeRef(element.getOutputTypeRef(), context));
        return element;
    }

    // DRG Elements
    @Override
    public Object visit(TInputData<C> element, C context) {
        visitTNamedElementProperties(element, context);
        TInformationItem<C> variable = element.getVariable();
        if (variable  != null) {
            variable.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TDecision<C> element, C context) {
        visitTNamedElementProperties(element, context);
        TInformationItem<C> variable = element.getVariable();
        if (variable != null) {
            variable.accept(this,context);
        }
        for (TInformationRequirement<C> informationRequirement : element.getInformationRequirement()) {
            informationRequirement.accept(this, context);
        }
        for (TKnowledgeRequirement<C> knowledgeRequirement: element.getKnowledgeRequirement()) {
            knowledgeRequirement.accept(this, context);
        }
        for (TAuthorityRequirement<C> authorityRequirement : element.getAuthorityRequirement()) {
            authorityRequirement.accept(this, context);
        }
        for (TDMNElementReference<C> elementReference : element.getSupportedObjective()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference<C> elementReference : element.getImpactedPerformanceIndicator()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference<C> elementReference : element.getDecisionMaker()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference<C> elementReference : element.getDecisionOwner()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference<C> elementReference : element.getUsingProcess()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference<C> elementReference : element.getUsingTask()) {
            elementReference.accept(this, context);
        }
        visitTExpression(element.getExpression(), context);
        return element;
    }

    @Override
    public Object visit(TBusinessKnowledgeModel<C> element, C context) {
        visitTNamedElementProperties(element, context);
        TInformationItem<C> variable = element.getVariable();
        if (variable != null) {
            variable.accept(this,context);
        }
        TFunctionDefinition<C> encapsulatedLogic = element.getEncapsulatedLogic();
        if (encapsulatedLogic != null) {
            encapsulatedLogic.accept(this, context);
        }
        for (TKnowledgeRequirement<C> knowledgeRequirement : element.getKnowledgeRequirement()) {
            knowledgeRequirement.accept(this, context);
        }
        for (TAuthorityRequirement<C> authorityRequirement : element.getAuthorityRequirement()) {
            authorityRequirement.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TDecisionService<C> element, C context) {
        visitTNamedElementProperties(element, context);
        TInformationItem<C> variable = element.getVariable();
        if (variable != null) {
            variable.accept(this,context);
        }
        for (TDMNElementReference<C> elementReference : element.getOutputDecision()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference<C> elementReference : element.getEncapsulatedDecision()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference<C> elementReference : element.getInputDecision()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference<C> elementReference : element.getInputData()) {
            elementReference.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TKnowledgeSource<C> element, C context) {
        visitTNamedElementProperties(element, context);
        for (TAuthorityRequirement<C> authorityRequirement : element.getAuthorityRequirement()) {
            authorityRequirement.accept(this, context);
        }
        TDMNElementReference<C> owner = element.getOwner();
        if (owner != null) {
            owner.accept(this, context);
        }
        return element;
    }

    // Expressions
    @Override
    public Object visit(TContext<C> element, C context) {
        visitTExpressionProperties(element, context);
        for (TContextEntry<C> contextEntry : element.getContextEntry()) {
            contextEntry.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TContextEntry<C> element, C context) {
        visitTDMNElementProperties(element, context);
        TInformationItem<C> variable = element.getVariable();
        if (variable != null) {
            variable.accept(this, context);
        }
        visitTExpression(element.getExpression(), context);
        return element;
    }

    @Override
    public Object visit(TDecisionTable<C> element, C context) {
        visitTExpressionProperties(element, context);
        for (TInputClause<C> inputClause : element.getInput()) {
            inputClause.accept(this, context);
        }
        for (TOutputClause<C> outputClause : element.getOutput()) {
            outputClause.accept(this, context);
        }
        for (TRuleAnnotationClause<C> clause : element.getAnnotation()) {
            clause.accept(this, context);
        }
        for (TDecisionRule<C> rule : element.getRule()) {
            rule.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TInputClause<C> element, C context) {
        visitTDMNElementProperties(element, context);
        TLiteralExpression<C> inputExpression = element.getInputExpression();
        if (inputExpression != null) {
            inputExpression.accept(this, context);
        }
        TUnaryTests<C> inputValues = element.getInputValues();
        if (inputValues != null) {
            inputValues.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TOutputClause<C> element, C context) {
        visitTDMNElementProperties(element, context);
        TUnaryTests<C> outputValues = element.getOutputValues();
        if (outputValues != null) {
            outputValues.accept(this, context);
        }
        TLiteralExpression<C> defaultOutputEntry = element.getDefaultOutputEntry();
        if (defaultOutputEntry != null) {
            defaultOutputEntry.accept(this, context);
        }
        element.setTypeRef(visitTypeRef(element.getTypeRef(), context));
        return element;
    }

    @Override
    public Object visit(TRuleAnnotationClause<C> element, C context) {
        return element;
    }

    @Override
    public Object visit(TDecisionRule<C> element, C context) {
        visitTDMNElementProperties(element, context);
        for (TUnaryTests<C> unaryTests : element.getInputEntry()) {
            unaryTests.accept(this, context);
        }
        for (TLiteralExpression<C> expression : element.getOutputEntry()) {
            expression.accept(this, context);
        }
        for (TRuleAnnotation<C> ruleAnnotation : element.getAnnotationEntry()) {
            ruleAnnotation.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TRuleAnnotation<C> element, C context) {
        return element;
    }

    @Override
    public Object visit(TFunctionDefinition<C> element, C context) {
        visitTExpressionProperties(element, context);
        for (TInformationItem<C> informationItem : element.getFormalParameter()) {
            informationItem.accept(this, context);
        }
        visitTExpression(element.getExpression(), context);
        return element;
    }

    @Override
    public Object visit(TInvocation<C> element, C context) {
        visitTExpressionProperties(element, context);
        visitTExpression(element.getExpression(), context);
        for (TBinding<C> binding : element.getBinding()) {
            binding.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TBinding<C> element, C context) {
        TInformationItem<C> parameter = element.getParameter();
        if (parameter != null) {
            parameter.accept(this, context);
        }
        visitTExpression(element.getExpression(), context);
        return element;
    }

    @Override
    public Object visit(TList<C> element, C context) {
        visitTExpressionProperties(element, context);
        for (TExpression<C> expression : element.getExpression()) {
            visitTExpression(expression, context);
        }
        return element;
    }

    @Override
    public Object visit(TLiteralExpression<C> element, C context) {
        visitTExpressionProperties(element, context);
        TImportedValues<C> importedValues = element.getImportedValues();
        if (importedValues != null) {
            importedValues.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TRelation<C> element, C context) {
        visitTExpressionProperties(element, context);
        for (TInformationItem<C> informationItem : element.getColumn()) {
            informationItem.accept(this, context);
        }
        for (TList<C> list : element.getRow()) {
            list.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TUnaryTests<C> element, C context) {
        visitTExpressionProperties(element, context);
        return element;
    }

    // Requirements
    @Override
    public Object visit(TAuthorityRequirement<C> element, C context) {
        visitTDMNElementProperties(element, context);
        TDMNElementReference<C> requiredDecision = element.getRequiredDecision();
        if (requiredDecision != null) {
            requiredDecision.accept(this, context);
        }
        TDMNElementReference<C> requiredInput = element.getRequiredInput();
        if (requiredInput != null) {
            requiredInput.accept(this, context);
        }
        TDMNElementReference<C> requiredAuthority = element.getRequiredAuthority();
        if (requiredAuthority != null) {
            requiredAuthority.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TInformationRequirement<C> element, C context) {
        visitTDMNElementProperties(element, context);
        TDMNElementReference<C> requiredDecision = element.getRequiredDecision();
        if (requiredDecision != null) {
            requiredDecision.accept(this, context);
        }
        TDMNElementReference<C> requiredInput = element.getRequiredInput();
        if (requiredInput != null) {
            requiredInput.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TKnowledgeRequirement<C> element, C context) {
        visitTDMNElementProperties(element, context);
        TDMNElementReference<C> requiredKnowledge = element.getRequiredKnowledge();
        if (requiredKnowledge != null) {
            requiredKnowledge.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TInformationItem<C> element, C context) {
        visitTNamedElementProperties(element, context);
        element.setTypeRef(visitTypeRef(element.getTypeRef(), context));
        return element;
    }

    @Override
    public Object visit(TDMNElementReference<C> element, C context) {
        return element;
    }

    // Artifacts
    @Override
    public Object visit(TAssociation<C> element, C context) {
        visitTDMNElementProperties(element, context);
        TDMNElementReference<C> sourceRef = element.getSourceRef();
        if (sourceRef != null) {
            sourceRef.accept(this, context);
        }
        TDMNElementReference<C> targetRef = element.getTargetRef();
        if (targetRef != null) {
            targetRef.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TGroup<C> element, C context) {
        visitTDMNElementProperties(element, context);
        return element;
    }

    @Override
    public Object visit(TTextAnnotation<C> element, C context) {
        visitTDMNElementProperties(element, context);
        return element;
    }

    // Other
    @Override
    public Object visit(TBusinessContextElement<C> element, C context) {
        visitTNamedElementProperties(element, context);
        return element;
    }

    @Override
    public Object visit(TPerformanceIndicator<C> element, C context) {
        visitTNamedElementProperties(element, context);
        for (TDMNElementReference<C> elementReference : element.getImpactingDecision()) {
            elementReference.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TOrganizationUnit<C> element, C context) {
        visitTNamedElementProperties(element, context);
        for (TDMNElementReference<C> elementReference : element.getDecisionMade()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference<C> elementReference : element.getDecisionOwned()) {
            elementReference.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(TElementCollection<C> element, C context) {
        visitTNamedElementProperties(element, context);
        for (TDMNElementReference<C> elementReference : element.getDrgElement()) {
            elementReference.accept(this, context);
        }
        return element;
    }

    // Extensions
    @Override
    public Object visit(TDMNElement.ExtensionElements element, C context) {
        return visitExtensions(element, context);
    }

    //
    // DMNDI elements
    //
    @Override
    public Object visit(DMNDI<C> element, C context) {
        for (DMNDiagram<C> diagram : element.getDMNDiagram()) {
            diagram.accept(this, context);
        }
        for (DMNStyle<C> style : element.getDMNStyle()) {
            style.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(DMNDiagram<C> element, C context) {
        visitDiagramElementProperties(element, context);
        Dimension<C> size = element.getSize();
        if (size != null) {
            size.accept(this, context);
        }
        for (DiagramElement<C> diagramElement : element.getDMNDiagramElement()) {
            visitDiagramElement(diagramElement, context);
        }
        return element;
    }

    @Override
    public Object visit(DMNShape<C> element, C context) {
        visitDiagramElementProperties(element, context);
        visitShapeProperties(element, context);
        DMNLabel<C> dmnLabel = element.getDMNLabel();
        if (dmnLabel != null) {
            dmnLabel.accept(this, context);
        }
        DMNDecisionServiceDividerLine<C> dmnDecisionServiceDividerLine = element.getDMNDecisionServiceDividerLine();
        if (dmnDecisionServiceDividerLine != null) {
            dmnDecisionServiceDividerLine.accept(this, context);
        }
        element.setDmnElementRef(visitQName(element.getDmnElementRef(), context));
        return element;
    }

    @Override
    public Object visit(DMNEdge<C> element, C context) {
        visitEdgeProperties(element, context);
        DMNLabel<C> dmnLabel = element.getDMNLabel();
        if (dmnLabel != null) {
            dmnLabel.accept(this, context);
        }
        element.setDmnElementRef(visitQName(element.getDmnElementRef(), context));
        element.setSourceElement(visitQName(element.getSourceElement(), context));
        element.setTargetElement(visitQName(element.getTargetElement(), context));
        return element;
    }

    @Override
    public Object visit(DMNStyle<C> element, C context) {
        visitExtension(element.getExtension(), context);
        Color<C> fillColor = element.getFillColor();
        if (fillColor != null) {
            fillColor.accept(this, context);
        }
        Color<C> strokeColor = element.getStrokeColor();
        if (strokeColor != null) {
            strokeColor.accept(this, context);
        }
        Color<C> fontColor = element.getFontColor();
        if (fontColor != null) {
            fontColor.accept(this, context);
        }
        return element;
    }

    @Override
    public Object visit(DMNLabel<C> element, C context) {
        visitShapeProperties(element, context);
        return element;
    }

    @Override
    public Object visit(DMNDecisionServiceDividerLine<C> element, C context) {
        visitEdgeProperties(element, context);
        return element;
    }

    @Override
    public Object visit(Color<C> element, C context) {
        return element;
    }

    @Override
    public Object visit(Point<C> element, C context) {
        return element;
    }

    @Override
    public Object visit(Bounds<C> element, C context) {
        return element;
    }

    @Override
    public Object visit(Dimension<C> element, C context) {
        return element;
    }

    @Override
    public Object visit(DiagramElement.Extension element, C context) {
        return visitExtension(element, context);
    }

    @Override
    public Object visit(Style.Extension element, C context) {
        return visitExtension(element, context);
    }

    private void visitTDMNElementProperties(TDMNElement<C> element, C context) {
        visitExtensions(element.getExtensionElements(), context);
    }

    private void visitTArtifact(TArtifact<C> element, C context) {
        visitTDMNElementProperties(element, context);
        if (element instanceof TAssociation) {
            ((TAssociation<C>) element).accept(this, context);
        } else if (element instanceof TGroup) {
            ((TGroup<C>) element).accept(this, context);
        } else if (element instanceof TTextAnnotation) {
            ((TTextAnnotation<C>) element).accept(this, context);
        }
    }

    private void visitTExpressionProperties(TExpression<C> element, C context) {
        visitTDMNElementProperties(element, context);
        element.setTypeRef(visitTypeRef(element.getTypeRef(), context));
    }

    private void visitTExpression(TExpression<C> element, C context) {
        if (element == null) {
            return;
        }

        visitTExpressionProperties(element, context);
        if (element instanceof TContext) {
            ((TContext<C>) element).accept(this, context);
        } else if (element instanceof TDecisionTable) {
            ((TDecisionTable<C>) element).accept(this, context);
        } else if (element instanceof TFunctionDefinition) {
            ((TFunctionDefinition<C>) element).accept(this, context);
        } else if (element instanceof TInvocation) {
            ((TInvocation<C>) element).accept(this, context);
        } else if (element instanceof TList) {
            ((TList<C>) element).accept(this, context);
        } else if (element instanceof TLiteralExpression) {
            ((TLiteralExpression<C>) element).accept(this, context);
        } else if (element instanceof TRelation) {
            ((TRelation<C>) element).accept(this, context);
        } else if (element instanceof TUnaryTests) {
            ((TUnaryTests<C>) element).accept(this, context);
        }
    }

    private void visitTNamedElementProperties(TNamedElement<C> element, C context) {
        visitTDMNElementProperties(element, context);
    }

    private void visitTDRGElement(TDRGElement<C> element, C context) {
        if (element instanceof TInputData) {
            ((TInputData<C>) element).accept(this, context);
        } else if (element instanceof TDecision) {
            ((TDecision<C>) element).accept(this, context);
        } else if (element instanceof TInvocable) {
            visitInvocable(element, context);
        } else if (element instanceof TKnowledgeSource) {
            ((TKnowledgeSource<C>) element).accept(this, context);
        }
    }

    private void visitInvocable(TDRGElement<C> element, C context) {
        if (element instanceof TBusinessKnowledgeModel) {
            ((TBusinessKnowledgeModel<C>) element).accept(this, context);
        } else if (element instanceof TDecisionService) {
            ((TDecisionService<C>) element).accept(this, context);
        }
    }

    protected QName visitTypeRef(QName typeRef, C context) {
        return typeRef;
    }

    protected QName visitQName(QName qName, C context) {
        return qName;
    }

    private Object visitExtensions(TDMNElement.ExtensionElements element, C context) {
        return element;
    }

    private void visitDiagramElementProperties(DiagramElement<C> element, C context) {
        visitExtension(element.getExtension(), context);
    }

    private void visitDiagramElement(DiagramElement<C> element, C context) {
        visitDiagramElementProperties(element, context);
        if (element instanceof DMNDiagram) {
            ((DMNDiagram<C>) element).accept(this, context);
        } else if (element instanceof DMNDecisionServiceDividerLine) {
            ((DMNDecisionServiceDividerLine<C>) element).accept(this, context);
        } else if (element instanceof DMNEdge) {
            ((DMNEdge<C>) element).accept(this, context);
        } else if (element instanceof DMNLabel) {
            ((DMNLabel<C>) element).accept(this, context);
        } else if (element instanceof DMNShape) {
            ((DMNShape<C>) element).accept(this, context);
        }
    }

    private void visitShapeProperties(Shape<C> element, C context) {
        visitDiagramElementProperties(element, context);
        Bounds<C> bounds = element.getBounds();
        if (bounds != null) {
            bounds.accept(this, context);
        }
    }

    private void visitEdgeProperties(Edge<C> element, C context) {
        visitDiagramElementProperties(element, context);
        for (Point<C> point : element.getWaypoint()) {
            point.accept(this, context);
        }
    }

    private Object visitExtension(DiagramElement.Extension element, C context) {
        return element;
    }

    private Object visitExtension(Style.Extension element, C context) {
        return element;
    }
}
