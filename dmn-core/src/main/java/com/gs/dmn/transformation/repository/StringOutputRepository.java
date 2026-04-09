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
package com.gs.dmn.transformation.repository;

import java.nio.file.Path;

public class StringOutputRepository extends OutputRepository {
    private final String rootPath;

    public StringOutputRepository() {
        this("");
    }

    public StringOutputRepository(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    public OutputElement makeOutputElement(String nativePackageName, String elementName, String fileExtension) {
        OutputElement elementOutput = new StringOutput(nativePackageName, elementName, fileExtension);
        this.elements.add(elementOutput);
        return elementOutput;
    }

    @Override
    protected OutputRepository makeOutputRepository(Path childPath) {
        return new StringOutputRepository(childPath.toString());
    }
}
