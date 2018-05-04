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
package com.gs.dmn.transformation.basic;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.java.ExpressionStatement;
import com.gs.dmn.transformation.java.Statement;
import org.omg.spec.dmn._20180521.model.TDRGElement;
import org.omg.spec.dmn._20180521.model.TExpression;
import org.omg.spec.dmn._20180521.model.TList;
import org.omg.spec.dmn._20180521.model.TRelation;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RelationToJavaTransformer {
    private final BasicDMN2JavaTransformer dmnTransformer;
    private final DMNModelRepository modelRepository;
    private final String indent = "\t\t\t\t";
    private final EnvironmentFactory environmentFactory;

    RelationToJavaTransformer(BasicDMN2JavaTransformer dmnTransformer) {
        this.modelRepository = dmnTransformer.getDMNModelRepository();
        this.dmnTransformer = dmnTransformer;
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();
    }

    public Statement expressionToJava(TRelation relation, TDRGElement element) {
        Environment elementEnvironment = dmnTransformer.makeEnvironment(element);

        // Make relation environment
        Environment relationEnvironment = dmnTransformer.makeRelationEnvironment(relation, elementEnvironment);
        return relationExpressionToJava(relation, relationEnvironment, element);
    }

    Statement relationExpressionToJava(TRelation relation, Environment relationEnvironment, TDRGElement element) {
        Type resultType = dmnTransformer.toFEELType(dmnTransformer.drgElementOutputTypeRef(element));
        if (relation.getRow() == null) {
            return new ExpressionStatement("null", resultType);
        }

        // Type name
        String javaType = dmnTransformer.drgElementOutputClassName(element);

        // Constructor argument names
        List<String> argNameList = relation.getColumn().stream().map(c -> dmnTransformer.javaFriendlyVariableName(c.getName())).collect(Collectors.toList());

        // Scan relation and translate each row to a Java constructor invocation
        List<String> rowValues = new ArrayList<>();
        for(TList row: relation.getRow()) {
            // Translate value
            String value;
            List<JAXBElement<? extends TExpression>> jaxbElementList = row.getExpression();
            if (jaxbElementList == null) {
                rowValues.add("null");
            } else {
                List<Pair<String, String>> argPairList = new ArrayList<>();
                for(int i = 0; i < jaxbElementList.size(); i++) {
                    JAXBElement<? extends TExpression> jaxbElement = jaxbElementList.get(i);
                    TExpression expression = jaxbElement.getValue();
                    String argValue;
                    if (expression == null) {
                        argValue = "null";
                    } else {
                        Statement statement = dmnTransformer.expressionToJava(expression, relationEnvironment, element);
                        argValue = ((ExpressionStatement)statement).getExpression();
                    }
                    argPairList.add(new Pair<String, String>(argNameList.get(i), argValue));
                }
                argPairList.sort(Comparator.comparing(Pair::getLeft));
                String argList = argPairList.stream().map(Pair::getRight).collect(Collectors.joining(", "));
                rowValues.add(dmnTransformer.constructor(dmnTransformer.itemDefinitionJavaClassName(javaType), argList));
            }
        }

        // Make a list
        String result = dmnTransformer.asList(rowValues.stream().collect(Collectors.joining(",\n" + indent)));
        return new ExpressionStatement(result, resultType);
    }
}
