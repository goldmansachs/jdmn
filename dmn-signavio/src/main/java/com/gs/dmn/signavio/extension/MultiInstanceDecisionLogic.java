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
package com.gs.dmn.signavio.extension;

import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.runtime.DMNRuntimeException;

public class MultiInstanceDecisionLogic {
    private String iterationExpression;
    private final TDRGElement iterator;
    private final Aggregator aggregator;
    private final TDecision topLevelDecision;

    public MultiInstanceDecisionLogic(String iterationExpression, TDRGElement iterator, Aggregator aggregator, TDecision topLevelDecision) {
        if (iterationExpression == null) {
            throw new DMNRuntimeException("iterationExpression cannot be null ");
        }
        if (iterator == null) {
            throw new DMNRuntimeException("iteratorDecision cannot be null ");
        }
        if (aggregator == null) {
            throw new DMNRuntimeException("aggregator cannot be null ");
        }
        if (topLevelDecision == null) {
            throw new DMNRuntimeException("topLevelDecision cannot be null ");
        }
        this.iterationExpression = iterationExpression;
        this.iterator = iterator;
        this.aggregator = aggregator;
        this.topLevelDecision = topLevelDecision;
    }

    public String getIterationExpression() {
        return iterationExpression;
    }

    public void setIterationExpression(String iterationExpression) {
        this.iterationExpression = iterationExpression;
    }

    public TDRGElement getIterator() {
        return iterator;
    }

    public Aggregator getAggregator() {
        return aggregator;
    }

    public TDecision getTopLevelDecision() {
        return topLevelDecision;
    }
}
