package model_b;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "EvaluatingSayHello"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "model_b",
    name = "EvaluatingSayHello",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class EvaluatingSayHello extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "model_b",
        "EvaluatingSayHello",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final model_a.GreetThePerson modela_greetThePerson;

    public EvaluatingSayHello() {
        this(new model_a.GreetThePerson());
    }

    public EvaluatingSayHello(model_a.GreetThePerson modela_greetThePerson) {
        this.modela_greetThePerson = modela_greetThePerson;
    }

    public String apply(String modela_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(modela_personName, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(String modela_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'EvaluatingSayHello'
            long evaluatingSayHelloStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments evaluatingSayHelloArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            evaluatingSayHelloArguments_.put("modelA.PersonName", modela_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, evaluatingSayHelloArguments_);

            // Apply child decisions
            String modela_greetThePerson = this.modela_greetThePerson.apply(modela_personName, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'EvaluatingSayHello'
            String output_ = evaluate(modela_greetThePerson, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'EvaluatingSayHello'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, evaluatingSayHelloArguments_, output_, (System.currentTimeMillis() - evaluatingSayHelloStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'EvaluatingSayHello' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String modela_greetThePerson, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return stringAdd("Evaluating Say Hello to: ", modela_greetThePerson);
    }
}
