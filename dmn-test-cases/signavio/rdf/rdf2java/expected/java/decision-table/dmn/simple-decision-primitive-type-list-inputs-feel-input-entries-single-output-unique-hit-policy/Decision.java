
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
            return apply((input_.get("BooleanInput") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("BooleanInput"), new com.fasterxml.jackson.core.type.TypeReference<List<Boolean>>() {}) : null), (input_.get("DateAndTimeInput") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("DateAndTimeInput"), new com.fasterxml.jackson.core.type.TypeReference<List<java.time.temporal.TemporalAccessor>>() {}) : null), (input_.get("DateInput") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("DateInput"), new com.fasterxml.jackson.core.type.TypeReference<List<java.time.LocalDate>>() {}) : null), (input_.get("EnumerationInput") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("EnumerationInput"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (input_.get("NumberInput") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("NumberInput"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), (input_.get("TextInput") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("TextInput"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (input_.get("TimeInput") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("TimeInput"), new com.fasterxml.jackson.core.type.TypeReference<List<java.time.temporal.TemporalAccessor>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision'", e);
            return null;
        }
    }

    public String apply(List<Boolean> booleanInput, List<java.time.temporal.TemporalAccessor> dateAndTimeInput, List<java.time.LocalDate> dateInput, List<String> enumerationInput, List<java.lang.Number> numberInput, List<String> textInput, List<java.time.temporal.TemporalAccessor> timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
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

    protected String evaluate(List<Boolean> booleanInput, List<java.time.temporal.TemporalAccessor> dateAndTimeInput, List<java.time.LocalDate> dateInput, List<String> enumerationInput, List<java.lang.Number> numberInput, List<String> textInput, List<java.time.temporal.TemporalAccessor> timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
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
            output_ = ruleOutput_ == null ? null : ((DecisionRuleOutput)ruleOutput_).getOutput();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(List<Boolean> booleanInput, List<java.time.temporal.TemporalAccessor> dateAndTimeInput, List<java.time.LocalDate> dateInput, List<String> enumerationInput, List<java.lang.Number> numberInput, List<String> textInput, List<java.time.temporal.TemporalAccessor> timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
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
            listEqual(dateInput, asList(date("2016-08-01"))),
            listEqual(timeInput, asList(time("12:00:00Z"))),
            listEqual(dateAndTimeInput, asList(dateAndTime("2016-08-01T11:00:00Z"))),
            listEqual(numberInput, asList(numericUnaryMinus(number("1")))),
            listEqual(textInput, asList("abc")),
            listEqual(booleanInput, asList(Boolean.TRUE)),
            listEqual(enumerationInput, asList("e1"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutput("r1");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(List<Boolean> booleanInput, List<java.time.temporal.TemporalAccessor> dateAndTimeInput, List<java.time.LocalDate> dateInput, List<String> enumerationInput, List<java.lang.Number> numberInput, List<String> textInput, List<java.time.temporal.TemporalAccessor> timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
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
            booleanNot(listEqual(dateInput, asList(date("2016-08-01")))),
            booleanNot(listEqual(timeInput, asList(time("12:00:00Z")))),
            booleanNot(listEqual(dateAndTimeInput, asList(dateAndTime("2016-08-01T11:00:00Z")))),
            booleanNot(listEqual(numberInput, asList(numericUnaryMinus(number("1"))))),
            booleanNot(listEqual(textInput, asList("abc"))),
            booleanNot(listEqual(booleanInput, asList(Boolean.FALSE))),
            booleanNot(listEqual(enumerationInput, asList("e1")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutput("r2");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(List<Boolean> booleanInput, List<java.time.temporal.TemporalAccessor> dateAndTimeInput, List<java.time.LocalDate> dateInput, List<String> enumerationInput, List<java.lang.Number> numberInput, List<String> textInput, List<java.time.temporal.TemporalAccessor> timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
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
            elementOf(dateInput, asList(date("2016-08-01"), date("2016-08-02"))),
            elementOf(timeInput, asList(time("12:00:00Z"), time("13:00:00Z"))),
            elementOf(dateAndTimeInput, asList(dateAndTime("2016-08-01T11:00:00Z"))),
            elementOf(numberInput, asList(numericUnaryMinus(number("1")))),
            elementOf(textInput, asList("b")),
            elementOf(booleanInput, asList(Boolean.TRUE)),
            elementOf(enumerationInput, asList("e1", "e2"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutput("r3");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(List<Boolean> booleanInput, List<java.time.temporal.TemporalAccessor> dateAndTimeInput, List<java.time.LocalDate> dateInput, List<String> enumerationInput, List<java.lang.Number> numberInput, List<String> textInput, List<java.time.temporal.TemporalAccessor> timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
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
            booleanNot(notContainsAny(dateInput, asList(date("2016-08-01"), date("2016-08-02")))),
            booleanNot(notContainsAny(timeInput, asList(time("12:00:00Z"), time("13:00:00Z")))),
            booleanNot(notContainsAny(dateAndTimeInput, asList(dateAndTime("2016-08-01T11:00:00Z")))),
            booleanNot(notContainsAny(numberInput, asList(numericUnaryMinus(number("1"))))),
            booleanNot(notContainsAny(textInput, asList("b"))),
            booleanNot(notContainsAny(booleanInput, asList(Boolean.FALSE))),
            booleanNot(notContainsAny(enumerationInput, asList("e1", "e2")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutput("r4");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule4(List<Boolean> booleanInput, List<java.time.temporal.TemporalAccessor> dateAndTimeInput, List<java.time.LocalDate> dateInput, List<String> enumerationInput, List<java.lang.Number> numberInput, List<String> textInput, List<java.time.temporal.TemporalAccessor> timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
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
            containsOnly(dateInput, asList(date("2016-08-01"), date("2016-08-02"))),
            containsOnly(timeInput, asList(time("12:00:00Z"), time("13:00:00Z"))),
            containsOnly(dateAndTimeInput, asList(dateAndTime("2016-08-01T11:00:00Z"))),
            containsOnly(numberInput, asList(numericUnaryMinus(number("1")))),
            containsOnly(textInput, asList("ab")),
            containsOnly(booleanInput, asList(Boolean.FALSE)),
            containsOnly(enumerationInput, asList("e1"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutput("r5");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule5(List<Boolean> booleanInput, List<java.time.temporal.TemporalAccessor> dateAndTimeInput, List<java.time.LocalDate> dateInput, List<String> enumerationInput, List<java.lang.Number> numberInput, List<String> textInput, List<java.time.temporal.TemporalAccessor> timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
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
            notContainsAny(dateInput, asList(date("2016-08-01"), date("2016-08-02"))),
            notContainsAny(timeInput, asList(time("12:00:00Z"), time("13:00:00Z"))),
            notContainsAny(dateAndTimeInput, asList(dateAndTime("2016-08-01T11:00:00Z"))),
            Boolean.TRUE,
            notContainsAny(textInput, asList("bc")),
            notContainsAny(booleanInput, asList(Boolean.FALSE)),
            notContainsAny(enumerationInput, asList("e2"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutput("r6");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule6(List<Boolean> booleanInput, List<java.time.temporal.TemporalAccessor> dateAndTimeInput, List<java.time.LocalDate> dateInput, List<String> enumerationInput, List<java.lang.Number> numberInput, List<String> textInput, List<java.time.temporal.TemporalAccessor> timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
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
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutput("r7");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule7(List<Boolean> booleanInput, List<java.time.temporal.TemporalAccessor> dateAndTimeInput, List<java.time.LocalDate> dateInput, List<String> enumerationInput, List<java.lang.Number> numberInput, List<String> textInput, List<java.time.temporal.TemporalAccessor> timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
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
            Boolean.TRUE,
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
            output_.setOutput("r8");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 8, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule8(List<Boolean> booleanInput, List<java.time.temporal.TemporalAccessor> dateAndTimeInput, List<java.time.LocalDate> dateInput, List<String> enumerationInput, List<java.lang.Number> numberInput, List<String> textInput, List<java.time.temporal.TemporalAccessor> timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
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
            Boolean.TRUE,
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
            output_.setOutput("r9");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 9, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule9(List<Boolean> booleanInput, List<java.time.temporal.TemporalAccessor> dateAndTimeInput, List<java.time.LocalDate> dateInput, List<String> enumerationInput, List<java.lang.Number> numberInput, List<String> textInput, List<java.time.temporal.TemporalAccessor> timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
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
            Boolean.TRUE,
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
            output_.setOutput("r10");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 10, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule10(List<Boolean> booleanInput, List<java.time.temporal.TemporalAccessor> dateAndTimeInput, List<java.time.LocalDate> dateInput, List<String> enumerationInput, List<java.lang.Number> numberInput, List<String> textInput, List<java.time.temporal.TemporalAccessor> timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
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
            Boolean.TRUE,
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
            output_.setOutput(null);
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
