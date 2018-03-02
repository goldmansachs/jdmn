
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "dateCompare2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "dateCompare2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 2
)
public class DateCompare2 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "dateCompare2",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        2
    );

    public DateCompare2() {
    }

    public Boolean apply(String dateD, String dateE, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((dateD != null ? date(dateD) : null), (dateE != null ? date(dateE) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'DateCompare2'", e);
            return null;
        }
    }

    public Boolean apply(String dateD, String dateE, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((dateD != null ? date(dateD) : null), (dateE != null ? date(dateE) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DateCompare2'", e);
            return null;
        }
    }

    public Boolean apply(javax.xml.datatype.XMLGregorianCalendar dateD, javax.xml.datatype.XMLGregorianCalendar dateE, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(dateD, dateE, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public Boolean apply(javax.xml.datatype.XMLGregorianCalendar dateD, javax.xml.datatype.XMLGregorianCalendar dateE, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'dateCompare2'
            long dateCompare2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dateCompare2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dateCompare2Arguments_.put("dateD", dateD);
            dateCompare2Arguments_.put("dateE", dateE);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateCompare2Arguments_);

            // Evaluate decision 'dateCompare2'
            Boolean output_ = evaluate(dateD, dateE, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'dateCompare2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dateCompare2Arguments_, output_, (System.currentTimeMillis() - dateCompare2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'dateCompare2' evaluation", e);
            return null;
        }
    }

    private Boolean evaluate(javax.xml.datatype.XMLGregorianCalendar dateD, javax.xml.datatype.XMLGregorianCalendar dateE, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(dateD, dateE, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(dateD, dateE, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        Boolean output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((DateCompare2RuleOutput)ruleOutput_).getDateCompare2();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(javax.xml.datatype.XMLGregorianCalendar dateD, javax.xml.datatype.XMLGregorianCalendar dateE, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateCompare2RuleOutput output_ = new DateCompare2RuleOutput(false);
        if (Boolean.TRUE == (dateGreaterThan(dateD, dateE))) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateCompare2(Boolean.TRUE);

            // Add annotation
            annotationSet_.addAnnotation("dateCompare2", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(javax.xml.datatype.XMLGregorianCalendar dateD, javax.xml.datatype.XMLGregorianCalendar dateE, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateCompare2RuleOutput output_ = new DateCompare2RuleOutput(false);
        if (Boolean.TRUE == (dateLessEqualThan(dateD, dateE))) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateCompare2(Boolean.FALSE);

            // Add annotation
            annotationSet_.addAnnotation("dateCompare2", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
