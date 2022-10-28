
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

    @java.lang.Override()
    public List<Boolean> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("TestPeopleType") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("TestPeopleType"), new com.fasterxml.jackson.core.type.TypeReference<type.TestPeopleTypeImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'BigMid'", e);
            return null;
        }
    }

    public List<Boolean> apply(type.TestPeopleType testPeopleType, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'bigMid'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long bigMidStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bigMidArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            bigMidArguments_.put("TestPeopleType", testPeopleType);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bigMidArguments_);

            // Iterate and aggregate
            List<Boolean> output_ = evaluate(testPeopleType, context_);

            // End decision 'bigMid'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bigMidArguments_, output_, (System.currentTimeMillis() - bigMidStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'bigMid' evaluation", e);
            return null;
        }
    }

    protected List<Boolean> evaluate(type.TestPeopleType testPeopleType, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        TopDecision topDecision = new TopDecision();
        return ((List<type.TestPersonType>)(testPeopleType != null ? testPeopleType.getTestPersonType() : null)).stream().map(testPersonType6_iterator -> topDecision.apply(type.TestPersonType.toTestPersonType(testPersonType6_iterator), context_)).collect(Collectors.toList());
    }
}
