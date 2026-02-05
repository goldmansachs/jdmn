
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
public class PostBureauRiskCategory extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Post-bureauRiskCategory",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

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

    @java.lang.Override()
    public String applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((PostBureauRiskCategoryInput_)input_).getApplicantData(), ((PostBureauRiskCategoryInput_)input_).getBureauData(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'PostBureauRiskCategory'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new PostBureauRiskCategoryInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'PostBureauRiskCategory'", e);
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

            // Evaluate decision 'Post-bureauRiskCategory'
            String output_ = lambda.apply(applicantData, bureauData, context_);

            // End decision 'Post-bureauRiskCategory'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauRiskCategoryArguments_, output_, (System.currentTimeMillis() - postBureauRiskCategoryStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Post-bureauRiskCategory' evaluation", e);
            return null;
        }
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
                java.lang.Number applicationRiskScore = PostBureauRiskCategory.this.applicationRiskScore.apply(applicantData, context_);

                return PostBureauRiskCategoryTable.instance().apply(((Boolean)(applicantData != null ? applicantData.getExistingCustomer() : null)), applicationRiskScore, ((java.lang.Number)(bureauData != null ? bureauData.getCreditScore() : null)), context_);
            }
        };
}
