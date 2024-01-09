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

import com.gs.dmn.DRGElementReference;
import com.gs.dmn.NameUtils;
import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.feel.interpreter.SignavioFEELInterpreter;
import com.gs.dmn.feel.interpreter.StandardFEELInterpreter;
import com.gs.dmn.feel.interpreter.TypeConverter;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.interpreter.AbstractDMNInterpreter;
import com.gs.dmn.runtime.interpreter.EvaluationContext;
import com.gs.dmn.runtime.interpreter.ExpressionEvaluationContext;
import com.gs.dmn.runtime.interpreter.Result;
import com.gs.dmn.runtime.listener.DRGElement;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.extension.Aggregator;
import com.gs.dmn.signavio.extension.MultiInstanceDecisionLogic;
import com.gs.dmn.signavio.feel.lib.SignavioLib;
import com.gs.dmn.signavio.transformation.basic.BasicSignavioDMNToJavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SignavioDMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    private final SignavioDMNModelRepository dmnModelRepository;

    public SignavioDMNInterpreter(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib) {
        super(dmnTransformer, feelLib);
        this.dmnModelRepository = (SignavioDMNModelRepository) this.getBasicDMNTransformer().getDMNModelRepository();
        this.visitor = new SignavioInterpreterVisitor(this.errorHandler);
        this.elInterpreter = new SignavioFEELInterpreter<>(this);
        this.typeConverter = new SignavioTypeConverter(dmnTransformer, this.getElInterpreter(), feelLib);
    }

    @Override
    public SignavioLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> getFeelLib() {
        return (SignavioLib<NUMBER, DATE, TIME, DATE_TIME, DURATION>) this.feelLib;
    }

    @Override
    protected boolean dagOptimisation() {
        return false;
    }

    protected class SignavioInterpreterVisitor extends InterpreterVisitor {
        public SignavioInterpreterVisitor(ErrorHandler errorHandler) {
            super(errorHandler);
        }

        @Override
        public Result visit(TExpression expression, EvaluationContext evaluationContext) {
            ExpressionEvaluationContext expressionContext = (ExpressionEvaluationContext) evaluationContext;
            TDRGElement element = expressionContext.getElement();
            DMNContext context = expressionContext.getContext();
            if (element instanceof TDecision && dmnModelRepository.isMultiInstanceDecision(element)) {
                return evaluateMultipleInstanceDecision((TDecision) element, context);
            } else {
                return super.visit(expression, evaluationContext);
            }
        }

        private Result evaluateMultipleInstanceDecision(TDecision decision, DMNContext parentContext) {
            // Multi instance attributes
            MultiInstanceDecisionLogic multiInstanceDecision = ((BasicSignavioDMNToJavaTransformer) getBasicDMNTransformer()).multiInstanceDecisionLogic(decision);
            String iterationExpression = multiInstanceDecision.getIterationExpression();
            TDRGElement iterator = multiInstanceDecision.getIterator();
            Aggregator aggregator = multiInstanceDecision.getAggregator();
            TDecision topLevelDecision = multiInstanceDecision.getTopLevelDecision();
            String lambdaParamName = getBasicDMNTransformer().namedElementVariableName(iterator);

            // Evaluate source
            Result result = evaluateLiteralExpression(iterationExpression, parentContext);
            List sourceList = (List) Result.value(result);

            // Iterate over source
            List outputList = new ArrayList<>();
            DMNContext loopContext = getBasicDMNTransformer().makeGlobalContext(decision, parentContext);
            for (Object obj : sourceList) {
                loopContext.bind(lambdaParamName, obj);
                DRGElementReference<TDecision> reference = dmnModelRepository.makeDRGElementReference(topLevelDecision);
                Result decisionResult = visitor.visitDecisionReference(reference, makeDecisionGlobalContext(reference, loopContext));
                outputList.add(Result.value(decisionResult));
            }

            // Aggregate
            Object output;
            if (aggregator == Aggregator.COLLECT) {
                output = outputList;
            } else {
                SignavioLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib = getFeelLib();
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
        public Result visit(TInvocation invocation, EvaluationContext evaluationContext) {
            ExpressionEvaluationContext expressionContext = (ExpressionEvaluationContext) evaluationContext;
            TDRGElement element = expressionContext.getElement();
            DMNContext parentContext = expressionContext.getContext();
            DRGElement elementAnnotation = expressionContext.getElementAnnotation();
            // Compute name-java binding for arguments
            Map<String, Object> argBinding = new LinkedHashMap<>();
            for (TBinding binding : invocation.getBinding()) {
                String argName = binding.getParameter().getName();
                TExpression argExpression = binding.getExpression();
                Result argResult = this.visit(argExpression, EvaluationContext.makeExpressionEvaluationContext(element, parentContext, elementAnnotation));
                Object argJava = Result.value(argResult);
                argBinding.put(argName, argJava);
            }

            // Evaluate function
            TExpression functionExp = invocation.getExpression();
            if (functionExp instanceof TLiteralExpression) {
                // Find invocable
                String invocableName = NameUtils.invocableName(((TLiteralExpression) functionExp).getText());
                TInvocable invocable = dmnModelRepository.findInvocableByName(invocableName);
                if (invocable == null) {
                    throw new DMNRuntimeException(String.format("Cannot find BKM for '%s'", invocableName));
                }
                // Make args
                List<Object> argList = new ArrayList<>();
                List<String> formalParameterList = dmnTransformer.invocableFEELParameterNames(invocable);
                for (String paramName : formalParameterList) {
                    if (argBinding.containsKey(paramName)) {
                        Object argValue = argBinding.get(paramName);
                        argList.add(argValue);
                    } else {
                        throw new DMNRuntimeException(String.format("Cannot find binding for parameter '%s'", paramName));
                    }
                }

                // Evaluate invocation
                DRGElementReference<TInvocable> reference = dmnModelRepository.makeDRGElementReference(invocable);
                return visitor.visitInvocable(reference, makeInvocableGlobalContext(((DRGElementReference<? extends TInvocable>) reference).getElement(), argList, parentContext));
            } else {
                throw new DMNRuntimeException(String.format("Not supported '%s'", functionExp.getClass().getSimpleName()));
            }
        }
    }
}