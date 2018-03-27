
import java.util.*;
import java.util.stream.Collectors;

import static AffordabilityCalculation.AffordabilityCalculation;

@javax.annotation.Generated(value = {"decision.ftl", "PostBureauAffordability"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "PostBureauAffordability",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class PostBureauAffordability extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "PostBureauAffordability",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final PostBureauRiskCategory postBureauRiskCategory;
    private final RequiredMonthlyInstallment requiredMonthlyInstallment;

    public PostBureauAffordability() {
        this(new PostBureauRiskCategory(), new RequiredMonthlyInstallment());
    }

    public PostBureauAffordability(PostBureauRiskCategory postBureauRiskCategory, RequiredMonthlyInstallment requiredMonthlyInstallment) {
        this.postBureauRiskCategory = postBureauRiskCategory;
        this.requiredMonthlyInstallment = requiredMonthlyInstallment;
    }

    public Boolean apply(String applicantData, String bureauData, String requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, type.TApplicantDataImpl.class) : null), (bureauData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(bureauData, type.TBureauDataImpl.class) : null), (requestedProduct != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(requestedProduct, type.TRequestedProductImpl.class) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'PostBureauAffordability'", e);
            return null;
        }
    }

    public Boolean apply(String applicantData, String bureauData, String requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, type.TApplicantDataImpl.class) : null), (bureauData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(bureauData, type.TBureauDataImpl.class) : null), (requestedProduct != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(requestedProduct, type.TRequestedProductImpl.class) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PostBureauAffordability'", e);
            return null;
        }
    }

    public Boolean apply(type.TApplicantData applicantData, type.TBureauData bureauData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(applicantData, bureauData, requestedProduct, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public Boolean apply(type.TApplicantData applicantData, type.TBureauData bureauData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'PostBureauAffordability'
            long postBureauAffordabilityStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments postBureauAffordabilityArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            postBureauAffordabilityArguments_.put("applicantData", applicantData);
            postBureauAffordabilityArguments_.put("bureauData", bureauData);
            postBureauAffordabilityArguments_.put("requestedProduct", requestedProduct);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, postBureauAffordabilityArguments_);

            // Apply child decisions
            String postBureauRiskCategory = this.postBureauRiskCategory.apply(applicantData, bureauData, annotationSet_, eventListener_, externalExecutor_);
            java.math.BigDecimal requiredMonthlyInstallment = this.requiredMonthlyInstallment.apply(requestedProduct, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate decision 'PostBureauAffordability'
            Boolean output_ = evaluate(applicantData, postBureauRiskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'PostBureauAffordability'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, postBureauAffordabilityArguments_, output_, (System.currentTimeMillis() - postBureauAffordabilityStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'PostBureauAffordability' evaluation", e);
            return null;
        }
    }

    private Boolean evaluate(type.TApplicantData applicantData, String postBureauRiskCategory, java.math.BigDecimal requiredMonthlyInstallment, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return AffordabilityCalculation(((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getIncome() : null)), ((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getRepayments() : null)), ((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getExpenses() : null)), postBureauRiskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_);
    }
}
