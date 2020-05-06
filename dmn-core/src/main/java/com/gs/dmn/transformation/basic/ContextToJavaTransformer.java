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
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.java.CompoundStatement;
import com.gs.dmn.transformation.java.ExpressionStatement;
import com.gs.dmn.transformation.java.Statement;
import org.omg.spec.dmn._20180521.model.*;

import javax.xml.bind.JAXBElement;
import java.util.Map;

public class ContextToJavaTransformer {
    private final DMNModelRepository dmnModelRepository;
    private final BasicDMN2JavaTransformer dmnTransformer;
    private final EnvironmentFactory environmentFactory;

    ContextToJavaTransformer(BasicDMN2JavaTransformer dmnTransformer) {
        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.dmnTransformer = dmnTransformer;
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();
    }

    public Statement expressionToJava(TDRGElement element, TContext context) {
        Environment elementEnvironment = this.dmnTransformer.makeEnvironment(element);

        // Make context environment
        Pair<Environment, Map<TContextEntry, Expression>> pair = this.dmnTransformer.makeContextEnvironment(element, context, elementEnvironment);
        return contextExpressionToJava(element, context, pair.getLeft(), pair.getRight());
    }

    Statement contextExpressionToJava(TDRGElement element, TContext context, Environment elementEnvironment) {
        // Make context environment
        Pair<Environment, Map<TContextEntry, Expression>> pair = this.dmnTransformer.makeContextEnvironment(element, context, elementEnvironment);
        return contextExpressionToJava(element, context, pair.getLeft(), pair.getRight());
    }

     private Statement contextExpressionToJava(TDRGElement element, TContext context, Environment contextEnvironment, Map<TContextEntry, Expression> literalExpressionMap) {
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
                    entryType = this.dmnTransformer.entryType(element, entry, expression, feelExpression);
                    String stm = this.dmnTransformer.feelTranslator.expressionToJava(feelExpression, feelContext);
                    value = new ExpressionStatement(stm, entryType);
                } else {
                    entryType = this.dmnTransformer.entryType(element, entry, contextEnvironment);
                    value = (ExpressionStatement) this.dmnTransformer.expressionToJava(element, expression, contextEnvironment);
                }
            } else {
                entryType = this.dmnTransformer.entryType(element, entry, contextEnvironment);
                value = new ExpressionStatement("null", entryType);
            }

            // Add statement
            TInformationItem variable = entry.getVariable();
            if (variable != null) {
                String javaName = this.dmnTransformer.lowerCaseFirst(variable.getName());
                String javaType = this.dmnTransformer.toJavaType(entryType);
                String assignmentText = String.format("%s %s = %s;", javaType, javaName, value.getExpression());
                statement.add(new ExpressionStatement(assignmentText, entryType));
            } else {
                returnValue = value;
            }
        }

        // Add return statement
        Type returnType = this.dmnTransformer.toFEELType(model, this.dmnTransformer.drgElementOutputTypeRef(element));
        if (returnValue != null) {
            String text = String.format("return %s;", returnValue.getExpression());
            statement.add(new ExpressionStatement(text, returnType));
        } else {
            // Make complex type value
            String complexJavaType = this.dmnTransformer.drgElementOutputType(element);
            String complexTypeVariable = this.dmnTransformer.drgElementVariableName(element);
            String expressionText = String.format("%s %s = %s;", this.dmnTransformer.itemDefinitionJavaSimpleClassName(complexJavaType), complexTypeVariable, this.dmnTransformer.defaultConstructor(this.dmnTransformer.itemDefinitionJavaSimpleClassName(complexJavaType)));
            statement.add(new ExpressionStatement(expressionText, returnType));
            // Add entries
            for(TContextEntry entry: context.getContextEntry()) {
                Type entryType;
                JAXBElement<? extends TExpression> jaxbElement = entry.getExpression();
                TExpression expression = jaxbElement == null ? null : jaxbElement.getValue();
                if (expression instanceof TLiteralExpression) {
                    Expression feelExpression = literalExpressionMap.get(entry);
                    entryType = this.dmnTransformer.entryType(element, entry, expression, feelExpression);
                } else {
                    entryType = this.dmnTransformer.entryType(element, entry, contextEnvironment);
                }

                // Add statement
                TInformationItem variable = entry.getVariable();
                if (variable != null) {
                    String javaContextEntryName = this.dmnTransformer.lowerCaseFirst(variable.getName());
                    String entryText = String.format("%s.%s(%s);", complexTypeVariable, this.dmnTransformer.setter(javaContextEntryName), javaContextEntryName);
                    statement.add(new ExpressionStatement(entryText, entryType));
                }
            }
            // Return value
            String returnText = String.format("return %s;", complexTypeVariable);
            statement.add(new ExpressionStatement(returnText, returnType));
        }
        return statement;
    }
}
