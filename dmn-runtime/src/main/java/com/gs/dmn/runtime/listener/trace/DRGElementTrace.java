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
package com.gs.dmn.runtime.listener.trace;

import com.gs.dmn.runtime.listener.Arguments;
import com.gs.dmn.runtime.listener.DRGElement;

import java.util.ArrayList;
import java.util.List;

public class DRGElementTrace {
    private final DRGElement element;
    private final Arguments arguments;
    private List<RuleTrace> ruleTraces = new ArrayList<>();
    private List<DRGElementTrace> children = new ArrayList<>();

    public DRGElementTrace(DRGElement element, Arguments arguments) {
        this.element = element;
        this.arguments = arguments;
    }

    public void addRuleTrace(RuleTrace ruleTrace) {
        this.ruleTraces.add(ruleTrace);
    }

    public DRGElement getElement() {
        return element;
    }

    public Arguments getArguments() {
        return arguments;
    }

    public List<RuleTrace> getRuleTraces() {
        return ruleTraces;
    }

    public List<DRGElementTrace> getChildren() {
        return children;
    }

    public void addChild(DRGElementTrace elementTrace) {
        this.children.add(elementTrace);
    }
}
