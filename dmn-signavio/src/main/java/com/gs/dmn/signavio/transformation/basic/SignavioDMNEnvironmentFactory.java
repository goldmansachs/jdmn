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

import com.gs.dmn.feel.analysis.semantics.environment.Declaration;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import com.gs.dmn.transformation.basic.StandardDMNEnvironmentFactory;
import org.omg.spec.dmn._20180521.model.*;

public class SignavioDMNEnvironmentFactory extends StandardDMNEnvironmentFactory {
    private final SignavioDMNModelRepository dmnModelRepository;

    public SignavioDMNEnvironmentFactory(BasicDMNToNativeTransformer basicDMNToNativeTransformer) {
        super(basicDMNToNativeTransformer);
        this.dmnModelRepository = (SignavioDMNModelRepository) super.dmnModelRepository;
    }

    @Override
    public Declaration makeDeclaration(TDRGElement parent, Environment parentEnvironment, TDRGElement child) {
        TDefinitions childModel = this.dmnModelRepository.getModel(child);
        Declaration declaration;
        if (child instanceof TInputData) {
            declaration = makeVariableDeclaration(child, ((TInputData) child).getVariable());
        } else if (child instanceof TBusinessKnowledgeModel) {
            TBusinessKnowledgeModel bkm = (TBusinessKnowledgeModel) child;
            if (this.dmnModelRepository.isBKMLinkedToDecision(bkm)) {
                TDecision outputDecision = this.dmnModelRepository.getOutputDecision(bkm);
                declaration = makeVariableDeclaration(child, outputDecision.getVariable());
            } else {
                TFunctionDefinition functionDefinition = bkm.getEncapsulatedLogic();
                functionDefinition.getFormalParameter().forEach(
                        p -> parentEnvironment.addDeclaration(this.environmentFactory.makeVariableDeclaration(p.getName(), this.dmnTransformer.toFEELType(childModel, QualifiedName.toQualifiedName(childModel, p.getTypeRef())))));
                declaration = makeInvocableDeclaration(bkm);
            }
        } else if (child instanceof TDecision) {
            declaration = makeVariableDeclaration(child, ((TDecision) child).getVariable());
        } else if (child instanceof TDecisionService) {
            declaration = makeInvocableDeclaration((TDecisionService) child);
        } else {
            throw new UnsupportedOperationException(String.format("'%s' is not supported yet", child.getClass().getSimpleName()));
        }
        return declaration;
    }
}
