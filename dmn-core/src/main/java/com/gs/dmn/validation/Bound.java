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
package com.gs.dmn.validation;

import java.util.Comparator;
import java.util.Objects;

public class Bound {
    public static Comparator<Bound> COMPARATOR = (o1, o2) -> {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) {
            return 1;
        } else if (o2 == null) {
            return -1;
        } else {
            if (o1.value == null && o2.value == null) {
                return 0;
            } else if (o1.value == null) {
                return 1;
            } else if (o2.value == null) {
                return -1;
            } else {
                double diff = o1.compareValue() - o2.compareValue();
                if (diff < 0) {
                    return -1;
                } else if (diff > 0) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    };

    private final Interval interval;
    private final boolean isLowerBound;
    private final boolean isIncluded;
    private final Number value;

    public Bound(Interval interval, boolean lowerBound, boolean isIncluded, Object value) {
        this.interval = interval;
        this.isLowerBound = lowerBound;
        this.isIncluded = isIncluded;
        if (!(value instanceof Number)) {
            throw new IllegalArgumentException(String.format("Unexpected value in bound '%s'", value));
        } else {
            this.value = (Number) value;
        }
    }

    public Interval getInterval() {
        return interval;
    }

    public boolean isLowerBound() {
        return isLowerBound;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bound bound = (Bound) o;
        return isLowerBound == bound.isLowerBound && isIncluded == bound.isIncluded && Objects.equals(interval, bound.interval) && Objects.equals(value, bound.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interval, isLowerBound, isIncluded, value);
    }

    @Override
    public String toString() {
        if (isLowerBound) {
            return String.format("%s%s", isIncluded ? "[" : "(", value);
        } else {
            return String.format("%s%s", value, isIncluded ? "]" : ")");
        }
    }

    private double compareValue() {
        if (isIncluded) {
            return this.value.doubleValue();
        } else if (isLowerBound) {
            return this.value.doubleValue() + BoundList.DELTA.doubleValue();
        } else {
            return this.value.doubleValue() - BoundList.DELTA.doubleValue();
        }
    }
}
