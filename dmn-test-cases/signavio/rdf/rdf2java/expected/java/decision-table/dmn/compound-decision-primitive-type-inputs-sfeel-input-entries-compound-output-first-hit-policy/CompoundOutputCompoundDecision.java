
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "compoundOutputCompoundDecision"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "compoundOutputCompoundDecision",
    label = "Compound Output Compound Decision",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.FIRST,
    rulesCount = 2
)
public class CompoundOutputCompoundDecision extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "compoundOutputCompoundDecision",
        "Compound Output Compound Decision",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.FIRST,
        2
    );

    private final DependentDecision1 dependentDecision1;
    private final DependentDecision2 dependentDecision2;

    public CompoundOutputCompoundDecision() {
        this(new DependentDecision1(), new DependentDecision2());
    }

    public CompoundOutputCompoundDecision(DependentDecision1 dependentDecision1, DependentDecision2 dependentDecision2) {
        this.dependentDecision1 = dependentDecision1;
        this.dependentDecision2 = dependentDecision2;
    }

    public type.CompoundOutputCompoundDecision apply(String booleanInput, String dD1TextInput, String dD2NumberInput, String enumerationInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((booleanInput != null ? Boolean.valueOf(booleanInput) : null), dD1TextInput, (dD2NumberInput != null ? number(dD2NumberInput) : null), enumerationInput, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'CompoundOutputCompoundDecision'", e);
            return null;
        }
    }

    public type.CompoundOutputCompoundDecision apply(String booleanInput, String dD1TextInput, String dD2NumberInput, String enumerationInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((booleanInput != null ? Boolean.valueOf(booleanInput) : null), dD1TextInput, (dD2NumberInput != null ? number(dD2NumberInput) : null), enumerationInput, annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CompoundOutputCompoundDecision'", e);
            return null;
        }
    }

    public type.CompoundOutputCompoundDecision apply(Boolean booleanInput, String dD1TextInput, java.math.BigDecimal dD2NumberInput, String enumerationInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(booleanInput, dD1TextInput, dD2NumberInput, enumerationInput, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public type.CompoundOutputCompoundDecision apply(Boolean booleanInput, String dD1TextInput, java.math.BigDecimal dD2NumberInput, String enumerationInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'compoundOutputCompoundDecision'
            long compoundOutputCompoundDecisionStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments compoundOutputCompoundDecisionArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            compoundOutputCompoundDecisionArguments_.put("BooleanInput", booleanInput);
            compoundOutputCompoundDecisionArguments_.put("DD1 Text Input", dD1TextInput);
            compoundOutputCompoundDecisionArguments_.put("DD2 Number Input", dD2NumberInput);
            compoundOutputCompoundDecisionArguments_.put("EnumerationInput", enumerationInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, compoundOutputCompoundDecisionArguments_);

            // Apply child decisions
            type.DependentDecision1 dependentDecision1 = this.dependentDecision1.apply(dD1TextInput, annotationSet_, eventListener_, externalExecutor_, cache_);
            type.DependentDecision2 dependentDecision2 = this.dependentDecision2.apply(dD2NumberInput, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'compoundOutputCompoundDecision'
            type.CompoundOutputCompoundDecision output_ = evaluate(booleanInput, dependentDecision1, dependentDecision2, enumerationInput, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'compoundOutputCompoundDecision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, compoundOutputCompoundDecisionArguments_, output_, (System.currentTimeMillis() - compoundOutputCompoundDecisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'compoundOutputCompoundDecision' evaluation", e);
            return null;
        }
    }

    protected type.CompoundOutputCompoundDecision evaluate(Boolean booleanInput, type.DependentDecision1 dependentDecision1, type.DependentDecision2 dependentDecision2, String enumerationInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        com.gs.dmn.runtime.RuleOutput tempRuleOutput_ = rule0(booleanInput, dependentDecision1, dependentDecision2, enumerationInput, annotationSet_, eventListener_, externalExecutor_);
        ruleOutputList_.add(tempRuleOutput_);
        boolean matched_ = tempRuleOutput_.isMatched();
        if (!matched_) {
            tempRuleOutput_ = rule1(booleanInput, dependentDecision1, dependentDecision2, enumerationInput, annotationSet_, eventListener_, externalExecutor_);
            ruleOutputList_.add(tempRuleOutput_);
            matched_ = tempRuleOutput_.isMatched();
        }

        // Return results based on hit policy
        type.CompoundOutputCompoundDecision output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.FIRST);
            output_ = toDecisionOutput((CompoundOutputCompoundDecisionRuleOutput)ruleOutput_);
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(Boolean booleanInput, type.DependentDecision1 dependentDecision1, type.DependentDecision2 dependentDecision2, String enumerationInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompoundOutputCompoundDecisionRuleOutput output_ = new CompoundOutputCompoundDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (booleanEqual(booleanInput, Boolean.TRUE)),
            (stringEqual(enumerationInput, "e1")),
            (stringEqual(((String)(dependentDecision1 != null ? dependentDecision1.getDD1O1() : null)), "dd1o1")),
            (stringEqual(((String)(dependentDecision1 != null ? dependentDecision1.getDD1O2() : null)), "dd1o2")),
            (stringEqual(((String)(dependentDecision2 != null ? dependentDecision2.getDD2O1() : null)), "dd2o1")),
            (stringEqual(((String)(dependentDecision2 != null ? dependentDecision2.getDD2O2() : null)), "dd2o2"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFirstOutput("r11");
            output_.setSecondOutput("r12");

            // Add annotation
            annotationSet_.addAnnotation("compoundOutputCompoundDecision", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule1(Boolean booleanInput, type.DependentDecision1 dependentDecision1, type.DependentDecision2 dependentDecision2, String enumerationInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(1, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        CompoundOutputCompoundDecisionRuleOutput output_ = new CompoundOutputCompoundDecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((booleanEqual(booleanInput, Boolean.FALSE))),
            booleanNot((stringEqual(enumerationInput, "e1"))),
            (stringEqual(((String)(dependentDecision1 != null ? dependentDecision1.getDD1O1() : null)), "dd1o1")),
            (stringEqual(((String)(dependentDecision1 != null ? dependentDecision1.getDD1O2() : null)), "dd1o2")),
            (stringEqual(((String)(dependentDecision2 != null ? dependentDecision2.getDD2O1() : null)), "dd2o1")),
            (stringEqual(((String)(dependentDecision2 != null ? dependentDecision2.getDD2O2() : null)), "dd2o2"))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setFirstOutput("r21");
            output_.setSecondOutput("r22");

            // Add annotation
            annotationSet_.addAnnotation("compoundOutputCompoundDecision", 1, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

    public type.CompoundOutputCompoundDecision toDecisionOutput(CompoundOutputCompoundDecisionRuleOutput ruleOutput_) {
        type.CompoundOutputCompoundDecisionImpl result_ = new type.CompoundOutputCompoundDecisionImpl();
        result_.setFirstOutput(ruleOutput_ == null ? null : ruleOutput_.getFirstOutput());
        result_.setSecondOutput(ruleOutput_ == null ? null : ruleOutput_.getSecondOutput());
        return result_;
    }
}
