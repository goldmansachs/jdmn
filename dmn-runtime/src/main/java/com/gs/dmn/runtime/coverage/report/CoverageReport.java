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
package com.gs.dmn.runtime.coverage.report;

import com.gs.dmn.runtime.coverage.trace.ModelCoverageTrace;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CoverageReport {
    private Map<String, ModelCoverageTrace> traceMap = new ConcurrentHashMap<>();

    public void addTraces(List<ModelCoverageTrace> traces) {
        // Merge traces for the same model.
        traces.stream().forEach(trace -> {
            if (traceMap.containsKey(trace.getNamespace())) {
                traceMap.get(trace.getNamespace()).merge(trace);
            } else {
                traceMap.put(trace.getNamespace(), trace);
            }
        });
    }

    public Table toTable() {
        List<List<String>> lines = new ArrayList<>();
        traceMap.forEach((namespace, trace) -> {
            lines.addAll(toLines(trace));
        });
        return new Table(columnNames(), lines);
    }

    protected abstract List<String> columnNames();

    protected abstract List<List<String>> toLines(ModelCoverageTrace modelCoverage);
}
