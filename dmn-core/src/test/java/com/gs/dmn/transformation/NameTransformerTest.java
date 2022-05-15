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
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.serialization.diff.XMLDifferenceEvaluator;
import com.gs.dmn.serialization.xstream.XMLDMNSerializer;
import com.gs.dmn.tck.TCKSerializer;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.serialization.xstream.XMLTCKSerializer;
import org.xmlunit.diff.DifferenceEvaluator;
import org.xmlunit.diff.DifferenceEvaluators;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class NameTransformerTest extends AbstractFileTransformerTest {
    protected static final ClassLoader CLASS_LOADER = NameTransformerTest.class.getClassLoader();

    protected final DMNSerializer dmnSerializer = new XMLDMNSerializer(LOGGER, true);
    protected final TCKSerializer tckSerializer = new XMLTCKSerializer(LOGGER, true);

    protected void doTest(String dmmVersion, List<String> dmnFileNames, String testsFileName, Map<String, Pair<String, String>> namespacePrefixMapping) throws Exception {
        DMNTransformer<TestCases> transformer = getTransformer();
        String path = getInputPath() + dmmVersion + "/";

        // Read DMN files
        List<TDefinitions> definitionsList = readModels(path, dmnFileNames);
        DMNModelRepository repository = new DMNModelRepository(definitionsList);

        // Transform Models and Tests
        File inputTestsFile = new File(CLASS_LOADER.getResource(path + testsFileName).getFile());
        List<TestCases> testCasesList = new ArrayList<>();
        if (inputTestsFile.isFile()) {
            TestCases testCases = this.tckSerializer.read(inputTestsFile);
            testCasesList.add(testCases);
        } else {
            throw new DMNRuntimeException("Only single files are supported");
        }
        List<TestCases> actualTestCasesList = transformer.transform(repository, testCasesList).getRight();

        // Check output
        for (TestCases actualTestCases: actualTestCasesList) {
            File targetFolder = new File(getTargetPath());
            targetFolder.mkdirs();
            for (int i = 0; i < dmnFileNames.size(); i++) {
                String dmnFileName = dmnFileNames.get(i);
                TDefinitions definitions = definitionsList.get(i);
                check(definitions, dmnFileName);
            }
            Pair<String, String> testsNamespacePrefixMapping = namespacePrefixMapping.get(testsFileName);
            check(actualTestCases, testsFileName, testsNamespacePrefixMapping);
        }
    }

    private List<TDefinitions> readModels(String path, List<String> fileNames) {
        List<TDefinitions> definitionsList = new ArrayList<>();
        for (String fileName: fileNames) {
            File dmnFile = new File(resource(path + fileName));
            TDefinitions definitions = this.dmnSerializer.readModel(dmnFile);
            definitionsList.add(definitions);
        }
        return definitionsList;
    }

    private void check(TDefinitions actualDefinitions, String fileName) throws Exception {
        File actualDMNFile = new File(getTargetPath() + fileName);
        this.dmnSerializer.writeModel(actualDefinitions, actualDMNFile);
        File expectedDMNFile = new File(CLASS_LOADER.getResource(getExpectedPath() + fileName).getFile());

        compareFile(expectedDMNFile, actualDMNFile);
    }

    private void check(TestCases actualTestCases, String fileName, Pair<String, String> namespacePrefixMapping) throws Exception {
        File actualTestsFile = new File(getTargetPath() + fileName);
        this.tckSerializer.write(actualTestCases, actualTestsFile);
        File expectedTestLabFile = new File(CLASS_LOADER.getResource(getExpectedPath() + fileName).getFile());

        compareFile(expectedTestLabFile, actualTestsFile);
    }

    @Override
    protected DifferenceEvaluator makeTCKDifferenceEvaluator() {
        return DifferenceEvaluators.chain(DifferenceEvaluators.Default, XMLDifferenceEvaluator.tck1DiffEvaluator());
    }

    protected abstract DMNTransformer<TestCases> getTransformer();
    protected abstract String getInputPath();
    protected abstract String getTargetPath();
    protected abstract String getExpectedPath();
}
