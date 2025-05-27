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

import com.gs.dmn.feel.lib.FormatUtils;
import net.sf.saxon.s9api.*;

import java.time.Duration;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DefaultStringLib implements StringLib {
    @Override
    public String string(Object from) {
        if (from == null) {
            return null;
        } else if (from instanceof Number) {
            return FormatUtils.formatNumber((Number) from);
        } else if (from instanceof Duration) {
            return FormatUtils.formatTemporal(from);
        } else if (from instanceof TemporalAccessor || from instanceof TemporalAmount) {
            return FormatUtils.formatTemporal(from);
        } else {
            return from.toString();
        }
    }

    @Override
    public Boolean contains(String string, String match) {
        if (string == null || match == null) {
            return null;
        }

        return string.contains(match);
    }

    @Override
    public Boolean startsWith(String string, String match) {
        if (string == null || match == null) {
            return null;
        }

        return string.startsWith(match);
    }

    @Override
    public Boolean endsWith(String string, String match) {
        if (string == null || match == null) {
            return null;
        }

        return string.endsWith(match);
    }

    @Override
    public Integer stringLength(String string) {
        if (string == null) {
            return null;
        }
        // The number of Unicode code units in the string
        int unicodeCodeUnitsCount = string.length();
        // The number of characters (Unicode code point)
        return string.codePointCount(0, unicodeCodeUnitsCount);
    }

    @Override
    public String substring(String string, Number startPosition) {
        if (string == null || startPosition == null) {
            return null;
        }

        int start = startPosition.intValue();
        if (start < 0) {
            start = string.length() + start;
        } else {
            --start;
        }

        int[] cps = string.codePoints().toArray();
        int end = cps.length;
        return appendCodePoints(cps, start, end);
    }

    @Override
    public String substring(String string, Number startPosition, Number length) {
        if (string == null || startPosition == null || length == null) {
            return null;
        }

        int start = startPosition.intValue();
        if (start < 0) {
            start = string.length() + start;
        } else {
            --start;
        }
        int[] cps = string.codePoints().toArray();
        int end = start + length.intValue();
        return appendCodePoints(cps, start, end);
    }

    private String appendCodePoints(int[] cps, int start, int end) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cps.length; i++) {
            if (i >= start && i < end) {
                result.appendCodePoint(cps[i]);
            }
        }
        return result.toString();
    }

    @Override
    public String upperCase(String string) {
        if (string == null) {
            return null;
        }

        return string.toUpperCase();
    }

    @Override
    public String lowerCase(String string) {
        if (string == null) {
            return null;
        }

        return string.toLowerCase();
    }

    @Override
    public String substringBefore(String string, String match) {
        if (string == null || match == null) {
            return null;
        }

        int i = string.indexOf(match);
        return i == -1 ? "" : string.substring(0, i);
    }

    @Override
    public String substringAfter(String string, String match) {
        if (string == null || match == null) {
            return null;
        }

        int i = string.indexOf(match);
        return i == -1 ? "" : string.substring(i + match.length());
    }

    @Override
    // Semantics according to DMN
    // https://www.w3.org/TR/xquery-operators/#func-replace
    public String replace(String input, String pattern, String replacement, String flags) throws Exception {
        if (input == null || pattern == null || replacement == null) {
            return null;
        }
        if (flags == null) {
            flags = "";
        }

        return evaluateReplace(input, pattern, replacement, flags);
    }

    @Override
    // Semantics according to DMN
    // See https://www.w3.org/TR/xquery-operators/#func-matches
    public Boolean matches(String input, String pattern, String flags) throws Exception {
        if (input == null || pattern == null) {
            return null;
        }
        if (flags == null) {
            flags = "";
        }

        return evaluateMatches(input, pattern, flags);
    }

    @Override
    public List<String> split(String string, String delimiter) throws Exception {
        if (string == null || delimiter == null) {
            return null;
        }
        if (string.isEmpty() || delimiter.isEmpty()) {
            return null;
        }

        return evaluateSplit(string, delimiter);
    }

    @Override
    public String stringJoin(List<String> list) {
        return stringJoin(list, null);
    }

    @Override
    public String stringJoin(List<String> list, String delimiter) {
        if (list == null) {
            return null;
        }
        if (delimiter == null) {
            delimiter = "";
        }

        return list.stream().filter(Objects::nonNull).collect(Collectors.joining(delimiter));
    }

    @Override
    public String min(List<?> list) {
        return minMax(list, x -> x > 0);
    }

    @Override
    public String max(List<?> list) {
        return minMax(list, x -> x < 0);
    }

    private String minMax(List<?> list, Predicate<Integer> condition) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        String result = (String) list.get(0);
        for (int i = 1; i < list.size(); i++) {
            String x = (String) list.get(i);
            if (condition.test(result.compareTo(x))) {
                result = x;
            }
        }
        return result;
    }

    private String evaluateReplace(String input, String pattern, String replacement, String flags) throws Exception {
        String functionName = "replace";
        List<String> paramNames = Arrays.asList("input", "pattern", "replacement", "flags");
        List<String> arguments = Arrays.asList(input, pattern, replacement, flags);
        XdmValue result = evaluateFunction(functionName, paramNames, arguments);
        return ((XdmAtomicValue) result).getStringValue();
    }

    private boolean evaluateMatches(String input, String pattern, String flags) throws Exception {
        String functionName = "matches";
        List<String> paramNames = Arrays.asList("input", "pattern", "flags");
        List<String> arguments = Arrays.asList(input, pattern, flags);
        XdmValue result = evaluateFunction(functionName, paramNames, arguments);
        return ((XdmAtomicValue) result).getBooleanValue();
    }

    private List<String> evaluateSplit(String input, String pattern) throws SaxonApiException {
        String functionName = "tokenize";
        List<String> paramNames = Arrays.asList("input", "pattern");
        List<String> arguments = Arrays.asList(input, pattern);
        XdmValue result = evaluateFunction(functionName, paramNames, arguments);
        // Iterate over the resulting tokens
        return result.stream().map(XdmItem::getStringValue).collect(Collectors.toList());
    }

    private static XdmValue evaluateFunction(String functionName, List<String> paramNames, List<String> arguments) throws SaxonApiException {
        // Create a Saxon processor
        Processor processor = new Processor(false);
        XPathCompiler xpathCompiler = processor.newXPathCompiler();

        // Declare variables in the XPath expression
        for (String param: paramNames) {
            xpathCompiler.declareVariable(new QName(param));
        }

        // Construct an XPath expression using the tokenize() function
        String call = String.format("%s(%s)", functionName, paramNames.stream().map(p -> "$"+p).collect(Collectors.joining(", ")));
        XPathExecutable compile = xpathCompiler.compile(call);
        XPathSelector selector = compile.load();

        // Set the input string and pattern as variables
        for (int i = 0; i< paramNames.size(); i++) {
            selector.setVariable(new QName(paramNames.get(i)), new XdmAtomicValue(arguments.get(i)));
        }

        // Evaluate the XPath expression
        return selector.evaluate();
    }
}
