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
package com.gs.dmn.signavio.transformation.basic;

import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.context.environment.Environment;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.semantics.type.FEELFunctionType;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.extension.MultiInstanceDecisionLogic;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.StandardDMNEnvironmentFactory;

public class SignavioDMNEnvironmentFactory extends StandardDMNEnvironmentFactory {
    private final SignavioDMNModelRepository dmnModelRepository;

    public SignavioDMNEnvironmentFactory(BasicDMNToNativeTransformer<Type, DMNContext> basicDMNToNativeTransformer) {
        super(basicDMNToNativeTransformer);
        this.dmnModelRepository = (SignavioDMNModelRepository) super.dmnModelRepository;
    }

    @Override
    public Type drgElementOutputFEELType(TDRGElement element) {
        if (this.dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = this.dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            return super.drgElementOutputFEELType(outputDecision);
        } else if (element instanceof TDecision && this.dmnModelRepository.isFreeTextLiteralExpression(element)) {
            Expression<Type> feelExpression = analyzeExpression(element);
            if (feelExpression instanceof FunctionDefinition) {
                Type type = feelExpression.getType();
                if (type instanceof FEELFunctionType functionType) {
                    type = functionType.getReturnType();
                }
                return type;
            }
            return feelExpression.getType();
        } else {
            return super.drgElementOutputFEELType(element);
        }
    }

    @Override
    public Type drgElementOutputFEELType(TDRGElement element, DMNContext context) {
        if (this.dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = this.dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            return super.drgElementOutputFEELType(outputDecision);
        } else if (element instanceof TDecision && this.dmnModelRepository.isFreeTextLiteralExpression(element)) {
            Expression<Type> feelExpression = analyzeExpression(element);
            if (feelExpression instanceof FunctionDefinition) {
                if (((FunctionDefinition<Type>) feelExpression).isExternal()) {
                    Expression<Type> body = ((FunctionDefinition<Type>) feelExpression).getBody();
                    return externalFunctionReturnFEELType(element, body);
                } else {
                    Type type = feelExpression.getType();
                    if (type instanceof FEELFunctionType functionType) {
                        type = functionType.getReturnType();
                    }
                    return type;
                }
            }
            return feelExpression.getType();
        } else {
            return super.drgElementOutputFEELType(element, context);
        }
    }

    private Expression<Type> analyzeExpression(TDRGElement element) {
        TLiteralExpression expression = (TLiteralExpression) this.dmnModelRepository.expression(element);
        DMNContext globalContext = this.dmnTransformer.makeGlobalContext(element);
        return this.feelTranslator.analyzeExpression(expression.getText(), globalContext);
    }

    @Override
    public Type expressionType(TDRGElement element, TExpression expression, DMNContext context) {
        if (this.dmnModelRepository.isMultiInstanceDecision(element)) {
            TDecision decision = (TDecision) element;
            MultiInstanceDecisionLogic multiInstanceDecision = ((BasicSignavioDMNToJavaTransformer) this.dmnTransformer).multiInstanceDecisionLogic(decision);
            TDecision topLevelDecision = multiInstanceDecision.getTopLevelDecision();
            return super.drgElementVariableFEELType(topLevelDecision);
        } else if (this.dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = this.dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            return super.drgElementVariableFEELType(outputDecision);
        } else {
            return super.expressionType(element, expression, context);
        }
    }

    @Override
    public Declaration makeDeclaration(TDRGElement parent, Environment parentEnvironment, TDRGElement child) {
        TDefinitions childModel = this.dmnModelRepository.getModel(child);
        Declaration declaration;
        if (child instanceof TInputData data) {
            declaration = makeVariableDeclaration(child, data.getVariable());
        } else if (child instanceof TBusinessKnowledgeModel bkm) {
            if (this.dmnModelRepository.isBKMLinkedToDecision(bkm)) {
                TDecision outputDecision = this.dmnModelRepository.getOutputDecision(bkm);
                declaration = makeVariableDeclaration(child, outputDecision.getVariable());
            } else {
                TFunctionDefinition functionDefinition = bkm.getEncapsulatedLogic();
                functionDefinition.getFormalParameter().forEach(
                        p -> parentEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(p.getName(), this.dmnTransformer.toFEELType(childModel, QualifiedName.toQualifiedName(childModel, p.getTypeRef())))));
                declaration = makeInvocableDeclaration(bkm);
            }
        } else if (child instanceof TDecision decision) {
            declaration = makeVariableDeclaration(child, decision.getVariable());
        } else if (child instanceof TDecisionService service) {
            declaration = makeInvocableDeclaration(service);
        } else {
            throw new UnsupportedOperationException("'%s' is not supported yet".formatted(child.getClass().getSimpleName()));
        }
        return declaration;
    }
}
