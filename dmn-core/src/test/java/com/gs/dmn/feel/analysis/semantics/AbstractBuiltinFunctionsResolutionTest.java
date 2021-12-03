package com.gs.dmn.feel.analysis.semantics;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.feel.analysis.FEELAnalyzerImpl;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionInvocation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.SimpleLiteral;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.LogicNegation;
import com.gs.dmn.runtime.DMNContext;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;

import static org.junit.Assert.*;

public abstract class AbstractBuiltinFunctionsResolutionTest {
    private final LazyEvaluationDetector lazyEvaluator = new NopLazyEvaluationDetector();
    private final BasicDMNToJavaTransformer basicTransformer = new StandardDMNDialectDefinition().createBasicTransformer(new DMNModelRepository(), lazyEvaluator, new InputParameters());
    private final FEELAnalyzerImpl feelAnalyzer = new FEELAnalyzerImpl(basicTransformer);
    private final DMNContext dmnContext = basicTransformer.makeBuiltInContext();

    protected final String numberString = "\"123.00\"";
    protected final String number = "3";
    protected final String stringString = "\"string\"";
    protected final String dateString = "\"2000-01-01\"";
    protected final String date = "date(2000, 1, 1)";
    protected final String timeString = "\"12:00:00\"";
    protected final String time = "time(12, 0, 0, 0)";
    protected final String dateTimeString = "\"2000-01-01T12:00:00\"";
    protected final String dateTime = "date and time(\"2000-01-01T12:00:00\")";
    protected final String yearsAndMonthsDurationString = "\"P1Y1M\"";
    protected final String daysAndTimeDurationString = "\"P1DT2H\"";
    protected final String yearsAndMonthsDuration = "duration(" + yearsAndMonthsDurationString + ")";
    protected final String daysAndTimeDuration = "duration(" + daysAndTimeDurationString + ")";

    protected final String numberSequence = "1, 2, 3";
    protected final String numberList = "[" + numberSequence + "]";
    protected final String booleanSequence = "true, true, true";
    protected final String booleanList = "[" + booleanSequence + "]";
    protected final String numberRange = "[1..2]";

    protected final String context = "{\"k\": 123}";
    protected final String contextValue = "\"123\"";

    protected void testFunctionInvocation(String text, String expectedType, boolean error) {
        try {
            Expression expression = feelAnalyzer.analyzeExpression(text, dmnContext);
            if (expression instanceof SimpleLiteral) {
                assertEquals(expectedType, expression.getClass().getSimpleName());
                assertFalse(error);
            } else if (expression instanceof LogicNegation) {
                assertEquals(expectedType, expression.getClass().getSimpleName());
                assertFalse(error);
            } else {
                FunctionInvocation functionInvocation = (FunctionInvocation) expression;
                Type actualType = functionInvocation.getFunction().getType();
                assertEquals(expectedType, actualType.toString());
            }
            assertFalse(error);
        } catch (Exception e) {
            assertTrue(text + " " + e.getMessage(), error);
        }
    }
}