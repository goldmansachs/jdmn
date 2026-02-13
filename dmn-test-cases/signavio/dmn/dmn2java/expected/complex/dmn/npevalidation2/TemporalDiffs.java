
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "temporalDiffs"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/5417bfd1893048bc9ca18c51aa11b7f0.xml",
    name = "temporalDiffs",
    label = "temporalDiffs",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 4
)
public class TemporalDiffs extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<List<type.TemporalDiffs>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "http://www.provider.com/dmn/1.1/diagram/5417bfd1893048bc9ca18c51aa11b7f0.xml",
        "temporalDiffs",
        "temporalDiffs",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        4
    );

    private final GenerateTemporalObjects generateTemporalObjects;

    public TemporalDiffs() {
        this(new GenerateTemporalObjects());
    }

    public TemporalDiffs(GenerateTemporalObjects generateTemporalObjects) {
        this.generateTemporalObjects = generateTemporalObjects;
    }

    @java.lang.Override()
    public List<type.TemporalDiffs> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("day") != null ? number(input_.get("day")) : null), (input_.get("hour") != null ? number(input_.get("hour")) : null), (input_.get("minute") != null ? number(input_.get("minute")) : null), (input_.get("month") != null ? number(input_.get("month")) : null), (input_.get("second") != null ? number(input_.get("second")) : null), (input_.get("year") != null ? number(input_.get("year")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'TemporalDiffs'", e);
            return null;
        }
    }

    public List<type.TemporalDiffs> apply(java.lang.Number day, java.lang.Number hour, java.lang.Number minute, java.lang.Number month, java.lang.Number second, java.lang.Number year, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'temporalDiffs'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long temporalDiffsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments temporalDiffsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            temporalDiffsArguments_.put("day", day);
            temporalDiffsArguments_.put("hour", hour);
            temporalDiffsArguments_.put("minute", minute);
            temporalDiffsArguments_.put("month", month);
            temporalDiffsArguments_.put("second", second);
            temporalDiffsArguments_.put("year", year);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, temporalDiffsArguments_);

            // Evaluate decision 'temporalDiffs'
            List<type.TemporalDiffs> output_ = evaluate(day, hour, minute, month, second, year, context_);

            // End decision 'temporalDiffs'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, temporalDiffsArguments_, output_, (System.currentTimeMillis() - temporalDiffsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'temporalDiffs' evaluation", e);
            return null;
        }
    }

    protected List<type.TemporalDiffs> evaluate(java.lang.Number day, java.lang.Number hour, java.lang.Number minute, java.lang.Number month, java.lang.Number second, java.lang.Number year, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        type.GenerateTemporalObjects generateTemporalObjects = this.generateTemporalObjects.apply(day, hour, minute, month, second, year, context_);

        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(generateTemporalObjects, context_));
        ruleOutputList_.add(rule1(generateTemporalObjects, context_));
        ruleOutputList_.add(rule2(generateTemporalObjects, context_));
        ruleOutputList_.add(rule3(generateTemporalObjects, context_));

        // Return results based on hit policy
        List<type.TemporalDiffs> output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
            if (output_ == null) {
                output_ = asList();
            }
        } else {
            List<? extends com.gs.dmn.runtime.RuleOutput> ruleOutputs_ = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT);
            output_ = ruleOutputs_.stream().map(ro_ -> toDecisionOutput(((TemporalDiffsRuleOutput)ro_))).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(type.GenerateTemporalObjects generateTemporalObjects, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TemporalDiffsRuleOutput output_ = new TemporalDiffsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateDiff(dayDiff(((java.time.LocalDate)(generateTemporalObjects != null ? generateTemporalObjects.getDate() : null)), ((java.time.LocalDate)(generateTemporalObjects != null ? generateTemporalObjects.getDate() : null))));
            output_.setDateTimeDiff(minutesDiff(((java.time.temporal.TemporalAccessor)(generateTemporalObjects != null ? generateTemporalObjects.getDatetime() : null)), ((java.time.temporal.TemporalAccessor)(generateTemporalObjects != null ? generateTemporalObjects.getDatetime() : null))));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(type.GenerateTemporalObjects generateTemporalObjects, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TemporalDiffsRuleOutput output_ = new TemporalDiffsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateDiff(monthDiff(((java.time.LocalDate)(generateTemporalObjects != null ? generateTemporalObjects.getDate() : null)), ((java.time.LocalDate)(generateTemporalObjects != null ? generateTemporalObjects.getDate() : null))));
            output_.setDateTimeDiff(hourDiff(((java.time.temporal.TemporalAccessor)(generateTemporalObjects != null ? generateTemporalObjects.getDatetime() : null)), ((java.time.temporal.TemporalAccessor)(generateTemporalObjects != null ? generateTemporalObjects.getDatetime() : null))));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(type.GenerateTemporalObjects generateTemporalObjects, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TemporalDiffsRuleOutput output_ = new TemporalDiffsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateDiff(yearDiff(((java.time.LocalDate)(generateTemporalObjects != null ? generateTemporalObjects.getDate() : null)), ((java.time.LocalDate)(generateTemporalObjects != null ? generateTemporalObjects.getDate() : null))));
            output_.setDateTimeDiff(minutesDiff(((java.time.temporal.TemporalAccessor)(generateTemporalObjects != null ? generateTemporalObjects.getDatetime() : null)), ((java.time.temporal.TemporalAccessor)(generateTemporalObjects != null ? generateTemporalObjects.getDatetime() : null))));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(type.GenerateTemporalObjects generateTemporalObjects, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TemporalDiffsRuleOutput output_ = new TemporalDiffsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateDiff(dayDiff(((java.time.LocalDate)(generateTemporalObjects != null ? generateTemporalObjects.getDate() : null)), date(number("2016"), number("1"), number("1"))));
            output_.setDateTimeDiff(hourDiff(((java.time.temporal.TemporalAccessor)(generateTemporalObjects != null ? generateTemporalObjects.getDatetime() : null)), dateTime(number("1"), number("1"), number("2016"), number("11"), number("11"), number("11"))));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    public type.TemporalDiffs toDecisionOutput(TemporalDiffsRuleOutput ruleOutput_) {
        type.TemporalDiffsImpl result_ = new type.TemporalDiffsImpl();
        result_.setDateDiff(ruleOutput_ == null ? null : ruleOutput_.getDateDiff());
        result_.setDateTimeDiff(ruleOutput_ == null ? null : ruleOutput_.getDateTimeDiff());
        return result_;
    }
}
