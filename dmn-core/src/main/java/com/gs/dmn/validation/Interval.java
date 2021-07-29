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

public class Interval {
    private final int ruleIndex;
    private final int columnIndex;
    private final Bound lowerBound;
    private final Bound upperBound;

    public Interval(int ruleIndex, int columnIndex, boolean openStart, BigDecimal startValue, boolean openEnd, BigDecimal endValue) {
        this.ruleIndex = ruleIndex;
        this.columnIndex = columnIndex;
        lowerBound = new Bound(this, true, !openStart, startValue);
        upperBound = new Bound(this, false, !openEnd, endValue);
    }

    public int getRuleIndex() {
        return ruleIndex;
    }

    public Bound getLowerBound() {
        return lowerBound;
    }

    public Bound getUpperBound() {
        return upperBound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interval interval = (Interval) o;

        if (ruleIndex != interval.ruleIndex) return false;
        return columnIndex == interval.columnIndex;
    }

    @Override
    public int hashCode() {
        int result = ruleIndex;
        result = 31 * result + columnIndex;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", lowerBound, upperBound);
    }
}
