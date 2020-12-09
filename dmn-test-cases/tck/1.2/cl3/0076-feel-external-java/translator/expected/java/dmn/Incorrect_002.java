
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "incorrect_002"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "incorrect_002",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Incorrect_002 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "incorrect_002",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Incorrect_002() {
    }

    public java.math.BigDecimal apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'incorrect_002'
            long incorrect_002StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments incorrect_002Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, incorrect_002Arguments_);

            // Evaluate decision 'incorrect_002'
            java.math.BigDecimal output_ = evaluate(annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'incorrect_002'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, incorrect_002Arguments_, output_, (System.currentTimeMillis() - incorrect_002StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'incorrect_002' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        com.gs.dmn.runtime.external.JavaExternalFunction<java.math.BigDecimal> fooValueOf = new com.gs.dmn.runtime.external.JavaExternalFunction<>(new com.gs.dmn.runtime.external.JavaFunctionInfo("java.lang.Foo", "valueOf", Arrays.asList("double")), externalExecutor_, java.math.BigDecimal.class);
        return fooValueOf.apply(number("123"));
    }
}
