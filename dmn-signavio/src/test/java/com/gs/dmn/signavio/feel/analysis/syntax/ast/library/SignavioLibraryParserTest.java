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
package com.gs.dmn.signavio.feel.analysis.syntax.ast.library;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.feel.analysis.syntax.ast.library.BuiltinLibraryParserTest;
import com.gs.dmn.feel.analysis.syntax.ast.library.ELLib;
import com.gs.dmn.feel.analysis.syntax.ast.library.LibraryMetadata;
import com.gs.dmn.feel.analysis.syntax.ast.library.LibraryRepository;
import com.gs.dmn.signavio.runtime.SignavioEnvironmentFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SignavioLibraryParserTest extends BuiltinLibraryParserTest {
    @Test
    public void testSignavioLibrary() {
        // Create library
        LibraryRepository libraryRepository = new LibraryRepository(inputParameters);
        String libPath = "feel/library/signavio.lib";
        libraryRepository.discoverLibraries(SignavioEnvironmentFactory.instance().getBuiltInContext());
        ELLib library = libraryRepository.getLibrary("com.gs.dmn.signavio.feel.lib");

        // Check properties
        assertNotNull(library);
        assertEquals("com.gs.dmn.signavio.feel.lib", library.getNamespace());
        assertEquals("signavio", library.getName());
        LibraryMetadata metadata = library.getMetadata();
        assertNotNull(metadata);
        assertEquals("feel/library/signavio.lib", metadata.getDescriptionPath());
        assertEquals("com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib", metadata.getClassName());
        assertFalse(metadata.isStaticAccess());

        // Semantic analysis
        checkTypes(library);

        // Check every library declaration
        // Semantic analysis
        checkTypes(library);

        // Check every library declaration
        DMNContext builtInContext = SignavioEnvironmentFactory.instance().getBuiltInContext();
        Map<String, List<Declaration>> variablesTable = builtInContext.getEnvironment().getVariablesTable();
        check(library, variablesTable);
    }
}
