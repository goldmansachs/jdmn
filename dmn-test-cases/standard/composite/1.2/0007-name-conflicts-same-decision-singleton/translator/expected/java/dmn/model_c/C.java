package model_c;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "c"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "model_c",
    name = "c",
    label = "C",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class C extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "model_c",
        "c",
        "C",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public C() {
    }

    public Object apply(String model_a_a, String model_b_a, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(model_a_a, model_b_a, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Object apply(String model_a_a, String model_b_a, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'c'
            long cStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments cArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            cArguments_.put("Model A.A", model_a_a);
            cArguments_.put("Model B.A", model_b_a);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, cArguments_);

            // Evaluate decision 'c'
            Object output_ = evaluate(model_a_a, model_b_a, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'c'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, cArguments_, output_, (System.currentTimeMillis() - cStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'c' evaluation", e);
            return null;
        }
    }

    protected Object evaluate(String model_a_a, String model_b_a, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return stringAdd(stringAdd(stringAdd("A: ", model_a_a), "; B: "), model_b_a);
    }
}
