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
package com.gs.dmn.runtime.interpreter;

import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.RuleOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InterpretedRuleOutput extends RuleOutput {
    private final Object result;

    public InterpretedRuleOutput(boolean matched, Object result) {
        super(matched);
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public List<RuleOutput> sort(List<RuleOutput> matchedResults) {
        if (matchedResults == null || matchedResults.isEmpty()) {
            return matchedResults;
        }
        Object listElement = ((InterpretedRuleOutput) matchedResults.get(0)).getResult();
        if (listElement instanceof Pair) {
            List<com.gs.dmn.runtime.Pair<Object, Integer>> decisionPairs = new ArrayList<>();
            matchedResults.forEach(matchedResult -> {
                InterpretedRuleOutput interpretedRule = (InterpretedRuleOutput) matchedResult;
                decisionPairs.add((Pair) interpretedRule.getResult());
            });
            decisionPairs.sort(new com.gs.dmn.runtime.PairComparator());
            // Create new list
            List<InterpretedRuleOutput> newList = new ArrayList<>();
            for (int i = 0; i < matchedResults.size(); i++) {
                newList.add(new InterpretedRuleOutput(true, decisionPairs.get(i)));
            }
            return (List) newList;
        } else if (listElement instanceof Context) {
            Context skeleton = (Context) listElement;
            // Create new list
            List<InterpretedRuleOutput> newList = new ArrayList<>();
            for (int i = 0; i < matchedResults.size(); i++) {
                newList.add(new InterpretedRuleOutput(true, new Context()));
            }
            // Sort all outputs and add them to new list
            for (Object key : skeleton.keySet()) {
                // Sort output 'key'
                List<com.gs.dmn.runtime.Pair<Object, Integer>> decisionPairs = new ArrayList<>();
                matchedResults.forEach(matchedResult -> {
                    InterpretedRuleOutput interpretedRule = (InterpretedRuleOutput) matchedResult;
                    decisionPairs.add((Pair) ((Context) interpretedRule.getResult()).get(key));
                });
                decisionPairs.sort(new com.gs.dmn.runtime.PairComparator());
                // Add them to new list
                for (int i = 0; i < decisionPairs.size(); i++) {
                    ((Context) newList.get(i).getResult()).put(key, decisionPairs.get(i));
                }
            }
            return (List) newList;
        } else {
            return matchedResults;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InterpretedRuleOutput)) return false;
        InterpretedRuleOutput that = (InterpretedRuleOutput) o;
        return matched == that.matched &&
                Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matched, result);
    }

    @Override
    public String toString() {
        return "(matched=" + isMatched() + String.format(", result='%s'", result) + ")";
    }
}
