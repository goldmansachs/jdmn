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
        "authorityRequirement",
        "type",
        "owner",
        "locationURI",
        "extensionElements"
})
public class TKnowledgeSource<C> extends TDRGElement<C> implements Visitable<C>{
    private List<TAuthorityRequirement<C>> authorityRequirement;
    private String type;
    private TDMNElementReference<C> owner;
    private String locationURI;

    public List<TAuthorityRequirement<C>> getAuthorityRequirement() {
        if (authorityRequirement == null) {
            authorityRequirement = new ArrayList<>();
        }
        return this.authorityRequirement;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public TDMNElementReference<C> getOwner() {
        return owner;
    }

    public void setOwner(TDMNElementReference<C> value) {
        this.owner = value;
    }

    public String getLocationURI() {
        return locationURI;
    }

    public void setLocationURI(String value) {
        this.locationURI = value;
    }

    @Override
    public Object accept(Visitor<C> visitor, C context) {
        return visitor.visit(this, context);
    }
}
