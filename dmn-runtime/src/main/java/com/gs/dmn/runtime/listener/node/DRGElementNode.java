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
package com.gs.dmn.runtime.listener.node;

import com.gs.dmn.runtime.listener.Arguments;
import com.gs.dmn.runtime.listener.DRGElement;

import java.util.ArrayList;
import java.util.List;

public class DRGElementNode {
    private final DRGElement element;
    private final Arguments arguments;
    private Object output;
    private List<RuleNode> ruleNodes = new ArrayList<>();
    private List<DRGElementNode> children = new ArrayList<>();

    public DRGElementNode(DRGElement element, Arguments arguments) {
        this.element = element;
        this.arguments = arguments;
    }

    public void addRuleNode(RuleNode ruleTrace) {
        this.ruleNodes.add(ruleTrace);
    }

    public DRGElement getElement() {
        return element;
    }

    public Arguments getArguments() {
        return arguments;
    }

    public Object getOutput() {
        return output;
    }

    public void setOutput(Object output) {
        this.output = output;
    }

    public List<RuleNode> getRuleNodes() {
        return ruleNodes;
    }

    public List<DRGElementNode> getChildren() {
        return children;
    }

    public void addChild(DRGElementNode elementTrace) {
        this.children.add(elementTrace);
    }
}
