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
import com.gs.dmn.serialization.CustomStaxReader;
import com.gs.dmn.serialization.DMNMarshaller;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XStreamMarshaller implements DMNMarshaller {
    private static final Logger LOGGER = LoggerFactory.getLogger(XStreamMarshaller.class);
    private static final StaxDriver STAX_DRIVER = new StaxDriver();

    private final List<DMNExtensionRegister> extensionRegisters = new ArrayList<>();

    private final com.gs.dmn.serialization.xstream.v1_1.XStreamMarshaller xStream11;
    private final com.gs.dmn.serialization.xstream.v1_2.XStreamMarshaller xStream12;
    private final com.gs.dmn.serialization.xstream.v1_3.XStreamMarshaller xStream13;

    XStreamMarshaller() {
        this(new ArrayList<>());
    }

    XStreamMarshaller(List<DMNExtensionRegister> extensionRegisters) {
        this.extensionRegisters.addAll(extensionRegisters);

        this.xStream11 = new com.gs.dmn.serialization.xstream.v1_1.XStreamMarshaller(extensionRegisters);
        this.xStream12 = new com.gs.dmn.serialization.xstream.v1_2.XStreamMarshaller(extensionRegisters);
        this.xStream13 = new com.gs.dmn.serialization.xstream.v1_3.XStreamMarshaller(extensionRegisters);
    }

    private static DMNVersion inferDMNVersion(Reader from) {
        DMNVersion result = null;
        try {
            XMLStreamReader xmlReader = STAX_DRIVER.getInputFactory().createXMLStreamReader(from);
            CustomStaxReader customStaxReader = new CustomStaxReader(new QNameMap(), xmlReader);
            result = inferDMNVersion(customStaxReader.getNsContext());
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

    public static DMNVersion inferDMNVersion(DMNBaseElement from) {
        DMNVersion result = null;
        try {
            Map<String, String> nsContext = from.getNsContext();
            if (nsContext.values().stream().anyMatch(s -> DMNVersion.DMN_13.getNamespace().equals(s))) {
                result = DMNVersion.DMN_13;
            } else if (nsContext.values().stream().anyMatch(s -> DMNVersion.DMN_12.getNamespace().equals(s))) {
                result = DMNVersion.DMN_12;
            } else if (nsContext.values().stream().anyMatch(s -> DMNVersion.DMN_11.getNamespace().equals(s))) {
                result = DMNVersion.DMN_11;
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
        if (nsContext.values().stream().anyMatch(s -> DMNVersion.DMN_13.getNamespace().equals(s))) {
            result = DMNVersion.DMN_13;
        } else if (nsContext.values().stream().anyMatch(s -> DMNVersion.DMN_12.getNamespace().equals(s))) {
            result = DMNVersion.DMN_12;
        } else if (nsContext.values().stream().anyMatch(s -> DMNVersion.DMN_11.getNamespace().equals(s))) {
            result = DMNVersion.DMN_11;
        }
        return result;
    }

    @Override
    public TDefinitions unmarshal(String input) {
        try (Reader firstStringReader = new StringReader(input); Reader secondStringReader = new StringReader(input)) {
            DMNVersion dmnVersion = inferDMNVersion(firstStringReader);
            return unmarshal(dmnVersion, secondStringReader);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from reader.", e);
        }
        return null;
    }

    @Override
    public TDefinitions unmarshal(File input) {
        try (Reader firstStringReader = new FileReader(input); Reader secondStringReader = new FileReader(input)) {
            DMNVersion dmnVersion = inferDMNVersion(firstStringReader);
            return unmarshal(dmnVersion, secondStringReader);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from reader.", e);
        }
        return null;
    }

    @Override
    public TDefinitions unmarshal(URL input) {
        try (Reader firstStringReader = new InputStreamReader(input.openStream()); Reader secondStringReader = new InputStreamReader(input.openStream())) {
            DMNVersion dmnVersion = inferDMNVersion(firstStringReader);
            return unmarshal(dmnVersion, secondStringReader);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from reader.", e);
        }
        return null;
    }

    @Override
    public TDefinitions unmarshal(InputStream input) {
        try (Reader firstStringReader = new InputStreamReader(input); Reader secondStringReader = new InputStreamReader(input)) {
            DMNVersion dmnVersion = inferDMNVersion(firstStringReader);
            return unmarshal(dmnVersion, secondStringReader);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from reader.", e);
        }
        return null;
    }

    @Override
    public TDefinitions unmarshal(Reader input) {
        try (BufferedReader buffer = new BufferedReader(input)) {
            String xml = buffer.lines().collect(Collectors.joining("\n"));
            return unmarshal(xml);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from reader.", e);
        }
        return null;
    }

    private TDefinitions unmarshal(DMNVersion inferDMNVersion, Reader secondStringReader) {
        TDefinitions result = null;
        if (DMNVersion.DMN_13.equals(inferDMNVersion)) {
            result = xStream13.unmarshal(secondStringReader);
        } else if (DMNVersion.DMN_12.equals(inferDMNVersion)) {
            result = xStream12.unmarshal(secondStringReader);
        } else if (DMNVersion.DMN_11.equals(inferDMNVersion)) {
            result = xStream11.unmarshal(secondStringReader);
        }
        return result;
    }

    @Override
    public String marshal(Object o) {
        if (o instanceof DMNBaseElement) {
            DMNVersion dmnVersion = inferDMNVersion((DMNBaseElement) o);
            return marshall(o, dmnVersion);
        } else {
            LOGGER.error("Error marshalling object {}", o);
        }
        return null;
    }

    @Override
    public void marshal(Object o, File output) {
        try (FileWriter fileWriter = new FileWriter(output)) {
            marshal(o, fileWriter);
        } catch (Exception e) {
            LOGGER.error("Error marshalling object {}", o);
        }
    }

    @Override
    public void marshal(Object o, OutputStream output) {
        try (Writer fileWriter = new OutputStreamWriter(output)) {
            marshal(o, fileWriter);
        } catch (Exception e) {
            LOGGER.error("Error marshalling object {}", o);
        }
    }

    @Override
    public void marshal(Object o, Writer output) {
        if (o instanceof DMNBaseElement) {
            DMNVersion dmnVersion = inferDMNVersion((DMNBaseElement) o);
            marshall(o, output, dmnVersion);
        } else {
            LOGGER.error("Error marshalling object {}", o);
        }
    }

    private String marshall(Object o, DMNVersion dmnVersion) {
        if (dmnVersion == DMNVersion.DMN_13) {
            return xStream13.marshal(o);
        } else if (dmnVersion == DMNVersion.DMN_12) {
            return xStream12.marshal(o);
        } else if (dmnVersion == DMNVersion.DMN_11) {
            return xStream11.marshal(o);
        } else {
            return null;
        }
    }

    private void marshall(Object o, Writer out, DMNVersion dmnVersion) {
        if (dmnVersion == DMNVersion.DMN_13) {
            xStream13.marshal(o, out);
        } else if (dmnVersion == DMNVersion.DMN_12) {
            xStream12.marshal(o, out);
        } else if (dmnVersion == DMNVersion.DMN_11) {
            xStream11.marshal(o, out);
        }
    }
}
