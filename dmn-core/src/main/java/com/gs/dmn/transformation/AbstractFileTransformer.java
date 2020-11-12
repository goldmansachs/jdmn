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
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public abstract class AbstractFileTransformer implements FileTransformer {
    protected final InputParameters inputParameters;
    protected final BuildLogger logger;

    protected AbstractFileTransformer(InputParameters inputParameters, BuildLogger logger) {
        this.inputParameters = inputParameters;
        this.logger = logger;
    }

    @Override
    public void transform(Path inputPath, Path outputPath) {
        File targetDirectory = outputPath.toFile();
        if (!targetDirectory.isDirectory() && !targetDirectory.mkdirs()) {
            throw new DMNRuntimeException("Unable to create directory " + targetDirectory);
        }

        File inputFile = inputPath.toFile();
        if (shouldTransformFile(inputFile)) {
            transformFile(inputFile, inputFile, outputPath);
        } else {
            throw new DMNRuntimeException("Illegal input file " + inputFile.getAbsolutePath());
        }
    }

    protected File outputFolder(File child, File root, Path outputPath) throws IOException {
        if (root.isDirectory()) {
            String relativePath = relativePath(root, child);
            File outputFolder;
            if (StringUtils.isBlank(relativePath)) {
                outputFolder = outputPath.toFile();
            } else {
                String path = outputPath.toFile().getCanonicalPath();
                outputFolder = new File(path + "/" + relativePath);
            }
            outputFolder.mkdirs();
            return outputFolder;
        } else if (root.getCanonicalPath().equals(child.getCanonicalPath())) {
            File outputFolder = outputPath.toFile();
            outputFolder.mkdirs();
            return outputFolder;
        } else {
            throw new DMNRuntimeException(String.format("Cannot compute output folder for child '%s' and root '%s'", child.getCanonicalPath(), root.getCanonicalPath()));
        }
    }

    private String relativePath(File root, File child) throws IOException {
        String childPath = child.getParentFile().getCanonicalPath();
        String parentPath = root.getCanonicalPath();
        return relativePath(parentPath, childPath);
    }

    String relativePath(String parentPath, String childPath) {
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

    protected abstract boolean shouldTransformFile(File inputFile);

    protected abstract void transformFile(File child, File root, Path outputPath);
}
