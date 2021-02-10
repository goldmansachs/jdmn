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

import com.gs.dmn.feel.analysis.semantics.type.NumberType;
import com.gs.dmn.feel.analysis.semantics.type.StringType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class ParameterTypesTest {
    private int count;

    @Test
    public void testSize() {
        assertEquals(0, makeParameterTypes(new ArrayList<>()).size());
    }

    @Test
    public void testCompatibleWhenEmpty() {
        List<FormalParameter> formalParameters = makeFormalParameters(new ArrayList<>());
        assertTrue(makeParameterTypes(formalParameters).compatible(formalParameters));
    }

    @Test
    public void testCompatible() {
        List<Type> parameterTypes = Arrays.asList(NumberType.NUMBER, StringType.STRING);
        List<FormalParameter> formalParameters = makeFormalParameters(parameterTypes);
        assertTrue(makeParameterTypes(formalParameters).compatible(formalParameters));
    }

    protected List<FormalParameter> makeFormalParameters(List<Type> types) {
        return types.stream().map(t -> new FormalParameter(newName(), t)).collect(Collectors.toList());
    }

    protected String newName() {
        return String.format("name_%d", count++);
    }

    protected abstract ParameterTypes makeParameterTypes(List<FormalParameter> parameterTypes);
}