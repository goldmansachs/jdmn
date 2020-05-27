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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.feel.synthesis.expression.NativeExpressionFactory;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.java.CompoundStatement;
import com.gs.dmn.transformation.java.ExpressionStatement;
import com.gs.dmn.transformation.java.Statement;
import org.omg.spec.dmn._20180521.model.*;

import javax.xml.bind.JAXBElement;
import java.util.Map;

public class ContextToJavaTransformer {
    private final DMNModelRepository dmnModelRepository;
    private final BasicDMNToNativeTransformer dmnTransformer;
    private final EnvironmentFactory environmentFactory;
    private final NativeExpressionFactory expressionFactory;
    private final FEELTranslator feelTranslator;
    private final StandardDMNEnvironmentFactory dmnEnvironmentFactory;

    ContextToJavaTransformer(BasicDMNToNativeTransformer dmnTransformer) {
        this.dmnTransformer = dmnTransformer;

        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();
        this.expressionFactory = dmnTransformer.getExpressionFactory();
        this.dmnEnvironmentFactory = dmnTransformer.getDMNEnvironmentFactory();
        this.feelTranslator = dmnTransformer.getFEELTranslator();
    }

    public Statement expressionToNative(TDRGElement element, TContext context) {
        Environment elementEnvironment = this.dmnTransformer.makeEnvironment(element);

        // Make context environment
        Pair<Environment, Map<TContextEntry, Expression>> pair = this.dmnTransformer.makeContextEnvironment(element, context, elementEnvironment);
        return contextExpressionToNative(element, context, pair.getLeft(), pair.getRight());
    }

    Statement contextExpressionToNative(TDRGElement element, TContext context, Environment elementEnvironment) {
        // Make context environment
        Pair<Environment, Map<TContextEntry, Expression>> pair = this.dmnTransformer.makeContextEnvironment(element, context, elementEnvironment);
        return contextExpressionToNative(element, context, pair.getLeft(), pair.getRight());
    }

    private Statement contextExpressionToNative(TDRGElement element, TContext context, Environment contextEnvironment, Map<TContextEntry, Expression> literalExpressionMap) {
        // Translate to Java
        TDefinitions model = this.dmnModelRepository.getModel(element);
        FEELContext feelContext = FEELContext.makeContext(element, contextEnvironment);
        CompoundStatement statement = new CompoundStatement();
        ExpressionStatement returnValue = null;
        for(TContextEntry entry: context.getContextEntry()) {
            // Translate value
            ExpressionStatement value;
            Type entryType;
            JAXBElement<? extends TExpression> jaxbElement = entry.getExpression();
            if (jaxbElement != null) {
                TExpression expression = jaxbElement.getValue();
                if (expression instanceof TLiteralExpression) {
                    Expression feelExpression = literalExpressionMap.get(entry);
                    entryType = this.dmnEnvironmentFactory.entryType(element, entry, expression, feelExpression);
                    String stm = this.feelTranslator.expressionToNative(feelExpression, feelContext);
                    value = new ExpressionStatement(stm, entryType);
                } else {
                    entryType = this.dmnEnvironmentFactory.entryType(element, entry, contextEnvironment);
                    value = (ExpressionStatement) this.dmnTransformer.expressionToNative(element, expression, contextEnvironment);
                }
            } else {
                entryType = this.dmnEnvironmentFactory.entryType(element, entry, contextEnvironment);
                value = new ExpressionStatement("null", entryType);
            }

            // Add statement
            TInformationItem variable = entry.getVariable();
            if (variable != null) {
                String name = this.dmnTransformer.lowerCaseFirst(variable.getName());
                String type = this.dmnTransformer.toNativeType(entryType);
                String assignmentText = this.expressionFactory.makeVariableAssignment(type, name, value.getExpression());
                statement.add(new ExpressionStatement(assignmentText, entryType));
            } else {
                returnValue = value;
            }
        }

        // Add return statement
        Type returnType = this.dmnTransformer.toFEELType(model, this.dmnTransformer.drgElementOutputTypeRef(element));
        if (returnValue != null) {
            String text = this.expressionFactory.makeReturn(returnValue.getExpression());
            statement.add(new ExpressionStatement(text, returnType));
        } else {
            // Make complex type value
            String complexJavaType = this.dmnTransformer.drgElementOutputType(element);
            String complexTypeVariable = this.dmnTransformer.drgElementVariableName(element);
            String expressionText = this.expressionFactory.makeVariableAssignment(this.dmnTransformer.itemDefinitionNativeClassName(complexJavaType), complexTypeVariable, this.dmnTransformer.defaultConstructor(this.dmnTransformer.itemDefinitionNativeClassName(complexJavaType)));
            statement.add(new ExpressionStatement(expressionText, returnType));
            // Add entries
            for(TContextEntry entry: context.getContextEntry()) {
                Type entryType;
                JAXBElement<? extends TExpression> jaxbElement = entry.getExpression();
                TExpression expression = jaxbElement == null ? null : jaxbElement.getValue();
                if (expression instanceof TLiteralExpression) {
                    Expression feelExpression = literalExpressionMap.get(entry);
                    entryType = this.dmnEnvironmentFactory.entryType(element, entry, expression, feelExpression);
                } else {
                    entryType = this.dmnEnvironmentFactory.entryType(element, entry, contextEnvironment);
                }

                // Add statement
                TInformationItem variable = entry.getVariable();
                if (variable != null) {
                    String javaContextEntryName = this.dmnTransformer.lowerCaseFirst(variable.getName());
                    String entryText = this.expressionFactory.makeMemberAssignment(complexTypeVariable, javaContextEntryName, javaContextEntryName);
                    statement.add(new ExpressionStatement(entryText, entryType));
                }
            }
            // Return value
            String returnText = this.expressionFactory.makeReturn(complexTypeVariable);
            statement.add(new ExpressionStatement(returnText, returnType));
        }
        return statement;
    }
}
