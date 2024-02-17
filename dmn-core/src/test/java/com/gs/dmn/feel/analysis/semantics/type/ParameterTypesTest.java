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
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class ParameterTypesTest {
    private int count;

    @Test
    public void testSize() {
        Assertions.assertEquals(0, makeParameterTypes(new ArrayList<>()).size());
    }

    @Test
    public void testCompatibleWhenEmpty() {
        List<FormalParameter<Type>> formalParameters = makeFormalParameters(new ArrayList<>());
        assertTrue(FunctionType.ANY_FUNCTION.compatible(makeParameterTypes(formalParameters), formalParameters));
    }

    @Test
    public void testCompatible() {
        List<Type> parameterTypes = Arrays.asList(NumberType.NUMBER, StringType.STRING);
        List<FormalParameter<Type>> formalParameters = makeFormalParameters(parameterTypes);
        assertTrue(FunctionType.ANY_FUNCTION.compatible(makeParameterTypes(formalParameters), formalParameters));
    }

    protected List<FormalParameter<Type>> makeFormalParameters(List<Type> types) {
        return types.stream().map(t -> new FormalParameter<>(newName(), t)).collect(Collectors.toList());
    }

    protected String newName() {
        return "name_%d".formatted(count++);
    }

    protected abstract ParameterTypes<Type> makeParameterTypes(List<FormalParameter<Type>> parameterTypes);
}