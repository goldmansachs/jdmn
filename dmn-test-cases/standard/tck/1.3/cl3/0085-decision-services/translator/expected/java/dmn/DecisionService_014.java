
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "decisionService_014"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionService_014",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_014 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
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
    public String apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("inputData_014_1"), input_.get("decision_014_3"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionService_014'", e);
            return null;
        }
    }

    public String apply(String inputData_014_1, String decision_014_3, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start DS 'decisionService_014'
            long decisionService_014StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_014Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionService_014Arguments_.put("inputData_014_1", inputData_014_1);
            decisionService_014Arguments_.put("decision_014_3", decision_014_3);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_014Arguments_);

            // Bind input decisions
            cache_.bind("decision_014_3", decision_014_3);

            // Evaluate DS 'decisionService_014'
            String output_ = lambda.apply(inputData_014_1, decision_014_3, annotationSet_, eventListener_, externalExecutor_, cache_);

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
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 2 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[2] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 3 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[3] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 4 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[4] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 5 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[5] : null;

                // Apply child decisions
                String decision_014_2 = DecisionService_014.this.decision_014_2.apply(inputData_014_1, annotationSet_, eventListener_, externalExecutor_, cache_);

                return decision_014_2;
            }
    };
}