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
import com.gs.dmn.ast.TConditional;
import com.gs.dmn.ast.TExpression;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.v1_1.ExpressionConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ConditionalConverter extends ExpressionConverter {
    public static final String IF = "if";
    public static final String THEN = "then";
    public static final String ELSE = "else";

    public ConditionalConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public String defineExpressionNodeName(TExpression e) {
        return "conditional";
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TConditional.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TConditional();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TConditional cond = (TConditional) parent;

        if (IF.equals(nodeName)) {
            cond.setIf((TChildExpression) child);
        } else if (THEN.equals(nodeName)) {
            cond.setThen((TChildExpression) child);
        } else if (ELSE.equals(nodeName)) {
            cond.setElse((TChildExpression) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TConditional cond = (TConditional) parent;
        writeChildrenNode(writer, context, cond.getIf(), IF);
        writeChildrenNode(writer, context, cond.getThen(), THEN);
        writeChildrenNode(writer, context, cond.getElse(), ELSE);
    }

    @Override
    protected void parseElements(HierarchicalStreamReader reader, UnmarshallingContext context, Object parent) {
        mvDownConvertAnotherMvUpAssignChildElement(reader, context, parent, IF, TChildExpression.class);
        mvDownConvertAnotherMvUpAssignChildElement(reader, context, parent, THEN, TChildExpression.class);
        mvDownConvertAnotherMvUpAssignChildElement(reader, context, parent, ELSE, TChildExpression.class);
    }
}
