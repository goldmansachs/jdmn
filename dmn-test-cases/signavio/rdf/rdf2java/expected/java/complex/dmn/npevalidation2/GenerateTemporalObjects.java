
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "generateTemporalObjects"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "generateTemporalObjects",
    label = "generateTemporalObjects",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 1
)
public class GenerateTemporalObjects extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "generateTemporalObjects",
        "generateTemporalObjects",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        1
    );

    public GenerateTemporalObjects() {
    }

    public type.GenerateTemporalObjects apply(String day, String hour, String minute, String month, String second, String year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((day != null ? number(day) : null), (hour != null ? number(hour) : null), (minute != null ? number(minute) : null), (month != null ? number(month) : null), (second != null ? number(second) : null), (year != null ? number(year) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'GenerateTemporalObjects'", e);
            return null;
        }
    }

    public type.GenerateTemporalObjects apply(String day, String hour, String minute, String month, String second, String year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((day != null ? number(day) : null), (hour != null ? number(hour) : null), (minute != null ? number(minute) : null), (month != null ? number(month) : null), (second != null ? number(second) : null), (year != null ? number(year) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'GenerateTemporalObjects'", e);
            return null;
        }
    }

    public type.GenerateTemporalObjects apply(java.math.BigDecimal day, java.math.BigDecimal hour, java.math.BigDecimal minute, java.math.BigDecimal month, java.math.BigDecimal second, java.math.BigDecimal year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(day, hour, minute, month, second, year, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public type.GenerateTemporalObjects apply(java.math.BigDecimal day, java.math.BigDecimal hour, java.math.BigDecimal minute, java.math.BigDecimal month, java.math.BigDecimal second, java.math.BigDecimal year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'generateTemporalObjects'
            long generateTemporalObjectsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments generateTemporalObjectsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            generateTemporalObjectsArguments_.put("day", day);
            generateTemporalObjectsArguments_.put("hour", hour);
            generateTemporalObjectsArguments_.put("minute", minute);
            generateTemporalObjectsArguments_.put("month", month);
            generateTemporalObjectsArguments_.put("second", second);
            generateTemporalObjectsArguments_.put("year", year);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, generateTemporalObjectsArguments_);

            // Evaluate decision 'generateTemporalObjects'
            type.GenerateTemporalObjects output_ = evaluate(day, hour, minute, month, second, year, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'generateTemporalObjects'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, generateTemporalObjectsArguments_, output_, (System.currentTimeMillis() - generateTemporalObjectsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'generateTemporalObjects' evaluation", e);
            return null;
        }
    }

    protected type.GenerateTemporalObjects evaluate(java.math.BigDecimal day, java.math.BigDecimal hour, java.math.BigDecimal minute, java.math.BigDecimal month, java.math.BigDecimal second, java.math.BigDecimal year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(day, hour, minute, month, second, year, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        type.GenerateTemporalObjects output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = toDecisionOutput((GenerateTemporalObjectsRuleOutput)ruleOutput_);
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal day, java.math.BigDecimal hour, java.math.BigDecimal minute, java.math.BigDecimal month, java.math.BigDecimal second, java.math.BigDecimal year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        GenerateTemporalObjectsRuleOutput output_ = new GenerateTemporalObjectsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDate(date(year, month, day));
            output_.setDatetime(dateTime(day, month, year, hour, minute, second));

            // Add annotation
            annotationSet_.addAnnotation("generateTemporalObjects", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    public type.GenerateTemporalObjects toDecisionOutput(GenerateTemporalObjectsRuleOutput ruleOutput_) {
        type.GenerateTemporalObjectsImpl result_ = new type.GenerateTemporalObjectsImpl();
        result_.setDate(ruleOutput_ == null ? null : ruleOutput_.getDate());
        result_.setDatetime(ruleOutput_ == null ? null : ruleOutput_.getDatetime());
        return result_;
    }
}
