
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Date"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Date",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Date extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
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
        javax.xml.datatype.XMLGregorianCalendar inputDate = com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.date(dateRequest_.getInputDate());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("InputDate", inputDate);
        return map_;
    }

    public static javax.xml.datatype.XMLGregorianCalendar responseToOutput(proto.DateResponse dateResponse_) {
        // Extract and convert output
        return com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.date(dateResponse_.getDate());
    }

    public Date() {
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(String inputDate, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((inputDate != null ? date(inputDate) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Date'", e);
            return null;
        }
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(String inputDate, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((inputDate != null ? date(inputDate) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Date'", e);
            return null;
        }
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(javax.xml.datatype.XMLGregorianCalendar inputDate, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(inputDate, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(javax.xml.datatype.XMLGregorianCalendar inputDate, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'Date'
            long dateStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dateArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dateArguments_.put("InputDate", inputDate);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateArguments_);

            // Evaluate decision 'Date'
            javax.xml.datatype.XMLGregorianCalendar output_ = evaluate(inputDate, annotationSet_, eventListener_, externalExecutor_, cache_);

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
        javax.xml.datatype.XMLGregorianCalendar inputDate = date(dateRequest_.getInputDate());

        // Invoke apply method
        javax.xml.datatype.XMLGregorianCalendar output_ = apply(inputDate, annotationSet_, eventListener_, externalExecutor_, cache_);

        // Convert output to Response Message
        proto.DateResponse.Builder builder_ = proto.DateResponse.newBuilder();
        String outputProto_ = string(output_);
        builder_.setDate(outputProto_);
        return builder_.build();
    }

    protected javax.xml.datatype.XMLGregorianCalendar evaluate(javax.xml.datatype.XMLGregorianCalendar inputDate, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return inputDate;
    }
}
