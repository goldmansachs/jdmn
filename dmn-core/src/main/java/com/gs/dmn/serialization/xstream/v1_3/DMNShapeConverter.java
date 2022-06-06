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
import com.gs.dmn.ast.dmndi.DMNDecisionServiceDividerLine;
import com.gs.dmn.ast.dmndi.DMNLabel;
import com.gs.dmn.ast.dmndi.DMNShape;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DMNShapeConverter extends ShapeConverter {
    private static final String FILL_COLOR = "FillColor";
    private static final String STROKE_COLOR = "StrokeColor";
    private static final String FONT_COLOR = "FontColor";

    private static final String FONT_FAMILY = "fontFamily";
    private static final String FONT_SIZE = "fontSize";
    private static final String FONT_ITALIC = "fontItalic";
    private static final String FONT_BOLD = "fontBold";
    private static final String FONT_UNDERLINE = "fontUnderline";
    private static final String FONT_STRIKE_THROUGH = "fontStrikeThrough";
    private static final String LABEL_HORIZONTAL_ALIGNMENT = "labelHorizontalAlignement";
    private static final String LABEL_VERTICAL_ALIGNMENT = "labelVerticalAlignment";

    public DMNShapeConverter(XStream xstream) {
        super(xstream);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(DMNShape.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new DMNShape();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        DMNShape shape = (DMNShape) parent;

        if (child instanceof DMNLabel) {
            shape.setDMNLabel((DMNLabel) child);
        } else if (child instanceof DMNDecisionServiceDividerLine) {
            shape.setDMNDecisionServiceDividerLine((DMNDecisionServiceDividerLine) child);
        } else {
            super.assignChildElement(shape, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        DMNShape shape = (DMNShape) parent;

        shape.setDmnElementRef(MarshallingUtils.parseQNameString(reader.getAttribute("dmnElementRef")));

        String isListedInputData = reader.getAttribute("isListedInputData");
        String isCollapsed = reader.getAttribute("isCollapsed");

        if (isListedInputData != null) {
            shape.setIsListedInputData(Boolean.valueOf(isListedInputData));
        }
        if (isCollapsed != null) {
            shape.setIsCollapsed(Boolean.valueOf(isCollapsed));
        }
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        DMNShape shape = (DMNShape) parent;

        if (shape.getDMNLabel() != null) {
            writeChildrenNode(writer, context, shape.getDMNLabel(), "DMNLabel");
        }
        if (shape.getDMNDecisionServiceDividerLine() != null) {
            writeChildrenNode(writer, context, shape.getDMNDecisionServiceDividerLine(), "DMNDecisionServiceDividerLine");
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        DMNShape shape = (DMNShape) parent;

        writer.addAttribute("dmnElementRef", MarshallingUtils.formatQName(shape.getDmnElementRef(), shape));

        if (shape.isIsListedInputData() != null) {
            writer.addAttribute("isListedInputData", shape.isIsListedInputData().toString());
        }
        if (shape.getIsCollapsedField() != null) {
            writer.addAttribute("isCollapsed", Boolean.valueOf(shape.getIsCollapsedField()).toString());
        }
    }
}
