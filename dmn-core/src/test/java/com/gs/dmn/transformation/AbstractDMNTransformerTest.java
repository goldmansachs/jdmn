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

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.DefaultDMNValidator;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractDMNTransformerTest extends AbstractTransformerTest {
    protected void doFolderTest() throws Exception {
        String inputPath = getInputPath();
        File folder = path(inputPath).toFile();
        if (folder.listFiles() != null) {
            for(File file: folder.listFiles()) {
                if (file.isFile() && file.getName().endsWith(DMNConstants.DMN_FILE_EXTENSION)) {
                    doSingleModelTest(diagramName(file));
                }
            }
        }
    }

    protected void doSingleModelTest(String dmnFileName, Pair<String, String>... extraInputParameters) throws Exception {
        String path = getInputPath();
        String inputFilePath = path + "/" + dmnFileName + DMNConstants.DMN_FILE_EXTENSION;
        String expectedOutputPath = getExpectedPath() + "/" + friendlyFolderName(dmnFileName.toLowerCase());
        URI resource = resource(inputFilePath);
        doTest(resource.getPath(), expectedOutputPath, extraInputParameters);
    }

    protected void doMultipleModelsTest(String dmnFolderName, Pair<String, String>... extraInputParameters) throws Exception {
        String path = getInputPath();
        String inputFilePath = path + "/" + dmnFolderName;
        String expectedOutputPath = getExpectedPath() + "/" + friendlyFolderName(dmnFolderName.toLowerCase());
        URI resource = resource(inputFilePath);
        doTest(resource.getPath(), expectedOutputPath, extraInputParameters);
    }

    protected void doTest(String inputFilePath, String expectedOutputPath, Pair<String, String>... extraInputParameters) throws Exception {
        File outputFolder = new File("target/" + expectedOutputPath);
        outputFolder.mkdirs();

        Path inputPath = new File(inputFilePath).toPath();
        Map<String, String> inputParameters = makeInputParameters();
        for (Pair<String, String> pair: extraInputParameters) {
            inputParameters.put(pair.getLeft(), pair.getRight());
        }
        FileTransformer transformer = makeTransformer(inputParameters, LOGGER);
        transformer.transform(inputPath, outputFolder.toPath());

        File expectedOutputFolder = new File(resource(expectedOutputPath));
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
        return makeDialectDefinition().createDMNToNativeTransformer(makeDMNValidator(logger), makeDMNTransformer(logger), makeTemplateProvider(), makeLazyEvaluationDetector(inputParameters, logger), makeTypeDeserializationConfigurer(logger), inputParameters, logger);
    }

    @Override
    protected Map<String, String> makeInputParameters() {
        Map<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("dmnVersion", "1.1");
        inputParams.put("modelVersion", "2.0");
        inputParams.put("platformVersion", "1.0");
        return inputParams;
    }

    private String diagramName(File file) {
        String name = file.getName();
        int i = name.indexOf(DMNConstants.DMN_FILE_EXTENSION);
        return i == -1 ? name : name.substring(0, i);
    }

    protected abstract String getInputPath();

    protected abstract String getExpectedPath();
}
