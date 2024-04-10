
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

    @java.lang.Override()
    public type.CompoundOutputDecision applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("BooleanInput") != null ? Boolean.valueOf(input_.get("BooleanInput")) : null), (input_.get("DateAndTimeInput") != null ? dateAndTime(input_.get("DateAndTimeInput")) : null), (input_.get("DateInput") != null ? date(input_.get("DateInput")) : null), input_.get("EnumerationInput"), (input_.get("NumberInput") != null ? number(input_.get("NumberInput")) : null), input_.get("TextInput"), (input_.get("TimeInput") != null ? time(input_.get("TimeInput")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CompoundOutputDecision'", e);
            return null;
        }
    }

    public type.CompoundOutputDecision apply(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'compoundOutputDecision'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
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
            type.CompoundOutputDecision output_ = evaluate(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_);

            // End decision 'compoundOutputDecision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, compoundOutputDecisionArguments_, output_, (System.currentTimeMillis() - compoundOutputDecisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'compoundOutputDecision' evaluation", e);
            return null;
        }
    }

    protected type.CompoundOutputDecision evaluate(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        com.gs.dmn.runtime.RuleOutput tempRuleOutput_ = rule0(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_);
        ruleOutputList_.add(tempRuleOutput_);
        boolean matched_ = tempRuleOutput_.isMatched();
        if (!matched_) {
            tempRuleOutput_ = rule1(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_);
            ruleOutputList_.add(tempRuleOutput_);
            matched_ = tempRuleOutput_.isMatched();
        }
        if (!matched_) {
            tempRuleOutput_ = rule2(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_);
            ruleOutputList_.add(tempRuleOutput_);
            matched_ = tempRuleOutput_.isMatched();
        }
        if (!matched_) {
            tempRuleOutput_ = rule3(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_);
            ruleOutputList_.add(tempRuleOutput_);
            matched_ = tempRuleOutput_.isMatched();
        }
        if (!matched_) {
            tempRuleOutput_ = rule4(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_);
            ruleOutputList_.add(tempRuleOutput_);
            matched_ = tempRuleOutput_.isMatched();
        }
        if (!matched_) {
            tempRuleOutput_ = rule5(booleanInput, dateAndTimeInput, dateInput, enumerationInput, numberInput, textInput, timeInput, context_);
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

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompoundOutputDecisionRuleOutput output_ = new CompoundOutputDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            dateEqual(dateInput, date("2016-08-01")),
            timeEqual(timeInput, time("12:00:00Z")),
            dateTimeEqual(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00Z")),
            numericEqual(numberInput, numericUnaryMinus(number("1"))),
            stringEqual(textInput, "abc"),
            booleanEqual(booleanInput, Boolean.TRUE),
            stringEqual(enumerationInput, "e1")
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFirstOutput("r11");
            output_.setSecondOutput("r12");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompoundOutputDecisionRuleOutput output_ = new CompoundOutputDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(dateEqual(dateInput, date("2016-08-01"))),
            booleanNot(timeEqual(timeInput, time("12:00:00Z"))),
            booleanNot(dateTimeEqual(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00Z"))),
            booleanNot(numericEqual(numberInput, numericUnaryMinus(number("1")))),
            booleanNot(stringEqual(textInput, "abc")),
            booleanNot(booleanEqual(booleanInput, Boolean.FALSE)),
            booleanNot(stringEqual(enumerationInput, "e1"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFirstOutput("r21");
            output_.setSecondOutput("r22");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompoundOutputDecisionRuleOutput output_ = new CompoundOutputDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            dateLessThan(dateInput, date("2016-08-01")),
            timeLessThan(timeInput, time("12:00:00Z")),
            dateTimeLessThan(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00Z")),
            numericLessThan(numberInput, numericUnaryMinus(number("1"))),
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
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompoundOutputDecisionRuleOutput output_ = new CompoundOutputDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            dateLessEqualThan(dateInput, date("2016-08-01")),
            timeLessEqualThan(timeInput, time("12:00:00Z")),
            dateTimeLessEqualThan(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00Z")),
            numericGreaterEqualThan(numberInput, numericUnaryMinus(number("1"))),
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
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 4, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule4(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(4, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompoundOutputDecisionRuleOutput output_ = new CompoundOutputDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            dateGreaterThan(dateInput, date("2016-08-01")),
            timeGreaterThan(timeInput, time("12:00:00Z")),
            dateTimeGreaterThan(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00Z")),
            numericGreaterThan(numberInput, numericUnaryMinus(number("1"))),
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
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 5, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule5(Boolean booleanInput, javax.xml.datatype.XMLGregorianCalendar dateAndTimeInput, javax.xml.datatype.XMLGregorianCalendar dateInput, String enumerationInput, java.math.BigDecimal numberInput, String textInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(5, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompoundOutputDecisionRuleOutput output_ = new CompoundOutputDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            dateGreaterEqualThan(dateInput, date("2016-08-01")),
            timeGreaterEqualThan(timeInput, time("12:00:00Z")),
            dateTimeGreaterEqualThan(dateAndTimeInput, dateAndTime("2016-08-01T11:00:00Z")),
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
