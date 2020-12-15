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
package com.gs.dmn.signavio.rdf2dmn;

import com.gs.dmn.AbstractTest;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.transformation.FileTransformer;
import com.gs.dmn.transformation.InputParameters;
import org.apache.commons.io.FileUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.omg.spec.dmn._20191111.model.TDMNElement;
import org.omg.spec.dmn._20191111.model.TLiteralExpression;
import org.omg.spec.dmn._20191111.model.TNamedElement;
import org.slf4j.LoggerFactory;
import org.xmlunit.validation.Languages;
import org.xmlunit.validation.ValidationProblem;
import org.xmlunit.validation.ValidationResult;
import org.xmlunit.validation.Validator;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;

import static com.gs.dmn.serialization.DMNVersion.DMN_11;
import static com.gs.dmn.signavio.rdf2dmn.RDFToDMNTransformer.RDF_FILE_EXTENSION;
import static com.gs.dmn.signavio.rdf2dmn.RDFToDMNTransformer.isRDFFile;
import static org.junit.Assert.*;

public abstract class AbstractRDFToDMNTransformerTest extends AbstractTest {
    private static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(AbstractRDFToDMNTransformerTest.class));
    private final String schemaVersion = "1.1";

    protected void doTestFolder() throws Exception {
        doTestFolder(getInputPath(), getOutputPath(), getExpectedPath());
    }

    protected void doTest(String diagramName) throws Exception {
        String outputPath = getOutputPath();
        String inputPath = getInputPath() + "/" + diagramName + RDF_FILE_EXTENSION;
        String expectedPath = getExpectedPath();
        doTestFolder(inputPath, outputPath, expectedPath, diagramName);
    }

    private void doTestFolder(String inputPath, String outputPath, String expectedPath) throws Exception {
        File inputFolder = new File(resource(inputPath));
        File[] files = inputFolder.listFiles();
        assertNotNull("Not empty folder", files);
        for (File child : files) {
            if (isRDFFile(child)) {
                String fileName = child.getName();
                int index = fileName.lastIndexOf(".");
                String diagramName = fileName.substring(0, index);
                doTestFolder(inputPath, outputPath, expectedPath, diagramName);
            }
        }
    }

    private void doTestFolder(String inputPath, String outputPath, String expectedDMNPath, String diagramName) throws Exception {
        File outputFolder = new File(outputPath);
        outputFolder.mkdirs();

        RDFToDMNTransformer transformer = (RDFToDMNTransformer) makeTransformer(new InputParameters(makeInputParametersMap()), LOGGER);
        transformer.transform(path(inputPath), new File(outputPath).toPath());

        File actualOutputFile = new File(outputFolder, diagramName + DMNConstants.DMN_FILE_EXTENSION);
        String resourcePath = expectedDMNPath + "/" + diagramName + DMNConstants.DMN_FILE_EXTENSION;
        URI resource = signavioResource(resourcePath);
        if (resource != null) {
            File expectedOutputFile = new File(resource.getPath());
            compareFile(expectedOutputFile, actualOutputFile);

            Validator v = Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI);
            URI schemaURI = AbstractRDFToDMNTransformerTest.class.getResource("/dmn/" + schemaVersion + "/dmn.xsd").toURI();
            v.setSchemaSource(new StreamSource(schemaURI.toString()));
            ValidationResult validationResult = v.validateInstance(new StreamSource(actualOutputFile));
            for (ValidationProblem vp : validationResult.getProblems()) {
                System.out.println(vp.toString());
            }
            assertTrue(validationResult.isValid());
        } else {
            createEmptyDMNFile(resourcePath);
            fail(String.format("Cannot find file expected file %s. Created an empty one.", resourcePath));
        }
    }

    private void createEmptyDMNFile(String resourcePath) throws IOException {
        File file = new File("src/test/resources" +
                (resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath));
        FileUtils.writeStringToFile(file,
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<dmn:definitions xmlns:dmn=\"http://www.omg.org/spec/DMN/20151101/dmn.xsd\" xmlns:cip=\"http://www.gs.com/cip\" xmlns:feel=\"http://www.omg.org/spec/FEEL/20140401\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://www.omg.org/spec/DMN/20151101/dmn.xsd\" name=\"XXX\">\n" +
                "</dmn:definitions>"
        )
        ;
    }

    private final SAXBuilder builder = new SAXBuilder();

    private FileTransformer makeTransformer(InputParameters inputParameters, BuildLogger logger) {
        return new RDFToDMNTransformer(inputParameters, logger);
    }

    private void compareFile(File expectedOutputFile, File actualOutputFile) throws Exception {
        if (expectedOutputFile.isFile() && actualOutputFile.isFile()) {
            Document expectedDocument = builder.build(expectedOutputFile);
            sort(expectedDocument);
            String expectedContent = new XMLOutputter().outputString(expectedDocument);

            Document actualDocument = builder.build(actualOutputFile);
            sort(actualDocument);
            String actualContent = new XMLOutputter().outputString(actualDocument);

            assertEquals(expectedOutputFile.getName(), expectedContent, actualContent);
        } else {
            throw new DMNRuntimeException(String.format("Cannot compare folder with file %s %s ", expectedOutputFile.getName(), actualOutputFile.getName()));
        }
    }

    private void sort(Document document) {
        Comparator<Element> comparator = new DMNNodeComparator();

        // Sort definitions
        Element rootElement = document.getRootElement();
        if (rootElement != null) {
            rootElement.sortChildren(comparator);

            // Sort decision
            Element decision = rootElement.getChild("decision", Namespace.getNamespace("dmn", DMN_11.getNamespace()));
            if (decision != null) {
                decision.sortChildren(comparator);
            }
        }
    }

    protected void assertLiteralExpression(TLiteralExpression inputExpression, String stringType, String id, String text) {
        assertEquals(id, inputExpression.getId());
        assertEquals(stringType, inputExpression.getTypeRef());
        assertEquals(text, inputExpression.getText());
    }

    protected void assertNamedElement(TNamedElement decision, String id, String name) {
        assertEquals(id, decision.getId());
        assertEquals(name, decision.getName());
    }

    protected void assertDMNElement(TDMNElement decision, String id, String label) {
        assertEquals(id, decision.getId());
        assertEquals(label, decision.getLabel());
    }

    protected Map<String, String> makeInputParametersMap() {
        Map<String, String> map = super.makeInputParametersMap();
        map.put("namespace", "http://www.gs.com/cip");
        map.put("prefix", "cip");
        return map;
    }

    private Path path(String path) {
        File file = new File(signavioResource(path));
        return file.toPath();
    }

    private String getInputPath() {
        return "rdf/" + getTestFolder();
    }

    private String getOutputPath() {
        return "target/rdf2dmn/" + getTestFolder() + "/";
    }

    private String getExpectedPath() {
        return "rdf/rdf2dmn/expected/" + getTestFolder();
    }

    protected abstract String getTestFolder();
}
