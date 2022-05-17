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
package com.gs.dmn.tck;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.TCKMarshaller;

import java.io.File;
import java.net.URL;

public abstract class TCKSerializer {
    public static final String DEFAULT_TEST_CASE_FILE_EXTENSION = ".xml";
    private static final String TEST_CASE_FILE_EXTENSION = ".tck";

    public static boolean isTCKFile(File file) {
        return file != null && file.isFile() &&
                (
                    file.getName().endsWith(DEFAULT_TEST_CASE_FILE_EXTENSION) || file.getName().endsWith(TEST_CASE_FILE_EXTENSION)
                );
    }

    private final BuildLogger logger;
    private final TCKMarshaller marshaller;
    private final boolean validateSchema;

    public TCKSerializer(BuildLogger logger, TCKMarshaller marshaller, boolean validateSchema) {
        this.logger = logger;
        this.marshaller = marshaller;
        this.validateSchema = validateSchema;
    }

    public TestCases read(File input) {
        try {
            return read(input.toURI().toURL());
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read DMN from '%s'", input.getPath()), e);
        }
    }

    public TestCases read(URL input) {
        try {
            logger.info(String.format("Reading TCK '%s' ...", input.toString()));

            TestCases testCases = this.marshaller.unmarshal(input, this.validateSchema);

            logger.info("TCK read.");
            return testCases;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read TCK from '%s'", input.toString()), e);
        }
    }

    public void write(TestCases testCases, File file) {
        try {
            this.marshaller.marshal(testCases, file);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot write DMN to '%s'", file.getPath()), e);
        }
    }
}
