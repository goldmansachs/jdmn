package model_b;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'Evaluating Say Hello'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "model_b",
    name = "'Evaluating Say Hello'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class EvaluatingSayHello extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "model_b",
        "'Evaluating Say Hello'",
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

    @java.lang.Override()
    public String apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("'Person name'"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'EvaluatingSayHello'", e);
            return null;
        }
    }

    public String apply(String modela_personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision ''Evaluating Say Hello''
            long evaluatingSayHelloStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments evaluatingSayHelloArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            evaluatingSayHelloArguments_.put("modelA.'Person name'", modela_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, evaluatingSayHelloArguments_);

            // Evaluate decision ''Evaluating Say Hello''
            String output_ = lambda.apply(modela_personName, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision ''Evaluating Say Hello''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, evaluatingSayHelloArguments_, output_, (System.currentTimeMillis() - evaluatingSayHelloStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''Evaluating Say Hello'' evaluation", e);
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
                String modela_greetThePerson = EvaluatingSayHello.this.modela_greetThePerson.apply(modela_personName, annotationSet_, eventListener_, externalExecutor_, cache_);

                return stringAdd("Evaluating Say Hello to: ", modela_greetThePerson);
            }
        };
}
