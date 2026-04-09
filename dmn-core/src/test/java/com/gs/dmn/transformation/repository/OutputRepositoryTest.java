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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public abstract class OutputRepositoryTest {
    protected Path tempDir;

    @AfterEach
    public void cleanup() throws Exception {
        if (tempDir != null && Files.exists(tempDir)) {
            // Recursively delete
            Files.walk(tempDir)
                    .map(Path::toFile)
                    .sorted((a, b) -> -a.compareTo(b))
                    .forEach(File::delete);
        }
    }

    @Test
    public void testRepositoryWithoutChildren() throws Exception {
        tempDir = Files.createTempDirectory("for-test-");
        OutputRepository repo = makeRepository();

        // Make java element
        String packageName = "com.gs.test";
        String className = "MyClass";
        String ext = ".java";
        String content = "package com.gs.test;\npublic class MyClass {}";
        OutputElement element = makeOutputElement(repo, packageName, className, ext, content);

        // Check properties of element
        assertEquals("com.gs.test", element.getNativePackageName());
        assertEquals(className, element.getName());
        assertEquals(ext, element.getExtension());
        assertEquals(content, element.readText(StandardCharsets.UTF_8));

        // Check notEmptyPackage
        assertTrue(repo.notEmptyPackage("com.gs"));
        assertFalse(repo.notEmptyPackage("other"));

        // collectJavaClasses should find the java element we created
        Map<String, String> classes = new HashMap<>();
        repo.collectJavaClasses(classes);
        assertEquals(1, classes.size());
        assertTrue(classes.containsKey("com.gs.test.MyClass"));
        assertEquals(content, classes.get("com.gs.test.MyClass"));
    }

    @Test
    public void testRepositoryWithNonJavaElements() throws Exception {
        tempDir = Files.createTempDirectory("for-test-root-");
        OutputRepository repo = makeRepository();

        // Make Java element
        String packageName = "";
        String name = "RootFile";
        String ext = ".txt";
        String content = "root content";
        OutputElement element = makeOutputElement(repo, packageName, name, ext, content);

        // Check properties of element
        assertEquals("", element.getNativePackageName());
        assertEquals(name, element.getName());
        assertEquals(ext, element.getExtension());
        assertEquals(content, element.readText(StandardCharsets.UTF_8));

        // collectJavaClasses should not collect non-java files
        Map<String, String> classes = new HashMap<>();
        repo.collectJavaClasses(classes);
        assertTrue(classes.isEmpty());
    }

    @Test
    public void testRepositoryWithChild() throws Exception {
        tempDir = Files.createTempDirectory("for-test-child-");
        OutputRepository parentRepo = makeRepository();

        // Make non-Java element in parent repository
        String parentPackage = "org.example";
        String parentClass = "A";
        String parentExt = ".txt";
        String parentContent = "text";
        OutputElement parentElement = makeOutputElement(parentRepo, parentPackage, parentClass, parentExt, parentContent);

        // Make child repository
        Path childPath = tempDir.resolve("child-dir");
        OutputRepository childRepo = parentRepo.addChildRepository(childPath);
        assertNotNull(childRepo);
        assertEquals(childPath.toFile().getPath(), childRepo.getRootPath());

        // Make Java element in child repository
        String childPackage = "org.example";
        String childClass = "C";
        String childExt = ".java";
        String childContent = "class C {}";
        OutputElement childElement = makeOutputElement(childRepo, childPackage, childClass, childExt, childContent);

        // collectJavaClasses on root repo should include child's java class
        Map<String, String> classes = new HashMap<>();
        parentRepo.collectJavaClasses(classes);
        assertEquals(1, classes.size());
        assertTrue(classes.containsKey("org.example.C"));
        assertEquals(childContent, classes.get("org.example.C"));
    }

    private static OutputElement makeOutputElement(OutputRepository repo, String packageName, String className, String ext, String content) {
        // Create element and write content
        OutputElement element = repo.makeOutputElement(packageName, className, ext);
        assertNotNull(element, "makeOutputElement should return a non-null element");
        element.writeText(content, StandardCharsets.UTF_8);
        return element;
    }

    protected abstract OutputRepository makeRepository();
}

