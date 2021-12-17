
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "decisionService_001"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionService_001",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_001 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionService_001",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static final DecisionService_001 INSTANCE = new DecisionService_001();

    private final Decision_001 decision_001;

    public DecisionService_001() {
        this(new Decision_001());
    }

    public DecisionService_001(Decision_001 decision_001) {
        this.decision_001 = decision_001;
    }

    public static String decisionService_001(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return INSTANCE.apply(annotationSet_, eventListener_, externalExecutor_, cache_);
    }

    private String apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start DS 'decisionService_001'
            long decisionService_001StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_001Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_001Arguments_);

            // Apply child decisions
            String decision_001 = this.decision_001.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate DS 'decisionService_001'
            String output_ = evaluate(decision_001, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End DS 'decisionService_001'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_001Arguments_, output_, (System.currentTimeMillis() - decisionService_001StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_001' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String decision_001, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return decision_001;
    }
}