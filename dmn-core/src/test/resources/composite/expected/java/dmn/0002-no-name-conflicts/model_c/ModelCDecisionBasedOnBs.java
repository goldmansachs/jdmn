package model_c;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "modelCDecisionBasedOnBs"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "model_c",
    name = "modelCDecisionBasedOnBs",
    label = "Model C Decision based on Bs",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ModelCDecisionBasedOnBs extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "model_c",
        "modelCDecisionBasedOnBs",
        "Model C Decision based on Bs",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final model_b1.EvaluatingB1SayHello model_b1_evaluatingB1SayHello;
    private final model_b2.EvaluatingB2SayHello model_b2_evaluatingB2SayHello;

    public ModelCDecisionBasedOnBs() {
        this(new model_b1.EvaluatingB1SayHello(), new model_b2.EvaluatingB2SayHello());
    }

    public ModelCDecisionBasedOnBs(model_b1.EvaluatingB1SayHello model_b1_evaluatingB1SayHello, model_b2.EvaluatingB2SayHello model_b2_evaluatingB2SayHello) {
        this.model_b1_evaluatingB1SayHello = model_b1_evaluatingB1SayHello;
        this.model_b2_evaluatingB2SayHello = model_b2_evaluatingB2SayHello;
    }

    public Object apply(String model_a_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(model_a_personName, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Object apply(String model_a_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'modelCDecisionBasedOnBs'
            long modelCDecisionBasedOnBsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments modelCDecisionBasedOnBsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            modelCDecisionBasedOnBsArguments_.put("model-a.Person Name", model_a_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, modelCDecisionBasedOnBsArguments_);

            // Apply child decisions
            String model_b1_evaluatingB1SayHello = this.model_b1_evaluatingB1SayHello.apply(model_a_personName, annotationSet_, eventListener_, externalExecutor_, cache_);
            String model_b2_evaluatingB2SayHello = this.model_b2_evaluatingB2SayHello.apply(model_a_personName, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'modelCDecisionBasedOnBs'
            Object output_ = evaluate(model_b1_evaluatingB1SayHello, model_b2_evaluatingB2SayHello, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'modelCDecisionBasedOnBs'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, modelCDecisionBasedOnBsArguments_, output_, (System.currentTimeMillis() - modelCDecisionBasedOnBsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'modelCDecisionBasedOnBs' evaluation", e);
            return null;
        }
    }

    protected Object evaluate(String model_b1_evaluatingB1SayHello, String model_b2_evaluatingB2SayHello, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return stringAdd(stringAdd(stringAdd("B1: ", model_b1_evaluatingB1SayHello), "; B2: "), model_b2_evaluatingB2SayHello);
    }
}
