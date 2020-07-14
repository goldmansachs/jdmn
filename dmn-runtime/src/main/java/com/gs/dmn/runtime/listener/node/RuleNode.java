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

import com.gs.dmn.runtime.listener.Rule;

import java.util.ArrayList;
import java.util.List;

public class RuleNode {
    private final Rule rule;
    private boolean matched;
    private Object result;
    private List<ColumnNode> columnNodes = new ArrayList<>();

    public RuleNode(Rule rule) {
        this.rule = rule;
    }

    public Rule getRule() {
        return rule;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public List<ColumnNode> getColumnNodes() {
        return columnNodes;
    }

    public void addColumnNode(ColumnNode columnNode) {
        if (columnNode != null) {
            this.columnNodes.add(columnNode);
        }
    }
}
