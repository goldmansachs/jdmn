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
package com.gs.dmn.transformation.lazy;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.ast.*;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.transformation.InputParameters;

public abstract class DecisionTableLazyEvaluationDetector extends SimpleLazyEvaluationDetector {
    public DecisionTableLazyEvaluationDetector(InputParameters inputParameters, BuildLogger logger) {
        super(inputParameters, logger);
    }

    @Override
    public LazyEvaluationOptimisation detect(DMNModelRepository modelRepository) {
        LazyEvaluationOptimisation lazyEvaluationOptimisation = new LazyEvaluationOptimisation();

        logger.info("Scanning for decisions ...");

        for (TDecision decision : modelRepository.findAllDecisions()) {
            TExpression expression = decision.getExpression();
            if (modelRepository.isDecisionTableExpression(decision) && applicable(decision, expression)) {
                logger.info(String.format("Found candidate parent decision '%s'", modelRepository.qualifiedName(decision)));

                for (DRGElementReference<TDecision> reference : modelRepository.directSubDecisions(decision)) {
                    lazyEvaluationOptimisation.addLazyEvaluatedDecision(modelRepository.lazyEvaluationKey(reference.getElement()));
                }
            }
        }

        logger.info(String.format("Decisions to be lazy evaluated: '%s'", String.join(", ", lazyEvaluationOptimisation.getLazyEvaluatedDecisions())));

        return lazyEvaluationOptimisation;
    }

    protected abstract boolean applicable(TDecision decision, TExpression expression);
}
