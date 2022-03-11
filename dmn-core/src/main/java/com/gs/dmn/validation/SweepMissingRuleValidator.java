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
import com.gs.dmn.validation.table.*;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.TDRGElement;
import org.omg.spec.dmn._20191111.model.TDecisionTable;
import org.omg.spec.dmn._20191111.model.TDefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SweepMissingRuleValidator extends SweepValidator {
    private final boolean merge;

    public SweepMissingRuleValidator() {
        this(new Slf4jBuildLogger(LOGGER), false);
    }

    public SweepMissingRuleValidator(BuildLogger logger, boolean merge) {
        super(logger);
        this.merge = merge;
    }

    public SweepMissingRuleValidator(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition) {
        super(dmnDialectDefinition);
        this.merge = false;
    }

    public SweepMissingRuleValidator(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition, boolean merge) {
        super(dmnDialectDefinition);
        this.merge = merge;
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
        MissingRuleList missingRuleList = new MissingRuleList();
        int totalNumberOfColumns = decisionTable.getInput().size();
        Table table = this.factory.makeTable(totalNumberOfRules, totalNumberOfColumns, repository, element, decisionTable, feelTranslator);
        if (!table.isEmpty()) {
            findMissingRules(ruleIndex, 0, totalNumberOfColumns, missingIntervals, missingRuleList, table);

            LOGGER.debug("Found missing rules {}", missingRuleList);

            for (Rule rule: missingRuleList.getRules()) {
                String error = makeError(element, rule, repository);
                errorReport.add(error);
            }
        }
    }

    private String makeError(TDRGElement element, Rule rule, DMNModelRepository repository) {
        TDefinitions model = repository.getModel(element);
        String intervalsString = rule.getIntervals().stream().map(Interval::serialize).collect(Collectors.joining(", "));
        String message = String.format("Found missing rule '[%s]' in '%s' table", intervalsString, repository.displayName(element));
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
    private void findMissingRules(List<Integer> ruleList, int columnIndex, int totalNumberOfColumns, MissingIntervals missingIntervals, MissingRuleList missingRuleList, Table table) {
        String indent = StringUtils.repeat("\t", columnIndex);
        LOGGER.debug("{}Column = '{}' Active rules = '{}' Missing intervals = '{}'", indent, columnIndex, ruleList, missingIntervals);

        if(columnIndex < totalNumberOfColumns) {
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
                    // No active intervals yet but values are different (e.g., lastBound is upper bound and current bound is lower bound)
                    if (lxi.isEmpty() && !lastBound.isLowerBound() && currentBound.isLowerBound() && !Bound.areAdjacent(lastBound, currentBound)) {
                        missingInterval = factory.makeInterval(columnIndex, lastBound, currentBound, table);
                    }
                }
                if (missingInterval != null) {
                    missingIntervals.putMissingInterval(columnIndex, missingInterval);
                    if (merge) {
                        missingRuleList.addOrMerge(columnIndex, totalNumberOfColumns, missingIntervals);
                    } else {
                        missingRuleList.add(columnIndex, totalNumberOfColumns, missingIntervals);
                    }
                }

                if (!lxi.isEmpty() && !Bound.areAdjacent(lastBound, currentBound)) {
                    missingInterval = factory.makeInterval(columnIndex, lastBound, currentBound, table);
                    missingIntervals.putMissingInterval(columnIndex, missingInterval);
                    findMissingRules(lxi, columnIndex + 1, totalNumberOfColumns, missingIntervals, missingRuleList, table);
                }

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
