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
import com.gs.dmn.runtime.listener.trace.DRGElementTrace;
import com.gs.dmn.runtime.listener.TraceEventListener;
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
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TraceListenerTest {
    private final TotalVacationDays decision = new TotalVacationDays();

    @Test
    public void testListener() throws Exception {
        AnnotationSet annotationSet = new AnnotationSet();
        TraceEventListener listener = new TraceEventListener();

        String expectedResult = "27";
        String age = "16";
        String yearsOfService = "1";
        BigDecimal actualResult = decision.apply(age, yearsOfService, annotationSet, listener, new DefaultExternalFunctionExecutor());
        assertEquals(expectedResult, actualResult.toPlainString());

        List<DRGElementTrace> elementTraces = listener.getElementTraces();
        File actualOutputFile = writeTraces(elementTraces);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/26-1.json"));
        checkTrace(expectedOutputFile, actualOutputFile);
    }

    @Test
    public void testListenerWithFilter() throws Exception {
        AnnotationSet annotationSet = new AnnotationSet();
        TraceEventListener listener = new TraceEventListener(Arrays.asList("'Extra days case 1'", "'Extra days case 2'"));

        String expectedResult = "27";
        String age = "16";
        String yearsOfService = "1";
        BigDecimal actualResult = decision.apply(age, yearsOfService, annotationSet, listener, new DefaultExternalFunctionExecutor());
        assertEquals(expectedResult, actualResult.toPlainString());

        List<DRGElementTrace> elementTraces = listener.getElementTraces();
        File actualOutputFile = writeTraces(elementTraces);
        File expectedOutputFile = new File(resource(getExpectedPath() + "/26-1-with-filter.json"));
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

    private File writeTraces(List<DRGElementTrace> elementTraces) throws IOException {
        File actualOutputFile = File.createTempFile("trace", "trc");
        JsonSerializer.OBJECT_MAPPER.writeValue(actualOutputFile, elementTraces);
        return actualOutputFile;
    }

    private void checkTrace(File expectedOutputFile, File actualOutputFile) throws Exception {
        String expectedContent = FileUtils.readFileToString(expectedOutputFile, "UTF-8");
        String actualContent = FileUtils.readFileToString(actualOutputFile, "UTF-8");
        JSONAssert.assertEquals(expectedContent, actualContent, JSONCompareMode.STRICT);
    }
}
