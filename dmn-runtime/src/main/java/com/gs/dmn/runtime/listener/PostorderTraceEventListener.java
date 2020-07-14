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
package com.gs.dmn.runtime.listener;

import com.gs.dmn.runtime.listener.trace.DRGElementTrace;
import com.gs.dmn.runtime.listener.trace.RuleTrace;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class PostorderTraceEventListener implements EventListener {
    // Elements to trace
    private final List<String> drgElementNames = new ArrayList<>();

    // Output
    private List<DRGElementTrace> elementTraces = new ArrayList<>();
    private RuleTrace ruleTrace;

    // Temp data
    Stack<DRGElementTrace> elementTraceStack = new Stack<>();

    public PostorderTraceEventListener() {
    }

    public PostorderTraceEventListener(List<String> drgElementNames) {
        if (drgElementNames != null) {
            this.drgElementNames.addAll(drgElementNames);
        }
    }

    @Override
    public void startDRGElement(DRGElement element, Arguments arguments) {
        DRGElementTrace elementTrace = new DRGElementTrace(element, arguments);
        this.elementTraceStack.push(elementTrace);
    }

    @Override
    public void endDRGElement(DRGElement element, Arguments arguments, Object output, long duration) {
        if (!this.elementTraceStack.empty()) {
            DRGElementTrace top = this.elementTraceStack.pop();
            if (top != null) {
                this.elementTraces.add(top);
            }
        }
    }

    @Override
    public void startRule(DRGElement element, Rule rule) {
        this.ruleTrace = new RuleTrace(rule);
    }

    @Override
    public void matchRule(DRGElement element, Rule rule) {
        this.ruleTrace.setMatched(true);
    }

    @Override
    public void endRule(DRGElement element, Rule rule, Object result) {
        this.ruleTrace.setResult(result);
        if (!this.elementTraceStack.empty()) {
            DRGElementTrace top = this.elementTraceStack.peek();
            top.addRuleTrace(this.ruleTrace);
        }
    }

    public List<DRGElementTrace> getElementTraces() {
        return this.elementTraces.stream().filter(et -> filter(et)).collect(Collectors.toList());
    }

    private boolean filter(DRGElementTrace et) {
        if (et == null) {
            return false;
        }
        if (this.drgElementNames == null || this.drgElementNames.isEmpty()) {
            return true;
        }
        return this.drgElementNames.contains(et.getElement().getName()) || this.drgElementNames.contains(et.getElement().getLabel());
    }
}
