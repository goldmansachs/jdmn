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
    private final model_a.GreetThePerson model_a_greetThePerson;

    public EvaluatingSayHello() {
        this(new model_a.GreetThePerson());
    }

    public EvaluatingSayHello(model_a.GreetThePerson model_a_greetThePerson) {
        this.model_a_greetThePerson = model_a_greetThePerson;
    }

    public String apply(String model_a_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(model_a_personName, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public String apply(String model_a_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'EvaluatingSayHello'
            long evaluatingSayHelloStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments evaluatingSayHelloArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            evaluatingSayHelloArguments_.put("model_a_personName", model_a_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, evaluatingSayHelloArguments_);

            // Apply child decisions
            String model_a_greetThePerson = this.model_a_greetThePerson.apply(model_a_personName, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate decision 'EvaluatingSayHello'
            String output_ = evaluate(model_a_greetThePerson, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'EvaluatingSayHello'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, evaluatingSayHelloArguments_, output_, (System.currentTimeMillis() - evaluatingSayHelloStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'EvaluatingSayHello' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String model_a_greetThePerson, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return stringAdd("Evaluating Say Hello to: ", model_a_greetThePerson);
    }
}
