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
import com.gs.dmn.serialization.TestSerializer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.TCKMarshaller;
import com.gs.dmn.transformation.InputParameters;

import java.io.Reader;
import java.io.Writer;

import static com.gs.dmn.error.DMNErrorHandler.handleError;

public abstract class TCKSerializer extends TestSerializer<TestCases> {
    private final TCKMarshaller marshaller;

    public TCKSerializer(BuildLogger logger, InputParameters inputParameters, TCKMarshaller marshaller) {
        super(logger, inputParameters);
        this.marshaller = marshaller;
    }

    @Override
    public TestCases read(Reader input) {
        if (input == null) {
            throw new DMNRuntimeException("Cannot read TCK from null Reader");
        }

        try {
            return this.marshaller.unmarshal(input, this.inputParameters.isXsdValidation());
        } catch (Exception e) {
            throw handleError(String.format("Cannot read TCK from Reader '%s'", input), e);
        }
    }

    @Override
    public void write(TestCases testCases, Writer output) {
        if (testCases == null) {
            throw new DMNRuntimeException("Cannot write null TCK");
        }
        if (output == null) {
            throw new DMNRuntimeException("Cannot write to null Writer");
        }

        try {
            this.marshaller.marshal(testCases, output);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot write TCK to Writer '%s'", output), e);
        }
    }
}
