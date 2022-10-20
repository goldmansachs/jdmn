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
package com.gs.dmn.tck;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TInputData;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.PureJavaTimeDMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.type.ContextType;
import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.tck.ast.AnySimpleType;
import com.gs.dmn.tck.ast.Component;
import com.gs.dmn.tck.ast.List;
import com.gs.dmn.tck.ast.ValueType;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.junit.Test;

import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Collections;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.*;
import static com.gs.dmn.feel.analysis.semantics.type.ListType.*;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;
import static org.junit.Assert.assertEquals;

public class MockTCKValueTranslatorTest {
    static final DatatypeFactory DATATYPE_FACTORY;
    static {
        try {
            DATATYPE_FACTORY = DatatypeFactory.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot create xml factory");
        }
    }

    private final PureJavaTimeDMNDialectDefinition dialect = new PureJavaTimeDMNDialectDefinition();
    private final DMNModelRepository repository = new DMNModelRepository();
    private final BasicDMNToNativeTransformer<Type, DMNContext> transformer = dialect.createBasicTransformer(repository, new NopLazyEvaluationDetector(), new InputParameters());
    private final StandardFEELLib<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> lib = (StandardFEELLib<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount>) dialect.createFEELLib();
    private final MockTCKValueTranslator<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> translator = new MockTCKValueTranslator<>(transformer, lib);

    @Test
    public void testWhenValueIsNil() {
        doTest("null", makeSimpleValue(null), STRING, null);
        doTest("null", makeSimpleValue("null"), STRING, null);
        doTest("null", makeSimpleValue(null), NUMBER, null);
        doTest("null", makeSimpleValue("null"), NUMBER, null);
        doTest("null", makeSimpleValue(null), DATE, null);
        doTest("null", makeSimpleValue("null"), DATE, null);
        doTest("null", makeSimpleValue(null), YEARS_AND_MONTHS_DURATION, null);
        doTest("null", makeSimpleValue("null"), YEARS_AND_MONTHS_DURATION, null);
    }

    @Test
    public void testWhenTypeIsMissing() {
        doTest("a", makeSimpleValue("a"), null, null);
        doTest("1.0", makeSimpleValue(1.0F), null, null);
        doTest("3", makeSimpleValue(BigDecimal.valueOf(3)), null, null);
        doTest("1990-03-29", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29")), null, null);
        doTest("12:00:00", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("12:00:00")), null, null);
        doTest("1990-03-29T12:00:00", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29T12:00:00")), null, null);
        doTest("P1Y1M2DT3H", makeSimpleValue(DATATYPE_FACTORY.newDuration("P1Y1M2DT3H")), null, null);
    }

    @Test
    public void testWhenTypeIsAny() {
        doTest("a", makeSimpleValue("a"), ANY, null);
        doTest("1.0", makeSimpleValue(1.0F), ANY, null);
        doTest("3", makeSimpleValue(BigDecimal.valueOf(3)), ANY, null);
        doTest("1990-03-29", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29")), ANY, null);
        doTest("12:00:00", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("12:00:00")), ANY, null);
        doTest("1990-03-29T12:00:00", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29T12:00:00")), ANY, null);
        doTest("P1Y1M2DT3H", makeSimpleValue(DATATYPE_FACTORY.newDuration("P1Y1M2DT3H")), ANY, null);
    }

    @Test
    public void testToNativeExpressionForSimpleValue() {
        doTest("a", makeSimpleValue("a"), STRING, null);
        doTest("Boolean.TRUE", makeSimpleValue(true), BOOLEAN, null);

        doTest("new java.math.BigDecimal(1.0)", makeSimpleValue("1.0"), NUMBER, null);
        doTest("new java.math.BigDecimal(1.0)", makeSimpleValue("\"1.0\""), NUMBER, null);
        doTest("new java.math.BigDecimal(1.0)", makeSimpleValue(1.0F), NUMBER, null);
        doTest("new java.math.BigDecimal(1.0)", makeSimpleValue(1.0), NUMBER, null);
        doTest("new java.math.BigDecimal(3)", makeSimpleValue(BigDecimal.valueOf(3)), NUMBER, null);
        doTest("new java.math.BigDecimal(3.0)", makeSimpleValue(BigDecimal.valueOf(3.0)), NUMBER, null);
        doTest("new java.math.BigDecimal(java.lang.Double.toString(3))", makeSimpleValue(BigDecimal.valueOf(3)), NUMBER, makeInputData());
        doTest("new java.math.BigDecimal(java.lang.Double.toString(3.0))", makeSimpleValue(BigDecimal.valueOf(3.0)), NUMBER, makeInputData());
        doTest("new java.math.BigDecimal(java.lang.Double.toString(3))", makeSimpleValue(BigDecimal.valueOf(3)), NUMBER, makeInputData());
        doTest("new java.math.BigDecimal(java.lang.Double.toString(3.0))", makeSimpleValue(BigDecimal.valueOf(3.0)), NUMBER, makeInputData());

        doTest("date(\"1990-03-29\")", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29")), DATE, null);
        doTest("time(\"12:00:00\")", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("12:00:00")), TIME, null);
        doTest("dateAndTime(\"1990-03-29T12:00:00\")", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29T12:00:00")), DATE_AND_TIME, null);
        doTest("duration(\"P2Y3M\")", makeSimpleValue(DATATYPE_FACTORY.newDuration("P2Y3M")), YEARS_AND_MONTHS_DURATION, null);
        doTest("duration(\"P2DT3H\")", makeSimpleValue(DATATYPE_FACTORY.newDuration("P2DT3H")), DAYS_AND_TIME_DURATION, null);
        doTest("duration(\"P1Y1M2DT3H\")", makeSimpleValue(DATATYPE_FACTORY.newDuration("P1Y1M2DT3H")), ANY_DURATION, null);
    }

    @Test
    public void testToNativeExpressionForListValue() {
        doTest("asList(\"a\")", makeListValue("\"a\""), STRING_LIST, null);
        doTest("asList(Boolean.TRUE)", makeListValue(true), BOOLEAN_LIST, null);

        doTest("asList(new java.math.BigDecimal(1.0))", makeListValue("1.0"), NUMBER_LIST, null);
        doTest("asList(new java.math.BigDecimal(1.0))", makeListValue("\"1.0\""), NUMBER_LIST, null);
        doTest("asList(new java.math.BigDecimal(1.0))", makeListValue(1.0F), NUMBER_LIST, null);
        doTest("asList(new java.math.BigDecimal(1.0))", makeListValue(1.0), NUMBER_LIST, null);
        doTest("asList(new java.math.BigDecimal(3))", makeListValue(BigDecimal.valueOf(3)), NUMBER_LIST, null);
        doTest("asList(new java.math.BigDecimal(3.0))", makeListValue(BigDecimal.valueOf(3.0)), NUMBER_LIST, null);

        doTest("asList(date(\"1990-03-29\"))", makeListValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29")), DATE_LIST, null);
        doTest("asList(time(\"12:00:00\"))", makeListValue(DATATYPE_FACTORY.newXMLGregorianCalendar("12:00:00")), TIME_LIST, null);
        doTest("asList(dateAndTime(\"1990-03-29T12:00:00\"))", makeListValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29T12:00:00")), DATE_AND_TIME_LIST, null);
        doTest("asList(duration(\"P2Y3M\"))", makeListValue(DATATYPE_FACTORY.newDuration("P2Y3M")), YEARS_AND_MONTHS_DURATION_LIST, null);
        doTest("asList(duration(\"P2DT3H\"))", makeListValue(DATATYPE_FACTORY.newDuration("P2DT3H")), DAYS_AND_TIME_DURATION_LIST, null);
    }

    @Test
    public void testToNativeExpressionForComplexTypes() {
        Object[] values = new Object[] {
                "\"a\"", true, 1.0, DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29"), DATATYPE_FACTORY.newDuration("P2Y3M")
        };
        Type[] types = new Type[] {
                STRING, BOOLEAN, NUMBER, DATE, YEARS_AND_MONTHS_DURATION, STRING
        };

        doTest("new type.TestImpl(\"a\", Boolean.TRUE, new java.math.BigDecimal(1.0), date(\"1990-03-29\"), duration(\"P2Y3M\"), DEFAULT_STRING)", makeComponentValue(values), makeItemDefinitionType(types), null);
        doTest("new com.gs.dmn.runtime.Context().add(\"m0\", \"a\").add(\"m1\", Boolean.TRUE).add(\"m2\", new java.math.BigDecimal(1.0)).add(\"m3\", date(\"1990-03-29\")).add(\"m4\", duration(\"P2Y3M\")).add(\"m5\", DEFAULT_STRING)", makeComponentValue(values), makeContextType(types), null);
    }

    private void doTest(String expected, ValueType value, Type type, TDRGElement element) {
        assertEquals(expected, translator.toNativeExpression(value, type, element));
    }

    private ValueType makeSimpleValue(Object value) {
        ValueType result = new ValueType();
        result.setValue(AnySimpleType.of(value));
        return result;
    }

    private ValueType makeListValue(Object... values) {
        ValueType result = new ValueType();
        List value  = new List();
        for (Object v: values) {
            value.getItem().add(makeSimpleValue(v));
        }
        result.setList(value);
        return result;
    }

    private ValueType makeComponentValue(Object... values) {
        ValueType result = new ValueType();
        for (int i=0; i<values.length; i++) {
            Component c = new Component();
            c.setName("m" + i);
            c.setValue(AnySimpleType.of(values[i]));
            result.getComponent().add(c);
        }
        return result;
    }

    private Type makeItemDefinitionType(Type... types) {
        ItemDefinitionType result = new ItemDefinitionType("test");
        for (int i=0; i<types.length; i++) {
            result.addMember("m" + i, Collections.emptyList(), types[i]);
        }
        return result;
    }

    private Type makeContextType(Type... types) {
        ContextType result = new ContextType();
        for (int i=0; i<types.length; i++) {
            result.addMember("m" + i, Collections.emptyList(), types[i]);
        }
        return result;
    }

    private TInputData makeInputData() {
        return new TInputData();
    }

}