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

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.FEELFunctionType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.StandardFEELTypeFactory;
import org.omg.spec.dmn._20180521.model.*;

public class SignavioFEELTypeFactory extends StandardFEELTypeFactory {
    private final SignavioDMNModelRepository dmnModelRepository;

    public SignavioFEELTypeFactory(BasicDMNToNativeTransformer dmnTransformer) {
        super(dmnTransformer);
        this.dmnModelRepository = (SignavioDMNModelRepository) super.dmnModelRepository;
    }

    @Override
    public Type drgElementOutputFEELType(TDRGElement element) {
        if (this.dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = this.dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            return super.drgElementOutputFEELType(outputDecision);
        } else if (element instanceof TDecision && this.dmnModelRepository.isFreeTextLiteralExpression(element)) {
            Expression feelExpression = analyzeExpression(element);
            if (feelExpression instanceof FunctionDefinition) {
                if (((FunctionDefinition) feelExpression).isExternal()) {
                    Expression body = ((FunctionDefinition) feelExpression).getBody();
                    return externalFunctionReturnFEELType(element, body);
                } else {
                    Type type = feelExpression.getType();
                    if (type instanceof FEELFunctionType) {
                        type = ((FEELFunctionType) type).getReturnType();
                    }
                    return type;
                }
            }
            return feelExpression.getType();
        } else {
            return super.drgElementOutputFEELType(element);
        }
    }

    @Override
    public Type drgElementOutputFEELType(TDRGElement element, Environment environment) {
        if (this.dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = this.dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            return super.drgElementOutputFEELType(outputDecision);
        } else if (element instanceof TDecision && this.dmnModelRepository.isFreeTextLiteralExpression(element)) {
            Expression feelExpression = analyzeExpression(element);
            if (feelExpression instanceof FunctionDefinition) {
                if (((FunctionDefinition) feelExpression).isExternal()) {
                    Expression body = ((FunctionDefinition) feelExpression).getBody();
                    return externalFunctionReturnFEELType(element, body);
                } else {
                    Type type = feelExpression.getType();
                    if (type instanceof FEELFunctionType) {
                        type = ((FEELFunctionType) type).getReturnType();
                    }
                    return type;
                }
            }
            return feelExpression.getType();
        } else {
            return super.drgElementOutputFEELType(element, environment);
        }
    }

    private Expression analyzeExpression(TNamedElement element) {
        TLiteralExpression expression = (TLiteralExpression) this.dmnModelRepository.expression(element);
        Environment decisionEnvironment = this.dmnEnvironmentFactory.makeEnvironment((TDecision) element);
        return this.feelTranslator.analyzeExpression(expression.getText(), FEELContext.makeContext(element, decisionEnvironment));
    }
}
