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

import java.util.*;
import java.util.stream.Collectors;

public class RuleGroup {
    public static Comparator<RuleGroup> COMPARATOR = (o1, o2) -> {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) {
            return 1;
        } else if (o2 == null) {
            return -1;
        } else {
            return o1.min - o2.min;
        }
    };

    private final List<Integer> ruleIndexes;
    private int min;

    public RuleGroup() {
        this.ruleIndexes = new ArrayList<>();
        this.min = -1;
    }

    public RuleGroup(List<Integer> ruleIndexes) {
        this.ruleIndexes = ruleIndexes;
        this.min = -1;
        for(int index: ruleIndexes) {
            if (min == -1) {
                this.min = index;
            } else if (index < min) {
                this.min = index;
            }
        }
    }

    public List<Integer> getRuleIndexes() {
        return ruleIndexes;
    }

    public boolean isEmpty() {
        return ruleIndexes.isEmpty();
    }

    public String serialize() {
        return ruleIndexes.stream().sorted().map(i -> i + 1).collect(Collectors.toList()).toString();
    }

    public RuleGroup sort() {
        this.ruleIndexes.sort(Integer::compareTo);
        return this;
    }

    public RuleGroup minus(Collection<Integer> nodes) {
        List<Integer> indexes = new ArrayList<>(this.ruleIndexes);
        if (nodes != null) {
            indexes.removeAll(nodes);
        }
        return new RuleGroup(indexes);
    }

    public RuleGroup minus(Integer node) {
        List<Integer> indexes = new ArrayList<>(this.ruleIndexes);
        if (node != null) {
            indexes.remove(node);
        }
        return new RuleGroup(indexes);
    }

    public RuleGroup union(int node) {
        List<Integer> indexes = new ArrayList<>(this.ruleIndexes);
        if (!indexes.contains(node)) {
            indexes.add(node);
        }
        return new RuleGroup(indexes);
    }

    public RuleGroup intersect(Collection<Integer> nodes) {
        List<Integer> indexes = new ArrayList<>();
        if (nodes != null) {
            for (int node: nodes) {
                if (this.ruleIndexes.contains(node)) {
                    indexes.add(node);
                }
            }
        }
        return new RuleGroup(indexes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleGroup ruleGroup = (RuleGroup) o;
        return min == ruleGroup.min && Objects.equals(ruleIndexes, ruleGroup.ruleIndexes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleIndexes, min);
    }

    @Override
    public String toString() {
        return ruleIndexes.toString();
    }
}
