/**
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
package com.gs.dmn.runtime.annotation;

public class Annotation {
    private final String decisionName;
    private final int ruleIndex;
    private final String annotation;

    public Annotation(String decisionName, int ruleIndex, String annotation) {
        this.decisionName = decisionName;
        this.ruleIndex = ruleIndex;
        this.annotation = annotation;
    }

    public String getDecisionName() {
        return decisionName;
    }

    public int getRuleIndex() {
        return ruleIndex;
    }

    public String getAnnotation() {
        return annotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Annotation that = (Annotation) o;

        if (ruleIndex != that.ruleIndex) return false;
        if (decisionName != null ? !decisionName.equals(that.decisionName) : that.decisionName != null) return false;
        return annotation != null ? annotation.equals(that.annotation) : that.annotation == null;
    }

    @Override
    public int hashCode() {
        int result = decisionName != null ? decisionName.hashCode() : 0;
        result = 31 * result + ruleIndex;
        result = 31 * result + (annotation != null ? annotation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Annotation('%s', %d, '%s')", decisionName, ruleIndex, annotation);
    }
}
