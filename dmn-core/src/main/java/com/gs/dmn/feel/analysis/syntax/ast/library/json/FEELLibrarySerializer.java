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
package com.gs.dmn.feel.analysis.syntax.ast.library.json;

import com.gs.dmn.serialization.JsonSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Objects;

public class FEELLibrarySerializer {
    public void write(FEELLibrary library, File jsonFile) throws Exception {
        if (library != null) {
            try (Writer writer = new FileWriter(jsonFile)) {
                JsonSerializer.OBJECT_MAPPER.writeValue(writer, library);
            }
        }
    }

    public FEELLibrary read(String libPath) throws Exception {
        File file = new File(Objects.requireNonNull(this.getClass().getClassLoader().getResource(libPath)).getFile());
        return JsonSerializer.OBJECT_MAPPER.readValue(file, FEELLibrary.class);
    }

    public FEELLibrary read(File file) throws Exception {
        return JsonSerializer.OBJECT_MAPPER.readValue(file, FEELLibrary.class);
    }
}

