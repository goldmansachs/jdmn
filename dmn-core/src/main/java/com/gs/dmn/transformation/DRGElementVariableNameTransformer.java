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
import com.gs.dmn.ast.*;
import com.gs.dmn.ast.visitor.TraversalVisitor;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;

import java.util.List;

public class DRGElementVariableNameTransformer<T> extends SimpleDMNTransformer<T> {
    public DRGElementVariableNameTransformer() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public DRGElementVariableNameTransformer(BuildLogger logger) {
        super(logger);
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        if (isEmpty(repository)) {
            logger.warn("DMN repository is empty; transformer will not run");
            return repository;
        }

        this.transformRepository = false;
        return changeVariable(repository, logger);
    }

    @Override
    public Pair<DMNModelRepository, List<T>> transform(DMNModelRepository repository, List<T> testCasesList) {
        if (isEmpty(repository, testCasesList)) {
            logger.warn("DMN repository or test cases list is empty; transformer will not run");
            return new Pair<>(repository, testCasesList);
        }

        // Transform model
        if (this.transformRepository) {
            transform(repository);
        }

        return new Pair<>(repository, testCasesList);
    }

    private DMNModelRepository changeVariable(DMNModelRepository repository, BuildLogger logger) {
        logger.debug("Change variable name descriptions ...");
        DRGElementVariableNameVisitor dmnVisitor = new DRGElementVariableNameVisitor(this.logger, this.errorHandler);
        TransformationContext context = new TransformationContext(repository);
        for (TDefinitions definitions: repository.getAllDefinitions()) {
            definitions.accept(dmnVisitor, context);
        }

        return repository;
    }
}

class DRGElementVariableNameVisitor extends TraversalVisitor<TransformationContext> {
    public DRGElementVariableNameVisitor(BuildLogger logger, ErrorHandler errorHandler) {
        super(logger, errorHandler);
    }

    @Override
    public DMNBaseElement visit(TInputData element, TransformationContext context) {
        logger.debug(String.format("Process variable in input '%s'", element.getName()));

        DMNModelRepository repository = context.getRepository();
        changeVariableName(repository.variable(element), element);
        return element;
    }

    @Override
    public DMNBaseElement visit(TDecision element, TransformationContext context) {
        logger.debug(String.format("Process variable in decision '%s'", element));

        DMNModelRepository repository = context.getRepository();
        changeVariableName(repository.variable(element), element);
        return element;
    }

    @Override
    public DMNBaseElement visit(TBusinessKnowledgeModel element, TransformationContext context) {
        logger.debug(String.format("Process variable in BKM '%s'", element.getName()));

        DMNModelRepository repository = context.getRepository();
        changeVariableName(repository.variable(element), element);
        return element;
    }

    @Override
    public DMNBaseElement visit(TDecisionService element, TransformationContext context) {
        logger.debug(String.format("Process variable in DS '%s'", element.getName()));

        DMNModelRepository repository = context.getRepository();
        changeVariableName(repository.variable(element), element);
        return element;
    }

    private void changeVariableName(TInformationItem variable, TNamedElement element) {
        if (variable != null) {
            if (shouldCorrect(element, variable)) {
                String elementName = element.getName();
                String variableName = variable.getName();
                logger.debug(String.format("Change variable name from '%s' to '%s'", variableName, elementName));
                variable.setName(elementName);
            }
        }
    }

    private boolean shouldCorrect(TNamedElement element, TInformationItem variable) {
        if (element == null || element.getName() == null) {
            return false;
        }
        if (variable == null) {
            return false;
        }

        return !element.getName().equals(variable.getName());
    }
}
