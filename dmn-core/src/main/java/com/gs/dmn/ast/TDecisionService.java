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

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({
        "name",
        "id",
        "label",
        "otherAttributes",
        "description",
        "variable",
        "outputDecision",
        "encapsulatedDecision",
        "inputDecision",
        "inputData",
        "extensionElements"
})
public class TDecisionService<C> extends TInvocable<C> implements Visitable<C> {
    private List<TDMNElementReference<C>> outputDecision;
    private List<TDMNElementReference<C>> encapsulatedDecision;
    private List<TDMNElementReference<C>> inputDecision;
    private List<TDMNElementReference<C>> inputData;

    public List<TDMNElementReference<C>> getOutputDecision() {
        if (outputDecision == null) {
            outputDecision = new ArrayList<>();
        }
        return this.outputDecision;
    }

    public List<TDMNElementReference<C>> getEncapsulatedDecision() {
        if (encapsulatedDecision == null) {
            encapsulatedDecision = new ArrayList<>();
        }
        return this.encapsulatedDecision;
    }

    public List<TDMNElementReference<C>> getInputDecision() {
        if (inputDecision == null) {
            inputDecision = new ArrayList<>();
        }
        return this.inputDecision;
    }

    public List<TDMNElementReference<C>> getInputData() {
        if (inputData == null) {
            inputData = new ArrayList<>();
        }
        return this.inputData;
    }

    @Override
    public Object accept(Visitor<C> visitor, C context) {
        return visitor.visit(this, context);
    }
}
