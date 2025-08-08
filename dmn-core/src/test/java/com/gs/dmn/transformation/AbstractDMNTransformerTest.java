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
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.NopDMNValidator;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractDMNTransformerTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractTransformerTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> {
    protected void doFolderTest() throws Exception {
        String inputPath = getInputPath();
        File folder = path(inputPath).toFile();
        if (folder.listFiles() != null) {
            for(File file: Objects.requireNonNull(folder.listFiles())) {
                if (file.isFile() && file.getName().endsWith(this.inputParameters.getDmnFileExtension())) {
                    doSingleModelTest(diagramName(file));
                }
            }
        }
    }

    @SafeVarargs
    protected final void doSingleModelTest(String dmnFileName, Pair<String, String>... extraInputParameters) throws Exception {
        String inputFilePath = getInputPath() + "/" + dmnFileName + this.inputParameters.getDmnFileExtension();
        String expectedOutputPath = getExpectedPath() + "/" + friendlyFolderName(dmnFileName.toLowerCase());
        URI resource = resource(inputFilePath);
        doTest(resource.getPath(), expectedOutputPath, extraInputParameters);
    }

    @SafeVarargs
    protected final void doFolderTest(String dmnVersion, String dmnFolderName, Pair<String, String>... extraInputParameters) throws Exception {
        String inputFilePath = completePath(getInputPath(), dmnVersion, dmnFolderName) + "/";
        String expectedOutputPath = completePath(getExpectedPath(), dmnVersion, dmnFolderName) + "/";
        URI resource = resource(inputFilePath);
        doTest(resource.getPath(), expectedOutputPath, extraInputParameters);
    }

    protected void doTest(String inputFilePath, String expectedOutputPath, Pair<String, String>... extraInputParameters) throws Exception {
        if (inputFilePath == null) {
            throw new DMNRuntimeException("Input path cannot be null");
        }

        File outputFolder = new File("target/" + expectedOutputPath);
        Files.createDirectories(outputFolder.toPath());

        Path inputPath = new File(inputFilePath).toPath();
        Map<String, String> inputParameters = makeInputParametersMap(extraInputParameters);
        FileTransformer transformer = makeTransformer(makeInputParameters(inputParameters), LOGGER);
        transformer.transform(inputPath, outputFolder.toPath());

        File expectedOutputFolder = new File(resource(expectedOutputPath));
        compareFile(expectedOutputFolder, outputFolder);
    }

    protected InputParameters makeInputParameters(Map<String, String> inputParameters) {
        return new InputParameters(inputParameters);
    }

    @Override
    protected DMNValidator makeDMNValidator(BuildLogger logger) {
        return new NopDMNValidator();
    }

    @Override
    protected DMNTransformer<TEST> makeDMNTransformer(BuildLogger logger) {
        return new NopDMNTransformer<>();
    }

    private Map<String, String> makeInputParametersMap(Pair<String, String>[] extraInputParameters) {
        Map<String, String> inputParameters = makeInputParametersMap();
        for (Pair<String, String> pair: extraInputParameters) {
            inputParameters.put(pair.getLeft(), pair.getRight());
        }
        return inputParameters;
    }

    protected FileTransformer makeTransformer(InputParameters inputParameters, BuildLogger logger) {
        return makeDialectDefinition().createDMNToNativeTransformer(makeDMNValidator(logger), makeDMNTransformer(logger), makeTemplateProvider(), makeLazyEvaluationDetector(inputParameters, logger), makeTypeDeserializationConfigurer(logger), inputParameters, logger);
    }

    private String diagramName(File file) {
        String name = file.getName();
        int i = name.indexOf(this.inputParameters.getDmnFileExtension());
        return i == -1 ? name : name.substring(0, i);
    }

    protected abstract String getInputPath();

    protected abstract String getExpectedPath();
}
