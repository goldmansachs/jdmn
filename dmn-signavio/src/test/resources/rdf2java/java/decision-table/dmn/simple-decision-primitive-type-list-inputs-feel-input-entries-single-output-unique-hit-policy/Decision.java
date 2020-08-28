
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
public class Decision extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
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

    public String apply(String booleanInput, String dateAndTimeInput, String dateInput, String enumerationInput, String numberInput, String textInput, String timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((booleanInput != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(booleanInput, new com.fasterxml.jackson.core.type.TypeReference<List<Boolean>>() {}) : null), (dateAndTimeInput != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(dateAndTimeInput, new com.fasterxml.jackson.core.type.TypeReference<List<javax.xml.datatype.XMLGregorianCalendar>>() {}) : null), (dateInput != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(dateInput, new com.fasterxml.jackson.core.type.TypeReference<List<javax.xml.datatype.XMLGregorianCalendar>>() {}) : null), (enumerationInput != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(enumerationInput, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (numberInput != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(numberInput, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (textInput != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(textInput, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (timeInput != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(timeInput, new com.fasterxml.jackson.core.type.TypeReference<List<javax.xml.datatype.XMLGregorianCalendar>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision'", e);
            return null;
        }
    }

    public String apply(String booleanInput, String dateAndTimeInput, String dateInput, String enumerationInput, String numberInput, String textInput, String timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((booleanInput != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(booleanInput, new com.fasterxml.jackson.core.type.TypeReference<List<Boolean>>() {}) : null), (dateAndTimeInput != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(dateAndTimeInput, new com.fasterxml.jackson.core.type.TypeReference<List<javax.xml.datatype.XMLGregorianCalendar>>() {}) : null), (dateInput != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(dateInput, new com.fasterxml.jackson.core.type.TypeReference<List<javax.xml.datatype.XMLGregorianCalendar>>() {}) : null), (enumerationInput != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(enumerationInput, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (numberInput != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(numberInput, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (textInput != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(textInput, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (timeInput != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(timeInput, new com.fasterxml.jackson.core.type.TypeReference<List<javax.xml.datatype.XMLGregorianCalendar>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision'", e);
            return null;
        }
    }

    public String apply(List<Boolean> booleanInput, List<javax.xml.datatype.XMLGregorianCalendar> dateAndTimeInput, List<javax.xml.datatype.XMLGregorianCalendar> dateInput, List<String> enumerationInput, List<java.math.BigDecimal> numberInput, List<String> textInput, List<javax.xml.datatype.XMLGregorianCalendar> timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(List<Boolean> booleanInput, List<javax.xml.datatype.XMLGregorianCalendar> dateAndTimeInput, List<javax.xml.datatype.XMLGregorianCalendar> dateInput, List<String> enumerationInput, List<java.math.BigDecimal> numberInput, List<String> textInput, List<javax.xml.datatype.XMLGregorianCalendar> timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision'
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
            String output_ = evaluate(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionArguments_, output_, (System.currentTimeMillis() - decisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision' evaluation", e);
            return null;
        }
    }

    protected String evaluate(List<Boolean> booleanInput, List<javax.xml.datatype.XMLGregorianCalendar> dateAndTimeInput, List<javax.xml.datatype.XMLGregorianCalendar> dateInput, List<String> enumerationInput, List<java.math.BigDecimal> numberInput, List<String> textInput, List<javax.xml.datatype.XMLGregorianCalendar> timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule5(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule6(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule7(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule8(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule9(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule10(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_));

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
    public com.gs.dmn.runtime.RuleOutput rule0(List<Boolean> booleanInput, List<javax.xml.datatype.XMLGregorianCalendar> dateAndTimeInput, List<javax.xml.datatype.XMLGregorianCalendar> dateInput, List<String> enumerationInput, List<java.math.BigDecimal> numberInput, List<String> textInput, List<javax.xml.datatype.XMLGregorianCalendar> timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (listEqual(dateInput, asList(date("2016-08-01")))),
            (listEqual(timeInput, asList(time("12:00:00Z")))),
            (listEqual(dateAndTimeInput, asList(dateAndTime("2016-08-01T11:00:00Z")))),
            (listEqual(numberInput, asList(numericUnaryMinus(number("1"))))),
            (listEqual(textInput, asList("abc"))),
            (listEqual(booleanInput, asList(Boolean.TRUE))),
            (listEqual(enumerationInput, asList("e1")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutput("r1");

            // Add annotation
            annotationSet_.addAnnotation("decision", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(List<Boolean> booleanInput, List<javax.xml.datatype.XMLGregorianCalendar> dateAndTimeInput, List<javax.xml.datatype.XMLGregorianCalendar> dateInput, List<String> enumerationInput, List<java.math.BigDecimal> numberInput, List<String> textInput, List<javax.xml.datatype.XMLGregorianCalendar> timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((listEqual(dateInput, asList(date("2016-08-01"))))),
            booleanNot((listEqual(timeInput, asList(time("12:00:00Z"))))),
            booleanNot((listEqual(dateAndTimeInput, asList(dateAndTime("2016-08-01T11:00:00Z"))))),
            booleanNot((listEqual(numberInput, asList(numericUnaryMinus(number("1")))))),
            booleanNot((listEqual(textInput, asList("abc")))),
            booleanNot((listEqual(booleanInput, asList(Boolean.FALSE)))),
            booleanNot((listEqual(enumerationInput, asList("e1"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutput("r2");

            // Add annotation
            annotationSet_.addAnnotation("decision", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(List<Boolean> booleanInput, List<javax.xml.datatype.XMLGregorianCalendar> dateAndTimeInput, List<javax.xml.datatype.XMLGregorianCalendar> dateInput, List<String> enumerationInput, List<java.math.BigDecimal> numberInput, List<String> textInput, List<javax.xml.datatype.XMLGregorianCalendar> timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (elementOf(dateInput, asList(date("2016-08-01"), date("2016-08-02")))),
            (elementOf(timeInput, asList(time("12:00:00Z"), time("13:00:00Z")))),
            (elementOf(dateAndTimeInput, asList(dateAndTime("2016-08-01T11:00:00Z")))),
            (elementOf(numberInput, asList(numericUnaryMinus(number("1"))))),
            (elementOf(textInput, asList("b"))),
            (elementOf(booleanInput, asList(Boolean.TRUE))),
            (elementOf(enumerationInput, asList("e1", "e2")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutput("r3");

            // Add annotation
            annotationSet_.addAnnotation("decision", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(List<Boolean> booleanInput, List<javax.xml.datatype.XMLGregorianCalendar> dateAndTimeInput, List<javax.xml.datatype.XMLGregorianCalendar> dateInput, List<String> enumerationInput, List<java.math.BigDecimal> numberInput, List<String> textInput, List<javax.xml.datatype.XMLGregorianCalendar> timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((notContainsAny(dateInput, asList(date("2016-08-01"), date("2016-08-02"))))),
            booleanNot((notContainsAny(timeInput, asList(time("12:00:00Z"), time("13:00:00Z"))))),
            booleanNot((notContainsAny(dateAndTimeInput, asList(dateAndTime("2016-08-01T11:00:00Z"))))),
            booleanNot((notContainsAny(numberInput, asList(numericUnaryMinus(number("1")))))),
            booleanNot((notContainsAny(textInput, asList("b")))),
            booleanNot((notContainsAny(booleanInput, asList(Boolean.FALSE)))),
            booleanNot((notContainsAny(enumerationInput, asList("e1", "e2"))))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutput("r4");

            // Add annotation
            annotationSet_.addAnnotation("decision", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule4(List<Boolean> booleanInput, List<javax.xml.datatype.XMLGregorianCalendar> dateAndTimeInput, List<javax.xml.datatype.XMLGregorianCalendar> dateInput, List<String> enumerationInput, List<java.math.BigDecimal> numberInput, List<String> textInput, List<javax.xml.datatype.XMLGregorianCalendar> timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (containsOnly(dateInput, asList(date("2016-08-01"), date("2016-08-02")))),
            (containsOnly(timeInput, asList(time("12:00:00Z"), time("13:00:00Z")))),
            (containsOnly(dateAndTimeInput, asList(dateAndTime("2016-08-01T11:00:00Z")))),
            (containsOnly(numberInput, asList(numericUnaryMinus(number("1"))))),
            (containsOnly(textInput, asList("ab"))),
            (containsOnly(booleanInput, asList(Boolean.FALSE))),
            (containsOnly(enumerationInput, asList("e1")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutput("r5");

            // Add annotation
            annotationSet_.addAnnotation("decision", 4, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule5(List<Boolean> booleanInput, List<javax.xml.datatype.XMLGregorianCalendar> dateAndTimeInput, List<javax.xml.datatype.XMLGregorianCalendar> dateInput, List<String> enumerationInput, List<java.math.BigDecimal> numberInput, List<String> textInput, List<javax.xml.datatype.XMLGregorianCalendar> timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (notContainsAny(dateInput, asList(date("2016-08-01"), date("2016-08-02")))),
            (notContainsAny(timeInput, asList(time("12:00:00Z"), time("13:00:00Z")))),
            (notContainsAny(dateAndTimeInput, asList(dateAndTime("2016-08-01T11:00:00Z")))),
            Boolean.TRUE,
            (notContainsAny(textInput, asList("bc"))),
            (notContainsAny(booleanInput, asList(Boolean.FALSE))),
            (notContainsAny(enumerationInput, asList("e2")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setOutput("r6");

            // Add annotation
            annotationSet_.addAnnotation("decision", 5, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 6, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule6(List<Boolean> booleanInput, List<javax.xml.datatype.XMLGregorianCalendar> dateAndTimeInput, List<javax.xml.datatype.XMLGregorianCalendar> dateInput, List<String> enumerationInput, List<java.math.BigDecimal> numberInput, List<String> textInput, List<javax.xml.datatype.XMLGregorianCalendar> timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(6, "");

        // Rule start
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

            // Add annotation
            annotationSet_.addAnnotation("decision", 6, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 7, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule7(List<Boolean> booleanInput, List<javax.xml.datatype.XMLGregorianCalendar> dateAndTimeInput, List<javax.xml.datatype.XMLGregorianCalendar> dateInput, List<String> enumerationInput, List<java.math.BigDecimal> numberInput, List<String> textInput, List<javax.xml.datatype.XMLGregorianCalendar> timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(7, "");

        // Rule start
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

            // Add annotation
            annotationSet_.addAnnotation("decision", 7, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 8, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule8(List<Boolean> booleanInput, List<javax.xml.datatype.XMLGregorianCalendar> dateAndTimeInput, List<javax.xml.datatype.XMLGregorianCalendar> dateInput, List<String> enumerationInput, List<java.math.BigDecimal> numberInput, List<String> textInput, List<javax.xml.datatype.XMLGregorianCalendar> timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(8, "");

        // Rule start
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

            // Add annotation
            annotationSet_.addAnnotation("decision", 8, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 9, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule9(List<Boolean> booleanInput, List<javax.xml.datatype.XMLGregorianCalendar> dateAndTimeInput, List<javax.xml.datatype.XMLGregorianCalendar> dateInput, List<String> enumerationInput, List<java.math.BigDecimal> numberInput, List<String> textInput, List<javax.xml.datatype.XMLGregorianCalendar> timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(9, "");

        // Rule start
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

            // Add annotation
            annotationSet_.addAnnotation("decision", 9, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 10, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule10(List<Boolean> booleanInput, List<javax.xml.datatype.XMLGregorianCalendar> dateAndTimeInput, List<javax.xml.datatype.XMLGregorianCalendar> dateInput, List<String> enumerationInput, List<java.math.BigDecimal> numberInput, List<String> textInput, List<javax.xml.datatype.XMLGregorianCalendar> timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(10, "");

        // Rule start
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

            // Add annotation
            annotationSet_.addAnnotation("decision", 10, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
