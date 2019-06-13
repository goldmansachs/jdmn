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
package com.gs.dmn.feel.analysis.syntax.ast.expression.function;

import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.NumberType;
import com.gs.dmn.feel.analysis.semantics.type.StringType;
import com.gs.dmn.runtime.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PositionalParameterTypesTest extends ParameterTypesTest {
    protected ParameterTypes getParameterTypes() {
        return new PositionalParameterTypes(Arrays.asList(NumberType.NUMBER, StringType.STRING));
    }

    @Test
    public void testCandidates() {
        ParameterTypes pt = getParameterTypes();
        List<Pair<ParameterTypes, ParameterConversions>> candidates = pt.candidates();
        assertEquals(0, candidates.size());

        pt = new PositionalParameterTypes(Arrays.asList(ListType.NUMBER_LIST, StringType.STRING, ListType.STRING_LIST));
        candidates = pt.candidates();
        assertEquals(3, candidates.size());
        Pair<ParameterTypes, ParameterConversions> candidate = candidates.get(0);
        assertEquals("Pair(PositionalParameterTypes(ListType(number), string, string), PositionalParameterConversions([Conversion(NONE, ListType(number)), Conversion(NONE, string), Conversion(LIST_TO_ELEMENT, string)]))", candidate.toString());
        candidate = candidates.get(1);
        assertEquals("Pair(PositionalParameterTypes(number, string, ListType(string)), PositionalParameterConversions([Conversion(LIST_TO_ELEMENT, number), Conversion(NONE, string), Conversion(NONE, ListType(string))]))", candidate.toString());
        candidate = candidates.get(2);
        assertEquals("Pair(PositionalParameterTypes(number, string, string), PositionalParameterConversions([Conversion(LIST_TO_ELEMENT, number), Conversion(NONE, string), Conversion(LIST_TO_ELEMENT, string)]))", candidate.toString());
    }
}