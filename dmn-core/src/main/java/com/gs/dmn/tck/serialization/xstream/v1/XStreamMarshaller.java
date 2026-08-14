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

import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.TCKVersion;
import com.gs.dmn.serialization.xstream.*;
import com.gs.dmn.serialization.xstream.v1_1.ExtensionElementsConverter;
import com.gs.dmn.serialization.xstream.v1_1.QNameConverter;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XStreamMarshaller implements SimpleXStreamMarshaller {
    private static final Logger LOGGER = LoggerFactory.getLogger(XStreamMarshaller.class);

    private static final StaxDriver STAX_DRIVER = makeStaxDriver();

    protected static StaxDriver makeStaxDriver() {
        StaxDriver driver = new SafeStaxDriver() {
            @Override
            public AbstractPullReader createStaxReader(XMLStreamReader in) {
                return new CustomStaxReader(getQnameMap(), in);
            }

            @Override
            public StaxWriter createStaxWriter(XMLStreamWriter out, boolean writeStartEndDocument) throws XMLStreamException {
                return new CustomStaxWriter(newQNameMap(TCKVersion.TCK_1), out, false, isRepairingNamespace(), getNameCoder());
            }
        };
        QNameMap nameMap = newQNameMap(TCKVersion.TCK_1);
        driver.setQnameMap(nameMap);
        driver.setRepairingNamespace(false);

        return driver;
    }

    private static QNameMap newQNameMap(TCKVersion version) {
        QNameMap nameMap = new QNameMap();
        nameMap.setDefaultNamespace(version.getNamespace());
        return nameMap;
    }

    private final List<DMNExtensionRegister> extensionRegisters = new ArrayList<>();

    public XStreamMarshaller() {
    }

    public XStreamMarshaller(List<DMNExtensionRegister> extensionRegisters) {
        this.extensionRegisters.addAll(extensionRegisters);
    }

    @Override
    public String marshal(Object o) {
        try (
                Writer writer = new StringWriter();
                CustomStaxWriter hsWriter = (CustomStaxWriter) STAX_DRIVER.createWriter(writer)) {

            XStream xStream = newXStream();
            if (o instanceof TCKBaseElement base) {
                String dmnPrefix = base.getElementInfo().getNsContext().entrySet().stream().filter(kv -> TCKVersion.TCK_1.getNamespace().equals(kv.getValue())).findFirst().map(Map.Entry::getKey).orElse("");
                hsWriter.getQNameMap().setDefaultPrefix(dmnPrefix);
            }
            extensionRegisters.forEach(r -> r.beforeMarshal(o, hsWriter.getQNameMap()));
            xStream.marshal(o, hsWriter);
            hsWriter.flush();
            return writer.toString();
        } catch (Exception e) {
            logError("Error marshalling {} to XML.", artifactName(), e);
        }
        return null;
    }

    @Override
    public void logError(String message, Object argument1, Exception exception) {
        LOGGER.error(message, argument1, exception);
    }

    @Override
    public void logError(String message, Object argument1, Object argument2, Exception exception) {
        LOGGER.error(message, argument1, argument2, exception);
    }

    @Override
    public String artifactName() {
        return "TCK model";
    }

    @Override
    public XStream newXStream() {
        XStream xStream = XStreamUtils.createNonTrustingXStream(STAX_DRIVER, TestCases.class.getClassLoader(), DMNXStream::from);
        xStream.addPermission(new TypeHierarchyPermission(QName.class));
        xStream.addPermission(new TypeHierarchyPermission(TCKBaseElement.class));

        xStream.alias("testCases", TestCases.class);
        xStream.alias(TestCasesConverter.TEST_CASE, TestCase.class);
        xStream.alias(TestCaseConverter.INPUT_NODE, InputNode.class);
        xStream.alias(TestCaseConverter.RESULT_NODE, ResultNode.class);
        xStream.alias(ValueTypeConverter.VALUE, AnySimpleType.class);
        xStream.alias(ValueTypeConverter.COMPONENT, Component.class);
        xStream.alias(ValueTypeConverter.LIST, com.gs.dmn.tck.ast.List.class);
        xStream.alias(ListConverter.ITEM, ValueType.class);
        xStream.alias(TestCasesConverter.LABELS, Labels.class);
        xStream.alias(TCKBaseElementConverter.EXTENSION_ELEMENTS, ExtensionElements.class);

        xStream.alias(TestCasesConverter.TEST_CASES_NAME, String.class);
        xStream.alias(TestCasesConverter.MODEL_NAME, String.class);
        xStream.alias(TestCaseConverter.DESCRIPTION, String.class);
        xStream.alias(ResultNodeConverter.EXPECTED, ValueType.class);
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

        xStream.registerConverter(new QNameConverter(DMNVersion.DMN_13));
        xStream.registerConverter(new ExtensionElementsConverter(xStream, DMNVersion.DMN_13, extensionRegisters));
        xStream.registerConverter(new AnySimpleTypeConverter(xStream));

        for (DMNExtensionRegister extensionRegister : extensionRegisters) {
            extensionRegister.registerExtensionConverters(xStream);
        }

        xStream.ignoreUnknownElements();
        return xStream;
    }
}
