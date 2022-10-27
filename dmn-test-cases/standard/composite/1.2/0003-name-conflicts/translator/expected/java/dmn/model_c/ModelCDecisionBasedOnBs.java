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

    private final model_b1.EvaluatingSayHello model_b1_evaluatingSayHello;
    private final model_b2.EvaluatingSayHello model_b2_evaluatingSayHello;

    public ModelCDecisionBasedOnBs() {
        this(new model_b1.EvaluatingSayHello(), new model_b2.EvaluatingSayHello());
    }

    public ModelCDecisionBasedOnBs(model_b1.EvaluatingSayHello model_b1_evaluatingSayHello, model_b2.EvaluatingSayHello model_b2_evaluatingSayHello) {
        this.model_b1_evaluatingSayHello = model_b1_evaluatingSayHello;
        this.model_b2_evaluatingSayHello = model_b2_evaluatingSayHello;
    }

    @java.lang.Override()
    public String apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("model-a.Person Name"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'ModelCDecisionBasedOnBs'", e);
            return null;
        }
    }

    public String apply(String model_a_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'modelCDecisionBasedOnBs'
            long modelCDecisionBasedOnBsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments modelCDecisionBasedOnBsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            modelCDecisionBasedOnBsArguments_.put("model-a.Person Name", model_a_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, modelCDecisionBasedOnBsArguments_);

            // Evaluate decision 'modelCDecisionBasedOnBs'
            String output_ = lambda.apply(model_a_personName, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'modelCDecisionBasedOnBs'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, modelCDecisionBasedOnBsArguments_, output_, (System.currentTimeMillis() - modelCDecisionBasedOnBsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'modelCDecisionBasedOnBs' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String model_a_personName = 0 < args_.length ? (String) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 1 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[1] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 2 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[2] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 3 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[3] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 4 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[4] : null;

                // Apply child decisions
                String model_b1_evaluatingSayHello = ModelCDecisionBasedOnBs.this.model_b1_evaluatingSayHello.apply(model_a_personName, annotationSet_, eventListener_, externalExecutor_, cache_);
                String model_b2_evaluatingSayHello = ModelCDecisionBasedOnBs.this.model_b2_evaluatingSayHello.apply(model_a_personName, annotationSet_, eventListener_, externalExecutor_, cache_);

                return stringAdd(stringAdd(stringAdd("B1: ", model_b1_evaluatingSayHello), "; B2: "), model_b2_evaluatingSayHello);
            }
        };
}
