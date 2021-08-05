/*
 Copyright 2016.
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
*/
package com.gs.dmn.validation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.serialization.DMNReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class BatchValidationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchValidationTest.class);

    private final DMNReader reader = new DMNReader(new Slf4jBuildLogger(LOGGER), false);
    private final DMNValidator validator = new RuleOverlapValidator();

    public void validateFolder(File rootFolder) throws IOException {
        if (rootFolder.exists()) {
            Files.find(Paths.get(rootFolder.getPath()), 999, (p, bfa) -> bfa.isRegularFile()).forEach(
                    p -> validate(p)
            );
        }

    }

    private void validate(Path path) {
        File file = new File(String.valueOf(path));
        if (file.getName().endsWith(".dmn")) {
            LOGGER.info("Validating {}", file.getPath());
            DMNModelRepository repository = new DMNModelRepository(reader.read(file));
            List<String> errors = validator.validate(repository);
            if (!errors.isEmpty()) {
                for (String error: errors) {
                    LOGGER.error(error);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File root = new File ("dmn-test-cases/signavio/dmn");

        new BatchValidationTest().validateFolder(root);
    }
}
