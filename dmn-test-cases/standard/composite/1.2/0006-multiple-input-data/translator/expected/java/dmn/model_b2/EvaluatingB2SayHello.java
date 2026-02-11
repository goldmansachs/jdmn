package model_b2;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Evaluating B2 Say Hello"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.trisotech.com/definitions/_9d46ece4-a96c-4cb0-abc0-0ca121ac3768",
    name = "Evaluating B2 Say Hello",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class EvaluatingB2SayHello extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "model_b2",
        "Evaluating B2 Say Hello",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final model_a.GreetThePerson modela_greetThePerson;

    public EvaluatingB2SayHello() {
        this(new model_a.GreetThePerson());
    }

    public EvaluatingB2SayHello(model_a.GreetThePerson modela_greetThePerson) {
        this.modela_greetThePerson = modela_greetThePerson;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("modelA.Person name"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'EvaluatingB2SayHello'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((EvaluatingB2SayHelloInput_)input_).getModela_personName(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'EvaluatingB2SayHello'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new EvaluatingB2SayHelloInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'EvaluatingB2SayHello'", e);
            return null;
        }
    }

    public String apply(String modela_personName, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Evaluating B2 Say Hello'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long evaluatingB2SayHelloStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments evaluatingB2SayHelloArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            evaluatingB2SayHelloArguments_.put("modelA.Person name", modela_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, evaluatingB2SayHelloArguments_);

            // Evaluate decision 'Evaluating B2 Say Hello'
            String output_ = lambda.apply(modela_personName, context_);

            // End decision 'Evaluating B2 Say Hello'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, evaluatingB2SayHelloArguments_, output_, (System.currentTimeMillis() - evaluatingB2SayHelloStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Evaluating B2 Say Hello' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String modela_personName = 0 < args_.length ? (String) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String modela_greetThePerson = EvaluatingB2SayHello.this.modela_greetThePerson.apply(modela_personName, context_);

                return stringAdd("Evaluating Say Hello to: ", modela_greetThePerson);
            }
        };
}
