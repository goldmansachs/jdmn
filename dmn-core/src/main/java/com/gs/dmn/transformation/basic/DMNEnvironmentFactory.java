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
package com.gs.dmn.transformation.basic;

import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.context.environment.Environment;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.runtime.Pair;

import java.util.Map;

public interface DMNEnvironmentFactory {
    //
    // DRG Elements
    //
    Type drgElementOutputFEELType(TDRGElement element);

    Type drgElementOutputFEELType(TDRGElement element, DMNContext context);

    Type drgElementVariableFEELType(TDRGElement element);

    Type drgElementVariableFEELType(TDRGElement element, DMNContext context);

    Type toFEELType(TInputData inputData);

    //
    // Expression related functions
    //
    Type expressionType(TDRGElement element, TExpression expression, DMNContext context);

    Type toFEELType(TDRGElement element, TOutputClause outputClause, int index);

    Type convertType(Type type, boolean convertToContext);

    //
    // Common Type functions
    //
    Type toFEELType(TDefinitions model, String typeName);

    Type toFEELType(TDefinitions model, QualifiedName typeRef);

    Type toFEELType(TItemDefinition itemDefinition);

    Type externalFunctionReturnFEELType(TNamedElement element, Expression<Type> body);

    //
    // Environments
    //
    Environment makeEnvironment(TDRGElement element);

    Environment makeEnvironment(TDRGElement element, boolean isRecursive);

    //
    // Decision Table
    //
    Environment makeUnaryTestEnvironment(TDRGElement element, Expression<Type> inputExpression);

    //
    // Function Definition
    //
    Environment makeFunctionDefinitionEnvironment(TNamedElement element, TFunctionDefinition functionDefinition);

    Declaration makeVariableDeclaration(TDRGElement element, TInformationItem variable);

    //
    // Context
    //
    Pair<DMNContext, Map<TContextEntry, Expression<Type>>> makeContextEnvironment(TDRGElement element, TContext context, DMNContext parentContext);

    Type entryType(TDRGElement element, TContextEntry entry, DMNContext localContext);

    Type entryType(TDRGElement element, TContextEntry entry, TExpression expression, Expression<Type> feelExpression);

    //
    // Relation
    //
    Environment makeRelationEnvironment(TNamedElement element, TRelation relation);
}
