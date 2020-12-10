
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "timeOperators"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "timeOperators",
    label = "time operators",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 10
)
public class TimeOperators extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "timeOperators",
        "time operators",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        10
    );

    public TimeOperators() {
    }

    public List<String> apply(String time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((time != null ? time(time) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'TimeOperators'", e);
            return null;
        }
    }

    public List<String> apply(String time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((time != null ? time(time) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'TimeOperators'", e);
            return null;
        }
    }

    public List<String> apply(javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(time, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'timeOperators'
            long timeOperatorsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments timeOperatorsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            timeOperatorsArguments_.put("time", time);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, timeOperatorsArguments_);

            // Evaluate decision 'timeOperators'
            List<String> output_ = evaluate(time, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'timeOperators'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, timeOperatorsArguments_, output_, (System.currentTimeMillis() - timeOperatorsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'timeOperators' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(time, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(time, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(time, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(time, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(time, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule5(time, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule6(time, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule7(time, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule8(time, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule9(time, annotationSet_, eventListener_, externalExecutor_));

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
            output_ = ruleOutputs_.stream().map(o -> ((TimeOperatorsRuleOutput)o).getTimeOperators()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"D2R1\")")
    public com.gs.dmn.runtime.RuleOutput rule0(javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"D2R1\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (timeEqual(time, time("19:00:00-0500")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time midnight");

            // Add annotation
            annotationSet_.addAnnotation("timeOperators", 0, string("D2R1"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"D2R2\")")
    public com.gs.dmn.runtime.RuleOutput rule1(javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "string(\"D2R2\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((timeEqual(time, time("19:00:00-0500"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time not midnight");

            // Add annotation
            annotationSet_.addAnnotation("timeOperators", 1, string("D2R2"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "string(\"D2R3\")")
    public com.gs.dmn.runtime.RuleOutput rule2(javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "string(\"D2R3\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (timeLessThan(time, time("07:00:00-0500")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time before noon");

            // Add annotation
            annotationSet_.addAnnotation("timeOperators", 2, string("D2R3"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "string(\"D2R4\")")
    public com.gs.dmn.runtime.RuleOutput rule3(javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "string(\"D2R4\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (timeLessEqualThan(time, time("07:00:00-0500")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time noon and before");

            // Add annotation
            annotationSet_.addAnnotation("timeOperators", 3, string("D2R4"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "string(\"D2R5\")")
    public com.gs.dmn.runtime.RuleOutput rule4(javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "string(\"D2R5\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (timeGreaterThan(time, time("07:00:00-0500")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time afternoon");

            // Add annotation
            annotationSet_.addAnnotation("timeOperators", 4, string("D2R5"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "string(\"D2R6\")")
    public com.gs.dmn.runtime.RuleOutput rule5(javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "string(\"D2R6\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (timeGreaterEqualThan(time, time("07:00:00-0500")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time noon and after");

            // Add annotation
            annotationSet_.addAnnotation("timeOperators", 5, string("D2R6"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "string(\"D2R7\")")
    public com.gs.dmn.runtime.RuleOutput rule6(javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(6, "string(\"D2R7\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanOr((timeEqual(time, time("01:00:00-0500"))), (timeEqual(time, time("13:00:00-0500"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time sixes");

            // Add annotation
            annotationSet_.addAnnotation("timeOperators", 6, string("D2R7"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "string(\"D2R8\")")
    public com.gs.dmn.runtime.RuleOutput rule7(javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(7, "string(\"D2R8\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(booleanOr((timeEqual(time, time("01:00:00-0500"))), (timeEqual(time, time("13:00:00-0500")))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time not sixes");

            // Add annotation
            annotationSet_.addAnnotation("timeOperators", 7, string("D2R8"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 8, annotation = "string(\"D2R9\")")
    public com.gs.dmn.runtime.RuleOutput rule8(javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(8, "string(\"D2R9\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((time == null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time defined");

            // Add annotation
            annotationSet_.addAnnotation("timeOperators", 8, string("D2R9"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 9, annotation = "string(\"D2R10\")")
    public com.gs.dmn.runtime.RuleOutput rule9(javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(9, "string(\"D2R10\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (time == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time not defined");

            // Add annotation
            annotationSet_.addAnnotation("timeOperators", 9, string("D2R10"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
