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
package com.gs.dmn.transformation;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractFileTransformer implements FileTransformer {
    protected final BuildLogger logger;
    protected final InputParameters inputParameters;

    protected AbstractFileTransformer(BuildLogger logger, InputParameters inputParameters) {
        this.logger = logger;
        this.inputParameters = inputParameters;
    }

    @Override
    public void transform(File inputFile, File outputFolder) {
        List<File> files = new ArrayList<>();
        collectFiles(inputFile, files);
        if (files.isEmpty()) {
            logger.warn(String.format("Cannot find %s files to transform in %s", getInputFileType(), inputFile.getAbsolutePath()));
        } else {
            transformFiles(files, outputFolder);
        }
    }

    protected void collectFiles(File inputFile, List<File> files) {
        if (Files.isRegularFile(inputFile.toPath()) && shouldTransformFile(inputFile)) {
            files.add(inputFile);
        } else if (Files.isDirectory(inputFile.toPath())) {
            // All levels
            try (Stream<Path> stream = Files.walk(inputFile.toPath())) {
                files.addAll(
                    stream
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .filter(this::shouldTransformFile)
                    .toList()
                );
            } catch (IOException e) {
                throw new DMNRuntimeException(String.format("Error collecting files in '%s'", inputFile.getPath()), e);
            }
        }
    }

    protected String relativePath(String parentPath, String childPath) {
        if (parentPath.endsWith("/")) {
            parentPath = parentPath.substring(0, parentPath.length() - 1);
        }
        if (childPath.startsWith(parentPath)) {
            String relativePath = childPath.substring(parentPath.length());
            return relativePath.startsWith("/") ? relativePath.substring(1) : relativePath;
        } else {
            throw new DMNRuntimeException(String.format("Cannot compute relative path for parent '%s' and child '%s'", parentPath, childPath));
        }
    }

    protected abstract String getInputFileType();

    protected abstract boolean shouldTransformFile(File inputFile);

    protected abstract void transformFiles(List<File> files, File outputFolder);
}
