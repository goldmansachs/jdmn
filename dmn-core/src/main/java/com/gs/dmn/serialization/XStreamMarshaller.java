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

import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamReader;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
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

    public XStreamMarshaller() {
        this(new ArrayList<>());
    }

    public XStreamMarshaller(List<DMNExtensionRegister> extensionRegisters) {
        this.extensionRegisters.addAll(extensionRegisters);

        this.xStream11 = new com.gs.dmn.serialization.xstream.v1_1.XStreamMarshaller(extensionRegisters);
        this.xStream12 = new com.gs.dmn.serialization.xstream.v1_2.XStreamMarshaller(extensionRegisters);
    }

    private static DMNVersion inferDMNVersion(Reader from) {
        DMNVersion result = null;
        try {
            XMLStreamReader xmlReader = STAX_DRIVER.getInputFactory().createXMLStreamReader(from);
            CustomStaxReader customStaxReader = new CustomStaxReader(new QNameMap(), xmlReader);
            Map<String, String> nsContext = customStaxReader.getNsContext();
            if (nsContext.values().stream().anyMatch(s -> DMNVersion.DMN_12.getNamespace().equals(s))) {
                result = DMNVersion.DMN_12;
            } else if (nsContext.values().stream().anyMatch(s -> DMNVersion.DMN_11.getNamespace().equals(s))) {
                result = DMNVersion.DMN_11;
            }

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

    private static DMNVersion inferDMNVersion(DMNBaseElement from) {
        DMNVersion result = null;
        try {
            Map<String, String> nsContext = from.getNsContext();
            if (nsContext.values().stream().anyMatch(s -> DMNVersion.DMN_12.getNamespace().equals(s))) {
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

    @Override
    public TDefinitions<DMNContext> unmarshal(String xml) {
        try (Reader firstStringReader = new StringReader(xml); Reader secondStringReader = new StringReader(xml)) {
            DMNVersion inferDMNVersion = inferDMNVersion(firstStringReader);

            TDefinitions<DMNContext> result = null;
            if (DMNVersion.DMN_12.equals(inferDMNVersion)) {
                result = xStream12.unmarshal(secondStringReader);
            } else if (DMNVersion.DMN_11.equals(inferDMNVersion)) {
                result = xStream11.unmarshal(secondStringReader);
            }
            return result;
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from reader.", e);
        }
        return null;
    }

    @Override
    public TDefinitions<DMNContext> unmarshal(Reader isr) {
        try (BufferedReader buffer = new BufferedReader(isr)) {
            String xml = buffer.lines().collect(Collectors.joining("\n"));
            return unmarshal(xml);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from reader.", e);
        }
        return null;
    }

    @Override
    public String marshal(Object o) {
        if (o instanceof DMNBaseElement) {
            DMNVersion dmnVersion = inferDMNVersion((DMNBaseElement) o);
            if (dmnVersion == DMNVersion.DMN_12) {
                return xStream12.marshal(o);
            } else if (dmnVersion == DMNVersion.DMN_11) {
                return xStream11.marshal(o);
            }
        } else {
            LOGGER.error("Error marshalling object {}", o);
        }
        return null;
    }

    @Override
    public void marshal(Object o, Writer out) {
        if (o instanceof DMNBaseElement) {
            DMNVersion dmnVersion = inferDMNVersion((DMNBaseElement) o);
            if (dmnVersion == DMNVersion.DMN_12) {
                xStream12.marshal(o, out);
            } else if (dmnVersion == DMNVersion.DMN_11) {
                xStream11.marshal(o, out);
            }
        } else {
            LOGGER.error("Error marshalling object {}", o);
        }
    }
}
