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
package com.gs.dmn.feel.synthesis.type;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KotlinNativeTypeFactoryTest {
    private final NativeTypeFactory typeFactory = new JavaTimeKotlinNativeTypeFactory();

    @Test
    public void testToNativeType() {
        assertEquals("kotlin.Number", typeFactory.toNativeType("number"));
        assertEquals("String", typeFactory.toNativeType("string"));
    }

    @Test
    public void testToQualifiedNativeType() {
        assertEquals("kotlin.Number", typeFactory.toQualifiedNativeType("number"));
    }

    @Test
    public void testNullableType() {
        assertEquals("A?", typeFactory.nullableType("A"));
    }

    @Test
    public void testConstructorOfGenericType() {
        assertEquals("A<a, b>", typeFactory.constructorOfGenericType("A", "a", "b"));
    }

    @Test
    public void testClassOf() {
        assertEquals("A::class.java", typeFactory.classOf("A"));
    }

    @Test
    public void testGetNativeNumberType() {
        assertEquals("kotlin.Number", typeFactory.getNativeNumberType());
    }

    @Test
    public void testGetNativeNumberConcreteType() {
        assertEquals("java.math.BigDecimal", typeFactory.getNativeNumberConcreteType());
    }

    @Test
    public void testGetNativeDateType() {
        assertEquals("java.time.LocalDate", typeFactory.getNativeDateType());
    }

    @Test
    public void testGetNativeTimeType() {
        assertEquals("java.time.temporal.TemporalAccessor", typeFactory.getNativeTimeType());
    }

    @Test
    public void testGetNativeDateAndTimeType() {
        assertEquals("java.time.temporal.TemporalAccessor", typeFactory.getNativeDateAndTimeType());
    }

    @Test
    public void testGetNativeDurationType() {
        assertEquals("java.time.temporal.TemporalAmount", typeFactory.getNativeDurationType());
    }
}