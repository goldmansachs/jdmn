
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'simple function invocation'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'simple function invocation'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class SimpleFunctionInvocation extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "'simple function invocation'",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public SimpleFunctionInvocation() {
    }

    public String apply(String stringInputA, String stringInputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision ''simple function invocation''
            long simpleFunctionInvocationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments simpleFunctionInvocationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            simpleFunctionInvocationArguments_.put("stringInputA", stringInputA);
            simpleFunctionInvocationArguments_.put("stringInputB", stringInputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, simpleFunctionInvocationArguments_);

            // Evaluate decision ''simple function invocation''
            String output_ = lambda.apply(stringInputA, stringInputB, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision ''simple function invocation''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, simpleFunctionInvocationArguments_, output_, (System.currentTimeMillis() - simpleFunctionInvocationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''simple function invocation'' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String stringInputA = 0 < args_.length ? (String) args_[0] : null;
                String stringInputB = 1 < args_.length ? (String) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 2 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[2] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 3 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[3] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 4 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[4] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 5 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[5] : null;

                com.gs.dmn.runtime.LambdaExpression<String> boxedFnDefinition = new com.gs.dmn.runtime.LambdaExpression<String>() {public String apply(Object... args_) {String a = (String)args_[0]; String b = (String)args_[1];return stringAdd(a, b);}};
                com.gs.dmn.runtime.LambdaExpression<String> literalFnDefinition = new com.gs.dmn.runtime.LambdaExpression<String>() {public String apply(Object... args_) {String a = (String)args_[0]; String b = (String)args_[1];return stringAdd(a, b);}};
                return stringAdd(boxedFnDefinition.apply(stringInputA, stringInputB, annotationSet_, eventListener_, externalExecutor_, cache_), literalFnDefinition.apply(stringInputA, stringInputB, annotationSet_, eventListener_, externalExecutor_, cache_));
            }
        };
}
