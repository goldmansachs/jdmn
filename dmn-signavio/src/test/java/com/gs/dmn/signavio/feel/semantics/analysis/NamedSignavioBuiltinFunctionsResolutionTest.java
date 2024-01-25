package com.gs.dmn.signavio.feel.semantics.analysis;

import org.junit.jupiter.api.Test;

public class NamedSignavioBuiltinFunctionsResolutionTest extends AbstractSignavioBuiltinFunctionsResolutionTest {
    @Test
    public void testDataAcceptanceFunctions() {
        testFunctionInvocation("isDefined(arg: null)", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
        testFunctionInvocation("isDefined(arg: " + number + ")", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
        testFunctionInvocation("isUndefined(arg: null)", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
        testFunctionInvocation("isUndefined(arg: " + number + ")", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
        testFunctionInvocation("isValid(arg: null)", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
        testFunctionInvocation("isValid(arg: " + number + ")", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
        testFunctionInvocation("isInvalid(arg: null)", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
        testFunctionInvocation("isInvalid(arg: " + number + ")", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
    }

    @Test
    public void testConversionFunctions() {
        testFunctionInvocation("date(from: null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), date)", false);
        testFunctionInvocation("date(from: " + dateString + ")", "BuiltinFunctionType(FormalParameter(from, string, false, false), date)", false);
        testFunctionInvocation("date and time(from: null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), date and time)", false);
        testFunctionInvocation("date and time(from: " + dateTimeString + ")", "BuiltinFunctionType(FormalParameter(from, string, false, false), date and time)", false);
        testFunctionInvocation("time(from: null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), time)", false);
        testFunctionInvocation("time(from: " + timeString + ")", "BuiltinFunctionType(FormalParameter(from, string, false, false), time)", false);
        testFunctionInvocation("string(from: null)", "BuiltinFunctionType(FormalParameter(from, Any, false, false), string)", false);
        testFunctionInvocation("string(from: " + number + ")", "BuiltinFunctionType(FormalParameter(from, Any, false, false), string)", false);
    }

    @Test
    public void testNumberFunctions() {
        testFunctionInvocation("abs(number: null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("abs(number: " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("count(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("round(number: null, digits: null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), FormalParameter(digits, number, false, false), number)", false);
        testFunctionInvocation("round(number: " + number + ", digits:" + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), FormalParameter(digits, number, false, false), number)", false);
        testFunctionInvocation("ceiling(number: null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("ceiling(number: " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("floor(number: null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("floor(number: " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("integer(number: null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("integer(number: " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("modulo(dividend: null, divisor: null)", "BuiltinFunctionType(FormalParameter(dividend, number, false, false), FormalParameter(divisor, number, false, false), number)", false);
        testFunctionInvocation("modulo(dividend: " + number + ", divisor: " + number + ")", "BuiltinFunctionType(FormalParameter(dividend, number, false, false), FormalParameter(divisor, number, false, false), number)", false);
        testFunctionInvocation("percent(number: null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("percent(number: " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("power(base: null, exponent: null)", "BuiltinFunctionType(FormalParameter(base, number, false, false), FormalParameter(exponent, number, false, false), number)", false);
        testFunctionInvocation("power(base: " + number + ", exponent: " + number + ")", "BuiltinFunctionType(FormalParameter(base, number, false, false), FormalParameter(exponent, number, false, false), number)", false);
        testFunctionInvocation("product(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("product(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("roundDown(number: null, digits: null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), FormalParameter(digits, number, false, false), number)", false);
        testFunctionInvocation("roundDown(number: " + number + ", digits: " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), FormalParameter(digits, number, false, false), number)", false);
        testFunctionInvocation("roundUp(number: null, digits: null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), FormalParameter(digits, number, false, false), number)", false);
        testFunctionInvocation("roundUp(number: " + number + ", digits: " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), FormalParameter(digits, number, false, false), number)", false);
        testFunctionInvocation("sum(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("sum(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
    }

    @Test
    public void testDateTimeFunctions() {
        testFunctionInvocation("day(datetime: null)", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("day(datetime: " + date + ")", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("day(datetime: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), number)", false);
        testFunctionInvocation("dayAdd(datetime: null, days_to_add: null)", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), FormalParameter(days_to_add, number, false, false), date)", false);
        testFunctionInvocation("dayAdd(datetime: " + date + ", days_to_add: " + number + ")", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), FormalParameter(days_to_add, number, false, false), date)", false);
        testFunctionInvocation("dayAdd(datetime: " + dateTime + ", days_to_add: " + number + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), FormalParameter(days_to_add, number, false, false), date and time)", false);
        testFunctionInvocation("dayDiff(datetime1: null, datetime2: null)", "BuiltinFunctionType(FormalParameter(datetime1, date, false, false), FormalParameter(datetime2, date, false, false), number)", false);
        testFunctionInvocation("dayDiff(datetime1: " + date + ", datetime2: " + date + ")", "BuiltinFunctionType(FormalParameter(datetime1, date, false, false), FormalParameter(datetime2, date, false, false), number)", false);
        testFunctionInvocation("dayDiff(datetime1: " + dateTime + ", datetime2: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime1, date and time, false, false), FormalParameter(datetime2, date and time, false, false), number)", false);
        testFunctionInvocation("date(year: null, month: null, day: null)", "BuiltinFunctionType(FormalParameter(year, number, false, false), FormalParameter(month, number, false, false), FormalParameter(day, number, false, false), date)", false);
        testFunctionInvocation("date(year: " + number + ", month: " + number + ", day: " + number + ")", "BuiltinFunctionType(FormalParameter(year, number, false, false), FormalParameter(month, number, false, false), FormalParameter(day, number, false, false), date)", false);
        testFunctionInvocation("dateTime(day: null, month: null, year: null, hour: null, minute: null, second: null, hourOffset: null)", "BuiltinFunctionType(FormalParameter(day, number, false, false), FormalParameter(month, number, false, false), FormalParameter(year, number, false, false), FormalParameter(hour, number, false, false), FormalParameter(minute, number, false, false), FormalParameter(second, number, false, false), FormalParameter(hourOffset, number, true, false), date and time)", false);
        testFunctionInvocation("dateTime(day: " + number + ", month: " + number + ", year: " + number + ", hour: " + number + ", minute: " + number + ", second: " + number + ")", "BuiltinFunctionType(FormalParameter(day, number, false, false), FormalParameter(month, number, false, false), FormalParameter(year, number, false, false), FormalParameter(hour, number, false, false), FormalParameter(minute, number, false, false), FormalParameter(second, number, false, false), FormalParameter(hourOffset, number, true, false), date and time)", false);
        testFunctionInvocation("dateTime(day: " + number + ", month: " + number + ", year: " + number + ", hour: " + number + ", minute: " + number + ", second: " + number + ", hourOffset: " + number + ")", "BuiltinFunctionType(FormalParameter(day, number, false, false), FormalParameter(month, number, false, false), FormalParameter(year, number, false, false), FormalParameter(hour, number, false, false), FormalParameter(minute, number, false, false), FormalParameter(second, number, false, false), FormalParameter(hourOffset, number, true, false), date and time)", false);
        testFunctionInvocation("hour(datetime: null)", "BuiltinFunctionType(FormalParameter(datetime, time, false, false), number)", false);
        testFunctionInvocation("hour(datetime: " + date + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), number)", false);
        testFunctionInvocation("hour(datetime: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), number)", false);
        testFunctionInvocation("hourDiff(datetime1: null, datetime2: null)", "BuiltinFunctionType(FormalParameter(datetime1, temporal, false, false), FormalParameter(datetime2, temporal, false, false), number)", false);
        testFunctionInvocation("hourDiff(datetime1: " + time + ", datetime2: " + time + ")", "BuiltinFunctionType(FormalParameter(datetime1, temporal, false, false), FormalParameter(datetime2, temporal, false, false), number)", false);
        testFunctionInvocation("hourDiff(datetime1: " + dateTime + ", datetime2: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime1, temporal, false, false), FormalParameter(datetime2, temporal, false, false), number)", false);
        testFunctionInvocation("minute(datetime: null)", "BuiltinFunctionType(FormalParameter(datetime, time, false, false), number)", false);
        testFunctionInvocation("minute(datetime: " + time + ")", "BuiltinFunctionType(FormalParameter(datetime, time, false, false), number)", false);
        testFunctionInvocation("minute(datetime: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), number)", false);
        testFunctionInvocation("minutesDiff(datetime1: null, datetime2: null)", "BuiltinFunctionType(FormalParameter(datetime1, temporal, false, false), FormalParameter(datetime2, temporal, false, false), number)", false);
        testFunctionInvocation("minutesDiff(datetime1: " + time + ", datetime2: " + time + ")", "BuiltinFunctionType(FormalParameter(datetime1, temporal, false, false), FormalParameter(datetime2, temporal, false, false), number)", false);
        testFunctionInvocation("minutesDiff(datetime1: " + dateTime + ", datetime2: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime1, temporal, false, false), FormalParameter(datetime2, temporal, false, false), number)", false);
        testFunctionInvocation("month(datetime: null)", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("month(datetime: " + date + ")", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("month(datetime: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), number)", false);
        testFunctionInvocation("monthAdd(datetime: null, months_to_add: null)", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), FormalParameter(months_to_add, number, false, false), date)", false);
        testFunctionInvocation("monthAdd(datetime: " + date + ", months_to_add: " + number + ")", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), FormalParameter(months_to_add, number, false, false), date)", false);
        testFunctionInvocation("monthAdd(datetime: " + dateTime + ", months_to_add: " + number + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), FormalParameter(months_to_add, number, false, false), date and time)", false);
        testFunctionInvocation("monthDiff(datetime1: null, datetime2: null)", "BuiltinFunctionType(FormalParameter(datetime1, date, false, false), FormalParameter(datetime2, date, false, false), number)", false);
        testFunctionInvocation("monthDiff(datetime1: " + date + ", datetime2: " + date + ")", "BuiltinFunctionType(FormalParameter(datetime1, date, false, false), FormalParameter(datetime2, date, false, false), number)", false);
        testFunctionInvocation("monthDiff(datetime1: " + dateTime + ", datetime2: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime1, date and time, false, false), FormalParameter(datetime2, date and time, false, false), number)", false);
        testFunctionInvocation("now()", "BuiltinFunctionType(, date and time)", false);
        testFunctionInvocation("today()", "BuiltinFunctionType(, date)", false);
        testFunctionInvocation("weekday(datetime: null)", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("weekday(datetime: " + date + ")", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("weekday(datetime: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), number)", false);
        testFunctionInvocation("year(null)", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("year(datetime: " + date + ")", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("year(datetime: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), number)", false);
        testFunctionInvocation("yearAdd(datetime: null, years_to_add: null)", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), FormalParameter(years_to_add, number, false, false), date)", false);
        testFunctionInvocation("yearAdd(datetime: " + date + ", years_to_add: " + number + ")", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), FormalParameter(years_to_add, number, false, false), date)", false);
        testFunctionInvocation("yearAdd(datetime: " + dateTime + ", years_to_add: " + number + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), FormalParameter(years_to_add, number, false, false), date and time)", false);
        testFunctionInvocation("yearDiff(datetime1: null, datetime2: null)", "BuiltinFunctionType(FormalParameter(datetime1, date, false, false), FormalParameter(datetime2, date, false, false), number)", false);
        testFunctionInvocation("yearDiff(datetime1: " + date + ", datetime2: " + date + ")", "BuiltinFunctionType(FormalParameter(datetime1, date, false, false), FormalParameter(datetime2, date, false, false), number)", false);
        testFunctionInvocation("yearDiff(datetime1: " + dateTime + ", datetime2: " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime1, date and time, false, false), FormalParameter(datetime2, date and time, false, false), number)", false);
    }

    @Test
    public void testListFunctions() {
        testFunctionInvocation("append(list: null, element: null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(element, Null, false, false), Null)", false);
        testFunctionInvocation("append(list: " + numberList + ", element: " + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(element, number, false, false), ListType(number))", false);
        testFunctionInvocation("appendAll(list1: null, list2: null)", "BuiltinFunctionType(FormalParameter(list1, Null, false, false), FormalParameter(list2, Null, false, false), Null)", false);
        testFunctionInvocation("appendAll(list1: " + numberList + ", list2: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list1, ListType(number), false, false), FormalParameter(list2, ListType(number), false, false), ListType(number))", false);
        testFunctionInvocation("remove(list: null, element: null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(element, Null, false, false), Null)", false);
        testFunctionInvocation("remove(list: " + numberList + ", element: " + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(element, number, false, false), ListType(number))", false);
        testFunctionInvocation("removeAll(list1: null, list2: null)", "BuiltinFunctionType(FormalParameter(list1, Null, false, false), FormalParameter(list2, Null, false, false), Null)", false);
        testFunctionInvocation("removeAll(list1: " + numberList + ", list2: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list1, ListType(number), false, false), FormalParameter(list2, ListType(number), false, false), ListType(number))", false);
        testFunctionInvocation("zip(attributes: null, values: null)", "BuiltinFunctionType(FormalParameter(attributes, ListType(Any), false, false), FormalParameter(values, ListType(Any), false, false), ListType(Any))", false);
        testFunctionInvocation("zip(attributes: " + numberList + ", values: " + numberList + ")", "BuiltinFunctionType(FormalParameter(attributes, ListType(Any), false, false), FormalParameter(values, ListType(Any), false, false), ListType(Any))", false);
        testFunctionInvocation("notContainsAny(list1: null, list2: null)", "BuiltinFunctionType(FormalParameter(list1, ListType(Any), false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
        testFunctionInvocation("notContainsAny(list1: " + numberList + ", list2: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list1, ListType(Any), false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
        testFunctionInvocation("containsOnly(list1: null, list2: null)", "BuiltinFunctionType(FormalParameter(list1, ListType(Any), false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
        testFunctionInvocation("containsOnly(list1: " + numberList + ", list2: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list1, ListType(Any), false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
        testFunctionInvocation("areElementsOf(list1: null, list2: null)", "BuiltinFunctionType(FormalParameter(list1, ListType(Any), false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
        testFunctionInvocation("areElementsOf(list1: " + numberList + ", list2: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list1, ListType(Any), false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
        testFunctionInvocation("elementOf(list1: null, list2: null)", "BuiltinFunctionType(FormalParameter(list1, Any, false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
        testFunctionInvocation("elementOf(list1: " + numberList + ", list2: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list1, Any, false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
    }

    @Test
    public void testStatisticalFunctions() {
        testFunctionInvocation("avg(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("avg(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("max(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("max(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("median(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("median(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("min(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("min(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("mode(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("mode(list: " + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
    }

    @Test
    public void testStringFunctions() {
        testFunctionInvocation("concat(list: null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), string)", false);
        testFunctionInvocation("concat(list: " + stringList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), string)", false);
        testFunctionInvocation("isAlpha(text: null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("isAlpha(text: " + stringString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("isAlphanumeric(text: null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("isAlphanumeric(text: " + stringString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("isNumeric(text: null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("isNumeric(text: " + stringString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("isSpaces(text: null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("upper(text: null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), string)", false);
        testFunctionInvocation("upper(text: " + stringString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), string)", false);
        testFunctionInvocation("number(text: null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(default_value, number, true, false), number)", false);
        testFunctionInvocation("number(text: " + numberString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(default_value, number, true, false), number)", false);
        testFunctionInvocation("number(text: " + stringString + ", default_value:" + number + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(default_value, number, true, false), number)", false);
        testFunctionInvocation("mid(text: null, start: null, num_chars: null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(start, number, false, false), FormalParameter(num_chars, number, false, false), string)", false);
        testFunctionInvocation("mid(text: " + stringString + ", start: " + number + ", num_chars: " + number + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(start, number, false, false), FormalParameter(num_chars, number, false, false), string)", false);
        testFunctionInvocation("left(text: null, num_chars: null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(num_chars, number, false, false), string)", false);
        testFunctionInvocation("left(text: " + stringString + ", num_chars: " + number + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(num_chars, number, false, false), string)", false);
        testFunctionInvocation("right(text: null, num_chars: null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(num_chars, number, false, false), string)", false);
        testFunctionInvocation("right(text: " + stringString + ", num_chars: " + number + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(num_chars, number, false, false), string)", false);
        testFunctionInvocation("text(num: null, format_text: null)", "BuiltinFunctionType(FormalParameter(num, number, false, false), FormalParameter(format_text, string, false, false), string)", false);
        testFunctionInvocation("text(num: " + number + ", format_text: " + stringString + ")", "BuiltinFunctionType(FormalParameter(num, number, false, false), FormalParameter(format_text, string, false, false), string)", false);
        testFunctionInvocation("textOccurrences(find_text: null, within_text: null)", "BuiltinFunctionType(FormalParameter(find_text, string, false, false), FormalParameter(within_text, string, false, false), number)", false);
        testFunctionInvocation("textOccurrences(find_text: " + stringString + ", within_text: " + stringString + ")", "BuiltinFunctionType(FormalParameter(find_text, string, false, false), FormalParameter(within_text, string, false, false), number)", false);
        testFunctionInvocation("contains(text: null, substring: null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(substring, string, false, false), boolean)", false);
        testFunctionInvocation("contains(text: " + stringString + ", substring: " + stringString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(substring, string, false, false), boolean)", false);
        testFunctionInvocation("startsWith(string: null, prefix: null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(prefix, string, false, false), boolean)", false);
        testFunctionInvocation("startsWith(string: " + stringString + ", prefix: " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(prefix, string, false, false), boolean)", false);
        testFunctionInvocation("endsWith(string: null, suffix: null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(suffix, string, false, false), boolean)", false);
        testFunctionInvocation("endsWith(string: " + stringString + ", suffix: " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(suffix, string, false, false), boolean)", false);
    }

    @Test
    public void testBooleanFunctions() {
        // Not supported yet as not is also a unary operator
//        testFunctionInvocation("not(negand: null)", "LogicNegation", false);
//        testFunctionInvocation("not(negand: true)", "LogicNegation", false);
    }
}
