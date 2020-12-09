
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "evaluatingB2SayHello"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "evaluatingB2SayHello",
    label = "Evaluating B2 Say Hello",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class EvaluatingB2SayHello extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "evaluatingB2SayHello",
        "Evaluating B2 Say Hello",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final GreetThePerson greetThePerson;

    public EvaluatingB2SayHello() {
        this(new GreetThePerson());
    }

    public EvaluatingB2SayHello(GreetThePerson greetThePerson) {
        this.greetThePerson = greetThePerson;
    }

    public String apply(String personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(personName, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(String personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'evaluatingB2SayHello'
            long evaluatingB2SayHelloStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments evaluatingB2SayHelloArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            evaluatingB2SayHelloArguments_.put("Person Name", personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, evaluatingB2SayHelloArguments_);

            // Apply child decisions
            String greetThePerson = this.greetThePerson.apply(personName, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'evaluatingB2SayHello'
            String output_ = evaluate(greetThePerson, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'evaluatingB2SayHello'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, evaluatingB2SayHelloArguments_, output_, (System.currentTimeMillis() - evaluatingB2SayHelloStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'evaluatingB2SayHello' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String greetThePerson, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return stringAdd("Evaluating Say Hello to: ", greetThePerson);
    }
}
