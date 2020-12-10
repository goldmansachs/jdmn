
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "dateOperators"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "dateOperators",
    label = "date operators",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 10
)
public class DateOperators extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "dateOperators",
        "date operators",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        10
    );

    public DateOperators() {
    }

    public List<String> apply(String date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((date != null ? date(date) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DateOperators'", e);
            return null;
        }
    }

    public List<String> apply(String date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((date != null ? date(date) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DateOperators'", e);
            return null;
        }
    }

    public List<String> apply(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(date, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'dateOperators'
            long dateOperatorsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dateOperatorsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dateOperatorsArguments_.put("date", date);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateOperatorsArguments_);

            // Evaluate decision 'dateOperators'
            List<String> output_ = evaluate(date, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'dateOperators'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dateOperatorsArguments_, output_, (System.currentTimeMillis() - dateOperatorsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'dateOperators' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(date, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(date, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(date, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(date, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(date, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule5(date, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule6(date, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule7(date, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule8(date, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule9(date, annotationSet_, eventListener_, externalExecutor_));

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
            output_ = ruleOutputs_.stream().map(o -> ((DateOperatorsRuleOutput)o).getDateOperators()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"D1R1\")")
    public com.gs.dmn.runtime.RuleOutput rule0(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"D1R1\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateOperatorsRuleOutput output_ = new DateOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateEqual(date, date("2016-12-25")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateOperators("date christmas day");

            // Add annotation
            annotationSet_.addAnnotation("dateOperators", 0, string("D1R1"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"D1R2\")")
    public com.gs.dmn.runtime.RuleOutput rule1(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "string(\"D1R2\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateOperatorsRuleOutput output_ = new DateOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((dateEqual(date, date("2016-12-25"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateOperators("date not christmas day");

            // Add annotation
            annotationSet_.addAnnotation("dateOperators", 1, string("D1R2"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "string(\"D1R3\")")
    public com.gs.dmn.runtime.RuleOutput rule2(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "string(\"D1R3\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateOperatorsRuleOutput output_ = new DateOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateLessThan(date, date("2017-01-01")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateOperators("date before 2017");

            // Add annotation
            annotationSet_.addAnnotation("dateOperators", 2, string("D1R3"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "string(\"D1R4\")")
    public com.gs.dmn.runtime.RuleOutput rule3(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "string(\"D1R4\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateOperatorsRuleOutput output_ = new DateOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateLessEqualThan(date, date("2017-03-25")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateOperators("date before daylight savings 2017");

            // Add annotation
            annotationSet_.addAnnotation("dateOperators", 3, string("D1R4"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "string(\"D1R5\")")
    public com.gs.dmn.runtime.RuleOutput rule4(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "string(\"D1R5\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateOperatorsRuleOutput output_ = new DateOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateGreaterThan(date, date("2016-12-31")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateOperators("date 2017 and beyond");

            // Add annotation
            annotationSet_.addAnnotation("dateOperators", 4, string("D1R5"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "string(\"D1R6\")")
    public com.gs.dmn.runtime.RuleOutput rule5(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "string(\"D1R6\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateOperatorsRuleOutput output_ = new DateOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateGreaterEqualThan(date, date("2017-03-26")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateOperators("date daylight savings 2017 and beyond");

            // Add annotation
            annotationSet_.addAnnotation("dateOperators", 5, string("D1R6"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "string(\"D1R7\")")
    public com.gs.dmn.runtime.RuleOutput rule6(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(6, "string(\"D1R7\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateOperatorsRuleOutput output_ = new DateOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanOr((dateEqual(date, date("2016-12-26"))), (dateEqual(date, date("2017-01-01"))), (dateEqual(date, date("2016-12-25"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateOperators("date uk public holidays festive season");

            // Add annotation
            annotationSet_.addAnnotation("dateOperators", 6, string("D1R7"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "string(\"D1R8\")")
    public com.gs.dmn.runtime.RuleOutput rule7(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(7, "string(\"D1R8\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateOperatorsRuleOutput output_ = new DateOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(booleanOr((dateEqual(date, date("2016-12-25"))), (dateEqual(date, date("2016-12-26"))), (dateEqual(date, date("2017-01-01")))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateOperators("date not uk public holidays festive season");

            // Add annotation
            annotationSet_.addAnnotation("dateOperators", 7, string("D1R8"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 8, annotation = "string(\"D1R9\")")
    public com.gs.dmn.runtime.RuleOutput rule8(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(8, "string(\"D1R9\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateOperatorsRuleOutput output_ = new DateOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((date == null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateOperators("date defined");

            // Add annotation
            annotationSet_.addAnnotation("dateOperators", 8, string("D1R9"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 9, annotation = "string(\"D1R10\")")
    public com.gs.dmn.runtime.RuleOutput rule9(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(9, "string(\"D1R10\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateOperatorsRuleOutput output_ = new DateOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (date == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateOperators("date not defined");

            // Add annotation
            annotationSet_.addAnnotation("dateOperators", 9, string("D1R10"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
