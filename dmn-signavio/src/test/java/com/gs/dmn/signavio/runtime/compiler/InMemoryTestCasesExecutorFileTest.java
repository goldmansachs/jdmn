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
package com.gs.dmn.signavio.runtime.compiler;

import com.gs.dmn.transformation.repository.FileOutputRepository;
import com.gs.dmn.transformation.repository.OutputRepository;

import java.io.File;

public class InMemoryTestCasesExecutorFileTest extends InMemoryTestCasesExecutorTest {
    @Override
    protected OutputRepository makeOutputRepository(File outputTestFolder) {
        return new FileOutputRepository(outputTestFolder);
    }
}