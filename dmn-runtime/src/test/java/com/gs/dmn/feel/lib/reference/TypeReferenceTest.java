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
package com.gs.dmn.feel.lib.reference;

import com.gs.dmn.runtime.DMNRuntimeException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TypeReferenceTest {
    @Test
    public void testConstructor() {
        assertThrows(DMNRuntimeException.class, () -> check(new SimpleType("number"), "null"));

        // Primitive types
        check(new SimpleType("Null"), "Null");
        check(new SimpleType("Any"), "Any");
        check(new SimpleType("number"), "number");
        check(new SimpleType("string"), "string");
        check(new SimpleType("boolean"), "boolean");
        check(new SimpleType("date"), "date");
        check(new SimpleType("time"), "time");
        check(new SimpleType("date and time"), "date and time");
        check(new SimpleType("years and months duration"), "years and months duration");
        check(new SimpleType("days and time duration"), "days and time duration");

        // Composite types
        check(new ListType(new SimpleType("number")), "list<number>");
        check(new RangeType(new SimpleType("number")), "range<number>");
        check(new ContextType().addMember("a", new SimpleType("number")), "context<a:number>");
        check(new ContextType().addMember("a", new SimpleType("number")).addMember("b", new SimpleType("boolean")), "context<a:number, b:boolean>");
        check(new FunctionType(Collections.emptyList(), new SimpleType("number")), "function<> -> number");
        check(new FunctionType(Arrays.asList(new SimpleType("number"), new SimpleType("boolean")), new SimpleType("number")), "function<number, boolean> -> number");

        // Nested types
        check(new ListType(new RangeType(new SimpleType("number"))), "list<range<number>>");
    }

    private void check(Type expecytedType, String typeExpression) {
        TypeReference typeReference = new TypeReference(typeExpression);
        assertEquals(typeExpression, typeReference.getTypeExpression());
        assertEquals(expecytedType, typeReference.getType());
    }
}