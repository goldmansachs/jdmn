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

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OutputRepository {
    protected final File rootFile;

    public OutputRepository(File rootFile) {
        this.rootFile = rootFile;
    }

    public File getRootFile() {
        return rootFile;
    }

    public String getPath() {
        return rootFile.getPath();
    }

    public File makeOutputFile(String relativeFilePath, String fileName, String fileExtension) throws IOException {
        String absoluteFilePath = rootFile.getAbsolutePath();
        if (!StringUtils.isBlank(relativeFilePath)) {
            absoluteFilePath += "/" + relativeFilePath;
        }
        absoluteFilePath += "/" + fileName + fileExtension;
        File outputFile = new File(absoluteFilePath);
        Files.createDirectories(outputFile.getParentFile().toPath());
        return outputFile;
    }

    public List<File> makeOutputFolders(String[] packageParts) {
        List<File> folders = new ArrayList<>();
        File currentFolder = rootFile;
        for (String part : packageParts) {
            if (!StringUtils.isBlank(part)) {
                currentFolder = new File(currentFolder, part);
                folders.add(currentFolder);
            }
        }
        return folders;
    }

    public File makeOutputFile(File parent, String name) {
        return new File(parent, name);
    }

    // Collects source code from the provided files and puts it in the map with class name as key and source code as value
    public void collectJavaClasses(Map<String, String> allClassesMap) throws IOException {
        if (rootFile != null && rootFile.listFiles() != null) {
            deepCollectClasses(Arrays.asList(rootFile.listFiles()), "", this::isJavaFile, allClassesMap);
        }
    }

    private void deepCollectClasses(List<File> files, String currentPackage, java.util.function.Predicate<File> predicate, Map<String, String> allClassesMap) throws IOException {
        if (files != null) {
            for (File file : files) {
                deepCollectClasses(file, currentPackage, predicate, allClassesMap);
            }
        }
    }

    private void deepCollectClasses(File file, String currentPackage, java.util.function.Predicate<File> predicate, Map<String, String> allClassesMap) throws IOException {
        if (file.isDirectory()) {
            String childPackage = currentPackage.isEmpty() ? file.getName() : currentPackage + "." + file.getName();
            deepCollectClasses(Arrays.asList(file.listFiles()), childPackage, predicate, allClassesMap);
        } else if (predicate.test(file)) {
            String className = file.getName().replace(".java", "");
            String classQName = StringUtils.isEmpty(currentPackage) ? className : currentPackage + "." + className;
            String classSource = new String(Files.readAllBytes(file.toPath()));
            allClassesMap.put(classQName, classSource);
        }
    }

    private boolean isJavaFile(File file) {
        return file.isFile() && file.getName().endsWith(".java");
    }
}
