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

import javax.xml.namespace.QName;

public class TraversalVisitor<C> extends AbstractVisitor<C, Object> {
    public TraversalVisitor(ErrorHandler errorHandler) {
        super(errorHandler);
    }

    //
    // DMN Elements
    //
    // Definitions
    @Override
    public DMNBaseElement visit(TDefinitions element, C context) {
        visitTNamedElementProperties(element, context);
        for (TImport import_ : element.getImport()){
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
        for (TElementCollection  elementCollection : element.getElementCollection()) {
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

    // Import
    @Override
    public DMNBaseElement visit(TImport element, C context) {
        visitTNamedElementProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TImportedValues element, C context) {
        visitTNamedElementProperties(element, context);
        return element;
    }

    // Data types
    @Override
    public DMNBaseElement visit(TItemDefinition  element, C context) {
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
        if (functionItem  != null) {
            functionItem.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TFunctionItem  element, C context) {
        visitTDMNElementProperties(element, context);
        for (TInformationItem parameter : element.getParameters()) {
            parameter.accept(this, context);
        }
        element.setOutputTypeRef(visitTypeRef(element.getOutputTypeRef(), context));
        return element;
    }

    // DRG Elements
    @Override
    public DMNBaseElement visit(TInputData  element, C context) {
        visitTNamedElementProperties(element, context);
        TInformationItem variable = element.getVariable();
        if (variable  != null) {
            variable.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TDecision  element, C context) {
        visitTNamedElementProperties(element, context);
        TInformationItem variable = element.getVariable();
        if (variable != null) {
            variable.accept(this,context);
        }
        for (TInformationRequirement informationRequirement : element.getInformationRequirement()) {
            informationRequirement.accept(this, context);
        }
        for (TKnowledgeRequirement knowledgeRequirement: element.getKnowledgeRequirement()) {
            knowledgeRequirement.accept(this, context);
        }
        for (TAuthorityRequirement authorityRequirement : element.getAuthorityRequirement()) {
            authorityRequirement.accept(this, context);
        }
        for (TDMNElementReference  elementReference : element.getSupportedObjective()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference  elementReference : element.getImpactedPerformanceIndicator()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference  elementReference : element.getDecisionMaker()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference  elementReference : element.getDecisionOwner()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference  elementReference : element.getUsingProcess()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference  elementReference : element.getUsingTask()) {
            elementReference.accept(this, context);
        }
        visitTExpression(element.getExpression(), context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TBusinessKnowledgeModel  element, C context) {
        visitTNamedElementProperties(element, context);
        TInformationItem variable = element.getVariable();
        if (variable != null) {
            variable.accept(this,context);
        }
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
    public DMNBaseElement visit(TDecisionService  element, C context) {
        visitTNamedElementProperties(element, context);
        TInformationItem variable = element.getVariable();
        if (variable != null) {
            variable.accept(this,context);
        }
        for (TDMNElementReference  elementReference : element.getOutputDecision()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference  elementReference : element.getEncapsulatedDecision()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference  elementReference : element.getInputDecision()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference  elementReference : element.getInputData()) {
            elementReference.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TKnowledgeSource  element, C context) {
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

    // Expressions
    @Override
    public DMNBaseElement visit(TContext  element, C context) {
        visitTExpressionProperties(element, context);
        for (TContextEntry contextEntry : element.getContextEntry()) {
            contextEntry.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TContextEntry  element, C context) {
        visitTDMNElementProperties(element, context);
        TInformationItem variable = element.getVariable();
        if (variable != null) {
            variable.accept(this, context);
        }
        visitTExpression(element.getExpression(), context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TDecisionTable  element, C context) {
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
    public DMNBaseElement visit(TInputClause  element, C context) {
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
    public DMNBaseElement visit(TOutputClause  element, C context) {
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
    public DMNBaseElement visit(TRuleAnnotationClause  element, C context) {
        return element;
    }

    @Override
    public DMNBaseElement visit(TDecisionRule  element, C context) {
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
    public DMNBaseElement visit(TRuleAnnotation  element, C context) {
        return element;
    }

    @Override
    public DMNBaseElement visit(TFunctionDefinition  element, C context) {
        visitTExpressionProperties(element, context);
        for (TInformationItem informationItem : element.getFormalParameter()) {
            informationItem.accept(this, context);
        }
        visitTExpression(element.getExpression(), context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TInvocation  element, C context) {
        visitTExpressionProperties(element, context);
        visitTExpression(element.getExpression(), context);
        for (TBinding binding : element.getBinding()) {
            binding.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TBinding  element, C context) {
        TInformationItem parameter = element.getParameter();
        if (parameter != null) {
            parameter.accept(this, context);
        }
        visitTExpression(element.getExpression(), context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TList  element, C context) {
        visitTExpressionProperties(element, context);
        for (TExpression expression : element.getExpression()) {
            visitTExpression(expression, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TLiteralExpression  element, C context) {
        visitTExpressionProperties(element, context);
        TImportedValues importedValues = element.getImportedValues();
        if (importedValues != null) {
            importedValues.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TRelation  element, C context) {
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
    public DMNBaseElement visit(TUnaryTests  element, C context) {
        visitTExpressionProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TConditional  element, C context) {
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
    public DMNBaseElement visit(TFor  element, C context) {
        visitTExpressionProperties(element, context);
        TChildExpression inExp = element.getIn();
        if (inExp != null) {
            inExp.accept(this, context);
        }
        TChildExpression returnExp = element.getReturn();
        if (returnExp != null) {
            returnExp.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TFilter  element, C context) {
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
    public DMNBaseElement visit(TEvery  element, C context) {
        visitTExpressionProperties(element, context);
        visitTQuantifiedProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TSome  element, C context) {
        visitTExpressionProperties(element, context);
        visitTQuantifiedProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TChildExpression  element, C context) {
        TExpression exp = element.getExpression();
        if (exp != null) {
            visitTExpression(exp, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TTypedChildExpression  element, C context) {
        TExpression exp = element.getExpression();
        if (exp != null) {
            visitTExpression(exp, context);
        }
        return element;
    }

    private void visitTQuantifiedProperties(TQuantified element, C context) {
        TChildExpression inExp = element.getIn();
        if (inExp != null) {
            inExp.accept(this, context);
        }
        TChildExpression satisfiesExp = element.getSatisfies();
        if (satisfiesExp != null) {
            satisfiesExp.accept(this, context);
        }
    }

    // Requirements
    @Override
    public DMNBaseElement visit(TAuthorityRequirement  element, C context) {
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
    public DMNBaseElement visit(TInformationRequirement  element, C context) {
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
    public DMNBaseElement visit(TKnowledgeRequirement  element, C context) {
        visitTDMNElementProperties(element, context);
        TDMNElementReference requiredKnowledge = element.getRequiredKnowledge();
        if (requiredKnowledge != null) {
            requiredKnowledge.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TInformationItem  element, C context) {
        visitTNamedElementProperties(element, context);
        element.setTypeRef(visitTypeRef(element.getTypeRef(), context));
        return element;
    }

    @Override
    public DMNBaseElement visit(TDMNElementReference  element, C context) {
        return element;
    }

    // Artifacts
    @Override
    public DMNBaseElement visit(TAssociation  element, C context) {
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
    public DMNBaseElement visit(TGroup  element, C context) {
        visitTDMNElementProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TTextAnnotation  element, C context) {
        visitTDMNElementProperties(element, context);
        return element;
    }

    // Other
    @Override
    public DMNBaseElement visit(TBusinessContextElement  element, C context) {
        visitTNamedElementProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(TPerformanceIndicator  element, C context) {
        visitTNamedElementProperties(element, context);
        for (TDMNElementReference  elementReference : element.getImpactingDecision()) {
            elementReference.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TOrganizationUnit  element, C context) {
        visitTNamedElementProperties(element, context);
        for (TDMNElementReference  elementReference : element.getDecisionMade()) {
            elementReference.accept(this, context);
        }
        for (TDMNElementReference  elementReference : element.getDecisionOwned()) {
            elementReference.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(TElementCollection  element, C context) {
        visitTNamedElementProperties(element, context);
        for (TDMNElementReference  elementReference : element.getDrgElement()) {
            elementReference.accept(this, context);
        }
        return element;
    }

    // Extensions
    @Override
    public DMNBaseElement visit(TDMNElement.ExtensionElements element, C context) {
        return visitExtensions(element, context);
    }

    //
    // DMNDI elements
    //
    @Override
    public DMNBaseElement visit(DMNDI  element, C context) {
        for (DMNDiagram diagram : element.getDMNDiagram()) {
            diagram.accept(this, context);
        }
        for (DMNStyle style : element.getDMNStyle()) {
            style.accept(this, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(DMNDiagram  element, C context) {
        visitDiagramElementProperties(element, context);
        Dimension size = element.getSize();
        if (size != null) {
            size.accept(this, context);
        }
        for (DiagramElement diagramElement : element.getDMNDiagramElement()) {
            visitDiagramElement(diagramElement, context);
        }
        return element;
    }

    @Override
    public DMNBaseElement visit(DMNShape  element, C context) {
        visitDiagramElementProperties(element, context);
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
    public DMNBaseElement visit(DMNEdge  element, C context) {
        visitEdgeProperties(element, context);
        DMNLabel dmnLabel = element.getDMNLabel();
        if (dmnLabel != null) {
            dmnLabel.accept(this, context);
        }
        element.setDmnElementRef(visitQName(element.getDmnElementRef(), context));
        element.setSourceElement(visitQName(element.getSourceElement(), context));
        element.setTargetElement(visitQName(element.getTargetElement(), context));
        return element;
    }

    @Override
    public DMNBaseElement visit(DMNStyle  element, C context) {
        visitExtension(element.getExtension(), context);
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
    public DMNBaseElement visit(DMNLabel  element, C context) {
        visitShapeProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(DMNDecisionServiceDividerLine  element, C context) {
        visitEdgeProperties(element, context);
        return element;
    }

    @Override
    public DMNBaseElement visit(Color  element, C context) {
        return element;
    }

    @Override
    public DMNBaseElement visit(Point  element, C context) {
        return element;
    }

    @Override
    public DMNBaseElement visit(Bounds  element, C context) {
        return element;
    }

    @Override
    public DMNBaseElement visit(Dimension  element, C context) {
        return element;
    }

    @Override
    public DMNBaseElement visit(DiagramElement.Extension element, C context) {
        return visitExtension(element, context);
    }

    @Override
    public Object visit(Style.Extension element, C context) {
        return visitExtension(element, context);
    }

    private void visitTDMNElementProperties(TDMNElement  element, C context) {
        visitExtensions(element.getExtensionElements(), context);
    }

    private void visitTArtifact(TArtifact  element, C context) {
        visitTDMNElementProperties(element, context);
        if (element instanceof TAssociation association) {
            association.accept(this, context);
        } else if (element instanceof TGroup group) {
            group.accept(this, context);
        } else if (element instanceof TTextAnnotation annotation) {
            annotation.accept(this, context);
        }
    }

    private void visitTExpressionProperties(TExpression  element, C context) {
        visitTDMNElementProperties(element, context);
        element.setTypeRef(visitTypeRef(element.getTypeRef(), context));
    }

    private void visitTExpression(TExpression  element, C context) {
        if (element == null) {
            return;
        }

        visitTExpressionProperties(element, context);
        if (element instanceof TContext tContext) {
            tContext.accept(this, context);
        } else if (element instanceof TDecisionTable table) {
            table.accept(this, context);
        } else if (element instanceof TFunctionDefinition definition) {
            definition.accept(this, context);
        } else if (element instanceof TInvocation invocation) {
            invocation.accept(this, context);
        } else if (element instanceof TList list) {
            list.accept(this, context);
        } else if (element instanceof TLiteralExpression expression) {
            expression.accept(this, context);
        } else if (element instanceof TRelation relation) {
            relation.accept(this, context);
        } else if (element instanceof TUnaryTests tests) {
            tests.accept(this, context);
        } else if (element instanceof TConditional conditional) {
            conditional.accept(this, context);
        } else if (element instanceof TFor for1) {
            for1.accept(this, context);
        } else if (element instanceof TFilter filter) {
            filter.accept(this, context);
        } else if (element instanceof TEvery every) {
            every.accept(this, context);
        } else if (element instanceof TSome some) {
            some.accept(this, context);
        }
    }

    private void visitTNamedElementProperties(TNamedElement  element, C context) {
        visitTDMNElementProperties(element, context);
    }

    private void visitTDRGElement(TDRGElement  element, C context) {
        if (element instanceof TInputData data) {
            data.accept(this, context);
        } else if (element instanceof TDecision decision) {
            decision.accept(this, context);
        } else if (element instanceof TInvocable) {
            visitInvocable(element, context);
        } else if (element instanceof TKnowledgeSource source) {
            source.accept(this, context);
        }
    }

    private void visitInvocable(TDRGElement  element, C context) {
        if (element instanceof TBusinessKnowledgeModel model) {
            model.accept(this, context);
        } else if (element instanceof TDecisionService service) {
            service.accept(this, context);
        }
    }

    protected QName visitTypeRef(QName typeRef, C context) {
        return typeRef;
    }

    protected QName visitQName(QName qName, C context) {
        return qName;
    }

    private DMNBaseElement visitExtensions(TDMNElement.ExtensionElements element, C context) {
        return element;
    }

    private void visitDiagramElementProperties(DiagramElement  element, C context) {
        visitExtension(element.getExtension(), context);
    }

    private void visitDiagramElement(DiagramElement  element, C context) {
        visitDiagramElementProperties(element, context);
        if (element instanceof DMNDiagram diagram) {
            diagram.accept(this, context);
        } else if (element instanceof DMNDecisionServiceDividerLine line) {
            line.accept(this, context);
        } else if (element instanceof DMNEdge edge) {
            edge.accept(this, context);
        } else if (element instanceof DMNLabel label) {
            label.accept(this, context);
        } else if (element instanceof DMNShape shape) {
            shape.accept(this, context);
        }
    }

    private void visitShapeProperties(Shape  element, C context) {
        visitDiagramElementProperties(element, context);
        Bounds bounds = element.getBounds();
        if (bounds != null) {
            bounds.accept(this, context);
        }
    }

    private void visitEdgeProperties(Edge  element, C context) {
        visitDiagramElementProperties(element, context);
        for (Point point : element.getWaypoint()) {
            point.accept(this, context);
        }
    }

    private DMNBaseElement visitExtension(DiagramElement.Extension element, C context) {
        return element;
    }

    private Object visitExtension(Style.Extension element, C context) {
        return element;
    }
}
