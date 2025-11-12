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
package com.gs.dmn.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LocationInfo {
    private final String namespace;
    private final String modelName;
    private final String modelId;
    private final String elementName;
    private final String elementId;

    @JsonIgnore
    private final String text;

    public LocationInfo(String namespace, String modelName, String modelId, String elementName, String elementId) {
        this.namespace = namespace;
        this.modelName = modelName;
        this.modelId = modelId;
        this.elementName = elementName;
        this.elementId = elementId;

        // Derive the textual representation
        Map<String, String> parts = new LinkedHashMap<>();
        addPart("namespace", namespace, parts);
        addPart("modelName", modelName, parts);
        addPart("modelId", modelId, parts);
        addPart("elementName", elementName, parts);
        addPart("elementId", elementId, parts);
        List<String> notEmptyParts = parts.entrySet().stream()
                .filter(e -> StringUtils.isNotEmpty(e.getValue()))
                .map(e -> String.format("%s = '%s'", e.getKey(), e.getValue())).collect(Collectors.toList());
        this.text = notEmptyParts.isEmpty() ? "" : String.format("(%s)", String.join(", ", notEmptyParts));
    }

    private void addPart(String key, String value, Map<String, String> parts) {
        parts.put(key, value);
    }

    public String getNamespace() {
        return namespace;
    }

    public String getModelName() {
        return modelName;
    }

    public String getModelId() {
        return modelId;
    }

    public String getElementName() {
        return elementName;
    }

    public String getElementId() {
        return elementId;
    }

    public String toText() {
        return text;
    }
}
