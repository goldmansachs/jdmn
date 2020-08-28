
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
    rulesCount = 6
)
public class Decision extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision",
        "Decision",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        6
    );

    public Decision() {
    }

    public String apply(String booleanInput, String dateInput, String enumerationInput, String numberInput, String person, String stringInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((booleanInput != null ? Boolean.valueOf(booleanInput) : null), (dateInput != null ? date(dateInput) : null), enumerationInput, (numberInput != null ? number(numberInput) : null), (person != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(person, new com.fasterxml.jackson.core.type.TypeReference<type.PersonImpl>() {}) : null), stringInput, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision'", e);
            return null;
        }
    }

    public String apply(String booleanInput, String dateInput, String enumerationInput, String numberInput, String person, String stringInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((booleanInput != null ? Boolean.valueOf(booleanInput) : null), (dateInput != null ? date(dateInput) : null), enumerationInput, (numberInput != null ? number(numberInput) : null), (person != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(person, new com.fasterxml.jackson.core.type.TypeReference<type.PersonImpl>() {}) : null), stringInput, annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision'", e);
            return null;
        }
    }

    public String apply(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, type.Person person, String stringInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(booleanInput, dateInput, enumerationInput, numberInput, person, stringInput, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, type.Person person, String stringInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision'
            long decisionStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionArguments_.put("BooleanInput", booleanInput);
            decisionArguments_.put("DateInput", dateInput);
            decisionArguments_.put("EnumerationInput", enumerationInput);
            decisionArguments_.put("NumberInput", numberInput);
            decisionArguments_.put("Person", person);
            decisionArguments_.put("StringInput", stringInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionArguments_);

            // Evaluate decision 'decision'
            String output_ = evaluate(booleanInput, dateInput, enumerationInput, numberInput, person, stringInput, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionArguments_, output_, (System.currentTimeMillis() - decisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision' evaluation", e);
            return null;
        }
    }

    protected String evaluate(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, type.Person person, String stringInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(booleanInput, dateInput, enumerationInput, numberInput, person, stringInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(booleanInput, dateInput, enumerationInput, numberInput, person, stringInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(booleanInput, dateInput, enumerationInput, numberInput, person, stringInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(booleanInput, dateInput, enumerationInput, numberInput, person, stringInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule4(booleanInput, dateInput, enumerationInput, numberInput, person, stringInput, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule5(booleanInput, dateInput, enumerationInput, numberInput, person, stringInput, annotationSet_, eventListener_, externalExecutor_));

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

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"Rule number\")")
    public com.gs.dmn.runtime.RuleOutput rule0(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, type.Person person, String stringInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"Rule number\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericEqual(numberInput, number("1"))),
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
            output_.setDecision("number");

            // Add annotation
            annotationSet_.addAnnotation("decision", 0, string("Rule number"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"Rule string\")")
    public com.gs.dmn.runtime.RuleOutput rule1(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, type.Person person, String stringInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "string(\"Rule string\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            (stringEqual(stringInput, "abc")),
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
            output_.setDecision("string");

            // Add annotation
            annotationSet_.addAnnotation("decision", 1, string("Rule string"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "string(\"Rule boolean\")")
    public com.gs.dmn.runtime.RuleOutput rule2(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, type.Person person, String stringInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "string(\"Rule boolean\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            (booleanEqual(booleanInput, Boolean.TRUE)),
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("boolean");

            // Add annotation
            annotationSet_.addAnnotation("decision", 2, string("Rule boolean"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "string(\"Rule date\")")
    public com.gs.dmn.runtime.RuleOutput rule3(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, type.Person person, String stringInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "string(\"Rule date\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            (dateEqual(dateInput, date("2017-01-01"))),
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("date");

            // Add annotation
            annotationSet_.addAnnotation("decision", 3, string("Rule date"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "string(\"Rule enumeration\")")
    public com.gs.dmn.runtime.RuleOutput rule4(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, type.Person person, String stringInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "string(\"Rule enumeration\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            (stringEqual(enumerationInput, "E1")),
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("enumeration");

            // Add annotation
            annotationSet_.addAnnotation("decision", 4, string("Rule enumeration"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "string(\"Rule person\")")
    public com.gs.dmn.runtime.RuleOutput rule5(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, type.Person person, String stringInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "string(\"Rule person\")");

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
            (stringEqual(((String)(person != null ? person.getName() : null)), "Peter")),
            (numericEqual(((java.math.BigDecimal)(person != null ? person.getAge() : null)), number("10")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDecision("person");

            // Add annotation
            annotationSet_.addAnnotation("decision", 5, string("Rule person"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
