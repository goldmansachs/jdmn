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
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.transformation.InputParameters;
import org.omg.spec.dmn._20191111.model.*;

import javax.xml.bind.JAXBElement;

public class SparseDecisionDetector extends SimpleLazyEvaluationDetector {
    private final double sparsityThreshold;

    public SparseDecisionDetector() {
        this(new InputParameters(), new Slf4jBuildLogger(LOGGER));
    }

    public SparseDecisionDetector(InputParameters inputParameters, BuildLogger logger) {
        super(inputParameters, logger);
        this.sparsityThreshold = inputParameters.getSparsityThreshold();
    }

    @Override
    public LazyEvaluationOptimisation detect(DMNModelRepository modelRepository) {
        LazyEvaluationOptimisation lazyEvaluationOptimisation = new LazyEvaluationOptimisation();
        logger.info("Scanning for sparse decisions ...");

        for (TDefinitions definitions: modelRepository.getAllDefinitions()) {
            for (TDecision decision : modelRepository.findDecisions(definitions)) {
                JAXBElement<? extends TExpression> element = decision.getExpression();
                TExpression expression = element == null ? null : element.getValue();
                if (expression instanceof TDecisionTable && isSparseDecisionTable((TDecisionTable) expression, sparsityThreshold)) {
                    logger.info(String.format("Found sparse decision '%s'", decision.getName()));

                    for (TInformationRequirement ir : decision.getInformationRequirement()) {
                        TDMNElementReference requiredDecision = ir.getRequiredDecision();
                        if (requiredDecision != null) {
                            String href = requiredDecision.getHref();
                            TDecision drgElement = modelRepository.findDecisionByRef(decision, href);
                            if (drgElement != null) {
                                lazyEvaluationOptimisation.addLazyEvaluatedDecision(drgElement.getName());
                            }
                        }
                    }
                }
            }
        }

        logger.info(String.format("Decisions to be lazy evaluated: '%s'", String.join(", ", lazyEvaluationOptimisation.getLazyEvaluatedDecisions())));
        return lazyEvaluationOptimisation;
    }

    boolean isSparseDecisionTable(TDecisionTable expression, double sparsityThreshold) {
        int columnNo = expression.getInput().size();
        int lineNo = expression.getRule().size();
        int anyMatchCount = 0;
        for(TDecisionRule rule: expression.getRule()) {
            for(TUnaryTests test : rule.getInputEntry()) {
                if ("-".equals(test.getText())) {
                    anyMatchCount++;
                }
            }
        }
        double sparsity = 1.0 * anyMatchCount / (lineNo * columnNo);
        return sparsity >= sparsityThreshold;
    }
}
