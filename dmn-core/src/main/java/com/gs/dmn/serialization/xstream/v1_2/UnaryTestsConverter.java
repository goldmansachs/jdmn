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
import com.gs.dmn.ast.TUnaryTests;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class UnaryTestsConverter extends ExpressionConverter {
    public static final String TEXT = "text";
    public static final String EXPRESSION_LANGUAGE = "expressionLanguage";

    public UnaryTestsConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TUnaryTests.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TUnaryTests();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TUnaryTests ut = (TUnaryTests) parent;

        if (TEXT.equals(nodeName)) {
            ut.setText((String) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        TUnaryTests ut = (TUnaryTests) parent;

        String expressionLanguage = reader.getAttribute(EXPRESSION_LANGUAGE);

        ut.setExpressionLanguage(expressionLanguage);
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TUnaryTests ut = (TUnaryTests) parent;

        writeChildrenNode(writer, context, ut.getText(), TEXT);
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        TUnaryTests ut = (TUnaryTests) parent;

        if (ut.getExpressionLanguage() != null) {
            writer.addAttribute(EXPRESSION_LANGUAGE, ut.getExpressionLanguage());
        }
    }
}
