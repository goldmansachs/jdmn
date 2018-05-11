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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.NopBuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.serialization.DMNNamespacePrefixMapper;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.DMNWriter;
import com.gs.dmn.tck.TestCasesReader;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ToSimpleNameTransformer extends NameTransformer {
    private int counter = 0;
    private final NameMappings namesMapping = new NameMappings();

    public ToSimpleNameTransformer() {
        super(new Slf4jBuildLogger(LOGGER));
    }

    public ToSimpleNameTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public String transformName(String oldName) {
        String newName = namesMapping.get(oldName);
        if (newName == null) {
            newName = toSimpleName(oldName);
            // Check for duplicates
            boolean isDuplicate = false;
            for(String key: namesMapping.keys()) {
                if (!key.equals(oldName) && newName.equals(namesMapping.get(key))) {
                    isDuplicate = true;
                    break;
                }
            }
            if (isDuplicate) {
                newName = newName + "_" + ++counter;
            }
            namesMapping.put(oldName, newName);
        }

        return newName;
    }

    private String toSimpleName(String name) {
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

    public static void main(String[] args) {
        NopBuildLogger logger = new NopBuildLogger();

        File inputFolder = new File("H:/Projects/EP/dmn/dmn-core/src/test/resources/tck/cl2/input/");
        File outputFolder = new File("H:/Projects/EP/dmn/dmn-tck-integration-tests/src/main/resources/tck/cl2/");

        for(File child: inputFolder.listFiles()) {
            if (child.getName().endsWith(".dmn")) {
                ToSimpleNameTransformer simpleNameTransformer = new ToSimpleNameTransformer(new NopBuildLogger());

                // Clean DMN
                String dmnFileName = child.getName();
                File inputFile = new File(inputFolder, dmnFileName);
                File outputFile = new File(outputFolder, dmnFileName);
                DMNModelRepository repository = transformDefinitions(simpleNameTransformer, inputFile, outputFile, logger);

                // Clean Test
                String testFileName = dmnFileName.replace(".dmn", "-test-01" + TestCasesReader.TEST_FILE_EXTENSION);
                File inputTestFile = new File(inputFolder, testFileName);
                if (inputTestFile.exists()) {
                    File outputTestFile = new File(outputFolder, testFileName);
                    transformTestCases(simpleNameTransformer, repository, inputTestFile, outputTestFile, logger);
                }
            }
        }
    }

    private static DMNModelRepository transformDefinitions(ToSimpleNameTransformer transformer, File inputFile, File outputFile, BuildLogger logger) {
        // Read
        DMNReader reader = new DMNReader(logger, false);
        DMNModelRepository repository = reader.read(inputFile);

        // Transform
        transformer.transform(repository);

        // Write
        DMNWriter writer = new DMNWriter(logger);
        writer.write(repository, outputFile, new DMNNamespacePrefixMapper());

        return repository;
    }

    private static void transformTestCases(ToSimpleNameTransformer transformer, DMNModelRepository repository, File inputFile, File outputFile, BuildLogger logger) {
        // Read
        TestCasesReader reader = new TestCasesReader(logger);

        TestCases testCases = reader.read(inputFile);

        // Clean
        transformer.transform(repository, testCases);

        // Write
        reader.write(testCases, outputFile, new DMNNamespacePrefixMapper());
    }

}

class NameMappings {
    private final Map<String, String> mappings = new LinkedHashMap<>();
    private List<String> orderedKeys;

    public String get(String name) {
        return mappings.get(name);
    }

    public void put(String key, String value) {
        this.mappings.put(key, value);
    }

    public List<String> keys() {
        return new ArrayList<>(mappings.keySet());
    }

    public List<String> values() {
        return new ArrayList<>(mappings.values());
    }
}