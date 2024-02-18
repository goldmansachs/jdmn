
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"signavio-decision.ftl", "partB"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "partB",
    label = "partB",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 1
)
public class PartB extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "partB",
        "partB",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        1
    );

    private final Arithmetic arithmetic;
    private final Comparator comparator;
    private final FormattingAndCoercing formattingAndCoercing;
    private final ListHandling listHandling;
    private final Statistical statistical;
    private final StringHandlingComparator stringHandlingComparator;

    public PartB() {
        this(new Arithmetic(), new Comparator(), new FormattingAndCoercing(), new ListHandling(), new Statistical(), new StringHandlingComparator());
    }

    public PartB(Arithmetic arithmetic, Comparator comparator, FormattingAndCoercing formattingAndCoercing, ListHandling listHandling, Statistical statistical, StringHandlingComparator stringHandlingComparator) {
        this.arithmetic = arithmetic;
        this.comparator = comparator;
        this.formattingAndCoercing = formattingAndCoercing;
        this.listHandling = listHandling;
        this.statistical = statistical;
        this.stringHandlingComparator = stringHandlingComparator;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("numberA") != null ? number(input_.get("numberA")) : null), (input_.get("numberB") != null ? number(input_.get("numberB")) : null), (input_.get("numberList") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("numberList"), new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), input_.get("string"), (input_.get("stringList") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("stringList"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PartB'", e);
            return null;
        }
    }

    public String apply(java.math.BigDecimal numberA, java.math.BigDecimal numberB, List<java.math.BigDecimal> numberList, String string, List<String> stringList, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'partB'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long partBStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments partBArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            partBArguments_.put("numberA", numberA);
            partBArguments_.put("numberB", numberB);
            partBArguments_.put("numberList", numberList);
            partBArguments_.put("string", string);
            partBArguments_.put("stringList", stringList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, partBArguments_);

            // Evaluate decision 'partB'
            String output_ = evaluate(numberA, numberB, numberList, string, stringList, context_);

            // End decision 'partB'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, partBArguments_, output_, (System.currentTimeMillis() - partBStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'partB' evaluation", e);
            return null;
        }
    }

    protected String evaluate(java.math.BigDecimal numberA, java.math.BigDecimal numberB, List<java.math.BigDecimal> numberList, String string, List<String> stringList, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        java.math.BigDecimal arithmetic = this.arithmetic.apply(numberA, numberB, numberList, context_);
        List<String> comparator = this.comparator.apply(numberA, context_);
        java.math.BigDecimal formattingAndCoercing = this.formattingAndCoercing.apply(numberB, string, context_);
        Boolean listHandling = this.listHandling.apply(numberB, numberList, context_);
        java.math.BigDecimal statistical = this.statistical.apply(numberList, context_);
        Boolean stringHandlingComparator = this.stringHandlingComparator.apply(numberA, numberB, stringList, context_);

        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(arithmetic, comparator, formattingAndCoercing, listHandling, statistical, stringHandlingComparator, context_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((PartBRuleOutput)ruleOutput_).getPartB();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(java.math.BigDecimal arithmetic, List<String> comparator, java.math.BigDecimal formattingAndCoercing, Boolean listHandling, java.math.BigDecimal statistical, Boolean stringHandlingComparator, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PartBRuleOutput output_ = new PartBRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(stringHandlingComparator == null),
            notContainsAny(comparator, asList("bottomRule")),
            booleanNot(statistical == null),
            booleanNot(listHandling == null),
            booleanNot(formattingAndCoercing == null),
            booleanNot(arithmetic == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPartB("NotNull");

            // Add annotation
            annotationSet_.addAnnotation("partB", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
