
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "datetimeFormula"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "datetimeFormula",
    label = "datetime formula",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 2
)
public class DatetimeFormula extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "datetimeFormula",
        "datetime formula",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        2
    );

    public DatetimeFormula() {
    }

    public List<java.math.BigDecimal> apply(String datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((datetime != null ? dateAndTime(datetime) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DatetimeFormula'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(String datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((datetime != null ? dateAndTime(datetime) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DatetimeFormula'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(datetime, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<java.math.BigDecimal> apply(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'datetimeFormula'
            long datetimeFormulaStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments datetimeFormulaArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            datetimeFormulaArguments_.put("datetime", datetime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, datetimeFormulaArguments_);

            // Evaluate decision 'datetimeFormula'
            List<java.math.BigDecimal> output_ = evaluate(datetime, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'datetimeFormula'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, datetimeFormulaArguments_, output_, (System.currentTimeMillis() - datetimeFormulaStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'datetimeFormula' evaluation", e);
            return null;
        }
    }

    protected List<java.math.BigDecimal> evaluate(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(datetime, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(datetime, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        List<java.math.BigDecimal> output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
            if (output_ == null) {
                output_ = asList();
            }
        } else {
            List<? extends com.gs.dmn.runtime.RuleOutput> ruleOutputs_ = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT);
            output_ = ruleOutputs_.stream().map(o -> ((DatetimeFormulaRuleOutput)o).getDatetimeFormula()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"D6R1\")")
    public com.gs.dmn.runtime.RuleOutput rule0(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"D6R1\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DatetimeFormulaRuleOutput output_ = new DatetimeFormulaRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDatetimeFormula(hourDiff(datetime, dateTime(modulo(year(datetime), number("31")), numericDivide(hour(datetime), number("12")), numericAdd(number("1990"), power(day(datetime), numericMultiply(number("1"), power(number("1"), numericSubtract(number("12"), hour(datetime)))))), modulo(minute(datetime), number("24")), numericAdd(number("1"), numericMultiply(numericDivide(number("58"), number("11")), numericSubtract(month(datetime), number("1")))), number("0"))));

            // Add annotation
            annotationSet_.addAnnotation("datetimeFormula", 0, string("D6R1"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"D6R2\")")
    public com.gs.dmn.runtime.RuleOutput rule1(javax.xml.datatype.XMLGregorianCalendar datetime, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "string(\"D6R2\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DatetimeFormulaRuleOutput output_ = new DatetimeFormulaRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDatetimeFormula(minutesDiff(datetime, dateTime(modulo(year(datetime), number("31")), numericDivide(hour(datetime), number("12")), numericAdd(number("1990"), power(day(datetime), numericMultiply(number("1"), power(number("1"), numericSubtract(number("12"), hour(datetime)))))), modulo(minute(datetime), number("24")), numericAdd(number("1"), numericMultiply(numericDivide(number("58"), number("11")), numericSubtract(month(datetime), number("1")))), number("0"))));

            // Add annotation
            annotationSet_.addAnnotation("datetimeFormula", 1, string("D6R2"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
