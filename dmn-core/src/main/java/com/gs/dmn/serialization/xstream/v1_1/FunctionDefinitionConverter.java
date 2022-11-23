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
import com.gs.dmn.ast.TFunctionDefinition;
import com.gs.dmn.ast.TInformationItem;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class FunctionDefinitionConverter extends ExpressionConverter {
    public static final String EXPRESSION = "expression";
    public static final String FORMAL_PARAMETER = "formalParameter";

    public FunctionDefinitionConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
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

        // no attributes (drools:kind is now managed fully as a generic additional attribute in DMNModelInstrumentedBase)
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TFunctionDefinition fd = (TFunctionDefinition) parent;

        for (TInformationItem fparam : fd.getFormalParameter()) {
            writeChildrenNode(writer, context, fparam, FORMAL_PARAMETER);
        }
        if (fd.getExpression() != null) {
            writeChildrenNode(writer, context, fd.getExpression(), DMNBaseConverter.defineExpressionNodeName(fd.getExpression()));
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        // no attributes.
    }
}
