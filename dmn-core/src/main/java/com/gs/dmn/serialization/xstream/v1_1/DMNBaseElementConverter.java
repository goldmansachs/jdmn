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
package com.gs.dmn.serialization.xstream.v1_1;

import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.TDMNElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.dmndi.DiagramElement;
import com.gs.dmn.ast.dmndi.Style;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.CustomStaxReader;
import com.gs.dmn.serialization.xstream.CustomStaxWriter;
import com.gs.dmn.serialization.xstream.v1_2.DMNLabelConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class DMNBaseElementConverter extends DMNBaseConverter {
    private static final Logger LOG = LoggerFactory.getLogger(DMNBaseElementConverter.class);

    public DMNBaseElementConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        DMNBaseElement mib = (DMNBaseElement) parent;

        CustomStaxReader customStaxReader = (CustomStaxReader) reader.underlyingReader();

        mib.setElementInfo(customStaxReader.getElementInfo());

        setAdditionalAttributes(parent, customStaxReader.getAdditionalAttributes());
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        DMNBaseElement mib = (DMNBaseElement) parent;

        CustomStaxWriter staxWriter = ((CustomStaxWriter) writer.underlyingWriter());
        for (Entry<String, String> kv : mib.getElementInfo().getNsContext().entrySet()) {
            try {
                staxWriter.writeNamespace(kv.getKey(), kv.getValue());
            } catch (Exception e) {
                LOG.warn("The XML driver writer failed to manage writing namespace, namespaces prefixes could be wrong in the resulting file.", e);
            }
        }

        Map<QName, String> otherAttributes = getOtherAttributes(parent);
        for (Entry<QName, String> kv : otherAttributes.entrySet()) {
            staxWriter.addAttribute(kv.getKey().getPrefix() + ":" + kv.getKey().getLocalPart(), kv.getValue());
        }

        if (parent instanceof TDefinitions tDefinitions) {
            if (version == DMNVersion.DMN_11) {
                // Do nothing
            } else if (version == DMNVersion.DMN_12 || version == DMNVersion.DMN_13 || version == DMNVersion.DMN_14) {

                String dmndiURI = version.getPrefixToNamespaceMap().get("dmndi");
                String diURI = version.getPrefixToNamespaceMap().get("di");
                String dcURI = version.getPrefixToNamespaceMap().get("dc");

                String dmndiPrefix = tDefinitions.getPrefixForNamespaceURI(dmndiURI).orElse("dmndi");
                String diPrefix = tDefinitions.getPrefixForNamespaceURI(diURI).orElse("di");
                String dcPrefix = tDefinitions.getPrefixForNamespaceURI(dcURI).orElse("dc");

                staxWriter.getQNameMap().registerMapping(new QName(dmndiURI, "DMNDI", dmndiPrefix), "DMNDI");
                staxWriter.getQNameMap().registerMapping(new QName(dmndiURI, "DMNDiagram", dmndiPrefix), "DMNDiagram");
                staxWriter.getQNameMap().registerMapping(new QName(dmndiURI, "DMNStyle", dmndiPrefix), "style");
                staxWriter.getQNameMap().registerMapping(new QName(dmndiURI, "DMNStyle", dmndiPrefix), "DMNStyle");
                staxWriter.getQNameMap().registerMapping(new QName(dmndiURI, "DMNShape", dmndiPrefix), "DMNShape");
                staxWriter.getQNameMap().registerMapping(new QName(dmndiURI, "DMNEdge", dmndiPrefix), "DMNEdge");
                staxWriter.getQNameMap().registerMapping(new QName(dmndiURI, "DMNDecisionServiceDividerLine", dmndiPrefix), "DMNDecisionServiceDividerLine");
                staxWriter.getQNameMap().registerMapping(new QName(dmndiURI, "DMNLabel", dmndiPrefix), "DMNLabel");
                staxWriter.getQNameMap().registerMapping(new QName(dmndiURI, DMNLabelConverter.TEXT, dmndiPrefix), DMNLabelConverter.TEXT);
                staxWriter.getQNameMap().registerMapping(new QName(dmndiURI, "Size", dmndiPrefix), "Size");

                staxWriter.getQNameMap().registerMapping(new QName(dmndiURI, "FillColor", dmndiPrefix), "FillColor");
                staxWriter.getQNameMap().registerMapping(new QName(dmndiURI, "StrokeColor", dmndiPrefix), "StrokeColor");
                staxWriter.getQNameMap().registerMapping(new QName(dmndiURI, "FontColor", dmndiPrefix), "FontColor");

                staxWriter.getQNameMap().registerMapping(new QName(diURI, "waypoint", diPrefix), "waypoint");
                staxWriter.getQNameMap().registerMapping(new QName(diURI, "extension", diPrefix), "extension");
                staxWriter.getQNameMap().registerMapping(new QName(dcURI, "Bounds", dcPrefix), "Bounds");
            } else {
                throw new DMNRuntimeException("Unknown DMN version '%s'".formatted(version));
            }
        }
    }

    private Map<QName, String> getOtherAttributes(Object parent) {
        if (parent instanceof TDMNElement element) {
            return element.getOtherAttributes();
        } else if (parent instanceof DiagramElement element) {
            return element.getOtherAttributes();
        } else if (parent instanceof Style style) {
            return style.getOtherAttributes();
        } else {
            return new LinkedHashMap<>();
        }
    }

    private void setAdditionalAttributes(Object parent, Map<QName, String> additionalAttributes) {
        if (parent instanceof TDMNElement element) {
            element.getOtherAttributes().putAll(additionalAttributes);
        } else if (parent instanceof DiagramElement element) {
            element.getOtherAttributes().putAll(additionalAttributes);
        } else if (parent instanceof Style style) {
            style.getOtherAttributes().putAll(additionalAttributes);
        }
    }
}
