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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BoundList {
    private static final BigDecimal MINUS_INFINITY = new BigDecimal(-Double.MAX_VALUE);
    private static final BigDecimal PLUS_INFINITY = new BigDecimal(Double.MAX_VALUE);
    private static final BigDecimal DELTA = new BigDecimal("0.00001");

    private final List<Bound> bounds = new ArrayList<>();
    private boolean canProject = true;

    public BoundList(DMNModelRepository repository, TDRGElement element, TDecisionTable decisionTable, List<Integer> comparingRules, int columnIndex, FEELTranslator feelTranslator) {
        for (int ruleIndex: comparingRules) {
            TDecisionRule rule = decisionTable.getRule().get(ruleIndex);
            List<TUnaryTests> inputEntry = rule.getInputEntry();
            TUnaryTests cell = inputEntry.get(columnIndex);
            Interval interval = makeInterval(repository, element, decisionTable, columnIndex, rule, ruleIndex, cell, feelTranslator);
            if (interval == null) {
                canProject = false;
                break;
            } else {
                this.bounds.add(interval.getLowerBound());
                this.bounds.add(interval.getUpperBound());
            }
        }
    }

    public List<Bound> getBounds() {
        return bounds;
    }

    public boolean isCanProject() {
        return canProject;
    }

    private Interval makeInterval(DMNModelRepository repository, TDRGElement element, TDecisionTable decisionTable, int columnIndex, TDecisionRule rule, int ruleIndex, TUnaryTests cell, FEELTranslator feelTranslator) {
        if (cell == null) {
            return null;
        }

        // Parse unary tests
        String text = cell.getText();
        UnaryTests unaryTests = feelTranslator.parseUnaryTests(text);
        if (unaryTests instanceof Any) {
            return new Interval(ruleIndex, false, MINUS_INFINITY, false, PLUS_INFINITY);
        } else if (unaryTests instanceof PositiveUnaryTests) {
            List<PositiveUnaryTest> positiveUnaryTests = ((PositiveUnaryTests) unaryTests).getPositiveUnaryTests();
            // Check simple expressions only
            if (positiveUnaryTests.size() == 1) {
                PositiveUnaryTest positiveUnaryTest = positiveUnaryTests.get(0);
                // Check intervals
                if (positiveUnaryTest instanceof EndpointsRange) {
                    EndpointsRange astRange = (EndpointsRange) positiveUnaryTest;
                    BigDecimal startValue = makeBoundValue(repository, element, decisionTable, columnIndex, astRange.getStart());
                    BigDecimal endValue = makeBoundValue(repository, element, decisionTable, columnIndex, astRange.getEnd());
                    if (startValue != null && endValue != null) {
                        boolean openEnd = astRange.isOpenEnd();
                        boolean openStart = astRange.isOpenStart();
                        if (openStart) {
                            startValue = startValue.add(DELTA);
                        }
                        if (openEnd) {
                            endValue = endValue.subtract(DELTA);
                        }
                        return new Interval(ruleIndex, false, startValue, false, endValue);
                    }
                } else if (positiveUnaryTest instanceof OperatorRange) {
                    OperatorRange operatorRange = (OperatorRange) positiveUnaryTest;
                    BigDecimal value = makeBoundValue(repository, element, decisionTable, columnIndex, operatorRange.getEndpoint());
                    if (value != null) {
                        String operator = operatorRange.getOperator();
                        String columnInputType = getInputType(decisionTable, columnIndex);
                        if (isNumberType(columnInputType)) {
                            if (operator == null) {
                                return new Interval(ruleIndex, false, value, false, value);
                            } else if ("<".equals(operator)) {
                                return new Interval(ruleIndex, false, MINUS_INFINITY, false, value.subtract(DELTA));
                            } else if ("<=".equals(operator)) {
                                return new Interval(ruleIndex, false, MINUS_INFINITY, false, value);
                            } else if (">".equals(operator)) {
                                return new Interval(ruleIndex, false, value.add(DELTA), false, PLUS_INFINITY);
                            } else if (">=".equals(operator)) {
                                return new Interval(ruleIndex, false, value, false, PLUS_INFINITY);
                            }
                        } else if (isBooleanType(columnInputType)) {
                            if (operator == null) {
                                return new Interval(ruleIndex, false, value, false, value);
                            }
                        } else {
                            // Lookup for enumerations
                            TDefinitions model = repository.getModel(element);
                            TItemDefinition itemDefinition = repository.lookupItemDefinition(model, QualifiedName.toQualifiedName(model, columnInputType));
                            if (itemDefinition != null) {
                                String typeRef = itemDefinition.getTypeRef();
                                if (isStringType(typeRef)) {
                                    return new Interval(ruleIndex, false, value, false, value);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private BigDecimal makeBoundValue(DMNModelRepository repository, TDRGElement element, TDecisionTable decisionTable, int columnIndex, Expression exp) {
        BigDecimal value = null;
        if (exp instanceof NumericLiteral) {
            String lexeme = ((SimpleLiteral) exp).getLexeme();
            value = new BigDecimal(lexeme);
        } else if (exp instanceof BooleanLiteral) {
            String lexeme = ((SimpleLiteral) exp).getLexeme();
            boolean bValue = Boolean.parseBoolean(lexeme);
            value = bValue ? BigDecimal.ONE : BigDecimal.ZERO;
        } else if (exp instanceof StringLiteral) {
            String lexeme = ((SimpleLiteral) exp).getLexeme();
            List<String> allowedValues = findAllowedValues(repository, element, decisionTable, columnIndex);
            for (int i=0; i<allowedValues.size(); i++) {
                String enumValue = allowedValues.get(i);
                if (enumValue.equals(lexeme)) {
                    return new BigDecimal(i);
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
        return null;
    }

    public void sort() {
        this.bounds.sort(Bound::compareTo);
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
    };

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
