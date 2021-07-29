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
import org.omg.spec.dmn._20191111.model.*;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RuleOverlapValidator extends SimpleDMNValidator {
    private final DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition;
    private final InputParameters inputParameters;

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

        return makeErrorReport(dmnModelRepository);
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
        for (TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            List<TDRGElement> drgElements = dmnModelRepository.findDRGElements(definitions);
            for (TDRGElement element: drgElements) {
                if (element instanceof TDecision) {
                    JAXBElement<? extends TExpression> jaxbExpression = ((TDecision) element).getExpression();
                    if (jaxbExpression != null) {
                        TExpression expression = jaxbExpression.getValue();
                        if (expression instanceof TDecisionTable && ((TDecisionTable) expression).getHitPolicy() == THitPolicy.UNIQUE) {
                            validate(element, (TDecisionTable) expression, dmnModelRepository, errorReport);
                        }
                    }
                }
            }
        }
        return errorReport;
    }

    private void validate(TDRGElement element, TDecisionTable decisionTable, DMNModelRepository repository, List<String> errorReport) {
        logger.debug(String.format("Validate element '%s'", element.getName()));

        FEELTranslator feelTranslator = this.dmnDialectDefinition.createFEELTranslator(repository, this.inputParameters);
        List<Integer> ruleIndexList = new ArrayList<>();
        for (int i=0; i<decisionTable.getRule().size(); i++) {
            ruleIndexList.add(i);
        }
        // Find the overlapping rules
        ArrayList<RuleGroup> overlappingRules = new ArrayList<>();
        findOverlappingRules(ruleIndexList, 0, decisionTable.getInput().size(), overlappingRules, repository, element, decisionTable, feelTranslator);
        for (RuleGroup ruleGroup: overlappingRules) {
            String error = makeError(element, ruleGroup, repository);
            errorReport.add(error);
        }
    }

    private String makeError(TDRGElement element, RuleGroup group, DMNModelRepository repository) {
        TDefinitions model = repository.getModel(element);
        String message = String.format("Decision table rules '%s' overlap in decision '%s'", group.serialize(), repository.displayName(element));
        return makeError(repository, model, element, message);
    }

    //  From "Semantics and Analysis of DMN Decision Tables.pdf"
    //  Algorithm: findOverlappingRules
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
    private List<RuleGroup> findOverlappingRules(List<Integer> ruleList, int columnIndex, int inputColumnCount, List<RuleGroup> overlappingRuleList, DMNModelRepository repository, TDRGElement element, TDecisionTable decisionTable, FEELTranslator feelTranslator) {
        if(columnIndex == inputColumnCount) {
            RuleGroup group = new RuleGroup(new ArrayList<>(ruleList));
            addGroup(overlappingRuleList, group);
        } else {
            // Define the current list of bounds lxi
            List<Integer> lxi = new ArrayList<>();
            // Project rules on column columnIndex
            List<Bound> sortedListAllBounds = makeBoundList(ruleList, columnIndex, repository, element, decisionTable, feelTranslator);
            for (Bound bound : sortedListAllBounds) {
                if (!bound.isLowerBound()) {
                    List<RuleGroup> overlappingRules = findOverlappingRules(lxi, columnIndex + 1, inputColumnCount, overlappingRuleList, repository, element, decisionTable, feelTranslator);
                    lxi.remove((Object) bound.getInterval().getRuleIndex());

                    for (RuleGroup group : overlappingRules) {
                        addGroup(overlappingRuleList, group);
                    }
                } else {
                    lxi.add(bound.getInterval().getRuleIndex());
                }
            }
        }

        return overlappingRuleList;
    }

    private void addGroup(List<RuleGroup> rules, RuleGroup group) {
        if (group != null && group.getRuleIndexes().size() > 1) {
            if (!rules.contains(group)) {
                rules.add(group);
            }
        }
    }

    private List<Bound> makeBoundList(List<Integer> ruleList, int columnIndex, DMNModelRepository repository, TDRGElement element, TDecisionTable decisionTable, FEELTranslator feelTranslator) {
        BoundList boundList = new BoundList(repository, element, decisionTable, ruleList, columnIndex, feelTranslator);
        if (boundList.isCanProject()) {
            boundList.sort();
            return boundList.getBounds();
        }
        return new ArrayList<>();
    }
}
