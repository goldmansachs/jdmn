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
package com.gs.dmn.feel.analysis.syntax.ast.expression.function;

import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.NumberType;
import com.gs.dmn.feel.analysis.semantics.type.StringType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.runtime.Pair;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class NamedParameterTypesTest extends ParameterTypesTest {
    protected ParameterTypes getParameterTypes() {
        Map<String, Type> namedTypes = new LinkedHashMap<>();
        namedTypes.put("number", NumberType.NUMBER);
        namedTypes.put("string", StringType.STRING);
        return new NamedParameterTypes(namedTypes);
    }

    @Test
    public void testCandidates() {
        ParameterTypes pt = getParameterTypes();
        List<Pair<ParameterTypes, ParameterConversions>> candidates = pt.candidates();
        assertEquals(0, candidates.size());

        Map<String, Type> namedTypes = new LinkedHashMap<>();
        namedTypes.put("numbers", ListType.NUMBER_LIST);
        namedTypes.put("string", StringType.STRING);
        pt =  new NamedParameterTypes(namedTypes);
        candidates = pt.candidates();
        assertEquals(1, candidates.size());
        Pair<ParameterTypes, ParameterConversions> candidate = candidates.get(0);
        assertEquals("Pair(NamedParameterTypes(numbers : number, string : string), NamedParameterConversions({numbers=Conversion(LIST_TO_ELEMENT, number), string=Conversion(NONE, string)}))", candidate.toString());
    }

}