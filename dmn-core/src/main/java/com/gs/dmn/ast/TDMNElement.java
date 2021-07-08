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

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "@kind")
@JsonSubTypes({
        @JsonSubTypes.Type(name = "informationRequirement", value = TInformationRequirement.class),
        @JsonSubTypes.Type(name = "knowledgeRequirement", value = TKnowledgeRequirement.class),
        @JsonSubTypes.Type(name = "authorityRequirement", value = TAuthorityRequirement.class),
        @JsonSubTypes.Type(name = "functionItem", value = TFunctionItem.class),
        @JsonSubTypes.Type(name = "artifact", value = TArtifact.class),
        @JsonSubTypes.Type(name = "contextEntry", value = TContextEntry.class),
        @JsonSubTypes.Type(name = "inputClause", value = TInputClause.class),
        @JsonSubTypes.Type(name = "outputClause", value = TOutputClause.class),
        @JsonSubTypes.Type(name = "decisionRule", value = TDecisionRule.class),
        @JsonSubTypes.Type(name = "namedElement", value = TNamedElement.class),
        @JsonSubTypes.Type(name = "expression", value = TExpression.class),
})
public abstract class TDMNElement {
    private String description;
    private ExtensionElements extensionElements;
    private String id;
    private String label;
    private final Map<QName, String> otherAttributes = new HashMap<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public ExtensionElements getExtensionElements() {
        return extensionElements;
    }

    public void setExtensionElements(ExtensionElements value) {
        this.extensionElements = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String value) {
        this.label = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

    public static class ExtensionElements {
        private List<Object> any;

        public List<Object> getAny() {
            if (any == null) {
                any = new ArrayList<>();
            }
            return this.any;
        }
    }
}
