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

import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.ParameterTypes;
import com.gs.dmn.runtime.DMNContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public abstract class ParameterTypesTest {
    private int count;

    @Test
    public void testSize() {
        Assert.assertEquals(0, makeParameterTypes(new ArrayList<>()).size());
    }

    @Test
    public void testCompatibleWhenEmpty() {
        List<FormalParameter<Type, DMNContext>> formalParameters = makeFormalParameters(new ArrayList<>());
        assertTrue(FunctionType.ANY_FUNCTION.compatible(makeParameterTypes(formalParameters), formalParameters));
    }

    @Test
    public void testCompatible() {
        List<Type> parameterTypes = Arrays.asList(NumberType.NUMBER, StringType.STRING);
        List<FormalParameter<Type, DMNContext>> formalParameters = makeFormalParameters(parameterTypes);
        assertTrue(FunctionType.ANY_FUNCTION.compatible(makeParameterTypes(formalParameters), formalParameters));
    }

    protected List<FormalParameter<Type, DMNContext>> makeFormalParameters(List<Type> types) {
        return types.stream().map(t -> new FormalParameter<Type, DMNContext>(newName(), t)).collect(Collectors.toList());
    }

    protected String newName() {
        return String.format("name_%d", count++);
    }

    protected abstract ParameterTypes<Type, DMNContext> makeParameterTypes(List<FormalParameter<Type, DMNContext>> parameterTypes);
}