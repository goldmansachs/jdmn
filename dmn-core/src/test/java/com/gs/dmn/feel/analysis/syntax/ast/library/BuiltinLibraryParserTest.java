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
import com.gs.dmn.feel.analysis.syntax.ErrorListener;
import com.gs.dmn.feel.analysis.syntax.antlrv4.LibraryLexer;
import com.gs.dmn.feel.analysis.syntax.antlrv4.LibraryParser;
import com.gs.dmn.feel.analysis.syntax.ast.ASTFactory;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class BuiltinLibraryParserTest extends AbstractTest {
    @Test
    public void testBuiltinLibrary() throws IOException {
        // Parse library
        String libPath = "feel/library/builtin.lib";
        Library<?> library = parseLibrary(libPath);

        // Semantic analysis
        DMNContext builtInContext = StandardEnvironmentFactory.instance().getBuiltInContext();
        ELLib lib = library.analyze(builtInContext);

        // Check properties
        assertNotNull(lib);
        assertEquals("com.gs.dmn.feel.lib", lib.getNamespace());
        assertEquals("builtin", lib.getName());

        // Semantic analysis
        checkTypes(lib);

        // Check every library declaration
        Map<String, List<Declaration>> variablesTable = builtInContext.getEnvironment().getVariablesTable();
        check(lib.getDeclarations(), variablesTable);
    }

    protected Library<?> parseLibrary(String libPath) throws IOException {
        String text = readLibrary(libPath);
        LibraryParser parser = makeParser(text);
        return (Library<?>) parser.library().ast;
    }

    private String readLibrary(String libPath) {
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(libPath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Error reading lib: " + libPath);
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading lib: " + libPath, e);
        }
    }

    protected void check(List<Declaration> libraryDeclarations, Map<String, List<Declaration>> variablesTable) {
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

    protected LibraryParser makeParser(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Input tape cannot be null.");
        }
        CharStream cs = CharStreams.fromString(text);
        LibraryLexer lexer = new LibraryLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LibraryParser parser = LibraryParser.makeLibraryParser(tokens, new ASTFactory<Type, DMNContext>());
        parser.removeErrorListeners();
        parser.addErrorListener(new ErrorListener());
        return parser;
    }

}
