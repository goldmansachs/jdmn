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
    public void testDefaultConstructor() {
        // Endpoints Range
        Range<Integer> r = new Range<>();
        assertFalse(r.isStartIncluded());
        assertNull(r.getStart());
        assertFalse(r.isEndIncluded());
        assertNull(r.getEnd());
        assertNull(r.getOperator());
    }

    @Test
    public void testConstructorWithEndpoints() {
        // Endpoints Range
        Range<Integer> r = new Range<>(true, 3, false, 4);
        assertTrue(r.isStartIncluded());
        assertEquals(3, r.getStart());
        assertFalse(r.isEndIncluded());
        assertEquals(4, r.getEnd());
        assertNull(r.getOperator());
    }

    @Test
    public void testConstructorWithEqualOperator() {
        // Operator range
        Range<Integer> r = new Range<>("=", 4);
        assertTrue(r.isStartIncluded());
        assertEquals(4, r.getStart());
        assertTrue(r.isEndIncluded());
        assertEquals(4, r.getEnd());
        assertEquals("=", r.getOperator());
    }

    @Test
    public void testConstructorWithNullOperator() {
        // Operator range
        Range<Integer> r = new Range<>( null, 4);
        assertTrue(r.isStartIncluded());
        assertEquals(4, r.getStart());
        assertTrue(r.isEndIncluded());
        assertEquals(4, r.getEnd());
        assertEquals("=", r.getOperator());
    }

    @Test
    public void testConstructorWithEmptyOperator() {
        // Operator range
        Range<Integer> r = new Range<>( "  ", 4);
        assertTrue(r.isStartIncluded());
        assertEquals(4, r.getStart());
        assertTrue(r.isEndIncluded());
        assertEquals(4, r.getEnd());
        assertEquals("=", r.getOperator());
    }

    @Test
    public void testConstructorWithNotEqualOperator() {
        // Operator range
        Range<Integer> r = new Range<>("!=", 4);
        assertFalse(r.isStartIncluded());
        assertNull(r.getStart());
        assertFalse(r.isEndIncluded());
        assertNull(r.getEnd());
        assertEquals("!=", r.getOperator());
    }

    @Test
    public void testConstructorWithLessOperator() {
        // Operator range
        Range<Integer> r = new Range<>("<", 4);
        assertFalse(r.isStartIncluded());
        assertNull(r.getStart());
        assertFalse(r.isEndIncluded());
        assertEquals(4, r.getEnd());
        assertEquals("<", r.getOperator());
    }

    @Test
    public void testConstructorWithLessEqualOperator() {
        // Operator range
        Range<Integer> r = new Range<>("<=", 4);
        assertFalse(r.isStartIncluded());
        assertNull(r.getStart());
        assertTrue(r.isEndIncluded());
        assertEquals(4, r.getEnd());
        assertEquals("<=", r.getOperator());
    }

    @Test
    public void testConstructorWithGreaterOperator() {
        // Operator range
        Range<Integer> r = new Range<>(">", 4);
        assertFalse(r.isStartIncluded());
        assertEquals(4, r.getStart());
        assertFalse(r.isEndIncluded());
        assertNull(r.getEnd());
        assertEquals(">", r.getOperator());
    }

    @Test
    public void testConstructorWithGreaterEqualOperator() {
        // Operator range
        Range<Integer> r = new Range<>(">=", 4);
        assertTrue(r.isStartIncluded());
        assertEquals(4, r.getStart());
        assertFalse(r.isEndIncluded());
        assertNull(r.getEnd());
        assertEquals(">=", r.getOperator());
    }

    @Test
    public void testConstructorWithIncorrectEndpoints() {
        // Endpoints Range
        // Assert exception being thrown
        Assertions.assertThrows(DMNRuntimeException.class, () -> {
            new Range<>(true, 4, false, 3);
        });
    }

    @Test
    public void testConstructorWithIncorrectOperator() {
        // Operator Range
        // Assert exception being thrown
        Assertions.assertThrows(DMNRuntimeException.class, () -> {
            new Range<>("abc", 4);
        });
    }
}