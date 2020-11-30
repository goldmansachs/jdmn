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
package com.gs.dmn.feel.synthesis;

import com.gs.dmn.DRGElementReference;
import com.gs.dmn.feel.OperatorDecisionTable;
import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.AbstractAnalysisVisitor;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Name;
import com.gs.dmn.feel.analysis.syntax.ast.expression.QualifiedName;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.DateTimeLiteral;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.ImportContextType;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.TBusinessKnowledgeModel;
import org.omg.spec.dmn._20191111.model.TDRGElement;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public abstract class AbstractFEELToJavaVisitor extends AbstractAnalysisVisitor {
    private static final Map<String, String> FEEL_2_JAVA_FUNCTION = new LinkedHashMap<>();
    static {
        FEEL_2_JAVA_FUNCTION.put("get value", "getValue");
        FEEL_2_JAVA_FUNCTION.put("get entries", "getEntries");

        FEEL_2_JAVA_FUNCTION.put("distinct values", "distinctValues");
        FEEL_2_JAVA_FUNCTION.put("index of", "indexOf");
        FEEL_2_JAVA_FUNCTION.put("insert before", "insertBefore");
        FEEL_2_JAVA_FUNCTION.put("list contains", "listContains");

        FEEL_2_JAVA_FUNCTION.put("ends with", "endsWith");
        FEEL_2_JAVA_FUNCTION.put("starts with", "startsWith");
        FEEL_2_JAVA_FUNCTION.put("substring after", "substringAfter");
        FEEL_2_JAVA_FUNCTION.put("substring before", "substringBefore");
        FEEL_2_JAVA_FUNCTION.put("lower case", "lowerCase");
        FEEL_2_JAVA_FUNCTION.put("upper case", "upperCase");
        FEEL_2_JAVA_FUNCTION.put("string length", "stringLength");

        FEEL_2_JAVA_FUNCTION.put("years and months duration", "yearsAndMonthsDuration");
        FEEL_2_JAVA_FUNCTION.put("date and time", "dateAndTime");
    }

    public AbstractFEELToJavaVisitor(BasicDMNToNativeTransformer dmnTransformer) {
        super(dmnTransformer);
    }

    protected String javaFunctionName(String feelFunctionName) {
        String javaFunctionName = FEEL_2_JAVA_FUNCTION.get(feelFunctionName);
        if (StringUtils.isEmpty(javaFunctionName)) {
            return feelFunctionName;
        } else {
            return javaFunctionName;
        }
    }

    protected String makeNavigation(Expression element, Type sourceType, String source, String memberName, String memberVariableName) {
        if (sourceType instanceof ImportContextType) {
            ImportContextType importContextType = (ImportContextType) sourceType;
            DRGElementReference<? extends TDRGElement> memberReference = importContextType.getMemberReference(memberName);
            TDRGElement drgElement = memberReference.getElement();
            if (drgElement instanceof TBusinessKnowledgeModel) {
                String javaQualifiedName = this.dmnTransformer.bkmQualifiedFunctionName((TBusinessKnowledgeModel) drgElement);
                return javaQualifiedName;
            } else {
                String javaName = this.dmnTransformer.drgReferenceQualifiedName(memberReference);
                return this.dmnTransformer.lazyEvaluation(memberReference.getElementName(), javaName);
            }
        } else if (sourceType instanceof ItemDefinitionType) {
            Type memberType = ((ItemDefinitionType) sourceType).getMemberType(memberName);
            String javaType = dmnTransformer.toNativeType(memberType);
            return this.nativeFactory.makeItemDefinitionAccessor(javaType, source, memberName);
        } else if (sourceType instanceof ContextType) {
            Type memberType = ((ContextType) sourceType).getMemberType(memberName);
            String javaType = dmnTransformer.toNativeType(memberType);
            return this.nativeFactory.makeContextAccessor(javaType, source, memberName);
        } else if (sourceType instanceof ListType) {
            String filter = makeNavigation(element, ((ListType) sourceType).getElementType(), "x", memberName, memberVariableName);
            return this.nativeFactory.makeCollectionMap(source, filter);
        } else if (sourceType instanceof DateType) {
            return String.format("%s(%s)", javaMemberFunctionName(memberName), source);
        } else if (sourceType instanceof TimeType) {
            return String.format("%s(%s)", javaMemberFunctionName(memberName), source);
        } else if (sourceType instanceof DateTimeType) {
            return String.format("%s(%s)", javaMemberFunctionName(memberName), source);
        } else if (sourceType instanceof DurationType) {
            return String.format("%s(%s)", javaMemberFunctionName(memberName), source);
        } else if (sourceType instanceof AnyType) {
            // source is Context
            return this.nativeFactory.makeContextSelectExpression(dmnTransformer.contextClassName(), source, memberName);
        } else {
            throw new SemanticError(element, String.format("Cannot generate navigation path '%s'", element.toString()));
        }
    }

    protected String javaMemberFunctionName(String memberName) {
        memberName = dmnTransformer.getDMNModelRepository().removeSingleQuotes(memberName);
        if ("time offset".equalsIgnoreCase(memberName)) {
            return "timeOffset";
        } else {
            return memberName;
        }
    }

    protected String javaFriendlyVariableName(String name) {
        name = dmnTransformer.getDMNModelRepository().removeSingleQuotes(name);
        String firstChar = Character.toString(Character.toLowerCase(name.charAt(0)));
        return dmnTransformer.nativeFriendlyName(name.length() == 1 ? firstChar : firstChar + name.substring(1));
    }

    protected Object makeCondition(String feelOperator, Expression leftOperand, Expression rightOperand, FEELContext context) {
        String leftOpd = (String) leftOperand.accept(this, context);
        String rightOpd = (String) rightOperand.accept(this, context);
        NativeOperator javaOperator = OperatorDecisionTable.javaOperator(feelOperator, leftOperand.getType(), rightOperand.getType());
        return makeCondition(feelOperator, leftOpd, rightOpd, javaOperator);
    }

    protected String makeCondition(String feelOperator, String leftOpd, String rightOpd, NativeOperator javaOperator) {
        if (javaOperator == null) {
            throw new DMNRuntimeException(String.format("Operator '%s' cannot be applied to '%s' and '%s'", feelOperator, leftOpd, rightOpd));
        } else {
            if (javaOperator.getCardinality() == 2) {
                if (javaOperator.getNotation() == NativeOperator.Notation.FUNCTIONAL) {
                    if (javaOperator.getAssociativity() == NativeOperator.Associativity.LEFT_RIGHT) {
                        return functionalExpression(javaOperator.getName(), leftOpd, rightOpd);
                    } else {
                        return functionalExpression(javaOperator.getName(), rightOpd, leftOpd);
                    }
                } else {
                    if (javaOperator.getAssociativity() == NativeOperator.Associativity.LEFT_RIGHT) {
                        return infixExpression(javaOperator.getName(), leftOpd, rightOpd);
                    } else {
                        return infixExpression(javaOperator.getName(), rightOpd, leftOpd);
                    }
                }
            } else {
                throw new DMNRuntimeException(String.format("Operator '%s' cannot be applied to '%s' and '%s'", feelOperator, leftOpd, rightOpd));
            }
        }
    }

    protected String listTestOperator(String feelOperatorName, Expression leftOperand, Expression rightOperand) {
        NativeOperator javaOperator = OperatorDecisionTable.javaOperator(feelOperatorName, rightOperand.getType(), rightOperand.getType());
        if (javaOperator != null) {
            return javaOperator.getName();
        } else {
            throw new DMNRuntimeException(String.format("Operator '%s' cannot be applied to '%s' and '%s'", feelOperatorName, leftOperand, rightOperand));
        }
    }

    protected String functionalExpression(String javaOperator, String leftOpd, String rightOpd) {
        return String.format("%s(%s, %s)", javaOperator, leftOpd, rightOpd);
    }

    protected String infixExpression(String javaOperator, String leftOpd, String rightOpd) {
        return String.format("(%s) %s (%s)", leftOpd, javaOperator, rightOpd);
    }

    protected Object dateTimeLiteralToJava(DateTimeLiteral element) {
        Type type = element.getType();
        String value = element.getLexeme();
        if (type == DATE) {
            return String.format("date(%s)", value);
        } else if (type == TIME) {
            return String.format("time(%s)", value);
        } else if (type == DATE_AND_TIME) {
            return String.format("dateAndTime(%s)", value);
        } else if (type == DAYS_AND_TIME_DURATION || type == YEARS_AND_MONTHS_DURATION) {
            return String.format("duration(%s)", value);
        } else {
            throw new DMNRuntimeException("Illegal date literal kind '" + type + "'. Expected 'date', 'time', 'date and time' or 'duration'.");
        }
    }

    protected String functionName(Expression function) {
        String feelFunctionName;
        if (function instanceof Name) {
            feelFunctionName = ((Name) function).getName();
        } else {
            feelFunctionName = ((QualifiedName) function).getQualifiedName();
        }
        return feelFunctionName;
    }
}
