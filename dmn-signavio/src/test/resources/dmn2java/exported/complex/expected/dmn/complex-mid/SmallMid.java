
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "smallMid"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "smallMid",
    label = "small mid",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class SmallMid extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "smallMid",
        "small mid",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public SmallMid() {
    }

    public List<String> apply(String testPersonType6_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((testPersonType6_iterator != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(testPersonType6_iterator, new com.fasterxml.jackson.core.type.TypeReference<type.TestPersonTypeImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'SmallMid'", e);
            return null;
        }
    }

    public List<String> apply(String testPersonType6_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((testPersonType6_iterator != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(testPersonType6_iterator, new com.fasterxml.jackson.core.type.TypeReference<type.TestPersonTypeImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'SmallMid'", e);
            return null;
        }
    }

    public List<String> apply(type.TestPersonType testPersonType6_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(testPersonType6_iterator, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(type.TestPersonType testPersonType6_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'smallMid'
            long smallMidStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments smallMidArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            smallMidArguments_.put("TestPersonType", testPersonType6_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, smallMidArguments_);

            // Iterate and aggregate
            List<String> output_ = evaluate(testPersonType6_iterator, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'smallMid'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, smallMidArguments_, output_, (System.currentTimeMillis() - smallMidStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'smallMid' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(type.TestPersonType testPersonType6_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        Decide decide = new Decide();
        return ((List<String>)(testPersonType6_iterator != null ? testPersonType6_iterator.getProperties() : null)).stream().map(properties_iterator -> decide.apply(properties_iterator, annotationSet_, eventListener_, externalExecutor_, cache_)).collect(Collectors.toList());
    }
}
