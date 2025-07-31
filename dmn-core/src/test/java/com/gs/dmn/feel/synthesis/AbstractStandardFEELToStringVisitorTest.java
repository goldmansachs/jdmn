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
package com.gs.dmn.feel.synthesis;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public abstract class AbstractStandardFEELToStringVisitorTest extends AbstractFEELToStringVisitorTest {
    //
    // Test UnaryTests
    //
    @ParameterizedTest
    @CsvFileSource(resources = "/feel/serialization/standard-unary-tests.csv", numLinesToSkip = 1, delimiter = '|')
    public void testStandardUnaryTests(String input, String expected, String ast) {
        doUnaryTestsTest(input, ast, expected);
    }

    //
    // Test Expressions
    //
    @ParameterizedTest
    @CsvFileSource(resources = "/feel/serialization/standard-expressions.csv", numLinesToSkip = 1, delimiter = '|')
    public void testStandardExpressionsTests(String input, String expected, String ast) {
        doExpressionTest(input, ast, expected);
    }
}