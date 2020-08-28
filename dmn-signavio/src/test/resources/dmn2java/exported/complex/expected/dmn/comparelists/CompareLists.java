
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "compareLists"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "compareLists",
    label = "CompareLists",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 2
)
public class CompareLists extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "compareLists",
        "CompareLists",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        2
    );

    public CompareLists() {
    }

    public java.math.BigDecimal apply(String l12_iterator, String l2_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((l12_iterator != null ? number(l12_iterator) : null), (l2_iterator != null ? number(l2_iterator) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'CompareLists'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String l12_iterator, String l2_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((l12_iterator != null ? number(l12_iterator) : null), (l2_iterator != null ? number(l2_iterator) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CompareLists'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal l12_iterator, java.math.BigDecimal l2_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(l12_iterator, l2_iterator, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal l12_iterator, java.math.BigDecimal l2_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'compareLists'
            long compareListsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments compareListsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            compareListsArguments_.put("L1", l12_iterator);
            compareListsArguments_.put("L2", l2_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compareListsArguments_);

            // Evaluate decision 'compareLists'
            java.math.BigDecimal output_ = evaluate(l12_iterator, l2_iterator, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'compareLists'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, compareListsArguments_, output_, (System.currentTimeMillis() - compareListsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'compareLists' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal l12_iterator, java.math.BigDecimal l2_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(l12_iterator, l2_iterator, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(l12_iterator, l2_iterator, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        java.math.BigDecimal output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((CompareListsRuleOutput)ruleOutput_).getCompareLists();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal l12_iterator, java.math.BigDecimal l2_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompareListsRuleOutput output_ = new CompareListsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericEqual(l12_iterator, l2_iterator)),
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setCompareLists(number("1"));

            // Add annotation
            annotationSet_.addAnnotation("compareLists", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(java.math.BigDecimal l12_iterator, java.math.BigDecimal l2_iterator, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompareListsRuleOutput output_ = new CompareListsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((numericEqual(l12_iterator, l2_iterator))),
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setCompareLists(number("0"));

            // Add annotation
            annotationSet_.addAnnotation("compareLists", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
