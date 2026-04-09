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

import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public class StringOutput extends OutputElement {
    private StringWriter writer;

    public StringOutput(String nativePackageName, String name, String fileExtension) {
        super(nativePackageName, name, fileExtension);
    }

    @Override
    public Writer getWriter() {
        if (writer == null) {
            writer = new StringWriter();
        }
        return writer;
    }

    @Override
    public String readText(Charset charset) {
        return writer == null ? "" : writer.toString();
    }

    @Override
    public void writeText(String text, Charset charset) {
        // Reset content before writing to ensure previous content is cleared
        this.writer = new StringWriter();
        this.writer.write(text);
    }
}
