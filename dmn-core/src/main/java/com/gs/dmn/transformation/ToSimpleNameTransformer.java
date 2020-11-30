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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.NopBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.*;
import com.gs.dmn.tck.TestCasesReader;
import org.apache.commons.lang3.StringUtils;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.spec.dmn._20191111.model.TDefinitions;

import java.io.File;
import java.util.*;

public class ToSimpleNameTransformer extends NameTransformer {
    private int counter = 0;
    private final NameMappings namesMapping = new NameMappings();

    public ToSimpleNameTransformer() {
        super();
    }

    public ToSimpleNameTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public String transformName(String oldName) {
        if (StringUtils.isEmpty(oldName)) {
            return oldName;
        } else if (isSimpleName(oldName)) {
            return oldName;
        } else {
            String newName = namesMapping.get(oldName);
            if (newName == null) {
                newName = toSimpleName(oldName);
                if (!newName.equals(oldName)) {
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
            }

            return newName;
        }
    }

    private String toSimpleName(String name) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        StringBuilder result = new StringBuilder();
        boolean skippedPrevious = false;
        for (int ch: name.codePoints().toArray()) {
            if (Character.isJavaIdentifierPart(ch)) {
                if (skippedPrevious) {
                    ch = Character.toUpperCase(ch);
                }
                result.append((char)ch);
                skippedPrevious = false;
            } else {
                skippedPrevious = true;
            }
        }
        String newName = result.toString();
        return newName.isEmpty() ? "_" : newName;
    }

    public static void main(String[] args) {
        NopBuildLogger logger = new NopBuildLogger();

        File inputFolder = new File("H:/Projects/EP/dmn/dmn-core/src/test/resources/tck/cl2/input/");
        File outputFolder = new File("H:/Projects/EP/dmn/dmn-tck-integration-tests/src/main/resources/tck/cl2/");

        for(File child: inputFolder.listFiles()) {
            if (DMNReader.isDMNFile(child)) {
                ToSimpleNameTransformer simpleNameTransformer = new ToSimpleNameTransformer(new NopBuildLogger());

                // Clean DMN
                String dmnFileName = child.getName();
                File inputFile = new File(inputFolder, dmnFileName);
                File outputFile = new File(outputFolder, dmnFileName);
                DMNModelRepository repository = transformDefinitions(simpleNameTransformer, inputFile, outputFile, logger);

                // Clean Test
                String testFileName = dmnFileName.replace(DMNConstants.DMN_FILE_EXTENSION, "-test-01" + TestCasesReader.DEFAULT_TEST_CASE_FILE_EXTENSION);
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
        Pair<TDefinitions, PrefixNamespaceMappings> result = reader.read(inputFile);
        DMNModelRepository repository = new DMNModelRepository(result);

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
        transformer.transform(repository, Arrays.asList(testCases));

        // Write
        reader.write(testCases, outputFile, new TCKNamespacePrefixMapper());
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