
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "dateFormula"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "dateFormula",
    label = "date formula",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 4
)
public class DateFormula extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "dateFormula",
        "date formula",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        4
    );

    public DateFormula() {
    }

    public List<java.math.BigDecimal> apply(String date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((date != null ? date(date) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DateFormula'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(String date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((date != null ? date(date) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DateFormula'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(date, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<java.math.BigDecimal> apply(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'dateFormula'
            long dateFormulaStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments dateFormulaArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            dateFormulaArguments_.put("date", date);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateFormulaArguments_);

            // Evaluate decision 'dateFormula'
            List<java.math.BigDecimal> output_ = evaluate(date, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'dateFormula'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dateFormulaArguments_, output_, (System.currentTimeMillis() - dateFormulaStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'dateFormula' evaluation", e);
            return null;
        }
    }

    protected List<java.math.BigDecimal> evaluate(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(date, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(date, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(date, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(date, annotationSet_, eventListener_, externalExecutor_));

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
            output_ = ruleOutputs_.stream().map(o -> ((DateFormulaRuleOutput)o).getDateFormula()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "string(\"D4R1\")")
    public com.gs.dmn.runtime.RuleOutput rule0(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "string(\"D4R1\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateFormulaRuleOutput output_ = new DateFormulaRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateFormula(day(today()));

            // Add annotation
            annotationSet_.addAnnotation("dateFormula", 0, string("D4R1"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "string(\"D4R2\")")
    public com.gs.dmn.runtime.RuleOutput rule1(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "string(\"D4R2\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateFormulaRuleOutput output_ = new DateFormulaRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateFormula(dayDiff(date, dayAdd(date, numericMultiply(day(date), numericUnaryMinus(number("1"))))));

            // Add annotation
            annotationSet_.addAnnotation("dateFormula", 1, string("D4R2"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "string(\"D4R3\")")
    public com.gs.dmn.runtime.RuleOutput rule2(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "string(\"D4R3\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateFormulaRuleOutput output_ = new DateFormulaRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateFormula(monthDiff(monthAdd(date, day(date)), date(numericAdd(number("2000"), power(month(date), numericMultiply(number("1"), power(number("1"), numericSubtract(number("14"), day(date)))))), modulo(day(date), number("12")), modulo(year(date), number("31")))));

            // Add annotation
            annotationSet_.addAnnotation("dateFormula", 2, string("D4R3"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "string(\"D4R4\")")
    public com.gs.dmn.runtime.RuleOutput rule3(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "string(\"D4R4\")");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DateFormulaRuleOutput output_ = new DateFormulaRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            Boolean.TRUE
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setDateFormula(yearDiff(dayAdd(today(), numericMultiply(number("1"), numericUnaryMinus(number("1")))), yearAdd(date, weekday(date))));

            // Add annotation
            annotationSet_.addAnnotation("dateFormula", 3, string("D4R4"));
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
