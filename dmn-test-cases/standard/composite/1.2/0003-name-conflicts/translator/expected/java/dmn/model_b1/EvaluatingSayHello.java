package model_b1;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "evaluatingSayHello"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "model_b1",
    name = "evaluatingSayHello",
    label = "Evaluating Say Hello",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class EvaluatingSayHello extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "model_b1",
        "evaluatingSayHello",
        "Evaluating Say Hello",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final model_b1.GreetThePerson greetThePerson;

    public EvaluatingSayHello() {
        this(new model_b1.GreetThePerson());
    }

    public EvaluatingSayHello(model_b1.GreetThePerson greetThePerson) {
        this.greetThePerson = greetThePerson;
    }

    @java.lang.Override()
    public String apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("model-a.Person Name"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'EvaluatingSayHello'", e);
            return null;
        }
    }

    public String apply(String model_a_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'evaluatingSayHello'
            long evaluatingSayHelloStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments evaluatingSayHelloArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            evaluatingSayHelloArguments_.put("model-a.Person Name", model_a_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, evaluatingSayHelloArguments_);

            // Evaluate decision 'evaluatingSayHello'
            String output_ = lambda.apply(model_a_personName, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'evaluatingSayHello'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, evaluatingSayHelloArguments_, output_, (System.currentTimeMillis() - evaluatingSayHelloStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'evaluatingSayHello' evaluation", e);
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
                String greetThePerson = EvaluatingSayHello.this.greetThePerson.apply(model_a_personName, annotationSet_, eventListener_, externalExecutor_, cache_);

                return stringAdd("Evaluating Say Hello to: ", greetThePerson);
            }
        };
}
