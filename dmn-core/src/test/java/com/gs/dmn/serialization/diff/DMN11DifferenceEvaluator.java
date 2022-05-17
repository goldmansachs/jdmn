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
package com.gs.dmn.serialization.diff;

import com.gs.dmn.serialization.DMNVersion;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class DMN11DifferenceEvaluator extends XMLDifferenceEvaluator {
    @Override
    protected Set<QName> getAttributesWithDefaultValues() {
        Set<QName> attributesWithDefaultValue = new HashSet<>();
        attributesWithDefaultValue.addAll(Arrays.asList(
                new QName("expressionLanguage"),
                new QName("typeLanguage"),
                new QName("isCollection"),
                new QName("hitPolicy"),
                new QName("preferredOrientation"),
                new QName("textFormat"),
                new QName("associationDirection")));
        return attributesWithDefaultValue;
    }

    @Override
    protected Set<String> getNodesHavingAttributesWithDefaultValues() {
        Set<String> nodesHavingAttributesWithDefaultValues = new HashSet<>();
        nodesHavingAttributesWithDefaultValues.addAll(Arrays.asList(
                "definitions",
                "itemDefinition",
                "itemComponent",
                "decisionTable",
                "textAnnotation",
                "association"));
        return nodesHavingAttributesWithDefaultValues;
    }

    @Override
    protected String safeStripDMNPrefix(Node target) {
        if (DMNVersion.DMN_11.getNamespace().equals(target.getNamespaceURI())) {
            return target.getLocalName();
        }
        return null;
    }
}