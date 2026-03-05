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
package com.gs.dmn.tck.validation;

import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.error.LogErrorHandler;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.tck.ast.TCKBaseElement;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.error.ErrorFactory;
import com.gs.dmn.tck.error.TCKError;
import com.gs.dmn.tck.error.TestLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SimpleTCKValidator implements TCKValidator {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SimpleTCKValidator.class);

    protected final BuildLogger logger;
    protected final ErrorHandler errorHandler = new LogErrorHandler(LOGGER);

    protected SimpleTCKValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    protected SimpleTCKValidator(BuildLogger logger) {
        this.logger = logger;
    }

    protected void addValidationError(ValidationContext context, TCKBaseElement element, String errorMessage) {
        TestCases testCases = context.getTestCases();
        TCKError error = ErrorFactory.makeTCKError(new TestLocation(testCases, element), errorMessage);
        context.addError(new ValidationError(error, this.ruleName()));
    }
}
