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

import com.gs.dmn.ast.TFunctionDefinition;
import com.gs.dmn.ast.TInvocable;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.interpreter.ELInterpreter;
import com.gs.dmn.feel.interpreter.TypeConverter;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

public interface DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    BasicDMNToNativeTransformer getBasicDMNTransformer();

    ELInterpreter<Type, DMNContext> getElInterpreter();

    FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> getFeelLib();

    TypeConverter getTypeConverter();

    //
    // Evaluate DRG elements
    //
    Result evaluateDecision(String namespace, String decisionName, EvaluationContext context);
    Result evaluateInvocable(String namespace, String invocableName, EvaluationContext context);

    //
    // Evaluate DMN elements in context
    //
    Result evaluate(TInvocable invocable, EvaluationContext context);
    Result evaluate(TFunctionDefinition functionDefinition, EvaluationContext context);
}
