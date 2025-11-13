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
package com.gs.dmn.validation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.error.ValidationError;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.serialization.DMNSerializer;
import com.gs.dmn.serialization.xstream.XMLDMNSerializer;
import com.gs.dmn.transformation.InputParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class BatchValidationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchValidationTest.class);

    private final DMNSerializer serializer = new XMLDMNSerializer(new Slf4jBuildLogger(LOGGER), new InputParameters());
    private final DMNValidator validator = new CompositeDMNValidator(Arrays.asList(
            new SweepRuleOverlapValidator(),
            new SweepMissingIntervalValidator(),
            new SweepMissingRuleValidator(),
            new SweepMissingRuleValidator(new Slf4jBuildLogger(LOGGER), true)
    ));

    public void validateFolder(File rootFolder) throws IOException {
        if (rootFolder.exists()) {
            try (Stream<Path> pathStream = Files.find(Paths.get(rootFolder.getPath()), 999, (p, bfa) -> bfa.isRegularFile())) {
                pathStream.forEach(this::validate);
            }
        }
    }

    private void validate(Path path) {
        File file = new File(String.valueOf(path));
        if (file.getName().endsWith(".dmn")) {
            LOGGER.info("Validating {}", file.getPath());
            DMNModelRepository repository = new DMNModelRepository(this.serializer.readModel(file));
            List<ValidationError> errors = this.validator.validate(repository);
            if (!errors.isEmpty()) {
                for (ValidationError error : errors) {
                    LOGGER.error(error.toText());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File root = new File("dmn-test-cases/signavio/dmn");

        new BatchValidationTest().validateFolder(root);
    }
}
