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

import com.gs.dmn.tck.ast.TCKBaseElement;
import com.gs.dmn.tck.ast.InputNode;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class InputNodeConverter extends ValueTypeConverter {
    // Attributes
    private static final String NAMESPACE = "namespace";
    private static final String NAME = "name";

    public InputNodeConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(InputNode.class);
    }

    @Override
    protected TCKBaseElement createModelObject() {
        return new InputNode();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        super.assignChildElement(parent, nodeName, child);
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);

        InputNode element = (InputNode) parent;
        element.setNamespace(reader.getAttribute(NAMESPACE));
        element.setName(reader.getAttribute(NAME));
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        InputNode element = (InputNode) parent;
        String namespace = element.getNamespace();
        if (namespace != null) {
            writer.addAttribute(NAMESPACE, namespace);
        }
        String name = element.getName();
        if (name != null) {
            writer.addAttribute(NAME, name);
        }
    }
}
