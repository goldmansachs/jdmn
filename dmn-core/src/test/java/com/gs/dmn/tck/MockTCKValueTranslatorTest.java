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

import com.gs.dmn.AbstractTest;
import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.type.ContextType;
import com.gs.dmn.feel.analysis.semantics.type.DaysAndTimeDurationType;
import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.analysis.semantics.type.YearsAndMonthsDurationType;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.log.NopBuildLogger;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.tck.ast.AnySimpleType;
import com.gs.dmn.tck.ast.Component;
import com.gs.dmn.tck.ast.List;
import com.gs.dmn.tck.ast.ValueType;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.DatatypeFactory;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.Objects;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.ListType.*;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MockTCKValueTranslatorTest extends AbstractTest {
    static final DatatypeFactory DATATYPE_FACTORY;
    static {
        try {
            DATATYPE_FACTORY = DatatypeFactory.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot create xml factory");
        }
    }

    private static final JavaTimeDMNDialectDefinition DIALECT = new JavaTimeDMNDialectDefinition();
    private static DMNModelRepository REPOSITORY;

    private final BasicDMNToNativeTransformer<Type, DMNContext> transformer = DIALECT.createBasicTransformer(REPOSITORY, new NopLazyEvaluationDetector(), new InputParameters());
    private final StandardFEELLib<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> lib = (StandardFEELLib<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount>) DIALECT.createFEELLib();
    private final MockTCKValueTranslator<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> translator = new MockTCKValueTranslator<>(transformer, lib);

    @BeforeAll
    public static void setUp() {
        REPOSITORY = readModel();
    }

    private static DMNModelRepository readModel() {
        File dmnFile = new File(Objects.requireNonNull(MockTCKValueTranslatorTest.class.getClassLoader().getResource("dmn/input/1.5/test-mock-translator.dmn")).getFile());
        DMNSerializer dmnSerializer = new JavaTimeDMNDialectDefinition().createDMNSerializer(new NopBuildLogger(), new InputParameters());
        TDefinitions model = dmnSerializer.readModel(dmnFile);
        return new DMNModelRepository(model);
    }

    private static TDRGElement findElement(String name) {
        return REPOSITORY.getRootDefinitions().getDrgElement().stream().filter(p -> p.getName().equals(name)).findFirst().orElseThrow();
    }

    @Test
    public void testWhenValueIsNil() {
        doTest("null", makeSimpleValue("null"), STRING, null);
        doTest("null", makeSimpleValue(null), STRING, findElement("aString"));
        doTest("null", makeSimpleValue(null), NUMBER, null);
        doTest("null", makeSimpleValue("null"), NUMBER, null);
        doTest("null", makeSimpleValue(null), NUMBER, findElement("aNumber"));
        doTest("null", makeSimpleValue("null"), NUMBER, findElement("aNumber"));
        doTest("null", makeSimpleValue(null), DATE, null);
        doTest("null", makeSimpleValue("null"), DATE, null);
        doTest("null", makeSimpleValue(null), DATE, findElement("aDate"));
        doTest("null", makeSimpleValue("null"), DATE, findElement("aDate"));
        doTest("null", makeSimpleValue(null), YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION, null);
        doTest("null", makeSimpleValue("null"), YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION, null);
        doTest("null", makeSimpleValue(null), YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION, findElement("aYearDuration"));
        doTest("null", makeSimpleValue("null"), YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION, findElement("aYearDuration"));
        doTest("null", makeSimpleValue(null), DaysAndTimeDurationType.DAYS_AND_TIME_DURATION, null);
        doTest("null", makeSimpleValue("null"), DaysAndTimeDurationType.DAYS_AND_TIME_DURATION, null);
        doTest("null", makeSimpleValue(null), DaysAndTimeDurationType.DAYS_AND_TIME_DURATION, findElement("aDayDuration"));
        doTest("null", makeSimpleValue("null"), DaysAndTimeDurationType.DAYS_AND_TIME_DURATION, findElement("aDayDuration"));
    }

    @Test
    public void testWhenTypeIsMissing() {
        doTest("a", makeSimpleValue("a"), null, null);
        doTest("a", makeSimpleValue("a"), null, findElement("aString"));
        doTest("1.0", makeSimpleValue(1.0F), null, null);
        doTest("1.0", makeSimpleValue(1.0F), null, findElement("aNumber"));
        doTest("3", makeSimpleValue(BigDecimal.valueOf(3)), null, null);
        doTest("3", makeSimpleValue(BigDecimal.valueOf(3)), null, findElement("aNumber"));
        doTest("1990-03-29", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29")), null, null);
        doTest("1990-03-29", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29")), null, findElement("aDate"));
        doTest("12:00:00", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("12:00:00")), null, null);
        doTest("12:00:00", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("12:00:00")), null, findElement("aTime"));
        doTest("1990-03-29T12:00:00", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29T12:00:00")), null, null);
        doTest("1990-03-29T12:00:00", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29T12:00:00")), null, findElement("aDateTime"));
        doTest("P1Y1M2DT3H", makeSimpleValue(DATATYPE_FACTORY.newDuration("P1Y1M2DT3H")), null, null);
        doTest("P1Y1M2DT3H", makeSimpleValue(DATATYPE_FACTORY.newDuration("P1Y1M2DT3H")), null, findElement("aYearDuration"));
    }

    @Test
    public void testWhenTypeIsAny() {
        doTest("a", makeSimpleValue("a"), ANY, null);
        doTest("a", makeSimpleValue("a"), ANY, findElement("aString"));
        doTest("1.0", makeSimpleValue(1.0F), ANY, null);
        doTest("1.0", makeSimpleValue(1.0F), ANY, findElement("aNumber"));
        doTest("3", makeSimpleValue(BigDecimal.valueOf(3)), ANY, null);
        doTest("3", makeSimpleValue(BigDecimal.valueOf(3)), ANY, findElement("aInteger"));
        doTest("1990-03-29", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29")), ANY, null);
        doTest("1990-03-29", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29")), ANY, findElement("aDate"));
        doTest("12:00:00", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("12:00:00")), ANY, null);
        doTest("12:00:00", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("12:00:00")), ANY, findElement("aTime"));
        doTest("1990-03-29T12:00:00", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29T12:00:00")), ANY, null);
        doTest("1990-03-29T12:00:00", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29T12:00:00")), ANY, findElement("aDateTime"));
        doTest("P1Y1M2DT3H", makeSimpleValue(DATATYPE_FACTORY.newDuration("P1Y1M2DT3H")), ANY, null);
        doTest("P1Y1M2DT3H", makeSimpleValue(DATATYPE_FACTORY.newDuration("P1Y1M2DT3H")), ANY, findElement("aDayDuration"));
    }

    @Test
    public void testToNativeExpressionForSimpleValue() {
        doTest("a", makeSimpleValue("a"), STRING, null);
        doTest("a", makeSimpleValue("a"), STRING, findElement("aString"));
        doTest("Boolean.TRUE", makeSimpleValue(true), BOOLEAN, null);
        doTest("Boolean.TRUE", makeSimpleValue(true), BOOLEAN, findElement("aBoolean"));

        doTest("new java.math.BigDecimal(1.0)", makeSimpleValue("1.0"), NUMBER, null);
        doTest("new java.math.BigDecimal(1.0)", makeSimpleValue("1.0"), NUMBER, findElement("aDecisionNumber"));
        doTest("new java.math.BigDecimal(1.0)", makeSimpleValue("\"1.0\""), NUMBER, null);
        doTest("new java.math.BigDecimal(1.0)", makeSimpleValue("\"1.0\""), NUMBER, findElement("aDecisionNumber"));
        doTest("new java.math.BigDecimal(1.0)", makeSimpleValue(1.0F), NUMBER, null);
        doTest("new java.math.BigDecimal(1.0)", makeSimpleValue(1.0F), NUMBER, findElement("aDecisionNumber"));
        doTest("new java.math.BigDecimal(1.0)", makeSimpleValue(1.0), NUMBER, null);
        doTest("new java.math.BigDecimal(1.0)", makeSimpleValue(1.0), NUMBER, findElement("aDecisionNumber"));
        doTest("new java.math.BigDecimal(3)", makeSimpleValue(BigDecimal.valueOf(3)), NUMBER, null);
        doTest("new java.math.BigDecimal(3)", makeSimpleValue(BigDecimal.valueOf(3)), NUMBER, findElement("aDecisionNumber"));
        doTest("new java.math.BigDecimal(3.0)", makeSimpleValue(BigDecimal.valueOf(3.0)), NUMBER, null);
        doTest("new java.math.BigDecimal(3.0)", makeSimpleValue(BigDecimal.valueOf(3.0)), NUMBER, findElement("aDecisionNumber"));
        doTest("new java.math.BigDecimal(java.lang.Double.toString(3))", makeSimpleValue(BigDecimal.valueOf(3)), NUMBER, findElement("aNumber"));
        doTest("new java.math.BigDecimal(java.lang.Double.toString(3.0))", makeSimpleValue(BigDecimal.valueOf(3.0)), NUMBER, findElement("aNumber"));
        doTest("new java.math.BigDecimal(java.lang.Double.toString(3))", makeSimpleValue(BigDecimal.valueOf(3)), NUMBER, findElement("aNumber"));
        doTest("new java.math.BigDecimal(java.lang.Double.toString(3.0))", makeSimpleValue(BigDecimal.valueOf(3.0)), NUMBER, findElement("aNumber"));

        doTest("date(\"1990-03-29\")", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29")), DATE, null);
        doTest("date(\"1990-03-29\")", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29")), DATE, findElement("aDate"));
        doTest("time(\"12:00:00\")", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("12:00:00")), TIME, null);
        doTest("time(\"12:00:00\")", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("12:00:00")), TIME, findElement("aTime"));
        doTest("dateAndTime(\"1990-03-29T12:00:00\")", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29T12:00:00")), DATE_AND_TIME, null);
        doTest("dateAndTime(\"1990-03-29T12:00:00\")", makeSimpleValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29T12:00:00")), DATE_AND_TIME, findElement("aDateTime"));
        doTest("duration(\"P2Y3M\")", makeSimpleValue(DATATYPE_FACTORY.newDuration("P2Y3M")), YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION, null);
        doTest("duration(\"P2Y3M\")", makeSimpleValue(DATATYPE_FACTORY.newDuration("P2Y3M")), YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION, findElement("aYearDuration"));
        doTest("duration(\"P2DT3H\")", makeSimpleValue(DATATYPE_FACTORY.newDuration("P2DT3H")), DaysAndTimeDurationType.DAYS_AND_TIME_DURATION, null);
        doTest("duration(\"P2DT3H\")", makeSimpleValue(DATATYPE_FACTORY.newDuration("P2DT3H")), DaysAndTimeDurationType.DAYS_AND_TIME_DURATION, findElement("aDayDuration"));
        doTest("duration(\"P1Y1M2DT3H\")", makeSimpleValue(DATATYPE_FACTORY.newDuration("P1Y1M2DT3H")), DURATION, null);
        doTest("duration(\"P1Y1M2DT3H\")", makeSimpleValue(DATATYPE_FACTORY.newDuration("P1Y1M2DT3H")), DURATION, findElement("aYearDuration"));
        doTest("duration(\"P1Y1M2DT3H\")", makeSimpleValue(DATATYPE_FACTORY.newDuration("P1Y1M2DT3H")), DURATION, findElement("aDayDuration"));
    }

    @Test
    public void testToNativeExpressionForListValue() {
        doTest("asList(\"a\")", makeListValue("\"a\""), STRING_LIST, null);
        doTest("asList(\"a\")", makeListValue("\"a\""), STRING_LIST, findElement("aStringList"));
        doTest("asList(Boolean.TRUE)", makeListValue(true), BOOLEAN_LIST, null);
        doTest("asList(Boolean.TRUE)", makeListValue(true), BOOLEAN_LIST, findElement("aBooleanList"));

        doTest("asList(new java.math.BigDecimal(1.0))", makeListValue("1.0"), NUMBER_LIST, null);
        doTest("asList(new java.math.BigDecimal(java.lang.Double.toString(1.0)))", makeListValue("1.0"), NUMBER_LIST, findElement("aNumberList"));
        doTest("asList(new java.math.BigDecimal(1.0))", makeListValue("1.0"), NUMBER_LIST, findElement("aDecisionNumberList"));
        doTest("asList(new java.math.BigDecimal(1.0))", makeListValue("\"1.0\""), NUMBER_LIST, null);
        doTest("asList(new java.math.BigDecimal(1.0))", makeListValue("\"1.0\""), NUMBER_LIST, findElement("aDecisionNumberList"));
        doTest("asList(new java.math.BigDecimal(1.0))", makeListValue(1.0F), NUMBER_LIST, null);
        doTest("asList(new java.math.BigDecimal(1.0))", makeListValue(1.0F), NUMBER_LIST, findElement("aDecisionNumberList"));
        doTest("asList(new java.math.BigDecimal(1.0))", makeListValue(1.0), NUMBER_LIST, null);
        doTest("asList(new java.math.BigDecimal(1.0))", makeListValue(1.0), NUMBER_LIST, findElement("aDecisionNumberList"));
        doTest("asList(new java.math.BigDecimal(3))", makeListValue(BigDecimal.valueOf(3)), NUMBER_LIST, null);
        doTest("asList(new java.math.BigDecimal(3))", makeListValue(BigDecimal.valueOf(3)), NUMBER_LIST, findElement("aDecisionNumberList"));
        doTest("asList(new java.math.BigDecimal(3.0))", makeListValue(BigDecimal.valueOf(3.0)), NUMBER_LIST, null);
        doTest("asList(new java.math.BigDecimal(3.0))", makeListValue(BigDecimal.valueOf(3.0)), NUMBER_LIST, findElement("aDecisionNumberList"));

        doTest("asList(date(\"1990-03-29\"))", makeListValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29")), DATE_LIST, null);
        doTest("asList(date(\"1990-03-29\"))", makeListValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29")), DATE_LIST, findElement("aDateList"));
        doTest("asList(time(\"12:00:00\"))", makeListValue(DATATYPE_FACTORY.newXMLGregorianCalendar("12:00:00")), TIME_LIST, null);
        doTest("asList(time(\"12:00:00\"))", makeListValue(DATATYPE_FACTORY.newXMLGregorianCalendar("12:00:00")), TIME_LIST, findElement("aTimeList"));
        doTest("asList(dateAndTime(\"1990-03-29T12:00:00\"))", makeListValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29T12:00:00")), DATE_AND_TIME_LIST, null);
        doTest("asList(dateAndTime(\"1990-03-29T12:00:00\"))", makeListValue(DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29T12:00:00")), DATE_AND_TIME_LIST, findElement("aDateTimeList"));
        doTest("asList(duration(\"P2Y3M\"))", makeListValue(DATATYPE_FACTORY.newDuration("P2Y3M")), YEARS_AND_MONTHS_DURATION_LIST, null);
        doTest("asList(duration(\"P2Y3M\"))", makeListValue(DATATYPE_FACTORY.newDuration("P2Y3M")), YEARS_AND_MONTHS_DURATION_LIST, findElement("aYearDurationList"));
        doTest("asList(duration(\"P2DT3H\"))", makeListValue(DATATYPE_FACTORY.newDuration("P2DT3H")), DAYS_AND_TIME_DURATION_LIST, null);
        doTest("asList(duration(\"P2DT3H\"))", makeListValue(DATATYPE_FACTORY.newDuration("P2DT3H")), DAYS_AND_TIME_DURATION_LIST, findElement("aDayDurationList"));
    }

    @Test
    public void testToNativeExpressionForComplexTypes() {
        Object[] values = new Object[] {
                "\"a\"", true, 1.0, DATATYPE_FACTORY.newXMLGregorianCalendar("1990-03-29"), DATATYPE_FACTORY.newDuration("P2Y3M")
        };
        Type[] types = new Type[] {
                STRING, BOOLEAN, NUMBER, DATE, YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION, STRING
        };
        ValueType value = makeComponentValue(values);
        Type itemDefinitionType = makeItemDefinitionType(types);
        Type contextType = makeContextType(types);

        doTest("new type.TestImpl(\"a\", Boolean.TRUE, new java.math.BigDecimal(1.0), date(\"1990-03-29\"), duration(\"P2Y3M\"), DEFAULT_STRING)", value, itemDefinitionType, null);
        doTest("new type.TestImpl(\"a\", Boolean.TRUE, new java.math.BigDecimal(1.0), date(\"1990-03-29\"), duration(\"P2Y3M\"), DEFAULT_STRING)", value, itemDefinitionType, findElement("aDecisionComplex"));
        doTest("new com.gs.dmn.runtime.Context().add(\"m0\", \"a\").add(\"m1\", Boolean.TRUE).add(\"m2\", new java.math.BigDecimal(1.0)).add(\"m3\", date(\"1990-03-29\")).add(\"m4\", duration(\"P2Y3M\")).add(\"m5\", DEFAULT_STRING)", value, contextType, null);
        doTest("new com.gs.dmn.runtime.Context().add(\"m0\", \"a\").add(\"m1\", Boolean.TRUE).add(\"m2\", new java.math.BigDecimal(1.0)).add(\"m3\", date(\"1990-03-29\")).add(\"m4\", duration(\"P2Y3M\")).add(\"m5\", DEFAULT_STRING)", value, contextType, findElement("aDecisionComplex"));
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
        List value = new List();
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
        ItemDefinitionType result = makeItemDefinitionType("test");
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

}