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
package com.gs.dmn.runtime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RangeTest {
    @Test
    public void testConstructor() {
        // Endpoints Range
        Range<Integer> r1 = new Range<>(true, 3, false, 4);
        assertTrue(r1.isStartIncluded());
        assertEquals(3, r1.getStart());
        assertFalse(r1.isEndIncluded());
        assertEquals(4, r1.getEnd());
        assertNull(r1.getOperator());

        // Assert exception being thrown
        Assertions.assertThrows(DMNRuntimeException.class, () -> {
            new Range<>(true, 4, false, 3);
        });


        // Operator Range
        Range<Integer> r2 = new Range<>("<", 4);
        assertFalse(r2.isStartIncluded());
        assertNull(r2.getStart());
        assertFalse(r2.isEndIncluded());
        assertEquals(4, r2.getEnd());
        assertEquals("<", r2.getOperator());
    }
}