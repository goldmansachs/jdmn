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

import com.gs.dmn.runtime.Range;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractDefaultRangeLibTest {
    private final DefaultRangeLib rangeLib = new DefaultRangeLib();

    @Test
    public void testBefore() {
        assertNull(rangeLib.before((Number) null, (Number) null));
        assertNull(rangeLib.before((Number) null, (Range) null));
        assertNull(rangeLib.before((Range) null, (Number) null));
        assertNull(rangeLib.before((Range) null, (Range) null));

        assertTrue(rangeLib.before(makePoint(1), makePoint(10)));
        assertFalse(rangeLib.before(makePoint(10), makePoint(1)));
        assertFalse(rangeLib.before( makePoint(1), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.before( makePoint(1), makeRange("(", 1, 10, "]") ));
        assertTrue(rangeLib.before( makePoint(1), makeRange("[", 5, 10, "]") ));
        assertFalse(rangeLib.before( makeRange("[", 1, 10, "]"), makePoint(10) ));
        assertTrue(rangeLib.before( makeRange("[", 1, 10, ")"), makePoint(10) ));
        assertTrue(rangeLib.before( makeRange("[", 1, 10, "]"), makePoint(15) ));
        assertTrue(rangeLib.before( makeRange("[", 1, 10, "]"), makeRange("[", 15, 20, "]") ));
        assertFalse(rangeLib.before( makeRange("[", 1, 10, "]"), makeRange("[", 10, 20, "]") ));
        assertTrue(rangeLib.before( makeRange("[", 1, 10, ")"), makeRange("[", 10, 20, "]") ));
        assertTrue(rangeLib.before( makeRange("[", 1, 10, "]"), makeRange("(", 10, 20, "]") ));
    }

    @Test
    public void testAfter() {
        assertNull(rangeLib.after((Number) null, (Number) null));
        assertNull(rangeLib.after((Number) null, (Range) null));
        assertNull(rangeLib.after((Range) null, (Number) null));
        assertNull(rangeLib.after((Range) null, (Range) null));

        assertTrue(rangeLib.after(makePoint(10), makePoint(5)));
        assertFalse(rangeLib.after(makePoint(5), makePoint(10)));
        assertTrue(rangeLib.after( makePoint(12), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.after( makePoint(10), makeRange("[", 1, 10, ")") ));
        assertFalse(rangeLib.after( makePoint(10), makeRange("[", 1, 10, "]") ));
        assertFalse(rangeLib.after( makeRange("[", 11, 20, "]"), makePoint(12) ));
        assertTrue(rangeLib.after( makeRange("[", 11, 20, "]"), makePoint(10) ));
        assertTrue(rangeLib.after( makeRange("(", 11, 20, "]"), makePoint(11) ));
        assertFalse(rangeLib.after( makeRange("[", 11, 20, "]"), makePoint(11) ));
        assertTrue(rangeLib.after( makeRange("[", 11, 20, "]"), makeRange("[", 1, 10, "]") ));
        assertFalse(rangeLib.after( makeRange("[", 1, 10, "]"), makeRange("[", 11, 20, "]") ));
        assertTrue(rangeLib.after( makeRange("[", 11, 20, "]"), makeRange("[", 1, 11, ")") ));
        assertTrue(rangeLib.after( makeRange("(", 11, 20, "]"), makeRange("[", 1, 11, "]") ));
    }

    @Test
    public void testMeets() {
        assertNull(rangeLib.meets(null, null));

        assertTrue(rangeLib.meets( makeRange("[", 1, 5, "]"), makeRange("[", 5, 10, "]") ));
        assertFalse(rangeLib.meets( makeRange("[", 1, 5, ")"), makeRange("[", 5, 10, "]") ));
        assertFalse(rangeLib.meets( makeRange("[", 1, 5, "]"), makeRange("(", 5, 10, "]") ));
        assertFalse(rangeLib.meets( makeRange("[", 1, 5, "]"), makeRange("[", 6, 10, "]") ));
    }

    @Test
    public void testMetBy() {
        assertNull(rangeLib.metBy(null, null));

        assertTrue(rangeLib.metBy( makeRange("[", 5, 10, "]"), makeRange("[", 1, 5, "]") ));
        assertFalse(rangeLib.metBy( makeRange("[", 5, 10, "]"), makeRange("[", 1, 5, ")") ));
        assertFalse(rangeLib.metBy( makeRange("(", 5, 10, "]"), makeRange("[", 1, 5, "]") ));
        assertFalse(rangeLib.metBy( makeRange("[", 6, 10, "]"), makeRange("[", 1, 5, "]") ));
    }

    @Test
    public void testOverlaps() {
        assertNull(rangeLib.overlaps(null, null));

        assertTrue(rangeLib.overlaps( makeRange("[", 1, 5, "]"), makeRange("[", 3, 8, "]") ));
        assertTrue(rangeLib.overlaps( makeRange("[", 3, 8, "]"), makeRange("[", 1, 5, "]") ));
        assertTrue(rangeLib.overlaps( makeRange("[", 1, 8, "]"), makeRange("[", 3, 5, "]") ));
        assertTrue(rangeLib.overlaps( makeRange("[", 3, 5, "]"), makeRange("[", 1, 8, "]") ));
        assertFalse(rangeLib.overlaps( makeRange("[", 1, 5, "]"), makeRange("[", 6, 8, "]") ));
        assertFalse(rangeLib.overlaps( makeRange("[", 6, 8, "]"), makeRange("[", 1, 5, "]") ));
        assertTrue(rangeLib.overlaps( makeRange("[", 1, 5, "]"), makeRange("[", 5, 8, "]") ));
        assertFalse(rangeLib.overlaps( makeRange("[", 1, 5, "]"), makeRange("(", 5, 8, "]") ));
        assertFalse(rangeLib.overlaps( makeRange("[", 1, 5, ")"), makeRange("[", 5, 8, "]") ));
        assertFalse(rangeLib.overlaps( makeRange("[", 1, 5, ")"), makeRange("(", 5, 8, "]") ));
        assertTrue(rangeLib.overlaps( makeRange("[", 5, 8, "]"), makeRange("[", 1, 5, "]") ));
        assertFalse(rangeLib.overlaps( makeRange("(", 5, 8, "]"), makeRange("[", 1, 5, "]") ));
        assertFalse(rangeLib.overlaps( makeRange("[", 5, 8, "]"), makeRange("[", 1, 5, ")") ));
        assertFalse(rangeLib.overlaps( makeRange("(", 5, 8, "]"), makeRange("[", 1, 5, ")") ));
    }

    @Test
    public void testOverlapsBefore() {
        assertNull(rangeLib.overlapsBefore(null, null));

        assertTrue(rangeLib.overlapsBefore( makeRange("[", 1, 5, "]"), makeRange("[", 3, 8, "]") ));
        assertFalse(rangeLib.overlapsBefore( makeRange("[", 1, 5, "]"), makeRange("[", 6, 8, "]") ));
        assertTrue(rangeLib.overlapsBefore( makeRange("[", 1, 5, "]"), makeRange("[", 5, 8, "]") ));
        assertFalse(rangeLib.overlapsBefore( makeRange("[", 1, 5, "]"), makeRange("(", 5, 8, "]") ));
        assertFalse(rangeLib.overlapsBefore( makeRange("[", 1, 5, ")"), makeRange("[", 5, 8, "]") ));
        assertTrue(rangeLib.overlapsBefore( makeRange("[", 1, 5, ")"), makeRange("(", 1, 5, "]") ));
        assertTrue(rangeLib.overlapsBefore( makeRange("[", 1, 5, "]"), makeRange("(", 1, 5, "]") ));
        assertFalse(rangeLib.overlapsBefore( makeRange("[", 1, 5, ")"), makeRange("[", 1, 5, "]") ));
        assertFalse(rangeLib.overlapsBefore( makeRange("[", 1, 5, "]"), makeRange("[", 1, 5, "]") ));
    }

    @Test
    public void testOverlapsAfter() {
        assertNull(rangeLib.overlapsAfter(null, null));

        assertTrue(rangeLib.overlapsAfter( makeRange("[", 3, 8, "]"), makeRange("[", 1, 5, "]")));
        assertFalse(rangeLib.overlapsAfter( makeRange("[", 6, 8, "]"), makeRange("[", 1, 5, "]")));
        assertTrue(rangeLib.overlapsAfter( makeRange("[", 5, 8, "]"), makeRange("[", 1, 5, "]")));
        assertFalse(rangeLib.overlapsAfter( makeRange("(", 5, 8, "]"), makeRange("[", 1, 5, "]")));
        assertFalse(rangeLib.overlapsAfter( makeRange("[", 5, 8, "]"), makeRange("[", 1, 5, ")")));
        assertTrue(rangeLib.overlapsAfter( makeRange("(", 1, 5, "]"), makeRange("[", 1, 5, ")") ));
        assertTrue(rangeLib.overlapsAfter( makeRange("(", 1, 5, "]"), makeRange("[", 1, 5, "]") ));
        assertFalse(rangeLib.overlapsAfter( makeRange("[", 1, 5, "]"), makeRange("[", 1, 5, ")") ));
        assertFalse(rangeLib.overlapsAfter( makeRange("[", 1, 5, "]"), makeRange("[", 1, 5, "]") ));
    }

    @Test
    public void testFinishes() {
        assertNull(rangeLib.finishes((Number) null, (Range) null));
        assertNull(rangeLib.finishes((Range) null, (Range) null));

        assertTrue(rangeLib.finishes( makePoint(10), makeRange("[", 1, 10, "]") ));
        assertFalse(rangeLib.finishes( makePoint(10), makeRange("[", 1, 10, ")") ));
        assertTrue(rangeLib.finishes( makeRange("[", 5, 10, "]"), makeRange("[", 1, 10, "]") ));
        assertFalse(rangeLib.finishes( makeRange("[", 5, 10, ")"), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.finishes( makeRange("[", 5, 10, ")"), makeRange("[", 1, 10, ")") ));
        assertTrue(rangeLib.finishes( makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.finishes( makeRange("(", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
    }

    @Test
    public void testFinishedBy() {
        assertNull(rangeLib.finishedBy((Range) null, (Number) null));
        assertNull(rangeLib.finishedBy((Range) null, (Range) null));

        assertTrue(rangeLib.finishedBy( makeRange("[", 1, 10, "]"), makePoint(10) ));
        assertFalse(rangeLib.finishedBy( makeRange("[", 1, 10, ")"), makePoint(10) ));
        assertTrue(rangeLib.finishedBy( makeRange("[", 1, 10, "]"), makeRange("[", 5, 10, "]") ));
        assertFalse(rangeLib.finishedBy( makeRange("[", 1, 10, "]"), makeRange("[", 5, 10, ")") ));
        assertTrue(rangeLib.finishedBy( makeRange("[", 1, 10, ")"), makeRange("[", 5, 10, ")") ));
        assertTrue(rangeLib.finishedBy( makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.finishedBy( makeRange("[", 1, 10, "]"), makeRange("(", 1, 10, "]") ));
    }

    @Test
    public void testIncludes() {
        assertNull(rangeLib.includes((Range) null, (Number) null));
        assertNull(rangeLib.includes((Range) null, (Range) null));

        assertTrue(rangeLib.includes( makeRange("[", 1, 10, "]"), makePoint(5) ));
        assertFalse(rangeLib.includes( makeRange("[", 1, 10, "]"), makePoint(12) ));
        assertTrue(rangeLib.includes( makeRange("[", 1, 10, "]"), makePoint(1) ));
        assertTrue(rangeLib.includes( makeRange("[", 1, 10, "]"), makePoint(10) ));
        assertFalse(rangeLib.includes( makeRange("(", 1, 10, "]"), makePoint(1) ));
        assertFalse(rangeLib.includes( makeRange("[", 1, 10, ")"), makePoint(10) ));
        assertTrue(rangeLib.includes( makeRange("[", 1, 10, "]"), makeRange("[", 4, 6, "]") ));
        assertTrue(rangeLib.includes( makeRange("[", 1, 10, "]"), makeRange("[", 1, 5, "]") ));
        assertTrue(rangeLib.includes( makeRange("(", 1, 10, "]"), makeRange("(", 1, 5, "]") ));
        assertTrue(rangeLib.includes( makeRange("[", 1, 10, "]"), makeRange("(", 1, 10, ")") ));
        assertTrue(rangeLib.includes( makeRange("[", 1, 10, ")"), makeRange("[", 5, 10, ")") ));
        assertTrue(rangeLib.includes( makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, ")") ));
        assertTrue(rangeLib.includes( makeRange("[", 1, 10, "]"), makeRange("(", 1, 10, "]") ));
        assertTrue(rangeLib.includes( makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
    }

    @Test
    public void testDuring() {
        assertNull(rangeLib.during((Number) null, (Range) null));
        assertNull(rangeLib.during((Range) null, (Range) null));

        assertTrue(rangeLib.during( makePoint(5), makeRange("[", 1, 10, "]") ));
        assertFalse(rangeLib.during( makePoint(12), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.during( makePoint(1), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.during( makePoint(10), makeRange("[", 1, 10, "]") ));
        assertFalse(rangeLib.during( makePoint(1), makeRange("(", 1, 10, "]") ));
        assertFalse(rangeLib.during( makePoint(10), makeRange("[", 1, 10, ")") ));
        assertTrue(rangeLib.during( makeRange("[", 4, 6, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.during( makeRange("[", 1, 5, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.during( makeRange("(", 1, 5, "]"), makeRange("(", 1, 10, "]") ));
        assertTrue(rangeLib.during( makeRange("(", 1, 10, ")"), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.during( makeRange("[", 5, 10, ")"), makeRange("[", 1, 10, ")") ));
        assertTrue(rangeLib.during( makeRange("[", 1, 10, ")"), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.during( makeRange("(", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.during( makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
    }

    @Test
    public void testStarts() {
        assertNull(rangeLib.starts((Number) null, (Range) null));
        assertNull(rangeLib.starts((Range) null, (Range) null));

        assertTrue(rangeLib.starts( makePoint(1), makeRange("[", 1, 10, "]") ));
        assertFalse(rangeLib.starts( makePoint(1), makeRange("(", 1, 10, "]") ));
        assertFalse(rangeLib.starts( makePoint(2), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.starts( makeRange("[", 1, 5, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.starts( makeRange("(", 1, 5, "]"), makeRange("(", 1, 10, "]") ));
        assertFalse(rangeLib.starts( makeRange("(", 1, 5, "]"), makeRange("[", 1, 10, "]") ));
        assertFalse(rangeLib.starts( makeRange("[", 1, 5, "]"), makeRange("(", 1, 10, "]") ));
        assertTrue(rangeLib.starts( makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.starts( makeRange("[", 1, 10, ")"), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.starts( makeRange("(", 1, 10, ")"), makeRange("(", 1, 10, ")") ));
    }

    @Test
    public void testStartedBy() {
        assertNull(rangeLib.startedBy((Range) null, (Number) null));
        assertNull(rangeLib.startedBy((Range) null, (Range) null));

        assertTrue(rangeLib.startedBy( makeRange("[", 1, 10, "]"), makePoint(1)));
        assertFalse(rangeLib.startedBy( makeRange("(", 1, 10, "]"), makePoint(1)));
        assertFalse(rangeLib.startedBy( makeRange("[", 1, 10, "]"), makePoint(2)));
        assertTrue(rangeLib.startedBy( makeRange("[", 1, 10, "]"), makeRange("[", 1, 5, "]") ));
        assertTrue(rangeLib.startedBy( makeRange("(", 1, 10, "]"), makeRange("(", 1, 5, "]") ));
        assertFalse(rangeLib.startedBy( makeRange("[", 1, 10, "]"), makeRange("(", 1, 5, "]") ));
        assertFalse(rangeLib.startedBy( makeRange("(", 1, 10, "]"), makeRange("[", 1, 5, "]") ));
        assertTrue(rangeLib.startedBy( makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, "]") ));
        assertTrue(rangeLib.startedBy( makeRange("[", 1, 10, "]"), makeRange("[", 1, 10, ")") ));
        assertTrue(rangeLib.startedBy( makeRange("(", 1, 10, ")"), makeRange("(", 1, 10, ")") ));    }

    @Test
    public void testCoincides() {
        assertNull(rangeLib.coincides((Number) null, (Number) null));
        assertNull(rangeLib.coincides((Range) null, (Range) null));

        assertTrue(rangeLib.coincides(makePoint(5), makePoint(5)));
        assertFalse(rangeLib.coincides(makePoint(3), makePoint(4)));
        assertTrue(rangeLib.coincides( makeRange("[", 1, 5, "]"), makeRange("[", 1, 5, "]") ));
        assertFalse(rangeLib.coincides( makeRange("(", 1, 5, ")"), makeRange("[", 1, 5, "]") ));
        assertFalse(rangeLib.coincides( makeRange("[", 1, 5, "]"), makeRange("[", 2, 6, "]") ));
    }

    private Range makeRange(String startIncluded, int start, int end, String endIncluded) {
        return new Range(startIncluded.equals("["), makePoint(start), endIncluded.equals("]"), makePoint(end));
    }

    protected  abstract Object makePoint(int number);
}