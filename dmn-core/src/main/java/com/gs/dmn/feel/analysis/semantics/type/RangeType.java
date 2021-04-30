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
package com.gs.dmn.feel.analysis.semantics.type;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;
import static com.gs.dmn.feel.analysis.semantics.type.BooleanType.BOOLEAN;
import static com.gs.dmn.feel.analysis.semantics.type.ComparableDataType.COMPARABLE;
import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public class RangeType extends Type {
    public static final Type NUMBER_RANGE = new RangeType(NUMBER);
    public static final Type STRING_RANGE = new RangeType(STRING);
    public static final Type DATE_RANGE = new RangeType(DATE);
    public static final Type TIME_RANGE = new RangeType(TIME);
    public static final Type DATE_AND_TIME_RANGE = new RangeType(DATE_AND_TIME);
    public static final Type YEARS_AND_MONTHS_DURATION_RANGE = new RangeType(YEARS_AND_MONTHS_DURATION);
    public static final Type DAYS_AND_TIME_DURATION_RANGE = new RangeType(DAYS_AND_TIME_DURATION);
    public static final Type COMPARABLE_RANGE = new RangeType(COMPARABLE);
    public static final Type ANY_RANGE = new RangeType(ANY);

    private final Type type;

    public RangeType() {
        this(NUMBER);
    }

    public RangeType(Type type) {
        if (Type.isNull(type)) {
            type = NUMBER;
        }
        this.type = type;
    }

    public Type getRangeType() {
        return type;
    }

    public Type getMemberType(String member) {
        if ("start".equals(member) || "end".equals(member)) {
            return this.type;
        } else if ("start included".equals(member) || "end included".equals(member)) {
            return BOOLEAN;
        } else {
            return null;
        }
    }

    @Override
    protected boolean equivalentTo(Type other) {
        return other instanceof RangeType
                && Type.equivalentTo(this.type, ((RangeType) other).type);
    }

    @Override
    protected boolean conformsTo(Type other) {
        return other instanceof RangeType
                && Type.conformsTo(this.type, ((RangeType) other).type);
    }

    @Override
    public boolean isValid() {
        return !Type.isNull(type) && type.isValid();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RangeType rangeType = (RangeType) o;

        return type.equals(rangeType.type);

    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public String toString() {
        return String.format("RangeType(%s)", type.toString());
    }
}
