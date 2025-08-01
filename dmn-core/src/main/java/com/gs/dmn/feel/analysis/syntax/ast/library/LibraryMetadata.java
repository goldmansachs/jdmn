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
package com.gs.dmn.feel.analysis.syntax.ast.library;

public class LibraryMetadata {
    private final String descriptionPath;
    private final String className;
    private final boolean staticAccess;

    // Serialization
    public LibraryMetadata() {
        this(null, null, false);
    }

    public LibraryMetadata(String descriptionPath, String className, boolean staticAccess) {
        this.descriptionPath = descriptionPath;
        this.className = className;
        this.staticAccess = staticAccess;
    }

    public String getDescriptionPath() {
        return descriptionPath;
    }

    public String getClassName() {
        return className;
    }

    public boolean isStaticAccess() {
        return staticAccess;
    }
}
