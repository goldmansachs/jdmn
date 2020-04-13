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
package com.gs.dmn.generated.null_safe_tests;

import com.gs.dmn.generated.AbstractHandwrittenDecisionTest;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HandwrittenAllTogetherTest extends AbstractHandwrittenDecisionTest {
    private final AllTogether decision = new AllTogether();

    @Test
    public void testCase1() {
        AnnotationSet annotationSet = new AnnotationSet();
        String dateTime = "2015-01-01T12:00:00.000+00:00";
        String time = "13:00:00";
        String booleanList = toJson(Arrays.asList(true, false, true));
        String string = "00.00";
        String booleanB = "false";
        String numberA = "12";
        String numberList = toJson(Arrays.asList(decision.number("10"), decision.number("20"), decision.number("30"), decision.number("40")));
        String date = "2015-01-01";
        String numberB = "34";
        String stringList = toJson(Arrays.asList("Foo", "Bar"));
        String booleanA = "true";

        assertEquals("NotNull", decision.apply(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, annotationSet));
    }

    @Test
    public void testCase2() {
        AnnotationSet annotationSet = new AnnotationSet();
        String dateTime = "2016-11-16T12:10:00.000+00:00";
        String time = "12:00:00";
        String booleanList = toJson(Arrays.asList());
        String string = "0.0";
        String booleanB = "true";
        String numberA = "12";
        String numberList = toJson(Arrays.asList(null, null, null));
        String date = "2016-11-16";
        String numberB = "43";
        String stringList = toJson(Arrays.asList("Some", "Thing"));
        String booleanA = "true";

        assertNull(decision.apply(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, annotationSet));
    }

    @Test
    public void testCase3() {
        AnnotationSet annotationSet = new AnnotationSet();
        String dateTime = "2105-11-16T00:00:00.000+00:00";
        String time = "11:11:11";
        String booleanList = toJson(Arrays.asList(true, true, false, true, false));
        String string = "X";
        String booleanB = "true";
        String numberA = "23";
        String numberList = toJson(Arrays.asList(decision.number("1"), decision.number("2"), decision.number("1")));
        String date = "2016-11-09";
        String numberB = "1";
        String stringList = toJson(Arrays.asList("1", "2", "3"));
        String booleanA = "false";

        assertNull(decision.apply(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, annotationSet));
    }

    @Test
    public void testCase4() {
        AnnotationSet annotationSet = new AnnotationSet();
        String dateTime = "2016-11-01T01:01:01.000+00:00";
        String time = "12:12:12";
        String booleanList = toJson(Arrays.asList(true));
        String string = "0.0##";
        String booleanB = "true";
        String numberA = "11";
        String numberList = toJson(Arrays.asList(decision.number("1"), decision.number("2"), decision.number("1")));
        String date = "2016-11-01";
        String numberB = "22";
        String stringList = toJson(Arrays.asList("a", "d", "s"));
        String booleanA = "false";

        assertEquals("NotNull", decision.apply(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, annotationSet));
    }

    @Test
    public void testCase5() {
        AnnotationSet annotationSet = new AnnotationSet();
        String dateTime = "2017-01-01T10:00:00.000+00:00";
        String time = "12:12:12";
        String booleanList = toJson(Arrays.asList(true, false, true, false));
        String string = "000";
        String booleanB = "false";
        String numberA = "90";
        String numberList = toJson(Arrays.asList(decision.number("9"), decision.number("8"), decision.number("7")));
        String date = "2016-11-18";
        String numberB = "90";
        String stringList = toJson(Arrays.asList("A", "B", "C"));
        String booleanA = "false";

        assertEquals("NotNull", decision.apply(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, annotationSet));
    }

    @Test
    public void testCase6() {
        AnnotationSet annotationSet = new AnnotationSet();
        String dateTime = "2016-11-03T04:00:00.000+00:00";
        String time = "11:11:11";
        String booleanList = toJson(Arrays.asList(false));
        String string = "#";
        String booleanB = "true";
        String numberA = "12";
        String numberList = toJson(Arrays.asList(decision.number("4"), decision.number("24"), decision.number("4")));
        String date = "2016-11-03";
        String numberB = "42";
        String stringList = toJson(Arrays.asList("123", "234", "345"));
        String booleanA = "true";

        assertEquals("NotNull", decision.apply(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, annotationSet));
    }

    @Override
    protected void applyDecision() {
        AnnotationSet annotationSet = new AnnotationSet();
        String dateTime = "2016-11-03T04:00:00.000+00:00";
        String time = "11:11:11";
        String booleanList = toJson(Arrays.asList(false));
        String string = "#";
        String booleanB = "true";
        String numberA = "12";
        String numberList = toJson(Arrays.asList(decision.number("4"), decision.number("24"), decision.number("4")));
        String date = "2016-11-03";
        String numberB = "42";
        String stringList = toJson(Arrays.asList("123", "234", "345"));
        String booleanA = "true";

        decision.apply(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, annotationSet);
    }
}
