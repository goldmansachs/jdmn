package com.gs.dmn.feel.analysis.syntax.ast.library.json;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonDescriptionGeneratorTest {
    @Test
    public void testGenerateWritesToCustomDirectory() throws Exception {
        Path tempDir = Files.createTempDirectory("jdmn-json-out");
        try {
            JsonDescriptionGenerator generator = new JsonDescriptionGenerator();
            generator.generate("feel/library/builtin.lib", tempDir);

            Path outFile = tempDir.resolve("builtin.json");
            assertTrue(Files.exists(outFile), "Expected generated file to exist: " + outFile);
            String content = Files.readString(outFile, StandardCharsets.UTF_8);
            // Basic sanity checks
            assertTrue(content.contains("\"name\""));
            assertTrue(content.contains("builtin"), "Expected content to mention builtin library");
        } finally {
            // Cleanup
            Files.walk(tempDir)
                    .sorted(java.util.Comparator.reverseOrder())
                    .forEach(p -> p.toFile().delete());
        }
    }

    @Test
    public void testToJsonConvertsLibrary() throws Exception {
        // Create a library.FunctionDeclaration with a named type and a single parameter
        com.gs.dmn.feel.analysis.syntax.ast.expression.type.NamedTypeExpression<Object> typeExpr = new com.gs.dmn.feel.analysis.syntax.ast.expression.type.NamedTypeExpression<>("number");
        com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter<Object> formal = new com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter<>("a", typeExpr, null);
        com.gs.dmn.feel.analysis.syntax.ast.library.FunctionDeclaration<Object> libFunc = new com.gs.dmn.feel.analysis.syntax.ast.library.FunctionDeclaration<>("sum", List.of(formal), typeExpr);
        com.gs.dmn.feel.analysis.syntax.ast.library.Library<Object> library = new com.gs.dmn.feel.analysis.syntax.ast.library.Library<>(null, "my-lib", List.of(libFunc));

        JsonDescriptionGenerator generator = new JsonDescriptionGenerator();
        FEELLibrary feelLibrary = generator.toJson(library);

        assertEquals("my-lib", feelLibrary.name());
        assertEquals(1, feelLibrary.functions().size());
        FunctionDeclaration jsonFunc = feelLibrary.functions().get(0);
        assertEquals("sum", jsonFunc.getName());
        assertEquals("sum(a: number) : number", jsonFunc.getSignature());
        assertEquals("number", jsonFunc.getReturnType());
    }
}
