
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "CompositeDateTime"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "CompositeDateTime",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class CompositeDateTime extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
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
        type.CompositeDateTime compositeInputDateTime = type.CompositeDateTime.toCompositeDateTime(compositeDateTimeRequest_.getCompositeInputDateTime());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("CompositeInputDateTime", compositeInputDateTime);
        return map_;
    }

    public static type.CompositeDateTime responseToOutput(proto.CompositeDateTimeResponse compositeDateTimeResponse_) {
        // Extract and convert output
        return type.CompositeDateTime.toCompositeDateTime(compositeDateTimeResponse_.getCompositeDateTime());
    }

    public CompositeDateTime() {
    }

    public type.CompositeDateTime apply(String compositeInputDateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((compositeInputDateTime != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(compositeInputDateTime, new com.fasterxml.jackson.core.type.TypeReference<type.CompositeDateTimeImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'CompositeDateTime'", e);
            return null;
        }
    }

    public type.CompositeDateTime apply(String compositeInputDateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((compositeInputDateTime != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(compositeInputDateTime, new com.fasterxml.jackson.core.type.TypeReference<type.CompositeDateTimeImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CompositeDateTime'", e);
            return null;
        }
    }

    public type.CompositeDateTime apply(type.CompositeDateTime compositeInputDateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(compositeInputDateTime, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public type.CompositeDateTime apply(type.CompositeDateTime compositeInputDateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'CompositeDateTime'
            long compositeDateTimeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments compositeDateTimeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            compositeDateTimeArguments_.put("CompositeInputDateTime", compositeInputDateTime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compositeDateTimeArguments_);

            // Evaluate decision 'CompositeDateTime'
            type.CompositeDateTime output_ = evaluate(compositeInputDateTime, annotationSet_, eventListener_, externalExecutor_, cache_);

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
        type.CompositeDateTime compositeInputDateTime = type.CompositeDateTime.toCompositeDateTime(compositeDateTimeRequest_.getCompositeInputDateTime());

        // Invoke apply method
        type.CompositeDateTime output_ = apply(compositeInputDateTime, annotationSet_, eventListener_, externalExecutor_, cache_);

        // Convert output to Response Message
        proto.CompositeDateTimeResponse.Builder builder_ = proto.CompositeDateTimeResponse.newBuilder();
        proto.CompositeDateTime outputProto_ = type.CompositeDateTime.toProto(output_);
        if (outputProto_ != null) {
            builder_.setCompositeDateTime(outputProto_);
        }
        return builder_.build();
    }

    protected type.CompositeDateTime evaluate(type.CompositeDateTime compositeInputDateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return type.CompositeDateTime.toCompositeDateTime(compositeInputDateTime);
    }
}
