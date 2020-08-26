
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "bigMid"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "bigMid",
    label = "Big mid",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class BigMid extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "bigMid",
        "Big mid",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public BigMid() {
    }

    public List<Boolean> apply(String testPeopleType, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((testPeopleType != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(testPeopleType, new com.fasterxml.jackson.core.type.TypeReference<type.TestPeopleTypeImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'BigMid'", e);
            return null;
        }
    }

    public List<Boolean> apply(String testPeopleType, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((testPeopleType != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(testPeopleType, new com.fasterxml.jackson.core.type.TypeReference<type.TestPeopleTypeImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'BigMid'", e);
            return null;
        }
    }

    public List<Boolean> apply(type.TestPeopleType testPeopleType, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(testPeopleType, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<Boolean> apply(type.TestPeopleType testPeopleType, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'bigMid'
            long bigMidStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bigMidArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            bigMidArguments_.put("TestPeopleType", testPeopleType);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bigMidArguments_);

            // Iterate and aggregate
            List<Boolean> output_ = evaluate(testPeopleType, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'bigMid'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bigMidArguments_, output_, (System.currentTimeMillis() - bigMidStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'bigMid' evaluation", e);
            return null;
        }
    }

    protected List<Boolean> evaluate(type.TestPeopleType testPeopleType, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        TopDecision topDecision = new TopDecision();
        return ((List<type.TestPersonType>)(testPeopleType != null ? testPeopleType.getTestPersonType() : null)).stream().map(testPersonType6_iterator -> topDecision.apply(type.TestPersonType.toTestPersonType(testPersonType6_iterator), annotationSet_, eventListener_, externalExecutor_, cache_)).collect(Collectors.toList());
    }
}
