package com.gs.dmn.feel.analysis.semantics;

import org.junit.Test;

public class NamedBuiltinFunctionsResolutionTest extends AbstractStandardBuiltinFunctionsResolutionTest {
    @Test
    public void testConversionFunctions() {
        testFunctionInvocation("date(from: null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), date)", false);
        testFunctionInvocation("date(year: null, month:null, day:null)", "BuiltinFunctionType(FormalParameter(year, number, false, false), FormalParameter(month, number, false, false), FormalParameter(day, number, false, false), date)", false);
        testFunctionInvocation("date(from:" + dateString + ")", "BuiltinFunctionType(FormalParameter(from, string, false, false), date)", false);
        testFunctionInvocation("date(from:" + date + ")", "BuiltinFunctionType(FormalParameter(from, date, false, false), date)", false);
        testFunctionInvocation("date(from:" + dateTime + ")", "BuiltinFunctionType(FormalParameter(from, date and time, false, false), date)", false);
        testFunctionInvocation("date(year: 2000, month: 1, day: 1)", "BuiltinFunctionType(FormalParameter(year, number, false, false), FormalParameter(month, number, false, false), FormalParameter(day, number, false, false), date)", false);

        testFunctionInvocation("date and time(from: null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), date and time)", false);
        testFunctionInvocation("date and time(date: null, time: null)", "BuiltinFunctionType(FormalParameter(date, date, false, false), FormalParameter(time, time, false, false), date and time)", false);
        testFunctionInvocation("date and time(date:" + date + ", time: " + time + ")", "BuiltinFunctionType(FormalParameter(date, date, false, false), FormalParameter(time, time, false, false), date and time)", false);
        testFunctionInvocation("date and time(date: " + dateTime + ", time: " + time +")", "BuiltinFunctionType(FormalParameter(date, date and time, false, false), FormalParameter(time, time, false, false), date and time)", false);
        testFunctionInvocation("date and time(from: " + dateTimeString + ")", "BuiltinFunctionType(FormalParameter(from, string, false, false), date and time)", false);

        testFunctionInvocation("time(from: null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), time)", false);
        testFunctionInvocation("time(hour: null, minute: null, second: null)", "BuiltinFunctionType(FormalParameter(hour, number, false, false), FormalParameter(minute, number, false, false), FormalParameter(second, number, false, false), FormalParameter(offset, days and time duration, true, false), time)", false);
        testFunctionInvocation("time(hour: null, minute: null, second: null, offset: null)", "BuiltinFunctionType(FormalParameter(hour, number, false, false), FormalParameter(minute, number, false, false), FormalParameter(second, number, false, false), FormalParameter(offset, days and time duration, true, false), time)", false);
        testFunctionInvocation("time(from: " + timeString + ")", "BuiltinFunctionType(FormalParameter(from, string, false, false), time)", false);
        testFunctionInvocation("time(from: " + date + ")", "BuiltinFunctionType(FormalParameter(from, date, false, false), time)", false);
        testFunctionInvocation("time(from: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(from, date and time, false, false), time)", false);
        testFunctionInvocation("time(hour: 12, minute: 0, second: 0)", "BuiltinFunctionType(FormalParameter(hour, number, false, false), FormalParameter(minute, number, false, false), FormalParameter(second, number, false, false), FormalParameter(offset, days and time duration, true, false), time)", false);
        testFunctionInvocation("time(hour: 12, minute: 0, second: 0, offset: " + daysAndTimeDuration + ")", "BuiltinFunctionType(FormalParameter(hour, number, false, false), FormalParameter(minute, number, false, false), FormalParameter(second, number, false, false), FormalParameter(offset, days and time duration, true, false), time)", false);

        testFunctionInvocation("number(from: null, 'grouping separator': null, 'decimal separator': null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), FormalParameter(grouping separator, string, false, false), FormalParameter(decimal separator, string, false, false), number)", false);
        testFunctionInvocation("number(from: " + numberString + ", 'grouping separator': \",\", 'decimal separator': \".\")", "BuiltinFunctionType(FormalParameter(from, string, false, false), FormalParameter(grouping separator, string, false, false), FormalParameter(decimal separator, string, false, false), number)", false);

        testFunctionInvocation("string(from: null)", "BuiltinFunctionType(FormalParameter(from, Any, false, false), string)", false);
        testFunctionInvocation("string(from: 123)", "BuiltinFunctionType(FormalParameter(from, Any, false, false), string)", false);

        testFunctionInvocation("duration(from: null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), duration)", false);
        testFunctionInvocation("duration(from: " + yearsAndMonthsDurationString + ")", "BuiltinFunctionType(FormalParameter(from, string, false, false), duration)", false);
        testFunctionInvocation("duration(from: " + daysAndTimeDurationString + ")", "BuiltinFunctionType(FormalParameter(from, string, false, false), duration)", false);

        testFunctionInvocation("years and months duration(from: null, to: null)", "BuiltinFunctionType(FormalParameter(from, date, false, false), FormalParameter(to, date, false, false), years and months duration)", false);
        testFunctionInvocation("years and months duration(from:" + date + ", to: " +  date + ")", "BuiltinFunctionType(FormalParameter(from, date, false, false), FormalParameter(to, date, false, false), years and months duration)", false);
        testFunctionInvocation("years and months duration(from:" + dateTime + ", to: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(from, date and time, false, false), FormalParameter(to, date and time, false, false), years and months duration)", false);
        testFunctionInvocation("years and months duration(from:" + date + ", to: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(from, date, false, false), FormalParameter(to, date and time, false, false), years and months duration)", false);
        testFunctionInvocation("years and months duration(from:" + dateTime + ", to: " + date + ")", "BuiltinFunctionType(FormalParameter(from, date and time, false, false), FormalParameter(to, date, false, false), years and months duration)", false);
    }

    @Test
    public void testNumberFunctions() {
        testFunctionInvocation("decimal(n: null, scale: null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);
        testFunctionInvocation("decimal(n: " + number + ", scale: " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);

        testFunctionInvocation("round(n: null, scale: null, mode: null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), FormalParameter(mode, string, false, false), number)", false);
        testFunctionInvocation("round(n: " + number + ", scale: " + number + ", mode: " + stringString + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), FormalParameter(mode, string, false, false), number)", false);

        testFunctionInvocation("round up(n: null, scale: null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);
        testFunctionInvocation("round up(n: " + number + ", scale: " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);

        testFunctionInvocation("round down(n: null, scale: null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);
        testFunctionInvocation("round down(n: " + number + ", scale: " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);

        testFunctionInvocation("round half up(n: null, scale: null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);
        testFunctionInvocation("round half up(n: " + number + ", scale: " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);

        testFunctionInvocation("round half down(n: null, scale: null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);
        testFunctionInvocation("round half down(n: " + number + ", scale: " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);

        testFunctionInvocation("floor(n: null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), number)", false);
        testFunctionInvocation("floor(n: null, scale: null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);
        testFunctionInvocation("floor(n: " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), number)", false);
        testFunctionInvocation("floor(n: " + number + ", scale: " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);

        testFunctionInvocation("ceiling(n: null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), number)", false);
        testFunctionInvocation("ceiling(n: null, scale: null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);
        testFunctionInvocation("ceiling(n: " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), number)", false);
        testFunctionInvocation("ceiling(n:" + number + ", scale: " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);

        testFunctionInvocation("abs(n: null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), number)", false);
        testFunctionInvocation("abs(n: " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), number)", false);
        testFunctionInvocation("abs(n: " + yearsAndMonthsDuration + ")", "BuiltinFunctionType(FormalParameter(n, years and months duration, false, false), years and months duration)", false);
        testFunctionInvocation("abs(n: " + daysAndTimeDuration + ")", "BuiltinFunctionType(FormalParameter(n, days and time duration, false, false), days and time duration)", false);

        testFunctionInvocation("modulo(dividend: null, divisor: null)", "BuiltinFunctionType(FormalParameter(dividend, number, false, false), FormalParameter(divisor, number, false, false), number)", false);
        testFunctionInvocation("modulo(dividend: " + number + ", divisor: " + number + ")", "BuiltinFunctionType(FormalParameter(dividend, number, false, false), FormalParameter(divisor, number, false, false), number)", false);

        testFunctionInvocation("sqrt(number: null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("sqrt(number: " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);

        testFunctionInvocation("log(number: null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("log(number: " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);

        testFunctionInvocation("exp(number: null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("exp(number: " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);

        testFunctionInvocation("odd(number: null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), boolean)", false);
        testFunctionInvocation("odd(number: " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), boolean)", false);

        testFunctionInvocation("even(number: null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), boolean)", false);
        testFunctionInvocation("even(number: " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), boolean)", false);
    }

    @Test
    public void testBooleanFunctions() {
        // Not supported yet as not is also a unary operator
//        testFunctionInvocation("not(negand: null)", "LogicNegation", false);
//        testFunctionInvocation("not(negand: true)", "LogicNegation", false);
    }

    @Test
    public void testStringFunctions() {
        testFunctionInvocation("substring(string: null, 'start position': null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), string)", false);
        testFunctionInvocation("substring(string: null, 'start position': null, length: null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), string)", false);
        testFunctionInvocation("substring(string: " + stringString + ", 'start position': " + number + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), string)", false);
        testFunctionInvocation("substring(string: " + stringString + ", 'start position': " + number + ", length: " + number+ ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), string)", false);

        testFunctionInvocation("string length(string: null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), number)", false);
        testFunctionInvocation("string length(string: " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), number)", false);

        testFunctionInvocation("upper case(string: null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), string)", false);
        testFunctionInvocation("upper case(string: " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), string)", false);

        testFunctionInvocation("lower case(string: null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), string)", false);
        testFunctionInvocation("lower case(string: " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), string)", false);

        testFunctionInvocation("substring before(string: null, match: null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), string)", false);
        testFunctionInvocation("substring before(string: " + stringString + ", match: " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), string)", false);

        testFunctionInvocation("substring after(string: null, match: null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), string)", false);
        testFunctionInvocation("substring after(string: " + stringString + ", match: " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), string)", false);

        testFunctionInvocation("replace(input: null, pattern: null, replacement: null)", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(replacement, string, false, false), FormalParameter(flags, string, true, false), string)", false);
        testFunctionInvocation("replace(input: null, pattern: null, replacement: null, flags: null)", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(replacement, string, false, false), FormalParameter(flags, string, true, false), string)", false);
        testFunctionInvocation("replace(input: " + stringString + ", pattern: " + stringString + ", replacement: " + stringString + ")", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(replacement, string, false, false), FormalParameter(flags, string, true, false), string)", false);
        testFunctionInvocation("replace(input: " + stringString + ", pattern: " + stringString + ", replacement: " + stringString + ", flags: " + stringString + ")", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(replacement, string, false, false), FormalParameter(flags, string, true, false), string)", false);

        testFunctionInvocation("contains(string: null, match: null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), boolean)", false);
        testFunctionInvocation("contains(string: " + stringString + ", match: " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), boolean)", false);

        testFunctionInvocation("starts with(string: null, match: null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), boolean)", false);
        testFunctionInvocation("starts with(string: " + stringString + ", match: " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), boolean)", false);

        testFunctionInvocation("ends with(string: null, match: null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), boolean)", false);
        testFunctionInvocation("ends with(string: " + stringString + ", match: " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), boolean)", false);

        testFunctionInvocation("matches(input: null, pattern: null)", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(flags, string, true, false), boolean)", false);
        testFunctionInvocation("matches(input: null, pattern: null, flags: null)", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(flags, string, true, false), boolean)", false);
        testFunctionInvocation("matches(input: " + stringString + ", pattern: " + stringString + ")", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(flags, string, true, false), boolean)", false);
        testFunctionInvocation("matches(input: " + stringString + ", pattern: " + stringString + ", flags: " + stringString + ")", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(flags, string, true, false), boolean)", false);

        testFunctionInvocation("split(string: null, delimiter: null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(delimiter, string, false, false), ListType(string))", false);
        testFunctionInvocation("split(string: " + stringString + ", delimiter: " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(delimiter, string, false, false), ListType(string))", false);
    }

    @Test
    public void testListFunctions() {
        // Vararg not supported yet
        testFunctionInvocation("list contains(list: null, element: null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), FormalParameter(element, Any, false, false), boolean)", false);
        testFunctionInvocation("list contains(list: " + numberList + ", element: " + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), FormalParameter(element, Any, false, false), boolean)", false);

        testFunctionInvocation("count(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("count(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);

        testFunctionInvocation("min()", "", true);
        testFunctionInvocation("min(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(comparable), false, false), number)", false);
//        testFunctionInvocation("min(c1: null, cs: null)", "BuiltinFunctionType(FormalParameter(c1, comparable, false, false), FormalParameter(cs, comparable, false, true), number)", false);
        testFunctionInvocation("min(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(comparable), false, false), number)", false);
//        testFunctionInvocation("min(c1: " + number + ", cs: " + numberList + ")", "BuiltinFunctionType(FormalParameter(c1, comparable, false, false), FormalParameter(cs, comparable, false, true), number)", false);

        testFunctionInvocation("max()", "", true);
        testFunctionInvocation("max(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(comparable), false, false), number)", false);
//        testFunctionInvocation("max(c1: null, cs: null)", "BuiltinFunctionType(FormalParameter(c1, comparable, false, false), FormalParameter(cs, comparable, false, true), number)", false);
        testFunctionInvocation("max(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(comparable), false, false), number)", false);
//        testFunctionInvocation("max(c1: " + number + ", cs: " + numberList + ")", "BuiltinFunctionType(FormalParameter(c1, comparable, false, false), FormalParameter(cs, comparable, false, true), number)", false);

        testFunctionInvocation("sum()", "", true);
        testFunctionInvocation("sum(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
//        testFunctionInvocation("sum(null, null)", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);
        testFunctionInvocation("sum(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
//        testFunctionInvocation("sum(n1: " + number + ", ns: " + numberList + ")", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);

        testFunctionInvocation("mean()", "", true);
        testFunctionInvocation("mean(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
//        testFunctionInvocation("mean(n1: null, ns: null)", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);
        testFunctionInvocation("mean(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
//        testFunctionInvocation("mean(n1: " + number + "ns: " + numberList + ")", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);

        testFunctionInvocation("and()", "", true);
        testFunctionInvocation("and(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
//        testFunctionInvocation("and(b1: null, bs: null)", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);
        testFunctionInvocation("and(list: " + booleanList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
//        testFunctionInvocation("and(b1: true, bs: " + booleanList + ")", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);

        testFunctionInvocation("all()", "", true);
        testFunctionInvocation("all(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
//        testFunctionInvocation("all(b1: null, bs: null)", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);
        testFunctionInvocation("all(list: " + booleanList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
//        testFunctionInvocation("all(b1: true, bs:" + booleanList + ")", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);

        testFunctionInvocation("or()", "", true);
        testFunctionInvocation("or(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
//        testFunctionInvocation("or(b1: null, bs: null)", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);
        testFunctionInvocation("or(list: " + booleanList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
//        testFunctionInvocation("or(b1: true, bs: " + booleanList + ")", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);

        testFunctionInvocation("any()", "", true);
        testFunctionInvocation("any(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
//        testFunctionInvocation("any(b1: null, bs: null)", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);
        testFunctionInvocation("any(list: " + booleanList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
//        testFunctionInvocation("any(b1: true, bs: " + booleanList + ")", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);

        testFunctionInvocation("sublist(list: null, 'start position': null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), Null)", false);
        testFunctionInvocation("sublist(list: null, 'start position': null, length: null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), Null)", false);
        testFunctionInvocation("sublist(list: " + numberList + ", 'start position': "  + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), ListType(number))", false);
        testFunctionInvocation("sublist(list: " + numberList + ", 'start position': "  + number + ", length: " + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), ListType(number))", false);

//        testFunctionInvocation("append(list: null, item: [null])", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(item, Null, false, true), Null)", false);
//        testFunctionInvocation("append(list: null, item: [null, null]", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(item, Null, false, true), Null)", false);
//        testFunctionInvocation("append(list: " + numberList + ", item:"  + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(item, number, false, true), ListType(number))", false);

        testFunctionInvocation("concatenate()", "", true);
//        testFunctionInvocation("concatenate(list1: [null])", "BuiltinFunctionType(FormalParameter(list1, Null, false, true), Null)", false);
//        testFunctionInvocation("concatenate(list1: [null, null])", "BuiltinFunctionType(FormalParameter(list1, Null, false, true), Null)", false);
//        testFunctionInvocation("concatenate(list1: [" + numberList + ", " + numberList + "])", "BuiltinFunctionType(FormalParameter(list1, ListType(number), false, true), ListType(number))", false);

        testFunctionInvocation("insert before(list: null, position: null, newItem: null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(position, number, false, false), FormalParameter(newItem, Null, false, false), Null)", false);
        testFunctionInvocation("insert before(list: " + numberList + ", position: "  + number + ", newItem: "  + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(position, number, false, false), FormalParameter(newItem, number, false, false), ListType(number))", false);

        testFunctionInvocation("remove(list: null, position: null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(position, number, false, false), Null)", false);
        testFunctionInvocation("remove(list: " + numberList + ", position: "  + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(position, number, false, false), ListType(number))", false);

        testFunctionInvocation("reverse(list: null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), Null)", false);
        testFunctionInvocation("reverse(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), ListType(number))", false);

        testFunctionInvocation("index of(list: null, match: null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(match, Null, false, false), ListType(number))", false);
        testFunctionInvocation("index of(list: " + numberList + ", match: "  + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(match, number, false, false), ListType(number))", false);

        testFunctionInvocation("distinct values(list: null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), Null)", false);
        testFunctionInvocation("distinct values(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), ListType(number))", false);

//        testFunctionInvocation("union(list: null)", "BuiltinFunctionType(FormalParameter(list, Null, false, true))", false);
//        testFunctionInvocation("union(list: " + numberList + ", list2: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list1, ListType(number), false, false), FormalParameter(list2, ListType(number), false, false), ListType(number))", false);

        testFunctionInvocation("flatten(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), ListType(Null))", false);
        testFunctionInvocation("flatten(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), ListType(number))", false);

        testFunctionInvocation("product()", "", true);
        testFunctionInvocation("product(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
//        testFunctionInvocation("product(n1: null, ns: null)", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);
        testFunctionInvocation("product(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
//        testFunctionInvocation("product(n1: " + number + ", ns: " + numberList + ")", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);

        testFunctionInvocation("median()", "", true);
        testFunctionInvocation("median(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
//        testFunctionInvocation("median(n1: null, ns: null)", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);
        testFunctionInvocation("median(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
//        testFunctionInvocation("median(n1: " + number + ", ns: " + numberList + ")", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);

        testFunctionInvocation("stddev()", "", true);
        testFunctionInvocation("stddev(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
//        testFunctionInvocation("stddev(n1: null, ns: null)", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);
        testFunctionInvocation("stddev(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
//        testFunctionInvocation("stddev(n1: " + number + ", ns: " + numberList + ")", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);

        testFunctionInvocation("mode()", "", true);
        testFunctionInvocation("mode(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
//        testFunctionInvocation("mode(n1: null, ns: null)", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);
        testFunctionInvocation("mode(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
//        testFunctionInvocation("mode(n1: " + number + ", ns: " + numberList + ")", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);

        testFunctionInvocation("sort()", "", true);
        testFunctionInvocation("sort(list: null)", "", true);
        testFunctionInvocation("sort(list: null, function: null)", "", true);
        testFunctionInvocation("sort(list: " + numberList + ", function: function(a, b) if a < b then true else false)", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(function, FEELFunctionType(FormalParameter(a, number, false, false), FormalParameter(b, number, false, false), boolean, false), false, false), ListType(number))", false);
    }

    @Test
    public void testContextFunctions() {
        testFunctionInvocation("get entries(m: null)", "BuiltinFunctionType(FormalParameter(m, ContextType(), false, false), ListType(ContextType()))", false);
        testFunctionInvocation("get entries(m: " + context + ")", "BuiltinFunctionType(FormalParameter(m, ContextType(), false, false), ListType(ContextType()))", false);

        testFunctionInvocation("get value(m: null, key: null)", "BuiltinFunctionType(FormalParameter(m, ContextType(), false, false), FormalParameter(key, string, false, false), Any)", false);
        testFunctionInvocation("get value(m: " + context + ", key: " + contextValue + ")", "BuiltinFunctionType(FormalParameter(m, ContextType(), false, false), FormalParameter(key, string, false, false), Any)", false);
    }

    @Test
    public void testDateTimeFunctions() {
        testFunctionInvocation("is(value1: null, value2: null)", "BuiltinFunctionType(FormalParameter(value1, Any, false, false), FormalParameter(value2, Any, false, false), boolean)", false);
        testFunctionInvocation("is(value1: " + number + ", value2: " + number + ")", "BuiltinFunctionType(FormalParameter(value1, Any, false, false), FormalParameter(value2, Any, false, false), boolean)", false);
    }

    @Test
    public void testTemporalFunctions() {
        testFunctionInvocation("day of year(date: null)", "BuiltinFunctionType(FormalParameter(date, date, false, false), number)", false);
        testFunctionInvocation("day of year(date: " + date + ")", "BuiltinFunctionType(FormalParameter(date, date, false, false), number)", false);
        testFunctionInvocation("day of year(date: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(date, date and time, false, false), number)", false);

        testFunctionInvocation("day of week(date: null)", "BuiltinFunctionType(FormalParameter(date, date, false, false), string)", false);
        testFunctionInvocation("day of week(date: " + date + ")", "BuiltinFunctionType(FormalParameter(date, date, false, false), string)", false);
        testFunctionInvocation("day of week(date: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(date, date and time, false, false), string)", false);

        testFunctionInvocation("month of year(date: null)", "BuiltinFunctionType(FormalParameter(date, date, false, false), string)", false);
        testFunctionInvocation("month of year(date: " + date + ")", "BuiltinFunctionType(FormalParameter(date, date, false, false), string)", false);
        testFunctionInvocation("month of year(date: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(date, date and time, false, false), string)", false);

        testFunctionInvocation("week of year(date: null)", "BuiltinFunctionType(FormalParameter(date, date, false, false), number)", false);
        testFunctionInvocation("week of year(date: " + date + ")", "BuiltinFunctionType(FormalParameter(date, date, false, false), number)", false);
        testFunctionInvocation("week of year(date: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(date, date and time, false, false), number)", false);
    }

    @Test
    public void testRangeFunctions() {
        testFunctionInvocation("before(null, null)", "BuiltinFunctionType(FormalParameter(point1, comparable, false, false), FormalParameter(point2, comparable, false, false), boolean)", false);
        testFunctionInvocation("before(point1: " + number + ", point2: " + number + ")", "BuiltinFunctionType(FormalParameter(point1, comparable, false, false), FormalParameter(point2, comparable, false, false), boolean)", false);
        testFunctionInvocation("before(point: " + number + ", range: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
//        testFunctionInvocation("before(range: " + numberRange + ", point: " + number + ")", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("before(range1: " + numberRange + ", range2: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("after(null, null)", "BuiltinFunctionType(FormalParameter(point1, comparable, false, false), FormalParameter(point2, comparable, false, false), boolean)", false);
        testFunctionInvocation("after(point1: " + number + ", point2: " + number + ")", "BuiltinFunctionType(FormalParameter(point1, comparable, false, false), FormalParameter(point2, comparable, false, false), boolean)", false);
        testFunctionInvocation("after(point: " + number + ", range: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
//        testFunctionInvocation("after(range: " + numberRange + ", point: " + number + ")", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("after(range1: " + numberRange + ", range2: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("meets(range1: null, range2: null)", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("meets(range1: " + numberRange + ", range2: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("met by(range1: null, range2: null)", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("met by(range1: " + numberRange + ", range2: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("overlaps(range1: null, range2: null)", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("overlaps(range1: " + numberRange + ", range2: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("overlaps before(range1: null, range2: null)", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("overlaps before(range1: " + numberRange + ", range2: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("overlaps after(range1: null, range2: null)", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("overlaps after(range1: " + numberRange + ", range2: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("finishes(point: null, range: null)", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("finishes(point: " + number + ", range: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("finishes(range1: " + numberRange + ", range2: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("finished by(range: null, point: null)", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("finished by(range: " + numberRange + ", point: " + number + ")", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("finished by(range1: " + numberRange + ", range2: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("includes(null, null)", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("includes(range: " + numberRange + ", point: " + number + ")", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("includes(range1: " + numberRange + ", range2: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("during(point: null, range: null)", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("during(point: " + number + ", range: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("during(range1: " + numberRange + ", range2: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("starts(point: null, range: null)", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("starts(point: " + number +", range: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("starts(range1: " + numberRange + ", range2: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("started by(range: null, point: null)", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("started by(range: " + numberRange + ", point: " + number + ")", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("started by(range1: " + numberRange + ", range2: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("coincides(point1: null, point2: null)", "BuiltinFunctionType(FormalParameter(point1, comparable, false, false), FormalParameter(point2, comparable, false, false), boolean)", false);
        testFunctionInvocation("coincides(point1: " + number + ", point2: " + number+ ")", "BuiltinFunctionType(FormalParameter(point1, comparable, false, false), FormalParameter(point2, comparable, false, false), boolean)", false);
        testFunctionInvocation("coincides(range1: " + numberRange + ", range2: " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);
    }
}