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
package com.gs.dmn.tck.ast;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder({
        "id",
        "type",
        "namespace",
        "name",
        "invocableName",
        "otherAttributes",
        "description",
        "inputNode",
        "resultNode",
        "extensionElements"
})
public class TestCase extends DMNBaseElement {
    protected String description;
    protected List<InputNode> inputNode;
    protected List<ResultNode> resultNode;
    protected ExtensionElements extensionElements;
    protected String id;
    protected TestCaseType type;
    protected String invocableName;
    protected String name;
    protected String namespace;
    protected Map<QName, String> otherAttributes;

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public List<InputNode> getInputNode() {
        if (inputNode == null) {
            inputNode = new ArrayList<>();
        }
        return this.inputNode;
    }

    public List<ResultNode> getResultNode() {
        if (resultNode == null) {
            resultNode = new ArrayList<>();
        }
        return this.resultNode;
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

    public TestCaseType getType() {
        if (type == null) {
            return TestCaseType.DECISION;
        } else {
            return type;
        }
    }

    public void setType(TestCaseType value) {
        this.type = value;
    }

    public String getInvocableName() {
        return invocableName;
    }

    public void setInvocableName(String value) {
        this.invocableName = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String value) {
        this.namespace = value;
    }

    public Map<QName, String> getOtherAttributes() {
        if (otherAttributes == null) {
            this.otherAttributes = new LinkedHashMap<>();
        }
        return this.otherAttributes;
    }

    @Override
    public <C> Object accept(Visitor visitor, C context) {
        return visitor.visit(this, context);
    }
}
