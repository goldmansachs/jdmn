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
package com.gs.dmn.tck.serialization.xstream.v1;

import com.gs.dmn.tck.TCKSerializer;
import com.gs.dmn.tck.serialization.AbstractTCKUnmarshalMarshalTest;
import com.gs.dmn.tck.serialization.TCKMarshaller;
import com.gs.dmn.tck.serialization.xstream.TCKMarshallerFactory;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

public class UnmarshalMarshalTest extends AbstractTCKUnmarshalMarshalTest {
    private final TCKMarshaller marshaller = TCKMarshallerFactory.newDefaultMarshaller();
    private final String rootOutputPath = "target/";

    @Ignore("Integration all TCK files")
    public void testTCKTests() throws Exception {
        testRoundTrip(STANDARD_FOLDER);
    }

    private void testRoundTrip(File file) throws Exception {
        if (TCKSerializer.isTCKFile(file)) {
            LOGGER.debug(String.format("Testing '%s'", file.getPath()));
            testRoundTrip(file, marshaller, rootOutputPath);
        } else if (file.isDirectory() && !file.getName().equals("expected")) {
            for (File child: file.listFiles()) {
                testRoundTrip(child);
            }
        }
    }

    @Test
    public void testVersion11() throws Exception {
        String version = "1.1";
        testRoundTrip(makeFile(version, "0001-input-data-string"), marshaller, makeOutputPath(version));
        testRoundTrip(makeFile(version, "0002-input-data-number"), marshaller, makeOutputPath(version));
    }

    @Test
    public void testVersion12() throws Exception {
        String version = "1.2";
        testRoundTrip(makeFile(version, "0001-input-data-string"), marshaller, makeOutputPath(version));
        testRoundTrip(makeFile(version, "0002-input-data-number"), marshaller, makeOutputPath(version));
    }

    @Test
    public void testVersion13() throws Exception {
        String version = "1.3";
        testRoundTrip(makeFile(version, "0001-input-data-string"), marshaller, makeOutputPath(version));
        testRoundTrip(makeFile(version, "0002-input-data-number"), marshaller, makeOutputPath(version));
    }

    private String makeOutputPath(String version) {
        return String.format("%s/xstream/tck/%s", rootOutputPath, version);
    }

    private File makeFile(String version, String name) {
        String path = String.format("tck/%s/cl2/%s/%s-test-01.xml", version, name, name);
        return new File(STANDARD_FOLDER, path);
    }

}
