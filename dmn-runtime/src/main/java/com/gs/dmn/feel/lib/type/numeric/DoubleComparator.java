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
package com.gs.dmn.feel.lib.type.numeric;

import com.gs.dmn.feel.lib.type.ComparableComparator;

public class DoubleComparator extends ComparableComparator<Double> {
    public DoubleComparator() {
    }

    @Override
    protected int compareTo(Comparable<Double> first, Comparable<Double> second) {
        if (((Double) first) == 0 && ((Double) second) == -0 || ((Double) second) == 0 && ((Double) first) == -0) {
            return 0;
        } else {
            return first.compareTo((Double) second);
        }
    }
}
