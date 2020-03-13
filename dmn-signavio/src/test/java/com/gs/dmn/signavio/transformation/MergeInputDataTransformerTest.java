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
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNNamespacePrefixMapper;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.DMNWriter;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.testlab.InputParameterDefinition;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.signavio.testlab.TestLabReader;
import com.gs.dmn.transformation.AbstractFileTransformerTest;
import org.junit.Test;
import org.omg.spec.dmn._20180521.model.TDefinitions;
import org.omg.spec.dmn._20180521.model.TInputData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MergeInputDataTransformerTest extends AbstractFileTransformerTest {
    private static final ClassLoader CLASS_LOADER = MergeInputDataTransformerTest.class.getClassLoader();

    private final MergeInputDataTransformer transformer = new MergeInputDataTransformer(LOGGER);
    private final DMNReader dmnReader = new DMNReader(LOGGER, false);
    private DMNWriter dmnWriter = new DMNWriter(LOGGER);
    private final TestLabReader testReader = new TestLabReader();
    private String dmnFile = "simple-decision-with-bkm-clone.dmn";
    private String testLabFile = "simple-decision-with-bkm-clone.json";

    @Test
    public void testTransform() throws Exception {
        String path = "dmn/input/";

        // Transform DMN
        File dmnFile = new File(CLASS_LOADER.getResource(path + this.dmnFile).getFile());
        Pair<TDefinitions, PrefixNamespaceMappings> pair = dmnReader.read(dmnFile);
        DMNModelRepository repository = new SignavioDMNModelRepository(pair);
        DMNModelRepository actualRepository = transformer.transform(repository);

        // Transform TestLab
        File testLabFile = new File(CLASS_LOADER.getResource(path + this.testLabFile).getFile());
        List<TestLab> testLabList = new ArrayList<>();
        if (testLabFile.isFile()) {
            TestLab testLab = testReader.read(testLabFile);
            testLabList.add(testLab);
        } else {
            for (File child: testLabFile.listFiles()) {
                TestLab testLab = testReader.read(child);
                testLabList.add(testLab);
            }

        }
        List<TestLab> actualTestLabList = transformer.transform(actualRepository, testLabList).getRight();

        // Check output
        TDefinitions definitions = actualRepository.getRootDefinitions();
        check(definitions, actualTestLabList);
    }

    private void check(TDefinitions actualDefinitions, List<TestLab> actualTestLabList) throws Exception {
        // Check definitions for duplicate InputData
        List<TInputData> inputDataList = new SignavioDMNModelRepository(actualDefinitions, new PrefixNamespaceMappings()).inputDatas();
        for(TInputData inputData1: inputDataList) {
            TInputData duplicate = null;
            for(TInputData inputData2: inputDataList) {
                if (inputData1.getLabel().equals(inputData2.getLabel()) && inputData1 != inputData2) {
                    duplicate = inputData2;
                    break;
                }
            }
            if (duplicate != null) {
                fail(String.format("Duplicate '%s' and '%s'", inputData1.getName(), duplicate.getName()));
            }
        }
        // Check definitions
        checkDefinitions(actualDefinitions, dmnFile);

        for (TestLab actualTestLab: actualTestLabList) {
            // Check TestLab for missing parameters
            for (TInputData inputData : inputDataList) {
                boolean found = false;
                for (InputParameterDefinition ipd : actualTestLab.getInputParameterDefinitions()) {
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
            checkTestLab(actualTestLab, testLabFile);
        }
    }

    private void checkDefinitions(TDefinitions actualDefinitions, String fileName) throws Exception {
        assertEquals(2, new SignavioDMNModelRepository(actualDefinitions, new PrefixNamespaceMappings()).inputDatas().size());

        File actualDMNFile = new File("target/" + fileName);
        dmnWriter.write(actualDefinitions, actualDMNFile, new DMNNamespacePrefixMapper(actualDefinitions.getNamespace(), "sig"));

        String path = "dmn/expected/";
        File expectedDMNFile = new File(CLASS_LOADER.getResource(path + fileName).getFile());

        compareFile(expectedDMNFile, actualDMNFile);
    }

    private void checkTestLab(TestLab actualTestLab, String fileName) throws Exception {
        assertEquals(2, actualTestLab.getInputParameterDefinitions().size());
        assertEquals(2, actualTestLab.getTestCases().get(0).getInputValues().size());

        File actualTestLabFile = new File("target/" + fileName);
        testReader.write(actualTestLab, actualTestLabFile);

        String path = "dmn/expected/";
        File expectedTestLabFile = new File(CLASS_LOADER.getResource(path + fileName).getFile());

        compareFile(expectedTestLabFile, actualTestLabFile);
    }

}