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
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TDecisionTable;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.synthesis.ELTranslator;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.validation.table.Bound;
import com.gs.dmn.validation.table.Interval;
import com.gs.dmn.validation.table.MissingIntervals;
import com.gs.dmn.validation.table.Table;
import org.apache.commons.lang3.StringUtils;

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
    protected void validate(TDRGElement element, TDecisionTable decisionTable, SweepValidationContext context) {
        if (element != null) {
            DMNModelRepository repository = context.getRepository();
            ELTranslator<Type, DMNContext> elTranslator = context.getElTranslator();
            logger.debug("Validating element '%s'".formatted(element.getName()));

            List<Integer> ruleIndex = new ArrayList<>();
            int totalNumberOfRules = decisionTable.getRule().size();
            for (int i = 0; i< totalNumberOfRules; i++) {
                ruleIndex.add(i);
            }
            MissingIntervals missingIntervals = new MissingIntervals();
            int totalNumberOfColumns = decisionTable.getInput().size();
            Table table = this.factory.makeTable(totalNumberOfRules, totalNumberOfColumns, repository, element, decisionTable, elTranslator);
            if (!table.isEmpty()) {
                findMissingIntervals(ruleIndex, totalNumberOfColumns, missingIntervals, table);

                logger.debug("Found missing intervals '%s'".formatted(missingIntervals));

                for (int i=0; i<totalNumberOfColumns; i++) {
                    List<Interval> intervals = missingIntervals.getIntervals(i);
                    if (intervals != null && !intervals.isEmpty()) {
                        context.addError(makeError(element, i, intervals, repository));
                    }
                }
            }
        }
    }

    private String makeError(TDRGElement element, int columnIndex, List<Interval> intervals, DMNModelRepository repository) {
        TDefinitions model = repository.getModel(element);
        String intervalsString = intervals.stream().map(Interval::serialize).collect(Collectors.joining(", "));
        String message;
        if (intervals.size() == 1) {
            message = "Interval '%s' is not covered for column %d in '%s' table".formatted(intervalsString, columnIndex + 1, repository.displayName(element));
        } else {
            message = "Intervals '%s' are not covered for column %d in '%s' table".formatted(intervalsString, columnIndex + 1, repository.displayName(element));
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

            logger.debug("%sColumn = %s Sorted bounds = '%s'".formatted(indent, columnIndex, sortedListAllBounds));

            for (int i = 0; i<boundCount; i++) {
                Bound currentBound = sortedListAllBounds.get(i);

                logger.debug("%sCurrent bound = '%s'".formatted(indent, currentBound));

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
                    logger.debug("%sAdd active rule %s".formatted(indent, ruleIndex));
                    lxi.add(ruleIndex);
                } else {
                    logger.debug("%sRemove active rule %s".formatted(indent, ruleIndex));
                    lxi.remove((Object) ruleIndex);
                }

                lastBound = currentBound;
            }
        }
    }
}
