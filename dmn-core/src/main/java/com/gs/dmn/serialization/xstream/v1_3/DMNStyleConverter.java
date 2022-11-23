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
import com.gs.dmn.ast.dmndi.AlignmentKind;
import com.gs.dmn.ast.dmndi.Color;
import com.gs.dmn.ast.dmndi.DMNStyle;
import com.gs.dmn.serialization.DMNVersion;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DMNStyleConverter extends StyleConverter {
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

    public DMNStyleConverter(XStream xstream, DMNVersion version) {
        super(xstream, version);
    }

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.equals(DMNStyle.class);
    }

    @Override
    protected DMNBaseElement createModelObject() {
        return new DMNStyle();
    }

    @Override
    protected void assignChildElement(Object parent, String nodeName, Object child) {
        DMNStyle style = (DMNStyle) parent;

        if (FILL_COLOR.equals(nodeName)) {
            style.setFillColor((Color) child);
        } else if (STROKE_COLOR.equals(nodeName)) {
            style.setStrokeColor((Color) child);
        } else if (FONT_COLOR.equals(nodeName)) {
            style.setFontColor((Color) child);
        } else {
            super.assignChildElement(style, nodeName, child);
        }
    }

    @Override
    protected void assignAttributes(HierarchicalStreamReader reader, Object parent) {
        super.assignAttributes(reader, parent);
        DMNStyle style = (DMNStyle) parent;

        String fontFamily = reader.getAttribute(FONT_FAMILY);
        String fontSize = reader.getAttribute(FONT_SIZE);
        String fontItalic = reader.getAttribute(FONT_ITALIC);
        String fontBold = reader.getAttribute(FONT_BOLD);
        String fontUnderline = reader.getAttribute(FONT_UNDERLINE);
        String fontStrikeThrough = reader.getAttribute(FONT_STRIKE_THROUGH);
        String labelHorizontalAlignement = reader.getAttribute(LABEL_HORIZONTAL_ALIGNMENT);
        String labelVerticalAlignment = reader.getAttribute(LABEL_VERTICAL_ALIGNMENT);

        if (fontFamily != null) {
            style.setFontFamily(fontFamily);
        }
        if (fontSize != null) {
            style.setFontSize(Double.valueOf(fontSize));
        }
        if (fontItalic != null) {
            style.setFontItalic(Boolean.valueOf(fontItalic));
        }
        if (fontBold != null) {
            style.setFontBold(Boolean.valueOf(fontBold));
        }
        if (fontUnderline != null) {
            style.setFontUnderline(Boolean.valueOf(fontUnderline));
        }
        if (fontStrikeThrough != null) {
            style.setFontStrikeThrough(Boolean.valueOf(fontStrikeThrough));
        }
        if (labelHorizontalAlignement != null) {
            style.setLabelHorizontalAlignment(AlignmentKind.valueOf(labelHorizontalAlignement));
        }
        if (labelVerticalAlignment != null) {
            style.setLabelVerticalAlignment(AlignmentKind.valueOf(labelVerticalAlignment));
        }
    }

    @Override
    protected void writeChildren(HierarchicalStreamWriter writer, MarshallingContext context, Object parent) {
        super.writeChildren(writer, context, parent);
        DMNStyle style = (DMNStyle) parent;

        if (style.getFillColor() != null) {
            writeChildrenNode(writer, context, style.getFillColor(), FILL_COLOR);
        }
        if (style.getStrokeColor() != null) {
            writeChildrenNode(writer, context, style.getStrokeColor(), STROKE_COLOR);
        }
        if (style.getFontColor() != null) {
            writeChildrenNode(writer, context, style.getFontColor(), FONT_COLOR);
        }
    }

    @Override
    protected void writeAttributes(HierarchicalStreamWriter writer, Object parent) {
        super.writeAttributes(writer, parent);
        DMNStyle style = (DMNStyle) parent;

        if (style.getFontFamily() != null) {
            writer.addAttribute(FONT_FAMILY, style.getFontFamily());
        }
        if (style.getFontSize() != null) {
            writer.addAttribute(FONT_SIZE, FormatUtils.manageDouble(style.getFontSize()));
        }
        if (style.isFontItalic() != null) {
            writer.addAttribute(FONT_ITALIC, style.isFontItalic().toString());
        }
        if (style.isFontBold() != null) {
            writer.addAttribute(FONT_BOLD, style.isFontBold().toString());
        }
        if (style.isFontUnderline() != null) {
            writer.addAttribute(FONT_UNDERLINE, style.isFontUnderline().toString());
        }
        if (style.isFontStrikeThrough() != null) {
            writer.addAttribute(FONT_STRIKE_THROUGH, style.isFontStrikeThrough().toString());
        }
        if (style.getLabelHorizontalAlignment() != null) {
            writer.addAttribute(LABEL_HORIZONTAL_ALIGNMENT, style.getLabelHorizontalAlignment().toString());
        }
        if (style.getLabelVerticalAlignment() != null) {
            writer.addAttribute(LABEL_VERTICAL_ALIGNMENT, style.getLabelVerticalAlignment().toString());
        }
    }
}
