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

import com.gs.dmn.transformation.AbstractFileTransformerTest;
import org.xmlunit.validation.Languages;
import org.xmlunit.validation.ValidationProblem;
import org.xmlunit.validation.ValidationResult;
import org.xmlunit.validation.Validator;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.assertTrue;

public abstract class AbstractUnmarshalMarshalTest<D, M> extends AbstractFileTransformerTest {
    protected void testRoundTrip(String inputFilePath) throws Exception {
        M marshaller = getMarshaller();
        testRoundTrip(inputFilePath, marshaller);
    }

    protected void testRoundTrip(String inputFilePath, M marshaller) throws Exception {
        File inputFile = new File(resource(inputFilePath));
        String outputPath = String.format("target/%s/", new File(inputFilePath).getParent());
        testRoundTrip(inputFile, marshaller,  outputPath);
    }

//    private String makeOutputPath(String version) {
//        return String.format("%s/xstream/tck/%s", rootOutputPath, version);
//    }

    protected void testRoundTrip(File inputFile, M marshaller, String outputFilePath) throws Exception {
        // Validate input XML
        validateXSDSchema(inputFile);

        // Read definitions
        FileInputStream fis = new FileInputStream(inputFile);
        D definitions = readModel(marshaller, fis);

        // Write definitions
        File baseOutputDir = new File(outputFilePath);
        baseOutputDir.mkdirs();
        File outputFile = new File(baseOutputDir, inputFile.getName());
        writeModel(marshaller, definitions, outputFile);

        // Validate output XML:
        validateXSDSchema(outputFile);

        // Compare input and output
        compareFile(inputFile, outputFile);
    }

    protected void validateXSDSchema(File outputDMNFile) {
        StreamSource schemaSource = getSchemaSource();
        Validator v = Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI);
        v.setSchemaSource(schemaSource);

        ValidationResult validateOutputResult = v.validateInstance(new StreamSource(outputDMNFile));
        if (!validateOutputResult.isValid()) {
            for (ValidationProblem p : validateOutputResult.getProblems()) {
                LOGGER.error("" + p);
            }
        }
        assertTrue(outputDMNFile.getAbsolutePath(), validateOutputResult.isValid());
    }

    protected abstract M getMarshaller();

    protected abstract D readModel(M marshaller, FileInputStream fis);

    protected abstract void writeModel(M marshaller, D definitions, File outputDMNFile) throws Exception;

    protected abstract StreamSource getSchemaSource();
}
