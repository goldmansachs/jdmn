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
import java.util.HashMap;
import java.util.Map;

@JsonPropertyOrder({
        "type",
        "namespace",
        "name",
        "cast",
        "errorResult",
        "otherAttributes",
        "expected",
        "computed"
})
public class ResultNode extends DMNBaseElement {
    protected ValueType computed;
    protected ValueType expected;
    protected Boolean errorResult;
    protected String name;
    protected String namespace;
    protected String type;
    protected String cast;
    private final Map<QName, String> otherAttributes = new HashMap<>();

    public ValueType getComputed() {
        return computed;
    }

    public void setComputed(ValueType value) {
        this.computed = value;
    }

    public ValueType getExpected() {
        return expected;
    }

    public void setExpected(ValueType value) {
        this.expected = value;
    }

    public boolean isErrorResult() {
        if (errorResult == null) {
            return false;
        } else {
            return errorResult;
        }
    }

    public void setErrorResult(Boolean value) {
        this.errorResult = value;
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

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String value) {
        this.cast = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

    @Override
    public <C> Object accept(Visitor visitor, C context) {
        return visitor.visit(this, context);
    }
}
