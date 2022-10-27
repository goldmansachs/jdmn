package model_b2;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'Evaluating B2 Say Hello'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "model_b2",
    name = "'Evaluating B2 Say Hello'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class EvaluatingB2SayHello extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "model_b2",
        "'Evaluating B2 Say Hello'",
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
    public String apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("modelA.'Person name'"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'EvaluatingB2SayHello'", e);
            return null;
        }
    }

    public String apply(String modela_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision ''Evaluating B2 Say Hello''
            long evaluatingB2SayHelloStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments evaluatingB2SayHelloArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            evaluatingB2SayHelloArguments_.put("modelA.'Person name'", modela_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, evaluatingB2SayHelloArguments_);

            // Evaluate decision ''Evaluating B2 Say Hello''
            String output_ = lambda.apply(modela_personName, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision ''Evaluating B2 Say Hello''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, evaluatingB2SayHelloArguments_, output_, (System.currentTimeMillis() - evaluatingB2SayHelloStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''Evaluating B2 Say Hello'' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String modela_personName = 0 < args_.length ? (String) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 1 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[1] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 2 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[2] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 3 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[3] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 4 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[4] : null;

                // Apply child decisions
                String modela_greetThePerson = EvaluatingB2SayHello.this.modela_greetThePerson.apply(modela_personName, annotationSet_, eventListener_, externalExecutor_, cache_);

                return stringAdd("Evaluating Say Hello to: ", modela_greetThePerson);
            }
        };
}
