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
import org.junit.Ignore;
import org.junit.Test;
import org.omg.spec.dmn._20180521.model.TDefinitions;
import org.omg.spec.dmn._20180521.model.TInputData;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@Ignore
public class MergeInputDataTransformerTest extends AbstractFileTransformerTest {
    private final MergeInputDataTransformer transformer = new MergeInputDataTransformer(LOGGER);
    private final DMNReader dmnReader = new DMNReader(LOGGER, false);
    private DMNWriter dmnWriter = new DMNWriter(LOGGER);
    private final TestLabReader testReader = new TestLabReader();
    private static final ClassLoader CLASS_LOADER = MergeInputDataTransformerTest.class.getClassLoader();

    @Test
    public void testDMNTransform() throws Exception {
        String path = "dmn2java/exported/complex/input/";

        // Transform DMN
        File dmnFile = new File(CLASS_LOADER.getResource(path + "m2-pttr-38.dmn").getFile());
        Pair<TDefinitions, PrefixNamespaceMappings> pair = dmnReader.read(dmnFile);
        DMNModelRepository repository = new SignavioDMNModelRepository(pair);
        DMNModelRepository actualRepository = transformer.transform(repository);

        // Transform TestLab
        File testLabFile = new File(CLASS_LOADER.getResource(path + "m2-pttr-38.json").getFile());
        TestLab testLab = testReader.read(testLabFile);
        TestLab actualTestLab = transformer.transform(actualRepository, testLab).getRight();

        // Check output
        check(actualRepository.getRootDefinitions(), actualTestLab);
    }

    private void check(TDefinitions actualDefinitions, TestLab actualTestLab) throws Exception {
        // Check for duplicate InputData
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
        // Check for missing parameters
        for(TInputData inputData: inputDataList) {
            boolean found = false;
            for(InputParameterDefinition ipd: actualTestLab.getInputParameterDefinitions()) {
                if (inputData.getLabel().equals(ipd.getRequirementName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                fail("Missing parameter " + inputData.getLabel());
            }
        }


        checkDefinitions(actualDefinitions, "m2-pttr-38.dmn");
        checkTestLab(actualTestLab, "m2-pttr-38.json");
    }

    private void checkDefinitions(TDefinitions actualDefinitions, String fileName) throws Exception {
        assertEquals(146, new SignavioDMNModelRepository(actualDefinitions, new PrefixNamespaceMappings()).inputDatas().size());

        File actualDMNFile = new File("target/" + fileName);
        dmnWriter.write(actualDefinitions, actualDMNFile, new DMNNamespacePrefixMapper(actualDefinitions.getNamespace(), "sig"));

        String path = "dmn2java/exported/complex/expected/";
        File expectedDMNFile = new File(CLASS_LOADER.getResource(path + fileName).getFile());

        compareFile(expectedDMNFile, actualDMNFile);
    }

    private void checkTestLab(TestLab actualTestLab, String fileName) throws Exception {
        assertEquals(146, actualTestLab.getInputParameterDefinitions().size());
        assertEquals(146, actualTestLab.getTestCases().get(0).getInputValues().size());

        File actualTestLabFile = new File("target/" + fileName);
        testReader.write(actualTestLab, actualTestLabFile);

        String path = "dmn2java/exported/complex/expected/";
        File expectedTestLabFile = new File(CLASS_LOADER.getResource(path + fileName).getFile());

        compareFile(expectedTestLabFile, actualTestLabFile);
    }

}