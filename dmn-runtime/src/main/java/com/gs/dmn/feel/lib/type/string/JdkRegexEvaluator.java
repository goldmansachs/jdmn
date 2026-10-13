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

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Implements the FEEL string functions replace(), matches() and split() with
 * {@code java.util.regex} — no external dependency.
 *
 * <p>The XPath flags are mapped to the corresponding {@link Pattern} flags:
 * {@code i} → CASE_INSENSITIVE+UNICODE_CASE, {@code s} → DOTALL,
 * {@code m} → MULTILINE, {@code x} → COMMENTS, {@code q} → LITERAL.
 *
 * <p>Known deviations from the XPath F&amp;O regex dialect required by the DMN
 * specification:
 * <ul>
 *   <li>character class subtraction {@code [a-z-[aeiou]]} is not supported
 *       (Java equivalent: {@code [a-z&&[^aeiou]]})</li>
 *   <li>the XML character classes {@code \i} and {@code \c} are not supported</li>
 *   <li>replacement strings that are invalid in XPath (escapes other than
 *       {@code $N}, {@code \$}, {@code \\}) are not always rejected</li>
 * </ul>
 */
class JdkRegexEvaluator implements RegexEvaluator {

    @Override
    public String evaluateReplace(String input, String pattern, String replacement, String flags) {
        // XPath-valid replacements ($N, \$, \\) have identical semantics in
        // Matcher.replaceAll, so the replacement string is passed through unchanged.
        return compile(pattern, flags).matcher(input).replaceAll(replacement);
    }

    @Override
    public boolean evaluateMatches(String input, String pattern, String flags) {
        // fn:matches is unanchored, i.e. Matcher.find, not Matcher.matches
        return compile(pattern, flags).matcher(input).find();
    }

    @Override
    public List<String> evaluateSplit(String input, String pattern) {
        Pattern compiled = compile(pattern, "");
        if (compiled.matcher("").find()) {
            // XPath error FORX0003: the tokenize pattern must not match the empty string
            throw new IllegalArgumentException(
                    "split() pattern '" + pattern + "' matches the empty string");
        }
        // limit -1 keeps trailing empty tokens, matching fn:tokenize
        return Arrays.asList(compiled.split(input, -1));
    }

    private static Pattern compile(String pattern, String flags) {
        int patternFlags = 0;
        if (flags != null) {
            for (char flag : flags.toCharArray()) {
                switch (flag) {
                    case 'i': patternFlags |= Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE; break;
                    case 's': patternFlags |= Pattern.DOTALL; break;
                    case 'm': patternFlags |= Pattern.MULTILINE; break;
                    case 'x': patternFlags |= Pattern.COMMENTS; break;
                    case 'q': patternFlags |= Pattern.LITERAL; break;
                    default:
                        // XPath error FORX0001: invalid regular expression flag
                        throw new IllegalArgumentException(
                                "Invalid regular expression flag '" + flag + "' in '" + flags + "'");
                }
            }
        }
        return Pattern.compile(pattern, patternFlags);
    }
}
