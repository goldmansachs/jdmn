
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
public class RoutingRules extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "RoutingRules",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.PRIORITY,
        5
    );

    private static class RoutingRulesLazyHolder {
        static final RoutingRules INSTANCE = new RoutingRules();
    }
    public static RoutingRules instance() {
        return RoutingRulesLazyHolder.INSTANCE;
    }

    private RoutingRules() {
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("Post-bureauRiskCategory"), (input_.get("Post-bureauAffordability") != null ? Boolean.valueOf(input_.get("Post-bureauAffordability")) : null), (input_.get("Bankrupt") != null ? Boolean.valueOf(input_.get("Bankrupt")) : null), (input_.get("CreditScore") != null ? number(input_.get("CreditScore")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'RoutingRules'", e);
            return null;
        }
    }

    public String apply(String postBureauRiskCategory, Boolean postBureauAffordability, Boolean bankrupt, java.math.BigDecimal creditScore, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'RoutingRules'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long routingRulesStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments routingRulesArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            routingRulesArguments_.put("Post-bureauRiskCategory", postBureauRiskCategory);
            routingRulesArguments_.put("Post-bureauAffordability", postBureauAffordability);
            routingRulesArguments_.put("Bankrupt", bankrupt);
            routingRulesArguments_.put("CreditScore", creditScore);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, routingRulesArguments_);

            // Evaluate BKM 'RoutingRules'
            String output_ = lambda.apply(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_);

            // End BKM 'RoutingRules'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, routingRulesArguments_, output_, (System.currentTimeMillis() - routingRulesStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'RoutingRules' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String postBureauRiskCategory = 0 < args_.length ? (String) args_[0] : null;
                Boolean postBureauAffordability = 1 < args_.length ? (Boolean) args_[1] : null;
                Boolean bankrupt = 2 < args_.length ? (Boolean) args_[2] : null;
                java.math.BigDecimal creditScore = 3 < args_.length ? (java.math.BigDecimal) args_[3] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 4 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[4] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply rules and collect results
                com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
                ruleOutputList_.add(rule0(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_));
                ruleOutputList_.add(rule1(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_));
                ruleOutputList_.add(rule2(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_));
                ruleOutputList_.add(rule3(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_));
                ruleOutputList_.add(rule4(postBureauRiskCategory, postBureauAffordability, bankrupt, creditScore, context_));

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
    };

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(String postBureauRiskCategory, Boolean postBureauAffordability, Boolean bankrupt, java.math.BigDecimal creditScore, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        RoutingRulesRuleOutput output_ = new RoutingRulesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            booleanEqual(postBureauAffordability, Boolean.FALSE),
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setRoutingRules("DECLINE");
            output_.setRoutingRulesPriority(3);
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(String postBureauRiskCategory, Boolean postBureauAffordability, Boolean bankrupt, java.math.BigDecimal creditScore, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        RoutingRulesRuleOutput output_ = new RoutingRulesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanEqual(bankrupt, Boolean.TRUE),
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setRoutingRules("DECLINE");
            output_.setRoutingRulesPriority(3);
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(String postBureauRiskCategory, Boolean postBureauAffordability, Boolean bankrupt, java.math.BigDecimal creditScore, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        RoutingRulesRuleOutput output_ = new RoutingRulesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            stringEqual(postBureauRiskCategory, "HIGH"),
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setRoutingRules("REFER");
            output_.setRoutingRulesPriority(2);
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(String postBureauRiskCategory, Boolean postBureauAffordability, Boolean bankrupt, java.math.BigDecimal creditScore, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        RoutingRulesRuleOutput output_ = new RoutingRulesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            numericLessThan(creditScore, number("580"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setRoutingRules("REFER");
            output_.setRoutingRulesPriority(2);
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule4(String postBureauRiskCategory, Boolean postBureauAffordability, Boolean bankrupt, java.math.BigDecimal creditScore, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        RoutingRulesRuleOutput output_ = new RoutingRulesRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setRoutingRules("ACCEPT");
            output_.setRoutingRulesPriority(1);
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
