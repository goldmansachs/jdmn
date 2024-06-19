
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"ds.ftl", "decisionService_013"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionService_013",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_013 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
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

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("inputData_013_1"), input_.get("decision_013_3"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionService_013'", e);
            return null;
        }
    }

    public String apply(String inputData_013_1, String decision_013_3, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start DS 'decisionService_013'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decisionService_013StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_013Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionService_013Arguments_.put("inputData_013_1", inputData_013_1);
            decisionService_013Arguments_.put("decision_013_3", decision_013_3);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_013Arguments_);

            // Bind input decisions
            cache_.bind("decision_013_3", decision_013_3);

            // Evaluate DS 'decisionService_013'
            String output_ = lambda.apply(inputData_013_1, decision_013_3, context_);

            // End DS 'decisionService_013'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_013Arguments_, output_, (System.currentTimeMillis() - decisionService_013StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_013' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String inputData_013_1 = 0 < args_.length ? (String) args_[0] : null;
                String decision_013_3 = 1 < args_.length ? (String) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String decision_013_2 = DecisionService_013.this.decision_013_2.apply(inputData_013_1, context_);

                return decision_013_2;
            }
    };
}