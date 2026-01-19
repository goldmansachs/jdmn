
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
public class ProcessL1 extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<List<java.lang.Number>> {
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

    @java.lang.Override()
    public List<java.lang.Number> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("L1") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("L1"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), (input_.get("L2") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("L2"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ProcessL1'", e);
            return null;
        }
    }

    public List<java.lang.Number> apply(List<java.lang.Number> l1, List<java.lang.Number> l23, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'processL1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long processL1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments processL1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            processL1Arguments_.put("L1", l1);
            processL1Arguments_.put("L2", l23);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, processL1Arguments_);

            // Iterate and aggregate
            List<java.lang.Number> output_ = evaluate(l1, l23, context_);

            // End decision 'processL1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, processL1Arguments_, output_, (System.currentTimeMillis() - processL1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'processL1' evaluation", e);
            return null;
        }
    }

    protected List<java.lang.Number> evaluate(List<java.lang.Number> l1, List<java.lang.Number> l23, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        ProcessL2 processL2 = new ProcessL2();
        return l1.stream().map(l12_iterator -> processL2.apply(l12_iterator, l23, context_)).collect(Collectors.toList());
    }
}
