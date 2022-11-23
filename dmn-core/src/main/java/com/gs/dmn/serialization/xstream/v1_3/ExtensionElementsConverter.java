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
package com.gs.dmn.serialization.xstream.v1_3;

import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.TDMNElement;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.DMNExtensionRegister;
import com.gs.dmn.serialization.xstream.dom.DomConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.core.util.HierarchicalStreams;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class ExtensionElementsConverter extends DMNBaseElementConverter {
    private static final Logger LOG = LoggerFactory.getLogger(ExtensionElementsConverter.class);

    private final List<DMNExtensionRegister> extensionRegisters = new ArrayList<>();

    public ExtensionElementsConverter(XStream xStream, DMNVersion version, List<DMNExtensionRegister> extensionRegisters) {
        super(xStream, version);
        if (!extensionRegisters.isEmpty()) {
            this.extensionRegisters.addAll(extensionRegisters);
        }
    }

    public ExtensionElementsConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TDMNElement.ExtensionElements.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TDMNElement.ExtensionElements();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        DMNBaseElement obj = createModelObject();
        assignAttributes(reader, obj);
        // do as default behavior, but in case cannot unmarshall an extension element child, just skip it.
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            String nodeName = reader.getNodeName();
            try {
                Object object = readObject(reader, context, obj);
                if (object instanceof DMNBaseElement) {
                    ((DMNBaseElement) object).setParent(obj);
                    obj.addChildren((DMNBaseElement) object);
                }
                assignChildElement(obj, nodeName, object);
            } catch (CannotResolveClassException e) {
                // do nothing; I tried to convert the extension element child with the converters, but no converter is registered for this child.
                LOG.debug("Tried to convert the extension element child {}, but no converter is registered for this child.", nodeName);
            }
            reader.moveUp();
        }
        return obj;
    }

    @Override
    public void assignChildElement(Object parent, String nodeName, Object child) {
        TDMNElement.ExtensionElements id = (TDMNElement.ExtensionElements) parent;
        id.getAny().add(child);
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        // no attributes.
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);

        TDMNElement.ExtensionElements ee = (TDMNElement.ExtensionElements) parent;
        if (ee.getAny() != null) {
            for (Object a : ee.getAny()) {
                if (a instanceof Element) {
                    new DomConverter().marshal(a, writer, context);
                } else {
                    writeCompleteItem(a, context, writer);
                }
            }
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        // no attributes.
    }

    private Object readObject(HierarchicalStreamReader reader, UnmarshallingContext context, Object obj) {
        if (hasAlias(reader)) {
            return readBareItem(reader, context, null);
        } else {
            return context.convertAnother(obj, Element.class, new DomConverter());
        }
    }

    private boolean hasAlias(HierarchicalStreamReader reader) {
        try {
            HierarchicalStreams.readClassType(reader, mapper());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
