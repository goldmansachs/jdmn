
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "decisionService_013"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionService_013",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_013 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionService_013",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class DecisionService_013LazyHolder {
        static final DecisionService_013 INSTANCE = new DecisionService_013();
    }
    public static DecisionService_013 instance() {
        return DecisionService_013LazyHolder.INSTANCE;
    }

    private final Decision_013_2 decision_013_2;

    private DecisionService_013() {
        this(new Decision_013_2());
    }

    private DecisionService_013(Decision_013_2 decision_013_2) {
        this.decision_013_2 = decision_013_2;
    }

    public String apply(String inputData_013_1, String decision_013_3, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start DS 'decisionService_013'
            long decisionService_013StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_013Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionService_013Arguments_.put("inputData_013_1", inputData_013_1);
            decisionService_013Arguments_.put("decision_013_3", decision_013_3);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_013Arguments_);

            // Bind input decisions
            cache_.bind("decision_013_3", decision_013_3);

            // Evaluate DS 'decisionService_013'
            String output_ = evaluate(inputData_013_1, decision_013_3, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End DS 'decisionService_013'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_013Arguments_, output_, (System.currentTimeMillis() - decisionService_013StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_013' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String inputData_013_1, String decision_013_3, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply child decisions
        String decision_013_2 = DecisionService_013.this.decision_013_2.apply(inputData_013_1, annotationSet_, eventListener_, externalExecutor_, cache_);

        return decision_013_2;
    }
}