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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TInputData;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.SignavioTestConstants;
import com.gs.dmn.signavio.testlab.InputParameterDefinition;
import com.gs.dmn.signavio.testlab.TestLab;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractMergeInputDataTransformerTest extends AbstractSignavioFileTransformerTest {
    protected final AbstractMergeInputDataTransformer transformer = getTransformer();

    protected abstract AbstractMergeInputDataTransformer getTransformer();

    protected void doTransform(String dmnFileName) throws Exception {
        doTransform(dmnFileName, null);
    }

    protected void doTransform(String dmnFileName, String testLabFileName) throws Exception {
        String path = "dmn/input/1.1/";

        // Transform DMN
        File dmnFile = new File(resource(path + dmnFileName));
        TDefinitions definitions = this.dmnSerializer.readModel(dmnFile);
        DMNModelRepository repository = new SignavioDMNModelRepository(definitions, SignavioTestConstants.SIG_EXT_NAMESPACE);
        Map<String, Object> config = new LinkedHashMap<>();
        config.put("forceMerge", "false");
        this.transformer.configure(config);
        DMNModelRepository actualRepository = this.transformer.transform(repository);

        // Transform TestLab
        List<TestLab> actualTestLabList = null;
        if (testLabFileName != null) {
            File testLabFile = new File(resource(path + testLabFileName));
            List<TestLab> testLabList = new ArrayList<>();
            if (testLabFile.isFile()) {
                TestLab testLab = this.testReader.read(testLabFile);
                testLabList.add(testLab);
            } else {
                for (File child: testLabFile.listFiles()) {
                    TestLab testLab = this.testReader.read(child);
                    testLabList.add(testLab);
                }

            }
            actualTestLabList = this.transformer.transform(actualRepository, testLabList).getRight();
        }

        // Check output
        TDefinitions rootDefinitions = actualRepository.getRootDefinitions();
        check(dmnFileName, testLabFileName, rootDefinitions, actualTestLabList);
    }

    private void check(String dmnFileName, String testLabFileName, TDefinitions actualDefinitions, List<TestLab> actualTestLabList) throws Exception {
        // Check definitions for InputData with same key
        SignavioDMNModelRepository signavioDMNModelRepository = new SignavioDMNModelRepository(actualDefinitions, SignavioTestConstants.SIG_EXT_NAMESPACE);
        List<TInputData> inputDataList = signavioDMNModelRepository.findInputDatas(actualDefinitions);
        for (TInputData inputData1: inputDataList) {
            TInputData duplicate = null;
            for (TInputData inputData2: inputDataList) {
                if (isSameClass(inputData1, inputData2, signavioDMNModelRepository)) {
                    duplicate = inputData2;
                    break;
                }
            }
            if (duplicate != null) {
                fail("Duplicate '%s' and '%s'".formatted(inputData1.getName(), duplicate.getName()));
            }
        }
        // Check definitions
        checkDefinitions(actualDefinitions, dmnFileName);

        if (testLabFileName != null) {
            for (TestLab actualTestLab: actualTestLabList) {
                // Check TestLab for missing parameters
                for (TInputData inputData: inputDataList) {
                    boolean found = false;
                    for (InputParameterDefinition ipd: actualTestLab.getInputParameterDefinitions()) {
                        if (inputData.getLabel().equals(ipd.getRequirementName())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        fail("Missing parameter " + inputData.getLabel());
                    }
                }
                // Check TestLab
                checkTestLab(actualTestLab, testLabFileName);
            }
        }
    }

    protected void checkDefinitions(TDefinitions actualDefinitions, String fileName) throws Exception {
        File actualDMNFile = new File("target/" + fileName);
        this.dmnSerializer.writeModel(actualDefinitions, actualDMNFile);

        String path = "dmn/expected/";
        File expectedDMNFile = new File(resource(path + fileName));

        compareFile(expectedDMNFile, actualDMNFile);
    }

    private void checkTestLab(TestLab actualTestLab, String fileName) throws Exception {
        File actualTestLabFile = new File("target/" + fileName);
        this.testReader.write(actualTestLab, actualTestLabFile);

        String path = "dmn/expected/";
        File expectedTestLabFile = new File(resource(path + fileName));

        compareFile(expectedTestLabFile, actualTestLabFile);
    }

    protected boolean isSameClass(TInputData inputData1, TInputData inputData2, SignavioDMNModelRepository signavioDMNModelRepository) {
        String key1 = this.transformer.equivalenceKey(inputData1, signavioDMNModelRepository);
        String key2 = this.transformer.equivalenceKey(inputData2, signavioDMNModelRepository);
        return key1.equals(key2) && inputData1 != inputData2;
    }

    @Test
    public void testReplace() {
        doReplace("employee222", "employee", "employee2227.field", "employee2227.field");
    }

    private void doReplace(String oldName, String newName, String oldText, String newText) {
        assertEquals(newText, getTransformer().replace(oldName, newName, oldText));
    }
}