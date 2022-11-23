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
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ImportedValuesConverter extends ImportConverter {
    public static final String IMPORTED_ELEMENT = "importedElement";
    public static final String EXPRESSION_LANGUAGE = "expressionLanguage";

    public ImportedValuesConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TImportedValues.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TImportedValues();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TImportedValues iv = (TImportedValues) parent;

        if (IMPORTED_ELEMENT.equals(nodeName)) {
            iv.setImportedElement((String) child);
        } else {
            super.assignChildElement(parent, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        TImportedValues iv = (TImportedValues) parent;

        String expressionLanguage = reader.getAttribute(EXPRESSION_LANGUAGE);

        iv.setExpressionLanguage(expressionLanguage);
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        TImportedValues iv = (TImportedValues) parent;

        writeChildrenNode(writer, context, iv.getImportedElement(), IMPORTED_ELEMENT);
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        TImportedValues iv = (TImportedValues) parent;

        if (iv.getExpressionLanguage() != null) {
            writer.addAttribute(EXPRESSION_LANGUAGE, iv.getExpressionLanguage());
        }
    }
}
