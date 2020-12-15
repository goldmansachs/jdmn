
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
public class Date extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
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
        javax.xml.datatype.XMLGregorianCalendar inputDate = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.date(dateRequest_.getInputDate());
        javax.xml.datatype.XMLGregorianCalendar inputDateTime = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.dateAndTime(dateRequest_.getInputDateTime());
        javax.xml.datatype.XMLGregorianCalendar inputTime = com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.time(dateRequest_.getInputTime());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("CompositeInputDateTime", compositeInputDateTime);
        map_.put("InputDate", inputDate);
        map_.put("InputDateTime", inputDateTime);
        map_.put("InputTime", inputTime);
        return map_;
    }

    public static javax.xml.datatype.XMLGregorianCalendar responseToOutput(proto.DateResponse dateResponse_) {
        // Extract and convert output
        return com.gs.dmn.signavio.feel.lib.DefaultSignavioLib.INSTANCE.date(dateResponse_.getDate());
    }

    public Date() {
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(String compositeInputDateTime, String inputDate, String inputDateTime, String inputTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((compositeInputDateTime != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(compositeInputDateTime, new com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) : null), (inputDate != null ? date(inputDate) : null), (inputDateTime != null ? dateAndTime(inputDateTime) : null), (inputTime != null ? time(inputTime) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Date'", e);
            return null;
        }
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(String compositeInputDateTime, String inputDate, String inputDateTime, String inputTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((compositeInputDateTime != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(compositeInputDateTime, new com.fasterxml.jackson.core.type.TypeReference<type.TCompositeDateTimeImpl>() {}) : null), (inputDate != null ? date(inputDate) : null), (inputDateTime != null ? dateAndTime(inputDateTime) : null), (inputTime != null ? time(inputTime) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Date'", e);
            return null;
        }
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(type.TCompositeDateTime compositeInputDateTime, javax.xml.datatype.XMLGregorianCalendar inputDate, javax.xml.datatype.XMLGregorianCalendar inputDateTime, javax.xml.datatype.XMLGregorianCalendar inputTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(type.TCompositeDateTime compositeInputDateTime, javax.xml.datatype.XMLGregorianCalendar inputDate, javax.xml.datatype.XMLGregorianCalendar inputDateTime, javax.xml.datatype.XMLGregorianCalendar inputTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'Date'
            long dateStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dateArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dateArguments_.put("CompositeInputDateTime", compositeInputDateTime);
            dateArguments_.put("InputDate", inputDate);
            dateArguments_.put("InputDateTime", inputDateTime);
            dateArguments_.put("InputTime", inputTime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateArguments_);

            // Evaluate decision 'Date'
            javax.xml.datatype.XMLGregorianCalendar output_ = evaluate(compositeInputDateTime, inputDate, inputDateTime, inputTime, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'Date'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dateArguments_, output_, (System.currentTimeMillis() - dateStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Date' evaluation", e);
            return null;
        }
    }

    public proto.DateResponse apply(proto.DateRequest dateRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(dateRequest_, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public proto.DateResponse apply(proto.DateRequest dateRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Create arguments from Request Message
        type.TCompositeDateTime compositeInputDateTime = type.TCompositeDateTime.toTCompositeDateTime(dateRequest_.getCompositeInputDateTime());
        javax.xml.datatype.XMLGregorianCalendar inputDate = date(dateRequest_.getInputDate());
        javax.xml.datatype.XMLGregorianCalendar inputDateTime = dateAndTime(dateRequest_.getInputDateTime());
        javax.xml.datatype.XMLGregorianCalendar inputTime = time(dateRequest_.getInputTime());

        // Invoke apply method
        javax.xml.datatype.XMLGregorianCalendar output_ = apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, annotationSet_, eventListener_, externalExecutor_, cache_);

        // Convert output to Response Message
        proto.DateResponse.Builder builder_ = proto.DateResponse.newBuilder();
        String outputProto_ = string(output_);
        builder_.setDate(outputProto_);
        return builder_.build();
    }

    protected javax.xml.datatype.XMLGregorianCalendar evaluate(type.TCompositeDateTime compositeInputDateTime, javax.xml.datatype.XMLGregorianCalendar inputDate, javax.xml.datatype.XMLGregorianCalendar inputDateTime, javax.xml.datatype.XMLGregorianCalendar inputTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return inputDate;
    }
}
