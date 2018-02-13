/**
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
package com.gs.dmn.runtime.annotation;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationSet extends LinkedList<Annotation> {
    public void addAnnotation(String decisionName, int ruleIndex, List<String> annotationList) {
        if (annotationList != null && !annotationList.isEmpty()) {
            String annotation = annotationList.stream().collect(Collectors.joining(" "));
            this.addAnnotation(decisionName, ruleIndex, annotation);
        }
    }

    public void addAnnotation(String decisionName, int ruleIndex, String annotation) {
        if (!StringUtils.isBlank(annotation)) {
            Annotation element = new Annotation(decisionName, ruleIndex, annotation);
            this.add(element);
        }
    }

    public Set<Annotation> toSet() {
        return new LinkedHashSet<>(this);
    }
}
