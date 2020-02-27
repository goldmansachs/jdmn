package nested_input_data_imports;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "ModelCDecisionBasedOnBs"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "nested_input_data_imports",
    name = "ModelCDecisionBasedOnBs",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ModelCDecisionBasedOnBs extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "nested_input_data_imports",
        "ModelCDecisionBasedOnBs",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final model_b2.EvaluatingB2SayHello model_b2_evaluatingB2SayHello;
    private final model_b.EvaluatingSayHello model_b_evaluatingSayHello;

    public ModelCDecisionBasedOnBs() {
        this(new model_b2.EvaluatingB2SayHello(), new model_b.EvaluatingSayHello());
    }

    public ModelCDecisionBasedOnBs(model_b2.EvaluatingB2SayHello model_b2_evaluatingB2SayHello, model_b.EvaluatingSayHello model_b_evaluatingSayHello) {
        this.model_b2_evaluatingB2SayHello = model_b2_evaluatingB2SayHello;
        this.model_b_evaluatingSayHello = model_b_evaluatingSayHello;
    }

    public Object apply(String model_a_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(model_a_personName, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public Object apply(String model_a_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'ModelCDecisionBasedOnBs'
            long modelCDecisionBasedOnBsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments modelCDecisionBasedOnBsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            modelCDecisionBasedOnBsArguments_.put("model_a_personName", model_a_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, modelCDecisionBasedOnBsArguments_);

            // Apply child decisions
            String model_b2_evaluatingB2SayHello = this.model_b2_evaluatingB2SayHello.apply(model_a_personName, annotationSet_, eventListener_, externalExecutor_);
            String model_b_evaluatingSayHello = this.model_b_evaluatingSayHello.apply(model_a_personName, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate decision 'ModelCDecisionBasedOnBs'
            Object output_ = evaluate(model_b2_evaluatingB2SayHello, model_b_evaluatingSayHello, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'ModelCDecisionBasedOnBs'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, modelCDecisionBasedOnBsArguments_, output_, (System.currentTimeMillis() - modelCDecisionBasedOnBsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'ModelCDecisionBasedOnBs' evaluation", e);
            return null;
        }
    }

    protected Object evaluate(String model_b2_evaluatingB2SayHello, String model_b_evaluatingSayHello, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return stringAdd(stringAdd(stringAdd("B: ", model_b_evaluatingSayHello), "; B2: "), model_b2_evaluatingB2SayHello);
    }
}
