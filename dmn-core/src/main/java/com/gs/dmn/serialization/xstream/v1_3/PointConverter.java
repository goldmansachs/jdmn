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
import com.gs.dmn.ast.dmndi.Point;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PointConverter extends DMNBaseElementConverter {
    private static final String Y = "y";
    private static final String X = "x";

    public PointConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class type) {
        return type.equals(Point.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new Point();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        super.assignChildElement(parent, nodeName, child);
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        Point abs = (Point) parent;

        abs.setX(Double.valueOf(reader.getAttribute(X)));
        abs.setY(Double.valueOf(reader.getAttribute(Y)));
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);

        Point abs = (Point) parent;

        writer.addAttribute(X, FormatUtils.manageDouble(abs.getX()));
        writer.addAttribute(Y, FormatUtils.manageDouble(abs.getY()));
    }
}
