package com.gs.dmn.runtime.metadata;

import java.util.Map;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "assessApplicantAge"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "com.gs.dmn.generated.composite_example_credit_decision",
    name = "assessApplicantAge",
    label = "Assess applicant age",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 3
)
public class AssessApplicantAge extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "com.gs.dmn.generated.composite_example_credit_decision",
        "assessApplicantAge",
        "Assess applicant age",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        3
    );

    public AssessApplicantAge() {
    }

    @Override()
    public java.math.BigDecimal applyMap(Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return null;
        } catch (Exception e) {
            logError("Cannot apply decision 'AssessApplicantAge'", e);
            return null;
        }
    }

}
