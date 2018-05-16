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
package com.gs.dmn.feel.analysis.semantics.type;

import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.runtime.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.*;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;
import static org.junit.Assert.assertEquals;

public class TypeConformanceTest {
    public final Map<Pair<Type, Type>, Boolean> dataTypesTable = new LinkedHashMap() {{
        put(new Pair(NUMBER, NUMBER), true);
        put(new Pair(NUMBER, ANY), true);

        put(new Pair(BOOLEAN, BOOLEAN), true);
        put(new Pair(BOOLEAN, ANY), true);

        put(new Pair(STRING, STRING), true);
        put(new Pair(STRING, ANY), true);

        put(new Pair(DATE, DATE), true);
        put(new Pair(DATE, ANY), true);

        put(new Pair(DATE_AND_TIME, DATE_AND_TIME), true);
        put(new Pair(DATE_AND_TIME, DATE_TIME), true);
        put(new Pair(DATE_AND_TIME, DATE_TIME_CAMEL), true);
        put(new Pair(DATE_AND_TIME, ANY), true);

        put(new Pair(DATE_TIME, DATE_AND_TIME), true);
        put(new Pair(DATE_TIME, DATE_TIME), true);
        put(new Pair(DATE_TIME, DATE_TIME_CAMEL), true);
        put(new Pair(DATE_TIME, ANY), true);

        put(new Pair(DATE_TIME_CAMEL, DATE_AND_TIME), true);
        put(new Pair(DATE_TIME_CAMEL, DATE_TIME), true);
        put(new Pair(DATE_TIME_CAMEL, DATE_TIME_CAMEL), true);
        put(new Pair(DATE_TIME_CAMEL, ANY), true);

        put(new Pair(TIME, TIME), true);
        put(new Pair(TIME, ANY), true);

        put(new Pair(DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), true);
        put(new Pair(DAYS_AND_TIME_DURATION, ANY), true);

        put(new Pair(YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), true);
        put(new Pair(YEARS_AND_MONTHS_DURATION, ANY), true);

        put(new Pair(ANY, ANY), true);

        put(new Pair(NUMBER, STRING), false);
        put(new Pair(BOOLEAN, NUMBER), false);
        put(new Pair(DATE, NUMBER), false);
        put(new Pair(DATE_AND_TIME, NUMBER), false);
        put(new Pair(DATE_TIME_CAMEL, NUMBER), false);
        put(new Pair(TIME, NUMBER), false);
        put(new Pair(ANY, NUMBER), false);
    }};

    @Test
    public void testDataTypes() {
        // data types
        for (Pair<Type, Type> p : dataTypesTable.keySet()) {
            Type left = p.getLeft();
            Type right = p.getRight();
            checkConformsTo(dataTypesTable.get(p), left, right);
        }
    }

    @Test
    public void testFunctionType() {
        FEELFunctionType type1 = new FEELFunctionType(Arrays.asList(new FormalParameter("p", STRING)), STRING, false);
        FEELFunctionType type2 = new FEELFunctionType(Arrays.asList(new FormalParameter("p", NUMBER)), STRING, false);
        FEELFunctionType tyep3 = new FEELFunctionType(Arrays.asList(new FormalParameter("p1", STRING), new FormalParameter("p2", NUMBER)), STRING, false);

        checkConformsTo(true, type1, type1);
        checkConformsTo(true, type1, ANY);

        checkConformsTo(false, type1, type2);
        checkConformsTo(false, type1, tyep3);
        checkConformsTo(false, type1, NUMBER);
    }

    @Test
    public void testListType() {
        checkConformsTo(true, ListType.BOOLEAN_LIST, ListType.BOOLEAN_LIST);
        checkConformsTo(true, ListType.NUMBER_LIST, ListType.NUMBER_LIST);
        checkConformsTo(true, ListType.ANY_LIST, ListType.ANY_LIST);

        checkConformsTo(false, ListType.BOOLEAN_LIST, ListType.NUMBER_LIST);
        checkConformsTo(false, ListType.NUMBER_LIST, ListType.BOOLEAN_LIST);
        checkConformsTo(false, ListType.ANY_LIST, ListType.BOOLEAN_LIST);
    }

    @Test
    public void testContextType() {
        ContextType type1 = new ContextType(new LinkedHashMap() {{
            put("m", NUMBER);
        }});
        ContextType type2 = new ContextType(new LinkedHashMap() {{
            put("m", BOOLEAN);
        }});
        ContextType type3 = new ContextType(new LinkedHashMap() {{
            put("m", NUMBER);
            put("n", NUMBER);
        }});

        checkConformsTo(true, type1, type1);
        checkConformsTo(true, type1, ANY);

        checkConformsTo(false, type1, type2);
        checkConformsTo(false, type1, type3);
        checkConformsTo(true, type3, type1);
        checkConformsTo(false, type1, NUMBER);
    }

    @Test
    public void testItemDefinitionType() {
        ItemDefinitionType type1 = new ItemDefinitionType("ID1");
        type1.addMember("m", Arrays.asList(), NUMBER);

        ItemDefinitionType type2 = new ItemDefinitionType("ID2");
        type2.addMember("m", Arrays.asList(), BOOLEAN);

        ItemDefinitionType type3 = new ItemDefinitionType("ID3");
        type3.addMember("m", Arrays.asList(), NUMBER);
        type3.addMember("x", Arrays.asList(), NUMBER);

        checkConformsTo(true, type1, ANY);
        checkConformsTo(true, type1, type1);

        checkConformsTo(false, type1, type2);
        checkConformsTo(false, type1, type3);
        checkConformsTo(true, type3, type1);
        checkConformsTo(false, type1, NUMBER);
    }

    @Test
    public void testRangeType() {
        checkConformsTo(true, RangeType.NUMBER_RANGE_TYPE, RangeType.NUMBER_RANGE_TYPE);
        checkConformsTo(true, RangeType.NUMBER_RANGE_TYPE, ANY);

        checkConformsTo(false, RangeType.NUMBER_RANGE_TYPE, NUMBER);
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
        NullType type = NullType.NULL;

        checkConformsTo(true, type, type);

        checkConformsTo(true, type, ANY);
        checkConformsTo(true, type, STRING);
        checkConformsTo(true, type, BOOLEAN);
        checkConformsTo(true, type, NUMBER);
    }

    private void checkConformsTo(boolean expected, Type left, Type right) {
        assertEquals(String.format("'%s'.conformsTo('%s') mismatch ", left, right), expected, left.conformsTo(right));
    }
}