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

import javax.xml.namespace.QName;
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
public class TItemDefinition<C> extends TNamedElement<C> implements Visitable<C> {
    private QName typeRef;
    private TUnaryTests<C> allowedValues;
    private List<TItemDefinition<C>> itemComponent;
    private TFunctionItem<C> functionItem;
    private String typeLanguage;
    private Boolean isCollection;

    public QName getTypeRef() {
        return typeRef;
    }

    public void setTypeRef(QName value) {
        this.typeRef = value;
    }

    public TUnaryTests<C> getAllowedValues() {
        return allowedValues;
    }

    public void setAllowedValues(TUnaryTests<C> value) {
        this.allowedValues = value;
    }

    public List<TItemDefinition<C>> getItemComponent() {
        if (itemComponent == null) {
            itemComponent = new ArrayList<>();
        }
        return this.itemComponent;
    }

    public TFunctionItem<C> getFunctionItem() {
        return functionItem;
    }

    public void setFunctionItem(TFunctionItem<C> value) {
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
    public Object accept(Visitor<C> visitor, C context) {
        return visitor.visit(this, context);
    }
}
