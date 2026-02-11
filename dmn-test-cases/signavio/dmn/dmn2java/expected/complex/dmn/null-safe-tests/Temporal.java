
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "temporal"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml",
    name = "temporal",
    label = "temporal",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Temporal extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<Boolean> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "temporal",
        "temporal",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Temporal() {
    }

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("dateTime") != null ? dateAndTime(input_.get("dateTime")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Temporal'", e);
            return null;
        }
    }

    public Boolean apply(java.time.temporal.TemporalAccessor dateTime, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'temporal'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long temporalStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments temporalArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            temporalArguments_.put("dateTime", dateTime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, temporalArguments_);

            // Evaluate decision 'temporal'
            Boolean output_ = evaluate(dateTime, context_);

            // End decision 'temporal'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, temporalArguments_, output_, (System.currentTimeMillis() - temporalStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'temporal' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(java.time.temporal.TemporalAccessor dateTime, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return dateTimeEqual(dateTime, dateTime(day(dateTime), month(dateTime), year(dateTime), hour(dateTime), minute(dateTime), number("0")));
    }
}
