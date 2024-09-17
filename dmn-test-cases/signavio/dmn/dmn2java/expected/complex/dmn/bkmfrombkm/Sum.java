
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "sum"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "sum",
    label = "sum",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.ANY,
    rulesCount = 5
)
public class Sum extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "sum",
        "sum",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.ANY,
        5
    );

    private final DateFormula dateFormula;
    private final DatetimeFormula datetimeFormula;
    private final TimeFormula timeFormula;

    public Sum() {
        this(new DateFormula(), new DatetimeFormula(), new TimeFormula());
    }

    public Sum(DateFormula dateFormula, DatetimeFormula datetimeFormula, TimeFormula timeFormula) {
        this.dateFormula = dateFormula;
        this.datetimeFormula = datetimeFormula;
        this.timeFormula = timeFormula;
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("date") != null ? date(input_.get("date")) : null), (input_.get("datetime") != null ? dateAndTime(input_.get("datetime")) : null), (input_.get("time") != null ? time(input_.get("time")) : null), (input_.get("time 2") != null ? time(input_.get("time 2")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Sum'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.time.LocalDate date, java.time.temporal.TemporalAccessor datetime, java.time.temporal.TemporalAccessor time, java.time.temporal.TemporalAccessor time2, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'sum'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long sumStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments sumArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            sumArguments_.put("date", date);
            sumArguments_.put("datetime", datetime);
            sumArguments_.put("time", time);
            sumArguments_.put("time 2", time2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, sumArguments_);

            // Evaluate decision 'sum'
            java.lang.Number output_ = evaluate(date, datetime, time, time2, context_);

            // End decision 'sum'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, sumArguments_, output_, (System.currentTimeMillis() - sumStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'sum' evaluation", e);
            return null;
        }
    }

    protected java.lang.Number evaluate(java.time.LocalDate date, java.time.temporal.TemporalAccessor datetime, java.time.temporal.TemporalAccessor time, java.time.temporal.TemporalAccessor time2, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        List<java.lang.Number> dateFormula = this.dateFormula.apply(date, context_);
        List<java.lang.Number> datetimeFormula = this.datetimeFormula.apply(datetime, context_);
        List<java.lang.Number> timeFormula = this.timeFormula.apply(time, time2, context_);

        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(dateFormula, datetimeFormula, timeFormula, context_));
        ruleOutputList_.add(rule1(dateFormula, datetimeFormula, timeFormula, context_));
        ruleOutputList_.add(rule2(dateFormula, datetimeFormula, timeFormula, context_));
        ruleOutputList_.add(rule3(dateFormula, datetimeFormula, timeFormula, context_));
        ruleOutputList_.add(rule4(dateFormula, datetimeFormula, timeFormula, context_));

        // Return results based on hit policy
        java.lang.Number output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.ANY);
            output_ = ruleOutput_ == null ? null : ((SumRuleOutput)ruleOutput_).getSum();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"D8R1\")")
    public com.gs.dmn.runtime.RuleOutput rule0(List<java.lang.Number> dateFormula, List<java.lang.Number> datetimeFormula, List<java.lang.Number> timeFormula, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"D8R1\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        SumRuleOutput output_ = new SumRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanEqual(numericLessEqualThan(sum(dateFormula), sum(datetimeFormula)), Boolean.FALSE),
            booleanEqual(numericGreaterEqualThan(sum(datetimeFormula), sum(timeFormula)), Boolean.TRUE),
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setSum(sum(dateFormula));

            // Add annotations
            annotationSet_.addAnnotation("sum", 0, string("D8R1"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"D8R2\")")
    public com.gs.dmn.runtime.RuleOutput rule1(List<java.lang.Number> dateFormula, List<java.lang.Number> datetimeFormula, List<java.lang.Number> timeFormula, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "string(\"D8R2\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        SumRuleOutput output_ = new SumRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanEqual(numericLessEqualThan(sum(dateFormula), sum(datetimeFormula)), Boolean.TRUE),
            booleanEqual(numericGreaterEqualThan(sum(datetimeFormula), sum(timeFormula)), Boolean.FALSE),
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setSum(sum(timeFormula));

            // Add annotations
            annotationSet_.addAnnotation("sum", 1, string("D8R2"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "string(\"D8R3\")")
    public com.gs.dmn.runtime.RuleOutput rule2(List<java.lang.Number> dateFormula, List<java.lang.Number> datetimeFormula, List<java.lang.Number> timeFormula, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "string(\"D8R3\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        SumRuleOutput output_ = new SumRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanEqual(numericLessEqualThan(sum(dateFormula), sum(datetimeFormula)), Boolean.TRUE),
            booleanEqual(numericGreaterEqualThan(sum(datetimeFormula), sum(timeFormula)), Boolean.TRUE),
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setSum(sum(datetimeFormula));

            // Add annotations
            annotationSet_.addAnnotation("sum", 2, string("D8R3"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "string(\"D8R4\")")
    public com.gs.dmn.runtime.RuleOutput rule3(List<java.lang.Number> dateFormula, List<java.lang.Number> datetimeFormula, List<java.lang.Number> timeFormula, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "string(\"D8R4\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        SumRuleOutput output_ = new SumRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanEqual(numericLessEqualThan(sum(dateFormula), sum(datetimeFormula)), Boolean.FALSE),
            booleanEqual(numericGreaterEqualThan(sum(datetimeFormula), sum(timeFormula)), Boolean.FALSE),
            booleanEqual(numericGreaterEqualThan(sum(dateFormula), sum(timeFormula)), Boolean.TRUE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setSum(sum(dateFormula));

            // Add annotations
            annotationSet_.addAnnotation("sum", 3, string("D8R4"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "string(\"D8R5\")")
    public com.gs.dmn.runtime.RuleOutput rule4(List<java.lang.Number> dateFormula, List<java.lang.Number> datetimeFormula, List<java.lang.Number> timeFormula, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "string(\"D8R5\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        SumRuleOutput output_ = new SumRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanEqual(numericLessEqualThan(sum(dateFormula), sum(datetimeFormula)), Boolean.FALSE),
            booleanEqual(numericGreaterEqualThan(sum(datetimeFormula), sum(timeFormula)), Boolean.FALSE),
            booleanEqual(numericGreaterEqualThan(sum(dateFormula), sum(timeFormula)), Boolean.FALSE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setSum(sum(timeFormula));

            // Add annotations
            annotationSet_.addAnnotation("sum", 4, string("D8R5"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
