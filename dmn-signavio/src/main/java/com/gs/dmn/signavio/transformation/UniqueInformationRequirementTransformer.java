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
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.transformation.SimpleDMNTransformer;
import org.omg.spec.dmn._20180521.model.TDMNElementReference;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.omg.spec.dmn._20180521.model.TInformationRequirement;
import org.omg.spec.dmn._20180521.model.TInputData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UniqueInformationRequirementTransformer extends SimpleDMNTransformer<TestLab> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UniqueInformationRequirementTransformer.class);

    private final BuildLogger logger;
    private Map<String, Pair<TInputData, List<TInputData>>> inputDataClasses;

    public UniqueInformationRequirementTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public UniqueInformationRequirementTransformer(BuildLogger logger) {
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
        for(TDecision decision: repository.decisions()) {
            List<String> hrefs = new ArrayList<>();
            List<TInformationRequirement> newList = new ArrayList<>();
            for(TInformationRequirement ir: decision.getInformationRequirement()) {
                String href = null;
                TDMNElementReference requiredInput = ir.getRequiredInput();
                TDMNElementReference requiredDecision = ir.getRequiredDecision();
                if (requiredInput != null) {
                    href = requiredInput.getHref();
                }
                if (requiredDecision != null) {
                    href = requiredDecision.getHref();
                }
                if (!hrefs.contains(href)) {
                    newList.add(ir);
                    hrefs.add(href);
                }
            }
            decision.getInformationRequirement().clear();
            decision.getInformationRequirement().addAll(newList);
        }

        return repository;
    }
}
