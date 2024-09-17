
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "decision"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision",
    label = "Decision",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 11
)
public class Decision extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision",
        "Decision",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        11
    );

    public Decision() {
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("BooleanInput") != null ? Boolean.valueOf(input_.get("BooleanInput")) : null), (input_.get("DateAndTimeInput") != null ? dateAndTime(input_.get("DateAndTimeInput")) : null), (input_.get("DateInput") != null ? date(input_.get("DateInput")) : null), input_.get("EnumerationInput"), (input_.get("NumberInput") != null ? number(input_.get("NumberInput")) : null), input_.get("TextInput"), (input_.get("TimeInput") != null ? time(input_.get("TimeInput")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision'", e);
            return null;
        }
    }

    public String apply(Boolean booleanInput, java.time.temporal.TemporalAccessor dateAndTimeInput, java.time.LocalDate dateInput, String enumerationInput, java.lang.Number numberInput, String textInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decision'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decisionStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionArguments_.put("BooleanInput", booleanInput);
            decisionArguments_.put("DateAndTimeInput", dateAndTimeInput);
            decisionArguments_.put("DateInput", dateInput);
            decisionArguments_.put("EnumerationInput", enumerationInput);
            decisionArguments_.put("NumberInput", numberInput);
            decisionArguments_.put("TextInput", textInput);
            decisionArguments_.put("TimeInput", timeInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionArguments_);

            // Evaluate decision 'decision'
            String output_ = evaluate(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_);

            // End decision 'decision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionArguments_, output_, (System.currentTimeMillis() - decisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision' evaluation", e);
            return null;
        }
    }

    protected String evaluate(Boolean booleanInput, java.time.temporal.TemporalAccessor dateAndTimeInput, java.time.LocalDate dateInput, String enumerationInput, java.lang.Number numberInput, String textInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_));
        ruleOutputList_.add(rule1(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_));
        ruleOutputList_.add(rule2(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_));
        ruleOutputList_.add(rule3(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_));
        ruleOutputList_.add(rule4(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_));
        ruleOutputList_.add(rule5(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_));
        ruleOutputList_.add(rule6(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_));
        ruleOutputList_.add(rule7(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_));
        ruleOutputList_.add(rule8(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_));
        ruleOutputList_.add(rule9(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_));
        ruleOutputList_.add(rule10(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((DecisionRuleOutput)ruleOutput_).getDecision();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(Boolean booleanInput, java.time.temporal.TemporalAccessor dateAndTimeInput, java.time.LocalDate dateInput, String enumerationInput, java.lang.Number numberInput, String textInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            dateEqual(dateInput, date("2016-08-01")),
            timeEqual(timeInput, time("12:00:00+0000")),
            dateTimeEqual(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00+0000")),
            numericEqual(numberInput, numericUnaryMinus(number("1"))),
            stringEqual(textInput, "abc"),
            booleanEqual(booleanInput, Boolean.TRUE),
            stringEqual(enumerationInput, "e1")
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("r1");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(Boolean booleanInput, java.time.temporal.TemporalAccessor dateAndTimeInput, java.time.LocalDate dateInput, String enumerationInput, java.lang.Number numberInput, String textInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(dateEqual(dateInput, date("2016-08-01"))),
            booleanNot(timeEqual(timeInput, time("12:00:00+0000"))),
            booleanNot(dateTimeEqual(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00+0000"))),
            booleanNot(numericEqual(numberInput, numericUnaryMinus(number("1")))),
            booleanNot(stringEqual(textInput, "abc")),
            booleanNot(booleanEqual(booleanInput, Boolean.FALSE)),
            booleanNot(stringEqual(enumerationInput, "e1"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("r2");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(Boolean booleanInput, java.time.temporal.TemporalAccessor dateAndTimeInput, java.time.LocalDate dateInput, String enumerationInput, java.lang.Number numberInput, String textInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            dateLessThan(dateInput, date("2016-08-01")),
            timeLessThan(timeInput, time("12:00:00+0000")),
            dateTimeLessThan(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00+0000")),
            numericLessThan(numberInput, numericUnaryMinus(number("1"))),
            contains(textInput, "b"),
            Boolean.TRUE,
            booleanOr(stringEqual(enumerationInput, "e1"), stringEqual(enumerationInput, "e2"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("r3");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(Boolean booleanInput, java.time.temporal.TemporalAccessor dateAndTimeInput, java.time.LocalDate dateInput, String enumerationInput, java.lang.Number numberInput, String textInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            dateLessEqualThan(dateInput, date("2016-08-01")),
            timeLessEqualThan(timeInput, time("12:00:00+0000")),
            dateTimeLessEqualThan(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00+0000")),
            numericGreaterEqualThan(numberInput, numericUnaryMinus(number("1"))),
            booleanNot(contains(textInput, "b")),
            Boolean.TRUE,
            booleanNot(booleanOr(stringEqual(enumerationInput, "e1"), stringEqual(enumerationInput, "e2")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("r4");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule4(Boolean booleanInput, java.time.temporal.TemporalAccessor dateAndTimeInput, java.time.LocalDate dateInput, String enumerationInput, java.lang.Number numberInput, String textInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            dateGreaterThan(dateInput, date("2016-08-01")),
            timeGreaterThan(timeInput, time("12:00:00+0000")),
            dateTimeGreaterThan(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00+0000")),
            numericGreaterThan(numberInput, numericUnaryMinus(number("1"))),
            startsWith(textInput, "ab"),
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("r5");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule5(Boolean booleanInput, java.time.temporal.TemporalAccessor dateAndTimeInput, java.time.LocalDate dateInput, String enumerationInput, java.lang.Number numberInput, String textInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            dateGreaterEqualThan(dateInput, date("2016-08-01")),
            timeGreaterEqualThan(timeInput, time("12:00:00+0000")),
            dateTimeGreaterEqualThan(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00+0000")),
            booleanAnd(numericGreaterEqualThan(numberInput, number("1")), numericLessThan(numberInput, number("2"))),
            endsWith(textInput, "bc"),
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("r6");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule6(Boolean booleanInput, java.time.temporal.TemporalAccessor dateAndTimeInput, java.time.LocalDate dateInput, String enumerationInput, java.lang.Number numberInput, String textInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(6, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            booleanNot(booleanAnd(numericGreaterEqualThan(numberInput, number("1")), numericLessThan(numberInput, number("2")))),
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("r7");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule7(Boolean booleanInput, java.time.temporal.TemporalAccessor dateAndTimeInput, java.time.LocalDate dateInput, String enumerationInput, java.lang.Number numberInput, String textInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(7, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(dateInput == null),
            booleanNot(timeInput == null),
            booleanNot(dateAndTimeInput == null),
            booleanNot(numberInput == null),
            booleanNot(textInput == null),
            booleanNot(booleanInput == null),
            booleanNot(enumerationInput == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("r8");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 8, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule8(Boolean booleanInput, java.time.temporal.TemporalAccessor dateAndTimeInput, java.time.LocalDate dateInput, String enumerationInput, java.lang.Number numberInput, String textInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(8, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            dateInput == null,
            timeInput == null,
            dateAndTimeInput == null,
            numberInput == null,
            textInput == null,
            booleanInput == null,
            enumerationInput == null
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("r9");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 9, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule9(Boolean booleanInput, java.time.temporal.TemporalAccessor dateAndTimeInput, java.time.LocalDate dateInput, String enumerationInput, java.lang.Number numberInput, String textInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(9, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(dateInput == null),
            booleanNot(timeInput == null),
            booleanNot(dateAndTimeInput == null),
            booleanNot(numberInput == null),
            booleanNot(textInput == null),
            booleanNot(booleanInput == null),
            booleanNot(enumerationInput == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("r10");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 10, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule10(Boolean booleanInput, java.time.temporal.TemporalAccessor dateAndTimeInput, java.time.LocalDate dateInput, String enumerationInput, java.lang.Number numberInput, String textInput, java.time.temporal.TemporalAccessor timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(10, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            dateInput == null,
            timeInput == null,
            dateAndTimeInput == null,
            numberInput == null,
            textInput == null,
            booleanInput == null,
            enumerationInput == null
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("r11");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
