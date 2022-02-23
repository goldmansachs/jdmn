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
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.signavio.transformation.config.Correction;
import com.gs.dmn.signavio.transformation.config.DecisionTableCorrection;
import com.gs.dmn.transformation.SimpleDMNTransformer;
import org.omg.spec.dmn._20191111.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class CorrectPathsInDecisionsTransformer extends SimpleDMNTransformer<TestLab> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(CorrectPathsInDecisionsTransformer.class);

    private static final String CORRECTIONS_TAG = "corrections";
    private static final String CORRECTION_TAG = "correction";
    private static final String NAME_TAG = "name";
    private static final String OLD_VALUE_TAG = "oldValue";
    private static final String NEW_VALUE_TAG = "newValue";
    private static final String INPUT_INDEXES_TAG = "inputClauseIndexes";
    private static final String RULE_INDEXES_TAG = "ruleIndexes";

    protected final BuildLogger logger;
    protected boolean transformRepository = true;
    protected List<Correction> corrections;

    public CorrectPathsInDecisionsTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    protected CorrectPathsInDecisionsTransformer(BuildLogger logger) {
        this.logger = logger;
    }

    @Override
    public void configure(Map<String, Object> configuration) {
        this.corrections = parseConfigurationForCorrections(configuration);
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("Repository is empty; transformer will not run");
            return repository;
        }

        if (this.corrections == null || this.corrections.isEmpty()) {
            logger.warn("No definitions provided; transformer will not run");
            return repository;
        }

        correctDecisions(repository, this.corrections);

        this.transformRepository = false;
        return repository;
    }

    @Override
    public Pair<DMNModelRepository, List<TestLab>> transform(DMNModelRepository repository, List<TestLab> testCasesList) {
        if (isEmpty(repository, testCasesList)) {
            logger.warn("DMN repository or test list is empty; transformer will not run");
            return new Pair<>(repository, testCasesList);
        }

        // Transform model
        if (this.transformRepository) {
            transform(repository);
        }

        return new Pair<>(repository, testCasesList);
    }

    private List<Correction> parseConfigurationForCorrections(Map<String, Object> configuration) {
        List<Correction> result = new ArrayList<>();
        List<Object> correctionObjList = extractCorrectionConfigurations(configuration);
        for (Object correctionObj : correctionObjList) {
            if (!(correctionObj instanceof Map)) {
                reportInvalidConfig(String.format("Incorrect or missing '%s' nodes", CORRECTION_TAG));
            } else {
                Map<String, Object> def = (Map<String, Object>) correctionObj;
                Object name = def.get(NAME_TAG);
                Object oldValue = def.get(OLD_VALUE_TAG);
                Object newValue = def.get(NEW_VALUE_TAG);
                Object inputClauseIndexes = def.get(INPUT_INDEXES_TAG);
                Object ruleIndexes = def.get(RULE_INDEXES_TAG);

                Correction correction = makeCorrection(name, oldValue, newValue, inputClauseIndexes, ruleIndexes);
                result.add(correction);
            }
        }

        return result;
    }

    private List<Object> extractCorrectionConfigurations(Map<String, Object> configuration) {
        List<Object> correctionList = null;
        if (configuration == null) {
            reportInvalidConfig(String.format("Incorrect or missing '%s' node", CORRECTIONS_TAG));
        } else {
            Object correctionsObj = configuration.get(CORRECTIONS_TAG);
            if (!(correctionsObj instanceof Map)) {
                reportInvalidConfig(String.format("Incorrect or missing '%s' node", CORRECTIONS_TAG));
            } else {
                Object correctionObj = ((Map<String, Object>) correctionsObj).get(CORRECTION_TAG);
                if (correctionObj instanceof List) {
                    correctionList = (List<Object>) correctionObj;
                } else if (correctionObj instanceof Map) {
                    correctionList = Collections.singletonList(correctionObj);
                } else {
                    reportInvalidConfig(String.format("Incorrect or missing '%s' nodes", CORRECTION_TAG));
                }
            }
        }
        return correctionList;
    }

    private Correction makeCorrection(Object name, Object oldValue, Object newValue, Object inputClauseIndexes, Object ruleIndexes) {
        Correction correction = null;
        if (!(name instanceof String && oldValue instanceof String && newValue instanceof String)) {
            reportInvalidConfig(String.format("Incorrect fields in '%s' node", CORRECTION_TAG));
        } else {
            // Create Correction
            List<Integer> inputIndexesList = makeIndexList(inputClauseIndexes);
            List<Integer> ruleIndexesList = makeIndexList(ruleIndexes);
            correction = new DecisionTableCorrection((String) name, (String) oldValue, (String) newValue, inputIndexesList, ruleIndexesList);
        }
        return correction;
    }

    private List<Integer> makeIndexList(Object object) {
        if (object == null) {
            return new ArrayList<>();
        } else if (object instanceof String) {
            if (((String) object).isEmpty()) {
                return new ArrayList<>();
            } else {
                return Arrays.stream(((String) object).split(",")).map(Integer::parseInt).collect(Collectors.toList());
            }
        } else {
            reportInvalidConfig(String.format("Unexpected comma separated list of indexes '%s'", object));
            return new ArrayList<>();
        }
    }

    private void correctDecisions(DMNModelRepository repository, List<Correction> corrections) {
        for (Correction cor: corrections) {
            String drgName = cor.getDrgName();
            TDRGElement drgElement = repository.findDRGElementByName(drgName);
            if (drgElement instanceof TDecision) {
                correctDecision((TDecision) drgElement, cor, repository);
            } else {
                reportInvalidConfig(String.format("Cannot find decision '%s'", drgName));
            }
        }
    }

    private void correctDecision(TDecision decision, Correction cor, DMNModelRepository repository) {
        TExpression expression = repository.expression(decision);
        if (cor instanceof DecisionTableCorrection && expression instanceof TDecisionTable) {
            DecisionTableCorrection dtc = (DecisionTableCorrection) cor;
            String oldValue = dtc.getOldValue();
            String newValue = dtc.getNewValue();
            List<Integer> inputIndexes = dtc.getInputIndexes();
            List<Integer> ruleIndexes = dtc.getRuleIndexes();

            // Correct InputClauses
            TDecisionTable dte = (TDecisionTable) expression;
            List<TInputClause> inputList = dte.getInput();
            for (int i=0; i<inputList.size(); i++) {
                if (inputIndexes.contains(i + 1)) {
                    TInputClause inputClause = inputList.get(i);
                    updateLiteralExpression(inputClause.getInputExpression(), oldValue, newValue);
                }
            }
            // Correct Rules
            List<TDecisionRule> ruleList = dte.getRule();
            for (int i=0; i<ruleList.size(); i++) {
                if (ruleIndexes.contains(i + 1)) {
                    TDecisionRule rule = ruleList.get(i);
                    for (TLiteralExpression outputEntry: rule.getOutputEntry()) {
                        updateLiteralExpression(outputEntry, oldValue, newValue);
                    }
                }
            }
        } else {
            reportInvalidConfig(String.format("Correction '%s' is not supported for decision '%s' yet", cor, decision));
        }

    }

    private void updateLiteralExpression(TLiteralExpression expression, String oldValue, String newValue) {
        String newText = expression.getText().replace(oldValue, newValue);
        expression.setText(newText);
    }

    protected void reportInvalidConfig(String message) {
        throw new DMNRuntimeException(String.format("Invalid transformer configuration: %s", message));
    }
}
