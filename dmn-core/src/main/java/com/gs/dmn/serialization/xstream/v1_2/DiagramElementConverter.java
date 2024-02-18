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
package com.gs.dmn.serialization.xstream.v1_2;

import com.gs.dmn.ast.dmndi.DiagramElement;
import com.gs.dmn.ast.dmndi.Style;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.v1_1.DMNBaseElementConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public abstract class DiagramElementConverter extends DMNBaseElementConverter {
    private static final String STYLE = "style";
    private static final String SHARED_STYLE = "sharedStyle";
    private static final String EXTENSION = "extension";
    private static final String ID = "id";

    public DiagramElementConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        DiagramElement abs = (DiagramElement) parent;

        if (child instanceof DiagramElement.Extension extension) {
            abs.setExtension(extension);
        } else if (child instanceof Style style) {
            abs.setStyle(style);
        } else {
            super.assignChildElement(abs, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        DiagramElement abs = (DiagramElement) parent;
        String id = reader.getAttribute(ID);
        if (id != null) {
            abs.setId(id);
        }

        String sharedStyleXmlSerialization = reader.getAttribute(SHARED_STYLE);
        if (sharedStyleXmlSerialization != null) {
            abs.setSharedStyle(new Style.IDREFStubStyle(sharedStyleXmlSerialization));
        }
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        DiagramElement abs = (DiagramElement) parent;

        if (abs.getExtension() != null) {
            writeChildrenNode(writer, context, abs.getExtension(), EXTENSION);
        }
        if (abs.getStyle() != null) {
            writeChildrenNode(writer, context, abs.getStyle(), STYLE);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        DiagramElement abs = (DiagramElement) parent;

        if (abs.getId() != null) {
            writer.addAttribute(ID, abs.getId());
        }

        if (abs.getSharedStyle() != null) {
            writer.addAttribute(SHARED_STYLE, abs.getSharedStyle().getId());
        }
    }
}
