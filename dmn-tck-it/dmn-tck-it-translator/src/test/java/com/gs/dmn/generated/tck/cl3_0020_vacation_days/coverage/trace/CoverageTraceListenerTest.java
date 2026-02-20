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
package com.gs.dmn.generated.tck.cl3_0020_vacation_days.coverage.trace;

import com.gs.dmn.generated.tck.cl3_0020_vacation_days.TotalVacationDays;
import com.gs.dmn.generated.tck.cl3_0020_vacation_days.listener.AbstractTraceListenerTest;
import com.gs.dmn.runtime.ExecutionContext;
import com.gs.dmn.runtime.coverage.trace.CoverageTraceListener;
import com.gs.dmn.runtime.coverage.trace.ModelCoverageTrace;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CoverageTraceListenerTest extends AbstractTraceListenerTest {
    private final TotalVacationDays decision = new TotalVacationDays();
    private String namespace = "https://www.drools.org/kie-dmn";
    private String modelName = "0020-vacation-days";
    private int elementCount = 5;

    @Test
    public void testForFirstTest() throws Exception {
        CoverageTraceListener eventListener = new CoverageTraceListener(namespace, modelName, elementCount);
        ExecutionContext context = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(eventListener).build();

        applyDecision("16", "1", context);
        List<ModelCoverageTrace> modelTraces = eventListener.getModelTraces();

        File actualOutputFile = writeNodes(modelTraces);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/16-1-coverage.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    @Test
    public void testForSecondTest() throws Exception {
        CoverageTraceListener eventListener = new CoverageTraceListener(namespace, modelName, elementCount);
        ExecutionContext context = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(eventListener).build();

        applyDecision("60", "20", context);
        List<ModelCoverageTrace> modelTraces = eventListener.getModelTraces();

        File actualOutputFile = writeNodes(modelTraces);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/60-20-coverage.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    @Test
    public void testForBothTests() throws Exception {
        CoverageTraceListener eventListener = new CoverageTraceListener(namespace, modelName, elementCount);
        ExecutionContext context = com.gs.dmn.runtime.ExecutionContextBuilder.executionContext().withEventListener(eventListener).build();

        applyDecision("16", "1", context);
        applyDecision("60", "20", context);
        List<ModelCoverageTrace> modelTraces = eventListener.getModelTraces();

        File actualOutputFile = writeNodes(modelTraces);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/16-1-60-20-coverage.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    private Number applyDecision(String age, String yearsOfService, ExecutionContext context) {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("Age", age);
        result.put("Years of Service", yearsOfService);
        return decision.applyMap(result, context);
    }

    private String getExpectedPath() {
        return "coverage-traces/0020_vacation_days";
    }
}