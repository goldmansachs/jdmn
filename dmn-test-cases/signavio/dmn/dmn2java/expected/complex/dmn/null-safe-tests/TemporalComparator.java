
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "temporalComparator"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "temporalComparator",
    label = "temporalComparator ",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 6
)
public class TemporalComparator extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "temporalComparator",
        "temporalComparator ",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        6
    );

    public TemporalComparator() {
    }

    public List<String> apply(String dateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((dateTime != null ? dateAndTime(dateTime) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'TemporalComparator'", e);
            return null;
        }
    }

    public List<String> apply(String dateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((dateTime != null ? dateAndTime(dateTime) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'TemporalComparator'", e);
            return null;
        }
    }

    public List<String> apply(javax.xml.datatype.XMLGregorianCalendar dateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(dateTime, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(javax.xml.datatype.XMLGregorianCalendar dateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'temporalComparator'
            long temporalComparatorStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments temporalComparatorArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            temporalComparatorArguments_.put("dateTime", dateTime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, temporalComparatorArguments_);

            // Evaluate decision 'temporalComparator'
            List<String> output_ = evaluate(dateTime, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'temporalComparator'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, temporalComparatorArguments_, output_, (System.currentTimeMillis() - temporalComparatorStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'temporalComparator' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(javax.xml.datatype.XMLGregorianCalendar dateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(dateTime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(dateTime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(dateTime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(dateTime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(dateTime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule5(dateTime, annotationSet_, eventListener_, externalExecutor_));

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
            output_ = ruleOutputs_.stream().map(o -> ((TemporalComparatorRuleOutput)o).getTemporalComparator()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(javax.xml.datatype.XMLGregorianCalendar dateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TemporalComparatorRuleOutput output_ = new TemporalComparatorRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateTimeLessThan(dateTime, dateAndTime("2015-12-31T18:00:00-0500")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTemporalComparator("isBefore");

            // Add annotation
            annotationSet_.addAnnotation("temporalComparator", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(javax.xml.datatype.XMLGregorianCalendar dateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TemporalComparatorRuleOutput output_ = new TemporalComparatorRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateTimeGreaterThan(dateTime, dateAndTime("2015-12-31T18:00:00-0500")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTemporalComparator("isAfter");

            // Add annotation
            annotationSet_.addAnnotation("temporalComparator", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(javax.xml.datatype.XMLGregorianCalendar dateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TemporalComparatorRuleOutput output_ = new TemporalComparatorRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((dateTimeEqual(dateTime, dateAndTime("2015-12-31T18:00:00-0500"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTemporalComparator("isNotOn");

            // Add annotation
            annotationSet_.addAnnotation("temporalComparator", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule3(javax.xml.datatype.XMLGregorianCalendar dateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TemporalComparatorRuleOutput output_ = new TemporalComparatorRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateTimeLessEqualThan(dateTime, dateAndTime("2015-12-31T18:00:00-0500")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTemporalComparator("until");

            // Add annotation
            annotationSet_.addAnnotation("temporalComparator", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule4(javax.xml.datatype.XMLGregorianCalendar dateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TemporalComparatorRuleOutput output_ = new TemporalComparatorRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateTimeGreaterEqualThan(dateTime, dateAndTime("2015-12-31T18:00:00-0500")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTemporalComparator("from");

            // Add annotation
            annotationSet_.addAnnotation("temporalComparator", 4, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule5(javax.xml.datatype.XMLGregorianCalendar dateTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TemporalComparatorRuleOutput output_ = new TemporalComparatorRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateTime == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTemporalComparator("notDefined");

            // Add annotation
            annotationSet_.addAnnotation("temporalComparator", 5, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
