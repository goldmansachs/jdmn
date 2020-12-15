
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "describeAgesList"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "describeAgesList",
    label = "describeAgesList",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 6
)
public class DescribeAgesList extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "describeAgesList",
        "describeAgesList",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        6
    );

    public DescribeAgesList() {
    }

    public List<String> apply(String ages, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((ages != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(ages, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DescribeAgesList'", e);
            return null;
        }
    }

    public List<String> apply(String ages, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((ages != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(ages, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DescribeAgesList'", e);
            return null;
        }
    }

    public List<String> apply(List<java.math.BigDecimal> ages, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(ages, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(List<java.math.BigDecimal> ages, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'describeAgesList'
            long describeAgesListStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments describeAgesListArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            describeAgesListArguments_.put("ages", ages);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, describeAgesListArguments_);

            // Evaluate decision 'describeAgesList'
            List<String> output_ = evaluate(ages, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'describeAgesList'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, describeAgesListArguments_, output_, (System.currentTimeMillis() - describeAgesListStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'describeAgesList' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(List<java.math.BigDecimal> ages, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(ages, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(ages, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(ages, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(ages, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(ages, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule5(ages, annotationSet_, eventListener_, externalExecutor_));

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
            output_ = ruleOutputs_.stream().map(o -> ((DescribeAgesListRuleOutput)o).getListDescription()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(List<java.math.BigDecimal> ages, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DescribeAgesListRuleOutput output_ = new DescribeAgesListRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (listEqual(ages, asList(number("1"), number("2"), number("3"), number("4"), number("5"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setListDescription("exactly 1 to 5");

            // Add annotation
            annotationSet_.addAnnotation("describeAgesList", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(List<java.math.BigDecimal> ages, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DescribeAgesListRuleOutput output_ = new DescribeAgesListRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((listEqual(ages, asList(number("1"), number("2"), number("3"), number("4"), number("5")))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setListDescription("not exactly 1 to 5");

            // Add annotation
            annotationSet_.addAnnotation("describeAgesList", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(List<java.math.BigDecimal> ages, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DescribeAgesListRuleOutput output_ = new DescribeAgesListRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (elementOf(ages, asList(number("1"), number("2"), number("3"), number("4"), number("5"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setListDescription("only numbers between 1 and 5");

            // Add annotation
            annotationSet_.addAnnotation("describeAgesList", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(List<java.math.BigDecimal> ages, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DescribeAgesListRuleOutput output_ = new DescribeAgesListRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((notContainsAny(ages, asList(number("1"), number("2"), number("3"), number("4"), number("5")))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setListDescription("at least one number betwen 1 and 5");

            // Add annotation
            annotationSet_.addAnnotation("describeAgesList", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule4(List<java.math.BigDecimal> ages, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DescribeAgesListRuleOutput output_ = new DescribeAgesListRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (containsOnly(ages, asList(number("1"), number("2"), number("3"), number("4"), number("5"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setListDescription("only numbers between 1 and 5");

            // Add annotation
            annotationSet_.addAnnotation("describeAgesList", 4, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule5(List<java.math.BigDecimal> ages, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DescribeAgesListRuleOutput output_ = new DescribeAgesListRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (notContainsAny(ages, asList(number("1"), number("2"), number("3"), number("4"), number("5"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setListDescription("non of the numbers 1 to 5");

            // Add annotation
            annotationSet_.addAnnotation("describeAgesList", 5, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
