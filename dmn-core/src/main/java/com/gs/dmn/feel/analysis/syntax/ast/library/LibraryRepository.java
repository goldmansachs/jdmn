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

import com.fasterxml.jackson.core.type.TypeReference;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ErrorListener;
import com.gs.dmn.feel.analysis.syntax.antlrv4.LibraryLexer;
import com.gs.dmn.feel.analysis.syntax.antlrv4.LibraryParser;
import com.gs.dmn.feel.analysis.syntax.ast.ASTFactory;
import com.gs.dmn.serialization.JsonSerializer;
import com.gs.dmn.transformation.InputParameters;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryRepository {
    private static final LibraryMemoizer CACHE = new LibraryMemoizer();
    private final InputParameters inputParameters;

    public LibraryRepository(InputParameters inputParameters) {
        this.inputParameters = inputParameters;
    }

    public ELLib getLibrary(String namespace) {
        return CACHE.getLib(namespace);
    }

    public Collection<ELLib> getLibraries() {
        return CACHE.getLibraries();
    }

    public void discoverLibraries(DMNContext context) {
        String configPath = this.inputParameters.getLibrariesConfigPath();
        if (!CACHE.containsConfig(configPath)) {
            // Discover libraries
            List<LibraryMetadata> metadataList = readConfig(configPath);
            for (LibraryMetadata metadata : metadataList) {
                if (metadata != null && !StringUtils.isBlank(metadata.getDescriptionPath())) {
                    ELLib lib = createLibrary(metadata, context);
                    CACHE.addLib(lib);
                }
            }

            // Mark as discovered
            CACHE.addConfig(configPath);
        }
    }

    private List<LibraryMetadata> readConfig(String resourcePath) {
        try (InputStream inputStream = LibraryRepository.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            } else {
                return JsonSerializer.OBJECT_MAPPER.readValue(inputStream, new TypeReference<List<LibraryMetadata>>(){});
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading resource: " + resourcePath, e);
        }
    }

    public ELLib createLibrary(LibraryMetadata metadata, DMNContext context) {
        // Parse library
        Library<?> library = parseLibrary(metadata.getDescriptionPath());
        // Semantic analysis
        return library.analyze(context, metadata);
    }

    private Library<?> parseLibrary(String libPath) {
        String text = readLibrary(libPath);
        LibraryParser parser = makeParser(text);
        return (Library<?>) parser.library().ast;
    }

    private String readLibrary(String libPath) {
        try (InputStream inputStream = LibraryRepository.class.getClassLoader().getResourceAsStream(libPath)) {
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

    private LibraryParser makeParser(String text) {
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
