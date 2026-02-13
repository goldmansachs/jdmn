
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "smallMid"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/3652588c6383423c9774f4dfd4393cb1.xml",
    name = "smallMid",
    label = "small mid",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class SmallMid extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<List<String>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "http://www.provider.com/dmn/1.1/diagram/3652588c6383423c9774f4dfd4393cb1.xml",
        "smallMid",
        "small mid",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public SmallMid() {
    }

    @java.lang.Override()
    public List<String> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("TestPersonType") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("TestPersonType"), new com.fasterxml.jackson.core.type.TypeReference<type.TestPersonTypeImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'SmallMid'", e);
            return null;
        }
    }

    public List<String> apply(type.TestPersonType testPersonType6_iterator, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'smallMid'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long smallMidStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments smallMidArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            smallMidArguments_.put("TestPersonType", testPersonType6_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, smallMidArguments_);

            // Iterate and aggregate
            List<String> output_ = evaluate(testPersonType6_iterator, context_);

            // End decision 'smallMid'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, smallMidArguments_, output_, (System.currentTimeMillis() - smallMidStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'smallMid' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(type.TestPersonType testPersonType6_iterator, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        Decide decide = new Decide();
        return ((List<String>)(testPersonType6_iterator != null ? testPersonType6_iterator.getProperties() : null)).stream().map(properties_iterator -> decide.apply(properties_iterator, context_)).collect(Collectors.toList());
    }
}
