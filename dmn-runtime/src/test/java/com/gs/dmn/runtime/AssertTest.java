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

import java.math.BigDecimal;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AssertTest {
    // ============ Null Handling Tests ============

    @Test
    void testAssertEqualsWithBothNull() {
        Assert.assertEquals(null, null);
    }

    @Test
    void testAssertEqualsWithExpectedNullAndActualValue() {
        AssertionError error = assertThrows(AssertionError.class, () -> {
            Assert.assertEquals(null, "value");
        });
        assertEquals("expected: <null> but was: <value>", error.getMessage());
    }

    @Test
    void testAssertEqualsWithExpectedValueAndActualNull() {
        AssertionError error = assertThrows(AssertionError.class, () -> {
            Assert.assertEquals("value", null);
        });
        assertEquals("expected: <value> but was: <null>", error.getMessage());
    }

    @Test
    void testAssertEqualsWithMessageAndBothNull() {
        Assert.assertEquals("custom message", null, null);
    }

    // ============ Number Comparison Tests ============

    @Test
    void testAssertEqualsWithIntegers() {
        Assert.assertEquals(42, 42);
    }

    @Test
    void testAssertEqualsWithIntegersFail() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Assert.assertEquals(42, 43);
        });
        assertEquals("expected: <42.0> but was: <43.0>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithBigDecimals() {
        Assert.assertEquals(new BigDecimal("100.00"), new BigDecimal("100.00"));
    }

    @Test
    void testAssertEqualsWithBigDecimalsWithinPrecision() {
        // Within precision of 0.00000001
        Assert.assertEquals(new BigDecimal("100.000000001"), new BigDecimal("100.000000009"));
    }

    @Test
    void testAssertEqualsWithBigDecimalsOutsidePrecision() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Assert.assertEquals(new BigDecimal("100.0000001"), new BigDecimal("100.0000002"));
        });
        assertEquals("expected: <100.0000001> but was: <100.0000002>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithDoubles() {
        Assert.assertEquals(3.14, 3.14);
    }

    @Test
    void testAssertEqualsWithDoublesWithinPrecision() {
        // Conversion: 3.14159 -> BigDecimal -> within precision of 0.00000001
        Assert.assertEquals(3.14159265, 3.14159266);
    }

    @Test
    void testAssertEqualsWithMixedNumberTypes() {
        // Integer vs BigDecimal
        Assert.assertEquals(new BigDecimal("100"), 100);
    }

    @Test
    void testAssertEqualsWithNumbersFail() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Assert.assertEquals(100.5, 100.6);
        });
        assertEquals("expected: <100.5> but was: <100.6>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithNumberAndMessageParameter() {
        Assert.assertEquals("Numbers should be equal", 42, 42);
    }

    @Test
    void testAssertEqualsWithNumberAndMessageParameterFails() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Assert.assertEquals("Numbers should be equal", 42, 43);
        });
        assertEquals("Numbers should be equal ==> expected: <42.0> but was: <43.0>", assertionError.getMessage());
    }

    // ============ Boolean Comparison Tests ============

    @Test
    void testAssertEqualsWithBooleanTrue() {
        Assert.assertEquals(true, true);
    }

    @Test
    void testAssertEqualsWithBooleanFalse() {
        Assert.assertEquals(false, false);
    }

    @Test
    void testAssertEqualsWithBooleanMismatchWithMessage() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Assert.assertEquals("Booleans should be equal", true, false);
        });
        assertEquals("Booleans should be equal ==> expected: <true> but was: <false>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithBooleanMismatch() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Assert.assertEquals(true, false);
        });
        assertEquals("expected: <true> but was: <false>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithBooleanAndMessage() {
        Assert.assertEquals("Boolean should be true", true, true);
    }

    // ============ String Comparison Tests ============

    @Test
    void testAssertEqualsWithIdenticalStrings() {
        Assert.assertEquals("hello", "hello");
    }

    @Test
    void testAssertEqualsWithDifferentStrings() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Assert.assertEquals("hello", "world");
        });
        assertEquals("expected: <hello> but was: <world>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithDifferentStringsWithMessage() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Assert.assertEquals("Strings should be equal", "hello", "world");
        });
        assertEquals("Strings should be equal ==> expected: <hello> but was: <world>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithEmptyStrings() {
        Assert.assertEquals("", "");
    }

    @Test
    void testAssertEqualsWithStringAndMessage() {
        Assert.assertEquals("Strings should match", "test", "test");
    }

    @Test
    void testAssertEqualsWithStringCaseSensitivity() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Assert.assertEquals("Hello", "hello");
        
        });
        assertEquals("expected: <Hello> but was: <hello>", assertionError.getMessage());
    }

    // ============ DateTime Comparison Tests ============

    @Test
    void testAssertEqualsWithOffsetTimeSameZone() {
        OffsetTime time1 = OffsetTime.of(14, 30, 0, 0, ZoneOffset.UTC);
        OffsetTime time2 = OffsetTime.of(14, 30, 0, 0, ZoneOffset.UTC);
        Assert.assertEquals(time1, time2);
    }

    @Test
    void testAssertEqualsWithOffsetTimeDifferentZones() {
        // Same instant, different zones - should normalize to UTC and match
        OffsetTime time1 = OffsetTime.of(14, 30, 0, 0, ZoneOffset.UTC);
        OffsetTime time2 = OffsetTime.of(15, 30, 0, 0, ZoneOffset.ofHours(1));
        Assert.assertEquals(time1, time2);
    }

    @Test
    void testAssertEqualsWithZonedDateTime() {
        ZonedDateTime zdt1 = ZonedDateTime.of(2026, 6, 16, 14, 30, 0, 0, ZoneOffset.UTC);
        ZonedDateTime zdt2 = ZonedDateTime.of(2026, 6, 16, 14, 30, 0, 0, ZoneOffset.UTC);
        Assert.assertEquals(zdt1, zdt2);
    }

    @Test
    void testAssertEqualsWithZonedDateTimeDifferentZones() {
        // Same instant, different zones - should normalize and match
        ZonedDateTime zdt1 = ZonedDateTime.of(2026, 6, 16, 14, 30, 0, 0, ZoneOffset.UTC);
        ZonedDateTime zdt2 = ZonedDateTime.of(2026, 6, 16, 15, 30, 0, 0, ZoneOffset.ofHours(1));
        Assert.assertEquals(zdt1, zdt2);
    }

    @Test
    void testAssertEqualsWithZonedDateTimeDifferentInstants() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            ZonedDateTime zdt1 = ZonedDateTime.of(2026, 6, 16, 14, 30, 0, 0, ZoneOffset.UTC);
            ZonedDateTime zdt2 = ZonedDateTime.of(2026, 6, 16, 15, 30, 0, 0, ZoneOffset.UTC);
            Assert.assertEquals(zdt1, zdt2);
        });
        assertEquals("expected: <2026-06-16T14:30Z[UTC]> but was: <2026-06-16T15:30Z[UTC]>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithPeriod() {
        Period period1 = Period.of(1, 2, 3);
        Period period2 = Period.of(1, 2, 3);
        Assert.assertEquals(period1, period2);
    }

    @Test
    void testAssertEqualsWithPeriodMismatch() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Period period1 = Period.of(1, 2, 3);
            Period period2 = Period.of(1, 2, 4);
            Assert.assertEquals(period1, period2);
        });
        assertEquals("expected: <P1Y2M3D> but was: <P1Y2M4D>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithPeriodMismatchWithMessage() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Period period1 = Period.of(1, 2, 3);
            Period period2 = Period.of(1, 2, 4);
            Assert.assertEquals("Periods should be equal", period1, period2);
        });
        assertEquals("Periods should be equal ==> expected: <P1Y2M3D> but was: <P1Y2M4D>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithDateTimeAndMessage() {
        OffsetTime time1 = OffsetTime.of(14, 30, 0, 0, ZoneOffset.UTC);
        OffsetTime time2 = OffsetTime.of(14, 30, 0, 0, ZoneOffset.UTC);
        Assert.assertEquals("Times should match", time1, time2);
    }

    // ============ List Comparison Tests ============

    @Test
    void testAssertEqualsWithEmptyLists() {
        Assert.assertEquals(Collections.emptyList(), Collections.emptyList());
    }

    @Test
    void testAssertEqualsWithIdenticalLists() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(1, 2, 3);
        Assert.assertEquals(list1, list2);
    }

    @Test
    void testAssertEqualsWithListsDifferentSize() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            List<Integer> list1 = Arrays.asList(1, 2, 3);
            List<Integer> list2 = Arrays.asList(1, 2);
            Assert.assertEquals(list1, list2);
        });
        Assertions.assertEquals("Size of [1, 2, 3] vs [1, 2] ==> expected: <3> but was: <2>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithListsDifferentSizeWithMessage() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            List<Integer> list1 = Arrays.asList(1, 2, 3);
            List<Integer> list2 = Arrays.asList(1, 2);
            Assert.assertEquals("Lists should be equal", list1, list2);
        });
        Assertions.assertEquals("Lists should be equal Size of [1, 2, 3] vs [1, 2] ==> expected: <3> but was: <2>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithListsDifferentElements() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            List<Integer> list1 = Arrays.asList(1, 2, 3);
            List<Integer> list2 = Arrays.asList(1, 2, 4);
            Assert.assertEquals(list1, list2);
        });
        assertEquals("[1, 2, 3] vs [1, 2, 4] at index 3 ==> expected: <3.0> but was: <4.0>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithListsDifferentElementsWithMessage() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            List<Integer> list1 = Arrays.asList(1, 2, 3);
            List<Integer> list2 = Arrays.asList(1, 2, 4);
            Assert.assertEquals("Lists should be equal", list1, list2);
        });
        assertEquals("Lists should be equal [1, 2, 3] vs [1, 2, 4] at index 3 ==> expected: <3.0> but was: <4.0>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithListsOfStrings() {
        List<String> list1 = Arrays.asList("a", "b", "c");
        List<String> list2 = Arrays.asList("a", "b", "c");
        Assert.assertEquals(list1, list2);
    }

    @Test
    void testAssertEqualsWithNestedLists() {
        List<List<Integer>> list1 = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4)
        );
        List<List<Integer>> list2 = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4)
        );
        Assert.assertEquals(list1, list2);
    }

    @Test
    void testAssertEqualsWithNestedListsMismatch() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            List<List<Integer>> list1 = Arrays.asList(
                    Arrays.asList(1, 2),
                    Arrays.asList(3, 4)
            );
            List<List<Integer>> list2 = Arrays.asList(
                    Arrays.asList(1, 2),
                    Arrays.asList(3, 5)
            );
            Assert.assertEquals(list1, list2);
        });
        assertEquals("[[1, 2], [3, 4]] vs [[1, 2], [3, 5]] at index 2 [3, 4] vs [3, 5] at index 2 ==> expected: <4.0> but was: <5.0>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithNestedListsMismatchWithMessage() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            List<List<Integer>> list1 = Arrays.asList(
                    Arrays.asList(1, 2),
                    Arrays.asList(3, 4)
            );
            List<List<Integer>> list2 = Arrays.asList(
                    Arrays.asList(1, 2),
                    Arrays.asList(3, 5)
            );
            Assert.assertEquals("Nested lists should be equal", list1, list2);
        });
        assertEquals("Nested lists should be equal [[1, 2], [3, 4]] vs [[1, 2], [3, 5]] at index 2 [3, 4] vs [3, 5] at index 2 ==> expected: <4.0> but was: <5.0>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithListAndNull() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Assert.assertEquals(Arrays.asList(1, 2, 3), null);
        });
        assertEquals("expected: <[1, 2, 3]> but was: <null>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithNullAndList() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Assert.assertEquals(null, Arrays.asList(1, 2, 3));
        });
        assertEquals("expected: <null> but was: <[1, 2, 3]>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithListAndMessage() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(1, 2, 3);
        Assert.assertEquals("Lists should be equal", list1, list2);
    }

    @Test
    void testAssertEqualsWithListContainingNumbers() {
        // Mixed numeric types with precision comparison
        List<Number> list1 = Arrays.asList(100, 200.5);
        List<Number> list2 = Arrays.asList(new BigDecimal("100"), new BigDecimal("200.500000001"));
        Assert.assertEquals(list1, list2);
    }

    // ============ Context Comparison Tests ============

    @Test
    void testAssertEqualsWithEmptyContexts() {
        Context ctx1 = new Context();
        Context ctx2 = new Context();
        Assert.assertEquals(ctx1, ctx2);
    }

    @Test
    void testAssertEqualsWithSimpleContexts() {
        Context ctx1 = new Context("test");
        ctx1.add("key1", "value1");
        ctx1.add("key2", 42);

        Context ctx2 = new Context("test");
        ctx2.add("key1", "value1");
        ctx2.add("key2", 42);

        Assert.assertEquals(ctx1, ctx2);
    }

    @Test
    void testAssertEqualsWithContextsDifferentValues() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Context ctx1 = new Context("test");
            ctx1.add("key1", "value1");

            Context ctx2 = new Context("test");
            ctx2.add("key1", "value2");

            Assert.assertEquals(ctx1, ctx2);
        });
        assertEquals("{key1=value1} vs {key1=value2} at member key1 ==> expected: <value1> but was: <value2>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithContextsDifferentKeys() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Context ctx1 = new Context("test");
            ctx1.add("key1", "value1");

            Context ctx2 = new Context("test");
            ctx2.add("key2", "value1");

            Assert.assertEquals(ctx1, ctx2);
        });
        assertEquals("{key1=value1} vs {key2=value1} at member key1 ==> expected: <value1> but was: <null>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithExpectedContextAndActualNull() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            Context ctx1 = new Context("test");
            ctx1.add("key1", "value1");

            Assert.assertEquals(ctx1, null);
        });
        assertEquals("{key1=value1} vs {} at member key1 ==> expected: <value1> but was: <null>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithContextsContainingNumbers() {
        Context ctx1 = new Context("test");
        ctx1.add("amount", 100.5);

        Context ctx2 = new Context("test");
        ctx2.add("amount", new BigDecimal("100.500000001"));

        Assert.assertEquals(ctx1, ctx2);
    }

    @Test
    void testAssertEqualsWithContextAndMessage() {
        Context ctx1 = new Context("test");
        ctx1.add("key", "value");

        Context ctx2 = new Context("test");
        ctx2.add("key", "value");

        Assert.assertEquals("Contexts should be equal", ctx1, ctx2);
    }

    @Test
    void testAssertEqualsWithNestedContexts() {
        Context innerCtx1 = new Context("inner");
        innerCtx1.add("nested", "value");

        Context outerCtx1 = new Context("outer");
        outerCtx1.add("inner", innerCtx1);

        Context innerCtx2 = new Context("inner");
        innerCtx2.add("nested", "value");

        Context outerCtx2 = new Context("outer");
        outerCtx2.add("inner", innerCtx2);

        Assert.assertEquals(outerCtx1, outerCtx2);
    }

    // ============ Complex DMNType Object Tests ============

    @Test
    void testAssertEqualsWithSimpleDMNType() {
        SimpleDMNTypeStub expected = new SimpleDMNTypeStub("value1", 42);
        SimpleDMNTypeStub actual = new SimpleDMNTypeStub("value1", 42);

        Assert.assertEquals(expected, actual);
    }

    @Test
    void testAssertEqualsWithDMNTypeDifferentValues() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            SimpleDMNTypeStub expected = new SimpleDMNTypeStub("value1", 42);
            SimpleDMNTypeStub actual = new SimpleDMNTypeStub("value1", 43);

            Assert.assertEquals(expected, actual);
        });
        assertEquals("expected: <42.0> but was: <43.0>", assertionError.getMessage());
    }

    @Test
    void testAssertEqualsWithDMNTypeAndNull() {
        AssertionError assertionError = assertThrows(AssertionError.class, () -> {
            SimpleDMNTypeStub expected = new SimpleDMNTypeStub("value1", 42);

            Assert.assertEquals(expected, null);
        });
        assertNotNull(assertionError);
    }

    @Test
    void testAssertEqualsWithDMNTypeAndMessage() {
        SimpleDMNTypeStub expected = new SimpleDMNTypeStub("value1", 42);
        SimpleDMNTypeStub actual = new SimpleDMNTypeStub("value1", 42);

        Assert.assertEquals("DMN types should match", expected, actual);
    }

    @Test
    void testAssertEqualsWithDMNTypeContainingNumbers() {
        SimpleDMNTypeStub expected = new SimpleDMNTypeStub("value", 100.5);
        SimpleDMNTypeStub actual = new SimpleDMNTypeStub("value", new BigDecimal("100.500000001"));

        Assert.assertEquals(expected, actual);
    }

    @Test
    void testAssertEqualsWithDMNTypeContainingLists() {
        SimpleDMNTypeStub expected = new SimpleDMNTypeStub("value", Arrays.asList(1, 2, 3));
        SimpleDMNTypeStub actual = new SimpleDMNTypeStub("value", Arrays.asList(1, 2, 3));

        Assert.assertEquals(expected, actual);
    }

    // ============ Mixed Type Comparison Tests ============

    @Test
    void testAssertEqualsWithListOfContexts() {
        Context ctx1 = new Context("test");
        ctx1.add("key", "value1");

        Context ctx2 = new Context("test");
        ctx2.add("key", "value1");

        List<Context> list1 = Arrays.asList(ctx1);
        List<Context> list2 = Arrays.asList(ctx2);

        Assert.assertEquals(list1, list2);
    }

    @Test
    void testAssertEqualsWithListOfDMNTypes() {
        SimpleDMNTypeStub obj1 = new SimpleDMNTypeStub("value", 42);
        SimpleDMNTypeStub obj2 = new SimpleDMNTypeStub("value", 42);

        List<SimpleDMNTypeStub> list1 = Arrays.asList(obj1);
        List<SimpleDMNTypeStub> list2 = Arrays.asList(obj2);

        Assert.assertEquals(list1, list2);
    }

    @Test
    void testAssertEqualsWithContextContainingLists() {
        Context ctx1 = new Context("test");
        ctx1.add("items", Arrays.asList(1, 2, 3));

        Context ctx2 = new Context("test");
        ctx2.add("items", Arrays.asList(1, 2, 3));

        Assert.assertEquals(ctx1, ctx2);
    }

    // ============ Edge Cases ============

    @Test
    void testAssertEqualsWithZeroValues() {
        Assert.assertEquals(0, 0);
        Assert.assertEquals(0.0, 0.0);
        Assert.assertEquals(new BigDecimal("0"), new BigDecimal("0"));
    }

    @Test
    void testAssertEqualsWithNegativeNumbers() {
        Assert.assertEquals(-100, -100);
        Assert.assertEquals(new BigDecimal("-100.5"), new BigDecimal("-100.5"));
    }

    @Test
    void testAssertEqualsWithVeryLargeNumbers() {
        BigDecimal large1 = new BigDecimal("999999999999999999.999999999");
        BigDecimal large2 = new BigDecimal("999999999999999999.999999999");
        Assert.assertEquals(large1, large2);
    }

    @Test
    void testAssertEqualsWithEmptyContextKeySet() {
        Context ctx = new Context();
        assertTrue(ctx.keySet().isEmpty());
        Assert.assertEquals(ctx, ctx);
    }

    // ============ Stub Class for DMNType Testing ============

    /**
     * Simple stub class implementing DMNType for testing purposes
     */
    public static class SimpleDMNTypeStub implements DMNType {
        private String field1;
        private Object field2;

        public SimpleDMNTypeStub(String field1, Object field2) {
            this.field1 = field1;
            this.field2 = field2;
        }

        public String getField1() {
            return field1;
        }

        public Object getField2() {
            return field2;
        }

        @Override
        public Context toContext() {
            return new Context("SimpleDMNTypeStub")
                    .add("field1", field1)
                    .add("field2", field2);
        }
    }
}