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
import com.gs.dmn.transformation.repository.InputRepository;
import com.gs.dmn.transformation.repository.OutputRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileTransformer implements FileTransformer {
    protected final BuildLogger logger;
    protected final InputParameters inputParameters;

    protected AbstractFileTransformer(BuildLogger logger, InputParameters inputParameters) {
        this.logger = logger;
        this.inputParameters = inputParameters;
    }

    @Override
    public void transform(InputRepository inputRepository, OutputRepository outputRepository) {
        List<File> files = new ArrayList<>();
        collectFiles(inputRepository, files);
        if (files.isEmpty()) {
            logger.warn(String.format("Cannot find %s files to transform in %s", getInputFileType(), inputRepository.getPath()));
        } else {
            transformFiles(files, outputRepository);
        }
    }

    protected void collectFiles(InputRepository inputRepository, List<File> files) {
        inputRepository.deepCollectFiles(this::shouldTransformFile, files);
    }

    protected abstract String getInputFileType();

    protected abstract boolean shouldTransformFile(File inputFile);

    protected abstract void transformFiles(List<File> files, OutputRepository outputRepository);
}
