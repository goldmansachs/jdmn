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

import org.junit.Test;

import static org.junit.Assert.*;

public class MissingRuleListTest {
    @Test
    public void testAddWhenNoMissingIntervals() {
        checkAdd("[]", new MissingRuleList(), 1, 2, null);
    }

    @Test
    public void testAddWhenNotEnoughColumns() {
        checkAdd("[]", 0, 2, makeMissingIntervals(makeInterval(true, 0, true, 1)));
    }

    @Test
    public void testAddWhenEnoughColumns() {
        checkAdd("[[(0, 1)]]", 0, 1, makeMissingIntervals(makeInterval(true, 0, true, 1)));
        checkAdd("[[(0, 1), (2, 3)]]", 1, 2, makeMissingIntervals(makeInterval(true, 0, true, 1), makeInterval(true, 2, true, 3)));
    }

    private void checkAdd(String expectedList, MissingRuleList ruleList, int columnIndex, int totalNumberOfColumns, MissingIntervals missingIntervals) {
        ruleList.add(columnIndex, totalNumberOfColumns, missingIntervals);
        assertEquals(expectedList, ruleList.getRules().toString());
    }

    private void checkAdd(String expectedList, int columnIndex, int totalNumberOfColumns, MissingIntervals missingIntervals) {
        MissingRuleList ruleList = new MissingRuleList();
        ruleList.add(columnIndex, totalNumberOfColumns, missingIntervals);
        assertEquals(expectedList, ruleList.getRules().toString());
    }

    private MissingRuleList makeRuleList(Interval... intervals) {
        MissingRuleList ruleList = new MissingRuleList();
        int length = intervals.length;
        ruleList.add(length - 1, length, makeMissingIntervals(intervals));
        return ruleList;
    }

    private MissingIntervals makeMissingIntervals(Interval... intervals) {
        MissingIntervals missingIntervals = new MissingIntervals();
        for (int i=0; i<intervals.length; i++) {
            missingIntervals.addMissingInterval(i, intervals[i]);
        }
        return missingIntervals;
    }

    private Interval makeInterval(boolean openStart, Number start, boolean openEnd, Number end) {
        return new NumericInterval(-1, 0, null, openStart, start, openEnd, end);
    }
}