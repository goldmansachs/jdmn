
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Post-bureauAffordability"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Post-bureauAffordability",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class PostBureauAffordability extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Post-bureauAffordability",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.PostBureauAffordabilityRequest postBureauAffordabilityRequest_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(postBureauAffordabilityRequest_.getApplicantData());
        type.TBureauData bureauData = type.TBureauData.toTBureauData(postBureauAffordabilityRequest_.getBureauData());
        type.TRequestedProduct requestedProduct = type.TRequestedProduct.toTRequestedProduct(postBureauAffordabilityRequest_.getRequestedProduct());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("ApplicantData", applicantData);
        map_.put("BureauData", bureauData);
        map_.put("RequestedProduct", requestedProduct);
        return map_;
    }

    public static Boolean responseToOutput(proto.PostBureauAffordabilityResponse postBureauAffordabilityResponse_) {
        // Extract and convert output
        return postBureauAffordabilityResponse_.getPostBureauAffordability();
    }

    private final PostBureauRiskCategory postBureauRiskCategory;
    private final RequiredMonthlyInstallment requiredMonthlyInstallment;

    public PostBureauAffordability() {
        this(new PostBureauRiskCategory(), new RequiredMonthlyInstallment());
    }

    public PostBureauAffordability(PostBureauRiskCategory postBureauRiskCategory, RequiredMonthlyInstallment requiredMonthlyInstallment) {
        this.postBureauRiskCategory = postBureauRiskCategory;
        this.requiredMonthlyInstallment = requiredMonthlyInstallment;
    }

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("ApplicantData") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("ApplicantData"), new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), (input_.get("BureauData") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("BureauData"), new com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) : null), (input_.get("RequestedProduct") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("RequestedProduct"), new com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PostBureauAffordability'", e);
            return null;
        }
    }

    public Boolean apply(type.TApplicantData applicantData, type.TBureauData bureauData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Post-bureauAffordability'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long postBureauAffordabilityStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments postBureauAffordabilityArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            postBureauAffordabilityArguments_.put("ApplicantData", applicantData);
            postBureauAffordabilityArguments_.put("BureauData", bureauData);
            postBureauAffordabilityArguments_.put("RequestedProduct", requestedProduct);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, postBureauAffordabilityArguments_);

            // Evaluate decision 'Post-bureauAffordability'
            Boolean output_ = lambda.apply(applicantData, bureauData, requestedProduct, context_);

            // End decision 'Post-bureauAffordability'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauAffordabilityArguments_, output_, (System.currentTimeMillis() - postBureauAffordabilityStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Post-bureauAffordability' evaluation", e);
            return null;
        }
    }

    public proto.PostBureauAffordabilityResponse applyProto(proto.PostBureauAffordabilityRequest postBureauAffordabilityRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(postBureauAffordabilityRequest_.getApplicantData());
        type.TBureauData bureauData = type.TBureauData.toTBureauData(postBureauAffordabilityRequest_.getBureauData());
        type.TRequestedProduct requestedProduct = type.TRequestedProduct.toTRequestedProduct(postBureauAffordabilityRequest_.getRequestedProduct());

        // Invoke apply method
        Boolean output_ = apply(applicantData, bureauData, requestedProduct, context_);

        // Convert output to Response Message
        proto.PostBureauAffordabilityResponse.Builder builder_ = proto.PostBureauAffordabilityResponse.newBuilder();
        Boolean outputProto_ = (output_ == null ? false : output_);
        builder_.setPostBureauAffordability(outputProto_);
        return builder_.build();
    }

    public com.gs.dmn.runtime.LambdaExpression<Boolean> lambda =
        new com.gs.dmn.runtime.LambdaExpression<Boolean>() {
            public Boolean apply(Object... args_) {
                type.TApplicantData applicantData = 0 < args_.length ? (type.TApplicantData) args_[0] : null;
                type.TBureauData bureauData = 1 < args_.length ? (type.TBureauData) args_[1] : null;
                type.TRequestedProduct requestedProduct = 2 < args_.length ? (type.TRequestedProduct) args_[2] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 3 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[3] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String postBureauRiskCategory = PostBureauAffordability.this.postBureauRiskCategory.apply(applicantData, bureauData, context_);
                java.lang.Number requiredMonthlyInstallment = PostBureauAffordability.this.requiredMonthlyInstallment.apply(requestedProduct, context_);

                return AffordabilityCalculation.instance().apply(((java.lang.Number)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getIncome() : null)), ((java.lang.Number)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getRepayments() : null)), ((java.lang.Number)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getExpenses() : null)), postBureauRiskCategory, requiredMonthlyInstallment, context_);
            }
        };
}
