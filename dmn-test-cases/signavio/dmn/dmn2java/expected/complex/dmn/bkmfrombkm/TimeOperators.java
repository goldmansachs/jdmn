
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "timeOperators"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/af75837563be485d941eba0f9bf7a5f4.xml",
    name = "timeOperators",
    label = "time operators",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 10
)
public class TimeOperators extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<List<String>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "timeOperators",
        "time operators",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        10
    );

    public TimeOperators() {
    }

    @java.lang.Override()
    public List<String> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("time") != null ? time(input_.get("time")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'TimeOperators'", e);
            return null;
        }
    }

    public List<String> apply(java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'timeOperators'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long timeOperatorsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments timeOperatorsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            timeOperatorsArguments_.put("time", time);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, timeOperatorsArguments_);

            // Evaluate decision 'timeOperators'
            List<String> output_ = evaluate(time, context_);

            // End decision 'timeOperators'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, timeOperatorsArguments_, output_, (System.currentTimeMillis() - timeOperatorsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'timeOperators' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(time, context_));
        ruleOutputList_.add(rule1(time, context_));
        ruleOutputList_.add(rule2(time, context_));
        ruleOutputList_.add(rule3(time, context_));
        ruleOutputList_.add(rule4(time, context_));
        ruleOutputList_.add(rule5(time, context_));
        ruleOutputList_.add(rule6(time, context_));
        ruleOutputList_.add(rule7(time, context_));
        ruleOutputList_.add(rule8(time, context_));
        ruleOutputList_.add(rule9(time, context_));

        // Return results based on hit policy
        List<String> output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
            if (output_ == null) {
                output_ = asList();
            }
        } else {
            List<? extends com.gs.dmn.runtime.RuleOutput> ruleOutputs_ = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT);
            output_ = ruleOutputs_.stream().map(ro_ -> ((TimeOperatorsRuleOutput)ro_).getTimeOperators()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"D2R1\")")
    public com.gs.dmn.runtime.RuleOutput rule0(java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"D2R1\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            timeEqual(time, time("19:00:00-0500"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time midnight");

            // Add annotations
            annotationSet_.addAnnotation("timeOperators", 0, string("D2R1"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"D2R2\")")
    public com.gs.dmn.runtime.RuleOutput rule1(java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "string(\"D2R2\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(timeEqual(time, time("19:00:00-0500")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time not midnight");

            // Add annotations
            annotationSet_.addAnnotation("timeOperators", 1, string("D2R2"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "string(\"D2R3\")")
    public com.gs.dmn.runtime.RuleOutput rule2(java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "string(\"D2R3\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            timeLessThan(time, time("07:00:00-0500"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time before noon");

            // Add annotations
            annotationSet_.addAnnotation("timeOperators", 2, string("D2R3"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "string(\"D2R4\")")
    public com.gs.dmn.runtime.RuleOutput rule3(java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "string(\"D2R4\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            timeLessEqualThan(time, time("07:00:00-0500"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time noon and before");

            // Add annotations
            annotationSet_.addAnnotation("timeOperators", 3, string("D2R4"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "string(\"D2R5\")")
    public com.gs.dmn.runtime.RuleOutput rule4(java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "string(\"D2R5\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            timeGreaterThan(time, time("07:00:00-0500"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time afternoon");

            // Add annotations
            annotationSet_.addAnnotation("timeOperators", 4, string("D2R5"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "string(\"D2R6\")")
    public com.gs.dmn.runtime.RuleOutput rule5(java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "string(\"D2R6\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            timeGreaterEqualThan(time, time("07:00:00-0500"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time noon and after");

            // Add annotations
            annotationSet_.addAnnotation("timeOperators", 5, string("D2R6"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "string(\"D2R7\")")
    public com.gs.dmn.runtime.RuleOutput rule6(java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(6, "string(\"D2R7\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanOr(timeEqual(time, time("01:00:00-0500")), timeEqual(time, time("13:00:00-0500")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time sixes");

            // Add annotations
            annotationSet_.addAnnotation("timeOperators", 6, string("D2R7"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "string(\"D2R8\")")
    public com.gs.dmn.runtime.RuleOutput rule7(java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(7, "string(\"D2R8\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(booleanOr(timeEqual(time, time("01:00:00-0500")), timeEqual(time, time("13:00:00-0500"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time not sixes");

            // Add annotations
            annotationSet_.addAnnotation("timeOperators", 7, string("D2R8"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 8, annotation = "string(\"D2R9\")")
    public com.gs.dmn.runtime.RuleOutput rule8(java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(8, "string(\"D2R9\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(timeEqual(time, null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time defined");

            // Add annotations
            annotationSet_.addAnnotation("timeOperators", 8, string("D2R9"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 9, annotation = "string(\"D2R10\")")
    public com.gs.dmn.runtime.RuleOutput rule9(java.time.temporal.TemporalAccessor time, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(9, "string(\"D2R10\")");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TimeOperatorsRuleOutput output_ = new TimeOperatorsRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            timeEqual(time, null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTimeOperators("time not defined");

            // Add annotations
            annotationSet_.addAnnotation("timeOperators", 9, string("D2R10"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
