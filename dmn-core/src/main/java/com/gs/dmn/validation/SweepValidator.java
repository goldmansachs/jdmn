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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.*;
import com.gs.dmn.ast.visitor.TraversalVisitor;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.synthesis.ELTranslator;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.error.ValidationError;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.validation.table.Bound;
import com.gs.dmn.validation.table.BoundList;
import com.gs.dmn.validation.table.Table;
import com.gs.dmn.validation.table.TableFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class SweepValidator extends SimpleDMNValidator {
    protected final DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition;
    protected final InputParameters inputParameters;
    protected final TableFactory factory = new TableFactory();

    public SweepValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public SweepValidator(BuildLogger logger) {
        super(logger);
        this.dmnDialectDefinition = new JavaTimeDMNDialectDefinition();
        this.inputParameters = new InputParameters(makeInputParametersMap());
    }

    public SweepValidator(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition) {
        super(new Slf4jBuildLogger(LOGGER));
        this.dmnDialectDefinition = dmnDialectDefinition;
        this.inputParameters = new InputParameters(makeInputParametersMap());
    }

    @Override
    public List<ValidationError> validate(DMNModelRepository dmnModelRepository) {
        if (isEmpty(dmnModelRepository)) {
            this.logger.warn("DMN repository is empty; validator will not run");
            return new ArrayList<>();
        }

        ELTranslator<Type, DMNContext> feelTranslator = this.dmnDialectDefinition.createFEELTranslator(dmnModelRepository, this.inputParameters);
        SweepValidationContext context = new SweepValidationContext(dmnModelRepository, feelTranslator);
        SweepValidatorVisitor visitor = new SweepValidatorVisitor(this.logger, this.errorHandler, this);
        for (TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            definitions.accept(visitor, context);
        }
        return context.getErrors();
    }

    private Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("dmnVersion", "1.1");
        inputParams.put("modelVersion", "2.0");
        inputParams.put("platformVersion", "1.0");
        return inputParams;
    }

    protected List<Bound> makeBoundList(List<Integer> ruleList, int columnIndex, Table table) {
        BoundList boundList = new BoundList(ruleList, columnIndex, table);
        boundList.sort();
        return boundList.getBounds();
    }

    protected abstract void validate(TDRGElement element, TDecisionTable decisionTable, SweepValidationContext context);
}

class SweepValidatorVisitor extends TraversalVisitor<SweepValidationContext> {
    private final SweepValidator validator;

    public SweepValidatorVisitor(BuildLogger logger, ErrorHandler errorHandler, SweepValidator validator) {
        super(logger, errorHandler);
        this.validator = validator;
    }

    @Override
    public DMNBaseElement visit(TDecision element, SweepValidationContext context) {
        if (element != null) {
            this.logger.debug(String.format("Validating element '%s'", element.getName()));
            TExpression expression = element.getExpression();
            if (expression instanceof TDecisionTable && ((TDecisionTable) expression).getHitPolicy() == THitPolicy.UNIQUE) {
                this.validator.validate(element, (TDecisionTable) expression, context);
            }

        }

        return element;
    }
}