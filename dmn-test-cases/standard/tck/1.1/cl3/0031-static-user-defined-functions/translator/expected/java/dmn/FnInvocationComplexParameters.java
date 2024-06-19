
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "fn invocation complex parameters"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "fn invocation complex parameters",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FnInvocationComplexParameters extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "fn invocation complex parameters",
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

    @java.lang.Override()
    public type.TFnInvocationComplexParamsResult applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("inputA") != null ? number(input_.get("inputA")) : null), (input_.get("inputB") != null ? number(input_.get("inputB")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationComplexParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationComplexParamsResult apply(java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'fn invocation complex parameters'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long fnInvocationComplexParametersStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fnInvocationComplexParametersArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            fnInvocationComplexParametersArguments_.put("inputA", inputA);
            fnInvocationComplexParametersArguments_.put("inputB", inputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationComplexParametersArguments_);

            // Evaluate decision 'fn invocation complex parameters'
            type.TFnInvocationComplexParamsResult output_ = lambda.apply(inputA, inputB, context_);

            // End decision 'fn invocation complex parameters'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationComplexParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationComplexParametersStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'fn invocation complex parameters' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<type.TFnInvocationComplexParamsResult> lambda =
        new com.gs.dmn.runtime.LambdaExpression<type.TFnInvocationComplexParamsResult>() {
            public type.TFnInvocationComplexParamsResult apply(Object... args_) {
                java.math.BigDecimal inputA = 0 < args_.length ? (java.math.BigDecimal) args_[0] : null;
                java.math.BigDecimal inputB = 1 < args_.length ? (java.math.BigDecimal) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                type.TFnLibrary fnLibrary = FnInvocationComplexParameters.this.fnLibrary.apply(context_);

                java.math.BigDecimal functionInvocationInParameter = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getMultiplyFn() : null)).apply(((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSumFn() : null)).apply(inputA, inputA, context_), ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSumFn() : null)).apply(inputB, inputB, context_), context_);
                java.math.BigDecimal functionInvocationLiteralExpressionInParameter = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getMultiplyFn() : null)).apply(numericMultiply(inputA, inputA), (booleanEqual(booleanAnd(numericGreaterEqualThan(((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSubFn() : null)).apply(inputA, inputB, context_), number("0")), numericLessEqualThan(((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSubFn() : null)).apply(inputA, inputB, context_), number("10"))), Boolean.TRUE)) ? number("5") : number("10"), context_);
                java.math.BigDecimal circumference = Circumference.instance().apply(numericAdd(inputA, inputB), context_);
                type.TFnInvocationComplexParamsResultImpl fnInvocationComplexParameters = new type.TFnInvocationComplexParamsResultImpl();
                fnInvocationComplexParameters.setFunctionInvocationInParameter(functionInvocationInParameter);
                fnInvocationComplexParameters.setFunctionInvocationLiteralExpressionInParameter(functionInvocationLiteralExpressionInParameter);
                fnInvocationComplexParameters.setCircumference(circumference);
                return fnInvocationComplexParameters;
            }
        };
}
