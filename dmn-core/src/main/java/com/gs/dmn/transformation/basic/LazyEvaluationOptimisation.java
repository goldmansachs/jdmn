package com.gs.dmn.transformation.basic;

import java.util.*;

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
}
