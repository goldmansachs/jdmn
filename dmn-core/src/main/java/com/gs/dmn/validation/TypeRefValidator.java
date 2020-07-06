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
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.FEELTypes;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import com.gs.dmn.transformation.basic.DMNEnvironmentFactory;
import com.gs.dmn.transformation.basic.QualifiedName;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class TypeRefValidator extends SimpleDMNValidator {
    private final StandardDMNDialectDefinition dmnDialectDefinition;

    public TypeRefValidator() {
        super(new Slf4jBuildLogger(LOGGER));
        this.dmnDialectDefinition = new StandardDMNDialectDefinition();
    }

    public TypeRefValidator(BuildLogger logger) {
        super(logger);
        this.dmnDialectDefinition = new StandardDMNDialectDefinition();
    }

    @Override
    public List<String> validate(DMNModelRepository dmnModelRepository) {
        List<String> errors = new ArrayList<>();

        if (dmnModelRepository == null) {
            throw new IllegalArgumentException("Missing definitions");
        }

        BasicDMN2JavaTransformer dmnTransformer = this.dmnDialectDefinition.createBasicTransformer(dmnModelRepository, new NopLazyEvaluationDetector(), new LinkedHashMap<>());
        for (TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            List<TDRGElement> drgElements = dmnModelRepository.findDRGElements(definitions);
            for (TDRGElement element: drgElements) {
                logger.debug(String.format("Validate element '%s'", element.getName()));
                if (element instanceof TInputData) {
                    TInformationItem variable = ((TInputData) element).getVariable();
                    validate(element, variable, dmnTransformer, dmnModelRepository, errors);
                } else if (element instanceof TDecision) {
                    TInformationItem variable = ((TDecision) element).getVariable();
                    validate(element, variable, dmnTransformer, dmnModelRepository, errors);
                }
            }
        }

        return errors;
    }

    private void validate(TDRGElement element, TInformationItem variable, BasicDMN2JavaTransformer dmnTransformer, DMNModelRepository dmnModelRepository, List<String> errors) {
        TDefinitions model = dmnModelRepository.getModel(element);
        DMNEnvironmentFactory dmnEnvironmentFactory = dmnTransformer.getDMNEnvironmentFactory();
        if (variable != null) {
            String varTypeRef = variable.getTypeRef();
            if (!isPrimitiveType(varTypeRef) && StringUtils.isNotEmpty(varTypeRef)) {
                QualifiedName typeRef = QualifiedName.toQualifiedName(model, varTypeRef);
                TItemDefinition itemDefinition = dmnModelRepository.lookupItemDefinition(model, typeRef);
                if (itemDefinition == null) {
                    Type type = inferType(element, dmnTransformer, dmnModelRepository, dmnEnvironmentFactory);
                    String hint = type == null ? "" : String.format(". The inferred type is '%s'", type.toString());
                    String error = makeError(element, String.format("Cannot find typeRef '%s'", typeRef.toString()) + hint);
                    errors.add(error);
                }
            }
        }
    }

    private Type inferType(TDRGElement element, BasicDMN2JavaTransformer dmnTransformer, DMNModelRepository dmnModelRepository, DMNEnvironmentFactory dmnEnvironmentFactory) {
        Type type = null;
        try {
            QualifiedName typeRef = null;
            TDefinitions model = dmnModelRepository.getModel(element);
            if (element instanceof TDecision) {
                typeRef = dmnModelRepository.inferExpressionTypeRef(model, element);
            }
            if (typeRef != null) {
                type = dmnEnvironmentFactory.toFEELType(model, typeRef);
            } else {
                TExpression expression = dmnModelRepository.expression(element);
                Environment environment = dmnEnvironmentFactory.makeEnvironment(element, dmnTransformer.getEnvironmentFactory().getRootEnvironment(), false);
                type = dmnEnvironmentFactory.expressionType(element, expression, environment);
            }
        } catch (Exception e) {
        }
        return type;
    }

    private boolean isPrimitiveType(String name) {
        return FEELTypes.FEEL_TYPE_NAMES.contains(name) || name.startsWith(DMNVersion.DMN_12.getFeelPrefix());
    }
}
