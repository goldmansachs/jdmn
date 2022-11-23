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

import com.gs.dmn.ast.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class FunctionDefinitionConverter extends ExpressionConverter {
    private static final String KIND = "kind";
    public static final String EXPRESSION = "expression";
    public static final String FORMAL_PARAMETER = "formalParameter";

    public FunctionDefinitionConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TFunctionDefinition.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TFunctionDefinition();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TFunctionDefinition fd = (TFunctionDefinition) parent;

        if (FORMAL_PARAMETER.equals(nodeName)) {
            fd.getFormalParameter().add((TInformationItem) child);
        } else if (child instanceof TExpression) {
            fd.setExpression((TExpression) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);

        TFunctionDefinition i = (TFunctionDefinition) parent;

        String kind = reader.getAttribute(KIND);
        if (kind != null) {
            i.setKind(TFunctionKind.fromValue(kind));
        }
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TFunctionDefinition fd = (TFunctionDefinition) parent;

        for (TInformationItem fparam : fd.getFormalParameter()) {
            writeChildrenNode(writer, context, fparam, FORMAL_PARAMETER);
        }
        if (fd.getExpression() != null)
            writeChildrenNode(writer, context, fd.getExpression(), DMNBaseConverter.defineExpressionNodeName(fd.getExpression()));
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        TFunctionDefinition fd = (TFunctionDefinition) parent;

        if (fd.getKindField() != null) {
            writer.addAttribute(KIND, fd.getKindField().value());
        }
    }
}
