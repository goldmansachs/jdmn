
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "comparator"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "comparator",
    label = "comparator ",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 6
)
public class Comparator extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "comparator",
        "comparator ",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        6
    );

    public Comparator() {
    }

    public List<String> apply(String numberA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((numberA != null ? number(numberA) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Comparator'", e);
            return null;
        }
    }

    public List<String> apply(String numberA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((numberA != null ? number(numberA) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Comparator'", e);
            return null;
        }
    }

    public List<String> apply(java.math.BigDecimal numberA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(numberA, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(java.math.BigDecimal numberA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'comparator'
            long comparatorStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments comparatorArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            comparatorArguments_.put("numberA", numberA);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, comparatorArguments_);

            // Evaluate decision 'comparator'
            List<String> output_ = evaluate(numberA, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'comparator'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, comparatorArguments_, output_, (System.currentTimeMillis() - comparatorStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'comparator' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(java.math.BigDecimal numberA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(numberA, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(numberA, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(numberA, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(numberA, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(numberA, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule5(numberA, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        List<String> output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
            if (output_ == null) {
                output_ = asList();
            }
        } else {
            List<? extends com.gs.dmn.runtime.RuleOutput> ruleOutputs_ = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT);
            output_ = ruleOutputs_.stream().map(o -> ((ComparatorRuleOutput)o).getComparator()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal numberA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ComparatorRuleOutput output_ = new ComparatorRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterThan(numberA, number("0")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setComparator("rule1");

            // Add annotation
            annotationSet_.addAnnotation("comparator", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(java.math.BigDecimal numberA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ComparatorRuleOutput output_ = new ComparatorRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericLessThan(numberA, number("0")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setComparator("rule2");

            // Add annotation
            annotationSet_.addAnnotation("comparator", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(java.math.BigDecimal numberA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ComparatorRuleOutput output_ = new ComparatorRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericGreaterEqualThan(numberA, number("2")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setComparator("rule3");

            // Add annotation
            annotationSet_.addAnnotation("comparator", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule3(java.math.BigDecimal numberA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ComparatorRuleOutput output_ = new ComparatorRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericLessEqualThan(numberA, numericUnaryMinus(number("2"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setComparator("rule4");

            // Add annotation
            annotationSet_.addAnnotation("comparator", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule4(java.math.BigDecimal numberA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ComparatorRuleOutput output_ = new ComparatorRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setComparator("rule5");

            // Add annotation
            annotationSet_.addAnnotation("comparator", 4, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule5(java.math.BigDecimal numberA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        ComparatorRuleOutput output_ = new ComparatorRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numberA == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setComparator("bottomRule");

            // Add annotation
            annotationSet_.addAnnotation("comparator", 5, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
