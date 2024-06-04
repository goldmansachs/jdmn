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

import com.gs.dmn.serialization.DMNConstants;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

import static com.gs.dmn.tck.ast.AnySimpleType.DATATYPE_FACTORY;
import static org.junit.jupiter.api.Assertions.*;

public class AnySimpleTypeTest {
    @Test
    public void testFromForNil() {
        AnySimpleType result = AnySimpleType.from("nil", "true", "");
        assertEquals("", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}nil=true}", result.getOtherAttributes().toString());
        assertNull(result.getValue());
    }

    @Test
    public void testFromForNumber() {
        AnySimpleType result = AnySimpleType.from("type", "decimal", "123");
        assertEquals("123", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=decimal}", result.getOtherAttributes().toString());
        assertInstanceOf(BigDecimal.class, result.getValue());
    }

    @Test
    public void testFromForString() {
        AnySimpleType result = AnySimpleType.from("type", "string", "abc");
        assertEquals("abc", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=string}", result.getOtherAttributes().toString());
        assertInstanceOf(String.class, result.getValue());
    }

    @Test
    public void testFromForBoolean() {
        AnySimpleType result = AnySimpleType.from("type", "boolean", "true");
        assertEquals("true", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=boolean}", result.getOtherAttributes().toString());
        assertInstanceOf(Boolean.class, result.getValue());
    }

    @Test
    public void testFromForDate() {
        AnySimpleType result = AnySimpleType.from("type", "date", "2010-12-01");
        assertEquals("2010-12-01", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=date}", result.getOtherAttributes().toString());
        Object value = result.getValue();
        assertTrue(value instanceof XMLGregorianCalendar && ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATE);
    }

    @Test
    public void testFromForTime() {
        AnySimpleType result = AnySimpleType.from("type", "time", "12:00:00");
        assertEquals("12:00:00", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=time}", result.getOtherAttributes().toString());
        Object value = result.getValue();
        assertTrue(value instanceof XMLGregorianCalendar && ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.TIME);
    }

    @Test
    public void testFromForDateTime() {
        AnySimpleType result = AnySimpleType.from("type", "dateTime", "2010-01-01T12:00:00");
        assertEquals("2010-01-01T12:00:00", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=dateTime}", result.getOtherAttributes().toString());
        Object value = result.getValue();
        assertTrue(value instanceof XMLGregorianCalendar && ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATETIME);
    }

    @Test
    public void testFromForDuration() {
        AnySimpleType result = AnySimpleType.from("type", "duration", "P12D");
        assertEquals("P12D", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=duration}", result.getOtherAttributes().toString());
        assertInstanceOf(Duration.class, result.getValue());
    }

    @Test
    public void testOfFromNull() {
        AnySimpleType result = AnySimpleType.of(null);
        assertEquals("", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}nil=true}", result.getOtherAttributes().toString());
        assertEquals(DMNConstants.XSI_PREFIX, result.getOtherAttributes().keySet().iterator().next().getPrefix());
    }

    @Test
    public void testOfFromString() {
        AnySimpleType result = AnySimpleType.of("abc");
        assertEquals("abc", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:string}", result.getOtherAttributes().toString());
        assertEquals(DMNConstants.XSI_PREFIX, result.getOtherAttributes().keySet().iterator().next().getPrefix());
    }

    @Test
    public void testOfFromBoolean() {
        AnySimpleType result = AnySimpleType.of(true);
        assertEquals("true", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:boolean}", result.getOtherAttributes().toString());
        assertEquals(DMNConstants.XSI_PREFIX, result.getOtherAttributes().keySet().iterator().next().getPrefix());
    }

    @Test
    public void testOfFromNumber() {
        AnySimpleType result = AnySimpleType.of(123.45F);
        assertEquals("123.45", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:float}", result.getOtherAttributes().toString());

        result = AnySimpleType.of(123.45D);
        assertEquals("123.45", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:double}", result.getOtherAttributes().toString());

        result = AnySimpleType.of(BigDecimal.valueOf(123));
        assertEquals("123", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:decimal}", result.getOtherAttributes().toString());
        assertEquals(DMNConstants.XSI_PREFIX, result.getOtherAttributes().keySet().iterator().next().getPrefix());
    }

    @Test
    public void testOfFromDuration() {
        AnySimpleType result = AnySimpleType.of(DATATYPE_FACTORY.newDuration("P12Y"));
        assertEquals("P12Y", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:duration}", result.getOtherAttributes().toString());
        assertEquals(DMNConstants.XSI_PREFIX, result.getOtherAttributes().keySet().iterator().next().getPrefix());
    }

    @Test
    public void testOfFromDate() {
        AnySimpleType result = AnySimpleType.of(DATATYPE_FACTORY.newXMLGregorianCalendar("2010-10-11"));
        assertEquals("2010-10-11", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:date}", result.getOtherAttributes().toString());
        assertEquals(DMNConstants.XSI_PREFIX, result.getOtherAttributes().keySet().iterator().next().getPrefix());
    }

    @Test
    public void testOfFromTime() {
        AnySimpleType result = AnySimpleType.of(DATATYPE_FACTORY.newXMLGregorianCalendar("12:00:01"));
        assertEquals("12:00:01", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:time}", result.getOtherAttributes().toString());
        assertEquals(DMNConstants.XSI_PREFIX, result.getOtherAttributes().keySet().iterator().next().getPrefix());
    }

    @Test
    public void testOfFromDateTime() {
        AnySimpleType result = AnySimpleType.of(DATATYPE_FACTORY.newXMLGregorianCalendar("2010-01-02T12:00:01"));
        assertEquals("2010-01-02T12:00:01", result.getText());
        assertEquals("{{http://www.w3.org/2001/XMLSchema-instance}type=xsd:dateTime}", result.getOtherAttributes().toString());
        assertEquals(DMNConstants.XSI_PREFIX, result.getOtherAttributes().keySet().iterator().next().getPrefix());
    }
}