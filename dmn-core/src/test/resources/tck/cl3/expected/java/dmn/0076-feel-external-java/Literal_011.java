
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "literal_011"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "literal_011",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Literal_011 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "literal_011",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Literal_011() {
    }

    public Object apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Object apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'literal_011'
            long literal_011StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments literal_011Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, literal_011Arguments_);

            // Evaluate decision 'literal_011'
            Object output_ = evaluate(annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'literal_011'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, literal_011Arguments_, output_, (System.currentTimeMillis() - literal_011StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'literal_011' evaluation", e);
            return null;
        }
    }

    protected Object evaluate(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        com.gs.dmn.runtime.external.JavaExternalFunction<Object> valueOf = new com.gs.dmn.runtime.external.JavaExternalFunction<>(new com.gs.dmn.runtime.external.JavaFunctionInfo("java.lang.Float", "valueOf", Arrays.asList("java.lang.String")), externalExecutor_, Object.class);
        return valueOf.apply("1234.56");
    }
}
