
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"signavio-decision.ftl", "CompositeDateTime"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "CompositeDateTime",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class CompositeDateTime extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "CompositeDateTime",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.CompositeDateTimeRequest compositeDateTimeRequest_) {
        // Create arguments from Request Message
        type.TCompositeDateTime compositeInputDateTime = type.TCompositeDateTime.toTCompositeDateTime(compositeDateTimeRequest_.getCompositeInputDateTime());
        javax.xml.datatype.XMLGregorianCalendar inputDate = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.date(compositeDateTimeRequest_.getInputDate());
        javax.xml.datatype.XMLGregorianCalendar inputDateTime = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.dateAndTime(compositeDateTimeRequest_.getInputDateTime());
        javax.xml.datatype.XMLGregorianCalendar inputTime = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.time(compositeDateTimeRequest_.getInputTime());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("CompositeInputDateTime", compositeInputDateTime);
        map_.put("InputDate", inputDate);
        map_.put("InputDateTime", inputDateTime);
        map_.put("InputTime", inputTime);
        return map_;
    }

    public static type.TCompositeDateTime responseToOutput(proto.CompositeDateTimeResponse compositeDateTimeResponse_) {
        // Extract and convert output
        return type.TCompositeDateTime.toTCompositeDateTime(compositeDateTimeResponse_.getCompositeDateTime());
    }

    public CompositeDateTime() {
    }

    @java.lang.Override()
    public type.TCompositeDateTime applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("CompositeInputDateTime") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("CompositeInputDateTime"), new com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) : null), (input_.get("InputDate") != null ? date(input_.get("InputDate")) : null), (input_.get("InputDateTime") != null ? dateAndTime(input_.get("InputDateTime")) : null), (input_.get("InputTime") != null ? time(input_.get("InputTime")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CompositeDateTime'", e);
            return null;
        }
    }

    public type.TCompositeDateTime apply(type.TCompositeDateTime compositeInputDateTime, javax.xml.datatype.XMLGregorianCalendar inputDate, javax.xml.datatype.XMLGregorianCalendar inputDateTime, javax.xml.datatype.XMLGregorianCalendar inputTime, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'CompositeDateTime'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long compositeDateTimeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments compositeDateTimeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            compositeDateTimeArguments_.put("CompositeInputDateTime", compositeInputDateTime);
            compositeDateTimeArguments_.put("InputDate", inputDate);
            compositeDateTimeArguments_.put("InputDateTime", inputDateTime);
            compositeDateTimeArguments_.put("InputTime", inputTime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compositeDateTimeArguments_);

            // Evaluate decision 'CompositeDateTime'
            type.TCompositeDateTime output_ = evaluate(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_);

            // End decision 'CompositeDateTime'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, compositeDateTimeArguments_, output_, (System.currentTimeMillis() - compositeDateTimeStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'CompositeDateTime' evaluation", e);
            return null;
        }
    }

    public proto.CompositeDateTimeResponse applyProto(proto.CompositeDateTimeRequest compositeDateTimeRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        type.TCompositeDateTime compositeInputDateTime = type.TCompositeDateTime.toTCompositeDateTime(compositeDateTimeRequest_.getCompositeInputDateTime());
        javax.xml.datatype.XMLGregorianCalendar inputDate = date(compositeDateTimeRequest_.getInputDate());
        javax.xml.datatype.XMLGregorianCalendar inputDateTime = dateAndTime(compositeDateTimeRequest_.getInputDateTime());
        javax.xml.datatype.XMLGregorianCalendar inputTime = time(compositeDateTimeRequest_.getInputTime());

        // Invoke apply method
        type.TCompositeDateTime output_ = apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_);

        // Convert output to Response Message
        proto.CompositeDateTimeResponse.Builder builder_ = proto.CompositeDateTimeResponse.newBuilder();
        proto.TCompositeDateTime outputProto_ = type.TCompositeDateTime.toProto(output_);
        if (outputProto_ != null) {
            builder_.setCompositeDateTime(outputProto_);
        }
        return builder_.build();
    }

    protected type.TCompositeDateTime evaluate(type.TCompositeDateTime compositeInputDateTime, javax.xml.datatype.XMLGregorianCalendar inputDate, javax.xml.datatype.XMLGregorianCalendar inputDateTime, javax.xml.datatype.XMLGregorianCalendar inputTime, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return type.TCompositeDateTime.toTCompositeDateTime(compositeInputDateTime);
    }
}
