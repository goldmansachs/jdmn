
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "modelCDecisionBasedOnBs"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "modelCDecisionBasedOnBs",
    label = "Model C Decision based on Bs",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ModelCDecisionBasedOnBs extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "modelCDecisionBasedOnBs",
        "Model C Decision based on Bs",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final EvaluatingB1SayHello evaluatingB1SayHello;
    private final EvaluatingB2SayHello evaluatingB2SayHello;

    public ModelCDecisionBasedOnBs() {
        this(new EvaluatingB1SayHello(), new EvaluatingB2SayHello());
    }

    public ModelCDecisionBasedOnBs(EvaluatingB1SayHello evaluatingB1SayHello, EvaluatingB2SayHello evaluatingB2SayHello) {
        this.evaluatingB1SayHello = evaluatingB1SayHello;
        this.evaluatingB2SayHello = evaluatingB2SayHello;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("Person Name"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ModelCDecisionBasedOnBs'", e);
            return null;
        }
    }

    public String apply(String personName, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'modelCDecisionBasedOnBs'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long modelCDecisionBasedOnBsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments modelCDecisionBasedOnBsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            modelCDecisionBasedOnBsArguments_.put("Person Name", personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, modelCDecisionBasedOnBsArguments_);

            // Evaluate decision 'modelCDecisionBasedOnBs'
            String output_ = lambda.apply(personName, context_);

            // End decision 'modelCDecisionBasedOnBs'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, modelCDecisionBasedOnBsArguments_, output_, (System.currentTimeMillis() - modelCDecisionBasedOnBsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'modelCDecisionBasedOnBs' evaluation", e);
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
                String evaluatingB1SayHello = ModelCDecisionBasedOnBs.this.evaluatingB1SayHello.apply(personName, context_);
                String evaluatingB2SayHello = ModelCDecisionBasedOnBs.this.evaluatingB2SayHello.apply(personName, context_);

                return stringAdd(stringAdd(stringAdd("B1: ", evaluatingB1SayHello), "; B2: "), evaluatingB2SayHello);
            }
        };
}
