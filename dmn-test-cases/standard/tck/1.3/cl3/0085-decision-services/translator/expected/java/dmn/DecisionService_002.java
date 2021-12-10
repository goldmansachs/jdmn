
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "decisionService_002"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionService_002",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_002 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionService_002",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static final DecisionService_002 INSTANCE = new DecisionService_002();

    private final Decision_002 decision_002;

    public DecisionService_002() {
        this(new Decision_002());
    }

    public DecisionService_002(Decision_002 decision_002) {
        this.decision_002 = decision_002;
    }

    public static String decisionService_002(String decision_002_input, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return INSTANCE.apply(decision_002_input, annotationSet_, eventListener_, externalExecutor_, cache_);
    }

    private String apply(String decision_002_input, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start DS 'decisionService_002'
            long decisionService_002StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_002Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionService_002Arguments_.put("decision_002_input", decision_002_input);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_002Arguments_);

            // Bind input decisions
            cache_.bind("decision_002_input", decision_002_input);

            // Apply child decisions
            String decision_002 = this.decision_002.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate DS 'decisionService_002'
            String output_ = evaluate(decision_002, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End DS 'decisionService_002'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_002Arguments_, output_, (System.currentTimeMillis() - decisionService_002StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_002' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String decision_002, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return decision_002;
    }
}