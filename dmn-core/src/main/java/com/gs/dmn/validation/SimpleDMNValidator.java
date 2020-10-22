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

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.TDMNElement;
import org.omg.spec.dmn._20180521.model.TDefinitions;
import org.omg.spec.dmn._20180521.model.TNamedElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleDMNValidator implements DMNValidator {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SimpleDMNValidator.class);

    protected final BuildLogger logger;

    protected SimpleDMNValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    protected SimpleDMNValidator(BuildLogger logger) {
        this.logger = logger;
    }

    protected String makeError(TDefinitions definitions, TDMNElement element, String message) {
        String location = makeLocation(definitions, element);
        if (location == null) {
            return message;
        } else {
            return String.format("%s: error: %s", location, message);
        }
    }

    private String makeLocation(TDefinitions definitions, TDMNElement element) {
        if (definitions == null && element == null) {
            return null;
        }

        List<String> locationParts = new ArrayList<>();
        if (definitions != null) {
            if (!StringUtils.isBlank(definitions.getName())) {
                locationParts.add(String.format("model='%s'", definitions.getName()));
            }
        }
        if (element != null) {
            String id = element.getId();
            String label = element.getLabel();
            String name = element instanceof TNamedElement ? ((TNamedElement) element).getName() : null;
            if (!StringUtils.isBlank(label)) {
                locationParts.add(String.format("label='%s'", label));
            }
            if (!StringUtils.isBlank(name)) {
                locationParts.add(String.format("name='%s'", name));
            }
            if (!StringUtils.isBlank(id)) {
                locationParts.add(String.format("id='%s'", id));
            }
        }
        return locationParts.isEmpty() ? null : String.format("(%s)", String.join(", ", locationParts));
    }
}
