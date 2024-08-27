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
    private static final List<String> dmnNodesWithDefaultAttributes = Arrays.asList(
            "definitions", "decisionTable", "itemDefinition", "itemComponent", "encapsulatedLogic", "textAnnotation", "association", "DMNShape"
    );
    private static final Map<QName, String> dmnAttributesWithDefaultValues = new LinkedHashMap<QName, String>() {{
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
    private static final List<String> tckNodesWithDefaultAttributes = Arrays.asList(
            "resultNode", "testCase"
    );
    private static final Map<QName, String> tckAttributesWithDefaultValues = new LinkedHashMap<QName, String>() {{
        put(new QName("errorResult"), "false");
        put(new QName("type"), "decision");
    }};

    public static final DifferenceEvaluator DMN_EVALUATOR = new DefaultAttributesDifferenceEvaluator(dmnNodesWithDefaultAttributes, dmnAttributesWithDefaultValues);

    public static final DifferenceEvaluator TCK_EVALUATOR = new DefaultAttributesDifferenceEvaluator(tckNodesWithDefaultAttributes, tckAttributesWithDefaultValues);

    private final List<String> nodesWithDefaultAttributes;
    private final Map<QName, String> attributesWithDefaultValues;

    public DefaultAttributesDifferenceEvaluator(List<String> nodesWithDefaultAttributes, Map<QName, String> attributesWithDefaultValues) {
        this.nodesWithDefaultAttributes = nodesWithDefaultAttributes;
        this.attributesWithDefaultValues = attributesWithDefaultValues;
    }

    @Override
    public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
        ComparisonType type = comparison.getType();
        Node expectedNode = comparison.getControlDetails().getTarget();
        if (outcome == ComparisonResult.DIFFERENT) {
            // Check number of attributes
            Node actualNode = comparison.getTestDetails().getTarget();
            if (type == ComparisonType.ELEMENT_NUM_ATTRIBUTES) {
                if (expectedNode.getNodeName().equals(actualNode.getNodeName())
                        && nodesWithDefaultAttributes.contains(removeNameSpace(expectedNode))) {
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
                QName whichDefaultAttr = null;
                if (comparison.getControlDetails().getValue() == null && attributesWithDefaultValues.containsKey(comparison.getTestDetails().getValue())) {
                    for (QName a : attributesWithDefaultValues.keySet()) {
                        boolean check = comparison.getTestDetails().getXPath().endsWith("@" + a);
                        if (check) {
                            whichDefaultAttr = a;
                            break;
                        }
                    }
                }
                if (whichDefaultAttr != null) {
                    String defaultValue = attributesWithDefaultValues.get(whichDefaultAttr);
                    String actualValue = getAttributeValue(actualNode, whichDefaultAttr, defaultValue);
                    String expectedValue = getAttributeValue(expectedNode, whichDefaultAttr, defaultValue);
                    if (Objects.equals(expectedValue, actualValue)) {
                        return ComparisonResult.SIMILAR;
                    }
                }
            }
            return outcome;
        }
        return outcome;
    }

    private String getAttributeValue(Node node, QName qName, String defaultValue) {
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
