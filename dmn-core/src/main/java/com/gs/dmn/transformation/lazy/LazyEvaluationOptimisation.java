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
package com.gs.dmn.transformation.lazy;

import java.util.LinkedHashSet;
import java.util.Set;

public class LazyEvaluationOptimisation {
    private final Set<String> lazyEvaluatedDecisions = new LinkedHashSet<>();

    public void addLazyEvaluatedDecision(String name) {
        this.lazyEvaluatedDecisions.add(name);
    }

    public boolean isLazyEvaluated(String name) {
        return this.lazyEvaluatedDecisions.contains(name);
    }

    public Set<String> getLazyEvaluatedDecisions() {
        return this.lazyEvaluatedDecisions;
    }

    public void union(LazyEvaluationOptimisation other) {
        this.lazyEvaluatedDecisions.addAll(other.lazyEvaluatedDecisions);
    }
}
