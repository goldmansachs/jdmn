/**
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
package com.gs.dmn.runtime;

import java.util.Comparator;

public class PairComparator<T> implements Comparator<Pair<T, Integer>> {
    @Override
    public int compare(Pair<T, Integer> o1, Pair<T, Integer> o2) {
        Integer priority1 = o1.getRight();
        Integer priority2 = o2.getRight();
        if (priority1 == null && priority2 == null) {
            return 0;
        } else if (priority1 == null) {
            return 1;
        } else if (priority2 == null) {
            return -1;
        } else {
            return priority2 - priority1;
        }
    }
}
