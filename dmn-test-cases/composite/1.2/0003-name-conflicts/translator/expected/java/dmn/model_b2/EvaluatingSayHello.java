package model_b2;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "evaluatingSayHello"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "model_b2",
    name = "evaluatingSayHello",
    label = "Evaluating B2 Say Hello",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class EvaluatingSayHello extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "model_b2",
        "evaluatingSayHello",
        "Evaluating B2 Say Hello",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final model_b1.GreetThePerson model_b1_greetThePerson;

    public EvaluatingSayHello() {
        this(new model_b1.GreetThePerson());
    }

    public EvaluatingSayHello(model_b1.GreetThePerson model_b1_greetThePerson) {
        this.model_b1_greetThePerson = model_b1_greetThePerson;
    }

    public String apply(String model_a_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(model_a_personName, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(String model_a_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'evaluatingSayHello'
            long evaluatingSayHelloStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments evaluatingSayHelloArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            evaluatingSayHelloArguments_.put("model-a.Person Name", model_a_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, evaluatingSayHelloArguments_);

            // Apply child decisions
            String model_b1_greetThePerson = this.model_b1_greetThePerson.apply(model_a_personName, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'evaluatingSayHello'
            String output_ = evaluate(model_b1_greetThePerson, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'evaluatingSayHello'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, evaluatingSayHelloArguments_, output_, (System.currentTimeMillis() - evaluatingSayHelloStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'evaluatingSayHello' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String model_b1_greetThePerson, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return stringAdd("Evaluating Say Hello to: ", model_b1_greetThePerson);
    }
}
