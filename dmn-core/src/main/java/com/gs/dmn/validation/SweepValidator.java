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
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
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
        this.dmnDialectDefinition = new StandardDMNDialectDefinition();
        this.inputParameters = new InputParameters(makeInputParametersMap());
    }

    public SweepValidator(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition) {
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

        return makeErrorReport(dmnModelRepository);
    }

    private Map<String, String> makeInputParametersMap() {
        Map<String, String> inputParams = new LinkedHashMap<>();
        inputParams.put("dmnVersion", "1.1");
        inputParams.put("modelVersion", "2.0");
        inputParams.put("platformVersion", "1.0");
        return inputParams;
    }

    public List<String> makeErrorReport(DMNModelRepository dmnModelRepository) {
        List<String> errorReport = new ArrayList<>();
        BasicDMNToJavaTransformer dmnTransformer = this.dmnDialectDefinition.createBasicTransformer(dmnModelRepository, new NopLazyEvaluationDetector(), this.inputParameters);
        for (TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            List<TDRGElement> drgElements = dmnModelRepository.findDRGElements(definitions);
            for (TDRGElement element: drgElements) {
                if (element instanceof TDecision) {
                    TExpression expression = ((TDecision) element).getExpression();
                    if (expression instanceof TDecisionTable && ((TDecisionTable) expression).getHitPolicy() == THitPolicy.UNIQUE) {
                        validate(element, (TDecisionTable) expression, dmnTransformer, dmnModelRepository, errorReport);
                    }
                }
            }
        }
        return errorReport;
    }

    protected abstract void validate(TDRGElement element, TDecisionTable decisionTable, BasicDMNToJavaTransformer transformer, DMNModelRepository repository, List<String> errorReport);

    protected List<Bound> makeBoundList(List<Integer> ruleList, int columnIndex, Table table) {
        BoundList boundList = new BoundList(ruleList, columnIndex, table);
        boundList.sort();
        return boundList.getBounds();
    }
}
