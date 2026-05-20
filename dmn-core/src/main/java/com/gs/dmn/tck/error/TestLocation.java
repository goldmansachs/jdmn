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
package com.gs.dmn.tck.error;

import com.gs.dmn.error.ErrorLocation;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestLocation extends ErrorLocation {
    private final String testCasesName;
    private final String testCasesId;
    private final String modelName;

    public TestLocation(String testCasesName, String testCasesId, String modelName) {
        this.testCasesName = testCasesName;
        this.testCasesId = testCasesId;
        this.modelName = modelName;

        // Derive the textual representation
        Map<String, String> parts = new LinkedHashMap<>();
        addPart("testCasesName", testCasesName, parts);
        addPart("testCase", testCasesId, parts);
        addPart("modelName", modelName, parts);
        initText(parts);
    }

    public String getTestCasesName() {
        return testCasesName;
    }

    public String getTestCasesId() {
        return testCasesId;
    }

    public String getModelName() {
        return modelName;
    }
}
