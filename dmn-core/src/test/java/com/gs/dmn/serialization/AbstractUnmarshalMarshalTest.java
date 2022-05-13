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
    protected void testRoundTrip(String subDir, String dmnFile) throws Exception {
        M marshaller = getMarshaller();
        testRoundTrip(subDir, dmnFile, marshaller);
    }

    protected void testRoundTrip(String subDir, String dmnFile, M marshaller) throws Exception {
        File baseInputDir = new File("target/test-classes/");
        File baseOutputDir = new File("target/");

        // Validate input XML
        File inputDMNFile = new File(baseInputDir, subDir + dmnFile);
        validateXSDSchema(inputDMNFile);

        // Read definitions
        FileInputStream fis = new FileInputStream(inputDMNFile);
        D definitions = readModel(marshaller, fis);

        // Write definitions
        File outputDMNFile = new File(baseOutputDir, subDir + dmnFile);
        outputDMNFile.getParentFile().mkdirs();
        writeModel(marshaller, definitions, outputDMNFile);

        // Validate output XML:
        validateXSDSchema(outputDMNFile);

        // Compare input and output
        compareFile(inputDMNFile, outputDMNFile);
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
