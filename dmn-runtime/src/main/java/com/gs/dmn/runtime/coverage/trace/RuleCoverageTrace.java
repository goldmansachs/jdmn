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

import java.util.Objects;

public class RuleCoverageTrace {
    private int ruleIndex;
    private boolean matched;

    public RuleCoverageTrace() {
        this(0);
    }

    public RuleCoverageTrace(int ruleIndex) {
        this.ruleIndex = ruleIndex;
    }

    @JsonProperty("ruleIndex")
    public int getRuleIndex() {
        return ruleIndex;
    }

    @JsonProperty("ruleIndex")
    public void setRuleIndex(int ruleIndex) {
        this.ruleIndex = ruleIndex;
    }

    @JsonProperty("matched")
    public boolean isMatched() {
        return matched;
    }

    @JsonProperty("matched")
    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleCoverageTrace that = (RuleCoverageTrace) o;
        return ruleIndex == that.ruleIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleIndex);
    }

    @Override
    public String toString() {
        return "RuleCoverageTrace{ruleIndex=" + ruleIndex + "}";
    }

    public void merge(RuleCoverageTrace ruleTrace) {
        // Merge rule traces for the same rule.
        this.matched = this.matched || ruleTrace.isMatched();
    }
}

