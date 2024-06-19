
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "DateTime"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "DateTime",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DateTime extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "DateTime",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.DateTimeRequest dateTimeRequest_) {
        // Create arguments from Request Message
        java.time.temporal.TemporalAccessor inputDateTime = com.gs.dmn.feel.lib.JavaTimeFEELLib.INSTANCE.dateAndTime(dateTimeRequest_.getInputDateTime());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("InputDateTime", inputDateTime);
        return map_;
    }

    public static java.time.temporal.TemporalAccessor responseToOutput(proto.DateTimeResponse dateTimeResponse_) {
        // Extract and convert output
        return com.gs.dmn.feel.lib.JavaTimeFEELLib.INSTANCE.dateAndTime(dateTimeResponse_.getDateTime());
    }

    public DateTime() {
    }

    @java.lang.Override()
    public java.time.temporal.TemporalAccessor applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("InputDateTime") != null ? dateAndTime(input_.get("InputDateTime")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DateTime'", e);
            return null;
        }
    }

    public java.time.temporal.TemporalAccessor apply(java.time.temporal.TemporalAccessor inputDateTime, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'DateTime'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long dateTimeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dateTimeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dateTimeArguments_.put("InputDateTime", inputDateTime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateTimeArguments_);

            // Evaluate decision 'DateTime'
            java.time.temporal.TemporalAccessor output_ = lambda.apply(inputDateTime, context_);

            // End decision 'DateTime'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dateTimeArguments_, output_, (System.currentTimeMillis() - dateTimeStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'DateTime' evaluation", e);
            return null;
        }
    }

    public proto.DateTimeResponse applyProto(proto.DateTimeRequest dateTimeRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        java.time.temporal.TemporalAccessor inputDateTime = dateAndTime(dateTimeRequest_.getInputDateTime());

        // Invoke apply method
        java.time.temporal.TemporalAccessor output_ = apply(inputDateTime, context_);

        // Convert output to Response Message
        proto.DateTimeResponse.Builder builder_ = proto.DateTimeResponse.newBuilder();
        String outputProto_ = string(output_);
        builder_.setDateTime(outputProto_);
        return builder_.build();
    }

    public com.gs.dmn.runtime.LambdaExpression<java.time.temporal.TemporalAccessor> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.time.temporal.TemporalAccessor>() {
            public java.time.temporal.TemporalAccessor apply(Object... args_) {
                java.time.temporal.TemporalAccessor inputDateTime = 0 < args_.length ? (java.time.temporal.TemporalAccessor) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return inputDateTime;
            }
        };
}
