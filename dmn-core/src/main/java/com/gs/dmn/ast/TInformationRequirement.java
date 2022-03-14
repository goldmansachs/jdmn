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

@JsonPropertyOrder({
        "id",
        "label",
        "otherAttributes",
        "description",
        "requiredDecision",
        "requiredInput",
        "extensionElements"
})
public class TInformationRequirement<C> extends TDMNElement<C> implements Visitable<C> {
    private TDMNElementReference<C> requiredDecision;
    private TDMNElementReference<C> requiredInput;

    public TDMNElementReference<C> getRequiredDecision() {
        return requiredDecision;
    }

    public void setRequiredDecision(TDMNElementReference<C> value) {
        this.requiredDecision = value;
    }

    public TDMNElementReference<C> getRequiredInput() {
        return requiredInput;
    }

    public void setRequiredInput(TDMNElementReference<C> value) {
        this.requiredInput = value;
    }

    @Override
    public Object accept(Visitor<C> visitor, C context) {
        return visitor.visit(this, context);
    }
}
