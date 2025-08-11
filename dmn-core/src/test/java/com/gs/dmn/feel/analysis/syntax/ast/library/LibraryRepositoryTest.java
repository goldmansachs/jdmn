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

import com.gs.dmn.feel.analysis.semantics.environment.StandardEnvironmentFactory;
import com.gs.dmn.transformation.InputParameters;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LibraryRepositoryTest {
    @Test
    public void testExternalLibsAreConfigured() {
        LibraryRepository libraryRepository = new LibraryRepository(new InputParameters());
        libraryRepository.discoverLibraries(StandardEnvironmentFactory.instance().getBuiltInContext());
        ELLib library = libraryRepository.getLibrary("com.gs.dmn.signavio.feel.lib");
        assertNotNull(library);
        assertEquals("com.gs.dmn.signavio.feel.lib", library.getNamespace());
        assertEquals("signavio", library.getName());
    }
}