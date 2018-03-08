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

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PairComparatorTest {
    @Test
    public void testSort() {
        List<Pair<String, Integer>> resultList = new ArrayList<>();
        resultList.add(new Pair<>("1", null));
        resultList.add(new Pair<>("2", null));
        resultList.add(new Pair<>("4", 1));
        resultList.add(new Pair<>("3", null));
        resultList.add(new Pair<>("5", 2));

        resultList.sort(new PairComparator<>());

        assertEquals("5", resultList.get(0).getLeft());
        assertEquals("4", resultList.get(1).getLeft());
        assertEquals("1", resultList.get(2).getLeft());
        assertEquals("2", resultList.get(3).getLeft());
        assertEquals("3", resultList.get(4).getLeft());
    }
}