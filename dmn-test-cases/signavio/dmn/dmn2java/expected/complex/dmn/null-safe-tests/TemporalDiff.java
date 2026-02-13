
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "temporalDiff"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml",
    name = "temporalDiff",
    label = "temporalDiff",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class TemporalDiff extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<Boolean> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml",
        "temporalDiff",
        "temporalDiff",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public TemporalDiff() {
    }

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("date") != null ? date(input_.get("date")) : null), (input_.get("dateTime") != null ? dateAndTime(input_.get("dateTime")) : null), (input_.get("time") != null ? time(input_.get("time")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'TemporalDiff'", e);
            return null;
        }
    }

    public Boolean apply(java.time.LocalDate date, java.time.temporal.TemporalAccessor dateTime, java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'temporalDiff'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long temporalDiffStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments temporalDiffArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            temporalDiffArguments_.put("date", date);
            temporalDiffArguments_.put("dateTime", dateTime);
            temporalDiffArguments_.put("time", time);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, temporalDiffArguments_);

            // Evaluate decision 'temporalDiff'
            Boolean output_ = evaluate(date, dateTime, time, context_);

            // End decision 'temporalDiff'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, temporalDiffArguments_, output_, (System.currentTimeMillis() - temporalDiffStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'temporalDiff' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(java.time.LocalDate date, java.time.temporal.TemporalAccessor dateTime, java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return numericEqual(number("3"), numericAdd(numericAdd(numericAdd(numericAdd(dayDiff(date, dayAdd(date, number("1"))), monthDiff(date, monthAdd(date, number("1")))), yearDiff(date, yearAdd(date, number("1")))), hourDiff(dateTime, dateTime)), minutesDiff(time, time)));
    }
}
