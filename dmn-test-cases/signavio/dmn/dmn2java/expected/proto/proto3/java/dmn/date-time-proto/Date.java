
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "Date"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Date",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Date extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Date",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.DateRequest dateRequest_) {
        // Create arguments from Request Message
        type.TCompositeDateTime compositeInputDateTime = type.TCompositeDateTime.toTCompositeDateTime(dateRequest_.getCompositeInputDateTime());
        java.time.LocalDate inputDate = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.date(dateRequest_.getInputDate());
        java.time.temporal.TemporalAccessor inputDateTime = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.dateAndTime(dateRequest_.getInputDateTime());
        java.time.temporal.TemporalAccessor inputTime = com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.time(dateRequest_.getInputTime());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("CompositeInputDateTime", compositeInputDateTime);
        map_.put("InputDate", inputDate);
        map_.put("InputDateTime", inputDateTime);
        map_.put("InputTime", inputTime);
        return map_;
    }

    public static java.time.LocalDate responseToOutput(proto.DateResponse dateResponse_) {
        // Extract and convert output
        return com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.date(dateResponse_.getDate());
    }

    public Date() {
    }

    @java.lang.Override()
    public java.time.LocalDate applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("CompositeInputDateTime") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("CompositeInputDateTime"), new com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) : null), (input_.get("InputDate") != null ? date(input_.get("InputDate")) : null), (input_.get("InputDateTime") != null ? dateAndTime(input_.get("InputDateTime")) : null), (input_.get("InputTime") != null ? time(input_.get("InputTime")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Date'", e);
            return null;
        }
    }

    public java.time.LocalDate apply(type.TCompositeDateTime compositeInputDateTime, java.time.LocalDate inputDate, java.time.temporal.TemporalAccessor inputDateTime, java.time.temporal.TemporalAccessor inputTime, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Date'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long dateStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dateArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dateArguments_.put("CompositeInputDateTime", compositeInputDateTime);
            dateArguments_.put("InputDate", inputDate);
            dateArguments_.put("InputDateTime", inputDateTime);
            dateArguments_.put("InputTime", inputTime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateArguments_);

            // Evaluate decision 'Date'
            java.time.LocalDate output_ = evaluate(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_);

            // End decision 'Date'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dateArguments_, output_, (System.currentTimeMillis() - dateStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Date' evaluation", e);
            return null;
        }
    }

    public proto.DateResponse applyProto(proto.DateRequest dateRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        type.TCompositeDateTime compositeInputDateTime = type.TCompositeDateTime.toTCompositeDateTime(dateRequest_.getCompositeInputDateTime());
        java.time.LocalDate inputDate = date(dateRequest_.getInputDate());
        java.time.temporal.TemporalAccessor inputDateTime = dateAndTime(dateRequest_.getInputDateTime());
        java.time.temporal.TemporalAccessor inputTime = time(dateRequest_.getInputTime());

        // Invoke apply method
        java.time.LocalDate output_ = apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_);

        // Convert output to Response Message
        proto.DateResponse.Builder builder_ = proto.DateResponse.newBuilder();
        String outputProto_ = string(output_);
        builder_.setDate(outputProto_);
        return builder_.build();
    }

    protected java.time.LocalDate evaluate(type.TCompositeDateTime compositeInputDateTime, java.time.LocalDate inputDate, java.time.temporal.TemporalAccessor inputDateTime, java.time.temporal.TemporalAccessor inputTime, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return inputDate;
    }
}
