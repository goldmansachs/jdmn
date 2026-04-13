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
package com.gs.dmn.serialization;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.transformation.InputParameters;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.gs.dmn.error.DMNErrorHandler.handleError;

public abstract class TestSerializer<TEST> {
    protected final BuildLogger logger;
    protected final InputParameters inputParameters;

    protected TestSerializer(BuildLogger logger, InputParameters inputParameters) {
        this.logger = logger;
        this.inputParameters = inputParameters;
    }

    public List<TEST> read(List<File> inputs) {
        if (inputs == null || inputs.isEmpty()) {
            return List.of();
        }

        List<TEST> result = new ArrayList<>();
        inputs.forEach(input -> result.add(read(input)));
        return result;
    }

    public TEST read(File input) {
        if (input == null) {
            throw new DMNRuntimeException("Cannot read Test Cases from null File");
        }

        try (FileInputStream fis = new FileInputStream(input); InputStreamReader isr = new InputStreamReader(fis, inputParameters.getCharset())) {
            this.logger.info(String.format("Reading Test Cases '%s' ...", input.getPath()));

            TEST testCases = read(isr);

            this.logger.info("TCK read.");
            return testCases;
        } catch (Exception e) {
            throw handleError(String.format("Cannot read TCK from File '%s'", input.getPath()), e);
        }
    }

    public abstract TEST read(Reader input);

    public void write(TEST testCases, File output) {
        if (testCases == null) {
            throw new DMNRuntimeException("Cannot write null TCK");
        }
        if (output == null) {
            throw new DMNRuntimeException("Cannot write tp null File");
        }

        try (FileOutputStream fos = new FileOutputStream(output); OutputStreamWriter osw = new OutputStreamWriter(fos, inputParameters.getCharset())) {
            write(testCases, osw);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot write TCK to File '%s'", output.getPath()), e);
        }
    }

    public abstract void write(TEST testCases, Writer output);
}

