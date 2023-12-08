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
import com.gs.dmn.runtime.DMNRuntimeException;

public interface Visitor<C, R> {
    //
    // DMN Elements
    //
    // Definitions
    R visit(TDefinitions element, C context);

    // Import
    R visit(TImport element, C context);

    R visit(TImportedValues element, C context);

    // Data types
    R visit(TItemDefinition element, C context);

    R visit(TFunctionItem element, C context);

    // DRG Elements
    R visit(TInputData element, C context);

    R visit(TDecision element, C context);

    default R visit(TInvocable element, C context) {
        R result;
        if (element == null) {
            result = null;
        } else {
            result = element.accept(this, context);
        }
        return result;
    }

    R visit(TBusinessKnowledgeModel element, C context);

    R visit(TDecisionService element, C context);

    R visit(TKnowledgeSource element, C context);

    // Expressions
    R visit(TContext element, C context);

    R visit(TContextEntry element, C context);

    R visit(TDecisionTable element, C context);

    R visit(TInputClause element, C context);

    R visit(TOutputClause element, C context);

    R visit(TRuleAnnotationClause element, C context);

    R visit(TDecisionRule element, C context);

    R visit(TRuleAnnotation element, C context);

    R visit(TFunctionDefinition element, C context);

    R visit(TInvocation element, C context);

    R visit(TBinding element, C context);

    R visit(TList element, C context);

    default R visit(TExpression element, C context) {
        R result;
        if (element == null) {
            result = null;
        } else {
            result = element.accept(this, context);
        }
        return result;
    }

    R visit(TLiteralExpression element, C context);

    R visit(TRelation element, C context);

    R visit(TUnaryTests element, C context);

    R visit(TConditional element, C context);

    R visit(TFor element, C context);

    R visit(TFilter element, C context);

    R visit(TEvery element, C context);

    R visit(TSome element, C context);

    R visit(TChildExpression element, C context);

    R visit(TTypedChildExpression element, C context);

    // Requirements
    R visit(TAuthorityRequirement element, C context);

    R visit(TInformationRequirement element, C context);

    R visit(TKnowledgeRequirement element, C context);

    R visit(TInformationItem element, C context);

    R visit(TDMNElementReference element, C context);

    // Artifacts
    R visit(TAssociation element, C context);

    R visit(TGroup element, C context);

    R visit(TTextAnnotation element, C context);

    // Other
    R visit(TBusinessContextElement element, C context);

    R visit(TPerformanceIndicator element, C context);

    R visit(TOrganizationUnit element, C context);

    R visit(TElementCollection element, C context);

    // Extensions
    R visit(TDMNElement.ExtensionElements element, C context);

    //
    // DMNDI elements
    //
    R visit(DMNDI element, C context);

    R visit(DMNDiagram element, C context);

    R visit(DMNShape element, C context);

    R visit(DMNEdge element, C context);

    R visit(DMNStyle element, C context);

    R visit(DMNLabel element, C context);

    R visit(DMNDecisionServiceDividerLine element, C context);

    R visit(Color element, C context);

    R visit(Point element, C context);

    R visit(Bounds element, C context);

    R visit(Dimension element, C context);

    R visit(DiagramElement.Extension element, C context);

    R visit(Style.Extension element, C context);
}
