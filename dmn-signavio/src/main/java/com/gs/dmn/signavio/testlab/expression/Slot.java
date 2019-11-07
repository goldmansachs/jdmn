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
        return String.format("%s : %s", itemComponentName, value.toFEELExpression());
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
