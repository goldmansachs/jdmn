
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
public class DateTime extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
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
        javax.xml.datatype.XMLGregorianCalendar inputDateTime = com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.dateAndTime(dateTimeRequest_.getInputDateTime());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("InputDateTime", inputDateTime);
        return map_;
    }

    public static javax.xml.datatype.XMLGregorianCalendar responseToOutput(proto.DateTimeResponse dateTimeResponse_) {
        // Extract and convert output
        return com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.dateAndTime(dateTimeResponse_.getDateTime());
    }

    public DateTime() {
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("InputDateTime"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DateTime'", e);
            return null;
        }
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(String inputDateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((inputDateTime != null ? dateAndTime(inputDateTime) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DateTime'", e);
            return null;
        }
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(javax.xml.datatype.XMLGregorianCalendar inputDateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'DateTime'
            long dateTimeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dateTimeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dateTimeArguments_.put("InputDateTime", inputDateTime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateTimeArguments_);

            // Evaluate decision 'DateTime'
            javax.xml.datatype.XMLGregorianCalendar output_ = lambda.apply(inputDateTime, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'DateTime'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dateTimeArguments_, output_, (System.currentTimeMillis() - dateTimeStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'DateTime' evaluation", e);
            return null;
        }
    }

    public proto.DateTimeResponse apply(proto.DateTimeRequest dateTimeRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Create arguments from Request Message
        javax.xml.datatype.XMLGregorianCalendar inputDateTime = dateAndTime(dateTimeRequest_.getInputDateTime());

        // Invoke apply method
        javax.xml.datatype.XMLGregorianCalendar output_ = apply(inputDateTime, annotationSet_, eventListener_, externalExecutor_, cache_);

        // Convert output to Response Message
        proto.DateTimeResponse.Builder builder_ = proto.DateTimeResponse.newBuilder();
        String outputProto_ = string(output_);
        builder_.setDateTime(outputProto_);
        return builder_.build();
    }

    public com.gs.dmn.runtime.LambdaExpression<javax.xml.datatype.XMLGregorianCalendar> lambda =
        new com.gs.dmn.runtime.LambdaExpression<javax.xml.datatype.XMLGregorianCalendar>() {
            public javax.xml.datatype.XMLGregorianCalendar apply(Object... args_) {
                javax.xml.datatype.XMLGregorianCalendar inputDateTime = 0 < args_.length ? (javax.xml.datatype.XMLGregorianCalendar) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 1 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[1] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 2 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[2] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 3 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[3] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 4 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[4] : null;

                return inputDateTime;
            }
        };
}
