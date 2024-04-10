
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

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("booleanA") != null ? Boolean.valueOf(input_.get("booleanA")) : null), (input_.get("booleanB") != null ? Boolean.valueOf(input_.get("booleanB")) : null), (input_.get("booleanList") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("booleanList"), new com.fasterxml.jackson.core.type.TypeReference<List<Boolean>>() {}) : null), (input_.get("date") != null ? date(input_.get("date")) : null), (input_.get("dateTime") != null ? dateAndTime(input_.get("dateTime")) : null), (input_.get("numberA") != null ? number(input_.get("numberA")) : null), (input_.get("numberB") != null ? number(input_.get("numberB")) : null), (input_.get("numberList") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("numberList"), new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), input_.get("string"), (input_.get("stringList") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("stringList"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (input_.get("time") != null ? time(input_.get("time")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AllTogether'", e);
            return null;
        }
    }

    public String apply(Boolean booleanA, Boolean booleanB, List<Boolean> booleanList, javax.xml.datatype.XMLGregorianCalendar date, javax.xml.datatype.XMLGregorianCalendar dateTime, java.math.BigDecimal numberA, java.math.BigDecimal numberB, List<java.math.BigDecimal> numberList, String string, List<String> stringList, javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'allTogether'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
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

            // Evaluate decision 'allTogether'
            String output_ = evaluate(booleanA, booleanB, booleanList, date, dateTime, numberA, numberB, numberList, string, stringList, time, context_);

            // End decision 'allTogether'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, allTogetherArguments_, output_, (System.currentTimeMillis() - allTogetherStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'allTogether' evaluation", e);
            return null;
        }
    }

    protected String evaluate(Boolean booleanA, Boolean booleanB, List<Boolean> booleanList, javax.xml.datatype.XMLGregorianCalendar date, javax.xml.datatype.XMLGregorianCalendar dateTime, java.math.BigDecimal numberA, java.math.BigDecimal numberB, List<java.math.BigDecimal> numberList, String string, List<String> stringList, javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        String partA = this.partA.apply(booleanList, context_);
        String partB = this.partB.apply(numberA, numberB, numberList, string, stringList, context_);
        String partC = this.partC.apply(booleanA, booleanB, date, dateTime, time, context_);

        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(partA, partB, partC, context_));

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

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(String partA, String partB, String partC, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        AllTogetherRuleOutput output_ = new AllTogetherRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(partB == null),
            booleanNot(partA == null),
            booleanNot(partC == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setAllTogether("NotNull");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
