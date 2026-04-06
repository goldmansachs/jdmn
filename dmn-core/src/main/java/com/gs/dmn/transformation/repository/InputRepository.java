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

import com.gs.dmn.runtime.DMNRuntimeException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class InputRepository {
    protected final File rootFile;

    public InputRepository(File rootFile) {
        this.rootFile = rootFile;
    }

    public File getRootFile() {
        return rootFile;
    }

    public String getPath() {
        return rootFile.getPath();
    }

    public void deepCollectFiles(Predicate<File> filter, List<File> files) {
        deepCollectFiles(rootFile, filter, files);
    }

    public void shallowCollectFiles(Predicate<File> filter, List<File> files) {
        shallowCollectFiles(rootFile, filter, files);
    }

    private void deepCollectFiles(File inputFile, Predicate<File> filter, List<File> files) {
        if (Files.isRegularFile(inputFile.toPath()) && filter.test(inputFile)) {
            files.add(inputFile);
        } else if (Files.isDirectory(inputFile.toPath())) {
            // All levels
            try (Stream<Path> stream = Files.walk(inputFile.toPath())) {
                files.addAll(
                        stream
                                .filter(Files::isRegularFile)
                                .map(Path::toFile)
                                .filter(filter)
                                .toList()
                );
            } catch (IOException e) {
                throw new DMNRuntimeException(String.format("Error collecting files in '%s'", inputFile.getPath()), e);
            }
        }
    }

    private void shallowCollectFiles(File inputFile, Predicate<File> filter, List<File> files) {
        if (Files.isRegularFile(inputFile.toPath()) && filter.test(inputFile)) {
            files.add(inputFile);
        } else if (Files.isDirectory(inputFile.toPath())) {
            // One single level
            try (Stream<Path> stream = Files.list(inputFile.toPath())) {
                files.addAll(
                        stream
                                .filter(Files::isRegularFile)
                                .map(Path::toFile)
                                .filter(filter)
                                .toList()
                );
            } catch (IOException e) {
                throw new DMNRuntimeException(String.format("Error collecting files in '%s'", inputFile.getPath()), e);
            }
        }
    }
}
