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

import com.gs.dmn.runtime.annotation.HitPolicy;
import org.omg.spec.dmn._20180521.model.THitPolicy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuleOutputList {
    private final List<RuleOutput> matchedRuleResults = new ArrayList<>();

    public void add(RuleOutput result) {
        if (result != null && result.isMatched()) {
            this.matchedRuleResults.add(result);
        }
    }

    public List<RuleOutput> getMatchedRuleResults() {
        return this.matchedRuleResults;
    }

    public boolean noMatchedRules() {
        return matchedRuleResults.isEmpty();
    }

    public RuleOutput applySingle(HitPolicy hitPolicy) {
        THitPolicy tHitPolicy = getHitPolicy(hitPolicy);
        if (tHitPolicy == null) {
            tHitPolicy = THitPolicy.UNIQUE;
        }
        if (tHitPolicy == THitPolicy.UNIQUE) {
            if (this.matchedRuleResults.size() == 1) {
                return this.matchedRuleResults.get(0);
            } else {
                return null;
            }
        } else if (tHitPolicy == THitPolicy.ANY) {
            if (this.matchedRuleResults.size() > 0) {
                Set<RuleOutput> distinctResults = new HashSet<>(this.matchedRuleResults);
                if (distinctResults.size() == 1) {
                    return distinctResults.iterator().next();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else if (tHitPolicy == THitPolicy.FIRST) {
            if (this.matchedRuleResults.size() > 0) {
                return this.matchedRuleResults.get(0);
            } else {
                return null;
            }
        } else if (tHitPolicy == THitPolicy.PRIORITY) {
            if (this.matchedRuleResults.size() > 0) {
                List<RuleOutput> sortedRuleOutputs = sort(this.matchedRuleResults);
                return sortedRuleOutputs.get(0);
            } else {
                return null;
            }
        } else {
            throw new UnsupportedOperationException(String.format("Not supported single hit policy %s.", tHitPolicy.name()));
        }
    }

    public List<? extends RuleOutput> applyMultiple(HitPolicy hitPolicy) {
        List<RuleOutput> matchedRuleOutputs = this.getMatchedRuleResults();
        THitPolicy tHitPolicy = THitPolicy.fromValue(hitPolicy.value());
        if (tHitPolicy == THitPolicy.COLLECT) {
            return matchedRuleOutputs;
        } else if (tHitPolicy == THitPolicy.RULE_ORDER) {
            return matchedRuleOutputs;
        } else if (tHitPolicy == THitPolicy.OUTPUT_ORDER) {
            return sort(matchedRuleOutputs);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported multiple hit policy %s.", tHitPolicy.name()));
        }
    }

    private List<RuleOutput> sort(List<RuleOutput> matchedRuleOutputs) {
        if (matchedRuleOutputs.isEmpty()) {
            return matchedRuleOutputs;
        } else {
            RuleOutput result = matchedRuleOutputs.get(0);
            return result.sort(matchedRuleOutputs);
        }
    }

    private THitPolicy getHitPolicy(HitPolicy hitPolicy) {
        THitPolicy tHitPolicy = null;
        try {
            tHitPolicy = THitPolicy.fromValue(hitPolicy.name());
        } catch (Exception e) {
        }
        return tHitPolicy;
    }
}
