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
package com.gs.dmn.transformation;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.NopBuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.serialization.DMNNamespacePrefixMapper;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.tck.TestCasesReader;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.spec.dmn._20151101.dmn.TDefinitions;
import org.omg.spec.dmn._20151101.dmn.TNamedElement;

import java.io.File;

public class ToJavaNameTransformer extends NameTransformer {
    private int counter = 0;

    public ToJavaNameTransformer() {
        super(new Slf4jBuildLogger(LOGGER));
    }

    public ToJavaNameTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public String transformName(String name) {
        if (name == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean skippedPrevious = false;
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (Character.isAlphabetic(ch) || Character.isDigit(ch) || ch == '_') {
                if (skippedPrevious) {
                    ch = Character.toUpperCase(ch);
                }
                result.append(ch);
                skippedPrevious = false;
            } else {
                skippedPrevious = true;
            }
        }
        return result.toString();
    }

    @Override
    protected void addNameMapping(TNamedElement element, NameMappings namesMapping) {
        if (element == null) {
            return;
        }
        String oldName = element.getName();
        String newValue = transformName(oldName);
        // Check for duplicates
        boolean isDuplicate = false;
        for(String key: namesMapping.keys()) {
            if (!key.equals(oldName) && newValue.equals(namesMapping.get(key))) {
                isDuplicate = true;
                break;
            }
        }
        if (isDuplicate) {
            newValue = newValue + "_" + ++counter;
        }
        namesMapping.put(oldName, newValue);
    }

    public static void main(String[] args) throws Exception {
        NopBuildLogger logger = new NopBuildLogger();

        File inputFolder = new File("H:/Projects/EP/dmn/dmn-core/src/test/resources/tck/cl2/input/");
        File outputFolder = new File("H:/Projects/EP/dmn/dmn-tck-integration-tests/src/main/resources/tck/cl2/");

        for(File child: inputFolder.listFiles()) {
            if (child.getName().endsWith(".dmn")) {
                ToJavaNameTransformer cleaner = new ToJavaNameTransformer(new NopBuildLogger());

                // Clean DMN
                String dmnFileName = child.getName();
                File inputFile = new File(inputFolder, dmnFileName);
                File outputFile = new File(outputFolder, dmnFileName);
                TDefinitions definitions = transformDefinitions(cleaner, inputFile, outputFile, logger);

                // Clean Test
                String testFileName = dmnFileName.replace(".dmn", "-test-01" + TestCasesReader.TEST_FILE_EXTENSION);
                File inputTestFile = new File(inputFolder, testFileName);
                if (inputTestFile.exists()) {
                    File outputTestFile = new File(outputFolder, testFileName);
                    transformTestCases(cleaner, definitions, inputTestFile, outputTestFile, logger);
                }
            }
        }
    }

    private static TDefinitions transformDefinitions(ToJavaNameTransformer transformer, File inputFile, File outputFile, BuildLogger logger) {
        // Read
        DMNReader reader = new DMNReader(logger, false);
        TDefinitions definitions = reader.read(inputFile);

        // Clean
        transformer.transform(definitions);

        // Write
        reader.write(definitions, outputFile, new DMNNamespacePrefixMapper());

        return definitions;
    }

    private static void transformTestCases(ToJavaNameTransformer transformer, TDefinitions definitions, File inputFile, File outputFile, BuildLogger logger) throws Exception {
        // Read
        TestCasesReader reader = new TestCasesReader(logger);

        TestCases testCases = reader.read(inputFile);

        // Clean
        transformer.transform(definitions, testCases);

        // Write
        reader.write(testCases, outputFile, new DMNNamespacePrefixMapper());
    }

}
