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

public class NopVisitor<C, R> extends AbstractVisitor<C, R> {
    public NopVisitor(BuildLogger logger, ErrorHandler errorHandler) {
        super(logger, errorHandler);
    }

    @Override
    public R visit(TDefinitions element, C context) {
        return null;
    }

    @Override
    public R visit(TImport element, C context) {
        return null;
    }

    @Override
    public R visit(TImportedValues element, C context) {
        return null;
    }

    @Override
    public R visit(TItemDefinition element, C context) {
        return null;
    }

    @Override
    public R visit(TFunctionItem element, C context) {
        return null;
    }

    @Override
    public R visit(TInputData element, C context) {
        return null;
    }

    @Override
    public R visit(TDecision element, C context) {
        return null;
    }

    @Override
    public R visit(TBusinessKnowledgeModel element, C context) {
        return null;
    }

    @Override
    public R visit(TDecisionService element, C context) {
        return null;
    }

    @Override
    public R visit(TKnowledgeSource element, C context) {
        return null;
    }

    @Override
    public R visit(TContext element, C context) {
        return null;
    }

    @Override
    public R visit(TContextEntry element, C context) {
        return null;
    }

    @Override
    public R visit(TDecisionTable element, C context) {
        return null;
    }

    @Override
    public R visit(TInputClause element, C context) {
        return null;
    }

    @Override
    public R visit(TOutputClause element, C context) {
        return null;
    }

    @Override
    public R visit(TRuleAnnotationClause element, C context) {
        return null;
    }

    @Override
    public R visit(TDecisionRule element, C context) {
        return null;
    }

    @Override
    public R visit(TRuleAnnotation element, C context) {
        return null;
    }

    @Override
    public R visit(TFunctionDefinition element, C context) {
        return null;
    }

    @Override
    public R visit(TInvocation element, C context) {
        return null;
    }

    @Override
    public R visit(TBinding element, C context) {
        return null;
    }

    @Override
    public R visit(TList element, C context) {
        return null;
    }

    @Override
    public R visit(TLiteralExpression element, C context) {
        return null;
    }

    @Override
    public R visit(TRelation element, C context) {
        return null;
    }

    @Override
    public R visit(TUnaryTests element, C context) {
        return null;
    }

    @Override
    public R visit(TConditional element, C context) {
        return null;
    }

    @Override
    public R visit(TFor element, C context) {
        return null;
    }

    @Override
    public R visit(TFilter element, C context) {
        return null;
    }

    @Override
    public R visit(TEvery element, C context) {
        return null;
    }

    @Override
    public R visit(TSome element, C context) {
        return null;
    }

    @Override
    public R visit(TChildExpression element, C context) {
        return null;
    }

    @Override
    public R visit(TTypedChildExpression element, C context) {
        return null;
    }

    @Override
    public R visit(TAuthorityRequirement element, C context) {
        return null;
    }

    @Override
    public R visit(TInformationRequirement element, C context) {
        return null;
    }

    @Override
    public R visit(TKnowledgeRequirement element, C context) {
        return null;
    }

    @Override
    public R visit(TInformationItem element, C context) {
        return null;
    }

    @Override
    public R visit(TDMNElementReference element, C context) {
        return null;
    }

    @Override
    public R visit(TAssociation element, C context) {
        return null;
    }

    @Override
    public R visit(TGroup element, C context) {
        return null;
    }

    @Override
    public R visit(TTextAnnotation element, C context) {
        return null;
    }

    @Override
    public R visit(TBusinessContextElement element, C context) {
        return null;
    }

    @Override
    public R visit(TPerformanceIndicator element, C context) {
        return null;
    }

    @Override
    public R visit(TOrganizationUnit element, C context) {
        return null;
    }

    @Override
    public R visit(TElementCollection element, C context) {
        return null;
    }

    @Override
    public R visit(TDMNElement.ExtensionElements element, C context) {
        return null;
    }

    @Override
    public R visit(DMNDI element, C context) {
        return null;
    }

    @Override
    public R visit(DMNDiagram element, C context) {
        return null;
    }

    @Override
    public R visit(DMNShape element, C context) {
        return null;
    }

    @Override
    public R visit(DMNEdge element, C context) {
        return null;
    }

    @Override
    public R visit(DMNStyle element, C context) {
        return null;
    }

    @Override
    public R visit(Style.IDREFStubStyle element, C context) {
        return null;
    }

    @Override
    public R visit(DMNLabel element, C context) {
        return null;
    }

    @Override
    public R visit(DMNDecisionServiceDividerLine element, C context) {
        return null;
    }

    @Override
    public R visit(Color element, C context) {
        return null;
    }

    @Override
    public R visit(Point element, C context) {
        return null;
    }

    @Override
    public R visit(Bounds element, C context) {
        return null;
    }

    @Override
    public R visit(Dimension element, C context) {
        return null;
    }

    @Override
    public R visit(DiagramElement.Extension element, C context) {
        return null;
    }

    @Override
    public R visit(Style.Extension element, C context) {
        return null;
    }
}

