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

import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor;
import com.gs.dmn.runtime.listener.TreeTraceEventListener;
import com.gs.dmn.runtime.listener.node.DRGElementNode;
import com.gs.dmn.serialization.JsonSerializer;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TreeTraceListenerTest {
    private final TotalVacationDays decision = new TotalVacationDays();

    @Test
    public void testTree() throws Exception {
        AnnotationSet annotationSet = new AnnotationSet();
        TreeTraceEventListener listener = new TreeTraceEventListener();

        String expectedResult = "27";
        String age = "16";
        String yearsOfService = "1";
        BigDecimal actualResult = decision.apply(age, yearsOfService, annotationSet, listener, new DefaultExternalFunctionExecutor());
        assertEquals(expectedResult, actualResult.toPlainString());

        DRGElementNode root = listener.getRoot();
        File actualOutputFile = writeNodes(root);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/26-1-tree.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    @Test
    public void testPreorder() throws Exception {
        AnnotationSet annotationSet = new AnnotationSet();
        TreeTraceEventListener listener = new TreeTraceEventListener();

        String expectedResult = "27";
        String age = "16";
        String yearsOfService = "1";
        BigDecimal actualResult = decision.apply(age, yearsOfService, annotationSet, listener, new DefaultExternalFunctionExecutor());
        assertEquals(expectedResult, actualResult.toPlainString());

        List<DRGElementNode> nodes = listener.preorderNodes();
        File actualOutputFile = writeNodes(nodes);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/26-1-tree-preorder.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    @Test
    public void testPostorder() throws Exception {
        AnnotationSet annotationSet = new AnnotationSet();
        TreeTraceEventListener listener = new TreeTraceEventListener();

        String expectedResult = "27";
        String age = "16";
        String yearsOfService = "1";
        BigDecimal actualResult = decision.apply(age, yearsOfService, annotationSet, listener, new DefaultExternalFunctionExecutor());
        assertEquals(expectedResult, actualResult.toPlainString());

        List<DRGElementNode> nodes = listener.postorderNodes();
        File actualOutputFile = writeNodes(nodes);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/26-1-tree-postorder.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    private String getExpectedPath() {
        return "traces/cl3_0020_vacation_days";
    }

    protected URI resource(String path) {
        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            if (url == null) {
                throw new DMNRuntimeException(String.format("Cannot find resource '%s'", path));
            }
            return url.toURI();
        } catch (URISyntaxException e) {
            throw new DMNRuntimeException(e);
        }
    }

    private File writeNodes(Object nodes) throws IOException {
        File actualOutputFile = File.createTempFile("trace", "trc");
        JsonSerializer.OBJECT_MAPPER.writeValue(actualOutputFile, nodes);
        return actualOutputFile;
    }

    private void checkTrace(File expectedOutputFile, File actualOutputFile) throws Exception {
        String expectedContent = FileUtils.readFileToString(expectedOutputFile, "UTF-8");
        String actualContent = FileUtils.readFileToString(actualOutputFile, "UTF-8");
        JSONAssert.assertEquals(expectedContent, actualContent, JSONCompareMode.STRICT);
    }
}
