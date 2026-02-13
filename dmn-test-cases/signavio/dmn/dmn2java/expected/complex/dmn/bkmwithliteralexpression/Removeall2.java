
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "removeall2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/ec84b81482a64a2fbfcec8b1c831507a.xml",
    name = "removeall2",
    label = "removeall",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Removeall2 extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<List<String>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "http://www.provider.com/dmn/1.1/diagram/ec84b81482a64a2fbfcec8b1c831507a.xml",
        "removeall2",
        "removeall",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Removeall2() {
    }

    @java.lang.Override()
    public List<String> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("rgb1"), (input_.get("rgb1 list") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("rgb1 list"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Removeall2'", e);
            return null;
        }
    }

    public List<String> apply(String rgb1, List<String> rgb1List, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'removeall2'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long removeall2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments removeall2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            removeall2Arguments_.put("rgb1", rgb1);
            removeall2Arguments_.put("rgb1 list", rgb1List);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, removeall2Arguments_);

            // Evaluate decision 'removeall2'
            List<String> output_ = evaluate(rgb1, rgb1List, context_);

            // End decision 'removeall2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, removeall2Arguments_, output_, (System.currentTimeMillis() - removeall2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'removeall2' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(String rgb1, List<String> rgb1List, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return removeAll(rgb1List, asList(rgb1));
    }
}
