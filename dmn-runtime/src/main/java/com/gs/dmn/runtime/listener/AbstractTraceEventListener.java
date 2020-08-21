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

import java.util.Stack;

public abstract class AbstractTraceEventListener implements SimpleEventListener {
    // Temp data
    protected final Stack<DRGElementNode> elementNodeStack = new Stack<>();
    private RuleNode ruleNode;

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
}
