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

import java.math.BigDecimal;

public class Bound implements Comparable<Bound> {
    private final Interval interval;
    private final boolean isLowerBound;
    private final boolean isIncluded;
    private final Object value;

    public Bound(Interval interval, boolean lowerBound, boolean isIncluded, Object value) {
        this.interval = interval;
        this.isLowerBound = lowerBound;
        this.isIncluded = isIncluded;
        this.value = value;
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
    public int compareTo(Bound other) {
        if (other == null || other.value == null) {
            return -1;
        } else {
            if (this.value instanceof BigDecimal && other.value instanceof BigDecimal) {
                return ((BigDecimal) value).compareTo((BigDecimal) other.value);
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bound bound = (Bound) o;

        if (isIncluded != bound.isIncluded) return false;
        if (isLowerBound != bound.isLowerBound) return false;
        return value != null ? value.equals(bound.value) : bound.value == null;
    }

    @Override
    public int hashCode() {
        int result = 0 + (isIncluded ? 1 : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (isLowerBound ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        if (isLowerBound) {
            return String.format("%s%s", isIncluded ? "[" : "(", value);
        } else {
            return String.format("%s%s", value, isIncluded ? "]" : ")");
        }
    }
}
