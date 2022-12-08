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

import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.dmndi.DMNEdge;
import com.gs.dmn.ast.dmndi.DMNLabel;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.v1_1.DMNBaseConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DMNEdgeConverter extends EdgeConverter {
    private static final String DMN_ELEMENT_REF = "dmnElementRef";
    private static final String DMN_LABEL = "DMNLabel";

    public DMNEdgeConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(DMNEdge.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new DMNEdge();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        DMNEdge concrete = (DMNEdge) parent;

        if (child instanceof DMNLabel) {
            concrete.setDMNLabel((DMNLabel) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        DMNEdge concrete = (DMNEdge) parent;

        String dmnElementRef = reader.getAttribute(DMN_ELEMENT_REF);

        if (dmnElementRef != null) {
            concrete.setDmnElementRef(DMNBaseConverter.parseQNameString(dmnElementRef));
        }
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        DMNEdge concrete = (DMNEdge) parent;

        if (concrete.getDMNLabel() != null) {
            writeChildrenNode(writer, context, concrete.getDMNLabel(), DMN_LABEL);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        DMNEdge concrete = (DMNEdge) parent;
        if (concrete.getDmnElementRef() != null) {
            writer.addAttribute(DMN_ELEMENT_REF, DMNBaseConverter.formatQName(concrete.getDmnElementRef(), concrete, version));
        }
    }
}
