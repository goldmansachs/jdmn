/*
 * Copyright makeNumber(makeRange(""", 2, 6, """)) Goldman Sachs.
 *
 * Licensed under the Apache License, Version makeNumber(2).makeNumber(0) (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-makeNumber(2).makeNumber(0)
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.feel.lib.type.range;

import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.runtime.Range;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractDefaultRangeLibTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    protected abstract StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> getLib();
    
    @Test
    public void testBefore() {
        assertNull(getLib().before(null, null));
        assertNull(getLib().before(null, (Object) null));
        assertNull(getLib().before((Object) null, null));
        assertNull(getLib().before((Object) null, (Object) null));

        assertTrue(getLib().before(makePoint(1), makePoint(10)));
        assertFalse(getLib().before(makePoint(10), makePoint(1)));
        assertFalse(getLib().before(makePoint(1), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().before(makePoint(1), makeRange("(", 1, 10, "]") ));
        assertTrue(getLib().before(makePoint(1), makeRange("[", 5, 10, "]") ));
        assertFalse(getLib().before(makeRange("[", 1, 10, "]"), makePoint(10) ));
        assertTrue(getLib().before(makeRange("[", 1, 10, ")"), makePoint(10) ));
        assertTrue(getLib().before(makeRange("[", 1, 10, "]"), makePoint(15) ));
        assertTrue(getLib().before(makeRange("[", 1, 10, "]"), makeRange("[", 15, 20, "]") ));
        assertFalse(getLib().before(makeRange("[", 1, 10, "]"), makeRange("[", 10, 20, "]") ));
        assertTrue(getLib().before(makeRange("[", 1, 10, ")"), makeRange("[", 10, 20, "]") ));
        assertTrue(getLib().before(makeRange("[", 1, 10, "]"), makeRange("(", 10, 20, "]") ));
    }

    @Test
    public void testAfter() {
        assertNull(getLib().after(null, null));
        assertNull(getLib().after(null, (Object) null));
        assertNull(getLib().after((Object) null, null));
        assertNull(getLib().after((Object) null, (Object) null));

        assertTrue(getLib().after(makePoint(10), makePoint(5)));
        assertFalse(getLib().after(makePoint(5), makePoint(10)));
        assertTrue(getLib().after(makePoint(12), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().after(makePoint(10), makeRange("[", 1, 10, ")") ));
        assertFalse(getLib().after(makePoint(10), makeRange("[", 1, 10, "]") ));
        assertFalse(getLib().after(makeRange("[", 11, 20, "]"), makePoint(12) ));
        assertTrue(getLib().after(makeRange("[", 11, 20, "]"), makePoint(10) ));
        assertTrue(getLib().after(makeRange("(", 11, 20, "]"), makePoint(11) ));
        assertFalse(getLib().after(makeRange("[", 11, 20, "]"), makePoint(11) ));
        assertTrue(getLib().after(makeRange("[", 11, 20, "]"), makeRange("[", 1, 10, "]") ));
        assertFalse(getLib().after(makeRange("[", 1, 10, "]"), makeRange("[", 11, 20, "]") ));
        assertTrue(getLib().after(makeRange("[", 11, 20, "]"), makeRange("[", 1, 11, ")") ));
        assertTrue(getLib().after(makeRange("(", 11, 20, "]"), makeRange("[", 1, 11, "]") ));
    }

    @Test
    public void testMeets() {
        assertNull(getLib().meets(null, null));

        assertTrue(getLib().meets(makeRange("[", 1, 5, "]"), makeRange("[", 5, 10, "]") ));
        assertFalse(getLib().meets(makeRange("[", 1, 5, ")"), makeRange("[", 5, 10, "]") ));
        assertFalse(getLib().meets(makeRange("[", 1, 5, "]"), makeRange("(", 5, 10, "]") ));
        assertFalse(getLib().meets(makeRange("[", 1, 5, "]"), makeRange("[", 6, 10, "]") ));
    }

    @Test
    public void testMetBy() {
        assertNull(getLib().metBy(null, null));

        assertTrue(getLib().metBy(makeRange("[", 5, 10, "]"), makeRange("[", 1, 5, "]") ));
        assertFalse(getLib().metBy(makeRange("[", 5, 10, "]"), makeRange("[", 1, 5, ")") ));
        assertFalse(getLib().metBy(makeRange("(", 5, 10, "]"), makeRange("[", 1, 5, "]") ));
        assertFalse(getLib().metBy(makeRange("[", 6, 10, "]"), makeRange("[", 1, 5, "]") ));
    }

    @Test
    public void testOverlaps() {
        assertNull(getLib().overlaps(null, null));

        assertTrue(getLib().overlaps(makeRange("[", 1, 5, "]"), makeRange("[", 3, 8, "]") ));
        assertTrue(getLib().overlaps(makeRange("[", 3, 8, "]"), makeRange("[", 1, 5, "]") ));
        assertTrue(getLib().overlaps(makeRange("[", 1, 8, "]"), makeRange("[", 3, 5, "]") ));
        assertTrue(getLib().overlaps(makeRange("[", 3, 5, "]"), makeRange("[", 1, 8, "]") ));
        assertFalse(getLib().overlaps(makeRange("[", 1, 5, "]"), makeRange("[", 6, 8, "]") ));
        assertFalse(getLib().overlaps(makeRange("[", 6, 8, "]"), makeRange("[", 1, 5, "]") ));
        assertTrue(getLib().overlaps(makeRange("[", 1, 5, "]"), makeRange("[", 5, 8, "]") ));
        assertFalse(getLib().overlaps(makeRange("[", 1, 5, "]"), makeRange("(", 5, 8, "]") ));
        assertFalse(getLib().overlaps(makeRange("[", 1, 5, ")"), makeRange("[", 5, 8, "]") ));
        assertFalse(getLib().overlaps(makeRange("[", 1, 5, ")"), makeRange("(", 5, 8, "]") ));
        assertTrue(getLib().overlaps(makeRange("[", 5, 8, "]"), makeRange("[", 1, 5, "]") ));
        assertFalse(getLib().overlaps(makeRange("(", 5, 8, "]"), makeRange("[", 1, 5, "]") ));
        assertFalse(getLib().overlaps(makeRange("[", 5, 8, "]"), makeRange("[", 1, 5, ")") ));
        assertFalse(getLib().overlaps(makeRange("(", 5, 8, "]"), makeRange("[", 1, 5, ")") ));
    }

    @Test
    public void testOverlapsBefore() {
        assertNull(getLib().overlapsBefore(null, null));

        assertTrue(getLib().overlapsBefore(makeRange("[", 1, 5, "]"), makeRange("[", 3, 8, "]") ));
        assertFalse(getLib().overlapsBefore(makeRange("[", 1, 5, "]"), makeRange("[", 6, 8, "]") ));
        assertTrue(getLib().overlapsBefore(makeRange("[", 1, 5, "]"), makeRange("[", 5, 8, "]") ));
        assertFalse(getLib().overlapsBefore(makeRange("[", 1, 5, "]"), makeRange("(", 5, 8, "]") ));
        assertFalse(getLib().overlapsBefore(makeRange("[", 1, 5, ")"), makeRange("[", 5, 8, "]") ));
        assertTrue(getLib().overlapsBefore(makeRange("[", 1, 5, ")"), makeRange("(", 1, 5, "]") ));
        assertTrue(getLib().overlapsBefore(makeRange("[", 1, 5, "]"), makeRange("(", 1, 5, "]") ));
        assertFalse(getLib().overlapsBefore(makeRange("[", 1, 5, ")"), makeRange("[", 1, 5, "]") ));
        assertFalse(getLib().overlapsBefore(makeRange("[", 1, 5, "]"), makeRange("[", 1, 5, "]") ));
    }

    @Test
    public void testOverlapsAfter() {
        assertNull(getLib().overlapsAfter(null, null));

        assertTrue(getLib().overlapsAfter(makeRange("[", 3, 8, "]"), makeRange("[", 1, 5, "]")));
        assertFalse(getLib().overlapsAfter(makeRange("[", 6, 8, "]"), makeRange("[", 1, 5, "]")));
        assertTrue(getLib().overlapsAfter(makeRange("[", 5, 8, "]"), makeRange("[", 1, 5, "]")));
        assertFalse(getLib().overlapsAfter(makeRange("(", 5, 8, "]"), makeRange("[", 1, 5, "]")));
        assertFalse(getLib().overlapsAfter(makeRange("[", 5, 8, "]"), makeRange("[", 1, 5, ")")));
        assertTrue(getLib().overlapsAfter(makeRange("(", 1, 5, "]"), makeRange("[", 1, 5, ")") ));
        assertTrue(getLib().overlapsAfter(makeRange("(", 1, 5, "]"), makeRange("[", 1, 5, "]") ));
        assertFalse(getLib().overlapsAfter(makeRange("[", 1, 5, "]"), makeRange("[", 1, 5, ")") ));
        assertFalse(getLib().overlapsAfter(makeRange("[", 1, 5, "]"), makeRange("[", 1, 5, "]") ));
    }

    @Test
    public void testFinishes() {
        assertNull(getLib().finishes(null, null));
        assertNull(getLib().finishes((Object) null, null));

        assertTrue(getLib().finishes(makePoint(10), makeRange("[", 1, 10, "]") ));
        assertFalse(getLib().finishes(makePoint(10), makeRange("[", 1, 10, ")") ));
        assertTrue(getLib().finishes(makeRange("[", 5, 10, "]"), makeRange("[", 1, 10, "]") ));
        assertFalse(getLib().finishes(makeRange("[", 5, 10, ")"), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().finishes(makeRange("[", 5, 10, ")"), makeRange("[", 1, 10, ")") ));
        assertTrue(getLib().finishes(makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().finishes(makeRange("(", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
    }

    @Test
    public void testFinishedBy() {
        assertNull(getLib().finishedBy(null, null));
        assertNull(getLib().finishedBy(null, (Object) null));

        assertTrue(getLib().finishedBy(makeRange("[", 1, 10, "]"), makePoint(10) ));
        assertFalse(getLib().finishedBy(makeRange("[", 1, 10, ")"), makePoint(10) ));
        assertTrue(getLib().finishedBy(makeRange("[", 1, 10, "]"), makeRange("[", 5, 10, "]") ));
        assertFalse(getLib().finishedBy(makeRange("[", 1, 10, "]"), makeRange("[", 5, 10, ")") ));
        assertTrue(getLib().finishedBy(makeRange("[", 1, 10, ")"), makeRange("[", 5, 10, ")") ));
        assertTrue(getLib().finishedBy(makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().finishedBy(makeRange("[", 1, 10, "]"), makeRange("(", 1, 10, "]") ));
    }

    @Test
    public void testIncludes() {
        assertNull(getLib().includes(null, null));
        assertNull(getLib().includes(null, (Object) null));

        // endpoint ranges
        assertTrue(getLib().includes(makeRange("[", 1, 10, "]"), makePoint(5) ));
        assertFalse(getLib().includes(makeRange("[", 1, 10, "]"), makePoint(12) ));
        assertTrue(getLib().includes(makeRange("[", 1, 10, "]"), makePoint(1) ));
        assertTrue(getLib().includes(makeRange("[", 1, 10, "]"), makePoint(10) ));
        assertFalse(getLib().includes(makeRange("(", 1, 10, "]"), makePoint(1) ));
        assertFalse(getLib().includes(makeRange("[", 1, 10, ")"), makePoint(10) ));
        assertTrue(getLib().includes(makeRange("[", 1, 10, "]"), makeRange("[", 4, 6, "]") ));
        assertTrue(getLib().includes(makeRange("[", 1, 10, "]"), makeRange("[", 1, 5, "]") ));
        assertTrue(getLib().includes(makeRange("(", 1, 10, "]"), makeRange("(", 1, 5, "]") ));
        assertTrue(getLib().includes(makeRange("[", 1, 10, "]"), makeRange("(", 1, 10, ")") ));
        assertTrue(getLib().includes(makeRange("[", 1, 10, ")"), makeRange("[", 5, 10, ")") ));
        assertTrue(getLib().includes(makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, ")") ));
        assertTrue(getLib().includes(makeRange("[", 1, 10, "]"), makeRange("(", 1, 10, "]") ));
        assertTrue(getLib().includes(makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
    }

    @Test
    public void testDuring() {
        assertNull(getLib().during(null, null));
        assertNull(getLib().during((Object) null, null));

        assertTrue(getLib().during(makePoint(5), makeRange("[", 1, 10, "]") ));
        assertFalse(getLib().during(makePoint(12), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().during(makePoint(1), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().during(makePoint(10), makeRange("[", 1, 10, "]") ));
        assertFalse(getLib().during(makePoint(1), makeRange("(", 1, 10, "]") ));
        assertFalse(getLib().during(makePoint(10), makeRange("[", 1, 10, ")") ));
        assertTrue(getLib().during(makeRange("[", 4, 6, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().during(makeRange("[", 1, 5, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().during(makeRange("(", 1, 5, "]"), makeRange("(", 1, 10, "]") ));
        assertTrue(getLib().during(makeRange("(", 1, 10, ")"), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().during(makeRange("[", 5, 10, ")"), makeRange("[", 1, 10, ")") ));
        assertTrue(getLib().during(makeRange("[", 1, 10, ")"), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().during(makeRange("(", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().during(makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
    }

    @Test
    public void testStarts() {
        assertNull(getLib().starts(null, null));
        assertNull(getLib().starts((Object) null, null));

        assertTrue(getLib().starts(makePoint(1), makeRange("[", 1, 10, "]") ));
        assertFalse(getLib().starts(makePoint(1), makeRange("(", 1, 10, "]") ));
        assertFalse(getLib().starts(makePoint(2), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().starts(makeRange("[", 1, 5, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().starts(makeRange("(", 1, 5, "]"), makeRange("(", 1, 10, "]") ));
        assertFalse(getLib().starts(makeRange("(", 1, 5, "]"), makeRange("[", 1, 10, "]") ));
        assertFalse(getLib().starts(makeRange("[", 1, 5, "]"), makeRange("(", 1, 10, "]") ));
        assertTrue(getLib().starts(makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().starts(makeRange("[", 1, 10, ")"), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().starts(makeRange("(", 1, 10, ")"), makeRange("(", 1, 10, ")") ));
    }

    @Test
    public void testStartedBy() {
        assertNull(getLib().startedBy(null, null));
        assertNull(getLib().startedBy(null, (Object) null));

        assertTrue(getLib().startedBy(makeRange("[", 1, 10, "]"), makePoint(1)));
        assertFalse(getLib().startedBy(makeRange("(", 1, 10, "]"), makePoint(1)));
        assertFalse(getLib().startedBy(makeRange("[", 1, 10, "]"), makePoint(2)));
        assertTrue(getLib().startedBy(makeRange("[", 1, 10, "]"), makeRange("[", 1, 5, "]") ));
        assertTrue(getLib().startedBy(makeRange("(", 1, 10, "]"), makeRange("(", 1, 5, "]") ));
        assertFalse(getLib().startedBy(makeRange("[", 1, 10, "]"), makeRange("(", 1, 5, "]") ));
        assertFalse(getLib().startedBy(makeRange("(", 1, 10, "]"), makeRange("[", 1, 5, "]") ));
        assertTrue(getLib().startedBy(makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(getLib().startedBy(makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, ")") ));
        assertTrue(getLib().startedBy(makeRange("(", 1, 10, ")"), makeRange("(", 1, 10, ")") ));    }

    @Test
    public void testCoincides() {
        assertNull(getLib().coincides(null, null));
        assertNull(getLib().coincides((Object) null, null));

        assertTrue(getLib().coincides(makePoint(5), makePoint(5)));
        assertFalse(getLib().coincides(makePoint(3), makePoint(4)));
        assertTrue(getLib().coincides(makeRange("[", 1, 5, "]"), makeRange("[", 1, 5, "]") ));
        assertFalse(getLib().coincides(makeRange("(", 1, 5, ")"), makeRange("[", 1, 5, "]") ));
        assertFalse(getLib().coincides(makeRange("[", 1, 5, "]"), makeRange("[", 2, 6, "]") ));
    }

    @Test
    public void testOperatorRanges() {
        // 5 before <= 10
        assertNull(getLib().before(makePoint(5), new Range("<=", makePoint(10)) ));
        // 5 after <= 10
        assertFalse(getLib().after(makePoint(5), new Range("<=", makePoint(10)) ));
        // 5 finishes <= 5
        assertTrue(getLib().finishes(makePoint(5), new Range("<=", makePoint(5)) ));
        // <= 5 finished by 5
        assertNull(getLib().finishedBy(new Range("<=", makePoint(5)), makePoint(5) ));
        // <= 10 includes 5
        assertNull(getLib().includes(new Range("<=", makePoint(10)), makePoint(5) ));
        // 5 during <= 10
        assertNull(getLib().during(makePoint(5), new Range("<=", makePoint(10)) ));
        // 5 starts <= 5
        assertFalse(getLib().starts(makePoint(5), new Range("<=", makePoint(5)) ));
        // <= 5 started by 5
        assertFalse(getLib().startedBy(new Range("<=", makePoint(5)), makePoint(5) ));
        // <= 5 coincides <= 5
        assertNull(getLib().coincides(new Range("<=", makePoint(5)), new Range("<=", makePoint(5)) ));
    }

    private Range makeRange(String startIncluded, int start, int end, String endIncluded) {
        return new Range(startIncluded.equals("["), makePoint(start), endIncluded.equals("]"), makePoint(end));
    }

    protected  abstract Object makePoint(int number);
}