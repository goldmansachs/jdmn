
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "From Date To Date and Time"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "From Date To Date and Time",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FromDateToDateAndTime extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "From Date To Date and Time",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public FromDateToDateAndTime() {
    }

    @java.lang.Override()
    public java.time.temporal.TemporalAccessor applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FromDateToDateAndTime'", e);
            return null;
        }
    }

    public java.time.temporal.TemporalAccessor apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'From Date To Date and Time'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long fromDateToDateAndTimeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fromDateToDateAndTimeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fromDateToDateAndTimeArguments_);

            // Evaluate decision 'From Date To Date and Time'
            java.time.temporal.TemporalAccessor output_ = lambda.apply(context_);

            // End decision 'From Date To Date and Time'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fromDateToDateAndTimeArguments_, output_, (System.currentTimeMillis() - fromDateToDateAndTimeStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'From Date To Date and Time' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.time.temporal.TemporalAccessor> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.time.temporal.TemporalAccessor>() {
            public java.time.temporal.TemporalAccessor apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return toDateTime(date(number("2000"), number("01"), number("02")));
            }
        };
}
