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

import com.gs.dmn.runtime.Context;

import java.util.ArrayList;
import java.util.List;

public class ContextSpecification {
    private final List<PathValue> entries = new ArrayList<>();

    public ContextSpecification addEntry(String path, Object value) {
        this.entries.add(new PathValue(path, value));
        return this;
    }

    public Context build() {
        return ContextBuilder.build(entries);
    }
}