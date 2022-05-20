package nested_input_data_imports;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'Model C Decision based on Bs'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "nested_input_data_imports",
    name = "'Model C Decision based on Bs'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ModelCDecisionBasedOnBs extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "nested_input_data_imports",
        "'Model C Decision based on Bs'",
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

    public String apply(String model_b2_modela_personName, String model_b_modela_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision ''Model C Decision based on Bs''
            long modelCDecisionBasedOnBsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments modelCDecisionBasedOnBsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            modelCDecisionBasedOnBsArguments_.put("'Model B2'.modelA.'Person name'", model_b2_modela_personName);
            modelCDecisionBasedOnBsArguments_.put("'Model B'.modelA.'Person name'", model_b_modela_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, modelCDecisionBasedOnBsArguments_);

            // Evaluate decision ''Model C Decision based on Bs''
            String output_ = lambda.apply(model_b2_modela_personName, model_b_modela_personName, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision ''Model C Decision based on Bs''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, modelCDecisionBasedOnBsArguments_, output_, (System.currentTimeMillis() - modelCDecisionBasedOnBsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''Model C Decision based on Bs'' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String model_b2_modela_personName = 0 < args_.length ? (String) args_[0] : null;
                String model_b_modela_personName = 1 < args_.length ? (String) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 2 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[2] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 3 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[3] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 4 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[4] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 5 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[5] : null;

                // Apply child decisions
                String model_b2_evaluatingB2SayHello = ModelCDecisionBasedOnBs.this.model_b2_evaluatingB2SayHello.apply(model_b2_modela_personName, annotationSet_, eventListener_, externalExecutor_, cache_);
                String model_b_evaluatingSayHello = ModelCDecisionBasedOnBs.this.model_b_evaluatingSayHello.apply(model_b_modela_personName, annotationSet_, eventListener_, externalExecutor_, cache_);

                return stringAdd(stringAdd(stringAdd("B: ", model_b_evaluatingSayHello), "; B2: "), model_b2_evaluatingB2SayHello);
            }
        };
}
