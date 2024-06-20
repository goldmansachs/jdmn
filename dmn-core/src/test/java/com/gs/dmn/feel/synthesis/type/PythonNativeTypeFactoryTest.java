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

class PythonNativeTypeFactoryTest {
    private final NativeTypeFactory typeFactory = new StandardNativeTypeToPythonFactory();

    @Test
    public void testToNativeType() {
        assertEquals("decimal.Decimal", typeFactory.toNativeType("number"));
        assertEquals("str", typeFactory.toNativeType("string"));
        assertEquals("bool", typeFactory.toNativeType("boolean"));
    }

    @Test
    public void testToQualifiedNativeType() {
        assertEquals("decimal.Decimal", typeFactory.toQualifiedNativeType("number"));
        assertEquals("str", typeFactory.toQualifiedNativeType("string"));
        assertEquals("bool", typeFactory.toQualifiedNativeType("boolean"));
    }

    @Test
    public void testNullableType() {
        assertEquals("typing.Optional[A]", typeFactory.nullableType("A"));
    }

    @Test
    public void testConstructorOfGenericType() {
        assertEquals("A", typeFactory.constructorOfGenericType("A", "a", "b"));
    }

    @Test
    public void testClassOf() {
        assertEquals("A.__class__", typeFactory.classOf("A"));
    }

    @Test
    public void testGetNativeNumberType() {
        assertEquals("decimal.Decimal", typeFactory.getNativeNumberType());
    }

    @Test
    public void testGetNativeNumberConcreteType() {
        assertEquals("decimal.Decimal", typeFactory.getNativeNumberConcreteType());
    }

    @Test
    public void testGetNativeDateType() {
        assertEquals("datetime.date", typeFactory.getNativeDateType());
    }

    @Test
    public void testGetNativeTimeType() {
        assertEquals("datetime.time", typeFactory.getNativeTimeType());
    }

    @Test
    public void testGetNativeDateAndTimeType() {
        assertEquals("datetime.datetime", typeFactory.getNativeDateAndTimeType());
    }

    @Test
    public void testGetNativeDurationType() {
        assertEquals("datetime.timedelta", typeFactory.getNativeDurationType());
    }
}