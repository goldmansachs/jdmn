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
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.DefaultDMNValidator;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractDMNToJavaTest extends AbstractTransformerTest {
    protected void doTestFolder() throws Exception {
        String inputPath = getInputPath();
        File folder = path(inputPath).toFile();
        if (folder.listFiles() != null) {
            for(File file: folder.listFiles()) {
                if (file.isFile() && file.getName().endsWith(DMNConstants.DMN_FILE_EXTENSION)) {
                    doTest(diagramName(file));
                }
            }
        }
    }

    protected void doTest(String diagramName) throws Exception {
        String path = getInputPath();
        String inputFilePath = path + "/" + diagramName + DMNConstants.DMN_FILE_EXTENSION;
        String expectedOutputPath = getExpectedPath() + "/" + friendlyFolderName(diagramName.toLowerCase());
        URI resource = resource(inputFilePath);
        doTest(resource.getPath(), expectedOutputPath);
    }

    protected void doTest(String inputFilePath, String expectedOutputPath) throws Exception {
        File outputFolder = new File("target/" + expectedOutputPath);
        outputFolder.mkdirs();
        File expectedOutputFolder = new File(resource(expectedOutputPath));

        Path inputPath = new File(inputFilePath).toPath();
        FileTransformer transformer = makeTransformer(makeInputParameters(), LOGGER);
        transformer.transform(inputPath, outputFolder.toPath());
        compareFile(expectedOutputFolder, outputFolder);
    }

    @Override
    protected DMNValidator makeDMNValidator(BuildLogger logger) {
        return new DefaultDMNValidator(logger);
    }

    @Override
    protected DMNTransformer makeDMNTransformer(BuildLogger logger) {
        return new NopDMNTransformer();
    }

    private FileTransformer makeTransformer(Map<String, String> inputParameters, BuildLogger logger) {
        return makeDialectDefinition().createDMNToJavaTransformer(makeDMNValidator(logger), makeDMNTransformer(logger), makeTemplateProvider(), makeLazyEvaluationDetector(inputParameters, logger), inputParameters, logger);
    }

    @Override
    protected Map<String, String> makeInputParameters() {
        return new LinkedHashMap<String, String>() {{
            put("dmnVersion", "1.1");
            put("modelVersion", "2.0");
            put("platformVersion", "1.0");
        }};
    }

    private String diagramName(File file) {
        String name = file.getName();
        int i = name.indexOf(DMNConstants.DMN_FILE_EXTENSION);
        return i == -1 ? name : name.substring(0, i);
    }

    protected abstract String getInputPath();

    protected abstract String getExpectedPath();
}
