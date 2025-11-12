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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.feel.analysis.semantics.type.DataType;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.dialect.JavaTimeSignavioDMNDialectDefinition;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.basic.DMNEnvironmentFactory;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.validation.TypeRefValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InferMissingItemDefinitionsTransformer extends AbstractMissingItemDefinitionsTransformer {
    static final String DMN_DIALECT_NAME = "dmnDialect";

    private TypeRefValidator typeRefValidator;
    private DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialect;

    public InferMissingItemDefinitionsTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public InferMissingItemDefinitionsTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public void configure(Map<String, Object> configuration) {
        parseConfigurationForDialect(configuration);
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; transformer will not run");
            return repository;
        }

        inferAndAddMissingDefinitions(repository);
        return repository;
    }

    private void inferAndAddMissingDefinitions(DMNModelRepository repository) {
        List<TNamedElement> resolvedElements = new ArrayList<>();
        int idSequence = 0;

        int iteration = 0;
        List<TItemDefinition> itemDefinitionsToAdd = new ArrayList<>();
        do {
            logger.debug(String.format("Iteration: %d", iteration));

            itemDefinitionsToAdd.clear();
            List<Pair<TNamedElement, SemanticError>> errorReport = typeRefValidator.makeErrorReport(repository);
            List<TNamedElement> namedElements = errorReport.stream().map(Pair::getLeft).collect(Collectors.toList());
            List<Pair<TNamedElement, Type>> enhancedReport = inferTypes(namedElements, repository);
            for (Pair<TNamedElement, Type> pair : enhancedReport) {
                TNamedElement element = pair.getLeft();
                Type type = pair.getRight();
                if (com.gs.dmn.el.analysis.semantics.type.Type.isNull(type)) {
                } else if (isPrimitive(type) || isListOfPrimitive(type)) {
                    if (!resolvedElements.contains(element)) {
                        // Create ItemDefinition and add it
                        String name = QualifiedName.toName(repository.variable(element).getTypeRef());
                        boolean isCollection = type instanceof ListType;
                        String typeRef = getTypeRef(type);
                        TItemDefinition itemDefinition = makeItemDefinition(idSequence, name, isCollection, typeRef);
                        idSequence++;
                        itemDefinitionsToAdd.add(itemDefinition);

                        // Mark element as resolved
                        resolvedElements.add(element);
                    }
                } else {
                    throw new DMNRuntimeException(String.format("Cannot infer type for '%s'. '%s' is not supported yet", element.getName(), type));
                }
            }
            // Add new ItemDefinitions and try again
            this.addNewDefinitions(repository, itemDefinitionsToAdd);

            iteration++;
        } while (!itemDefinitionsToAdd.isEmpty());
    }

    private List<Pair<TNamedElement, Type>> inferTypes(List<TNamedElement> errorReport, DMNModelRepository repository) {
        List<Pair<TNamedElement, Type>> enhancedReport = new ArrayList<>();
        BasicDMNToJavaTransformer dmnTransformer = this.dmnDialect.createBasicTransformer(repository, new NopLazyEvaluationDetector(), new InputParameters());
        DMNEnvironmentFactory dmnEnvironmentFactory = dmnTransformer.getDMNEnvironmentFactory();
        for (TNamedElement element : errorReport) {
            if (element instanceof TDRGElement) {
                Type type = inferType((TDRGElement) element, repository, dmnTransformer, dmnEnvironmentFactory);
                enhancedReport.add(new Pair<>(element, type));

                logger.debug(String.format("Inferred type of '%s' is '%s'", element.getName(), type));
            }
        }

        return enhancedReport;
    }

    private boolean isPrimitive(Type type) {
        return type instanceof DataType;
    }

    private boolean isListOfPrimitive(Type type) {
        return type instanceof ListType && isPrimitive(((ListType) type).getElementType());
    }

    private String getTypeRef(Type type) {
        if (isPrimitive(type)) {
            return ((DataType) type).getName();
        } else if (isListOfPrimitive(type)) {
            return getTypeRef(((ListType) type).getElementType());
        } else {
            throw new DMNRuntimeException(String.format("'%s' is not supported yet", type));
        }
    }

    private void parseConfigurationForDialect(Map<String, Object> configuration) {
        String dialectClassName = JavaTimeSignavioDMNDialectDefinition.class.getName();
        if (configuration != null && configuration.size() != 0) {
            Object dialectNode = configuration.get(DMN_DIALECT_NAME);
            if (dialectNode == null || configuration.values().size() != 1) {
                reportInvalidConfig(String.format("Configuration does not have expected structure (expecting only '%s' node)", DMN_DIALECT_NAME));
            } else if (dialectNode instanceof String) {
                dialectClassName = (String) dialectNode;
            } else {
                reportInvalidConfig(String.format("'%s' should be a string", DMN_DIALECT_NAME));
            }
        }
        try {
            Object object = Class.forName(dialectClassName).getDeclaredConstructor().newInstance();
            if (object instanceof DMNDialectDefinition) {
                this.dmnDialect = (DMNDialectDefinition) object;
                this.typeRefValidator = new TypeRefValidator();
            } else {
                reportInvalidConfig(String.format("Incorrect DMN dialect name '%s'", dialectClassName));
            }
        } catch (Exception e) {
            reportInvalidConfig(String.format("Incorrect DMN dialect name '%s'", dialectClassName));
        }
    }

    private Type inferType(TDRGElement element, DMNModelRepository repository, BasicDMNToJavaTransformer dmnTransformer, DMNEnvironmentFactory dmnEnvironmentFactory) {
        Type type = null;
        try {
            QualifiedName typeRef = null;
            TDefinitions model = repository.getModel(element);
            if (element instanceof TDecision) {
                typeRef = repository.inferExpressionTypeRef(model, element);
            }
            if (!repository.isNull(typeRef)) {
                type = dmnEnvironmentFactory.toFEELType(model, typeRef);
            } else {
                TExpression expression = repository.expression(element);
                DMNContext globalContext = dmnTransformer.makeGlobalContext(element);
                type = dmnEnvironmentFactory.expressionType(element, expression, globalContext);
            }
        } catch (Exception e) {
            logger.warn(String.format("Cannot infer type for element '%s'", element.getName()));
        }
        return type;
    }
}
