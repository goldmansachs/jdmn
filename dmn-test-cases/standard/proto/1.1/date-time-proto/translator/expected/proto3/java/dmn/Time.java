
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Time"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Time",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Time extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Time",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.TimeRequest timeRequest_) {
        // Create arguments from Request Message
        java.time.temporal.TemporalAccessor inputTime = com.gs.dmn.feel.lib.JavaTimeFEELLib.INSTANCE.time(timeRequest_.getInputTime());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("InputTime", inputTime);
        return map_;
    }

    public static java.time.temporal.TemporalAccessor responseToOutput(proto.TimeResponse timeResponse_) {
        // Extract and convert output
        return com.gs.dmn.feel.lib.JavaTimeFEELLib.INSTANCE.time(timeResponse_.getTime());
    }

    public Time() {
    }

    @java.lang.Override()
    public java.time.temporal.TemporalAccessor applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("InputTime") != null ? time(input_.get("InputTime")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Time'", e);
            return null;
        }
    }

    public java.time.temporal.TemporalAccessor apply(java.time.temporal.TemporalAccessor inputTime, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Time'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long timeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments timeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            timeArguments_.put("InputTime", inputTime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, timeArguments_);

            // Evaluate decision 'Time'
            java.time.temporal.TemporalAccessor output_ = lambda.apply(inputTime, context_);

            // End decision 'Time'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, timeArguments_, output_, (System.currentTimeMillis() - timeStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Time' evaluation", e);
            return null;
        }
    }

    public proto.TimeResponse applyProto(proto.TimeRequest timeRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        java.time.temporal.TemporalAccessor inputTime = time(timeRequest_.getInputTime());

        // Invoke apply method
        java.time.temporal.TemporalAccessor output_ = apply(inputTime, context_);

        // Convert output to Response Message
        proto.TimeResponse.Builder builder_ = proto.TimeResponse.newBuilder();
        String outputProto_ = string(output_);
        builder_.setTime(outputProto_);
        return builder_.build();
    }

    public com.gs.dmn.runtime.LambdaExpression<java.time.temporal.TemporalAccessor> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.time.temporal.TemporalAccessor>() {
            public java.time.temporal.TemporalAccessor apply(Object... args_) {
                java.time.temporal.TemporalAccessor inputTime = 0 < args_.length ? (java.time.temporal.TemporalAccessor) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return inputTime;
            }
        };
}
