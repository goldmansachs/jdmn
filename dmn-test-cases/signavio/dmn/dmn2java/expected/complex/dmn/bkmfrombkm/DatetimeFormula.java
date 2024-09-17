
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "datetimeFormula"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "datetimeFormula",
    label = "datetime formula",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 2
)
public class DatetimeFormula extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "datetimeFormula",
        "datetime formula",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        2
    );

    public DatetimeFormula() {
    }

    @java.lang.Override()
    public List<java.lang.Number> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("datetime") != null ? dateAndTime(input_.get("datetime")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DatetimeFormula'", e);
            return null;
        }
    }

    public List<java.lang.Number> apply(java.time.temporal.TemporalAccessor datetime, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'datetimeFormula'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long datetimeFormulaStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments datetimeFormulaArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            datetimeFormulaArguments_.put("datetime", datetime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, datetimeFormulaArguments_);

            // Evaluate decision 'datetimeFormula'
            List<java.lang.Number> output_ = evaluate(datetime, context_);

            // End decision 'datetimeFormula'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, datetimeFormulaArguments_, output_, (System.currentTimeMillis() - datetimeFormulaStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'datetimeFormula' evaluation", e);
            return null;
        }
    }

    protected List<java.lang.Number> evaluate(java.time.temporal.TemporalAccessor datetime, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(datetime, context_));
        ruleOutputList_.add(rule1(datetime, context_));

        // Return results based on hit policy
        List<java.lang.Number> output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
            if (output_ == null) {
                output_ = asList();
            }
        } else {
            List<? extends com.gs.dmn.runtime.RuleOutput> ruleOutputs_ = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT);
            output_ = ruleOutputs_.stream().map(o -> ((DatetimeFormulaRuleOutput)o).getDatetimeFormula()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"D6R1\")")
    public com.gs.dmn.runtime.RuleOutput rule0(java.time.temporal.TemporalAccessor datetime, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"D6R1\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DatetimeFormulaRuleOutput output_ = new DatetimeFormulaRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDatetimeFormula(hourDiff(datetime, dateTime(modulo(year(datetime), number("31")), numericDivide(hour(datetime), number("12")), numericAdd(number("1990"), power(day(datetime), numericMultiply(number("1"), power(number("1"), numericSubtract(number("12"), hour(datetime)))))), modulo(minute(datetime), number("24")), numericAdd(number("1"), numericMultiply(numericDivide(number("58"), number("11")), numericSubtract(month(datetime), number("1")))), number("0"))));

            // Add annotations
            annotationSet_.addAnnotation("datetimeFormula", 0, string("D6R1"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"D6R2\")")
    public com.gs.dmn.runtime.RuleOutput rule1(java.time.temporal.TemporalAccessor datetime, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "string(\"D6R2\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DatetimeFormulaRuleOutput output_ = new DatetimeFormulaRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDatetimeFormula(minutesDiff(datetime, dateTime(modulo(year(datetime), number("31")), numericDivide(hour(datetime), number("12")), numericAdd(number("1990"), power(day(datetime), numericMultiply(number("1"), power(number("1"), numericSubtract(number("12"), hour(datetime)))))), modulo(minute(datetime), number("24")), numericAdd(number("1"), numericMultiply(numericDivide(number("58"), number("11")), numericSubtract(month(datetime), number("1")))), number("0"))));

            // Add annotations
            annotationSet_.addAnnotation("datetimeFormula", 1, string("D6R2"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
