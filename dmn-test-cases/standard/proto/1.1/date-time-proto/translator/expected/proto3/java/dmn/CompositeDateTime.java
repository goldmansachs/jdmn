
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

    @java.lang.Override()
    public type.CompositeDateTime applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("CompositeInputDateTime") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("CompositeInputDateTime"), new com.fasterxml.jackson.core.type.TypeReference<type.CompositeDateTimeImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CompositeDateTime'", e);
            return null;
        }
    }

    public type.CompositeDateTime apply(type.CompositeDateTime compositeInputDateTime, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'CompositeDateTime'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long compositeDateTimeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments compositeDateTimeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            compositeDateTimeArguments_.put("CompositeInputDateTime", compositeInputDateTime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compositeDateTimeArguments_);

            // Evaluate decision 'CompositeDateTime'
            type.CompositeDateTime output_ = lambda.apply(compositeInputDateTime, context_);

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
        type.CompositeDateTime compositeInputDateTime = type.CompositeDateTime.toCompositeDateTime(compositeDateTimeRequest_.getCompositeInputDateTime());

        // Invoke apply method
        type.CompositeDateTime output_ = apply(compositeInputDateTime, context_);

        // Convert output to Response Message
        proto.CompositeDateTimeResponse.Builder builder_ = proto.CompositeDateTimeResponse.newBuilder();
        proto.CompositeDateTime outputProto_ = type.CompositeDateTime.toProto(output_);
        if (outputProto_ != null) {
            builder_.setCompositeDateTime(outputProto_);
        }
        return builder_.build();
    }

    public com.gs.dmn.runtime.LambdaExpression<type.CompositeDateTime> lambda =
        new com.gs.dmn.runtime.LambdaExpression<type.CompositeDateTime>() {
            public type.CompositeDateTime apply(Object... args_) {
                type.CompositeDateTime compositeInputDateTime = 0 < args_.length ? (type.CompositeDateTime) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return type.CompositeDateTime.toCompositeDateTime(compositeInputDateTime);
            }
        };
}
