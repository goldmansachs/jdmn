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
package com.gs.dmn.signavio.serialization.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("MultiInstanceDecisionLogic")
public class MultiInstanceDecisionLogic {
    @XStreamAlias("iterationExpression")
    private String iterationExpression;
    
    @XStreamAlias("iteratorShapeId")
    private String iteratorShapeId;
    
    @XStreamAlias("aggregationFunction")
    private String aggregationFunction;
    
    @XStreamAlias("topLevelDecisionId")
    private String topLevelDecisionId;
    
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

    @Override
    public String toString() {
        return String.format(
                "MultiInstanceDecisionLogic(iterationExpression='%s', iteratorShapeId='%s', aggregationFunction='%s', topLevelDecisionId='%s')",
                iterationExpression, iteratorShapeId, aggregationFunction, topLevelDecisionId
        );
    }
}
