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
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static org.junit.Assert.assertEquals;

public class TypeDeserializerTest {
    private final TypeDeserializer resolver = new TypeDeserializer();

    @Test
    public void testResolve() {
        // Data type
        assertEquals(NUMBER, resolver.deserialize(NUMBER.toString()));
        assertEquals(STRING, resolver.deserialize(STRING.toString()));
        assertEquals(BOOLEAN, resolver.deserialize(BOOLEAN.toString()));
        assertEquals(DateTimeType.DATE_AND_TIME, resolver.deserialize(DateTimeType.DATE_AND_TIME.toString()));
        assertEquals(TimeType.TIME, resolver.deserialize(TimeType.TIME.toString()));
        assertEquals(DateType.DATE, resolver.deserialize(DateType.DATE.toString()));
        assertEquals(DurationType.DAYS_AND_TIME_DURATION, resolver.deserialize(DurationType.DAYS_AND_TIME_DURATION.toString()));
        assertEquals(DurationType.YEARS_AND_MONTHS_DURATION, resolver.deserialize(DurationType.YEARS_AND_MONTHS_DURATION.toString()));

        // Simple types
        assertEquals(AnyType.ANY, resolver.deserialize(AnyType.ANY.toString()));
        assertEquals(NullType.NULL, resolver.deserialize(NullType.NULL.toString()));
        assertEquals(EnumerationType.ENUMERATION, resolver.deserialize(EnumerationType.ENUMERATION.toString()));

        // Composite types
        ContextType contextType = new ContextType(new LinkedHashMap() {{
            put("name", STRING);
        }});
        assertEquals(contextType, resolver.deserialize(contextType.toString()));

        ItemDefinitionType itemDefinitionType = new ItemDefinitionType("A");
        itemDefinitionType.addMember("m1", Arrays.asList("M1", "M11"), STRING);
        itemDefinitionType.addMember("m2", Arrays.asList("M2", "M21"), NUMBER);
        assertEquals(itemDefinitionType, resolver.deserialize(itemDefinitionType.toString()));

        ListType listType = new ListType(NUMBER);
        assertEquals(listType, resolver.deserialize(listType.toString()));

        RangeType rangeType = new RangeType(NUMBER);
        assertEquals(rangeType, resolver.deserialize(rangeType.toString()));

        TupleType tupleType = new TupleType(Arrays.asList(NUMBER, BOOLEAN));
        assertEquals(tupleType, resolver.deserialize(tupleType.toString()));

        FEELFunctionType FEELFunctionType = new FEELFunctionType(Arrays.asList(new FormalParameter("p1", STRING), new FormalParameter("p2", BOOLEAN)), STRING, true);
        assertEquals(FEELFunctionType, resolver.deserialize(FEELFunctionType.toString()));
        FEELFunctionType = new FEELFunctionType(Arrays.asList(new FormalParameter("p1", STRING), new FormalParameter("p2", BOOLEAN)), contextType, false);
        assertEquals(FEELFunctionType, resolver.deserialize(FEELFunctionType.toString()));
        FEELFunctionType = new FEELFunctionType(Arrays.asList(new FormalParameter("p1", STRING), new FormalParameter("p2", BOOLEAN)), itemDefinitionType, true);
        assertEquals(FEELFunctionType, resolver.deserialize(FEELFunctionType.toString()));
    }
}