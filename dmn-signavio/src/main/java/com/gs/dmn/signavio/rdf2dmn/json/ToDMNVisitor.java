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
package com.gs.dmn.signavio.rdf2dmn.json;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.type.FEELTypes;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.dialect.SignavioDMNDialectDefinition;
import com.gs.dmn.signavio.rdf2dmn.RDFModel;
import com.gs.dmn.signavio.rdf2dmn.json.decision.DecisionTable;
import com.gs.dmn.signavio.rdf2dmn.json.decision.FreeTextExpression;
import com.gs.dmn.signavio.rdf2dmn.json.decision.LiteralExpression;
import com.gs.dmn.signavio.rdf2dmn.json.expression.*;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.semantics.type.DateTimeType.DATE_AND_TIME;
import static com.gs.dmn.feel.analysis.semantics.type.DateType.DATE;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.DAYS_AND_TIME_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.DurationType.YEARS_AND_MONTHS_DURATION;
import static com.gs.dmn.feel.analysis.semantics.type.StringType.STRING;
import static com.gs.dmn.feel.analysis.semantics.type.TimeType.TIME;

public class ToDMNVisitor implements Visitor {
    protected static final Map<String, String> TO_FEEL_OPERATOR = new HashMap<>();
    static {
        TO_FEEL_OPERATOR.put("and", "and");
        TO_FEEL_OPERATOR.put("or", "or");

        TO_FEEL_OPERATOR.put("less", "<");
        TO_FEEL_OPERATOR.put("lessOrEqual", "<=");
        TO_FEEL_OPERATOR.put("greater", ">");
        TO_FEEL_OPERATOR.put("greaterOrEqual", ">=");

        TO_FEEL_OPERATOR.put("equal", "=");

        TO_FEEL_OPERATOR.put("add", "+");
        TO_FEEL_OPERATOR.put("subtract", "-");
        TO_FEEL_OPERATOR.put("multiply", "*");
        TO_FEEL_OPERATOR.put("divide", "/");
    }

    private final RDFModel rdfModel;
    private final DMNDialectDefinition dialectDefinition;
    private final BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer;

    public ToDMNVisitor(RDFModel rdfModel, InputParameters inputParameters) {
        this.dialectDefinition = new SignavioDMNDialectDefinition();
        this.dmnTransformer = dialectDefinition.createBasicTransformer(new SignavioDMNModelRepository(), new NopLazyEvaluationDetector(), inputParameters);
        this.rdfModel = rdfModel;
    }

    @Override
    public String visit(List element, Context context) {
        String list = element.getValue().stream().map(ie -> ie.accept(this, context)).collect(Collectors.joining(", "));
        return "[%s]".formatted(list);
    }

    @Override
    public String visit(SimpleLiteral element, Context context) {
        String typeName = element.getType();
        Type type = FEELTypes.FEEL_NAME_TO_FEEL_TYPE.get(typeName);
        if (STRING == type) {
            return "%s".formatted(element.getValue());
        } else if (DATE == type) {
            return "date(%s)".formatted(element.getValue());
        } else if (TIME == type) {
            return "time(%s)".formatted(element.getValue());
        } else if (DATE_AND_TIME == type) {
            return "date and time(%s)".formatted(element.getValue());
        } else if (DAYS_AND_TIME_DURATION == type || YEARS_AND_MONTHS_DURATION == type) {
            return "duration(%s)".formatted(element.getValue());
        } else {
            return "%s".formatted(element.getValue());
        }
    }

    @Override
    public String visit(DecisionTable element, Context params) {
        throw new UnsupportedOperationException("%s not supported yet".formatted(element.getClass().getSimpleName()));
    }

    @Override
    public String visit(FreeTextExpression element, Context params) {
        throw new UnsupportedOperationException("%s not supported yet".formatted(element.getClass().getSimpleName()));
    }

    @Override
    public String visit(LiteralExpression element, Context params) {
        return element.getText().accept(this, params);
    }

    @Override
    public String visit(Logical element, Context params) {
        String leftOperandText = element.getLeftOperand().accept(this, params);
        String rightOperandText = element.getRightOperand().accept(this, params);
        String feelOperator = TO_FEEL_OPERATOR.get(element.getOperator());
        if (feelOperator == null) {
            throw new DMNRuntimeException("Cannot find FEEL operator for '%s'".formatted(element.getOperator()));
        }
        return "(%s) %s (%s)".formatted(leftOperandText, feelOperator, rightOperandText);
    }

    @Override
    public String visit(Comparison element, Context params) {
        String leftOperandText = element.getLeftOperand().accept(this, params);
        String rightOperandText = element.getRightOperand().accept(this, params);
        String feelOperator = TO_FEEL_OPERATOR.get(element.getOperator());
        if (feelOperator == null) {
            throw new DMNRuntimeException("Cannot find FEEL operator for '%s'".formatted(element.getOperator()));
        }
        return "(%s) %s (%s)".formatted(leftOperandText, feelOperator, rightOperandText);
    }

    @Override
    public String visit(Arithmetic element, Context params) {
        String leftOperandText = element.getLeftOperand().accept(this, params);
        String rightOperandText = element.getRightOperand().accept(this, params);
        String feelOperator = TO_FEEL_OPERATOR.get(element.getOperator());
        if (feelOperator == null) {
            throw new DMNRuntimeException("Cannot find FEEL operator for '%s'".formatted(element.getOperator()));
        }
        return "(%s) %s (%s)".formatted(leftOperandText, feelOperator, rightOperandText);
    }

    @Override
    public String visit(ArithmeticNegation element, Context context) {
        Expression operand = element.getOperand();
        return "- (%s)".formatted(operand.accept(this, context));
    }

    @Override
    public String visit(FunctionCall functionCall, Context context) {
        String functionId = functionCall.getFunctionId();
        java.util.List<Expression> parameters = functionCall.getParameters();
        String argList = parameters.stream().map(r -> r.accept(this, context)).collect(Collectors.joining(", "));
        return "%s(%s)".formatted(functionId, argList);
    }


    @Override
    public String visit(Reference reference, Context context) {
        String shapeId = reference.getShapeId();
        java.util.List<String> pathElements = reference.getPathElements();
        Element description = rdfModel.findDescriptionById(shapeId);
        String root = dmnTransformer.nativeFriendlyVariableName(rdfModel.getName(description));
        if (pathElements == null || pathElements.isEmpty() || rdfModel.hasSingleOutput(shapeId)) {
            return "%s".formatted(root);
        } else {
            String path = pathElements.stream().map(pe -> dmnTransformer.nativeFriendlyVariableName(rdfModel.pathName(description, pe))).collect(Collectors.joining("."));
            return "%s.%s".formatted(root, path);
        }
    }

    @Override
    public String visit(Any element, Context context) {
        return "-";
    }

    @Override
    public String visit(Disjunction element, Context context) {
        return element.getValue().stream().map(ie -> ie.accept(this, context)).collect(Collectors.joining(", "));
    }

    @Override
    public String visit(Enumeration element, Context context) {
        return ((FeelContext)context).findEnumerator(element.getItemId());
    }

    @Override
    public String visit(Hierarchy element, Context context) {
        return "TODO";
    }

    @Override
    public String visit(Interval element, Context context) {
        String leftBrace = element.isOpenStart() ? "(" : "[";
        String rightBrace = element.isOpenEnd() ? ")" : "]";
        return "%s%s..%s%s".formatted(leftBrace, element.getStart().accept(this, context), element.getEnd().accept(this, context), rightBrace);
    }

    @Override
    public String visit(Negation element, Context context) {
        Expression operand = element.getOperand();
        return "not (%s)".formatted(operand.accept(this, context));
    }

    @Override
    public String visit(UnaryComparison element, Context context) {
        String rdfOperator = element.getOperator();
        String operator = TO_FEEL_OPERATOR.get(rdfOperator);
        if (operator == null) {
            throw new DMNRuntimeException("Cannot find FEEL operator for " + rdfOperator);
        }
        return "%s %s".formatted(operator, element.getOperand().accept(this, context));
    }

    @Override
    public String visit(UnaryTestFunctionCall element, Context context) {
        java.util.List<Expression> parameters = element.getParameters();
        if (parameters != null && !parameters.isEmpty()) {
            Expression inputEntry = parameters.get(0);
            String args = parameters.stream().map(ie -> ie.accept(this, context)).collect(Collectors.joining(","));
            if (inputEntry instanceof Disjunction) {
                return "%s(?, [%s])".formatted(element.getFunctionId(), args);
            } else {
                return "%s(?, %s)".formatted(element.getFunctionId(), args);
            }
        } else {
            return "%s(?)".formatted(element.getFunctionId());
        }
    }

    @Override
    public String visit(DataAcceptance dataAcceptance, Context context) {
        String functionId = dataAcceptance.getValue();
        return "%s(?)".formatted(functionId);
    }
}
