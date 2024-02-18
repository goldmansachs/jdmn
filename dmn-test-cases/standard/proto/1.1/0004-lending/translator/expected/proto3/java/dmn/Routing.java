
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"decision.ftl", "Routing"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Routing",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Routing extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Routing",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.RoutingRequest routingRequest_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(routingRequest_.getApplicantData());
        type.TBureauData bureauData = type.TBureauData.toTBureauData(routingRequest_.getBureauData());
        type.TRequestedProduct requestedProduct = type.TRequestedProduct.toTRequestedProduct(routingRequest_.getRequestedProduct());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("ApplicantData", applicantData);
        map_.put("BureauData", bureauData);
        map_.put("RequestedProduct", requestedProduct);
        return map_;
    }

    public static String responseToOutput(proto.RoutingResponse routingResponse_) {
        // Extract and convert output
        return routingResponse_.getRouting();
    }

    private final PostBureauAffordability postBureauAffordability;
    private final PostBureauRiskCategory postBureauRiskCategory;

    public Routing() {
        this(new PostBureauAffordability(), new PostBureauRiskCategory());
    }

    public Routing(PostBureauAffordability postBureauAffordability, PostBureauRiskCategory postBureauRiskCategory) {
        this.postBureauAffordability = postBureauAffordability;
        this.postBureauRiskCategory = postBureauRiskCategory;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("ApplicantData") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("ApplicantData"), new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), (input_.get("BureauData") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("BureauData"), new com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) : null), (input_.get("RequestedProduct") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("RequestedProduct"), new com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Routing'", e);
            return null;
        }
    }

    public String apply(type.TApplicantData applicantData, type.TBureauData bureauData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Routing'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long routingStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments routingArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            routingArguments_.put("ApplicantData", applicantData);
            routingArguments_.put("BureauData", bureauData);
            routingArguments_.put("RequestedProduct", requestedProduct);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, routingArguments_);

            // Evaluate decision 'Routing'
            String output_ = lambda.apply(applicantData, bureauData, requestedProduct, context_);

            // End decision 'Routing'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, routingArguments_, output_, (System.currentTimeMillis() - routingStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Routing' evaluation", e);
            return null;
        }
    }

    public proto.RoutingResponse applyProto(proto.RoutingRequest routingRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(routingRequest_.getApplicantData());
        type.TBureauData bureauData = type.TBureauData.toTBureauData(routingRequest_.getBureauData());
        type.TRequestedProduct requestedProduct = type.TRequestedProduct.toTRequestedProduct(routingRequest_.getRequestedProduct());

        // Invoke apply method
        String output_ = apply(applicantData, bureauData, requestedProduct, context_);

        // Convert output to Response Message
        proto.RoutingResponse.Builder builder_ = proto.RoutingResponse.newBuilder();
        String outputProto_ = (output_ == null ? "" : output_);
        builder_.setRouting(outputProto_);
        return builder_.build();
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                type.TApplicantData applicantData = 0 < args_.length ? (type.TApplicantData) args_[0] : null;
                type.TBureauData bureauData = 1 < args_.length ? (type.TBureauData) args_[1] : null;
                type.TRequestedProduct requestedProduct = 2 < args_.length ? (type.TRequestedProduct) args_[2] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 3 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[3] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                Boolean postBureauAffordability = Routing.this.postBureauAffordability.apply(applicantData, bureauData, requestedProduct, context_);
                String postBureauRiskCategory = Routing.this.postBureauRiskCategory.apply(applicantData, bureauData, context_);

                return RoutingRules.instance().apply(postBureauRiskCategory, postBureauAffordability, ((Boolean)(bureauData != null ? bureauData.getBankrupt() : null)), ((java.math.BigDecimal)(bureauData != null ? bureauData.getCreditScore() : null)), context_);
            }
        };
}
