
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
public class ModelCDecisionBasedOnBs extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
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

    public Object apply(String personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(personName, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Object apply(String personName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'modelCDecisionBasedOnBs'
            long modelCDecisionBasedOnBsStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments modelCDecisionBasedOnBsArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            modelCDecisionBasedOnBsArguments_.put("Person Name", personName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, modelCDecisionBasedOnBsArguments_);

            // Apply child decisions
            String evaluatingB1SayHello = this.evaluatingB1SayHello.apply(personName, annotationSet_, eventListener_, externalExecutor_, cache_);
            String evaluatingB2SayHello = this.evaluatingB2SayHello.apply(personName, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'modelCDecisionBasedOnBs'
            Object output_ = evaluate(evaluatingB1SayHello, evaluatingB2SayHello, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'modelCDecisionBasedOnBs'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, modelCDecisionBasedOnBsArguments_, output_, (System.currentTimeMillis() - modelCDecisionBasedOnBsStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'modelCDecisionBasedOnBs' evaluation", e);
            return null;
        }
    }

    protected Object evaluate(String evaluatingB1SayHello, String evaluatingB2SayHello, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return stringAdd(stringAdd(stringAdd("B1: ", evaluatingB1SayHello), "; B2: "), evaluatingB2SayHello);
    }
}
