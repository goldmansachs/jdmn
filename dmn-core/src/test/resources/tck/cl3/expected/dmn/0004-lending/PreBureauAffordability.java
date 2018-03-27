
import java.util.*;
import java.util.stream.Collectors;

import static AffordabilityCalculation.AffordabilityCalculation;

@javax.annotation.Generated(value = {"decision.ftl", "PreBureauAffordability"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "PreBureauAffordability",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class PreBureauAffordability extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "PreBureauAffordability",
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

    public Boolean apply(String applicantData, String requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, type.TApplicantDataImpl.class) : null), (requestedProduct != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(requestedProduct, type.TRequestedProductImpl.class) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'PreBureauAffordability'", e);
            return null;
        }
    }

    public Boolean apply(String applicantData, String requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, type.TApplicantDataImpl.class) : null), (requestedProduct != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(requestedProduct, type.TRequestedProductImpl.class) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PreBureauAffordability'", e);
            return null;
        }
    }

    public Boolean apply(type.TApplicantData applicantData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(applicantData, requestedProduct, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public Boolean apply(type.TApplicantData applicantData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'PreBureauAffordability'
            long preBureauAffordabilityStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments preBureauAffordabilityArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            preBureauAffordabilityArguments_.put("applicantData", applicantData);
            preBureauAffordabilityArguments_.put("requestedProduct", requestedProduct);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, preBureauAffordabilityArguments_);

            // Apply child decisions
            String preBureauRiskCategory = this.preBureauRiskCategory.apply(applicantData, annotationSet_, eventListener_, externalExecutor_);
            java.math.BigDecimal requiredMonthlyInstallment = this.requiredMonthlyInstallment.apply(requestedProduct, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate decision 'PreBureauAffordability'
            Boolean output_ = evaluate(applicantData, preBureauRiskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'PreBureauAffordability'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, preBureauAffordabilityArguments_, output_, (System.currentTimeMillis() - preBureauAffordabilityStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'PreBureauAffordability' evaluation", e);
            return null;
        }
    }

    private Boolean evaluate(type.TApplicantData applicantData, String preBureauRiskCategory, java.math.BigDecimal requiredMonthlyInstallment, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return AffordabilityCalculation(((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getIncome() : null)), ((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getRepayments() : null)), ((java.math.BigDecimal)(((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)) != null ? ((type.Monthly)(applicantData != null ? applicantData.getMonthly() : null)).getExpenses() : null)), preBureauRiskCategory, requiredMonthlyInstallment, annotationSet_, eventListener_, externalExecutor_);
    }
}
