
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Pre-bureauAffordability"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Pre-bureauAffordability",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class PreBureauAffordability extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Pre-bureauAffordability",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final PreBureauRiskCategory preBureauRiskCategory;
    private final RequiredMonthlyInstallment requiredMonthlyInstallment;

    public PreBureauAffordability() {
        this(new PreBureauRiskCategory(), new RequiredMonthlyInstallment());
    }

    public PreBureauAffordability(PreBureauRiskCategory preBureauRiskCategory, RequiredMonthlyInstallment requiredMonthlyInstallment) {
        this.preBureauRiskCategory = preBureauRiskCategory;
        this.requiredMonthlyInstallment = requiredMonthlyInstallment;
    }

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("ApplicantData") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("ApplicantData"), new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), (input_.get("RequestedProduct") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("RequestedProduct"), new com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PreBureauAffordability'", e);
            return null;
        }
    }

    public Boolean apply(type.TApplicantData applicantData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Pre-bureauAffordability'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long preBureauAffordabilityStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments preBureauAffordabilityArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            preBureauAffordabilityArguments_.put("ApplicantData", applicantData);
            preBureauAffordabilityArguments_.put("RequestedProduct", requestedProduct);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, preBureauAffordabilityArguments_);

            // Evaluate decision 'Pre-bureauAffordability'
            Boolean output_ = lambda.apply(applicantData, requestedProduct, context_);

            // End decision 'Pre-bureauAffordability'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, preBureauAffordabilityArguments_, output_, (System.currentTimeMillis() - preBureauAffordabilityStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Pre-bureauAffordability' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<Boolean> lambda =
        new com.gs.dmn.runtime.LambdaExpression<Boolean>() {
            public Boolean apply(Object... args_) {
                type.TApplicantData applicantData = 0 < args_.length ? (type.TApplicantData) args_[0] : null;
                type.TRequestedProduct requestedProduct = 1 < args_.length ? (type.TRequestedProduct) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String preBureauRiskCategory = PreBureauAffordability.this.preBureauRiskCategory.apply(applicantData, context_);
                java.math.BigDecimal requiredMonthlyInstallment = PreBureauAffordability.this.requiredMonthlyInstallment.apply(requestedProduct, context_);

                return AffordabilityCalculation.instance().apply(((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getIncome() : null)), ((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getRepayments() : null)), ((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getExpenses() : null)), preBureauRiskCategory, requiredMonthlyInstallment, context_);
            }
        };
}
