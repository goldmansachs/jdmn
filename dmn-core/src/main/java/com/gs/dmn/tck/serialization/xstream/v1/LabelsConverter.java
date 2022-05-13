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

import com.gs.dmn.tck.ast.DMNBaseElement;
import com.gs.dmn.tck.ast.Labels;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class LabelsConverter extends DMNBaseElementConverter {
    public static final String LABEL = "label";

    public LabelsConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(Labels.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new Labels();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        Labels element = (Labels) parent;
        if (LABEL.equals(nodeName)) {
            element.getLabel().add((String) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
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

        Labels element = (Labels) parent;
        for (String label : element.getLabel()) {
            writeChildrenNode(writer, context, label, LABEL);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        // no attributes.
    }
}
