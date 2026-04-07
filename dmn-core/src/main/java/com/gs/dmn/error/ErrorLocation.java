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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ErrorLocation {
    @JsonIgnore
    protected String text;

    public String toText() {
        return text;
    }

    @Override
    public String toString() {
        return this.toText();
    }

    protected void addPart(String key, String value, Map<String, String> parts) {
        parts.put(key, value);
    }

    protected void initText(Map<String, String> parts) {
        List<String> notEmptyParts = parts.entrySet().stream()
                .filter(e -> StringUtils.isNotEmpty(e.getValue()))
                .map(e -> String.format("%s = '%s'", e.getKey(), e.getValue())).collect(Collectors.toList());
        this.text = notEmptyParts.isEmpty() ? "" : String.format("(%s)", String.join(", ", notEmptyParts));
    }
}
