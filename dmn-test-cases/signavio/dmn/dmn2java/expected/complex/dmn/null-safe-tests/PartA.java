
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "partA"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "partA",
    label = "partA",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 1
)
public class PartA extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "partA",
        "partA",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        1
    );

    private final AllFalseAggregation allFalseAggregation;
    private final AllTrueAggregation allTrueAggregation;
    private final AnyTrueAggregation anyTrueAggregation;

    public PartA() {
        this(new AllFalseAggregation(), new AllTrueAggregation(), new AnyTrueAggregation());
    }

    public PartA(AllFalseAggregation allFalseAggregation, AllTrueAggregation allTrueAggregation, AnyTrueAggregation anyTrueAggregation) {
        this.allFalseAggregation = allFalseAggregation;
        this.allTrueAggregation = allTrueAggregation;
        this.anyTrueAggregation = anyTrueAggregation;
    }

    public String apply(String booleanList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((booleanList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(booleanList, new com.fasterxml.jackson.core.type.TypeReference<List<Boolean>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'PartA'", e);
            return null;
        }
    }

    public String apply(String booleanList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((booleanList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(booleanList, new com.fasterxml.jackson.core.type.TypeReference<List<Boolean>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PartA'", e);
            return null;
        }
    }

    public String apply(List<Boolean> booleanList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(booleanList, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(List<Boolean> booleanList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'partA'
            long partAStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments partAArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            partAArguments_.put("booleanList", booleanList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, partAArguments_);

            // Apply child decisions
            Boolean allFalseAggregation = this.allFalseAggregation.apply(booleanList, annotationSet_, eventListener_, externalExecutor_, cache_);
            Boolean allTrueAggregation = this.allTrueAggregation.apply(booleanList, annotationSet_, eventListener_, externalExecutor_, cache_);
            Boolean anyTrueAggregation = this.anyTrueAggregation.apply(booleanList, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'partA'
            String output_ = evaluate(allFalseAggregation, allTrueAggregation, anyTrueAggregation, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'partA'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, partAArguments_, output_, (System.currentTimeMillis() - partAStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'partA' evaluation", e);
            return null;
        }
    }

    protected String evaluate(Boolean allFalseAggregation, Boolean allTrueAggregation, Boolean anyTrueAggregation, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(allFalseAggregation, allTrueAggregation, anyTrueAggregation, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((PartARuleOutput)ruleOutput_).getPartA();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(Boolean allFalseAggregation, Boolean allTrueAggregation, Boolean anyTrueAggregation, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PartARuleOutput output_ = new PartARuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((anyTrueAggregation == null)),
            booleanNot((allTrueAggregation == null)),
            booleanNot((allFalseAggregation == null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPartA("NotNull");

            // Add annotation
            annotationSet_.addAnnotation("partA", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
