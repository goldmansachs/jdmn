
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "compoundOutputDecision"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "compoundOutputDecision",
    label = "Compound Output Decision",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.FIRST,
    rulesCount = 6
)
public class CompoundOutputDecision extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "compoundOutputDecision",
        "Compound Output Decision",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.FIRST,
        6
    );

    public CompoundOutputDecision() {
    }

    public type.CompoundOutputDecision apply(String booleanInput, String dateAndTimeInput, String dateInput, String enumerationInput, String numberInput, String textInput, String timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((booleanInput != null ? Boolean.valueOf(booleanInput) : null), (dateAndTimeInput != null ? dateAndTime(dateAndTimeInput) : null), (dateInput != null ? date(dateInput) : null), enumerationInput, (numberInput != null ? number(numberInput) : null), textInput, (timeInput != null ? time(timeInput) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'CompoundOutputDecision'", e);
            return null;
        }
    }

    public type.CompoundOutputDecision apply(String booleanInput, String dateAndTimeInput, String dateInput, String enumerationInput, String numberInput, String textInput, String timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((booleanInput != null ? Boolean.valueOf(booleanInput) : null), (dateAndTimeInput != null ? dateAndTime(dateAndTimeInput) : null), (dateInput != null ? date(dateInput) : null), enumerationInput, (numberInput != null ? number(numberInput) : null), textInput, (timeInput != null ? time(timeInput) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CompoundOutputDecision'", e);
            return null;
        }
    }

    public type.CompoundOutputDecision apply(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public type.CompoundOutputDecision apply(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'compoundOutputDecision'
            long compoundOutputDecisionStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments compoundOutputDecisionArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            compoundOutputDecisionArguments_.put("BooleanInput", booleanInput);
            compoundOutputDecisionArguments_.put("DateAndTimeInput", dateAndTimeInput);
            compoundOutputDecisionArguments_.put("DateInput", dateInput);
            compoundOutputDecisionArguments_.put("EnumerationInput", enumerationInput);
            compoundOutputDecisionArguments_.put("NumberInput", numberInput);
            compoundOutputDecisionArguments_.put("TextInput", textInput);
            compoundOutputDecisionArguments_.put("TimeInput", timeInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compoundOutputDecisionArguments_);

            // Evaluate decision 'compoundOutputDecision'
            type.CompoundOutputDecision output_ = evaluate(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'compoundOutputDecision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, compoundOutputDecisionArguments_, output_, (System.currentTimeMillis() - compoundOutputDecisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'compoundOutputDecision' evaluation", e);
            return null;
        }
    }

    protected type.CompoundOutputDecision evaluate(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        com.gs.dmn.runtime.RuleOutput tempRuleOutput_ = rule0(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_);
        ruleOutputList_.add(tempRuleOutput_);
        boolean matched_ = tempRuleOutput_.isMatched();
        if (!matched_) {
            tempRuleOutput_ = rule1(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_);
            ruleOutputList_.add(tempRuleOutput_);
            matched_ = tempRuleOutput_.isMatched();
        }
        if (!matched_) {
            tempRuleOutput_ = rule2(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_);
            ruleOutputList_.add(tempRuleOutput_);
            matched_ = tempRuleOutput_.isMatched();
        }
        if (!matched_) {
            tempRuleOutput_ = rule3(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_);
            ruleOutputList_.add(tempRuleOutput_);
            matched_ = tempRuleOutput_.isMatched();
        }
        if (!matched_) {
            tempRuleOutput_ = rule4(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_);
            ruleOutputList_.add(tempRuleOutput_);
            matched_ = tempRuleOutput_.isMatched();
        }
        if (!matched_) {
            tempRuleOutput_ = rule5(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, annotationSet_, eventListener_, externalExecutor_);
            ruleOutputList_.add(tempRuleOutput_);
            matched_ = tempRuleOutput_.isMatched();
        }

        // Return results based on hit policy
        type.CompoundOutputDecision output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.FIRST);
            output_ = toDecisionOutput((CompoundOutputDecisionRuleOutput)ruleOutput_);
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompoundOutputDecisionRuleOutput output_ = new CompoundOutputDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateEqual(dateInput, date("2016-08-01"))),
            (timeEqual(timeInput, time("12:00:00+0000"))),
            (dateTimeEqual(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00+0000"))),
            (numericEqual(numberInput, numericUnaryMinus(number("1")))),
            (stringEqual(textInput, "abc")),
            (booleanEqual(booleanInput, Boolean.TRUE)),
            (stringEqual(enumerationInput, "e1"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFirstOutput("r11");
            output_.setSecondOutput("r12");

            // Add annotation
            annotationSet_.addAnnotation("compoundOutputDecision", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompoundOutputDecisionRuleOutput output_ = new CompoundOutputDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((dateEqual(dateInput, date("2016-08-01")))),
            booleanNot((timeEqual(timeInput, time("12:00:00+0000")))),
            booleanNot((dateTimeEqual(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00+0000")))),
            booleanNot((numericEqual(numberInput, numericUnaryMinus(number("1"))))),
            booleanNot((stringEqual(textInput, "abc"))),
            booleanNot((booleanEqual(booleanInput, Boolean.FALSE))),
            booleanNot((stringEqual(enumerationInput, "e1")))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFirstOutput("r21");
            output_.setSecondOutput("r22");

            // Add annotation
            annotationSet_.addAnnotation("compoundOutputDecision", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompoundOutputDecisionRuleOutput output_ = new CompoundOutputDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateLessThan(dateInput, date("2016-08-01"))),
            (timeLessThan(timeInput, time("12:00:00+0000"))),
            (dateTimeLessThan(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00+0000"))),
            (numericLessThan(numberInput, numericUnaryMinus(number("1")))),
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFirstOutput("r31");
            output_.setSecondOutput("r32");

            // Add annotation
            annotationSet_.addAnnotation("compoundOutputDecision", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule3(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompoundOutputDecisionRuleOutput output_ = new CompoundOutputDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateLessEqualThan(dateInput, date("2016-08-01"))),
            (timeLessEqualThan(timeInput, time("12:00:00+0000"))),
            (dateTimeLessEqualThan(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00+0000"))),
            (numericGreaterEqualThan(numberInput, numericUnaryMinus(number("1")))),
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFirstOutput("r41");
            output_.setSecondOutput("r42");

            // Add annotation
            annotationSet_.addAnnotation("compoundOutputDecision", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule4(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompoundOutputDecisionRuleOutput output_ = new CompoundOutputDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateGreaterThan(dateInput, date("2016-08-01"))),
            (timeGreaterThan(timeInput, time("12:00:00+0000"))),
            (dateTimeGreaterThan(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00+0000"))),
            (numericGreaterThan(numberInput, numericUnaryMinus(number("1")))),
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFirstOutput("r51");
            output_.setSecondOutput("r52");

            // Add annotation
            annotationSet_.addAnnotation("compoundOutputDecision", 4, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule5(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompoundOutputDecisionRuleOutput output_ = new CompoundOutputDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateGreaterEqualThan(dateInput, date("2016-08-01"))),
            (timeGreaterEqualThan(timeInput, time("12:00:00+0000"))),
            (dateTimeGreaterEqualThan(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00+0000"))),
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFirstOutput("r61");
            output_.setSecondOutput("r62");

            // Add annotation
            annotationSet_.addAnnotation("compoundOutputDecision", 5, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    public type.CompoundOutputDecision toDecisionOutput(CompoundOutputDecisionRuleOutput ruleOutput_) {
        type.CompoundOutputDecisionImpl result_ = new type.CompoundOutputDecisionImpl();
        result_.setFirstOutput(ruleOutput_ == null ? null : ruleOutput_.getFirstOutput());
        result_.setSecondOutput(ruleOutput_ == null ? null : ruleOutput_.getSecondOutput());
        return result_;
    }
}
