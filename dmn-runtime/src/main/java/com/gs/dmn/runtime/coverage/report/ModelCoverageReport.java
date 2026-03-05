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

import java.util.*;

public class ModelCoverageReport extends CoverageReport {
    private final List<String> columnNames = Arrays.asList(
            "Namespace",
            "Model",
            "Elements",
            "Missed Elements",
            "Elements Coverage",
            "Rules",
            "Missed Rules",
            "Rules Coverage"
    );

    @Override
    protected List<String> columnNames() {
        return columnNames;
    }

    @Override
    protected List<List<String>> toLines(ModelCoverageTrace modelCoverage) {
        return Arrays.asList(toLine(modelCoverage));
    }

    protected List<String> toLine(ModelCoverageTrace modelCoverage) {
        List<String> line = new ArrayList<>();
        // Add namespace.
        line.add(modelCoverage.getNamespace());
        line.add(modelCoverage.getModelName());

        // Add elements count.
        int elementsCount = modelCoverage.getElementCount();
        line.add(elementsCount + "");

        // Add missed elements count.
        Set<ElementCoverageTrace> coveredElements = new LinkedHashSet<>(modelCoverage.getElementTraces());
        int coveredElementsCount = coveredElements.size();
        // Elements count can be 0 in case not having a TCK file for that model - where the element count is initialized.
        int missedElementsCount = elementsCount == 0 ? 0 : elementsCount  - coveredElementsCount;
        line.add(missedElementsCount + "");

        // Add elements coverage.
        String elementsCoverage = elementsCount == 0 ? COVERAGE_FOR_MISSING : String.format("%.2f%%", (coveredElementsCount * 100.0) / elementsCount);
        line.add(elementsCoverage);

        // Add rules count.
        int rulesCount = coveredElements.stream().map(ElementCoverageTrace::getRulesCount).reduce(0, Integer::sum);
        line.add(rulesCount + "");

        // Add missed rules count.
        long coveredRulesCount = coveredElements.stream().flatMap(e -> e.getRuleTraces().stream()).count();
        long missedRules = rulesCount - coveredRulesCount;
        line.add(missedRules + "");

        // Add rules coverage.
        String rulesCoverage = rulesCount == 0 ? COVERAGE_FOR_MISSING : String.format("%.2f%%", (coveredRulesCount * 100.0) / rulesCount);
        line.add(rulesCoverage);

        return line;
    }
}
