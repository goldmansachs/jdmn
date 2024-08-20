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

import static com.gs.dmn.serialization.DMNConstants.isDMNFile;
import static com.gs.dmn.serialization.DMNConstants.isTCKFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
            } else if (isDMNFile(expectedOutputFile, this.inputParameters.getDmnFileExtension()) && isDMNFile(actualOutputFile, this.inputParameters.getDmnFileExtension())) {
                compareDMNFile(expectedOutputFile, actualOutputFile);
            } else if (isTCKFile(expectedOutputFile, this.inputParameters.getTckFileExtension()) && isTCKFile(actualOutputFile, this.inputParameters.getTckFileExtension())) {
                compareTCKFile(expectedOutputFile, actualOutputFile);
            } else if (expectedOutputFile.getName().equals(actualOutputFile.getName())) {
                compareTextFile(expectedOutputFile, actualOutputFile);
            } else {
                throw new DMNRuntimeException(String.format("Files with different names '%s' '%s' ", expectedOutputFile.getCanonicalPath(), actualOutputFile.getCanonicalPath()));
            }
        } else {
            throw new DMNRuntimeException(String.format("Cannot compare folder with file '%s' '%s' ", expectedOutputFile.getCanonicalPath(), actualOutputFile.getCanonicalPath()));
        }
    }

    private boolean isJsonFile(File file) {
        return file.getName().endsWith(".json");
    }

    protected void compareDMNFile(File expectedOutputFile, File actualOutputFile) {
        compareXmlFile(expectedOutputFile, actualOutputFile, makeDMNDiff(expectedOutputFile, actualOutputFile));
    }

    protected void compareTCKFile(File expectedOutputFile, File actualOutputFile) {
        compareXmlFile(expectedOutputFile, actualOutputFile, makeTCKDiff(expectedOutputFile, actualOutputFile));
    }

    protected void compareXmlFile(File expectedOutputFile, File actualOutputFile, Diff diff) {
        LOGGER.info("XMLUnit comparison with customized similarity for defaults:");
        if (diff.hasDifferences()) {
            for (Difference d: diff.getDifferences()) {
                LOGGER.error(d.toString());
            }
        } else {
            LOGGER.info("No diffs");
        }
        String message = String.format("XMLs %s and %s are not similar", expectedOutputFile.getPath(), actualOutputFile.getPath());
        assertFalse(diff.hasDifferences(), message);
    }

    protected Diff makeDMNDiff(File expectedOutputFile, File actualOutputFile) {
        return DiffBuilder
                .compare(Input.fromFile(expectedOutputFile))
                .withTest(Input.fromFile(actualOutputFile))
                .checkForSimilar()
                .withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byNameAndAllAttributes))
                .withDifferenceEvaluator(makeDMNDifferenceEvaluator())
                .ignoreWhitespace()
                .ignoreComments()
                .build();
    }

    protected Diff makeTCKDiff(File expectedOutputFile, File actualOutputFile) {
        return DiffBuilder
                .compare(Input.fromFile(expectedOutputFile))
                .withTest(Input.fromFile(actualOutputFile))
                .withDifferenceEvaluator(makeTCKDifferenceEvaluator())
                .checkForSimilar()
                .ignoreWhitespace()
                .ignoreComments()
                .build();
    }

    protected DifferenceEvaluator makeDMNDifferenceEvaluator() {
        return DifferenceEvaluators.Default;
    }

    protected DifferenceEvaluator makeTCKDifferenceEvaluator() {
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
        assertEquals(expectedTypeContent, actualTypeContent, expectedOutputFile.getCanonicalPath());
    }

    protected void compareFileList(File expectedParent, List<File> expectedChildren, File actualParent, List<File> actualChildren) throws Exception {
        String message = String.format("Different number of children when comparing '%s' with '%s'", expectedParent.getCanonicalPath(), actualParent.getCanonicalPath());
        assertEquals(expectedChildren.size(), actualChildren.size(), message);
        expectedChildren.sort(new FileComparator());
        actualChildren.sort(new FileComparator());
        for (int i = 0; i < expectedChildren.size(); i++) {
            compareFile(expectedChildren.get(i), actualChildren.get(i));
        }
    }
}
