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
package com.gs.dmn.ast;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gs.dmn.runtime.DMNContext;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({
        "name",
        "id",
        "label",
        "otherAttributes",
        "description",
        "drgElement",
        "extensionElements"
})
public class TElementCollection extends TNamedElement implements Visitable {
    private List<TDMNElementReference> drgElement;

    public List<TDMNElementReference> getDrgElement() {
        if (drgElement == null) {
            drgElement = new ArrayList<>();
        }
        return this.drgElement;
    }

    @Override
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }
}
