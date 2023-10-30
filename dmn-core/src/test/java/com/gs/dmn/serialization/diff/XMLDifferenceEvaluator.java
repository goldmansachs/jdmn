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

import org.w3c.dom.Node;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.ComparisonType;
import org.xmlunit.diff.DifferenceEvaluator;

import javax.xml.namespace.QName;
import java.util.Set;

public abstract class XMLDifferenceEvaluator implements DifferenceEvaluator {
    public static DifferenceEvaluator dmn11DiffEvaluator() {
        return new DMN11DifferenceEvaluator();
    }

    public static DifferenceEvaluator dmn12DiffEvaluator() {
        return new DMN12DifferenceEvaluator();
    }

    public static DifferenceEvaluator dmn13DiffEvaluator() {
        return new DMN13DifferenceEvaluator();
    }

    public static DifferenceEvaluator dmn14DiffEvaluator() {
        return new DMN14DifferenceEvaluator();
    }

    public static DifferenceEvaluator tck1DiffEvaluator() {
        return new TCK1DifferenceEvaluator();
    }

    @Override
    public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
        // see XSD schemas
        Set<QName> attributesWithDefaultValue = getAttributesWithDefaultValues();
        Set<String> nodesHavingAttributesWithDefaultValues = getNodesHavingAttributesWithDefaultValues();

        // Ignore encoding and standalone attributes in the XML header
        if (outcome == ComparisonResult.DIFFERENT && comparison.getType() == ComparisonType.XML_STANDALONE) {
            return ComparisonResult.SIMILAR;
        }
        if (outcome == ComparisonResult.DIFFERENT && comparison.getType() == ComparisonType.ELEMENT_NUM_ATTRIBUTES) {
            if (comparison.getControlDetails().getTarget().getNodeName().equals(comparison.getTestDetails().getTarget().getNodeName())
                    && nodesHavingAttributesWithDefaultValues.contains(safeStripDMNPrefix(comparison.getControlDetails().getTarget()))) {
                return ComparisonResult.SIMILAR;
            }
        }
        // DMNDI/DMNDiagram#documentation is actually deserialized escaped with newlines as &#10; by the XML JDK infra.
        if (outcome == ComparisonResult.DIFFERENT && comparison.getType() == ComparisonType.ATTR_VALUE) {
            if (comparison.getControlDetails().getTarget().getNodeName().equals(comparison.getTestDetails().getTarget().getNodeName())
                    && comparison.getControlDetails().getTarget().getNodeType() == Node.ATTRIBUTE_NODE
                    && comparison.getControlDetails().getTarget().getLocalName().equals("documentation")) {
                return ComparisonResult.SIMILAR;
            }
        }
        if (outcome == ComparisonResult.DIFFERENT && comparison.getType() == ComparisonType.ATTR_NAME_LOOKUP) {
            boolean attributeHasDefaultValue = false;
            QName attributeWithDefaultValue = null;
            if (comparison.getControlDetails().getValue() == null && attributesWithDefaultValue.contains(comparison.getTestDetails().getValue())) {
                for (QName a : attributesWithDefaultValue) {
                    boolean isPathToAttribute = comparison.getTestDetails().getXPath().endsWith("@" + a);
                    if (isPathToAttribute) {
                        attributeHasDefaultValue = true;
                        attributeWithDefaultValue = a;
                        break;
                    }
                }
            }
            if (attributeHasDefaultValue) {
                if (comparison.getTestDetails().getXPath().equals(comparison.getControlDetails().getXPath() + "/@" + attributeWithDefaultValue)) {
                    // TODO check if values are the same
                    return ComparisonResult.SIMILAR;
                }
            }
        }
        return outcome;
    }

    protected abstract Set<String> getNodesHavingAttributesWithDefaultValues();

    protected abstract Set<QName> getAttributesWithDefaultValues();

    protected abstract String safeStripDMNPrefix(Node target);
}