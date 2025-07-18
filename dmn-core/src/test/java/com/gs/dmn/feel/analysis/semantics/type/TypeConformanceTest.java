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
package com.gs.dmn.feel.analysis.semantics.type;

import com.gs.dmn.el.analysis.semantics.type.NullType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.runtime.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.el.analysis.semantics.type.NullType.NULL;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.ContextType.CONTEXT;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.*;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DaysAndTimeDurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;
import static com.gs.dmn.feel.analysis.semantics.type.YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeConformanceTest {
    public final Map<Pair<Type, Type>, Boolean> dataTypesTable = new LinkedHashMap<>() {{
        // Concrete types
        put(new Pair<>(NUMBER, ANY), true);
        put(new Pair<>(NUMBER, COMPARABLE), true);
        put(new Pair<>(NUMBER, NUMBER), true);

        put(new Pair<>(BOOLEAN, ANY), true);
        put(new Pair<>(BOOLEAN, COMPARABLE), false);
        put(new Pair<>(BOOLEAN, BOOLEAN), true);

        put(new Pair<>(STRING, ANY), true);
        put(new Pair<>(NUMBER, COMPARABLE), true);
        put(new Pair<>(STRING, STRING), true);

        put(new Pair<>(DATE, ANY), true);
        put(new Pair<>(DATE, COMPARABLE), true);
        put(new Pair<>(DATE, TEMPORAL), true);
        put(new Pair<>(DATE, DATE), true);

        put(new Pair<>(TIME, ANY), true);
        put(new Pair<>(TIME, COMPARABLE), true);
        put(new Pair<>(TIME, TEMPORAL), true);
        put(new Pair<>(TIME, TIME), true);

        put(new Pair<>(DATE_AND_TIME, ANY), true);
        put(new Pair<>(DATE_AND_TIME, COMPARABLE), true);
        put(new Pair<>(DATE_AND_TIME, TEMPORAL), true);
        put(new Pair<>(DATE_AND_TIME, DATE_AND_TIME), true);
        put(new Pair<>(DATE_AND_TIME, DATE_TIME), true);
        put(new Pair<>(DATE_AND_TIME, DATE_TIME_CAMEL), true);

        put(new Pair<>(DATE_TIME, ANY), true);
        put(new Pair<>(DATE_TIME, COMPARABLE), true);
        put(new Pair<>(DATE_TIME, TEMPORAL), true);
        put(new Pair<>(DATE_TIME, DATE_AND_TIME), true);
        put(new Pair<>(DATE_TIME, DATE_TIME), true);
        put(new Pair<>(DATE_TIME, DATE_TIME_CAMEL), true);

        put(new Pair<>(DATE_TIME_CAMEL, ANY), true);
        put(new Pair<>(DATE_TIME_CAMEL, COMPARABLE), true);
        put(new Pair<>(DATE_TIME_CAMEL, TEMPORAL), true);
        put(new Pair<>(DATE_TIME_CAMEL, DATE_AND_TIME), true);
        put(new Pair<>(DATE_TIME_CAMEL, DATE_TIME), true);
        put(new Pair<>(DATE_TIME_CAMEL, DATE_TIME_CAMEL), true);

        put(new Pair<>(DAYS_AND_TIME_DURATION, ANY), true);
        put(new Pair<>(DAYS_AND_TIME_DURATION, COMPARABLE), true);
        put(new Pair<>(DAYS_AND_TIME_DURATION, DURATION), true);
        put(new Pair<>(DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), true);

        put(new Pair<>(YEARS_AND_MONTHS_DURATION, ANY), true);
        put(new Pair<>(YEARS_AND_MONTHS_DURATION, COMPARABLE), true);
        put(new Pair<>(YEARS_AND_MONTHS_DURATION, DURATION), true);
        put(new Pair<>(YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), true);

        // Abstract types
        put(new Pair<>(ANY, ANY), true);

        put(new Pair<>(COMPARABLE, ANY), true);
        put(new Pair<>(COMPARABLE, COMPARABLE), true);

        put(new Pair<>(TEMPORAL, COMPARABLE), true);
        put(new Pair<>(TEMPORAL, TEMPORAL), true);

        put(new Pair<>(DURATION, COMPARABLE), true);
        put(new Pair<>(DURATION, DURATION), true);

        // Mixture
        put(new Pair<>(NUMBER, STRING), false);
        put(new Pair<>(BOOLEAN, NUMBER), false);
        put(new Pair<>(DATE, NUMBER), false);
        put(new Pair<>(DATE_AND_TIME, NUMBER), false);
        put(new Pair<>(DATE_TIME_CAMEL, NUMBER), false);
        put(new Pair<>(TIME, NUMBER), false);
        put(new Pair<>(ANY, NUMBER), false);
    }};

    @Test
    public void testDataTypes() {
        // data types
        for (Map.Entry<Pair<Type, Type>, Boolean> e : dataTypesTable.entrySet()) {
            Pair<Type, Type> p = e.getKey();
            Type left = p.getLeft();
            Type right = p.getRight();
            checkConformsTo(e.getValue(), left, right);
        }
    }

    @Test
    public void testFunctionType() {
        FEELFunctionType type1 = new FEELFunctionType(Collections.singletonList(new FormalParameter<>("p", STRING)), STRING, false);
        FEELFunctionType type2 = new FEELFunctionType(Collections.singletonList(new FormalParameter<>("p", NUMBER)), STRING, false);
        FEELFunctionType type3 = new FEELFunctionType(Arrays.asList(new FormalParameter<>("p1", STRING), new FormalParameter<>("p2", NUMBER)), STRING, false);
        FEELFunctionType type4 = new FEELFunctionType(Collections.emptyList(), ANY, false);

        checkConformsTo(true, type1, type1);
        checkConformsTo(true, type1, ANY);

        checkConformsTo(false, type1, type2);
        checkConformsTo(false, type1, type3);
        checkConformsTo(false, type1, NUMBER);
        checkConformsTo(false, type1, type4);
    }

    @Test
    public void testListType() {
        checkConformsTo(true, ListType.BOOLEAN_LIST, ListType.BOOLEAN_LIST);
        checkConformsTo(true, ListType.NUMBER_LIST, ListType.NUMBER_LIST);
        checkConformsTo(true, ListType.ANY_LIST, ListType.ANY_LIST);
        checkConformsTo(true, ListType.EMPTY_LIST, ListType.EMPTY_LIST);

        checkConformsTo(true, ListType.EMPTY_LIST, ListType.BOOLEAN_LIST);
        checkConformsTo(true, ListType.EMPTY_LIST, ListType.NUMBER_LIST);
        checkConformsTo(true, ListType.EMPTY_LIST, ListType.ANY_LIST);
        checkConformsTo(false, ListType.BOOLEAN_LIST, ListType.NUMBER_LIST);
        checkConformsTo(false, ListType.NUMBER_LIST, ListType.BOOLEAN_LIST);
        checkConformsTo(false, ListType.ANY_LIST, ListType.BOOLEAN_LIST);
    }

    @Test
    public void testContextType() {
        ContextType type1 = new ContextType(new LinkedHashMap<>() {{
            put("m", NUMBER);
        }});
        ContextType type2 = new ContextType(new LinkedHashMap<>() {{
            put("m", BOOLEAN);
        }});
        ContextType type3 = new ContextType(new LinkedHashMap<>() {{
            put("m", NUMBER);
            put("n", NUMBER);
        }});
        ContextType type4 = new ContextType(new LinkedHashMap<>());

        checkConformsTo(true, type1, type1);
        checkConformsTo(true, type1, ANY);
        checkConformsTo(true, type1, CONTEXT);
        checkConformsTo(true, type1, type4);

        checkConformsTo(false, type1, type2);
        checkConformsTo(false, type1, type3);
        checkConformsTo(true, type3, type1);
        checkConformsTo(false, type1, NUMBER);

        checkConformsTo(true, type4, type4);
        checkConformsTo(true, type4, CONTEXT);
        checkConformsTo(true, CONTEXT, type4);
        checkConformsTo(false, CONTEXT, type1);
    }

    @Test
    public void testItemDefinitionType() {
        ItemDefinitionType type1 = new ItemDefinitionType("ID1");
        type1.addMember("m", Collections.emptyList(), NUMBER);

        ItemDefinitionType type2 = new ItemDefinitionType("ID2");
        type2.addMember("m", Collections.emptyList(), BOOLEAN);

        ItemDefinitionType type3 = new ItemDefinitionType("ID3");
        type3.addMember("m", Collections.emptyList(), NUMBER);
        type3.addMember("x", Collections.emptyList(), NUMBER);

        ContextType type4 = new ContextType(new LinkedHashMap<>());

        checkConformsTo(true, type1, ANY);
        checkConformsTo(true, type1, CONTEXT);
        checkConformsTo(true, type1, type4);
        checkConformsTo(true, type1, type1);

        checkConformsTo(false, type1, type2);
        checkConformsTo(false, type1, type3);
        checkConformsTo(true, type3, type1);
        checkConformsTo(false, type1, NUMBER);
        checkConformsTo(false, type4, type1);
        checkConformsTo(false, CONTEXT, type1);
    }

    @Test
    public void testRangeType() {
        checkConformsTo(true, RangeType.NUMBER_RANGE, RangeType.NUMBER_RANGE);
        checkConformsTo(true, RangeType.NUMBER_RANGE, ANY);

        checkConformsTo(false, RangeType.NUMBER_RANGE, NUMBER);
    }

    @Test
    public void testTupleType() {
        TupleType type1 = new TupleType(Arrays.asList(NUMBER, BOOLEAN, DATE));
        TupleType type2 = new TupleType(Arrays.asList(BOOLEAN, DATE));
        TupleType type3 = new TupleType(Arrays.asList(NUMBER, BOOLEAN));

        checkConformsTo(true, type1, type1);
        checkConformsTo(true, type1, ANY);

        checkConformsTo(false, type1, type2);
        checkConformsTo(false, type1, type3);
        checkConformsTo(false, type1, NUMBER);
    }

    @Test
    public void testNullType() {
        NullType type = NULL;

        checkConformsTo(true, type, type);

        checkConformsTo(true, type, ANY);
        checkConformsTo(true, type, COMPARABLE);
        checkConformsTo(true, type, TEMPORAL);
        checkConformsTo(true, type, DURATION);
        checkConformsTo(true, type, STRING);
        checkConformsTo(true, type, BOOLEAN);
        checkConformsTo(true, type, NUMBER);
    }

    private void checkConformsTo(boolean expected, Type left, Type right) {
        assertEquals(expected, com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(left, right), String.format("'%s'.conformsTo('%s') mismatch ", left, right));
    }
}