
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "RoutingRules"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "RoutingRules",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
    rulesCount = 5
)
public class RoutingRules extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "RoutingRules",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
        5
    );

    public static final RoutingRules INSTANCE = new RoutingRules();

    private RoutingRules() {
    }

    public static String RoutingRules(String postBureauRiskCategory, Boolean postBureauAffordability, Boolean bankrupt, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return INSTANCE.apply(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, annotationSet_, eventListener_, externalExecutor_);
    }

    private String apply(String postBureauRiskCategory, Boolean postBureauAffordability, Boolean bankrupt, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start BKM 'RoutingRules'
            long routingRulesStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments routingRulesArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            routingRulesArguments_.put("postBureauRiskCategory", postBureauRiskCategory);
            routingRulesArguments_.put("postBureauAffordability", postBureauAffordability);
            routingRulesArguments_.put("bankrupt", bankrupt);
            routingRulesArguments_.put("creditScore", creditScore);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, routingRulesArguments_);

            // Evaluate BKM 'RoutingRules'
            String output_ = evaluate(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, annotationSet_, eventListener_, externalExecutor_);

            // End BKM 'RoutingRules'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, routingRulesArguments_, output_, (System.currentTimeMillis() - routingRulesStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'RoutingRules' evaluation", e);
            return null;
        }
    }

    private String evaluate(String postBureauRiskCategory, Boolean postBureauAffordability, Boolean bankrupt, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY);
            output_ = ruleOutput_ == null ? null : ((RoutingRulesRuleOutput)ruleOutput_).getRoutingRules();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(String postBureauRiskCategory, Boolean postBureauAffordability, Boolean bankrupt, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        RoutingRulesRuleOutput output_ = new RoutingRulesRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            (booleanEqual(postBureauAffordability, Boolean.FALSE)),
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setRoutingRules("DECLINE");
            output_.setRoutingRulesPriority(3);

            // Add annotation
            annotationSet_.addAnnotation("RoutingRules", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(String postBureauRiskCategory, Boolean postBureauAffordability, Boolean bankrupt, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        RoutingRulesRuleOutput output_ = new RoutingRulesRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            true,
            (booleanEqual(bankrupt, Boolean.TRUE)),
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setRoutingRules("DECLINE");
            output_.setRoutingRulesPriority(3);

            // Add annotation
            annotationSet_.addAnnotation("RoutingRules", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(String postBureauRiskCategory, Boolean postBureauAffordability, Boolean bankrupt, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        RoutingRulesRuleOutput output_ = new RoutingRulesRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            (stringEqual(postBureauRiskCategory, "HIGH")),
            true,
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setRoutingRules("REFER");
            output_.setRoutingRulesPriority(2);

            // Add annotation
            annotationSet_.addAnnotation("RoutingRules", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(String postBureauRiskCategory, Boolean postBureauAffordability, Boolean bankrupt, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        RoutingRulesRuleOutput output_ = new RoutingRulesRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            true,
            true,
            (numericLessThan(creditScore, number("580")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setRoutingRules("REFER");
            output_.setRoutingRulesPriority(2);

            // Add annotation
            annotationSet_.addAnnotation("RoutingRules", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule4(String postBureauRiskCategory, Boolean postBureauAffordability, Boolean bankrupt, java.math.BigDecimal creditScore, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        RoutingRulesRuleOutput output_ = new RoutingRulesRuleOutput(false);
        if (Boolean.TRUE == booleanAnd(
            true,
            true,
            true,
            true
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setRoutingRules("ACCEPT");
            output_.setRoutingRulesPriority(1);

            // Add annotation
            annotationSet_.addAnnotation("RoutingRules", 4, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
