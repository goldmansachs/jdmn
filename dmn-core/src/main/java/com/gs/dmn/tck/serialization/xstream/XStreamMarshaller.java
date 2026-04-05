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
package com.gs.dmn.tck.serialization.xstream;

import com.gs.dmn.error.SyntaxErrorException;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.TCKVersion;
import com.gs.dmn.serialization.xstream.DMNExtensionRegister;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.TCKMarshaller;
import com.gs.dmn.tck.validation.ValidationError;

import javax.xml.transform.stream.StreamSource;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.error.DMNErrorHandler.handleError;
import static com.gs.dmn.serialization.TCKVersion.LATEST;
import static com.gs.dmn.serialization.TCKVersion.TCK_1;

public class XStreamMarshaller implements TCKMarshaller {
    private static TCKVersion inferTCKVersion(Reader firstStringReader) {
        return LATEST;
    }

    private TCKVersion inferTCKVersion(TestCases o) {
        return LATEST;
    }

    private final List<DMNExtensionRegister> extensionRegisters = new ArrayList<>();

    private final com.gs.dmn.tck.serialization.xstream.v1.XStreamMarshaller xStream1;

    XStreamMarshaller() {
        this(new ArrayList<>());
    }

    XStreamMarshaller(List<DMNExtensionRegister> extensionRegisters) {
        this.extensionRegisters.addAll(extensionRegisters);

        this.xStream1 = new com.gs.dmn.tck.serialization.xstream.v1.XStreamMarshaller(extensionRegisters);
    }

    @Override
    public TestCases unmarshal(String input, boolean validateSchema) {
        try (Reader firstStringReader = new StringReader(input); Reader secondStringReader = new StringReader(input)) {
            TCKVersion tckVersion = inferTCKVersion(firstStringReader);
            if (validateSchema && tckVersion != null) {
                try (StringReader reader = new StringReader(input)) {
                    List<ValidationError> errors = new XSDSchemaValidator().validateXSDSchema(new StreamSource(reader), LATEST);
                    if (!errors.isEmpty()) {
                        throw new SyntaxErrorException(String.format("%s", errors));
                    }
                }
            }
            return unmarshal(tckVersion, secondStringReader);
        } catch (Exception e) {
            throw handleError(e.getMessage(), e);
        }
    }

    @Override
    public TestCases unmarshal(Reader input, boolean validateSchema) {
        try (BufferedReader buffer = new BufferedReader(input)) {
            String xml = buffer.lines().collect(Collectors.joining("\n"));
            return unmarshal(xml, validateSchema);
        } catch (Exception e) {
            throw handleError(e.getMessage(), e);
        }
    }

    private TestCases unmarshal(TCKVersion inferTCKVersion, Reader secondStringReader) {
        TestCases result = null;
        if (TCK_1.equals(inferTCKVersion)) {
            result = (TestCases) xStream1.unmarshal(secondStringReader);
        }
        return result;
    }

    @Override
    public String marshal(TestCases testCases) {
        if (testCases != null) {
            TCKVersion tckVersion = inferTCKVersion(testCases);
            return marshall(testCases, tckVersion);
        } else {
            throw new DMNRuntimeException(String.format("Error marshalling object {}", testCases));
        }
    }

    @Override
    public void marshal(TestCases testCases, Writer output) {
        if (testCases != null) {
            TCKVersion tckVersion = inferTCKVersion(testCases);
            marshall(testCases, output, tckVersion);
        } else {
            throw new DMNRuntimeException(String.format("Error marshalling object {}", testCases));
        }
    }

    private String marshall(TestCases o, TCKVersion tckVersion) {
        if (tckVersion == TCK_1) {
            return xStream1.marshal(o);
        } else {
            throw new DMNRuntimeException(String.format("Error marshalling object %s for TCK version %s", o, tckVersion));
        }
    }

    private void marshall(TestCases testCases, Writer out, TCKVersion tckVersion) {
        if (tckVersion == TCK_1) {
            xStream1.marshal(testCases, out);
        }
    }
}
