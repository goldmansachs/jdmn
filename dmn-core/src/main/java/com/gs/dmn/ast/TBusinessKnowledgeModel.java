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
        "encapsulatedLogic",
        "knowledgeRequirement",
        "authorityRequirement",
        "extensionElements"
})
public class TBusinessKnowledgeModel<C> extends TInvocable<C> implements Visitable<C> {
    private TFunctionDefinition<C> encapsulatedLogic;
    private List<TKnowledgeRequirement<C>> knowledgeRequirement;
    private List<TAuthorityRequirement<C>> authorityRequirement;

    public TFunctionDefinition<C> getEncapsulatedLogic() {
        return encapsulatedLogic;
    }

    public void setEncapsulatedLogic(TFunctionDefinition<C> value) {
        this.encapsulatedLogic = value;
    }

    public List<TKnowledgeRequirement<C>> getKnowledgeRequirement() {
        if (knowledgeRequirement == null) {
            knowledgeRequirement = new ArrayList<>();
        }
        return this.knowledgeRequirement;
    }

    public List<TAuthorityRequirement<C>> getAuthorityRequirement() {
        if (authorityRequirement == null) {
            authorityRequirement = new ArrayList<>();
        }
        return this.authorityRequirement;
    }

    @Override
    public Object accept(Visitor<C> visitor, C context) {
        return visitor.visit(this, context);
    }
}
