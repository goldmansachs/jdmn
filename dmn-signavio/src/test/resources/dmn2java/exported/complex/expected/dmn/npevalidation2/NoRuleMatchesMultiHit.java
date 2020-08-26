
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "noRuleMatchesMultiHit"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "noRuleMatchesMultiHit",
    label = "noRuleMatchesMultiHit",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.RULE_ORDER,
    rulesCount = 3
)
public class NoRuleMatchesMultiHit extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "noRuleMatchesMultiHit",
        "noRuleMatchesMultiHit",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.RULE_ORDER,
        3
    );

    public NoRuleMatchesMultiHit() {
    }

    public List<Boolean> apply(String second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((second != null ? number(second) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'NoRuleMatchesMultiHit'", e);
            return null;
        }
    }

    public List<Boolean> apply(String second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((second != null ? number(second) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'NoRuleMatchesMultiHit'", e);
            return null;
        }
    }

    public List<Boolean> apply(java.math.BigDecimal second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(second, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<Boolean> apply(java.math.BigDecimal second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'noRuleMatchesMultiHit'
            long noRuleMatchesMultiHitStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments noRuleMatchesMultiHitArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            noRuleMatchesMultiHitArguments_.put("second", second);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, noRuleMatchesMultiHitArguments_);

            // Evaluate decision 'noRuleMatchesMultiHit'
            List<Boolean> output_ = evaluate(second, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'noRuleMatchesMultiHit'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, noRuleMatchesMultiHitArguments_, output_, (System.currentTimeMillis() - noRuleMatchesMultiHitStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'noRuleMatchesMultiHit' evaluation", e);
            return null;
        }
    }

    protected List<Boolean> evaluate(java.math.BigDecimal second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(second, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(second, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(second, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        List<Boolean> output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
            if (output_ == null) {
                output_ = asList();
            }
        } else {
            List<? extends com.gs.dmn.runtime.RuleOutput> ruleOutputs_ = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.RULE_ORDER);
            output_ = ruleOutputs_.stream().map(o -> ((NoRuleMatchesMultiHitRuleOutput)o).getNoRuleMatchesMultiHit()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        NoRuleMatchesMultiHitRuleOutput output_ = new NoRuleMatchesMultiHitRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericEqual(second, number("1")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setNoRuleMatchesMultiHit(Boolean.TRUE);

            // Add annotation
            annotationSet_.addAnnotation("noRuleMatchesMultiHit", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(java.math.BigDecimal second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        NoRuleMatchesMultiHitRuleOutput output_ = new NoRuleMatchesMultiHitRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericEqual(second, number("2")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setNoRuleMatchesMultiHit(Boolean.TRUE);

            // Add annotation
            annotationSet_.addAnnotation("noRuleMatchesMultiHit", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(java.math.BigDecimal second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        NoRuleMatchesMultiHitRuleOutput output_ = new NoRuleMatchesMultiHitRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericEqual(second, number("3")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setNoRuleMatchesMultiHit(Boolean.TRUE);

            // Add annotation
            annotationSet_.addAnnotation("noRuleMatchesMultiHit", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
