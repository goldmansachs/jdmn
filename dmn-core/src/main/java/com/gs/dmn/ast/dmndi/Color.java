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
import com.gs.dmn.ast.DMNBaseElement;
import com.gs.dmn.ast.Visitable;
import com.gs.dmn.ast.Visitor;

@JsonPropertyOrder({
        "red",
        "green",
        "blue"
})
public class Color extends DMNBaseElement implements Visitable {
    private int red;
    private int green;
    private int blue;

    public int getRed() {
        return red;
    }

    public void setRed(int value) {
        this.red = value;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int value) {
        this.green = value;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int value) {
        this.blue = value;
    }

    @Override
    public <C> Object accept(Visitor visitor, C context) {
        return visitor.visit(this, context);
    }
}
