/**
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
package com.gs.dmn.feel.lib;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class CommonLibFunctionsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends LibOperatorsTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    //
    // Numeric functions
    //

    @Test
    public void testNumber() {
        assertNull(getLib().number(null));

        assertEquals("123.56", getLib().number("123.56").toString());
        assertEquals("-123.56", getLib().number("-123.56").toString());
        assertNull(getLib().number("xxx"));
    }

    @Test
    public void testFloor() {
        assertNull(getLib().floor(null));

        assertEqualsNumber(makeNumber("1"), getLib().floor(makeNumber(1)));
        assertEqualsNumber(makeNumber("1"), getLib().floor(makeNumber(1.23)));
        assertEqualsNumber(makeNumber("1"), getLib().floor(makeNumber(1.5)));
        assertEqualsNumber(makeNumber("1"), getLib().floor(makeNumber(1.56)));
        assertEqualsNumber(makeNumber("-2"), getLib().floor(makeNumber(-1.56)));
    }

    @Test
    public void testCeiling() {
        assertNull(getLib().ceiling(null));

        assertEqualsNumber(makeNumber("1"), getLib().ceiling(makeNumber(1)));
        assertEqualsNumber(makeNumber("2"), getLib().ceiling(makeNumber(1.23)));
        assertEqualsNumber(makeNumber("2"), getLib().ceiling(makeNumber(1.5)));
        assertEqualsNumber(makeNumber("2"), getLib().ceiling(makeNumber(1.56)));
        assertEqualsNumber(makeNumber("-1"), getLib().ceiling(makeNumber(-1.5)));
    }

    //
    // String functions
    //
    @Test
    public void testContains() {
        assertNull(getLib().contains(null, null));
        assertNull(getLib().contains(null, "bcg"));
        assertNull(getLib().contains("bcg", null));

        assertTrue(getLib().contains("abc", "a"));
        assertFalse(getLib().contains("aBc1", "bcg"));

        assertEquals(false, getLib().contains("foobar", "of"));
        assertEquals(true, getLib().startsWith("foobar", "fo"));
        assertEquals(true, getLib().endsWith("foobar", "r"));
    }

    @Test
    public void testStartsWith() {
        assertNull(getLib().startsWith(null, null));
        assertNull(getLib().startsWith(null, "bcg"));
        assertNull(getLib().startsWith("bcg", null));

        assertTrue(getLib().startsWith("abc", "a"));
        assertFalse(getLib().startsWith("aBc1", "bcg"));

        assertEquals(true, getLib().startsWith("foobar", "fo"));
    }

    @Test
    public void testEndsWith() {
        assertNull(getLib().endsWith(null, null));
        assertNull(getLib().endsWith(null, "bcg"));
        assertNull(getLib().endsWith("bcg", null));

        assertTrue(getLib().endsWith("abc", "c"));
        assertFalse(getLib().endsWith("aBc1", "bcg"));

        assertEquals(true, getLib().endsWith("foobar", "r"));
    }

    //
    // Test list functions
    //

    @Test
    public void testAppend() {
        assertEquals("[null]", getLib().append(null, null).toString());
        assertEquals(Arrays.asList("3"), getLib().append(null, "3"));
        assertEquals(makeNumberList("1", null, "3", null, "3"), getLib().append(makeNumberList(1, null, 3), null, makeNumber(3)));

        assertEquals(makeNumberList("1", "2", "3", "4"), getLib().append(makeNumberList(1, 2, 3), makeNumber(4)));
        assertEquals(makeNumberList("1", null, "3", "4"), getLib().append(makeNumberList(1, null, 3), makeNumber(4)));
    }

    @Test
    public void testCount() {
        assertEqualsNumber(makeNumber("0"), getLib().count(null));

        assertEqualsNumber(makeNumber("3"), getLib().count(makeNumberList(1, 2, 3)));
        assertEqualsNumber(makeNumber("3"), getLib().count(makeNumberList(1, null, 3)));
    }

    @Test
    public void testMin() throws Exception {
        assertNull(getLib().min((List) null));

        assertEqualsNumber(makeNumber("1"), getLib().min(makeNumberList(1, 2, 3)));
        assertNull(getLib().min(makeNumberList(1, null, 3)));
    }

    @Test
    public void testMax() throws Exception {
        assertNull(getLib().max((List) null));

        assertEqualsNumber(makeNumber("3"), getLib().max(makeNumberList(1, 2, 3)));
        assertNull(getLib().max(makeNumberList(1, null, 3)));

        assertEqualsNumber(makeNumber("3"), getLib().max(makeNumberList(1, 3)));
        assertNull(getLib().max(makeNumberList(1, null)));
    }

    @Test
    public void testSum() throws Exception {
        assertNull(getLib().sum((List) null));
        assertNull(getLib().sum((makeNumberList())));

        assertEqualsNumber(makeNumber("6"), getLib().sum(makeNumberList(1, 2, 3)));
        assertNull(getLib().sum(makeNumberList(1, null, 3)));
    }
}
