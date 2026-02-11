
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "pick"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/af75837563be485d941eba0f9bf7a5f4.xml",
    name = "pick",
    label = "pick",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.ANY,
    rulesCount = 3
)
public class Pick extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "pick",
        "pick",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.ANY,
        3
    );

    private final DateOperators dateOperators;
    private final DatetimeOperators datetimeOperators;
    private final TimeOperators timeOperators;

    public Pick() {
        this(new DateOperators(), new DatetimeOperators(), new TimeOperators());
    }

    public Pick(DateOperators dateOperators, DatetimeOperators datetimeOperators, TimeOperators timeOperators) {
        this.dateOperators = dateOperators;
        this.datetimeOperators = datetimeOperators;
        this.timeOperators = timeOperators;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("date") != null ? date(input_.get("date")) : null), (input_.get("datetime") != null ? dateAndTime(input_.get("datetime")) : null), (input_.get("time") != null ? time(input_.get("time")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Pick'", e);
            return null;
        }
    }

    public String apply(java.time.LocalDate date, java.time.temporal.TemporalAccessor datetime, java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'pick'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long pickStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments pickArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            pickArguments_.put("date", date);
            pickArguments_.put("datetime", datetime);
            pickArguments_.put("time", time);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, pickArguments_);

            // Evaluate decision 'pick'
            String output_ = evaluate(date, datetime, time, context_);

            // End decision 'pick'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, pickArguments_, output_, (System.currentTimeMillis() - pickStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'pick' evaluation", e);
            return null;
        }
    }

    protected String evaluate(java.time.LocalDate date, java.time.temporal.TemporalAccessor datetime, java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        List<String> dateOperators = this.dateOperators.apply(date, context_);
        List<String> datetimeOperators = this.datetimeOperators.apply(datetime, context_);
        List<String> timeOperators = this.timeOperators.apply(time, context_);

        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(dateOperators, datetimeOperators, timeOperators, context_));
        ruleOutputList_.add(rule1(dateOperators, datetimeOperators, timeOperators, context_));
        ruleOutputList_.add(rule2(dateOperators, datetimeOperators, timeOperators, context_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.ANY);
            output_ = ruleOutput_ == null ? null : ((PickRuleOutput)ruleOutput_).getPick();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"D7R1\")")
    public com.gs.dmn.runtime.RuleOutput rule0(List<String> dateOperators, List<String> datetimeOperators, List<String> timeOperators, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"D7R1\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PickRuleOutput output_ = new PickRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanEqual(numericLessEqualThan(count(dateOperators), count(datetimeOperators)), Boolean.FALSE),
            booleanEqual(numericGreaterEqualThan(count(datetimeOperators), count(timeOperators)), Boolean.TRUE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPick(concat(asList("date: ", text(count(dateOperators), "0"))));

            // Add annotations
            annotationSet_.addAnnotation("pick", 0, string("D7R1"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"D7R2\")")
    public com.gs.dmn.runtime.RuleOutput rule1(List<String> dateOperators, List<String> datetimeOperators, List<String> timeOperators, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "string(\"D7R2\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PickRuleOutput output_ = new PickRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanEqual(numericLessEqualThan(count(dateOperators), count(datetimeOperators)), Boolean.TRUE),
            booleanEqual(numericGreaterEqualThan(count(datetimeOperators), count(timeOperators)), Boolean.FALSE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPick(concat(asList("time: ", text(count(timeOperators), "0"))));

            // Add annotations
            annotationSet_.addAnnotation("pick", 1, string("D7R2"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "string(\"D7R3\")")
    public com.gs.dmn.runtime.RuleOutput rule2(List<String> dateOperators, List<String> datetimeOperators, List<String> timeOperators, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "string(\"D7R3\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PickRuleOutput output_ = new PickRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanEqual(numericLessEqualThan(count(dateOperators), count(datetimeOperators)), Boolean.TRUE),
            booleanEqual(numericGreaterEqualThan(count(datetimeOperators), count(timeOperators)), Boolean.TRUE)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPick(concat(asList("datetime: ", text(count(datetimeOperators), "0"))));

            // Add annotations
            annotationSet_.addAnnotation("pick", 2, string("D7R3"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
