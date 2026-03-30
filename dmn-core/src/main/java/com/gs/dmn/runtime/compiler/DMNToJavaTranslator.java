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
package com.gs.dmn.runtime.compiler;

import com.gs.dmn.tck.TestCasesToNativeTransformer;
import com.gs.dmn.transformation.DMNToNativeTransformer;

import java.io.File;

public class DMNToJavaTranslator {
    private final DMNToNativeTransformer dmnTranslator;
    private final TestCasesToNativeTransformer tckTranslator;

    public DMNToJavaTranslator(DMNToNativeTransformer dmnTranslator, TestCasesToNativeTransformer tckTestCasesToJavaJUnitTransformer) {
        this.dmnTranslator = dmnTranslator;
        this.tckTranslator = tckTestCasesToJavaJUnitTransformer;
    }

    public void translateDMN(File inputFile, File outputFolder) {
        dmnTranslator.transform(inputFile.toPath(), outputFolder.toPath());
    }

    public void translateTCK(File inputTestFile, File outputFile) {
        tckTranslator.transform(inputTestFile.toPath(), outputFile.toPath());
    }
}


