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
import com.gs.dmn.context.DMNContext;

public interface Visitor {
    //
    // DMN Elements
    //
    Object visit(TDefinitions element, DMNContext context);

    Object visit(TImport element, DMNContext context);

    Object visit(TElementCollection element, DMNContext context);

    Object visit(TDecision element, DMNContext context);

    Object visit(TBusinessContextElement element, DMNContext context);

    Object visit(TPerformanceIndicator element, DMNContext context);

    Object visit(TOrganizationUnit element, DMNContext context);

    Object visit(TBusinessKnowledgeModel element, DMNContext context);

    Object visit(TInputData element, DMNContext context);

    Object visit(TKnowledgeSource element, DMNContext context);

    Object visit(TInformationRequirement element, DMNContext context);

    Object visit(TKnowledgeRequirement element, DMNContext context);

    Object visit(TAuthorityRequirement element, DMNContext context);

    Object visit(TItemDefinition element, DMNContext context);

    Object visit(TFunctionItem element, DMNContext context);

    Object visit(TLiteralExpression element, DMNContext context);

    Object visit(TInvocation element, DMNContext context);

    Object visit(TInformationItem element, DMNContext context);

    Object visit(TDecisionTable element, DMNContext context);

    Object visit(TGroup element, DMNContext context);

    Object visit(TTextAnnotation element, DMNContext context);

    Object visit(TAssociation element, DMNContext context);

    Object visit(TContext element, DMNContext context);

    Object visit(TContextEntry element, DMNContext context);

    Object visit(TFunctionDefinition element, DMNContext context);

    Object visit(TRelation element, DMNContext context);

    Object visit(TList element, DMNContext context);

    Object visit(TDecisionService element, DMNContext context);

    Object visit(TDMNElementReference element, DMNContext context);

    Object visit(TBinding element, DMNContext context);

    Object visit(TInputClause element, DMNContext context);

    Object visit(TOutputClause element, DMNContext context);

    Object visit(TRuleAnnotationClause element, DMNContext context);

    Object visit(TDecisionRule element, DMNContext context);

    Object visit(TRuleAnnotation element, DMNContext context);

    Object visit(TImportedValues element, DMNContext context);

    Object visit(TUnaryTests element, DMNContext context);

    Object visit(TDMNElement.ExtensionElements element, DMNContext context);

    //
    // DMNDI elements
    //
    Object visit(DMNDI element, DMNContext context);

    Object visit(DMNDiagram element, DMNContext context);

    Object visit(DMNShape element, DMNContext context);

    Object visit(DMNEdge element, DMNContext context);

    Object visit(DMNStyle element, DMNContext context);

    Object visit(DMNLabel element, DMNContext context);

    Object visit(DMNDecisionServiceDividerLine element, DMNContext context);

    Object visit(Color element, DMNContext context);

    Object visit(Point element, DMNContext context);

    Object visit(Bounds element, DMNContext context);

    Object visit(Dimension element, DMNContext context);

    Object visit(DiagramElement.Extension element, DMNContext context);

    Object visit(Style.Extension element, DMNContext context);
}
