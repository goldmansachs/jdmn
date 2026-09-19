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
package com.gs.dmn.feel.lib.type.string;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegexEvaluatorTest {

    private final RegexEvaluator saxon = new XPathRegexEvaluator();
    private final RegexEvaluator jdk = new JdkRegexEvaluator();

    @Test
    public void testMatchesParity() throws Exception {
        assertParityMatches("north-42", "^no.*-\\d+$", "");
        assertParityMatches("north-42", "^south", "");
        assertParityMatches("Widget", "widget", "i");
        assertParityMatches("line one\nline two", "^line two", "m");
        assertParityMatches("alpha\nbeta", "alpha.beta", "s");
        assertParityMatches("3+4", "3+4", "q");
        assertParityMatches("teal", " t e a l ", "x");
        assertParityMatches("code-9911", "[0-9]{4}", "");
        assertParityMatches("plain words", "\\d", "");
    }

    @Test
    public void testReplaceParity() throws Exception {
        assertParityReplace("aabbbaa", "b+", "-", "");
        assertParityReplace("x1y2z3", "(\\d)", "<$1>", "");
        assertParityReplace("2026-08-20", "(\\d{4})-(\\d{2})-(\\d{2})", "$3.$2.$1", "");
        assertParityReplace("Row Row Row", "row", "boat", "i");
    }

    @Test
    public void testSplitParity() throws Exception {
        assertParitySplit("red|green|blue", "\\|");
        assertParitySplit("k1=v1;k2=v2;", ";");
        assertParitySplit(";leading", ";");
        assertParitySplit("solo", ",");
        assertParitySplit("a,,b", ",");
    }

    @Test
    public void testJdkSplitRejectsPatternMatchingEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> jdk.evaluateSplit("abc", "x?"));
    }

    @Test
    public void testJdkRejectsInvalidFlag() {
        assertThrows(IllegalArgumentException.class, () -> jdk.evaluateMatches("abc", "a", "z"));
    }

    @Test
    public void testEngineSelectionBySystemProperty() throws Exception {
        String previous = System.setProperty(DefaultStringLib.REGEX_ENGINE_PROPERTY, "jdk");
        try {
            DefaultStringLib lib = new DefaultStringLib();
            assertTrue(lib.matches("north-42", "^no.*-\\d+$", ""));
            assertEquals(Arrays.asList("one", "two"), lib.split("one two", "\\s"));
            assertEquals("20.08.2026", lib.replace("2026-08-20", "(\\d{4})-(\\d{2})-(\\d{2})", "$3.$2.$1", ""));
        } finally {
            restore(previous);
        }
        previous = System.setProperty(DefaultStringLib.REGEX_ENGINE_PROPERTY, "invalid");
        try {
            DefaultStringLib lib = new DefaultStringLib();
            assertThrows(IllegalArgumentException.class, () -> lib.matches("north-42", "^no", ""));
        } finally {
            restore(previous);
        }
    }

    private void restore(String previous) {
        if (previous == null) {
            System.clearProperty(DefaultStringLib.REGEX_ENGINE_PROPERTY);
        } else {
            System.setProperty(DefaultStringLib.REGEX_ENGINE_PROPERTY, previous);
        }
    }

    private void assertParityMatches(String input, String pattern, String flags) throws Exception {
        assertEquals(saxon.evaluateMatches(input, pattern, flags), jdk.evaluateMatches(input, pattern, flags),
                "matches('" + input + "', '" + pattern + "', '" + flags + "')");
    }

    private void assertParityReplace(String input, String pattern, String replacement, String flags) throws Exception {
        assertEquals(saxon.evaluateReplace(input, pattern, replacement, flags),
                jdk.evaluateReplace(input, pattern, replacement, flags),
                "replace('" + input + "', '" + pattern + "', '" + replacement + "', '" + flags + "')");
    }

    private void assertParitySplit(String input, String pattern) throws Exception {
        List<String> expected = saxon.evaluateSplit(input, pattern);
        List<String> actual = jdk.evaluateSplit(input, pattern);
        assertEquals(expected, actual, "split('" + input + "', '" + pattern + "')");
    }
}
