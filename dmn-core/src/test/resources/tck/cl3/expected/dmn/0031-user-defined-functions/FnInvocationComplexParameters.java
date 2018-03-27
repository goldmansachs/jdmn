
import java.util.*;
import java.util.stream.Collectors;

import static Circumference.Circumference;

@javax.annotation.Generated(value = {"decision.ftl", "fnInvocationComplexParameters"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "fnInvocationComplexParameters",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FnInvocationComplexParameters extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "fnInvocationComplexParameters",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final FnLibrary fnLibrary;

    public FnInvocationComplexParameters() {
        this(new FnLibrary());
    }

    public FnInvocationComplexParameters(FnLibrary fnLibrary) {
        this.fnLibrary = fnLibrary;
    }

    public type.TFnInvocationComplexParamsResult apply(String inputA, String inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((inputA != null ? number(inputA) : null), (inputB != null ? number(inputB) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationComplexParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationComplexParamsResult apply(String inputA, String inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((inputA != null ? number(inputA) : null), (inputB != null ? number(inputB) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationComplexParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationComplexParamsResult apply(java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(inputA, inputB, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public type.TFnInvocationComplexParamsResult apply(java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'fnInvocationComplexParameters'
            long fnInvocationComplexParametersStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fnInvocationComplexParametersArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            fnInvocationComplexParametersArguments_.put("inputA", inputA);
            fnInvocationComplexParametersArguments_.put("inputB", inputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationComplexParametersArguments_);

            // Apply child decisions
            type.TFnLibrary fnLibrary = this.fnLibrary.apply(annotationSet_, eventListener_, externalExecutor_);

            // Evaluate decision 'fnInvocationComplexParameters'
            type.TFnInvocationComplexParamsResult output_ = evaluate(fnLibrary, inputA, inputB, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'fnInvocationComplexParameters'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationComplexParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationComplexParametersStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'fnInvocationComplexParameters' evaluation", e);
            return null;
        }
    }

    private type.TFnInvocationComplexParamsResult evaluate(type.TFnLibrary fnLibrary, java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        java.math.BigDecimal functionInvocationInParameter = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getMultiplyFn() : null)).apply(((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSumFn() : null)).apply(inputA, inputA), ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSumFn() : null)).apply(inputB, inputB));
        java.math.BigDecimal functionInvocationLiteralExpressionInParameter = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getMultiplyFn() : null)).apply(numericMultiply(inputA, inputA), (booleanEqual((booleanAnd(numericGreaterEqualThan(((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSubFn() : null)).apply(inputA, inputB), number("0")), numericLessEqualThan(((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSubFn() : null)).apply(inputA, inputB), number("10")))), Boolean.TRUE)) ? number("5") : number("10"));
        java.math.BigDecimal circumference = Circumference(numericAdd(inputA, inputB), annotationSet_, eventListener_, externalExecutor_);
        type.TFnInvocationComplexParamsResultImpl fnInvocationComplexParameters = new type.TFnInvocationComplexParamsResultImpl();
        fnInvocationComplexParameters.setFunctionInvocationInParameter(functionInvocationInParameter);
        fnInvocationComplexParameters.setFunctionInvocationLiteralExpressionInParameter(functionInvocationLiteralExpressionInParameter);
        fnInvocationComplexParameters.setCircumference(circumference);
        return fnInvocationComplexParameters;
    }
}
