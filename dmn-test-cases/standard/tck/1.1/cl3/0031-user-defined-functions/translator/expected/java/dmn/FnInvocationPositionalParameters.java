
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "fn invocation positional parameters"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "fn invocation positional parameters",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FnInvocationPositionalParameters extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<type.TFnInvocationPositionalResult> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "fn invocation positional parameters",
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

    @java.lang.Override()
    public type.TFnInvocationPositionalResult applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("inputA") != null ? number(input_.get("inputA")) : null), (input_.get("inputB") != null ? number(input_.get("inputB")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationPositionalParameters'", e);
            return null;
        }
    }

    @java.lang.Override()
    public type.TFnInvocationPositionalResult applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((FnInvocationPositionalParametersInput_)input_).getInputA(), ((FnInvocationPositionalParametersInput_)input_).getInputB(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FnInvocationPositionalParameters'", e);
            return null;
        }
    }

    @java.lang.Override()
    public type.TFnInvocationPositionalResult applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new FnInvocationPositionalParametersInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FnInvocationPositionalParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationPositionalResult apply(java.lang.Number inputA, java.lang.Number inputB, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'fn invocation positional parameters'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long fnInvocationPositionalParametersStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fnInvocationPositionalParametersArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            fnInvocationPositionalParametersArguments_.put("inputA", inputA);
            fnInvocationPositionalParametersArguments_.put("inputB", inputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationPositionalParametersArguments_);

            // Evaluate decision 'fn invocation positional parameters'
            type.TFnInvocationPositionalResult output_ = lambda.apply(inputA, inputB, context_);

            // End decision 'fn invocation positional parameters'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationPositionalParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationPositionalParametersStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'fn invocation positional parameters' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<type.TFnInvocationPositionalResult> lambda =
        new com.gs.dmn.runtime.LambdaExpression<type.TFnInvocationPositionalResult>() {
            public type.TFnInvocationPositionalResult apply(Object... args_) {
                java.lang.Number inputA = 0 < args_.length ? (java.lang.Number) args_[0] : null;
                java.lang.Number inputB = 1 < args_.length ? (java.lang.Number) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                type.TFnLibrary fnLibrary = FnInvocationPositionalParameters.this.fnLibrary.apply(context_);

                java.lang.Number sumResult = ((com.gs.dmn.runtime.LambdaExpression<java.lang.Number>)(fnLibrary != null ? fnLibrary.getSumFn() : null)).apply(inputA, inputB, context_);
                java.lang.Number multiplicationResultPositional = ((com.gs.dmn.runtime.LambdaExpression<java.lang.Number>)(fnLibrary != null ? fnLibrary.getMultiplyFn() : null)).apply(inputA, inputB, context_);
                java.lang.Number divisionResultPositional = ((com.gs.dmn.runtime.LambdaExpression<java.lang.Number>)(fnLibrary != null ? fnLibrary.getDivideFn() : null)).apply(inputA, inputB, context_);
                type.TFnInvocationPositionalResultImpl fnInvocationPositionalParameters = new type.TFnInvocationPositionalResultImpl();
                fnInvocationPositionalParameters.setSumResult(sumResult);
                fnInvocationPositionalParameters.setMultiplicationResultPositional(multiplicationResultPositional);
                fnInvocationPositionalParameters.setDivisionResultPositional(divisionResultPositional);
                return fnInvocationPositionalParameters;
            }
        };
}
