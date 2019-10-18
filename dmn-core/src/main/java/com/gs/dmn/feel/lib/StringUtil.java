/*
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.feel.lib;

import com.gs.dmn.serialization.XMLUtil;
import net.sf.saxon.xpath.XPathFactoryImpl;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static Boolean contains(String string, String match) {
        if (string == null || match == null) {
            return null;
        }

        return string.contains(match);
    }

    public static Boolean startsWith(String string, String match) {
        if (string == null || match == null) {
            return null;
        }

        return string.startsWith(match);
    }

    public static Boolean endsWith(String string, String match) {
        if (string == null || match == null) {
            return null;
        }

        return string.endsWith(match);
    }

    public static long stringLength(String string) {
        return string.length();
    }

    public static String substring(String string, Number startPosition) {
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
        String result = appendCodePoints(cps, start, end);
        return result;
    }

    public static String substring(String string, Number startPosition, Number length) {
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
        String result = appendCodePoints(cps, start, end);
        return result;
    }

    private static String appendCodePoints(int[] cps, int start, int end) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < cps.length; i++) {
            if (i >= start && i < end) {
                result.appendCodePoint(cps[i]);
            }
        }
        return result.toString();
    }

    public static String upperCase(String string) {
        if (string == null) {
            return null;
        }

        return string.toUpperCase();
    }

    public static String lowerCase(String string) {
        if (string == null) {
            return null;
        }

        return string.toLowerCase();
    }

    public static String substringBefore(String string, String match) {
        if (string == null || match == null) {
            return null;
        }

        int i = string.indexOf(match);
        return i == -1 ? "" : string.substring(0, i);
    }

    public static String substringAfter(String string, String match) {
        if (string == null || match == null) {
            return null;
        }

        int i = string.indexOf(match);
        return i == -1 ? "" : string.substring(i + match.length());
    }

    public static String replace(String input, String pattern, String replacement, String flags) throws Exception {
        if (input == null || pattern == null || replacement == null) {
            return null;
        }
        if (flags == null) {
            flags = "";
        }

        String expression = String.format("replace(/root, '%s', '%s', '%s')", pattern, replacement, flags);
        return evaluateXPath(input, expression);
    }

    public static Boolean matches(String input, String pattern, String flags) throws Exception {
        if (input == null || pattern == null) {
            return false;
        }
        if (flags == null) {
            flags = "";
        }

        String expression = String.format("/root[matches(., '%s', '%s')]", pattern, flags);
        String value = evaluateXPath(input, expression);
        return input.equals(value);
    }

    public static List split(String string, String delimiter) {
        if (string == null || delimiter == null) {
            return null;
        }
        if (string.isEmpty() || delimiter.isEmpty()) {
            return null;
        }

        Pattern p = Pattern.compile(delimiter);
        Matcher m = p.matcher(string);
        List<String> result = new ArrayList<>();
        int start = 0;
        while (m.find(start)) {
            int delimiterStart = m.start();
            int delimiterEnd = m.end();
            String token = string.substring(start, delimiterStart);
            result.add(token);
            start = delimiterEnd;
        }
        if (start <= string.length()) {
            String token = string.substring(start);
            result.add(token);
        }
        return result;
    }

    private static String evaluateXPath(String input, String expression) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        // Read document
        String xml = "<root>" + input + "</root>";
        DocumentBuilderFactory builderFactory = XMLUtil.makeDocumentBuilderFactory();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
        Document document = builder.parse(inputStream);

        // Evaluate xpath
        XPathFactory xPathFactory = new XPathFactoryImpl();
        XPath xPath = xPathFactory.newXPath();
        return xPath.evaluate(expression, document.getDocumentElement());
    }

    public static String stripQuotes(String value) {
        if (StringUtils.isEmpty(value) || !value.startsWith("\"")) {
            return value;
        }
        return value.substring(1, value.length() - 1);
    }
}
