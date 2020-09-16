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

import com.gs.dmn.runtime.listener.node.DRGElementNode;

import java.util.ArrayList;
import java.util.List;

public class TreeTraceEventListener extends AbstractTraceEventListener implements SimpleEventListener {
    // Output
    DRGElementNode root = null;

    public TreeTraceEventListener() {
    }

    @Override
    public void startDRGElement(DRGElement element, Arguments arguments) {
        DRGElementNode elementNode = new DRGElementNode(element, arguments);
        if (this.root == null) {
            // Set root
            this.root = elementNode;
        } else {
            // Add to parent
            if (!this.elementNodeStack.empty()) {
                DRGElementNode parent = this.elementNodeStack.peek();
                if (parent != null) {
                    parent.addChild(elementNode);
                }
            }
        }
        this.elementNodeStack.push(elementNode);
    }

    @Override
    public void endDRGElement(DRGElement element, Arguments arguments, Object output, long duration) {
        if (!this.elementNodeStack.empty()) {
            DRGElementNode top = this.elementNodeStack.pop();
            if (top != null) {
                top.setOutput(output);
            }
        }
    }

    public DRGElementNode getRoot() {
        return this.root;
    }

    public List<DRGElementNode> preorderNodes() {
        List<DRGElementNode> accumulator = new ArrayList<>();
        collectPreorder(root, accumulator);
        return accumulator;
    }

    public List<DRGElementNode> postorderNodes() {
        List<DRGElementNode> accumulator = new ArrayList<>();
        collectPostorder(root, accumulator);
        return accumulator;
    }

    private void collectPreorder(DRGElementNode node, List<DRGElementNode> accumulator) {
        if (node != null) {
            accumulator.add(node);
            for (DRGElementNode child: node.getChildren()) {
                collectPreorder(child, accumulator);
            }
        }
    }

    private void collectPostorder(DRGElementNode node, List<DRGElementNode> accumulator) {
        if (node != null) {
            for (DRGElementNode child: node.getChildren()) {
                collectPostorder(child, accumulator);
            }
            accumulator.add(node);
        }
    }
}
