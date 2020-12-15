
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "loop"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "loop",
    label = "Loop",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Loop extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "loop",
        "Loop",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Zip1 zip1;

    public Loop() {
        this(new Zip1());
    }

    public Loop(Zip1 zip1) {
        this.zip1 = zip1;
    }

    public List<java.math.BigDecimal> apply(String a4, String b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((a4 != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(a4, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (b != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(b, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Loop'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(String a4, String b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((a4 != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(a4, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (b != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(b, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Loop'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(List<java.math.BigDecimal> a4, List<java.math.BigDecimal> b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(a4, b, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<java.math.BigDecimal> apply(List<java.math.BigDecimal> a4, List<java.math.BigDecimal> b, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'loop'
            long loopStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments loopArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            loopArguments_.put("A", a4);
            loopArguments_.put("B", b);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, loopArguments_);

            // Apply child decisions
            List<type.Zip1> zip1 = this.zip1.apply(a4, b, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Iterate and aggregate
            List<java.math.BigDecimal> output_ = evaluate(a4, b, zip1, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'loop'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, loopArguments_, output_, (System.currentTimeMillis() - loopStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'loop' evaluation", e);
            return null;
        }
    }

    protected List<java.math.BigDecimal> evaluate(List<java.math.BigDecimal> a4, List<java.math.BigDecimal> b, List<type.Zip1> zip1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        Body body = new Body();
        return zip1.stream().map(it_iterator -> body.apply(type.It.toIt(it_iterator), annotationSet_, eventListener_, externalExecutor_, cache_)).collect(Collectors.toList());
    }
}
