
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "makeCreditDecision"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "makeCreditDecision",
    label = "Make credit decision",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 3
)
public class MakeCreditDecision extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "makeCreditDecision",
        "Make credit decision",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        3
    );

    private final CompareAgainstLendingThreshold compareAgainstLendingThreshold;

    public MakeCreditDecision() {
        this(new CompareAgainstLendingThreshold());
    }

    public MakeCreditDecision(CompareAgainstLendingThreshold compareAgainstLendingThreshold) {
        this.compareAgainstLendingThreshold = compareAgainstLendingThreshold;
    }

    public String apply(String applicant, String currentRiskAppetite, String lendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((applicant != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicant, new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), (currentRiskAppetite != null ? number(currentRiskAppetite) : null), (lendingThreshold != null ? number(lendingThreshold) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'MakeCreditDecision'", e);
            return null;
        }
    }

    public String apply(String applicant, String currentRiskAppetite, String lendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((applicant != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(applicant, new com.fasterxml.jackson.core.type.TypeReference<type.ApplicantImpl>() {}) : null), (currentRiskAppetite != null ? number(currentRiskAppetite) : null), (lendingThreshold != null ? number(lendingThreshold) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'MakeCreditDecision'", e);
            return null;
        }
    }

    public String apply(type.Applicant applicant, java.math.BigDecimal currentRiskAppetite, java.math.BigDecimal lendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(type.Applicant applicant, java.math.BigDecimal currentRiskAppetite, java.math.BigDecimal lendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'makeCreditDecision'
            long makeCreditDecisionStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments makeCreditDecisionArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            makeCreditDecisionArguments_.put("Applicant", applicant);
            makeCreditDecisionArguments_.put("Current risk appetite", currentRiskAppetite);
            makeCreditDecisionArguments_.put("Lending threshold", lendingThreshold);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, makeCreditDecisionArguments_);

            // Apply child decisions
            java.math.BigDecimal compareAgainstLendingThreshold = this.compareAgainstLendingThreshold.apply(applicant, currentRiskAppetite, lendingThreshold, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'makeCreditDecision'
            String output_ = evaluate(compareAgainstLendingThreshold, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'makeCreditDecision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, makeCreditDecisionArguments_, output_, (System.currentTimeMillis() - makeCreditDecisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'makeCreditDecision' evaluation", e);
            return null;
        }
    }

    protected String evaluate(java.math.BigDecimal compareAgainstLendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(compareAgainstLendingThreshold, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(compareAgainstLendingThreshold, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(compareAgainstLendingThreshold, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((MakeCreditDecisionRuleOutput)ruleOutput_).getMakeCreditDecision();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal compareAgainstLendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        MakeCreditDecisionRuleOutput output_ = new MakeCreditDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericLessThan(compareAgainstLendingThreshold, numericUnaryMinus(number("0.1"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setMakeCreditDecision("Reject");

            // Add annotation
            annotationSet_.addAnnotation("makeCreditDecision", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(java.math.BigDecimal compareAgainstLendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        MakeCreditDecisionRuleOutput output_ = new MakeCreditDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanAnd(numericGreaterEqualThan(compareAgainstLendingThreshold, numericUnaryMinus(number("0.1"))), numericLessEqualThan(compareAgainstLendingThreshold, number("0.1"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setMakeCreditDecision("Recommend further assessment");

            // Add annotation
            annotationSet_.addAnnotation("makeCreditDecision", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(java.math.BigDecimal compareAgainstLendingThreshold, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        MakeCreditDecisionRuleOutput output_ = new MakeCreditDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterThan(compareAgainstLendingThreshold, number("0.1")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setMakeCreditDecision("Accept");

            // Add annotation
            annotationSet_.addAnnotation("makeCreditDecision", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
