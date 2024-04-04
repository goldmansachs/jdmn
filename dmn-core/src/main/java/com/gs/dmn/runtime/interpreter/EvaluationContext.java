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
package com.gs.dmn.runtime.interpreter;

import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.runtime.listener.DRGElement;

import java.util.List;
import java.util.Map;

public abstract class EvaluationContext {
    public static EvaluationContext makeFunctionInvocationContext(TDRGElement element, List<Object> argList, DMNContext context) {
        return new FunctionInvocationContext(element, argList, context);
    }

    public static EvaluationContext makeFunctionInvocationContext(TDRGElement element, List<Object> argList) {
        return new FunctionInvocationContext(element, argList);
    }

    public static EvaluationContext makeDecisionEvaluationContext(TDRGElement element, Map<String, Object> informationRequirements) {
        return new DecisionEvaluationContext(element, informationRequirements);
    }

    public static EvaluationContext makeExpressionEvaluationContext(TDRGElement element, DMNContext context, DRGElement elementAnnotation) {
        return new ExpressionEvaluationContext(element, context, elementAnnotation);
    }

    private final TDRGElement element;

    public EvaluationContext(TDRGElement element) {
        this.element = element;
    }

    public TDRGElement getElement() {
        return element;
    }
}