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

import org.apache.commons.lang3.text.translate.UnicodeEscaper;

public class FEELUnicodeEscaper extends UnicodeEscaper {
    public static FEELUnicodeEscaper above(final int codepoint) {
        return outsideOf(0, codepoint);
    }

    public static FEELUnicodeEscaper below(final int codepoint) {
        return outsideOf(codepoint, Integer.MAX_VALUE);
    }

    public static FEELUnicodeEscaper between(final int codepointLow, final int codepointHigh) {
        return new FEELUnicodeEscaper(codepointLow, codepointHigh, true);
    }

    public static FEELUnicodeEscaper outsideOf(final int codepointLow, final int codepointHigh) {
        return new FEELUnicodeEscaper(codepointLow, codepointHigh, false);
    }

    public FEELUnicodeEscaper(final int below, final int above, final boolean between) {
        super(below, above, between);
    }

    @Override
    protected String toUtf16Escape(final int codepoint) {
        final char[] surrogatePair = Character.toChars(codepoint);
        return "\\u" + hex(surrogatePair[0]) + "\\u" + hex(surrogatePair[1]);
    }
}
