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
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.*;
import com.gs.dmn.ast.visitor.TraversalVisitor;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.feel.analysis.semantics.type.FEELTypes;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.basic.DMNEnvironmentFactory;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TypeRefValidator extends SimpleDMNValidator {
    private final DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition;
    private final InputParameters inputParameters;

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
            this.logger.warn("DMN repository is empty; validator will not run");
            return new ArrayList<>();
        }

        List<Pair<TDRGElement, Type>> errorReport = makeErrorReport(dmnModelRepository);
        return errorReport.stream().map(p -> makeError(p, dmnModelRepository)).collect(Collectors.toList());
    }

    public List<Pair<TDRGElement, Type>> makeErrorReport(DMNModelRepository dmnModelRepository) {
        List<Pair<TDRGElement, Type>> errorReport = new ArrayList<>();
        BasicDMNToJavaTransformer dmnTransformer = this.dmnDialectDefinition.createBasicTransformer(dmnModelRepository, new NopLazyEvaluationDetector(), this.inputParameters);
        TypeRefVisitorContext context = new TypeRefVisitorContext(dmnModelRepository, dmnTransformer, errorReport);
        TypeRefValidatorVisitor visitor = new TypeRefValidatorVisitor(this.errorHandler, this.logger);
        for (TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            definitions.accept(visitor, context);
        }
        return errorReport;
    }

    private String makeError(Pair<TDRGElement, Type> pair, DMNModelRepository repository) {
        TDRGElement element = pair.getLeft();
        TDefinitions model = repository.getModel(element);
        Type type = pair.getRight();
        TInformationItem variable = repository.variable(element);
        QualifiedName typeRef = QualifiedName.toQualifiedName(model, variable.getTypeRef());

        String hint = com.gs.dmn.el.analysis.semantics.type.Type.isNull(type) ? "" : String.format(". The inferred type is '%s'", type);
        return makeError(repository, model, element, String.format("Cannot find typeRef '%s'", typeRef.toString()) + hint);
    }

    private Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("dmnVersion", "1.1");
        inputParams.put("modelVersion", "2.0");
        inputParams.put("platformVersion", "1.0");
        return inputParams;
    }
}

class TypeRefValidatorVisitor extends TraversalVisitor<TypeRefVisitorContext> {
    private final BuildLogger logger;

    public TypeRefValidatorVisitor(ErrorHandler errorHandler, BuildLogger logger) {
        super(errorHandler);
        this.logger = logger;
    }

    @Override
    public DMNBaseElement visit(TInputData element, TypeRefVisitorContext context) {
        if (element != null) {
            TInformationItem variable = element.getVariable();
            validate(element, variable, context);
        }

        return element;
    }

    @Override
    public DMNBaseElement visit(TDecision element, TypeRefVisitorContext context) {
        if (element != null) {
            TInformationItem variable = element.getVariable();
            validate(element, variable, context);
        }

        return element;
    }

    private void validate(TDRGElement element, TInformationItem variable, TypeRefVisitorContext context) {
        logger.debug(String.format("Validating element '%s'", element.getName()));

        DMNModelRepository dmnModelRepository = context.getRepository();
        BasicDMNToJavaTransformer dmnTransformer = context.getDmnTransformer();
        List<Pair<TDRGElement, Type>> errorReport = context.getErrorReport();

        TDefinitions model = dmnModelRepository.getModel(element);
        DMNEnvironmentFactory dmnEnvironmentFactory = dmnTransformer.getDMNEnvironmentFactory();
        if (variable != null) {
            String varTypeRef = QualifiedName.toName(variable.getTypeRef());
            if (!isPrimitiveType(varTypeRef) && StringUtils.isNotEmpty(varTypeRef)) {
                QualifiedName typeRef = QualifiedName.toQualifiedName(model, varTypeRef);
                TItemDefinition itemDefinition = dmnModelRepository.lookupItemDefinition(model, typeRef);
                if (itemDefinition == null) {
                    Type type = inferType(element, dmnTransformer, dmnModelRepository, dmnEnvironmentFactory);
                    errorReport.add(new Pair<>(element, type));

                    this.logger.debug(String.format("Missing typeRef '%s'", typeRef));
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
            if (!dmnModelRepository.isNull(typeRef)) {
                type = dmnEnvironmentFactory.toFEELType(model, typeRef);
            } else {
                TExpression expression = dmnModelRepository.expression(element);
                DMNContext globalContext = dmnTransformer.makeGlobalContext(element, false);
                type = dmnEnvironmentFactory.expressionType(element, expression, globalContext);
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

class TypeRefVisitorContext {
    private final DMNModelRepository repository;
    private final BasicDMNToJavaTransformer dmnTransformer;
    private final List<Pair<TDRGElement, Type>> errorReport;

    public TypeRefVisitorContext(DMNModelRepository repository, BasicDMNToJavaTransformer dmnTransformer, List<Pair<TDRGElement, Type>> errorReport) {
        this.repository = repository;
        this.dmnTransformer = dmnTransformer;
        this.errorReport = errorReport;
    }

    public DMNModelRepository getRepository() {
        return repository;
    }

    public BasicDMNToJavaTransformer getDmnTransformer() {
        return dmnTransformer;
    }

    public List<Pair<TDRGElement, Type>> getErrorReport() {
        return errorReport;
    }
}