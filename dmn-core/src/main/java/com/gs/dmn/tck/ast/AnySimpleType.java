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
package com.gs.dmn.tck.ast;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

@JsonPropertyOrder({
        "value"
})
public class AnySimpleType extends DMNBaseElement {
    private final Map<QName, String> otherAttributes = new HashMap<>();
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

    @Override
    public <C> Object accept(Visitor visitor, C context) {
        return visitor.visit(this, context);
    }
}
