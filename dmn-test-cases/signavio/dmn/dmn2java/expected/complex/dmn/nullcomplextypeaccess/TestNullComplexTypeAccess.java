
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "testNullComplexTypeAccess"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "testNullComplexTypeAccess",
    label = "TestNullComplexTypeAccess",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 4
)
public class TestNullComplexTypeAccess extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "testNullComplexTypeAccess",
        "TestNullComplexTypeAccess",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        4
    );

    private final IncompleteDecisionTable incompleteDecisionTable;

    public TestNullComplexTypeAccess() {
        this(new IncompleteDecisionTable());
    }

    public TestNullComplexTypeAccess(IncompleteDecisionTable incompleteDecisionTable) {
        this.incompleteDecisionTable = incompleteDecisionTable;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("InputString"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'TestNullComplexTypeAccess'", e);
            return null;
        }
    }

    public String apply(String inputString, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'testNullComplexTypeAccess'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long testNullComplexTypeAccessStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments testNullComplexTypeAccessArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            testNullComplexTypeAccessArguments_.put("InputString", inputString);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, testNullComplexTypeAccessArguments_);

            // Evaluate decision 'testNullComplexTypeAccess'
            String output_ = evaluate(inputString, context_);

            // End decision 'testNullComplexTypeAccess'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, testNullComplexTypeAccessArguments_, output_, (System.currentTimeMillis() - testNullComplexTypeAccessStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'testNullComplexTypeAccess' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String inputString, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        type.IncompleteDecisionTable incompleteDecisionTable = this.incompleteDecisionTable.apply(inputString, context_);

        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(incompleteDecisionTable, context_));
        ruleOutputList_.add(rule1(incompleteDecisionTable, context_));
        ruleOutputList_.add(rule2(incompleteDecisionTable, context_));
        ruleOutputList_.add(rule3(incompleteDecisionTable, context_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((TestNullComplexTypeAccessRuleOutput)ruleOutput_).getTestNullComplexTypeAccess();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(type.IncompleteDecisionTable incompleteDecisionTable, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TestNullComplexTypeAccessRuleOutput output_ = new TestNullComplexTypeAccessRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(stringEqual(((String)(incompleteDecisionTable != null ? incompleteDecisionTable.getA() : null)), null)),
            numericEqual(((java.lang.Number)(incompleteDecisionTable != null ? incompleteDecisionTable.getB() : null)), null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTestNullComplexTypeAccess("NonNull/Null");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(type.IncompleteDecisionTable incompleteDecisionTable, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TestNullComplexTypeAccessRuleOutput output_ = new TestNullComplexTypeAccessRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot(stringEqual(((String)(incompleteDecisionTable != null ? incompleteDecisionTable.getA() : null)), null)),
            booleanNot(numericEqual(((java.lang.Number)(incompleteDecisionTable != null ? incompleteDecisionTable.getB() : null)), null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTestNullComplexTypeAccess("NonNull/NonNull");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule2(type.IncompleteDecisionTable incompleteDecisionTable, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TestNullComplexTypeAccessRuleOutput output_ = new TestNullComplexTypeAccessRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            stringEqual(((String)(incompleteDecisionTable != null ? incompleteDecisionTable.getA() : null)), null),
            numericEqual(((java.lang.Number)(incompleteDecisionTable != null ? incompleteDecisionTable.getB() : null)), null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTestNullComplexTypeAccess("Null/Null");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule3(type.IncompleteDecisionTable incompleteDecisionTable, com.gs.dmn.runtime.ExecutionContext context_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "");

        // Rule start
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TestNullComplexTypeAccessRuleOutput output_ = new TestNullComplexTypeAccessRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            stringEqual(((String)(incompleteDecisionTable != null ? incompleteDecisionTable.getA() : null)), null),
            booleanNot(numericEqual(((java.lang.Number)(incompleteDecisionTable != null ? incompleteDecisionTable.getB() : null)), null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTestNullComplexTypeAccess("Null/NonNull");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
