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
package com.gs.dmn.signavio.runtime.interpreter;

import java.util.Map;

public class DecisionTestConfig {
    private final String decisionName;
    private final String diagramName;
    private final Map<String, Object> runtimeContext;
    private final Object expectedResult;

    public DecisionTestConfig(String decisionName, String diagramName, Map<String, Object> runtimeContext, Object expectedResult) {
        this.decisionName = decisionName;
        this.diagramName = diagramName;
        this.runtimeContext = runtimeContext;
        this.expectedResult = expectedResult;
    }

    public String getDecisionName() {
        return decisionName;
    }

    public String getDiagramName() {
        return diagramName;
    }

    public Map<String, Object> getRuntimeContext() {
        return runtimeContext;
    }

    public Object getExpectedResult() {
        return expectedResult;
    }
}
