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
package com.gs.dmn.tck;

import com.gs.dmn.runtime.Pair;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CL3TestCasesToJavaJUnitTransformerTest extends AbstractTCKTestCasesToJavaJUnitTransformerTest {
    @Override
    protected String getDMNInputPath() {
        return "tck/%s/cl3/%s/translator";
    }

    @Override
    protected String getTestCasesInputPath() {
        return getDMNInputPath() + "/pure";
    }

    @Override
    protected String getExpectedPath() {
        return "tck/%s/cl3/%s/translator/expected/java/test";
    }

    @Test
    public void testCL3() throws Exception {
        // DMN 1.1
        doSingleModelTest("1.1","0004-lending", "0004-lending-test-01");
        doSingleModelTest("1.1","0005-literal-invocation", "0005-literal-invocation-test-01");
        doSingleModelTest("1.1","0006-join", "0006-join-test-01");
        doSingleModelTest("1.1","0013-sort", "0013-sort-test-01");
        doSingleModelTest("1.1","0014-loan-comparison", "0014-loan-comparison-test-01");
        doSingleModelTest("1.1","0016-some-every", "0016-some-every-test-01");
        doSingleModelTest("1.1","0017-tableTests", "0017-tableTests-test-01");
        doSingleModelTest("1.1","0020-vacation-days", "0020-vacation-days-test-01");
        doSingleModelTest("1.1","0021-singleton-list", "0021-singleton-list-test-01");
        doSingleModelTest("1.1","0030-user-defined-functions", "0030-user-defined-functions-test-01");
        doSingleModelTest("1.1","0031-user-defined-functions", "0031-user-defined-functions-test-01");

        // DMN 1.2
        doSingleModelTest("1.2","0076-feel-external-java", "0076-feel-external-java-test-01");

        // DMN 1.3
        doSingleModelTest("1.3", "0085-decision-services", "0085-decision-services-test-01", new Pair<>("caching", "true"));
        doSingleModelTest("1.3", "0092-feel-lambda", "0092-feel-lambda-test-01");
        doSingleModelTest("1.3", "0083-feel-unicode", "0083-feel-unicode-test-01");

        // DMN 1.5
        doSingleModelTest("1.5","1157-implicit-conversions", "1157-implicit-conversions-test-01");

        // Extensions
        doSingleModelTest("1.1", "9001-recursive-function", "9001-recursive-function-test-01");
    }

    @Test
    public void testCL3Singleton() throws Exception {
        doSingleModelTest("1.3", "0004-lending", "0004-lending-test-01", new Pair<>("singletonDecision", "true"));
    }

    @Test
    public void testCollectTCKFiles() {
        String dmnVersion = "1.5";
        String dmnFolderName = "0068-feel-equality";
        String inputFilePath = completePath(getDMNInputPath(), dmnVersion, dmnFolderName) + "/";
        URI resource = resource(inputFilePath);

        // Do not collect recursively the TCK files
        Path tckTestFolderPath = new File(resource.getPath()).toPath();
        TCKTestCasesToJavaJUnitTransformer<?, ?, ?, ?, ?> transformer = (TCKTestCasesToJavaJUnitTransformer<?, ?, ?, ?, ?>) makeTransformer(tckTestFolderPath, makeInputParameters(makeInputParametersMap()), LOGGER);
        List<File> files = new ArrayList<>();
        transformer.collectFiles(tckTestFolderPath, files);
        assertEquals(1, files.size());
    }
}
