/**
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
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.serialization.DMNReader;
import com.gs.dmn.serialization.DMNValidator;
import com.gs.dmn.transformation.template.TemplateProvider;
import org.omg.spec.dmn._20151101.dmn.TDefinitions;

import java.io.File;
import java.util.Map;

public abstract class AbstractDMNTransformer extends AbstractTemplateBasedTransformer {
    protected final DMNReader dmnReader;
    protected final DMNValidator dmnValidator;
    protected final DMNTransformer dmnTransformer;

    protected final DMNDialectDefinition dialectDefinition;
    protected final String decisionBaseClass;
    protected final String javaRootPackage;
    protected final boolean lazyEvaluation;

    public AbstractDMNTransformer(DMNDialectDefinition dialectDefinition, DMNTransformer dmnTransformer, TemplateProvider templateProvider, Map<String, String> inputParameters, BuildLogger logger) {
        super(templateProvider, inputParameters, logger);
        this.dialectDefinition = dialectDefinition;
        this.dmnTransformer = dmnTransformer;
        boolean xsdValidation = InputParamUtil.getOptionalBooleanParam(inputParameters, "xsdValidation");
        this.dmnReader = new DMNReader(logger, xsdValidation);
        boolean semanticValidation = InputParamUtil.getOptionalBooleanParam(inputParameters, "semanticValidation");
        this.dmnValidator = this.dialectDefinition.createValidator(semanticValidation);

        this.javaRootPackage = InputParamUtil.getOptionalParam(inputParameters, "javaRootPackage");
        this.decisionBaseClass = dialectDefinition.getDecisionBaseClass();
        this.lazyEvaluation = InputParamUtil.getOptionalBooleanParam(inputParameters, "lazyEvaluation");
    }

    protected TDefinitions readDMN(File file) {
        return dmnReader.read(file);
    }
}
