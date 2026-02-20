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
package com.gs.dmn.runtime.coverage.trace;

import com.gs.dmn.runtime.listener.Arguments;
import com.gs.dmn.runtime.listener.DRGElement;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.runtime.listener.Rule;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CoverageTraceListener implements EventListener {
    private final String rootNamespace;
    private final String rootModelName;
    private final int rootElementCount;
    private final Map<String, ModelCoverageTrace> modelTraces = new LinkedHashMap<>();

    public CoverageTraceListener(String rootNamespace, String rootModelName, int rootElementCount) {
        this.rootNamespace = rootNamespace;
        this.rootModelName = rootModelName;
        this.rootElementCount = rootElementCount;
    }

    public List<ModelCoverageTrace> getModelTraces() {
        return new ArrayList<>(modelTraces.values());
    }

    @Override
    public void startDRGElement(DRGElement element, Arguments arguments) {
        if (element == null) {
            return;
        }

        // Add model trace if not exists
        String namespace = element.getNamespace();
        ModelCoverageTrace modelTrace;
        if (namespace.equals(rootNamespace)) {
            modelTrace = modelTraces.computeIfAbsent(namespace, k -> new ModelCoverageTrace(k, rootModelName, rootElementCount));
        } else {
            modelTrace = modelTraces.computeIfAbsent(namespace, k -> new ModelCoverageTrace(k, "", 0));
        }

        // Add element trace to model trace
        String name = element.getName();
        int rulesCount = element.getRulesCount() == -1 ? 0 : element.getRulesCount();
        modelTrace.addElementTrace(name, rulesCount);
    }

    @Override
    public void endDRGElement(DRGElement element, Arguments arguments, Object output, long duration) {
        // No-op
    }

    @Override
    public void startRule(DRGElement element, Rule rule) {
        if (element == null || rule == null) {
            return;
        }

        // Find model trace
        String namespace = element.getNamespace();
        ModelCoverageTrace modelTrace = modelTraces.get(namespace);

        // Add rule trace to element trace
        if (modelTrace != null) {
            ElementCoverageTrace elementTrace = modelTrace.getElementTrace(element.getName());
            elementTrace.addRuleTrace(rule.getIndex());
        } else {
            throw new IllegalStateException(String.format("Model trace for namespace '%s' not found", namespace));
        }
    }

    @Override
    public void matchRule(DRGElement element, Rule rule) {
        if (element == null || rule == null) {
            return;
        }

        // Find model trace
        String namespace = element.getNamespace();
        ModelCoverageTrace modelTrace = modelTraces.get(namespace);

        // Match existing rule trace to element trace
        if (modelTrace != null) {
            ElementCoverageTrace elementTrace = modelTrace.getElementTrace(element.getName());
            elementTrace.matchRuleTrace(rule.getIndex());
        } else {
            throw new IllegalStateException(String.format("Model trace for namespace '%s' not found", namespace));
        }
    }

    @Override
    public void endRule(DRGElement element, Rule rule, Object result) {
        // No-op
    }

    @Override
    public void matchColumn(Rule rule, int columnIndex, Object result) {
        // No-op
    }
}
