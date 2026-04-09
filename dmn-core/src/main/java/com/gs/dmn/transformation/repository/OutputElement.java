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

import org.apache.commons.lang3.StringUtils;

import java.io.Writer;
import java.nio.charset.Charset;

public abstract class OutputElement {
    private final String nativePackageName;
    private final String name;
    private final String extension;

    public OutputElement(String nativePackageName, String name, String extension) {
        this.nativePackageName = nativePackageName;
        this.name = name;
        this.extension = extension;
    }

    public String getNativePackageName() {
        return nativePackageName;
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

    public String qualifiedName() {
        return StringUtils.isBlank(nativePackageName) ? name : String.format("%s.%s", nativePackageName, name);
    }

    public abstract Writer getWriter();

    public abstract String readText(Charset charset);

    public abstract void writeText(String text, Charset charset);

    @Override
    public String toString() {
        return qualifiedName();
    }
}
