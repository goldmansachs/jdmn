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
package com.gs.dmn.signavio.testlab.visitor;

import com.gs.dmn.signavio.testlab.InputParameterDefinition;
import com.gs.dmn.signavio.testlab.OutputParameterDefinition;

import java.util.ArrayList;
import java.util.List;

public class TestLabContext {
    private List<InputParameterDefinition> inputParameterDefinitions = new ArrayList<>();
    private List<OutputParameterDefinition> outputParameterDefinitions = new ArrayList<>();

    private int testIndex;
    private int inputIndex;
    private int outputIndex;

    public List<InputParameterDefinition> getInputParameterDefinitions() {
        return inputParameterDefinitions;
    }

    public void setInputParameterDefinitions(List<InputParameterDefinition> inputParameterDefinitions) {
        if (inputParameterDefinitions != null) {
            this.inputParameterDefinitions = inputParameterDefinitions;
        }
    }

    public List<OutputParameterDefinition> getOutputParameterDefinitions() {
        return outputParameterDefinitions;
    }

    public void setOutputParameterDefinitions(List<OutputParameterDefinition> outputParameterDefinitions) {
        if (outputParameterDefinitions != null) {
            this.outputParameterDefinitions = outputParameterDefinitions;
        }
    }

    public int getTestIndex() {
        return testIndex;
    }

    public void setTestIndex(int testIndex) {
        this.testIndex = testIndex;
    }

    public int getInputIndex() {
        return inputIndex;
    }

    public void setInputIndex(int inputIndex) {
        this.inputIndex = inputIndex;
    }

    public int getOutputIndex() {
        return outputIndex;
    }

    public void setOutputIndex(int outputIndex) {
        this.outputIndex = outputIndex;
    }
}
