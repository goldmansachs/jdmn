
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "CompositeDateTime"})
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

    public type.TCompositeDateTime apply(String compositeInputDateTime, String inputDate, String inputDateTime, String inputTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((compositeInputDateTime != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(compositeInputDateTime, new com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) : null), (inputDate != null ? date(inputDate) : null), (inputDateTime != null ? dateAndTime(inputDateTime) : null), (inputTime != null ? time(inputTime) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'CompositeDateTime'", e);
            return null;
        }
    }

    public type.TCompositeDateTime apply(String compositeInputDateTime, String inputDate, String inputDateTime, String inputTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((compositeInputDateTime != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(compositeInputDateTime, new com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) : null), (inputDate != null ? date(inputDate) : null), (inputDateTime != null ? dateAndTime(inputDateTime) : null), (inputTime != null ? time(inputTime) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CompositeDateTime'", e);
            return null;
        }
    }

    public type.TCompositeDateTime apply(type.TCompositeDateTime compositeInputDateTime, javax.xml.datatype.XMLGregorianCalendar inputDate, javax.xml.datatype.XMLGregorianCalendar inputDateTime, javax.xml.datatype.XMLGregorianCalendar inputTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public type.TCompositeDateTime apply(type.TCompositeDateTime compositeInputDateTime, javax.xml.datatype.XMLGregorianCalendar inputDate, javax.xml.datatype.XMLGregorianCalendar inputDateTime, javax.xml.datatype.XMLGregorianCalendar inputTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'CompositeDateTime'
            long compositeDateTimeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments compositeDateTimeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            compositeDateTimeArguments_.put("CompositeInputDateTime", compositeInputDateTime);
            compositeDateTimeArguments_.put("InputDate", inputDate);
            compositeDateTimeArguments_.put("InputDateTime", inputDateTime);
            compositeDateTimeArguments_.put("InputTime", inputTime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compositeDateTimeArguments_);

            // Evaluate decision 'CompositeDateTime'
            type.TCompositeDateTime output_ = evaluate(compositeInputDateTime, inputDate, inputDateTime, inputTime, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'CompositeDateTime'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, compositeDateTimeArguments_, output_, (System.currentTimeMillis() - compositeDateTimeStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'CompositeDateTime' evaluation", e);
            return null;
        }
    }

    public proto.CompositeDateTimeResponse apply(proto.CompositeDateTimeRequest compositeDateTimeRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(compositeDateTimeRequest_, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public proto.CompositeDateTimeResponse apply(proto.CompositeDateTimeRequest compositeDateTimeRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Create arguments from Request Message
        type.TCompositeDateTime compositeInputDateTime = type.TCompositeDateTime.toTCompositeDateTime(compositeDateTimeRequest_.getCompositeInputDateTime());
        javax.xml.datatype.XMLGregorianCalendar inputDate = date(compositeDateTimeRequest_.getInputDate());
        javax.xml.datatype.XMLGregorianCalendar inputDateTime = dateAndTime(compositeDateTimeRequest_.getInputDateTime());
        javax.xml.datatype.XMLGregorianCalendar inputTime = time(compositeDateTimeRequest_.getInputTime());

        // Invoke apply method
        type.TCompositeDateTime output_ = apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, annotationSet_, eventListener_, externalExecutor_, cache_);

        // Convert output to Response Message
        proto.CompositeDateTimeResponse.Builder builder_ = proto.CompositeDateTimeResponse.newBuilder();
        proto.TCompositeDateTime outputProto_ = type.TCompositeDateTime.toProto(output_);
        if (outputProto_ != null) {
            builder_.setCompositeDateTime(outputProto_);
        }
        return builder_.build();
    }

    protected type.TCompositeDateTime evaluate(type.TCompositeDateTime compositeInputDateTime, javax.xml.datatype.XMLGregorianCalendar inputDate, javax.xml.datatype.XMLGregorianCalendar inputDateTime, javax.xml.datatype.XMLGregorianCalendar inputTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return type.TCompositeDateTime.toTCompositeDateTime(compositeInputDateTime);
    }
}
