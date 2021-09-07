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
package com.gs.dmn.validation.table;

import com.gs.dmn.runtime.DMNRuntimeException;

public abstract class Interval {
    public static boolean sameEndValues(Interval i1, Interval i2) {
        if (i1 == null || i2 == null) {
            throw new DMNRuntimeException(String.format("Unexpected null interval '%s' or '%s'", i1, i2));
        }

        return Bound.sameEnd(i1.lowerBound, i2.lowerBound)
                && Bound.sameEnd(i1.upperBound, i2.upperBound);
    }

    public static boolean areAdjacent(Interval i1, Interval i2) {
        if (i1 == null || i2 == null) {
            return false;
        }
        return
                Bound.areAdjacent(i1.upperBound, i2.lowerBound) ||
                Bound.areAdjacent(i2.upperBound, i1.lowerBound)
        ;
    }

    protected final int ruleIndex;
    protected final int columnIndex;
    protected final Bound lowerBound;
    protected final Bound upperBound;
    protected final Input input;

    public Interval(int ruleIndex, int columnIndex, Input input, boolean startIncluded, Number startValue, boolean endIncluded, Number endValue) {
        this.ruleIndex = ruleIndex;
        this.columnIndex = columnIndex;
        this.input = input;
        lowerBound = new Bound(this, true, startIncluded, startValue);
        upperBound = new Bound(this, false, endIncluded, endValue);
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

    public abstract String serialize();
}
