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
import com.gs.dmn.ast.TImportedValues;
import com.gs.dmn.ast.TLiteralExpression;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class LiteralExpressionConverter extends ExpressionConverter {
    public static final String IMPORTED_VALUES = "importedValues";
    public static final String TEXT = "text";
    public static final String EXPR_LANGUAGE = "expressionLanguage";

    public LiteralExpressionConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TLiteralExpression.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TLiteralExpression();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TLiteralExpression le = (TLiteralExpression) parent;

        if (TEXT.equals(nodeName)) {
            le.setText((String) child);
        } else if (IMPORTED_VALUES.equals(nodeName)) {
            le.setImportedValues((TImportedValues) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        TLiteralExpression le = (TLiteralExpression) parent;

        String exprLanguage = reader.getAttribute(EXPR_LANGUAGE);

        le.setExpressionLanguage(exprLanguage);
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TLiteralExpression le = (TLiteralExpression) parent;

        if (le.getText() != null) {
            writeChildrenNodeAsValue(writer, context, le.getText(), TEXT);
        }
        if (le.getImportedValues() != null) {
            writeChildrenNode(writer, context, le.getImportedValues(), IMPORTED_VALUES);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        TLiteralExpression le = (TLiteralExpression) parent;

        if (le.getExpressionLanguage() != null) {
            writer.addAttribute(EXPR_LANGUAGE, le.getExpressionLanguage());
        }
    }
}
