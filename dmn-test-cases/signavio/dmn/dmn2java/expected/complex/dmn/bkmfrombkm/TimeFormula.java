
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "timeFormula"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "timeFormula",
    label = "time formula",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 4
)
public class TimeFormula extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "timeFormula",
        "time formula",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        4
    );

    public TimeFormula() {
    }

    public List<java.math.BigDecimal> apply(String time, String time2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((time != null ? time(time) : null), (time2 != null ? time(time2) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'TimeFormula'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(String time, String time2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((time != null ? time(time) : null), (time2 != null ? time(time2) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'TimeFormula'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(javax.xml.datatype.XMLGregorianCalendar time, javax.xml.datatype.XMLGregorianCalendar time2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(time, time2, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<java.math.BigDecimal> apply(javax.xml.datatype.XMLGregorianCalendar time, javax.xml.datatype.XMLGregorianCalendar time2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'timeFormula'
            long timeFormulaStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments timeFormulaArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            timeFormulaArguments_.put("time", time);
            timeFormulaArguments_.put("time 2", time2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, timeFormulaArguments_);

            // Evaluate decision 'timeFormula'
            List<java.math.BigDecimal> output_ = evaluate(time, time2, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'timeFormula'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, timeFormulaArguments_, output_, (System.currentTimeMillis() - timeFormulaStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'timeFormula' evaluation", e);
            return null;
        }
    }

    protected List<java.math.BigDecimal> evaluate(javax.xml.datatype.XMLGregorianCalendar time, javax.xml.datatype.XMLGregorianCalendar time2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(time, time2, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(time, time2, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(time, time2, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(time, time2, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        List<java.math.BigDecimal> output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
            if (output_ == null) {
                output_ = asList();
            }
        } else {
            List<? extends com.gs.dmn.runtime.RuleOutput> ruleOutputs_ = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT);
            output_ = ruleOutputs_.stream().map(o -> ((TimeFormulaRuleOutput)o).getTimeFormula()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"D5R1\")")
    public com.gs.dmn.runtime.RuleOutput rule0(javax.xml.datatype.XMLGregorianCalendar time, javax.xml.datatype.XMLGregorianCalendar time2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"D5R1\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeFormulaRuleOutput output_ = new TimeFormulaRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeFormula(hour(time));

            // Add annotation
            annotationSet_.addAnnotation("timeFormula", 0, string("D5R1"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"D5R2\")")
    public com.gs.dmn.runtime.RuleOutput rule1(javax.xml.datatype.XMLGregorianCalendar time, javax.xml.datatype.XMLGregorianCalendar time2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "string(\"D5R2\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeFormulaRuleOutput output_ = new TimeFormulaRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeFormula(hourDiff(time, time2));

            // Add annotation
            annotationSet_.addAnnotation("timeFormula", 1, string("D5R2"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "string(\"D5R3\")")
    public com.gs.dmn.runtime.RuleOutput rule2(javax.xml.datatype.XMLGregorianCalendar time, javax.xml.datatype.XMLGregorianCalendar time2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "string(\"D5R3\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeFormulaRuleOutput output_ = new TimeFormulaRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeFormula(minute(time));

            // Add annotation
            annotationSet_.addAnnotation("timeFormula", 2, string("D5R3"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "string(\"D5R4\")")
    public com.gs.dmn.runtime.RuleOutput rule3(javax.xml.datatype.XMLGregorianCalendar time, javax.xml.datatype.XMLGregorianCalendar time2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "string(\"D5R4\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeFormulaRuleOutput output_ = new TimeFormulaRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeFormula(minutesDiff(time, time2));

            // Add annotation
            annotationSet_.addAnnotation("timeFormula", 3, string("D5R4"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
