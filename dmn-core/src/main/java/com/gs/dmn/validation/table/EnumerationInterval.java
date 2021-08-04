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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnumerationInterval extends Interval {
    public static final List<String> BOOLEAN_ALLOWED_VALUES = Arrays.asList("false", "true");

    private final List<String> values = new ArrayList<>();

    public EnumerationInterval(int ruleIndex, int columnIndex, List<String> allowedValues) {
        super(ruleIndex, columnIndex, false, Bound.ZERO, true, (double) allowedValues.size());
        this.values.addAll(allowedValues);
    }

    public EnumerationInterval(int ruleIndex, int columnIndex, List<String> allowedValues, String value) {
        super(ruleIndex, columnIndex, false, (double) allowedValues.indexOf(value), true, (double) allowedValues.indexOf(value) + 1.0);
        this.values.add(value);
    }

    public EnumerationInterval(int ruleIndex, int columnIndex, boolean openStart, Double startValue, boolean openEnd, Double endValue) {
        super(ruleIndex, columnIndex, openStart, startValue, openEnd, endValue);
    }

    public List<String> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", lowerBound, upperBound);
    }
}
