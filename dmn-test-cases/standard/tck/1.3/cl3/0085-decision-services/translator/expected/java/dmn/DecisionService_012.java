
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "decisionService_012"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionService_012",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_012 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionService_012",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class DecisionService_012LazyHolder {
        static final DecisionService_012 INSTANCE = new DecisionService_012();
    }
    public static DecisionService_012 instance() {
        return DecisionService_012LazyHolder.INSTANCE;
    }

    private final Decision_012_2 decision_012_2;

    private DecisionService_012() {
        this(new Decision_012_2());
    }

    private DecisionService_012(Decision_012_2 decision_012_2) {
        this.decision_012_2 = decision_012_2;
    }

    public String apply(String inputData_012_1, String inputData_012_2, String decision_012_3, String decision_012_4, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start DS 'decisionService_012'
            long decisionService_012StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_012Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionService_012Arguments_.put("inputData_012_1", inputData_012_1);
            decisionService_012Arguments_.put("inputData_012_2", inputData_012_2);
            decisionService_012Arguments_.put("decision_012_3", decision_012_3);
            decisionService_012Arguments_.put("decision_012_4", decision_012_4);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_012Arguments_);

            // Bind input decisions
            cache_.bind("decision_012_3", decision_012_3);
            cache_.bind("decision_012_4", decision_012_4);

            // Evaluate DS 'decisionService_012'
            String output_ = evaluate(inputData_012_1, inputData_012_2, decision_012_3, decision_012_4, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End DS 'decisionService_012'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_012Arguments_, output_, (System.currentTimeMillis() - decisionService_012StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_012' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String inputData_012_1, String inputData_012_2, String decision_012_3, String decision_012_4, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply child decisions
        String decision_012_2 = DecisionService_012.this.decision_012_2.apply(inputData_012_1, inputData_012_2, annotationSet_, eventListener_, externalExecutor_, cache_);

        return decision_012_2;
    }
}