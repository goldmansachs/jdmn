
import java.util.*;
import java.util.stream.Collectors;

import static EligibilityRules.EligibilityRules;

@javax.annotation.Generated(value = {"decision.ftl", "Eligibility"})
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

    public String apply(String applicantData, String requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, type.TApplicantDataImpl.class) : null), (requestedProduct != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(requestedProduct, type.TRequestedProductImpl.class) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Eligibility'", e);
            return null;
        }
    }

    public String apply(String applicantData, String requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((applicantData != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicantData, type.TApplicantDataImpl.class) : null), (requestedProduct != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(requestedProduct, type.TRequestedProductImpl.class) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Eligibility'", e);
            return null;
        }
    }

    public String apply(type.TApplicantData applicantData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(applicantData, requestedProduct, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public String apply(type.TApplicantData applicantData, type.TRequestedProduct requestedProduct, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'Eligibility'
            long eligibilityStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments eligibilityArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eligibilityArguments_.put("applicantData", applicantData);
            eligibilityArguments_.put("requestedProduct", requestedProduct);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, eligibilityArguments_);

            // Apply child decisions
            Boolean preBureauAffordability = this.preBureauAffordability.apply(applicantData, requestedProduct, annotationSet_, eventListener_, externalExecutor_);
            String preBureauRiskCategory = this.preBureauRiskCategory.apply(applicantData, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate decision 'Eligibility'
            String output_ = evaluate(applicantData, preBureauAffordability, preBureauRiskCategory, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'Eligibility'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, eligibilityArguments_, output_, (System.currentTimeMillis() - eligibilityStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Eligibility' evaluation", e);
            return null;
        }
    }

    private String evaluate(type.TApplicantData applicantData, Boolean preBureauAffordability, String preBureauRiskCategory, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return EligibilityRules(preBureauRiskCategory, preBureauAffordability, ((java.math.BigDecimal)(applicantData != null ? applicantData.getAge() : null)), annotationSet_, eventListener_, externalExecutor_);
    }
}
