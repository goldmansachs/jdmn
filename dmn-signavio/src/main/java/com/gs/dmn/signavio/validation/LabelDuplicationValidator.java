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
package com.gs.dmn.signavio.validation;

import com.gs.dmn.validation.SimpleDMNValidator;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.TDMNElement;
import org.omg.spec.dmn._20191111.model.TDRGElement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class LabelDuplicationValidator extends SimpleDMNValidator {
    void validateElements(List<? extends TDMNElement> elements, List<String> errors) {
        Map<String, List<TDMNElement>> map = new LinkedHashMap<>();
        for(TDMNElement decision: elements) {
            String label = decision.getLabel();
            if (label == null) {
                throw new RuntimeException(String.format("%s has no label", decision.getId()));
            }
            List<TDMNElement> elementList = map.computeIfAbsent(label, k -> new ArrayList<>());
            elementList.add(decision);
        }
        for(Map.Entry<String, List<TDMNElement>> entry: map.entrySet()) {
            String key = entry.getKey();
            List<TDMNElement> elementList = entry.getValue();
            if (!StringUtils.isBlank(key) && elementList.size() > 1) {
                String elementKind = elementList.get(0).getClass().getSimpleName().substring(1);
                errors.add(String.format("Found %d %s with duplicated label '%s'", elementList.size(), elementKind, key));
                for(TDMNElement e: elementList) {
                    if (e instanceof TDRGElement) {
                        errors.add(String.format("Label = '%s' Id = '%s' kind = '%s'", key, e.getId(), e.getClass().getSimpleName()));
                    }
                }
            }
        }
    }
}
