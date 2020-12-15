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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.PrefixNamespaceMappings;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.DMNValidator;
import org.omg.spec.dmn._20191111.model.TDefinitions;

import java.io.File;
import java.util.List;

public abstract class AbstractDMNTransformer<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractTemplateBasedTransformer {
    protected final DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> dialectDefinition;
    protected final DMNReader dmnReader;
    protected final DMNValidator dmnValidator;
    protected final DMNTransformer<TEST> dmnTransformer;
    protected final LazyEvaluationDetector lazyEvaluationDetector;
    protected final TypeDeserializationConfigurer typeDeserializationConfigurer;

    protected final String decisionBaseClass;

    protected AbstractDMNTransformer(DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> dialectDefinition, DMNValidator dmnValidator, DMNTransformer<TEST> dmnTransformer, TemplateProvider templateProvider, LazyEvaluationDetector lazyEvaluationDetector, TypeDeserializationConfigurer typeDeserializationConfigurer, InputParameters inputParameters, BuildLogger logger) {
        super(templateProvider, inputParameters, logger);
        this.dialectDefinition = dialectDefinition;
        this.dmnTransformer = dmnTransformer;
        this.lazyEvaluationDetector = lazyEvaluationDetector;
        this.typeDeserializationConfigurer = typeDeserializationConfigurer;
        this.dmnReader = new DMNReader(logger, this.inputParameters.isXsdValidation());
        this.dmnValidator = dmnValidator;

        this.decisionBaseClass = dialectDefinition.getDecisionBaseClass();
    }

    protected DMNModelRepository readModels(File file) {
        List<Pair<TDefinitions, PrefixNamespaceMappings>> pairs = dmnReader.readModels(file);
        return new DMNModelRepository(pairs);
    }

    protected void handleValidationErrors(List<String> errors) {
        if (errors == null || errors.isEmpty()) {
            return;
        }

        for(String error: errors) {
            logger.error(error);
        }
        throw new IllegalArgumentException("Validation errors " + errors);
    }
}
