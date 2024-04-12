
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

    private static class CompareAgainstLendingThresholdLazyHolder {
        static final CompareAgainstLendingThreshold INSTANCE = new CompareAgainstLendingThreshold(AssessApplicantAge.instance(), AssessIssueRisk.instance());
    }
    public static CompareAgainstLendingThreshold instance() {
        return CompareAgainstLendingThresholdLazyHolder.INSTANCE;
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

    @java.lang.Override()
    public java.math.BigDecimal applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Applicant") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("Applicant"), new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), (input_.get("Current risk appetite") != null ? number(input_.get("Current risk appetite")) : null), (input_.get("Lending threshold") != null ? number(input_.get("Lending threshold")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CompareAgainstLendingThreshold'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(type.Applicant applicant, java.math.BigDecimal currentRiskAppetite, java.math.BigDecimal lendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'compareAgainstLendingThreshold'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long compareAgainstLendingThresholdStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments compareAgainstLendingThresholdArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            compareAgainstLendingThresholdArguments_.put("Applicant", applicant);
            compareAgainstLendingThresholdArguments_.put("Current risk appetite", currentRiskAppetite);
            compareAgainstLendingThresholdArguments_.put("Lending threshold", lendingThreshold);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compareAgainstLendingThresholdArguments_);

            // Evaluate decision 'compareAgainstLendingThreshold'
            java.math.BigDecimal output_ = evaluate(applicant, currentRiskAppetite, lendingThreshold, context_);

            // End decision 'compareAgainstLendingThreshold'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, compareAgainstLendingThresholdArguments_, output_, (System.currentTimeMillis() - compareAgainstLendingThresholdStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'compareAgainstLendingThreshold' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(type.Applicant applicant, java.math.BigDecimal currentRiskAppetite, java.math.BigDecimal lendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        java.math.BigDecimal assessApplicantAge = this.assessApplicantAge.apply(applicant, context_);
        java.math.BigDecimal assessIssueRisk = this.assessIssueRisk.apply(applicant, currentRiskAppetite, context_);

        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(assessApplicantAge, assessIssueRisk, lendingThreshold, context_));
        ruleOutputList_.add(rule1(assessApplicantAge, assessIssueRisk, lendingThreshold, context_));

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
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal assessApplicantAge, java.math.BigDecimal assessIssueRisk, java.math.BigDecimal lendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"Raw issue score is \") + string(assessIssueRisk) + string(\", Age-weighted score is \") + string(assessApplicantAge) + string(\", Acceptance threshold is \") + string(lendingThreshold)");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompareAgainstLendingThresholdRuleOutput output_ = new CompareAgainstLendingThresholdRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(lendingThreshold == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setCompareAgainstLendingThreshold(numericSubtract(numericAdd(assessIssueRisk, assessApplicantAge), lendingThreshold));

            // Add annotations
            annotationSet_.addAnnotation("compareAgainstLendingThreshold", 0, stringAdd(stringAdd(stringAdd(stringAdd(stringAdd(string("Raw issue score is "), string(assessIssueRisk)), string(", Age-weighted score is ")), string(assessApplicantAge)), string(", Acceptance threshold is ")), string(lendingThreshold)));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"Error: threshold undefined\")")
    public com.gs.dmn.runtime.RuleOutput rule1(java.math.BigDecimal assessApplicantAge, java.math.BigDecimal assessIssueRisk, java.math.BigDecimal lendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "string(\"Error: threshold undefined\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompareAgainstLendingThresholdRuleOutput output_ = new CompareAgainstLendingThresholdRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            lendingThreshold == null
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setCompareAgainstLendingThreshold(number("0"));

            // Add annotations
            annotationSet_.addAnnotation("compareAgainstLendingThreshold", 1, string("Error: threshold undefined"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
