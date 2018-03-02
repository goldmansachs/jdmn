
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "namedFunctionInvocation"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "namedFunctionInvocation",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class NamedFunctionInvocation extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "namedFunctionInvocation",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public NamedFunctionInvocation() {
    }

    public String apply(String stringInputA, String stringInputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(stringInputA, stringInputB, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public String apply(String stringInputA, String stringInputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'namedFunctionInvocation'
            long namedFunctionInvocationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments namedFunctionInvocationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            namedFunctionInvocationArguments_.put("stringInputA", stringInputA);
            namedFunctionInvocationArguments_.put("stringInputB", stringInputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, namedFunctionInvocationArguments_);

            // Evaluate decision 'namedFunctionInvocation'
            String output_ = evaluate(stringInputA, stringInputB, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'namedFunctionInvocation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, namedFunctionInvocationArguments_, output_, (System.currentTimeMillis() - namedFunctionInvocationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'namedFunctionInvocation' evaluation", e);
            return null;
        }
    }

    private String evaluate(String stringInputA, String stringInputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        com.gs.dmn.runtime.LambdaExpression<String> boxedFnDefinition = new com.gs.dmn.runtime.LambdaExpression<String>() {public String apply(Object... args) {String a = (String)args[0]; String b = (String)args[1];return stringAdd(a, b);}};
        com.gs.dmn.runtime.LambdaExpression<String> literalFnDefinition = new com.gs.dmn.runtime.LambdaExpression<String>() {public String apply(Object... args) {String a = (String)args[0]; String b = (String)args[1];return stringAdd(a, b);}};
        return stringAdd(boxedFnDefinition.apply(stringInputB, stringInputA), literalFnDefinition.apply(stringInputB, stringInputA));
    }
}
