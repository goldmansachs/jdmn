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

public abstract class Interval {
    protected final int ruleIndex;
    protected final int columnIndex;
    protected final Bound lowerBound;
    protected final Bound upperBound;

    public Interval(int ruleIndex, int columnIndex, boolean openStart, Number startValue, boolean openEnd, Number endValue) {
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

    public abstract String serialize();
}
