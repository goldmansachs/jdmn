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

import static org.junit.Assert.*;

public class IntervalTest {
    private final Input numericInput = new Input("number");
    private final Input booleanInput = new Input("boolean", EnumerationInterval.BOOLEAN_ALLOWED_VALUES);
    private final Input enumerationInput = new Input("string", Arrays.asList("E1", "E2", "E3"));

    @Test(expected = DMNRuntimeException.class)
    public void testSameValueWhenNull() {
        NumericInterval n1 = new NumericInterval(0, 0, numericInput);

        assertTrue(Interval.sameEndValues(null, null));
        assertTrue(Interval.sameEndValues(null, n1));
        assertTrue(Interval.sameEndValues(n1, null));
    }

    @Test
    public void testSameEndValues() {
        NumericInterval n1 = new NumericInterval(0, 0, numericInput);
        NumericInterval n2 = new NumericInterval(0, 0, numericInput, true, 0, false, 5.6);
        NumericInterval n3 = new NumericInterval(0, 0, numericInput, true, 0, true, 5.6);

        assertTrue(Interval.sameEndValues(n1, n1));
        assertFalse(Interval.sameEndValues(n1, n2));
        assertFalse(Interval.sameEndValues(n2, n3));
    }

    @Test
    public void testAreAdjacentWhenNull() {
        NumericInterval n1 = new NumericInterval(0, 0, numericInput);

        assertFalse(Interval.areAdjacent(null, null));
        assertFalse(Interval.areAdjacent(null, n1));
        assertFalse(Interval.areAdjacent(n1, null));
    }

    @Test
    public void testAreAdjacent() {
        NumericInterval n1 = new NumericInterval(0, 0, numericInput);
        NumericInterval n2 = new NumericInterval(0, 0, numericInput, true, 0, false, 5.6);
        NumericInterval n3 = new NumericInterval(0, 0, numericInput, true, 5.6, true, 6.6);

        assertFalse(Interval.areAdjacent(n1, n1));
        assertFalse(Interval.areAdjacent(n1, n2));
        assertTrue(Interval.areAdjacent(n2, n3));
    }

    @Test
    public void serialize() {
        assertEquals("[-Infinity, +Infinity]", new NumericInterval(0, 0, numericInput).serialize());
        assertEquals("(0, 5.6]", new NumericInterval(0, 0, numericInput, true, 0, false, 5.6).serialize());

        assertEquals("{false, true}", new EnumerationInterval(0, 0, booleanInput).serialize());
        assertEquals("{false}", new EnumerationInterval(0, 0, booleanInput, "false").serialize());
        assertEquals("{true}", new EnumerationInterval(0, 0, booleanInput, "true").serialize());
        assertEquals("{false}", new EnumerationInterval(0, 0, booleanInput, false, 0, true, 1).serialize());
        assertEquals("{true}", new EnumerationInterval(0, 0, booleanInput, false, 1, true, 2).serialize());
        assertEquals("{false, true}", new EnumerationInterval(0, 0, booleanInput, false, 0, true, 2).serialize());

        assertEquals("{E1, E2, E3}", new EnumerationInterval(0, 0, enumerationInput).serialize());
        assertEquals("{E1}", new EnumerationInterval(0, 0, enumerationInput, "E1").serialize());
        assertEquals("{E2}", new EnumerationInterval(0, 0, enumerationInput, "E2").serialize());
        assertEquals("{E1}", new EnumerationInterval(0, 0, enumerationInput, false, 0, true, 1).serialize());
        assertEquals("{E2}", new EnumerationInterval(0, 0, enumerationInput, false, 1, true, 2).serialize());
        assertEquals("{E1, E2}", new EnumerationInterval(0, 0, enumerationInput, false, 0, true, 2).serialize());
    }
}