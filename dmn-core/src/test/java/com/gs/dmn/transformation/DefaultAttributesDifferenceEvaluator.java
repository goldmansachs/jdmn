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
package com.gs.dmn.transformation;

import com.gs.dmn.serialization.DMNVersion;
import org.w3c.dom.Node;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.ComparisonType;
import org.xmlunit.diff.DifferenceEvaluator;

import javax.xml.namespace.QName;
import java.util.*;

public class DefaultAttributesDifferenceEvaluator implements DifferenceEvaluator {
    // The following DMN attributes have default values
    // DMN 1.1
    //     <xsd:attribute name="expressionLanguage" type="xsd:anyURI" use="optional" default="http://www.omg.org/spec/FEEL/20140401"
    //     <xsd:attribute name="typeLanguage" type="xsd:anyURI" use="optional" default="http://www.omg.org/spec/FEEL/20140401"
    //     <xsd:attribute name="isCollection" type="xsd:boolean" use="optional" default="false"
    //     <xsd:attribute name="hitPolicy" type="tHitPolicy" use="optional" default="UNIQUE"
    //     <xsd:attribute name="preferredOrientation" type="tDecisionTableOrientation" use="optional" default="Rule-as-Row"
    //     <xsd:attribute name="textFormat" type="xsd:string" default="text/plain"/>
    // 	   <xsd:attribute name="associationDirection" type="tAssociationDirection" default="None"/>
    // DMN 1.2:
    // DMNDI:
    //     <xsd:attribute name="isCollapsed" type="xsd:boolean" use="optional" default="false"/>
    // DMN 1.3
    //     <xsd:attribute name="kind" type="tFunctionKind" default="FEEL"/>
    private static final Map<String, List<QName>> dmnNodeToAttribute = new LinkedHashMap<String, List<QName>>() {{
        put("definitions", Arrays.asList(new QName("expressionLanguage"), new QName("typeLanguage")));
        put("itemDefinition", Collections.singletonList(new QName("isCollection")));
        put("itemComponent", Collections.singletonList(new QName("isCollection")));
        put("decisionTable", Arrays.asList(new QName("hitPolicy"), new QName("preferredOrientation")));
        put("textAnnotation", Collections.singletonList(new QName("textFormat")));
        put("association", Collections.singletonList(new QName("associationDirection")));
        put("DMNShape", Collections.singletonList(new QName("isCollapsed")));
        put("encapsulatedLogic", Collections.singletonList(new QName("kind")));
    }};
    private static final Map<QName, String> dmnAttributeToDefaultValue = new LinkedHashMap<QName, String>() {{
           put(new QName("expressionLanguage"), DMNVersion.LATEST.getFeelNamespace());
           put(new QName("typeLanguage"), DMNVersion.LATEST.getFeelNamespace());
           put(new QName("isCollection"), "false");
           put(new QName("hitPolicy"), "UNIQUE");
           put(new QName("preferredOrientation"), "Rule-as-Row");
           put(new QName("textFormat"), "text/plain");
           put(new QName("associationDirection"), "None");
           put(new QName("isCollapsed"), "false");
           put(new QName("kind"), "FEEL");
    }};

    // The following TCK attributes have default values
    //     <xs:attribute name="errorResult" type="xs:boolean" default="false"/>
    //     <xs:attribute name="type" type="testCaseType"/>
    private static final Map<String, List<QName>> tckNodesToAttributes = new LinkedHashMap<String, List<QName>>() {{
            put("resultNode", Collections.singletonList(new QName("errorResult")));
            put("testCase", Collections.singletonList(new QName("type")));
    }};

    private static final Map<QName, String> tckAttributeToDefaultValue = new LinkedHashMap<QName, String>() {{
        put(new QName("errorResult"), "false");
        put(new QName("type"), "decision");
    }};

    public static final DifferenceEvaluator DMN_EVALUATOR = new DefaultAttributesDifferenceEvaluator(dmnNodeToAttribute, dmnAttributeToDefaultValue);

    public static final DifferenceEvaluator TCK_EVALUATOR = new DefaultAttributesDifferenceEvaluator(tckNodesToAttributes, tckAttributeToDefaultValue);

    private final Map<String, List<QName>> nodeToAttributes;
    private final Map<QName, String> attributeToDefaultValue;

    public DefaultAttributesDifferenceEvaluator(Map<String, List<QName>> nodeToAttributes, Map<QName, String> attributeToDefaultValue) {
        this.nodeToAttributes = nodeToAttributes;
        this.attributeToDefaultValue = attributeToDefaultValue;
    }

    @Override
    public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
        ComparisonType type = comparison.getType();
        Node expectedNode = comparison.getControlDetails().getTarget();
        Node actualNode = comparison.getTestDetails().getTarget();
        if (outcome == ComparisonResult.EQUAL) {
            // evaluate only differences.
            return outcome;
        }

        // Check schema location
        if (type == ComparisonType.SCHEMA_LOCATION || type == ComparisonType.NO_NAMESPACE_SCHEMA_LOCATION) {
            // similar when different xsi:schemaLocation and xsi:noNamspaceSchemaLocation
            return outcome == ComparisonResult.SIMILAR ? ComparisonResult.DIFFERENT : outcome;
        }
        // Check number of attributes
        if (type == ComparisonType.ELEMENT_NUM_ATTRIBUTES) {
            // Similar if node has attributes with default values
            if (expectedNode.getNodeName().equals(actualNode.getNodeName())
                    && nodeToAttributes.containsKey(removeNameSpace(expectedNode))) {
                return ComparisonResult.SIMILAR;
            }
        }
        // Check value of attribute
        if (type == ComparisonType.ATTR_VALUE) {
            // Ignore DiagramElement.documentation
            if (expectedNode.getNodeName().equals(actualNode.getNodeName())
                    && expectedNode.getNodeType() == Node.ATTRIBUTE_NODE
                    && expectedNode.getLocalName().equals("documentation")) {
                return ComparisonResult.SIMILAR;
            }
        }
        // Check value of missing attribute
        if (comparison.getType() == ComparisonType.ATTR_NAME_LOOKUP) {
            // If the attribute has default value
            QName defaultAttrQName = getDefaultAttributeQName(comparison);
            if (defaultAttrQName != null) {
                String defaultValue = attributeToDefaultValue.get(defaultAttrQName);
                String actualValue = getDefaultAttributeValue(actualNode, defaultAttrQName, defaultValue);
                String expectedValue = getDefaultAttributeValue(expectedNode, defaultAttrQName, defaultValue);
                if (Objects.equals(expectedValue, actualValue)) {
                    return ComparisonResult.SIMILAR;
                }
            }
        }
        return outcome;
    }

    private QName getDefaultAttributeQName(Comparison comparison) {
        QName attributeQName = null;
        Object expectedAttribute = comparison.getControlDetails().getValue();
        Object actualAttribute = comparison.getTestDetails().getValue();
        if (expectedAttribute instanceof QName && attributeToDefaultValue.containsKey(expectedAttribute)) {
            attributeQName = (QName) expectedAttribute;
        } else if (actualAttribute instanceof QName && attributeToDefaultValue.containsKey(actualAttribute)) {
            attributeQName = (QName) actualAttribute;
        }
        return attributeQName;
    }

    private String getDefaultAttributeValue(Node node, QName qName, String defaultValue) {
        Node attribute = node.getAttributes().getNamedItem(removeNameSpace(qName));
        return attribute == null ? defaultValue : attribute.getNodeValue();
    }

    private String removeNameSpace(Node target) {
        return target.getLocalName();
    }

    private String removeNameSpace(QName qName) {
        return qName.getLocalPart();
    }
}
