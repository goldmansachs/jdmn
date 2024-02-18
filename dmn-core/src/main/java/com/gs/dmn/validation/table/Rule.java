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
import java.util.stream.Collectors;

public class Rule {
    private final List<Interval> intervals = new ArrayList<>();

    public Rule(List<? extends Interval> intervals) {
        if (intervals != null) {
            this.intervals.addAll(intervals);
        }
    }

    public List<Interval> getIntervals() {
        return intervals;
    }

    public Interval getInterval(int columnIndex) {
        return intervals.get(columnIndex);
    }

    public int findColumnToMerge(MissingIntervals missingIntervals) {
        if (missingIntervals == null) {
            return -1;
        }
        if (intervals.size() != missingIntervals.size()) {
            return -1;
        }
        List<Integer> columnsToMerge = new ArrayList<>();
        for (int i=0; i<intervals.size(); i++) {
            List<Interval> otherIntervals = missingIntervals.getIntervals(i);
            Interval thisInterval = intervals.get(i);
            if (otherIntervals.size() == 1) {
                Interval otherInterval = otherIntervals.get(0);
                if (Interval.sameEndValues(thisInterval, otherInterval)) {
                } else if (Interval.areAdjacent(thisInterval, otherInterval)) {
                    columnsToMerge.add(i);
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        }
        return columnsToMerge.size() == 1 ? columnsToMerge.get(0) : -1;
    }

    public void merge(Integer columnIndex, Interval otherInterval) {
        Interval thisInterval = intervals.get(columnIndex);
        thisInterval.merge(otherInterval);
    }

    @Override
    public String toString() {
        return "[%s]".formatted(intervals.stream().map(i -> i == null ? null : i.toString()).collect(Collectors.joining(", ")));
    }
}
