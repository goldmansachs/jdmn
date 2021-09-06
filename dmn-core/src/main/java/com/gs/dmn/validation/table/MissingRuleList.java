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

    public void add(int columnIndex, int totalNumberOfColumns, MissingIntervals missingIntervals) {
        if (missingIntervals != null && columnIndex == totalNumberOfColumns - 1) {
            String indent = StringUtils.repeat("\t", columnIndex);
            List<Interval> intervals = new ArrayList<>();
            for (int i=0; i<totalNumberOfColumns; i++) {
                List<Interval> subIntervals = missingIntervals.getIntervals(i);
                if (subIntervals.size() == 1) {
                    intervals.add(subIntervals.get(0));
                } else {
                    throw new IllegalArgumentException("Cannot have 2 intervals on same column");
                }
            }
            LOGGER.info("{}Add missing rule '{}'", indent, intervals);

            rules.add(new Rule(intervals));
        }
    }

    @Override
    public String toString() {
        return rules.stream().map(Rule::toString).collect(Collectors.joining(" | "));
    }
}
