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
    private final Input input;

    public EnumerationInterval(int ruleIndex, int columnIndex, Input input) {
        super(ruleIndex, columnIndex, false, Bound.ZERO, true, (double) input.getAllowedValues().size());
        this.input = input;
    }

    public EnumerationInterval(int ruleIndex, int columnIndex, Input input, String value) {
        super(ruleIndex, columnIndex, false, (double) input.getAllowedValues().indexOf(value), true, (double) input.getAllowedValues().indexOf(value) + 1.0);
        this.input = input;
    }

    public EnumerationInterval(int ruleIndex, int columnIndex, Input input, boolean openStart, Double startValue, boolean openEnd, Double endValue) {
        super(ruleIndex, columnIndex, openStart, startValue, openEnd, endValue);
        this.input = input;
    }

    @Override
    public String serialize() {
        List<String> enumValues = new ArrayList<>();
        int lowerIndex = this.lowerBound.getValue().intValue();
        int upperIndex = this.upperBound.getValue().intValue();
        for (int i=lowerIndex; i<upperIndex; i++) {
            enumValues.add(input.getAllowedValues().get(i));
        }
        return String.join(", ", enumValues);
    }

    @Override
    public String toString() {
        return String.format("%s, %s", lowerBound, upperBound);
    }
}
