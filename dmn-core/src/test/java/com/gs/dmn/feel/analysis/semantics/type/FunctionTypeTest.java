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

import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterConversions;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterTypes;
import com.gs.dmn.runtime.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.feel.analysis.semantics.type.ListType.NUMBER_LIST;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static org.junit.Assert.assertEquals;

public abstract class FunctionTypeTest {
    protected abstract FunctionType makeFunctionType();

    @Test
    public void testCalculateCandidates() {
        doTest(Arrays.asList(), Arrays.asList(), "[]");
        doTest(Arrays.asList(NUMBER), Arrays.asList(NUMBER), "[]");
        doTest(Arrays.asList(NUMBER), Arrays.asList(NUMBER_LIST), "[" +
                 "Pair(PositionalParameterTypes(number), PositionalParameterConversions([Conversion(LIST_TO_ELEMENT, number)])), " +
                 "Pair(PositionalParameterTypes(NullType), PositionalParameterConversions([Conversion(CONFORMS_TO, NullType)]))" +
                 "]");
        doTest(Arrays.asList(NUMBER_LIST), Arrays.asList(NUMBER), "[" +
                "Pair(PositionalParameterTypes(ListType(number)), PositionalParameterConversions([Conversion(ELEMENT_TO_LIST, ListType(number))])), " +
                "Pair(PositionalParameterTypes(NullType), PositionalParameterConversions([Conversion(CONFORMS_TO, NullType)]))" +
                "]");
        doTest(Arrays.asList(NUMBER), Arrays.asList(STRING), "[" +
                "Pair(PositionalParameterTypes(NullType), PositionalParameterConversions([Conversion(CONFORMS_TO, NullType)]))" +
                "]");
    }

    private void doTest(List<Type> parameterTypes, List<Type> argumentTypes, String expectedCandidates) {
        List<Pair<ParameterTypes, ParameterConversions>> candidates = makeFunctionType().calculateCandidates(parameterTypes, argumentTypes);
        assertEquals(expectedCandidates, candidates.toString());
    }
}