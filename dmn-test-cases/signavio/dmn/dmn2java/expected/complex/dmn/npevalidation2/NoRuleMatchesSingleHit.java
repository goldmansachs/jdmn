
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "noRuleMatchesSingleHit"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "noRuleMatchesSingleHit",
    label = "noRuleMatchesSingleHit",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 3
)
public class NoRuleMatchesSingleHit extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "noRuleMatchesSingleHit",
        "noRuleMatchesSingleHit",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        3
    );

    public NoRuleMatchesSingleHit() {
    }

    public Boolean apply(String second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((second != null ? number(second) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'NoRuleMatchesSingleHit'", e);
            return null;
        }
    }

    public Boolean apply(String second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((second != null ? number(second) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'NoRuleMatchesSingleHit'", e);
            return null;
        }
    }

    public Boolean apply(java.math.BigDecimal second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(second, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Boolean apply(java.math.BigDecimal second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'noRuleMatchesSingleHit'
            long noRuleMatchesSingleHitStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments noRuleMatchesSingleHitArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            noRuleMatchesSingleHitArguments_.put("second", second);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, noRuleMatchesSingleHitArguments_);

            // Evaluate decision 'noRuleMatchesSingleHit'
            Boolean output_ = evaluate(second, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'noRuleMatchesSingleHit'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, noRuleMatchesSingleHitArguments_, output_, (System.currentTimeMillis() - noRuleMatchesSingleHitStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'noRuleMatchesSingleHit' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(java.math.BigDecimal second, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(second, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(second, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(second, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        Boolean output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((NoRuleMatchesSingleHitRuleOutput)ruleOutput_).getNoRuleMatchesSingleHit();
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
        NoRuleMatchesSingleHitRuleOutput output_ = new NoRuleMatchesSingleHitRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericEqual(second, number("1")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setNoRuleMatchesSingleHit(Boolean.TRUE);

            // Add annotation
            annotationSet_.addAnnotation("noRuleMatchesSingleHit", 0, "");
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
        NoRuleMatchesSingleHitRuleOutput output_ = new NoRuleMatchesSingleHitRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericEqual(second, number("2")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setNoRuleMatchesSingleHit(Boolean.TRUE);

            // Add annotation
            annotationSet_.addAnnotation("noRuleMatchesSingleHit", 1, "");
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
        NoRuleMatchesSingleHitRuleOutput output_ = new NoRuleMatchesSingleHitRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericEqual(second, number("3")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setNoRuleMatchesSingleHit(Boolean.TRUE);

            // Add annotation
            annotationSet_.addAnnotation("noRuleMatchesSingleHit", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
