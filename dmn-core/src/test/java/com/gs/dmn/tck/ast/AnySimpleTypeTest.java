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
package com.gs.dmn.tck.ast;

import org.junit.Test;

import java.math.BigDecimal;

import static com.gs.dmn.tck.ast.AnySimpleType.DATATYPE_FACTORY;
import static org.junit.Assert.assertEquals;

public class AnySimpleTypeTest {
    @Test
    public void testCreateFromNull() {
        AnySimpleType result = AnySimpleType.of(null);
        assertEquals("", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}nil=true}", result.getOtherAttributes().toString());
    }

    @Test
    public void testCreateFromString() {
        AnySimpleType result = AnySimpleType.of("abc");
        assertEquals("abc", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:string}", result.getOtherAttributes().toString());
    }

    @Test
    public void testCreateFromBoolean() {
        AnySimpleType result = AnySimpleType.of(true);
        assertEquals("true", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:boolean}", result.getOtherAttributes().toString());
    }

    @Test
    public void testCreateFromNumber() {
        AnySimpleType result = AnySimpleType.of(123.45F);
        assertEquals("123.45", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:float}", result.getOtherAttributes().toString());

        result = AnySimpleType.of(123.45D);
        assertEquals("123.45", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:double}", result.getOtherAttributes().toString());

        result = AnySimpleType.of(BigDecimal.valueOf(123));
        assertEquals("123", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:decimal}", result.getOtherAttributes().toString());
    }

    @Test
    public void testCreateFromDuration() {
        AnySimpleType result = AnySimpleType.of(DATATYPE_FACTORY.newDuration("P12Y"));
        assertEquals("P12Y", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:duration}", result.getOtherAttributes().toString());
    }

    @Test
    public void testCreateFromDate() {
        AnySimpleType result = AnySimpleType.of(DATATYPE_FACTORY.newXMLGregorianCalendar("2010-10-11"));
        assertEquals("2010-10-11", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:date}", result.getOtherAttributes().toString());
    }

    @Test
    public void testCreateFromTime() {
        AnySimpleType result = AnySimpleType.of(DATATYPE_FACTORY.newXMLGregorianCalendar("12:00:01"));
        assertEquals("12:00:01", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:time}", result.getOtherAttributes().toString());
    }

    @Test
    public void testCreateFromDateTime() {
        AnySimpleType result = AnySimpleType.of(DATATYPE_FACTORY.newXMLGregorianCalendar("2010-01-02T12:00:01"));
        assertEquals("2010-01-02T12:00:01", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:dateTime}", result.getOtherAttributes().toString());
    }
}