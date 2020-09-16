
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "finalDecision"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "finalDecision",
    label = "final decision",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.ANY,
    rulesCount = 4
)
public class FinalDecision extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "finalDecision",
        "final decision",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.ANY,
        4
    );

    private final DecisionDate decisionDate;
    private final DecisionTime decisionTime;

    public FinalDecision() {
        this(new DecisionDate(), new DecisionTime());
    }

    public FinalDecision(DecisionDate decisionDate, DecisionTime decisionTime) {
        this.decisionDate = decisionDate;
        this.decisionTime = decisionTime;
    }

    public String apply(String dateInput, String timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((dateInput != null ? date(dateInput) : null), (timeInput != null ? time(timeInput) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'FinalDecision'", e);
            return null;
        }
    }

    public String apply(String dateInput, String timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((dateInput != null ? date(dateInput) : null), (timeInput != null ? time(timeInput) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FinalDecision'", e);
            return null;
        }
    }

    public String apply(javax.xml.datatype.XMLGregorianCalendar dateInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(dateInput, timeInput, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(javax.xml.datatype.XMLGregorianCalendar dateInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'finalDecision'
            long finalDecisionStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments finalDecisionArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            finalDecisionArguments_.put("date input", dateInput);
            finalDecisionArguments_.put("time input", timeInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, finalDecisionArguments_);

            // Apply child decisions
            List<String> decisionDate = this.decisionDate.apply(dateInput, annotationSet_, eventListener_, externalExecutor_, cache_);
            List<String> decisionTime = this.decisionTime.apply(timeInput, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'finalDecision'
            String output_ = evaluate(decisionDate, decisionTime, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'finalDecision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, finalDecisionArguments_, output_, (System.currentTimeMillis() - finalDecisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'finalDecision' evaluation", e);
            return null;
        }
    }

    protected String evaluate(List<String> decisionDate, List<String> decisionTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(decisionDate, decisionTime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(decisionDate, decisionTime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(decisionDate, decisionTime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(decisionDate, decisionTime, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.ANY);
            output_ = ruleOutput_ == null ? null : ((FinalDecisionRuleOutput)ruleOutput_).getFinalDecision();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(List<String> decisionDate, List<String> decisionTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        FinalDecisionRuleOutput output_ = new FinalDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            (booleanEqual(numericGreaterEqualThan(count(decisionDate), number("3")), Boolean.TRUE)),
            (booleanEqual(numericGreaterEqualThan(count(decisionTime), number("3")), Boolean.TRUE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFinalDecision("good");

            // Add annotation
            annotationSet_.addAnnotation("finalDecision", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(List<String> decisionDate, List<String> decisionTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        FinalDecisionRuleOutput output_ = new FinalDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            (booleanEqual(numericGreaterEqualThan(count(decisionDate), number("3")), Boolean.FALSE)),
            (booleanEqual(numericGreaterEqualThan(count(decisionTime), number("3")), Boolean.TRUE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFinalDecision("mmm");

            // Add annotation
            annotationSet_.addAnnotation("finalDecision", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(List<String> decisionDate, List<String> decisionTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        FinalDecisionRuleOutput output_ = new FinalDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            (booleanEqual(numericGreaterEqualThan(count(decisionDate), number("3")), Boolean.TRUE)),
            (booleanEqual(numericGreaterEqualThan(count(decisionTime), number("3")), Boolean.FALSE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFinalDecision("erhm");

            // Add annotation
            annotationSet_.addAnnotation("finalDecision", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule3(List<String> decisionDate, List<String> decisionTime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        FinalDecisionRuleOutput output_ = new FinalDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            (booleanEqual(numericGreaterEqualThan(count(decisionDate), number("3")), Boolean.FALSE)),
            (booleanEqual(numericGreaterEqualThan(count(decisionTime), number("3")), Boolean.FALSE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFinalDecision("bad");

            // Add annotation
            annotationSet_.addAnnotation("finalDecision", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
