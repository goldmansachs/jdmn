
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "greetThePerson"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "greetThePerson",
    label = "Great the person",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class GreetThePerson extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "greetThePerson",
        "Great the person",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public GreetThePerson() {
    }

    public String apply(String personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(personName, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(String personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'greetThePerson'
            long greetThePersonStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments greetThePersonArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            greetThePersonArguments_.put("Person Name", personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, greetThePersonArguments_);

            // Evaluate decision 'greetThePerson'
            String output_ = evaluate(personName, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'greetThePerson'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, greetThePersonArguments_, output_, (System.currentTimeMillis() - greetThePersonStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'greetThePerson' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return stringAdd("Hello, ", personName);
    }
}
