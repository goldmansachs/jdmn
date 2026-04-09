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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class OutputRepository {
    protected final List<OutputElement> elements = new ArrayList<>();

    public abstract String getRootPath();

    public abstract OutputElement makeOutputElement(String path, String nativeClassName, String fileExtension);

    public boolean notEmptyPackage(String packageName) {
        for (OutputElement element : elements) {
            if (isChild(element, packageName)) {
                return true;
            }
        }
        return false;
    }

    private boolean isChild(OutputElement element, String packageName) {
        String elementPackageName = element.getNativePackageName();
        return elementPackageName.startsWith(packageName);
    }

    // Collects source code from the provided files and puts it in the map with class name as key and source code as value
    public void collectJavaClasses(Map<String, String> allClassesMap) {
        List<OutputElement> javaElements = this.elements.stream().filter(this::isJavaElement).toList();
        collectCode(javaElements, allClassesMap);
    }

    private boolean isJavaElement(OutputElement element) {
        return element.getExtension().endsWith(".java");
    }

    // Collects source code from the provided files and puts it in the map with class name as key and source code as value
    private void collectCode(List<OutputElement> sources, Map<String, String> allSources) {
        for (OutputElement element : sources) {
            String className = element.qualifiedName();
            String classSource = element.readText(java.nio.charset.StandardCharsets.UTF_8);
            allSources.put(className, classSource);
        }
    }
}
