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

import com.gs.dmn.AbstractTest;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.environment.StandardEnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.FunctionType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BuiltinLibraryParserTest extends AbstractTest {
    @Test
    public void testBuiltinLibrary() {
        // Create library
        LibraryRepository libraryRepository = new LibraryRepository(inputParameters);
        LibraryMetadata metadata1 = new LibraryMetadata("feel/library/builtin.lib", "com.gs.dmn.feel.lib", false);
        ELLib library = libraryRepository.createLibrary(metadata1, StandardEnvironmentFactory.instance().getBuiltInContext());

        // Check properties
        assertNotNull(library);
        assertEquals("com.gs.dmn.feel.lib", library.getNamespace());
        assertEquals("builtin", library.getName());
        LibraryMetadata metadata2 = library.getMetadata();
        assertNotNull(metadata2);

        // Semantic analysis
        checkTypes(library);

        // Check every library declaration
        DMNContext builtInContext = StandardEnvironmentFactory.instance().getBuiltInContext();
        Map<String, List<Declaration>> variablesTable = builtInContext.getEnvironment().getVariablesTable();
        check(library, variablesTable);
    }

    protected void check(ELLib library, Map<String, List<Declaration>> variablesTable) {
        List<Declaration> libraryDeclarations = library.getDeclarations();
        int sum = variablesTable.values().stream().mapToInt(List::size).sum();
        assertEquals(sum, libraryDeclarations.size());
        for (Declaration libraryDeclaration : libraryDeclarations) {
            String name = libraryDeclaration.getName();
            List<Declaration> declarations = variablesTable.get(name);
            Type libraryDeclarationType = libraryDeclaration.getType();
            boolean found = false;
            for (Declaration declaration : declarations) {
                Type type = declaration.getType();
                // Same serialization / type expression missing in some
                if (sameSignature(libraryDeclarationType, type)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, String.format("Cannot find declaration for '%s'", libraryDeclaration));
        }
    }

    protected void checkTypes(ELLib lib) {
        for (Declaration declaration : lib.getDeclarations()) {
            Type type = declaration.getType();
            if (type instanceof FunctionType) {
                FunctionType functionType = (FunctionType) type;
                boolean staticParameterTypes = functionType.getParameters().stream().allMatch(p -> p.getType() != null);
                boolean staticReturnType = functionType.getReturnType() != null;
                assertTrue(staticParameterTypes && staticReturnType, String.format("Declaration '%s' is not static types", declaration));
            }
        }
    }

    private static boolean sameSignature(Type libraryDeclarationType, Type type) {
        if (libraryDeclarationType instanceof FunctionType && type instanceof FunctionType) {
            String typeExp1 = libraryDeclarationType.typeExpression();
            String typeExp2 = type.typeExpression();
            return typeExp1.equals(typeExp2);
        } else {
            return false;
        }
    }
}
