
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "decisionService_009"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionService_009",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_009 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionService_009",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class DecisionService_009LazyHolder {
        static final DecisionService_009 INSTANCE = new DecisionService_009();
    }
    public static DecisionService_009 instance() {
        return DecisionService_009LazyHolder.INSTANCE;
    }

    private final Decision_009_2 decision_009_2;

    private DecisionService_009() {
        this(new Decision_009_2());
    }

    private DecisionService_009(Decision_009_2 decision_009_2) {
        this.decision_009_2 = decision_009_2;
    }

    public String apply(String decision_009_3, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start DS 'decisionService_009'
            long decisionService_009StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_009Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionService_009Arguments_.put("decision_009_3", decision_009_3);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_009Arguments_);

            // Bind input decisions
            cache_.bind("decision_009_3", decision_009_3);

            // Apply child decisions
            String decision_009_2 = this.decision_009_2.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate DS 'decisionService_009'
            String output_ = evaluate(decision_009_2, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End DS 'decisionService_009'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_009Arguments_, output_, (System.currentTimeMillis() - decisionService_009StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_009' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String decision_009_2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return decision_009_2;
    }
}