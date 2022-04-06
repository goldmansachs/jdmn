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

public interface Visitor<C> {
    //
    // DMN Elements
    //
    Object visit(TDefinitions<C> element, C context);

    Object visit(TImport<C> element, C context);

    Object visit(TElementCollection<C> element, C context);

    Object visit(TDecision<C> element, C context);

    Object visit(TBusinessContextElement<C> element, C context);

    Object visit(TPerformanceIndicator<C> element, C context);

    Object visit(TOrganizationUnit<C> element, C context);

    Object visit(TBusinessKnowledgeModel<C> element, C context);

    Object visit(TInputData<C> element, C context);

    Object visit(TKnowledgeSource<C> element, C context);

    Object visit(TInformationRequirement<C> element, C context);

    Object visit(TKnowledgeRequirement<C> element, C context);

    Object visit(TAuthorityRequirement<C> element, C context);

    Object visit(TItemDefinition<C> element, C context);

    Object visit(TFunctionItem<C> element, C context);

    Object visit(TLiteralExpression<C> element, C context);

    Object visit(TInvocation<C> element, C context);

    Object visit(TInformationItem<C> element, C context);

    Object visit(TDecisionTable<C> element, C context);

    Object visit(TGroup<C> element, C context);

    Object visit(TTextAnnotation<C> element, C context);

    Object visit(TAssociation<C> element, C context);

    Object visit(TContext<C> element, C context);

    Object visit(TContextEntry<C> element, C context);

    Object visit(TFunctionDefinition<C> element, C context);

    Object visit(TRelation<C> element, C context);

    Object visit(TList<C> element, C context);

    Object visit(TDecisionService<C> element, C context);

    Object visit(TDMNElementReference<C> element, C context);

    Object visit(TBinding<C> element, C context);

    Object visit(TInputClause<C> element, C context);

    Object visit(TOutputClause<C> element, C context);

    Object visit(TRuleAnnotationClause<C> element, C context);

    Object visit(TDecisionRule<C> element, C context);

    Object visit(TRuleAnnotation<C> element, C context);

    Object visit(TImportedValues<C> element, C context);

    Object visit(TUnaryTests<C> element, C context);

    Object visit(TDMNElement.ExtensionElements element, C context);

    //
    // DMNDI<C> elements
    //
    Object visit(DMNDI<C> element, C context);

    Object visit(DMNDiagram<C> element, C context);

    Object visit(DMNShape<C> element, C context);

    Object visit(DMNEdge<C> element, C context);

    Object visit(DMNStyle<C> element, C context);

    Object visit(DMNLabel<C> element, C context);

    Object visit(DMNDecisionServiceDividerLine<C> element, C context);

    Object visit(Color<C> element, C context);

    Object visit(Point<C> element, C context);

    Object visit(Bounds<C> element, C context);

    Object visit(Dimension<C> element, C context);

    Object visit(DiagramElement.Extension element, C context);

    Object visit(Style.Extension element, C context);
}
