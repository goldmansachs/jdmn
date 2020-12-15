
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "allTogether"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "allTogether",
    label = "allTogether",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 1
)
public class AllTogether extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "allTogether",
        "allTogether",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        1
    );

    private final PartA partA;
    private final PartB partB;
    private final PartC partC;

    public AllTogether() {
        this(new PartA(), new PartB(), new PartC());
    }

    public AllTogether(PartA partA, PartB partB, PartC partC) {
        this.partA = partA;
        this.partB = partB;
        this.partC = partC;
    }

    public String apply(String booleanA, String booleanB, String booleanList, String date, String dateTime, String numberA, String numberB, String numberList, String string, String stringList, String time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((booleanA != null ? Boolean.valueOf(booleanA) : null), (booleanB != null ? Boolean.valueOf(booleanB) : null), (booleanList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(booleanList, new com.fasterxml.jackson.core.type.TypeReference<List<Boolean>>() {}) : null), (date != null ? date(date) : null), (dateTime != null ? dateAndTime(dateTime) : null), (numberA != null ? number(numberA) : null), (numberB != null ? number(numberB) : null), (numberList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(numberList, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), string, (stringList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(stringList, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (time != null ? time(time) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'AllTogether'", e);
            return null;
        }
    }

    public String apply(String booleanA, String booleanB, String booleanList, String date, String dateTime, String numberA, String numberB, String numberList, String string, String stringList, String time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((booleanA != null ? Boolean.valueOf(booleanA) : null), (booleanB != null ? Boolean.valueOf(booleanB) : null), (booleanList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(booleanList, new com.fasterxml.jackson.core.type.TypeReference<List<Boolean>>() {}) : null), (date != null ? date(date) : null), (dateTime != null ? dateAndTime(dateTime) : null), (numberA != null ? number(numberA) : null), (numberB != null ? number(numberB) : null), (numberList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(numberList, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), string, (stringList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(stringList, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (time != null ? time(time) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AllTogether'", e);
            return null;
        }
    }

    public String apply(Boolean booleanA, Boolean booleanB, List<Boolean> booleanList, javax.xml.datatype.XMLGregorianCalendar date, javax.xml.datatype.XMLGregorianCalendar dateTime, java.math.BigDecimal numberA, java.math.BigDecimal numberB, List<java.math.BigDecimal> numberList, String string, List<String> stringList, javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(Boolean booleanA, Boolean booleanB, List<Boolean> booleanList, javax.xml.datatype.XMLGregorianCalendar date, javax.xml.datatype.XMLGregorianCalendar dateTime, java.math.BigDecimal numberA, java.math.BigDecimal numberB, List<java.math.BigDecimal> numberList, String string, List<String> stringList, javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'allTogether'
            long allTogetherStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments allTogetherArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            allTogetherArguments_.put("booleanA", booleanA);
            allTogetherArguments_.put("booleanB", booleanB);
            allTogetherArguments_.put("booleanList", booleanList);
            allTogetherArguments_.put("date", date);
            allTogetherArguments_.put("dateTime", dateTime);
            allTogetherArguments_.put("numberA", numberA);
            allTogetherArguments_.put("numberB", numberB);
            allTogetherArguments_.put("numberList", numberList);
            allTogetherArguments_.put("string", string);
            allTogetherArguments_.put("stringList", stringList);
            allTogetherArguments_.put("time", time);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, allTogetherArguments_);

            // Apply child decisions
            String partA = this.partA.apply(booleanList, annotationSet_, eventListener_, externalExecutor_, cache_);
            String partB = this.partB.apply(numberA, numberB, numberList, string, stringList, annotationSet_, eventListener_, externalExecutor_, cache_);
            String partC = this.partC.apply(booleanA, booleanB, date, dateTime, time, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'allTogether'
            String output_ = evaluate(partA, partB, partC, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'allTogether'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, allTogetherArguments_, output_, (System.currentTimeMillis() - allTogetherStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'allTogether' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String partA, String partB, String partC, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(partA, partB, partC, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((AllTogetherRuleOutput)ruleOutput_).getAllTogether();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(String partA, String partB, String partC, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        AllTogetherRuleOutput output_ = new AllTogetherRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((partB == null)),
            booleanNot((partA == null)),
            booleanNot((partC == null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setAllTogether("NotNull");

            // Add annotation
            annotationSet_.addAnnotation("allTogether", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
