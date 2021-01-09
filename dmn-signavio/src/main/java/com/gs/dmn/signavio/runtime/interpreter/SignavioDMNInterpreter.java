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
package com.gs.dmn.signavio.runtime.interpreter;

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.interpreter.TypeConverter;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.interpreter.Result;
import com.gs.dmn.runtime.interpreter.StandardDMNInterpreter;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.runtime.listener.DRGElement;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.extension.Aggregator;
import com.gs.dmn.signavio.extension.MultiInstanceDecisionLogic;
import com.gs.dmn.signavio.transformation.basic.BasicSignavioDMNToJavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.omg.spec.dmn._20191111.model.TDRGElement;
import org.omg.spec.dmn._20191111.model.TDecision;
import org.omg.spec.dmn._20191111.model.TExpression;

import java.util.ArrayList;
import java.util.List;

public class SignavioDMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends StandardDMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    private final SignavioDMNModelRepository dmnModelRepository;

    public SignavioDMNInterpreter(BasicDMNToNativeTransformer dmnTransformer, FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib, TypeConverter typeConverter) {
        super(dmnTransformer, feelLib, typeConverter);
        this.dmnModelRepository = (SignavioDMNModelRepository) this.getBasicDMNTransformer().getDMNModelRepository();
    }

    @Override
    protected Result evaluateExpression(TDRGElement element, TExpression expression, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation) {
        if (element instanceof TDecision && this.dmnModelRepository.isMultiInstanceDecision(element)) {
            return evaluateMultipleInstanceDecision((TDecision) element, environment, runtimeEnvironment, elementAnnotation);
        } else {
            return super.evaluateExpression(element, expression, environment, runtimeEnvironment, elementAnnotation);
        }
    }

    private Result evaluateMultipleInstanceDecision(TDecision decision, Environment environment, RuntimeEnvironment runtimeEnvironment, DRGElement elementAnnotation) {
        // Multi instance attributes
        MultiInstanceDecisionLogic multiInstanceDecision = ((BasicSignavioDMNToJavaTransformer) getBasicDMNTransformer()).multiInstanceDecisionLogic(decision);
        String iterationExpression = multiInstanceDecision.getIterationExpression();
        TDRGElement iterator = multiInstanceDecision.getIterator();
        Aggregator aggregator = multiInstanceDecision.getAggregator();
        TDecision topLevelDecision = multiInstanceDecision.getTopLevelDecision();
        String lambdaParamName = getBasicDMNTransformer().namedElementVariableName(iterator);
        String topLevelVariableName = getBasicDMNTransformer().namedElementVariableName(topLevelDecision);

        // Evaluate source
        Result result = evaluateLiteralExpression(decision, iterationExpression, environment, runtimeEnvironment);
        List sourceList = (List) Result.value(result);

        // Iterate over source
        List outputList = new ArrayList<>();
        RuntimeEnvironment newRuntimeEnvironment = RuntimeEnvironment.of(runtimeEnvironment);
        for (Object obj : sourceList) {
            newRuntimeEnvironment.bind(lambdaParamName, obj);
            applyDecision(this.dmnModelRepository.makeDRGElementReference(topLevelDecision), newRuntimeEnvironment);
            outputList.add(newRuntimeEnvironment.lookupBinding(topLevelVariableName));
        }

        // Aggregate
        Object output;
        if (aggregator == Aggregator.COLLECT) {
            output = outputList;
        } else {
            FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib = getFeelLib();
            if (aggregator == Aggregator.SUM) {
                output = feelLib.sum(outputList);
            } else if (aggregator == Aggregator.MIN) {
                output = feelLib.min(outputList);
            } else if (aggregator == Aggregator.MAX) {
                output = feelLib.max(outputList);
            } else if (aggregator == Aggregator.COUNT) {
                output = feelLib.count(outputList);
            } else if (aggregator == Aggregator.ALLTRUE) {
                output = outputList.stream().allMatch(o -> o == Boolean.TRUE);
            } else if (aggregator == Aggregator.ANYTRUE) {
                output = outputList.stream().anyMatch(o -> o == Boolean.TRUE);
            } else if (aggregator == Aggregator.ALLFALSE) {
                output = outputList.stream().anyMatch(o -> o == Boolean.FALSE);
            } else {
                this.errorHandler.reportError(String.format("'%s' is not implemented yet", aggregator));
                output = null;
            }
        }
        return Result.of(output, getBasicDMNTransformer().drgElementOutputFEELType(decision));
    }

    @Override
    protected boolean dagOptimisation() {
        return false;
    }
}
