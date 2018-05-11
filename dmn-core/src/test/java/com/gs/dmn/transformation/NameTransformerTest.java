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
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNNamespacePrefixMapper;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.DMNWriter;
import com.gs.dmn.tck.TestCasesReader;
import org.omg.dmn.tck.marshaller._20160719.TestCases;
import org.omg.spec.dmn._20180521.model.TDefinitions;

import java.io.File;

public abstract class NameTransformerTest extends AbstractFileTransformerTest {
    protected static final ClassLoader CLASS_LOADER = NameTransformerTest.class.getClassLoader();

    protected final DMNReader dmnReader = new DMNReader(LOGGER, false);
    protected final DMNWriter dmnWriter = new DMNWriter(LOGGER);
    protected final TestCasesReader testReader = new TestCasesReader(LOGGER);

    protected void doTest(String dmnFileName, Pair<String, String> dmnNamespacePrefixMapping, String testsFileName, Pair<String, String> testsNamespacePrefixMapping) throws Exception {
        DMNTransformer<TestCases> transformer = getTransformer();
        String path = getInputPath();

        // Transform DMN
        File inputDmnFile = new File(CLASS_LOADER.getResource(path + dmnFileName).getFile());
        DMNModelRepository repository = dmnReader.read(inputDmnFile);
        TDefinitions actualDefinitions = transformer.transform(repository).getDefinitions();

        // Transform Tests
        File inputTestsFile = new File(CLASS_LOADER.getResource(path + testsFileName).getFile());
        TestCases testCases = testReader.read(inputTestsFile);
        TestCases actualTestCases = transformer.transform(repository, testCases).getRight();

        // Check output
        File targetFolder = new File(getTargetPath());
        targetFolder.mkdirs();
        DMNNamespacePrefixMapper dmnNamespacePrefixMapper = new DMNNamespacePrefixMapper(dmnNamespacePrefixMapping.getLeft(), dmnNamespacePrefixMapping.getRight());
        check(actualDefinitions, dmnFileName, dmnNamespacePrefixMapper);
        DMNNamespacePrefixMapper testsNamespacePrefixMapper = new DMNNamespacePrefixMapper(testsNamespacePrefixMapping.getLeft(), testsNamespacePrefixMapping.getRight());
        check(actualTestCases, testsFileName, testsNamespacePrefixMapper);
    }

    private void check(TDefinitions actualDefinitions, String fileName, DMNNamespacePrefixMapper dmnNamespacePrefixMapper) throws Exception {
        File actualDMNFile = new File(getTargetPath() + fileName);
        dmnWriter.write(actualDefinitions, actualDMNFile, dmnNamespacePrefixMapper);
        File expectedDMNFile = new File(CLASS_LOADER.getResource(getExpectedPath() + fileName).getFile());

        compareFile(expectedDMNFile, actualDMNFile);
    }

    private void check(TestCases actualTestCases, String fileName, DMNNamespacePrefixMapper testsNamespacePrefixMapper) throws Exception {
        File actualTestsFile = new File(getTargetPath() + fileName);
        testReader.write(actualTestCases, actualTestsFile, testsNamespacePrefixMapper);
        File expectedTestLabFile = new File(CLASS_LOADER.getResource(getExpectedPath() + fileName).getFile());

        compareFile(expectedTestLabFile, actualTestsFile);
    }

    protected abstract DMNTransformer<TestCases> getTransformer();
    protected abstract String getInputPath();
    protected abstract String getTargetPath();
    protected abstract String getExpectedPath();
}
