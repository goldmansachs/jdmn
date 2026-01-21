
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"ds.ftl", "decisionService_014"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionService_014",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_014 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionService_014",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class DecisionService_014LazyHolder {
        static final DecisionService_014 INSTANCE = new DecisionService_014();
    }
    public static DecisionService_014 instance() {
        return DecisionService_014LazyHolder.INSTANCE;
    }

    private final Decision_014_2 decision_014_2;

    private DecisionService_014() {
        this(new Decision_014_2());
    }

    private DecisionService_014(Decision_014_2 decision_014_2) {
        this.decision_014_2 = decision_014_2;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("inputData_014_1"), input_.get("decision_014_3"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionService_014'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((DecisionService_014Input_)input_).getInputData_014_1(), ((DecisionService_014Input_)input_).getDecision_014_3(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'DecisionService_014'", e);
            return null;
        }
    }

    public String apply(String inputData_014_1, String decision_014_3, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start DS 'decisionService_014'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decisionService_014StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_014Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionService_014Arguments_.put("inputData_014_1", inputData_014_1);
            decisionService_014Arguments_.put("decision_014_3", decision_014_3);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_014Arguments_);

            // Bind input decisions
            cache_.bind("decision_014_3", decision_014_3);

            // Evaluate DS 'decisionService_014'
            String output_ = lambda.apply(inputData_014_1, decision_014_3, context_);

            // End DS 'decisionService_014'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_014Arguments_, output_, (System.currentTimeMillis() - decisionService_014StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_014' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String inputData_014_1 = 0 < args_.length ? (String) args_[0] : null;
                String decision_014_3 = 1 < args_.length ? (String) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String decision_014_2 = DecisionService_014.this.decision_014_2.apply(inputData_014_1, context_);

                return decision_014_2;
            }
    };
}