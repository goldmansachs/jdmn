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

import com.gs.dmn.tck.ast.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ValueTypeConverter extends TCKBaseElementConverter {
    // Elements
    static final String VALUE = "value";
    static final String LIST = "list";
    static final String COMPONENT = "component";

    public ValueTypeConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class type) {
        return ValueType.class.equals(type);
    }

    @Override
    protected TCKBaseElement createModelObject() {
        return new ValueType();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        super.assignChildElement(parent, nodeName, child);

        ValueType element = (ValueType) parent;
        if (VALUE.equals(nodeName)) {
            element.setValue((AnySimpleType) child);
        } else if (LIST.equals(nodeName)) {
            element.setList((List) child);
        } else if (child instanceof Component) {
            element.getComponent().add((Component) child);
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

        ValueType element = (ValueType) parent;
        Object value = element.getValue();
        if (value != null) {
            writeChildrenNode(writer, context, value, VALUE);
        }
        for (Component component : element.getComponent()) {
            writeChildrenNode(writer, context, component, COMPONENT);
        }
        List list = element.getList();
        if (list != null) {
            writeChildrenNode(writer, context, list, LIST);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        // no attributes.
    }
}
