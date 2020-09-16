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
import com.gs.dmn.serialization.JsonSerializer;
import org.apache.commons.io.FileUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class AbstractTraceListenerTest {
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

    protected File writeNodes(Object nodes) throws IOException {
        File actualOutputFile = File.createTempFile("trace", "trc");
        JsonSerializer.OBJECT_MAPPER.writeValue(actualOutputFile, nodes);
        return actualOutputFile;
    }

    protected void checkTrace(File expectedOutputFile, File actualOutputFile) throws Exception {
        String expectedContent = FileUtils.readFileToString(expectedOutputFile, "UTF-8");
        String actualContent = FileUtils.readFileToString(actualOutputFile, "UTF-8");
        JSONAssert.assertEquals(expectedContent, actualContent, JSONCompareMode.STRICT);
    }
}
