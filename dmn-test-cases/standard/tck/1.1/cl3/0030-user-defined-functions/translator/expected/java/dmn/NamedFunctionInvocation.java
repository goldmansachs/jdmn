
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "named function invocation"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "named function invocation",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class NamedFunctionInvocation extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "named function invocation",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public NamedFunctionInvocation() {
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("stringInputA"), input_.get("stringInputB"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'NamedFunctionInvocation'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((NamedFunctionInvocationInput_)input_).getStringInputA(), ((NamedFunctionInvocationInput_)input_).getStringInputB(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'NamedFunctionInvocation'", e);
            return null;
        }
    }

    public String apply(String stringInputA, String stringInputB, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'named function invocation'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long namedFunctionInvocationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments namedFunctionInvocationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            namedFunctionInvocationArguments_.put("stringInputA", stringInputA);
            namedFunctionInvocationArguments_.put("stringInputB", stringInputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, namedFunctionInvocationArguments_);

            // Evaluate decision 'named function invocation'
            String output_ = lambda.apply(stringInputA, stringInputB, context_);

            // End decision 'named function invocation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, namedFunctionInvocationArguments_, output_, (System.currentTimeMillis() - namedFunctionInvocationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'named function invocation' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String stringInputA = 0 < args_.length ? (String) args_[0] : null;
                String stringInputB = 1 < args_.length ? (String) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                com.gs.dmn.runtime.LambdaExpression<String> boxedFnDefinition = new com.gs.dmn.runtime.LambdaExpression<String>() {public String apply(Object... args_) {String a = (String)args_[0]; String b = (String)args_[1];return stringAdd(a, b);}};
                com.gs.dmn.runtime.LambdaExpression<String> literalFnDefinition = new com.gs.dmn.runtime.LambdaExpression<String>() {public String apply(Object... args_) {String a = (String)args_[0]; String b = (String)args_[1];return stringAdd(a, b);}};
                return stringAdd(boxedFnDefinition.apply(stringInputB, stringInputA, context_), literalFnDefinition.apply(stringInputB, stringInputA, context_));
            }
        };
}
