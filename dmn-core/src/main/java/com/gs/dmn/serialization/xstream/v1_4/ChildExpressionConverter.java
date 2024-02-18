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
package com.gs.dmn.serialization.xstream.v1_4;

import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.TChildExpression;
import com.gs.dmn.ast.TExpression;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.v1_1.DMNBaseElementConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ChildExpressionConverter extends DMNBaseElementConverter {
    public static final String ID = "id";

    public ChildExpressionConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(TChildExpression.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TChildExpression();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TChildExpression i = (TChildExpression) parent;

        if (child instanceof TExpression expression) {
            i.setExpression(expression);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        String id = reader.getAttribute(ID);

        if (id != null) {
            ((TChildExpression) parent).setId(id);
        }
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TChildExpression i = (TChildExpression) parent;

        writeChildrenNode(writer, context, i.getExpression(), defineExpressionNodeName(i.getExpression()));
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        TChildExpression e = (TChildExpression) parent;

        if (e.getId() != null) {
            writer.addAttribute(ID, e.getId());
        }
    }
}
