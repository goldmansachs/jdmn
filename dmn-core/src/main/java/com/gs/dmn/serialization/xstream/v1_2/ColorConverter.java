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
import com.gs.dmn.ast.dmndi.Color;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ColorConverter extends DMNBaseElementConverter {
    private static final String RED = "red";
    private static final String GREEN = "green";
    private static final String BLUE = "blue";

    public ColorConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(Color.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new Color();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        super.assignChildElement(parent, nodeName, child);
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        Color style = (Color) parent;

        style.setRed(Integer.valueOf(reader.getAttribute(RED)));
        style.setGreen(Integer.valueOf(reader.getAttribute(GREEN)));
        style.setBlue(Integer.valueOf(reader.getAttribute(BLUE)));
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        Color style = (Color) parent;

        writer.addAttribute(RED, Integer.valueOf(style.getRed()).toString());
        writer.addAttribute(GREEN, Integer.valueOf(style.getGreen()).toString());
        writer.addAttribute(BLUE, Integer.valueOf(style.getBlue()).toString());
    }
}
