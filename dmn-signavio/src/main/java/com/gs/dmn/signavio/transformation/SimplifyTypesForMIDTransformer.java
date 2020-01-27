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
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.dialect.SignavioDMNDialectDefinition;
import com.gs.dmn.signavio.extension.MultiInstanceDecisionLogic;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.SimpleDMNTransformer;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.omg.spec.dmn._20180521.model.TDefinitions;
import org.omg.spec.dmn._20180521.model.TInputData;
import org.omg.spec.dmn._20180521.model.TItemDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SimplifyTypesForMIDTransformer extends SimpleDMNTransformer<TestLab> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimplifyTypesForMIDTransformer.class);
    private static final DMNDialectDefinition SIGNAVIO_DIALECT = new SignavioDMNDialectDefinition();

    private final BuildLogger logger;
    private Map<String, Pair<TInputData, List<TInputData>>> inputDataClasses;

    public SimplifyTypesForMIDTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public SimplifyTypesForMIDTransformer(BuildLogger logger) {
        this.logger = logger;
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        this.inputDataClasses = new LinkedHashMap<>();

        return removeDuplicateInformationRequirements(repository, logger);
    }

    @Override
    public Pair<DMNModelRepository, TestLab> transform(DMNModelRepository repository, TestLab testLab) {
        if (inputDataClasses == null) {
            transform(repository);
        }

        return new Pair<>(repository, testLab);
    }

    private DMNModelRepository removeDuplicateInformationRequirements(DMNModelRepository repository, BuildLogger logger) {
        BasicDMN2JavaTransformer basicTransformer = SIGNAVIO_DIALECT.createBasicTransformer(repository, new NopLazyEvaluationDetector(), new LinkedHashMap<>());
        SignavioDMNModelRepository signavioRepository;
        if (repository instanceof SignavioDMNModelRepository) {
            signavioRepository = (SignavioDMNModelRepository) repository;
        } else {
            TDefinitions definitions = repository.getRootDefinitions();
            signavioRepository = new SignavioDMNModelRepository(definitions, repository.getPrefixNamespaceMappings());
        }
        for(TDecision decision: signavioRepository.decisions()) {
            if (signavioRepository.isMultiInstanceDecision(decision)) {
                MultiInstanceDecisionLogic midLogic = signavioRepository.getExtension().multiInstanceDecisionLogic(decision);
                TDecision bodyDecision = midLogic.getTopLevelDecision();
                QualifiedName midDecisionTypeRef = signavioRepository.typeRef(decision);
                QualifiedName bodyDecisionTypeRef = signavioRepository.typeRef(bodyDecision);
                Type midType = basicTransformer.toFEELType(midDecisionTypeRef);
                Type bodyDecisionType = basicTransformer.toFEELType(bodyDecisionTypeRef);
                if (midType instanceof ListType) {
                    Type midElementType = ((ListType) midType).getElementType();
                    if (midElementType.equivalentTo(bodyDecisionType) && basicTransformer.isComplexType(bodyDecisionType)) {
                        TItemDefinition midItemDefinitionType = signavioRepository.lookupItemDefinition(midDecisionTypeRef);
                        midItemDefinitionType.setTypeRef(String.format("%s.%s", bodyDecisionTypeRef.getNamespace(), bodyDecisionTypeRef.getLocalPart()));
                        midItemDefinitionType.getItemComponent().clear();
                    }
                }
            }
        }

        return repository;
    }
}
