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

import com.gs.dmn.AbstractTest;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import org.apache.commons.io.FileUtils;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.LoggerFactory;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public abstract class AbstractFileTransformerTest extends AbstractTest {
    protected static final BuildLogger LOGGER = new Slf4jBuildLogger(LoggerFactory.getLogger(AbstractFileTransformerTest.class));

    protected void compareFile(File expectedOutputFile, File actualOutputFile) throws Exception {
        if (expectedOutputFile.isDirectory() && actualOutputFile.isDirectory()) {
            List<File> expectedChildren = Arrays.asList(expectedOutputFile.listFiles());
            List<File> actualChildren = Arrays.asList(actualOutputFile.listFiles());
            compareFileList(expectedOutputFile, expectedChildren, actualOutputFile, actualChildren);
        } else if (expectedOutputFile.isFile() && actualOutputFile.isFile()) {
            if (isJsonFile(expectedOutputFile) && isJsonFile(actualOutputFile)) {
                compareJsonFile(expectedOutputFile, actualOutputFile);
            } else if (isDmnFile(expectedOutputFile) && isDmnFile(actualOutputFile)) {
                compareXmlFile(expectedOutputFile, actualOutputFile);
            } else if (isXmlFile(expectedOutputFile) && isXmlFile(actualOutputFile)) {
                compareXmlFile(expectedOutputFile, actualOutputFile);
            } else if (expectedOutputFile.getName().equals(actualOutputFile.getName())) {
                compareTextFile(expectedOutputFile, actualOutputFile);
            } else {
                throw new DMNRuntimeException(String.format("Files with different names '%s' '%s' ", expectedOutputFile.getCanonicalPath(), actualOutputFile.getCanonicalPath()));
            }
        } else {
            throw new DMNRuntimeException(String.format("Cannot compare folder with file '%s' '%s' ", expectedOutputFile.getCanonicalPath(), actualOutputFile.getCanonicalPath()));
        }
    }

    private boolean isDmnFile(File file) {
        return file.getName().endsWith(".dmn");
    }

    private boolean isXmlFile(File file) {
        return file.getName().endsWith(".xml");
    }

    private boolean isJsonFile(File file) {
        return file.getName().endsWith(".json");
    }

    protected void compareXmlFile(File expectedOutputFile, File actualOutputFile) {
        LOGGER.info("XMLUnit comparison with customized similarity for defaults:");
        Diff diff = makeXmlDiff(expectedOutputFile, actualOutputFile);
        if (diff.hasDifferences()) {
            for (Difference d: diff.getDifferences()) {
                LOGGER.error(d.toString());
            }
        } else {
            LOGGER.info("No diffs");
        }
        String message = String.format("XMLs %s and %s are not similar", expectedOutputFile.getPath(), actualOutputFile.getPath());
        assertFalse(message, diff.hasDifferences());
    }

    protected Diff makeXmlDiff(File expectedOutputFile, File actualOutputFile) {
        return DiffBuilder
                .compare(Input.fromFile(expectedOutputFile))
                .withTest(Input.fromFile(actualOutputFile))
                .checkForSimilar()
                .ignoreWhitespace()
                .ignoreComments()
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes))
                .withDifferenceEvaluator(makeDifferenceEvaluator())
                .build();
    }

    protected DifferenceEvaluator makeDifferenceEvaluator() {
        return DifferenceEvaluators.Default;
    }

    protected void compareJsonFile(File expectedOutputFile, File actualOutputFile) throws Exception {
        String expectedContent = FileUtils.readFileToString(expectedOutputFile, "UTF-8");
        String actualContent = FileUtils.readFileToString(actualOutputFile, "UTF-8");
        String message = String.format("%s vs %s", expectedOutputFile.getPath(), actualOutputFile.getPath());
        JSONAssert.assertEquals(message, expectedContent, actualContent, JSONCompareMode.STRICT);
    }

    protected void compareTextFile(File expectedOutputFile, File actualOutputFile) throws IOException {
        String expectedTypeContent = FileUtils.readFileToString(expectedOutputFile, "UTF-8").replace("    \r", "\r").replace("\r", "");
        String actualTypeContent = FileUtils.readFileToString(actualOutputFile, "UTF-8").replace("    \r", "\r").replace("\r", "");
        assertEquals(expectedOutputFile.getCanonicalPath(), expectedTypeContent, actualTypeContent);
    }

    protected void compareFileList(File expectedParent, List<File> expectedChildren, File actualParent, List<File> actualChildren) throws Exception {
        String message = String.format("Different number of children when comparing '%s' with '%s'", expectedParent.getCanonicalPath(), actualParent.getCanonicalPath());
        assertEquals(message, expectedChildren.size(), actualChildren.size());
        expectedChildren.sort(new FileComparator());
        actualChildren.sort(new FileComparator());
        for (int i = 0; i < expectedChildren.size(); i++) {
            compareFile(expectedChildren.get(i), actualChildren.get(i));
        }
    }
}
