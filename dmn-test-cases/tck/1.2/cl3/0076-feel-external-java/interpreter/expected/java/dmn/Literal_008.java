
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "literal_008"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "literal_008",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Literal_008 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "literal_008",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Literal_008() {
    }

    public java.math.BigDecimal apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'literal_008'
            long literal_008StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments literal_008Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, literal_008Arguments_);

            // Evaluate decision 'literal_008'
            java.math.BigDecimal output_ = evaluate(annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'literal_008'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, literal_008Arguments_, output_, (System.currentTimeMillis() - literal_008StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'literal_008' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        com.gs.dmn.runtime.external.JavaExternalFunction<java.math.BigDecimal> max = new com.gs.dmn.runtime.external.JavaExternalFunction<>(new com.gs.dmn.runtime.external.JavaFunctionInfo("java.lang.Math", "max", Arrays.asList("long", "long")), externalExecutor_, java.math.BigDecimal.class);
        return max.apply(number("123"), number("456"));
    }
}
