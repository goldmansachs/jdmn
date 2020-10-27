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
package com.gs.dmn.signavio.feel.lib;

import com.gs.dmn.feel.lib.FEELLib;

import java.util.List;

public interface SignavioLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    //
    // Data acceptance functions
    //
    Boolean isDefined(Object object);

    Boolean isUndefined(Object object);

    Boolean isValid(Object object);

    Boolean isInvalid(Object object);

    //
    // Arithmetic functions
    //

    //    Abs(number):NUMERIC
    //    Returns the absolute value of a number.
    //    Example: Abs(-5) returns 5.
    //
    NUMBER abs(NUMBER number);

    //    Count([num1 ,num2, num3]):NUMERIC
    //    Returns the number of elements of the given list.
    //    Example: Count(["item1", "item2", "item3"]) returns 3.
    @Override
    NUMBER count(List list);

    //    Round(number,digits):NUMERIC
    //    Returns a number rounded to the corresponding number of digits.
    //    Example: Round(3.44,1) returns 3.4.
    NUMBER round(NUMBER number, NUMBER digits);

    //    Ceiling(number):NUMERIC
    //    Returns a number rounded up to the next integer.
    //    Example: Ceiling(1.3) returns 2.
    NUMBER ceiling(NUMBER number);

    //    Floor(number):NUMERIC
    //    Returns a number rounded down to the next integer.
    //    Example: Floor(1.6) returns 1.
    NUMBER floor(NUMBER number);

    //    Integer(number): NUMERIC
    //    Returns the integer part of a number.
    //    Example: Integer(1.34) returns 1.
    NUMBER integer(NUMBER number);

    //    Modulo(divident, divisor):NUMERIC
    //    Returns the remainder of the dividend divided by the divisor.
    //    Example: Modulo(4, 3) returns 1.
    NUMBER modulo(NUMBER dividend, NUMBER divisor);

    //    Percent(number):NUMERIC
    //    Returns the number divided by .
    //    Example: Percent(10) returns 0.1.
    NUMBER percent(NUMBER number);

    //    Power(base, exponent):NUMERIC
    //    Returns the base raised to the power of the exponent.
    //    Example: Power(2, 3) returns 8.
    NUMBER power(NUMBER base, NUMBER exponent);

    //    Product([factor1, factor2, factor3]):NUMERIC
    //    Returns the proudct of a list of factors.
    //    Example: Product([2, 3, 4]) returns 24.
    NUMBER product(List factors);

    //    RoundDown(number, digits):NUMERIC
    //    Returns a number rounded down to the corresponding number of digits.
    //    Example: RoundDown(1.3674, 2) returns 1.36.
    NUMBER roundDown(NUMBER number, NUMBER digits);

    //    RoundUp(number, digits):NUMERIC
    //    Returns a number rounded up to the corresponding number of digits.
    //    Example: Abs(1.344, 2) returns 1.35.
    NUMBER roundUp(NUMBER number, NUMBER digits);

    //    Sum([number1, number2, number3]):NUMERIC
    //    Returns the sum of a list of values.
    //    Example: Sum([1, 2, 3, 4, 5]) returns 15.
    @Override
    NUMBER sum(List numbers);

    //
    // Date and time operations
    //

    //    Day(datetime):NUMERIC
    //    Returns the day part of a datetime.
    //    Example: Day(2015-12-24T12:15:00.000+01:00) returns 24.
    @Override
    NUMBER day(DATE date);

    //    DayAdd(datetime, days to add):DATE
    //    Returns the date plus the provided number of days.
    //    Example: DayAdd(2015-12-24T12:15:00.000+01:00, 1) returns
    //    2015-12-25T12:15:00.000+01:00.
    DATE dayAdd(DATE date, NUMBER daysToAdd);

    //    DayDiff(datetime1, datetime2):NUMERIC
    //    Returns the amount of days between two days.
    //    Example: DayDiff(2015-12-24T12:15:00.000+01:00, 2015-12-25T12:15:00.000+01:00)
    //    returns 1.
    NUMBER dayDiff(DATE date1, DATE date2);

    //    Date(day, month, year):DATE
    //    Returns a date using the standard parameters of a date: day, month, year
    //    Example: Date(25, 12, 2015) returns 2015-12-25.
    DATE date(NUMBER year, NUMBER month, NUMBER day);

    //    DateTime(day, month, year, hour, minute, second, hourOffsett):DATE
    //    Returns the dateTime using the standard parameters of a data time. The last parameter
    //    ‘hourOffset’ is optional.
    //    Example: DateTime(25, 12, 2015, 12, 15, 0, 1) returns
    //    2015-12-24T12:15:00.000+01:00.
    DATE_TIME dateTime(NUMBER day, NUMBER month, NUMBER year, NUMBER hour, NUMBER minute, NUMBER second);
    DATE_TIME dateTime(NUMBER day, NUMBER month, NUMBER year, NUMBER hour, NUMBER minute, NUMBER second, NUMBER hourOffset);

    //    Hour(datetime):NUMERIC
    //    Returns the hour part of a datetime.
    //    Example: Hour(2015-12-24T12:15:00.000+01:00) returns 12.
    @Override
    NUMBER hour(TIME time);

    //    HourDiff(datetime1, datetime2):NUMERIC
    //    Return the number of hours between two datetimes.
    //    Example: HourDiff(2015-12-24T12:15:00.000+01:00, 2015-12-24T14:15:00.000+01:00)
    //    returns 2.
    NUMBER hourDiff(TIME time1, TIME time2);

    //    Minute(time):NUMERIC
    //    Returns the minute part of a datetime.
    //    Example: Minute(2015-12-24T12:15:00.000+01:00) returns 15.
    @Override
    NUMBER minute(TIME time);

    //    MinutesDiff(datetimes1, date2times):NUMERIC
    //    Return the number of minutes between two datetimes.
    //    Example: MinutesDiff(2015-12-24T12:15:00.000+01:00,
    //                         2015-12-24T13:15:00.000+01:00) returns 60.
    NUMBER minutesDiff(TIME time1, TIME time2);

    //    Month(datetime):NUMERIC
    //    Returns the month part of a datetime.
    //    Example: Month(2015-12-24T12:15:00.000+01:00) returns 12.
    @Override
    NUMBER month(DATE date);

    //    MonthAdd(datetime, months_to_add):DATE
    //    Returns the datetime plus the number of months.
    //    Example: MonthAdd(2015-10-10T12:15:00.000+01:00, 1) returns
    //    2015-11-10T12:15:00.000+01:00.
    DATE monthAdd(DATE date, NUMBER monthsToAdd);

    //    MonthDiff(datetime1, datetime2):NUMERIC
    //    Return the number of month between two datetimes.
    //    Example: MonthDiff(2015-10-10T12:15:00.000+01:00, 2015-11-10T12:15:00.000+01:00)
    //    returns 1.
    NUMBER monthDiff(DATE date1, DATE date2);

    //    Now():DATE_TIME
    //    Returns current datetime.
    //    Example: Now() could have returned 2015-11-10T12:15:00.000+01:00.
    DATE_TIME now();

    //    Today():DATE
    //    Returns the current date.
    //    Example: Today() could have returned 2015-11-10.
    DATE today();

    //    Weekday(datetime):NUMERIC
    //    Returns a number ( to ) representing the day of the week.
    //    Example: weekday(2016-02-09T12:15:00.000+01:00) returns 3.
    // NUMBER weekday(DATE_TIME dateTime);

    //    Year(datetime):NUMERIC
    //    Returns the year part of a datetime.
    //    Example: Year(2016-02-09T12:15:00.000+01:00) returns 2016.
    @Override
    NUMBER year(DATE date);

    //    YearAdd(datetime, years_to_add):DATE
    //    Returns the datetime plus the number of years.
    //    Example: YearAdd(2016-02-09T12:15:00.000+01:00, 1) returns
    //    2017-02-09T12:15:00.000+01:00.
    DATE yearAdd(DATE date, NUMBER yearsToAdd);

    //    YearDiff(datetime1, datetime2):NUMERIC
    //    Returns the number of years between two datetimes.
    //    Example: YearDiff(2016-02-09T12:15:00.000+01:00, 2017-02-09T12:15:00.000+01:00)
    //    returns 1.
    NUMBER yearDiff(DATE date1, DATE date2);

    //
    // List operations
    //

    //    Append(list, element): LIST
    //    Adds the element to a copy of the provided list. Returns the manipulated copy.
    //    Example: Append([2.5, 5.8, 4.3], 6.7) returns [2.5, 5.8, 4.3, 6.7].
    List append(List list, Object element);

    //    AppendAll(list1, list2): LIST
    //    Adds all elements from the second provided list to a copy of the 1rst one. Returns the
    //    manipulated copy.
    //    Example: AppendAll([2.5, 5.8, 4.3], [2.1, 3.5, 7.4]) returns [2.5, 5.8, 4.3,
    //            2.1, 3.5, 7.4].
    List appendAll(List list1, List list2);

    //    Zip(attributes, values): LIST
    //    Assembles a list of objects out of a list of attributes and a list values.
    //    Example: Zip(["id", "value"], [[23a3e98, c45da1b], [40, 120]]) returns [{id:
    //    23a3e98, value: 40},{ id: c45da1b, value: 120}].
    List<?> zip(List attributes, List values);

    //    Remove(list, element): LIST
    List remove(List list, Object element);

    //    RemoveAll(list1, list2): LIST
    List removeAll(List list1, List list2);

    //  NotContainsAny(list1, list2): BOOLEAN
    //  Determines whether list1 contains any element of list2.
    //  Example: NotContainsAny(["item1", "item2"], ["item2, item3"]) returns false.
    Boolean notContainsAny(List list1, List list2);

    //  ContainsOnly(list1, list2): BOOLEAN
    //  Determines whether list1 contains only elements of list2.
    //  Example: ContainsOnly(["item1", "item2"], ["item2, item3"]) returns false.
    Boolean containsOnly(List list1, List list2);

    //  AreElementsOf(list1, list2): BOOLEAN
    //  Determines whether list2 contains all elements of list1.
    //  Example: AreElementsOf(["item2, item3"], ["item1", "item2" "item3"]) returns
    //  true.
    Boolean areElementsOf(List list1, List list2);
    Boolean elementOf(List list1, List list2);

    //
    // Statistical operations
    //

    //    Avg([number1, number2, number3]):NUMERIC
    //    Returns the average of the values of the given list.
    //    Example: Avg([3,5]) returns 4.
    NUMBER avg(List list);

    //    Max([number1, number2, number3]):NUMERIC
    //    Returns the maximum value of the given list.
    //    Example: Max([5, 4, 10]) returns 10.
    @Override
    NUMBER max(List numbers);

    //    Median([number1, number2, number3]):NUMERIC
    //    Returns the median value of the given list.
    //    Example: Median([2, 5, 10, 12, 34, 35]) returns 11.
    NUMBER median(List numbers);

    //    Min([number1, number2, number3]):NUMERIC
    //    Returns the minimum value of the given list.
    //    Example: Min([5, 4, 10]) returns 2.
    @Override
    NUMBER min(List numbers);

    //    Mode([number1, number2, number3]):NUMERIC
    //    Returns the most frequently occurring value of the given list. Returns the 1rst (most left)
    //    most frequent value, if several values occur most frequently (e.g. two values appear each
    //            two times).
    //    Example: Mode([1, 2, 4, 4, 5, 6]) returns 4.
    NUMBER mode(List numbers);

    //
    // String functions
    //

    //    Concat([text1, text2, text3]):TEXT
    //    Returns the concatenation of the given list of text values.
    //    Example: Concat(["Hello ", "World", "!"]) returns "Hello World!".
    String concat(List<String> texts);

    //    IsAlpha(text):BOOLEAN
    //    Determines whether the text contains only alphabetic characters (A-Z, a-z). Umlauts and
    //    similar characters (e.g. Ä, Å ß) must not be included.
    //    Example: IsAlpha("abcdefg5") returns false.
    Boolean isAlpha(String text);

    //    IsAlphanumeric(text):BOOLEAN
    //    Determines whether the text contains only alphanumeric characters (A-Z, a-z, -). Umlauts
    //    and similar characters (e.g. Ä, Å ß) must not be included.
    //    Example: isAlphanumeric("abcdefg5") returns true.
    Boolean isAlphanumeric(String text);

    //    IsNumeric(text):BOOLEAN
    //    Determines whether the text is a valid number containing only plus or minus sign, digits,
    //    commas, and decimal points.
    //    Example: IsNumeric(2.3.5) returns false
    Boolean isNumeric(String text);

    //    IsSpaces(text):BOOLEAN
    //    Determines whether the text contains only spaces.
    //    Example: IsSpaces(" ") returns true.
    Boolean isSpaces(String text);

    //    Len(text):NUMERIC
    //    Returns the number of characters in a text string.
    //    Example: Len("five") returns 4.
    NUMBER len(String text);

    //    Lower(text):TEXT
    //    Returns the text string with all letters converted to lowercase.
    //    Example: Lower("UPPER") returns upper.
    String lower(String text);

    //    Trim(text):TEXT
    //    Returns the text string with all spaces removed except single spaces between words.
    //            Example: Trim("Hello World! ") returns "Hello World!".
    String trim(String text);

    //    Upper(text):TEXT
    //    Returns the text string with all letters converted to uppercase.
    //    Example: Upper("lower") returns "LOWER".
    String upper(String text);

    //    Number(text):NUMERIC
    //    Returns the numerical value represented in the text string. Only a period (.) is allowed as a
    //    separator.
    //    Example: Number("5") returns 5.
    @Override
    NUMBER number(String text);

    //    Number(text, default_value):NUMERIC
    //    Returns the numerical value represented in the text string. Only a period (.) is allowed as a
    //    separator. Returns default_value if unable to convert text into number.
    //    Example: Number("5,5", 10) returns 10 (Number("5.5", 10) returns 5.5).
    NUMBER number(String text, NUMBER defaultValue);

    //    Mid(text, start, num_chars):TEXT
    //    Returns the character sequence of the length num_chars from the corresponding starting
    //    position of a text string.
    //    Example: Mid("Hello World!", 6, 5) returns "World".
    String mid(String text, NUMBER start, NUMBER numChar);

    //    Left(text, num_chars):TEXT
    //    Returns the character sequence of the length num_chars from the start of a text string.
    //    Example: Left("Hello World!", 5) returns "Hello".
    String left(String text, NUMBER numChar);

    //    Right(text, num_chars):TEXT
    //    Returns the character sequence of the length num_chars from the end of a text string.
    //    Example: right("Hello World!", 6) returns "World!".
    String right(String text, NUMBER numChar);

    //    Text(num, format_text):TEXT
    //    Returns a numeric value as a text string in a speci1c format. The format is specified by the
    //    placeholders # and 0 and a decimal point ..
    //    Example: Text(1, "#.000") returns "1.000".
    String text(NUMBER num, String formatText);

    //    TextOccurrences(find_text, within_text):NUMERIC
    //    Returns the number of occurrences of find_text within within_text.
    //    Example: TextOccurrences("can", "Can you can a can as a canner can can a
    //    can?") returns 6.
    NUMBER textOccurrences(String findText, String withinText);

    //    Contains(text, substring): BOOLEAN
    //    Determines whether text‘‘contains the ‘‘substring.
    //    Example: Contains("Hello World!", "o World") returns true.
    Boolean contains(String text, String substring);

    //    StartsWith(text, prefix): BOOLEAN
    //    Determines whether text‘‘starts with the ‘‘prefix.
    //    Example: StartsWith("Hello World!", "Hello") returns true.
    Boolean startsWith(String text, String prefix);

    //    EndsWith(text, suffix): BOOLEAN
    //    Determines whether text‘‘ends with the ‘‘suffix.
    //    Example: endsWith("Hello World!", "!") returns true.
    Boolean endsWith(String text, String suffix);

    //    not(boolean): BOOLEAN
    //    Negates the input boolean
    //    Example: endsWith("Hello World!", "!") returns true.
    Boolean not(Boolean bool);
}
