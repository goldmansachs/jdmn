
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"ds.ftl", "decisionService_003"})
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

    private static class DecisionService_003LazyHolder {
        static final DecisionService_003 INSTANCE = new DecisionService_003();
    }
    public static DecisionService_003 instance() {
        return DecisionService_003LazyHolder.INSTANCE;
    }

    private final Decision_003 decision_003;

    private DecisionService_003() {
        this(new Decision_003());
    }

    private DecisionService_003(Decision_003 decision_003) {
        this.decision_003 = decision_003;
    }

    @java.lang.Override()
    public String apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("inputData_003"), input_.get("decision_003_input_1"), input_.get("decision_003_input_2"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionService_003'", e);
            return null;
        }
    }

    public String apply(String inputData_003, String decision_003_input_1, String decision_003_input_2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
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

            // Evaluate DS 'decisionService_003'
            String output_ = lambda.apply(inputData_003, decision_003_input_1, decision_003_input_2, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End DS 'decisionService_003'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_003Arguments_, output_, (System.currentTimeMillis() - decisionService_003StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_003' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String inputData_003 = 0 < args_.length ? (String) args_[0] : null;
                String decision_003_input_1 = 1 < args_.length ? (String) args_[1] : null;
                String decision_003_input_2 = 2 < args_.length ? (String) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 3 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[3] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 4 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[4] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 5 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[5] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 6 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[6] : null;

                // Apply child decisions
                String decision_003 = DecisionService_003.this.decision_003.apply(inputData_003, annotationSet_, eventListener_, externalExecutor_, cache_);

                return decision_003;
            }
    };
}