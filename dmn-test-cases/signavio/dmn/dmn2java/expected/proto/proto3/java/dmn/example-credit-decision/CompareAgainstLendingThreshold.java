
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
public class CompareAgainstLendingThreshold extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
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
        java.lang.Number currentRiskAppetite = ((java.lang.Number) java.math.BigDecimal.valueOf(compareAgainstLendingThresholdRequest_.getCurrentRiskAppetite()));
        java.lang.Number lendingThreshold = ((java.lang.Number) java.math.BigDecimal.valueOf(compareAgainstLendingThresholdRequest_.getLendingThreshold()));

        // Create map
        java.util.Map<String, Object> map_ = new java.util.LinkedHashMap<>();
        map_.put("Applicant", applicant);
        map_.put("Current risk appetite", currentRiskAppetite);
        map_.put("Lending threshold", lendingThreshold);
        return map_;
    }

    public static java.lang.Number responseToOutput(proto.CompareAgainstLendingThresholdResponse compareAgainstLendingThresholdResponse_) {
        // Extract and convert output
        return ((java.lang.Number) java.math.BigDecimal.valueOf(compareAgainstLendingThresholdResponse_.getCompareAgainstLendingThreshold()));
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
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Applicant") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("Applicant"), new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), (input_.get("Current risk appetite") != null ? number(input_.get("Current risk appetite")) : null), (input_.get("Lending threshold") != null ? number(input_.get("Lending threshold")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CompareAgainstLendingThreshold'", e);
            return null;
        }
    }

    public java.lang.Number apply(type.Applicant applicant, java.lang.Number currentRiskAppetite, java.lang.Number lendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
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
            java.lang.Number output_ = evaluate(applicant, currentRiskAppetite, lendingThreshold, context_);

            // End decision 'compareAgainstLendingThreshold'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, compareAgainstLendingThresholdArguments_, output_, (System.currentTimeMillis() - compareAgainstLendingThresholdStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'compareAgainstLendingThreshold' evaluation", e);
            return null;
        }
    }

    public proto.CompareAgainstLendingThresholdResponse applyProto(proto.CompareAgainstLendingThresholdRequest compareAgainstLendingThresholdRequest_, com.gs.dmn.runtime.ExecutionContext context_) {
        // Create arguments from Request Message
        type.Applicant applicant = type.Applicant.toApplicant(compareAgainstLendingThresholdRequest_.getApplicant());
        java.lang.Number currentRiskAppetite = ((java.lang.Number) java.math.BigDecimal.valueOf(compareAgainstLendingThresholdRequest_.getCurrentRiskAppetite()));
        java.lang.Number lendingThreshold = ((java.lang.Number) java.math.BigDecimal.valueOf(compareAgainstLendingThresholdRequest_.getLendingThreshold()));

        // Invoke apply method
        java.lang.Number output_ = apply(applicant, currentRiskAppetite, lendingThreshold, context_);

        // Convert output to Response Message
        proto.CompareAgainstLendingThresholdResponse.Builder builder_ = proto.CompareAgainstLendingThresholdResponse.newBuilder();
        Double outputProto_ = (output_ == null ? 0.0 : output_.doubleValue());
        builder_.setCompareAgainstLendingThreshold(outputProto_);
        return builder_.build();
    }

    protected java.lang.Number evaluate(type.Applicant applicant, java.lang.Number currentRiskAppetite, java.lang.Number lendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        java.lang.Number assessApplicantAge = this.assessApplicantAge.apply(applicant, context_);
        java.lang.Number assessIssueRisk = this.assessIssueRisk.apply(applicant, currentRiskAppetite, context_);

        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(assessApplicantAge, assessIssueRisk, lendingThreshold, context_));
        ruleOutputList_.add(rule1(assessApplicantAge, assessIssueRisk, lendingThreshold, context_));

        // Return results based on hit policy
        java.lang.Number output_;
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
    public com.gs.dmn.runtime.RuleOutput rule0(java.lang.Number assessApplicantAge, java.lang.Number assessIssueRisk, java.lang.Number lendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
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
            booleanNot(numericEqual(lendingThreshold, null))
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
    public com.gs.dmn.runtime.RuleOutput rule1(java.lang.Number assessApplicantAge, java.lang.Number assessIssueRisk, java.lang.Number lendingThreshold, com.gs.dmn.runtime.ExecutionContext context_) {
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
            numericEqual(lendingThreshold, null)
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
