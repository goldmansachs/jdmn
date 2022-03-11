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
package com.gs.dmn.ast.dmndi;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gs.dmn.ast.Visitable;
import com.gs.dmn.ast.Visitor;
import com.gs.dmn.context.DMNContext;

@JsonPropertyOrder({
        "id",
        "otherAttributes",
        "extension",
        "fontFamily",
        "fontSize",
        "fontItalic",
        "fontBold",
        "fontUnderline",
        "fontStrikeThrough",
        "labelHorizontalAlignment",
        "labelVerticalAlignment",
        "fillColor",
        "strokeColor",
        "fontColor"
})
public class DMNStyle extends Style implements Visitable {
    private Color fillColor;
    private Color strokeColor;
    private Color fontColor;
    private String fontFamily;
    private Double fontSize;
    private Boolean fontItalic;
    private Boolean fontBold;
    private Boolean fontUnderline;
    private Boolean fontStrikeThrough;
    private AlignmentKind labelHorizontalAlignment;
    private AlignmentKind labelVerticalAlignment;

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color value) {
        this.fillColor = value;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color value) {
        this.strokeColor = value;
    }

    public Color getFontColor() {
        return fontColor;
    }

    public void setFontColor(Color value) {
        this.fontColor = value;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String value) {
        this.fontFamily = value;
    }

    public Double getFontSize() {
        return fontSize;
    }

    public void setFontSize(Double value) {
        this.fontSize = value;
    }

    public Boolean isFontItalic() {
        return fontItalic;
    }

    public void setFontItalic(Boolean value) {
        this.fontItalic = value;
    }

    public Boolean isFontBold() {
        return fontBold;
    }

    public void setFontBold(Boolean value) {
        this.fontBold = value;
    }

    public Boolean isFontUnderline() {
        return fontUnderline;
    }

    public void setFontUnderline(Boolean value) {
        this.fontUnderline = value;
    }

    public Boolean isFontStrikeThrough() {
        return fontStrikeThrough;
    }

    public void setFontStrikeThrough(Boolean value) {
        this.fontStrikeThrough = value;
    }

    public AlignmentKind getLabelHorizontalAlignment() {
        return labelHorizontalAlignment;
    }

    public void setLabelHorizontalAlignement(AlignmentKind value) {
        this.labelHorizontalAlignment = value;
    }

    public AlignmentKind getLabelVerticalAlignment() {
        return labelVerticalAlignment;
    }

    public void setLabelVerticalAlignment(AlignmentKind value) {
        this.labelVerticalAlignment = value;
    }

    @Override
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }
}
