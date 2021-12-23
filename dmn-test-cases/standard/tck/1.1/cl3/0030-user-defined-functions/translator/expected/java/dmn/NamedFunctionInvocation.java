
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'named function invocation'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'named function invocation'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class NamedFunctionInvocation extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "'named function invocation'",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public NamedFunctionInvocation() {
    }

    public String apply(String stringInputA, String stringInputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(stringInputA, stringInputB, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(String stringInputA, String stringInputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision ''named function invocation''
            long namedFunctionInvocationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments namedFunctionInvocationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            namedFunctionInvocationArguments_.put("stringInputA", stringInputA);
            namedFunctionInvocationArguments_.put("stringInputB", stringInputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, namedFunctionInvocationArguments_);

            // Evaluate decision ''named function invocation''
            String output_ = lambda.apply(stringInputA, stringInputB, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision ''named function invocation''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, namedFunctionInvocationArguments_, output_, (System.currentTimeMillis() - namedFunctionInvocationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''named function invocation'' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args) {
                String stringInputA = 0 < args.length ? (String) args[0] : null;
                String stringInputB = 1 < args.length ? (String) args[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 2 < args.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args[2] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 3 < args.length ? (com.gs.dmn.runtime.listener.EventListener) args[3] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 4 < args.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args[4] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 5 < args.length ? (com.gs.dmn.runtime.cache.Cache) args[5] : null;

                com.gs.dmn.runtime.LambdaExpression<String> boxedFnDefinition = new com.gs.dmn.runtime.LambdaExpression<String>() {public String apply(Object... args) {String a = (String)args[0]; String b = (String)args[1];return stringAdd(a, b);}};
                com.gs.dmn.runtime.LambdaExpression<String> literalFnDefinition = new com.gs.dmn.runtime.LambdaExpression<String>() {public String apply(Object... args) {String a = (String)args[0]; String b = (String)args[1];return stringAdd(a, b);}};
                return stringAdd(boxedFnDefinition.apply(stringInputB, stringInputA, annotationSet_, eventListener_, externalExecutor_, cache_), literalFnDefinition.apply(stringInputB, stringInputA, annotationSet_, eventListener_, externalExecutor_, cache_));
            }
        };
}
