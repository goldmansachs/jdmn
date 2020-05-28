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
package com.gs.dmn.runtime.interpreter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ImportPath {
    public static boolean isEmpty(String importName) {
        return StringUtils.isBlank(importName);
    }

    public static boolean isEmpty(ImportPath importPath) {
        return importPath == null || importPath.isEmpty();
    }

    private final List<String> pathElements = new ArrayList<>();

    public ImportPath() {
    }

    public ImportPath(String pathElement) {
        if (!StringUtils.isEmpty(pathElement)) {
            this.pathElements.add(pathElement);
        }
    }

    public ImportPath(ImportPath parent, String pathElement) {
        if (parent !=  null) {
            this.pathElements.addAll(parent.pathElements);
        }
        this.addPathElement(pathElement);
    }

    public List<String> getPathElements() {
        return this.pathElements;
    }

    public void addPathElement(String pathElement) {
        if (!StringUtils.isEmpty(pathElement)) {
            this.pathElements.add(pathElement);
        }
    }

    private boolean isEmpty() {
        return this.pathElements.isEmpty();
    }

    public String asString() {
        return String.join(".", pathElements);
    }

    @Override
    public String toString() {
        return String.format("%s", this.pathElements);
    }
}