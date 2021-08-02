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
package com.gs.dmn.validation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.BooleanLiteral;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.NumericLiteral;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.SimpleLiteral;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.StringLiteral;
import com.gs.dmn.feel.analysis.syntax.ast.test.*;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.transformation.basic.QualifiedName;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.*;

import java.util.ArrayList;
import java.util.List;

public class RuleFactory {
    public List<Rule> makeRules(int totalNumberOfRules, int totalNumberOfColumns, DMNModelRepository repository, TDRGElement element, TDecisionTable decisionTable, FEELTranslator feelTranslator) {
        List<Rule> rules = new ArrayList<>();
        for (int ruleIndex=0; ruleIndex<totalNumberOfRules; ruleIndex++) {
            List<Interval> intervals = new ArrayList<>();
            for (int columnIndex=0; columnIndex<totalNumberOfColumns; columnIndex++) {
                TDecisionRule rule = decisionTable.getRule().get(ruleIndex);
                List<TUnaryTests> inputEntry = rule.getInputEntry();
                TUnaryTests cell = inputEntry.get(columnIndex);
                Interval interval = makeInterval(repository, element, decisionTable, columnIndex, ruleIndex, cell, feelTranslator);
                if (interval != null) {
                    intervals.add(interval);
                }
            }
            if (intervals.size() == totalNumberOfColumns) {
                rules.add(new Rule(intervals));
            } else {
                return new ArrayList<>();
            }
        }
        return rules;
    }

    private Interval makeInterval(DMNModelRepository repository, TDRGElement element, TDecisionTable decisionTable, int columnIndex, int ruleIndex, TUnaryTests cell, FEELTranslator feelTranslator) {
        if (cell == null) {
            return null;
        }

        // Parse unary tests
        String text = cell.getText();
        UnaryTests unaryTests = feelTranslator.parseUnaryTests(text);
        String columnInputType = getInputType(decisionTable, columnIndex);
        if (unaryTests instanceof Any) {
            if (isNumberType(columnInputType)) {
                // Number - min, max interval is [-Infinity .. +Infinity]
                return new Interval(ruleIndex, columnIndex, false, Bound.MINUS_INFINITY, false, Bound.PLUS_INFINITY);
            } else if (isBooleanType(columnInputType)) {
                // Boolean - min, max interval is [0..2)
                return new Interval(ruleIndex, columnIndex, false, Bound.ZERO, true, Bound.ONE + 1);
            } else {
                // Enumeration
                TDefinitions model = repository.getModel(element);
                TItemDefinition itemDefinition = repository.lookupItemDefinition(model, QualifiedName.toQualifiedName(model, columnInputType));
                if (itemDefinition != null) {
                    String typeRef = itemDefinition.getTypeRef();
                    if (isStringType(typeRef)) {
                        List<String> allowedValues = findAllowedValues(repository, element, decisionTable, columnIndex);
                        if (!allowedValues.isEmpty()) {
                            // Number - min, max interval is [0..max+1)
                            return new Interval(ruleIndex, columnIndex, false, Bound.ZERO, true, allowedValues.size() + 1.0);
                        }
                    }
                }
            }
        } else if (unaryTests instanceof PositiveUnaryTests) {
            List<PositiveUnaryTest> positiveUnaryTests = ((PositiveUnaryTests) unaryTests).getPositiveUnaryTests();
            // Check simple expressions only
            if (positiveUnaryTests.size() == 1) {
                PositiveUnaryTest positiveUnaryTest = positiveUnaryTests.get(0);
                // Check intervals
                if (positiveUnaryTest instanceof EndpointsRange) {
                    EndpointsRange astRange = (EndpointsRange) positiveUnaryTest;
                    Double startValue = makeBoundValue(repository, element, decisionTable, columnIndex, astRange.getStart());
                    Double endValue = makeBoundValue(repository, element, decisionTable, columnIndex, astRange.getEnd());
                    if (startValue != null && endValue != null) {
                        boolean openEnd = astRange.isOpenEnd();
                        boolean openStart = astRange.isOpenStart();
                        if (openStart) {
                            startValue = startValue + Bound.DELTA;
                        }
                        if (openEnd) {
                            endValue = endValue - Bound.DELTA;
                        }
                        return new Interval(ruleIndex, columnIndex, false, startValue, false, endValue);
                    }
                } else if (positiveUnaryTest instanceof OperatorRange) {
                    OperatorRange operatorRange = (OperatorRange) positiveUnaryTest;
                    Double value = makeBoundValue(repository, element, decisionTable, columnIndex, operatorRange.getEndpoint());
                    if (value != null) {
                        String operator = operatorRange.getOperator();
                        if (isNumberType(columnInputType)) {
                            // Number
                            if (operator == null) {
                                return new Interval(ruleIndex, columnIndex, false, value, false, value);
                            } else if ("<".equals(operator)) {
                                return new Interval(ruleIndex, columnIndex, false, Bound.MINUS_INFINITY, false, value - Bound.DELTA);
                            } else if ("<=".equals(operator)) {
                                return new Interval(ruleIndex, columnIndex, false, Bound.MINUS_INFINITY, false, value);
                            } else if (">".equals(operator)) {
                                return new Interval(ruleIndex, columnIndex, false, value + Bound.DELTA, false, Bound.PLUS_INFINITY);
                            } else if (">=".equals(operator)) {
                                return new Interval(ruleIndex, columnIndex, false, value, false, Bound.PLUS_INFINITY);
                            }
                        } else if (isBooleanType(columnInputType)) {
                            // Boolean
                            if (operator == null) {
                                // create interval [i..i+1)
                                return new Interval(ruleIndex, columnIndex, false, value, true, value + 1);
                            }
                        } else {
                            // Enumeration
                            TDefinitions model = repository.getModel(element);
                            TItemDefinition itemDefinition = repository.lookupItemDefinition(model, QualifiedName.toQualifiedName(model, columnInputType));
                            if (itemDefinition != null) {
                                String typeRef = itemDefinition.getTypeRef();
                                if (isStringType(typeRef)) {
                                    // create interval [i..i+1)
                                    return new Interval(ruleIndex, columnIndex, false, value, true, value + 1);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private Double makeBoundValue(DMNModelRepository repository, TDRGElement element, TDecisionTable decisionTable, int columnIndex, Expression exp) {
        Double value = null;
        if (exp instanceof NumericLiteral) {
            String lexeme = ((SimpleLiteral) exp).getLexeme();
            value = Double.parseDouble(lexeme);
        } else if (exp instanceof BooleanLiteral) {
            String lexeme = ((SimpleLiteral) exp).getLexeme();
            boolean bValue = Boolean.parseBoolean(lexeme);
            value = bValue ? Bound.ONE : Bound.ZERO;
        } else if (exp instanceof StringLiteral) {
            String lexeme = ((SimpleLiteral) exp).getLexeme();
            List<String> allowedValues = findAllowedValues(repository, element, decisionTable, columnIndex);
            for (int i=0; i<allowedValues.size(); i++) {
                String enumValue = allowedValues.get(i);
                if (enumValue.equals(lexeme)) {
                    return (double) i;
                }
            }
        }
        return value;
    }

    private List<String> findAllowedValues(DMNModelRepository repository, TDRGElement element, TDecisionTable decisionTable, int columnIndex) {
        List<TInputClause> input = decisionTable.getInput();
        if (input != null && input.size() > columnIndex) {
            TInputClause inputClause = input.get(columnIndex);
            TLiteralExpression inputExpression = inputClause.getInputExpression();
            if (inputExpression != null) {
                String typeRef = inputExpression.getTypeRef();
                if (!StringUtils.isBlank(typeRef)) {
                    TDefinitions model = repository.getModel(element);
                    TItemDefinition tItemDefinition = repository.lookupItemDefinition(model, QualifiedName.toQualifiedName(model, typeRef));
                    if (tItemDefinition != null) {
                        TUnaryTests allowedValues = tItemDefinition.getAllowedValues();
                        if (allowedValues != null) {
                            String text = allowedValues.getText();
                            if (!StringUtils.isBlank(text)) {
                                String[] split = text.split(",");
                                List<String> enumValues = new ArrayList<>();
                                for (String part: split) {
                                    if (part != null) {
                                        enumValues.add(part.trim());
                                    }
                                }
                                return enumValues;
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    private String getInputType(TDecisionTable decisionTable, int columnIndex) {
        List<TInputClause> input = decisionTable.getInput();
        String typeRef = "";
        if (input != null) {
            TInputClause inputClause = input.get(columnIndex);
            if (inputClause != null) {
                TLiteralExpression inputExpression = inputClause.getInputExpression();
                if (inputExpression != null) {
                    typeRef = inputExpression.getTypeRef();
                }
            }
        }
        return typeRef;
    }

    private boolean isNumberType(String currentColumnType) {
        return "number".equals(currentColumnType);
    }

    private boolean isBooleanType(String currentColumnType) {
        return "boolean".equals(currentColumnType);
    }

    private boolean isStringType(String currentColumnType) {
        return "string".equals(currentColumnType);
    }
}
