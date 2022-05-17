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
import com.gs.dmn.ast.TBinding;
import com.gs.dmn.ast.TExpression;
import com.gs.dmn.ast.TInvocation;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class InvocationConverter extends ExpressionConverter {
    public static final String BINDING = "binding";
    public static final String EXPRESSION = "expression";

    public InvocationConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TInvocation.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TInvocation();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TInvocation i = (TInvocation) parent;

        if (child instanceof TExpression) {
            i.setExpression((TExpression) child);
        } else if (BINDING.equals(nodeName)) {
            i.getBinding().add((TBinding) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TInvocation i = (TInvocation) parent;

        if (i.getExpression() != null)
            writeChildrenNode(writer, context, i.getExpression(), MarshallingUtils.defineExpressionNodeName(xstream, i.getExpression()));
        for (TBinding b : i.getBinding()) {
            writeChildrenNode(writer, context, b, BINDING);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
    }
}
