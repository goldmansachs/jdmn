package model_b1;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "evaluatingB1SayHello"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/definitions/model-b1",
    name = "evaluatingB1SayHello",
    label = "Evaluating Say Hello",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class EvaluatingB1SayHello extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
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

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("model-a.personName"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'EvaluatingB1SayHello'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((EvaluatingB1SayHelloInput_)input_).getModel_a_personName(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'EvaluatingB1SayHello'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new EvaluatingB1SayHelloInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'EvaluatingB1SayHello'", e);
            return null;
        }
    }

    public String apply(String model_a_personName, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'evaluatingB1SayHello'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long evaluatingB1SayHelloStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments evaluatingB1SayHelloArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            evaluatingB1SayHelloArguments_.put("model-a.personName", model_a_personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, evaluatingB1SayHelloArguments_);

            // Evaluate decision 'evaluatingB1SayHello'
            String output_ = lambda.apply(model_a_personName, context_);

            // End decision 'evaluatingB1SayHello'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, evaluatingB1SayHelloArguments_, output_, (System.currentTimeMillis() - evaluatingB1SayHelloStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'evaluatingB1SayHello' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String model_a_personName = 0 < args_.length ? (String) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String greetThePerson = EvaluatingB1SayHello.this.greetThePerson.apply(model_a_personName, context_);

                return stringAdd("Evaluating Say Hello to: ", greetThePerson);
            }
        };
}
