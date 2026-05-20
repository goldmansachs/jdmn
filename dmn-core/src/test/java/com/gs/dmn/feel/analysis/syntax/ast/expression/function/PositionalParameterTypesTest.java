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

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PositionalParameterTypesTest {

    @Test
    void toString_does_not_propagate_exception_from_element_toString() {
        // Reproduces: NPE propagated from PositionalParameterTypes.toString() when a
        // Type element's own toString() throws. This occurred during error-message
        // formatting in FunctionInvocationUtils.functionResolution(), masking the
        // original type-resolution failure with an uninformative NullPointerException.
        Object faultyType = new Object() {
            @Override
            public String toString() {
                throw new NullPointerException("null field inside type");
            }
        };

        PositionalParameterTypes<Object> params =
                new PositionalParameterTypes<>(List.of(faultyType));

        assertDoesNotThrow(params::toString);
    }

    @Test
    void toString_handles_null_element() {
        PositionalParameterTypes<Object> params =
                new PositionalParameterTypes<>(Arrays.asList(null, "SomeType"));

        assertDoesNotThrow(params::toString);
    }
}
