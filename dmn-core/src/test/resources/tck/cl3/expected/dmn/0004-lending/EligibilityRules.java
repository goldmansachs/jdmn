
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "EligibilityRules"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "EligibilityRules",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
    rulesCount = 4
)
public class EligibilityRules extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "EligibilityRules",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
        4
    );

    public static final EligibilityRules INSTANCE = new EligibilityRules();

    private EligibilityRules() {
    }

    public static String EligibilityRules(String preBureauRiskCategory, Boolean preBureauAffordability, java.math.BigDecimal age, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return INSTANCE.apply(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_);
    }

    private String apply(String preBureauRiskCategory, Boolean preBureauAffordability, java.math.BigDecimal age, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start BKM 'EligibilityRules'
            long eligibilityRulesStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments eligibilityRulesArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eligibilityRulesArguments_.put("preBureauRiskCategory", preBureauRiskCategory);
            eligibilityRulesArguments_.put("preBureauAffordability", preBureauAffordability);
            eligibilityRulesArguments_.put("age", age);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, eligibilityRulesArguments_);

            // Evaluate BKM 'EligibilityRules'
            String output_ = evaluate(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_);

            // End BKM 'EligibilityRules'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, eligibilityRulesArguments_, output_, (System.currentTimeMillis() - eligibilityRulesStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'EligibilityRules' evaluation", e);
            return null;
        }
    }

    private String evaluate(String preBureauRiskCategory, Boolean preBureauAffordability, java.math.BigDecimal age, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY);
            output_ = ruleOutput_ == null ? null : ((EligibilityRulesRuleOutput)ruleOutput_).getEligibilityRules();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(String preBureauRiskCategory, Boolean preBureauAffordability, java.math.BigDecimal age, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        EligibilityRulesRuleOutput output_ = new EligibilityRulesRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (stringEqual(preBureauRiskCategory, "DECLINE")),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setEligibilityRules("INELIGIBLE");
            output_.setEligibilityRulesPriority(2);

            // Add annotation
            annotationSet_.addAnnotation("EligibilityRules", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(String preBureauRiskCategory, Boolean preBureauAffordability, java.math.BigDecimal age, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        EligibilityRulesRuleOutput output_ = new EligibilityRulesRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            (booleanEqual(preBureauAffordability, Boolean.FALSE)),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setEligibilityRules("INELIGIBLE");
            output_.setEligibilityRulesPriority(2);

            // Add annotation
            annotationSet_.addAnnotation("EligibilityRules", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(String preBureauRiskCategory, Boolean preBureauAffordability, java.math.BigDecimal age, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        EligibilityRulesRuleOutput output_ = new EligibilityRulesRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            true,
            (numericLessThan(age, number("18")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setEligibilityRules("INELIGIBLE");
            output_.setEligibilityRulesPriority(2);

            // Add annotation
            annotationSet_.addAnnotation("EligibilityRules", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(String preBureauRiskCategory, Boolean preBureauAffordability, java.math.BigDecimal age, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        EligibilityRulesRuleOutput output_ = new EligibilityRulesRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setEligibilityRules("ELIGIBLE");
            output_.setEligibilityRulesPriority(1);

            // Add annotation
            annotationSet_.addAnnotation("EligibilityRules", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
