
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"signavio-decision.ftl", "DateTime"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "DateTime",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DateTime extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
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
        type.TCompositeDateTime compositeInputDateTime = type.TCompositeDateTime.toTCompositeDateTime(dateTimeRequest_.getCompositeInputDateTime());
        javax.xml.datatype.XMLGregorianCalendar inputDate = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.date(dateTimeRequest_.getInputDate());
        javax.xml.datatype.XMLGregorianCalendar inputDateTime = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.dateAndTime(dateTimeRequest_.getInputDateTime());
        javax.xml.datatype.XMLGregorianCalendar inputTime = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.time(dateTimeRequest_.getInputTime());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("CompositeInputDateTime", compositeInputDateTime);
        map_.put("InputDate", inputDate);
        map_.put("InputDateTime", inputDateTime);
        map_.put("InputTime", inputTime);
        return map_;
    }

    public static javax.xml.datatype.XMLGregorianCalendar responseToOutput(proto.DateTimeResponse dateTimeResponse_) {
        // Extract and convert output
        return com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.dateAndTime(dateTimeResponse_.getDateTime());
    }

    public DateTime() {
    }

    @java.lang.Override()
    public javax.xml.datatype.XMLGregorianCalendar applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("CompositeInputDateTime") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("CompositeInputDateTime"), new com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) : null), (input_.get("InputDate") != null ? date(input_.get("InputDate")) : null), (input_.get("InputDateTime") != null ? dateAndTime(input_.get("InputDateTime")) : null), (input_.get("InputTime") != null ? time(input_.get("InputTime")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DateTime'", e);
            return null;
        }
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(type.TCompositeDateTime compositeInputDateTime, javax.xml.datatype.XMLGregorianCalendar inputDate, javax.xml.datatype.XMLGregorianCalendar inputDateTime, javax.xml.datatype.XMLGregorianCalendar inputTime, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'DateTime'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long dateTimeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dateTimeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dateTimeArguments_.put("CompositeInputDateTime", compositeInputDateTime);
            dateTimeArguments_.put("InputDate", inputDate);
            dateTimeArguments_.put("InputDateTime", inputDateTime);
            dateTimeArguments_.put("InputTime", inputTime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateTimeArguments_);

            // Evaluate decision 'DateTime'
            javax.xml.datatype.XMLGregorianCalendar output_ = evaluate(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_);

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
        type.TCompositeDateTime compositeInputDateTime = type.TCompositeDateTime.toTCompositeDateTime(dateTimeRequest_.getCompositeInputDateTime());
        javax.xml.datatype.XMLGregorianCalendar inputDate = date(dateTimeRequest_.getInputDate());
        javax.xml.datatype.XMLGregorianCalendar inputDateTime = dateAndTime(dateTimeRequest_.getInputDateTime());
        javax.xml.datatype.XMLGregorianCalendar inputTime = time(dateTimeRequest_.getInputTime());

        // Invoke apply method
        javax.xml.datatype.XMLGregorianCalendar output_ = apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_);

        // Convert output to Response Message
        proto.DateTimeResponse.Builder builder_ = proto.DateTimeResponse.newBuilder();
        String outputProto_ = string(output_);
        builder_.setDateTime(outputProto_);
        return builder_.build();
    }

    protected javax.xml.datatype.XMLGregorianCalendar evaluate(type.TCompositeDateTime compositeInputDateTime, javax.xml.datatype.XMLGregorianCalendar inputDate, javax.xml.datatype.XMLGregorianCalendar inputDateTime, javax.xml.datatype.XMLGregorianCalendar inputTime, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return inputDateTime;
    }
}
