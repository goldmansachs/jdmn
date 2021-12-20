
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_006_1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_006_1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_006_1 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_006_1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision_006_1() {
    }

    public String apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision_006_1'
            long decision_006_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_006_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_006_1Arguments_);

            // Evaluate decision 'decision_006_1'
            String output_ = evaluate(annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decision_006_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_006_1Arguments_, output_, (System.currentTimeMillis() - decision_006_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_006_1' evaluation", e);
            return null;
        }
    }

    protected String evaluate(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return DecisionService_006.instance().apply("bar", annotationSet_, eventListener_, externalExecutor_, cache_);
    }
}
