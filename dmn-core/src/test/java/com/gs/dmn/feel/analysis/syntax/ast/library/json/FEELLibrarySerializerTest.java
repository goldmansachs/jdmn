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

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class FEELLibrarySerializerTest {
    private final FEELLibrarySerializer serializer = new FEELLibrarySerializer();

    @Test
    public void testRead() throws Exception {
        FEELLibrary library = serializer.read("feel/json/signavio.json");
        assertNotNull(library);
        assertEquals("signavio", library.name());
        assertEquals(91, library.functions().size());
    }

    @Test
    public void testWrite() throws Exception {
        FEELLibrary library1 = serializer.read("feel/json/signavio.json");
        assertNotNull(library1);
        File tempFile = File.createTempFile("feel-lib", ".json");
        serializer.write(library1, tempFile);

        FEELLibrary library2 = serializer.read(tempFile);
        assertNotNull(library2);
        assertEquals("signavio", library2.name());
        assertEquals(91, library2.functions().size());
    }
}