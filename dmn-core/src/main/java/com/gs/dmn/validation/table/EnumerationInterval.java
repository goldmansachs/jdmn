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

    public EnumerationInterval(int ruleIndex, int columnIndex, Input input) {
        super(ruleIndex, columnIndex, input, true, Bound.ZERO, false, (double) input.getAllowedValues().size());
    }

    public EnumerationInterval(int ruleIndex, int columnIndex, Input input, String value) {
        super(ruleIndex, columnIndex, input, true, (double) input.getAllowedValues().indexOf(value), false, (double) input.getAllowedValues().indexOf(value) + 1.0);
    }

    public EnumerationInterval(int ruleIndex, int columnIndex, Input input, boolean startIncluded, Number startValue, boolean endIncluded, Number endValue) {
        super(ruleIndex, columnIndex, input, startIncluded, startValue, endIncluded, endValue);
    }

    @Override
    public Interval copy() {
        return new EnumerationInterval(ruleIndex, columnIndex, input, lowerBound.isIncluded(), lowerBound.getValue(), upperBound.isIncluded(), upperBound.getValue());
    }

    @Override
    public String serialize() {
        List<String> enumValues = new ArrayList<>();
        int lowerIndex = this.lowerBound.getValue().intValue();
        int upperIndex = this.upperBound.getValue().intValue();
        for (int i=lowerIndex; i<upperIndex; i++) {
            enumValues.add(input.getAllowedValues().get(i));
        }
        return "{" + String.join(", ", enumValues) + "}";
    }

    @Override
    public String toString() {
        return "%s, %s".formatted(lowerBound, upperBound);
    }
}
