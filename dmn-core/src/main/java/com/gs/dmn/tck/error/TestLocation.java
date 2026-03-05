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

import com.gs.dmn.tck.ast.TCKBaseElement;
import com.gs.dmn.tck.ast.TestCases;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestLocation {
    private final TestCases testCases;
    private final TCKBaseElement element;

    private final String text;

    public TestLocation(TestCases testCases, TCKBaseElement element) {
        this.testCases = testCases;
        this.element = element;

        // Derive the textual representation
        Map<String, String> parts = new LinkedHashMap<>();
        addPart("testCasesName", testCases.getTestCasesName(), parts);
        addPart("testCase", findTestCaseId(element), parts);
        List<String> notEmptyParts = parts.entrySet().stream()
                .filter(e -> StringUtils.isNotEmpty(e.getValue()))
                .map(e -> String.format("%s = '%s'", e.getKey(), e.getValue())).collect(Collectors.toList());
        this.text = notEmptyParts.isEmpty() ? "" : String.format("(%s)", String.join(", ", notEmptyParts));
    }

    private String findTestCaseId(TCKBaseElement element) {
        while (element != null) {
            if (element instanceof com.gs.dmn.tck.ast.TestCase) {
                return ((com.gs.dmn.tck.ast.TestCase) element).getId();
            }
            element = element.getParent();
        }
        return null;
    }

    public TestCases getTestCases() {
        return testCases;
    }

    public TCKBaseElement getElement() {
        return element;
    }

    private void addPart(String key, String value, Map<String, String> parts) {
        parts.put(key, value);
    }

    public String toText() {
        return text;
    }
}
