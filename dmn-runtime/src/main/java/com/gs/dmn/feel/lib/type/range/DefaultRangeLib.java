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

import com.gs.dmn.feel.lib.type.BooleanType;
import com.gs.dmn.feel.lib.type.ComparableComparator;
import com.gs.dmn.feel.lib.type.RelationalComparator;
import com.gs.dmn.feel.lib.type.logic.DefaultBooleanType;
import com.gs.dmn.runtime.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

//
// Allen's algebra https://en.wikipedia.org/wiki/Allen%27s_interval_algebra
//
// CQL https://cql.hl7.org/09-b-cqlreference.html#interval-operators-3
//
public class DefaultRangeLib implements RangeLib {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRangeLib.class);

    private static final BooleanType BOOLEAN_TYPE = new DefaultBooleanType(LOGGER);
    private static final Map<Class<?>, RelationalComparator> COMPARATOR_MAP = new LinkedHashMap<>();

    static {
        COMPARATOR_MAP.put(BigDecimal.class, new ComparableComparator<BigDecimal>());
        COMPARATOR_MAP.put(Double.class, new ComparableComparator<BigDecimal>());
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(point1.getClass());
        return rc.lessThan(point1, point2);
    }

    @Override
    public Boolean before(Object point, Range range) {
        if (checkArguments(point, range)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // point < range.start
        // or (point = range.start and not(range.start included) )
        //
        Object start = range.getStart();
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(point.getClass());
        return BOOLEAN_TYPE.booleanOr(
                rc.lessThan(point, start),
                BOOLEAN_TYPE.booleanAnd(rc.equalTo(point, start), !range.isStartIncluded())
        );
    }

    @Override
    public Boolean before(Range range, Object point) {
        if (checkArguments(range, point)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range.end < point
        // or (range.end = point and not(range.end included) )
        //
        Object end = range.getEnd();
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(point.getClass());
        return BOOLEAN_TYPE.booleanOr(
                rc.lessThan(end, point),
                BOOLEAN_TYPE.booleanAnd(rc.equalTo(end, point), !range.isEndIncluded())
        );
    }

    @Override
    public Boolean before(Range range1, Range range2) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start1.getClass());
        return BOOLEAN_TYPE.booleanOr(
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
        RelationalComparator relationalComparator = COMPARATOR_MAP.get(point1.getClass());
        return relationalComparator.greaterThan(point1, point2);
    }

    @Override
    public Boolean after(Object point, Range range) {
        if (checkArguments(point, range)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // point > range.end
        // or (point = range.end and not(range.end included) )
        //
        Object end = range.getEnd();
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(point.getClass());
        return BOOLEAN_TYPE.booleanOr(
                rc.greaterThan(point, end),
                BOOLEAN_TYPE.booleanAnd(
                        rc.equalTo(point, end),
                        !range.isEndIncluded()
                )
        );
    }

    @Override
    public Boolean after(Range range, Object point) {
        if (checkArguments(range, point)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range.start > point
        // or (range.start = point and not(range.start included) )
        //
        Object start = range.getStart();
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start.getClass());
        return BOOLEAN_TYPE.booleanOr(
                rc.greaterThan(start, point),
                BOOLEAN_TYPE.booleanAnd(
                        rc.equalTo(start, point),
                        !range.isStartIncluded()
                )
        );
    }

    @Override
    public Boolean after(Range range1, Range range2) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start1.getClass());
        return BOOLEAN_TYPE.booleanOr(
                rc.greaterThan(start1, end2),
                BOOLEAN_TYPE.booleanAnd(
                        BOOLEAN_TYPE.booleanOr(!range1.isStartIncluded(), !range2.isEndIncluded()),
                        rc.equalTo(start1, end2)
                )
        );
    }

    @Override
    public Boolean meets(Range range1, Range range2) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start1.getClass());
        return BOOLEAN_TYPE.booleanAnd(
                range1.isEndIncluded(),
                range2.isStartIncluded(),
                rc.equalTo(end1, start2)
        );
    }

    @Override
    public Boolean metBy(Range range1, Range range2) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start1.getClass());
        return BOOLEAN_TYPE.booleanAnd(
                range1.isStartIncluded(),
                range2.isEndIncluded(),
                rc.equalTo(start1, end2)
        );
    }

    @Override
    public Boolean overlaps(Range range1, Range range2) {
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
    public Boolean overlapsBefore(Range range1, Range range2) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start1.getClass());
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
    public Boolean overlapsAfter(Range range1, Range range2) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start1.getClass());
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
    public Boolean finishes(Object point, Range range) {
        if (checkArguments(point, range)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range.end included
        // and range.end = point
        //
        Object end = range.getEnd();
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(point.getClass());
        return BOOLEAN_TYPE.booleanAnd(
            range.isEndIncluded(),
            rc.equalTo(end, point)
        );
    }

    @Override
    public Boolean finishes(Range range1, Range range2) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start1.getClass());
        return BOOLEAN_TYPE.booleanAnd(
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
    public Boolean finishedBy(Range range, Object point) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start.getClass());
        return BOOLEAN_TYPE.booleanAnd(
            range.isEndIncluded(),
            rc.equalTo(end, point)
        );
    }

    @Override
    public Boolean finishedBy(Range range1, Range range2) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start1.getClass());
        return BOOLEAN_TYPE.booleanAnd(
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
    public Boolean includes(Range range, Object point) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start.getClass());
        return BOOLEAN_TYPE.booleanOr(
            BOOLEAN_TYPE.booleanAnd(rc.lessThan(start, point), rc.greaterThan(end, point)),
            BOOLEAN_TYPE.booleanAnd(rc.equalTo(start, point), range.isStartIncluded()),
            BOOLEAN_TYPE.booleanAnd(rc.equalTo(end, point), range.isEndIncluded())
        );
    }

    @Override
    public Boolean includes(Range range1, Range range2) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start1.getClass());
        return BOOLEAN_TYPE.booleanAnd(
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
    public Boolean during(Object point, Range range) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(point.getClass());
        return BOOLEAN_TYPE.booleanOr(
                BOOLEAN_TYPE.booleanAnd(rc.lessThan(start, point), rc.greaterThan(end, point)),
                BOOLEAN_TYPE.booleanAnd(rc.equalTo(start, point), range.isStartIncluded()),
                BOOLEAN_TYPE.booleanAnd(rc.equalTo(end, point), range.isEndIncluded())
        );
    }

    @Override
    public Boolean during(Range range1, Range range2) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start1.getClass());
        return BOOLEAN_TYPE.booleanAnd(
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
    public Boolean starts(Object point, Range range) {
        if (checkArguments(point, range)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range.start = point
        // and range.start included
        //
        Object start = range.getStart();
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(point.getClass());
        return BOOLEAN_TYPE.booleanAnd(
            rc.equalTo(start, point),
            range.isStartIncluded()
        );
    }

    @Override
    public Boolean starts(Range range1, Range range2) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start1.getClass());
        return BOOLEAN_TYPE.booleanAnd(
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
    public Boolean startedBy(Range range, Object point) {
        if (checkArguments(range, point)) {
            return null;
        }

        //
        // DMN 1.3 spec
        // range.start = point
        // and range.start included
        //
        Object start = range.getStart();
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(point.getClass());
        return BOOLEAN_TYPE.booleanAnd(
            rc.equalTo(start, point),
            range.isStartIncluded()
        );
    }

    @Override
    public Boolean startedBy(Range range1, Range range2) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start1.getClass());
        return BOOLEAN_TYPE.booleanAnd(
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(point1.getClass());
        return rc.equalTo(point1, point2);
    }

    @Override
    public Boolean coincides(Range range1, Range range2) {
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
        RelationalComparator<Object> rc = COMPARATOR_MAP.get(start1.getClass());
        return BOOLEAN_TYPE.booleanAnd(
            rc.equalTo(start1, start2),
            range1.isStartIncluded() == range2.isStartIncluded(),
            rc.equalTo(end1, end2),
            range1.isEndIncluded() == range2.isEndIncluded()
        );
    }

    private boolean checkArguments(Object point, Object range) {
        return point == null || range == null;
    }
}
