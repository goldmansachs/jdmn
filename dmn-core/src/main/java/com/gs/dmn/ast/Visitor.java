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

public interface Visitor {
    //
    // DMN Elements
    //
    // Definitions
    <C> Object visit(TDefinitions element, C context);

    // Import
    <C> Object visit(TImport element, C context);

    <C> Object visit(TImportedValues element, C context);

    // Data types
    <C> Object visit(TItemDefinition element, C context);

    <C> Object visit(TFunctionItem element, C context);

    // DRG Elements
    <C> Object visit(TInputData element, C context);

    <C> Object visit(TDecision element, C context);

    <C> Object visit(TBusinessKnowledgeModel element, C context);

    <C> Object visit(TDecisionService element, C context);

    <C> Object visit(TKnowledgeSource element, C context);

    // Expressions
    <C> Object visit(TContext element, C context);

    <C> Object visit(TContextEntry element, C context);

    <C> Object visit(TDecisionTable element, C context);

    <C> Object visit(TInputClause element, C context);

    <C> Object visit(TOutputClause element, C context);

    <C> Object visit(TRuleAnnotationClause element, C context);

    <C> Object visit(TDecisionRule element, C context);

    <C> Object visit(TRuleAnnotation element, C context);

    <C> Object visit(TFunctionDefinition element, C context);

    <C> Object visit(TInvocation element, C context);

    <C> Object visit(TBinding element, C context);

    <C> Object visit(TList element, C context);

    <C> Object visit(TLiteralExpression element, C context);

    <C> Object visit(TRelation element, C context);

    <C> Object visit(TUnaryTests element, C context);

    <C> Object visit(TConditional element, C context);

    <C> Object visit(TFor element, C context);

    <C> Object visit(TFilter element, C context);

    <C> Object visit(TEvery element, C context);

    <C> Object visit(TSome element, C context);

    <C> Object visit(TChildExpression element, C context);

    <C> Object visit(TTypedChildExpression element, C context);

    // Requirements
    <C> Object visit(TAuthorityRequirement element, C context);

    <C> Object visit(TInformationRequirement element, C context);

    <C> Object visit(TKnowledgeRequirement element, C context);

    <C> Object visit(TInformationItem element, C context);

    <C> Object visit(TDMNElementReference element, C context);

    // Artifacts
    <C> Object visit(TAssociation element, C context);

    <C> Object visit(TGroup element, C context);

    <C> Object visit(TTextAnnotation element, C context);

    // Other
    <C> Object visit(TBusinessContextElement element, C context);

    <C> Object visit(TPerformanceIndicator element, C context);

    <C> Object visit(TOrganizationUnit element, C context);

    <C> Object visit(TElementCollection element, C context);

    // Extensions
    <C> Object visit(TDMNElement.ExtensionElements element, C context);

    //
    // DMNDI elements
    //
    <C> Object visit(DMNDI element, C context);

    <C> Object visit(DMNDiagram element, C context);

    <C> Object visit(DMNShape element, C context);

    <C> Object visit(DMNEdge element, C context);

    <C> Object visit(DMNStyle element, C context);

    <C> Object visit(DMNLabel element, C context);

    <C> Object visit(DMNDecisionServiceDividerLine element, C context);

    <C> Object visit(Color element, C context);

    <C> Object visit(Point element, C context);

    <C> Object visit(Bounds element, C context);

    <C> Object visit(Dimension element, C context);

    <C> Object visit(DiagramElement.Extension element, C context);

    <C> Object visit(Style.Extension element, C context);
}
