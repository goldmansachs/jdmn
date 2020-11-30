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
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import org.omg.spec.dmn._20191111.model.TDMNElement;
import org.omg.spec.dmn._20191111.model.TDefinitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SimpleDMNValidator implements DMNValidator {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SimpleDMNValidator.class);

    protected final BuildLogger logger;

    protected SimpleDMNValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    protected SimpleDMNValidator(BuildLogger logger) {
        this.logger = logger;
    }

    protected String makeError(DMNModelRepository dmnModelRepository, TDefinitions definitions, TDMNElement element, String message) {
        String location = dmnModelRepository.makeLocation(definitions, element);
        if (location == null) {
            return message;
        } else {
            return String.format("%s: error: %s", location, message);
        }
    }
}
