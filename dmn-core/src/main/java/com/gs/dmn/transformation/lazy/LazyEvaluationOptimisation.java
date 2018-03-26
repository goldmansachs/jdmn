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
