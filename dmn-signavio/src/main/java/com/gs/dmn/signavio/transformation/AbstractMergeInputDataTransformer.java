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
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.extension.MultiInstanceDecisionLogic;
import com.gs.dmn.signavio.extension.SignavioExtension;
import com.gs.dmn.signavio.testlab.InputParameterDefinition;
import com.gs.dmn.signavio.testlab.TestCase;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.signavio.testlab.expression.Expression;
import com.gs.dmn.transformation.SimpleDMNTransformer;

import java.util.*;

public abstract class AbstractMergeInputDataTransformer extends SimpleDMNTransformer<TestLab> {
    private static final String FORCE_MERGE = "forceMerge";

    private Map<String, Pair<TInputData, List<TInputData>>> inputDataClasses;
    private boolean forceMerge = true;

    protected AbstractMergeInputDataTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    protected AbstractMergeInputDataTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public void configure(Map<String, Object> configuration) {
        if (!(configuration == null || configuration.isEmpty())) {
            Object forceMergeStr = configuration.get(FORCE_MERGE);
            if (forceMergeStr instanceof String && configuration.size() == 1) {
                this.forceMerge = Boolean.parseBoolean((String) forceMergeStr);
            } else {
                throw new DMNRuntimeException(String.format("Invalid transformer configuration: %s. Configuration does not have expected structure (expecting \"%s\" node)", configuration, FORCE_MERGE));
            }
        }
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; transformer will not run");
            return repository;
        }

        this.inputDataClasses = new LinkedHashMap<>();
        this.transformRepository = false;
        return mergeInputData(repository, logger);
    }

    @Override
    public Pair<DMNModelRepository, List<TestLab>> transform(DMNModelRepository repository, List<TestLab> testCasesList) {
        if (isEmpty(repository, testCasesList)) {
            logger.warn("DMN repository or test cases list is empty; transformer will not run");
            return new Pair<>(repository, testCasesList);
        }

        // Transform model
        if (this.transformRepository) {
            transform(repository);
        }

        // Transform test cases
        for (TestLab testLab: testCasesList) {
            transform(testLab, (SignavioDMNModelRepository) repository);
        }

        return new Pair<>(repository, testCasesList);
    }

    private void transform(TestLab testLab, SignavioDMNModelRepository repository) {
        // Check for conflicts between the values of the InputData in the same equivalence class
        if (!forceMerge) {
            List<TestCase> testCases = testLab.getTestCases();
            for(TestCase testCase: testCases) {
                List<Expression> inputValues = testCase.getInputValues();
                for(int i=0; i<testLab.getInputParameterDefinitions().size()-1; i++) {
                    InputParameterDefinition firstParameter = testLab.getInputParameterDefinitions().get(i);
                    String firstRequirementName = equivalenceKey(firstParameter);
                    Expression firstExpression = inputValues.get(i);
                    for (int j = i + 1; j < testLab.getInputParameterDefinitions().size(); j++) {
                        InputParameterDefinition secondParameter = testLab.getInputParameterDefinitions().get(j);
                        String secondRequirementName = equivalenceKey(secondParameter);
                        if (firstRequirementName.equals(secondRequirementName)) {
                            Expression secondExpression = inputValues.get(j);
                            if (!Objects.equals(firstExpression, secondExpression)) {
                                throw new DMNRuntimeException(String.format("Cannot merge, incompatible values for InputData '%s' '%s' and '%s'", firstRequirementName, firstExpression, secondExpression));
                            }
                        }
                    }
                }
            }
        }

        // Calculate parameters to remove
        List<InputParameterDefinition> toRemove = new ArrayList<>();
        List<Integer> indexToRemove = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for(int i=0; i<testLab.getInputParameterDefinitions().size(); i++) {
            InputParameterDefinition ipd = testLab.getInputParameterDefinitions().get(i);
            String requirementName = equivalenceKey(ipd);
            if (labels.contains(requirementName)) {
                toRemove.add(ipd);
                indexToRemove.add(i);
            } else {
                labels.add(requirementName);
                Pair<TInputData, List<TInputData>> pair = this.inputDataClasses.get(requirementName);
                if (pair == null) {
                    throw new DMNRuntimeException(String.format("Cannot find InputData for input parameter with requirementName='%s'", requirementName));
                }
                TInputData representative = pair.getLeft();
                String representativeDiagramId = repository.getDiagramId(representative);
                String representativeShapeId = repository.getShapeId(representative);
                ipd.setDiagramId(representativeDiagramId);
                ipd.setShapeId(representativeShapeId);
            }
        }

        // Remove InputParameterDefinition
        testLab.getInputParameterDefinitions().removeAll(toRemove);

        // Remove corresponding Input Values
        List<TestCase> testCases = testLab.getTestCases();
        for(TestCase testCase: testCases) {
            List<Expression> inputValues = testCase.getInputValues();
            List<Expression> newList = new ArrayList<>();
            for (int i=0; i<inputValues.size(); i++) {
                if (!indexToRemove.contains(i)) {
                    newList.add(inputValues.get(i));
                }
            }
            inputValues.clear();
            inputValues.addAll(newList);
        }
    }

    private DMNModelRepository mergeInputData(DMNModelRepository repository, BuildLogger logger) {
        // Compute equivalence classes for InputData with same equivalence key
        this.inputDataClasses = inputDataEquivalenceClasses(repository);

        // For each equivalence class
        TDefinitions definitions = repository.getRootDefinitions();
        for (Pair<TInputData, List<TInputData>> pair : inputDataClasses.values()) {
            TInputData representative = pair.getLeft();
            List<TInputData> inputDataInClass = pair.getRight();
            if (inputDataInClass.size() >= 2) {
                // For each decision
                List<TDecision> decisions = repository.findDecisions(definitions);
                for(TDecision decision: decisions) {
                    // Replace InputData in InformationRequirements with representative
                    List<TInformationRequirement> informationRequirementList = decision.getInformationRequirement();
                    for(TInformationRequirement ir: informationRequirementList) {
                        if (ir.getRequiredInput() != null) {
                            String href = ir.getRequiredInput().getHref();
                            TInputData referencedInputData = repository.findInputDataByRef(decision, href);
                            if (inputDataInClass.contains(referencedInputData) && referencedInputData != representative) {
                                String oldInputDataName = referencedInputData.getName();
                                String newInputDataName = representative.getName();
                                logger.info(String.format("Replacing input '%s' with '%s' in decision '%s'", oldInputDataName, newInputDataName, decision.getName()));

                                ir.getRequiredInput().setHref("#" + representative.getId());

                                // Replace in body
                                TExpression expression = decision.getExpression();
                                if (expression instanceof TDecisionTable) {
                                    // For each InputClause
                                    TDecisionTable decisionTable = (TDecisionTable) expression;
                                    List<TInputClause> inputClauselist = decisionTable.getInput();
                                    for(TInputClause ic: inputClauselist) {
                                        replace(oldInputDataName, newInputDataName, ic.getInputExpression());
                                    }
                                    // For each OutputClause
                                    for(TOutputClause oc: decisionTable.getOutput()) {
                                        replace(oldInputDataName, newInputDataName, oc.getDefaultOutputEntry());
                                    }
                                    // For each rule
                                    for(TDecisionRule rule: decisionTable.getRule()) {
                                        // Replace in description
                                        String description = rule.getDescription();
                                        rule.setDescription(replace(oldInputDataName, newInputDataName, description));
                                        // Replace in InputEntry
                                        for(TUnaryTests unaryTests : rule.getInputEntry()) {
                                            unaryTests.setText(replace(oldInputDataName, newInputDataName, unaryTests.getText()));
                                        }
                                        // Replace in OutputEntry
                                        for(TLiteralExpression literalExpression : rule.getOutputEntry()) {
                                            replace(oldInputDataName, newInputDataName, literalExpression);
                                        }
                                    }
                                } else if (expression instanceof TLiteralExpression) {
                                    replace(oldInputDataName, newInputDataName, (TLiteralExpression) expression);
                                }
                            }
                        }
                    }
                    // Replace IterationExpression with representative
                    if (((SignavioDMNModelRepository)repository).isMultiInstanceDecision(decision)) {
                        SignavioExtension extension = ((SignavioDMNModelRepository) repository).getExtension();
                        MultiInstanceDecisionLogic multiInstanceDecisionLogic = extension.multiInstanceDecisionLogic(decision);
                        String iterationExpression = multiInstanceDecisionLogic.getIterationExpression();
                        if (inputDataInClass.stream().anyMatch(i -> i.getName().equals(iterationExpression))) {
                            setIterationExpression(decision, representative.getName(), (SignavioDMNModelRepository) repository);
                        }
                    }
                }
            }
        }

        // Remove all but the representative
        for(Pair<TInputData, List<TInputData>> pair : inputDataClasses.values()) {
            TInputData representative = pair.getLeft();
            List<TInputData> inputDataClass = pair.getRight();
            for (TInputData inputData : inputDataClass) {
                if (inputData != representative) {
                    removeDRGElement(definitions, inputData);
                }
            }
        }

        return repository;
    }

    private void setIterationExpression(TDecision decision, String name, SignavioDMNModelRepository repository) {
        SignavioExtension signavioExtension = repository.getExtension();
        List<Object> extensions = signavioExtension.findExtensions(decision.getExtensionElements(), repository.getSchemaNamespace(), MultiInstanceDecisionLogic.class.getSimpleName());
        Object extension = extensions.get(0);
        if (extension instanceof com.gs.dmn.signavio.serialization.xstream.MultiInstanceDecisionLogic) {
            ((com.gs.dmn.signavio.serialization.xstream.MultiInstanceDecisionLogic) extension).setIterationExpression(name);
        } else {
            throw new RuntimeException(String.format("Cannot set iterationExpression for MID '%s'", decision));
        }
    }

    private Map<String, Pair<TInputData, List<TInputData>>> inputDataEquivalenceClasses(DMNModelRepository repository) {
        Map<String, Pair<TInputData, List<TInputData>>> inputDataClasses = new LinkedHashMap<>();
        TDefinitions definitions = repository.getRootDefinitions();
        List<TInputData> inputDataList = repository.findInputDatas(definitions);
        for(TInputData inputData: inputDataList) {
            if (!isIterator(inputData, repository)) {
                String key = equivalenceKey(inputData, repository);
                Pair<TInputData, List<TInputData>> inputDataClass = inputDataClasses.computeIfAbsent(key, k -> new Pair<>(null, new ArrayList<>()));
                inputDataClass.getRight().add(inputData);
            }
        }
        for(Map.Entry<String, Pair<TInputData, List<TInputData>>> entry: inputDataClasses.entrySet()) {
            Pair<TInputData, List<TInputData>> inputDataClass = entry.getValue();
            TInputData representative = shortestName(inputDataClass.getRight());
            inputDataClasses.put(entry.getKey(), new Pair<>(representative, inputDataClass.getRight()));
        }

        return inputDataClasses;
    }

    private void replace(String oldName, String newName, TLiteralExpression expression) {
        if (expression != null) {
            String text = expression.getText();
            String newText = replace(oldName, newName, text);
            expression.setText(newText);
        }
    }

    protected String replace(String oldName, String newName, String text) {
        return text == null ? null : text.replaceAll("\\b"+oldName+"\\b", newName);
    }

    private void removeDRGElement(TDefinitions definitions, TDRGElement drgElement) {
        TDRGElement elementToRemove = null;
        for(TDRGElement element: definitions.getDrgElement()) {
            if (element == drgElement) {
                elementToRemove = element;
            }
        }
        definitions.getDrgElement().remove(elementToRemove);
    }

    private TInputData shortestName(List<TInputData> inputDataClass) {
        int minLength = Integer.MAX_VALUE;
        TInputData representative = null;
        for(TInputData inputData: inputDataClass) {
            String name = inputData.getName();
            if (name.length() < minLength) {
                minLength = name.length();
                representative = inputData;
            }
        }
        return representative;
    }

    protected boolean isIterator(TInputData inputData, DMNModelRepository repository) {
        return repository instanceof SignavioDMNModelRepository && ((SignavioDMNModelRepository) repository).isIterator(inputData);
    }

    protected String diagramId(TInputData inputData, DMNModelRepository repository) {
        SignavioDMNModelRepository signavioRepository = (SignavioDMNModelRepository) repository;
        return signavioRepository.getDiagramId(inputData);
    }

    protected String shapeId(TInputData inputData, DMNModelRepository repository) {
        SignavioDMNModelRepository signavioRepository = (SignavioDMNModelRepository) repository;
        return signavioRepository.getShapeId(inputData);
    }

    protected abstract String equivalenceKey(TInputData inputData, DMNModelRepository repository);

    protected abstract String equivalenceKey(InputParameterDefinition parameter);
}
