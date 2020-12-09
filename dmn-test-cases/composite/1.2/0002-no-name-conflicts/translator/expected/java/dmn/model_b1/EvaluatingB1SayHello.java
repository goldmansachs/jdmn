package model_b1;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "evaluatingB1SayHello"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "model_b1",
    name = "evaluatingB1SayHello",
    label = "Evaluating Say Hello",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class EvaluatingB1SayHello extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "model_b1",
        "evaluatingB1SayHello",
        "Evaluating Say Hello",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final model_b1.GreetThePerson greetThePerson;

    public EvaluatingB1SayHello() {
        this(new model_b1.GreetThePerson());
    }

    public EvaluatingB1SayHello(model_b1.GreetThePerson greetThePerson) {
        this.greetThePerson = greetThePerson;
    }

    public String apply(String model_a_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(model_a_personName, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(String model_a_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'evaluatingB1SayHello'
            long evaluatingB1SayHelloStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments evaluatingB1SayHelloArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            evaluatingB1SayHelloArguments_.put("model-a.Person Name", model_a_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, evaluatingB1SayHelloArguments_);

            // Apply child decisions
            String greetThePerson = this.greetThePerson.apply(model_a_personName, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'evaluatingB1SayHello'
            String output_ = evaluate(greetThePerson, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'evaluatingB1SayHello'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, evaluatingB1SayHelloArguments_, output_, (System.currentTimeMillis() - evaluatingB1SayHelloStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'evaluatingB1SayHello' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String greetThePerson, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return stringAdd("Evaluating Say Hello to: ", greetThePerson);
    }
}
