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
package com.gs.dmn.serialization.xstream;

import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.DMNMarshaller;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gs.dmn.serialization.DMNVersion.*;

public class XStreamMarshaller implements DMNMarshaller {
    private static final Logger LOGGER = LoggerFactory.getLogger(XStreamMarshaller.class);
    private static final StaxDriver STAX_DRIVER = new StaxDriver();

    public static DMNVersion inferDMNVersion(DMNBaseElement from) {
        DMNVersion result = null;
        try {
            Map<String, String> nsContext = from.getElementInfo().getNsContext();
            if (nsContext.values().stream().anyMatch(s -> DMN_14.getNamespace().equals(s))) {
                result = DMN_14;
            } else if (nsContext.values().stream().anyMatch(s -> DMN_13.getNamespace().equals(s))) {
                result = DMN_13;
            } else if (nsContext.values().stream().anyMatch(s -> DMN_12.getNamespace().equals(s))) {
                result = DMN_12;
            } else if (nsContext.values().stream().anyMatch(s -> DMN_11.getNamespace().equals(s))) {
                result = DMN_11;
            }

            if (result == null) {
                throw new DMNRuntimeException("Cannot infer version of DMN");
            }
            return result;
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from reader.", e);
        }
        return result;
    }

    private static DMNVersion inferDMNVersion(Map<String, String> nsContext) {
        DMNVersion result = null;
        if (nsContext.values().stream().anyMatch(s -> DMN_14.getNamespace().equals(s))) {
            result = DMN_14;
        } else if (nsContext.values().stream().anyMatch(s -> DMN_13.getNamespace().equals(s))) {
            result = DMN_13;
        } else if (nsContext.values().stream().anyMatch(s -> DMN_12.getNamespace().equals(s))) {
            result = DMN_12;
        } else if (nsContext.values().stream().anyMatch(s -> DMN_11.getNamespace().equals(s))) {
            result = DMN_11;
        }
        return result;
    }

    private static DMNVersion inferDMNVersion(Reader from) {
        DMNVersion result = null;
        try {
            XMLStreamReader xmlReader = STAX_DRIVER.getInputFactory().createXMLStreamReader(from);
            CustomStaxReader customStaxReader = new CustomStaxReader(new QNameMap(), xmlReader);
            result = inferDMNVersion(customStaxReader.getElementInfo().getNsContext());
            xmlReader.close();
            customStaxReader.close();

            if (result == null) {
                throw new DMNRuntimeException("Cannot infer version of DMN");
            }
            return result;
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from reader.", e);
        }
        return result;
    }

    private final List<DMNExtensionRegister> extensionRegisters = new ArrayList<>();

    private final com.gs.dmn.serialization.xstream.v1_1.XStreamMarshaller xStream11;
    private final com.gs.dmn.serialization.xstream.v1_2.XStreamMarshaller xStream12;
    private final com.gs.dmn.serialization.xstream.v1_3.XStreamMarshaller xStream13;
    private final com.gs.dmn.serialization.xstream.v1_4.XStreamMarshaller xStream14;

    XStreamMarshaller() {
        this(new ArrayList<>());
    }

    XStreamMarshaller(List<DMNExtensionRegister> extensionRegisters) {
        this.extensionRegisters.addAll(extensionRegisters);

        this.xStream11 = new com.gs.dmn.serialization.xstream.v1_1.XStreamMarshaller(extensionRegisters);
        this.xStream12 = new com.gs.dmn.serialization.xstream.v1_2.XStreamMarshaller(extensionRegisters);
        this.xStream13 = new com.gs.dmn.serialization.xstream.v1_3.XStreamMarshaller(extensionRegisters);
        this.xStream14 = new com.gs.dmn.serialization.xstream.v1_4.XStreamMarshaller(extensionRegisters);
    }

    @Override
    public TDefinitions unmarshal(String input, boolean validateSchema) {
        try (Reader firstStringReader = new StringReader(input); Reader secondStringReader = new StringReader(input)) {
            DMNVersion dmnVersion = inferDMNVersion(firstStringReader);
            if (validateSchema && dmnVersion != null) {
                try (StringReader reader = new StringReader(input)) {
                    validateXMLSchema(new StreamSource(reader), dmnVersion.getSchemaLocation());
                }
            }
            return unmarshal(dmnVersion, secondStringReader);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from reader.", e);
        }
        return null;
    }

    @Override
    public TDefinitions unmarshal(Reader input, boolean validateSchema) {
        try (BufferedReader buffer = new BufferedReader(input)) {
            String xml = buffer.lines().collect(Collectors.joining("\n"));
            return unmarshal(xml, validateSchema);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from reader.", e);
        }
        return null;
    }

    private TDefinitions unmarshal(DMNVersion inferDMNVersion, Reader secondStringReader) {
        TDefinitions result = null;
        if (DMN_14.equals(inferDMNVersion)) {
            result = (TDefinitions) xStream14.unmarshal(secondStringReader);
        } else if (DMN_13.equals(inferDMNVersion)) {
            result = (TDefinitions) xStream13.unmarshal(secondStringReader);
        } else if (DMN_12.equals(inferDMNVersion)) {
            result = (TDefinitions) xStream12.unmarshal(secondStringReader);
        } else if (DMN_11.equals(inferDMNVersion)) {
            result = (TDefinitions) xStream11.unmarshal(secondStringReader);
        }
        return result;
    }

    @Override
    public String marshal(TDefinitions o) {
        if (o != null) {
            DMNVersion dmnVersion = inferDMNVersion(o);
            return marshall(o, dmnVersion);
        } else {
            LOGGER.error("Error marshalling object {}", o);
        }
        return null;
    }

    @Override
    public void marshal(TDefinitions o, Writer output) {
        if (o != null) {
            DMNVersion dmnVersion = inferDMNVersion(o);
            marshall(o, output, dmnVersion);
        } else {
            LOGGER.error("Error marshalling object {}", o);
        }
    }

    private String marshall(TDefinitions o, DMNVersion dmnVersion) {
        if (dmnVersion == DMN_14) {
            return xStream14.marshal(o);
        } else if (dmnVersion == DMN_13) {
            return xStream13.marshal(o);
        } else if (dmnVersion == DMN_12) {
            return xStream12.marshal(o);
        } else if (dmnVersion == DMN_11) {
            return xStream11.marshal(o);
        } else {
            return null;
        }
    }

    private void marshall(TDefinitions o, Writer out, DMNVersion dmnVersion) {
        if (dmnVersion == DMN_14) {
            xStream14.marshal(o, out);
        } else if (dmnVersion == DMN_13) {
            xStream13.marshal(o, out);
        } else if (dmnVersion == DMN_12) {
            xStream12.marshal(o, out);
        } else if (dmnVersion == DMN_11) {
            xStream11.marshal(o, out);
        }
    }

    private boolean validateXMLSchema(Source source, String schemaPath) {
        try {
            URL schemaURL = this.getClass().getClassLoader().getResource(schemaPath).toURI().toURL();
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            // Prohibit the use of all protocols by external entities:
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "file");
            Schema schema = factory.newSchema(schemaURL);
            Validator validator = schema.newValidator();
            validator.validate(source);
            return true;
        } catch (Exception e) {
            LOGGER.error(String.format("Invalid DMN file: %s", e.getMessage()));
            throw new DMNRuntimeException(e);
        }
    }
}
