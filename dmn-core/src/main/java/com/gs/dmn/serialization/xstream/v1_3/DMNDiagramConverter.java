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
import com.gs.dmn.ast.dmndi.DMNDiagram;
import com.gs.dmn.ast.dmndi.DiagramElement;
import com.gs.dmn.ast.dmndi.Dimension;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DMNDiagramConverter extends DiagramConverter {
    private static final String SIZE = "Size";

    public DMNDiagramConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(DMNDiagram.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new DMNDiagram();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        DMNDiagram style = (DMNDiagram) parent;

        if (child instanceof Dimension) {
            style.setSize((Dimension) child);
        } else if (child instanceof DiagramElement) {
            style.getDMNDiagramElement().add((DiagramElement) child);
        } else {
            super.assignChildElement(style, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        // no attributes.
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        DMNDiagram style = (DMNDiagram) parent;

        if (style.getSize() != null) {
            writeChildrenNode(writer, context, style.getSize(), SIZE);
        }
        for (DiagramElement de : style.getDMNDiagramElement()) {
            writeChildrenNode(writer, context, de, de.getClass().getSimpleName());
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        // no attributes.
    }
}
