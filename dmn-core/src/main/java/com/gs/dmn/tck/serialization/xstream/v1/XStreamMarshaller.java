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

import com.gs.dmn.serialization.TCKVersion;
import com.gs.dmn.serialization.xstream.*;
import com.gs.dmn.serialization.xstream.v1_3.ExtensionElementsConverter;
import com.gs.dmn.serialization.xstream.v1_3.QNameConverter;
import com.gs.dmn.tck.ast.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.AbstractPullReader;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.StaxWriter;
import com.thoughtworks.xstream.security.TypeHierarchyPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XStreamMarshaller implements SimpleDMNMarshaller {
    private static final Logger LOGGER = LoggerFactory.getLogger(XStreamMarshaller.class);

    private static final StaxDriver STAX_DRIVER;

    static {
        STAX_DRIVER = new StaxDriver() {
            @Override
            public AbstractPullReader createStaxReader(XMLStreamReader in) {
                return new CustomStaxReader(getQnameMap(), in);
            }

            @Override
            public StaxWriter createStaxWriter(XMLStreamWriter out, boolean writeStartEndDocument) throws XMLStreamException {
                return new CustomStaxWriter(newQNameMap(), out, false, isRepairingNamespace(), getNameCoder());
            }

            public QNameMap newQNameMap() {
                QNameMap qmap = new QNameMap();
                configureQNameMap(qmap);
                return qmap;
            }
        };
        QNameMap nameMap = new QNameMap();
        configureQNameMap(nameMap);
        STAX_DRIVER.setQnameMap(nameMap);
        STAX_DRIVER.setRepairingNamespace(false);
    }

    public static void configureQNameMap(QNameMap nameMap) {
        nameMap.setDefaultNamespace(TCKVersion.TCK_1.getNamespace());
    }

    private final List<DMNExtensionRegister> extensionRegisters = new ArrayList<>();

    public XStreamMarshaller() {
    }

    public XStreamMarshaller(List<DMNExtensionRegister> extensionRegisters) {
        this.extensionRegisters.addAll(extensionRegisters);
    }

    @Override
    public Object unmarshal(String input) {
        return unmarshal(new StringReader(input));
    }

    @Override
    public Object unmarshal(File input) {
        try {
            XStream xStream = newXStream();
            return xStream.fromXML(input);
        } catch (Exception e) {
            LOGGER.error(String.format("Error unmarshalling DMN model from file '%s'.", input.getAbsolutePath()), e);
        }
        return null;
    }

    @Override
    public Object unmarshal(URL input) {
        try {
            XStream xStream = newXStream();
            return xStream.fromXML(input);
        } catch (Exception e) {
            LOGGER.error(String.format("Error unmarshalling DMN model from file '%s'.", input), e);
        }
        return null;
    }

    @Override
    public Object unmarshal(InputStream input) {
        try {
            XStream xStream = newXStream();
            return xStream.fromXML(input);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from input.", e);
        }
        return null;
    }

    @Override
    public Object unmarshal(Reader input) {
        try {
            XStream xStream = newXStream();
            return xStream.fromXML(input);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from reader.", e);
        }
        return null;
    }

    @Override
    public String marshal(Object o) {
        try (
                Writer writer = new StringWriter();
                CustomStaxWriter hsWriter = (CustomStaxWriter) STAX_DRIVER.createWriter(writer)) {

            XStream xStream = newXStream();
            if (o instanceof DMNBaseElement) {
                DMNBaseElement base = (DMNBaseElement) o;
                String dmnPrefix = base.getElementInfo().getNsContext().entrySet().stream().filter(kv -> TCKVersion.TCK_1.getNamespace().equals(kv.getValue())).findFirst().map(Map.Entry::getKey).orElse("");
                hsWriter.getQNameMap().setDefaultPrefix(dmnPrefix);
            }
            extensionRegisters.forEach(r -> r.beforeMarshal(o, hsWriter.getQNameMap()));
            xStream.marshal(o, hsWriter);
            hsWriter.flush();
            return writer.toString();
        } catch (Exception e) {
            LOGGER.error("Error marshalling DMN model to XML.", e);
        }
        return null;
    }

    @Override
    public void marshal(Object o, File output) {
        try (FileWriter fileWriter = new FileWriter(output)) {
            marshal(o, fileWriter);
        } catch (IOException e) {
            LOGGER.error("Error marshalling DMN model to XML.", e);
        }
    }

    @Override
    public void marshal(Object o, OutputStream output) {
        try (OutputStreamWriter streamWriter = new OutputStreamWriter(output)) {
            marshal(o, streamWriter);
        } catch (Exception e) {
            LOGGER.error("Error marshalling DMN model to XML.", e);
        }
    }

    @Override
    public void marshal(Object o, Writer output) {
        try {
            output.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            output.write(marshal(o));
        } catch (Exception e) {
            LOGGER.error("Error marshalling DMN model to XML.", e);
        }
    }

    private XStream newXStream() {
        XStream xStream = XStreamUtils.createNonTrustingXStream(STAX_DRIVER, TestCases.class.getClassLoader(), DMNXStream::from);
        xStream.addPermission(new TypeHierarchyPermission(QName.class));
        xStream.addPermission(new TypeHierarchyPermission(DMNBaseElement.class));

        xStream.alias("testCases", TestCases.class);
        xStream.alias(TestCasesConverter.TEST_CASE, TestCase.class);
        xStream.alias(TestCaseConverter.INPUT_NODE, InputNode.class);
        xStream.alias(TestCaseConverter.RESULT_NODE, ResultNode.class);
        xStream.alias(ValueTypeConverter.VALUE, AnySimpleType.class);
        xStream.alias(ValueTypeConverter.COMPONENT, Component.class);
        xStream.alias(ValueTypeConverter.LIST, com.gs.dmn.tck.ast.List.class);
        xStream.alias(ListConverter.ITEM, ValueType.class);
        xStream.alias(TestCasesConverter.LABELS, Labels.class);
        xStream.alias(DMNBaseElementConverter.EXTENSION_ELEMENTS, ExtensionElements.class);

        xStream.alias(TestCasesConverter.MODEL_NAME, String.class);
        xStream.alias(TestCaseConverter.DESCRIPTION, String.class);
        xStream.alias(ResultNodeConverter.EXPECTED, ValueType.class);
        xStream.alias(ResultNodeConverter.COMPUTED, ValueType.class);
        xStream.alias(LabelsConverter.LABEL, String.class);

        // Register converters
        xStream.registerConverter(new TestCasesConverter(xStream));
        xStream.registerConverter(new TestCaseConverter(xStream));
        xStream.registerConverter(new InputNodeConverter(xStream));
        xStream.registerConverter(new ResultNodeConverter(xStream));
        xStream.registerConverter(new ValueTypeConverter(xStream));
        xStream.registerConverter(new ListConverter(xStream));
        xStream.registerConverter(new ComponentConverter(xStream));
        xStream.registerConverter(new LabelsConverter(xStream));

        xStream.registerConverter(new QNameConverter());
        xStream.registerConverter(new ExtensionElementsConverter(xStream, extensionRegisters));
        xStream.registerConverter(new AnySimpleTypeConverter(xStream));

        for (DMNExtensionRegister extensionRegister : extensionRegisters) {
            extensionRegister.registerExtensionConverters(xStream);
        }

        xStream.ignoreUnknownElements();
        return xStream;
    }
}
