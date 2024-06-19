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
package com.gs.dmn.generated.tck.cl3_0020_vacation_days;

import com.gs.dmn.runtime.ExecutionContext;
import com.gs.dmn.runtime.ExecutionContextBuilder;
import com.gs.dmn.runtime.listener.TreeTraceEventListener;
import com.gs.dmn.runtime.listener.node.DRGElementNode;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeTraceListenerTest extends AbstractTraceListenerTest {
    private final TotalVacationDays decision = new TotalVacationDays();

    @Test
    public void testTree() throws Exception {
        TreeTraceEventListener listener = new TreeTraceEventListener();
        ExecutionContext context = ExecutionContextBuilder.executionContext().withEventListener(listener).build();

        String expectedResult = "27";
        String age = "16";
        String yearsOfService = "1";
        Number actualResult = applyDecision(age, yearsOfService, context);
        assertEquals(expectedResult, actualResult.toString());

        DRGElementNode root = listener.getRoot();
        File actualOutputFile = writeNodes(root);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/26-1-tree.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    @Test
    public void testPreorder() throws Exception {
        TreeTraceEventListener listener = new TreeTraceEventListener();
        ExecutionContext context = ExecutionContextBuilder.executionContext().withEventListener(listener).build();

        String expectedResult = "27";
        String age = "16";
        String yearsOfService = "1";
        Number actualResult = applyDecision(age, yearsOfService, context);
        assertEquals(expectedResult, actualResult.toString());

        List<DRGElementNode> nodes = listener.preorderNodes();
        File actualOutputFile = writeNodes(nodes);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/26-1-tree-preorder.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    @Test
    public void testPostorder() throws Exception {
        TreeTraceEventListener listener = new TreeTraceEventListener();
        ExecutionContext context = ExecutionContextBuilder.executionContext().withEventListener(listener).build();

        String expectedResult = "27";
        String age = "16";
        String yearsOfService = "1";
        Number actualResult = applyDecision(age, yearsOfService, context);
        assertEquals(expectedResult, actualResult.toString());

        List<DRGElementNode> nodes = listener.postorderNodes();
        File actualOutputFile = writeNodes(nodes);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/26-1-tree-postorder.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    private Number applyDecision(String age, String yearsOfService, ExecutionContext context) {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("Age", age);
        result.put("Years of Service", yearsOfService);
        return decision.applyMap(result, context);
    }

    private String getExpectedPath() {
        return "traces/cl3_0020_vacation_days";
    }
}
