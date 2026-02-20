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
package com.gs.dmn.runtime.coverage.trace;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ElementCoverageTrace {
    private String name;
    private int rulesCount;
    private Map<Integer, RuleCoverageTrace> indexToTrace = new ConcurrentHashMap<>();

    public ElementCoverageTrace() {
        this("", 0);
    }

    public ElementCoverageTrace(String name, int rulesCount) {
        this.name = name;
        this.rulesCount = rulesCount;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("rulesCount")
    public int getRulesCount() {
        return rulesCount;
    }

    @JsonProperty("rulesCount")
    public void setRulesCount(int rulesCount) {
        this.rulesCount = rulesCount;
    }

    @JsonProperty("ruleTraces")
    public List<RuleCoverageTrace> getRuleTraces() {
        return new java.util.ArrayList<>(indexToTrace.values());
    }

    @JsonProperty("ruleTraces")
    public void setRuleTraces(List<RuleCoverageTrace> ruleTraces) {
        for (RuleCoverageTrace ruleTrace : ruleTraces) {
            indexToTrace.put(ruleTrace.getRuleIndex(),ruleTrace);
        }
    }

    void addRuleTrace(int index) {
        indexToTrace.computeIfAbsent(index, RuleCoverageTrace::new);
    }

    void matchRuleTrace(int ruleIndex) {
        RuleCoverageTrace ruleCoverageTrace = indexToTrace.get(ruleIndex);
        if (ruleCoverageTrace != null) {
            ruleCoverageTrace.setMatched(true);
        } else {
            throw new IllegalStateException(String.format("Rule trace for rule index '%d' not found", ruleIndex));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementCoverageTrace that = (ElementCoverageTrace) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ElementCoverageTrace{name='" + name + "'}";
    }

    public void merge(ElementCoverageTrace elementTrace) {
        // Merge rule traces for the same element.
        elementTrace.getRuleTraces().stream().forEach(ruleTrace -> {
            if (indexToTrace.containsKey(ruleTrace.getRuleIndex())) {
                indexToTrace.get(ruleTrace.getRuleIndex()).merge(ruleTrace);
            } else {
                indexToTrace.put(ruleTrace.getRuleIndex(), ruleTrace);
            }
        });
    }
}

