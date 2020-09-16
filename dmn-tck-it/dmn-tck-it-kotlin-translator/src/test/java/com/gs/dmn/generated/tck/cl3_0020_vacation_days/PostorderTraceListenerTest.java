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

import com.gs.dmn.runtime.annotation.AnnotationSet;
import com.gs.dmn.runtime.cache.DefaultCache;
import com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor;
import com.gs.dmn.runtime.listener.PostorderTraceEventListener;
import com.gs.dmn.runtime.listener.node.DRGElementNode;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PostorderTraceListenerTest extends AbstractTraceListenerTest {
    private final TotalVacationDays decision = new TotalVacationDays();

    @Test
    public void testListener() throws Exception {
        AnnotationSet annotationSet = new AnnotationSet();
        PostorderTraceEventListener listener = new PostorderTraceEventListener();

        String expectedResult = "27";
        String age = "16";
        String yearsOfService = "1";
        BigDecimal actualResult = decision.apply(age, yearsOfService, annotationSet, listener, new DefaultExternalFunctionExecutor(), new DefaultCache());
        assertEquals(expectedResult, actualResult.toPlainString());

        List<DRGElementNode> elementTraces = listener.postorderNodes();
        File actualOutputFile = writeNodes(elementTraces);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/26-1-postorder.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    @Test
    public void testListenerWithFilter() throws Exception {
        AnnotationSet annotationSet = new AnnotationSet();
        PostorderTraceEventListener listener = new PostorderTraceEventListener(Arrays.asList("'Extra days case 1'", "'Extra days case 2'"));

        String expectedResult = "27";
        String age = "16";
        String yearsOfService = "1";
        BigDecimal actualResult = decision.apply(age, yearsOfService, annotationSet, listener, new DefaultExternalFunctionExecutor(), new DefaultCache());
        assertEquals(expectedResult, actualResult.toPlainString());

        List<DRGElementNode> elementTraces = listener.postorderNodes();
        File actualOutputFile = writeNodes(elementTraces);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/26-1-postorder-with-filter.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    private String getExpectedPath() {
        return "traces/cl3_0020_vacation_days";
    }
}
