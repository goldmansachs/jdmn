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
package com.gs.dmn.signavio.transformation.config;

import java.util.List;
import java.util.Objects;

public class DecisionTableCorrection extends Correction {
    private final String oldValue;
    private final String newValue;

    private final List<Integer> inputIndexes;
    private final List<Integer> ruleIndexes;

    public DecisionTableCorrection(String drgName, String oldValue, String newValue, List<Integer> inputIndexes, List<Integer> ruleIndexes) {
        super(drgName);
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.inputIndexes = inputIndexes;
        this.ruleIndexes = ruleIndexes;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public List<Integer> getInputIndexes() {
        return inputIndexes;
    }

    public List<Integer> getRuleIndexes() {
        return ruleIndexes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecisionTableCorrection that = (DecisionTableCorrection) o;
        return Objects.equals(drgName, that.drgName) && Objects.equals(oldValue, that.oldValue) && Objects.equals(newValue, that.newValue) && Objects.equals(inputIndexes, that.inputIndexes) && Objects.equals(ruleIndexes, that.ruleIndexes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oldValue, newValue, inputIndexes, ruleIndexes);
    }

    @Override
    public String toString() {
        return String.format("%s('%s', '%s', '%s', '%s', '%s')", this.getClass().getSimpleName(), drgName, oldValue, newValue, inputIndexes, ruleIndexes);
    }
}
