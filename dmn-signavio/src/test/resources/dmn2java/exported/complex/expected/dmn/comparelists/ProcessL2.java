
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "processL2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "processL2",
    label = "ProcessL2",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ProcessL2 extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "processL2",
        "ProcessL2",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public ProcessL2() {
    }

    public java.math.BigDecimal apply(String l12_iterator, String l23, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((l12_iterator != null ? number(l12_iterator) : null), (l23 != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(l23, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'ProcessL2'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String l12_iterator, String l23, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((l12_iterator != null ? number(l12_iterator) : null), (l23 != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(l23, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ProcessL2'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal l12_iterator, List<java.math.BigDecimal> l23, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(l12_iterator, l23, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal l12_iterator, List<java.math.BigDecimal> l23, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'processL2'
            long processL2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments processL2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            processL2Arguments_.put("L1", l12_iterator);
            processL2Arguments_.put("L2", l23);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, processL2Arguments_);

            // Iterate and aggregate
            java.math.BigDecimal output_ = evaluate(l12_iterator, l23, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'processL2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, processL2Arguments_, output_, (System.currentTimeMillis() - processL2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'processL2' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal l12_iterator, List<java.math.BigDecimal> l23, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        CompareLists compareLists = new CompareLists();
        return sum(l23.stream().map(l2_iterator -> compareLists.apply(l12_iterator, l2_iterator, annotationSet_, eventListener_, externalExecutor_, cache_)).collect(Collectors.toList()));
    }
}
