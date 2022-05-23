
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "removeall2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "removeall2",
    label = "removeall",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Removeall2 extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
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
    public List<String> apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("rgb1"), input_.get("rgb1 list"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Removeall2'", e);
            return null;
        }
    }

    public List<String> apply(String rgb1, String rgb1List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply(rgb1, (rgb1List != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(rgb1List, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Removeall2'", e);
            return null;
        }
    }

    public List<String> apply(String rgb1, List<String> rgb1List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'removeall2'
            long removeall2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments removeall2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            removeall2Arguments_.put("rgb1", rgb1);
            removeall2Arguments_.put("rgb1 list", rgb1List);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, removeall2Arguments_);

            // Evaluate decision 'removeall2'
            List<String> output_ = evaluate(rgb1, rgb1List, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'removeall2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, removeall2Arguments_, output_, (System.currentTimeMillis() - removeall2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'removeall2' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(String rgb1, List<String> rgb1List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return removeAll(rgb1List, asList(rgb1));
    }
}
