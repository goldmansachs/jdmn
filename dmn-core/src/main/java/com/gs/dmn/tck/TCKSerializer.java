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
import com.gs.dmn.transformation.InputParameters;

import java.io.*;

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
    private final InputParameters inputParameters;

    public TCKSerializer(BuildLogger logger, TCKMarshaller marshaller, InputParameters inputParameters) {
        this.logger = logger;
        this.marshaller = marshaller;
        this.inputParameters = inputParameters;
    }

    public TestCases read(File input) {
        try (FileInputStream fis = new FileInputStream(input); InputStreamReader isr = new InputStreamReader(fis)) {
            this.logger.info(String.format("Reading TCK '%s' ...", input.getPath()));

            TestCases testCases = read(isr);

            this.logger.info("TCK read.");
            return testCases;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read TCK from File '%s'", input.getPath()), e);
        }
    }

    public TestCases read(Reader input) {
        try {
            this.logger.info(String.format("Reading TCK '%s' ...", input.toString()));

            TestCases testCases = this.marshaller.unmarshal(input, this.inputParameters.isXsdValidation());

            this.logger.info("TCK read.");
            return testCases;
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read TCK from Reader '%s'", input.toString()), e);
        }
    }

    public void write(TestCases testCases, File output) {
        try (FileOutputStream fos = new FileOutputStream(output); OutputStreamWriter osw = new OutputStreamWriter(fos)) {
            write(testCases, osw);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot write TCK to File '%s'", output.getPath()), e);
        }
    }

    public void write(TestCases testCases, Writer output) {
        try {
            this.marshaller.marshal(testCases, output);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot write TCK to Writer '%s'", output.toString()), e);
        }
    }
}
