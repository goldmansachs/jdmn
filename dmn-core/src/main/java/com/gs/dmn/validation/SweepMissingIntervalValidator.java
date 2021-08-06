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
import com.gs.dmn.validation.table.*;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.*;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SweepMissingIntervalValidator extends SimpleDMNValidator {
    private final DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition;
    private final InputParameters inputParameters;
    private final TableFactory factory = new TableFactory();

    public SweepMissingIntervalValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public SweepMissingIntervalValidator(BuildLogger logger) {
        super(logger);
        this.dmnDialectDefinition = new StandardDMNDialectDefinition();
        this.inputParameters = new InputParameters(makeInputParametersMap());
    }

    public SweepMissingIntervalValidator(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition) {
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
        int totalNumberOfRules = decisionTable.getRule().size();
        for (int i = 0; i< totalNumberOfRules; i++) {
            ruleIndex.add(i);
        }
        MissingIntervals missingIntervals = new MissingIntervals();
        int totalNumberOfColumns = decisionTable.getInput().size();
        Table table = factory.makeTable(totalNumberOfRules, totalNumberOfColumns, repository, element, decisionTable, feelTranslator);
        if (!table.isEmpty()) {
            findMissingRules(ruleIndex, totalNumberOfColumns, missingIntervals, table);

            LOGGER.debug("Found missing intervals {}", missingIntervals);

            for (int i=0; i<totalNumberOfColumns; i++) {
                List<Interval> intervals = missingIntervals.getIntervals(i);
                if (intervals != null && !intervals.isEmpty()) {
                    String error = makeError(element, i, intervals, repository);
                    errorReport.add(error);
                }
            }
        }
    }

    private String makeError(TDRGElement element, int columnIndex, List<Interval> intervals, DMNModelRepository repository) {
        String intervalsString = intervals.stream().map(Interval::serialize).collect(Collectors.joining(", "));
        if (intervals.size() == 1) {
            return String.format("Interval '%s' is not covered for column %d in '%s' table", intervalsString, columnIndex + 1, repository.displayName(element));
        } else {
            return String.format("Intervals '%s' are not covered for column %d in '%s' table", intervalsString, columnIndex + 1, repository.displayName(element));
        }
    }

    //  Algorithm: findMissingRules.
    //  Input: ruleList; missingIntervals; i; N; missingRuleList.
    //      - ruleList, containing all rules of the input DMN table;
    //      - missingIntervals, storing the current missing intervals;
    //      - i, containing the index of the column under scrutiny;
    //      - N, representing the total number of columns;
    //      - missingRuleList, storing the missing rules.

    //  if i < N then
    //      Lxi = [] // initializes the current list of bounds
    //      sortedListAllBounds = ruleList.sort(i)
    //      lastBound = 0
    //      foreach currentBound ∈ sortedListAllBounds do
    //          if !areAdjacent( lastBound, currentBound) then
    //              missingIntervals[i] = constructInterval(lastBound, currentBound)
    //              if missingRuleList.canBeMerged(missingIntervals) then
    //                  missingRuleList.merge(missingIntervals)
    //              else
    //                  missingRuleList.add(missingIntervals)
    //          if !Lxi.isEmpty() then
    //              missingIntervals[i] = constructInterval(lastBound, currentBound)
    //              findMissingRules(Lxi, missingIntervals, i+1, N, missingRuleList) /* recursive invocation */
    //          if currentBound.isLower() then
    //              Lxi.put(currentBound)
    //          else
    //              Lxi.delete( currentBound);
    //          lastBound = currentBound
    //  return missingRuleList;
    private void findMissingRules(List<Integer> ruleList, int totalNumberOfColumns, MissingIntervals missingIntervals, Table table) {
        for (int columnIndex=0; columnIndex<totalNumberOfColumns; columnIndex++) {
            String indent = StringUtils.repeat("\t", columnIndex);
            List<Bound> sortedListAllBounds = makeBoundList(ruleList, columnIndex, table);
            Bound lowerBound = table.getLowerBoundForColumn(columnIndex);
            Bound upperBound = table.getUpperBoundForColumn(columnIndex);
            Bound lastBound = lowerBound;
            int boundCount = sortedListAllBounds.size();
            List<Integer> lxi = new ArrayList<>();

            LOGGER.debug("{}Column = {} Sorted bounds = '{}'", indent, columnIndex, sortedListAllBounds);

            for (int i = 0; i<boundCount; i++) {
                Bound currentBound = sortedListAllBounds.get(i);

                LOGGER.debug("{}Current bound = '{}'", indent, currentBound);

                Interval missingInterval = null;
                if (i == 0) {
                    // Check if there is a gap between min(column) and first bound
                    if (lowerBound != null && !Bound.areAdjacent(lowerBound, currentBound)) {
                        missingInterval = factory.makeInterval(columnIndex, lowerBound, currentBound, table);
                    }
                } else if (i == boundCount - 1) {
                    // Check if there is a gap between last bound and max(column)
                    if (upperBound != null && !Bound.areAdjacent(currentBound, upperBound)) {
                        missingInterval = factory.makeInterval(columnIndex, currentBound, upperBound, table);
                    }
                } else {
                    // No active intervals yet but values are different (e.g., lastBound is upper bound and current bound is lower bound)
                    if (lxi.isEmpty() && !lastBound.isLowerBound() && currentBound.isLowerBound() && !Bound.areAdjacent(lastBound, currentBound)) {
                        missingInterval = factory.makeInterval(columnIndex, lastBound, currentBound, table);
                    }
                }
                if (missingInterval != null) {
                    missingIntervals.addMissingInterval(columnIndex, missingInterval);
                }

                // Update active rules
                int ruleIndex = currentBound.getInterval().getRuleIndex();
                if (currentBound.isLowerBound()) {
                    LOGGER.info("{}Add active rule {}", indent, ruleIndex);
                    lxi.add(ruleIndex);
                } else {
                    LOGGER.info("{}Remove active rule {}", indent, ruleIndex);
                    lxi.remove((Object) ruleIndex);
                }

                lastBound = currentBound;
            }
        }
    }

    private List<Bound> makeBoundList(List<Integer> ruleList, int columnIndex, Table table) {
        BoundList boundList = new BoundList(ruleList, columnIndex, table);
        boundList.sort();
        return boundList.getBounds();
    }
}
