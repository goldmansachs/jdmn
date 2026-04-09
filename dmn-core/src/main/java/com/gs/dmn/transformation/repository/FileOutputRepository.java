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
import java.nio.file.Path;

public class FileOutputRepository extends OutputRepository {
    static String toPath(String nativePackageName) {
        return nativePackageName.replace('.', '/');
    }

    private final File rootFile;

    public FileOutputRepository(File rootFile) {
        this.rootFile = rootFile;
    }

    @Override
    public String getRootPath() {
        return rootFile.getPath();
    }

    @Override
    public OutputElement makeOutputElement(String nativePackageName, String elementName, String fileExtension) {
        String relativePath = toPath(nativePackageName);
        try {
            String absoluteFilePath = rootFile.getAbsolutePath();
            if (!StringUtils.isBlank(relativePath)) {
                absoluteFilePath += "/" + relativePath;
            }
            absoluteFilePath += "/" + elementName + fileExtension;
            File outputFile = new File(absoluteFilePath);
            Files.createDirectories(outputFile.getParentFile().toPath());
            FileOutput fileOutput = new FileOutput(nativePackageName, elementName, fileExtension, outputFile);
            this.elements.add(fileOutput);
            return fileOutput;
        } catch (IOException e) {
            throw new RuntimeException(String.format("Error creating output file '%s'", relativePath), e);
        }
    }

    @Override
    protected OutputRepository makeOutputRepository(Path childPath) {
        return new FileOutputRepository(childPath.toFile());
    }
}
