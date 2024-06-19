
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Post-bureauRiskCategory"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Post-bureauRiskCategory",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class PostBureauRiskCategory extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Post-bureauRiskCategory",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static java.util.Map<String, Object> requestToMap(proto.PostBureauRiskCategoryRequest postBureauRiskCategoryRequest_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(postBureauRiskCategoryRequest_.getApplicantData());
        type.TBureauData bureauData = type.TBureauData.toTBureauData(postBureauRiskCategoryRequest_.getBureauData());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("ApplicantData", applicantData);
        map_.put("BureauData", bureauData);
        return map_;
    }

    public static String responseToOutput(proto.PostBureauRiskCategoryResponse postBureauRiskCategoryResponse_) {
        // Extract and convert output
        return postBureauRiskCategoryResponse_.getPostBureauRiskCategory();
    }

    private final ApplicationRiskScore applicationRiskScore;

    public PostBureauRiskCategory() {
        this(new ApplicationRiskScore());
    }

    public PostBureauRiskCategory(ApplicationRiskScore applicationRiskScore) {
        this.applicationRiskScore = applicationRiskScore;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("ApplicantData") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("ApplicantData"), new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), (input_.get("BureauData") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("BureauData"), new com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PostBureauRiskCategory'", e);
            return null;
        }
    }

    public String apply(type.TApplicantData applicantData, type.TBureauData bureauData, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Post-bureauRiskCategory'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long postBureauRiskCategoryStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments postBureauRiskCategoryArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            postBureauRiskCategoryArguments_.put("ApplicantData", applicantData);
            postBureauRiskCategoryArguments_.put("BureauData", bureauData);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryArguments_);

            if (cache_.contains("Post-bureauRiskCategory")) {
                // Retrieve value from cache
                String output_ = (String)cache_.lookup("Post-bureauRiskCategory");

                // End decision 'Post-bureauRiskCategory'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - postBureauRiskCategoryStartTime_));

                return output_;
            } else {
                // Evaluate decision 'Post-bureauRiskCategory'
                String output_ = lambda.apply(applicantData, bureauData, context_);
                cache_.bind("Post-bureauRiskCategory", output_);

                // End decision 'Post-bureauRiskCategory'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - postBureauRiskCategoryStartTime_));

                return output_;
            }
        } catch (Exception e) {
            logError("Exception caught in 'Post-bureauRiskCategory' evaluation", e);
            return null;
        }
    }

    public proto.PostBureauRiskCategoryResponse applyProto(proto.PostBureauRiskCategoryRequest postBureauRiskCategoryRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        type.TApplicantData applicantData = type.TApplicantData.toTApplicantData(postBureauRiskCategoryRequest_.getApplicantData());
        type.TBureauData bureauData = type.TBureauData.toTBureauData(postBureauRiskCategoryRequest_.getBureauData());

        // Invoke apply method
        String output_ = apply(applicantData, bureauData, context_);

        // Convert output to Response Message
        proto.PostBureauRiskCategoryResponse.Builder builder_ = proto.PostBureauRiskCategoryResponse.newBuilder();
        String outputProto_ = (output_ == null ? "" : output_);
        builder_.setPostBureauRiskCategory(outputProto_);
        return builder_.build();
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                type.TApplicantData applicantData = 0 < args_.length ? (type.TApplicantData) args_[0] : null;
                type.TBureauData bureauData = 1 < args_.length ? (type.TBureauData) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                java.math.BigDecimal applicationRiskScore = PostBureauRiskCategory.this.applicationRiskScore.apply(applicantData, context_);

                return PostBureauRiskCategoryTable.instance().apply(((Boolean)(applicantData != null ? applicantData.getExistingCustomer() : null)), applicationRiskScore, ((java.math.BigDecimal)(bureauData != null ? bureauData.getCreditScore() : null)), context_);
            }
        };
}
