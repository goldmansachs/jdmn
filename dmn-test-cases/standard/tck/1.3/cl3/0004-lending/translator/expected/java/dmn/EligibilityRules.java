
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

    private static class EligibilityRulesLazyHolder {
        static final EligibilityRules INSTANCE = new EligibilityRules();
    }
    public static EligibilityRules instance() {
        return EligibilityRulesLazyHolder.INSTANCE;
    }

    private EligibilityRules() {
    }

    public String apply(String preBureauRiskCategory, Boolean preBureauAffordability, java.math.BigDecimal age, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start BKM 'EligibilityRules'
            long eligibilityRulesStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments eligibilityRulesArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eligibilityRulesArguments_.put("'Pre-bureauRiskCategory'", preBureauRiskCategory);
            eligibilityRulesArguments_.put("'Pre-bureauAffordability'", preBureauAffordability);
            eligibilityRulesArguments_.put("Age", age);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, eligibilityRulesArguments_);

            // Evaluate BKM 'EligibilityRules'
            String output_ = lambda.apply(preBureauRiskCategory, preBureauAffordability, age, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End BKM 'EligibilityRules'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, eligibilityRulesArguments_, output_, (System.currentTimeMillis() - eligibilityRulesStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'EligibilityRules' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String preBureauRiskCategory = 0 < args_.length ? (String) args_[0] : null;
                Boolean preBureauAffordability = 1 < args_.length ? (Boolean) args_[1] : null;
                java.math.BigDecimal age = 2 < args_.length ? (java.math.BigDecimal) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 3 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[3] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 4 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[4] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 5 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[5] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 6 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[6] : null;

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
    };

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(String preBureauRiskCategory, Boolean preBureauAffordability, java.math.BigDecimal age, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        EligibilityRulesRuleOutput output_ = new EligibilityRulesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (stringEqual(preBureauRiskCategory, "DECLINE")),
            Boolean.TRUE,
            Boolean.TRUE
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
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            (booleanEqual(preBureauAffordability, Boolean.FALSE)),
            Boolean.TRUE
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
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
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
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE
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
