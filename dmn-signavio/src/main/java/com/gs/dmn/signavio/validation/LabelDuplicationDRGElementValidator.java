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
package com.gs.dmn.signavio.validation;

import com.gs.dmn.DMNModelRepository;
import org.omg.spec.dmn._20191111.model.TDefinitions;

import java.util.ArrayList;
import java.util.List;

public class LabelDuplicationDRGElementValidator extends LabelDuplicationValidator {
    @Override
    public List<String> validate(DMNModelRepository dmnModelRepository) {
        List<String> errors = new ArrayList<>();
        if (isEmpty(dmnModelRepository)) {
            logger.warn("DMN repository is empty; validator will not run");
            return errors;
        }

        for (TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            validateElements(dmnModelRepository.findDRGElements(definitions), errors);
        }

        return errors;
    }
}
