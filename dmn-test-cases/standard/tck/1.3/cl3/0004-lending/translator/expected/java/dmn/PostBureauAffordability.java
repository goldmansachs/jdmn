
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'Post-bureauAffordability'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'Post-bureauAffordability'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class PostBureauAffordability extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "'Post-bureauAffordability'",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class PostBureauAffordabilityLazyHolder {
        static final PostBureauAffordability INSTANCE = new PostBureauAffordability(PostBureauRiskCategory.instance(), RequiredMonthlyInstallment.instance());
    }
    public static PostBureauAffordability instance() {
        return PostBureauAffordabilityLazyHolder.INSTANCE;
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

    public Boolean apply(String applicantData, String bureauData, String requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), (bureauData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(bureauData, new com.fasterxml.jackson.core.type.TypeReference<type.TBureauDataImpl>() {}) : null), (requestedProduct != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(requestedProduct, new com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PostBureauAffordability'", e);
            return null;
        }
    }

    public Boolean apply(type.TApplicantData applicantData, type.TBureauData bureauData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision ''Post-bureauAffordability''
            long postBureauAffordabilityStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments postBureauAffordabilityArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            postBureauAffordabilityArguments_.put("ApplicantData", applicantData);
            postBureauAffordabilityArguments_.put("BureauData", bureauData);
            postBureauAffordabilityArguments_.put("RequestedProduct", requestedProduct);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, postBureauAffordabilityArguments_);

            // Evaluate decision ''Post-bureauAffordability''
            Boolean output_ = lambda.apply(applicantData, bureauData, requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision ''Post-bureauAffordability''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauAffordabilityArguments_, output_, (System.currentTimeMillis() - postBureauAffordabilityStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''Post-bureauAffordability'' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<Boolean> lambda =
        new com.gs.dmn.runtime.LambdaExpression<Boolean>() {
            public Boolean apply(Object... args_) {
                type.TApplicantData applicantData = 0 < args_.length ? (type.TApplicantData) args_[0] : null;
                type.TBureauData bureauData = 1 < args_.length ? (type.TBureauData) args_[1] : null;
                type.TRequestedProduct requestedProduct = 2 < args_.length ? (type.TRequestedProduct) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 3 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[3] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 4 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[4] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 5 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[5] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 6 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[6] : null;

                // Apply child decisions
                String postBureauRiskCategory = PostBureauAffordability.this.postBureauRiskCategory.apply(applicantData, bureauData, annotationSet_, eventListener_, externalExecutor_, cache_);
                java.math.BigDecimal requiredMonthlyInstallment = PostBureauAffordability.this.requiredMonthlyInstallment.apply(requestedProduct, annotationSet_, eventListener_, externalExecutor_, cache_);

                return AffordabilityCalculation.instance().apply(((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getIncome() : null)), ((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getRepayments() : null)), ((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getExpenses() : null)), postBureauRiskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_, cache_);
            }
        };
}
