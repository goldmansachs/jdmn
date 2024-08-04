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

import com.gs.dmn.serialization.TCKVersion;
import com.gs.dmn.serialization.xstream.DMNExtensionRegister;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.TCKMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.gs.dmn.serialization.TCKVersion.LATEST;
import static com.gs.dmn.serialization.TCKVersion.TCK_1;

public class XStreamMarshaller implements TCKMarshaller {
    private static final Logger LOGGER = LoggerFactory.getLogger(XStreamMarshaller.class);

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
            if (validateSchema) {
                try (StringReader reader = new StringReader(input)) {
                    validateXMLSchema(new StreamSource(reader), tckVersion.getSchemaLocation());
                }
            }
            return unmarshal(tckVersion, secondStringReader);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling TCK content from String.", e);
        }
        return null;
    }

    @Override
    public TestCases unmarshal(File input, boolean validateSchema) {
        try (Reader firstStringReader = new FileReader(input); Reader secondStringReader = new FileReader(input)) {
            TCKVersion tckVersion = inferTCKVersion(firstStringReader);
            if (validateSchema) {
                try (FileInputStream inputStream = new FileInputStream(input)) {
                    validateXMLSchema(new StreamSource(inputStream), tckVersion.getSchemaLocation());
                }
            }
            return unmarshal(tckVersion, secondStringReader);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling TCK content from File.", e);
        }
        return null;
    }

    @Override
    public TestCases unmarshal(Reader input, boolean validateSchema) {
        try (BufferedReader buffer = new BufferedReader(input)) {
            String xml = buffer.lines().collect(Collectors.joining("\n"));
            return unmarshal(xml, validateSchema);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling TCK content from Reader.", e);
        }
        return null;
    }

    private TestCases unmarshal(TCKVersion inferTCKVersion, Reader secondStringReader) {
        TestCases result = null;
        if (TCK_1.equals(inferTCKVersion)) {
            result = (TestCases) xStream1.unmarshal(secondStringReader);
        }
        return result;
    }

    @Override
    public String marshal(TestCases o) {
        if (o != null) {
            TCKVersion tckVersion = inferTCKVersion(o);
            return marshall(o, tckVersion);
        } else {
            LOGGER.error("Error marshalling object {}", o);
        }
        return null;
    }

    @Override
    public void marshal(TestCases o, File output) {
        try (FileWriter fileWriter = new FileWriter(output)) {
            marshal(o, fileWriter);
        } catch (Exception e) {
            LOGGER.error("Error marshalling object {}", o);
        }
    }

    @Override
    public void marshal(TestCases o, Writer output) {
        if (o != null) {
            TCKVersion tckVersion = inferTCKVersion(o);
            marshall(o, output, tckVersion);
        } else {
            LOGGER.error("Error marshalling object {}", o);
        }
    }

    private String marshall(TestCases o, TCKVersion tckVersion) {
        if (tckVersion == TCK_1) {
            return xStream1.marshal(o);
        } else {
            return null;
        }
    }

    private void marshall(TestCases o, Writer out, TCKVersion tckVersion) {
        if (tckVersion == TCK_1) {
            xStream1.marshal(o, out);
        }
    }

    private boolean validateXMLSchema(Source source, String schemaPath) {
        try {
            URL schemaURL = this.getClass().getClassLoader().getResource(schemaPath).toURI().toURL();
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            // Prohibit the use of all protocols by external entities:
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            Schema schema = factory.newSchema(schemaURL);
            Validator validator = schema.newValidator();
            validator.validate(source);
            return true;
        } catch (Exception e) {
            LOGGER.error("Invalid XML file: " + e.getMessage());
            return false;
        }
    }
}
