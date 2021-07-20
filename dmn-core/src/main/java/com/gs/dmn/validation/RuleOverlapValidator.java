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
package com.gs.dmn.validation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.omg.spec.dmn._20191111.model.*;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RuleOverlapValidator extends SimpleDMNValidator {
    private final DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition;
    private InputParameters inputParameters;

    public RuleOverlapValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public RuleOverlapValidator(BuildLogger logger) {
        super(logger);
        this.dmnDialectDefinition = new StandardDMNDialectDefinition();
        this.inputParameters = new InputParameters(makeInputParametersMap());
    }

    public RuleOverlapValidator(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition) {
        super(new Slf4jBuildLogger(LOGGER));
        this.dmnDialectDefinition = dmnDialectDefinition;
        this.inputParameters = new InputParameters(makeInputParametersMap());
    }

    @Override
    public List<String> validate(DMNModelRepository dmnModelRepository) {
        if (isEmpty(dmnModelRepository)) {
            logger.warn("DMN repository is empty; validator will not run");
            return new ArrayList<>();
        }

        List<String> errorReport = makeErrorReport(dmnModelRepository);
        return errorReport;
    }

    private Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("dmnVersion", "1.1");
        inputParams.put("modelVersion", "2.0");
        inputParams.put("platformVersion", "1.0");
        return inputParams;
    }

    public List<String> makeErrorReport(DMNModelRepository dmnModelRepository) {
        List<String> errorReport = new ArrayList<>();
        BasicDMNToJavaTransformer dmnTransformer = this.dmnDialectDefinition.createBasicTransformer(dmnModelRepository, new NopLazyEvaluationDetector(), this.inputParameters);
        for (TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            List<TDRGElement> drgElements = dmnModelRepository.findDRGElements(definitions);
            for (TDRGElement element: drgElements) {
                if (element instanceof TDecision) {
                    JAXBElement<? extends TExpression> jaxbExpression = ((TDecision) element).getExpression();
                    if (jaxbExpression != null) {
                        TExpression expression = jaxbExpression.getValue();
                        if (expression instanceof TDecisionTable && ((TDecisionTable) expression).getHitPolicy() == THitPolicy.UNIQUE) {
                            validate(element, (TDecisionTable) expression, dmnTransformer, dmnModelRepository, errorReport);
                        }
                    }
                }
            }
        }
        return errorReport;
    }

    private void validate(TDRGElement element, TDecisionTable decisionTable, BasicDMNToJavaTransformer transformer, DMNModelRepository repository, List<String> errorReport) {
        logger.debug(String.format("Validate element '%s'", element.getName()));

        FEELTranslator feelTranslator = this.dmnDialectDefinition.createFEELTranslator(repository, this.inputParameters);
        List<Integer> ruleIndex = new ArrayList<>();
        for (int i=0; i<decisionTable.getRule().size(); i++) {
            ruleIndex.add(i);
        }
        ArrayList<RuleGroup> overlappingRules = new ArrayList<>();
        findOverlappingRules(repository, element, decisionTable, ruleIndex, 0, decisionTable.getInput().size(), overlappingRules, feelTranslator);
        for (RuleGroup ruleGroup: overlappingRules) {
            String error = makeError(element, ruleGroup, repository);
            errorReport.add(error);
        }
    }

    private String makeError(TDRGElement element, RuleGroup group, DMNModelRepository repository) {
        TDefinitions model = repository.getModel(element);
        String message = String.format("Decision table rules '%s' overlap in decision '%s'", group.toString(), repository.displayName(element));
        return makeError(repository, model, element, message);
    }

    //  Input: ruleList; i; N; overlappingRuleList.
    //      1. ruleList, containing all rules of the input DMN table;
    //      2. i, containing the index of the column under scrutiny;
    //      3. N, representing the total number of columns;
    //      4. OverlappingRuleList, storing the rules that overlap.
    //  if i == N then
    //      define current overlap currentOverlapRules; /* it contains the list of rules that overlap up to the current point */ ;
    //      if !overlappingRuleList.includes(currentOverlapRules) then
    //          overlappingRuleList.put(currentOverlapRules);
    //  else
    //      define the current list of bounds Lxi ;
    //      sortedListAllBounds = ruleList.sort(i);
    //      foreach currentBound in sortedListAllBoundaries do
    //          if !currentBound.isLower() then
    //              findOverlappingRules(Lxi, i + 1, N, overlappingRuleList); /* recursive call */
    //              Lxi.delete(currentBound);
    //          else
    //              Lxi.put(currentBound);
    //  return overlappingRuleList;
    private List<RuleGroup> findOverlappingRules(DMNModelRepository repository, TDRGElement element, TDecisionTable decisionTable, List<Integer> comparingRules, int columnIndex, int inputColumnCount, List<RuleGroup> rules, FEELTranslator feelTranslator) {
        if(columnIndex == inputColumnCount) {
            RuleGroup group = new RuleGroup(new ArrayList<>(comparingRules));
            addGroup(rules, group);
        } else {
            // define the current list of bounds lxi
            List<Integer> rulesForNextDimension = new ArrayList<>();
            // Project rules on column columnIndex
            BoundList boundList = new BoundList(repository, element, decisionTable, comparingRules, columnIndex, feelTranslator);
            if (boundList.isCanProject()) {
                boundList.sort();
                List<Bound> sortedBounds = boundList.getBounds();
                for (Bound bound : sortedBounds) {
                    if (!bound.isLowerBound()) {
                        List<RuleGroup> overlappingRules = findOverlappingRules(repository, element, decisionTable, rulesForNextDimension, columnIndex + 1, inputColumnCount, rules, feelTranslator);
                        rulesForNextDimension.remove((Object) bound.getInterval().getRuleIndex());

                        for (RuleGroup group : overlappingRules) {
                            addGroup(rules, group);
                        }
                    } else {
                        rulesForNextDimension.add(bound.getInterval().getRuleIndex());
                    }
                }
            }
        }

        return rules;
    }

    private void addGroup(List<RuleGroup> rules, RuleGroup group) {
        if (group != null && group.getRuleIndexes().size() > 1) {
            if (!rules.contains(group)) {
                rules.add(group);
            }
        }
    }
}
