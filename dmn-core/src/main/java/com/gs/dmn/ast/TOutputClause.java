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

@JsonPropertyOrder({
        "id",
        "label",
        "otherAttributes",
        "description",
        "outputValues",
        "defaultOutputEntry",
        "extensionElements"
})
public class TOutputClause<C> extends TDMNElement<C> implements Visitable<C> {
    private TUnaryTests<C> outputValues;
    private TLiteralExpression<C> defaultOutputEntry;
    private String name;
    private QName typeRef;

    public TUnaryTests<C> getOutputValues() {
        return outputValues;
    }

    public void setOutputValues(TUnaryTests<C> value) {
        this.outputValues = value;
    }

    public TLiteralExpression<C> getDefaultOutputEntry() {
        return defaultOutputEntry;
    }

    public void setDefaultOutputEntry(TLiteralExpression<C> value) {
        this.defaultOutputEntry = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public QName getTypeRef() {
        return typeRef;
    }

    public void setTypeRef(QName value) {
        this.typeRef = value;
    }

    @Override
    public Object accept(Visitor<C> visitor, C context) {
        return visitor.visit(this, context);
    }
}
