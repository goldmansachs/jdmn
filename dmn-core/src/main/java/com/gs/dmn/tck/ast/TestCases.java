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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder({
        "namespace",
        "otherAttributes",
        "modelName",
        "labels",
        "testCase"
})
public class TestCases extends DMNBaseElement {
    protected String modelName;
    protected Labels labels;
    protected List<TestCase> testCase;
    protected String namespace;
    private final Map<QName, String> otherAttributes = new HashMap<>();

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String value) {
        this.modelName = value;
    }

    public Labels getLabels() {
        return labels;
    }

    public void setLabels(Labels value) {
        this.labels = value;
    }

    public List<TestCase> getTestCase() {
        if (testCase == null) {
            testCase = new ArrayList<>();
        }
        return this.testCase;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String value) {
        this.namespace = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

    @Override
    public <C> Object accept(Visitor visitor, C context) {
        return visitor.visit(this, context);
    }
}
