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
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.validation.table.Bound;
import com.gs.dmn.validation.table.Interval;
import com.gs.dmn.validation.table.MissingIntervals;
import com.gs.dmn.validation.table.Table;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.TDRGElement;
import org.omg.spec.dmn._20191111.model.TDecisionTable;
import org.omg.spec.dmn._20191111.model.TDefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SweepMissingIntervalValidator extends SweepValidator {
    public SweepMissingIntervalValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public SweepMissingIntervalValidator(BuildLogger logger) {
        super(logger);
    }

    public SweepMissingIntervalValidator(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition) {
        super(dmnDialectDefinition);
    }

    @Override
    protected void validate(TDRGElement element, TDecisionTable decisionTable, BasicDMNToJavaTransformer transformer, DMNModelRepository repository, List<String> errorReport) {
        logger.debug(String.format("Validate element '%s'", element.getName()));

        FEELTranslator<Type, DMNContext> feelTranslator = this.dmnDialectDefinition.createFEELTranslator(repository, this.inputParameters);
        List<Integer> ruleIndex = new ArrayList<>();
        int totalNumberOfRules = decisionTable.getRule().size();
        for (int i = 0; i< totalNumberOfRules; i++) {
            ruleIndex.add(i);
        }
        MissingIntervals missingIntervals = new MissingIntervals();
        int totalNumberOfColumns = decisionTable.getInput().size();
        Table table = this.factory.makeTable(totalNumberOfRules, totalNumberOfColumns, repository, element, decisionTable, feelTranslator);
        if (!table.isEmpty()) {
            findMissingIntervals(ruleIndex, totalNumberOfColumns, missingIntervals, table);

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
        TDefinitions model = repository.getModel(element);
        String intervalsString = intervals.stream().map(Interval::serialize).collect(Collectors.joining(", "));
        String message;
        if (intervals.size() == 1) {
            message = String.format("Interval '%s' is not covered for column %d in '%s' table", intervalsString, columnIndex + 1, repository.displayName(element));
        } else {
            message = String.format("Intervals '%s' are not covered for column %d in '%s' table", intervalsString, columnIndex + 1, repository.displayName(element));
        }
        return makeError(repository, model, element, message);
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
    private void findMissingIntervals(List<Integer> ruleList, int totalNumberOfColumns, MissingIntervals missingIntervals, Table table) {
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
                    if (lowerBound != null && !Bound.sameEnd(lowerBound, currentBound)) {
                        missingInterval = factory.makeIntervalMin(columnIndex, lowerBound, currentBound, table);
                    }
                } else if (i == boundCount - 1) {
                    // Check if there is a gap between last bound and max(column)
                    if (upperBound != null && !Bound.sameEnd(currentBound, upperBound)) {
                        missingInterval = factory.makeIntervalMax(columnIndex, currentBound, upperBound, table);
                    }
                } else {
                    // No active intervals yet but values are not adjacent (e.g., lastBound is upper bound and currentBound is lower bound)
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
}
