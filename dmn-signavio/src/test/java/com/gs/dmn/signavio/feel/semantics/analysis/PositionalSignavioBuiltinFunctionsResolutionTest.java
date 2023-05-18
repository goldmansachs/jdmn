package com.gs.dmn.signavio.feel.semantics.analysis;

import org.junit.Test;

public class PositionalSignavioBuiltinFunctionsResolutionTest extends AbstractSignavioBuiltinFunctionsResolutionTest {
    @Test
    public void testDataAcceptanceFunctions() {
        testFunctionInvocation("isDefined(null)", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
        testFunctionInvocation("isDefined(" + number + ")", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
        testFunctionInvocation("isUndefined(null)", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
        testFunctionInvocation("isUndefined(" + number + ")", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
        testFunctionInvocation("isValid(null)", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
        testFunctionInvocation("isValid(" + number + ")", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
        testFunctionInvocation("isInvalid(null)", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
        testFunctionInvocation("isInvalid(" + number + ")", "BuiltinFunctionType(FormalParameter(arg, Any, false, false), boolean)", false);
    }

    @Test
    public void testConversionFunctions() {
        testFunctionInvocation("date(null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), date)", false);
        testFunctionInvocation("date(" + dateString + ")", "DateTimeLiteral", false);
        testFunctionInvocation("date and time(null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), date and time)", false);
        testFunctionInvocation("date and time(" + dateTimeString + ")", "DateTimeLiteral", false);
        testFunctionInvocation("time(null)", "BuiltinFunctionType(FormalParameter(from, string, false, false), time)", false);
        testFunctionInvocation("time(" + timeString + ")", "DateTimeLiteral", false);
        testFunctionInvocation("string(null)", "BuiltinFunctionType(FormalParameter(from, Any, false, false), string)", false);
        testFunctionInvocation("string(" + number + ")", "BuiltinFunctionType(FormalParameter(from, Any, false, false), string)", false);
    }

    @Test
    public void testNumberFunctions() {
        testFunctionInvocation("abs(null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("abs(" + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("count(null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("round(null, null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), FormalParameter(digits, number, false, false), number)", false);
        testFunctionInvocation("round(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), FormalParameter(digits, number, false, false), number)", false);
        testFunctionInvocation("ceiling(null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("ceiling(" + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("floor(null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("floor(" + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("integer(null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("integer(" + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("modulo(null, null)", "BuiltinFunctionType(FormalParameter(dividend, number, false, false), FormalParameter(divisor, number, false, false), number)", false);
        testFunctionInvocation("modulo(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(dividend, number, false, false), FormalParameter(divisor, number, false, false), number)", false);
        testFunctionInvocation("percent(null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("percent(" + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), number)", false);
        testFunctionInvocation("power(null, null)", "BuiltinFunctionType(FormalParameter(base, number, false, false), FormalParameter(exponent, number, false, false), number)", false);
        testFunctionInvocation("power(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(base, number, false, false), FormalParameter(exponent, number, false, false), number)", false);
        testFunctionInvocation("product(null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("product([" + number + "])", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("roundDown(null, null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), FormalParameter(digits, number, false, false), number)", false);
        testFunctionInvocation("roundDown(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), FormalParameter(digits, number, false, false), number)", false);
        testFunctionInvocation("roundUp(null, null)", "BuiltinFunctionType(FormalParameter(number, number, false, false), FormalParameter(digits, number, false, false), number)", false);
        testFunctionInvocation("roundUp(" + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(number, number, false, false), FormalParameter(digits, number, false, false), number)", false);
        testFunctionInvocation("sum(null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("sum(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
    }

    @Test
    public void testDateTimeFunctions() {
        testFunctionInvocation("day(null)", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("day(" + date + ")", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("day(" + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), number)", false);
        testFunctionInvocation("dayAdd(null, null)", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), FormalParameter(days_to_add, number, false, false), date)", false);
        testFunctionInvocation("dayAdd(" + date + ", " + number + ")", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), FormalParameter(days_to_add, number, false, false), date)", false);
        testFunctionInvocation("dayAdd(" + dateTime + ", " + number + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), FormalParameter(days_to_add, number, false, false), date and time)", false);
        testFunctionInvocation("dayDiff(null, null)", "BuiltinFunctionType(FormalParameter(datetime1, date, false, false), FormalParameter(datetime2, date, false, false), number)", false);
        testFunctionInvocation("dayDiff(" + date + ", " + date + ")", "BuiltinFunctionType(FormalParameter(datetime1, date, false, false), FormalParameter(datetime2, date, false, false), number)", false);
        testFunctionInvocation("dayDiff(" + dateTime + ", " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime1, date and time, false, false), FormalParameter(datetime2, date and time, false, false), number)", false);
        testFunctionInvocation("date(null, null, null)", "BuiltinFunctionType(FormalParameter(year, number, false, false), FormalParameter(month, number, false, false), FormalParameter(day, number, false, false), date)", false);
        testFunctionInvocation("date(" + number + ", " + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(year, number, false, false), FormalParameter(month, number, false, false), FormalParameter(day, number, false, false), date)", false);
        testFunctionInvocation("dateTime(null, null, null, null, null, null, null)", "BuiltinFunctionType(FormalParameter(day, number, false, false), FormalParameter(month, number, false, false), FormalParameter(year, number, false, false), FormalParameter(hour, number, false, false), FormalParameter(minute, number, false, false), FormalParameter(second, number, false, false), FormalParameter(hourOffset, number, true, false), date and time)", false);
        testFunctionInvocation("dateTime(" + number + ", " + number + ", " + number + ", " + number + ", " + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(day, number, false, false), FormalParameter(month, number, false, false), FormalParameter(year, number, false, false), FormalParameter(hour, number, false, false), FormalParameter(minute, number, false, false), FormalParameter(second, number, false, false), FormalParameter(hourOffset, number, true, false), date and time)", false);
        testFunctionInvocation("dateTime(" + number + ", " + number + ", " + number + ", " + number + ", " + number + ", " + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(day, number, false, false), FormalParameter(month, number, false, false), FormalParameter(year, number, false, false), FormalParameter(hour, number, false, false), FormalParameter(minute, number, false, false), FormalParameter(second, number, false, false), FormalParameter(hourOffset, number, true, false), date and time)", false);
        testFunctionInvocation("hour(null)", "BuiltinFunctionType(FormalParameter(datetime, time, false, false), number)", false);
        testFunctionInvocation("hour(" + date + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), number)", false);
        testFunctionInvocation("hour(" + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), number)", false);
        testFunctionInvocation("hourDiff(null, null)", "BuiltinFunctionType(FormalParameter(datetime1, temporal, false, false), FormalParameter(datetime2, temporal, false, false), number)", false);
        testFunctionInvocation("hourDiff(" + time + ", " + time + ")", "BuiltinFunctionType(FormalParameter(datetime1, temporal, false, false), FormalParameter(datetime2, temporal, false, false), number)", false);
        testFunctionInvocation("hourDiff(" + dateTime + ", " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime1, temporal, false, false), FormalParameter(datetime2, temporal, false, false), number)", false);
        testFunctionInvocation("minute(null)", "BuiltinFunctionType(FormalParameter(datetime, time, false, false), number)", false);
        testFunctionInvocation("minute(" + time + ")", "BuiltinFunctionType(FormalParameter(datetime, time, false, false), number)", false);
        testFunctionInvocation("minute(" + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), number)", false);
        testFunctionInvocation("minutesDiff(null, null)", "BuiltinFunctionType(FormalParameter(datetime1, temporal, false, false), FormalParameter(datetime2, temporal, false, false), number)", false);
        testFunctionInvocation("minutesDiff(" + time + ", " + time + ")", "BuiltinFunctionType(FormalParameter(datetime1, temporal, false, false), FormalParameter(datetime2, temporal, false, false), number)", false);
        testFunctionInvocation("minutesDiff(" + dateTime + ", " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime1, temporal, false, false), FormalParameter(datetime2, temporal, false, false), number)", false);
        testFunctionInvocation("month(null)", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("month(" + date + ")", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("month(" + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), number)", false);
        testFunctionInvocation("monthAdd(null, null)", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), FormalParameter(months_to_add, number, false, false), date)", false);
        testFunctionInvocation("monthAdd(" + date + ", " + number + ")", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), FormalParameter(months_to_add, number, false, false), date)", false);
        testFunctionInvocation("monthAdd(" + dateTime + ", " + number + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), FormalParameter(months_to_add, number, false, false), date and time)", false);
        testFunctionInvocation("monthDiff(null, null)", "BuiltinFunctionType(FormalParameter(datetime1, date, false, false), FormalParameter(datetime2, date, false, false), number)", false);
        testFunctionInvocation("monthDiff(" + date + ", " + date + ")", "BuiltinFunctionType(FormalParameter(datetime1, date, false, false), FormalParameter(datetime2, date, false, false), number)", false);
        testFunctionInvocation("monthDiff(" + dateTime + ", " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime1, date and time, false, false), FormalParameter(datetime2, date and time, false, false), number)", false);
        testFunctionInvocation("now()", "BuiltinFunctionType(, date and time)", false);
        testFunctionInvocation("today()", "BuiltinFunctionType(, date)", false);
        testFunctionInvocation("weekday(null)", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("weekday(" + date + ")", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("weekday(" + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), number)", false);
        testFunctionInvocation("year(null)", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("year(" + date + ")", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), number)", false);
        testFunctionInvocation("year(" + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), number)", false);
        testFunctionInvocation("yearAdd(null, null)", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), FormalParameter(years_to_add, number, false, false), date)", false);
        testFunctionInvocation("yearAdd(" + date + ", " + number + ")", "BuiltinFunctionType(FormalParameter(datetime, date, false, false), FormalParameter(years_to_add, number, false, false), date)", false);
        testFunctionInvocation("yearAdd(" + dateTime + ", " + number + ")", "BuiltinFunctionType(FormalParameter(datetime, date and time, false, false), FormalParameter(years_to_add, number, false, false), date and time)", false);
        testFunctionInvocation("yearDiff(null, null)", "BuiltinFunctionType(FormalParameter(datetime1, date, false, false), FormalParameter(datetime2, date, false, false), number)", false);
        testFunctionInvocation("yearDiff(" + date + ", " + date + ")", "BuiltinFunctionType(FormalParameter(datetime1, date, false, false), FormalParameter(datetime2, date, false, false), number)", false);
        testFunctionInvocation("yearDiff(" + dateTime + ", " + dateTime + ")", "BuiltinFunctionType(FormalParameter(datetime1, date and time, false, false), FormalParameter(datetime2, date and time, false, false), number)", false);
    }

    @Test
    public void testListFunctions() {
        testFunctionInvocation("append(null, null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(element, Null, false, false), Null)", false);
        testFunctionInvocation("append(" + numberList + ", " + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(element, number, false, false), ListType(number))", false);
        testFunctionInvocation("appendAll(null, null)", "BuiltinFunctionType(FormalParameter(list1, Null, false, false), FormalParameter(list2, Null, false, false), Null)", false);
        testFunctionInvocation("appendAll(" + numberList + ", " + numberList + ")", "BuiltinFunctionType(FormalParameter(list1, ListType(number), false, false), FormalParameter(list2, ListType(number), false, false), ListType(number))", false);
        testFunctionInvocation("remove(null, null)", "BuiltinFunctionType(FormalParameter(list, Null, false, false), FormalParameter(element, Null, false, false), Null)", false);
        testFunctionInvocation("remove(" + numberList + ", " + number + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), FormalParameter(element, number, false, false), ListType(number))", false);
        testFunctionInvocation("removeAll(null, null)", "BuiltinFunctionType(FormalParameter(list1, Null, false, false), FormalParameter(list2, Null, false, false), Null)", false);
        testFunctionInvocation("removeAll(" + numberList + ", " + numberList + ")", "BuiltinFunctionType(FormalParameter(list1, ListType(number), false, false), FormalParameter(list2, ListType(number), false, false), ListType(number))", false);
        testFunctionInvocation("zip(null, null)", "BuiltinFunctionType(FormalParameter(attributes, ListType(Any), false, false), FormalParameter(values, ListType(Any), false, false), ListType(Any))", false);
        testFunctionInvocation("zip(" + numberList + ", " + numberList + ")", "BuiltinFunctionType(FormalParameter(attributes, ListType(Any), false, false), FormalParameter(values, ListType(Any), false, false), ListType(Any))", false);
        testFunctionInvocation("notContainsAny(null, null)", "BuiltinFunctionType(FormalParameter(list1, ListType(Any), false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
        testFunctionInvocation("notContainsAny(" + numberList + ", " + numberList + ")", "BuiltinFunctionType(FormalParameter(list1, ListType(Any), false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
        testFunctionInvocation("containsOnly(null, null)", "BuiltinFunctionType(FormalParameter(list1, ListType(Any), false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
        testFunctionInvocation("containsOnly(" + numberList + ", " + numberList + ")", "BuiltinFunctionType(FormalParameter(list1, ListType(Any), false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
        testFunctionInvocation("areElementsOf(null, null)", "BuiltinFunctionType(FormalParameter(list1, ListType(Any), false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
        testFunctionInvocation("areElementsOf(" + numberList + ", " + numberList + ")", "BuiltinFunctionType(FormalParameter(list1, ListType(Any), false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
        testFunctionInvocation("elementOf(null, null)", "BuiltinFunctionType(FormalParameter(list1, Any, false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
        testFunctionInvocation("elementOf(" + number + ", " + numberList + ")", "BuiltinFunctionType(FormalParameter(list1, Any, false, false), FormalParameter(list2, ListType(Any), false, false), boolean)", false);
    }

    @Test
    public void testStatisticalFunctions() {
        testFunctionInvocation("avg(null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("avg(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("max(null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("max(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("median(null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("median(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("min(null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("min(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(number), false, false), number)", false);
        testFunctionInvocation("mode(null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
        testFunctionInvocation("mode(" + numberList + ")", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), number)", false);
    }

    @Test
    public void testStringFunctions() {
        testFunctionInvocation("concat(null)", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), string)", false);
        testFunctionInvocation("concat([" + stringString + "])", "BuiltinFunctionType(FormalParameter(list, ListType(Any), false, false), string)", false);
        testFunctionInvocation("isAlpha(null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("isAlpha(" + stringString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("isAlphanumeric(null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("isAlphanumeric(" + stringString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("isNumeric(null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("isNumeric(" + stringString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("isSpaces(null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("upper(null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), string)", false);
        testFunctionInvocation("upper(" + stringString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), string)", false);
        testFunctionInvocation("number(null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(default_value, number, true, false), number)", false);
        testFunctionInvocation("number(\"123\"" + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(default_value, number, true, false), number)", false);
        testFunctionInvocation("number(" + stringString + ", " + number + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(default_value, number, true, false), number)", false);
        testFunctionInvocation("mid(null, null, null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(start, number, false, false), FormalParameter(num_chars, number, false, false), string)", false);
        testFunctionInvocation("mid(" + stringString + ", " + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(start, number, false, false), FormalParameter(num_chars, number, false, false), string)", false);
        testFunctionInvocation("isSpaces(" + stringString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), boolean)", false);
        testFunctionInvocation("len(null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), number)", false);
        testFunctionInvocation("len(" + stringString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), number)", false);
        testFunctionInvocation("lower(null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), string)", false);
        testFunctionInvocation("lower(" + stringString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), string)", false);
        testFunctionInvocation("trim(null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), string)", false);
        testFunctionInvocation("trim(" + stringString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), string)", false);
        testFunctionInvocation("mid(" + stringString + ", " + number + ", " + number + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(start, number, false, false), FormalParameter(num_chars, number, false, false), string)", false);
        testFunctionInvocation("left(null, null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(num_chars, number, false, false), string)", false);
        testFunctionInvocation("left(" + stringString + ", " + number + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(num_chars, number, false, false), string)", false);
        testFunctionInvocation("right(null, null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(num_chars, number, false, false), string)", false);
        testFunctionInvocation("right(" + stringString + ", " + number + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(num_chars, number, false, false), string)", false);
        testFunctionInvocation("text(null, null)", "BuiltinFunctionType(FormalParameter(num, number, false, false), FormalParameter(format_text, string, false, false), string)", false);
        testFunctionInvocation("text(" + number + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(num, number, false, false), FormalParameter(format_text, string, false, false), string)", false);
        testFunctionInvocation("textOccurrences(null, null)", "BuiltinFunctionType(FormalParameter(find_text, string, false, false), FormalParameter(within_text, string, false, false), number)", false);
        testFunctionInvocation("textOccurrences(" + stringString + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(find_text, string, false, false), FormalParameter(within_text, string, false, false), number)", false);
        testFunctionInvocation("contains(null, null)", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(substring, string, false, false), boolean)", false);
        testFunctionInvocation("contains(" + stringString + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(text, string, false, false), FormalParameter(substring, string, false, false), boolean)", false);
        testFunctionInvocation("startsWith(null, null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(prefix, string, false, false), boolean)", false);
        testFunctionInvocation("startsWith(" + stringString + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(prefix, string, false, false), boolean)", false);
        testFunctionInvocation("endsWith(null, null)", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(suffix, string, false, false), boolean)", false);
        testFunctionInvocation("endsWith(" + stringString + ", " + stringString + ")", "BuiltinFunctionType(FormalParameter(string, string, false, false), FormalParameter(suffix, string, false, false), boolean)", false);
    }

    @Test
    public void testBooleanFunctions() {
        testFunctionInvocation("not(null)", "LogicNegation", false);
        testFunctionInvocation("not(true)", "LogicNegation", false);
    }
}