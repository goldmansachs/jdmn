
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "evaluatingB1SayHello"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "evaluatingB1SayHello",
    label = "Evaluating Say Hello",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class EvaluatingB1SayHello extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "evaluatingB1SayHello",
        "Evaluating Say Hello",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final GreetThePerson greetThePerson;

    public EvaluatingB1SayHello() {
        this(new GreetThePerson());
    }

    public EvaluatingB1SayHello(GreetThePerson greetThePerson) {
        this.greetThePerson = greetThePerson;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("Person Name"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'EvaluatingB1SayHello'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((EvaluatingB1SayHelloInput_)input_).getPersonName(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'EvaluatingB1SayHello'", e);
            return null;
        }
    }

    public String apply(String personName, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'evaluatingB1SayHello'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long evaluatingB1SayHelloStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments evaluatingB1SayHelloArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            evaluatingB1SayHelloArguments_.put("Person Name", personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, evaluatingB1SayHelloArguments_);

            // Evaluate decision 'evaluatingB1SayHello'
            String output_ = lambda.apply(personName, context_);

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
                String personName = 0 < args_.length ? (String) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String greetThePerson = EvaluatingB1SayHello.this.greetThePerson.apply(personName, context_);

                return stringAdd("Evaluating Say Hello to: ", greetThePerson);
            }
        };
}
