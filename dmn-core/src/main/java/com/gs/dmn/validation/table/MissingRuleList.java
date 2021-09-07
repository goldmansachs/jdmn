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
package com.gs.dmn.validation.table;

import com.gs.dmn.runtime.Pair;
import com.gs.dmn.validation.SimpleDMNValidator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MissingRuleList {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDMNValidator.class);
    private final List<Rule> rules = new ArrayList<>();

    public List<Rule> getRules() {
        return rules;
    }

    public void addOrMerge(int columnIndex, int totalNumberOfColumns, MissingIntervals missingIntervals) {
        if (missingIntervals == null || columnIndex != totalNumberOfColumns -1) {
            return ;
        }

        boolean merge = mergeIfPossible(columnIndex, totalNumberOfColumns, missingIntervals);
        if (!merge) {
            add(columnIndex, totalNumberOfColumns, missingIntervals);
        }
    }

    public void add(int columnIndex, int totalNumberOfColumns, MissingIntervals missingIntervals) {
        if (missingIntervals != null && columnIndex == totalNumberOfColumns - 1) {
            String indent = StringUtils.repeat("\t", columnIndex);
            List<Interval> intervals = new ArrayList<>();
            for (int i=0; i<totalNumberOfColumns; i++) {
                List<Interval> subIntervals = missingIntervals.getIntervals(i);
                if (subIntervals.size() == 1) {
                    intervals.add(subIntervals.get(0).copy());
                } else {
                    throw new IllegalArgumentException("Cannot have 2 intervals on same column");
                }
            }
            LOGGER.info("{}Add missing rule '{}'", indent, intervals);

            rules.add(new Rule(intervals));
        }
    }

    private boolean mergeIfPossible(int columnIndex, int totalNumberOfColumns, MissingIntervals missingIntervals) {
        List<Pair<Rule, Integer>> mergeCandidates = new ArrayList<>();
        for (Rule rule: rules) {
            int columnToMerge = rule.findColumnToMerge(missingIntervals);
            if (columnToMerge != -1) {
                mergeCandidates.add(new Pair<>(rule, columnToMerge));
            }
        }
        if (mergeCandidates.size() != 1) {
            return false;
        } else {
            String indent = StringUtils.repeat("\t", columnIndex);
            Pair<Rule, Integer> candidate = mergeCandidates.get(0);
            Rule rule = candidate.getLeft();

            LOGGER.info("{}Merge existing rule '{}'", indent, rule);

            Integer columnToMerge = candidate.getRight();
            Interval otherInterval = missingIntervals.getIntervals(columnToMerge).get(0);
            rule.merge(columnToMerge, otherInterval);

            LOGGER.info("{}Merged result '{}'", indent, rule);

            return true;
        }
    }

    @Override
    public String toString() {
        return rules.stream().map(Rule::toString).collect(Collectors.joining(" | "));
    }
}
