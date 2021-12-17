
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "decisionService_003"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionService_003",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_003 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionService_003",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static final DecisionService_003 INSTANCE = new DecisionService_003();

    private final Decision_003 decision_003;

    public DecisionService_003() {
        this(new Decision_003());
    }

    public DecisionService_003(Decision_003 decision_003) {
        this.decision_003 = decision_003;
    }

    public static String decisionService_003(String inputData_003, String decision_003_input_1, String decision_003_input_2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return INSTANCE.apply(inputData_003, decision_003_input_1, decision_003_input_2, annotationSet_, eventListener_, externalExecutor_, cache_);
    }

    private String apply(String inputData_003, String decision_003_input_1, String decision_003_input_2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start DS 'decisionService_003'
            long decisionService_003StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_003Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionService_003Arguments_.put("inputData_003", inputData_003);
            decisionService_003Arguments_.put("decision_003_input_1", decision_003_input_1);
            decisionService_003Arguments_.put("decision_003_input_2", decision_003_input_2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_003Arguments_);

            // Bind input decisions
            cache_.bind("decision_003_input_1", decision_003_input_1);
            cache_.bind("decision_003_input_2", decision_003_input_2);

            // Apply child decisions
            String decision_003 = this.decision_003.apply(inputData_003, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate DS 'decisionService_003'
            String output_ = evaluate(decision_003, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End DS 'decisionService_003'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_003Arguments_, output_, (System.currentTimeMillis() - decisionService_003StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_003' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String decision_003, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return decision_003;
    }
}