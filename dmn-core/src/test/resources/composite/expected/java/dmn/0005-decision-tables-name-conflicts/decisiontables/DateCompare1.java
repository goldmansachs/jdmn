package decisiontables;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "dateCompare1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "decisiontables",
    name = "dateCompare1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 2
)
public class DateCompare1 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "decisiontables",
        "dateCompare1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        2
    );

    public DateCompare1() {
    }

    public Boolean apply(String decisioninputs1_dateD, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((decisioninputs1_dateD != null ? date(decisioninputs1_dateD) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DateCompare1'", e);
            return null;
        }
    }

    public Boolean apply(String decisioninputs1_dateD, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((decisioninputs1_dateD != null ? date(decisioninputs1_dateD) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DateCompare1'", e);
            return null;
        }
    }

    public Boolean apply(javax.xml.datatype.XMLGregorianCalendar decisioninputs1_dateD, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(decisioninputs1_dateD, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Boolean apply(javax.xml.datatype.XMLGregorianCalendar decisioninputs1_dateD, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'dateCompare1'
            long dateCompare1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dateCompare1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dateCompare1Arguments_.put("decisionInputs1.dateD", decisioninputs1_dateD);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateCompare1Arguments_);

            // Evaluate decision 'dateCompare1'
            Boolean output_ = evaluate(decisioninputs1_dateD, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'dateCompare1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dateCompare1Arguments_, output_, (System.currentTimeMillis() - dateCompare1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'dateCompare1' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(javax.xml.datatype.XMLGregorianCalendar decisioninputs1_dateD, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(decisioninputs1_dateD, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(decisioninputs1_dateD, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        Boolean output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((DateCompare1RuleOutput)ruleOutput_).getDateCompare1();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(javax.xml.datatype.XMLGregorianCalendar decisioninputs1_dateD, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateCompare1RuleOutput output_ = new DateCompare1RuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateGreaterThan(decisioninputs1_dateD, date("2016-10-01")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateCompare1(Boolean.TRUE);

            // Add annotation
            annotationSet_.addAnnotation("dateCompare1", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(javax.xml.datatype.XMLGregorianCalendar decisioninputs1_dateD, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateCompare1RuleOutput output_ = new DateCompare1RuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateLessEqualThan(decisioninputs1_dateD, date("2016-10-01")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateCompare1(Boolean.FALSE);

            // Add annotation
            annotationSet_.addAnnotation("dateCompare1", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
