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
package com.gs.dmn.feel.lib.type.range;

import com.gs.dmn.feel.lib.JavaTimeFEELLib;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.feel.lib.type.ComparableComparator;
import com.gs.dmn.feel.lib.type.RelationalComparator;
import com.gs.dmn.feel.lib.type.bool.BooleanType;
import com.gs.dmn.feel.lib.type.bool.DefaultBooleanType;
import com.gs.dmn.feel.lib.type.numeric.DoubleComparator;
import com.gs.dmn.feel.lib.type.time.mixed.LocalDateComparator;
import com.gs.dmn.feel.lib.type.time.mixed.OffsetTimeComparator;
import com.gs.dmn.feel.lib.type.time.mixed.ZonedDateTimeComparator;
import com.gs.dmn.feel.lib.type.time.pure.TemporalAmountComparator;
import com.gs.dmn.feel.lib.type.time.pure.TemporalComparator;
import com.gs.dmn.feel.lib.type.time.xml.DefaultDurationComparator;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.Range;
import org.apache.commons.lang3.StringUtils;

import javax.xml.datatype.Duration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.*;
import java.util.regex.Pattern;

//
// Allen's algebra https://en.wikipedia.org/wiki/Allen%27s_interval_algebra
//
// CQL https://cql.hl7.org/09-b-cqlreference.html#interval-operators-3
//
public class DefaultRangeLib implements RangeLib {
    private static final BooleanType BOOLEAN_TYPE = new DefaultBooleanType();
    private static final Map<Class<?>, RelationalComparator<?>> COMPARATOR_MAP = new LinkedHashMap<>();

    private static final Set<String> ENDPOINT_MARKERS = new LinkedHashSet<>();

    static {
        COMPARATOR_MAP.put(BigDecimal.class, new ComparableComparator<BigDecimal>());
        COMPARATOR_MAP.put(Double.class, DoubleComparator.COMPARATOR);
        COMPARATOR_MAP.put(String.class, new ComparableComparator<String>());

        COMPARATOR_MAP.put(LocalDate.class, LocalDateComparator.COMPARATOR);
        COMPARATOR_MAP.put(OffsetTime.class, OffsetTimeComparator.COMPARATOR);
        COMPARATOR_MAP.put(ZonedDateTime.class, ZonedDateTimeComparator.COMPARATOR);
        COMPARATOR_MAP.put(Temporal.class, TemporalComparator.COMPARATOR);
        COMPARATOR_MAP.put(TemporalAmount.class, TemporalAmountComparator.COMPARATOR);

        COMPARATOR_MAP.put(Duration.class, DefaultDurationComparator.COMPARATOR);

        ENDPOINT_MARKERS.add("(");
        ENDPOINT_MARKERS.add(")");
        ENDPOINT_MARKERS.add("[");
        ENDPOINT_MARKERS.add("]");
    }

    private static final Pattern STRING_RANGE = Pattern.compile("^\".*\"$");
    private static final Pattern NUMBER_RANGE = Pattern.compile("^[0-9]+.*$");
    private static final String DATE_FORMAT = "\\d{4}-\\d{2}-\\d{2}";
    private static final String TIME_FORMAT = "\\d{2}:\\d{2}:\\d{2}";
    private static final Pattern DATE_RANGE = Pattern.compile("^@\"" + DATE_FORMAT + "\"$" + "|^date\\(.*\\)$");
    private static final Pattern TIME_RANGE = Pattern.compile("^@\"" + TIME_FORMAT + "\"$" + "|^time\\(.*\\)$");
    private static final Pattern DATE_TIME_RANGE = Pattern.compile("^@\"" + DATE_FORMAT + "T" + TIME_FORMAT + "\"$" + "|^date and time\\(.*\\)$");

    public static final String YEAR_MONTH_DURATION = "P(\\d+Y)?(\\d+M)?";
    private static final Pattern YEARS_MONTHS_RANGE = Pattern.compile("^@\"" + YEAR_MONTH_DURATION + "\"$" + "|^duration\\(\"" + YEAR_MONTH_DURATION + "\"\\)$");

    public static final String DAY_TIME_DURATION = "P(\\d+D)?(T.*)?";
    private static final Pattern DAYS_TIME_RANGE = Pattern.compile("^@\"" + DAY_TIME_DURATION + "\"$" + "|^duration\\(\"" + DAY_TIME_DURATION + "\"\\)$");

    public static String[] extractRangeParts(String fromStr) {
        String[] result = new String[4];

        fromStr = fromStr.trim();
        int middle = fromStr.indexOf("..");
        if (middle != -1) {
            result[0] = fromStr.substring(0, 1);
            result[1] = fromStr.substring(1, middle);
            result[2] = fromStr.substring(middle + 2, fromStr.length() - 1);
            result[3] = fromStr.substring(fromStr.length() - 1);
        }
        return result;
    }

    private static String extractTemporalString(String str, List<String> allowedPrefixes) {
        if (StringUtils.isBlank(str)) {
            return str;
        }

        if (!startsWithPrefix(str, allowedPrefixes)) {
            throw new DMNRuntimeException(String.format("Incorrect endpoint literal '%s'", str));
        }

        int firstIndex = str.indexOf('"');
        int lastIndex = str.lastIndexOf('"');
        if (firstIndex != -1 && lastIndex != -1) {
            return str.substring(firstIndex + 1, lastIndex);
        }
        return null;
    }

    private static boolean startsWithPrefix(String str, List<String> allowedPrefixes) {
        for (String allowedPrefix : allowedPrefixes) {
            if (str.startsWith(allowedPrefix)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStringRange(String fromStr) {
        if (StringUtils.isBlank(fromStr)) {
            return false;
        }

        return STRING_RANGE.matcher(fromStr).matches();
    }

    public static boolean isNumberRange(String fromStr) {
        if (StringUtils.isBlank(fromStr)) {
            return false;
        }

        return NUMBER_RANGE.matcher(fromStr).matches();
    }

    public static boolean isDateRange(String fromStr) {
        if (StringUtils.isBlank(fromStr)) {
            return false;
        }

        return DATE_RANGE.matcher(fromStr).matches();
    }

    public static boolean isTimeRange(String fromStr) {
        if (StringUtils.isBlank(fromStr)) {
            return false;
        }

        return TIME_RANGE.matcher(fromStr).matches();
    }

    public static boolean isDateTimeRange(String fromStr) {
        if (StringUtils.isBlank(fromStr)) {
            return false;
        }

        return DATE_TIME_RANGE.matcher(fromStr).matches();
    }

    public static boolean isYearsMonthsRange(String fromStr) {
        if (StringUtils.isBlank(fromStr)) {
            return false;
        }

        return YEARS_MONTHS_RANGE.matcher(fromStr).matches();
    }

    public static boolean isDaysTimeRange(String fromStr) {
        if (StringUtils.isBlank(fromStr)) {
            return false;
        }

        return DAYS_TIME_RANGE.matcher(fromStr).matches();
    }

    @Override
    public Range<?> range(String from) {
        if (StringUtils.isBlank(from)) {
            return null;
        }

        // Extract the range parts
        String[] rangeParts = extractRangeParts(from);
        Boolean startIncluded = ENDPOINT_MARKERS.contains(rangeParts[0]) ? "[".equals(rangeParts[0]) : null;
        String startStr = rangeParts[1];
        Boolean endIncluded = ENDPOINT_MARKERS.contains(rangeParts[3]) ? "]".equals(rangeParts[3]) : null;
        String endStr = rangeParts[2];

        // Check range parts
        if (startIncluded == null || endIncluded == null) {
            throw new DMNRuntimeException(String.format("Incorrect range literal '%s'", from));
        }
        if (StringUtils.isBlank(startStr) && StringUtils.isBlank(endStr)) {
            throw new DMNRuntimeException(String.format("Incorrect range literal '%s'", from));
        }
        if (startIncluded && StringUtils.isBlank(startStr)) {
            throw new DMNRuntimeException(String.format("Illegal start endpoint in '%s'", from));
        }
        if (endIncluded && StringUtils.isBlank(endStr)) {
            throw new DMNRuntimeException(String.format("Illegal end endpoint in '%s'", from));
        }
        Pair<Object, String> start = literalValue(startStr);
        Pair<Object, String> end = literalValue(endStr);
        if (start != null && end !=  null) {
            if (!Objects.equals(start.getRight(), end.getRight()))  {
                throw new DMNRuntimeException(String.format("Endpoints with different types in range '%s'", from));
            }
            RelationalComparator<Object> rc = resolveComparator(start.getLeft());
            if (!rc.lessEqualThan(start.getLeft(), end.getLeft())) {
                throw new DMNRuntimeException(String.format("Endpoints must be ascending order '%s'", from));
            }
        } else if (start == null && end == null) {
            throw new DMNRuntimeException(String.format("Both endpoints are undefined in '%s'", from));
        }
        // Make range
        if (start == null) {
            return new Range<>(false, null, endIncluded, end.getLeft());
        } else if (end ==  null) {
            return new Range<>(startIncluded, start.getLeft(), false, null);
        } else {
            return new Range<>(startIncluded, start.getLeft(), endIncluded, end.getLeft());
        }
    }

    @Override
    public Boolean rangeContains(Range<?> range, Object point) {
        if (checkArguments(point, range)) {
            throw new DMNRuntimeException(String.format("Illegal operands '%s' %s", range, point));
        }

        String operator = range.getOperator();
        if (StringUtils.isBlank(operator)) {
            return includes(range, point);
        } else {
            RelationalComparator<Object> rc = resolveComparator(point);
            switch (operator) {
                case "=":
                    return rc.equalTo(point, range.getStart());
                case "!=":
                    return rc.notEqualTo(point, range.getStart());
                case "<":
                    return rc.lessThan(point, range.getEnd());
                case "<=":
                    return rc.lessEqualThan(point, range.getEnd());
                case ">":
                    return rc.greaterThan(point, range.getStart());
                case ">=":
                    return rc.greaterEqualThan(point, range.getStart());
                default:
                    throw new DMNRuntimeException(String.format("Illegal operator '%s'", operator));
            }
        }
    }

    private Pair<Object, String> literalValue(String str) {
        if (str != null) {
            str = str.trim().replace("\\\"", "\"");
        }

        if (StringUtils.isBlank(str)) {
            return null;
        } else if (isStringRange(str)) {
            return new Pair(str.substring(1, str.length() - 1), "string");
        } else if (isNumberRange(str)) {
            return new Pair(getFeelLib().number(str), "number");
        } else if (isDateRange(str)) {
            String temporalString = extractTemporalString(str, Arrays.asList("@\"", "date(\""));
            return new Pair(getFeelLib().date(temporalString), "date");
        } else if (isTimeRange(str)) {
            String temporalString = extractTemporalString(str, Arrays.asList("@\"", "time(\""));
            return new Pair(getFeelLib().time(temporalString), "time");
        } else if (isDateTimeRange(str)) {
            String temporalString = extractTemporalString(str, Arrays.asList("@\"", "date and time(\""));
            return new Pair(getFeelLib().dateAndTime(temporalString), "date and time");
        } else if (isYearsMonthsRange(str)) {
            String temporalString = extractTemporalString(str, Arrays.asList("@\"P", "duration(\"P"));
            return new Pair(getFeelLib().duration(temporalString), "years and months");
        } else if (isDaysTimeRange(str)) {
            String temporalString = extractTemporalString(str, Arrays.asList("@\"P", "duration(\"P"));
            return new Pair(getFeelLib().duration(temporalString), "days and time");
        }
        throw new DMNRuntimeException(String.format("Incorrect endpoint literal '%s'", str));
    }

    @Override
    public Boolean before(Object point1, Object point2) {
        if (checkArguments(point1, point2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // point1 < point2
        //
        RelationalComparator<Object> rc = resolveComparator(point1);
        return rc == null ? null : rc.lessThan(point1, point2);
    }

    @Override
    public Boolean before(Object point, Range<?> range) {
        if (checkArguments(point, range)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // point < range.start
        // or (point = range.start and not(range.start included) )
        //
        Object start = range.getStart();
        RelationalComparator<Object> rc = resolveComparator(point);
        return rc == null ? null : BOOLEAN_TYPE.booleanOr(
                rc.lessThan(point, start),
                BOOLEAN_TYPE.booleanAnd(rc.equalTo(point, start), !range.isStartIncluded())
        );
    }

    @Override
    public Boolean before(Range<?> range, Object point) {
        if (checkArguments(range, point)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range.end < point
        // or (range.end = point and not(range.end included) )
        //
        Object end = range.getEnd();
        RelationalComparator<Object> rc = resolveComparator(point);
        return rc == null ? null : BOOLEAN_TYPE.booleanOr(
                rc.lessThan(end, point),
                BOOLEAN_TYPE.booleanAnd(rc.equalTo(end, point), !range.isEndIncluded())
        );
    }

    @Override
    public Boolean before(Range<?> range1, Range<?> range2) {
        if (checkArguments(range1, range2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range1.end < range2.start
        // or (( not(range1.end included) or not(range2.start included)) and range1.end = range2.start)
        //
        Object start1 = range1.getStart();
        Object end1 = range1.getEnd();
        Object start2 = range2.getStart();
        RelationalComparator<Object> rc = resolveComparator(start1);
        return rc == null ? null : BOOLEAN_TYPE.booleanOr(
                rc.lessThan(end1, start2),
                BOOLEAN_TYPE.booleanAnd(
                        BOOLEAN_TYPE.booleanOr(!range1.isEndIncluded(), !range2.isStartIncluded()),
                        rc.equalTo(end1, start2)
                )
        );
    }

    @Override
    public Boolean after(Object point1, Object point2) {
        if (checkArguments(point1, point2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // point1 > point2
        //
        RelationalComparator rc = resolveComparator(point1);
        return rc == null ? null :  rc.greaterThan(point1, point2);
    }

    @Override
    public Boolean after(Object point, Range<?> range) {
        if (checkArguments(point, range)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // point > range.end
        // or (point = range.end and not(range.end included) )
        //
        Object end = range.getEnd();
        RelationalComparator<Object> rc = resolveComparator(point);
        return rc == null ? null : BOOLEAN_TYPE.booleanOr(
                rc.greaterThan(point, end),
                BOOLEAN_TYPE.booleanAnd(
                        rc.equalTo(point, end),
                        !range.isEndIncluded()
                )
        );
    }

    @Override
    public Boolean after(Range<?> range, Object point) {
        if (checkArguments(range, point)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range.start > point
        // or (range.start = point and not(range.start included) )
        //
        Object start = range.getStart();
        RelationalComparator<Object> rc = resolveComparator(start);
        return rc == null ? null : BOOLEAN_TYPE.booleanOr(
                rc.greaterThan(start, point),
                BOOLEAN_TYPE.booleanAnd(
                        rc.equalTo(start, point),
                        !range.isStartIncluded()
                )
        );
    }

    @Override
    public Boolean after(Range<?> range1, Range<?> range2) {
        if (checkArguments(range1, range2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range1.start > range2.end
        // or (( not(range1.start included) or not(range2.end included) ) and range1.start = range2.end)
        //
        Object start1 = range1.getStart();
        Object end2 = range2.getEnd();
        RelationalComparator<Object> rc = resolveComparator(start1);
        return rc == null ? null : BOOLEAN_TYPE.booleanOr(
                rc.greaterThan(start1, end2),
                BOOLEAN_TYPE.booleanAnd(
                        BOOLEAN_TYPE.booleanOr(!range1.isStartIncluded(), !range2.isEndIncluded()),
                        rc.equalTo(start1, end2)
                )
        );
    }

    @Override
    public Boolean meets(Range<?> range1, Range<?> range2) {
        if (checkArguments(range1, range2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range1.end included
        // and range2.start included
        // and range1.end = range2.start
        //
        Object start1 = range1.getStart();
        Object end1 = range1.getEnd();
        Object start2 = range2.getStart();
        RelationalComparator<Object> rc = resolveComparator(start1);
        return rc == null ? null : BOOLEAN_TYPE.booleanAnd(
                range1.isEndIncluded(),
                range2.isStartIncluded(),
                rc.equalTo(end1, start2)
        );
    }

    @Override
    public Boolean metBy(Range<?> range1, Range<?> range2) {
        if (checkArguments(range1, range2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range1.start included
        // and range2.end included
        // and range1.start = range2.end
        //
        Object start1 = range1.getStart();
        Object end2 = range2.getEnd();
        RelationalComparator<Object> rc = resolveComparator(start1);
        return rc == null ? null : BOOLEAN_TYPE.booleanAnd(
                range1.isStartIncluded(),
                range2.isEndIncluded(),
                rc.equalTo(start1, end2)
        );
    }

    @Override
    public Boolean overlaps(Range<?> range1, Range<?> range2) {
        if (checkArguments(range1, range2)) {
            return null;
        }

        //
        // CQL spec
        //
        // The overlaps operator returns true if the first interval overlaps the second.
        // More precisely,
        //      if the starting or ending point of either interval is in the other,
        //      or if the ending point of the first interval is greater than or equal to the starting point of the second interval,
        //      and the starting point of the first interval is less than or equal to the ending point of the second interval.
        //
        // DMN 1.3 spec
        // (range1.end > range2.start or (range1.end = range2.start and (range1.end included or range2.end included)))
        // and (range1.start < range2.end or (range1.start = range2.end and range1.start included and range2.end included))
        //
        // Corrected:
        // overlaps before(range1, range2)
        // or overlaps after(range1, range2)
        // or includes(range1, range2)
        // or includes(range2, range1)
        //
        return BOOLEAN_TYPE.booleanOr(
            overlapsBefore(range1, range2),
            overlapsAfter(range1, range2),
            includes(range1, range2),
            includes(range2, range1)
        );
    }

    @Override
    public Boolean overlapsBefore(Range<?> range1, Range<?> range2) {
        if (checkArguments(range1, range2)) {
            return null;
        }

        //
        // CQL spec:
        // The operator overlaps before returns true if the first interval overlaps the second and starts before it,
        // while the overlaps after operator returns true if the first interval overlaps the second and ends after it.
        //

        //
        // DMN 1.3 spec
        // range1 starts before range2
        // and range1.end is in range2
        //
        // (range1.start < range2.start or (range1.start = range2.start and range1.start included and range2.start included))
        // and (range1.end > range2.start or (range1.end = range2.start and range1.end included and range2.start included))
        // and (range1.end < range2.end  or (range1.end = range2.end and (not(range1.end included) or range2.end included )))
        //

        // Corrected to
        // (range1.start < range2.start or (range1.start = range2.start and (range1.start included and not(range2.start included))))
        // and (range1.end > range2.start or (range1.end = range2.start and range1.end included and range2.start included))
        // and (range1.end < range2.end  or (range1.end = range2.end and (not(range1.end included) or range2.end included )))
        Object start1 = range1.getStart();
        Object end1 = range1.getEnd();
        Object start2 = range2.getStart();
        Object end2 = range2.getEnd();
        RelationalComparator<Object> rc = resolveComparator(start1);
        if (rc == null) {
            return null;
        }

        Boolean range1StartsBeforeRange2 = BOOLEAN_TYPE.booleanOr(
                rc.lessThan(start1, start2),
                BOOLEAN_TYPE.booleanAnd(
                        rc.equalTo(start1, start2),
                        range1.isStartIncluded(),
                        !range2.isStartIncluded()
                )
        );
        Boolean range1EndIsInRange2 = BOOLEAN_TYPE.booleanAnd(
                BOOLEAN_TYPE.booleanOr(
                    rc.greaterThan(end1, start2),
                    BOOLEAN_TYPE.booleanAnd(
                        rc.equalTo(end1, start2),
                        range1.isEndIncluded(),
                        range2.isStartIncluded()
                    )
                ),
                BOOLEAN_TYPE.booleanOr(
                        rc.lessThan(end1, end2),
                        BOOLEAN_TYPE.booleanAnd(
                                rc.equalTo(end1, end2),
                                BOOLEAN_TYPE.booleanOr(
                                        !range1.isEndIncluded(),
                                        range2.isEndIncluded()
                                )
                        )
                )
        );
        return BOOLEAN_TYPE.booleanAnd(
                range1StartsBeforeRange2,
                range1EndIsInRange2
        );
    }

    @Override
    public Boolean overlapsAfter(Range<?> range1, Range<?> range2) {
        if (checkArguments(range1, range2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range2 starts before range1
        // range2.end is in range1
        //
        // (range2.start < range1.start or (range2.start = range1.start and range2.start included and not( range1.start included)))
        // and (range2.end > range1.start or (range2.end = range1.start and range2.end included and range1.start included ))
        // and (range2.end < range1.end or (range2.end = range1.end and (not(range2.end included) or range1.end included)))
        //
        Object start1 = range1.getStart();
        Object end1 = range1.getEnd();
        Object start2 = range2.getStart();
        Object end2 = range2.getEnd();
        RelationalComparator<Object> rc = resolveComparator(start1);
        if (rc == null) {
            return null;
        }

        Boolean range2StartsBeforeRange1 = BOOLEAN_TYPE.booleanOr(
                rc.lessThan(start2, start1),
                BOOLEAN_TYPE.booleanAnd(rc.equalTo(start2, start1), range2.isStartIncluded(), !range1.isStartIncluded())
        );
        Boolean range2EndIsInRange1 = BOOLEAN_TYPE.booleanAnd(
                BOOLEAN_TYPE.booleanOr(
                    rc.greaterThan(end2, start1),
                    BOOLEAN_TYPE.booleanAnd(
                            rc.equalTo(end2, start1),
                            range2.isEndIncluded(),
                            range1.isStartIncluded()
                    )
                ),
                BOOLEAN_TYPE.booleanOr(
                        rc.lessThan(end2, end1),
                        BOOLEAN_TYPE.booleanAnd(
                                rc.equalTo(end2, end1),
                                BOOLEAN_TYPE.booleanOr(
                                        !range2.isEndIncluded(),
                                        range1.isEndIncluded()
                                )
                        )
                )
        );
        return BOOLEAN_TYPE.booleanAnd(
                range2StartsBeforeRange1,
                range2EndIsInRange1
        );
    }

    @Override
    public Boolean finishes(Object point, Range<?> range) {
        if (checkArguments(point, range)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range.end included
        // and range.end = point
        //
        Object end = range.getEnd();
        RelationalComparator<Object> rc = resolveComparator(point);
        return rc == null ? null : BOOLEAN_TYPE.booleanAnd(
            range.isEndIncluded(),
            rc.equalTo(end, point)
        );
    }

    @Override
    public Boolean finishes(Range<?> range1, Range<?> range2) {
        if (checkArguments(range1, range2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range1.end included = range2.end included
        // and range1.end = range2.end
        // and (range1.start > range2.start or (range1.start = range2.start and (not(range1.start included) or range2.start included)))
        //
        Object start1 = range1.getStart();
        Object end1 = range1.getEnd();
        Object start2 = range2.getStart();
        Object end2 = range2.getEnd();
        RelationalComparator<Object> rc = resolveComparator(start1);
        return rc == null ? null : BOOLEAN_TYPE.booleanAnd(
                range1.isEndIncluded() == range2.isEndIncluded(),
                rc.equalTo(end1, end2),
                BOOLEAN_TYPE.booleanOr(
                        rc.greaterThan(start1, start2),
                        BOOLEAN_TYPE.booleanAnd(
                                rc.equalTo(start1, start2),
                                BOOLEAN_TYPE.booleanOr(!range1.isStartIncluded(), range2.isStartIncluded())
                        )
                )
        );
    }

    @Override
    public Boolean finishedBy(Range<?> range, Object point) {
        if (checkArguments(range, point)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range.end included
        // and range.end = point
        //
        Object start = range.getStart();
        Object end = range.getEnd();
        RelationalComparator<Object> rc = resolveComparator(start);
        return rc == null ? null : BOOLEAN_TYPE.booleanAnd(
            range.isEndIncluded(),
            rc.equalTo(end, point)
        );
    }

    @Override
    public Boolean finishedBy(Range<?> range1, Range<?> range2) {
        if (checkArguments(range1, range2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range1.end included = range2.end included
        // and range1.end = range2.end
        // and (range1.start < range2.start or (range1.start = range2.start and (range1.start included or not(range2.start included))))
        //
        Object start1 = range1.getStart();
        Object end1 = range1.getEnd();
        Object start2 = range2.getStart();
        Object end2 = range2.getEnd();
        RelationalComparator<Object> rc = resolveComparator(start1);
        return rc == null ? null : BOOLEAN_TYPE.booleanAnd(
                range1.isEndIncluded() == range2.isEndIncluded(),
                rc.equalTo(end1, end2),
                BOOLEAN_TYPE.booleanOr(
                        rc.lessThan(start1, start2),
                        BOOLEAN_TYPE.booleanAnd(
                                rc.equalTo(start1, start2),
                                BOOLEAN_TYPE.booleanOr(range1.isStartIncluded(), !range2.isStartIncluded())
                        )
                )
        );
    }

    @Override
    public Boolean includes(Range<?> range, Object point) {
        if (checkArguments(range, point)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // (range.start < point and range.end > point)
        // or (range.start = point and range.start included)
        // or (range.end = point and range.end included)
        //
        Object start = range.getStart();
        Object end = range.getEnd();
        RelationalComparator<Object> rc = resolveComparator(start);
        return rc == null ? null : BOOLEAN_TYPE.booleanOr(
            BOOLEAN_TYPE.booleanAnd(rc.lessThan(start, point), rc.greaterThan(end, point)),
            BOOLEAN_TYPE.booleanAnd(rc.equalTo(start, point), range.isStartIncluded()),
            BOOLEAN_TYPE.booleanAnd(rc.equalTo(end, point), range.isEndIncluded())
        );
    }

    @Override
    public Boolean includes(Range<?> range1, Range<?> range2) {
        if (checkArguments(range1, range2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // (range1.start < range2.start or (range1.start = range2.start and (range1.start included or not(range2.start included))))
        // and (range1.end > range2.end or (range1.end = range2.end and (range1.end included or not(range2.end included))))
        //
        Object start1 = range1.getStart();
        Object end1 = range1.getEnd();
        Object start2 = range2.getStart();
        Object end2 = range2.getEnd();
        RelationalComparator<Object> rc = resolveComparator(start1);
        return rc == null ? null : BOOLEAN_TYPE.booleanAnd(
            BOOLEAN_TYPE.booleanOr(
                    rc.lessThan(start1, start2),
                    BOOLEAN_TYPE.booleanAnd(
                            rc.equalTo(start1, start2),
                            BOOLEAN_TYPE.booleanOr(
                                    range1.isStartIncluded(),
                                    !range2.isStartIncluded()
                            )
                    )
            ),
            BOOLEAN_TYPE.booleanOr(
                    rc.greaterThan(end1, end2),
                    BOOLEAN_TYPE.booleanAnd(
                            rc.equalTo(end1, end2),
                            BOOLEAN_TYPE.booleanOr(
                                    range1.isEndIncluded(),
                                    !range2.isEndIncluded()
                            )
                    )
            )
        );
    }

    @Override
    public Boolean during(Object point, Range<?> range) {
        if (checkArguments(point, range)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // (range.start < point and range.end > point)
        // or (range.start = point and range.start included)
        // or (range.end = point and range.end included)
        //
        Object start = range.getStart();
        Object end = range.getEnd();
        RelationalComparator<Object> rc = resolveComparator(point);
        return rc == null ? null : BOOLEAN_TYPE.booleanOr(
                BOOLEAN_TYPE.booleanAnd(rc.lessThan(start, point), rc.greaterThan(end, point)),
                BOOLEAN_TYPE.booleanAnd(rc.equalTo(start, point), range.isStartIncluded()),
                BOOLEAN_TYPE.booleanAnd(rc.equalTo(end, point), range.isEndIncluded())
        );
    }

    @Override
    public Boolean during(Range<?> range1, Range<?> range2) {
        if (checkArguments(range1, range2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // (range2.start < range1.start or (range2.start = range1.start and (range2.start included or not(range1.start included))))
        // and (range2.end > range1.end or (range2.end = range1.end and (range2.end included or not(range1.end included))))
        //
        Object start1 = range1.getStart();
        Object end1 = range1.getEnd();
        Object start2 = range2.getStart();
        Object end2 = range2.getEnd();
        RelationalComparator<Object> rc = resolveComparator(start1);
        return rc == null ? null : BOOLEAN_TYPE.booleanAnd(
                BOOLEAN_TYPE.booleanOr(
                        rc.lessThan(start2, start1),
                        BOOLEAN_TYPE.booleanAnd(
                                rc.equalTo(start2, start1),
                                BOOLEAN_TYPE.booleanOr(range2.isStartIncluded(), !range1.isStartIncluded()))),
                BOOLEAN_TYPE.booleanOr(
                        rc.greaterThan(end2, end1),
                        BOOLEAN_TYPE.booleanAnd(
                                rc.equalTo(end2, end1),
                                BOOLEAN_TYPE.booleanOr(range2.isEndIncluded(), !range1.isEndIncluded())))
        );
    }

    @Override
    public Boolean starts(Object point, Range<?> range) {
        if (checkArguments(point, range)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range.start = point
        // and range.start included
        //
        Object start = range.getStart();
        RelationalComparator<Object> rc = resolveComparator(point);
        return rc == null ? null : BOOLEAN_TYPE.booleanAnd(
            rc.equalTo(start, point),
            range.isStartIncluded()
        );
    }

    @Override
    public Boolean starts(Range<?> range1, Range<?> range2) {
        if (checkArguments(range1, range2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range1.start = range2.start
        // and range1.start included = range2.start included
        // and (range1.end < range2.end or (range1.end = range2.end and (not(range1.end included) or range2.end included)))
        //
        Object start1 = range1.getStart();
        Object end1 = range1.getEnd();
        Object start2 = range2.getStart();
        Object end2 = range2.getEnd();
        RelationalComparator<Object> rc = resolveComparator(start1);
        return rc == null ? null : BOOLEAN_TYPE.booleanAnd(
            rc.equalTo(start1, start2),
            range1.isStartIncluded() == range2.isStartIncluded(),
            BOOLEAN_TYPE.booleanOr(
                    rc.lessThan(end1, end2),
                    BOOLEAN_TYPE.booleanAnd(
                            rc.equalTo(end1, end2),
                            BOOLEAN_TYPE.booleanOr(
                                   !range1.isEndIncluded(),
                                   range2.isEndIncluded()
                            )
                    )
            )
        );
    }

    @Override
    public Boolean startedBy(Range<?> range, Object point) {
        if (checkArguments(range, point)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range.start = point
        // and range.start included
        //
        Object start = range.getStart();
        RelationalComparator<Object> rc = resolveComparator(point);
        return rc == null ? null : BOOLEAN_TYPE.booleanAnd(
            rc.equalTo(start, point),
            range.isStartIncluded()
        );
    }

    @Override
    public Boolean startedBy(Range<?> range1, Range<?> range2) {
        if (checkArguments(range1, range2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range1.start = range2.start
        // and range1.start included = range2.start included
        // and (range2.end < range1.end or (range2.end = range1.end and (not(range2.end included) or range1.end included)))
        //
        Object start1 = range1.getStart();
        Object end1 = range1.getEnd();
        Object start2 = range2.getStart();
        Object end2 = range2.getEnd();
        RelationalComparator<Object> rc = resolveComparator(start1);
        return rc == null ? null : BOOLEAN_TYPE.booleanAnd(
                rc.equalTo(start1, start2),
                range1.isStartIncluded() == range2.isStartIncluded(),
                BOOLEAN_TYPE.booleanOr(
                        rc.lessThan(end2, end1),
                        BOOLEAN_TYPE.booleanAnd(
                                rc.equalTo(end2, end1),
                                BOOLEAN_TYPE.booleanOr(
                                        !range2.isEndIncluded(),
                                        range1.isEndIncluded()
                                )
                        )
                )
        );
    }

    @Override
    public Boolean coincides(Object point1, Object point2) {
        if (checkArguments(point1, point2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // point1 = point2
        //
        RelationalComparator<Object> rc = resolveComparator(point1);
        return rc == null ? null : rc.equalTo(point1, point2);
    }

    @Override
    public Boolean coincides(Range<?> range1, Range<?> range2) {
        if (checkArguments(range1, range2)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range1.start = range2.start
        // and range1.start included = range2.start included
        // and range1.end = range2.end
        // and range1.end included = range2.end included
        //
        Object start1 = range1.getStart();
        Object end1 = range1.getEnd();
        Object start2 = range2.getStart();
        Object end2 = range2.getEnd();
        RelationalComparator<Object> rc = resolveComparator(start1);
        return rc == null ? null : BOOLEAN_TYPE.booleanAnd(
            rc.equalTo(start1, start2),
            range1.isStartIncluded() == range2.isStartIncluded(),
            rc.equalTo(end1, end2),
            range1.isEndIncluded() == range2.isEndIncluded()
        );
    }

    private boolean checkArguments(Object arg1, Object arg2) {
        return arg1 == null || arg2 == null;
    }

    private RelationalComparator resolveComparator(Object point) {
        if (point == null) {
            return null;
        }

        Class<?> pointClass = point.getClass();
        RelationalComparator<?> relationalComparator = COMPARATOR_MAP.get(pointClass);
        if (relationalComparator == null) {
            // try instance of
            for (Map.Entry<Class<?>, RelationalComparator<?>> entry: COMPARATOR_MAP.entrySet()) {
                Class<?> key = entry.getKey();
                if (key.isAssignableFrom(pointClass)) {
                    relationalComparator = entry.getValue();
                    break;
                }
            }
        }
        return relationalComparator;
    }

    protected StandardFEELLib<?, ?, ?, ?, ?> getFeelLib() {
        return JavaTimeFEELLib.INSTANCE;
    }
}
