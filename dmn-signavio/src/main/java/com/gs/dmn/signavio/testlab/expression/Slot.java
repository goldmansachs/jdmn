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
package com.gs.dmn.signavio.testlab.expression;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gs.dmn.signavio.testlab.TestLabElement;
import com.gs.dmn.signavio.testlab.TestLabVisitor;

@JsonPropertyOrder({ "id", "name", "value", "itemComponentName" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Slot extends TestLabElement {
    private String id;
    private String name;
    private Expression value;
    private String itemComponentName;

    public Slot() {
    }

    public Slot(String id, String name, Expression value, String itemComponentName) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.itemComponentName = itemComponentName;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Expression getValue() {
        return value;
    }

    public String getItemComponentName() {
        return itemComponentName;
    }

    public void setItemComponentName(String itemComponentName) {
        this.itemComponentName = itemComponentName;
    }

    public String toContextEntry() {
        if (value == null) {
            return String.format("%s : null", itemComponentName);
        } else {
            return String.format("%s : %s", itemComponentName, value.toFEELExpression());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Slot slot = (Slot) o;

        if (id != null ? !id.equals(slot.id) : slot.id != null) return false;
        if (name != null ? !name.equals(slot.name) : slot.name != null) return false;
        if (value != null ? !value.equals(slot.value) : slot.value != null) return false;
        return itemComponentName != null ? itemComponentName.equals(slot.itemComponentName) : slot.itemComponentName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (itemComponentName != null ? itemComponentName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s, %s, %s)", this.getClass().getSimpleName(), name, itemComponentName, id, value);
    }

    @Override
    public Object accept(TestLabVisitor visitor, Object... params) {
        return visitor.visit(this, params);
    }
}
