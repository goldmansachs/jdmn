
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
public class TestNullComplexTypeAccess extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
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

    public String apply(String inputString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(inputString, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(String inputString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'testNullComplexTypeAccess'
            long testNullComplexTypeAccessStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments testNullComplexTypeAccessArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            testNullComplexTypeAccessArguments_.put("InputString", inputString);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, testNullComplexTypeAccessArguments_);

            // Apply child decisions
            type.IncompleteDecisionTable incompleteDecisionTable = this.incompleteDecisionTable.apply(inputString, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'testNullComplexTypeAccess'
            String output_ = evaluate(incompleteDecisionTable, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'testNullComplexTypeAccess'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, testNullComplexTypeAccessArguments_, output_, (System.currentTimeMillis() - testNullComplexTypeAccessStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'testNullComplexTypeAccess' evaluation", e);
            return null;
        }
    }

    protected String evaluate(type.IncompleteDecisionTable incompleteDecisionTable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(incompleteDecisionTable, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule1(incompleteDecisionTable, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule2(incompleteDecisionTable, annotationSet_, eventListener_, externalExecutor_));
        ruleOutputList_.add(rule3(incompleteDecisionTable, annotationSet_, eventListener_, externalExecutor_));

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

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(type.IncompleteDecisionTable incompleteDecisionTable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TestNullComplexTypeAccessRuleOutput output_ = new TestNullComplexTypeAccessRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((((String)(incompleteDecisionTable != null ? incompleteDecisionTable.getA() : null)) == null)),
            (((java.math.BigDecimal)(incompleteDecisionTable != null ? incompleteDecisionTable.getB() : null)) == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTestNullComplexTypeAccess("NonNull/Null");

            // Add annotation
            annotationSet_.addAnnotation("testNullComplexTypeAccess", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule1(type.IncompleteDecisionTable incompleteDecisionTable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TestNullComplexTypeAccessRuleOutput output_ = new TestNullComplexTypeAccessRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((((String)(incompleteDecisionTable != null ? incompleteDecisionTable.getA() : null)) == null)),
            booleanNot((((java.math.BigDecimal)(incompleteDecisionTable != null ? incompleteDecisionTable.getB() : null)) == null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTestNullComplexTypeAccess("NonNull/NonNull");

            // Add annotation
            annotationSet_.addAnnotation("testNullComplexTypeAccess", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 2, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule2(type.IncompleteDecisionTable incompleteDecisionTable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(2, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TestNullComplexTypeAccessRuleOutput output_ = new TestNullComplexTypeAccessRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (((String)(incompleteDecisionTable != null ? incompleteDecisionTable.getA() : null)) == null),
            (((java.math.BigDecimal)(incompleteDecisionTable != null ? incompleteDecisionTable.getB() : null)) == null)
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTestNullComplexTypeAccess("Null/Null");

            // Add annotation
            annotationSet_.addAnnotation("testNullComplexTypeAccess", 2, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 3, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule3(type.IncompleteDecisionTable incompleteDecisionTable, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(3, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        TestNullComplexTypeAccessRuleOutput output_ = new TestNullComplexTypeAccessRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (((String)(incompleteDecisionTable != null ? incompleteDecisionTable.getA() : null)) == null),
            booleanNot((((java.math.BigDecimal)(incompleteDecisionTable != null ? incompleteDecisionTable.getB() : null)) == null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setTestNullComplexTypeAccess("Null/NonNull");

            // Add annotation
            annotationSet_.addAnnotation("testNullComplexTypeAccess", 3, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
