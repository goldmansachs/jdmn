
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'fn invocation complex parameters'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'fn invocation complex parameters'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FnInvocationComplexParameters extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "'fn invocation complex parameters'",
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
            return apply((inputA != null ? number(inputA) : null), (inputB != null ? number(inputB) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationComplexParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationComplexParamsResult apply(String inputA, String inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((inputA != null ? number(inputA) : null), (inputB != null ? number(inputB) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationComplexParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationComplexParamsResult apply(java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(inputA, inputB, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public type.TFnInvocationComplexParamsResult apply(java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision ''fn invocation complex parameters''
            long fnInvocationComplexParametersStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fnInvocationComplexParametersArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            fnInvocationComplexParametersArguments_.put("inputA", inputA);
            fnInvocationComplexParametersArguments_.put("inputB", inputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationComplexParametersArguments_);

            // Evaluate decision ''fn invocation complex parameters''
            type.TFnInvocationComplexParamsResult output_ = lambda.apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision ''fn invocation complex parameters''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationComplexParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationComplexParametersStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''fn invocation complex parameters'' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<type.TFnInvocationComplexParamsResult> lambda =
        new com.gs.dmn.runtime.LambdaExpression<type.TFnInvocationComplexParamsResult>() {
            public type.TFnInvocationComplexParamsResult apply(Object... args) {
                java.math.BigDecimal inputA = 0 < args.length ? (java.math.BigDecimal) args[0] : null;
                java.math.BigDecimal inputB = 1 < args.length ? (java.math.BigDecimal) args[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 2 < args.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args[2] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 3 < args.length ? (com.gs.dmn.runtime.listener.EventListener) args[3] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 4 < args.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args[4] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 5 < args.length ? (com.gs.dmn.runtime.cache.Cache) args[5] : null;

                // Apply child decisions
                type.TFnLibrary fnLibrary = FnInvocationComplexParameters.this.fnLibrary.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

                java.math.BigDecimal functionInvocationInParameter = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getMultiplyFn() : null)).apply(((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSumFn() : null)).apply(inputA, inputA, annotationSet_, eventListener_, externalExecutor_, cache_), ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSumFn() : null)).apply(inputB, inputB, annotationSet_, eventListener_, externalExecutor_, cache_), annotationSet_, eventListener_, externalExecutor_, cache_);
                java.math.BigDecimal functionInvocationLiteralExpressionInParameter = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getMultiplyFn() : null)).apply(numericMultiply(inputA, inputA), (booleanEqual((booleanAnd(numericGreaterEqualThan(((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSubFn() : null)).apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_), number("0")), numericLessEqualThan(((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSubFn() : null)).apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_), number("10")))), Boolean.TRUE)) ? number("5") : number("10"), annotationSet_, eventListener_, externalExecutor_, cache_);
                java.math.BigDecimal circumference = Circumference.instance().apply(numericAdd(inputA, inputB), annotationSet_, eventListener_, externalExecutor_, cache_);
                type.TFnInvocationComplexParamsResultImpl fnInvocationComplexParameters = new type.TFnInvocationComplexParamsResultImpl();
                fnInvocationComplexParameters.setFunctionInvocationInParameter(functionInvocationInParameter);
                fnInvocationComplexParameters.setFunctionInvocationLiteralExpressionInParameter(functionInvocationLiteralExpressionInParameter);
                fnInvocationComplexParameters.setCircumference(circumference);
                return fnInvocationComplexParameters;
            }
        };
}
