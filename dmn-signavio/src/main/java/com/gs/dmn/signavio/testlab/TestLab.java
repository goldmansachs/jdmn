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
package com.gs.dmn.signavio.testlab;

import java.util.List;

public class TestLab extends TestLabElement {
    private String source;
    private List<InputParameterDefinition> inputParameterDefinitions;
    private List<OutputParameterDefinition> outputParameterDefinitions;
    private List<TestCase> testCases;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getModelName() {
        return getRootOutputParameter().getModelName();
    }

    public List<InputParameterDefinition> getInputParameterDefinitions() {
        return inputParameterDefinitions;
    }

    public List<OutputParameterDefinition> getOutputParameterDefinitions() {
        return outputParameterDefinitions;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public OutputParameterDefinition getRootOutputParameter() {
        try {
            return outputParameterDefinitions.get(0);
        } catch (Exception e) {
            throw new IllegalArgumentException("Missing root output parameter");
        }
    }

    public String getRootDecisionId() {
        try {
            return getRootOutputParameter().getId();
        } catch (Exception e) {
            throw new IllegalArgumentException("Missing root decision ID");
        }
    }

    @Override
    public <R, C> R accept(Visitor<R, C> visitor, C context) {
        return visitor.visit(this, context);
    }

}
