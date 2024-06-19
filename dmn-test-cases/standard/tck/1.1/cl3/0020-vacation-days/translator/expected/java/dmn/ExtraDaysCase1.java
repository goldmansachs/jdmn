
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Extra days case 1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Extra days case 1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 2
)
public class ExtraDaysCase1 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Extra days case 1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        2
    );

    public ExtraDaysCase1() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Age") != null ? number(input_.get("Age")) : null), (input_.get("Years of Service") != null ? number(input_.get("Years of Service")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ExtraDaysCase1'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.lang.Number age, java.lang.Number yearsOfService, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Extra days case 1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long extraDaysCase1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments extraDaysCase1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            extraDaysCase1Arguments_.put("Age", age);
            extraDaysCase1Arguments_.put("Years of Service", yearsOfService);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, extraDaysCase1Arguments_);

            // Evaluate decision 'Extra days case 1'
            java.lang.Number output_ = lambda.apply(age, yearsOfService, context_);

            // End decision 'Extra days case 1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, extraDaysCase1Arguments_, output_, (System.currentTimeMillis() - extraDaysCase1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Extra days case 1' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {
            public java.lang.Number apply(Object... args_) {
                java.lang.Number age = 0 < args_.length ? (java.lang.Number) args_[0] : null;
                java.lang.Number yearsOfService = 1 < args_.length ? (java.lang.Number) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply rules and collect results
                com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
                ruleOutputList_.add(rule0(age, yearsOfService, context_));
                ruleOutputList_.add(rule1(age, yearsOfService, context_));

                // Return results based on hit policy
                java.lang.Number output_;
                if (ruleOutputList_.noMatchedRules()) {
                    // Default value
                    output_ = number("0");
                } else {
                    List<? extends com.gs.dmn.runtime.RuleOutput> ruleOutputs_ = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT);
                    output_ = max(ruleOutputs_.stream().map(o -> ((ExtraDaysCase1RuleOutput)o).getExtraDaysCase1()).collect(Collectors.toList()));
                }

                return output_;
            }
    };

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(java.lang.Number age, java.lang.Number yearsOfService, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ExtraDaysCase1RuleOutput output_ = new ExtraDaysCase1RuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanOr(numericLessThan(age, number("18")), numericGreaterEqualThan(age, number("60"))),
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setExtraDaysCase1(number("5"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(java.lang.Number age, java.lang.Number yearsOfService, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ExtraDaysCase1RuleOutput output_ = new ExtraDaysCase1RuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            numericGreaterEqualThan(yearsOfService, number("30"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setExtraDaysCase1(number("5"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
