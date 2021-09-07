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

import java.util.ArrayList;
import java.util.List;

public class BoundList {
    private final List<Bound> bounds = new ArrayList<>();

    public BoundList(List<Integer> comparingRules, int columnIndex, Table table) {
        List<Rule> rules = table.getRules();
        for (int ruleIndex: comparingRules) {
            Rule rule = rules.get(ruleIndex);
            Interval interval = rule.getInterval(columnIndex);
            this.bounds.add(interval.getLowerBound());
            this.bounds.add(interval.getUpperBound());
        }
    }

    public List<Bound> getBounds() {
        return bounds;
    }

    public void sort() {
        this.bounds.sort(Bound.COMPARATOR);
    }
}
