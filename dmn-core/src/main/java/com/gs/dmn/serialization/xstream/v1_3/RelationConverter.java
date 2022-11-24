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
import com.gs.dmn.ast.TInformationItem;
import com.gs.dmn.ast.TList;
import com.gs.dmn.ast.TRelation;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.serialization.xstream.v1_1.ExpressionConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class RelationConverter extends ExpressionConverter {
    public static final String EXPRESSION = "expression";
    public static final String ROW = "row";
    public static final String COLUMN = "column";

    public RelationConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new TRelation();
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(TRelation.class);
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        TRelation r = (TRelation) parent;

        if (COLUMN.equals(nodeName)) {
            r.getColumn().add((TInformationItem) child);
        } else if (ROW.equals(nodeName)) {
            r.getRow().add((TList) child);
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
        TRelation r = (TRelation) parent;

        for (TInformationItem c : r.getColumn()) {
            writeChildrenNode(writer, context, c, COLUMN);
        }
        for (TList row : r.getRow()) {
            writeChildrenNode(writer, context, row, ROW);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        // no attributes.
    }
}
