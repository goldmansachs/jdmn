
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "datetimeOperators"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "datetimeOperators",
    label = "datetime operators",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 10
)
public class DatetimeOperators extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "datetimeOperators",
        "datetime operators",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        10
    );

    public DatetimeOperators() {
    }

    public List<String> apply(String datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((datetime != null ? dateAndTime(datetime) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DatetimeOperators'", e);
            return null;
        }
    }

    public List<String> apply(String datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((datetime != null ? dateAndTime(datetime) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DatetimeOperators'", e);
            return null;
        }
    }

    public List<String> apply(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(datetime, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'datetimeOperators'
            long datetimeOperatorsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments datetimeOperatorsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            datetimeOperatorsArguments_.put("datetime", datetime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, datetimeOperatorsArguments_);

            // Evaluate decision 'datetimeOperators'
            List<String> output_ = evaluate(datetime, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'datetimeOperators'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, datetimeOperatorsArguments_, output_, (System.currentTimeMillis() - datetimeOperatorsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'datetimeOperators' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(datetime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(datetime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(datetime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(datetime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(datetime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule5(datetime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule6(datetime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule7(datetime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule8(datetime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule9(datetime, annotationSet_, eventListener_, externalExecutor_));

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
            output_ = ruleOutputs_.stream().map(o -> ((DatetimeOperatorsRuleOutput)o).getDatetimeOperators()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"D3R1\")")
    public com.gs.dmn.runtime.RuleOutput rule0(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"D3R1\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DatetimeOperatorsRuleOutput output_ = new DatetimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateTimeEqual(datetime, dateAndTime("2016-12-31T19:00:00-0500")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDatetimeOperators("datetime new year started");

            // Add annotation
            annotationSet_.addAnnotation("datetimeOperators", 0, string("D3R1"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"D3R2\")")
    public com.gs.dmn.runtime.RuleOutput rule1(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "string(\"D3R2\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DatetimeOperatorsRuleOutput output_ = new DatetimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((dateTimeEqual(datetime, dateAndTime("2016-12-31T19:00:00-0500"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDatetimeOperators("datetime away from new year bonanza");

            // Add annotation
            annotationSet_.addAnnotation("datetimeOperators", 1, string("D3R2"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "string(\"D3R3\")")
    public com.gs.dmn.runtime.RuleOutput rule2(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "string(\"D3R3\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DatetimeOperatorsRuleOutput output_ = new DatetimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateTimeLessThan(datetime, dateAndTime("2017-03-25T21:00:00-0400")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDatetimeOperators("datetime before daylight savings");

            // Add annotation
            annotationSet_.addAnnotation("datetimeOperators", 2, string("D3R3"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "string(\"D3R4\")")
    public com.gs.dmn.runtime.RuleOutput rule3(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "string(\"D3R4\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DatetimeOperatorsRuleOutput output_ = new DatetimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateTimeLessEqualThan(datetime, dateAndTime("2017-03-25T21:00:00-0400")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDatetimeOperators("datetime upto exact daylight savings");

            // Add annotation
            annotationSet_.addAnnotation("datetimeOperators", 3, string("D3R4"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "string(\"D3R5\")")
    public com.gs.dmn.runtime.RuleOutput rule4(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "string(\"D3R5\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DatetimeOperatorsRuleOutput output_ = new DatetimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateTimeGreaterThan(datetime, dateAndTime("2017-03-25T21:00:00-0400")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDatetimeOperators("datetime after daylight savings");

            // Add annotation
            annotationSet_.addAnnotation("datetimeOperators", 4, string("D3R5"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "string(\"D3R6\")")
    public com.gs.dmn.runtime.RuleOutput rule5(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "string(\"D3R6\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DatetimeOperatorsRuleOutput output_ = new DatetimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateTimeGreaterEqualThan(datetime, dateAndTime("2017-03-25T21:00:00-0400")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDatetimeOperators("datetime daylight savings and beyond");

            // Add annotation
            annotationSet_.addAnnotation("datetimeOperators", 5, string("D3R6"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "string(\"D3R7\")")
    public com.gs.dmn.runtime.RuleOutput rule6(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(6, "string(\"D3R7\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DatetimeOperatorsRuleOutput output_ = new DatetimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanOr((dateTimeEqual(datetime, dateAndTime("2016-11-05T17:00:00-0400"))), (dateTimeEqual(datetime, dateAndTime("2016-12-31T19:00:00-0500"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDatetimeOperators("datetime fireworks");

            // Add annotation
            annotationSet_.addAnnotation("datetimeOperators", 6, string("D3R7"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "string(\"D3R8\")")
    public com.gs.dmn.runtime.RuleOutput rule7(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(7, "string(\"D3R8\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DatetimeOperatorsRuleOutput output_ = new DatetimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(booleanOr((dateTimeEqual(datetime, dateAndTime("2016-11-05T17:00:00-0400"))), (dateTimeEqual(datetime, dateAndTime("2016-12-31T19:00:00-0500")))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDatetimeOperators("datetime not fireworks");

            // Add annotation
            annotationSet_.addAnnotation("datetimeOperators", 7, string("D3R8"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 8, annotation = "string(\"D3R9\")")
    public com.gs.dmn.runtime.RuleOutput rule8(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(8, "string(\"D3R9\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DatetimeOperatorsRuleOutput output_ = new DatetimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((datetime == null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDatetimeOperators("datetime defined");

            // Add annotation
            annotationSet_.addAnnotation("datetimeOperators", 8, string("D3R9"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 9, annotation = "string(\"D3R10\")")
    public com.gs.dmn.runtime.RuleOutput rule9(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(9, "string(\"D3R10\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DatetimeOperatorsRuleOutput output_ = new DatetimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (datetime == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDatetimeOperators("datetime not defined");

            // Add annotation
            annotationSet_.addAnnotation("datetimeOperators", 9, string("D3R10"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
