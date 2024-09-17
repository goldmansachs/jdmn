
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "Time"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Time",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Time extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
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
        type.TCompositeDateTime compositeInputDateTime = type.TCompositeDateTime.toTCompositeDateTime(timeRequest_.getCompositeInputDateTime());
        java.time.LocalDate inputDate = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.date(timeRequest_.getInputDate());
        java.time.temporal.TemporalAccessor inputDateTime = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.dateAndTime(timeRequest_.getInputDateTime());
        java.time.temporal.TemporalAccessor inputTime = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.time(timeRequest_.getInputTime());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("CompositeInputDateTime", compositeInputDateTime);
        map_.put("InputDate", inputDate);
        map_.put("InputDateTime", inputDateTime);
        map_.put("InputTime", inputTime);
        return map_;
    }

    public static java.time.temporal.TemporalAccessor responseToOutput(proto.TimeResponse timeResponse_) {
        // Extract and convert output
        return com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.time(timeResponse_.getTime());
    }

    public Time() {
    }

    @java.lang.Override()
    public java.time.temporal.TemporalAccessor applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("CompositeInputDateTime") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("CompositeInputDateTime"), new com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) : null), (input_.get("InputDate") != null ? date(input_.get("InputDate")) : null), (input_.get("InputDateTime") != null ? dateAndTime(input_.get("InputDateTime")) : null), (input_.get("InputTime") != null ? time(input_.get("InputTime")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Time'", e);
            return null;
        }
    }

    public java.time.temporal.TemporalAccessor apply(type.TCompositeDateTime compositeInputDateTime, java.time.LocalDate inputDate, java.time.temporal.TemporalAccessor inputDateTime, java.time.temporal.TemporalAccessor inputTime, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Time'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long timeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments timeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            timeArguments_.put("CompositeInputDateTime", compositeInputDateTime);
            timeArguments_.put("InputDate", inputDate);
            timeArguments_.put("InputDateTime", inputDateTime);
            timeArguments_.put("InputTime", inputTime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, timeArguments_);

            // Evaluate decision 'Time'
            java.time.temporal.TemporalAccessor output_ = evaluate(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_);

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
        type.TCompositeDateTime compositeInputDateTime = type.TCompositeDateTime.toTCompositeDateTime(timeRequest_.getCompositeInputDateTime());
        java.time.LocalDate inputDate = date(timeRequest_.getInputDate());
        java.time.temporal.TemporalAccessor inputDateTime = dateAndTime(timeRequest_.getInputDateTime());
        java.time.temporal.TemporalAccessor inputTime = time(timeRequest_.getInputTime());

        // Invoke apply method
        java.time.temporal.TemporalAccessor output_ = apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_);

        // Convert output to Response Message
        proto.TimeResponse.Builder builder_ = proto.TimeResponse.newBuilder();
        String outputProto_ = string(output_);
        builder_.setTime(outputProto_);
        return builder_.build();
    }

    protected java.time.temporal.TemporalAccessor evaluate(type.TCompositeDateTime compositeInputDateTime, java.time.LocalDate inputDate, java.time.temporal.TemporalAccessor inputDateTime, java.time.temporal.TemporalAccessor inputTime, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return inputTime;
    }
}
