
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

    @java.lang.Override()
    public java.math.BigDecimal applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("L1") != null ? number(input_.get("L1")) : null), (input_.get("L2") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("L2"), new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ProcessL2'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal l12_iterator, List<java.math.BigDecimal> l23, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'processL2'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long processL2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments processL2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            processL2Arguments_.put("L1", l12_iterator);
            processL2Arguments_.put("L2", l23);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, processL2Arguments_);

            // Iterate and aggregate
            java.math.BigDecimal output_ = evaluate(l12_iterator, l23, context_);

            // End decision 'processL2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, processL2Arguments_, output_, (System.currentTimeMillis() - processL2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'processL2' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal l12_iterator, List<java.math.BigDecimal> l23, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        CompareLists compareLists = new CompareLists();
        return sum(l23.stream().map(l2_iterator -> compareLists.apply(l12_iterator, l2_iterator, context_)).collect(Collectors.toList()));
    }
}
