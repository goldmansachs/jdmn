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
package com.gs.dmn.runtime.coverage.report;

import com.gs.dmn.runtime.coverage.trace.ElementCoverageTrace;
import com.gs.dmn.runtime.coverage.trace.ModelCoverageTrace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElementsCoverageReport extends CoverageReport {
    private final List<String> columnNames = Arrays.asList(
            "Namespace",
            "Element",
            "Rules",
            "Missed Rules",
            "Rules Coverage"
    );

    @Override
    protected List<String> columnNames() {
        return this.columnNames;
    }

    @Override
    protected List<List<String>> toLines(ModelCoverageTrace modelCoverage) {
        List<List<String>> result = new ArrayList<>();
        for (ElementCoverageTrace elementTrace : modelCoverage.getElementTraces()) {
            result.add(toLine(modelCoverage, elementTrace));
        }
        return result;
    }

    private List<String> toLine(ModelCoverageTrace modelCoverage, ElementCoverageTrace elementCoverage) {
        List<String> line = new ArrayList<>();
        
        // Add element name with namespace.
        line.add(modelCoverage.getNamespace());
        line.add(elementCoverage.getName());

        // Add rules count.
        int rulesCount = elementCoverage.getRulesCount();
        line.add(rulesCount + "");

        // Add missed rules count.
        long coveredRulesCount = elementCoverage.getRuleTraces().size();
        long missedRules = rulesCount - coveredRulesCount;
        line.add(missedRules + "");

        // Add rules coverage.
        String rulesCoverage = rulesCount == 0 ? "N/A" : String.format("%.2f%%", (coveredRulesCount * 100.0) / rulesCount);
        line.add(rulesCoverage);

        return line;
    }
}
