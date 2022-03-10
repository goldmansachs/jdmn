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
        "x",
        "y",
        "width",
        "height"
})
public class Bounds implements Visitable {
    private double x;
    private double y;
    private double width;
    private double height;

    public double getX() {
        return x;
    }

    public void setX(double value) {
        this.x = value;
    }

    public double getY() {
        return y;
    }

    public void setY(double value) {
        this.y = value;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double value) {
        this.width = value;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double value) {
        this.height = value;
    }

    @Override
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }
}
