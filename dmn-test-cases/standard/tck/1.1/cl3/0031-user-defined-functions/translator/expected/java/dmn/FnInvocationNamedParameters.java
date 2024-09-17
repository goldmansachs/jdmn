
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "fn invocation named parameters"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "fn invocation named parameters",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FnInvocationNamedParameters extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "fn invocation named parameters",
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

    @java.lang.Override()
    public type.TFnInvocationNamedResult applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("inputA") != null ? number(input_.get("inputA")) : null), (input_.get("inputB") != null ? number(input_.get("inputB")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationNamedParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationNamedResult apply(java.lang.Number inputA, java.lang.Number inputB, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'fn invocation named parameters'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long fnInvocationNamedParametersStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fnInvocationNamedParametersArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            fnInvocationNamedParametersArguments_.put("inputA", inputA);
            fnInvocationNamedParametersArguments_.put("inputB", inputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationNamedParametersArguments_);

            // Evaluate decision 'fn invocation named parameters'
            type.TFnInvocationNamedResult output_ = lambda.apply(inputA, inputB, context_);

            // End decision 'fn invocation named parameters'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationNamedParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationNamedParametersStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'fn invocation named parameters' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<type.TFnInvocationNamedResult> lambda =
        new com.gs.dmn.runtime.LambdaExpression<type.TFnInvocationNamedResult>() {
            public type.TFnInvocationNamedResult apply(Object... args_) {
                java.lang.Number inputA = 0 < args_.length ? (java.lang.Number) args_[0] : null;
                java.lang.Number inputB = 1 < args_.length ? (java.lang.Number) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                type.TFnLibrary fnLibrary = FnInvocationNamedParameters.this.fnLibrary.apply(context_);

                java.lang.Number subResult = ((com.gs.dmn.runtime.LambdaExpression<java.lang.Number>)(fnLibrary != null ? fnLibrary.getSubFn() : null)).apply(inputA, inputB, context_);
                java.lang.Number multiplicationResultNamed = ((com.gs.dmn.runtime.LambdaExpression<java.lang.Number>)(fnLibrary != null ? fnLibrary.getMultiplyFn() : null)).apply(inputA, inputB, context_);
                java.lang.Number subResultMixed = ((com.gs.dmn.runtime.LambdaExpression<java.lang.Number>)(fnLibrary != null ? fnLibrary.getSubFn() : null)).apply(inputB, inputA, context_);
                java.lang.Number divisionResultNamed = ((com.gs.dmn.runtime.LambdaExpression<java.lang.Number>)(fnLibrary != null ? fnLibrary.getDivideFn() : null)).apply(inputA, inputB, context_);
                type.TFnInvocationNamedResultImpl fnInvocationNamedParameters = new type.TFnInvocationNamedResultImpl();
                fnInvocationNamedParameters.setSubResult(subResult);
                fnInvocationNamedParameters.setMultiplicationResultNamed(multiplicationResultNamed);
                fnInvocationNamedParameters.setSubResultMixed(subResultMixed);
                fnInvocationNamedParameters.setDivisionResultNamed(divisionResultNamed);
                return fnInvocationNamedParameters;
            }
        };
}
