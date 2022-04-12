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
import com.gs.dmn.ast.TExpression;
import com.gs.dmn.ast.TList;
import com.gs.dmn.context.DMNContext;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DMNListConverter extends ExpressionConverter {
    public DMNListConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TList.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TList();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TList list = (TList) parent;

        if (child instanceof TExpression) {
            list.getExpression().add(child);
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
        TList<DMNContext> list = (TList) parent;

        for (TExpression e : list.getExpression()) {
            writeChildrenNode(writer, context, e, MarshallingUtils.defineExpressionNodeName(e));
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        // no attributes.
    }
}
