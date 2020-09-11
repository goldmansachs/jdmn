
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "compareAgainstLendingThreshold"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "compareAgainstLendingThreshold",
    label = "Compare against lending threshold",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.ANY,
    rulesCount = 2
)
public class CompareAgainstLendingThreshold extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "compareAgainstLendingThreshold",
        "Compare against lending threshold",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.ANY,
        2
    );

    public static java.util.Map<String, Object> requestToMap(proto.CompareAgainstLendingThresholdRequest compareAgainstLendingThresholdRequest_) {
        // Create arguments from Request Message
        type.Applicant applicant = type.Applicant.toApplicant(compareAgainstLendingThresholdRequest_.getApplicant());
        java.math.BigDecimal currentRiskAppetite = java.math.BigDecimal.valueOf(compareAgainstLendingThresholdRequest_.getCurrentRiskAppetite());
        java.math.BigDecimal lendingThreshold = java.math.BigDecimal.valueOf(compareAgainstLendingThresholdRequest_.getLendingThreshold());

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("Applicant", applicant);
        map_.put("Current risk appetite", currentRiskAppetite);
        map_.put("Lending threshold", lendingThreshold);
        return map_;
    }

    public static java.math.BigDecimal responseToOutput(proto.CompareAgainstLendingThresholdResponse compareAgainstLendingThresholdResponse_) {
        // Extract and convert output
        return java.math.BigDecimal.valueOf(compareAgainstLendingThresholdResponse_.getCompareAgainstLendingThreshold());
    }

    private final AssessApplicantAge assessApplicantAge;
    private final AssessIssueRisk assessIssueRisk;

    public CompareAgainstLendingThreshold() {
        this(new AssessApplicantAge(), new AssessIssueRisk());
    }

    public CompareAgainstLendingThreshold(AssessApplicantAge assessApplicantAge, AssessIssueRisk assessIssueRisk) {
        this.assessApplicantAge = assessApplicantAge;
        this.assessIssueRisk = assessIssueRisk;
    }

    public java.math.BigDecimal apply(String applicant, String currentRiskAppetite, String lendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((applicant != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicant, new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), (currentRiskAppetite != null ? number(currentRiskAppetite) : null), (lendingThreshold != null ? number(lendingThreshold) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'CompareAgainstLendingThreshold'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String applicant, String currentRiskAppetite, String lendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((applicant != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicant, new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), (currentRiskAppetite != null ? number(currentRiskAppetite) : null), (lendingThreshold != null ? number(lendingThreshold) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CompareAgainstLendingThreshold'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(type.Applicant applicant, java.math.BigDecimal currentRiskAppetite, java.math.BigDecimal lendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(type.Applicant applicant, java.math.BigDecimal currentRiskAppetite, java.math.BigDecimal lendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'compareAgainstLendingThreshold'
            long compareAgainstLendingThresholdStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments compareAgainstLendingThresholdArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            compareAgainstLendingThresholdArguments_.put("Applicant", applicant);
            compareAgainstLendingThresholdArguments_.put("Current risk appetite", currentRiskAppetite);
            compareAgainstLendingThresholdArguments_.put("Lending threshold", lendingThreshold);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compareAgainstLendingThresholdArguments_);

            // Apply child decisions
            java.math.BigDecimal assessApplicantAge = this.assessApplicantAge.apply(applicant, annotationSet_, eventListener_, externalExecutor_, cache_);
            java.math.BigDecimal assessIssueRisk = this.assessIssueRisk.apply(applicant, currentRiskAppetite, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'compareAgainstLendingThreshold'
            java.math.BigDecimal output_ = evaluate(assessApplicantAge, assessIssueRisk, lendingThreshold, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'compareAgainstLendingThreshold'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, compareAgainstLendingThresholdArguments_, output_, (System.currentTimeMillis() - compareAgainstLendingThresholdStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'compareAgainstLendingThreshold' evaluation", e);
            return null;
        }
    }

    public proto.CompareAgainstLendingThresholdResponse apply(proto.CompareAgainstLendingThresholdRequest compareAgainstLendingThresholdRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(compareAgainstLendingThresholdRequest_, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public proto.CompareAgainstLendingThresholdResponse apply(proto.CompareAgainstLendingThresholdRequest compareAgainstLendingThresholdRequest_, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Create arguments from Request Message
        type.Applicant applicant = type.Applicant.toApplicant(compareAgainstLendingThresholdRequest_.getApplicant());
        java.math.BigDecimal currentRiskAppetite = java.math.BigDecimal.valueOf(compareAgainstLendingThresholdRequest_.getCurrentRiskAppetite());
        java.math.BigDecimal lendingThreshold = java.math.BigDecimal.valueOf(compareAgainstLendingThresholdRequest_.getLendingThreshold());

        // Invoke apply method
        java.math.BigDecimal output_ = apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_, eventListener_, externalExecutor_, cache_);

        // Convert output to Response Message
        proto.CompareAgainstLendingThresholdResponse.Builder builder_ = proto.CompareAgainstLendingThresholdResponse.newBuilder();
        Double outputProto_ = (output_ == null ? 0.0 : output_.doubleValue());
        builder_.setCompareAgainstLendingThreshold(outputProto_);
        return builder_.build();
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal assessApplicantAge, java.math.BigDecimal assessIssueRisk, java.math.BigDecimal lendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(assessApplicantAge, assessIssueRisk, lendingThreshold, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(assessApplicantAge, assessIssueRisk, lendingThreshold, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        java.math.BigDecimal output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.ANY);
            output_ = ruleOutput_ == null ? null : ((CompareAgainstLendingThresholdRuleOutput)ruleOutput_).getCompareAgainstLendingThreshold();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"Raw issue score is \") + string(assessIssueRisk) + string(\", Age-weighted score is \") + string(assessApplicantAge) + string(\", Acceptance threshold is \") + string(lendingThreshold)")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal assessApplicantAge, java.math.BigDecimal assessIssueRisk, java.math.BigDecimal lendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"Raw issue score is \") + string(assessIssueRisk) + string(\", Age-weighted score is \") + string(assessApplicantAge) + string(\", Acceptance threshold is \") + string(lendingThreshold)");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompareAgainstLendingThresholdRuleOutput output_ = new CompareAgainstLendingThresholdRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((lendingThreshold == null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setCompareAgainstLendingThreshold(numericSubtract(numericAdd(assessIssueRisk, assessApplicantAge), lendingThreshold));

            // Add annotation
            annotationSet_.addAnnotation("compareAgainstLendingThreshold", 0, stringAdd(stringAdd(stringAdd(stringAdd(stringAdd(string("Raw issue score is "), string(assessIssueRisk)), string(", Age-weighted score is ")), string(assessApplicantAge)), string(", Acceptance threshold is ")), string(lendingThreshold)));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"Error: threshold undefined\")")
    public com.gs.dmn.runtime.RuleOutput rule1(java.math.BigDecimal assessApplicantAge, java.math.BigDecimal assessIssueRisk, java.math.BigDecimal lendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "string(\"Error: threshold undefined\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompareAgainstLendingThresholdRuleOutput output_ = new CompareAgainstLendingThresholdRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (lendingThreshold == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setCompareAgainstLendingThreshold(number("0"));

            // Add annotation
            annotationSet_.addAnnotation("compareAgainstLendingThreshold", 1, string("Error: threshold undefined"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
