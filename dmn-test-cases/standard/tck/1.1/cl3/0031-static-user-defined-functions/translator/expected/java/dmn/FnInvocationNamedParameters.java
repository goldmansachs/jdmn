
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'fn invocation named parameters'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'fn invocation named parameters'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FnInvocationNamedParameters extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "'fn invocation named parameters'",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final FnLibrary fnLibrary;

    public FnInvocationNamedParameters() {
        this(new FnLibrary());
    }

    public FnInvocationNamedParameters(FnLibrary fnLibrary) {
        this.fnLibrary = fnLibrary;
    }

    public type.TFnInvocationNamedResult apply(String inputA, String inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((inputA != null ? number(inputA) : null), (inputB != null ? number(inputB) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationNamedParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationNamedResult apply(String inputA, String inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((inputA != null ? number(inputA) : null), (inputB != null ? number(inputB) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationNamedParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationNamedResult apply(java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(inputA, inputB, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public type.TFnInvocationNamedResult apply(java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision ''fn invocation named parameters''
            long fnInvocationNamedParametersStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fnInvocationNamedParametersArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            fnInvocationNamedParametersArguments_.put("inputA", inputA);
            fnInvocationNamedParametersArguments_.put("inputB", inputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationNamedParametersArguments_);

            // Evaluate decision ''fn invocation named parameters''
            type.TFnInvocationNamedResult output_ = lambda.apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision ''fn invocation named parameters''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationNamedParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationNamedParametersStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''fn invocation named parameters'' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<type.TFnInvocationNamedResult> lambda =
        new com.gs.dmn.runtime.LambdaExpression<type.TFnInvocationNamedResult>() {
            public type.TFnInvocationNamedResult apply(Object... args) {
                java.math.BigDecimal inputA = 0 < args.length ? (java.math.BigDecimal) args[0] : null;
                java.math.BigDecimal inputB = 1 < args.length ? (java.math.BigDecimal) args[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 2 < args.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args[2] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 3 < args.length ? (com.gs.dmn.runtime.listener.EventListener) args[3] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 4 < args.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args[4] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 5 < args.length ? (com.gs.dmn.runtime.cache.Cache) args[5] : null;

                // Apply child decisions
                type.TFnLibrary fnLibrary = FnInvocationNamedParameters.this.fnLibrary.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

                java.math.BigDecimal subResult = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSubFn() : null)).apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_);
                java.math.BigDecimal multiplicationResultNamed = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getMultiplyFn() : null)).apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_);
                java.math.BigDecimal subResultMixed = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSubFn() : null)).apply(inputB, inputA, annotationSet_, eventListener_, externalExecutor_, cache_);
                java.math.BigDecimal divisionResultNamed = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getDivideFn() : null)).apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_);
                type.TFnInvocationNamedResultImpl fnInvocationNamedParameters = new type.TFnInvocationNamedResultImpl();
                fnInvocationNamedParameters.setSubResult(subResult);
                fnInvocationNamedParameters.setMultiplicationResultNamed(multiplicationResultNamed);
                fnInvocationNamedParameters.setSubResultMixed(subResultMixed);
                fnInvocationNamedParameters.setDivisionResultNamed(divisionResultNamed);
                return fnInvocationNamedParameters;
            }
        };
}
