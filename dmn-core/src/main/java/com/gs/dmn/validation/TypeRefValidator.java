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
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.FEELTypes;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.basic.DMNEnvironmentFactory;
import com.gs.dmn.transformation.basic.QualifiedName;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TypeRefValidator extends SimpleDMNValidator {
    private final DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition;
    private InputParameters inputParameters;

    public TypeRefValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public TypeRefValidator(BuildLogger logger) {
        super(logger);
        this.dmnDialectDefinition = new StandardDMNDialectDefinition();
        this.inputParameters = new InputParameters(makeInputParametersMap());
    }

    public TypeRefValidator(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition) {
        super(new Slf4jBuildLogger(LOGGER));
        this.dmnDialectDefinition = dmnDialectDefinition;
        this.inputParameters = new InputParameters(makeInputParametersMap());
    }

    @Override
    public List<String> validate(DMNModelRepository dmnModelRepository) {
        if (isEmpty(dmnModelRepository)) {
            logger.warn("DMN repository is empty; validator will not run");
            return new ArrayList<>();
        }

        List<Pair<TDRGElement, Type>> errorReport = makeErrorReport(dmnModelRepository);
        return errorReport.stream().map(p -> makeError(p, dmnModelRepository)).collect(Collectors.toList());
    }

    private Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("dmnVersion", "1.1");
        inputParams.put("modelVersion", "2.0");
        inputParams.put("platformVersion", "1.0");
        return inputParams;
    }

    private String makeError(Pair<TDRGElement, Type> pair, DMNModelRepository repository) {
        TDRGElement element = pair.getLeft();
        TDefinitions model = repository.getModel(element);
        Type type = pair.getRight();
        TInformationItem variable = repository.variable(element);
        QualifiedName typeRef = QualifiedName.toQualifiedName(model, variable.getTypeRef());

        String hint = type == null ? "" : String.format(". The inferred type is '%s'", type.toString());
        return makeError(repository, model, element, String.format("Cannot find typeRef '%s'", typeRef.toString()) + hint);
    }

    public List<Pair<TDRGElement, Type>> makeErrorReport(DMNModelRepository dmnModelRepository) {
        List<Pair<TDRGElement, Type>> errorReport = new ArrayList<>();
        BasicDMNToJavaTransformer dmnTransformer = this.dmnDialectDefinition.createBasicTransformer(dmnModelRepository, new NopLazyEvaluationDetector(), this.inputParameters);
        for (TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            List<TDRGElement> drgElements = dmnModelRepository.findDRGElements(definitions);
            for (TDRGElement element: drgElements) {
                if (element instanceof TInputData) {
                    TInformationItem variable = ((TInputData) element).getVariable();
                    validate(element, variable, dmnTransformer, dmnModelRepository, errorReport);
                } else if (element instanceof TDecision) {
                    TInformationItem variable = ((TDecision) element).getVariable();
                    validate(element, variable, dmnTransformer, dmnModelRepository, errorReport);
                }
            }
        }
        return errorReport;
    }

    private void validate(TDRGElement element, TInformationItem variable, BasicDMNToJavaTransformer dmnTransformer, DMNModelRepository dmnModelRepository, List<Pair<TDRGElement, Type>> errorReport) {
        logger.debug(String.format("Validate element '%s'", element.getName()));

        TDefinitions model = dmnModelRepository.getModel(element);
        DMNEnvironmentFactory dmnEnvironmentFactory = dmnTransformer.getDMNEnvironmentFactory();
        if (variable != null) {
            String varTypeRef = variable.getTypeRef();
            if (!isPrimitiveType(varTypeRef) && StringUtils.isNotEmpty(varTypeRef)) {
                QualifiedName typeRef = QualifiedName.toQualifiedName(model, varTypeRef);
                TItemDefinition itemDefinition = dmnModelRepository.lookupItemDefinition(model, typeRef);
                if (itemDefinition == null) {
                    Type type = inferType(element, dmnTransformer, dmnModelRepository, dmnEnvironmentFactory);
                    errorReport.add(new Pair<>(element, type));

                    logger.debug(String.format("Missing typeRef '%s'", typeRef));
                }
            }
        }
    }

    private Type inferType(TDRGElement element, BasicDMNToJavaTransformer dmnTransformer, DMNModelRepository dmnModelRepository, DMNEnvironmentFactory dmnEnvironmentFactory) {
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
            logger.warn(String.format("Cannot infer type for element '%s'", element.getName()));
        }
        return type;
    }

    private boolean isPrimitiveType(String name) {
        return FEELTypes.FEEL_TYPE_NAMES.contains(name) || name.startsWith(DMNVersion.DMN_12.getFeelPrefix());
    }
}
