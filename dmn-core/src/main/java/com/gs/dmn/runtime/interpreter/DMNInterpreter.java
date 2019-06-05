/**
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

import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import org.omg.spec.dmn._20180521.model.TBusinessKnowledgeModel;
import org.omg.spec.dmn._20180521.model.TDecisionService;
import org.omg.spec.dmn._20180521.model.TFunctionDefinition;

import java.util.List;

public interface DMNInterpreter {
    BasicDMN2JavaTransformer getBasicDMNTransformer();

    FEELLib getFeelLib();

    Object evaluate(String drgElementName, RuntimeEnvironment runtimeEnvironment);

    Object evaluateBKM(TBusinessKnowledgeModel bkm, List<Object> argList, FEELContext context);

    Object evaluateDecisionService(TDecisionService service, List<Object> argList, FEELContext context);

    Object evaluateFunctionDefinition(TFunctionDefinition functionDefinition, List<Object> argList, FEELContext context);

    //
    // Expression evaluation
    //
    Object evaluateLiteralExpression(String text, Environment environment, RuntimeEnvironment runtimeEnvironment);
}
