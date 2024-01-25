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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MissingRuleListTest {
    @Test
    public void testAddWhenNoMissingIntervals() {
        checkAddOrMerge("[]", new MissingRuleList(), 1, 2, null);
    }

    @Test
    public void testAddWhenNotEnoughColumns() {
        checkAddOrMerge("[]", 0, 2, makeMissingIntervals(makeInterval(true, 0, true, 1)));
    }

    @Test
    public void testAddWhenEnoughColumns() {
        checkAddOrMerge("[[(0, 1)]]", 0, 1, makeMissingIntervals(makeInterval(false, 0, false, 1)));
        checkAddOrMerge("[[(0, 1), (2, 3)]]", 1, 2, makeMissingIntervals(makeInterval(false, 0, false, 1), makeInterval(false, 2, false, 3)));
    }

    @Test
    public void testMerge() {
        MissingRuleList ruleList = makeRuleList(makeInterval(false, 0, false, 1), makeInterval(false, 2, false, 3));
        MissingRuleList ruleList1 = makeRuleList(makeInterval(false, 0, false, 1), makeInterval(false, 2, false, 3));
        MissingRuleList ruleList2 = makeRuleList(makeInterval(false, 0, false, 1), makeInterval(false, 2, false, 3));
        MissingRuleList ruleList3 = makeRuleList(makeInterval(false, 0, false, 1), makeInterval(false, 2, false, 3));
        MissingRuleList ruleList4 = makeRuleList(makeInterval(false, 0, false, 1), makeInterval(false, 2, false, 3));
        assertEquals("[(0, 1), (2, 3)]", ruleList.toString());

        checkAddOrMerge("[[(0, 1), (1.5, 3)]]", ruleList, 1, 2,
                makeMissingIntervals(makeInterval(false, 0, false, 1), makeInterval(false, 1.5, true, 2)));
        checkAddOrMerge("[[(0, 1), (2, 3)], [(0, 1), (3, 4)]]", ruleList1, 1, 2,
                makeMissingIntervals(makeInterval(false, 0, false, 1), makeInterval(false, 3, false, 4)));
        checkAddOrMerge("[[(0, 1), (2, 4)]]", ruleList2, 1, 2,
                makeMissingIntervals(makeInterval(false, 0, false, 1), makeInterval(true, 3, false, 4)));
        checkAddOrMerge("[[(0, 1), (2, 3)], [(1, 2), (2, 3)]]", ruleList3, 1, 2,
                makeMissingIntervals(makeInterval(false, 1, false, 2), makeInterval(false, 2, false, 3)));
        checkAddOrMerge("[[(0, 2), (2, 3)]]", ruleList4, 1, 2,
                makeMissingIntervals(makeInterval(true, 1, false, 2), makeInterval(false, 2, false, 3)));
    }

    private void checkAddOrMerge(String expectedList, MissingRuleList ruleList, int columnIndex, int totalNumberOfColumns, MissingIntervals missingIntervals) {
        ruleList.addOrMerge(columnIndex, totalNumberOfColumns, missingIntervals);
        assertEquals(expectedList, ruleList.getRules().toString());
    }

    private void checkAddOrMerge(String expectedList, int columnIndex, int totalNumberOfColumns, MissingIntervals missingIntervals) {
        MissingRuleList ruleList = new MissingRuleList();
        ruleList.addOrMerge(columnIndex, totalNumberOfColumns, missingIntervals);
        assertEquals(expectedList, ruleList.getRules().toString());
    }

    private MissingRuleList makeRuleList(Interval... intervals) {
        MissingRuleList ruleList = new MissingRuleList();
        int length = intervals.length;
        ruleList.addOrMerge(length - 1, length, makeMissingIntervals(intervals));
        return ruleList;
    }

    private MissingIntervals makeMissingIntervals(Interval... intervals) {
        MissingIntervals missingIntervals = new MissingIntervals();
        for (int i=0; i<intervals.length; i++) {
            missingIntervals.addMissingInterval(i, intervals[i]);
        }
        return missingIntervals;
    }

    private Interval makeInterval(boolean startIncluded, Number start, boolean endIncluded, Number end) {
        return new NumericInterval(-1, 0, null, startIncluded, start, endIncluded, end);
    }
}