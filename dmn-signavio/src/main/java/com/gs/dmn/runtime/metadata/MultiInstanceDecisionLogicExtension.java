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
package com.gs.dmn.runtime.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MultiInstanceDecisionLogicExtension extends ExtensionElement{
    @JsonProperty("iterationExpression")
    private String iterationExpression;

    @JsonProperty("iteratorShapeId")
    private String iteratorShapeId;

    @JsonProperty("aggregationFunction")
    private String aggregationFunction;

    @JsonProperty("topLevelDecisionId")
    private String topLevelDecisionId;

    // Required by ObjectMapper
    MultiInstanceDecisionLogicExtension() {
    }

    public MultiInstanceDecisionLogicExtension(String iterationExpression, String iteratorShapeId, String aggregationFunction, String topLevelDecisionId) {
        this.iterationExpression = iterationExpression;
        this.iteratorShapeId = iteratorShapeId;
        this.aggregationFunction = aggregationFunction;
        this.topLevelDecisionId = topLevelDecisionId;
    }

    public String getIterationExpression() {
        return iterationExpression;
    }

    public String getIteratorShapeId() {
        return iteratorShapeId;
    }

    public String getAggregationFunction() {
        return aggregationFunction;
    }

    public String getTopLevelDecisionId() {
        return topLevelDecisionId;
    }
}
