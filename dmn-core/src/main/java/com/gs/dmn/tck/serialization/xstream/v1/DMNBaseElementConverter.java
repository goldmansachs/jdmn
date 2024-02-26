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

import com.gs.dmn.serialization.xstream.CustomStaxReader;
import com.gs.dmn.serialization.xstream.CustomStaxWriter;
import com.gs.dmn.tck.ast.*;
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

public abstract class DMNBaseElementConverter extends TCKBaseConverter {
    private static final Logger LOG = LoggerFactory.getLogger(DMNBaseElementConverter.class);

    static final String EXTENSION_ELEMENTS = "extensionElements";

    protected XStream xstream;

    public DMNBaseElementConverter(XStream xstream) {
        super(xstream.getMapper());
        this.xstream = xstream;
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        if (child instanceof ExtensionElements elements) {
            setExtensionElements(parent, elements);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        TCKBaseElement element = (TCKBaseElement) parent;
        CustomStaxReader customStaxReader = (CustomStaxReader) reader.underlyingReader();
        element.setElementInfo(customStaxReader.getElementInfo());
        setAdditionalAttributes(parent, customStaxReader.getAdditionalAttributes());
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        ExtensionElements extensionElements = getExtensionElements(parent);
        if (extensionElements != null) {
            writeChildrenNode(writer, context, extensionElements, EXTENSION_ELEMENTS);
        }
        // no call to super as super is abstract method.
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        TCKBaseElement element = (TCKBaseElement) parent;

        CustomStaxWriter staxWriter = ((CustomStaxWriter) writer.underlyingWriter());
        for (Entry<String, String> kv : element.getElementInfo().getNsContext().entrySet()) {
            try {
                staxWriter.writeNamespace(kv.getKey(), kv.getValue());
            } catch (Exception e) {
                LOG.warn("The XML driver writer failed to manage writing namespace, namespaces prefixes could be wrong in the resulting file.", e);
            }
        }

        Map<QName, String> otherAttributes = getOtherAttributes(element);
        for (Entry<QName, String> kv : otherAttributes.entrySet()) {
            staxWriter.addAttribute(kv.getKey().getPrefix() + ":" + kv.getKey().getLocalPart(), kv.getValue());
        }
    }

    private ExtensionElements getExtensionElements(Object parent) {
        if (parent instanceof TestCase case1) {
            return case1.getExtensionElements();
        } else if (parent instanceof ValueType type) {
            return type.getExtensionElements();
        } else {
            return null;
        }
    }

    private void setExtensionElements(Object parent, ExtensionElements extensionElements) {
        if (extensionElements != null && !extensionElements.getAny().isEmpty()) {
            if (parent instanceof TestCase case1) {
                case1.getExtensionElements().getAny().addAll(extensionElements.getAny());
            } else if (parent instanceof ValueType type) {
                type.getExtensionElements().getAny().addAll(extensionElements.getAny());
            }
        }
    }

    private Map<QName, String> getOtherAttributes(Object parent) {
        if (parent instanceof TestCases cases) {
            return cases.getOtherAttributes();
        } else if (parent instanceof TestCase case1) {
            return case1.getOtherAttributes();
        } else if (parent instanceof ValueType type) {
            return type.getOtherAttributes();
        } else if (parent instanceof ResultNode node) {
            return node.getOtherAttributes();
        } else if (parent instanceof AnySimpleType type) {
            return type.getOtherAttributes();
        } else {
            return new LinkedHashMap<>();
        }
    }

    private void setAdditionalAttributes(Object parent, Map<QName, String> additionalAttributes) {
        if (parent instanceof TestCases cases) {
            cases.getOtherAttributes().putAll(additionalAttributes);
        } else if (parent instanceof TestCase case1) {
            case1.getOtherAttributes().putAll(additionalAttributes);
        } else if (parent instanceof ValueType type) {
            type.getOtherAttributes().putAll(additionalAttributes);
        } else if (parent instanceof ResultNode node) {
            node.getOtherAttributes().putAll(additionalAttributes);
        } else if (parent instanceof AnySimpleType type) {
            type.getOtherAttributes().putAll(additionalAttributes);
        }
    }
}
