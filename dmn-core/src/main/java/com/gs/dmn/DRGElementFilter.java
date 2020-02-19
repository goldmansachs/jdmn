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
package com.gs.dmn;

import org.omg.spec.dmn._20180521.model.TDRGElement;
import org.omg.spec.dmn._20180521.model.TDecision;
import org.omg.spec.dmn._20180521.model.TInputData;
import org.omg.spec.dmn._20180521.model.TInvocable;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class DRGElementFilter {
    private final DMNModelRepository dmnModelRepository;

    public DRGElementFilter(DMNModelRepository dmnModelRepository) {
        this.dmnModelRepository = dmnModelRepository;
    }

    public List<TInputData> filterInputs(List<TInputData> elements) {
        LinkedHashSet<String> seen = new LinkedHashSet<>();
        return elements.stream().filter(e -> seen.add(this.dmnModelRepository.getQualifiedName(e))).collect(Collectors.toList());
    }

    public List<TDecision> filterDecisions(List<TDecision> elements) {
        LinkedHashSet<String> seen = new LinkedHashSet<>();
        return elements.stream().filter(e -> seen.add(this.dmnModelRepository.getQualifiedName(e))).collect(Collectors.toList());
    }

    public List<TInvocable> filterInvocables(List<TInvocable> elements) {
        LinkedHashSet<String> seen = new LinkedHashSet<>();
        return elements.stream().filter(e -> seen.add(this.dmnModelRepository.getQualifiedName(e))).collect(Collectors.toList());
    }
}
