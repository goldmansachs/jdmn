
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "temporalDiffs"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "temporalDiffs",
    label = "temporalDiffs",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 4
)
public class TemporalDiffs extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
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

    public List<type.TemporalDiffs> apply(String day, String hour, String minute, String month, String second, String year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((day != null ? number(day) : null), (hour != null ? number(hour) : null), (minute != null ? number(minute) : null), (month != null ? number(month) : null), (second != null ? number(second) : null), (year != null ? number(year) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'TemporalDiffs'", e);
            return null;
        }
    }

    public List<type.TemporalDiffs> apply(String day, String hour, String minute, String month, String second, String year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((day != null ? number(day) : null), (hour != null ? number(hour) : null), (minute != null ? number(minute) : null), (month != null ? number(month) : null), (second != null ? number(second) : null), (year != null ? number(year) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'TemporalDiffs'", e);
            return null;
        }
    }

    public List<type.TemporalDiffs> apply(java.math.BigDecimal day, java.math.BigDecimal hour, java.math.BigDecimal minute, java.math.BigDecimal month, java.math.BigDecimal second, java.math.BigDecimal year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(day, hour, minute, month, second, year, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<type.TemporalDiffs> apply(java.math.BigDecimal day, java.math.BigDecimal hour, java.math.BigDecimal minute, java.math.BigDecimal month, java.math.BigDecimal second, java.math.BigDecimal year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'temporalDiffs'
            long temporalDiffsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments temporalDiffsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            temporalDiffsArguments_.put("day", day);
            temporalDiffsArguments_.put("hour", hour);
            temporalDiffsArguments_.put("minute", minute);
            temporalDiffsArguments_.put("month", month);
            temporalDiffsArguments_.put("second", second);
            temporalDiffsArguments_.put("year", year);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, temporalDiffsArguments_);

            // Apply child decisions
            type.GenerateTemporalObjects generateTemporalObjects = this.generateTemporalObjects.apply(day, hour, minute, month, second, year, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'temporalDiffs'
            List<type.TemporalDiffs> output_ = evaluate(generateTemporalObjects, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'temporalDiffs'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, temporalDiffsArguments_, output_, (System.currentTimeMillis() - temporalDiffsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'temporalDiffs' evaluation", e);
            return null;
        }
    }

    protected List<type.TemporalDiffs> evaluate(type.GenerateTemporalObjects generateTemporalObjects, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(generateTemporalObjects, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(generateTemporalObjects, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(generateTemporalObjects, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(generateTemporalObjects, annotationSet_, eventListener_, externalExecutor_));

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
            output_ = ruleOutputs_.stream().map(o -> toDecisionOutput(((TemporalDiffsRuleOutput)o))).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(type.GenerateTemporalObjects generateTemporalObjects, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
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
            output_.setDateDiff(dayDiff(((javax.xml.datatype.XMLGregorianCalendar)(generateTemporalObjects != null ? generateTemporalObjects.getDate() : null)), ((javax.xml.datatype.XMLGregorianCalendar)(generateTemporalObjects != null ? generateTemporalObjects.getDate() : null))));
            output_.setDateTimeDiff(minutesDiff(((javax.xml.datatype.XMLGregorianCalendar)(generateTemporalObjects != null ? generateTemporalObjects.getDatetime() : null)), ((javax.xml.datatype.XMLGregorianCalendar)(generateTemporalObjects != null ? generateTemporalObjects.getDatetime() : null))));

            // Add annotation
            annotationSet_.addAnnotation("temporalDiffs", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(type.GenerateTemporalObjects generateTemporalObjects, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
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
            output_.setDateDiff(monthDiff(((javax.xml.datatype.XMLGregorianCalendar)(generateTemporalObjects != null ? generateTemporalObjects.getDate() : null)), ((javax.xml.datatype.XMLGregorianCalendar)(generateTemporalObjects != null ? generateTemporalObjects.getDate() : null))));
            output_.setDateTimeDiff(hourDiff(((javax.xml.datatype.XMLGregorianCalendar)(generateTemporalObjects != null ? generateTemporalObjects.getDatetime() : null)), ((javax.xml.datatype.XMLGregorianCalendar)(generateTemporalObjects != null ? generateTemporalObjects.getDatetime() : null))));

            // Add annotation
            annotationSet_.addAnnotation("temporalDiffs", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(type.GenerateTemporalObjects generateTemporalObjects, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
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
            output_.setDateDiff(yearDiff(((javax.xml.datatype.XMLGregorianCalendar)(generateTemporalObjects != null ? generateTemporalObjects.getDate() : null)), ((javax.xml.datatype.XMLGregorianCalendar)(generateTemporalObjects != null ? generateTemporalObjects.getDate() : null))));
            output_.setDateTimeDiff(minutesDiff(((javax.xml.datatype.XMLGregorianCalendar)(generateTemporalObjects != null ? generateTemporalObjects.getDatetime() : null)), ((javax.xml.datatype.XMLGregorianCalendar)(generateTemporalObjects != null ? generateTemporalObjects.getDatetime() : null))));

            // Add annotation
            annotationSet_.addAnnotation("temporalDiffs", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule3(type.GenerateTemporalObjects generateTemporalObjects, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "\"\"");

        // Rule start
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
            output_.setDateDiff(dayDiff(((javax.xml.datatype.XMLGregorianCalendar)(generateTemporalObjects != null ? generateTemporalObjects.getDate() : null)), date(number("2016"), number("1"), number("1"))));
            output_.setDateTimeDiff(hourDiff(((javax.xml.datatype.XMLGregorianCalendar)(generateTemporalObjects != null ? generateTemporalObjects.getDatetime() : null)), dateTime(number("1"), number("1"), number("2016"), number("11"), number("11"), number("11"))));

            // Add annotation
            annotationSet_.addAnnotation("temporalDiffs", 3, "");
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
