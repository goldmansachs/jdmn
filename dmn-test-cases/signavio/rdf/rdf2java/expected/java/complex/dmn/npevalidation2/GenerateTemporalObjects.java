
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "generateTemporalObjects"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.omg.org/spec/DMN/20151101/dmn.xsd",
    name = "generateTemporalObjects",
    label = "generateTemporalObjects",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 1
)
public class GenerateTemporalObjects extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<type.GenerateTemporalObjects> {
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

    @java.lang.Override()
    public type.GenerateTemporalObjects applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("day") != null ? number(input_.get("day")) : null), (input_.get("hour") != null ? number(input_.get("hour")) : null), (input_.get("minute") != null ? number(input_.get("minute")) : null), (input_.get("month") != null ? number(input_.get("month")) : null), (input_.get("second") != null ? number(input_.get("second")) : null), (input_.get("year") != null ? number(input_.get("year")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'GenerateTemporalObjects'", e);
            return null;
        }
    }

    public type.GenerateTemporalObjects apply(java.lang.Number day, java.lang.Number hour, java.lang.Number minute, java.lang.Number month, java.lang.Number second, java.lang.Number year, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'generateTemporalObjects'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
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
            type.GenerateTemporalObjects output_ = evaluate(day, hour, minute, month, second, year, context_);

            // End decision 'generateTemporalObjects'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, generateTemporalObjectsArguments_, output_, (System.currentTimeMillis() - generateTemporalObjectsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'generateTemporalObjects' evaluation", e);
            return null;
        }
    }

    protected type.GenerateTemporalObjects evaluate(java.lang.Number day, java.lang.Number hour, java.lang.Number minute, java.lang.Number month, java.lang.Number second, java.lang.Number year, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(day, hour, minute, month, second, year, context_));

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
    public com.gs.dmn.runtime.RuleOutput rule0(java.lang.Number day, java.lang.Number hour, java.lang.Number minute, java.lang.Number month, java.lang.Number second, java.lang.Number year, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
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
