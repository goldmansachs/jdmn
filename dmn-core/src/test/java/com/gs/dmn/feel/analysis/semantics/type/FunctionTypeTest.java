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

import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterConversions;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterTypes;
import com.gs.dmn.runtime.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.ListType.NUMBER_LIST;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class FunctionTypeTest {
    protected abstract FunctionType makeFunctionType();

    @Test
    public void testCalculateCandidates() {
        doTest(Collections.emptyList(), Collections.emptyList(), "[]");
        doTest(Collections.singletonList(NUMBER), Collections.singletonList(NUMBER), "[]");
        doTest(Collections.singletonList(NUMBER), Collections.singletonList(NUMBER_LIST), "[" +
                 "Pair(PositionalParameterTypes(number), PositionalParameterConversions([Conversion(SINGLETON_LIST_TO_ELEMENT, number)]))" +
                 "]");
        doTest(Collections.singletonList(NUMBER_LIST), Collections.singletonList(NUMBER), "[" +
                "Pair(PositionalParameterTypes(ListType(number)), PositionalParameterConversions([Conversion(ELEMENT_TO_SINGLETON_LIST, ListType(number))]))" +
                "]");
        doTest(Collections.singletonList(NUMBER), Collections.singletonList(STRING), "[" +
                "]");
        doTest(Arrays.asList(NUMBER, DATE_AND_TIME), Arrays.asList(NUMBER, DATE), "[" +
                "Pair(PositionalParameterTypes(number, date and time), PositionalParameterConversions([Conversion(NONE, number), Conversion(DATE_TO_UTC_MIDNIGHT, date and time)]))" +
                "]");
        doTest(Arrays.asList(DATE_AND_TIME), Arrays.asList(DATE, DATE), "[" +
                "Pair(PositionalParameterTypes(date and time, date and time), PositionalParameterConversions([Conversion(DATE_TO_UTC_MIDNIGHT, date and time), Conversion(DATE_TO_UTC_MIDNIGHT, date and time)]))" +
                "]");
    }

    private void doTest(List<Type> parameterTypes, List<Type> argumentTypes, String expectedCandidates) {
        List<Pair<ParameterTypes<Type>, ParameterConversions<Type>>> candidates = makeFunctionType().calculateCandidates(parameterTypes, argumentTypes);
        assertEquals(expectedCandidates, candidates.toString());
    }
}