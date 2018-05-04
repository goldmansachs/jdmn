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
import org.junit.Test;
import org.omg.spec.dmn._20180521.model.THitPolicy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RuleOutputListTest {
    public static final DefaultSimpleOutput STRING_1 = new DefaultSimpleOutput("value1", 2);
    public static final DefaultSimpleOutput STRING_2 = new DefaultSimpleOutput("value2", 1);

    public static final DefaultCompoundOutput STRING_LIST_1 = new DefaultCompoundOutput(Arrays.asList("value1"), 2);
    public static final DefaultCompoundOutput STRING_LIST_2 = new DefaultCompoundOutput(Arrays.asList("value2"), 1);

    private final HitPolicy UNIQUE = HitPolicy.fromValue(THitPolicy.UNIQUE.value());
    private final HitPolicy FIRST = HitPolicy.fromValue(THitPolicy.FIRST.value());
    private final HitPolicy PRIORITY = HitPolicy.fromValue(THitPolicy.PRIORITY.value());
    private final HitPolicy ANY = HitPolicy.fromValue(THitPolicy.ANY.value());

    private final HitPolicy COLLECT = HitPolicy.fromValue(THitPolicy.COLLECT.value());
    private final HitPolicy RULE_ORDER = HitPolicy.fromValue(THitPolicy.RULE_ORDER.value());
    private final HitPolicy OUTPUT_ORDER = HitPolicy.fromValue(THitPolicy.OUTPUT_ORDER.value());

    @Test
    public void testApplyUnique() {
        assertEquals(STRING_1, makeRuleResultList(STRING_1).applySingle(UNIQUE));
        assertEquals(null, makeRuleResultList(STRING_1, STRING_1).applySingle(UNIQUE));
        assertEquals(null, makeRuleResultList(STRING_LIST_1, STRING_LIST_1).applySingle(UNIQUE));

        assertEquals(null, makeRuleResultList().applySingle(UNIQUE));
    }

    @Test
    public void testApplyFirst() {
        assertEquals(STRING_1, makeRuleResultList(STRING_1, STRING_1).applySingle(FIRST));
        assertEquals(STRING_2, makeRuleResultList(STRING_2, STRING_1).applySingle(FIRST));
        assertEquals(STRING_LIST_2, makeRuleResultList(STRING_LIST_2, STRING_LIST_1).applySingle(FIRST));

        assertEquals(null, makeRuleResultList().applySingle(FIRST));
    }

    @Test
    public void testApplyPriority() {
        assertEquals(STRING_1.getOutput(),  ((DefaultSimpleOutput)makeRuleResultList(STRING_2, STRING_1).applySingle(PRIORITY)).getOutput());

        assertEquals(null, makeRuleResultList().applySingle(PRIORITY));
    }

    @Test
    public void testApplyAny() {
        assertEquals(STRING_1, makeRuleResultList(STRING_1, STRING_1).applySingle(ANY));

        assertEquals(null, makeRuleResultList().applySingle(ANY));
    }

    @Test
    public void testApplyCollect() {
        assertEquals("[value1, value2]", makeRuleResultList(STRING_1, STRING_2).applyMultiple(COLLECT).toString());
    }

    @Test
    public void testApplyRuleOrder() {
        assertEquals("[value1, value2]", makeRuleResultList(STRING_1, STRING_2).applyMultiple(RULE_ORDER).toString());
    }

    @Test
    public void testApplyOutputOrder() {
        assertEquals("[value1, value2]", makeRuleResultList(STRING_1, STRING_2).applyMultiple(OUTPUT_ORDER).toString());
    }

    private RuleOutputList makeRuleResultList(RuleOutput... values) {
        RuleOutputList results = new RuleOutputList();
        for(RuleOutput value: values) {
            results.add(value);
        }
        return results;
    }
}

class DefaultSimpleOutput extends RuleOutput {
    private final String output;
    private final Integer priority;

    public DefaultSimpleOutput(String output, Integer priority) {
        super(true);
        this.output = output;
        this.priority = priority;
    }

    public String getOutput() {
        return output;
    }

    public Integer getPriority() {
        return priority;
    }

    @Override
    public List<RuleOutput> sort(List<RuleOutput> results) {
        List<Pair<String, Integer>> outputPairs = new ArrayList<>();
        results.forEach(decisionOutputRuleResult -> {
            String output = ((DefaultSimpleOutput)decisionOutputRuleResult).getOutput();
            Integer priority = ((DefaultSimpleOutput)decisionOutputRuleResult).getPriority();
            Pair<String, Integer> pair = new Pair(output, priority);
            outputPairs.add(pair);
        });
        outputPairs.sort(new PairComparator());

        List<RuleOutput> result_ = new ArrayList<>();
        for(int i=0; i<results.size(); i++) {
            DefaultSimpleOutput output = new DefaultSimpleOutput(outputPairs.get(i).getLeft(), outputPairs.get(i).getRight());
            result_.add(output);
        }
        return result_;
    }

    @Override
    public String toString() {
        return output;
    }

}

class DefaultCompoundOutput extends RuleOutput {
    private final List<String> output;
    private final Integer priority;

    public DefaultCompoundOutput(List<String> output, Integer priority) {
        super(true);
        this.output = output;
        this.priority = priority;
    }

    public List<String> getOutput() {
        return output;
    }

    public Integer getPriority() {
        return priority;
    }

    @Override
    public List<RuleOutput> sort(List<RuleOutput> results) {
        List<Pair<List<String>, Integer>> outputPairs = new ArrayList<>();
        results.forEach(ruleResult -> {
            List<String> output1 = ((DefaultCompoundOutput)ruleResult).getOutput();
            Integer priority1 = ((DefaultCompoundOutput)ruleResult).getPriority();
            Pair<List<String>, Integer> pair = new Pair(output1, priority1);
            outputPairs.add(pair);
        });
        outputPairs.sort(new PairComparator());

        List<RuleOutput> result_ = new ArrayList<>();
        for(int i=0; i<results.size(); i++) {
            DefaultCompoundOutput output = new DefaultCompoundOutput(outputPairs.get(i).getLeft(), outputPairs.get(i).getRight());
            result_.add(output);
        }
        return result_;
    }

    @Override
    public String toString() {
        return output.toString();
    }
}