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

public class NumericInterval extends Interval {
    public NumericInterval(int ruleIndex, int columnIndex, Input input) {
        super(ruleIndex, columnIndex, input, true, Bound.MINUS_INFINITY, true, Bound.PLUS_INFINITY);
    }

    public NumericInterval(int ruleIndex, int columnIndex, Input input, boolean startIncluded, Number startValue, boolean endIncluded, Number endValue) {
        super(ruleIndex, columnIndex, input, startIncluded, startValue, endIncluded, endValue);
    }

    @Override
    public Interval copy() {
        return new NumericInterval(ruleIndex, columnIndex, input, lowerBound.isIncluded(), lowerBound.getValue(), upperBound.isIncluded(), upperBound.getValue());
    }

    @Override
    public String serialize() {
        return String.format("%s, %s", lowerBound, upperBound);
    }

    @Override
    public String toString() {
        return String.format("%s, %s", lowerBound, upperBound);
    }
}
