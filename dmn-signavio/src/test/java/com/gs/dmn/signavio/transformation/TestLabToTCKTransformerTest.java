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

import com.gs.dmn.signavio.SignavioTestConstants;
import com.gs.dmn.transformation.InputParameters;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

class TestLabToTCKTransformerTest extends AbstractSignavioFileTransformerTest {
    private final TestLabToTCKTransformer transformer = new TestLabToTCKTransformer(new InputParameters(makeInputParametersMap()));

    @Test
    public void testTransformFolder() throws Exception {
        String signavioTestCasesPath = "dmn/singleton/";

        doTestFolder(signavioTestCasesPath);
    }

    @Test
    public void testTransformFile() throws Exception {
        String signavioTestCasesPath = "dmn/complex/";

        doTest(signavioTestCasesPath, "Example credit decision");
        doTest(signavioTestCasesPath, "BKMfromBKM");
        doTest(signavioTestCasesPath, "BKMimportedfromMID");
        doTest(signavioTestCasesPath, "BKMwithLiteralExpression");
        doTest(signavioTestCasesPath, "ChildLinked");
        doTest(signavioTestCasesPath, "CompareLists");
        doTest(signavioTestCasesPath, "Complex MID");
        doTest(signavioTestCasesPath, "DotProduct");
        doTest(signavioTestCasesPath, "Null Safe Tests");
    }

    private void doTestFolder(String path) throws Exception {
        File testFolder = new File(signavioResource(path));
        File targetFolder = new File("target/tck/" + path);
        Files.createDirectories(targetFolder.toPath());
        transformer.transformFolder(testFolder, targetFolder);

        File expectedFolder = new File(this.resource("tck/expected/" + path));
        Files.createDirectories(expectedFolder.toPath());
        compareFile(expectedFolder, targetFolder);
    }

    private void doTest(String path, String modelName) throws Exception {
        // Transform
        File expectedFolder = new File(this.resource("tck/expected/" + path));
        Files.createDirectories(expectedFolder.toPath());
        File targetFolder = new File("target/tck/" + path);
        Files.createDirectories(targetFolder.toPath());

        File testLabFile = new File(signavioResource(path + modelName + ".json"));
        File dmnFile = new File(signavioResource(path + modelName + ".dmn"));
        transformer.transformFile(testLabFile, dmnFile, targetFolder);

        String tckFileName = modelName + ".tck";
        File expectedOutputPath = new File(expectedFolder, tckFileName);
        File actualOutputPath = new File(targetFolder, tckFileName);
        compareFile(expectedOutputPath, actualOutputPath);
    }

    @Override
    protected Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = super.makeInputParametersMap();
        inputParams.put("signavioSchemaNamespace", SignavioTestConstants.SIG_EXT_NAMESPACE);
        inputParams.put("tckFileExtension", ".tck");
        return inputParams;
    }
}