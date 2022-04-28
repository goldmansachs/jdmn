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
package com.gs.dmn.serialization;

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.ComparisonType;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.DifferenceEvaluators;
import org.xmlunit.validation.Languages;
import org.xmlunit.validation.ValidationProblem;
import org.xmlunit.validation.ValidationResult;
import org.xmlunit.validation.Validator;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class AbstractUnmarshalMarshalTest {
    protected static final Logger LOG = LoggerFactory.getLogger(AbstractUnmarshalMarshalTest.class);

    protected void testRoundTrip(String subDir, String xmlFile) throws Exception {
        DMNMarshaller marshaller = getMarshaller();
        testRoundTrip(subDir, xmlFile, marshaller);
    }

    protected void testRoundTrip(String subDir, String xmlFile, DMNMarshaller marshaller) throws Exception {
        File baseInputDir = new File("target/test-classes/");
        File baseOutputDir = new File("target/");

        // Validate input XML
        File inputXMLFile = new File(baseInputDir, subDir + xmlFile);
        Validator v = Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI);
        v.setSchemaSource(getSchemaSource());
        ValidationResult validateInputResult = v.validateInstance(new StreamSource(inputXMLFile));
        if (!validateInputResult.isValid()) {
            for (ValidationProblem p : validateInputResult.getProblems()) {
                LOG.error("{}", p);
            }
        }
        assertTrue(inputXMLFile.getAbsolutePath(), validateInputResult.isValid());

        // Read definitions
        FileInputStream fis = new FileInputStream(inputXMLFile);
        TDefinitions unmarshal = marshaller.unmarshal(new InputStreamReader(fis), true);

        // Write definitions
        LOG.debug("{}", marshaller.marshal(unmarshal));
        File outputXMLFile = new File(baseOutputDir, subDir + xmlFile);
        outputXMLFile.getParentFile().mkdirs();
        try (FileWriter targetFos = new FileWriter(outputXMLFile)) {
            marshaller.marshal(unmarshal, targetFos);
        }

        // Validate output XML:
        ValidationResult validateOutputResult = v.validateInstance(new StreamSource(outputXMLFile));
        if (!validateOutputResult.isValid()) {
            for (ValidationProblem p : validateOutputResult.getProblems()) {
                LOG.error("{}", p);
            }
        }
        assertTrue(outputXMLFile.getAbsolutePath(), validateOutputResult.isValid());

        // Compare input and output
        LOG.debug("\n---\nDefault XMLUnit comparison:");
        Source control = Input.fromFile(inputXMLFile).build();
        Source test = Input.fromFile(outputXMLFile).build();
        Diff allDiffsSimilarAndDifferent = DiffBuilder
                .compare(control)
                .withTest(test)
                .build();
        allDiffsSimilarAndDifferent.getDifferences().forEach(m -> LOG.debug("{}", m));
        // see XSD schemas
        LOG.info("XMLUnit comparison with customized similarity for defaults:");
        Set<QName> attributesWithDefaultValue = getAttributesWithDefaultValues();
        Set<String> nodesHavingAttributesWithDefaultValues = getNodesHavingAttributesWithDefaultValues();
        Diff checkSimilar = DiffBuilder
                .compare(control)
                .withTest(test)
                .withDifferenceEvaluator(
                        DifferenceEvaluators.chain(DifferenceEvaluators.Default,
                                ((comparison, outcome) -> {
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
                                })))
                .ignoreWhitespace()
                .checkForSimilar()
                .build();
        checkSimilar.getDifferences().forEach(m -> LOG.error("{}", m));
        if (!checkSimilar.getDifferences().iterator().hasNext()) {
            LOG.info("No diffs");
        }
        assertFalse("XML are NOT similar: " + checkSimilar, checkSimilar.hasDifferences());
    }

    protected DMNMarshaller getMarshaller() {
        return DMNMarshallerFactory.newDefaultMarshaller();
    }

    protected abstract StreamSource getSchemaSource();

    protected abstract Set<QName> getAttributesWithDefaultValues();

    protected abstract Set<String> getNodesHavingAttributesWithDefaultValues();

    protected abstract String safeStripDMNPrefix(Node target);
}
