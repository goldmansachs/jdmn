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
import com.gs.dmn.ast.dmndi.*;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
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

public abstract class VersionXStreamMarshaller implements SimpleDMNMarshaller {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionXStreamMarshaller.class);

    protected static StaxDriver makeStaxDriver(DMNVersion version) {
        StaxDriver driver = new SafeStaxDriver() {
            @Override
            public AbstractPullReader createStaxReader(XMLStreamReader in) {
                return new CustomStaxReader(getQnameMap(), in);
            }

            @Override
            public StaxWriter createStaxWriter(XMLStreamWriter out, boolean writeStartEndDocument) throws XMLStreamException {
                return new CustomStaxWriter(newQNameMap(version), out, false, isRepairingNamespace(), getNameCoder());
            }
        };
        QNameMap nameMap = newQNameMap(version);
        driver.setQnameMap(nameMap);
        driver.setRepairingNamespace(false);

        return driver;
    }

    private static QNameMap newQNameMap(DMNVersion version) {
        QNameMap nameMap = new QNameMap();
        nameMap.setDefaultNamespace(version.getNamespace());
        return nameMap;
    }

    protected final DMNVersion version;
    protected final List<DMNExtensionRegister> extensionRegisters = new ArrayList<>();

    protected VersionXStreamMarshaller(DMNVersion version) {
        this.version = version;
    }

    protected VersionXStreamMarshaller(DMNVersion version, List<DMNExtensionRegister> extensionRegisters) {
        this.version = version;
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
            LOGGER.error(String.format("Error unmarshalling DMN model from url '%s'.", input), e);
        }
        return null;
    }

    @Override
    public Object unmarshal(InputStream input) {
        try {
            XStream xStream = newXStream();
            return xStream.fromXML(input);
        } catch (Exception e) {
            LOGGER.error("Error unmarshalling DMN model from input stream.", e);
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
                CustomStaxWriter hsWriter = (CustomStaxWriter) getStaxDriver().createWriter(writer)) {

            XStream xStream = newXStream();
            if (o instanceof DMNBaseElement base) {
                String dmnPrefix = base.getElementInfo().getNsContext().entrySet().stream().filter(kv -> version.getNamespace().equals(kv.getValue())).findFirst().map(Map.Entry::getKey).orElse("");
                hsWriter.getQNameMap().setDefaultPrefix(dmnPrefix);
            }
            this.extensionRegisters.forEach(r -> r.beforeMarshal(o, hsWriter.getQNameMap()));
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

    protected XStream createXStream() {
        XStream xStream = XStreamUtils.createNonTrustingXStream(getStaxDriver(), TDefinitions.class.getClassLoader(), DMNXStream::from);
        xStream.addPermission(new TypeHierarchyPermission(QName.class));
        xStream.addPermission(new TypeHierarchyPermission(DMNBaseElement.class));
        return xStream;
    }

    protected void registerExtensionConverters(XStream xStream) {
        for (DMNExtensionRegister extensionRegister : extensionRegisters) {
            extensionRegister.registerExtensionConverters(xStream);
        }
    }

    protected abstract XStream newXStream();

    protected void registerCommonDMNDIParts(XStream xStream) {
        xStream.alias("DMNDI", DMNDI.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DMNDIConverter(xStream, this.version));
        xStream.alias("DMNDiagram", DMNDiagram.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DMNDiagramConverter(xStream, this.version));
        xStream.alias("DMNStyle", DMNStyle.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DMNStyleConverter(xStream, this.version));
        xStream.alias("Size", Dimension.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DimensionConverter(xStream, this.version));
        xStream.alias("DMNShape", DMNShape.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DMNShapeConverter(xStream, this.version));
        xStream.alias("FillColor", Color.class);
        xStream.alias("StrokeColor", Color.class);
        xStream.alias("FontColor", Color.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.ColorConverter(xStream, this.version));
        xStream.alias("Bounds", Bounds.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.BoundsConverter(xStream, this.version));
        xStream.alias("DMNLabel", DMNLabel.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DMNLabelConverter(xStream, this.version));
        xStream.alias("DMNEdge", DMNEdge.class);
        xStream.alias("DMNDecisionServiceDividerLine", DMNDecisionServiceDividerLine.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DMNDecisionServiceDividerLineConverter(xStream, this.version));
        xStream.alias("waypoint", Point.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.PointConverter(xStream, this.version));
        xStream.alias("extension", DiagramElement.Extension.class);
        xStream.alias(com.gs.dmn.serialization.xstream.v1_2.DMNLabelConverter.TEXT, String.class);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_2.DiagramElementExtensionConverter(xStream, this.version, extensionRegisters));
    }

    protected void register13DMNDIParts(XStream xStream) {
        registerCommonDMNDIParts(xStream);
        xStream.registerConverter(new com.gs.dmn.serialization.xstream.v1_3.DMNEdgeConverter(xStream, this.version));
    }

    protected abstract HierarchicalStreamDriver getStaxDriver();
}
