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
package com.gs.dmn.feel.lib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StringEscapeUtilTest {
    @Test
    public void testUnescapeFEEL() {
        assertNull(StringEscapeUtil.unescapeFEEL(null));
        assertEquals("", StringEscapeUtil.unescapeFEEL(""));

        assertEquals("abc", StringEscapeUtil.unescapeFEEL("abc"));
        assertEquals("abc", StringEscapeUtil.unescapeFEEL("abc"));
        assertEquals("Don't consider", StringEscapeUtil.unescapeFEEL("\"Don't consider\""));

        assertEquals("\n", StringEscapeUtil.unescapeFEEL("\\n"));
        assertEquals("\r", StringEscapeUtil.unescapeFEEL("\\r"));
        assertEquals("\t", StringEscapeUtil.unescapeFEEL("\\t"));
        assertEquals("\'", StringEscapeUtil.unescapeFEEL("\\'"));
        assertEquals("\"", StringEscapeUtil.unescapeFEEL("\\\""));
        assertEquals("\\", StringEscapeUtil.unescapeFEEL("\\\\"));

        assertEquals("\\s", StringEscapeUtil.unescapeFEEL("\\s"));
        assertEquals("\\d", StringEscapeUtil.unescapeFEEL("\\d"));

        assertEquals("\t", StringEscapeUtil.unescapeFEEL("\\u0009"));
        assertEquals("\\u0009", StringEscapeUtil.unescapeFEEL("\\\\u0009"));

        assertEquals("\uD83D\uDCA9", StringEscapeUtil.unescapeFEEL("\\uD83D\\uDCA9"));
        assertEquals("\ud83d\udca9", StringEscapeUtil.unescapeFEEL("\\ud83d\\udca9"));
        assertEquals("\ud83d\udc0e\uD83D\uDE00", StringEscapeUtil.unescapeFEEL("\\ud83d\\udc0e\\uD83D\\uDE00"));
        assertEquals("🐎😀", StringEscapeUtil.unescapeFEEL("🐎😀"));
    }

    @Test
    public void testEscapeFEEL() {
        assertNull(StringEscapeUtil.escapeFEEL(null));
        assertEquals("", StringEscapeUtil.escapeFEEL(""));

        assertEquals("abc", StringEscapeUtil.escapeFEEL("abc"));
        assertEquals("Don't consider", StringEscapeUtil.escapeFEEL("Don't consider"));

        assertEquals("\\n", StringEscapeUtil.escapeFEEL("\n"));
        assertEquals("\\r", StringEscapeUtil.escapeFEEL("\r"));
        assertEquals("\\t", StringEscapeUtil.escapeFEEL("\t"));
        assertEquals("'", StringEscapeUtil.escapeFEEL("\'"));
        assertEquals("\\\"", StringEscapeUtil.escapeFEEL("\""));
        assertEquals("\\\\", StringEscapeUtil.escapeFEEL("\\"));

        assertEquals("\\\\s", StringEscapeUtil.escapeFEEL("\\s"));
        assertEquals("\\\\d", StringEscapeUtil.escapeFEEL("\\d"));

        assertEquals("\\t", StringEscapeUtil.escapeFEEL("\u0009"));
        assertEquals("\\\\u0009", StringEscapeUtil.escapeFEEL("\\u0009"));

        assertEquals("\\uD83D\\uDCA9", StringEscapeUtil.escapeFEEL("\uD83D\uDCA9"));
        assertEquals("\\uD83D\\uDCA9", StringEscapeUtil.escapeFEEL("\ud83d\udca9"));
        assertEquals("\\uD83D\\uDC0E\\uD83D\\uDE00", StringEscapeUtil.escapeFEEL("\ud83d\udc0e\uD83D\uDE00"));
        assertEquals("\\uD83D\\uDC0E\\uD83D\\uDE00", StringEscapeUtil.escapeFEEL("🐎😀"));
    }

    @Test
    public void testEscapeInString() {
        assertNull(StringEscapeUtil.escapeInString(null));
        assertEquals("", StringEscapeUtil.escapeInString(""));

        assertEquals("abc", StringEscapeUtil.escapeInString("abc"));
        assertEquals("ab\\\"abc", StringEscapeUtil.escapeInString("ab\\\"abc"));
        assertEquals("ab\\\"abc", StringEscapeUtil.escapeInString("ab\"abc"));
        assertEquals("“£%$&3332", StringEscapeUtil.escapeInString("“£%$&3332"));
        assertEquals("ab\\\\dc", StringEscapeUtil.escapeInString("ab\\dc"));
        assertEquals("\u0009", StringEscapeUtil.escapeInString("\u0009"));
        assertEquals("\\u0009", StringEscapeUtil.escapeInString("\\u0009"));
        assertEquals("\uD83D\uDCA9", StringEscapeUtil.escapeInString("\uD83D\uDCA9"));
        assertEquals("\ud83d\udca9", StringEscapeUtil.escapeInString("\ud83d\udca9"));
        assertEquals("\ud83d\udc0e\uD83D\uDE00", StringEscapeUtil.escapeInString("\ud83d\udc0e\uD83D\uDE00"));
        assertEquals("🐎😀", StringEscapeUtil.escapeInString("🐎😀"));
    }
}