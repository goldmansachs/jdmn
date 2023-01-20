package com.gs.dmn.feel.analysis.semantics;

import org.junit.Test;

public class PositionalBuiltinFunctionsResolutionTest extends AbstractStandardBuiltinFunctionsResolutionTest {
    @Test
    public void testConversionFunctions() {
        testFunctionInvocation("date(null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), date)", false);
        testFunctionInvocation("date(null, null, null)", "BuiltinFunctionType(FormalParameter(year, number, false, false), FormalParameter(month, number, false, false), FormalParameter(day, number, false, false), date)", false);
        testFunctionInvocation("date(" + dateString + ")", "DateTimeLiteral", false);
        testFunctionInvocation("date(" + date + ")", "BuiltinFunctionType(FormalParameter(from, date, false, false), date)", false);
        testFunctionInvocation("date(" + dateTime + ")", "BuiltinFunctionType(FormalParameter(from, date and time, false, false), date)", false);
        testFunctionInvocation("date(2000, 1, 1)", "BuiltinFunctionType(FormalParameter(year, number, false, false), FormalParameter(month, number, false, false), FormalParameter(day, number, false, false), date)", false);

        testFunctionInvocation("date and time(null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), date and time)", false);
        testFunctionInvocation("date and time(null, null)", "BuiltinFunctionType(FormalParameter(date, date, false, false), FormalParameter(time, time, false, false), date and time)", false);
        testFunctionInvocation("date and time(" + date + ", " + time + ")", "BuiltinFunctionType(FormalParameter(date, date, false, false), FormalParameter(time, time, false, false), date and time)", false);
        testFunctionInvocation("date and time(" + dateTime + ", " + time +")", "BuiltinFunctionType(FormalParameter(date, date and time, false, false), FormalParameter(time, time, false, false), date and time)", false);
        testFunctionInvocation("date and time(" + dateTimeString + ")", "DateTimeLiteral", false);

        testFunctionInvocation("time(null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), time)", false);
        testFunctionInvocation("time(null, null, null)", "BuiltinFunctionType(FormalParameter(hour, number, false, false), FormalParameter(minute, number, false, false), FormalParameter(second, number, false, false), FormalParameter(offset, days and time duration, true, false), time)", false);
        testFunctionInvocation("time(null, null, null, null)", "BuiltinFunctionType(FormalParameter(hour, number, false, false), FormalParameter(minute, number, false, false), FormalParameter(second, number, false, false), FormalParameter(offset, days and time duration, true, false), time)", false);
        testFunctionInvocation("time(" + timeString + ")", "DateTimeLiteral", false);
        testFunctionInvocation("time(" + date + ")", "BuiltinFunctionType(FormalParameter(from, date, false, false), time)", false);
        testFunctionInvocation("time(" + dateTime + ")", "BuiltinFunctionType(FormalParameter(from, date and time, false, false), time)", false);
        testFunctionInvocation("time(12, 0, 0)", "BuiltinFunctionType(FormalParameter(hour, number, false, false), FormalParameter(minute, number, false, false), FormalParameter(second, number, false, false), FormalParameter(offset, days and time duration, true, false), time)", false);
        testFunctionInvocation("time(12, 0, 0, 0)", "BuiltinFunctionType(FormalParameter(hour, number, false, false), FormalParameter(minute, number, false, false), FormalParameter(second, number, false, false), FormalParameter(offset, days and time duration, true, false), time)", false);

        testFunctionInvocation("number(null, null, null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), FormalParameter(grouping separator, string, false, false), FormalParameter(decimal separator, string, false, false), number)", false);
        testFunctionInvocation("number(" + numberString + ", \",\", \".\")", "BuiltinFunctionType(FormalParameter(from, string, false, false), FormalParameter(grouping separator, string, false, false), FormalParameter(decimal separator, string, false, false), number)", false);

        testFunctionInvocation("string(null)", "BuiltinFunctionType(FormalParameter(from, Any, false, false), string)", false);
        testFunctionInvocation("string(123)", "BuiltinFunctionType(FormalParameter(from, Any, false, false), string)", false);

        testFunctionInvocation("duration(null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), duration)", false);
        testFunctionInvocation("duration(" + yearsAndMonthsDurationString + ")", "DateTimeLiteral", false);
        testFunctionInvocation("duration(" + daysAndTimeDuration + ")", "BuiltinFunctionType(FormalParameter(from, string, false, false), duration)", false);

        testFunctionInvocation("years and months duration(null, null)", "BuiltinFunctionType(FormalParameter(from, date, false, false), FormalParameter(to, date, false, false), years and months duration)", false);
        testFunctionInvocation("years and months duration(" + date + ", " +  date + ")", "BuiltinFunctionType(FormalParameter(from, date, false, false), FormalParameter(to, date, false, false), years and months duration)", false);
        testFunctionInvocation("years and months duration(" + dateTime + ", " + dateTime + ")", "BuiltinFunctionType(FormalParameter(from, date and time, false, false), FormalParameter(to, date and time, false, false), years and months duration)", false);
        testFunctionInvocation("years and months duration(" + date + ", " + dateTime + ")", "BuiltinFunctionType(FormalParameter(from, date, false, false), FormalParameter(to, date and time, false, false), years and months duration)", false);
        testFunctionInvocation("years and months duration(" + dateTime + ", " + date + ")", "BuiltinFunctionType(FormalParameter(from, date and time, false, false), FormalParameter(to, date, false, false), years and months duration)", false);
    }

    @Test
    public void testNumberFunctions() {
        testFunctionInvocation("decimal(null, null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);
        testFunctionInvocation("decimal(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);

        testFunctionInvocation("round(null, null, null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), FormalParameter(mode, string, false, false), number)", false);
        testFunctionInvocation("round(" + number + ", " + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), FormalParameter(mode, string, false, false), number)", false);

        testFunctionInvocation("round up(null, null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);
        testFunctionInvocation("round up(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);

        testFunctionInvocation("round down(null, null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);
        testFunctionInvocation("round down(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);

        testFunctionInvocation("round half up(null, null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);
        testFunctionInvocation("round half up(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);

        testFunctionInvocation("round half down(null, null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);
        testFunctionInvocation("round half down(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);

        testFunctionInvocation("floor(null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), number)", false);
        testFunctionInvocation("floor(null, null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);
        testFunctionInvocation("floor(" + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), number)", false);
        testFunctionInvocation("floor(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);

        testFunctionInvocation("ceiling(null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), number)", false);
        testFunctionInvocation("ceiling(null, null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);
        testFunctionInvocation("ceiling(" + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), number)", false);
        testFunctionInvocation("ceiling(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), FormalParameter(scale, number, false, false), number)", false);

        testFunctionInvocation("abs(null)", "BuiltinFunctionType(FormalParameter(n, number, false, false), number)", false);
        testFunctionInvocation("abs(" + number + ")", "BuiltinFunctionType(FormalParameter(n, number, false, false), number)", false);
        testFunctionInvocation("abs(" + yearsAndMonthsDuration + ")", "BuiltinFunctionType(FormalParameter(n, years and months duration, false, false), years and months duration)", false);
        testFunctionInvocation("abs(" + daysAndTimeDuration + ")", "BuiltinFunctionType(FormalParameter(n, days and time duration, false, false), days and time duration)", false);

        testFunctionInvocation("modulo(null, null)", "BuiltinFunctionType(FormalParameter(dividend, number, false, false), FormalParameter(divisor, number, false, false), number)", false);
        testFunctionInvocation("modulo(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(dividend, number, false, false), FormalParameter(divisor, number, false, false), number)", false);

        testFunctionInvocation("sqrt(null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("sqrt(" + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);

        testFunctionInvocation("log(null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("log(" + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);

        testFunctionInvocation("exp(null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("exp(" + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);

        testFunctionInvocation("odd(null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), boolean)", false);
        testFunctionInvocation("odd(" + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), boolean)", false);

        testFunctionInvocation("even(null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), boolean)", false);
        testFunctionInvocation("even(" + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), boolean)", false);
    }

    @Test
    public void testBooleanFunctions() {
        testFunctionInvocation("not(null)", "LogicNegation", false);
        testFunctionInvocation("not(true)", "LogicNegation", false);
    }

    @Test
    public void testStringFunctions() {
        testFunctionInvocation("substring(null, null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), string)", false);
        testFunctionInvocation("substring(null, null, null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), string)", false);
        testFunctionInvocation("substring(" + stringString + ", " + number + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), string)", false);
        testFunctionInvocation("substring(" + stringString + ", " + number + ", " + number+ ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), string)", false);

        testFunctionInvocation("string length(null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), number)", false);
        testFunctionInvocation("string length(" + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), number)", false);

        testFunctionInvocation("upper case(null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), string)", false);
        testFunctionInvocation("upper case(" + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), string)", false);

        testFunctionInvocation("lower case(null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), string)", false);
        testFunctionInvocation("lower case(" + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), string)", false);

        testFunctionInvocation("substring before(null, null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), string)", false);
        testFunctionInvocation("substring before(" + stringString + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), string)", false);

        testFunctionInvocation("substring after(null, null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), string)", false);
        testFunctionInvocation("substring after(" + stringString + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), string)", false);

        testFunctionInvocation("replace(null, null, null)", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(replacement, string, false, false), FormalParameter(flags, string, true, false), string)", false);
        testFunctionInvocation("replace(null, null, null, null)", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(replacement, string, false, false), FormalParameter(flags, string, true, false), string)", false);
        testFunctionInvocation("replace(" + stringString + "," + stringString + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(replacement, string, false, false), FormalParameter(flags, string, true, false), string)", false);
        testFunctionInvocation("replace(" + stringString + "," + stringString + ", " + stringString + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(replacement, string, false, false), FormalParameter(flags, string, true, false), string)", false);

        testFunctionInvocation("contains(null, null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), boolean)", false);
        testFunctionInvocation("contains(" + stringString + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), boolean)", false);

        testFunctionInvocation("starts with(null, null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), boolean)", false);
        testFunctionInvocation("starts with(" + stringString + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), boolean)", false);

        testFunctionInvocation("ends with(null, null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), boolean)", false);
        testFunctionInvocation("ends with(" + stringString + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(match, string, false, false), boolean)", false);

        testFunctionInvocation("matches(null, null)", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(flags, string, true, false), boolean)", false);
        testFunctionInvocation("matches(null, null, null)", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(flags, string, true, false), boolean)", false);
        testFunctionInvocation("matches(" + stringString + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(flags, string, true, false), boolean)", false);
        testFunctionInvocation("matches(" + stringString + ", " + stringString + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(input, string, false, false), FormalParameter(pattern, string, false, false), FormalParameter(flags, string, true, false), boolean)", false);

        testFunctionInvocation("split(null, null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(delimiter, string, false, false), ListType(string))", false);
        testFunctionInvocation("split(" + stringString + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(delimiter, string, false, false), ListType(string))", false);
    }

    @Test
    public void testListFunctions() {
        testFunctionInvocation("list contains(null, null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), FormalParameter(element, Any, false, false), boolean)", false);
        testFunctionInvocation("list contains(" + numberList + ", " + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), FormalParameter(element, Any, false, false), boolean)", false);

        testFunctionInvocation("count(null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("count(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);

        testFunctionInvocation("min()", "", true);
        testFunctionInvocation("min(null)", "BuiltinFunctionType(FormalParameter(list, ListType(comparable), false, false), number)", false);
        testFunctionInvocation("min(null, null)", "BuiltinFunctionType(FormalParameter(c1, comparable, false, false), FormalParameter(cs, comparable, false, true), number)", false);
        testFunctionInvocation("min(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(comparable), false, false), number)", false);
        testFunctionInvocation("min(" + numberSequence + ")", "BuiltinFunctionType(FormalParameter(c1, comparable, false, false), FormalParameter(cs, comparable, false, true), number)", false);

        testFunctionInvocation("max()", "", true);
        testFunctionInvocation("max(null)", "BuiltinFunctionType(FormalParameter(list, ListType(comparable), false, false), number)", false);
        testFunctionInvocation("max(null, null)", "BuiltinFunctionType(FormalParameter(c1, comparable, false, false), FormalParameter(cs, comparable, false, true), number)", false);
        testFunctionInvocation("max(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(comparable), false, false), number)", false);
        testFunctionInvocation("max(" + numberSequence + ")", "BuiltinFunctionType(FormalParameter(c1, comparable, false, false), FormalParameter(cs, comparable, false, true), number)", false);

        testFunctionInvocation("sum()", "", true);
        testFunctionInvocation("sum(null)", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("sum(null, null)", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);
        testFunctionInvocation("sum(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("sum(" + numberSequence + ")", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);

        testFunctionInvocation("mean()", "", true);
        testFunctionInvocation("mean(null)", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("mean(null, null)", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);
        testFunctionInvocation("mean(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("mean(" + numberSequence + ")", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);

        testFunctionInvocation("and()", "", true);
        testFunctionInvocation("and(null)", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
        testFunctionInvocation("and(null, null)", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);
        testFunctionInvocation("and(" + booleanList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
        testFunctionInvocation("and(" + booleanSequence + ")", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);

        testFunctionInvocation("all()", "", true);
        testFunctionInvocation("all(null)", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
        testFunctionInvocation("all(null, null)", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);
        testFunctionInvocation("all(" + booleanList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
        testFunctionInvocation("all(" + booleanSequence + ")", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);

        testFunctionInvocation("or()", "", true);
        testFunctionInvocation("or(null)", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
        testFunctionInvocation("or(null, null)", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);
        testFunctionInvocation("or(" + booleanList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
        testFunctionInvocation("or(" + booleanSequence + ")", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);

        testFunctionInvocation("any()", "", true);
        testFunctionInvocation("any(null)", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
        testFunctionInvocation("any(null, null)", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);
        testFunctionInvocation("any(" + booleanList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(boolean), false, false), boolean)", false);
        testFunctionInvocation("any(" + booleanSequence + ")", "BuiltinFunctionType(FormalParameter(b1, boolean, false, false), FormalParameter(bs, boolean, false, true), boolean)", false);

        testFunctionInvocation("sublist(null, null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), Null)", false);
        testFunctionInvocation("sublist(null, null, null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), Null)", false);
        testFunctionInvocation("sublist(" + numberList + ", "  + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), ListType(number))", false);
        testFunctionInvocation("sublist(" + numberList + ", "  + number + "," + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(start position, number, false, false), FormalParameter(length, number, true, false), ListType(number))", false);

        testFunctionInvocation("append(null, null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(item, Null, false, true), Null)", false);
        testFunctionInvocation("append(null, null, null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(item, Null, false, true), Null)", false);
        testFunctionInvocation("append(" + numberList + ", "  + number + ", "  + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(item, number, false, true), ListType(number))", false);

        testFunctionInvocation("concatenate()", "", true);
        testFunctionInvocation("concatenate(null)", "BuiltinFunctionType(FormalParameter(list, Null, false, true), Null)", false);
        testFunctionInvocation("concatenate(null, null)", "BuiltinFunctionType(FormalParameter(list, Null, false, true), Null)", false);
        testFunctionInvocation("concatenate(" + numberList + ", " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, true), ListType(number))", false);

        testFunctionInvocation("insert before(null, null, null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(position, number, false, false), FormalParameter(newItem, Null, false, false), Null)", false);
        testFunctionInvocation("insert before(" + numberList + ", "  + number + ", "  + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(position, number, false, false), FormalParameter(newItem, number, false, false), ListType(number))", false);

        testFunctionInvocation("remove(null, null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(position, number, false, false), Null)", false);
        testFunctionInvocation("remove(" + numberList + ", "  + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(position, number, false, false), ListType(number))", false);

        testFunctionInvocation("reverse(null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), Null)", false);
        testFunctionInvocation("reverse(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), ListType(number))", false);

        testFunctionInvocation("index of(null, null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(match, Null, false, false), ListType(number))", false);
        testFunctionInvocation("index of(" + numberList + ", "  + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(match, number, false, false), ListType(number))", false);

        testFunctionInvocation("distinct values(null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), Null)", false);
        testFunctionInvocation("distinct values(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), ListType(number))", false);

        testFunctionInvocation("union()", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, true), ListType(Any))", false);
        testFunctionInvocation("union(null)", "BuiltinFunctionType(FormalParameter(list, Null, false, true), Null)", false);
        testFunctionInvocation("union(null, null)", "BuiltinFunctionType(FormalParameter(list, Null, false, true), Null)", false);
        testFunctionInvocation("union(null, null, null)", "BuiltinFunctionType(FormalParameter(list, Null, false, true), Null)", false);
        testFunctionInvocation("union(" + numberList + ", " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, true), ListType(number))", false);

        testFunctionInvocation("flatten(null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), ListType(Null))", false);
        testFunctionInvocation("flatten(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), ListType(number))", false);

        testFunctionInvocation("product()", "", true);
        testFunctionInvocation("product(null)", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("product(null, null)", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);
        testFunctionInvocation("product(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("product(" + numberSequence + ")", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);

        testFunctionInvocation("median()", "", true);
        testFunctionInvocation("median(null)", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("median(null, null)", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);
        testFunctionInvocation("median(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("median(" + numberSequence + ")", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);

        testFunctionInvocation("stddev()", "", true);
        testFunctionInvocation("stddev(null)", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("stddev(null, null)", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);
        testFunctionInvocation("stddev(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("stddev(" + numberSequence + ")", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);

        testFunctionInvocation("mode()", "", true);
        testFunctionInvocation("mode(null)", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("mode(null, null)", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);
        testFunctionInvocation("mode(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("mode(" + numberSequence + ")", "BuiltinFunctionType(FormalParameter(n1, number, false, false), FormalParameter(ns, number, false, true), number)", false);

        testFunctionInvocation("sort()", "", true);
        testFunctionInvocation("sort(null)", "", true);
        testFunctionInvocation("sort(null, null)", "ListType(number)", true);
        testFunctionInvocation("sort(" + numberList + ", function(a, b) if a < b then true else false)", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(function, FEELFunctionType(FormalParameter(a, number, false, false), FormalParameter(b, number, false, false), boolean, false), false, false), ListType(number))", false);
    }

    @Test
    public void testContextFunctions() {
        testFunctionInvocation("get entries(null)", "BuiltinFunctionType(FormalParameter(m, ContextType(), false, false), ListType(ContextType()))", false);
        testFunctionInvocation("get entries(" + context + ")", "BuiltinFunctionType(FormalParameter(m, ContextType(), false, false), ListType(ContextType()))", false);

        testFunctionInvocation("get value(null, null)", "BuiltinFunctionType(FormalParameter(m, ContextType(), false, false), FormalParameter(key, string, false, false), Any)", false);
        testFunctionInvocation("get value(" + context + ", " + contextValue + ")", "BuiltinFunctionType(FormalParameter(m, ContextType(), false, false), FormalParameter(key, string, false, false), Any)", false);
    }

    @Test
    public void testDateTimeFunctions() {
        testFunctionInvocation("is(null, null)", "BuiltinFunctionType(FormalParameter(value1, Any, false, false), FormalParameter(value2, Any, false, false), boolean)", false);
        testFunctionInvocation("is(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(value1, Any, false, false), FormalParameter(value2, Any, false, false), boolean)", false);
    }

    @Test
    public void testTemporalFunctions() {
        testFunctionInvocation("day of year(null)", "BuiltinFunctionType(FormalParameter(date, date, false, false), number)", false);
        testFunctionInvocation("day of year(" + date + ")", "BuiltinFunctionType(FormalParameter(date, date, false, false), number)", false);
        testFunctionInvocation("day of year(" + dateTime + ")", "BuiltinFunctionType(FormalParameter(date, date and time, false, false), number)", false);

        testFunctionInvocation("day of week(null)", "BuiltinFunctionType(FormalParameter(date, date, false, false), string)", false);
        testFunctionInvocation("day of week(" + date + ")", "BuiltinFunctionType(FormalParameter(date, date, false, false), string)", false);
        testFunctionInvocation("day of week(" + dateTime + ")", "BuiltinFunctionType(FormalParameter(date, date and time, false, false), string)", false);

        testFunctionInvocation("month of year(null)", "BuiltinFunctionType(FormalParameter(date, date, false, false), string)", false);
        testFunctionInvocation("month of year(" + date + ")", "BuiltinFunctionType(FormalParameter(date, date, false, false), string)", false);
        testFunctionInvocation("month of year(" + dateTime + ")", "BuiltinFunctionType(FormalParameter(date, date and time, false, false), string)", false);

        testFunctionInvocation("week of year(null)", "BuiltinFunctionType(FormalParameter(date, date, false, false), number)", false);
        testFunctionInvocation("week of year(" + date + ")", "BuiltinFunctionType(FormalParameter(date, date, false, false), number)", false);
        testFunctionInvocation("week of year(" + dateTime + ")", "BuiltinFunctionType(FormalParameter(date, date and time, false, false), number)", false);
    }

    @Test
    public void testRangeFunctions() {
        testFunctionInvocation("before(null, null)", "BuiltinFunctionType(FormalParameter(point1, comparable, false, false), FormalParameter(point2, comparable, false, false), boolean)", false);
        testFunctionInvocation("before(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(point1, comparable, false, false), FormalParameter(point2, comparable, false, false), boolean)", false);
        testFunctionInvocation("before(" + number + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("before(" + numberRange + ", " + number + ")", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("before(" + numberRange + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("after(null, null)", "BuiltinFunctionType(FormalParameter(point1, comparable, false, false), FormalParameter(point2, comparable, false, false), boolean)", false);
        testFunctionInvocation("after(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(point1, comparable, false, false), FormalParameter(point2, comparable, false, false), boolean)", false);
        testFunctionInvocation("after(" + number + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("after(" + numberRange + ", " + number + ")", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("after(" + numberRange + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("meets(null, null)", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("meets(" + numberRange + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("met by(null, null)", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("met by(" + numberRange + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("overlaps(null, null)", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("overlaps(" + numberRange + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("overlaps before(null, null)", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("overlaps before(" + numberRange + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("overlaps after(null, null)", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("overlaps after(" + numberRange + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("finishes(null, null)", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("finishes(" + number + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("finishes(" + numberRange + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("finished by(null, null)", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("finished by(" + numberRange + ", " + number + ")", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("finished by(" + numberRange + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("includes(null, null)", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("includes(" + numberRange + ", " + number + ")", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("includes(" + numberRange + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("during(null, null)", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("during(" + number + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("during(" + numberRange + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("starts(null, null)", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("starts(" + number +", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(point, comparable, false, false), FormalParameter(range, RangeType(comparable), false, false), boolean)", false);
        testFunctionInvocation("starts(" + numberRange + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("started by(null, null)", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("started by(" + numberRange + ", " + number + ")", "BuiltinFunctionType(FormalParameter(range, RangeType(comparable), false, false), FormalParameter(point, comparable, false, false), boolean)", false);
        testFunctionInvocation("started by(" + numberRange + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);

        testFunctionInvocation("coincides(null, null)", "BuiltinFunctionType(FormalParameter(point1, comparable, false, false), FormalParameter(point2, comparable, false, false), boolean)", false);
        testFunctionInvocation("coincides(" + number + ", " + number+ ")", "BuiltinFunctionType(FormalParameter(point1, comparable, false, false), FormalParameter(point2, comparable, false, false), boolean)", false);
        testFunctionInvocation("coincides(" + numberRange + ", " + numberRange + ")", "BuiltinFunctionType(FormalParameter(range1, RangeType(comparable), false, false), FormalParameter(range2, RangeType(comparable), false, false), boolean)", false);
    }
}