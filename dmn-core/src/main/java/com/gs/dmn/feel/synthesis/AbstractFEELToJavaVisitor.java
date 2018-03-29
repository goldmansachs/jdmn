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
package com.gs.dmn.feel.synthesis;

import com.gs.dmn.feel.OperatorDecisionTable;
import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.environment.Conversion;
import com.gs.dmn.feel.analysis.semantics.environment.ConversionKind;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.AbstractAnalysisVisitor;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Name;
import com.gs.dmn.feel.analysis.syntax.ast.expression.QualifiedName;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.DateTimeLiteral;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.transformation.basic.BasicDMN2JavaTransformer;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public abstract class AbstractFEELToJavaVisitor extends AbstractAnalysisVisitor {
    private static final Map<String, String> FEEL_2_JAVA_FUNCTION = new LinkedHashMap() {{
        put("date and time", "dateAndTime");
        put("years and months duration", "yearsAndMonthsDuration");

        put("string length", "stringLength");
        put("upper case", "upperCase");
        put("lower case", "lowerCase");
        put("substring before", "substringBefore");
        put("substring after", "substringAfter");
        put("starts with", "startsWith");
        put("ends with", "endsWith");

        put("list contains", "listContains");
        put("insert before", "insertBefore");
        put("index of", "indexOf");
        put("distinct values", "distinctValues");
    }};

    public AbstractFEELToJavaVisitor(BasicDMN2JavaTransformer dmnTransformer) {
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
        if (sourceType instanceof ItemDefinitionType) {
            Type memberType = ((ItemDefinitionType) sourceType).getMemberType(memberName);
            String javaType = dmnTransformer.toJavaType(memberType);
            return makeSafeAccessor(javaType, source, dmnTransformer.getter(memberName));
        } else if (sourceType instanceof ContextType) {
            Type memberType = ((ContextType) sourceType).getMemberType(memberName);
            String javaType = dmnTransformer.toJavaType(memberType);
            return String.format("((%s)%s.%s)", javaType, source, dmnTransformer.contextGetter(memberName));
        } else if (sourceType instanceof ListType) {
            String filter = makeNavigation(element, ((ListType) sourceType).getElementType(), "x", memberName, memberVariableName);
            return String.format("%s.stream().map(x -> %s).collect(Collectors.toList())", source, filter);
        } else if (sourceType instanceof DateType) {
            return String.format("%s(%s)", javaMemberFunctionName(memberName), source);
        } else if (sourceType instanceof TimeType) {
            return String.format("%s(%s)", javaMemberFunctionName(memberName), source);
        } else if (sourceType instanceof DateTimeType) {
            return String.format("%s(%s)", javaMemberFunctionName(memberName), source);
        } else if (sourceType instanceof DurationType) {
            return String.format("%s(%s)", javaMemberFunctionName(memberName), source);
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
        return dmnTransformer.javaFriendlyName(name.length() == 1 ? firstChar : firstChar + name.substring(1));
    }

    protected Object makeCondition(String feelOperator, Expression leftOperand, Expression rightOperand, FEELContext params) {
        String leftOpd = (String) leftOperand.accept(this, params);
        String rightOpd = (String) rightOperand.accept(this, params);
        JavaOperator javaOperator = OperatorDecisionTable.javaOperator(feelOperator, leftOperand.getType(), rightOperand.getType());
        if (javaOperator == null) {
            throw new DMNRuntimeException(String.format("Operator '%s' cannot be applied to '%s' and '%s'", feelOperator, leftOpd, rightOpd));
        } else {
            if (javaOperator.getCardinality() == 2) {
                if (javaOperator.getNotation() == JavaOperator.Notation.FUNCTIONAL) {
                    if (javaOperator.getAssociativity() == JavaOperator.Associativity.LEFT_RIGHT) {
                        return functionalExpression(javaOperator.getName(), leftOpd, rightOpd);
                    } else {
                        return functionalExpression(javaOperator.getName(), rightOpd, leftOpd);
                    }
                } else {
                    if (javaOperator.getAssociativity() == JavaOperator.Associativity.LEFT_RIGHT) {
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

    protected String makeSafeAccessor(String javaType, String source, String accessorMethod) {
        return String.format("((%s)(%s != null ? %s.%s : null))", javaType, source, source, accessorMethod);
    }

    protected String listTestOperator(String feelOperatorName, Expression leftOperand, Expression rightOperand) {
        JavaOperator javaOperator = OperatorDecisionTable.javaOperator(feelOperatorName, rightOperand.getType(), rightOperand.getType());
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
        String value = element.getValue();
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

    protected List<Object> convertArguments(List<Object> argList, List<Conversion> parameterConversions) {
        if (requiresConversion(parameterConversions)) {
            List<Object> convertedArgList = new ArrayList<>();
            for(int i=0; i<parameterConversions.size(); i++) {
                Object arg = argList.get(i);
                Conversion conversion = parameterConversions.get(i);
                Object convertedArg = convertArgument(arg, conversion);
                convertedArgList.add(convertedArg);
            }
            return convertedArgList;
        } else {
            return argList;
        }
    }

    protected abstract Object convertArgument(Object arg, Conversion conversion);

    protected boolean requiresConversion(List<Conversion> parameterConversions) {
        if (parameterConversions == null) {
            return false;
        }
        return parameterConversions.stream().anyMatch(c -> c.getKind() != ConversionKind.NONE);
    }

    protected String functionName(Expression function) {
        String feelFunctionName = null;
        if (function instanceof Name) {
            feelFunctionName = ((Name) function).getName();
        } else {
            feelFunctionName = ((QualifiedName) function).getQualifiedName();
        }
        return feelFunctionName;
    }
}
