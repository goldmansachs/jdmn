
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"decision.ftl", "Eligibility"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Eligibility",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Eligibility extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Eligibility",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final PreBureauAffordability preBureauAffordability;
    private final PreBureauRiskCategory preBureauRiskCategory;

    public Eligibility() {
        this(new PreBureauAffordability(), new PreBureauRiskCategory());
    }

    public Eligibility(PreBureauAffordability preBureauAffordability, PreBureauRiskCategory preBureauRiskCategory) {
        this.preBureauAffordability = preBureauAffordability;
        this.preBureauRiskCategory = preBureauRiskCategory;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("ApplicantData") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("ApplicantData"), new com.fasterxml.jackson.core.type.TypeReference<type.TApplicantDataImpl>() {}) : null), (input_.get("RequestedProduct") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("RequestedProduct"), new com.fasterxml.jackson.core.type.TypeReference<type.TRequestedProductImpl>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Eligibility'", e);
            return null;
        }
    }

    public String apply(type.TApplicantData applicantData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Eligibility'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long eligibilityStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments eligibilityArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eligibilityArguments_.put("ApplicantData", applicantData);
            eligibilityArguments_.put("RequestedProduct", requestedProduct);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, eligibilityArguments_);

            // Evaluate decision 'Eligibility'
            String output_ = lambda.apply(applicantData, requestedProduct, context_);

            // End decision 'Eligibility'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, eligibilityArguments_, output_, (System.currentTimeMillis() - eligibilityStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Eligibility' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                type.TApplicantData applicantData = 0 < args_.length ? (type.TApplicantData) args_[0] : null;
                type.TRequestedProduct requestedProduct = 1 < args_.length ? (type.TRequestedProduct) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                Boolean preBureauAffordability = Eligibility.this.preBureauAffordability.apply(applicantData, requestedProduct, context_);
                String preBureauRiskCategory = Eligibility.this.preBureauRiskCategory.apply(applicantData, context_);

                return EligibilityRules.instance().apply(preBureauRiskCategory, preBureauAffordability, ((java.math.BigDecimal)(applicantData != null ? applicantData.getAge() : null)), context_);
            }
        };
}
