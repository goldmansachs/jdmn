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

import com.gs.dmn.ErrorFactory;
import com.gs.dmn.ModelCoordinates;
import com.gs.dmn.ast.TContext;
import com.gs.dmn.ast.TExpression;
import com.gs.dmn.ast.TLiteralExpression;
import org.junit.jupiter.api.Test;

import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorFactoryTest {
    private final String errorMessage = "Error XXX";
    private final String expectedErrorMessage = "[ERROR] " + errorMessage;

    @Test
    void testMakeDMNErrorMessage() {
        SemanticError error = ErrorFactory.makeDMNError(new ModelCoordinates(null, null), errorMessage);
        assertEquals(expectedErrorMessage, error.toText());
    }

    @Test
    void testMakeExpressionErrorMessage() {
        ModelCoordinates coordinates = new ModelCoordinates(null, null, null);
        SemanticError error = ErrorFactory.makeExpressionError(coordinates, errorMessage);
        assertEquals(expectedErrorMessage + " for expression 'null'", error.toText());
    }

    @Test
    void testMakeDMNExpressionErrorMessage() {
        TExpression expression = new TContext();
        ModelCoordinates coordinates = new ModelCoordinates(null, null, expression);
        SemanticError error = ErrorFactory.makeExpressionError(coordinates, errorMessage);
        assertEquals(expectedErrorMessage + " for expression 'TContext'", error.toText());
    }

    @Test
    void testMakeELExpressionErrorMessage() {
        TLiteralExpression expression = new TLiteralExpression();
        expression.setText("xxx");
        ModelCoordinates coordinates = new ModelCoordinates(null, null, expression);
        SemanticError error = ErrorFactory.makeExpressionError(coordinates, errorMessage);
        assertEquals(expectedErrorMessage + " for expression 'xxx'", error.toText());
    }

    @Test
    void testMakeIfErrorMessage() {
        SemanticError error = ErrorFactory.makeIfError(null, NUMBER, STRING);
        assertEquals("[ERROR] Types of then and else branches are incompatible, found 'number' and 'string'", error.toText());
    }
}