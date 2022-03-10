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
        "otherAttributes",
        "description",
        "authorityRequirement",
        "type",
        "owner",
        "locationURI",
        "extensionElements"
})
public class TKnowledgeSource extends TDRGElement implements Visitable{
    private List<TAuthorityRequirement> authorityRequirement;
    private String type;
    private TDMNElementReference owner;
    private String locationURI;

    public List<TAuthorityRequirement> getAuthorityRequirement() {
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

    public TDMNElementReference getOwner() {
        return owner;
    }

    public void setOwner(TDMNElementReference value) {
        this.owner = value;
    }

    public String getLocationURI() {
        return locationURI;
    }

    public void setLocationURI(String value) {
        this.locationURI = value;
    }

    @Override
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }
}
