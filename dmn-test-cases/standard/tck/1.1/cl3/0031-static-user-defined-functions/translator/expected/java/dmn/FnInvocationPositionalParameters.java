
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'fn invocation positional parameters'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'fn invocation positional parameters'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FnInvocationPositionalParameters extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "'fn invocation positional parameters'",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final FnLibrary fnLibrary;

    public FnInvocationPositionalParameters() {
        this(new FnLibrary());
    }

    public FnInvocationPositionalParameters(FnLibrary fnLibrary) {
        this.fnLibrary = fnLibrary;
    }

    public type.TFnInvocationPositionalResult apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("inputA"), input_.get("inputB"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationPositionalParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationPositionalResult apply(String inputA, String inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((inputA != null ? number(inputA) : null), (inputB != null ? number(inputB) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationPositionalParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationPositionalResult apply(java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision ''fn invocation positional parameters''
            long fnInvocationPositionalParametersStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fnInvocationPositionalParametersArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            fnInvocationPositionalParametersArguments_.put("inputA", inputA);
            fnInvocationPositionalParametersArguments_.put("inputB", inputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationPositionalParametersArguments_);

            // Evaluate decision ''fn invocation positional parameters''
            type.TFnInvocationPositionalResult output_ = lambda.apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision ''fn invocation positional parameters''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationPositionalParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationPositionalParametersStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''fn invocation positional parameters'' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<type.TFnInvocationPositionalResult> lambda =
        new com.gs.dmn.runtime.LambdaExpression<type.TFnInvocationPositionalResult>() {
            public type.TFnInvocationPositionalResult apply(Object... args_) {
                java.math.BigDecimal inputA = 0 < args_.length ? (java.math.BigDecimal) args_[0] : null;
                java.math.BigDecimal inputB = 1 < args_.length ? (java.math.BigDecimal) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 2 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[2] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 3 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[3] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 4 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[4] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 5 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[5] : null;

                // Apply child decisions
                type.TFnLibrary fnLibrary = FnInvocationPositionalParameters.this.fnLibrary.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

                java.math.BigDecimal sumResult = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSumFn() : null)).apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_);
                java.math.BigDecimal multiplicationResultPositional = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getMultiplyFn() : null)).apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_);
                java.math.BigDecimal divisionResultPositional = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getDivideFn() : null)).apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_);
                type.TFnInvocationPositionalResultImpl fnInvocationPositionalParameters = new type.TFnInvocationPositionalResultImpl();
                fnInvocationPositionalParameters.setSumResult(sumResult);
                fnInvocationPositionalParameters.setMultiplicationResultPositional(multiplicationResultPositional);
                fnInvocationPositionalParameters.setDivisionResultPositional(divisionResultPositional);
                return fnInvocationPositionalParameters;
            }
        };
}
