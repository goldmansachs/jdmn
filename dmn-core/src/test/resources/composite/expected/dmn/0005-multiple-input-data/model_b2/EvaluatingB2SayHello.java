package model_b2;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "EvaluatingB2SayHello"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "model_b2",
    name = "EvaluatingB2SayHello",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class EvaluatingB2SayHello extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "model_b2",
        "EvaluatingB2SayHello",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final say_hello_1id1d.GreetThePerson say_hello_1id1d_greetThePerson;

    public EvaluatingB2SayHello() {
        this(new say_hello_1id1d.GreetThePerson());
    }

    public EvaluatingB2SayHello(say_hello_1id1d.GreetThePerson say_hello_1id1d_greetThePerson) {
        this.say_hello_1id1d_greetThePerson = say_hello_1id1d_greetThePerson;
    }

    public String apply(String say_hello_1id1d_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(say_hello_1id1d_personName, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public String apply(String say_hello_1id1d_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'EvaluatingB2SayHello'
            long evaluatingB2SayHelloStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments evaluatingB2SayHelloArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            evaluatingB2SayHelloArguments_.put("say_hello_1id1d_personName", say_hello_1id1d_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, evaluatingB2SayHelloArguments_);

            // Apply child decisions
            String say_hello_1id1d_greetThePerson = this.say_hello_1id1d_greetThePerson.apply(say_hello_1id1d_personName, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate decision 'EvaluatingB2SayHello'
            String output_ = evaluate(say_hello_1id1d_greetThePerson, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'EvaluatingB2SayHello'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, evaluatingB2SayHelloArguments_, output_, (System.currentTimeMillis() - evaluatingB2SayHelloStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'EvaluatingB2SayHello' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String say_hello_1id1d_greetThePerson, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return stringAdd("Evaluating Say Hello to: ", say_hello_1id1d_greetThePerson);
    }
}
