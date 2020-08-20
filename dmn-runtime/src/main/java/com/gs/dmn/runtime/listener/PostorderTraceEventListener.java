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

import com.gs.dmn.runtime.listener.node.ColumnNode;
import com.gs.dmn.runtime.listener.node.DRGElementNode;
import com.gs.dmn.runtime.listener.node.RuleNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class PostorderTraceEventListener implements SimpleEventListener {
    // Elements to trace
    private final List<String> drgElementNames = new ArrayList<>();

    // Output
    private final List<DRGElementNode> elementNodes = new ArrayList<>();

    // Temp data
    private final Stack<DRGElementNode> elementNodeStack = new Stack<>();
    private RuleNode ruleNode;

    public PostorderTraceEventListener() {
    }

    public PostorderTraceEventListener(List<String> drgElementNames) {
        if (drgElementNames != null) {
            this.drgElementNames.addAll(drgElementNames);
        }
    }

    @Override
    public void startDRGElement(DRGElement element, Arguments arguments) {
        DRGElementNode elementNode = new DRGElementNode(element, arguments);
        this.elementNodeStack.push(elementNode);
    }

    @Override
    public void endDRGElement(DRGElement element, Arguments arguments, Object output, long duration) {
        if (!this.elementNodeStack.empty()) {
            DRGElementNode top = this.elementNodeStack.pop();
            if (top != null) {
                this.elementNodes.add(top);
            }
        }
    }

    @Override
    public void startRule(DRGElement element, Rule rule) {
        this.ruleNode = new RuleNode(rule);
    }

    @Override
    public void matchRule(DRGElement element, Rule rule) {
        this.ruleNode.setMatched(true);
    }

    @Override
    public void endRule(DRGElement element, Rule rule, Object result) {
        this.ruleNode.setResult(result);
        if (!this.elementNodeStack.empty()) {
            DRGElementNode top = this.elementNodeStack.peek();
            if (top != null) {
                top.addRuleNode(this.ruleNode);
            }
        }
    }

    @Override
    public void matchColumn(Rule rule, int columnIndex, Object result) {
        ColumnNode columnNode = new ColumnNode(columnIndex, result);
        this.ruleNode.addColumnNode(columnNode);
    }

    public List<DRGElementNode> postorderNodes() {
        return this.elementNodes.stream().filter(this::filter).collect(Collectors.toList());
    }

    private boolean filter(DRGElementNode elementNode) {
        if (elementNode == null) {
            return false;
        }
        if (this.drgElementNames.isEmpty()) {
            return true;
        }
        return this.drgElementNames.contains(elementNode.getElement().getName()) || this.drgElementNames.contains(elementNode.getElement().getLabel());
    }
}
