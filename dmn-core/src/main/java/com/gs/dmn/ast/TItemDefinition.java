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
import com.gs.dmn.context.DMNContext;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({
        "name",
        "id",
        "label",
        "isCollection",
        "typeLanguage",
        "otherAttributes",
        "description",
        "typeRef",
        "allowedValues",
        "itemComponent",
        "functionItem",
        "extensionElements"
})
public class TItemDefinition extends TNamedElement implements Visitable {
    private String typeRef;
    private TUnaryTests allowedValues;
    private List<TItemDefinition> itemComponent;
    private TFunctionItem functionItem;
    private String typeLanguage;
    private Boolean isCollection;

    public String getTypeRef() {
        return typeRef;
    }

    public void setTypeRef(String value) {
        this.typeRef = value;
    }

    public TUnaryTests getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(TUnaryTests value) {
        this.allowedValues = value;
    }

    public List<TItemDefinition> getItemComponent() {
        if (itemComponent == null) {
            itemComponent = new ArrayList<>();
        }
        return this.itemComponent;
    }

    public TFunctionItem getFunctionItem() {
        return functionItem;
    }

    public void setFunctionItem(TFunctionItem value) {
        this.functionItem = value;
    }

    public String getTypeLanguage() {
        return typeLanguage;
    }

    public void setTypeLanguage(String value) {
        this.typeLanguage = value;
    }

    public boolean isIsCollection() {
        if (isCollection == null) {
            return false;
        } else {
            return isCollection;
        }
    }

    public void setIsCollection(Boolean value) {
        this.isCollection = value;
    }

    @Override
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }
}
