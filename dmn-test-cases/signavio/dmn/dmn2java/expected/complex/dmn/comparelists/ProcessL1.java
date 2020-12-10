
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "processL1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "processL1",
    label = "ProcessL1",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ProcessL1 extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "processL1",
        "ProcessL1",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public ProcessL1() {
    }

    public List<java.math.BigDecimal> apply(String l1, String l23, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((l1 != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(l1, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (l23 != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(l23, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'ProcessL1'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(String l1, String l23, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((l1 != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(l1, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (l23 != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(l23, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ProcessL1'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(List<java.math.BigDecimal> l1, List<java.math.BigDecimal> l23, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(l1, l23, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<java.math.BigDecimal> apply(List<java.math.BigDecimal> l1, List<java.math.BigDecimal> l23, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'processL1'
            long processL1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments processL1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            processL1Arguments_.put("L1", l1);
            processL1Arguments_.put("L2", l23);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, processL1Arguments_);

            // Iterate and aggregate
            List<java.math.BigDecimal> output_ = evaluate(l1, l23, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'processL1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, processL1Arguments_, output_, (System.currentTimeMillis() - processL1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'processL1' evaluation", e);
            return null;
        }
    }

    protected List<java.math.BigDecimal> evaluate(List<java.math.BigDecimal> l1, List<java.math.BigDecimal> l23, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        ProcessL2 processL2 = new ProcessL2();
        return l1.stream().map(l12_iterator -> processL2.apply(l12_iterator, l23, annotationSet_, eventListener_, externalExecutor_, cache_)).collect(Collectors.toList());
    }
}
