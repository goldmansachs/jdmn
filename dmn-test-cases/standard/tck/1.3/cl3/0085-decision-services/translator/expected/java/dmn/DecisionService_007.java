
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "decisionService_007"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionService_007",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_007 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionService_007",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class DecisionService_007LazyHolder {
        static final DecisionService_007 INSTANCE = new DecisionService_007();
    }
    public static DecisionService_007 instance() {
        return DecisionService_007LazyHolder.INSTANCE;
    }

    private final Decision_007_2 decision_007_2;

    private DecisionService_007() {
        this(new Decision_007_2());
    }

    private DecisionService_007(Decision_007_2 decision_007_2) {
        this.decision_007_2 = decision_007_2;
    }

    public Boolean apply(String decision_007_3, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start DS 'decisionService_007'
            long decisionService_007StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_007Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionService_007Arguments_.put("decision_007_3", decision_007_3);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_007Arguments_);

            // Bind input decisions
            cache_.bind("decision_007_3", decision_007_3);

            // Evaluate DS 'decisionService_007'
            Boolean output_ = evaluate(decision_007_3, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End DS 'decisionService_007'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_007Arguments_, output_, (System.currentTimeMillis() - decisionService_007StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_007' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(String decision_007_3, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply child decisions
        Boolean decision_007_2 = DecisionService_007.this.decision_007_2.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

        return decision_007_2;
    }
}