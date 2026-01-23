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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.dialect.JavaTimeSignavioDMNDialectDefinition;
import com.gs.dmn.signavio.extension.MultiInstanceDecisionLogic;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.SimpleDMNTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.apache.commons.lang3.StringUtils;

import javax.xml.namespace.QName;
import java.util.LinkedHashMap;
import java.util.List;

public class SimplifyTypesForMIDTransformer extends SimpleDMNTransformer<TestLab> {
    private static final DMNDialectDefinition<?, ?, ?, ?, ?, ?> SIGNAVIO_DIALECT = new JavaTimeSignavioDMNDialectDefinition();

    public SimplifyTypesForMIDTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public SimplifyTypesForMIDTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; transformer will not run");
            return repository;
        }

        this.transformRepository = false;
        return removeDuplicateInformationRequirements(repository, logger);
    }

    @Override
    public Pair<DMNModelRepository, List<TestLab>> transform(DMNModelRepository repository, List<TestLab> testCasesList) {
        if (isEmpty(repository, testCasesList)) {
            logger.warn("DMN repository or test cases list is empty; transformer will not run");
            return new Pair<>(repository, testCasesList);
        }

        // Transform model
        if (transformRepository) {
            transform(repository);
        }

        return new Pair<>(repository, testCasesList);
    }

    private DMNModelRepository removeDuplicateInformationRequirements(DMNModelRepository repository, BuildLogger logger) {
        BasicDMNToNativeTransformer<Type, DMNContext> basicTransformer = SIGNAVIO_DIALECT.createBasicTransformer(repository, new NopLazyEvaluationDetector(), makeInputParameters());
        SignavioDMNModelRepository signavioRepository;
        if (repository instanceof SignavioDMNModelRepository) {
            signavioRepository = (SignavioDMNModelRepository) repository;
        } else {
            TDefinitions definitions = repository.getRootDefinitions();
            signavioRepository = new SignavioDMNModelRepository(definitions);
        }
        for (TDefinitions definitions: signavioRepository.getAllDefinitions()) {
            for (TDecision decision: signavioRepository.findDecisions(definitions)) {
                TDefinitions decisionModel = repository.getModel(decision);
                if (signavioRepository.isMultiInstanceDecision(decision)) {
                    MultiInstanceDecisionLogic midLogic = signavioRepository.getExtension().multiInstanceDecisionLogic(decision);
                    TDecision bodyDecision = midLogic.getTopLevelDecision();
                    TDefinitions bodyDecisionModel = repository.getModel(bodyDecision);
                    QualifiedName midDecisionTypeRef = signavioRepository.outputTypeRef(decisionModel, decision);
                    QualifiedName bodyDecisionTypeRef = signavioRepository.outputTypeRef(bodyDecisionModel, bodyDecision);
                    Type midType = basicTransformer.toFEELType(decisionModel, midDecisionTypeRef);
                    Type bodyDecisionType = basicTransformer.toFEELType(bodyDecisionModel, bodyDecisionTypeRef);
                    if (midType instanceof ListType) {
                        Type midElementType = ((ListType) midType).getElementType();
                        if (com.gs.dmn.el.analysis.semantics.type.Type.equivalentTo(midElementType, bodyDecisionType)
                                && (basicTransformer.isComplexType(bodyDecisionType) || basicTransformer.isListOfComplexType(bodyDecisionType))) {
                            TItemDefinition midItemDefinitionType = signavioRepository.lookupItemDefinition(decisionModel, midDecisionTypeRef);
                            String importName = bodyDecisionTypeRef.getNamespace();
                            if (StringUtils.isEmpty(importName)) {
                                midItemDefinitionType.setTypeRef(new QName(String.format("%s", bodyDecisionTypeRef.getLocalPart())));
                            } else {
                                midItemDefinitionType.setTypeRef(new QName(String.format("%s.%s", importName, bodyDecisionTypeRef.getLocalPart())));
                            }
                            midItemDefinitionType.getItemComponent().clear();
                        }
                    }
                }
            }
        }

        return repository;
    }

    private InputParameters makeInputParameters() {
        return new InputParameters(new LinkedHashMap<>());
    }
}
