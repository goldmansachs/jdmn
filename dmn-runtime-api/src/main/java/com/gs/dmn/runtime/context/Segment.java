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
package com.gs.dmn.runtime.context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Segment {
    private static final Pattern SEGMENT = Pattern.compile("([^\\[\\]]+)(?:\\[(\\d*)\\])?");

    public static Segment extractSegment(String segment) {
        Matcher m = SEGMENT.matcher(segment);
        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid path segment: " + segment);
        }
        String key = m.group(1);
        String idxGroup = m.group(2);

        return new Segment(key, idxGroup);
    }

    private final String key;
    private final String idxGroup;

    public Segment(String key, String idxGroup) {
        this.key = key;
        this.idxGroup = idxGroup;
    }

    public String getKey() {
        return key;
    }

    public boolean isList() {
        return idxGroup != null;
    }

    public Integer getIndex() {
        return (idxGroup == null || idxGroup.isEmpty()) ? null : Integer.valueOf(idxGroup);
    }
}
