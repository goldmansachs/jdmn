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
package com.gs.dmn.serialization;

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.serialization.xstream.DMNMarshallerFactory;
import com.gs.dmn.transformation.AbstractFileTransformerTest;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;

public abstract class DMNDialectTransformerTest extends AbstractFileTransformerTest {
    protected static final String LATEST_VERSION = "latest";

    protected final DMNMarshaller dmnMarshaller = getDMNMarshaller();

    protected void doTest(String inputFileName) throws Exception {
        Charset charset = this.inputParameters.getCharset();

        // Read
        File inputFile = new File(resource(getInputPath() + inputFileName));
        TDefinitions sourceDefinitions;
        try (FileInputStream fis = new FileInputStream(inputFile); InputStreamReader isr = new InputStreamReader(fis, charset)) {
            sourceDefinitions = dmnMarshaller.unmarshal(isr, true);
        }

        // Transform
        TDefinitions targetDefinitions = transform(sourceDefinitions);

        // Write
        File actualOutputFile = getActualOutputFile(inputFileName);
        try (FileOutputStream fos = new FileOutputStream(actualOutputFile); OutputStreamWriter osw = new OutputStreamWriter(fos, charset)) {
            dmnMarshaller.marshal(targetDefinitions, osw);
        }

        // Compare
        File expectedOutputFile = getExpectedOutputFile(inputFileName);
        compareFile(expectedOutputFile, actualOutputFile);
    }

    protected File getActualOutputFile(String inputFileName) throws IOException {
        File targetFolder = new File(getTargetPath());
        Files.createDirectories(targetFolder.toPath());
        return new File(targetFolder, inputFileName);
    }

    protected DMNMarshaller getDMNMarshaller() {
        return DMNMarshallerFactory.newDefaultMarshaller();
    }

    protected File getExpectedOutputFile(String inputFileName) {
        return new File(resource(getExpectedPath() + inputFileName));
    }

    protected TDefinitions transform(TDefinitions sourceDefinitions) {
        return getTransformer().transformDefinitions(sourceDefinitions);
    }

    protected String getInputPath() {
        return String.format("dmn/input/%s/", getSourceVersion());
    }

    protected String getTargetPath() {
        return String.format("target/version/%s/%s/", getSourceVersion(), getTargetVersion());
    }

    protected String getExpectedPath() {
        return String.format("dmn/expected/%s/%s/", getSourceVersion(), getTargetVersion());
    }

    protected abstract SimpleDMNDialectTransformer getTransformer();

    protected abstract String getSourceVersion();

    protected abstract String getTargetVersion();
}