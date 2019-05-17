/**
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

import static org.junit.Assert.*;

public class StringEscapeUtilTest {
    @Test
    public void testEscapeInString() {
        assertNull(StringEscapeUtil.escapeInString(null));
        assertEquals("", StringEscapeUtil.escapeInString(""));
        assertEquals("abc", StringEscapeUtil.escapeInString("abc"));
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