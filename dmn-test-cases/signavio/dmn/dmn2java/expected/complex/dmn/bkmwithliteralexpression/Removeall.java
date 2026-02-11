
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "removeall"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/ec84b81482a64a2fbfcec8b1c831507a.xml",
    name = "removeall",
    label = "removeall",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Removeall extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<List<String>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "removeall",
        "removeall",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Removeall() {
    }

    @java.lang.Override()
    public List<String> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("blacklist") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("blacklist"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (input_.get("names") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("names"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Removeall'", e);
            return null;
        }
    }

    public List<String> apply(List<String> blacklist, List<String> names, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'removeall'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long removeallStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments removeallArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            removeallArguments_.put("blacklist", blacklist);
            removeallArguments_.put("names", names);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, removeallArguments_);

            // Evaluate decision 'removeall'
            List<String> output_ = evaluate(blacklist, names, context_);

            // End decision 'removeall'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, removeallArguments_, output_, (System.currentTimeMillis() - removeallStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'removeall' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(List<String> blacklist, List<String> names, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return removeAll(names, blacklist);
    }
}
