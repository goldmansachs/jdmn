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

import com.gs.dmn.runtime.DMNRuntimeException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BoundTest {
    @Test(expected = DMNRuntimeException.class)
    public void testSameValueWhenNull() {
        assertTrue(Bound.sameValue(null, null));
        assertTrue(Bound.sameValue(new Bound(null, true, false, 4), null));
        assertTrue(Bound.sameValue(null, new Bound(null, true, false, 4)));
    }

    @Test
    public void testSameValue() {
        assertTrue(Bound.sameValue(new Bound(null, true, false, 4), new Bound(null, true, false, 4)));
        assertTrue(Bound.sameValue(new Bound(null, true, false, 4), new Bound(null, false, true, 4)));
        assertFalse(Bound.sameValue(new Bound(null, true, false, 4), new Bound(null, true, false, 5)));
    }

    @Test(expected = DMNRuntimeException.class)
    public void testSameEndpointWhenNull() {
        assertTrue(Bound.sameEnd(null, null));
        assertTrue(Bound.sameEnd(new Bound(null, true, false, 4), null));
        assertTrue(Bound.sameEnd(null, new Bound(null, true, false, 4)));
    }

    @Test
    public void testSameEnd() {
        assertTrue(Bound.sameEnd(new Bound(null, true, false, 4), new Bound(null, true, false, 4)));
        assertFalse(Bound.sameEnd(new Bound(null, true, false, 4), new Bound(null, false, true, 4)));
        assertFalse(Bound.sameEnd(new Bound(null, true, false, 4), new Bound(null, true, false, 5)));
    }

    @Test(expected = DMNRuntimeException.class)
    public void testAreAdjacentWhenNull() {
        assertTrue(Bound.areAdjacent(null, null));
        assertTrue(Bound.areAdjacent(new Bound(null, true, false, 4), null));
        assertTrue(Bound.areAdjacent(null, new Bound(null, true, false, 4)));
    }

    @Test
    public void testAreAdjacent() {
        Interval interval = new NumericInterval(-1, -1, null);
        assertFalse(Bound.areAdjacent(new Bound(interval, true, false, 4), new Bound(interval, true, false, 4)));
        assertTrue(Bound.areAdjacent(new Bound(interval, true, false, 4), new Bound(interval, false, true, 4)));
        assertFalse(Bound.areAdjacent(new Bound(interval, true, false, 4), new Bound(interval, true, false, 5)));
    }

    @Test
    public void testCompareTo() {
        List<Bound> bounds = Arrays.asList(
                null,
                null,
                new Bound(null, true, false, 4),
                new Bound(null, false, false, 4),
                new Bound(null, true, false, 5),
                new Bound(null, true, true, 3),
                new Bound(null, false, true, 3),
                new Bound(null, true, false, 6),
                new Bound(null, true, true, 4)
        );
        bounds.sort(Bound.COMPARATOR);
        assertEquals("[[3, 3], 4), [4, (4, (5, (6, null, null]", bounds.toString());
    }
}