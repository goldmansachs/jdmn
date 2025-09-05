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

import com.gs.dmn.AbstractTest;
import com.gs.dmn.context.environment.VariableDeclaration;
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
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.*;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DaysAndTimeDurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DaysAndTimeDurationType.DAY_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;
import static com.gs.dmn.feel.analysis.semantics.type.YearsAndMonthsDurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.YearsAndMonthsDurationType.YEAR_MONTH_DURATION;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TypeEquivalenceTest extends AbstractTest {
    public final Map<Pair<Type, Type>, Boolean> dataTypeTable = new LinkedHashMap<>() {{
        put(new Pair<>(NUMBER, ANY), false);
        put(new Pair<>(NUMBER, COMPARABLE), false);
        put(new Pair<>(NUMBER, NUMBER), true);

        put(new Pair<>(BOOLEAN, ANY), false);
        put(new Pair<>(BOOLEAN, BOOLEAN), true);

        put(new Pair<>(STRING, ANY), false);
        put(new Pair<>(STRING, COMPARABLE), false);
        put(new Pair<>(STRING, STRING), true);

        put(new Pair<>(DATE, ANY), false);
        put(new Pair<>(DATE, COMPARABLE), false);
        put(new Pair<>(DATE, TEMPORAL), false);
        put(new Pair<>(DATE, DATE), true);

        put(new Pair<>(TIME, ANY), false);
        put(new Pair<>(TIME, COMPARABLE), false);
        put(new Pair<>(TIME, TEMPORAL), false);
        put(new Pair<>(TIME, TIME), true);

        put(new Pair<>(DATE_AND_TIME, ANY), false);
        put(new Pair<>(DATE_AND_TIME, COMPARABLE), false);
        put(new Pair<>(DATE_AND_TIME, TEMPORAL), false);
        put(new Pair<>(DATE_AND_TIME, DATE_AND_TIME), true);
        put(new Pair<>(DATE_AND_TIME, DATE_TIME), true);
        put(new Pair<>(DATE_AND_TIME, DATE_TIME_CAMEL), true);

        put(new Pair<>(DATE_TIME, ANY), false);
        put(new Pair<>(DATE_TIME, COMPARABLE), false);
        put(new Pair<>(DATE_TIME, TEMPORAL), false);
        put(new Pair<>(DATE_TIME, DATE_AND_TIME), true);
        put(new Pair<>(DATE_TIME, DATE_TIME), true);
        put(new Pair<>(DATE_TIME, DATE_TIME_CAMEL), true);

        put(new Pair<>(DATE_TIME_CAMEL, ANY), false);
        put(new Pair<>(DATE_TIME_CAMEL, COMPARABLE), false);
        put(new Pair<>(DATE_TIME_CAMEL, TEMPORAL), false);
        put(new Pair<>(DATE_TIME_CAMEL, DATE_AND_TIME), true);
        put(new Pair<>(DATE_TIME_CAMEL, DATE_TIME), true);
        put(new Pair<>(DATE_TIME_CAMEL, DATE_TIME_CAMEL), true);

        put(new Pair<>(DAY_TIME_DURATION, ANY), false);
        put(new Pair<>(DAY_TIME_DURATION, COMPARABLE), false);
        put(new Pair<>(DAY_TIME_DURATION, TEMPORAL), false);
        put(new Pair<>(DAYS_AND_TIME_DURATION, DAYS_AND_TIME_DURATION), true);
        put(new Pair<>(DAYS_AND_TIME_DURATION, DAY_TIME_DURATION), true);
        put(new Pair<>(DAY_TIME_DURATION, DAY_TIME_DURATION), true);
        put(new Pair<>(DAY_TIME_DURATION, DAYS_AND_TIME_DURATION), true);
        put(new Pair<>(DAYS_AND_TIME_DURATION, ANY), false);

        put(new Pair<>(YEAR_MONTH_DURATION, ANY), false);
        put(new Pair<>(YEAR_MONTH_DURATION, COMPARABLE), false);
        put(new Pair<>(YEAR_MONTH_DURATION, TEMPORAL), false);
        put(new Pair<>(YEARS_AND_MONTHS_DURATION, YEARS_AND_MONTHS_DURATION), true);
        put(new Pair<>(YEARS_AND_MONTHS_DURATION, YEAR_MONTH_DURATION), true);
        put(new Pair<>(YEAR_MONTH_DURATION, YEARS_AND_MONTHS_DURATION), true);
        put(new Pair<>(YEAR_MONTH_DURATION, YEAR_MONTH_DURATION), true);

        put(new Pair<>(ANY, ANY), true);

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
        for (Map.Entry<Pair<Type, Type>, Boolean> e : dataTypeTable.entrySet()) {
            Pair<Type, Type> p = e.getKey();
            Type left = p.getLeft();
            Type right = p.getRight();
            checkEquivalentTo(e.getValue(), left, right);
        }
    }

    @Test
    public void testNullType() {
        NullType type = NullType.NULL;

        checkEquivalentTo(true, type, type);

        checkEquivalentTo(false, type, ANY);
        checkEquivalentTo(false, type, STRING);
        checkEquivalentTo(false, type, BOOLEAN);
        checkEquivalentTo(false, type, NUMBER);
    }

    @Test
    public void testEnumerationType() {
        Type type = EnumerationType.ENUMERATION;

        checkEquivalentTo(true, type, type);

        checkEquivalentTo(false, type, ANY);
        checkEquivalentTo(false, type, STRING);
        checkEquivalentTo(false, type, BOOLEAN);
        checkEquivalentTo(false, type, NUMBER);
    }

    @Test
    public void testListType() {
        checkEquivalentTo(true, ListType.BOOLEAN_LIST, ListType.BOOLEAN_LIST);
        checkEquivalentTo(true, ListType.NUMBER_LIST, ListType.NUMBER_LIST);
        checkEquivalentTo(true, ListType.ANY_LIST, ListType.ANY_LIST);
        checkEquivalentTo(true, ListType.EMPTY_LIST, ListType.EMPTY_LIST);

        checkEquivalentTo(false, ListType.BOOLEAN_LIST, ListType.NUMBER_LIST);
        checkEquivalentTo(false, ListType.NUMBER_LIST, ListType.BOOLEAN_LIST);
        checkEquivalentTo(false, ListType.ANY_LIST, ListType.BOOLEAN_LIST);
        checkEquivalentTo(false, ListType.EMPTY_LIST, ListType.BOOLEAN_LIST);
        checkEquivalentTo(false, ListType.EMPTY_LIST, ListType.ANY_LIST);
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

        checkEquivalentTo(true, type1, type1);

        checkEquivalentTo(false, type1, ANY);
        checkEquivalentTo(false, type1, type2);
        checkEquivalentTo(false, type1, type3);
        checkEquivalentTo(false, type1, NUMBER);
    }

    @Test
    public void testItemDefinitionType() {
        ItemDefinitionType type1 = makeItemDefinitionType("ID1");
        type1.addMember("m", Collections.emptyList(), NUMBER);

        ItemDefinitionType type2 = makeItemDefinitionType("ID2");
        type2.addMember("m", Collections.emptyList(), BOOLEAN);

        ItemDefinitionType type3 = makeItemDefinitionType("ID3");
        type3.addMember("m", Collections.emptyList(), NUMBER);
        type3.addMember("x", Collections.emptyList(), NUMBER);

        checkEquivalentTo(true, type1, type1);

        checkEquivalentTo(false, type1, ANY);
        checkEquivalentTo(false, type1, type2);
        checkEquivalentTo(false, type1, type3);
        checkEquivalentTo(false, type1, NUMBER);
    }

    @Test
    public void testRangeType() {
        checkEquivalentTo(true, RangeType.NUMBER_RANGE, RangeType.NUMBER_RANGE);

        checkEquivalentTo(false, RangeType.NUMBER_RANGE, ANY);
        checkEquivalentTo(false, RangeType.NUMBER_RANGE, NUMBER);
    }

    @Test
    public void testTupleType() {
        TupleType type1 = new TupleType(Arrays.asList(NUMBER, BOOLEAN, DATE));
        TupleType type2 = new TupleType(Arrays.asList(BOOLEAN, DATE));
        TupleType type3 = new TupleType(Arrays.asList(NUMBER, BOOLEAN));

        checkEquivalentTo(true, type1, type1);

        checkEquivalentTo(false, type1, ANY);
        checkEquivalentTo(false, type1, type2);
        checkEquivalentTo(false, type1, type3);
        checkEquivalentTo(false, type1, NUMBER);
    }

    @Test
    public void testDMNFunctionType() {
        FunctionType type1 = new DMNFunctionType(Collections.singletonList(new FormalParameter<>("p", STRING)), STRING, null);
        FunctionType type2 = new DMNFunctionType(Collections.singletonList(new FormalParameter<>("p", NUMBER)), STRING, null);
        FunctionType type3 = new DMNFunctionType(Arrays.asList(new FormalParameter<>("p1", STRING), new FormalParameter<>("p2", NUMBER)), STRING, null);

        checkFunctionType(type1, type1, type2, type3);
    }

    @Test
    public void testFEELFunctionType() {
        FunctionType type1 = new FEELFunctionType(Collections.singletonList(new FormalParameter<>("p", STRING)), STRING, false);
        FunctionType type2 = new FEELFunctionType(Collections.singletonList(new FormalParameter<>("p1", NUMBER)), STRING, false);
        FunctionType type3 = new FEELFunctionType(Arrays.asList(new FormalParameter<>("p1", STRING), new FormalParameter<>("p2", NUMBER)), STRING, false);

        checkFunctionType(type1, type1, type2, type3);
    }

    @Test
    public void testBuiltinFunctionType() {
        FunctionType type1 = new BuiltinFunctionType(STRING, new FormalParameter<>("p", STRING));
        FunctionType type2 = new BuiltinFunctionType(STRING, new FormalParameter<>("p", NUMBER));
        FunctionType type3 = new BuiltinFunctionType(STRING, new FormalParameter<>("p1", STRING), new FormalParameter<>("p2", NUMBER));

        checkFunctionType(type1, type1, type2, type3);
    }

    @Test
    public void testBuiltinOverloadedFunctionType() {
        FunctionType functionType1 = new BuiltinFunctionType(STRING, new FormalParameter<>("p", STRING));
        FunctionType functionType2 = new BuiltinFunctionType(STRING, new FormalParameter<>("p", NUMBER));
        FunctionType functionType3 = new BuiltinFunctionType(STRING, new FormalParameter<>("p1", STRING), new FormalParameter<>("p2", NUMBER));

        FunctionType type1 = new BuiltinOverloadedFunctionType(Collections.singletonList(new VariableDeclaration("f1", functionType1)));

        checkFunctionType(type1, functionType1, functionType2, functionType3);
    }

    @Test
    public void testLibraryFunctionType() {
        FunctionType type1 = new LibraryFunctionType(Collections.singletonList(new FormalParameter<>("p", STRING)), STRING);
        FunctionType type2 = new LibraryFunctionType(Collections.singletonList(new FormalParameter<>("p", NUMBER)), STRING);
        FunctionType type3 = new LibraryFunctionType(Arrays.asList(new FormalParameter<>("p1", STRING), new FormalParameter<>("p2", NUMBER)), STRING);

        checkFunctionType(type1, type1, type2, type3);
    }

    private void checkFunctionType(FunctionType type, FunctionType type1, FunctionType type2, FunctionType type3) {
        checkEquivalentTo(true, type, type1);

        checkEquivalentTo(false, type, ANY);
        checkEquivalentTo(false, type, type2);
        checkEquivalentTo(false, type, type3);
        checkEquivalentTo(false, type, NUMBER);
    }

    private void checkEquivalentTo(boolean expected, Type left, Type right) {
        assertEquals(expected, com.gs.dmn.el.analysis.semantics.type.Type.equivalentTo(left, right), String.format("'%s'.equivalentTo('%s') mismatch ", left, right));
    }
}