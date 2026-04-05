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

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.error.SemanticErrorException;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.tck.TestCasesToNativeTransformer;
import com.gs.dmn.tck.validation.ValidationError;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.TestValidator;

import java.util.List;

public abstract class AbstractTestCasesToJUnitTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractDMNTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> implements TestCasesToNativeTransformer {
    protected TestValidator<TEST> testeCasesValidator;

    protected AbstractTestCasesToJUnitTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> dialectDefinition, DMNValidator dmnValidator, TestValidator<TEST> testeCasesValidator, DMNTransformer<TEST> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputParameters inputParameters, BuildLogger logger) {
        super(dialectDefinition, dmnValidator, dmnTransformer, templateProvider, lazyEvaluationDetector, typeDeserializationConfigurer, inputParameters, logger);
        this.testeCasesValidator = testeCasesValidator;
    }

    protected void handleTestErrors(List<ValidationError> errors) {
        if (errors == null || errors.isEmpty()) {
            return;
        }

        for (com.gs.dmn.tck.validation.ValidationError error : errors) {
            this.logger.error(error.toText());
        }
        throw new SemanticErrorException("Validation errors " + errors);
    }

    @Override
    protected abstract String getFileExtension();
}
