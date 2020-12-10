
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "decide"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decide",
    label = "decide",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.ANY,
    rulesCount = 1
)
public class Decide extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decide",
        "decide",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.ANY,
        1
    );

    private final Pick pick;
    private final Sum sum;

    public Decide() {
        this(new Pick(), new Sum());
    }

    public Decide(Pick pick, Sum sum) {
        this.pick = pick;
        this.sum = sum;
    }

    public String apply(String date, String datetime, String time, String time2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((date != null ? date(date) : null), (datetime != null ? dateAndTime(datetime) : null), (time != null ? time(time) : null), (time2 != null ? time(time2) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Decide'", e);
            return null;
        }
    }

    public String apply(String date, String datetime, String time, String time2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((date != null ? date(date) : null), (datetime != null ? dateAndTime(datetime) : null), (time != null ? time(time) : null), (time2 != null ? time(time2) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decide'", e);
            return null;
        }
    }

    public String apply(javax.xml.datatype.XMLGregorianCalendar date, javax.xml.datatype.XMLGregorianCalendar datetime, javax.xml.datatype.XMLGregorianCalendar time, javax.xml.datatype.XMLGregorianCalendar time2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(date, datetime, time, time2, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(javax.xml.datatype.XMLGregorianCalendar date, javax.xml.datatype.XMLGregorianCalendar datetime, javax.xml.datatype.XMLGregorianCalendar time, javax.xml.datatype.XMLGregorianCalendar time2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decide'
            long decideStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decideArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decideArguments_.put("date", date);
            decideArguments_.put("datetime", datetime);
            decideArguments_.put("time", time);
            decideArguments_.put("time 2", time2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decideArguments_);

            // Apply child decisions
            String pick = this.pick.apply(date, datetime, time, annotationSet_, eventListener_, externalExecutor_, cache_);
            java.math.BigDecimal sum = this.sum.apply(date, datetime, time, time2, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'decide'
            String output_ = evaluate(pick, sum, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decide'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decideArguments_, output_, (System.currentTimeMillis() - decideStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decide' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String pick, java.math.BigDecimal sum, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(pick, sum, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.ANY);
            output_ = ruleOutput_ == null ? null : ((DecideRuleOutput)ruleOutput_).getDecide();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"D9R1\")")
    public com.gs.dmn.runtime.RuleOutput rule0(String pick, java.math.BigDecimal sum, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"D9R1\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecideRuleOutput output_ = new DecideRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecide(concat(asList(pick, " ", text(sum, "0"))));

            // Add annotation
            annotationSet_.addAnnotation("decide", 0, string("D9R1"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
