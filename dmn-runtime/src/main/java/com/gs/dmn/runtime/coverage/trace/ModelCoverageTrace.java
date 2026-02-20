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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class ModelCoverageTrace {
    private String namespace;
    private String modelName;
    private int elementCount;
    private Map<String, ElementCoverageTrace> nameToElementTraces = new ConcurrentHashMap<>();

    public ModelCoverageTrace() {
        this("", "", 0);
    }

    public ModelCoverageTrace(String namespace, String modelName, int elementCount) {
        this.namespace = namespace;
        this.modelName = modelName;
        this.elementCount = elementCount;
    }

    @JsonProperty("namespace")
    public String getNamespace() {
        return namespace;
    }

    @JsonProperty("namespace")
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @JsonProperty("modelName")
    public String getModelName() {
        return modelName;
    }

    @JsonProperty("modelName")
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @JsonProperty("elementCount")
    public int getElementCount() {
        return elementCount;
    }

    @JsonProperty("elementCount")
    public void setElementCount(int elementCount) {
        this.elementCount = elementCount;
    }

    @JsonProperty("elementTraces")
    public List<ElementCoverageTrace> getElementTraces() {
        return new java.util.ArrayList<>(nameToElementTraces.values());
    }

    @JsonProperty("elementTraces")
    public void setElementTraces(List<ElementCoverageTrace> elementTraces) {
        for (ElementCoverageTrace elementTrace : elementTraces) {
            nameToElementTraces.put(elementTrace.getName(), elementTrace);
        }
    }

    ElementCoverageTrace getElementTrace(String name) {
        return nameToElementTraces.get(name);
    }

    void addElementTrace(String name, int rulesCount) {
        nameToElementTraces.computeIfAbsent(name, k -> new ElementCoverageTrace(name, rulesCount));
    }

    public void merge(ModelCoverageTrace trace) {
        // Merge the element count for the same model.
        this.elementCount = Math.max(this.elementCount, trace.getElementCount());
        // Merge element traces for the same model.
        trace.getElementTraces().stream().forEach(elementTrace -> {
            String key = elementTrace.getName();
            if (nameToElementTraces.containsKey(key)) {
                nameToElementTraces.get(key).merge(elementTrace);
            } else {
                nameToElementTraces.put(key, elementTrace);
            }
        });
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelCoverageTrace that = (ModelCoverageTrace) o;
        return Objects.equals(namespace, that.namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace);
    }

    @Override
    public String toString() {
        return "ModelTrace{namespace='" + namespace + "'}";
    }
}

