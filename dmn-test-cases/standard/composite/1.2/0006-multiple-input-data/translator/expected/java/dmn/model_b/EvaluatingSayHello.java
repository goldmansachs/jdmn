package model_b;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Evaluating Say Hello"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "model_b",
    name = "Evaluating Say Hello",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class EvaluatingSayHello extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "model_b",
        "Evaluating Say Hello",
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
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("modelA.Person name"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'EvaluatingSayHello'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((EvaluatingSayHelloInput_)input_).getModela_personName(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'EvaluatingSayHello'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new EvaluatingSayHelloInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'EvaluatingSayHello'", e);
            return null;
        }
    }

    public String apply(String modela_personName, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Evaluating Say Hello'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long evaluatingSayHelloStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments evaluatingSayHelloArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            evaluatingSayHelloArguments_.put("modelA.Person name", modela_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, evaluatingSayHelloArguments_);

            // Evaluate decision 'Evaluating Say Hello'
            String output_ = lambda.apply(modela_personName, context_);

            // End decision 'Evaluating Say Hello'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, evaluatingSayHelloArguments_, output_, (System.currentTimeMillis() - evaluatingSayHelloStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Evaluating Say Hello' evaluation", e);
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
                String modela_greetThePerson = EvaluatingSayHello.this.modela_greetThePerson.apply(modela_personName, context_);

                return stringAdd("Evaluating Say Hello to: ", modela_greetThePerson);
            }
        };
}
