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
package com.gs.dmn.error;

import org.junit.jupiter.api.Test;

import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static org.junit.jupiter.api.Assertions.*;

class ErrorFactoryTest {
    private final String errorMessage = "Error XXX";

    @Test
    void testMakeDMNErrorMessage() {
        assertEquals(errorMessage, ErrorFactory.makeDMNErrorMessage(null, null, errorMessage));
    }

    @Test
    void testMakeDMNExpressionErrorMessage() {
        assertEquals(errorMessage + " for expression 'null'", ErrorFactory.makeDMNExpressionErrorMessage(null, null, null, errorMessage));
    }

    @Test
    void testMakeELExpressionErrorMessage() {
        assertEquals(errorMessage + " for expression 'null'", ErrorFactory.makeELExpressionErrorMessage(null, null, null, errorMessage));
    }

    @Test
    void testMakeIfErrorMessage() {
        assertEquals("Types of then and else branches are incompatible, found 'number' and 'string'", ErrorFactory.makeIfErrorMessage(null, NUMBER, STRING));
    }
}