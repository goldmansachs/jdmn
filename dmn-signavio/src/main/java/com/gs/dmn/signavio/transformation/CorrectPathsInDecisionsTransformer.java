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
import com.gs.dmn.ast.*;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.signavio.transformation.config.Correction;
import com.gs.dmn.signavio.transformation.config.DecisionTableCorrection;
import com.gs.dmn.transformation.SimpleDMNTransformer;

import java.util.*;
import java.util.stream.Collectors;

public class CorrectPathsInDecisionsTransformer extends SimpleDMNTransformer<TestLab> {
    private static final String CORRECTIONS_TAG = "corrections";
    private static final String CORRECTION_TAG = "correction";
    private static final String NAME_TAG = "name";
    private static final String OLD_VALUE_TAG = "oldValue";
    private static final String NEW_VALUE_TAG = "newValue";
    private static final String INPUT_INDEXES_TAG = "inputClauseIndexes";
    private static final String RULE_INDEXES_TAG = "ruleIndexes";

    protected List<Correction> corrections;

    public CorrectPathsInDecisionsTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public CorrectPathsInDecisionsTransformer(BuildLogger logger) {
        super(logger);
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
                reportInvalidConfig("Incorrect or missing '%s' nodes".formatted(CORRECTION_TAG));
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
            reportInvalidConfig("Incorrect or missing '%s' node".formatted(CORRECTIONS_TAG));
        } else {
            Object correctionsObj = configuration.get(CORRECTIONS_TAG);
            if (!(correctionsObj instanceof Map)) {
                reportInvalidConfig("Incorrect or missing '%s' node".formatted(CORRECTIONS_TAG));
            } else {
                Object correctionObj = ((Map<String, Object>) correctionsObj).get(CORRECTION_TAG);
                if (correctionObj instanceof List list) {
                    correctionList = list;
                } else if (correctionObj instanceof Map) {
                    correctionList = Collections.singletonList(correctionObj);
                } else {
                    reportInvalidConfig("Incorrect or missing '%s' nodes".formatted(CORRECTION_TAG));
                }
            }
        }
        return correctionList;
    }

    private Correction makeCorrection(Object name, Object oldValue, Object newValue, Object inputClauseIndexes, Object ruleIndexes) {
        Correction correction = null;
        if (!(name instanceof String && oldValue instanceof String && newValue instanceof String)) {
            reportInvalidConfig("Incorrect fields in '%s' node".formatted(CORRECTION_TAG));
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
        } else if (object instanceof String string) {
            if (string.isEmpty()) {
                return new ArrayList<>();
            } else {
                return Arrays.stream(string.split(",")).map(Integer::parseInt).collect(Collectors.toList());
            }
        } else {
            reportInvalidConfig("Unexpected comma separated list of indexes '%s'".formatted(object));
            return new ArrayList<>();
        }
    }

    private void correctDecisions(DMNModelRepository repository, List<Correction> corrections) {
        for (Correction cor: corrections) {
            String drgName = cor.getDrgName();
            TDRGElement drgElement = repository.findDRGElementByName(drgName);
            if (drgElement instanceof TDecision decision) {
                correctDecision(decision, cor, repository);
            } else {
                reportInvalidConfig("Cannot find decision '%s'".formatted(drgName));
            }
        }
    }

    private void correctDecision(TDecision decision, Correction cor, DMNModelRepository repository) {
        TExpression expression = repository.expression(decision);
        if (cor instanceof DecisionTableCorrection dtc && expression instanceof TDecisionTable dte) {
            String oldValue = dtc.getOldValue();
            String newValue = dtc.getNewValue();
            List<Integer> inputIndexes = dtc.getInputIndexes();
            List<Integer> ruleIndexes = dtc.getRuleIndexes();
            boolean correctAll = inputIndexes.isEmpty() && ruleIndexes.isEmpty();
            correctInputClauses(decision, oldValue, newValue, inputIndexes, correctAll, dte);
            // Correct Rules
            correctRules(decision, oldValue, newValue, ruleIndexes, correctAll, dte);
        } else {
            reportInvalidConfig("Correction '%s' is not supported for decision '%s' yet".formatted(cor, decision));
        }

    }

    private void correctRules(TDecision decision, String oldValue, String newValue, List<Integer> ruleIndexes, boolean correctAll, TDecisionTable dte) {
        List<TDecisionRule> ruleList = dte.getRule();
        for (int i=0; i<ruleList.size(); i++) {
            if (correctAll || ruleIndexes.contains(i + 1)) {
                TDecisionRule rule = ruleList.get(i);
                for (TLiteralExpression outputEntry: rule.getOutputEntry()) {
                    updateLiteralExpression(outputEntry, oldValue, newValue, decision);
                }
            }
        }
    }

    private void correctInputClauses(TDecision decision, String oldValue, String newValue, List<Integer> inputIndexes, boolean correctAll, TDecisionTable dte) {
        List<TInputClause> inputList = dte.getInput();
        for (int i=0; i<inputList.size(); i++) {
            if (correctAll || inputIndexes.contains(i + 1)) {
                TInputClause inputClause = inputList.get(i);
                updateLiteralExpression(inputClause.getInputExpression(), oldValue, newValue, decision);
            }
        }
    }

    private void updateLiteralExpression(TLiteralExpression expression, String oldValue, String newValue, TDecision decision) {
        String oldText = expression.getText();
        String newText = oldText.replace(oldValue, newValue);

        logger.info("Replacing expression '%s' with '%s' in decision '%s'".formatted(oldText, newText, decision.getName()));

        expression.setText(newText);
    }

    protected void reportInvalidConfig(String message) {
        throw new DMNRuntimeException("Invalid transformer configuration: %s".formatted(message));
    }
}
